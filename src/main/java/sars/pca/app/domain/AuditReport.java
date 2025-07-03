package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.hibernate.annotations.Type;
import sars.pca.app.common.LOIExtentionReason;
import sars.pca.app.common.PaymentType;
import sars.pca.app.common.YesNoEnum;

@Entity
@Table(name = "audit_report")
@Getter
@Setter
public class AuditReport extends BaseEntity {

    @Column(name = "findings_summary")
    private String findingsSummary;

    @Column(name = "sub_penalty_total")
    private double subPenaltyTotal;

    @Column(name = "loi_Issued")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean loiIssued;

    @Column(name = "loi_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loiDate;

    @Column(name = "extend_due_date")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean extendDueDate;

    @Column(name = "extend_reason")
    private String extendReason;

    @Column(name = "due_date_extend")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDateExtend;

    @Column(name = "lod_issued")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean lodIssued;

    @Column(name = "lod_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lodDate;

    @Column(name = "auditor_proof_of_payment")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean auditorProofOfPayment;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "outstanding_amount")
    private double outstandingAmount;

    @Column(name = "payed_amount")
    private double payedAmount;

    @Column(name = "loi_extension_reason")
    @Enumerated(EnumType.STRING)
    private LOIExtentionReason loiExtentionReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "was_offence_detected")
    private YesNoEnum wasOffenceDetected;

    @Column(name = "there_revenue_liability")
    @Enumerated(EnumType.STRING)
    private YesNoEnum thereRevenueLiability;

    @Column(name = "additional_risk_identified")
    @Enumerated(EnumType.STRING)
    private YesNoEnum additionalRiskIdentified;

    @Column(name = "total_rand_value_per_section_93")
    private double totalRandValuePerSection93;

    @Column(name = "was_goods_seizure_and_detained")
    @Enumerated(EnumType.STRING)
    private YesNoEnum seizureAndDetained;

    @Column(name = "was_there_lien_on_goods")
    @Enumerated(EnumType.STRING)
    private YesNoEnum wasLienOnGood;

    @Column(name = "did_client_request_goods_back")
    @Enumerated(EnumType.STRING)
    private YesNoEnum clientRequestGoodsBack;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = RevenueLiability.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "rev_liability_id")
    private RevenueLiability revenueLiability;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = DebtManagement.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "debt_management_id")
    private DebtManagement debtManagement;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = AuditReportOffencePenalty.class, orphanRemoval = true, mappedBy = "auditReport")
    private List<AuditReportOffencePenalty> auditReportOffencePenalties = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = AdditionalRisk.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "additional_risk_id")
    private AdditionalRisk additionalRisk;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = TotalLiability.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "total_Liab_id")
    private TotalLiability totalLiability;

    public AuditReport() {
        this.subPenaltyTotal = 0.00;
        this.loiIssued = Boolean.FALSE;
        this.loiDate = new Date();
        this.dueDateExtend = new Date();
        this.lodDate = new Date();
        this.outstandingAmount = 0.00;
        this.payedAmount = 0.00;
        this.wasOffenceDetected = YesNoEnum.NO;
        this.thereRevenueLiability = YesNoEnum.NO;
        this.additionalRiskIdentified = YesNoEnum.NO;
        this.totalRandValuePerSection93 = 0.00;
        this.seizureAndDetained = YesNoEnum.NO;
        this.wasLienOnGood = YesNoEnum.NO;
        this.clientRequestGoodsBack = YesNoEnum.NO;
    }
    public void addOffenceAndPenalty(OffenceAndPenalty offenceAndPenalty) {
        AuditReportOffencePenalty auditReportOffencePenalty = new AuditReportOffencePenalty(offenceAndPenalty, this);
        auditReportOffencePenalty.setCreatedDate(new Date());
        auditReportOffencePenalty.setCreatedBy(offenceAndPenalty.getCreatedBy());
        if (!auditReportOffencePenalties.contains(auditReportOffencePenalty)) {
            auditReportOffencePenalties.add(auditReportOffencePenalty);
            offenceAndPenalty.getAuditReportOffencePenalties().add(auditReportOffencePenalty);
        }
    }

    public void removeAuditReportOffencePenalty(AuditReportOffencePenalty auditReportOffencePenalty) {
        auditReportOffencePenalties.remove(auditReportOffencePenalty);
        auditReportOffencePenalty.getOffencePenalty().getAuditReportOffencePenalties().remove(auditReportOffencePenalty);
        auditReportOffencePenalty.setAuditReport(null);
        auditReportOffencePenalty.setOffencePenalty(null);
    }

    public void removeOffenceAndPenalty(OffenceAndPenalty offenceAndPenalty) {
        Iterator<AuditReportOffencePenalty> iterator = auditReportOffencePenalties.iterator();
        while (iterator.hasNext()) {
            AuditReportOffencePenalty auditReportOffencePenalty = iterator.next();
            if (auditReportOffencePenalty.getAuditReport().getId().equals(this.getId()) && auditReportOffencePenalty.getOffencePenalty().getId().equals(offenceAndPenalty.getId())) {
                iterator.remove();
                auditReportOffencePenalty.getOffencePenalty().getAuditReportOffencePenalties().remove(auditReportOffencePenalty);
                auditReportOffencePenalty.setAuditReport(null);
                auditReportOffencePenalty.setOffencePenalty(null);
            }
        }
    }
}
