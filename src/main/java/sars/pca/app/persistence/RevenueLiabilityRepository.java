package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.RevenueLiability;

/**
 *
 * @author S2026064
 */
@Repository
public interface RevenueLiabilityRepository extends JpaRepository<RevenueLiability, Long>{
    
}
