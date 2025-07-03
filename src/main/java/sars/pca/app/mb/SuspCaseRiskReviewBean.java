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
import sars.pca.app.common.MainIndustry;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.domain.RiskRatingConsequence;
import sars.pca.app.domain.RiskRatingLikelihood;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.HsChapterRiskServiceLocal;
import sars.pca.app.service.RiskRatingConsequenceServiceLocal;
import sars.pca.app.service.RiskRatingLikelihoodServiceLocal;
import sars.pca.app.service.SarCaseUserServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseRiskReviewBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private HsChapterRiskServiceLocal hsChapterRiskService;
    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private RiskRatingLikelihoodServiceLocal riskRatingLikelihoodService;
    @Autowired
    private RiskRatingConsequenceServiceLocal riskRatingConsequenceService;

    //notification services used
    @Autowired
    private SarCaseUserServiceLocal sarCaseUserService;
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<MainIndustry> mainIndustries = new ArrayList<>();
    private List<RiskArea> riskAreas = new ArrayList<>();
    private List<HsChapterRisk> hsChapterRisks = new ArrayList<>();
    private List<RiskRatingLikelihood> riskRatingLikelihoods = new ArrayList<>();
    private List<RiskRatingConsequence> riskRatingConsequences = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();

    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private AttachmentType selectedAttachmentType;

    @PostConstruct
    public void init() {
        reset().setList(true);
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        mainIndustries.addAll(Arrays.asList(MainIndustry.values()));
        hsChapterRisks.addAll(hsChapterRiskService.listAll());
        riskAreas.addAll(Arrays.asList(RiskArea.values()));
        riskRatingLikelihoods.addAll(riskRatingLikelihoodService.listAll());
        riskRatingConsequences.addAll(riskRatingConsequenceService.listAll());

        List<CaseStatus> caseStatuses = new ArrayList<>();
        if (getActiveUser().getUserRole().getDescription().equalsIgnoreCase(UserRoleType.CRCS_OPS_MANAGER.toString())) {
            caseStatuses.clear();
            caseStatuses.add(CaseStatus.ACTIVE_RA_TECHNICAL_REVIEW_REJECTED);
            caseStatuses.add(CaseStatus.ACTIVE_RA_REJECTED);
            caseStatuses.add(CaseStatus.ACTIVE_CRCS_RA);
            caseStatuses.add(CaseStatus.TECHNICAL_REVIEW_APPROVED);
            addCollections(suspiciousCaseService.findByStatusAndCrcsLocationOffice(caseStatuses, getActiveUser().getUser().getLocationOffice())); // Rerieve by office and status
        } else if (getActiveUser().getUserRole().getDescription().equalsIgnoreCase(UserRoleType.CRCS_QUALITY_ASSURER.toString())) {
            caseStatuses.clear();
            caseStatuses.add(CaseStatus.ACTIVE_RA_QA);
            addCollections(suspiciousCaseService.findByStatusAndCrcsLocationOffice(caseStatuses, getActiveUser().getUser().getLocationOffice()));
        }
    }

    public void addOrUpdate(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        addEntity(suspiciousCase);
    }

    public void save(SuspiciousCase suspiciousCase) {
        removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Risk Asssement was successfully created.");
        reset().setList(true);
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
        reset().setList(true);
    }

    //Get next sar case in the pool
    public void nextSarCase() {
        //Pull the case from the first pool stagin and change status and update to ensure case does not get picked by the next user. The QA pulls from Third POOL staging
        SuspiciousCase suspiciousCase = suspiciousCaseService.findTopByStatusOrderByCaseRefNumberDesc(CaseStatus.TECHNICAL_REVIEW_POOL);
        if (suspiciousCase != null) {
            suspiciousCase.setStatus(CaseStatus.ACTIVE_RA_QA);
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

    public void approve(SuspiciousCase suspiciousCase) {
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        if (getActiveUser().getUserRole().getDescription().equals(UserRoleType.CRCS_QUALITY_ASSURER.toString())) {
            suspiciousCase.setStatus(CaseStatus.TECHNICAL_REVIEW_APPROVED);
        }

        if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_CRCS_RA)) {
            if (checkForRiskAreaLevel(suspiciousCase.getSarCase().getRiskAssessment()).size() == 1) {
                suspiciousCase.setStatus(CaseStatus.LIMITED_SCOPE_POOL);
            } else if (checkForRiskAreaLevel(suspiciousCase.getSarCase().getRiskAssessment()).size() > 1) {
                suspiciousCase.setStatus(CaseStatus.FULL_SCOPE_POOL);
            } else if (suspiciousCase.getSarCase().getRiskAssessment().getOtherRisk().equals(YesNoEnum.YES)) {
                suspiciousCase.setStatus(CaseStatus.INTEGRATED_SCOPE_POOL);
            }
//                  email to CI OPS manager in the same region office wich the case was allocated to
            User recipient = userService.findUserByUserRoleAndLocationOffice(UserRoleType.CI_OPS_MANAGER.toString(), suspiciousCase.getCiLocationOffice());
            emailNotificationSenderService.sendEmailNotification(NotificationType.APPROVE_RA, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());

        } else if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_RA_TECHNICAL_REVIEW_REJECTED)) {
            suspiciousCase.setStatus(CaseStatus.ACTIVE_RA_REJECTED);
        }

        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Risk Asssement was successfully approved.");
        if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_RA_REJECTED)) {
            //email to QA
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CRCS_QUALITY_ASSURER.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotification(NotificationType.APPROVED_REJECT_TECHNICAL_REVIEW, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());
        } else if (suspiciousCase.getStatus().equals(CaseStatus.TECHNICAL_REVIEW_APPROVED)) {
            emailNotificationSenderService.sendEmailNotification(NotificationType.TECHNICAL_APPROVED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), getActiveUser().getUser().getManager(), getActiveUser().getUser());
        }

        reset().setList(true);
    }

    private List<YesNoEnum> checkForRiskAreaLevel(RiskAssessment riskAssessment) {
        List<YesNoEnum> yesNoEnums = new ArrayList<>();
        if (riskAssessment.getContraventionHistoryOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getDutyExemptionOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getDutySuspensionOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getLicensingAndRegistrationOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getModeOfTransportOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getOriginCountryOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getOtherPAndROption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getRevenueProtectionOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getRulesOfOriginOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getSecurityOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getTarrifOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getTradeRestrictionsOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getValuationOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        if (riskAssessment.getWarehousingOption().equals(YesNoEnum.YES)) {
            yesNoEnums.add(YesNoEnum.YES);
        }
        return yesNoEnums;
    }

    public void reject(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_CRCS_RA)) {
                suspiciousCase.setStatus(CaseStatus.ACTIVE_RA_REJECTED);
            } else if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_RA_QA)) {
                suspiciousCase.setStatus(CaseStatus.ACTIVE_RA_TECHNICAL_REVIEW_REJECTED);
            }
            removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Risk Asssement was successfully rejected.");
            if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_RA_REJECTED)) {
                //email to CRCS OPS specialist
                SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CRCS_OPS_SPECIALIST.toString(), suspiciousCase);
                emailNotificationSenderService.sendEmailNotification(NotificationType.REJECTED_RA, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());
            } else if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_RA_TECHNICAL_REVIEW_REJECTED)) {
                //email to CRCS OPS manager
                emailNotificationSenderService.sendEmailNotification(NotificationType.REJECTED_RA, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), getActiveUser().getUser().getManager(), getActiveUser().getUser());
            }
            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you reject the case.");
        }
    }

    public void disApprove(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            suspiciousCase.setStatus(CaseStatus.TECHNICAL_REVIEW_POOL);
            removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Risk Asssement was successfully disapproved.");
            //email to QA
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CRCS_QUALITY_ASSURER.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotification(NotificationType.REJECTED_REJECT_TECHNICAL_REVIEW, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());

            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you disaprove the case.");
        }
    }

    public void rework(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            suspiciousCase.setStatus(CaseStatus.ACTIVE_RA_SEND_BACK_FOR_REWORK);
            removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Risk Asssement was successfully send for rework.");

            //email to CRCS OPS specialist rework
            SarCaseUser recipient = sarCaseUserService.findUserByUserRoleAndSuspCase(UserRoleType.CRCS_OPS_SPECIALIST.toString(), suspiciousCase);
            emailNotificationSenderService.sendEmailNotification(NotificationType.REWORK_RA, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient.getUser(), getActiveUser().getUser());

            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you send the case for rework.");
        }
    }

    public void allocate(SuspiciousCase suspiciousCase) {
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        suspiciousCase.setStatus(CaseStatus.TECHNICAL_REVIEW_POOL);
        SarCaseUser sarCaseUser = new SarCaseUser();
        sarCaseUser.setCreatedBy(getActiveUser().getSid());
        sarCaseUser.setCreatedDate(new Date());
        sarCaseUser.setUser(getActiveUser().getUser());
        suspiciousCase.addSarCaseUser(sarCaseUser);
        removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Risk Asssement was successfully allocated.");

        //email to QA for technical review
        List<User> recipient = userService.findUserByUserRoleDescription(UserRoleType.CRCS_QUALITY_ASSURER.toString());
        emailNotificationSenderService.sendEmailNotifications(NotificationType.QA_TECHNICAL_REVIEW, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());
        reset().setList(true);
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

    public void addOrUpdateLetter(Letter letter, String targetPage, Long id) {
        id = getEntity().getId();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("letterKey", letter);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseKey", id);
        openDialogBox(targetPage, null, 1300, 780);
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

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public List<MainIndustry> getMainIndustries() {
        return mainIndustries;
    }

    public void setMainIndustries(List<MainIndustry> mainIndustries) {
        this.mainIndustries = mainIndustries;
    }

    public List<RiskArea> getRiskAreas() {
        return riskAreas;
    }

    public void setRiskAreas(List<RiskArea> riskAreas) {
        this.riskAreas = riskAreas;
    }

    public List<HsChapterRisk> getHsChapterRisks() {
        return hsChapterRisks;
    }

    public void setHsChapterRisks(List<HsChapterRisk> hsChapterRisks) {
        this.hsChapterRisks = hsChapterRisks;
    }

    public List<RiskRatingLikelihood> getRiskRatingLikelihoods() {
        return riskRatingLikelihoods;
    }

    public void setRiskRatingLikelihoods(List<RiskRatingLikelihood> riskRatingLikelihoods) {
        this.riskRatingLikelihoods = riskRatingLikelihoods;
    }

    public List<RiskRatingConsequence> getRiskRatingConsequences() {
        return riskRatingConsequences;
    }

    public void setRiskRatingConsequences(List<RiskRatingConsequence> riskRatingConsequences) {
        this.riskRatingConsequences = riskRatingConsequences;
    }

    public List<YesNoEnum> getYesNoOptions() {
        return yesNoOptions;
    }

    public void setYesNoOptions(List<YesNoEnum> yesNoOptions) {
        this.yesNoOptions = yesNoOptions;
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
}
