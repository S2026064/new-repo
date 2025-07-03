package sars.pca.app.mb;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.service.SlaConfigurationServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SlaConfigBean extends BaseBean<SlaConfiguration> {

    @Autowired
    private SlaConfigurationServiceLocal configurationService;
    private boolean moreThanOneSlaConfig;

    @PostConstruct
    public void init() {
        reset().setAdd(true);
        addCollections(configurationService.listAll());
        if (getCollections().isEmpty()) {
            SlaConfiguration slaConfiguration = new SlaConfiguration();
            slaConfiguration.setCreatedBy(getActiveUser().getSid());
            slaConfiguration.setCreatedDate(new Date());
            addCollection(slaConfiguration);
            addEntity(slaConfiguration);
        } else {
            if (getCollections().size() > 1) {
                addWarningMessage("The can not be more than one SLA Configuration");
            } else {
                addEntity(getCollections().get(0));
            }
        }
    }

    public void save(SlaConfiguration slaConfiguration) {
        if (slaConfiguration.getId() != null) {
            configurationService.update(slaConfiguration);
            addInformationMessage("SLA Configuration was successfully updated");
        } else {
            configurationService.save(slaConfiguration);
            addInformationMessage("SLA Configuration was successfully saved");
        }
        reset().setAdd(true);
    }

    public void delete(SlaConfiguration slaConfiguration) {
        configurationService.deleteById(slaConfiguration.getId());
        removeEntity(slaConfiguration);
        addInformationMessage("SLA Configuration was successfully deleted");
        reset().setAdd(true);
    }

    public void cancel(SlaConfiguration slaConfiguration) {
        if (slaConfiguration.getId() == null && getSlaConfigurations().contains(slaConfiguration)) {
            removeEntity(slaConfiguration);
        }
        reset().setAdd(true);
    }

    public List<SlaConfiguration> getSlaConfigurations() {
        return this.getCollections();
    }

    public boolean isMoreThanOneSlaConfig() {
        return moreThanOneSlaConfig;
    }

    public void setMoreThanOneSlaConfig(boolean moreThanOneSlaConfig) {
        this.moreThanOneSlaConfig = moreThanOneSlaConfig;
    }
}
