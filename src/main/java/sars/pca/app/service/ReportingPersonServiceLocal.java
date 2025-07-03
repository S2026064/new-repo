package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.ReportingPerson;

/**
 *
 * @author S2026987
 */
public interface ReportingPersonServiceLocal {

    ReportingPerson save(ReportingPerson reportingPerson);

    ReportingPerson findById(Long id);

    ReportingPerson update(ReportingPerson reportingPerson);

    ReportingPerson deleteById(Long id);

    List<ReportingPerson> listAll();
}
