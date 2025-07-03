package sars.pca.app.mb;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sars.pca.app.common.UserRoleType;

/**
 *
 * @author S2026987
 */
@ManagedBean
@RequestScoped
public class MenuBean extends BaseBean {

    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());

    @PostConstruct
    public void init() {

    }

    public String route(String page) {
        return defaultRouter(page);
    }

    public String routeForReview() {
        if (getActiveUser().getUserRole().getDescription().equalsIgnoreCase(UserRoleType.CRCS_REVIEWER.toString())) {
            return "suspcasecrcsreview.xhtml";
        } else if (getActiveUser().getUserRole().getDescription().equalsIgnoreCase(UserRoleType.CRCS_QUALITY_ASSURER.toString())) {
            return "suspcaseqareview.xhtml";
        }
        return null;
    }

    public String routing(String page) {
        if (page.equalsIgnoreCase("/users")) {
            getActiveUser().getRouter().reset().setAdministrator(true);
        }
        return defaultRouter(page);
    }

    public String routeToAdmin(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome To Administration");
        getActiveUser().getRouter().reset().setAdministrator(true);
        return defaultRouter(page);
    }

    public String routeToVddlOrSarCases(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
        getActiveUser().getRouter().reset().setVddlOrSarCases(true);
        return defaultRouter(page);
    }

    public String routeToRiskManagement(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
        getActiveUser().getRouter().reset().setRiskAssessment(true);
        return defaultRouter(page);
    }

    public String routeToAuditing(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
        getActiveUser().getRouter().reset().setAuditing(true);
        return defaultRouter(page);
    }

    public String routeToDebtManagement(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome to PCA Management Page");
        getActiveUser().getRouter().reset().setDebtManagement(true);
        return defaultRouter(page);
    }

    public String routeToReports(String page) {
        getActiveUser().setModuleWelcomeMessage("Welcome To Reports");
        getActiveUser().getRouter().reset().setReports(true);
        return defaultRouter(page);
    }
}
