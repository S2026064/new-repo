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
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.service.LocationOfficeServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class LocationOfficeBean extends BaseBean<LocationOffice> {
    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;
    private List<Province> provinces;
    private List<OfficeType> officeTypes;

    @PostConstruct
    public void init() {
        provinces = new ArrayList<>();
        officeTypes = new ArrayList<>();
        reset().setList(true);
        setPanelTitleName("Offices");
        addCollections(locationOfficeService.listAll());
        provinces.addAll(Arrays.asList(Province.values()));
        officeTypes.addAll(Arrays.asList(OfficeType.values()));
    }

    public void addOrUpdate(LocationOffice locationOffice) {
        reset().setAdd(true);
        if (locationOffice != null) {
            setPanelTitleName("Update Office");
            locationOffice.setUpdatedBy(getActiveUser().getSid());
            locationOffice.setUpdatedDate(new Date());
        } else {
            locationOffice = new LocationOffice();
            setPanelTitleName("Add Office");
            locationOffice.setCreatedBy(getActiveUser().getSid());
            locationOffice.setCreatedDate(new Date());
            addCollection(locationOffice);
        }
        addEntity(locationOffice);

    }

    public void save(LocationOffice locationOffice) {
        if (locationOffice.getId() != null) {
            locationOfficeService.update(locationOffice);
            addInformationMessage("Location Office was successfully updated");
        } else {
            locationOfficeService.save(locationOffice);
            addInformationMessage("Location Office was successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("Offices");
    }

    public void cancel(LocationOffice locationOffice) {
        if (locationOffice.getId() == null && getLocationOffice().contains(locationOffice)) {
            removeEntity(locationOffice);
        }
        reset().setList(true);

    }

    public void delete(LocationOffice locationOffice) {
        try {
            locationOfficeService.deleteById(locationOffice.getId());
            removeEntity(locationOffice);
            addInformationMessage("Location Office was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }

    }

    public List<LocationOffice> getLocationOffices() {
        return this.getCollections();
    }

    public List<LocationOffice> getLocationOffice() {
        return this.getCollections();
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<OfficeType> getOfficeTypes() {
        return officeTypes;
    }

    public void setOfficeTypes(List<OfficeType> officeTypes) {
        this.officeTypes = officeTypes;
    }

}
