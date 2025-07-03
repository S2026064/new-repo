package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.AuditReport;

/**
 *
 * @author S2026064
 */
public interface AuditReportServiceLocal {

    AuditReport save(AuditReport auditReporting);

    AuditReport findById(Long id);

    AuditReport update(AuditReport auditReporting);

    AuditReport deleteById(Long id);

    List<AuditReport> listAll();

    boolean isExist(AuditReport auditReporting);   
  
}
