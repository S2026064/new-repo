package sars.pca.app.mb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import sars.pca.app.common.JsonDocumentDto;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.Properties;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.service.EmailNotificationSenderServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseIndepthAnalysisBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;

    //notification services used
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<AttachmentType> attachmentTypes = new ArrayList<>();
    private AttachmentType selectedAttachmentType;

    @PostConstruct
    public void init() {
        reset().setList(true);
        List<CaseStatus> caseStatuses = new ArrayList<>();
        caseStatuses.add(CaseStatus.REJECTED_DISCARDED_INDEPTH_ANALYSIS);
        caseStatuses.add(CaseStatus.ALLOCATED_PENDING_INDEPTH_ANALYSIS);
        attachmentTypes.addAll(Arrays.asList(AttachmentType.values()));
        addCollections(suspiciousCaseService.findByStatusAndUserSid(caseStatuses, getActiveUser().getUser().getSid()));
    }

    public void addOrUpdate(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        addEntity(suspiciousCase);
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        if (suspiciousCase.getId() == null && this.getSuspiciousCases().contains(suspiciousCase)) {
            removeEntity(suspiciousCase);
        }
        reset().setList(true);
    }

    public void addComment(SuspiciousCase suspiciousCase) {
        Comment comment = new Comment();
        comment.setCreatedBy(getActiveUser().getSid());
        comment.setCreatedDate(new Date());
        comment.setRender(true);
        suspiciousCase.addComment(comment);
        addEntity(suspiciousCase);
    }

    public void saveComment(Comment comment) {
        suspiciousCaseService.update(getEntity());
        comment.setRender(false);
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

    public void accept(SuspiciousCase suspiciousCase) {
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        suspiciousCase.setStatus(CaseStatus.ACTIVE_CRCS_ANALYSIS);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Case accepted successfully.");
        reset().setList(true);
    }

    public void discard(SuspiciousCase suspiciousCase) {
        if (hasActiveUserCommented(suspiciousCase)) {
            suspiciousCase.setUpdatedBy(getActiveUser().getSid());
            suspiciousCase.setUpdatedDate(new Date());
            suspiciousCase.setStatus(CaseStatus.QA_REVIEW_POOL);
            removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
            addInformationMessage("Case discarded successfully.");

            //email to QA after discarded case indepth analysis
            List<User> recipient = userService.findUserByUserRoleDescription(UserRoleType.CRCS_QUALITY_ASSURER.toString());
            emailNotificationSenderService.sendEmailNotifications(NotificationType.INDEPTH_DISCARDED_POOL, suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), recipient, getActiveUser().getUser());
            reset().setList(true);
        } else {
            addErrorMessage("Enter comments as to why you discard the case.");
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (getEntity().getCaseType().equals(CaseType.VDDL)) {
            getPublicOfficers().clear();
            getPublicOfficers().addAll(getIbrDataService().getPublicOfficer(getEntity().getIbrData().getClNbr(), getActiveUser().getSid()));
        } else {
            populateIbrdata(event.getTab().getId(), getEntity().getSarCase().getCustomExciseCode());
        }
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

    public void removeAttachment(Attachment attachment) {
        getEntity().removeAttachment(attachment);
        if (attachment.getId() != null) {
            addEntity(suspiciousCaseService.update(getEntity()));
        }
    }

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
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

}
