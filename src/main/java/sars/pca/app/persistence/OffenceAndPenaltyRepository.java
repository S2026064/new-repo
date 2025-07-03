package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;

/**
 *
 * @author S2026064
 */
@Repository
public interface OffenceAndPenaltyRepository extends JpaRepository<OffenceAndPenalty, Long> {

    @Query("SELECT e FROM OffenceAndPenalty e WHERE e.offenceClassification.classificationType=:caseType")
    List<OffenceAndPenalty> findByOffenceClassificationType(@Param("caseType") CaseType offenceClassificationType);

    OffenceAndPenalty findByOffenceDescription(OffenceDescription offenceDescription);
}
