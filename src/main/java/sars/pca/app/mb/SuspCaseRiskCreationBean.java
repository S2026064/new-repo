package sars.pca.app.mb;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.MainIndustry;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.domain.RiskAssessmentDetails;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.gen.dto.res.PDFDocumentGenerationManagementResponse;
import sars.pca.app.service.LetterGenerationServiceLocal;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseRiskCreationBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private LetterGenerationServiceLocal letterGenerationService;
    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private AttachmentType selectedAttachmentType;
    private UploadedFile originalFile;
    private int documentSize;
    private List<MainIndustry> mainIndustries = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();
    private StreamedContent downloadLetter;
    private List<LocationOffice> locationOffices = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        mainIndustries.addAll(Arrays.asList(MainIndustry.values()));
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        locationOffices.addAll(locationOfficeService.findByOfficeType(OfficeType.CI_OFFICE));

        List<CaseStatus> caseStatuses = new ArrayList<>();
        caseStatuses.add(CaseStatus.ACTIVE_CRCS_ANALYSIS);
        caseStatuses.add(CaseStatus.ACTIVE_RA_SEND_BACK_FOR_REWORK);
        caseStatuses.add(CaseStatus.ACTIVE_RA_REJECTED);
        caseStatuses.add(CaseStatus.ACTIVE_RA_APPROVAL);
        addCollections(suspiciousCaseService.findByStatusAndUserSid(caseStatuses, getActiveUser().getUser().getSid()));
    }

    public void addOrUpdate(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        if (suspiciousCase.getSarCase().getRiskAssessment() != null) {
            suspiciousCase.getSarCase().getRiskAssessment().setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.getSarCase().getRiskAssessment().setUpdatedDate(new Date());
            //populateRiskAreas(suspiciousCase.getSarCase().getRiskAssessment());
        } else {
            RiskAssessment riskAssessment = new RiskAssessment();
            riskAssessment.setCreatedBy(getActiveUser().getSid());
            riskAssessment.setCreatedDate(new Date());
            suspiciousCase.getSarCase().setRiskAssessment(riskAssessment);
        }
        addEntity(suspiciousCase);
    }

    public void save(SuspiciousCase suspiciousCase) {
        removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Risk Asssement was successfully created.");
        reset().setList(true);
    }

    //This method submit the case to the CRCS Ops Manager for approval.
    public void submit(SuspiciousCase suspiciousCase) {
        //validation
        if (suspiciousCase.getSarCase().getRiskAssessment().getRiskAssessmentDetailses().size() != getRiskIdenfiedValue(suspiciousCase)) {
            for (Map.Entry<String, Integer> entry : getErrorMapMsg().entrySet()) {
                addErrorMessage("Enter " + entry.getValue(), " Risk Assessment Details for ", entry.getKey());
            }
            return;
        }
        //Case Prioritisation calculation at risk assessement stage.
        riskCasePrioritisationEvaluation(suspiciousCase);
        suspiciousCase.setStatus(CaseStatus.ACTIVE_CRCS_RA);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Risk Asssement was successfully submitted.");

        //email to crcs manager
        emailNotificationSenderService.sendEmailNotification(NotificationType.APPROVE_RA, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), getActiveUser().getUser().getManager(), getActiveUser().getUser());

        reset().setList(true);

    }

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
        reset().setList(true);
    }

    public void addOnlyRiskAssessmentDetails(RiskAssessmentDetails riskAssessmentDetails) {
        if (populateIdentifiedRiskAreas(getEntity().getSarCase().getRiskAssessment()).isEmpty()) {
            addErrorMessage("Select at least 1 risk area before capturing risk details.");
        } else {
            SuspiciousCase persistentSuspCase = suspiciousCaseService.update(getEntity());
            if (persistentSuspCase != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("riskAssessmentDetailKey", riskAssessmentDetails);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("riskAssessmentKey", persistentSuspCase.getSarCase().getRiskAssessment().getId());
                openDialogBox("riskassessmentdetailspopup", null, 1300, 700);
            }
        }
    }

    public void handleAddRiskAssessmentDetailsReturnListener(SelectEvent event) {
        RiskAssessmentDetails assessmentDetails = (RiskAssessmentDetails) event.getObject();
        getEntity().getSarCase().getRiskAssessment().addRiskAssessmentDetails(assessmentDetails);
    }

    public void handleUpdateRiskAssessmentDetailsReturnListener(SelectEvent event) {
        RiskAssessmentDetails riskAssessmentDetails = (RiskAssessmentDetails) event.getObject();
        Integer index = getEntity().getSarCase().getRiskAssessment().getRiskAssessmentDetailses().indexOf(riskAssessmentDetails);
        getEntity().getSarCase().getRiskAssessment().getRiskAssessmentDetailses().set(index, riskAssessmentDetails);
    }

    public void removeRiskAssessmentDetails(RiskAssessmentDetails riskAssessmentDetails) {
        getEntity().getSarCase().getRiskAssessment().removeRiskAssessmentDetails(riskAssessmentDetails);
        if (riskAssessmentDetails.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void addOrUpdateLetter(Letter letter) {

//        suspiciousCaseService.update(getEntity());
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

    public String onFlowProcess(FlowEvent event) {
        System.out.println(event.getNewStep());
        System.out.println(event.getComponent().getClientId());
        System.out.println(event.getComponent().getNamingContainer());
        System.out.println(event.getComponent().getParent());
        System.out.println(event.getComponent().getChildCount());
        return event.getNewStep();
    }

    public void onRiskSelectionListener(AjaxBehaviorEvent event) {
        if (getEntity().getSarCase().getRiskAssessment().getContraventionHistoryOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setContraventionHistory(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setContraventionHistory(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getOriginCountryOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setOriginCountry(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setOriginCountry(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getDutyExemptionOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setDutyExemption(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setDutyExemption(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getDutySuspensionOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setDutySuspension(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setDutySuspension(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getLicensingAndRegistrationOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setLicensingAndRegistration(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setLicensingAndRegistration(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getModeOfTransportOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setModeOfTransport(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setModeOfTransport(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getOtherPAndROption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setOtherPAndR(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setOtherPAndR(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getRevenueProtectionOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setRevenueProtection(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setRevenueProtection(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getRulesOfOriginOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setRulesOfOrigin(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setRulesOfOrigin(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getSecurityOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setSecurity(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setSecurity(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getTarrifOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setTarrif(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setTarrif(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getTradeRestrictionsOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setTradeRestrictions(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setTradeRestrictions(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getValuationOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setValuation(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setValuation(0);
        }
        if (getEntity().getSarCase().getRiskAssessment().getWarehousingOption().equals(YesNoEnum.YES)) {
            getEntity().getSarCase().getRiskAssessment().setWarehousing(1);
        } else {
            getEntity().getSarCase().getRiskAssessment().setWarehousing(0);
        }
    }

    public List<RiskArea> populateIdentifiedRiskAreas(RiskAssessment riskAssessment) {
        List<RiskArea> indetifiedRiskAreas = new ArrayList<>();
        if (riskAssessment.getContraventionHistoryOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.CONTRAVENTION_HISTORY);
        }
        if (riskAssessment.getOriginCountryOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.COUNTRY_OF_ORIGIN);
        }
        if (riskAssessment.getDutyExemptionOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.DUTY_EXEMPTION_REGIMES);
        }
        if (riskAssessment.getDutySuspensionOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.DUTY_SUSPENSSION);
        }
        if (riskAssessment.getLicensingAndRegistrationOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.LICENSING_AND_REGISTRATION);
        }
        if (riskAssessment.getModeOfTransportOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.MODE_OF_TRANSPORT);
        }
        if (riskAssessment.getOtherPAndROption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.OTHER_P_AND_R);
        }
        if (riskAssessment.getRevenueProtectionOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.REVENUE_PROTECTION);
        }
        if (riskAssessment.getRulesOfOriginOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.RULES_OF_ORIGIN);
        }
        if (riskAssessment.getSecurityOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.SECURITY);
        }
        if (riskAssessment.getTarrifOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.TARRIF);
        }
        if (riskAssessment.getTradeRestrictionsOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.TRADE_RESTRICTIONS);
        }
        if (riskAssessment.getValuationOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.VALUATION);
        }
        if (riskAssessment.getWarehousingOption().equals(YesNoEnum.YES)) {
            indetifiedRiskAreas.add(RiskArea.WAREHOUSING);
        }
        return indetifiedRiskAreas;
    }


    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public int getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(int documentSize) {
        this.documentSize = documentSize;
    }

    public UploadedFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(UploadedFile originalFile) {
        this.originalFile = originalFile;
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

    public List<LocationOffice> getLocationOffices() {
        return locationOffices;
    }

    public void setLocationOffices(List<LocationOffice> locationOffices) {
        this.locationOffices = locationOffices;
    }

    public StreamedContent getDownloadLetter() {
        return downloadLetter;
    }

    public void setDownloadLetter(StreamedContent downloadLetter) {
        this.downloadLetter = downloadLetter;
    }
}
