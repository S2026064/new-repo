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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.AddressType;
import sars.pca.app.common.AttachmentType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.ClientType;
import sars.pca.app.common.CountryUtility;
import sars.pca.app.common.CustomExcise;
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.PersonType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.Province;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.Address;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.CustomsExciseClientType;
import sars.pca.app.domain.EntityTypeDetails;
import sars.pca.app.domain.IbrData;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.NonComplianceDetails;
import sars.pca.app.domain.ReportedPerson;
import sars.pca.app.domain.ReportingPerson;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.VddLedCase;
import sars.pca.app.domain.VddLedType;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseCreationBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;
    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;

    private List<ClientType> clientTypes = new ArrayList<>();
    private List<CustomExcise> customsExcises = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();
    private List<String> listCountries = new ArrayList<>();
    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private List<CaseType> caseTypes = new ArrayList<>();
    private AttachmentType selectedAttachmentType;
    private UploadedFile originalFile;
    private String dob;
    private int documentSize;
    private String customsCodeParameter;
    private List<LocationOffice> locationOffices = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        addCollections(suspiciousCaseService.findByCreatedBy(getActiveUser().getSid()));
        clientTypes.addAll(Arrays.asList(ClientType.values()));
        customsExcises.addAll(Arrays.asList(CustomExcise.values()));
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
        provinces.addAll(Arrays.asList(Province.values()));
        listCountries = CountryUtility.getDisplayCountryNames();
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        caseTypes.addAll(Arrays.asList(CaseType.values()));
        locationOffices.addAll(locationOfficeService.findByOfficeType(OfficeType.CI_OFFICE));
    }

    public void addOrUpdate(SuspiciousCase suspiciousCase) {
        if (suspiciousCase != null) {
            reset().setAdd(true);
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            suspiciousCase.setDisabled(true);
        } else {
            reset().setCaseSelection(true);
            suspiciousCase = new SuspiciousCase();
            suspiciousCase.setCreatedBy(getActiveUser().getSid());
            suspiciousCase.setCreatedDate(new Date());
            suspiciousCase.setStatus(CaseStatus.NEW);
            SarCaseUser sarCaseUser = new SarCaseUser();
            sarCaseUser.setCreatedBy(getActiveUser().getSid());
            sarCaseUser.setCreatedDate(new Date());
            sarCaseUser.setUser(getActiveUser().getUser());
            suspiciousCase.addSarCaseUser(sarCaseUser);
            addCollection(suspiciousCase);
        }
        addEntity(suspiciousCase);
    }

    public void save(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() != null) {
            removeEntity(suspiciousCase).addFreshEntityAndSynchView(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("SAR Case updated successfully and case ref No. is ", suspiciousCase.getCaseRefNumber());
            reset().setList(true);
        } else {
            if (!sarCaseValidation(suspiciousCase).isEmpty()) {
                Iterator<String> iterator = getErrorCollectionMsg().iterator();
                while (iterator.hasNext()) {
                    addWarningMessage(iterator.next());
                }
                return;
            }
            if (suspiciousCase.getCaseType().equals(CaseType.SAR)) {
                IbrData ibrData = getIbrDataService().getRegParticularsByCustomsCodePerIBRData(suspiciousCase.getSarCase().getCustomExciseCode(), getActiveUser().getSid());
                if (ibrData == null) {
                    addInformationMessage("The customs code entered is invalid or does not have registered company relatable record");
                    return;
                }
                suspiciousCase.setIbrData(ibrData);
            }
            SuspiciousCase persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
            StringBuilder builder = new StringBuilder();
            if (persistentSarCase != null) {
                builder.append("CS");
                builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
            } else {
                builder.append("CS10000001");
            }
            suspiciousCase.setCaseRefNumber(builder.toString());
            suspiciousCaseService.save(suspiciousCase);
            addInformationMessage("SAR Case was saved successfully and case ref No. is " + suspiciousCase.getCaseRefNumber());
            reset().setList(true);
        }
    }

    public void submit(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getCaseType().equals(CaseType.SAR)) {
            suspiciousCase.setStatus(CaseStatus.CRCS_REVIEW_POOL);
        } else {
            suspiciousCase.setStatus(CaseStatus.VDDL_POOL);
        }
        if (suspiciousCase.getId() != null) {
            suspiciousCase.setCreatedBy(getActiveUser().getSid());
            suspiciousCase.setCreatedDate(new Date());
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("SAR Submitted Successfully and case ref No. is ", suspiciousCase.getCaseRefNumber());
        } else {
            if (!sarCaseValidation(suspiciousCase).isEmpty()) {
                Iterator<String> iterator = getErrorCollectionMsg().iterator();
                while (iterator.hasNext()) {
                    addWarningMessage(iterator.next());
                }
                return;
            }
            if (suspiciousCase.getCaseType().equals(CaseType.SAR)) {
                IbrData ibrData = getIbrDataService().getRegParticularsByCustomsCodePerIBRData(suspiciousCase.getSarCase().getCustomExciseCode(), getActiveUser().getSid());
                if (ibrData == null) {
                    addInformationMessage("The customs code entered is invalid or does not have registered company relatable record");
                    return;
                }
                suspiciousCase.setIbrData(ibrData);
            }
            SuspiciousCase persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
            StringBuilder builder = new StringBuilder();
            if (persistentSarCase != null) {
                builder.append("CS");
                builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
            } else {
                builder.append("CS10000001");
            }
            suspiciousCase.setCaseRefNumber(builder.toString());
            suspiciousCaseService.save(suspiciousCase);
            addInformationMessage("SAR Submitted Successfully and case ref No. is " + suspiciousCase.getCaseRefNumber());

        }
        reset().setList(true);
    }

    private List<String> sarCaseValidation(SuspiciousCase suspiciousCase) {
        this.getErrorCollectionMsg().clear();
        if (suspiciousCase.getSarCase() != null) {
            if (suspiciousCase.getSarCase().getNumberOfCompanyTraderImportExport() != null) {
                if (suspiciousCase.getSarCase().getNumberOfCompanyTraderImportExport() == 0) {
                    addErrorCollectionMsg("Number of Companies/traders/importers/exporters cannot be equal to zero");
                }
                if (suspiciousCase.getSarCase().getEntityTypeDetails().size() != suspiciousCase.getSarCase().getNumberOfCompanyTraderImportExport()) {
                    addErrorCollectionMsg("The number of entity records must match the number of company records. " + suspiciousCase.getSarCase().getNumberOfCompanyTraderImportExport());
                }
            } else {
                if (suspiciousCase.getSarCase().getNumberOfTravellerIndividual() == 0) {
                    addErrorCollectionMsg("Number of travellers/individuals cannot be equal to zero");
                }
                if (suspiciousCase.getSarCase().getReportedPersons().size() != suspiciousCase.getSarCase().getNumberOfTravellerIndividual()) {
                    addErrorCollectionMsg("The number of reported persons records must match the number of individual records. " + suspiciousCase.getSarCase().getNumberOfTravellerIndividual());
                }
            }
        }
        return this.getErrorCollectionMsg();
    }

    public void delete(SuspiciousCase suspiciousCase) {
        //WE NEED CLARITY ON DELETE. WE SHOULD ASK WHETHER DELETE WILL COMPLETELY REMOVE THE RECORD FROM THE DB.
        suspiciousCase.setStatus(CaseStatus.DELETED);
        suspiciousCaseService.update(suspiciousCase);
        removeEntity(suspiciousCase);
        addInformationMessage("SAR Case was deleted successfully");
        reset().setList(true);
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equalsIgnoreCase("vddled_public_officer_tab_2024")) {
            getPublicOfficers().clear();
            getPublicOfficers().addAll(getIbrDataService().getPublicOfficer(getEntity().getIbrData().getClNbr(), getActiveUser().getSid()));
        }
    }

    public void searchIbrDataByCustomsCode() {
        reset().setAdd(true);
        if (StringUtils.isNotEmpty(customsCodeParameter)) {
            IbrData ibrData = getIbrDataService().getRegParticularsByCustomsCodePerIBRData(customsCodeParameter, getActiveUser().getSid());
            if (ibrData == null) {
                addInformationMessage("The customs code entered is invalid or does not have registered company relatable record");
                return;
            }
            getEntity().setIbrData(ibrData);
            VddLedCase vddLedCase = new VddLedCase();
            vddLedCase.setCreatedBy(getActiveUser().getSid());
            vddLedCase.setCreatedDate(new Date());

            CustomsExciseClientType customsExciseClientType = new CustomsExciseClientType();
            customsExciseClientType.setCreatedBy(getActiveUser().getSid());
            customsExciseClientType.setCreatedDate(new Date());
            vddLedCase.setCustomsExciseClientType(customsExciseClientType);

            VddLedType vddLedType = new VddLedType();
            vddLedType.setCreatedBy(getActiveUser().getSid());
            vddLedType.setCreatedDate(new Date());
            vddLedCase.setVddlType(vddLedType);

            getEntity().setVddLed(vddLedCase);
        }
    }

    public void vddledCustomsCodeListener() {
        if (getEntity().getCaseType().equals(CaseType.SAR)) {
            reset().setAdd(true);
            SarCase sarCase = new SarCase();
            sarCase.setCreatedBy(getActiveUser().getSid());
            sarCase.setCreatedDate(new Date());
            sarCase.setClientType(ClientType.COMPANY_TRADER_IMPORTER_EXPORTER);
            ReportingPerson reportingPerson = new ReportingPerson();
            reportingPerson.setCreatedBy(getActiveUser().getSid());
            reportingPerson.setCreatedDate(new Date());
            Address reportingPersonPhysicalAddress = new Address();
            reportingPersonPhysicalAddress.setCreatedBy(getActiveUser().getSid());
            reportingPersonPhysicalAddress.setCreatedDate(new Date());
            reportingPersonPhysicalAddress.setAddressType(AddressType.PHYSICAL);
            reportingPerson.setResidentialAddress(reportingPersonPhysicalAddress);
            sarCase.setReportingPerson(reportingPerson);
            getEntity().setSarCase(sarCase);
        } else {
            reset().setSearch(true);
        }
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
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

    public void disclosePersonalListener() {
        if (getEntity().getSarCase().getDisclosePersonalDetails().equals(YesNoEnum.YES)) {
            ReportingPerson reportingPerson = new ReportingPerson();
            reportingPerson.setCreatedBy(getActiveUser().getSid());
            reportingPerson.setCreatedDate(new Date());
            reportingPerson.setPersonType(PersonType.REPORTING_PERSON);
            getEntity().getSarCase().setReportingPerson(reportingPerson);
        } else {
        }
    }

    public void handleAddReportedPersonReturnListener(SelectEvent event) {
        ReportedPerson reportedPerson = (ReportedPerson) event.getObject();
        getEntity().getSarCase().addReportedPerson(reportedPerson);
    }

    public void handleUpdateReportedPersonReturnListener(SelectEvent event) {
        ReportedPerson reportedPerson = (ReportedPerson) event.getObject();
        Integer index = getEntity().getSarCase().getReportedPersons().indexOf(reportedPerson);
        getEntity().getSarCase().getReportedPersons().set(index, reportedPerson);
    }

    public void handleAddNonComplianceDetailsReturnListener(SelectEvent event) {
        NonComplianceDetails nonComplianceDetails = (NonComplianceDetails) event.getObject();
        getEntity().getSarCase().addNonComplianceDetails(nonComplianceDetails);
    }

    public void handleUpdateNonComplianceDetailsReturnListener(SelectEvent event) {
        NonComplianceDetails nonComplianceDetails = (NonComplianceDetails) event.getObject();
        Integer index = getEntity().getSarCase().getNonComplianceDetails().indexOf(nonComplianceDetails);
        getEntity().getSarCase().getNonComplianceDetails().set(index, nonComplianceDetails);
    }

    public void handleAddEntityTypeDetailsReturnListener(SelectEvent event) {
        EntityTypeDetails entityTypeDetails = (EntityTypeDetails) event.getObject();
        getEntity().getSarCase().addEntityTypeDetails(entityTypeDetails);
    }

    public void handleUpdateEntityTypeDetailsReturnListener(SelectEvent event) {
        EntityTypeDetails entityTypeDetails = (EntityTypeDetails) event.getObject();
        Integer index = getEntity().getSarCase().getEntityTypeDetails().indexOf(entityTypeDetails);
        getEntity().getSarCase().getEntityTypeDetails().set(index, entityTypeDetails);
    }

    public void removeReportedPerson(ReportedPerson reportedPerson) {
        getEntity().getSarCase().removeReportedPerson(reportedPerson);
        if (reportedPerson.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void removeNonComplianceDetails(NonComplianceDetails nonComplianceDetails) {
        getEntity().getSarCase().removeNonComplianceDetails(nonComplianceDetails);
        if (nonComplianceDetails.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void removeEntityTypeDetails(EntityTypeDetails entityTypeDetail) {
        getEntity().getSarCase().removeEntityTypeDetails(entityTypeDetail);
        if (entityTypeDetail.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void removeAttachment(Attachment attachment) {
        getEntity().removeAttachment(attachment);
        if (attachment.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public void back() {
        reset().setAdd(true);
    }

    public void calculateAttachmentValues() {

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

    public int getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(int documentSize) {
        this.documentSize = documentSize;
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

    public String getDob() {
        return dob;
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

    public List<CaseType> getCaseTypes() {
        return caseTypes;
    }

    public void setCaseTypes(List<CaseType> caseTypes) {
        this.caseTypes = caseTypes;
    }

    public String getCustomsCodeParameter() {
        return customsCodeParameter;
    }

    public void setCustomsCodeParameter(String customsCodeParameter) {
        this.customsCodeParameter = customsCodeParameter;
    }

    public List<LocationOffice> getLocationOffices() {
        return locationOffices;
    }

    public void setLocationOffices(List<LocationOffice> locationOffices) {
        this.locationOffices = locationOffices;
    }

}
