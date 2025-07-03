package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sars.pca.app.common.NotificationType;
import sars.pca.app.domain.EmailNotification;
import sars.pca.app.persistence.EmailNotificationRepository;

/**
 *
 * @author S2028398
 */
@Service
public class EmailNotificationService implements EmailNotificationServiceLocal {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Override
    public EmailNotification save(EmailNotification emailNotification) {
        return emailNotificationRepository.save(emailNotification);
    }

    @Override
    public EmailNotification findById(Long id) {
        return emailNotificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public EmailNotification update(EmailNotification emailNotification) {
        return emailNotificationRepository.save(emailNotification);
    }

    @Override
    public EmailNotification deleteById(Long id) {
        EmailNotification emailNotification = findById(id);

        if (emailNotification != null) {
            emailNotificationRepository.delete(emailNotification);
        }
        return emailNotification;
    }

    @Override
    public List<EmailNotification> listAll() {
        return emailNotificationRepository.findAll();
    }

    @Override
    public boolean isExist(EmailNotification emailNotification) {
        return emailNotificationRepository.findById(emailNotification.getId()) != null;
    }

    @Override
    public EmailNotification findByNotificationType(NotificationType notificationType) {
        return emailNotificationRepository.findByNotificationType(notificationType); //To change body of generated methods, choose Tools | Templates.
    }

}
