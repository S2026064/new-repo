package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sla_config")
@Getter
@Setter
public class SlaConfiguration extends BaseEntity {
    @Column(name = "audit_plan_auto_approve")
    private Integer auditPlanAutoApprove;
    
    @Column(name = "audit_reporting_auto_approve")
    private Integer auditReportingAutoApprove;
    
    @Column(name = "risk_assessment_auto_approve")
    private Integer riskAssessmentAutoApprove;
    
    @Column(name = "ra_technical_review_minimum")
    private Integer raTechnicalReviewMinimum;
    
    @Column(name = "crcs_prioritisation_scoring_weight")
    private Integer crcsPrioritisationScoringWeight;
    
    @Column(name = "auto_discard_saved_cases_settings")
    private Integer autoDiscardSavedCasesSettings;
    
    @Column(name = "number_of_days_for_lod")
    private Integer numberOfDaysForLod;
    
    @Column(name = "number_of_days_for_loi")
    private Integer numberOfDaysForLoi;
    
    @Column(name = "south_african_repo_rate")
    private Double southAfricanRepoRate;
    
    @Column(name = "ci_group_manager_approval_amount")
    private Double ciGroupManagerApprovalAmount;
    
    @Column(name = "ci_ops_manager_approval_amount")
    private Double ciOpsManagerApprovalAmount;
    
    @Column(name = "ci_senior_manager_approval_amount")
    private Double ciSeniorManagerApprovalAmount;
    
    @Column(name = "alignmentOfComidity")
    private Integer alignmentOfComidity;
    
    @Column(name = "customs_value_scoring_weight")
    private Integer customsValueScoringWeight;
    
    @Column(name = "number_of_risk_areas_identified_scoring_weight")
    private Integer numberOfRiskAreasIdentifiedScoringWeight;
    
    @Column(name = "overal_risk_rating_identified_scoring_weight")
    private Integer overalRiskRatingScoringWeight;
    
    @Column(name = "revenue_at_risk_scoring_weight")
    private Integer revenueAtRiskScoringWeight;
    
    @Column(name = "value_of_transaction_scoring_weight")
    private Integer valueOfTransactionScoringWeight;

    public SlaConfiguration(Integer auditPlanAutoApprove, Integer auditReportingAutoApprove, Integer riskAssessmentAutoApprove, Integer raTechnicalReviewMinimum, Integer crcsPrioritisationScoringWeight, Integer autoDiscardSavedCasesSettings, Integer numberOfDaysForLod, Integer numberOfDaysForLoi, Double southAfricanRepoRate, Double ciGroupManagerApprovalAmount, Double ciOpsManagerApprovalAmount, Double ciSeniorManagerApprovalAmount, Integer alignmentOfComidity, Integer customsValueScoringWeight, Integer numberOfRiskAreasIdentifiedScoringWeight, Integer overalRiskRatingScoringWeight, Integer revenueAtRiskScoringWeight, Integer valueOfTransactionScoringWeight) {
        this.auditPlanAutoApprove = auditPlanAutoApprove;
        this.auditReportingAutoApprove = auditReportingAutoApprove;
        this.riskAssessmentAutoApprove = riskAssessmentAutoApprove;
        this.raTechnicalReviewMinimum = raTechnicalReviewMinimum;
        this.crcsPrioritisationScoringWeight = crcsPrioritisationScoringWeight;
        this.autoDiscardSavedCasesSettings = autoDiscardSavedCasesSettings;
        this.numberOfDaysForLod = numberOfDaysForLod;
        this.numberOfDaysForLoi = numberOfDaysForLoi;
        this.southAfricanRepoRate = southAfricanRepoRate;
        this.ciGroupManagerApprovalAmount = ciGroupManagerApprovalAmount;
        this.ciOpsManagerApprovalAmount = ciOpsManagerApprovalAmount;
        this.ciSeniorManagerApprovalAmount = ciSeniorManagerApprovalAmount;
        this.alignmentOfComidity = alignmentOfComidity;
        this.customsValueScoringWeight = customsValueScoringWeight;
        this.numberOfRiskAreasIdentifiedScoringWeight = numberOfRiskAreasIdentifiedScoringWeight;
        this.overalRiskRatingScoringWeight = overalRiskRatingScoringWeight;
        this.revenueAtRiskScoringWeight = revenueAtRiskScoringWeight;
        this.valueOfTransactionScoringWeight = valueOfTransactionScoringWeight;
    }
    public SlaConfiguration() {
    }   
}
