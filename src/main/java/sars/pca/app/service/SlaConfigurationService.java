package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.persistence.SlaConfigurationRepository;

/**
 *
 * @author S2026015
 */
@Service
@Transactional
public class SlaConfigurationService implements SlaConfigurationServiceLocal{
    
    @Autowired
    private SlaConfigurationRepository slaConfigurationRepository;

    @Override
    public SlaConfiguration save(SlaConfiguration slaConfiguration) {
         return slaConfigurationRepository.save(slaConfiguration);
        
    }

    @Override
    public SlaConfiguration findById(Long id) {
       return slaConfigurationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SlaConfiguration update(SlaConfiguration slaConfiguration) {
       return slaConfigurationRepository.save(slaConfiguration);
    }

    @Override
    public SlaConfiguration deleteById(Long id) {
        SlaConfiguration slaConfiguration = findById(id);

        if (slaConfiguration != null) {
            slaConfigurationRepository.delete(slaConfiguration);
        }
        return slaConfiguration;
       
    }

    @Override
    public List<SlaConfiguration> listAll() {
         return slaConfigurationRepository.findAll();
    }

    @Override
    public boolean isExist(SlaConfiguration slaConfiguration) {
        return slaConfigurationRepository.findById(slaConfiguration.getId()) != null;
    }
    
}
