package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.RevenueLiability;

/**
 *
 * @author S2026064
 */
public interface RevenueLiabilityServiceLocal {

    RevenueLiability save(RevenueLiability userRole);

    RevenueLiability findById(Long id);

    RevenueLiability update(RevenueLiability userRole);

    RevenueLiability deleteById(Long id);

    List<RevenueLiability> listAll();

}
