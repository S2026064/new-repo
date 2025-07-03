package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.VddlStatus;
import sars.pca.app.domain.VddLedCase;

/**
 *
 * @author S2026987
 */
@Repository
public interface VddLedRepository extends JpaRepository<VddLedCase, Long> {

//    VddLedCase findByCaseRefNumber(String caseRefNumber);
//
//    VddLedCase findByCaseRefNumberAndVddlStatus(String caseRefNumber, VddlStatus vddlStatus);
//
//    List<VddLedCase> findByVddlStatus(VddlStatus vddlStatus);
//
//    @Query(value = "  SELECT TOP (1) * FROM vddl ORDER BY case_ref_number DESC", nativeQuery = true)
//    VddLedCase findLastInsertedVddlRecord();
}
