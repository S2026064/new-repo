package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.RevenueLiability;
import sars.pca.app.persistence.RevenueLiabilityRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class RevenueLiabilityService implements RevenueLiabilityServiceLocal {

    @Autowired
    private RevenueLiabilityRepository revenueLiabilityRepository;

     @Override
    public RevenueLiability save(RevenueLiability revenueLiability) {
        return revenueLiabilityRepository.save(revenueLiability);
    }

    @Override
    public RevenueLiability findById(Long id) {
        return revenueLiabilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public RevenueLiability update(RevenueLiability revenueLiability) {
        return revenueLiabilityRepository.save(revenueLiability);
    }

    @Override
    public RevenueLiability deleteById(Long id) {
        RevenueLiability revenueLiability = findById(id);
        if (revenueLiability != null) {
            revenueLiabilityRepository.delete(revenueLiability);
        }
        return revenueLiability;
    }
    @Override
    public List<RevenueLiability> listAll() {
        return revenueLiabilityRepository.findAll();
    }
}
