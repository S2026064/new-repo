package sars.pca.app.mb;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.service.RelevantMaterialServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class RelevantMaterialBean extends BaseBean<RelevantMaterial> {

    @Autowired
    private RelevantMaterialServiceLocal relevantMaterialService;

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("Relevant Materials");
        addCollections(relevantMaterialService.listAll());
    }

    public void addOrUpdate(RelevantMaterial relevantMaterial) {
        reset().setAdd(true);
        if (relevantMaterial != null) {
            setPanelTitleName("Update Relevant Material");
            relevantMaterial.setUpdatedBy(getActiveUser().getSid());
            relevantMaterial.setUpdatedDate(new Date());
        } else {
            relevantMaterial = new RelevantMaterial();
            setPanelTitleName("Add Relevant Material");
            relevantMaterial.setCreatedBy(getActiveUser().getSid());
            relevantMaterial.setCreatedDate(new Date());
            addCollection(relevantMaterial);
        }
        addEntity(relevantMaterial);
    }

    public void save(RelevantMaterial relevantMaterial) {
        if (relevantMaterial.getId() != null) {
            relevantMaterialService.update(relevantMaterial);
            addInformationMessage("Relevant Material was successfully updated");
        } else {
            relevantMaterialService.save(relevantMaterial);
            addInformationMessage("Relevent Material successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("Relevant Materials");
    }

    public void cancel(RelevantMaterial relevantMaterial) {
        if (relevantMaterial.getId() == null && getRelevantMaterials().contains(relevantMaterial)) {
            removeEntity(relevantMaterial);
        }
        reset().setList(true);
    }

    public void delete(RelevantMaterial relevantMaterial) {
        try {
            relevantMaterialService.deleteById(relevantMaterial.getId());
            removeEntity(relevantMaterial);
            addInformationMessage("Relevant Material was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<RelevantMaterial> getRelevantMaterials() {
        return this.getCollections();
    }
}
