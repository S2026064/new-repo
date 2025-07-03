package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.RiskRatingConsequence;


public interface RiskRatingConsequenceServiceLocal {
    RiskRatingConsequence save(RiskRatingConsequence riskRatingConsequence);

    RiskRatingConsequence findById(Long id);

    RiskRatingConsequence update(RiskRatingConsequence riskRatingConsequence);

    RiskRatingConsequence deleteById(Long id);

    List<RiskRatingConsequence> listAll();

    boolean isExist(RiskRatingConsequence riskRatingConsequence);
}
