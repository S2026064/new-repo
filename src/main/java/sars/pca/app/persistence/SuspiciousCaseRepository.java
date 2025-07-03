package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.SuspiciousCase;

/**
 *
 * @author S2026987
 */
@Repository
public interface SuspiciousCaseRepository extends JpaRepository<SuspiciousCase, Long> {

    List<SuspiciousCase> findByCaseRefNumber(String caseNumber);

    List<SuspiciousCase> findByCreatedBy(String createdBy);

    List<SuspiciousCase> findByStatus(CaseStatus status);

    @Query("SELECT e FROM SuspiciousCase e WHERE e.status in(:statuses) AND e.crcsLocationOffice=:office order by e.priorityIndex desc")
    List<SuspiciousCase> findByStatusAndCrcsLocationOffice(@Param("statuses") List<CaseStatus> status, @Param("office") LocationOffice crcsLocationOffice);

    @Query("SELECT e FROM SuspiciousCase e WHERE e.status in(:statuses) AND e.ciLocationOffice=:office")
    List<SuspiciousCase> findByStatusAndCILocationOffice(@Param("statuses") List<CaseStatus> status, @Param("office") LocationOffice ciLocationOffice);

    @Query("SELECT e FROM SuspiciousCase e WHERE e.ciLocationOffice IS NOT NULL OR  (e.status in(:statuses) AND e.crcsLocationOffice=:office)")
    List<SuspiciousCase> findByStatusAndOffices(@Param("statuses") List<CaseStatus> status, @Param("office") LocationOffice crcsLocationOffice);

    @Query("SELECT e FROM SuspiciousCase e inner join e.sarCaseUsers su WHERE e.status in(:statuses) AND su.user.sid=:userSid")
    List<SuspiciousCase> findByStatusAndUserSid(@Param("statuses") List<CaseStatus> status, @Param("userSid") String sid);

    @Query("SELECT e FROM SuspiciousCase e WHERE e.status in(:statuses) AND e.ibrData.ibrCustomsNumber=:customsCode")
    SuspiciousCase findByStatusAndCustomsCode(@Param("statuses") List<CaseStatus> status, @Param("customsCode") String customsCode);

    @Query("SELECT e FROM SuspiciousCase e inner join e.sarCaseUsers su WHERE e.status=:sarCaseStatus AND su.user.sid=:userSid")
    List<SuspiciousCase> findByStatusAndUserSid(@Param("sarCaseStatus") CaseStatus status, @Param("userSid") String sid);

    List<SuspiciousCase> findByStatusAndCrcsLocationOffice(CaseStatus status, LocationOffice crcsLocationOffice);

    List<SuspiciousCase> findByStatusAndCiLocationOffice(CaseStatus status, LocationOffice ciLocationOffice);

    SuspiciousCase findByStatusAndCiLocationOfficeOrderByCaseRefNumberDesc(CaseStatus status, LocationOffice ciLocationOffice);

    List<SuspiciousCase> findByStatusNot(CaseStatus status);

    SuspiciousCase findTopByStatusOrderByCaseRefNumberDesc(CaseStatus status);

    //Check if we can select a column instead of the whole object
    // @EntityGraph(attributePaths = "reference", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT TOP (1) * FROM suspicious_case ORDER BY case_ref_num DESC", nativeQuery = true)
    SuspiciousCase findLastInsertedSarRecord();

    SuspiciousCase findTopByStatusAndCiLocationOfficeOrderByPriorityIndexDesc(CaseStatus status, LocationOffice ciLocationOffice);

    @Query("SELECT e FROM SuspiciousCase e WHERE e.ibrData.ibrCustomsNumber=:searchParm OR e.caseRefNumber=:searchParm")
    SuspiciousCase findSuspCaseByCustomsCodeOrCaseRefNumber(@Param("searchParm") String searchInputParam);

    SuspiciousCase findTopByStatusOrderByPriorityIndexDesc(CaseStatus status);

    //SuspiciousCase findTopByStatusOrderByCreatedDateDesc(CaseStatus status);
}
