package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.DutyType;
import sars.pca.app.domain.Duty;

/**
 *
 * @author S2026987
 */
@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {
    List<Duty> findByDutyType(DutyType dutyType);
}
