package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.UserRoleType;
import sars.pca.app.domain.SarCaseUser;
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
public class SuspCaseAllocationBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;

    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    private List<User> specialists = new ArrayList<>();
    private List<User> reviewers = new ArrayList<>();
    private User allocatedUser;

    @PostConstruct
    public void init() {
        reset().setList(true);
        //User officeManager = userService.findBySid(getActiveUser().getSid());
        List<CaseStatus> sarCaseStatuses = new ArrayList<>();
        /*
        The CRCS Ops Manager pulls all cases with statuses shown below:
        -ACTIVE_SAR : This enable the manager to rellocate in case allocated user leaves the office.
        -REVIEWED_PENDING_ALLOCATION :These are cases coming the CRCS Reviewer upon accepting them. 
        -ALLOCATED_PENDING_INDEPTH_ANALYSIS : These are cases allocated to the CRCS Ops Specialist
         */
        sarCaseStatuses.add(CaseStatus.ACTIVE_SAR);
        sarCaseStatuses.add(CaseStatus.REVIEWED_PENDING_ALLOCATION);
        sarCaseStatuses.add(CaseStatus.ALLOCATED_PENDING_INDEPTH_ANALYSIS);
        sarCaseStatuses.add(CaseStatus.ACTIVE_CRCS_RA);
        sarCaseStatuses.add(CaseStatus.ACTIVE_CRCS_ANALYSIS);
        addCollections(suspiciousCaseService.findByStatusAndCrcsLocationOffice(sarCaseStatuses, getActiveUser().getUser().getLocationOffice()));
    }

    public void allocate(SuspiciousCase suspiciousCase) {
        reset().setAdd(true);
        specialists.clear();
        specialists.addAll(userService.findByUserRoleAndLocationOffice(UserRoleType.CRCS_OPS_SPECIALIST.toString(), suspiciousCase.getCrcsLocationOffice()));
        addEntity(suspiciousCase);
    }

    public void reAssign(SuspiciousCase suspiciousCase) {
        specialists.clear();
        reviewers.clear();
        reset().setAdd(true);
        if (suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_SAR)) {
            reviewers.addAll(userService.findByUserRoleAndLocationOffice(UserRoleType.CRCS_REVIEWER.toString(), suspiciousCase.getCrcsLocationOffice()));
        } else {
            specialists.addAll(userService.findByUserRoleAndLocationOffice(UserRoleType.CRCS_OPS_SPECIALIST.toString(), suspiciousCase.getCrcsLocationOffice()));
        }
        addEntity(suspiciousCase);
    }

    public void save(SuspiciousCase suspiciousCase) {
        //Remove the CRCS_REVIEWER OR CRCS_OPS_SPECIALIST who was working on the case before re-allocating to the next CRCS_REVIEWER OR CRCS_OPS_SPECIALIST
        for (SarCaseUser sarCaseUser : suspiciousCase.getSarCaseUsers()) {
            if (sarCaseUser.getUser().getUserRole().getDescription().equals(UserRoleType.CRCS_REVIEWER.toString())
                    && suspiciousCase.getStatus().equals(CaseStatus.ACTIVE_SAR)) {
                suspiciousCase.removeSarCaseUser(sarCaseUser);
            } else if (sarCaseUser.getUser().getUserRole().getDescription().equals(UserRoleType.CRCS_OPS_SPECIALIST.toString())) {
                suspiciousCase.removeSarCaseUser(sarCaseUser);
            }
            //if a condition pass break so it can create user
            break;
        }
        SarCaseUser sarCaseUser = new SarCaseUser();
        sarCaseUser.setCreatedBy(getActiveUser().getSid());
        sarCaseUser.setCreatedDate(new Date());
        sarCaseUser.setUser(allocatedUser);
        suspiciousCase.addSarCaseUser(sarCaseUser);//Setting the CRCS Ops Specialist to whom a case is allocated as a user.
        suspiciousCase.setUpdatedBy(getActiveUser().getSid());
        suspiciousCase.setUpdatedDate(new Date());
        suspiciousCase.setStatus(CaseStatus.ALLOCATED_PENDING_INDEPTH_ANALYSIS);
        removeEntity(suspiciousCase).addFreshEntity(suspiciousCaseService.update(suspiciousCase));
        addInformationMessage("Case allocated successfuly");

        //email to Specialist who case is allocated to: Updated 09/06/2024
        if (suspiciousCase.getStatus().equals(CaseStatus.ALLOCATED_PENDING_INDEPTH_ANALYSIS)) {
            emailNotificationSenderService.sendEmailNotification(NotificationType.SAR_ALLOCATION, 
                    suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), 
                    allocatedUser, getActiveUser().getUser());
        } else {
            emailNotificationSenderService.sendEmailNotification(NotificationType.SAR_REALLOCATION,
                    suspiciousCase.getCaseRefNumber(), suspiciousCase.getUpdatedDate(), 
                    allocatedUser, getActiveUser().getUser());
        }

        reset().setList(true);
    }

    public void cancel(SuspiciousCase suspiciousCase) {
        reset().setList(true);
    }

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }

    public List<User> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(List<User> specialists) {
        this.specialists = specialists;
    }

    public User getAllocatedUser() {
        return allocatedUser;
    }

    public void setAllocatedUser(User allocatedUser) {
        this.allocatedUser = allocatedUser;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<User> reviewers) {
        this.reviewers = reviewers;
    }

}
