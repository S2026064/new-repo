package sars.pca.app.mb;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import sars.pca.app.common.AddressType;
import sars.pca.app.common.PersonType;
import sars.pca.app.domain.Address;
import sars.pca.app.domain.ReportedPerson;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class ReportedPersonDetailsBean extends BaseBean<ReportedPerson> {
    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        ReportedPerson reportedPerson = (ReportedPerson) sessionMap.get("reportedPersonKey");
        sessionMap.remove("reportedPersonKey");
        if (reportedPerson != null) {
            reportedPerson.setUpdatedBy(getActiveUser().getSid());
            reportedPerson.setUpdatedDate(new Date());
        } else {
            reportedPerson = new ReportedPerson();
            reportedPerson.setCreatedBy(getActiveUser().getSid());
            reportedPerson.setCreatedDate(new Date());
            reportedPerson.setPersonType(PersonType.REPORTED_PERSON);
            Address physicalAddress = new Address();
            physicalAddress.setCreatedBy(getActiveUser().getSid());
            physicalAddress.setCreatedDate(new Date());
            physicalAddress.setAddressType(AddressType.PHYSICAL);
            reportedPerson.setResidentialAddress(physicalAddress);
        }
        addEntity(reportedPerson);
    }

    public void selectReportedPersonDetailFromDialog(ReportedPerson reportedPerson) {
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(reportedPerson);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
    }

    public void cancelReportedPersonDetailFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }
}
