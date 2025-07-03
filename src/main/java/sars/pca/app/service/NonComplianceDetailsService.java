package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.NonComplianceDetails;
import sars.pca.app.persistence.NonComplianceDetailsRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class NonComplianceDetailsService implements NonComplianceDetailsServiceLocal {

    @Autowired
    private NonComplianceDetailsRepository nonComplianceDetailsRepository;

    @Override
    public NonComplianceDetails save(NonComplianceDetails nonComplianceDetails) {
        return nonComplianceDetailsRepository.saveAndFlush(nonComplianceDetails);
    }

    @Override
    public NonComplianceDetails findById(Long id) {
        return nonComplianceDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public NonComplianceDetails update(NonComplianceDetails nonComplianceDetails) {
        return nonComplianceDetailsRepository.saveAndFlush(nonComplianceDetails);
    }

    @Override
    public NonComplianceDetails deleteById(Long id) {
        NonComplianceDetails nonComplianceDetails = findById(id);

        if (nonComplianceDetails != null) {
            nonComplianceDetailsRepository.delete(nonComplianceDetails);
        }
        return nonComplianceDetails;
    }

    @Override
    public List<NonComplianceDetails> listAll() {
        return nonComplianceDetailsRepository.findAll();
    }
}
