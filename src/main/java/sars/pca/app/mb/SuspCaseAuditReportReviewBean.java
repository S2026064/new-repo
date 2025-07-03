package sars.pca.app.mb;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.LOIExtentionReason;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.PaymentType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.gen.dto.res.PDFDocumentGenerationManagementResponse;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.LetterGenerationServiceLocal;
import sars.pca.app.service.SarCaseUserServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseAuditReportReviewBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    //notification services used
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private SarCaseUserServiceLocal sarCaseUserService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private LetterGenerationServiceLocal letterGenerationService;
    private UploadedFile originalFile;
    private AttachmentType selectedAttachmentType;
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private List<LOIExtentionReason> loiExtentionReasons = new ArrayList<>();
    private List<PaymentType> paymentTypes = new ArrayList<>();
    private StreamedContent downloadLetter;

    @PostConstruct
    public void init() {
        reset().setList(true);
        paymentTypes.addAll(Arrays.asList(PaymentType.values()));
        loiExtentionReasons.addAll(Arrays.asList(LOIExtentionReason.values()));

        List<CaseStatus> sarCaseStatuses = new ArrayList<>();
        if (UserRoleType.SENIOR_AUDITOR.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            sarCaseStatuses.add(CaseStatus.CI_SENIOR_AUDITOR_AUDIT_REPORTING_REVIEW);
        } else if (UserRoleType.CI_AUDITOR.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            sarCaseStatuses.add(CaseStatus.DM_CASE_REFERED_TO_CI_FOR_CLOSURE);
            sarCaseStatuses.add(CaseStatus.CLOSURE);
            sarCaseStatuses.add(CaseStatus.CI_AUDIT_REPORTING_REVIEW);
            sarCaseStatuses.add(CaseStatus.DM_CASE_REFERED_TO_CI_FOR_CLOSURE);
        } else if (UserRoleType.CI_OPS_MANAGER.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            sarCaseStatuses.add(CaseStatus.CI_AUDIT_REPORTING_EXECUTE);
        } else if (UserRoleType.GROUP_MANAGER.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            sarCaseStatuses.add(CaseStatus.AUDIT_REPORTING_ACCEPTED_SEND_FOR_APPROVAL);
        } else if (UserRoleType.SENIOR_MANAGER.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            sarCaseStatuses.add(CaseStatus.THIRD_TIER_APPROVAL);
        } else if (UserRoleType.EXECUTIVE.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            sarCaseStatuses.add(CaseStatus.FOURTH_TIER_APPROVAL);
        }
        addCollections(suspiciousCaseService.findByStatusAndCILocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
    }

    public void viewSuspCase(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);       
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
  
        addEntity(suspiciousCase);
    }

    public void accept(SuspiciousCase suspiciousCase) {
        if (UserRoleType.SENIOR_AUDITOR.toString().equals(getActiveUser().getUser().getUserRole().getDescription())) {
            suspiciousCase.setStatus(CaseStatus.CI_AUDIT_REPORTING_EXECUTE);
            //email to CI OPS manager 
            emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_REPORT_APPROVE, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), getActiveUser().getUser().getManager(), getActiveUser().getUser());

        } else {
            Double liabilitiesTotal = suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().getLiabilitiesTotal();
            if (liabilitiesTotal >= getActiveUser().getCiOpsManagerApprovalAmount()
                    && liabilitiesTotal <= getActiveUser().getCiGroupManagerApprovalAmount()) {

                suspiciousCase.setStatus(CaseStatus.AUDIT_REPORTING_ACCEPTED_SEND_FOR_APPROVAL);
                //email to Group Manager
                User recipient = userService.findByUserRoleDescription(UserRoleType.GROUP_MANAGER.toString());
                emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_REPORT_APPROVE, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

            } else if (liabilitiesTotal > getActiveUser().getCiGroupManagerApprovalAmount()
                    && liabilitiesTotal <= getActiveUser().getCiSeniorManagerApprovalAmount()) {

                suspiciousCase.setStatus(CaseStatus.THIRD_TIER_APPROVAL);
                //email to senior manager
                User recipient = userService.findByUserRoleDescription(UserRoleType.SENIOR_MANAGER.toString());
                emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_REPORT_APPROVE, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

            } else {
                suspiciousCase.setStatus(CaseStatus.FOURTH_TIER_APPROVAL);
                //email to executive
                User recipient = userService.findByUserRoleDescription(UserRoleType.EXECUTIVE.toString());
                emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_REPORT_APPROVE, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

            }
        }

        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Audit Report accepted successfully.");
        reset().setList(true);
    }

    public void rework(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setStatus(CaseStatus.AUDIT_REPORTING_RE_WORK);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Audit report send for rework successfully.");
            //email to CI Auditor
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CI_AUDITOR.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_REPORT_REWORK, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());

            reset().setList(true);
        } else {
            addErrorMessage("Enter reason for rework on this case.");
        }
    }

    public void save(SuspiciousCase suspiciousCase) {
        reset().setList(true);
        removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Audit Report was successfully saved.");
    }

    public void approve(SuspiciousCase suspiciousCase) {
        reset().setList(true);
        suspiciousCase.setStatus(CaseStatus.CI_AUDIT_REPORTING_REVIEW);
        for (Letter letter : suspiciousCase.getLetters()) {
            letter.setApprove(YesNoEnum.YES);
        }
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Audit Report approved successfully.");

        //email to CI Auditor
        SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CI_AUDITOR.toString(), suspiciousCase);
        emailNotificationSenderService.sendEmailNotification(NotificationType.AUDIT_REPORT_APPROVED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());
        //email to CI Senior Auditor
        List<User> seniorrecipient = userService.findByUserRoleAndLocationOffice(UserRoleType.SENIOR_AUDITOR.toString(), suspiciousCase.getCiLocationOffice());
        emailNotificationSenderService.sendEmailNotifications(NotificationType.AUDIT_REPORT_APPROVED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), seniorrecipient, getActiveUser().getUser());

    }

    public void close(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().getLiabilitiesTotal() == 0 || suspiciousCase.getStatus().equals(CaseStatus.DM_CASE_REFERED_TO_CI_FOR_CLOSURE)) {
            suspiciousCase.setStatus(CaseStatus.CLOSURE);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Audit report closed successfully.");
            reset().setList(true);
        } else {
            addErrorMessage("Case with total liability which is greater than zero cannot be closed.");
        }
    }

    public void finalise(SuspiciousCase suspiciousCase) {
        getErrorCollectionMsg().clear();
        List<AttachmentType> localAttachmentTypes;
        localAttachmentTypes = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        if (!suspiciousCase.getAuditPlan().getAuditReport().getPaymentType().equals(PaymentType.NO_PAYMENT)) {
            if (suspiciousCase.getAuditPlan().getAuditReport().isAuditorProofOfPayment() && hasActiveUserAttachedDocument(suspiciousCase)) {
                for (Attachment attachment : suspiciousCase.getAttachments()) {
                    localAttachmentTypes.add(attachment.getAttachmentType());
                }
                if (Collections.frequency(localAttachmentTypes, AttachmentType.POP) == 0) {
                    addError("Proof of payment must be attached before the case could finalised.");
                }
            } else {
                addError("Attached the proof of payment before finalising a case.");
            }
        }
        //THIS SHOULD BE REVIEWED TOO.
        if (!suspiciousCase.getAuditPlan().getAuditReport().isLoiIssued()) {
            addError("Confirm the LOI issued date by clicking checkbox");
        }
        if (suspiciousCase.getAuditPlan().getAuditReport().getLodDate().after(new Date())) {
            addError("LOD due date must not be after the current date.");
        }
        if (suspiciousCase.getAuditPlan().getAuditReport().isExtendDueDate() && suspiciousCase.getAuditPlan().getAuditReport().getDueDateExtend() == null) {
            addError("Select the extension due date.");
        }
        if (DateUtils.addDays(suspiciousCase.getAuditPlan().getAuditReport().getDueDateExtend(), 5).compareTo(new Date()) < 0) {
            addError("The extension due date must not be over 5 days.");
        }
        if (suspiciousCase.getAuditPlan().getAuditReport().getDueDateExtend().before(new Date()) || simpleDateFormat.format(suspiciousCase.getAuditPlan().getAuditReport().getDueDateExtend()).equals(simpleDateFormat.format(new Date()))) {
            addError("The extension due date cannot be current or previous date.");
        }
        if (suspiciousCase.getAuditPlan().getAuditReport().getLoiDate().after(new Date())) {
            addError("LOI due date must not be after the current date.");
        }
        if (this.getErrorCollectionMsg().isEmpty()) {
            suspiciousCase.setStatus(CaseStatus.DEBT_MANAGEMENT_POOL);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Audit report send for finalisation successfully.");
            //email to DEBT MANAGEMENT
            List<User> recipient = userService.findUserByUserRoleDescription(UserRoleType.MANAGEMENT.toString());
            emailNotificationSenderService.sendEmailNotifications(NotificationType.DEBT_MANAGEMENT, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());
            if (suspiciousCase.getAuditPlan().getAuditReport().isExtendDueDate()) {
                SarCaseUser recipientAuditor = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CI_AUDITOR.toString(), suspiciousCase);
                emailNotificationSenderService.sendEmailNotification(NotificationType.EXTEND_LOI, suspiciousCase.getCaseRefNumber(), suspiciousCase.getAuditPlan().getAuditReport().getLoiDate(), recipientAuditor.getUser(), getActiveUser().getUser());
            }
            reset().setList(true);
        } else {
            Iterator<String> iterator = getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
        }
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
        reset().setList(true);
    }

    public void onPaymentListener() {
        switch (getEntity().getAuditPlan().getAuditReport().getPaymentType()) {
            case FULL_PAYMENT:
                getEntity().getAuditPlan().getAuditReport().setPayedAmount(getEntity().getAuditPlan().getAuditReport().getTotalLiability().getLiabilitiesTotal());
                getEntity().getAuditPlan().getAuditReport().setOutstandingAmount(0.00);
                break;
            case PARTIAL_PAYMENT:
                Double oustandingAmount = getEntity().getAuditPlan().getAuditReport().getTotalLiability().getLiabilitiesTotal() - getEntity().getAuditPlan().getAuditReport().getPayedAmount();
                getEntity().getAuditPlan().getAuditReport().setOutstandingAmount(oustandingAmount);
                break;
            case NO_PAYMENT:
                getEntity().getAuditPlan().getAuditReport().setPayedAmount(0.00);
                getEntity().getAuditPlan().getAuditReport().setOutstandingAmount(getEntity().getAuditPlan().getAuditReport().getTotalLiability().getLiabilitiesTotal());
                break;
            default:
                break;
        }
    }

    public void onKeyUpListener() {
        Double oustandingAmount = getEntity().getAuditPlan().getAuditReport().getTotalLiability().getLiabilitiesTotal() - getEntity().getAuditPlan().getAuditReport().getPayedAmount();
        getEntity().getAuditPlan().getAuditReport().setOutstandingAmount(oustandingAmount);
    }

    public void onExtensionReasonListener() {
        if (getEntity().getAuditPlan().getAuditReport().getLoiExtentionReason().equals(LOIExtentionReason.OTHER)) {

        }
    }

    public void addOrUpdateLetter(Letter letter, String targetPage, Long id) {
        id = getEntity().getId();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("letterKey", letter);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseKey", id);
        openDialogBox(targetPage, null, 1300, 780);
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

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public UploadedFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(UploadedFile originalFile) {
        this.originalFile = originalFile;
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

    public List<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public List<LOIExtentionReason> getLoiExtentionReasons() {
        return loiExtentionReasons;
    }

    public void setLoiExtentionReasons(List<LOIExtentionReason> loiExtentionReasons) {
        this.loiExtentionReasons = loiExtentionReasons;
    }

    public StreamedContent getDownloadLetter() {
        return downloadLetter;
    }

    public void setDownloadLetter(StreamedContent downloadLetter) {
        this.downloadLetter = downloadLetter;
    }

}
