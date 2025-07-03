package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.RiskArea;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "audit_plan_details")
@Getter
@Setter
public class AuditDetails extends BaseEntity {
    @Column(name = "audit_scope")
    private String scope;

    @Column(name = "audit_object")
    private String objective;

    @Column(name = "risk_area")
    @Enumerated(EnumType.STRING)
    private RiskArea riskArea;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = AuditPlan.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_plan_id")
    private AuditPlan auditPlan;

    public AuditDetails() {
        this.riskArea = RiskArea.CONTRAVENTION_HISTORY;
    }    
}
