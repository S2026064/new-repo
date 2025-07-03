package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.service.OffenceDescriptionServiceLocal;
import sars.pca.app.service.SectionRuleServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class OffenceDescriptionBean extends BaseBean<OffenceDescription> {

    @Autowired
    private OffenceDescriptionServiceLocal offenceDescriptionService;
    @Autowired
    private SectionRuleServiceLocal sectionRuleService;
    private List<SectionRule> sectionRules = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("Offence Descriptions");
        addCollections(offenceDescriptionService.listAll());
        sectionRules.addAll(sectionRuleService.listAll());
    }

    public void addOrUpdate(OffenceDescription offenceDescription) {
        reset().setAdd(true);
        if (offenceDescription != null) {
            setPanelTitleName("Update Offence Description");
            offenceDescription.setUpdatedBy(getActiveUser().getSid());
            offenceDescription.setUpdatedDate(new Date());
        } else {
            setPanelTitleName("Add Offence Description");
            offenceDescription = new OffenceDescription();
            offenceDescription.setCreatedBy(getActiveUser().getSid());
            offenceDescription.setCreatedDate(new Date());

            addCollection(offenceDescription);
        }
        addEntity(offenceDescription);
    }

    public void save(OffenceDescription offenceDescription) {
        if (offenceDescription.getId() != null) {
            offenceDescriptionService.update(offenceDescription);
            addInformationMessage("Offence Description was successfully updated.");
        } else {
            offenceDescriptionService.save(offenceDescription);
            addInformationMessage("Offence Description was successfully created.");
        }
        reset().setList(true);
    }

    public void cancel(OffenceDescription offenceDescription) {
        if (offenceDescription.getId() == null && getOffenceDescriptions().contains(offenceDescription)) {
            removeEntity(offenceDescription);
        }
        reset().setList(true);
        setPanelTitleName("User Roles");
    }

    public void delete(OffenceDescription offenceDescription) {
        offenceDescriptionService.deleteById(offenceDescription.getId());
        removeEntity(offenceDescription);
        addInformationMessage("User Role was successfully deleted");
        reset().setList(true);
        setPanelTitleName("User Roles");
    }

    public List<OffenceDescription> getOffenceDescriptions() {
        return this.getCollections();
    }

    public List<SectionRule> getSectionRules() {
        return sectionRules;
    }

    public void setSectionRules(List<SectionRule> sectionRules) {
        this.sectionRules = sectionRules;
    }

}
