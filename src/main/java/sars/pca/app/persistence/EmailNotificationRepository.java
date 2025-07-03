package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.NotificationType;
import sars.pca.app.domain.EmailNotification;

/**
 *
 * @author S2026987
 */
@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
    EmailNotification findByNotificationType(NotificationType notificationType);
}
