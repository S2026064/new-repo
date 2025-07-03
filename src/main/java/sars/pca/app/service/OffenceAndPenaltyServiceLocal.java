package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;

/**
 *
 * @author S2026064
 */
public interface OffenceAndPenaltyServiceLocal {

    OffenceAndPenalty save(OffenceAndPenalty offenceAndPenalty);

    OffenceAndPenalty findById(Long id);

    OffenceAndPenalty update(OffenceAndPenalty offenceAndPenalty);

    OffenceAndPenalty deleteById(Long id);

    List<OffenceAndPenalty> listAll();

    boolean isExist(OffenceAndPenalty offenceAndPenalty);

    List<OffenceAndPenalty> findByOffenceClassificationType(CaseType offenceClassificationType);
    
   OffenceAndPenalty findByOffenceDescription(OffenceDescription offenceDescription);
}
   
