package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.SarCase;

/**
 *
 * @author S2026987
 */
public interface SarCaseServiceLocal {

    SarCase save(SarCase sarCase);

    SarCase findById(Long id);

    SarCase update(SarCase sarCase);

    SarCase deleteById(Long id);

    List<SarCase> listAll();

    boolean isExist(SarCase sarCase);
//
//    SarCase findByCustomExciseCode(String customExciseCode);
//
//    List<SarCase> findByCreatedBy(String createdBy);
//
//
//
//    List<SarCase> findByStatus(SarCaseStatus status);
//
//    List<SarCase> findByStatusNot(SarCaseStatus status);
//
//    List<SarCase> findByStatusAndCrcsLocationOffice(List<SarCaseStatus> status, LocationOffice office);
//
//    List<SarCase> findByStatusAndCrcsLocationOffice(SarCaseStatus status, LocationOffice crcsLocationOffice);
//
//    SarCase findTopByStatusOrderByReferenceAsc(SarCaseStatus status);
//
//    List<SarCase> findByStatusAndUserSid(List<SarCaseStatus> status, String sid);
//
//    List<SarCase> findByStatusAndUserSid(SarCaseStatus status, String sid);
}
