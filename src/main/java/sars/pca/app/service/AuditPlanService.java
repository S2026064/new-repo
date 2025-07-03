package sars.pca.app.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.AuditPlanType;
import sars.pca.app.domain.AuditPlan;
import sars.pca.app.persistence.AuditPlanRepository;

/**
 *
 * @author S2028389
 */
@Service
@Transactional
public class AuditPlanService implements AuditPlanServiceLocal {
    @Autowired
    private AuditPlanRepository auditPlanRepository;

    @Override
    public AuditPlan save(AuditPlan auditPlan) {
        return auditPlanRepository.save(auditPlan);
    }
    @Override
    public AuditPlan findById(Long id) {
        return auditPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public AuditPlan update(AuditPlan auditPlan) {
        return auditPlanRepository.save(auditPlan);
    }

    @Override
    public AuditPlan deleteById(Long id) {
        AuditPlan auditPlan = findById(id);

        if (auditPlan != null) {
            auditPlanRepository.delete(auditPlan);
        }
        return auditPlan;
    }

    @Override
    public List<AuditPlan> listAll() {
        return auditPlanRepository.findAll();
    }

    @Override
    public boolean isExist(AuditPlan auditPlan) {
        return auditPlanRepository.findById(auditPlan.getId()) != null;
    }

//    @Override
//    public List<AuditPlan> findByAuditPlanType(AuditPlanType auditPlanType) {
//        return auditPlanRepository.findByAuditPlanType(auditPlanType);
//    }
}
