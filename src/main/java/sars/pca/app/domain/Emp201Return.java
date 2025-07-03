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
@Table(name = "emp_201_return")
@Getter
@Setter
public class Emp201Return extends BaseEntity {
    @Column(name = "paye_number")
    private String payeNumber;
    
    @Column(name = "emp201_period")
    private String period;
    
    @Column(name = "amount_paye")
    private String amountPaye;
    
    @Column(name = "amount_sdl")
    private String amountSdl;
    
    @Column(name = "amount_uif")
    private String amountUif;
    
    @Column(name = "total")
    private String total;
    
    @Column(name = "date_declared")
    private String dateDeclared;
    
    @Column(name = "interest")
    private String interest;
    
    @Column(name = "penalty")
    private String penalty;
    
    @Column(name = "prev_total_payment")
    private String prevTotalPayment;
    
    @Column(name = "date_recieved")
    private String dateRecieved;
    
    @Column(name = "cl_nbr")
    private String clNbr;
    
    @Column(name = "import_date")
    private String importDate;
}
