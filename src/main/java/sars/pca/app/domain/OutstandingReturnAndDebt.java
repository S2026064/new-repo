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
@Table(name = "outstanding_return_debt")
@Getter
@Setter
public class OutstandingReturnAndDebt extends BaseEntity {

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "outstanding_return_number")
    private String outstandingReturnNumber;

    @Column(name = "oldest")
    private String oldest;

    @Column(name = "outstanding_debt")
    private String outstandingDebt;

    @Column(name = "oldest_debt")
    private String oldestDebt;

    @Column(name = "cl_nbr")
    private String clNbr;

    @Column(name = "import_date")
    private String importDate;
}
