package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "duty_vat")
@Getter
@Setter
public class DutyVat extends BaseEntity {

    @Column(name = "declaration_line_number")
    private String declarationLineNumber;

    @Column(name = "line_number")
    private String lineNumber;

    @Column(name = "type_of_duty")
    private String typeOfDuty;

    @Column(name = "amount")
    private double amount;

    @Column(name = "sub_amount")
    private double subAmount;
}
