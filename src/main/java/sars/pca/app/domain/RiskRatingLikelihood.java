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
@Table(name = "risk_rating_likelihood")
@Getter
@Setter
public class RiskRatingLikelihood extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Integer weight;

    public RiskRatingLikelihood() {
        this.weight = 0;
    }

    public RiskRatingLikelihood(String description, Integer weight) {
        this.description = description;
        this.weight = weight;
    }
    
}
