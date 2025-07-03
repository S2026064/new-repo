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
@Table(name = "vddl_sarcase_sett")
@Getter
@Setter
public class VddlOrSarCaseSetting extends BaseEntity {

    @Column(name = "create_sarcase")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createSarCase;

    @Column(name = "review_sarcase")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reviewSarCase;

    @Column(name = "allocate_sarcase")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean allocateSarCase;

    @Column(name = "reverse_sarcase")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean reverseOrReassign;

    @Column(name = "create_vddl")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean createVddl;

    public VddlOrSarCaseSetting() {
        this.setAllocateSarCase(false);
        this.setCreateSarCase(false);
        this.setCreateVddl(false);
        this.setReverseOrReassign(false);
        this.setReviewSarCase(false);
    }
    public boolean isVddlOrSarCase() {
        return this.allocateSarCase || this.createSarCase || this.createVddl || this.reverseOrReassign || this.reviewSarCase;
    }
}
