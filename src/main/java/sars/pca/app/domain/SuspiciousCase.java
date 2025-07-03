package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;

@Entity
@Table(name = "suspicious_case")
@Getter
@Setter
public class SuspiciousCase extends BaseEntity {

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "sarcase_id", nullable = true)
    private SarCase sarCase;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "vddled_id", nullable = true)
    private VddLedCase vddLed;

    @Column(name = "case_type")
    @Enumerated(EnumType.STRING)
    private CaseType caseType;

    @Column(name = "case_status")
    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @Column(name = "case_ref_num")
    private String caseRefNumber;

    @Column(name = "priority_index")
    private Integer priorityIndex;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = SarCaseUser.class, mappedBy = "suspiciousCase", orphanRemoval = true)
    private List<SarCaseUser> sarCaseUsers = new ArrayList<>();

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = LocationOffice.class)
    @JoinColumn(name = "crcs_office_id", nullable = true)
    private LocationOffice crcsLocationOffice;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = LocationOffice.class)
    @JoinColumn(name = "ci_office_id", nullable = true)
    private LocationOffice ciLocationOffice;
    
    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = Letter.class, mappedBy = "suspiciousCase", orphanRemoval = true)
    private List<Letter> letters = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = IbrData.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ibr_data_id")
    private IbrData ibrData;

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = AuditPlan.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "audit_plan_id")
    private AuditPlan auditPlan;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = Comment.class, mappedBy = "suspiciousCase", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = Attachment.class, orphanRemoval = true, mappedBy = "suspiciousCase")
    private List<Attachment> attachments = new ArrayList<>();

    public SuspiciousCase() {
        this.priorityIndex = 0;
    }

    public void addSarCaseUser(SarCaseUser sarCaseUser) {
        if (!sarCaseUsers.contains(sarCaseUser)) {
            sarCaseUser.setSuspiciousCase(this);
            sarCaseUsers.add(0, sarCaseUser);
        }
    }

    public void removeSarCaseUser(SarCaseUser sarCaseUser) {
        sarCaseUser.setSuspiciousCase(null);
        sarCaseUsers.remove(sarCaseUser);
    }

    public void addComment(Comment comment) {
        comment.setSuspiciousCase(this);
        comments.add(0, comment);

    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setSuspiciousCase(null);
    }

    public void addAttachment(Attachment attachment) {
        if (!attachments.contains(attachment)) {
            attachment.setSuspiciousCase(this);
            attachments.add(attachment);
        }
    }

    public void removeAttachment(Attachment attachment) {
        attachments.remove(attachment);
        attachment.setSuspiciousCase(null);
    }
      public void addLetter(Letter letter) {
        if (!letters.contains(letter)) {
            letter.setSuspiciousCase(this);
            letters.add(0, letter);
        }
    }

    public void removeLetter(Letter letter) {
        letters.remove(letter);
        letter.setSuspiciousCase(null);
    }

}
