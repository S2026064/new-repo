package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sars.pca.app.domain.Office;

/**
 *
 * @author S2026987
 */
public interface OfficeRepository extends JpaRepository<Office, Long>{
    
}
