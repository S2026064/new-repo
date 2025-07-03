package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.NonComplianceDetails;

/**
 *
 * @author S2026987
 */
public interface NonComplianceDetailsServiceLocal {

    NonComplianceDetails save(NonComplianceDetails complianceDetails);

    NonComplianceDetails findById(Long id);

    NonComplianceDetails update(NonComplianceDetails complianceDetails);

    NonComplianceDetails deleteById(Long id);

    List<NonComplianceDetails> listAll();
}
