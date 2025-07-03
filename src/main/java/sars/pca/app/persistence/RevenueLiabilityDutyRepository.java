package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.RevenueLiability;
import sars.pca.app.domain.RevenueLiabilityDuty;

/**
 *
 * @author S2026987
 */
@Repository
public interface RevenueLiabilityDutyRepository extends JpaRepository<RevenueLiabilityDuty, Long> {
   Long countByAmountGreaterThanAndRevenueLiability(double amount,RevenueLiability revenueLiability);
}
