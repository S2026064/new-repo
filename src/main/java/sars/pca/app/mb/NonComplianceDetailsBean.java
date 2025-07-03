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
import sars.pca.app.common.TimeFrame;
import sars.pca.app.common.YearlyLoss;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.NonComplianceDetails;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class NonComplianceDetailsBean extends BaseBean<NonComplianceDetails> {
    private List<TimeFrame> timeFrames = new ArrayList<>();
    private List<YearlyLoss> yearlyLosses = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();

    @PostConstruct
    public void init() {
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        timeFrames.addAll(Arrays.asList(TimeFrame.values()));
        yearlyLosses.addAll(Arrays.asList(YearlyLoss.values()));
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        NonComplianceDetails nonComplianceDetails = (NonComplianceDetails) sessionMap.get("nonComplianceKey");
        sessionMap.remove("nonComplianceKey");

        if (nonComplianceDetails != null) {
            nonComplianceDetails.setUpdatedBy(getActiveUser().getSid());
            nonComplianceDetails.setUpdatedDate(new Date());
        } else {
            nonComplianceDetails = new NonComplianceDetails();
            nonComplianceDetails.setCreatedBy(getActiveUser().getSid());
            nonComplianceDetails.setCreatedDate(new Date());
        }
        addEntity(nonComplianceDetails);
    }

    public void selectNonComplianceDetailFromDialog(NonComplianceDetails nonComplianceDetails) {
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(nonComplianceDetails);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
    }

    public void cancelNonComplianceDetailFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public List<TimeFrame> getTimeFrames() {
        return timeFrames;
    }

    public void setTimeFrames(List<TimeFrame> timeFrames) {
        this.timeFrames = timeFrames;
    }

    public List<YearlyLoss> getYearlyLosses() {
        return yearlyLosses;
    }

    public void setYearlyLosses(List<YearlyLoss> yearlyLosses) {
        this.yearlyLosses = yearlyLosses;
    }

    public List<YesNoEnum> getYesNoOptions() {
        return yesNoOptions;
    }

    public void setYesNoOptions(List<YesNoEnum> yesNoOptions) {
        this.yesNoOptions = yesNoOptions;
    }
    
    
}
