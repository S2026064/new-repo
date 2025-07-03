package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.persistence.AuditReportingRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class AuditReportService implements AuditReportServiceLocal {

    @Autowired
    private AuditReportingRepository auditReportingRepository;

    @Override
    public AuditReport save(AuditReport auditReporting) {

        return auditReportingRepository.save(auditReporting);

    }
    @Override
    public AuditReport findById(Long id) {

        return auditReportingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));

    }
    @Override
    public AuditReport update(AuditReport auditReporting) {

        return auditReportingRepository.save(auditReporting);
    }
    @Override
    public AuditReport deleteById(Long id) {

        AuditReport auditReporting = findById(id);
        if (auditReporting != null) {
            auditReportingRepository.delete(auditReporting);
        }
        return auditReporting;
    }
    @Override
    public List<AuditReport> listAll() {

        return auditReportingRepository.findAll();
    }
    @Override
    public boolean isExist(AuditReport auditReporting) {
        return false;
    }
}
