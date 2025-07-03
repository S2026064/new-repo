package sars.pca.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sars.pca.app.common.DateUtil;
import sars.pca.app.common.NotificationType;
import sars.pca.app.domain.EmailNotification;
import sars.pca.app.domain.User;

/**
 *
 * @author S2028398
 */
@Service
public class EmailNotificationSenderService implements EmailNotificationSenderServiceLocal {

    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;

    private MailService mailService = new MailService();
    private LDAPService lDAPService = new LDAPService();

    private EmailNotification emailNotification;

    private List<String> emailNotificationRecipients = new ArrayList<>();

    @Override
    public boolean sendEmailNotification(NotificationType notificationType, String ReferenceNumber, Date date, User recipient, User provider) {
        emailNotification = emailNotificationService.findByNotificationType(notificationType);
        if (emailNotification != null) {
            boolean isMailSent = false;
            emailNotificationRecipients.clear();
//                emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
            emailNotificationRecipients.add(lDAPService.getUserEmailAddress("s2028398"));
            StringBuilder builder = new StringBuilder();
            builder.append("Good Day");
            builder.append(" ");
            builder.append(recipient.getFullNames());
            builder.append("<br /><br />");
            builder.append(emailNotification.getLine1());
            builder.append(" ");
            builder.append(ReferenceNumber);
            builder.append(" ");
            builder.append(emailNotification.getLine2());
            builder.append(" ");
            builder.append(DateUtil.convertStringToDate(date));
            builder.append(" ");
            builder.append(emailNotification.getLine3());
            if (provider != null) {
                builder.append(" ");
                builder.append(provider.getFullNames());
            }
            builder.append(" ");
            builder.append(emailNotification.getLine4());
            builder.append("<br /><br />");
            builder.append("Kind regards");
            builder.append("<br />");
            builder.append("Customs Excise Compliance Risk Case Selection & Customs Investigation");
            builder.append("<br /><br />");
            builder.append("NB: This is an automated email, do not reply.");

            if (mailService.send(emailNotificationRecipients, "Customs Excise Compliance Risk Case Selection & Customs Investigation Notifications", builder.toString())) {
                return true;
            }
        }
        return false;
    }
//closing of a case on debt management
    @Override
    public boolean sendEmailNotificationCollection(NotificationType notificationType, String ReferenceNumber, double outstandingAmount, User recipient, User provider) {
        emailNotification = emailNotificationService.findByNotificationType(notificationType);
        if (emailNotification != null) {
            boolean isMailSent = false;
            emailNotificationRecipients.clear();
//                emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
            emailNotificationRecipients.add(lDAPService.getUserEmailAddress("s2028398"));
            StringBuilder builder = new StringBuilder();
            builder.append("Good Day");
            builder.append(" ");
            builder.append(recipient.getFullNames());
            builder.append("<br /><br />");
            builder.append(emailNotification.getLine1());
            builder.append(" ");
            builder.append(outstandingAmount);
            builder.append(" ");
            builder.append(emailNotification.getLine2());
            builder.append(" ");
            builder.append(ReferenceNumber);
            builder.append(" ");
            builder.append(emailNotification.getLine3());
            builder.append("<br /><br />");
            builder.append("Kind regards");
            builder.append("<br />");
            builder.append("Customs Excise Compliance Risk Case Selection & Customs Investigation");
            builder.append("<br /><br />");
            builder.append("NB: This is an automated email, do not reply.");

            if (mailService.send(emailNotificationRecipients, "Customs Excise Compliance Risk Case Selection & Customs Investigation Notifications", builder.toString())) {
                return true;
            }
        }
        return false;
    }
    
//send email to list of users
    @Override
    public boolean sendEmailNotifications(NotificationType notificationType, String ReferenceNumber, Date date, List<User> recipients, User provider) {
        emailNotification = emailNotificationService.findByNotificationType(notificationType);
        if (emailNotification != null) {
            boolean isMailSent = false;
            for (User recipient : recipients) {
                emailNotificationRecipients.clear();
//                emailNotificationRecipients.add(lDAPService.getUserEmailAddress(recipient.getSid()));
                emailNotificationRecipients.add(lDAPService.getUserEmailAddress("s2026064")); 
                StringBuilder builder = new StringBuilder();
                builder.append("Good Day");
                builder.append(" ");
                builder.append(recipient.getFullNames());
                builder.append("<br /><br />");
                builder.append(emailNotification.getLine1());
                builder.append(" ");
                builder.append(ReferenceNumber);
                builder.append(" ");
                builder.append(emailNotification.getLine2());
                builder.append(" ");
                builder.append(DateUtil.convertStringToDate(date));
                builder.append(" ");
                builder.append(emailNotification.getLine3());
                if (provider != null) {
                    builder.append(" ");
                    builder.append(provider.getFullNames());
                }
                builder.append(" ");
                builder.append(emailNotification.getLine4());
                builder.append("<br /><br />");
                builder.append("Kind regards");
                builder.append("<br />");
                builder.append("Customs Excise Compliance Risk Case Selection & Customs Investigation");
                builder.append("<br /><br />");
                builder.append("NB: This is an automated email, do not reply.");

                if (mailService.send(emailNotificationRecipients, "Customs Excise Compliance Risk Case Selection & Customs Investigation Notifications", builder.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

}
