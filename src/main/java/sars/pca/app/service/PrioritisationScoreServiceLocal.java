package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.PrioritisationScore;

/**
 *
 * @author S2026015
 */
public interface PrioritisationScoreServiceLocal {

    PrioritisationScore save(PrioritisationScore prioritisationScore);

    PrioritisationScore findById(Long id);

    PrioritisationScore update(PrioritisationScore prioritisationScore);

    PrioritisationScore deleteById(Long id);

    List<PrioritisationScore> listAll();

    Integer findScorebyAmount(Double customValue, ScoreType scoreType);

    Integer findScorebyNumberOfLines(Integer lineNumber,ScoreType scoreType);

    Integer findScorebyCommodityAlignment(YesNoEnum yesNoEnum,ScoreType scoreType);
}
