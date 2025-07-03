package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.SarCase;
import sars.pca.app.persistence.SarCaseRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class SarCaseService implements SarCaseServiceLocal {

    @Autowired
    private SarCaseRepository sarCaseRepository;

    @Override
    public SarCase save(SarCase sarCase) {
        return sarCaseRepository.saveAndFlush(sarCase);
    }

    @Override
    public SarCase findById(Long id) {
        return sarCaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SarCase update(SarCase sarCase) {
        return sarCaseRepository.saveAndFlush(sarCase);
    }

    @Override
    public SarCase deleteById(Long id) {
        SarCase sarCase = findById(id);

        if (sarCase != null) {
            sarCaseRepository.delete(sarCase);
        }
        return sarCase;
    }

    @Override
    public List<SarCase> listAll() {
        return sarCaseRepository.findAll();
    }

    @Override
    public boolean isExist(SarCase sarCase) {
        return false;
    }

//    @Override
//    public SarCase findByCustomExciseCode(String customExciseCode) {
//        return sarCaseRepository.findByCustomExciseCode(customExciseCode);
//    }
//
//    @Override
//    public List<SarCase> findByCreatedBy(String createdBy) {
//        return sarCaseRepository.findByCreatedBy(createdBy);
//    }
//
//    @Override
//    public List<SarCase> findByStatus(SarCaseStatus status) {
//        return sarCaseRepository.findByStatus(status);
//    }
//
//    @Override
//    public List<SarCase> findByStatusNot(SarCaseStatus status) {
//        return sarCaseRepository.findByStatusNot(status);
//    }
//
//    @Override
//    public List<SarCase> findByStatusAndCrcsLocationOffice(List<SarCaseStatus> status, LocationOffice office) {
//        return sarCaseRepository.findByStatusAndCrcsLocationOffice(status, office);
//    }
//
//    @Override
//    public List<SarCase> findByStatusAndCrcsLocationOffice(SarCaseStatus status, LocationOffice crcsLocationOffice) {
//        return sarCaseRepository.findByStatusAndCrcsLocationOffice(status, crcsLocationOffice);
//    }
//
//    @Override
//    public SarCase findTopByStatusOrderByReferenceAsc(SarCaseStatus status) {
//        return sarCaseRepository.findTopByStatusOrderByReferenceAsc(status);
//    }
//
//    @Override
//    public List<SarCase> findByStatusAndUserSid(List<SarCaseStatus> statuses, String sid) {
//        return sarCaseRepository.findByStatusAndUserSid(statuses, sid);
//    }
//
//    @Override
//    public List<SarCase> findByStatusAndUserSid(SarCaseStatus status, String sid) {
//        return sarCaseRepository.findByStatusAndUserSid(status, sid);
//    }
}
