package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.RiskRatingConsequence;

/**
 *
 * @author S2026987
 */
@Repository
public interface RiskRatingConsequenceRepository extends JpaRepository<RiskRatingConsequence, Long> {

}
