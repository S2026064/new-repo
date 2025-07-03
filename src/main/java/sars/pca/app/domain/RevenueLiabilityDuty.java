package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "revenue_liability_duty")
@Getter
@Setter
public class RevenueLiabilityDuty extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "duty_id")
    private Duty duty;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "revenueLiability_id")
    private RevenueLiability revenueLiability;

    @Column(name = "amount")
    private double amount;

    @Column(name = "checked_item")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean checkedItem;

    public RevenueLiabilityDuty() {
        this.amount = 0.00;
        this.checkedItem = Boolean.FALSE;
    }
}
