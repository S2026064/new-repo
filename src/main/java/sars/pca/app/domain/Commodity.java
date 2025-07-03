package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "commodity")
@Getter
@Setter
public class Commodity extends BaseEntity {

    @Column(name = "tarrif_code")
    private String tarrifCode;

    @Column(name = "section_head")
    private String sectionHead;

    @Column(name = "lines")
    private String lines;

    @Column(name = "customs_value")
    private String customsValue;

    @Column(name = "duty")
    private String duty;

    @Column(name = "vat_due")
    private String vatDue;

    @Column(name = "commodity_type")
    private String commodityType;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "trade_type")
    private String tradeType;

    @Column(name = "purpose_code")
    private String purposeCode;

    @Column(name = "agent_code")
    private String agentCode;

    @Column(name = "custom_office_name")
    private String customOfficeName;

    @Column(name = "import_date")
    private String importDate;
}
