package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "debt_management")
@Getter
@Setter
public class DebtManagement extends BaseEntity {

    @Column(name = "collected_amount")
    private double collectedAmount;

    @Column(name = "debt_proof_of_payment")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean debtProofOfPayment;

    public DebtManagement(boolean debtProofOfPayment) {
        this.debtProofOfPayment = debtProofOfPayment;
    }

    public DebtManagement() {
        this.collectedAmount = 0.00;
        this.debtProofOfPayment = Boolean.FALSE;
    }
}
