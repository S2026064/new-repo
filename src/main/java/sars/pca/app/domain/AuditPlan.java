package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import sars.pca.app.common.AuditType;

@Entity
@Table(name = "audit_plan")
@Getter
@Setter
public class AuditPlan extends BaseEntity {

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @ElementCollection
    @Column(name = "audit_period")
    private Collection<Date> auditPeriod = new ArrayList<>();

    @Column(name = "vddl_audit_scope", nullable = true)
    private String vddlAuditScope;

    @Column(name = "vddl_audit_objective", nullable = true)
    private String vddlAuditObjective;

    @Column(name = "audit_type")
    @Enumerated(EnumType.STRING)
    private AuditType auditType;

    @OneToOne(mappedBy = "auditPlan")
    private SuspiciousCase suspiciousCase;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = AuditReport.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "audit_report_id")
    private AuditReport auditReport;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = AuditDetails.class, orphanRemoval = true, mappedBy = "auditPlan")
    private List<AuditDetails> auditDetailses = new ArrayList();

    public void addAuditDetails(AuditDetails auditDetail) {
        auditDetail.setAuditPlan(this);
        auditDetailses.add(auditDetail);
    }

    public void removeAuditDetails(AuditDetails auditDetail) {
        auditDetailses.remove(auditDetail);
        auditDetail.setAuditPlan(null);
    }

    public AuditPlan() {
        this.startDate = new Date();
        this.auditType = AuditType.DESK;
    }
}
