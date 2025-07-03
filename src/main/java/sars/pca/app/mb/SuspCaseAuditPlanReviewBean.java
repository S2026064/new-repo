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
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
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
import sars.pca.app.common.Properties;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.AuditDetails;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.SarCaseUserServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseAuditPlanReviewBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;

    //notification services used
    @Autowired
    private SarCaseUserServiceLocal sarCaseUserService;
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<User> auditors = new ArrayList<>();
    private List<LocationOffice> ciOffices = new ArrayList<>();
    private User allocatedUser;
    private AttachmentType selectedAttachmentType;
    private List<AttachmentType> attachmentTypes = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        List<CaseStatus> sarCaseStatuses = new ArrayList<>();
        if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.SENIOR_AUDITOR.toString())) {
            sarCaseStatuses.add(CaseStatus.CI_SENIOR_AUDITOR_AUDIT_PLAN_REVIEW);
            addCollections(suspiciousCaseService.findByStatusAndCILocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        } else if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.CI_OPS_MANAGER.toString())) {
            sarCaseStatuses.add(CaseStatus.ACTIVE_CI_PLAN);
            sarCaseStatuses.add(CaseStatus.CI_PLAN_REVIEW);
            addCollections(suspiciousCaseService.findByStatusAndCILocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        } else if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.CRCS_OPS_MANAGER.toString())) {
            sarCaseStatuses.add(CaseStatus.CI_MANAGER_REJECTED_AUDIT_PLAN);
            sarCaseStatuses.add(CaseStatus.SENIOR_AUDITOR_REJECTED_AUDIT_PLAN);
            addCollections(suspiciousCaseService.findByStatusAndCrcsLocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        } else if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.GROUP_MANAGER.toString())) {
            sarCaseStatuses.add(CaseStatus.CI_OPS_MANAGER_AUDIT_PLAN_SEND_FOR_APPROVAL);
            addCollections(suspiciousCaseService.findByStatusAndCILocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        }
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
    }

    public void review(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        addEntity(suspiciousCase);
    }

    public void accept(SuspiciousCase suspiciousCase) {
        suspiciousCase.setStatus(CaseStatus.ACTIVE_CI_PLAN);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Audit plan accepted successfully.");
        //email to CI OPS manager 
        emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_PLAN_SUBMITTED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), getActiveUser().getUser().getManager(), getActiveUser().getUser());
        reset().setList(true);
    }

    public void rework(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setStatus(CaseStatus.CI_PLAN_REWORK);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Audit plan send for rework successfully.");
            //email to auditor 
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CI_AUDITOR.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotification(NotificationType.REWORK_AUDIT_PLAN, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());

            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you send the case for rework.");
        }
    }

    public void reject(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.CI_OPS_MANAGER.toString())) {
                suspiciousCase.setStatus(CaseStatus.CI_MANAGER_REJECTED_AUDIT_PLAN);
                //email to crcs ops manager
                User recipient = userService.findByUserRoleDescription(UserRoleType.CRCS_OPS_MANAGER.toString());
                emailNotificationSenderService.sendEmailNotification(NotificationType.REJECT_AUDIT_PLAN, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

            } else if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.SENIOR_AUDITOR.toString())) {
                suspiciousCase.setStatus(CaseStatus.SENIOR_AUDITOR_REJECTED_AUDIT_PLAN);
                //email to CRCS OPS MANAGER
                User recipient = userService.findUserByUserRoleAndLocationOffice(UserRoleType.CRCS_OPS_MANAGER.toString(), suspiciousCase.getCrcsLocationOffice());
                emailNotificationSenderService.sendEmailNotification(NotificationType.REJECT_AUDIT_PLAN, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

            } else if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.CRCS_OPS_MANAGER.toString())) {
                if (suspiciousCase.getStatus().equals(CaseStatus.CI_MANAGER_REJECTED_AUDIT_PLAN)) {
                    suspiciousCase.setStatus(CaseStatus.ACTIVE_CI_PLAN);
                    //email to ci ops manager
                    User recipient = userService.findUserByUserRoleAndLocationOffice(UserRoleType.CI_OPS_MANAGER.toString(), suspiciousCase.getCiLocationOffice());
                    emailNotificationSenderService.sendEmailNotification(NotificationType.DISAPPROVE_REJECT_AUDIT_PLAN, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

                } else if (suspiciousCase.getStatus().equals(CaseStatus.SENIOR_AUDITOR_REJECTED_AUDIT_PLAN)) {
                    suspiciousCase.setStatus(CaseStatus.CI_SENIOR_AUDITOR_AUDIT_PLAN_REVIEW);
                    //email to CI Senior Auditor
                    List<User> recipient = userService.findByUserRoleAndLocationOffice(UserRoleType.SENIOR_AUDITOR.toString(), suspiciousCase.getCiLocationOffice());
                    emailNotificationSenderService.sendEmailNotifications(NotificationType.DISAPPROVE_REJECT_AUDIT_PLAN, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());
                }
            }
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Audit plan  rejected successfully.");
            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you want to reject the case.");
        }
    }

    public void addOrUpdateLetter(Letter letter, String targetPage, Long id) {
        id = getEntity().getId();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("letterKey", letter);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseKey", id);
        openDialogBox(targetPage, null, 1300, 780);
    }

    public void approve(SuspiciousCase suspiciousCase) {
        for (Letter letter : suspiciousCase.getLetters()) {
            letter.setApprove(YesNoEnum.YES);
        }
        if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.GROUP_MANAGER.toString())
                || getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.CI_OPS_MANAGER.toString())) {
            suspiciousCase.setStatus(CaseStatus.CI_PLAN_REVIEW);
            //email to CI Auditor
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CI_AUDITOR.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_PLAN_APPROVED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());
            //email to CI Senior Auditor
            List<User> seniorRecipient = userService.findByUserRoleAndLocationOffice(UserRoleType.SENIOR_AUDITOR.toString(), suspiciousCase.getCiLocationOffice());
            emailNotificationSenderService.sendEmailNotifications(NotificationType.AUDIT_PLAN_APPROVED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), seniorRecipient, getActiveUser().getUser());

        } else if (getActiveUser().getUser().getUserRole().getDescription().equals(UserRoleType.CRCS_OPS_MANAGER.toString())) {
            suspiciousCase.setStatus(CaseStatus.CRCS_APPROVED_REJECTED_AUDIT_PLAN);
            //email to CI Senior Auditor
            List<User> recipient = userService.findByUserRoleAndLocationOffice(UserRoleType.SENIOR_AUDITOR.toString(),suspiciousCase.getCiLocationOffice());
            emailNotificationSenderService.sendEmailNotifications(NotificationType.REJECT_AUDIT_REPORT_APPROVAL, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());
        }
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Audit plan was approved successfully.");
        reset().setList(true);
    }

    public void addAuditDetails(SuspiciousCase suspiciousCase) {
        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedBy(getActiveUser().getSid());
        auditDetails.setCreatedDate(new Date());
        auditDetails.setRender(true);
        suspiciousCase.getAuditPlan().addAuditDetails(auditDetails);
        addEntity(suspiciousCase);
    }

    public void removeAuditDetails(AuditDetails auditDetails) {
        getEntity().getAuditPlan().removeAuditDetails(auditDetails);
        if (auditDetails.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void addComment(SuspiciousCase suspiciousCase) {
        Comment comment = new Comment();
        comment.setCreatedBy(getActiveUser().getSid());
        comment.setCreatedDate(new Date());
        comment.setRender(true);
        //comment.setDescription("Testing");
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
}
