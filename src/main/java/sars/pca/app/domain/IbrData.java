package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vongani
 *
 */
@Entity
@Table(name = "ibr_data")
@Getter
@Setter
public class IbrData extends BaseEntity {

    @Column(name = "customs_code")
    private String customsCode;

    @Column(name = "ibr_customs_number")
    private String ibrCustomsNumber;

    @Column(name = "registration_name")
    private String registrationName;

    @Column(name = "trading_name")
    private String tradingName;

    @Column(name = "nature_Of_bus_income_tax")
    private String natureOfBusinessIncomeTax;

    @Column(name = "nature_Of_bus_customs")
    private String natureOfBusinessCustoms;

    @Column(name = "postal_address")
    private String postalAddress;

    @Column(name = "physical_address")
    private String physicalAddress;

    @Column(name = "auditors_cipc")
    private String auditorsCipc;

    @Column(name = "attorneys")
    private String attorneys;

    @Column(name = "restricted_tax_payer")
    private String restrictedTaxPayer;

    @Column(name = "company_number")
    private String companyNumber;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "income_tax_number")
    private String incomeTaxNumber;

    @Column(name = "uif_number")
    private String uifNumber;

    @Column(name = "paye_number")
    private String payeNumber;

    @Column(name = "sdl_number")
    private String sdlNumber;

    @Column(name = "sap_fin_number")
    private String sapFinNumber;

    @Column(name = "bond_surety_amount")
    private String bondSuretyAmount;

    @Column(name = "linked_customs_codes")
    private String linkedCustomsCodes;

    @Column(name = "cl_nbr")
    private String clNbr;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "home_tel_number")
    private String homeTelephoneNumber;

    @Column(name = "work_tel_number")
    private String workTelephoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "web_address")
    private String webAddress;

    @OneToOne(mappedBy = "ibrData")
    private SuspiciousCase suspiciousCase;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ibr_data_id")
    private List<Address> addresses = new ArrayList();

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void removeAddreess(Address address) {
        this.addresses.remove(address);
    }
}
