package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "vddl_type")
@Getter
@Setter
public class VddLedType extends BaseEntity {

    @Column(name = "cancellations")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean cancellations;

    @Column(name = "default_on_defement")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean defaultOnDefement;

    @Column(name = "rule_of_origin")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean ruleOfOrigin;

    @Column(name = "refunds")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean refunds;

    @Column(name = "app_substitution_overtime_goods")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean appSubstitutionOvertimeGoods;

    @Column(name = "other_app_sureties")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean otherAppSureties;

    @Column(name = "stocklist_account_da490")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean stocklistAccountDa490;

    @Column(name = "app_duty_account")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean appDutyAccount;

    @Column(name = "vdn_tdn_app")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean vdnTdnApp;

    @Column(name = "apdp_account")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean apdpAccount;

    @Column(name = "liquidations")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean liquidations;

    @Column(name = "warehouse_approvals")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean warehouseApprovals;

    @Column(name = "rebate_approvals")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean rebateApprovals;

    @Column(name = "approved_exporter_verif")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean approvedExporterVerifications;

    @Column(name = "maa_verif_request")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean maaVerificationsRequest;

    @Column(name = "tiu_case_hand_over")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean tiuCaseHandOver;

    @Column(name = "branch_op_hand_over")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean branchOpHandOver;

    @Column(name = "head_office_case_referral")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean headOfficeCaseReferral;

    @Column(name = "sars_service_manager_case_referral")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean sarsServiceManagerCaseReferral;

    @Column(name = "acquittals")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean acquittals;

    @Column(name = "other")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean other;

    @Column(name = "other_des")
    private String otherDescription;

    public VddLedType() {
        this.cancellations = Boolean.FALSE;
        this.defaultOnDefement = Boolean.FALSE;
        this.ruleOfOrigin = Boolean.FALSE;
        this.refunds = Boolean.FALSE;
        this.appSubstitutionOvertimeGoods = Boolean.FALSE;
        this.otherAppSureties = Boolean.FALSE;
        this.stocklistAccountDa490 = Boolean.FALSE;
        this.appDutyAccount = Boolean.FALSE;
        this.vdnTdnApp = Boolean.FALSE;
        this.apdpAccount = Boolean.FALSE;
        this.liquidations = Boolean.FALSE;
        this.warehouseApprovals = Boolean.FALSE;
        this.rebateApprovals = Boolean.FALSE;
        this.approvedExporterVerifications = Boolean.FALSE;
        this.maaVerificationsRequest = Boolean.FALSE;
        this.tiuCaseHandOver = Boolean.FALSE;
        this.branchOpHandOver = Boolean.FALSE;
        this.headOfficeCaseReferral = Boolean.FALSE;
        this.sarsServiceManagerCaseReferral = Boolean.FALSE;
        this.acquittals = Boolean.FALSE;
        this.other = Boolean.FALSE;
    }

}
