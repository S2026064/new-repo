package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.SectionRule;

/**
 *
 * @author S2026987
 */
public interface OffenceDescriptionServiceLocal {

    OffenceDescription save(OffenceDescription offenceDescription);

    OffenceDescription findById(Long id);

    OffenceDescription update(OffenceDescription offenceDescription);

    OffenceDescription deleteById(Long id);

    List<OffenceDescription> listAll();

    boolean isExist(OffenceDescription offenceDescription);

    OffenceDescription findByDescription(String description);

    List<OffenceDescription> findBySectionRule(SectionRule sectionRule);
}
