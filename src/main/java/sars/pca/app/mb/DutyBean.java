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
import sars.pca.app.common.DutyType;
import sars.pca.app.domain.Duty;
import sars.pca.app.service.DutyServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class DutyBean extends BaseBean<Duty> {
    @Autowired
    private DutyServiceLocal dutyService;
    private List<DutyType> dutyTypes = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("List of Duties");
        addCollections(dutyService.listAll());
        dutyTypes.addAll(Arrays.asList(DutyType.values()));
    }
    public void addOrUpdate(Duty duty) {
        reset().setAdd(true);
        if (duty != null) {
            setPanelTitleName("Update Duty");
            duty.setUpdatedBy(getActiveUser().getSid());
            duty.setUpdatedDate(new Date());
        } else {
            duty = new Duty();
            setPanelTitleName("Add Duty");
            duty.setCreatedBy(getActiveUser().getSid());
            duty.setCreatedDate(new Date());
            addCollection(duty);
        }
        addEntity(duty);
    }

    public void save(Duty duty) {
        if (duty.getId() != null) {
            dutyService.update(duty);
            addInformationMessage("Duty was successfully updated");
        } else {
            dutyService.save(duty);
            addInformationMessage("Duty successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("List of Duties");
    }

    public void cancel(Duty duty) {
        if (duty.getId() == null && getDuties().contains(duty)) {
            removeEntity(duty);
        }
        reset().setList(true);
    }

    public void delete(Duty duty) {
        try {
            dutyService.deleteById(duty.getId());
            removeEntity(duty);
            addInformationMessage("Duty was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<Duty> getDuties() {
        return this.getCollections();
    }

    public List<DutyType> getDutyTypes() {
        return dutyTypes;
    }

    public void setDutyTypes(List<DutyType> dutyTypes) {
        this.dutyTypes = dutyTypes;
    }  
    
}
