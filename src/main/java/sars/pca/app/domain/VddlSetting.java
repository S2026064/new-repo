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
@Table(name = "vddl_sett")
@Getter
@Setter
public class VddlSetting extends BaseEntity {

    @Column(name = "create_vddl")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createVddl;

    @Column(name = "allocate_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean allocateAuditPlan;
    @Column(name = "create_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createAuditPlan;
    @Column(name = "review_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewAuditPlan;
    @Column(name = "review_rejected_audit_plan")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewRejectedAuditPlan;

    @Column(name = "create_audit_reporting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createAuditReporting;
    @Column(name = "review_audit_reporting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewAuditReporting;

    @Column(name = "create_debt_management")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createDebtManagement;
    @Column(name = "claimed_case")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean claimedCase;

    public VddlSetting() {
        this.createVddl = Boolean.FALSE;
        this.allocateAuditPlan = Boolean.FALSE;
        this.createAuditPlan = Boolean.FALSE;
        this.reviewAuditPlan = Boolean.FALSE;
        this.reviewRejectedAuditPlan = Boolean.FALSE;
        this.createAuditReporting = Boolean.FALSE;
        this.reviewAuditReporting = Boolean.FALSE;
        this.createDebtManagement = Boolean.FALSE;
        this.claimedCase = Boolean.FALSE;
    }

    public boolean isVddl() {
        return this.createVddl || this.allocateAuditPlan || this.createAuditPlan || this.reviewAuditPlan || this.reviewRejectedAuditPlan || this.createAuditReporting
                || this.reviewAuditReporting || this.createDebtManagement || this.claimedCase;
    }
}
