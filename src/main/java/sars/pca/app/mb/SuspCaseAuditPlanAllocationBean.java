package sars.pca.app.mb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseAuditPlanAllocationBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    private List<User> auditors = new ArrayList<>();
    private List<LocationOffice> ciOffices = new ArrayList<>();
    private User allocatedUser;
    private AttachmentType selectedAttachmentType;
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    List<CaseStatus> pools = new ArrayList<>();
    private String searchInputParam;
    private LocationOffice selectedLocationOffice;
    private CaseStatus selectedStatus;

    @PostConstruct
    public void init() {
        reset().setList(true);
        List<CaseStatus> sarCaseStatuses = new ArrayList<>();
        //The status is set to ACTIVE_RA_APPROVAL when case is coming from RA_POOL
        sarCaseStatuses.add(CaseStatus.ACTIVE_RA_APPROVAL);
        //The status is set to ACTIVE_VDDL when case is coming from VDDL_POOL
        sarCaseStatuses.add(CaseStatus.ACTIVE_VDDL);
        //The status is set to CI_ALLOCATION after allocation by the CI Ops Manager.
        sarCaseStatuses.add(CaseStatus.CI_ALLOCATION);
        //The status is set to CI_PLAN_REWORK upon sending the case back Auditors for rework.
        sarCaseStatuses.add(CaseStatus.CI_PLAN_REWORK);
        //The status is set to AUDIT_REPORTING_RE_WORK upon sending the case back Auditors for rework.
        sarCaseStatuses.add(CaseStatus.AUDIT_REPORTING_RE_WORK);
        //The status is set to DM_CASE_REFERED_TO_CI_FOR_CLOSURE when case is sent back Auditors for closure.
        sarCaseStatuses.add(CaseStatus.DM_CASE_REFERED_TO_CI_FOR_CLOSURE);
        //The status is set to CI_AUDIT_REPORTING_REVIEW after an audit report has been approved.
        sarCaseStatuses.add(CaseStatus.CI_AUDIT_REPORTING_REVIEW);
        //The status is set to CI_PLAN_REVIEW after an audit plan has been approved.
        sarCaseStatuses.add(CaseStatus.CI_PLAN_REVIEW);
        addCollections(suspiciousCaseService.findByStatusAndCILocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        ciOffices.addAll(locationOfficeService.findByOfficeType(OfficeType.CI_OFFICE));
    }

    public void nextSuspCaseFromPool() {
        pools.clear();
        pools.add(CaseStatus.FULL_SCOPE_POOL);
        pools.add(CaseStatus.LIMITED_SCOPE_POOL);
        pools.add(CaseStatus.INTEGRATED_SCOPE_POOL);
        pools.add(CaseStatus.VDDL_POOL);
        reset().setFetchCaseFromPool(true);
    }

    public void nextSuspCase() {
        SuspiciousCase suspiciousCase = null;
        if (selectedStatus.equals(CaseStatus.VDDL_POOL)) {
            //Get case by VDDL status
            suspiciousCase = suspiciousCaseService.findTopByStatusOrderByCaseRefNumberDesc(selectedStatus);
        } else if (selectedStatus.equals(CaseStatus.VDDL_POOL) && selectedLocationOffice != null) {
            //Get case by VDDL status and CI Office Location
            suspiciousCase = suspiciousCaseService.findByStatusAndCiLocationOfficeOrderByCaseRefNumberDesc(selectedStatus, selectedLocationOffice);
        } else {
            if (selectedStatus != null && selectedLocationOffice != null) {
                //Get case by status and CI Office Location
                suspiciousCase = suspiciousCaseService.findTopByStatusAndCiLocationOfficeOrderByPriorityIndexDesc(selectedStatus, selectedLocationOffice);
            } else if (selectedStatus != null && selectedLocationOffice == null) {
                //Get case by status
                suspiciousCase = suspiciousCaseService.findTopByStatusOrderByPriorityIndexDesc(selectedStatus);
            } else if (StringUtils.isNotBlank(searchInputParam)) {
                //Get case by customs code or case ref number
                suspiciousCase = suspiciousCaseService.findSuspCaseByCustomsCodeOrCaseRefNumber(searchInputParam);
            }
        }
        //Pull the case from the first pool stagin and change status and update to ensure case does not get picked by the next user.
        if (suspiciousCase != null) {
            if (suspiciousCase.getCaseType().equals(CaseType.SAR)) {
                suspiciousCase.setStatus(CaseStatus.ACTIVE_RA_APPROVAL);
            } else {
                suspiciousCase.setStatus(CaseStatus.ACTIVE_VDDL);
            }
            //The CI Ops Manager pulls the case and assign it to himself so that he can allocate it to auditors.
            SarCaseUser sarCaseUser = new SarCaseUser();
            sarCaseUser.setCreatedBy(getActiveUser().getSid());
            sarCaseUser.setCreatedDate(new Date());
            sarCaseUser.setUser(getActiveUser().getUser());
            suspiciousCase.addSarCaseUser(sarCaseUser);

            suspiciousCase.setCiLocationOffice(getActiveUser().getUser().getLocationOffice());
            SuspiciousCase persistentSarCase = suspiciousCaseService.update(suspiciousCase);
            addCollection(persistentSarCase);
            reset().setList(true);
        } else {
            addInformationMessage("No cases available in the pool.");
        }
    }

    public void viewSuspCase(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        addEntity(suspiciousCase);
    }

    public void transfer(SuspiciousCase suspiciousCase) {
        reset().setTransfer(true);
//        ciOffices.clear();
//        ciOffices.addAll(locationOfficeService.findByOfficeType(OfficeType.CI_OFFICE));
        addEntity(suspiciousCase);
    }

    public void allocate(SuspiciousCase suspiciousCase) {
        reset().setAllocate(true);
        auditors.clear();
        auditors.addAll(userService.findByUserRoleAndLocationOffice(UserRoleType.CI_AUDITOR.toString(), suspiciousCase.getCiLocationOffice()));
        addEntity(suspiciousCase);
    }

    public void submit(SuspiciousCase suspiciousCase) {
        if (allocatedUser != null) {
            //Remove the CI_AUDITOR who was working on the case before re-allocating to the next CI_AUDITOR
            for (SarCaseUser sarCaseUser : suspiciousCase.getSarCaseUsers()) {
                if (sarCaseUser.getUser().getUserRole().getDescription().equals(UserRoleType.CI_AUDITOR.toString())) {
                    suspiciousCase.removeSarCaseUser(sarCaseUser);
                }
            }
            SarCaseUser sarCaseUser = new SarCaseUser();
            sarCaseUser.setCreatedBy(getActiveUser().getSid());
            sarCaseUser.setCreatedDate(new Date());
            sarCaseUser.setUser(allocatedUser);
            suspiciousCase.addSarCaseUser(sarCaseUser);
            suspiciousCase.setStatus(CaseStatus.CI_ALLOCATION);
            addInformationMessage("Audit plan allocated successfully.");
            //email to CI Auditor
            emailNotificationSenderService.sendEmailNotification(NotificationType.CI_ALLOCATION, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), allocatedUser, getActiveUser().getUser());

        } else {
            //                  email to CI OPS manager in the same region office wich the case was transfere
            User recipient = userService.findUserByUserRoleAndLocationOffice(UserRoleType.CI_OPS_MANAGER.toString(), suspiciousCase.getCiLocationOffice());
            emailNotificationSenderService.sendEmailNotification(NotificationType.TRANSFER, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

        }
        removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
        reset().setList(true);
    }

    public void addComment(SuspiciousCase suspiciousCase) {
        Comment comment = new Comment();
        comment.setCreatedBy(getActiveUser().getSid());
        comment.setCreatedDate(new Date());
        comment.setRender(true);

        suspiciousCase.addComment(comment);

        addEntity(suspiciousCase);
    }

    public void saveComment(Comment comment) {
        suspiciousCaseService.update(getEntity());
        comment.setRender(false);
    }

    public void updateComment(Comment comment) {
        comment.setRender(true);
    }

    public void removeComment(Comment comment) {
        getEntity().removeComment(comment);
        if (comment.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            try {
                //Setting the file upload bytes to uploaded to documentum on submit
                setFileUploadBytes(IOUtils.toByteArray(file.getInputStream()));
                addAttachment(file.getFileName(), file.getSize());
            } catch (IOException ex) {
                Logger.getLogger(SuspCaseAuditPlanCreationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addAttachment(String fileName, Long fileSize) {
        Attachment attachment = new Attachment();
        attachment.setCode(RandomStringUtils.randomNumeric(5));
        attachment.setCreatedBy(getActiveUser().getSid());
        attachment.setAttachmentType(selectedAttachmentType);
        attachment.setCreatedDate(new Date());
        attachment.setDocumentSize(fileSize.doubleValue());
        attachment.setDescription(fileName);
        getEntity().addAttachment(attachment);
    }

    public JsonDocumentDto uploadFileToDocumentumServer(Attachment attachment) {
        JsonDocumentDto jsonDocumentDto = new JsonDocumentDto();
        List<Properties> propertyList = new ArrayList<>();
        Properties properties = new Properties();
        properties.setPropertyName(attachment.getDescription());
        propertyList.add(properties);
        String base64File = Base64.getEncoder().encodeToString(getFileUploadBytes());
        jsonDocumentDto.setObjectType("sars_tarrif_doc");
        jsonDocumentDto.setObjectName(attachment.getDescription());
        jsonDocumentDto.setContentType(FilenameUtils.getExtension(attachment.getDescription()));
        jsonDocumentDto.setAuthor(getActiveUser().getSid());
        jsonDocumentDto.setProperties(propertyList);
        jsonDocumentDto.setContent(base64File);
        return jsonDocumentDto;
    }

    public void removeAttachment(Attachment attachment) {
        getEntity().removeAttachment(attachment);
        if (attachment.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (getEntity().getCaseType().equals(CaseType.VDDL)) {
            getPublicOfficers().clear();
            getPublicOfficers().addAll(getIbrDataService().getPublicOfficer(getEntity().getIbrData().getClNbr(), getActiveUser().getSid()));
        } else {
            //Retrieve and populate IBR DATA on demand
            populateIbrdata(event.getTab().getId(), getEntity().getSarCase().getCustomExciseCode());
        }
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        reset().setList(true);
    }

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public List<User> getAuditors() {
        return auditors;
    }

    public void setAuditors(List<User> auditors) {
        this.auditors = auditors;
    }

    public User getAllocatedUser() {
        return allocatedUser;
    }

    public void setAllocatedUser(User allocatedUser) {
        this.allocatedUser = allocatedUser;
    }

    public List<LocationOffice> getCiOffices() {
        return ciOffices;
    }

    public void setCiOffices(List<LocationOffice> ciOffices) {
        this.ciOffices = ciOffices;
    }

    public SuspiciousCaseServiceLocal getSuspiciousCaseService() {
        return suspiciousCaseService;
    }

    public void setSuspiciousCaseService(SuspiciousCaseServiceLocal suspiciousCaseService) {
        this.suspiciousCaseService = suspiciousCaseService;
    }

    public AttachmentType getSelectedAttachmentType() {
        return selectedAttachmentType;
    }

    public void setSelectedAttachmentType(AttachmentType selectedAttachmentType) {
        this.selectedAttachmentType = selectedAttachmentType;
    }

    public List<AttachmentType> getAttachmentTypes() {
        return attachmentTypes;
    }

    public void setAttachmentTypes(List<AttachmentType> attachmentTypes) {
        this.attachmentTypes = attachmentTypes;
    }

    public List<CaseStatus> getPools() {
        return pools;
    }

    public void setPools(List<CaseStatus> pools) {
        this.pools = pools;
    }

    public String getSearchInputParam() {
        return searchInputParam;
    }

    public void setSearchInputParam(String searchInputParam) {
        this.searchInputParam = searchInputParam;
    }

    public LocationOffice getSelectedLocationOffice() {
        return selectedLocationOffice;
    }

    public void setSelectedLocationOffice(LocationOffice selectedLocationOffice) {
        this.selectedLocationOffice = selectedLocationOffice;
    }

    public CaseStatus getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(CaseStatus selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

}
