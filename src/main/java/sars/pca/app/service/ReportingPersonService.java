package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.ReportingPerson;
import sars.pca.app.persistence.ReportingPersonRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class ReportingPersonService implements ReportingPersonServiceLocal {
    @Autowired
    private ReportingPersonRepository reportingPersonRepository;

    @Override
    public ReportingPerson save(ReportingPerson reportingPerson) {
        return reportingPersonRepository.saveAndFlush(reportingPerson);
    }

    @Override
    public ReportingPerson findById(Long id) {
        return reportingPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public ReportingPerson update(ReportingPerson reportingPerson) {
        return reportingPersonRepository.saveAndFlush(reportingPerson);
    }

    @Override
    public ReportingPerson deleteById(Long id) {
        ReportingPerson reportingPerson = findById(id);

        if (reportingPerson != null) {
            reportingPersonRepository.delete(reportingPerson);
        }
        return reportingPerson;
    }

    @Override
    public List<ReportingPerson> listAll() {
        return reportingPersonRepository.findAll();
    }
}
