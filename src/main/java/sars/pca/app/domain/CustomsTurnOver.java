package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customs_turn_over")
@Getter
@Setter
public class CustomsTurnOver extends BaseEntity {
    @Column(name = "ref_number")
    private String refNumber;
    
    @Column(name = "cal_year")
    private String calYear;
    
    @Column(name = "import_value")
    private String importValue;
    
    @Column(name = "import_duty")
    private String importDuty;
    
    @Column(name = "import_vat")
    private String importVat;
    
    @Column(name = "export_line")
    private String exportLine;
    
    @Column(name = "export_value")
    private String exportValue;
    
    @Column(name = "import_line")
    private String importLine;
    
    @Column(name = "import_date")
    private String importDate;   
}
