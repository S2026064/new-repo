package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2026987
 */
@Entity
@Table(name = "audit_sett")
@Getter
@Setter
public class AuditingSettting extends BaseEntity {

    @Column(name = "create_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createAuditPlan;

    @Column(name = "allocate_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean allocateAuditPlan;

    @Column(name = "review_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewAuditPlan;

    @Column(name = "create_audit_report")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createAuditReport;

    @Column(name = "review_audit_report")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewAuditReport;

    @Column(name = "reassign_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reAssignAuditPlan;

    public AuditingSettting() {
        this.setAllocateAuditPlan(false);
        this.setCreateAuditPlan(false);
        this.setCreateAuditReport(false);
        this.setReviewAuditPlan(false);
        this.setReviewAuditReport(false);
        this.setReAssignAuditPlan(reAssignAuditPlan);
    }
    public boolean isAuditing() {
        return this.allocateAuditPlan || this.createAuditPlan || this.createAuditReport || this.reviewAuditPlan || this.reviewAuditReport || reAssignAuditPlan;
    }
}
