package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.CaseType;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.service.OffenceClassificationServiceLocal;
import sars.pca.app.service.OffenceDescriptionServiceLocal;
import sars.pca.app.service.SectionRuleServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class AddOrUpdatePenaltyBean extends BaseBean<AuditReportOffencePenalty> {
    
    @Autowired
    private OffenceClassificationServiceLocal offenceClassificationService;
    @Autowired
    private SectionRuleServiceLocal sectionRuleService;
    @Autowired
    private OffenceDescriptionServiceLocal offenceDescriptionService;
    private List<OffenceClassification> offenceClassifications = new ArrayList<>();
    private List<SectionRule> sectionRules = new ArrayList<>();
    private List<OffenceDescription> offenceDescriptions = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        AuditReportOffencePenalty auditReportOffencePenalty = (AuditReportOffencePenalty) sessionMap.get("rop");
        sessionMap.remove("rop");
        CaseType caseType = (CaseType) sessionMap.get("caseType");
        sessionMap.remove("caseType");
        if (auditReportOffencePenalty == null) {
            auditReportOffencePenalty = new AuditReportOffencePenalty();
            auditReportOffencePenalty.setCreatedBy(getActiveUser().getSid());
            auditReportOffencePenalty.setCreatedDate(new Date());
            OffenceAndPenalty offenceAndPenalty = new OffenceAndPenalty();
            offenceAndPenalty.setCreatedBy(getActiveUser().getSid());
            offenceAndPenalty.setCreatedDate(new Date());
            auditReportOffencePenalty.setOffencePenalty(offenceAndPenalty);
        } else {
            auditReportOffencePenalty.setUpdatedBy(getActiveUser().getSid());
            auditReportOffencePenalty.setUpdatedDate(new Date());
            sectionRules.addAll(sectionRuleService.findByOffenceClassification(auditReportOffencePenalty.getOffencePenalty().getOffenceClassification()));
            offenceDescriptions.addAll(offenceDescriptionService.findBySectionRule(auditReportOffencePenalty.getOffencePenalty().getSectionRule()));
        }
        offenceClassifications.addAll(offenceClassificationService.findByClassificationType(caseType));
        addEntity(auditReportOffencePenalty);
    }
    
    public void onOffenceClassificationChange(SelectEvent event) {
        sectionRules.clear();
        OffenceClassification selectedOffenceClassification = (OffenceClassification) event.getObject();
        sectionRules.addAll(sectionRuleService.findByOffenceClassification(selectedOffenceClassification));
    }
    
    public void onSectionRuleChange(SelectEvent event) {
        offenceDescriptions.clear();
        SectionRule selectedSectionRule = (SectionRule) event.getObject();
        offenceDescriptions.addAll(offenceDescriptionService.findBySectionRule(selectedSectionRule));
    }
    
    public void selectOffencePenaltyFromDialog(AuditReportOffencePenalty auditReportOffencePenalty) {
        if (auditReportOffencePenalty.getOffencePenalty().getOffenceClassification() == null) {
            addError("Select Offence Classification ");
        }
        if (auditReportOffencePenalty.getOffencePenalty().getSectionRule() == null) {
            addError("Select SectionRule ");
        }
        if (auditReportOffencePenalty.getOffencePenalty().getOffenceDescription() == null) {
            addError("Select Offence Description ");
        }
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(auditReportOffencePenalty);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
    }
    
    public void cancelOffencePenaltyFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    public List<OffenceClassification> getOffenceClassifications() {
        return offenceClassifications;
    }
    
    public void setOffenceClassifications(List<OffenceClassification> offenceClassifications) {
        this.offenceClassifications = offenceClassifications;
    }
    
    public List<SectionRule> getSectionRules() {
        return sectionRules;
    }
    
    public void setSectionRules(List<SectionRule> sectionRules) {
        this.sectionRules = sectionRules;
    }
    
    public List<OffenceDescription> getOffenceDescriptions() {
        return offenceDescriptions;
    }
    
    public void setOffenceDescriptions(List<OffenceDescription> offenceDescriptions) {
        this.offenceDescriptions = offenceDescriptions;
    }
}
