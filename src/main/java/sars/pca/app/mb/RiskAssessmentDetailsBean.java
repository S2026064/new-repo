package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.domain.RiskAssessmentDetails;
import sars.pca.app.domain.RiskRatingConsequence;
import sars.pca.app.domain.RiskRatingLikelihood;
import sars.pca.app.service.HsChapterRiskServiceLocal;
import sars.pca.app.service.RiskAssessmentServiceLocal;
import sars.pca.app.service.RiskRatingConsequenceServiceLocal;
import sars.pca.app.service.RiskRatingLikelihoodServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class RiskAssessmentDetailsBean extends BaseBean<RiskAssessmentDetails> {

    @Autowired
    private HsChapterRiskServiceLocal hsChapterRiskService;
    @Autowired
    private RiskRatingLikelihoodServiceLocal riskRatingLikelihoodService;
    @Autowired
    private RiskRatingConsequenceServiceLocal riskRatingConsequenceService;
    @Autowired
    private RiskAssessmentServiceLocal riskAssessmentService;
    private List<RiskArea> riskAreas = new ArrayList<>();
    private List<HsChapterRisk> hsChapterRisks = new ArrayList<>();
    private List<RiskRatingLikelihood> riskRatingLikelihoods = new ArrayList<>();
    private List<RiskRatingConsequence> riskRatingConsequences = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();
    private RiskAssessment riskAssessment;

    @PostConstruct
    public void init() {
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        hsChapterRisks.addAll(hsChapterRiskService.listAll());
        riskRatingLikelihoods.addAll(riskRatingLikelihoodService.listAll());
        riskRatingConsequences.addAll(riskRatingConsequenceService.listAll());
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        RiskAssessmentDetails riskAssessmentDetails = (RiskAssessmentDetails) sessionMap.get("riskAssessmentDetailKey");
        sessionMap.remove("riskAssessmentDetailKey");
        Long riskAssessmentId = (Long) sessionMap.get("riskAssessmentKey");
        sessionMap.remove("riskAssessmentKey");
        if (riskAssessmentId != null) {
            riskAssessment = riskAssessmentService.findById(riskAssessmentId);
            populateRiskAreas(riskAssessment);
        }
        if (riskAssessmentDetails != null) {
            riskAssessmentDetails.setUpdatedBy(getActiveUser().getSid());
            riskAssessmentDetails.setUpdatedDate(new Date());
        } else {
            riskAssessmentDetails = new RiskAssessmentDetails();
            riskAssessmentDetails.setCreatedBy(getActiveUser().getSid());
            riskAssessmentDetails.setCreatedDate(new Date());
            RiskRatingConsequence riskRatingConsequence = new RiskRatingConsequence();
            riskRatingConsequence.setCreatedBy(getActiveUser().getSid());
            riskRatingConsequence.setCreatedDate(new Date());
            riskAssessmentDetails.setRiskRatingConsequence(riskRatingConsequence);
            RiskRatingLikelihood riskRatingLikelihood = new RiskRatingLikelihood();
            riskRatingLikelihood.setCreatedBy(getActiveUser().getSid());
            riskRatingLikelihood.setCreatedDate(new Date());
            riskAssessmentDetails.setRiskRatingLikehood(riskRatingLikelihood);
        }
        addEntity(riskAssessmentDetails);
    }

    public void onRiskRatingConsequenceListener(SelectEvent event) {
        RiskRatingConsequence riskRatingConsequence = (RiskRatingConsequence) event.getObject();
        getEntity().setOverallRiskRating(riskRatingConsequence.getWeight() * getEntity().getRiskRatingLikehood().getWeight());
    }

    public void onRiskRatingLikelihoodListener(SelectEvent event) {
        RiskRatingLikelihood riskRatingLikelihood = (RiskRatingLikelihood) event.getObject();
        getEntity().setOverallRiskRating(riskRatingLikelihood.getWeight() * getEntity().getRiskRatingConsequence().getWeight());
    }

    public void populateRiskAreas(RiskAssessment riskAssessment) {
        riskAreas.clear();
        if (riskAssessment.getContraventionHistoryOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.CONTRAVENTION_HISTORY);
        }
        if (riskAssessment.getOriginCountryOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.COUNTRY_OF_ORIGIN);
        }
        if (riskAssessment.getDutyExemptionOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.DUTY_EXEMPTION_REGIMES);
        }
        if (riskAssessment.getDutySuspensionOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.DUTY_SUSPENSSION);
        }
        if (riskAssessment.getLicensingAndRegistrationOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.LICENSING_AND_REGISTRATION);
        }
        if (riskAssessment.getModeOfTransportOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.MODE_OF_TRANSPORT);
        }
        if (riskAssessment.getOtherPAndROption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.OTHER_P_AND_R);
        }
        if (riskAssessment.getRevenueProtectionOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.REVENUE_PROTECTION);
        }
        if (riskAssessment.getRulesOfOriginOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.RULES_OF_ORIGIN);
        }
        if (riskAssessment.getSecurityOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.SECURITY);
        }
        if (riskAssessment.getTarrifOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.TARRIF);
        }
        if (riskAssessment.getTradeRestrictionsOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.TRADE_RESTRICTIONS);
        }
        if (riskAssessment.getValuationOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.VALUATION);
        }
        if (riskAssessment.getWarehousingOption().equals(YesNoEnum.YES)) {
            riskAreas.add(RiskArea.WAREHOUSING);
        }
    }

    public void selectRiskAssessmentDetailFromDialog(RiskAssessmentDetails riskAssessmentDetails) {
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(riskAssessmentDetails);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
    }

    public void cancelRiskAssessmentDetailFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
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

}
