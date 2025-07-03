package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.ReportedPerson;
import sars.pca.app.persistence.ReportedPersonRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class ReportedPersonService implements ReportedPersonServiceLocal {

    @Autowired
    private ReportedPersonRepository reportedPersonRepository;

    @Override
    public ReportedPerson save(ReportedPerson reportedPerson) {
        return reportedPersonRepository.save(reportedPerson);
    }

    @Override
    public ReportedPerson findById(Long id) {
        return reportedPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public ReportedPerson update(ReportedPerson reportedPerson) {
        return reportedPersonRepository.save(reportedPerson);
    }

    @Override
    public ReportedPerson deleteById(Long id) {
        ReportedPerson reportedPerson = findById(id);

        if (reportedPerson != null) {
            reportedPersonRepository.delete(reportedPerson);
        }
        return reportedPerson;
    }

    @Override
    public List<ReportedPerson> listAll() {
        return reportedPersonRepository.findAll();
    }
}
