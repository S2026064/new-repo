package sars.pca.app.mb;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.service.HsChapterRiskServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class HsChapterRiskBean extends BaseBean<HsChapterRisk> {

    @Autowired
    private HsChapterRiskServiceLocal hsChapterRiskService;

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("HS Chapter Risks");
        addCollections(hsChapterRiskService.listAll());
    }

    public void addOrUpdate(HsChapterRisk hsChapterRisk) {
        reset().setAdd(true);
        if (hsChapterRisk != null) {
            setPanelTitleName("Update HS Chapter Risk");
            hsChapterRisk.setUpdatedBy(getActiveUser().getSid());
            hsChapterRisk.setUpdatedDate(new Date());
        } else {
            hsChapterRisk = new HsChapterRisk();
            setPanelTitleName("Add HS Chapter Risk");
            hsChapterRisk.setCreatedBy(getActiveUser().getSid());
            hsChapterRisk.setCreatedDate(new Date());
            addCollection(hsChapterRisk);
        }
        addEntity(hsChapterRisk);
    }
    public void save(HsChapterRisk hsChapterRisk) {
        if (hsChapterRisk.getId() != null) {
            hsChapterRiskService.update(hsChapterRisk);
            addInformationMessage("HS Chapter Risk was successfully updated");
        } else {
            hsChapterRiskService.save(hsChapterRisk);
            addInformationMessage("HS Chapter Risk was successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("HS Chapter Risks");
    }
    public void cancel(HsChapterRisk hsChapterRisk) {
        if (hsChapterRisk.getId() == null && getHsChapterRisks().contains(hsChapterRisk)) {
            removeEntity(hsChapterRisk);
        }
        reset().setList(true);

    }
    public void delete(HsChapterRisk hsChapterRisk) {
        try {
            hsChapterRiskService.deleteById(hsChapterRisk.getId());
            removeEntity(hsChapterRisk);
            addInformationMessage("HS Chapter Risk was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
    public List<HsChapterRisk> getHsChapterRisks() {
        return this.getCollections();
    }
}
