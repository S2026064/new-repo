package sars.pca.app.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

//@NamedEntityGraph(
//        name = "vddled-entity-graph",
//        attributeNodes = {
//            @NamedAttributeNode("caseRefNumber"),
//            @NamedAttributeNode("instruction"),
//            @NamedAttributeNode("vddlStatus"),
//            @NamedAttributeNode("pendEndDate")
//        }
//)
@Entity
@Table(name = "vddl_case")
@Getter
@Setter
public class VddLedCase extends BaseEntity {

    @Column(name = "instruction", length = 4000)
    private String instruction;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = VddLedType.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "vddl_type_id")
    private VddLedType vddlType;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = CustomsExciseClientType.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "cust_exc_client_type_id")
    private CustomsExciseClientType customsExciseClientType;

    @Column(name = "pend_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pendEndDate;
    
}
