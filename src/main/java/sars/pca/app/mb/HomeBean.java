package sars.pca.app.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.service.SlaConfigurationServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@RequestScoped
public class HomeBean extends BaseBean {
    private static final String LANDING_PAGE = "/landing.xhtml";
    private static final String EXPIRY_PAGE = "/expired.xhtml?faces-redirect=true";
    @Autowired
    private SlaConfigurationServiceLocal slaConfigurationService;
      
    @PostConstruct
    public void init() {
        //SLA Configuration
        SlaConfiguration slaConfiguration = slaConfigurationService.listAll().iterator().next();
        if (slaConfiguration != null) {
            getActiveUser().setAlignmentOfComidityWeight(slaConfiguration.getAlignmentOfComidity());
            getActiveUser().setAuditPlanAutoApprove(slaConfiguration.getAuditPlanAutoApprove());
            getActiveUser().setAuditReportingAutoApprove(slaConfiguration.getAuditReportingAutoApprove());
            getActiveUser().setAutoDiscardSavedCasesSettings(slaConfiguration.getAutoDiscardSavedCasesSettings());
            getActiveUser().setCiGroupManagerApprovalAmount(slaConfiguration.getCiGroupManagerApprovalAmount());
            getActiveUser().setCiOpsManagerApprovalAmount(slaConfiguration.getCiOpsManagerApprovalAmount());
            getActiveUser().setCiSeniorManagerApprovalAmount(slaConfiguration.getCiSeniorManagerApprovalAmount());
            getActiveUser().setCrcsPrioritisationScoringWeight(slaConfiguration.getCrcsPrioritisationScoringWeight());
            getActiveUser().setCustomsValueScoringWeight(slaConfiguration.getCustomsValueScoringWeight());
            getActiveUser().setNumberOfRiskAreasIdentifiedScoringWeight(slaConfiguration.getNumberOfRiskAreasIdentifiedScoringWeight());
            getActiveUser().setOveralRiskRatingScoringWeight(slaConfiguration.getOveralRiskRatingScoringWeight());
            getActiveUser().setRaTechnicalReviewMinimum(slaConfiguration.getRaTechnicalReviewMinimum());
            getActiveUser().setRevenueAtRiskScoringWeight(slaConfiguration.getRevenueAtRiskScoringWeight());
            getActiveUser().setRiskAssessmentAutoApprove(slaConfiguration.getRiskAssessmentAutoApprove());
            getActiveUser().setSouthAfricanRepoRate(slaConfiguration.getSouthAfricanRepoRate());
            getActiveUser().setValueOfTransactionScoringWeight(slaConfiguration.getValueOfTransactionScoringWeight());
            getActiveUser().setNumberOfDaysForLod(slaConfiguration.getNumberOfDaysForLod());
            getActiveUser().setNumberOfDaysForLoi(slaConfiguration.getNumberOfDaysForLoi());
        }
    }

    public String routeToAdministration() {
        if (getActiveUser() != null) {
            getActiveUser().setModuleWelcomeMessage("Welcome to Administration Page");
            getActiveUser().getRouter().reset().setAdministrator(true);
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToVddlOrSarCases() {
        if (getActiveUser() != null) {
            getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
            getActiveUser().getRouter().reset().setVddlOrSarCases(true);
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToRiskAssessment() {
        if (getActiveUser() != null) {
            getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
            getActiveUser().getRouter().reset().setRiskAssessment(true);
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToAuditing() {
        if (getActiveUser() != null) {
            getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
            getActiveUser().getRouter().reset().setAuditing(true);
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToDebtManagement() {
        if (getActiveUser() != null) {
            getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
            getActiveUser().getRouter().reset().setDebtManagement(true);
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToReports() {
        if (getActiveUser() != null) {
            getActiveUser().setModuleWelcomeMessage("Welcome to Reports Page");
            getActiveUser().getRouter().reset().setReports(true);
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }
}
