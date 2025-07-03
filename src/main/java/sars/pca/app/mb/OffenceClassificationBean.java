package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.service.OffenceClassificationServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class OffenceClassificationBean extends BaseBean<OffenceClassification> {

    @Autowired
    private OffenceClassificationServiceLocal offenceClassificationService;
    private List<CaseType> classificationTypes = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("Offence Classifications");
        addCollections(offenceClassificationService.listAll());
        classificationTypes.addAll(Arrays.asList(CaseType.values()));
    }

    public void addOrUpdate(OffenceClassification offenceClassification) {
        reset().setAdd(true);
        if (offenceClassification != null) {
            setPanelTitleName("Update Offence Classification");
            offenceClassification.setUpdatedBy(getActiveUser().getSid());
            offenceClassification.setUpdatedDate(new Date());
        } else {
            setPanelTitleName("Add Offence Classification");
            offenceClassification = new OffenceClassification();
            offenceClassification.setCreatedBy(getActiveUser().getSid());
            offenceClassification.setCreatedDate(new Date());
            addCollection(offenceClassification);
        }
        addEntity(offenceClassification);
    }

    public void save(OffenceClassification offenceClassification) {
        if (offenceClassification.getId() != null) {
            offenceClassificationService.update(offenceClassification);
            addInformationMessage("Offence Classification was successfully updated.");
        } else {
            offenceClassificationService.save(offenceClassification);
            addInformationMessage("Offence Classification was successfully created.");
        }
        reset().setList(true);
        //setPanelTitleName("User Roles");
    }

    public void cancel(OffenceClassification offenceClassification) {
        if (offenceClassification.getId() == null && getOffenceClassifications().contains(offenceClassification)) {
            removeEntity(offenceClassification);
        }
        reset().setList(true);
        //setPanelTitleName("User Roles");
    }

    public void delete(OffenceClassification offenceClassification) {
        offenceClassificationService.deleteById(offenceClassification.getId());
        removeEntity(offenceClassification);
        addInformationMessage("Offence Classification was successfully deleted");
        reset().setList(true);
        //setPanelTitleName("User Roles");
    }

    public List<OffenceClassification> getOffenceClassifications() {
        return this.getCollections();
    }

    public List<CaseType> getClassificationTypes() {
        return classificationTypes;
    }

    public void setClassificationTypes(List<CaseType> classificationTypes) {
        this.classificationTypes = classificationTypes;
    }

}
