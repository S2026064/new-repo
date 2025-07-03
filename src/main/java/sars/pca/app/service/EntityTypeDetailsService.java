package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.EntityTypeDetails;
import sars.pca.app.persistence.EntityTypeDetailsRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class EntityTypeDetailsService implements EntityTypeDetailsServiceLocal {

    @Autowired
    private EntityTypeDetailsRepository entityTypeDetailsRepository;

    @Override
    public EntityTypeDetails save(EntityTypeDetails entityTypeDetails) {
        return entityTypeDetailsRepository.save(entityTypeDetails);
    }

    @Override
    public EntityTypeDetails findById(Long id) {
        return entityTypeDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public EntityTypeDetails update(EntityTypeDetails attachment) {
        return entityTypeDetailsRepository.save(attachment);
    }

    @Override
    public EntityTypeDetails deleteById(Long id) {
        EntityTypeDetails entityTypeDetails = findById(id);

        if (entityTypeDetails != null) {
            entityTypeDetailsRepository.delete(entityTypeDetails);
        }
        return entityTypeDetails;
    }
    @Override
    public List<EntityTypeDetails> listAll() {
        return entityTypeDetailsRepository.findAll();
    }
}
