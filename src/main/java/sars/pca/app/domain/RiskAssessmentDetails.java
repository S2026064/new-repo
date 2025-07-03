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
import sars.pca.app.common.YesNoEnum;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "risk_assessment_details")
@Getter
@Setter
public class RiskAssessmentDetails extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "overall_risk_rating")
    private Integer overallRiskRating;

    @Column(name = "revenue_at_risk")
    private Double revenueAtRisk;

    @Column(name = "risk_assessment_relate")
    @Enumerated(EnumType.STRING)
    private YesNoEnum riskAssessmentRelate;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = RiskRatingLikelihood.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_Rating_likelhood_id")
    private RiskRatingLikelihood riskRatingLikehood;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = RiskRatingConsequence.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_rating_consequence_id")
    private RiskRatingConsequence riskRatingConsequence;

    @Column(name = "risk_area")
    @Enumerated(EnumType.STRING)
    private RiskArea riskArea;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = RiskAssessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_assess_id")
    private RiskAssessment riskAssessment;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = HsChapterRisk.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "hs_chapter_risk_id")
    private HsChapterRisk hsChapterRisk;

    public RiskAssessmentDetails() {
        this.overallRiskRating = 0;
        this.revenueAtRisk = 0.00;
    }
}
