package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.EntityTypeDetails;

/**
 *
 * @author S2026987
 */
public interface EntityTypeDetailsServiceLocal {

    EntityTypeDetails save(EntityTypeDetails entityTypeDetails);

    EntityTypeDetails findById(Long id);

    EntityTypeDetails update(EntityTypeDetails entityTypeDetails);

    EntityTypeDetails deleteById(Long id);

    List<EntityTypeDetails> listAll();
}
