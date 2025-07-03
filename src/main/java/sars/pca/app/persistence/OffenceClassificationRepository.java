package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceClassification;

/**
 *
 * @author S2026987
 */
@Repository
public interface OffenceClassificationRepository extends JpaRepository<OffenceClassification, Long> {

    OffenceClassification findByDescription(String description);

    List<OffenceClassification> findByClassificationType(CaseType classificationType);
}
