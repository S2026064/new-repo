package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;

/**
 *
 * @author S2026987
 */
@Repository
public interface AuditReportOffencePenaltyRepository extends JpaRepository<AuditReportOffencePenalty, Long> {

    @Query("SELECT e FROM AuditReportOffencePenalty e WHERE e.offencePenalty.offenceDescription=:offenceDescrip")
    List<AuditReportOffencePenalty> findByAuditReportOffencePenalty(@Param("offenceDescrip") OffenceDescription offenceDescription);

    AuditReportOffencePenalty findByAuditReportAndOffencePenalty(AuditReport report, OffenceAndPenalty penalty);
}
