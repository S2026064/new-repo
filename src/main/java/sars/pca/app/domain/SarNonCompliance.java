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
@Table(name = "sar_non_compliance")
@Getter
@Setter
public class SarNonCompliance extends BaseEntity {

    @Column(name = "incident_id")
    private String incidentId;
    
    @Column(name = "incident")
    private String incident;
    
    @Column(name = "incident_desc",length = 4000)
    private String incidentDescription;
    
    @Column(name = "tax_type")
    private String taxType;
    
    @Column(name = "incident_value",length = 4000)
    private String incidentValue;
    
    @Column(name = "oldest_debt")
    private String oldestDebt;
    
    @Column(name = "incident_date")
    private String incidentDate;
    
    @Column(name = "other_info",length = 4000)
    private String otherInfo;
    
    @Column(name = "cl_nbr")
    private String clNbr;
    
    @Column(name = "import_date")
    private String importDate;
}
