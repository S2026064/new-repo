package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.OffenceClassificationType;
import sars.pca.app.common.OffenceDescriptionType;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.service.OffenceAndPenaltyServiceLocal;

/**
 *
 * @author S2026064
 */
@ManagedBean
@ViewScoped
public class OffenceAndPenaltyBean extends BaseBean<OffenceAndPenalty> {
    @Autowired
    private OffenceAndPenaltyServiceLocal offenceAndPenaltyService;
    private List<OffenceDescriptionType> OffenceDescriptionTypes;
    private List<OffenceClassificationType> offenceClassificationTypes;

    @PostConstruct
    public void init() {
        OffenceDescriptionTypes = new ArrayList<>();
        offenceClassificationTypes = new ArrayList<>();
        reset().setList(true);
        setPanelTitleName("Offence Classification");
        addCollections(offenceAndPenaltyService.listAll());
        OffenceDescriptionTypes.addAll(Arrays.asList(OffenceDescriptionType.values()));
        offenceClassificationTypes.addAll(Arrays.asList(OffenceClassificationType.values()));
    }
    public void addOrUpdate(OffenceAndPenalty offenceAndPenalty) {
        reset().setAdd(true);
        if (offenceAndPenalty != null) {
            setPanelTitleName("Update Offence Classification ");
            offenceAndPenalty.setUpdatedBy(getActiveUser().getSid());
            offenceAndPenalty.setUpdatedDate(new Date());

        } else {
            offenceAndPenalty = new OffenceAndPenalty();
            setPanelTitleName("Add Offence Classification ");
            offenceAndPenalty.setCreatedBy(getActiveUser().getSid());
            offenceAndPenalty.setCreatedDate(new Date());
            addCollection(offenceAndPenalty);
        }
        addEntity(offenceAndPenalty);
    }
    public void save(OffenceAndPenalty classification) {
        if (classification.getId() != null) {
            offenceAndPenaltyService.update(classification);
            addInformationMessage("Offence Classification was successfully updated");
        } else {
            offenceAndPenaltyService.save(classification);
            addInformationMessage("Offence Classification was successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("Offences");
    }
    public void cancel(OffenceAndPenalty offenceAndPenalty) {
        if (offenceAndPenalty.getId() == null && getOffenceAndPenalties().contains(offenceAndPenalty)) {
            removeEntity(offenceAndPenalty);
        }
        reset().setList(true);
    }

    public void delete(OffenceAndPenalty offenceAndPenalty) {
        try {
            offenceAndPenaltyService.deleteById(offenceAndPenalty.getId());
            removeEntity(offenceAndPenalty);
            addInformationMessage("Offence Classification was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<OffenceDescriptionType> getOffenceDescriptionTypes() {
        return OffenceDescriptionTypes;
    }

    public void setOffenceDescriptionTypes(List<OffenceDescriptionType> OffenceDescriptionTypes) {
        this.OffenceDescriptionTypes = OffenceDescriptionTypes;
    }

    public List<OffenceClassificationType> getOffenceClassificationTypes() {
        return offenceClassificationTypes;
    }

    public void setOffenceClassificationTypes(List<OffenceClassificationType> offenceClassificationTypes) {
        this.offenceClassificationTypes = offenceClassificationTypes;
    }

    public List<OffenceAndPenalty> getOffenceAndPenalties() {
        return this.getCollections();
    }
}
