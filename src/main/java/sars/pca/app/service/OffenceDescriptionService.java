package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.persistence.OffenceDescriptionRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class OffenceDescriptionService implements OffenceDescriptionServiceLocal {

    @Autowired
    private OffenceDescriptionRepository offenceDescriptionRepository;

    @Override
    public OffenceDescription save(OffenceDescription offenceDescription) {
        return offenceDescriptionRepository.save(offenceDescription);
    }

    @Override
    public OffenceDescription findById(Long id) {
        return offenceDescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public OffenceDescription update(OffenceDescription offenceDescription) {
        return offenceDescriptionRepository.save(offenceDescription);
    }

    @Override
    public OffenceDescription deleteById(Long id) {
        OffenceDescription offenceDescription = findById(id);
        if (offenceDescription != null) {
            offenceDescriptionRepository.delete(offenceDescription);
        }
        return offenceDescription;
    }

    @Override
    public List<OffenceDescription> listAll() {
        return offenceDescriptionRepository.findAll();
    }

    @Override
    public boolean isExist(OffenceDescription offenceDescription) {
        return offenceDescriptionRepository.findById(offenceDescription.getId()) != null;
    }

    @Override
    public OffenceDescription findByDescription(String description) {
        return offenceDescriptionRepository.findByDescription(description);
    }

    @Override
    public List<OffenceDescription> findBySectionRule(SectionRule sectionRule) {
        return offenceDescriptionRepository.findBySectionRule(sectionRule);
    }

}
