package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import sars.pca.app.common.AddressType;
import sars.pca.app.common.CountryUtility;
import sars.pca.app.common.Province;
import sars.pca.app.domain.Address;
import sars.pca.app.domain.EntityTypeDetails;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class EntityTypeDetailsBean extends BaseBean<EntityTypeDetails> {

    private List<String> countries = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();

    @PostConstruct
    public void init() {
        countries.addAll(CountryUtility.getDisplayCountryNames());
        provinces.addAll(Arrays.asList(Province.values()));
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        EntityTypeDetails entityTypeDetails = (EntityTypeDetails) sessionMap.get("entityTypeKey");
        sessionMap.remove("entityTypeKey");

        if (entityTypeDetails != null) {
            entityTypeDetails.setUpdatedBy(getActiveUser().getSid());
            entityTypeDetails.setUpdatedDate(new Date());
        } else {
            entityTypeDetails = new EntityTypeDetails();
            entityTypeDetails.setCreatedBy(getActiveUser().getSid());
            entityTypeDetails.setCreatedDate(new Date());
            Address entityTypeDetailsAddress = new Address();
            entityTypeDetailsAddress.setCreatedBy(getActiveUser().getSid());
            entityTypeDetailsAddress.setCreatedDate(new Date());
            entityTypeDetailsAddress.setAddressType(AddressType.BUSINESS);
            entityTypeDetails.setBusinessAddress(entityTypeDetailsAddress);
        }
        addEntity(entityTypeDetails);
    }

    public void selectEntityTypeDetailFromDialog(EntityTypeDetails entityTypeDetails) {
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(entityTypeDetails);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
    }

    public void cancelEntityTypeDetailFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }
    
}
