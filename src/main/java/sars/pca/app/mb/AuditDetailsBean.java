package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.AuditDetails;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.service.RiskAssessmentServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class AuditDetailsBean extends BaseBean<AuditDetails> {

    @Autowired
    private RiskAssessmentServiceLocal riskAssessmentCaseService;

    private List<RiskArea> riskAreas = new ArrayList<>();

    @PostConstruct
    public void init() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        AuditDetails auditDetails = (AuditDetails) sessionMap.get("auditDetailKey");
        sessionMap.remove("auditDetailKey");
        Long riskAssessmentId = (Long) sessionMap.get("riskAssessmentKey");
        sessionMap.remove("riskAssessmentKey");
        if (riskAssessmentId != null) {
            populateRiskAreas(riskAssessmentCaseService.findById(riskAssessmentId));
        }

        if (auditDetails != null) {
            auditDetails.setUpdatedBy(getActiveUser().getSid());
            auditDetails.setUpdatedDate(new Date());
        } else {
            auditDetails = new AuditDetails();
            auditDetails.setCreatedBy(getActiveUser().getSid());
            auditDetails.setCreatedDate(new Date());
        }
        addEntity(auditDetails);
    }

    public void selectAuditDetailFromDialog(AuditDetails auditDetails) {
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(auditDetails);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
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

    public void cancelAuditDetailFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public List<RiskArea> getRiskAreas() {
        return riskAreas;
    }

    public void setRiskAreas(List<RiskArea> riskAreas) {
        this.riskAreas = riskAreas;
    }
}
