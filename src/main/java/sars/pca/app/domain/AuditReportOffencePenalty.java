package sars.pca.app.domain;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "auditreport_offencepenalty")
@Getter
@Setter
public class AuditReportOffencePenalty extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "offence_penalty_id", nullable = true)
    private OffenceAndPenalty offencePenalty;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "audit_report_id", nullable = true)
    private AuditReport auditReport;

    public AuditReportOffencePenalty(OffenceAndPenalty offenceAndPenalty, AuditReport auditReport) {
        this.offencePenalty = offenceAndPenalty;
        this.auditReport = auditReport;
    }

    public AuditReportOffencePenalty() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.offencePenalty);
        hash = 73 * hash + Objects.hashCode(this.auditReport);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuditReportOffencePenalty other = (AuditReportOffencePenalty) obj;
        if (!Objects.equals(this.offencePenalty, other.offencePenalty)) {
            return false;
        }
        return Objects.equals(this.auditReport, other.auditReport);
    }
    
    
}
