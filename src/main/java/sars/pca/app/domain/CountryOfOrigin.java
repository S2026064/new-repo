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
@Table(name = "country_of_origin")
@Getter
@Setter
public class CountryOfOrigin extends BaseEntity {

    @Column(name = "country_name")
    private String countryName;
    
    @Column(name = "lines")
    private String lines;
    
    @Column(name = "customers_value")
    private String customsValue;
    
    @Column(name = "duty")
    private String duty;
    
    @Column(name = "vat_due")
    private String vatDue;
    
    @Column(name = "import_date")
    private String ImportDate;
}
