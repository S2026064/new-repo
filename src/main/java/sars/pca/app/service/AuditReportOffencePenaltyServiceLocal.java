package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;

/**
 *
 * @author S2026064
 */
public interface AuditReportOffencePenaltyServiceLocal {

    AuditReportOffencePenalty save(AuditReportOffencePenalty offencePenalty);

    AuditReportOffencePenalty findById(Long id);

    AuditReportOffencePenalty update(AuditReportOffencePenalty offencePenalty);

    AuditReportOffencePenalty deleteById(Long id);

    List<AuditReportOffencePenalty> listAll();

    boolean isExist(AuditReportOffencePenalty offencePenalty);

    List<AuditReportOffencePenalty> findByAuditReportOffencePenalty(OffenceDescription offenceDescription);

    boolean isReportLinkedToOffenceAndPenalty(AuditReport report, OffenceAndPenalty penalty);

}
