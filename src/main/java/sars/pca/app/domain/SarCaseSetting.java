package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "sar_case_sett")
@Getter
@Setter
public class SarCaseSetting extends BaseEntity {
    @Column(name = "create_sar_case")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createSarCase;
    @Column(name = "review_sar_case")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewSarCase;
    @Column(name = "allocate_sar_case")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean allocateSarCase;
    @Column(name = "reverse_re_assign_sar_case")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reversReAssignSarCase;
    @Column(name = "qa_review_sar_case")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean sarCaseQAReview;
    @Column(name = "rejected_case_techincal_review")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean rejectedCaseTechnicalReview;

    @Column(name = "indepth_analysis")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean indepthAnalysis;

    @Column(name = "create_risk_assessment")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createRiskAssessment;
    @Column(name = "review_risk_assessment")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewRiskAssessment;
    @Column(name = "risk_assessment_technical_review")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean riskAssessmentTechnicalReview;

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

    public SarCaseSetting() {
        this.createSarCase = Boolean.FALSE;
        this.reviewSarCase = Boolean.FALSE;
        this.allocateSarCase = Boolean.FALSE;
        this.reversReAssignSarCase = Boolean.FALSE;
        this.sarCaseQAReview = Boolean.FALSE;
        this.rejectedCaseTechnicalReview = Boolean.FALSE;

        this.indepthAnalysis = Boolean.FALSE;

        this.createRiskAssessment = Boolean.FALSE;
        this.reviewRiskAssessment = Boolean.FALSE;
        this.riskAssessmentTechnicalReview = Boolean.FALSE;

        this.allocateAuditPlan = Boolean.FALSE;
        this.createAuditPlan = Boolean.FALSE;
        this.reviewAuditPlan = Boolean.FALSE;
        this.reviewRejectedAuditPlan = Boolean.FALSE;

        this.createAuditReporting = Boolean.FALSE;
        this.reviewAuditReporting = Boolean.FALSE;

        this.createDebtManagement = Boolean.FALSE;
        this.claimedCase = Boolean.FALSE;

    }
    public boolean isSarCase() {
        return this.createSarCase || this.reviewSarCase || this.allocateSarCase || this.reversReAssignSarCase || this.sarCaseQAReview || this.rejectedCaseTechnicalReview || this.indepthAnalysis || this.createRiskAssessment
                || this.reviewRiskAssessment || this.riskAssessmentTechnicalReview || this.allocateAuditPlan || this.createAuditPlan || this.reviewAuditPlan
                || this.reviewRejectedAuditPlan || this.createAuditReporting || this.reviewAuditReporting || this.claimedCase || this.createDebtManagement;
    }
}
