package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "total_liability")
@Getter
@Setter
public class TotalLiability extends BaseEntity {

    @Column(name = "total_fortieture")
    private double totalForfieture;

    @Column(name = "total_customs_duty")
    private double totalCustomsDuty;

    @Column(name = "total_excise_duty")
    private double totalExciseDuty;

    @Column(name = "total_duty_other")
    private double totalDutyOther;

    @Column(name = "customs_interest")
    private double totalCustomsInterest;

    @Column(name = "vat")
    private double totalVat;

    @Column(name = "vat_interest")
    private double totalVatInterest;

    @Column(name = "vat_penalty")
    private double totalVatPenalty;

    @Column(name = "total_penalty")
    private double totalPenalty;

    @Column(name = "liabilities_total")
    private double liabilitiesTotal;

    public TotalLiability() {
        this.totalForfieture = 0.00;
        this.totalCustomsDuty = 0.00;
        this.totalExciseDuty = 0.00;
        this.totalDutyOther = 0.00;
        this.totalCustomsInterest = 0.00;
        this.totalVat = 0.00;
        this.totalVatInterest = 0.00;
        this.totalVatPenalty = 0.00;
        this.totalPenalty = 0.00;
        this.liabilitiesTotal = 0.00;
    }

}
