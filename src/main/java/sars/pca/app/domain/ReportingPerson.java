package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026987
 */
@Entity
@Table(name = "reporting_person")
@Getter
@Setter
public class ReportingPerson extends Person {

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = Address.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "address_id")
    private Address residentialAddress;
}
