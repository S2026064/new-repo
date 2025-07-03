package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "section_rule")
@Getter
@Setter
public class SectionRule extends BaseEntity {
    @Column(name = "description", length = 1000, unique = true)
    private String description;
    
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true, targetEntity = OffenceClassification.class)
    @JoinColumn(name = "offence_classif_id")
    private OffenceClassification offenceClassification;
}
