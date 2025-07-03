package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.persistence.AuditReportOffencePenaltyRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class AuditReportOffencePenaltyService implements AuditReportOffencePenaltyServiceLocal {

    @Autowired
    private AuditReportOffencePenaltyRepository auditReportOffencePenaltyRepository;

    @Override
    public AuditReportOffencePenalty save(AuditReportOffencePenalty auditReportOffencePenalty) {

        return auditReportOffencePenaltyRepository.save(auditReportOffencePenalty);

    }

    @Override
    public AuditReportOffencePenalty findById(Long id) {

        return auditReportOffencePenaltyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));

    }

    @Override
    public AuditReportOffencePenalty update(AuditReportOffencePenalty auditReportOffencePenalty) {

        return auditReportOffencePenaltyRepository.save(auditReportOffencePenalty);
    }

    @Override
    public AuditReportOffencePenalty deleteById(Long id) {

        AuditReportOffencePenalty auditReportOffencePenalty = findById(id);
        if (auditReportOffencePenalty != null) {
            auditReportOffencePenaltyRepository.delete(auditReportOffencePenalty);
        }
        return auditReportOffencePenalty;
    }

    @Override
    public List<AuditReportOffencePenalty> listAll() {
        return auditReportOffencePenaltyRepository.findAll();
    }

    @Override
    public boolean isExist(AuditReportOffencePenalty auditReportOffencePenalty) {
        return false;
    }

    @Override
    public List<AuditReportOffencePenalty> findByAuditReportOffencePenalty(OffenceDescription offenceDescription) {
        return auditReportOffencePenaltyRepository.findByAuditReportOffencePenalty(offenceDescription);
    }

    @Override
    public boolean isReportLinkedToOffenceAndPenalty(AuditReport report, OffenceAndPenalty penalty) {
        return auditReportOffencePenaltyRepository.findByAuditReportAndOffencePenalty(report, penalty) != null;
    }

}
