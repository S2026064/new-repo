package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceClassification;

/**
 *
 * @author S2026987
 */
public interface OffenceClassificationServiceLocal {

    OffenceClassification save(OffenceClassification offenceClassification);

    OffenceClassification findById(Long id);

    OffenceClassification update(OffenceClassification offenceClassification);

    OffenceClassification deleteById(Long id);

    List<OffenceClassification> listAll();

    boolean isExist(OffenceClassification offenceClassification);

    OffenceClassification findByDescription(String description);
    
    List<OffenceClassification> findByClassificationType(CaseType classificationType);
}
