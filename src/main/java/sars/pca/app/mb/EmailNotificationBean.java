package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.NotificationType;
import sars.pca.app.domain.EmailNotification;
import sars.pca.app.service.EmailNotificationServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class EmailNotificationBean extends BaseBean<EmailNotification> {
    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;
    private List<NotificationType> notificationTypes;

    @PostConstruct
    public void init() {
        notificationTypes = new ArrayList<>();
        reset().setList(true);
        setPanelTitleName("Email Notifications");
        notificationTypes.addAll(Arrays.asList(NotificationType.values()));
        addCollections(emailNotificationService.listAll());        
    }
    public void addOrUpdate(EmailNotification emailNotification) {
        reset().setAdd(true);
        if (emailNotification != null) {
            setPanelTitleName("Update Email Notification");
            emailNotification.setUpdatedBy(getActiveUser().getSid());
            emailNotification.setUpdatedDate(new Date());
        } else {
            setPanelTitleName("Add Email Notification");
            emailNotification = new EmailNotification();
            emailNotification.setCreatedBy(getActiveUser().getSid());
            emailNotification.setCreatedDate(new Date());
            addCollection(emailNotification);
        }
        addEntity(emailNotification);
    }

    public void save(EmailNotification emailNotification) {
        if (emailNotification.getId() != null) {
            emailNotificationService.update(emailNotification);
            addInformationMessage("Email Notification was successfully updated.");
        } else {
            emailNotificationService.save(emailNotification);
            addInformationMessage("Email Notification successfully created.");
        }
        reset().setList(true);
        setPanelTitleName("Email Notifications");
    }
    public void cancel(EmailNotification emailNotification) {
        if (emailNotification.getId() == null && getEmailNotifications().contains(emailNotification)) {
            removeEntity(emailNotification);
        }
        reset().setList(true);
        setPanelTitleName("Email Notifications");
    }
    public void delete(EmailNotification emailNotification) {
        emailNotificationService.deleteById(emailNotification.getId());
        removeEntity(emailNotification);
        addInformationMessage("Email Notification was successfully deleted");
        reset().setList(true);
        setPanelTitleName("Email Notifications");
    }
    public List<EmailNotification> getEmailNotifications() {
        return this.getCollections();
    }
    public List<NotificationType> getNotificationTypes() {
        return notificationTypes;
    }
    public void setNotificationTypes(List<NotificationType> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }
}
