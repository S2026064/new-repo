package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.SarCase;

/**
 *
 * @author S2026987
 */
@Repository
public interface SarCaseRepository extends JpaRepository<SarCase, Long> {
//
//    @EntityGraph(value = "sarcase-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
//    SarCase findByCustomExciseCode(String customExciseCode);
//
//    List<SarCase> findByCreatedBy(String createdBy);
//
//    List<SarCase> findByStatus(SarCaseStatus status);
//
//    @Query("SELECT e FROM SarCase e WHERE e.status in(:statuses) AND e.crcsLocationOffice=:office")
//    List<SarCase> findByStatusAndCrcsLocationOffice(@Param("statuses") List<SarCaseStatus> status, @Param("office") LocationOffice crcsLocationOffice);
//
//    @Query("SELECT e FROM SarCase e WHERE e.ciLocationOffice IS NOT NULL OR  (e.status in(:statuses) AND e.crcsLocationOffice=:office)")
//    List<SarCase> findByStatusAndOffices(@Param("statuses") List<SarCaseStatus> status, @Param("office") LocationOffice crcsLocationOffice);
//
//    @Query("SELECT e FROM SarCase e inner join e.sarCaseUsers su WHERE e.status in(:statuses) AND su.user.sid=:userSid")
//    List<SarCase> findByStatusAndUserSid(@Param("statuses") List<SarCaseStatus> status, @Param("userSid") String sid);
//
//    @Query("SELECT e FROM SarCase e inner join e.sarCaseUsers su WHERE e.status=:sarCaseStatus AND su.user.sid=:userSid")
//    List<SarCase> findByStatusAndUserSid(@Param("sarCaseStatus") SarCaseStatus status, @Param("userSid") String sid);
//
//    List<SarCase> findByStatusAndCrcsLocationOffice(SarCaseStatus status, LocationOffice crcsLocationOffice);
//
//    List<SarCase> findByStatusNot(SarCaseStatus status);
//
//    SarCase findTopByStatusOrderByReferenceAsc(SarCaseStatus status);
}
