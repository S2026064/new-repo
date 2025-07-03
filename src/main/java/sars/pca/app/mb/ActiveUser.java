package sars.pca.app.mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Router;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class ActiveUser implements Serializable {

    private String sid;
    private boolean userLoginIndicator;
    private UserRole userRole;
    private String moduleWelcomeMessage;
    private String loggedOnUserFullName;
    private String fullName;
    private Router router = new Router();
    private String officeArea;
    private OfficeType officeType;
    private User user; //User to be replaced by required properties only
    private SlaConfiguration slaConfiguration;
    private Integer auditPlanAutoApprove;
    private Integer auditReportingAutoApprove;
    private Integer riskAssessmentAutoApprove;
    private Integer raTechnicalReviewMinimum;
    private Integer crcsPrioritisationScoringWeight;
    private Integer autoDiscardSavedCasesSettings;
    private Integer numberOfDaysForLod;
    private Integer numberOfDaysForLoi;
    private Double southAfricanRepoRate;
    private Double ciGroupManagerApprovalAmount;
    private Double ciOpsManagerApprovalAmount;
    private Double ciSeniorManagerApprovalAmount;
    private Integer alignmentOfComidityWeight;
    private Integer customsValueScoringWeight;
    private Integer numberOfRiskAreasIdentifiedScoringWeight;
    private Integer overalRiskRatingScoringWeight;
    private Integer revenueAtRiskScoringWeight;
    private Integer valueOfTransactionScoringWeight;

    public ActiveUser() {
        setUserLoginIndicator(Boolean.FALSE);
    }

    public void setLogonUserSession(User user) {
        if (user.getId() != null) {
            setUserRole(user.getUserRole());
            setLoggedOnUserFullName(user.getFullNames());
            setSid(user.getSid());
            setUserLoginIndicator(true);
            setOfficeArea(user.getLocationOffice().getArea());
            setOfficeType(user.getLocationOffice().getOfficeType());
            setUser(user);
        }
    }
}
