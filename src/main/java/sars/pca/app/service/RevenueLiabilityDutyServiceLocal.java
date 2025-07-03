package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.RevenueLiability;
import sars.pca.app.domain.RevenueLiabilityDuty;


public interface RevenueLiabilityDutyServiceLocal {
    RevenueLiabilityDuty save(RevenueLiabilityDuty revenueLiabilityDuty);

    RevenueLiabilityDuty findById(Long id);

    RevenueLiabilityDuty update(RevenueLiabilityDuty revenueLiabilityDuty);

    RevenueLiabilityDuty deleteById(Long id);

    List<RevenueLiabilityDuty> listAll();

    boolean isExist(RevenueLiabilityDuty revenueLiabilityDuty);
    
    Long countByAmountGreaterThanAndRevenueLiability(double amount,RevenueLiability revenueLiability);

    
}
