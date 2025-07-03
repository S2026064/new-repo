package sars.pca.app.mb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.ClientType;
import sars.pca.app.common.CountryUtility;
import sars.pca.app.common.CustomExcise;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.Province;
import sars.pca.app.common.TimeFrame;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.common.YearlyLoss;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.CasePrioritisationDetails;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.NonComplianceDetails;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseCRCSReviewerBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;
    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;

    //notification services used
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<ClientType> clientTypes = new ArrayList<>();
    private List<CustomExcise> customsExcises = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();
    private List<String> listCountries = new ArrayList<>();
    private List<TimeFrame> timeFrames = new ArrayList<>();
    private List<YearlyLoss> yearlyLosses = new ArrayList<>();
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    List<LocationOffice> locationOffices = new ArrayList<>();

    private AttachmentType selectedAttachmentType;
    private UploadedFile originalFile;
    private SarCase selectedSarCase;
    private LocationOffice selectedLocationOffice;
    private SuspiciousCase persistentSuspiciousCase;

    @PostConstruct
    public void init() {
        reset().setList(true);
        //Find all cases linked to the active user.
        List<CaseStatus> caseStatuses = new ArrayList<>();
        caseStatuses.add(CaseStatus.ACTIVE_SAR);
        caseStatuses.add(CaseStatus.REJECTED_DISCARDED_SAR);
        addCollections(suspiciousCaseService.findByStatusAndUserSid(caseStatuses, getActiveUser().getUser().getSid()));
        clientTypes.addAll(Arrays.asList(ClientType.values()));
        customsExcises.addAll(Arrays.asList(CustomExcise.values()));
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        provinces.addAll(Arrays.asList(Province.values()));
        listCountries = CountryUtility.getDisplayCountryNames();
        timeFrames.addAll(Arrays.asList(TimeFrame.values()));
        yearlyLosses.addAll(Arrays.asList(YearlyLoss.values()));
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        locationOffices.addAll(locationOfficeService.findByOfficeType(OfficeType.CRSC_OFFICE));
    }

    public void addOrUpdate(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        if (suspiciousCase.getSarCase().getCasePrioritisationDetails() != null) {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
        } else {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            CasePrioritisationDetails casePrioritisationDetails = new CasePrioritisationDetails();
            casePrioritisationDetails.setCreatedBy(getActiveUser().getSid());
            casePrioritisationDetails.setCreatedDate(new Date());
            suspiciousCase.getSarCase().setCasePrioritisationDetails(casePrioritisationDetails);
        }
        addEntity(suspiciousCase);
    }

    //Get next sar case in the pool
    public void nextSarCase() {
        //Pull the case from the first pool stagin and change status and update to ensure case does not get picked by the next user.
        SuspiciousCase suspiciousCase = suspiciousCaseService.findTopByStatusOrderByCaseRefNumberDesc(CaseStatus.CRCS_REVIEW_POOL);
        if (suspiciousCase != null) {
            suspiciousCase.setStatus(CaseStatus.ACTIVE_SAR);
            SarCaseUser sarCaseUser = new SarCaseUser();
            sarCaseUser.setCreatedBy(getActiveUser().getSid());
            sarCaseUser.setCreatedDate(new Date());
            sarCaseUser.setUser(getActiveUser().getUser());
            suspiciousCase.addSarCaseUser(sarCaseUser);
            suspiciousCase.setCrcsLocationOffice(getActiveUser().getUser().getLocationOffice());
            SuspiciousCase persistentSarCase = suspiciousCaseService.update(suspiciousCase);
            addCollection(persistentSarCase);
        } else {
            addInformationMessage("No cases available in the pool.");
        }
    }

    public void accept(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalCustomDeclaredExport() == 0.00) {
            addError("Enter customs declare export amount");
        }
        if (suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalCustomDeclaredImport() == 0.00) {
            addError("Enter customs declare import amount");
        }
        if (suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalVolumeTransactionImport() == 0.00) {
            addError("Enter volume transaction import number");
        }
        if (suspiciousCase.getSarCase().getCasePrioritisationDetails().getTotalVolumetransactionExport() == 0.00) {
            addError("Enter volume transaction export number");
        }
        if (getErrorCollectionMsg().isEmpty()) {
            List<CaseStatus> caseStatuses = new ArrayList<>();
            caseStatuses.add(CaseStatus.REVIEWED_PENDING_ALLOCATION);
            caseStatuses.add(CaseStatus.REJECTED_DISCARDED_SAR);
            caseStatuses.add(CaseStatus.ALLOCATED_PENDING_INDEPTH_ANALYSIS);
            caseStatuses.add(CaseStatus.ACTIVE_CRCS_ANALYSIS);
            caseStatuses.add(CaseStatus.ACTIVE_CRCS_RA);
            caseStatuses.add(CaseStatus.CI_ALLOCATION);
            caseStatuses.add(CaseStatus.CI_SENIOR_AUDITOR_AUDIT_PLAN_REVIEW);
            caseStatuses.add(CaseStatus.ACTIVE_RA_APPROVAL);
            caseStatuses.add(CaseStatus.ACTIVE_CI_PLAN);
            caseStatuses.add(CaseStatus.QA_REVIEW_POOL);
            caseStatuses.add(CaseStatus.TECHNICAL_REVIEW_POOL);
            caseStatuses.add(CaseStatus.FULL_SCOPE_POOL);
            caseStatuses.add(CaseStatus.LIMITED_SCOPE_POOL);
            caseStatuses.add(CaseStatus.INTEGRATED_SCOPE_POOL);
            caseStatuses.add(CaseStatus.REJECTED_DISCARDED_INDEPTH_ANALYSIS);
            caseStatuses.add(CaseStatus.REJECTED_DISCARDED_SAR);
            caseStatuses.add(CaseStatus.CI_PLAN_REWORK);
            caseStatuses.add(CaseStatus.ACTIVE_RA_QA);
            caseStatuses.add(CaseStatus.ACTIVE_RA_REJECTED);
            caseStatuses.add(CaseStatus.ACTIVE_RA_SEND_BACK_FOR_REWORK);
            caseStatuses.add(CaseStatus.ACTIVE_RA_TECHNICAL_REVIEW_REJECTED);
            caseStatuses.add(CaseStatus.DISCARDED_PENDING_QA_REVIEW);
            SuspiciousCase existingSuspiciousCase = suspiciousCaseService.findByStatusAndCustomsCode(caseStatuses, suspiciousCase.getIbrData().getIbrCustomsNumber());
            if (existingSuspiciousCase != null) {
                addPersistentSuspiciousCase(existingSuspiciousCase);
                addEntity(suspiciousCase);
                reset().setMergecase(true);

            } else {
                crcsReviewerCasePrioritisationEvaluation(suspiciousCase);
                suspiciousCase.setStatus(CaseStatus.REVIEWED_PENDING_ALLOCATION);
                removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
                addInformationMessage("Case accepted successufully. ");
                reset().setList(true);
            }
        } else {
            Iterator<String> iterator = getErrorCollectionMsg().iterator();
            while (iterator.hasNext()) {
                addErrorMessage(iterator.next());
            }
        }
    }

    public void discard(SuspiciousCase suspiciousCase) {
        //Discarded cases are placed into the second staging pool.
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setStatus(CaseStatus.QA_REVIEW_POOL);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Case discarded successufully. ");
            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you discard the case.");
        }
        suspiciousCase.setStatus(CaseStatus.QA_REVIEW_POOL);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));

        //email to QA after discarded case:Update
        List<User> recipients = userService.findUserByUserRoleDescription(UserRoleType.CRCS_QUALITY_ASSURER.toString());
        emailNotificationSenderService.sendEmailNotifications(NotificationType.DISCARDED_POOL, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipients, getActiveUser().getUser());

        reset().setList(true);
    }

    public void merge(SuspiciousCase existingSuspiciousCase) {
        List<NonComplianceDetails> temporalStorage = new ArrayList<>();
        Iterator<NonComplianceDetails> existingIterator = existingSuspiciousCase.getSarCase().getNonComplianceDetails().iterator();
        Iterator<NonComplianceDetails> incomingIterator = getEntity().getSarCase().getNonComplianceDetails().iterator();
        while (existingIterator.hasNext()) {
            NonComplianceDetails existing = existingIterator.next();
            while (incomingIterator.hasNext()) {
                NonComplianceDetails incoming = incomingIterator.next();
                if (!incoming.getDescription().trim().equalsIgnoreCase(existing.getDescription().trim())) {
                    temporalStorage.add(incoming);
                }
            }
        }
        if (temporalStorage.isEmpty()) {
            //If the incoming case is the same as the existing, discard the incoming case.
            getEntity().setStatus(CaseStatus.QA_REVIEW_POOL);
            removeEntity(getEntity()).addFreshEntity(suspiciousCaseService.update(getEntity()));
            addInformationMessage("Case merged successfuly");
            reset().setList(true);
        } else {
            //If the incoming case is not the same as the existing one, merge the cases.
            existingSuspiciousCase.getSarCase().addNonComplianceDetails(temporalStorage);
            getEntity().setStatus(CaseStatus.CLOSURE);
            removeEntity(getEntity()).addFreshEntity(suspiciousCaseService.update(getEntity()));
            addInformationMessage("Case closed successfuly");

            reset().setList(true);
        }

    }

    public void close(SuspiciousCase suspiciousCase) {
        suspiciousCase.setStatus(CaseStatus.REVIEWED_CASE_CLOSED);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        reset().setList(true);
    }

    public void onTabChange(TabChangeEvent event) {
        if (getEntity().getCaseType().equals(CaseType.VDDL)) {
            getPublicOfficers().clear();
            getPublicOfficers().addAll(getIbrDataService().getPublicOfficer(getEntity().getIbrData().getClNbr(), getActiveUser().getSid()));
        } else {
            //Retrieve and populate IBR DATA on demand
            populateIbrdata(event.getTab().getId(), getEntity().getSarCase().getCustomExciseCode());
        }
    }

    public void addComment(SuspiciousCase suspiciousCase) {
        Comment comment = new Comment();
        comment.setCreatedBy(getActiveUser().getSid());
        comment.setCreatedDate(new Date());
        comment.setRender(true);
        suspiciousCase.addComment(comment);
        addEntity(suspiciousCase);
    }

    public void updateComment(Comment comment) {
        comment.setRender(true);
    }

    public void removeComment(Comment comment) {
        getEntity().removeComment(comment);
        if (comment.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
        reset().setList(true);
    }

    public void cancelMergeAction() {
        reset().setList(true);
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            try {
                //Setting the file upload bytes to uploaded to documentum on submit
                setFileUploadBytes(IOUtils.toByteArray(file.getInputStream()));
                addAttachment(file.getFileName(), file.getSize());
            } catch (IOException ex) {
                Logger.getLogger(SuspCaseAuditPlanCreationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addOrUpdateLetter(Letter letter, String targetPage, Long id) {
        id = getEntity().getId();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("letterKey", letter);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caseKey", id);
        openDialogBox(targetPage, null, 1300, 780);
    }

    public void addAttachment(String fileName, Long fileSize) {
        Attachment attachment = new Attachment();
        attachment.setCode(RandomStringUtils.randomNumeric(5));
        attachment.setCreatedBy(getActiveUser().getSid());
        attachment.setAttachmentType(selectedAttachmentType);
        attachment.setCreatedDate(new Date());
        attachment.setDocumentSize(fileSize.doubleValue());
        attachment.setDescription(fileName);
        getEntity().addAttachment(attachment);
    }

    public JsonDocumentDto uploadFileToDocumentumServer(Attachment attachment) {
        JsonDocumentDto jsonDocumentDto = new JsonDocumentDto();
        List<Properties> propertyList = new ArrayList<>();
        Properties properties = new Properties();
        properties.setPropertyName(attachment.getDescription());
        propertyList.add(properties);
        String base64File = Base64.getEncoder().encodeToString(getFileUploadBytes());
        jsonDocumentDto.setObjectType("sars_tarrif_doc");
        jsonDocumentDto.setObjectName(attachment.getDescription());
        jsonDocumentDto.setContentType(FilenameUtils.getExtension(attachment.getDescription()));
        jsonDocumentDto.setAuthor(getActiveUser().getSid());
        jsonDocumentDto.setProperties(propertyList);
        jsonDocumentDto.setContent(base64File);
        return jsonDocumentDto;
    }

    public void back() {
        reset().setAdd(true);
    }

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public List<ClientType> getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(List<ClientType> clientTypes) {
        this.clientTypes = clientTypes;
    }

    public List<CustomExcise> getCustomsExcises() {
        return customsExcises;
    }

    public void setCustomsExcises(List<CustomExcise> customsExcises) {
        this.customsExcises = customsExcises;
    }

    public List<YesNoEnum> getYesNoOptions() {
        return yesNoOptions;
    }

    public void setYesNoOptions(List<YesNoEnum> yesNoOptions) {
        this.yesNoOptions = yesNoOptions;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
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

    public UploadedFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(UploadedFile originalFile) {
        this.originalFile = originalFile;
    }

    public List<String> getListCountries() {
        return listCountries;
    }

    public void setListCountries(List<String> listCountries) {
        this.listCountries = listCountries;
    }

    public List<AttachmentType> getAttachmentTypes() {
        return attachmentTypes;
    }

    public void setAttachmentTypes(List<AttachmentType> attachmentTypes) {
        this.attachmentTypes = attachmentTypes;
    }

    public AttachmentType getSelectedAttachmentType() {
        return selectedAttachmentType;
    }

    public void setSelectedAttachmentType(AttachmentType selectedAttachmentType) {
        this.selectedAttachmentType = selectedAttachmentType;
    }

    public SarCase getSelectedSarCase() {
        return selectedSarCase;
    }

    public void setSelectedSarCase(SarCase selectedSarCase) {
        this.selectedSarCase = selectedSarCase;
    }

    public List<LocationOffice> getLocationOffices() {
        return locationOffices;
    }

    public void setLocationOffices(List<LocationOffice> locationOffices) {
        this.locationOffices = locationOffices;
    }

    public LocationOffice getSelectedLocationOffice() {
        return selectedLocationOffice;
    }

    public void setSelectedLocationOffice(LocationOffice selectedLocationOffice) {
        this.selectedLocationOffice = selectedLocationOffice;
    }

    public SuspiciousCase getPersistentSuspiciousCase() {
        return persistentSuspiciousCase;
    }

    public void setPersistentSuspiciousCase(SuspiciousCase persistentSuspiciousCase) {
        this.persistentSuspiciousCase = persistentSuspiciousCase;
    }

    public void addPersistentSuspiciousCase(SuspiciousCase persistentSuspiciousCase) {
        this.persistentSuspiciousCase = persistentSuspiciousCase;
    }
}
