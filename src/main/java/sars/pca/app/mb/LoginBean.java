package sars.pca.app.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserStatus;
import sars.pca.app.service.UserServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@RequestScoped
public class LoginBean extends BaseBean<User> {

    @Autowired
    private UserServiceLocal userService;
    private String sidParam;

    public void signIn() {
        User user = userService.findBySid(sidParam);
        if (user != null) {
            if (user.getUserStatus().equals(UserStatus.ACTIVE)) {
                getActiveUser().setLogonUserSession(user);
                redirect("home");
            } else {
                addErrorMessage("System user with SID number", sidParam, " is not active");
            }
        } else {
            addErrorMessage("System user with SID number", sidParam, "does not exist");
        }
    }

    public String getSidParam() {
        return sidParam;
    }

    public void setSidParam(String sidParam) {
        this.sidParam = sidParam;
    }
}
