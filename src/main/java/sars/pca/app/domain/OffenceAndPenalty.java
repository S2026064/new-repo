package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "offence_and_penalty")
@Getter
@Setter
public class OffenceAndPenalty extends BaseEntity {

    @Column(name = "penalty_amount")
    private double subOffenceOrPenaltyAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "offence_classif_id", nullable = false)
    private OffenceClassification offenceClassification; //descript1

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "section_rule_id", nullable = false)
    private SectionRule sectionRule; //section rule 2

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "offence_descrip_id", unique = true, nullable = false)
    private OffenceDescription offenceDescription; //offence description 4

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = AuditReportOffencePenalty.class, orphanRemoval = true, mappedBy = "offencePenalty")
    private List<AuditReportOffencePenalty> auditReportOffencePenalties = new ArrayList<>();

    public OffenceAndPenalty() {
        this.subOffenceOrPenaltyAmount = 0.00;
    }

    public void addAuditReportOffence(AuditReportOffencePenalty auditReportOffence) {
        auditReportOffence.setOffencePenalty(this);
        auditReportOffencePenalties.add(auditReportOffence);
    }

    public boolean compareOffenceDescription(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OffenceAndPenalty other = (OffenceAndPenalty) obj;
        return Objects.equals(this.offenceDescription.getDescription(), other.offenceDescription.getDescription());
    }
}
