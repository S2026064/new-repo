package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SuspiciousCase;

/**
 *
 * @author S2026987
 */
public interface SuspiciousCaseServiceLocal {

    SuspiciousCase save(SuspiciousCase suspiciousCase);

    SuspiciousCase findById(Long id);

    SuspiciousCase update(SuspiciousCase suspiciousCase);

    SuspiciousCase deleteById(Long id);

    List<SuspiciousCase> listAll();

    List<SuspiciousCase> findByCaseRefNumber(String caseNumber);

    List<SuspiciousCase> findByCreatedBy(String createdBy);

    List<SuspiciousCase> findByStatus(CaseStatus status);

    List<SuspiciousCase> findByStatusAndCrcsLocationOffice(List<CaseStatus> status, LocationOffice crcsLocationOffice);

    List<SuspiciousCase> findByStatusAndCILocationOffice(List<CaseStatus> status, LocationOffice crcsLocationOffice);

    List<SuspiciousCase> findByStatusAndOffices(List<CaseStatus> status, LocationOffice crcsLocationOffice);

    List<SuspiciousCase> findByStatusAndUserSid(List<CaseStatus> status, String sid);

    List<SuspiciousCase> findByStatusAndUserSid(CaseStatus status, String sid);

    List<SuspiciousCase> findByStatusAndCrcsLocationOffice(CaseStatus status, LocationOffice crcsLocationOffice);

    List<SuspiciousCase> findByStatusAndCILocationOffice(CaseStatus status, LocationOffice crcsLocationOffice);

    List<SuspiciousCase> findByStatusNot(CaseStatus status);

    SuspiciousCase findLastInsertedSarRecord();

//    SuspiciousCase findTopByStatusOrderByReferenceAsc(CaseStatus status);
    SuspiciousCase findByStatusAndCustomsCode(List<CaseStatus> status, String customsCode);

    SuspiciousCase findSuspCaseByCustomsCodeOrCaseRefNumber(String searchInputParam);

    SuspiciousCase findTopByStatusOrderByPriorityIndexDesc(CaseStatus status);

    SuspiciousCase findTopByStatusAndCiLocationOfficeOrderByPriorityIndexDesc(CaseStatus status, LocationOffice ciLocationOffice);

    SuspiciousCase findByStatusAndCiLocationOfficeOrderByCaseRefNumberDesc(CaseStatus status, LocationOffice ciLocationOffice);

    SuspiciousCase findTopByStatusOrderByCaseRefNumberDesc(CaseStatus status);
}
