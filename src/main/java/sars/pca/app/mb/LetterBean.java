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
import javax.faces.event.ComponentSystemEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AddressType;
import sars.pca.app.common.AuditType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.LetterHead;
import static sars.pca.app.common.LetterHead.FINALIZATION_OF_AUDIT;
import static sars.pca.app.common.LetterHead.IMPOSITION_OF_PENALTY;
import static sars.pca.app.common.LetterHead.LETTER_OF_DEMAND;
import static sars.pca.app.common.LetterHead.LETTER_OF_FINDINGS;
import static sars.pca.app.common.LetterHead.NOTICE_OF_INTENT;
import static sars.pca.app.common.LetterHead.NOTIFICATION_OF_AUDIT_DESK;
import static sars.pca.app.common.LetterHead.NOTIFICATION_OF_AUDIT_FIELD;
import static sars.pca.app.common.LetterHead.REQUEST_FOR_DOCUMENTATION;
import sars.pca.app.common.Salutation;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Address;
import sars.pca.app.domain.Auditor;
import sars.pca.app.domain.CustomDeclaration;
import sars.pca.app.domain.DutyVat;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.Program;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.LetterServiceLocal;
import sars.pca.app.service.ProgramServiceLocal;
import sars.pca.app.service.RelevantMaterialServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class LetterBean extends BaseBean<Letter> {

    @Autowired
    private LetterServiceLocal letterGenerationService;
    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private RelevantMaterialServiceLocal materialServiceLocal;
    @Autowired
    private ProgramServiceLocal programService;
    private List<LetterHead> letterHeads = new ArrayList<>();
    private List<RelevantMaterial> relevantMaterials = new ArrayList<>();
    private List<Salutation> salutations = new ArrayList<>();
    private List<YesNoEnum> paymentTypes = new ArrayList<>();
    private List<Program> programs = new ArrayList<>();
    private List<User> auditors = new ArrayList<>();
    private SuspiciousCase suspiciousCase;
    private boolean isPopulateLetterDetails;
    private boolean isSelectedFoa;
    private boolean isSelectedLod;
    private boolean isSelectedIop;
    private boolean isSelectedNoi;
    private boolean isReqDocLetterDetails;
    private boolean isAuditPlanLetterDetails;
    private boolean isAuditReportingLetter;
    private boolean isSelectedLof;
    
    @PostConstruct
    public void init() {
        relevantMaterials = materialServiceLocal.listAll();
        salutations.addAll(Arrays.asList(Salutation.values()));
        paymentTypes.addAll(Arrays.asList(YesNoEnum.values()));
        letterHeads.addAll(Arrays.asList(LetterHead.values()));
        programs = programService.listAll();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Letter letter = (Letter) sessionMap.get("letterKey");
        sessionMap.remove("letterKey");
        Long suspiciousCaseId = (Long) sessionMap.get("caseKey");
        sessionMap.remove("caseKey");
        setSuspiciousCase(suspiciousCaseService.findById(suspiciousCaseId));
        if (letter != null) {
            letter.setUpdatedBy(getActiveUser().getSid());
            letter.setUpdatedDate(new Date());
            
        } else {
            letter = new Letter();
            letter.setCreatedBy(getActiveUser().getSid());
            letter.setCreatedDate(new Date());
            letter.setRender(true);
            User user = getActiveUser().getUser();
            user.setWorkTelephoneNumber("0800 00 SARS (7277)");
            letter.setUser(user);
            Address address = new Address();
            address.setCreatedBy(getActiveUser().getSid());
            address.setCreatedDate(new Date());
            address.setAddressType(AddressType.POSTAL);
            letter.setAddress(address);
            
        }
        addEntity(letter);
    }
    
    public void before(ComponentSystemEvent event) {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getEntity().getLetterHead() != null) {
                processLetterType(getEntity().getLetterHead());
            }
        }
    }
    
    public void selectLetterFromDialog(Letter letter) {
        
        if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
            double subAmount;
            for (DutyVat dutyVat : letter.getDutyVats()) {
                subAmount = +dutyVat.getAmount();
                letter.setTotalA(String.valueOf(subAmount));
            }
        }
        if (letter.getLetterHead().equals(LetterHead.REQUEST_FOR_DOCUMENTATION)) {
            letter.setApprove(YesNoEnum.YES);
        }
        if (this.getErrorCollectionMsg().isEmpty()) {
            PrimeFaces.current().dialog().closeDynamic(letter);
        } else {
            Iterator<String> iterator = this.getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
            getErrorCollectionMsg().clear();
        }
    }
    
    public void cancelLetterFromDialog() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    public void letterTypeSelectionListener(SelectEvent event) {
        LetterHead letterHead = (LetterHead) event.getObject();
        
        processLetterType(letterHead);
    }
    
    public void processLetterType(LetterHead letterHead) {
        switch (letterHead) {
            case FINALIZATION_OF_AUDIT:
            case IMPOSITION_OF_PENALTY:
            case NOTICE_OF_INTENT:
            case LETTER_OF_FINDINGS:
            case LETTER_OF_DEMAND:
                isPopulateLetterDetails = Boolean.TRUE;
                isAuditPlanLetterDetails = Boolean.FALSE;
                isReqDocLetterDetails = Boolean.FALSE;
                isAuditReportingLetter = Boolean.FALSE;
                isSelectedIop = Boolean.FALSE;
                isSelectedLod = Boolean.FALSE;
                isSelectedLof = Boolean.FALSE;
                isSelectedNoi = Boolean.FALSE;
                if (letterHead.equals(LetterHead.FINALIZATION_OF_AUDIT)) {
                    isSelectedFoa = Boolean.TRUE;
                    isPopulateLetterDetails = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.FALSE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedIop = Boolean.FALSE;
                    isSelectedLod = Boolean.FALSE;
                    isSelectedLof = Boolean.FALSE;
                    isSelectedNoi = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                    
                }
                if (letterHead.equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                    isPopulateLetterDetails = Boolean.TRUE;
                    isSelectedIop = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.FALSE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedLod = Boolean.FALSE;
                    isSelectedLof = Boolean.FALSE;
                    isSelectedNoi = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                }
                if (letterHead.equals(LetterHead.LETTER_OF_DEMAND)) {
                    isPopulateLetterDetails = Boolean.TRUE;
                    isSelectedLod = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.FALSE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedIop = Boolean.FALSE;
                    isSelectedLof = Boolean.FALSE;
                    isSelectedNoi = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                }
                if (letterHead.equals(LetterHead.LETTER_OF_FINDINGS)) {
                    isPopulateLetterDetails = Boolean.TRUE;
                    isAuditReportingLetter = Boolean.TRUE;
                    isSelectedLof = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.FALSE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedIop = Boolean.FALSE;
                    isSelectedLod = Boolean.FALSE;
                    isSelectedNoi = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                    
                }
                if (letterHead.equals(LetterHead.NOTICE_OF_INTENT)) {
                    isPopulateLetterDetails = Boolean.TRUE;
                    isSelectedNoi = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.FALSE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedIop = Boolean.FALSE;
                    isSelectedLod = Boolean.FALSE;
                    isSelectedLof = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                }
                break;
            case NOTIFICATION_OF_AUDIT_DESK:
            case NOTIFICATION_OF_AUDIT_FIELD:
                isPopulateLetterDetails = Boolean.TRUE;
                isAuditPlanLetterDetails = Boolean.TRUE;
                isReqDocLetterDetails = Boolean.FALSE;
                isSelectedIop = Boolean.FALSE;
                isSelectedLod = Boolean.FALSE;
                isSelectedLof = Boolean.FALSE;
                isSelectedNoi = Boolean.FALSE;
                if (letterHead.equals(LetterHead.NOTIFICATION_OF_AUDIT_DESK)) {
                    isPopulateLetterDetails = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.TRUE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedIop = Boolean.FALSE;
                    isSelectedLod = Boolean.FALSE;
                    isSelectedLof = Boolean.FALSE;
                    isSelectedNoi = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                    
                }
                if (letterHead.equals(LetterHead.NOTIFICATION_OF_AUDIT_FIELD)) {
                    isPopulateLetterDetails = Boolean.TRUE;
                    isAuditPlanLetterDetails = Boolean.TRUE;
                    isReqDocLetterDetails = Boolean.FALSE;
                    isSelectedIop = Boolean.FALSE;
                    isSelectedLod = Boolean.FALSE;
                    isSelectedLof = Boolean.FALSE;
                    isSelectedNoi = Boolean.FALSE;
                    getEntity().setApprove(YesNoEnum.NO);
                    
                }
                break;
            case REQUEST_FOR_DOCUMENTATION:
                isPopulateLetterDetails = Boolean.TRUE;
                isReqDocLetterDetails = Boolean.TRUE;
                isAuditPlanLetterDetails = Boolean.FALSE;
                isSelectedIop = Boolean.FALSE;
                isSelectedLod = Boolean.FALSE;
                isSelectedLof = Boolean.FALSE;
                isSelectedNoi = Boolean.FALSE;
                break;
            default:
                isPopulateLetterDetails = Boolean.FALSE;
                isAuditPlanLetterDetails = Boolean.FALSE;
                isReqDocLetterDetails = Boolean.FALSE;
                isSelectedIop = Boolean.FALSE;
                isSelectedLod = Boolean.FALSE;
                isSelectedLof = Boolean.FALSE;
                isSelectedNoi = Boolean.FALSE;
                
                break;
        }
    }
    
    public void addDutyVat() {
        DutyVat dutyVat = new DutyVat();
        dutyVat.setCreatedBy(getActiveUser().getSid());
        dutyVat.setCreatedDate(new Date());
        getEntity().addDutyVat(dutyVat);
    }
    
    public void removeDutyVat(DutyVat dutyVat) {
        
        for (DutyVat savedDutyVat : getEntity().getDutyVats()) {
            if (savedDutyVat.getId().equals(dutyVat.getId())) {
                if (dutyVat.getId() != null) {
                    getEntity().removeDutyVat(dutyVat);
                    
                } else {
                    if (getEntity().getDutyVats().contains(dutyVat)) {
                        getEntity().removeDutyVat(dutyVat);
                    }
                }
                
            }
        }
        addInformationMessage("Duty Vat has been successfully removed ");
    }
    
    public void addCustomDeclaration() {
        CustomDeclaration declaration = new CustomDeclaration();
        declaration.setCreatedBy(getActiveUser().getSid());
        declaration.setCreatedDate(new Date());
        getEntity().addCustomsDeclaration(declaration);
    }
    
    public void removeCustomDeclaration(CustomDeclaration declaration) {
        for (CustomDeclaration savedDeclaration : getEntity().getDeclarations()) {
            if (savedDeclaration.getId().equals(declaration.getId())) {
                if (declaration.getId() != null) {
                    getEntity().removeCustomDeclaration(declaration);
                    
                } else {
                    if (getEntity().getDeclarations().contains(declaration)) {
                        getEntity().removeCustomDeclaration(declaration);
                    }
                }
            }
        }
        addInformationMessage("Customs Declaration has been successfully removed ");
    }
    
    public void addAuditor() {
        Auditor auditor = new Auditor();
        auditor.setCreatedBy(getActiveUser().getSid());
        auditor.setCreatedDate(new Date());
        getEntity().addAuditor(auditor);
    }
    
    public void removeAuditor(Auditor auditor) {
        
        for (Auditor savedAuditor : getEntity().getAuditors()) {
            if (savedAuditor.getId().equals(auditor.getId())) {
                if (auditor.getId() != null) {
                    getEntity().removeAuditor(auditor);
                    
                } else {
                    if (getEntity().getAuditors().contains(auditor)) {
                        getEntity().removeAuditor(auditor);
                    }
                }
            }
        }
        
        addInformationMessage("Customs Declaration has been successfully removed ");
    }
    
    public RelevantMaterialServiceLocal getMaterialServiceLocal() {
        return materialServiceLocal;
    }
    
    public void setMaterialServiceLocal(RelevantMaterialServiceLocal materialServiceLocal) {
        this.materialServiceLocal = materialServiceLocal;
    }
    
    public List<LetterHead> getLetterHeads() {
        return letterHeads;
    }
    
    public void setLetterHeads(List<LetterHead> letterHeads) {
        this.letterHeads = letterHeads;
    }
    
    public List<RelevantMaterial> getRelevantMaterials() {
        return relevantMaterials;
    }
    
    public void setRelevantMaterials(List<RelevantMaterial> relevantMaterials) {
        this.relevantMaterials = relevantMaterials;
    }
    
    public List<Salutation> getSalutations() {
        return salutations;
    }
    
    public void setSalutations(List<Salutation> salutations) {
        this.salutations = salutations;
    }
    
    public List<YesNoEnum> getPaymentTypes() {
        return paymentTypes;
    }
    
    public void setPaymentTypes(List<YesNoEnum> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }
    
    public boolean isIsPopulateLetterDetails() {
        return isPopulateLetterDetails;
    }
    
    public void setIsPopulateLetterDetails(boolean isPopulateLetterDetails) {
        this.isPopulateLetterDetails = isPopulateLetterDetails;
    }
    
    public boolean isIsSelectedFoa() {
        return isSelectedFoa;
    }
    
    public void setIsSelectedFoa(boolean isSelectedFoa) {
        this.isSelectedFoa = isSelectedFoa;
    }
    
    public boolean isIsSelectedLod() {
        return isSelectedLod;
    }
    
    public void setIsSelectedLod(boolean isSelectedLod) {
        this.isSelectedLod = isSelectedLod;
    }
    
    public boolean isIsSelectedIop() {
        return isSelectedIop;
    }
    
    public void setIsSelectedIop(boolean isSelectedIop) {
        this.isSelectedIop = isSelectedIop;
    }
    
    public boolean isIsSelectedNoi() {
        return isSelectedNoi;
    }
    
    public void setIsSelectedNoi(boolean isSelectedNoi) {
        this.isSelectedNoi = isSelectedNoi;
    }
    
    public boolean isIsReqDocLetterDetails() {
        return isReqDocLetterDetails;
    }
    
    public void setIsReqDocLetterDetails(boolean isReqDocLetterDetails) {
        this.isReqDocLetterDetails = isReqDocLetterDetails;
    }
    
    public boolean isIsAuditPlanLetterDetails() {
        return isAuditPlanLetterDetails;
    }
    
    public void setIsAuditPlanLetterDetails(boolean isAuditPlanLetterDetails) {
        this.isAuditPlanLetterDetails = isAuditPlanLetterDetails;
    }
    
    public boolean isIsAuditReportingLetter() {
        return isAuditReportingLetter;
    }
    
    public void setIsAuditReportingLetter(boolean isAuditReportingLetter) {
        this.isAuditReportingLetter = isAuditReportingLetter;
    }
    
    public boolean isIsSelectedLof() {
        return isSelectedLof;
    }
    
    public void setIsSelectedLof(boolean isSelectedLof) {
        this.isSelectedLof = isSelectedLof;
    }
    
    public List<Program> getPrograms() {
        return programs;
    }
    
    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
    
    public List<User> getAuditors() {
        return auditors;
    }
    
    public void setAuditors(List<User> auditors) {
        this.auditors = auditors;
    }
    
    public SuspiciousCase getSuspiciousCase() {
        return suspiciousCase;
    }
    
    public void setSuspiciousCase(SuspiciousCase suspiciousCase) {
        this.suspiciousCase = suspiciousCase;
    }
    
}
