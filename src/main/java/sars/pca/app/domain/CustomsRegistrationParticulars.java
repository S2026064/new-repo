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
@Table(name = "customs_reg_particulars")
@Getter
@Setter
public class CustomsRegistrationParticulars extends BaseEntity {
    @Column(name = "address")
    private String address;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "activation_date")
    private String activationDate; 
    
    @Column(name = "registration_type")
    private String registrationType;
    
    @Column(name = "import_date")
    private String importDate;
}
