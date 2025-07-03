package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.PrioritisationScore;
import sars.pca.app.persistence.PrioritisationScoreRepository;

/**
 *
 * @author S2026015
 */
@Service
@Transactional
public class PrioritisationScoreService implements PrioritisationScoreServiceLocal {

    @Autowired
    private PrioritisationScoreRepository prioritisationScoreRepository;

    @Override
    public PrioritisationScore save(PrioritisationScore prioritisationScore) {
        return prioritisationScoreRepository.save(prioritisationScore);
    }

    @Override
    public PrioritisationScore findById(Long id) {
        return prioritisationScoreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }
    @Override
    public PrioritisationScore update(PrioritisationScore prioritisationScore) {
        return prioritisationScoreRepository.save(prioritisationScore);
    }

    @Override
    public PrioritisationScore deleteById(Long id) {
        PrioritisationScore prioritisationScore = findById(id);
        if (prioritisationScore != null) {
            prioritisationScoreRepository.delete(prioritisationScore);
        }
        return prioritisationScore;
    }
    @Override
    public List<PrioritisationScore> listAll() {
        return prioritisationScoreRepository.findAll();
    }

    @Override
    public Integer findScorebyAmount(Double customValue, ScoreType scoreType) {
        return prioritisationScoreRepository.findScorebyAmount(customValue, scoreType);
    }

    @Override
    public Integer findScorebyNumberOfLines(Integer lineNumber, ScoreType scoreType) {
        return prioritisationScoreRepository.findScorebyNumberOfLines(lineNumber, scoreType);
    }
    @Override
    public Integer findScorebyCommodityAlignment(YesNoEnum yesNoEnum, ScoreType scoreType) {
        return prioritisationScoreRepository.findScorebyCommodityAlignment(yesNoEnum, scoreType);
    }
}
