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
@Table(name = "customs_value")
@Getter
@Setter
public class CustomsValue extends BaseEntity {

    @Column(name = "purpose_code")
    private String purposeCode;
    
    @Column(name = "lines")
    private String lines;
    
    @Column(name = "customs_value")
    private String customsValue;
    
    @Column(name = "duty")
    private String duty;
    
    @Column(name = "vat_due")
    private String vatDue;
    
    @Column(name = "agent_code")
    private String agentCode;
    
    @Column(name = "clearing_agent")
    private String clearingAgent;
    
    @Column(name = "import_date")
    private String importDate;
    
    @Column(name = "custom_office_name")
    private String customOfficeName;

    @Column(name = "customs_value_type")
    private String customsValueType;
}
