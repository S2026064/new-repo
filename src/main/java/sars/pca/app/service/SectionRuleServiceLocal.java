package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.SectionRule;

/**
 *
 * @author S2026987
 */
public interface SectionRuleServiceLocal {
    SectionRule save(SectionRule sectionRule);

    SectionRule findById(Long id);

    SectionRule update(SectionRule sectionRule);

    SectionRule deleteById(Long id);
    
    List<SectionRule> listAll();
    
    boolean isExist(SectionRule sectionRule);
    
    SectionRule findByDescription(String description);
    
    List<SectionRule> findByOffenceClassification(OffenceClassification offenceClassification);
}
