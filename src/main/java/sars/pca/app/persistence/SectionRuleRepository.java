package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.SectionRule;

/**
 *
 * @author S2026987
 */
@Repository
public interface SectionRuleRepository extends JpaRepository<SectionRule, Long> {

    SectionRule findByDescription(String description);

    List<SectionRule> findByOffenceClassification(OffenceClassification offenceClassification);
}
