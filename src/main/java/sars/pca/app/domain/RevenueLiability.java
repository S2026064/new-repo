package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026064
 */
@Entity
@Table(name = "revenue_liability")
@Getter
@Setter
public class RevenueLiability extends BaseEntity {

    @Column(name = "forfieture")
    private double forfieture;

    @Column(name = "customs_interest")
    private double customsInterest;

    @Column(name = "vat")
    private double vat;

    @Column(name = "vat_interest")
    private double vatInterest;

    @Column(name = "vat_penalty")
    private double vatPenalty;

    @Column(name = "total_sub_liablity")
    private double totalSubLiability;

    @OneToMany(mappedBy = "revenueLiability", cascade = {CascadeType.ALL}, targetEntity = RevenueLiabilityDuty.class)
    private List<RevenueLiabilityDuty> revenueLiabilityDutys = new ArrayList<>();

    public RevenueLiability() {
        this.forfieture = 0.00;
        this.customsInterest = 0.00;
        this.vat = 0.00;
        this.vatInterest = 0.00;
        this.vatPenalty = 0.00;
        this.totalSubLiability = 0.00;
    }

    public void addRevenueLiabilityDuty(RevenueLiabilityDuty revenueLiabilityDuty) {
        revenueLiabilityDuty.setRevenueLiability(this);
        revenueLiabilityDutys.add(revenueLiabilityDuty);
    }

    public void removeRevenueLiabilityDuty(RevenueLiabilityDuty revenueLiabilityDuty) {
        revenueLiabilityDuty.setRevenueLiability(null);
        revenueLiabilityDutys.remove(revenueLiabilityDuty);
    }
}
