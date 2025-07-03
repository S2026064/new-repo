package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.persistence.SectionRuleRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class SectionRuleService implements SectionRuleServiceLocal {

    @Autowired
    private SectionRuleRepository sectionRuleRepository;

    @Override
    public SectionRule save(SectionRule sectionRule) {
        return sectionRuleRepository.save(sectionRule);
    }

    @Override
    public SectionRule findById(Long id) {
        return sectionRuleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SectionRule update(SectionRule sectionRule) {
        return sectionRuleRepository.save(sectionRule);
    }

    @Override
    public SectionRule deleteById(Long id) {
        SectionRule sectionRule = findById(id);
        if (sectionRule != null) {
            sectionRuleRepository.delete(sectionRule);
        }
        return sectionRule;
    }

    @Override
    public List<SectionRule> listAll() {
        return sectionRuleRepository.findAll();
    }

    @Override
    public boolean isExist(SectionRule sectionRule) {
        return sectionRuleRepository.findById(sectionRule.getId()) != null;
    }

    @Override
    public SectionRule findByDescription(String description) {
        return sectionRuleRepository.findByDescription(description);
    }

    @Override
    public List<SectionRule> findByOffenceClassification(OffenceClassification offenceClassification) {
        return sectionRuleRepository.findByOffenceClassification(offenceClassification);
    }

}
