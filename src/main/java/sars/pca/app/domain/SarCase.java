package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.ClientType;
import sars.pca.app.common.CustomExcise;
import sars.pca.app.common.YesNoEnum;

//@NamedEntityGraph(
//        name = "sarcase-entity-graph",
//        attributeNodes = {
//            @NamedAttributeNode("vat"),
//            @NamedAttributeNode("companyNumber"),
//            @NamedAttributeNode("customExciseCode"),
//            @NamedAttributeNode("reference"),
//            @NamedAttributeNode("numberOfTravellerIndividual"),
//            @NamedAttributeNode("numberOfCompanyTraderImportExport"),
//            @NamedAttributeNode("clientType"),
//            @NamedAttributeNode("customExcise"),
//            @NamedAttributeNode("disclosePersonalDetails"),
//            @NamedAttributeNode("affidavit"),
//            @NamedAttributeNode("status"),
//            @NamedAttributeNode("pendEndDate")
//        }
//)
@Entity
@Table(name = "sar_case")
@Getter
@Setter
public class SarCase extends BaseEntity {

    @Column(name = "vat", length = 10)
    private String vat;

    @Column(name = "company_number", length = 14)
    private String companyNumber;

    @Column(name = "income_tax", length = 10)
    private String incomeTax;

    @Column(name = "custom_excise_code", length = 15)
    private String customExciseCode;

    @Column(name = "number_of_traveller_indiv")
    private Integer numberOfTravellerIndividual;

    @Column(name = "company_trader_import_export_num")
    private Integer numberOfCompanyTraderImportExport;

//    @Column(name = "priority_index")
//    private Integer priorityIndex;

    @Column(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(name = "custom_excise")
    @Enumerated(EnumType.STRING)
    private CustomExcise customExcise;

    @Column(name = "disclose_personal_details")
    @Enumerated(EnumType.STRING)
    private YesNoEnum disclosePersonalDetails;

    @Column(name = "affidavit")
    @Enumerated(EnumType.STRING)
    private YesNoEnum affidavit;

    @Column(name = "pend_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pendEndDate;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = RiskAssessment.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "risk_assessment_id")
    private RiskAssessment riskAssessment;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = NonComplianceDetails.class, orphanRemoval = true, mappedBy = "sarCase")
    private List<NonComplianceDetails> nonComplianceDetails = new ArrayList();

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = Address.class, orphanRemoval = true)
    @JoinColumn(name = "sarcase_id")
    private List<Address> addresses = new ArrayList();

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = ReportedPerson.class, orphanRemoval = true, mappedBy = "sarCase")
    private List<ReportedPerson> reportedPersons = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = EntityTypeDetails.class, orphanRemoval = true, mappedBy = "sarCase")
    private List<EntityTypeDetails> entityTypeDetails = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = ReportingPerson.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "reporting_person_id")
    private ReportingPerson reportingPerson;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = CasePrioritisationDetails.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "case_priorit_details_id")
    private CasePrioritisationDetails casePrioritisationDetails;

    public SarCase() {
        this.numberOfTravellerIndividual = 1;
        this.numberOfCompanyTraderImportExport = 1;
//        this.priorityIndex = 0;
        this.clientType = ClientType.COMPANY_TRADER_IMPORTER_EXPORTER;
        this.customExcise = CustomExcise.CUSTOM;
        this.disclosePersonalDetails = YesNoEnum.NO;
        this.affidavit = YesNoEnum.NO;
        this.pendEndDate = new Date();
    }

    public void addNonComplianceDetails(NonComplianceDetails nonComplianceDetail) {
        if (!nonComplianceDetails.contains(nonComplianceDetail)) {
            nonComplianceDetail.setSarCase(this);
            nonComplianceDetails.add(0, nonComplianceDetail);
        }
    }
    public void addNonComplianceDetails(List<NonComplianceDetails> nonComplianceDetailses) {
        for (NonComplianceDetails complianceDetails : nonComplianceDetailses) {
            if (!nonComplianceDetails.contains(complianceDetails)) {
                complianceDetails.setSarCase(this);
                nonComplianceDetails.add(0, complianceDetails);
            }
        }
    }
    public void removeNonComplianceDetails(NonComplianceDetails nonComplianceDetail) {
        nonComplianceDetail.setSarCase(null);
        nonComplianceDetails.remove(nonComplianceDetail);
    }

    public void addAddress(Address address) {
        addresses.add(0, address);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
    }

    public void addReportedPerson(ReportedPerson reportedPerson) {
        if (!reportedPersons.contains(reportedPerson)) {
            reportedPerson.setSarCase(this);
            reportedPersons.add(reportedPerson);
        }
    }

    public void removeReportedPerson(ReportedPerson reportedPerson) {
        reportedPersons.remove(reportedPerson);
        reportedPerson.setSarCase(null);
    }

    public void addEntityTypeDetails(EntityTypeDetails entityTypeDetail) {
        if (!entityTypeDetails.contains(entityTypeDetail)) {
            entityTypeDetail.setSarCase(this);
            entityTypeDetails.add(0, entityTypeDetail);
        }
    }

    public void removeEntityTypeDetails(EntityTypeDetails entityTypeDetail) {
        entityTypeDetails.remove(entityTypeDetail);
        entityTypeDetail.setSarCase(null);
    }
}
