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
@Table(name = "payment_and_refund")
@Getter
@Setter
public class PaymentAndRefund extends BaseEntity {

    @Column(name = "ref_number")
    private String refNumber;
    
    @Column(name = "account_type")
    private String accountType;
    
    @Column(name = "payment_refund_year")
    private String paymentRefundYear;
    
    @Column(name = "category")
    private String category;
   
    @Column(name = "payment")
    private String payment;
    
    @Column(name = "unallocated_payment")
    private String unallocatedPayment;
    
    @Column(name = "refund")
    private String refund;
    
    @Column(name = "import_date")
    private String importDate;  
}
