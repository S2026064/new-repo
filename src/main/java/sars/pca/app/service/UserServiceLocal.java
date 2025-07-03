package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;

/**
 *
 * @author S2026987
 */
public interface UserServiceLocal {

    User save(User user);

    User findById(Long id);

    User update(User user);

    User deleteById(Long id);

    List<User> listAll();

    boolean isExist(User user);

    User findBySid(String sid);

    List<User> searchForSystemUsers(String searchParam);

    List<User> findBySidOrFirstNameOrLastName(String searchParam);

    boolean isUserRoleUsed(UserRole userRole);

    List<User> findByUserRoleAndLocationOffice(UserRole userRole, LocationOffice locationOffice);

    List<User> findByUserRole(UserRole userRole);

    List<User> findUserByUserRoleDescription(String description);

    List<User> findByUserRoleAndLocationOffice(String userRoleDescription, LocationOffice locationOffice);

    //email notification
    List<User> findByUserRoleAndManager(String userRole, User manager);

    User findUserByUserRoleAndLocationOffice(String userRoleDescription,LocationOffice locationOffice);

    User findByUserRoleDescription(String userRoleDescription);

}
