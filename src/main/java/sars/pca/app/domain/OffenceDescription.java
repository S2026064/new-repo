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
@Table(name = "offence_descrip")
@Getter
@Setter
public class OffenceDescription extends BaseEntity {

    @Column(name = "description", length = 1000, unique = true)
    private String description;

    @Column(name = "deposit")
    private double deposit;

    @Column(name = "notes")
    private String notes;

    @Column(name = "section_amount")
    private double sectionAmount;

    @Column(name = "notes_amount")
    private String notesAmount;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true, targetEntity = SectionRule.class)
    @JoinColumn(name = "section_rule_id")
    private SectionRule sectionRule;

    public OffenceDescription() {
        this.deposit = 0.00;
        this.sectionAmount = 0.00;
    }
}
