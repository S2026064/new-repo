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
@Table(name = "risk_mgmnt_sett")
@Getter
@Setter
public class RiskManagementSettting extends BaseEntity {

    @Column(name = "new_risk")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean newRisk;

    @Column(name = "review_risk")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewRisk;

    @Column(name = "rework_risk")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reworkRisk;

    @Column(name = "completed_risk")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean completedRisk;

    @Column(name = "indepth_analysis")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean indepthAnalysis;

    public RiskManagementSettting() {
        this.setNewRisk(false);
        this.setReviewRisk(false);
        this.setReworkRisk(false);
        this.setCompletedRisk(false);
        this.setIndepthAnalysis(false);
    }

    public boolean isRiskAssessment() {
        return this.newRisk || this.reviewRisk || this.completedRisk || this.reworkRisk || this.indepthAnalysis;
    }
}
