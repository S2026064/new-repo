package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sars.pca.app.domain.Comment;

/**
 *
 * @author S2026987
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
