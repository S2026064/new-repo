package sars.pca.app.mb;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.AuditType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.Properties;

import sars.pca.app.common.UserRoleType;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.AuditDetails;
import sars.pca.app.domain.AuditPlan;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Letter;
import sars.pca.app.gen.dto.res.PDFDocumentGenerationManagementResponse;
import sars.pca.app.service.LetterGenerationServiceLocal;
import sars.pca.app.service.LetterServiceLocal;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseAuditPlanCreationBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private LetterServiceLocal letterService;
    @Autowired
    private LetterGenerationServiceLocal letterGenerationService;
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    private StreamedContent downloadLetter;
    private List<LocationOffice> ciOffices = new ArrayList<>();
    private User allocatedUser;
    private AttachmentType selectedAttachmentType;
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private List<AuditType> auditTypes = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        List<CaseStatus> sarCaseStatuses = new ArrayList<>();
        sarCaseStatuses.add(CaseStatus.CI_ALLOCATION);
        sarCaseStatuses.add(CaseStatus.CI_PLAN_REWORK);
        addCollections(suspiciousCaseService.findByStatusAndUserSid(sarCaseStatuses, getActiveUser().getSid()));
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        auditTypes.addAll(Arrays.asList(AuditType.values()));

    }

    public void createAuditPlan(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        if (suspiciousCase.getAuditPlan() != null) {
            suspiciousCase.getAuditPlan().setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.getAuditPlan().setUpdatedDate(new Date());
        } else {
            AuditPlan auditPlan = new AuditPlan();
            auditPlan.setCreatedBy(getActiveUser().getSid());
            auditPlan.setCreatedDate(new Date());
            suspiciousCase.setAuditPlan(auditPlan);
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            
        }
        addEntity(suspiciousCase);
    }

    public void save(SuspiciousCase suspiciousCase) {
        removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
        reset().setList(true);
    }

    public void submit(SuspiciousCase suspiciousCase) {
        if (checkAuditDetails(suspiciousCase)) {
            addErrorMessage("Number of risk areas don't match audit details");
            return;
        }
        suspiciousCase.setStatus(CaseStatus.CI_SENIOR_AUDITOR_AUDIT_PLAN_REVIEW);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Audit plan submitted successfully.");
        //email to senior auditor 
        List<User> recipient = userService.findByUserRoleAndLocationOffice(UserRoleType.SENIOR_AUDITOR.toString(), suspiciousCase.getCiLocationOffice());
        emailNotificationSenderService.sendEmailNotifications(NotificationType.AUDIT_PLAN_SUBMITTED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

        reset().setList(true);
    }

    private boolean checkAuditDetails(SuspiciousCase suspiciousCase) {
        return suspiciousCase.getSarCase().getRiskAssessment().getRiskAssessmentDetailses().size() != suspiciousCase.getAuditPlan().getAuditDetailses().size();
    }

    public void handleAddAuditDetailsReturnListener(SelectEvent event) {
        AuditDetails auditDetails = (AuditDetails) event.getObject();
        getEntity().getAuditPlan().addAuditDetails(auditDetails);
    }

    public void handleUpdateAuditDetailsReturnListener(SelectEvent event) {
        AuditDetails auditDetails = (AuditDetails) event.getObject();
        Integer index = getEntity().getAuditPlan().getAuditDetailses().indexOf(auditDetails);
        getEntity().getAuditPlan().getAuditDetailses().set(index, auditDetails);
    }

    public void removeAuditDetails(AuditDetails auditDetails) {
        getEntity().getAuditPlan().removeAuditDetails(auditDetails);
        if (auditDetails.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void addOrUpdateLetter(Letter letter) {
        suspiciousCaseService.update(getEntity());
        if (getEntity() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("letterKey", letter);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseKey", getEntity().getId());
            openDialogBox("letterpopup", null, 1400, 800);
        }

    }

    public void handleAddLetterReturnListener(SelectEvent event) {
        Letter returnedLetter = (Letter) event.getObject();
        getEntity().addLetter(returnedLetter);
    }

    public void handleUpdateLetterReturnListener(SelectEvent event) {
        Letter returnedLetter = (Letter) event.getObject();
        Integer index = getEntity().getLetters().indexOf(returnedLetter);
        getEntity().getLetters().set(index, returnedLetter);
    }

    public void removeLetter(Letter letter) {
        getEntity().removeLetter(letter);
        if (letter.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void downloadLetter(Letter letter) throws FileNotFoundException, IOException {
        String stringTooLong = IOUtils.toString(generateLetter(letter).getBytes(), "UTF-8");
        byte[] decoder = Base64.getDecoder().decode(stringTooLong);
        downloadLetter = DefaultStreamedContent.builder().name(letter.getLetterHead().toString() + "-" + getEntity().getIbrData().getRegistrationName() + ".pdf").contentType("application/pdf").stream(() -> new ByteArrayInputStream(decoder)).build();
    }

    public String generateLetter(Letter letter) throws IOException {
        PDFDocumentGenerationManagementResponse documentGenerationManagementResponse = letterGenerationService.generateLetterDocument(letter);
        String base64 = documentGenerationManagementResponse.getPDFDocuments().getPDFDocument().getContent();
        return base64;
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

    public List<AuditType> getAuditTypes() {
        return auditTypes;
    }

    public void setAuditTypes(List<AuditType> auditTypes) {
        this.auditTypes = auditTypes;
    }

    public StreamedContent getDownloadLetter() {
        return downloadLetter;
    }

    public void setDownloadLetter(StreamedContent downloadLetter) {
        this.downloadLetter = downloadLetter;
    }

}
