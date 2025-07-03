package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.RiskRatingConsequence;
import sars.pca.app.persistence.RiskRatingConsequenceRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class RiskRatingConsequenceService implements RiskRatingConsequenceServiceLocal {

    @Autowired
    private RiskRatingConsequenceRepository riskRatingConsequenceRepository;

    @Override
    public RiskRatingConsequence save(RiskRatingConsequence riskRatingConsequence) {
        return riskRatingConsequenceRepository.save(riskRatingConsequence);
    }

    @Override
    public RiskRatingConsequence findById(Long id) {
        return riskRatingConsequenceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public RiskRatingConsequence update(RiskRatingConsequence riskRatingConsequence) {
        return riskRatingConsequenceRepository.save(riskRatingConsequence);
    }

    @Override
    public RiskRatingConsequence deleteById(Long id) {
        RiskRatingConsequence riskRatingConsequence = findById(id);
        if (riskRatingConsequence != null) {
            riskRatingConsequenceRepository.delete(riskRatingConsequence);
        }
        return riskRatingConsequence;
    }

    @Override
    public List<RiskRatingConsequence> listAll() {
        return riskRatingConsequenceRepository.findAll();
    }

    @Override
    public boolean isExist(RiskRatingConsequence riskRatingConsequence) {
        return riskRatingConsequenceRepository.findById(riskRatingConsequence.getId()) != null;
    }
}
