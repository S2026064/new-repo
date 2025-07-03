package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "risk_rating_consequence")
@Getter
@Setter
public class RiskRatingConsequence extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Integer weight;

    public RiskRatingConsequence() {
        this.weight = 0;
    }

    public RiskRatingConsequence(String description, Integer weight) {
        this.description = description;
        this.weight = weight;
    }

}
