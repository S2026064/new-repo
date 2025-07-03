package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.NotificationType;
import sars.pca.app.domain.EmailNotification;

/**
 *
 * @author S2026987
 */
public interface EmailNotificationServiceLocal {
    EmailNotification save(EmailNotification emailNotification);

    EmailNotification findById(Long id);

    EmailNotification update(EmailNotification emailNotification);

    EmailNotification deleteById(Long id);
    
    List<EmailNotification> listAll();
    
    boolean isExist(EmailNotification emailNotification);
    
   EmailNotification findByNotificationType(NotificationType notificationType);
}
