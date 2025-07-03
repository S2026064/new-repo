package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.DutyType;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "duty")
@Getter
@Setter
public class Duty extends BaseEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "declaration_line_number")
    private String declarationLineNumber;

    @Column(name = "line_number")
    private String lineNumber;

    @Column(name = "duty_type")
    @Enumerated(EnumType.STRING)
    private DutyType dutyType;

    public Duty() {
    }

    public Duty(String description, DutyType dutyType) {
        this.description = description;
        this.dutyType = dutyType;
    }
    @OneToMany(mappedBy = "duty", cascade = {CascadeType.ALL})
    private List<RevenueLiabilityDuty> revenueLiabilityDutys = new ArrayList<>();

    public void addRevenueLiabilityDuty(RevenueLiabilityDuty revenueLiabilityDuty) {
        revenueLiabilityDuty.setDuty(this);
        revenueLiabilityDutys.add(revenueLiabilityDuty);
    }

    public void removeRevenueLiabilityDuty(RevenueLiabilityDuty revenueLiabilityDuty) {
        revenueLiabilityDuty.setDuty(null);
        revenueLiabilityDutys.remove(revenueLiabilityDuty);
    }
}
