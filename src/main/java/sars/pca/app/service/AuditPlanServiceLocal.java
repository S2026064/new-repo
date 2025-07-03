package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.AuditPlan;

/**
 *
 * @author S2028389
 */
public interface AuditPlanServiceLocal {

    AuditPlan save(AuditPlan auditPlan);

    AuditPlan findById(Long id);

    AuditPlan update(AuditPlan auditPlan);

    AuditPlan deleteById(Long id);

    List<AuditPlan> listAll();

    boolean isExist(AuditPlan auditPlan);
}
