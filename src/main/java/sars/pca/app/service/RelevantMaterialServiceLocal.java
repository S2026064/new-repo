package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.RelevantMaterial;

/**
 *
 * @author S2026015
 */
public interface RelevantMaterialServiceLocal {
    
    RelevantMaterial save(RelevantMaterial relevantMaterial);
    
    RelevantMaterial findById(Long id);
    
    RelevantMaterial update(RelevantMaterial relevantMaterial);
    
    RelevantMaterial deleteById(Long id);
    
    List<RelevantMaterial> listAll();
}
