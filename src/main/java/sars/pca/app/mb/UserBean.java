package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;
import sars.pca.app.domain.UserStatus;
import sars.pca.app.service.EmployeeInformationServiceLocal;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.UserRoleServiceLocal;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class UserBean extends BaseBean<User> {

    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private UserRoleServiceLocal userRoleService;
    @Autowired
    private EmployeeInformationServiceLocal employeeInformationService;
    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;
    private List<UserRole> userRoles;
    private List<UserStatus> userStatuses;
    private List<User> managers;
    private List<LocationOffice> locationOffices;
    private boolean subordinate;
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());
    private String userTitle;
    private String searchParameter;
    private String sid;

    @PostConstruct
    public void init() {
        userRoles = new ArrayList<>();
        userStatuses = new ArrayList<>();
        managers = new ArrayList<>();
        locationOffices = new ArrayList<>();
        reset().setList(true);
        setPanelTitleName("Users");
        userRoles.addAll(userRoleService.listAll());
        userStatuses.addAll(Arrays.asList(UserStatus.values()));
        locationOffices = locationOfficeService.listAll();
        subordinate = Boolean.FALSE;
    }

    public void updateUser(User user) {
        reset().setAdd(true);
        if (user == null) {
            addWarningMessage("The user you have selected is invalid, please check the user and try again");
            return;
        }
        setPanelTitleName("Update User");
        user.setUpdatedBy(getActiveUser().getSid());
        user.setUpdatedDate(new Date());
        addEntity(user);
    }

    public void addUser() {
        reset().setSearch(true);
        setPanelTitleName("Search Employee");
    }

    public void searchEmployee() {
        if (this.sid.isEmpty() || this.sid == null || this.sid.equals("")) {
            addWarningMessage("Please enter the employee S-ID");
            return;
        }
        User existingUser = userService.findBySid(sid);
        if (existingUser != null) {
            addWarningMessage("An employee with an sid of ", sid, " already exist as a user");
            return;
        }
        User user = employeeInformationService.getEmployeeUserBySid(sid, getActiveUser().getSid());
        if (user == null) {
            addWarningMessage("An employee with an sid of ", sid, " does not exist as a SARS employee");
            return;
        }
        addCollection(user);
        addEntity(user);
        reset().setAdd(true);
        setPanelTitleName("Add User");
    }

    public void save(User user) {
        try {
            if (user.getId() != null) {
                userService.update(user);
            } else {
                userService.save(user);
            }
            reset().setList(true);
            addInformationMessage("User successfully saved");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        setPanelTitleName("Users");
    }
    public void cancel(User user) {
        if (user.getId() == null && getCollections().contains(user)) {
            removeEntity(user);
        }
        reset().setList(true);
        setPanelTitleName("Users");
    }
    public void delete(User user) {
        try {
            userService.deleteById(user.getId());
            removeEntity(user);
            addInformationMessage("The content of Sid with the name ", user.getSid(), " was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
    public void onSystemUserSearchListener() {
        getCollections().clear();
        if (searchParameter.isEmpty()) {
            addInformationMessage("Enter search criteria");
            return;
        }
        addCollections(userService.findBySidOrFirstNameOrLastName(searchParameter));
        if (getCollections().isEmpty()) {
            addInformationMessage("The employee you are searching for is not registered as a user in this system");
            return;
        }
        setUserTitle("Users");
    }
    public void userRoleListener() {
        managers.clear();
        switch (getEntity().getUserRole().getDescription()) {
            case "CRCS Reviewer":
            case "CRCS Quality Assurer":
            case "CRCS Ops Specialist":
            case "SARS internal User":
                managers.addAll(userService.findUserByUserRoleDescription("CRCS Ops Manager"));
                subordinate = Boolean.TRUE;
                break;
            case "CI Auditor":
            case "Senior Auditor":
            case "Management":
                managers.addAll(userService.findUserByUserRoleDescription("CI Ops Manager"));
                subordinate = Boolean.TRUE;
                break;
            case "CRCS Ops Manager":
            case "CI Ops Manager":
                managers.addAll(userService.findUserByUserRoleDescription("Group Manager"));
                subordinate = Boolean.TRUE;
                break;
            case "Senior Manager":
                managers.addAll(userService.findUserByUserRoleDescription("Executive"));
                subordinate = Boolean.TRUE;
                break;
            case "Group Manager":
                managers.addAll(userService.findUserByUserRoleDescription("Senior Manager"));
                subordinate = Boolean.TRUE;
                break;
            default:
                subordinate = Boolean.FALSE;
                break;
        }
    }
    public List<User> getUsers() {
        return this.getCollections();
    }
    public List<UserRole> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    public List<User> getManagers() {
        return managers;
    }
    public void setManagers(List<User> managers) {
        this.managers = managers;
    }
    public String getUserTitle() {
        return userTitle;
    }
    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }
    public String getSearchParameter() {
        return searchParameter;
    }
    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public List<UserStatus> getUserStatuses() {
        return userStatuses;
    }
    public void setUserStatuses(List<UserStatus> userStatuses) {
        this.userStatuses = userStatuses;
    }
    public List<LocationOffice> getLocationOffices() {
        return locationOffices;
    }
    public void setLocationOffices(List<LocationOffice> locationOffices) {
        this.locationOffices = locationOffices;
    }
    public boolean isSubordinate() {
        return subordinate;
    }
    public void setSubordinate(boolean subordinate) {
        this.subordinate = subordinate;
    }
}
