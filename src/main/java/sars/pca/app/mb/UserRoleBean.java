package sars.pca.app.mb;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.AdministrationSetting;
import sars.pca.app.domain.AuditingSettting;
import sars.pca.app.domain.DebtManagementSetting;
import sars.pca.app.domain.Permission;
import sars.pca.app.domain.ReportSetting;
import sars.pca.app.domain.RiskManagementSettting;
import sars.pca.app.domain.UserRole;
import sars.pca.app.domain.UserStatus;
import sars.pca.app.domain.VddlOrSarCaseSetting;
import sars.pca.app.service.UserRoleServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class UserRoleBean extends BaseBean<UserRole> {

    @Autowired
    private UserRoleServiceLocal userRoleService;
    @Autowired
    private UserServiceLocal userService;
    private UserStatus userStatus;

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("User Roles");
        addCollections(userRoleService.listAll());
    }

    public void addOrUpdate(UserRole userRole) {
        reset().setAdd(true);
        if (userRole != null) {
            setPanelTitleName("Update User Role");
            userRole.setUpdatedBy(getActiveUser().getSid());
            userRole.setUpdatedDate(new Date());
        } else {
            setPanelTitleName("Add User Role");
            userRole = new UserRole();
            userRole.setCreatedBy(getActiveUser().getSid());
            userRole.setCreatedDate(new Date());

            AdministrationSetting administrationSetting = new AdministrationSetting();
            administrationSetting.setCreatedBy(getActiveUser().getSid());
            administrationSetting.setCreatedDate(new Date());
            userRole.setAdminSetting(administrationSetting);

            VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
            vddlOrSarCaseSetting.setCreatedBy(getActiveUser().getSid());
            vddlOrSarCaseSetting.setCreatedDate(new Date());
            userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

            RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
            riskManagementSettting.setCreatedBy(getActiveUser().getSid());
            riskManagementSettting.setCreatedDate(new Date());
            userRole.setRiskManagementSettting(riskManagementSettting);

            AuditingSettting auditingSettting = new AuditingSettting();
            auditingSettting.setCreatedBy(getActiveUser().getSid());
            auditingSettting.setCreatedDate(new Date());
            userRole.setAuditingSetting(auditingSettting);

            DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
            debtManagementSetting.setCreatedBy(getActiveUser().getSid());
            debtManagementSetting.setCreatedDate(new Date());
            userRole.setDebtManagementSetting(debtManagementSetting);

            ReportSetting reportSetting = new ReportSetting();
            reportSetting.setCreatedBy(getActiveUser().getSid());
            reportSetting.setCreatedDate(new Date());
            userRole.setReportSetting(reportSetting);

            Permission permission = new Permission();
            permission.setCreatedBy(getActiveUser().getSid());
            permission.setCreatedDate(new Date());
            userRole.setPermission(permission);
            addCollection(userRole);
        }
        addEntity(userRole);
    }

    public void save(UserRole userRole) {
        if (userRole.getId() != null) {
            userRoleService.update(userRole);
            addInformationMessage("User Role was successfully updated.");
        } else {
            userRoleService.save(userRole);
            addInformationMessage("User Role was successfully created.");
        }
        reset().setList(true);
        setPanelTitleName("User Roles");
    }

    public void cancel(UserRole userRole) {
        if (userRole.getId() == null && getUserRoles().contains(userRole)) {
            removeEntity(userRole);
        }
        reset().setList(true);
        setPanelTitleName("User Roles");
    }

    public void delete(UserRole userRole) {
        if (userService.isUserRoleUsed(userRole)) {
            addWarningMessage("This Role cannot be deleted because it is being used by other Users");
            return;
        }
        userRoleService.deleteById(userRole.getId());
        removeEntity(userRole);
        addInformationMessage("User Role was successfully deleted");
        reset().setList(true);
        setPanelTitleName("User Roles");
    }

    public List<UserRole> getUserRoles() {
        return this.getCollections();
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
