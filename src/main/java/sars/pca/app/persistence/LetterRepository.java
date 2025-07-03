package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.Letter;

/**
 *
 * @author S2026015
 */
@Repository
public interface LetterRepository extends JpaRepository<Letter, Long>{
    
}
