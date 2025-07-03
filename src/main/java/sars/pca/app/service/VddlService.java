package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.VddlStatus;
import sars.pca.app.domain.User;
import sars.pca.app.domain.VddLedCase;
import sars.pca.app.persistence.VddLedRepository;

/**
 *
 * @author S2026015
 */
@Service
@Transactional
public class VddlService implements VddlServiceLocal {

    @Autowired
    private VddLedRepository vddlRepository;

    @Override
    public VddLedCase save(VddLedCase vddl) {
        return vddlRepository.save(vddl);
    }

    @Override
    public VddLedCase findById(Long id) {
        return vddlRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public VddLedCase update(VddLedCase vddl) {
        return vddlRepository.save(vddl);

    }

    @Override
    public VddLedCase deleteById(Long id) {
        VddLedCase vddl = findById(id);

        if (vddl != null) {
            vddlRepository.delete(vddl);
        }
        return vddl;

    }

    @Override
    public List<VddLedCase> listAll() {
        return vddlRepository.findAll();
    }

    @Override
    public boolean isExist(VddLedCase vddl) {
        return vddlRepository.findById(vddl.getId()) != null;
    }
//
//    @Override
//    public VddLedCase findByCaseRefNumber(String caseRefNumber) {
//        return vddlRepository.findByCaseRefNumber(caseRefNumber);
//    }
//
//    @Override
//    public VddLedCase findByCaseRefNumberAndVddlStatus(String caseRefNumber, VddlStatus vddlStatus) {
//        return vddlRepository.findByCaseRefNumberAndVddlStatus(caseRefNumber, vddlStatus);
//    }
//    @Override
//    public List<VddLedCase> findByVddlStatus(VddlStatus vddlStatus) {
//        return vddlRepository.findByVddlStatus(vddlStatus);
//    }
//
//    @Override
//    public VddLedCase findLastInsertedVddlRecord() {
//        return vddlRepository.findLastInsertedVddlRecord();
//    }

}
