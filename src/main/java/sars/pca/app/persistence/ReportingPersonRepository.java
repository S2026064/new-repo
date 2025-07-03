package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.ReportingPerson;

/**
 *
 * @author S2026987
 */
@Repository
public interface ReportingPersonRepository extends JpaRepository<ReportingPerson, Long>{

}
