package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026987
 */
@Entity
@Table(name = "entity_type_details")
@Getter
@Setter
public class EntityTypeDetails extends BaseEntity {

    @Column(name = "registered_name")
    private String registeredName;

    @Column(name = "trading_name")
    private String tradingName;

    @Column(name = "company_number")
    private String companyNumber;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = SarCase.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sarcase_id")
    private SarCase sarCase;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = Address.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "address_id")
    private Address businessAddress;
}
