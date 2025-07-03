package sars.pca.app.service;

import java.util.Date;
import java.util.List;
import sars.pca.app.common.NotificationType;
import sars.pca.app.domain.User;

/**
 *
 * @author S2028398
 */
public interface EmailNotificationSenderServiceLocal {

    public boolean sendEmailNotification(NotificationType notificationType, String ReferenceNumber, Date date, User recipients, User provider);
    
    public boolean sendEmailNotificationCollection(NotificationType notificationType, String ReferenceNumber, double outstandingAmount, User recipients, User provider);

    public boolean sendEmailNotifications(NotificationType notificationType, String ReferenceNumber, Date date, List<User> recipients, User provider);

}
