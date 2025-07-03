package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.persistence.OffenceAndPenaltyRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class OffenceAndPenaltyService implements OffenceAndPenaltyServiceLocal {

    @Autowired
    private OffenceAndPenaltyRepository offenceAndPenaltyRepository;

    @Override
    public OffenceAndPenalty save(OffenceAndPenalty offenceAndPenalty) {
        return offenceAndPenaltyRepository.save(offenceAndPenalty);

    }

    @Override
    public OffenceAndPenalty findById(Long id) {
        return offenceAndPenaltyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));

    }

    @Override
    public OffenceAndPenalty deleteById(Long id) {
        OffenceAndPenalty offenceAndPenalty = findById(id);
        if (offenceAndPenalty != null) {
            offenceAndPenaltyRepository.delete(offenceAndPenalty);
        }
        return offenceAndPenalty;

    }

    @Override
    public List<OffenceAndPenalty> listAll() {
        return offenceAndPenaltyRepository.findAll();
    }

    @Override
    public boolean isExist(OffenceAndPenalty offenceAndPenalty) {
        return offenceAndPenaltyRepository.findById(offenceAndPenalty.getId()) != null;
    }

    @Override
    public OffenceAndPenalty update(OffenceAndPenalty offenceAndPenalty) {
        return offenceAndPenaltyRepository.save(offenceAndPenalty);
    }

    @Override
    public List<OffenceAndPenalty> findByOffenceClassificationType(CaseType offenceClassificationType) {
        return offenceAndPenaltyRepository.findByOffenceClassificationType(offenceClassificationType);
    }

    @Override
    public OffenceAndPenalty findByOffenceDescription(OffenceDescription offenceDescription) {
        return offenceAndPenaltyRepository.findByOffenceDescription(offenceDescription);
    }
}
