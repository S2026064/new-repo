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
@Table(name = "director_details")
@Getter
@Setter
public class DirectorDetails extends BaseEntity {

    @Column(name = "company_number")
    private String companyNumber;
    
    @Column(name = "surname")
    private String surname;
    
    @Column(name = "first_names")
    private String firstNames;
    
    @Column(name = "id_number")
    private String idNumber;
    
    @Column(name = "director_type")
    private String directorType;
    
    @Column(name = "director_status")
    private String directorStatus;
    
    @Column(name = "import_date")
    private String importDate;

}
