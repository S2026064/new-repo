package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.ReportedPerson;

/**
 *
 * @author S2026987
 */
public interface ReportedPersonServiceLocal {

    ReportedPerson save(ReportedPerson reportedPerson);

    ReportedPerson findById(Long id);

    ReportedPerson update(ReportedPerson reportedPerson);

    ReportedPerson deleteById(Long id);

    List<ReportedPerson> listAll();
}
