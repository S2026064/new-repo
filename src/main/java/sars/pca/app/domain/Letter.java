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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import sars.pca.app.common.LetterHead;
import sars.pca.app.common.LetterType;
import sars.pca.app.common.Salutation;
import sars.pca.app.common.YesNoEnum;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "letter")
@Getter
@Setter
public class Letter extends BaseEntity {

    @Column(name = "letter_head")
    @Enumerated(EnumType.STRING)
    private LetterHead letterHead;

    @Column(name = "letter_type")
    @Enumerated(EnumType.STRING)
    private LetterType letterType;

    @Column(name = "salutation")
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    @Column(name = "approve")
    @Enumerated(EnumType.STRING)
    private YesNoEnum approve;

    @Column(name = "issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address Address;

    @Column(name = "field_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fieldDate;

    @Column(name = "notification_of_audit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationOfAuditDate;

    @Column(name = "notice_of_intent_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date noticeOfIntentDate;

    @Column(name = "representation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date representationDate;

    @Column(name = "circumstance_of_convention")
    private String circumstanceOfContravention;

    @Column(name = "goods_dealt")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean goodsDealt;

    @Column(name = "pay_fully_or_partially")
    @Enumerated(EnumType.STRING)
    private YesNoEnum partiallyPayment;

    @Column(name = "additional_explanation")
    private String additionalExplanation;

    @Column(name = "amount_forfeiture")
    private String amountForfieture;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = SuspiciousCase.class)
    @JoinColumn(name = "susp_case_id")
    private SuspiciousCase suspiciousCase;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = CustomDeclaration.class, orphanRemoval = true)
    @JoinColumn(name = "custom_declaration_id")
    private List<CustomDeclaration> declarations = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = DutyVat.class, orphanRemoval = true)
    @JoinColumn(name = "duty_vat_id")
    private List<DutyVat> dutyVats = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = Auditor.class, orphanRemoval = true)
    @JoinColumn(name = "auditor_id")
    private List<Auditor> auditors = new ArrayList<>();
    ;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "letter_relevant_material",
            joinColumns = {
                @JoinColumn(name = "letter_id", referencedColumnName = "id",
                        nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "relevant_material_id", referencedColumnName = "id",
                        nullable = false, updatable = false)})
    private List<RelevantMaterial> relevantMaterials = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "letter_program",
            joinColumns = {
                @JoinColumn(name = "letter_id", referencedColumnName = "id",
                        nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "program_id", referencedColumnName = "id",
                        nullable = false, updatable = false)})
    private List<Program> programs = new ArrayList<>();

    @Column(name = "days_due")
    private String daysDue;

    @Column(name = "total_vat_due")
    private String totalVatDue;

    @Column(name = "total_b")
    private String totalB;

    @Column(name = "days_to_contact")
    private String daysToContact;

    @Column(name = "total_a")
    private String totalA;

    public void addAuditor(Auditor auditor) {
        auditors.add(auditor);
    }

    public void removeAuditor(Auditor auditor) {
        if (!auditors.contains(auditor)) {
            auditors.add(auditor);
        }
    }

    public void addDutyVat(DutyVat dutyVat) {
        dutyVats.add(dutyVat);
    }

    public void removeDutyVat(DutyVat dutyVat) {
        if (!dutyVats.contains(dutyVat)) {
            dutyVats.add(dutyVat);
        }
    }

    public void addCustomsDeclaration(CustomDeclaration customsDeclaration) {
        declarations.add(customsDeclaration);
    }

    public void removeCustomDeclaration(CustomDeclaration customDeclaration) {
        if (!declarations.contains(customDeclaration)) {
            declarations.add(customDeclaration);
        }
    }

}
