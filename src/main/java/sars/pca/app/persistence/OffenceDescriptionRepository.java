package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.SectionRule;

/**
 *
 * @author S2026987
 */
@Repository
public interface OffenceDescriptionRepository extends JpaRepository<OffenceDescription, Long> {

    OffenceDescription findByDescription(String description);

    List<OffenceDescription> findBySectionRule(SectionRule sectionRule);
}
