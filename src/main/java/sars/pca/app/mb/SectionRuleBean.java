package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.service.OffenceClassificationServiceLocal;
import sars.pca.app.service.SectionRuleServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SectionRuleBean extends BaseBean<SectionRule> {

    @Autowired
    private SectionRuleServiceLocal sectionRuleService;
    @Autowired
    private OffenceClassificationServiceLocal offenceClassificationService;
    private List<OffenceClassification> officeClassifications = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("Section Rules");
        addCollections(sectionRuleService.listAll());
        officeClassifications.addAll(offenceClassificationService.listAll());
    }

    public void addOrUpdate(SectionRule sectionRule) {
        reset().setAdd(true);
        if (sectionRule != null) {
            setPanelTitleName("Update Section Rules");
            sectionRule.setUpdatedBy(getActiveUser().getSid());
            sectionRule.setUpdatedDate(new Date());
        } else {
            setPanelTitleName("Add Section Rules");
            sectionRule = new SectionRule();
            sectionRule.setCreatedBy(getActiveUser().getSid());
            sectionRule.setCreatedDate(new Date());
            addCollection(sectionRule);
        }
        addEntity(sectionRule);
    }

    public void save(SectionRule sectionRule) {
        if (sectionRule.getId() != null) {
            sectionRuleService.update(sectionRule);
            addInformationMessage("Section Rule was successfully updated.");
        } else {
            sectionRuleService.save(sectionRule);
            addInformationMessage("Section Rule was successfully created.");
        }
        reset().setList(true);
    }
    public void cancel(SectionRule sectionRule) {
        if (sectionRule.getId() == null && getSectionRules().contains(sectionRule)) {
            removeEntity(sectionRule);
        }
        reset().setList(true);
    }

    public void delete(SectionRule sectionRule) {
        sectionRuleService.deleteById(sectionRule.getId());
        removeEntity(sectionRule);
        addInformationMessage("Section Rule was successfully deleted");
        reset().setList(true);
    }

    public List<SectionRule> getSectionRules() {
        return this.getCollections();
    }

    public List<OffenceClassification> getOfficeClassifications() {
        return officeClassifications;
    }

    public void setOfficeClassifications(List<OffenceClassification> officeClassifications) {
        this.officeClassifications = officeClassifications;
    }

}
