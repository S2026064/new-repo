package sars.pca.app.mb;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.CountryUtility;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.MainIndustry;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.Province;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.SummationUtility;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.AdditionalRisk;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Duty;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.RevenueLiability;
import sars.pca.app.domain.RevenueLiabilityDuty;
import sars.pca.app.domain.RiskRatingConsequence;
import sars.pca.app.domain.RiskRatingLikelihood;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.TotalLiability;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.gen.dto.res.PDFDocumentGenerationManagementResponse;
import sars.pca.app.service.DutyServiceLocal;
import sars.pca.app.service.LetterGenerationServiceLocal;
import sars.pca.app.service.OffenceAndPenaltyServiceLocal;
import sars.pca.app.service.RevenueLiabilityDutyServiceLocal;
import sars.pca.app.service.RiskRatingConsequenceServiceLocal;
import sars.pca.app.service.RiskRatingLikelihoodServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseAuditReportCreationBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private LetterGenerationServiceLocal letterGenerationService;
    @Autowired
    private RevenueLiabilityDutyServiceLocal revenueLiabilityDutyService;

    @Autowired
    private DutyServiceLocal dutyService;
    @Autowired
    private OffenceAndPenaltyServiceLocal offenceAndPenaltyService;
    @Autowired
    private RiskRatingLikelihoodServiceLocal riskRatingLikelihoodService;
    @Autowired
    private RiskRatingConsequenceServiceLocal riskRatingConsequenceService;

    //notification services used
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private UploadedFile originalFile;
    private StreamedContent downloadLetter;
    private AttachmentType selectedAttachmentType;
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();
    private List<MainIndustry> mainIndustries = new ArrayList<>();
    private List<RiskArea> riskAreas = new ArrayList<>();
    private List<RiskRatingLikelihood> riskRatingLikelihoods = new ArrayList<>();
    private List<RiskRatingConsequence> riskRatingConsequences = new ArrayList<>();
    private List<String> countries = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();
    private AuditReportOffencePenalty auditReportOffencePenalty;

    @PostConstruct
    public void init() {
        reset().setList(true);
        List<CaseStatus> sarCaseStatuses = new ArrayList<>();
        sarCaseStatuses.add(CaseStatus.CI_PLAN_REVIEW);
        sarCaseStatuses.add(CaseStatus.AUDIT_REPORTING_RE_WORK);
        addCollections(suspiciousCaseService.findByStatusAndCILocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        mainIndustries.addAll(Arrays.asList(MainIndustry.values()));
        riskAreas.addAll(Arrays.asList(RiskArea.values()));
        riskRatingLikelihoods.addAll(riskRatingLikelihoodService.listAll());
        riskRatingConsequences.addAll(riskRatingConsequenceService.listAll());
        countries.addAll(CountryUtility.getDisplayCountryNames());
        provinces.addAll(Arrays.asList(Province.values()));

    }

    public void createAuditReport(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        if (suspiciousCase.getAuditPlan().getAuditReport() != null) {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
        } else {
            AuditReport auditReport = new AuditReport();
            auditReport.setCreatedBy(getActiveUser().getSid());
            auditReport.setCreatedDate(new Date());

            AdditionalRisk additionalTask = new AdditionalRisk();
            additionalTask.setCreatedBy(getActiveUser().getSid());
            additionalTask.setCreatedDate(new Date());
            auditReport.setAdditionalRisk(additionalTask);

            TotalLiability totalLiability = new TotalLiability();
            totalLiability.setCreatedBy(getActiveUser().getSid());
            totalLiability.setCreatedDate(new Date());
            auditReport.setTotalLiability(totalLiability);

            RevenueLiability revenueLiability = new RevenueLiability();
            revenueLiability.setCreatedBy(getActiveUser().getSid());
            revenueLiability.setCreatedDate(new Date());
            auditReport.setRevenueLiability(revenueLiability);
            suspiciousCase.getAuditPlan().setAuditReport(auditReport);
            //Create RevenueLiabilityDuty from the list of duties retrieved from the database
            for (Duty duty : dutyService.listAll()) {
                RevenueLiabilityDuty revenueLiabilityDuty = new RevenueLiabilityDuty();
                revenueLiabilityDuty.setCreatedBy(getActiveUser().getSid());
                revenueLiabilityDuty.setCreatedDate(new Date());
                revenueLiabilityDuty.setDuty(duty);
                revenueLiabilityDuty.setRender(false);
                suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(revenueLiabilityDuty);
            }
        }
        addEntity(suspiciousCase);
    }

    public void save(SuspiciousCase suspiciousCase) {
        try {
            processPenaltiesForDuplications(suspiciousCase);
            if (getErrorCollectionMsg().isEmpty()) {
                removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
                reset().setList(true);
            }
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void submit(SuspiciousCase suspiciousCase) {
        try {
            processPenaltiesForDuplications(suspiciousCase);
            if (getErrorCollectionMsg().isEmpty()) {
                suspiciousCase.setStatus(CaseStatus.CI_SENIOR_AUDITOR_AUDIT_REPORTING_REVIEW);
                removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
                addInformationMessage("Audit Report submitted successfully.");

                //email to CI senior Auditor
                List<User> seniorrecipient = userService.findByUserRoleAndLocationOffice(UserRoleType.SENIOR_AUDITOR.toString(), suspiciousCase.getCiLocationOffice());
                emailNotificationSenderService.sendEmailNotifications(NotificationType.AUDIT_PLAN_SUBMITTED, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), seniorrecipient, getActiveUser().getUser());

                reset().setList(true);
            }
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void processPenaltiesForDuplications(SuspiciousCase suspiciousCase) {
        getErrorCollectionMsg().clear();
        List<AuditReportOffencePenalty> auditReportOffencePenalties = suspiciousCase.getAuditPlan().getAuditReport().getAuditReportOffencePenalties();
        List<OffenceDescription> offenceDescripts = new ArrayList<>();

        for (AuditReportOffencePenalty arop : auditReportOffencePenalties) {
            Integer index = auditReportOffencePenalties.indexOf(arop);
            OffenceAndPenalty offenceAndPenalty = arop.getOffencePenalty();
            offenceDescripts.add(offenceAndPenalty.getOffenceDescription());

            OffenceAndPenalty persistentOffencePenalty = offenceAndPenaltyService.findByOffenceDescription(offenceAndPenalty.getOffenceDescription());
            if (persistentOffencePenalty != null) {
                arop.setOffencePenalty(persistentOffencePenalty);
            }
            if (Collections.frequency(offenceDescripts, offenceAndPenalty.getOffenceDescription()) > 1) {
                addError(offenceAndPenalty.getOffenceDescription().getDescription(), "has already been created as an offence or penalty in the database.");
            }
            suspiciousCase.getAuditPlan().getAuditReport().getAuditReportOffencePenalties().set(index, arop);
        }
        //Perform calculations
        revenueLiabilityCalculations(suspiciousCase);
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        reset().setList(true);
    }

    public void addOffenceAndPenalty(AuditReportOffencePenalty reportOffencePenalty) {
        getErrorCollectionMsg().clear();
        if (reportOffencePenalty.getOffencePenalty().getOffenceClassification() == null) {
            addError("Select Offence Classification");
        }
        if (reportOffencePenalty.getOffencePenalty().getSectionRule() == null) {
            addError("Select Section Rule");
        }
        if (reportOffencePenalty.getOffencePenalty().getOffenceDescription() == null) {
            addError("Select Offence Description");
        }
        if (getErrorCollectionMsg().isEmpty()) {
            if (getEntity().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().contains(reportOffencePenalty)) {
                Integer index = getEntity().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().indexOf(reportOffencePenalty);
                getEntity().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().set(index, reportOffencePenalty);
            } else {
                getEntity().getAuditPlan().getAuditReport().addOffenceAndPenalty(reportOffencePenalty.getOffencePenalty());
            }
            auditReportOffencePenalty = new AuditReportOffencePenalty();
            auditReportOffencePenalty.setCreatedBy(getActiveUser().getSid());
            auditReportOffencePenalty.setCreatedDate(new Date());
            OffenceAndPenalty penalty = new OffenceAndPenalty();
            penalty.setCreatedBy(getActiveUser().getSid());
            penalty.setCreatedDate(new Date());
            auditReportOffencePenalty.setOffencePenalty(penalty);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
        }
    }

    public void removeOffenceAndPenalty(AuditReportOffencePenalty auditReportOffencePenalty) {
        getEntity().getAuditPlan().getAuditReport().removeAuditReportOffencePenalty(auditReportOffencePenalty);
        if (auditReportOffencePenalty.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
        getEntity().getAuditPlan().getAuditReport().setSubPenaltyTotal(0.00);
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
    }

    public void saveComment(Comment comment) {
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

    public void onAdditionalRiskIdentifiedListener(AjaxBehaviorEvent event) {
        if (getEntity().getAuditPlan().getAuditReport().getAdditionalRiskIdentified().equals(YesNoEnum.NO)) {
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setContraventionHistory(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setContraventionHistoryDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setDutyExemption(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setDutyExemptionDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setDutySuspension(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setDutySuspensionDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setLicensingAndRegistration(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setLicensingAndRegistrationDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setModeOfTransport(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setModeOfTransportDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setOriginCountry(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setOriginCountryDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setOtherPAndR(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setOtherPAndRDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setRevenueProtection(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setRevenueProtectionDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setRulesOfOrigin(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setRulesOfOriginDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setSecurity(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setSecurityDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setTarrif(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setTarrifDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setTradeRestrictions(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setTradeRestrictionDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setValuation(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setValuationDescription("");

            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setWarehousing(YesNoEnum.NO);
            getEntity().getAuditPlan().getAuditReport().getAdditionalRisk().setWarehousingDescription("");
        }
    }

    public void onDutySelectListener(AjaxBehaviorEvent event) {
        Integer index = (Integer) ((UIInput) event.getSource()).getAttributes().get("index");
        UIComponent component = event.getComponent();
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            Boolean selectedCheckedValue = (Boolean) inputComponent.getValue();
            if (selectedCheckedValue) {
                getEntity().getAuditPlan().getAuditReport().getRevenueLiability().getRevenueLiabilityDutys().get(index).setCheckedItem(true);
            } else {
                RevenueLiabilityDuty revenueLiabilityDuty = getEntity().getAuditPlan().getAuditReport().getRevenueLiability().getRevenueLiabilityDutys().get(index);
                revenueLiabilityDuty.setAmount(0.00);
                revenueLiabilityDuty.setCheckedItem(false);
            }
        }
    }

    public void addOrUpdateOffencePenalty(AuditReportOffencePenalty auditReportOffencePenalty, String targetPage) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rop", auditReportOffencePenalty);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseType", getEntity().getCaseType());
        openDialogBox(targetPage, null, 1300, 580);
    }

    public void handleAddReturnListener(SelectEvent event) {
        AuditReportOffencePenalty reportOffencePenalty = (AuditReportOffencePenalty) event.getObject();
        getEntity().getAuditPlan().getAuditReport().addOffenceAndPenalty(reportOffencePenalty.getOffencePenalty());
    }

    public void handleUpdateReturnListener(SelectEvent event) {
        AuditReportOffencePenalty reportOffencePenalty = (AuditReportOffencePenalty) event.getObject();
        Integer index = getEntity().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().indexOf(reportOffencePenalty);
        getEntity().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().set(index, reportOffencePenalty);
    }

    public void onThereRevenueLiabilityListener(AjaxBehaviorEvent event) {
        if (getEntity().getAuditPlan().getAuditReport().getThereRevenueLiability().equals(YesNoEnum.NO)) {
            getEntity().getAuditPlan().getAuditReport().getRevenueLiability().setCustomsInterest(0);
            getEntity().getAuditPlan().getAuditReport().getRevenueLiability().setForfieture(0);
            getEntity().getAuditPlan().getAuditReport().getRevenueLiability().setTotalSubLiability(0);
            getEntity().getAuditPlan().getAuditReport().getRevenueLiability().setVat(0);
            getEntity().getAuditPlan().getAuditReport().getRevenueLiability().setVatInterest(0);
            getEntity().getAuditPlan().getAuditReport().getRevenueLiability().setVatPenalty(0);
            for (RevenueLiabilityDuty revenueLiabilityDuty : getEntity().getAuditPlan().getAuditReport().getRevenueLiability().getRevenueLiabilityDutys()) {
                revenueLiabilityDuty.setAmount(0.00);
            }
        }
    }

    private void revenueLiabilityCalculations(SuspiciousCase suspiciousCase) {
        Double customSummation = 0.00;
        Double exciseSummation = 0.00;
        Double otherSummation = 0.00;
        Double totalPenalty = 0.00;
        Double summationOfOffenceOrTotalPenalty = 0.00;

        RevenueLiability revenueLiability = suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability();
        Double summation = revenueLiability.getCustomsInterest() + revenueLiability.getForfieture() + revenueLiability.getVat() + revenueLiability.getVatInterest() + revenueLiability.getVatPenalty();
        for (RevenueLiabilityDuty revenueLiabilityDuty : revenueLiability.getRevenueLiabilityDutys()) {
            switch (revenueLiabilityDuty.getDuty().getDutyType()) {
                case CUSTOM:
                    customSummation = Double.sum(customSummation, revenueLiabilityDuty.getAmount());
                    break;
                case EXCISE:
                    exciseSummation = Double.sum(exciseSummation, revenueLiabilityDuty.getAmount());
                    break;
                case OTHER:
                    otherSummation = Double.sum(otherSummation, revenueLiabilityDuty.getAmount());
                    break;
                default:
                    break;
            }
        }
        for (AuditReportOffencePenalty auditReportOffence : suspiciousCase.getAuditPlan().getAuditReport().getAuditReportOffencePenalties()) {
            totalPenalty = Double.sum(totalPenalty, auditReportOffence.getOffencePenalty().getOffenceDescription().getDeposit());
            summationOfOffenceOrTotalPenalty = Double.sum(summationOfOffenceOrTotalPenalty, auditReportOffence.getOffencePenalty().getSubOffenceOrPenaltyAmount());
        }
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalCustomsDuty(customSummation);
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalExciseDuty(exciseSummation);
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalDutyOther(otherSummation);

        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalPenalty(summationOfOffenceOrTotalPenalty);
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalCustomsInterest(revenueLiability.getCustomsInterest());
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalVatInterest(revenueLiability.getVatInterest());
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalVat(revenueLiability.getVat());
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalVatPenalty(revenueLiability.getVatPenalty());

        suspiciousCase.getAuditPlan().getAuditReport().setSubPenaltyTotal(totalPenalty);
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setTotalPenalty(totalPenalty);
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().setTotalSubLiability(SummationUtility.sumArrayOfNumbers(summation, customSummation, exciseSummation, otherSummation));
        suspiciousCase.getAuditPlan().getAuditReport().getTotalLiability().setLiabilitiesTotal(SummationUtility.sumArrayOfNumbers(summation, customSummation, exciseSummation, otherSummation, totalPenalty));
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

    public String handleFlow(FlowEvent event) {
        
        if (event.getOldStep().equals("audit_report_tab_2024")) {
            if (getEntity().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().isEmpty() && getEntity().getAuditPlan().getAuditReport().getWasOffenceDetected().equals(YesNoEnum.YES)) {
                addError("Complete offence and penalties");
            }
            if (getEntity().getAuditPlan().getAuditReport().getThereRevenueLiability().equals(YesNoEnum.YES)) {
                Double revenueDutySubTotal = 0.00;
                for (RevenueLiabilityDuty revenueLiabilityDuty : getEntity().getAuditPlan().getAuditReport().getRevenueLiability().getRevenueLiabilityDutys()) {
                    revenueDutySubTotal = Double.sum(revenueDutySubTotal, revenueLiabilityDuty.getAmount());
                }
                if (revenueDutySubTotal == 0.00) {
                    addError("Complete Revenue liabilities");
                }
            }
            if (this.getErrorCollectionMsg().isEmpty()) {
                suspiciousCaseService.update(getEntity());
            } else {
                Iterator<String> iterator = getErrorCollectionMsg().iterator();
                while (iterator.hasNext()) {
                    addErrorMessage(iterator.next());
                }
//                PrimeFaces.current().ajax().update("@namingcontainer@namingcontainer","messages");
                return event.getOldStep();
            }
        }

        return event.getNewStep();

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
// Letter Dialog

    public void addOrUpdateLetter(Letter letter) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("letterKey", letter);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseKey", getEntity().getId());
        openDialogBox("letterpopup", null, 1400, 800);
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

    public void onPenaltiesTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equalsIgnoreCase("audit_offenceandpenalties_2024")) {
            auditReportOffencePenalty = new AuditReportOffencePenalty();
            auditReportOffencePenalty.setCreatedBy(getActiveUser().getSid());
            auditReportOffencePenalty.setCreatedDate(new Date());
            OffenceAndPenalty penalty = new OffenceAndPenalty();
            penalty.setCreatedBy(getActiveUser().getSid());
            penalty.setCreatedDate(new Date());
            auditReportOffencePenalty.setOffencePenalty(penalty);
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

    public List<YesNoEnum> getYesNoOptions() {
        return yesNoOptions;
    }

    public void setYesNoOptions(List<YesNoEnum> yesNoOptions) {
        this.yesNoOptions = yesNoOptions;
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

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public AuditReportOffencePenalty getAuditReportOffencePenalty() {
        return auditReportOffencePenalty;
    }

    public void setAuditReportOffencePenalty(AuditReportOffencePenalty auditReportOffencePenalty) {
        this.auditReportOffencePenalty = auditReportOffencePenalty;
    }

    public void addAuditReportOffencePenaltyEntiy(AuditReportOffencePenalty auditReportOffencePenalty) {
        this.auditReportOffencePenalty = auditReportOffencePenalty;
    }

    public StreamedContent getDownloadLetter() {
        return downloadLetter;
    }

    public void setDownloadLetter(StreamedContent downloadLetter) {
        this.downloadLetter = downloadLetter;
    }

}
