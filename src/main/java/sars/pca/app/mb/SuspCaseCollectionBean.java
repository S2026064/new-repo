package sars.pca.app.mb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.ClientType;
import sars.pca.app.common.CustomExcise;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.Province;
import sars.pca.app.common.TimeFrame;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.common.YearlyLoss;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.DebtManagement;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.SarCaseUserServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseCollectionBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    
    
    //notification services used
    @Autowired
    private SarCaseUserServiceLocal sarCaseUserService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<ClientType> clientTypes = new ArrayList<>();
    private List<CustomExcise> customsExcises = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();
    private List<String> listCountries = new ArrayList<>();
    private List<TimeFrame> timeFrames = new ArrayList<>();
    private List<YearlyLoss> yearlyLosses = new ArrayList<>();
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    List<LocationOffice> locationOffices = new ArrayList<>();

    private AttachmentType selectedAttachmentType;
    private UploadedFile originalFile;
    private StreamedContent downloadFile;
    private SarCase selectedSarCase;
    private LocationOffice selectedLocationOffice;

    @PostConstruct
    public void init() {
        reset().setList(true);
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        //Find all cases linked to the active user.
        List<CaseStatus> caseStatuses = new ArrayList<>();
        caseStatuses.add(CaseStatus.DM_SENT_FOR_DEBT_MANAGEMENT);
        addCollections(suspiciousCaseService.findByStatusAndUserSid(caseStatuses, getActiveUser().getUser().getSid()));

    }

    public void viewSuspCase(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        DebtManagement debtManagement = new DebtManagement();
        debtManagement.setCreatedBy(getActiveUser().getSid());
        debtManagement.setCreatedDate(new Date());
        suspiciousCase.getAuditPlan().getAuditReport().setDebtManagement(debtManagement);
        addEntity(suspiciousCase);
    }

    public void submit(SuspiciousCase suspiciousCase) {
        getErrorCollectionMsg().clear();
        List<AttachmentType> localAttachmentTypes;
        localAttachmentTypes = new ArrayList<>();

        if (suspiciousCase.getAuditPlan().getAuditReport().getDebtManagement().isDebtProofOfPayment() && hasActiveUserAttachedDocument(suspiciousCase)) {
            for (Attachment attachment : suspiciousCase.getAttachments()) {
                localAttachmentTypes.add(attachment.getAttachmentType());
            }
            if (Collections.frequency(localAttachmentTypes, AttachmentType.POP) == 0) {
                addError("Proof of payment must be attached before the case could finalised.");
            }
        } else {
            addError("Attached the proof of payment before finalising a case.");
        }

        //THIS SHOULD BE REVIEWED TOO.
        if (getEntity().getAuditPlan().getAuditReport().getOutstandingAmount() - getEntity().getAuditPlan().getAuditReport().getDebtManagement().getCollectedAmount() != 0.00) {
            addError("Collected amount is not equal to debt owed");
        }

        if (this.getErrorCollectionMsg().isEmpty()) {
            suspiciousCase.setStatus(CaseStatus.DM_CASE_REFERED_TO_CI_FOR_CLOSURE);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            //email to ci Auditor
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CI_AUDITOR.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotificationCollection(NotificationType.DEBT_MANAGEMENT_COLLECTION, suspiciousCase.getCaseRefNumber(), suspiciousCase.getAuditPlan().getAuditReport().getOutstandingAmount(), recipient.getUser(), getActiveUser().getUser());

            reset().setList(true);
        } else {
            Iterator<String> iterator = getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
        }
    }

    //Get next sar case in the pool
    public void nextSarCase() {
        //Pull the case from the first pool stagin and change status and update to ensure case does not get picked by the next user.
        SuspiciousCase suspiciousCase = suspiciousCaseService.findTopByStatusOrderByCaseRefNumberDesc(CaseStatus.DEBT_MANAGEMENT_POOL);
        if (suspiciousCase != null) {
            suspiciousCase.setStatus(CaseStatus.DM_SENT_FOR_DEBT_MANAGEMENT);
            SarCaseUser sarCaseUser = new SarCaseUser();
            sarCaseUser.setCreatedBy(getActiveUser().getSid());
            sarCaseUser.setCreatedDate(new Date());
            sarCaseUser.setUser(getActiveUser().getUser());
            suspiciousCase.addSarCaseUser(sarCaseUser);
            SuspiciousCase persistentSarCase = suspiciousCaseService.update(suspiciousCase);
            addCollection(persistentSarCase);
        } else {
            addInformationMessage("No cases available in the pool.");
        }
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

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
        reset().setList(true);
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            try {
                //Setting the file upload bytes to uploaded to documentum on submit
                setFileUploadBytes(IOUtils.toByteArray(file.getInputStream()));
                addAttachment(file.getFileName(), file.getSize());
            } catch (IOException ex) {
                Logger.getLogger(SuspCaseCollectionBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public void back() {
        reset().setAdd(true);
    }

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public List<ClientType> getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(List<ClientType> clientTypes) {
        this.clientTypes = clientTypes;
    }

    public List<CustomExcise> getCustomsExcises() {
        return customsExcises;
    }

    public void setCustomsExcises(List<CustomExcise> customsExcises) {
        this.customsExcises = customsExcises;
    }

    public List<YesNoEnum> getYesNoOptions() {
        return yesNoOptions;
    }

    public void setYesNoOptions(List<YesNoEnum> yesNoOptions) {
        this.yesNoOptions = yesNoOptions;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<TimeFrame> getTimeFrames() {
        return timeFrames;
    }

    public void setTimeFrames(List<TimeFrame> timeFrames) {
        this.timeFrames = timeFrames;
    }

    public List<YearlyLoss> getYearlyLosses() {
        return yearlyLosses;
    }

    public void setYearlyLosses(List<YearlyLoss> yearlyLosses) {
        this.yearlyLosses = yearlyLosses;
    }

    public UploadedFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(UploadedFile originalFile) {
        this.originalFile = originalFile;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public List<String> getListCountries() {
        return listCountries;
    }

    public void setListCountries(List<String> listCountries) {
        this.listCountries = listCountries;
    }

    public List<AttachmentType> getAttachmentTypes() {
        return attachmentTypes;
    }

    public void setAttachmentTypes(List<AttachmentType> attachmentTypes) {
        this.attachmentTypes = attachmentTypes;
    }

    public AttachmentType getSelectedAttachmentType() {
        return selectedAttachmentType;
    }

    public void setSelectedAttachmentType(AttachmentType selectedAttachmentType) {
        this.selectedAttachmentType = selectedAttachmentType;
    }

    public SarCase getSelectedSarCase() {
        return selectedSarCase;
    }

    public void setSelectedSarCase(SarCase selectedSarCase) {
        this.selectedSarCase = selectedSarCase;
    }

    public List<LocationOffice> getLocationOffices() {
        return locationOffices;
    }

    public void setLocationOffices(List<LocationOffice> locationOffices) {
        this.locationOffices = locationOffices;
    }

    public LocationOffice getSelectedLocationOffice() {
        return selectedLocationOffice;
    }

    public void setSelectedLocationOffice(LocationOffice selectedLocationOffice) {
        this.selectedLocationOffice = selectedLocationOffice;
    }

}
