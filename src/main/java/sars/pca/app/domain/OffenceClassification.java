package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.CaseType;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "offence_classification")
@Getter
@Setter
public class OffenceClassification extends BaseEntity {
    @Column(name = "description", length = 1000, unique = true)
    private String description;

    @Column(name = "case_type", length = 1000)
    private CaseType classificationType;
}
