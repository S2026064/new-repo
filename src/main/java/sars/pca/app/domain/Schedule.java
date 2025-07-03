package sars.pca.app.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "debt_mgmnt_id")
    private DebtManagement debtManagement;
}
