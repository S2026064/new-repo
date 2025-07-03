package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.YesNoEnum;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "prioritisation_score")
@Getter
@Setter
public class PrioritisationScore extends BaseEntity {

    @Column(name = "min_value")
    private Double minimunValue;

    @Column(name = "max_value")
    private Double maximumValue;

    @Column(name = "min_line_amount")
    private Integer minimunLine;

    @Column(name = "max_line_amount")
    private Integer maximumLine;

    @Column(name = "score")
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "score_type")
    private ScoreType scoreType;

    @Enumerated(EnumType.STRING)
    @Column(name = "commodity")
    private YesNoEnum commodityAnswer;

    public PrioritisationScore() {
        this.minimunValue = 0.00;
        this.maximumValue = 0.00;
        this.minimunLine = 0;
        this.maximumLine = 0;
        this.score = 0;
        this.scoreType = ScoreType.CUSTOMS_VALUE;
        this.commodityAnswer = YesNoEnum.NO;
    }
    public PrioritisationScore(Double minimunValue, Double maximumValue, Integer score, ScoreType scoreType) {
        this.minimunValue = minimunValue;
        this.maximumValue = maximumValue;
        this.score = score;
        this.scoreType = scoreType;
    }

    public PrioritisationScore(Integer minimunLine, Integer maximumLine, Integer score, ScoreType scoreType) {
        this.minimunLine = minimunLine;
        this.maximumLine = maximumLine;
        this.score = score;
        this.scoreType = scoreType;
    }

    public PrioritisationScore(Integer score, ScoreType scoreType, YesNoEnum commodityAnswer) {
        this.score = score;
        this.scoreType = scoreType;
        this.commodityAnswer = commodityAnswer;
    }

    public PrioritisationScore(Integer score, ScoreType scoreType) {
        this.score = score;
        this.scoreType = scoreType;
    }
}
