package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.persistence.RelevantMaterialRepository;

/**
 *
 * @author S2026015
 */
@Service
@Transactional
public class RelevantMaterialService implements RelevantMaterialServiceLocal{
    
    @Autowired
    private RelevantMaterialRepository relevantMaterialRepository;

    @Override
    public RelevantMaterial save(RelevantMaterial relevantMaterial) {
       return relevantMaterialRepository.save(relevantMaterial);
    }

    @Override
    public RelevantMaterial findById(Long id) {
        return relevantMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public RelevantMaterial update(RelevantMaterial relevantMaterial) {
        return relevantMaterialRepository.save(relevantMaterial);
    }

    @Override
    public RelevantMaterial deleteById(Long id) {
         RelevantMaterial relevantMaterial = findById(id);
        if (relevantMaterial != null) {
            relevantMaterialRepository.delete(relevantMaterial);
        }
        return relevantMaterial;
    }

    @Override
    public List<RelevantMaterial> listAll() {
        return relevantMaterialRepository.findAll();
    }
    
}
