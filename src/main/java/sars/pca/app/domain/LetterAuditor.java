package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "letter_auditor")
@Getter
@Setter
public class LetterAuditor extends BaseEntity {
    @Column(name = "auditor_name")
    private String auditorName;
}
