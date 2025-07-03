package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "public_officer")
@Getter
@Setter
public class PublicOfficer extends Person {

    @Column(name = "company_status")
    private String companyStatus;

    @Column(name = "public_officer_at")
    private String publicOfficerAt;

    @Column(name = "linked_ref_nbr")
    private String linkedRefNBR;

    @Column(name = "linked_account_type")
    private String linkedAccountType;

    @Column(name = "linked_it_turnover")
    private String linkedItTurnover;

    @Column(name = "linked_it_ass_yr")
    private String linkedItAssYr;

    @Column(name = "linked_tot_outst_debt")
    private String linkedTotOutstDebt;

    @Column(name = "linked_tot_outst_returns")
    private String linkedTotOutstReturns;

    @Column(name = "po_name")
    private String poName;

    @Column(name = "po_id_number")
    private String poIdNumber;

    @Column(name = "po_tax_nbr")
    private String poTaxNbr;

    @Column(name = "po_phone_nbr")
    private String poPhoneNbr;

    @Column(name = "po_cell_nbr")
    private String poCellNbr;

    @Column(name = "import_date")
    private String importDate;
}
