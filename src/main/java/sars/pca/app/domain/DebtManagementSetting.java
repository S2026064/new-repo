package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2026987
 */
@Entity
@Table(name = "debt_mngmnt_sett")
@Getter
@Setter
public class DebtManagementSetting extends BaseEntity {
    @Column(name = "create_debt_mngmnt")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean create;

    @Column(name = "review_debt_mngmnt")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean review;

    public DebtManagementSetting() {
        this.setCreate(Boolean.FALSE);
        this.setReview(Boolean.FALSE);
    }
    public boolean isDebtManagement() {
        return this.create || this.review;
    }
}
