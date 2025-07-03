package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.AuditPlan;

/**
 *
 * @author S2026987
 */
@Repository
public interface AuditPlanRepository extends JpaRepository<AuditPlan, Long> {
     
}
