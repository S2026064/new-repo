package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.persistence.OffenceClassificationRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class OffenceClassificationService implements OffenceClassificationServiceLocal {

    @Autowired
    private OffenceClassificationRepository offenceClassificationRepository;

    @Override
    public OffenceClassification save(OffenceClassification offenceClassification) {
        return offenceClassificationRepository.save(offenceClassification);
    }

    @Override
    public OffenceClassification findById(Long id) {
        return offenceClassificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public OffenceClassification update(OffenceClassification offenceClassification) {
        return offenceClassificationRepository.save(offenceClassification);
    }

    @Override
    public OffenceClassification deleteById(Long id) {
        OffenceClassification offenceClassification = findById(id);
        if (offenceClassification != null) {
            offenceClassificationRepository.delete(offenceClassification);
        }
        return offenceClassification;
    }

    @Override
    public List<OffenceClassification> listAll() {
        return offenceClassificationRepository.findAll();
    }

    @Override
    public boolean isExist(OffenceClassification offenceClassification) {
        return offenceClassificationRepository.findById(offenceClassification.getId()) != null;
    }

    @Override
    public OffenceClassification findByDescription(String description) {
        return offenceClassificationRepository.findByDescription(description);
    }

    @Override
    public List<OffenceClassification> findByClassificationType(CaseType classificationType) {
        return offenceClassificationRepository.findByClassificationType(classificationType);
    }

}
