package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.RevenueLiability;
import sars.pca.app.domain.RevenueLiabilityDuty;
import sars.pca.app.persistence.RevenueLiabilityDutyRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class RevenueLiabilityDutyService implements RevenueLiabilityDutyServiceLocal {

    @Autowired
    private RevenueLiabilityDutyRepository revenueLiabilityDutyRepository;

    @Override
    public RevenueLiabilityDuty save(RevenueLiabilityDuty liabilityDuty) {
        return revenueLiabilityDutyRepository.save(liabilityDuty);
    }

    @Override
    public RevenueLiabilityDuty findById(Long id) {
        return revenueLiabilityDutyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public RevenueLiabilityDuty update(RevenueLiabilityDuty liabilityDuty) {
        return revenueLiabilityDutyRepository.save(liabilityDuty);
    }

    @Override
    public RevenueLiabilityDuty deleteById(Long id) {
        RevenueLiabilityDuty liabilityDuty = findById(id);
        if (liabilityDuty != null) {
            revenueLiabilityDutyRepository.delete(liabilityDuty);
        }
        return liabilityDuty;
    }

    @Override
    public List<RevenueLiabilityDuty> listAll() {
        return revenueLiabilityDutyRepository.findAll();
    }

    @Override
    public boolean isExist(RevenueLiabilityDuty liabilityDuty) {
        return revenueLiabilityDutyRepository.findById(liabilityDuty.getId()) != null;
    }

    @Override
    public Long countByAmountGreaterThanAndRevenueLiability(double amount, RevenueLiability revenueLiability) {
        return revenueLiabilityDutyRepository.countByAmountGreaterThanAndRevenueLiability(amount, revenueLiability);
    }

}
