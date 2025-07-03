package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.RiskRatingLikelihood;

public interface RiskRatingLikelihoodServiceLocal {

    RiskRatingLikelihood save(RiskRatingLikelihood riskRatingLikelihood);

    RiskRatingLikelihood findById(Long id);

    RiskRatingLikelihood update(RiskRatingLikelihood riskRatingLikelihood);

    RiskRatingLikelihood deleteById(Long id);

    List<RiskRatingLikelihood> listAll();

    boolean isExist(RiskRatingLikelihood riskRatingLikelihood);
}
