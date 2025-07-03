package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.persistence.SuspiciousCaseRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class SuspiciousCaseService implements SuspiciousCaseServiceLocal {

    @Autowired
    private SuspiciousCaseRepository suspiciousCaseRepository;

    @Override
    public SuspiciousCase save(SuspiciousCase suspiciousCase) {
        return suspiciousCaseRepository.saveAndFlush(suspiciousCase);
    }

    @Override
    public SuspiciousCase findById(Long id) {
        return suspiciousCaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SuspiciousCase update(SuspiciousCase suspiciousCase) {
        return suspiciousCaseRepository.saveAndFlush(suspiciousCase);
    }

    @Override
    public SuspiciousCase deleteById(Long id) {
        SuspiciousCase suspiciousCase = findById(id);

        if (suspiciousCase != null) {
            suspiciousCaseRepository.delete(suspiciousCase);
        }
        return suspiciousCase;
    }

    @Override
    public List<SuspiciousCase> listAll() {
        return suspiciousCaseRepository.findAll();
    }

    @Override
    public List<SuspiciousCase> findByCaseRefNumber(String caseNumber) {
        return suspiciousCaseRepository.findByCaseRefNumber(caseNumber);
    }

    @Override
    public List<SuspiciousCase> findByCreatedBy(String createdBy) {
        return suspiciousCaseRepository.findByCreatedBy(createdBy);
    }

    @Override
    public List<SuspiciousCase> findByStatus(CaseStatus status) {
        return suspiciousCaseRepository.findByStatus(status);
    }

    @Override
    public List<SuspiciousCase> findByStatusAndCrcsLocationOffice(List<CaseStatus> status, LocationOffice crcsLocationOffice) {
        return suspiciousCaseRepository.findByStatusAndCrcsLocationOffice(status, crcsLocationOffice);
    }

    @Override
    public List<SuspiciousCase> findByStatusAndOffices(List<CaseStatus> status, LocationOffice crcsLocationOffice) {
        return suspiciousCaseRepository.findByStatusAndOffices(status, crcsLocationOffice);
    }

    @Override
    public List<SuspiciousCase> findByStatusAndUserSid(List<CaseStatus> status, String sid) {
        return suspiciousCaseRepository.findByStatusAndUserSid(status, sid);
    }

    @Override
    public List<SuspiciousCase> findByStatusAndUserSid(CaseStatus status, String sid) {
        return suspiciousCaseRepository.findByStatusAndUserSid(status, sid);
    }

    @Override
    public List<SuspiciousCase> findByStatusAndCrcsLocationOffice(CaseStatus status, LocationOffice crcsLocationOffice) {
        return suspiciousCaseRepository.findByStatusAndCrcsLocationOffice(status, crcsLocationOffice);
    }

    @Override
    public List<SuspiciousCase> findByStatusNot(CaseStatus status) {
        return suspiciousCaseRepository.findByStatusNot(status);
    }

    @Override
    public SuspiciousCase findLastInsertedSarRecord() {
        return suspiciousCaseRepository.findLastInsertedSarRecord();
    }

//    @Override
//    public SuspiciousCase findTopByStatusOrderByReferenceAsc(CaseStatus status) {
//        return suspiciousCaseRepository.findTopByStatusOrderByReferenceDesc(status);
//    }
    @Override
    public List<SuspiciousCase> findByStatusAndCILocationOffice(List<CaseStatus> status, LocationOffice crcsLocationOffice) {
        return suspiciousCaseRepository.findByStatusAndCILocationOffice(status, crcsLocationOffice);
    }

    @Override
    public List<SuspiciousCase> findByStatusAndCILocationOffice(CaseStatus status, LocationOffice crcsLocationOffice) {
        return suspiciousCaseRepository.findByStatusAndCiLocationOffice(status, crcsLocationOffice);
    }

    @Override
    public SuspiciousCase findByStatusAndCustomsCode(List<CaseStatus> status, String customsCode) {
        return suspiciousCaseRepository.findByStatusAndCustomsCode(status, customsCode);
    }

    @Override
    public SuspiciousCase findSuspCaseByCustomsCodeOrCaseRefNumber(String searchInputParam) {
        return suspiciousCaseRepository.findSuspCaseByCustomsCodeOrCaseRefNumber(searchInputParam);
    }

    @Override
    public SuspiciousCase findTopByStatusOrderByPriorityIndexDesc(CaseStatus status) {
        return suspiciousCaseRepository.findTopByStatusOrderByPriorityIndexDesc(status);
    }

//    @Override
//    public SuspiciousCase findTopByStatusOrderByCreatedDateDesc(CaseStatus status) {
//        return suspiciousCaseRepository.findTopByStatusOrderByCreatedDateDesc(status);
//    }
    @Override
    public SuspiciousCase findTopByStatusAndCiLocationOfficeOrderByPriorityIndexDesc(CaseStatus status, LocationOffice ciLocationOffice) {
        return suspiciousCaseRepository.findTopByStatusAndCiLocationOfficeOrderByPriorityIndexDesc(status, ciLocationOffice);
    }

    @Override
    public SuspiciousCase findByStatusAndCiLocationOfficeOrderByCaseRefNumberDesc(CaseStatus status, LocationOffice ciLocationOffice) {
        return suspiciousCaseRepository.findByStatusAndCiLocationOfficeOrderByCaseRefNumberDesc(status, ciLocationOffice);
    }

//    @Override
//    public SuspiciousCase findTopByStatusOrderByReferenceAsc(CaseStatus status) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    @Override
    public SuspiciousCase findTopByStatusOrderByCaseRefNumberDesc(CaseStatus status) {
        return suspiciousCaseRepository.findTopByStatusOrderByCaseRefNumberDesc(status);
    }
}
