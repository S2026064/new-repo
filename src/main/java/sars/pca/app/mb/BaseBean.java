package sars.pca.app.mb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DialogFrameworkOptions;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.SummationUtility;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.AuditDetails;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Commodity;
import sars.pca.app.domain.CountryOfOrigin;
import sars.pca.app.domain.CustomsRegistrationParticulars;
import sars.pca.app.domain.CustomsTurnOver;
import sars.pca.app.domain.CustomsValue;
import sars.pca.app.domain.DirectorDetails;
import sars.pca.app.domain.Emp201Return;
import sars.pca.app.domain.EnterpriseCompanyLink;
import sars.pca.app.domain.EntityTypeDetails;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.NonComplianceDetails;
import sars.pca.app.domain.OutstandingReturnAndDebt;
import sars.pca.app.domain.PaymentAndRefund;
import sars.pca.app.domain.PublicOfficer;
import sars.pca.app.domain.ReportedPerson;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.domain.RiskAssessmentDetails;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.service.AttachmentServiceLocal;
import sars.pca.app.service.IbrDataServiceLocal;
import sars.pca.app.service.PrioritisationScoreServiceLocal;

/**
 *
 * @author S2026987
 * @param <T>
 */
public class BaseBean<T> extends SpringBeanAutowiringSupport implements Serializable {

    @ManagedProperty(value = "#{activeUser}")
    private ActiveUser activeUser;
    private List<String> errorCollectionMsg = new ArrayList<>();
    private Map<String, Integer> errorMapMsg = new HashMap<>();
    private boolean list;
    private boolean add;
    private boolean analyse;
    private boolean update;
    private boolean search;
    private boolean caseSelection;
    private boolean transfer;
    private boolean allocate;
    private boolean letterGenView;
    private boolean mergecase;
    private boolean fetchCaseFromPool;
    private List<T> collections = new ArrayList<>();
    T entity;
    private String confirmationMessage;
    private String panelTitleName;
    private final String OS = System.getProperty("os.name").toLowerCase();
    final Logger LOG = Logger.getLogger(BaseBean.class.getName());
    private byte[] fileUploadBytes;
    private StreamedContent downloadFile;
    @Autowired
    private AttachmentServiceLocal attachmentService;
    @Autowired
    private IbrDataServiceLocal ibrDataService;
    @Autowired
    private PrioritisationScoreServiceLocal prioritisationScoreService;
    private List<DirectorDetails> directordetails = new ArrayList<>();
    private List<CustomsTurnOver> customsturnovers = new ArrayList<>();
    private List<Commodity> top10commoditiesimporteds = new ArrayList<>();
    private List<Commodity> top10commoditiesexporteds = new ArrayList<>();
    private List<Commodity> topcommoditieswharehouses = new ArrayList<>();
    private List<Commodity> top10transitcommodities = new ArrayList<>();
    private List<CountryOfOrigin> top10countriesoforigins = new ArrayList<>();
    private List<CustomsValue> top10purposecodesxporteds = new ArrayList<>();
    private List<CustomsValue> top10clearingagentsxporteds = new ArrayList<>();
    private List<CustomsValue> top10portsofentryxporteds = new ArrayList<>();
    private List<EnterpriseCompanyLink> enterprisecompanylinks = new ArrayList<>();
    private List<OutstandingReturnAndDebt> monthendoutstandingreturns = new ArrayList<>();
    private List<Emp201Return> detailemp201returns = new ArrayList<>();
    private List<PaymentAndRefund> paymentsandrefunds = new ArrayList<>();
    private List<RiskAssessment> riskassessmentgeninfos = new ArrayList<>();
    private List<RiskAssessmentDetails> riskassessmentdetails = new ArrayList<>();
    private List<CustomsRegistrationParticulars> customsRegistrationParticularses = new ArrayList<>();
    private List<PublicOfficer> publicOfficers = new ArrayList<>();

    public BaseBean() {
    }

    public ActiveUser getActiveUser() {
        return activeUser;
    }

    /**
     * @param activeUser the activeUser to set
     */
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    public void redirect(String page) {
        try {
            StringBuilder builder = new StringBuilder(page);
            builder.append(".xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect(builder.toString());
        } catch (IOException ex) {
            Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirecting(String page) {
        try {
            StringBuilder builder = new StringBuilder(page);
            builder.append(".xhtml?faces-redirect=true");
            FacesContext.getCurrentInstance().getExternalContext().redirect(builder.toString());
        } catch (IOException ex) {
            Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addInformationMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addInformationMessage(String... detail) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : detail) {
            stringBuilder.append(message);
            stringBuilder.append(" ");
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", stringBuilder.toString().trim());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String... detail) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : detail) {
            stringBuilder.append(message);
            stringBuilder.append(" ");
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", stringBuilder.toString().trim());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addWarningMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addWarningMessage(String... detail) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : detail) {
            stringBuilder.append(message);
            stringBuilder.append(" ");
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "", stringBuilder.toString().trim());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void invalidateUserSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public void fileDownload(Attachment attachment) throws IOException {
        String objectId = attachment.getCode();
        String stringTooLong = IOUtils.toString(attachmentService.download(objectId), "UTF-8");
        String b64 = stringTooLong;
        byte[] decoder = Base64.getDecoder().decode(b64);
        downloadFile = DefaultStreamedContent.builder().name(attachment.getDescription()).contentType("application/pdf").stream(() -> new ByteArrayInputStream(decoder)).build();

    }

    public void addError(String... message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String error : message) {
            stringBuilder.append(error);
            stringBuilder.append(" ");
        }
        this.getErrorCollectionMsg().add(stringBuilder.toString());
    }

    public void addInfomation(String... message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String information : message) {
            stringBuilder.append(information);
            stringBuilder.append(" ");
        }
        this.getErrorCollectionMsg().add(stringBuilder.toString());
    }

    public void addWarning(String... message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String warning : message) {
            stringBuilder.append(warning);
            stringBuilder.append(" ");
        }
        this.getErrorCollectionMsg().add(stringBuilder.toString());
    }

    public String defaultRouter(String page) {
        StringBuilder builder = new StringBuilder(page);
        builder.append(".xhtml");
        return builder.toString();
    }

    public String defaultRouting(String page) {
        StringBuilder builder = new StringBuilder(page);
        builder.append(".xhtml");
        return builder.toString();
    }

    public void openDialogBox(String targetPageName, Map<String, List<String>> parameters, Integer width, Integer height) {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .draggable(false)
                .modal(true)
                .width(String.valueOf(width))
                .height(String.valueOf(height))
                .contentWidth("100%")
                .contentHeight("100%")
                .headerElement("customheader")
                .dynamic(false)
                .closeOnEscape(true)
                .position("top")
                .build();
        PrimeFaces.current().dialog().openDynamic(targetPageName, options, parameters);
    }

    public void addOrUpdateReportedPerson(ReportedPerson reportedPerson, String targetPage) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reportedPersonKey", reportedPerson);
        openDialogBox(targetPage, null, 1300, 750);
    }

    public void addOrUpdateNonComplianceDetails(NonComplianceDetails nonComplianceDetails, String targetPage) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nonComplianceKey", nonComplianceDetails);
        openDialogBox(targetPage, null, 1300, 780);
    }

    public void addOrUpdateEntityTypeDetails(EntityTypeDetails entityTypeDetails, String targetPage) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("entityTypeKey", entityTypeDetails);
        openDialogBox(targetPage, null, 1300, 750);
    }

    public void addOrUpdateRiskAssessmentDetails(RiskAssessmentDetails riskAssessmentDetails, String targetPage) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("riskAssessmentDetailKey", riskAssessmentDetails);
        openDialogBox(targetPage, null, 1300, 700);
    }

    public void addOrUpdateAuditDetails(AuditDetails auditDetails, String targetPage, Long riskAssessmentId) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("auditDetailKey", auditDetails);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("riskAssessmentKey", riskAssessmentId);
        openDialogBox(targetPage, null, 1300, 700);
    }

    public void populateIbrdata(String id, String customExciseCode) {
        switch (id) {
            case "risk_accordion_tab_003_customsregistparticulars":
                getCustomsRegistrationParticularses().clear();
                getCustomsRegistrationParticularses().addAll(ibrDataService.getCustomsRegistrationParticulars(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_004_directordetails":
                getDirectordetails().clear();
                getDirectordetails().addAll(ibrDataService.getDirectorDetailsByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_005_customsturnover":
                getCustomsturnovers().clear();
                getCustomsturnovers().addAll(ibrDataService.getCustomsTurnOverByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_006_top10commoditiesimported":
                getTop10commoditiesimporteds().clear();
                getTop10commoditiesimporteds().addAll(ibrDataService.getCommoditiesImportedPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_007_top10commoditiesexported":
                getTop10commoditiesexporteds().clear();
                getTop10commoditiesexporteds().addAll(ibrDataService.getCommoditiesExportedPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_008_topcommoditieswharehouse":
                getTopcommoditieswharehouses().clear();
                getTopcommoditieswharehouses().addAll(ibrDataService.getCommoditiesWarehousePerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_009_top10transitcommodities":
                getTop10transitcommodities().clear();
                getTop10transitcommodities().addAll(ibrDataService.getTransitCommoditiesPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_010_top10countriesoforigin":
                getTop10countriesoforigins().clear();
                getTop10countriesoforigins().addAll(ibrDataService.getCountriesOfOriginPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_011_top10purposecodesxported":
                getTop10purposecodesxporteds().clear();
                getTop10purposecodesxporteds().addAll(ibrDataService.getPurposeCodesPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_012_top10clearingagentsxported":
                getTop10clearingagentsxporteds().clear();
                getTop10clearingagentsxporteds().addAll(ibrDataService.getClearingAgentsPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_013_top10portsofentryxported":
                getTop10portsofentryxporteds().clear();
                getTop10portsofentryxporteds().addAll(ibrDataService.getPortsOfEntryPerCustomsValueByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_014_enterprisecompanylinks":
                getEnterprisecompanylinks().clear();
                getEnterprisecompanylinks().addAll(ibrDataService.getEnterpriseCompanyLinkByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_016_monthendoutstandingreturns":
                getMonthendoutstandingreturns().clear();
                getMonthendoutstandingreturns().addAll(ibrDataService.getMonthEndOutStandingReturnsAndDebtsByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_017_detailemp201return":
                getDetailemp201returns().clear();
                getDetailemp201returns().addAll(ibrDataService.getEmp201ReturnByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            case "risk_accordion_tab_018_paymentsandrefunds":
                getPaymentsandrefunds().clear();
                getPaymentsandrefunds().addAll(ibrDataService.getPaymentAndRefundByCustomeCode(customExciseCode, getActiveUser().getSid()));
                break;
            default:
                break;
        }
    }

    public void crcsReviewerCasePrioritisationEvaluation(SuspiciousCase suspiciousCase) {
        //Customs Value amounts
        Double totalCustomValue = SummationUtility.sumArrayOfNumbers(suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalCustomDeclaredExport(),
                suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalCustomDeclaredImport());
        Integer customScore = prioritisationScoreService.findScorebyAmount(totalCustomValue, ScoreType.CUSTOMS_VALUE);
        Double customsValuePriorityIndex = SummationUtility.multiplyArrayOfNumbers(customScore, getActiveUser().getCustomsValueScoringWeight());

        //Number of lines
        Double totalNumberOfLines = SummationUtility.sumArrayOfNumbers(suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalVolumeTransactionImport(),
                suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalVolumetransactionExport());
        Integer numberOfLinesScore = prioritisationScoreService.findScorebyNumberOfLines(totalNumberOfLines.intValue(), ScoreType.NUMBER_OF_LINES);
        Double numberOfLinesPriorityIndex = SummationUtility.multiplyArrayOfNumbers(numberOfLinesScore, getActiveUser().getValueOfTransactionScoringWeight());

        //Priority Index Calculation
        Double priorityIndex = SummationUtility.sumArrayOfNumbers(customsValuePriorityIndex, numberOfLinesPriorityIndex);

        suspiciousCase.setPriorityIndex(priorityIndex.intValue());
    }

    public void riskCasePrioritisationEvaluation(SuspiciousCase suspiciousCase) {
        //Customs Value amounts
        Double totalCustomValue = SummationUtility.sumArrayOfNumbers(suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalCustomDeclaredExport(),
                suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalCustomDeclaredImport());
        Integer customScore = prioritisationScoreService.findScorebyAmount(totalCustomValue, ScoreType.CUSTOMS_VALUE);
        Double customsValuePriorityIndex = SummationUtility.multiplyArrayOfNumbers(customScore, getActiveUser().getCustomsValueScoringWeight());
        //Number of lines
        Double totalNumberOfLines = SummationUtility.sumArrayOfNumbers(suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalVolumeTransactionImport(),
                suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalVolumetransactionExport());
        Integer numberOfLinesScore = prioritisationScoreService.findScorebyNumberOfLines(totalNumberOfLines.intValue(), ScoreType.NUMBER_OF_LINES);
        Double numberOfLinesPriorityIndex = SummationUtility.multiplyArrayOfNumbers(numberOfLinesScore, getActiveUser().getValueOfTransactionScoringWeight());

        //Commodity alignment
        Integer commodityAlignmentScore = 0;
        for (RiskAssessmentDetails riskAssessmentDetails : suspiciousCase.getSarCase().getRiskAssessment().getRiskAssessmentDetailses()) {
            if (riskAssessmentDetails.getRiskAssessmentRelate().equals(YesNoEnum.YES)) {
                commodityAlignmentScore = prioritisationScoreService.findScorebyCommodityAlignment(riskAssessmentDetails.getRiskAssessmentRelate(), ScoreType.COMMODITY_ALIGNMENT);
                break;
            }
        }
        Double commodityAlignmentPriorityIndex = SummationUtility.multiplyArrayOfNumbers(commodityAlignmentScore, getActiveUser().getAlignmentOfComidityWeight());
        //Calculate number risk identified.
        Double numberOfRiskIdentified = SummationUtility.sumArrayOfNumbers(suspiciousCase.getSarCase().getRiskAssessment().getContraventionHistory(),
                suspiciousCase.getSarCase().getRiskAssessment().getDutyExemption(),
                suspiciousCase.getSarCase().getRiskAssessment().getDutySuspension(),
                suspiciousCase.getSarCase().getRiskAssessment().getLicensingAndRegistration(),
                suspiciousCase.getSarCase().getRiskAssessment().getModeOfTransport(),
                suspiciousCase.getSarCase().getRiskAssessment().getOriginCountry(),
                suspiciousCase.getSarCase().getRiskAssessment().getOtherPAndR(),
                suspiciousCase.getSarCase().getRiskAssessment().getRevenueProtection(),
                suspiciousCase.getSarCase().getRiskAssessment().getRulesOfOrigin(),
                suspiciousCase.getSarCase().getRiskAssessment().getSecurity(),
                suspiciousCase.getSarCase().getRiskAssessment().getTarrif(),
                suspiciousCase.getSarCase().getRiskAssessment().getTradeRestrictions(),
                suspiciousCase.getSarCase().getRiskAssessment().getValuation(),
                suspiciousCase.getSarCase().getRiskAssessment().getWarehousing());

        suspiciousCase.getSarCase().getCasePrioritisationDetails().setNumberOfRiskAreas(numberOfRiskIdentified.intValue());

        Double riskIdentifiedPriorityIndex = SummationUtility.multiplyArrayOfNumbers(numberOfRiskIdentified, getActiveUser().getNumberOfRiskAreasIdentifiedScoringWeight());

        Double riskLikelyHood = 0.00;
        Double riskRatingScore = 0.00;
        Double toRevenueAtRisk = 0.00;

        for (RiskAssessmentDetails riskAssessmentDetails : suspiciousCase.getSarCase().getRiskAssessment().getRiskAssessmentDetailses()) {
            riskLikelyHood = Double.sum(riskLikelyHood, riskAssessmentDetails.getOverallRiskRating());
            toRevenueAtRisk = Double.sum(toRevenueAtRisk, riskAssessmentDetails.getRevenueAtRisk());
        }
        riskRatingScore = riskLikelyHood / numberOfRiskIdentified;
        Double riskRatingPriorityIndex = SummationUtility.multiplyArrayOfNumbers(riskRatingScore, getActiveUser().getOveralRiskRatingScoringWeight());

        //Calculation of revenue at risk
        Integer revenueAtRiskScore = prioritisationScoreService.findScorebyAmount(toRevenueAtRisk, ScoreType.REVENUE_AT_RISK);
        Double revenueAtRiskPriorityIndex = SummationUtility.multiplyArrayOfNumbers(revenueAtRiskScore, getActiveUser().getRevenueAtRiskScoringWeight());

        //Priority Index Calculation
        Double priorityIndex = SummationUtility.sumArrayOfNumbers(customsValuePriorityIndex,
                numberOfLinesPriorityIndex,
                commodityAlignmentPriorityIndex,
                riskIdentifiedPriorityIndex,
                riskRatingPriorityIndex,
                revenueAtRiskPriorityIndex);
        suspiciousCase.setPriorityIndex(priorityIndex.intValue());
    }

    public Integer getRiskIdenfiedValue(SuspiciousCase suspiciousCase) {
        errorMapMsg.clear();
        if (suspiciousCase.getSarCase().getRiskAssessment().getContraventionHistoryOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Contravention History", suspiciousCase.getSarCase().getRiskAssessment().getContraventionHistory());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getOriginCountryOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Origin Country", suspiciousCase.getSarCase().getRiskAssessment().getOriginCountry());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getDutyExemptionOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Duty Exemption", suspiciousCase.getSarCase().getRiskAssessment().getDutyExemption());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getDutySuspensionOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Duty Suspension", suspiciousCase.getSarCase().getRiskAssessment().getDutySuspension());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getLicensingAndRegistrationOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Licensensing And Registration", suspiciousCase.getSarCase().getRiskAssessment().getLicensingAndRegistration());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getModeOfTransportOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Mode of Transport", suspiciousCase.getSarCase().getRiskAssessment().getModeOfTransport());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getOtherPAndROption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Other And PA", suspiciousCase.getSarCase().getRiskAssessment().getOtherPAndR());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getRevenueProtectionOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Revenue Protection", suspiciousCase.getSarCase().getRiskAssessment().getRevenueProtection());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getRulesOfOriginOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Rules of Origin", suspiciousCase.getSarCase().getRiskAssessment().getRulesOfOrigin());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getSecurityOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Security", suspiciousCase.getSarCase().getRiskAssessment().getSecurity());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getTarrifOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Tarrif", suspiciousCase.getSarCase().getRiskAssessment().getTarrif());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getTradeRestrictionsOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Trade Restrictions", suspiciousCase.getSarCase().getRiskAssessment().getTradeRestrictions());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getValuationOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Valuation", suspiciousCase.getSarCase().getRiskAssessment().getValuation());
        }
        if (suspiciousCase.getSarCase().getRiskAssessment().getWarehousingOption().equals(YesNoEnum.YES)) {
            errorMapMsg.put("Warehousing", suspiciousCase.getSarCase().getRiskAssessment().getWarehousing());
        }
        Integer[] riskValues = errorMapMsg.values().toArray(new Integer[0]);
        Double numberOfRiskIdentified = SummationUtility.sumArrayOfNumbers(riskValues);
        return numberOfRiskIdentified.intValue();
    }

    /**
     * @return the errorCollectionMsg
     */
    public List<String> getErrorCollectionMsg() {
        return errorCollectionMsg;
    }

    /**
     * @param errorCollectionMsg the errorCollectionMsg to set
     */
    public void setErrorCollectionMsg(List<String> errorCollectionMsg) {
        this.errorCollectionMsg = errorCollectionMsg;
    }

    public void addErrorCollectionMsg(String message) {
        errorCollectionMsg.add(message);
    }

    public BaseBean reset() {
        setList(false);
        setAdd(false);
        setSearch(false);
        setUpdate(false);
        setAnalyse(false);
        setCaseSelection(false);
        setTransfer(false);
        setAllocate(false);
        setMergecase(false);
        setFetchCaseFromPool(false);
        setLetterGenView(false);
        return this;
    }

    public boolean hasActiveUserCommented(SuspiciousCase suspiciousCase) {
        for (Comment comment : suspiciousCase.getComments()) {
            if (getActiveUser().getSid().equals(comment.getCreatedBy())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActiveUserAttachedDocument(SuspiciousCase suspiciousCase) {
        for (Attachment attachment : suspiciousCase.getAttachments()) {
            if (getActiveUser().getSid().equals(attachment.getCreatedBy())) {
                return true;
            }
        }
        return false;
    }

    public boolean isWindows() {
        return (OS.contains("win"));
    }

    public boolean isMac() {
        return (OS.contains("mac"));
    }

    public boolean isLinux() {
        return (OS.contains("nux"));
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public List<T> getCollections() {
        return collections;
    }

    public void setCollections(List<T> collections) {
        this.collections = collections;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void addEntity(T entity) {
        this.entity = entity;
    }

    public void addCollections(List<T> list) {
        collections.clear();
        collections.addAll(list);
    }

    public void addCollection(T entity) {
        collections.add(0, entity);
    }

    public void addCollections(Set<T> list) {
        collections.clear();
        collections.addAll(list);
    }

    public void addFreshEntityAndSynchView(T entity) {
        collections.add(0, entity);
        addEntity(entity);
    }

    public void addFreshEntity(T entity) {
        addEntity(entity);
    }

    public BaseBean removeEntity(T entity) {
        collections.remove(entity);
        return this;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getPanelTitleName() {
        return panelTitleName;
    }

    public void setPanelTitleName(String panelTitleName) {
        this.panelTitleName = panelTitleName;
    }

    public boolean isAnalyse() {
        return analyse;
    }

    public void setAnalyse(boolean analyse) {
        this.analyse = analyse;
    }

    public boolean isCaseSelection() {
        return caseSelection;
    }

    public boolean isMergecase() {
        return mergecase;
    }

    public void setMergecase(boolean mergecase) {
        this.mergecase = mergecase;
    }

    public void setCaseSelection(boolean caseSelection) {
        this.caseSelection = caseSelection;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public boolean isAllocate() {
        return allocate;
    }

    public void setAllocate(boolean allocate) {
        this.allocate = allocate;
    }

    public byte[] getFileUploadBytes() {
        return fileUploadBytes;
    }

    public void setFileUploadBytes(byte[] fileUploadBytes) {
        this.fileUploadBytes = fileUploadBytes;
    }

    public List<DirectorDetails> getDirectordetails() {
        return directordetails;
    }

    public void setDirectordetails(List<DirectorDetails> directordetails) {
        this.directordetails = directordetails;
    }

    public List<CustomsTurnOver> getCustomsturnovers() {
        return customsturnovers;
    }

    public void setCustomsturnovers(List<CustomsTurnOver> customsturnovers) {
        this.customsturnovers = customsturnovers;
    }

    public List<Commodity> getTop10commoditiesimporteds() {
        return top10commoditiesimporteds;
    }

    public void setTop10commoditiesimporteds(List<Commodity> top10commoditiesimporteds) {
        this.top10commoditiesimporteds = top10commoditiesimporteds;
    }

    public List<Commodity> getTop10commoditiesexporteds() {
        return top10commoditiesexporteds;
    }

    public void setTop10commoditiesexporteds(List<Commodity> top10commoditiesexporteds) {
        this.top10commoditiesexporteds = top10commoditiesexporteds;
    }

    public List<Commodity> getTopcommoditieswharehouses() {
        return topcommoditieswharehouses;
    }

    public void setTopcommoditieswharehouses(List<Commodity> topcommoditieswharehouses) {
        this.topcommoditieswharehouses = topcommoditieswharehouses;
    }

    public List<Commodity> getTop10transitcommodities() {
        return top10transitcommodities;
    }

    public void setTop10transitcommodities(List<Commodity> top10transitcommodities) {
        this.top10transitcommodities = top10transitcommodities;
    }

    public List<CountryOfOrigin> getTop10countriesoforigins() {
        return top10countriesoforigins;
    }

    public void setTop10countriesoforigins(List<CountryOfOrigin> top10countriesoforigins) {
        this.top10countriesoforigins = top10countriesoforigins;
    }

    public List<CustomsValue> getTop10purposecodesxporteds() {
        return top10purposecodesxporteds;
    }

    public void setTop10purposecodesxporteds(List<CustomsValue> top10purposecodesxporteds) {
        this.top10purposecodesxporteds = top10purposecodesxporteds;
    }

    public List<CustomsValue> getTop10clearingagentsxporteds() {
        return top10clearingagentsxporteds;
    }

    public void setTop10clearingagentsxporteds(List<CustomsValue> top10clearingagentsxporteds) {
        this.top10clearingagentsxporteds = top10clearingagentsxporteds;
    }

    public List<CustomsValue> getTop10portsofentryxporteds() {
        return top10portsofentryxporteds;
    }

    public void setTop10portsofentryxporteds(List<CustomsValue> top10portsofentryxporteds) {
        this.top10portsofentryxporteds = top10portsofentryxporteds;
    }

    public List<EnterpriseCompanyLink> getEnterprisecompanylinks() {
        return enterprisecompanylinks;
    }

    public void setEnterprisecompanylinks(List<EnterpriseCompanyLink> enterprisecompanylinks) {
        this.enterprisecompanylinks = enterprisecompanylinks;
    }

    public List<OutstandingReturnAndDebt> getMonthendoutstandingreturns() {
        return monthendoutstandingreturns;
    }

    public void setMonthendoutstandingreturns(List<OutstandingReturnAndDebt> monthendoutstandingreturns) {
        this.monthendoutstandingreturns = monthendoutstandingreturns;
    }

    public List<Emp201Return> getDetailemp201returns() {
        return detailemp201returns;
    }

    public void setDetailemp201returns(List<Emp201Return> detailemp201returns) {
        this.detailemp201returns = detailemp201returns;
    }

    public List<PaymentAndRefund> getPaymentsandrefunds() {
        return paymentsandrefunds;
    }

    public void setPaymentsandrefunds(List<PaymentAndRefund> paymentsandrefunds) {
        this.paymentsandrefunds = paymentsandrefunds;
    }

    public List<RiskAssessment> getRiskassessmentgeninfos() {
        return riskassessmentgeninfos;
    }

    public void setRiskassessmentgeninfos(List<RiskAssessment> riskassessmentgeninfos) {
        this.riskassessmentgeninfos = riskassessmentgeninfos;
    }

    public List<RiskAssessmentDetails> getRiskassessmentdetails() {
        return riskassessmentdetails;
    }

    public void setRiskassessmentdetails(List<RiskAssessmentDetails> riskassessmentdetails) {
        this.riskassessmentdetails = riskassessmentdetails;
    }

    public List<CustomsRegistrationParticulars> getCustomsRegistrationParticularses() {
        return customsRegistrationParticularses;
    }

    public void setCustomsRegistrationParticularses(List<CustomsRegistrationParticulars> customsRegistrationParticularses) {
        this.customsRegistrationParticularses = customsRegistrationParticularses;
    }

    public List<PublicOfficer> getPublicOfficers() {
        return publicOfficers;
    }

    public void setPublicOfficers(List<PublicOfficer> publicOfficers) {
        this.publicOfficers = publicOfficers;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(StreamedContent downloadFile) {
        this.downloadFile = downloadFile;
    }

    public IbrDataServiceLocal getIbrDataService() {
        return ibrDataService;
    }

    public boolean isFetchCaseFromPool() {
        return fetchCaseFromPool;
    }

    public void setFetchCaseFromPool(boolean fetchCaseFromPool) {
        this.fetchCaseFromPool = fetchCaseFromPool;
    }

    public Map<String, Integer> getErrorMapMsg() {
        return errorMapMsg;
    }

    public void setErrorMapMsg(Map<String, Integer> errorMapMsg) {
        this.errorMapMsg = errorMapMsg;
    }

    public boolean isLetterGenView() {
        return letterGenView;
    }

    public void setLetterGenView(boolean letterGenView) {
        this.letterGenView = letterGenView;
    }

}
