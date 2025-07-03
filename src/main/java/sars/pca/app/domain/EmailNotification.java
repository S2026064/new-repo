package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.NotificationType;

@Entity
@Table(name = "email_notification")
@Getter
@Setter
public class EmailNotification extends BaseEntity {

    
    @Column(name = "line_1",length = 1000)
    private String line1;
    
    @Column(name = "line_2",length = 1000)
    private String line2;
    
    @Column(name = "line_3",length = 1000)
    private String line3;
    
    @Column(name = "line_4",length = 1000)
    private String line4;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

}
