package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.Office;

/**
 *
 * @author S2026987
 */
public interface OfficeServiceLocal {
    
    Office save(Office office);

    Office findById(Long id);

    Office update(Office office);

    Office deleteById(Long id);

    List<Office> listAll();

    boolean isExist(Office office);    
}
