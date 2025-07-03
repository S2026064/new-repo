package sars.pca.app.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author S2024726
 */
@WebListener
public class PcaServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "false");
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_LOCALISATION", "false");
        servletContext.setInitParameter("primefaces.COOKIES_SAME_SITE", "strict");

        servletContext.setInitParameter("primefaces.CSP", "false");
        servletContext.setInitParameter("primefaces.CSP_POLICY", "null");
        servletContext.setInitParameter("primefaces.CSP_REPORT_ONLY_POLICY", "null");

        servletContext.setInitParameter("primefaces.DIR", "ltr");
        servletContext.setInitParameter("primefaces.EARLY_POST_PARAM_EVALUATION", "false");
        servletContext.setInitParameter("primefaces.EXCEPTION_TYPES_TO_IGNORE_IN_LOGGING", "null");
        //servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        servletContext.setInitParameter("primefaces.FLEX", "false");
        
        servletContext.setInitParameter("primefaces.HIDE_RESOURCE_VERSION", "false");
        servletContext.setInitParameter("primefaces.HTML5_COMPLIANCE", "false");
        servletContext.setInitParameter("primefaces.INTERPOLATE_CLIENT_SIDE_VALIDATION_MESSAGES", "false");
        servletContext.setInitParameter("primefaces.LEGACY_WIDGET_NAMESPACE", "false");

        servletContext.setInitParameter("primefaces.MARK_INPUT_AS_INVALID_ON_ERROR_MSG", "false");
        servletContext.setInitParameter("primefaces.MOVE_SCRIPTS_TO_BOTTOM", "false");
        servletContext.setInitParameter("primefaces.MULTI_VIEW_STATE_STORE", "session");

        servletContext.setInitParameter("primefaces.PRIME_ICONS", "true");
        
        servletContext.setInitParameter("primefaces.RESET_VALUES", "false");
        servletContext.setInitParameter("primefaces.SUBMIT", "full");
        servletContext.setInitParameter("primefaces.THEME", "nova-light");
       
        servletContext.setInitParameter("primefaces.TOUCHABLE", "true");
        servletContext.setInitParameter("primefaces.TRANSFORM_METADATA", "false");

        servletContext.setInitParameter("primefaces.UPLOADER", "auto");

        servletContext.setInitParameter("net.bootsfaces.blockUI", "true");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
        servletContext.setInitParameter("com.sun.faces.numberOfViewsInSession", "3");
        servletContext.setInitParameter("com.sun.faces.numberOfLogicalViews", "10");
        servletContext.setInitParameter("BootsFaces_THEME", "default");
        servletContext.setInitParameter("BootsFaces_USETHEME", "cerulean");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
