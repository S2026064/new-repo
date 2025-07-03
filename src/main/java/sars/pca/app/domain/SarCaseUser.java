package sars.pca.app.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sarcase_user")
@Getter
@Setter
public class SarCaseUser extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sarcase_id")
    private SarCase sarCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "susp_case_id")
    private SuspiciousCase suspiciousCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
