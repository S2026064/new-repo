package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.SlaConfiguration;

/**
 *
 * @author S2026015
 */
public interface SlaConfigurationServiceLocal {
    
    SlaConfiguration save(SlaConfiguration slaConfiguration);

    SlaConfiguration findById(Long id);

    SlaConfiguration update(SlaConfiguration slaConfiguration);

    SlaConfiguration deleteById(Long id);

    List<SlaConfiguration> listAll();

    boolean isExist(SlaConfiguration slaConfiguration);
}
