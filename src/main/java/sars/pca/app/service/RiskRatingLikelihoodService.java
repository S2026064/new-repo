package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.RiskRatingLikelihood;
import sars.pca.app.persistence.RiskRatingLikelihoodRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class RiskRatingLikelihoodService implements RiskRatingLikelihoodServiceLocal {

    @Autowired
    private RiskRatingLikelihoodRepository riskRatingLikelihoodRepository;

    @Override
    public RiskRatingLikelihood save(RiskRatingLikelihood riskRatingLikelihood) {
        return riskRatingLikelihoodRepository.save(riskRatingLikelihood);
    }

    @Override
    public RiskRatingLikelihood findById(Long id) {
        return riskRatingLikelihoodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public RiskRatingLikelihood update(RiskRatingLikelihood riskRatingLikelihood) {
        return riskRatingLikelihoodRepository.save(riskRatingLikelihood);
    }

    @Override
    public RiskRatingLikelihood deleteById(Long id) {
        RiskRatingLikelihood riskRatingLikelihood = findById(id);
        if (riskRatingLikelihood != null) {
            riskRatingLikelihoodRepository.delete(riskRatingLikelihood);
        }
        return riskRatingLikelihood;
    }

    @Override
    public List<RiskRatingLikelihood> listAll() {
        return riskRatingLikelihoodRepository.findAll();
    }

    @Override
    public boolean isExist(RiskRatingLikelihood riskRatingLikelihood) {
        return riskRatingLikelihoodRepository.findById(riskRatingLikelihood.getId()) != null;
    }
}
