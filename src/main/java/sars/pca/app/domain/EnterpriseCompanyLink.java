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
@Table(name = "enterprise_company_link")
@Getter
@Setter
public class EnterpriseCompanyLink extends BaseEntity {

    @Column(name = "ref_number")
    private String refNumber;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "link_type")
    private String linkType;
    
    @Column(name = "link_name")
    private String linkName;
    
    @Column(name = "link_id")
    private String linkId;
    
    @Column(name = "share")
    private String share;
    
    @Column(name = "comp_cnt")
    private String compCnt;
    
    @Column(name = "listed")
    private String listed;
    
    @Column(name = "dir_cnt")
    private String dirCnt;
    
    @Column(name = "it_turn_over")
    private String itTurnOver;
    
    @Column(name = "it_ass_year")
    private String itAssYear;
    
    @Column(name = "tot_out_debt")
    private String totOutDebt;
    
    @Column(name = "tot_out_return")
    private String totOutReturn;
    
    @Column(name = "properties")
    private String properties;
    
    @Column(name = "vehicles")
    private String vehicles;
    
    @Column(name = "air_craft")
    private String airCraft;
    
    @Column(name = "link_cl_nbr")
    private String linkedClNumber;
    
    @Column(name = "dist_key")
    private String distKey;
    
    @Column(name = "restricted")
    private String restricted;
}
