package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;
import sars.pca.app.persistence.UserRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class UserService implements UserServiceLocal {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User deleteById(Long id) {
        User user = findById(id);

        if (user != null) {
            userRepository.delete(user);
        }
        return user;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isExist(User user) {
        return userRepository.findById(user.getId()) != null;
    }

    @Override
    public User findBySid(String sid) {
        return userRepository.findBySid(sid);
    }

    @Override
    public List<User> searchForSystemUsers(String searchParam) {
        return userRepository.searchForSystemUsers(searchParam);
    }

    @Override
    public List<User> findBySidOrFirstNameOrLastName(String searchParam) {
        return userRepository.findBySidOrFirstNameOrLastName(searchParam, searchParam);
    }

    @Override
    public boolean isUserRoleUsed(UserRole userRole) {
        return !userRepository.findByUserRole(userRole).isEmpty();
    }

    @Override
    public List<User> findByUserRoleAndLocationOffice(UserRole userRole, LocationOffice locationOffice) {
        return userRepository.findByUserRoleAndLocationOffice(userRole, locationOffice);
    }

    @Override
    public List<User> findByUserRole(UserRole userRole) {
        return userRepository.findByUserRole(userRole);
    }

    @Override
    public List<User> findUserByUserRoleDescription(String description) {
        return userRepository.findUserByUserRoleDescription(description);
    }

    @Override
    public List<User> findByUserRoleAndLocationOffice(String userRoleDescription, LocationOffice locationOffice) {
        return userRepository.findByUserRoleAndLocationOffice(userRoleDescription, locationOffice);
    }

//    @Override
//    public List<User> findByOpsSpecialistsByManagerandRole(String userRoleDescription, User manager) {
//        return userRepository.findByOpsSpecialistsByManagerandRole(userRoleDescription, manager);
//    }
    //email notification
    @Override
    public List<User> findByUserRoleAndManager(String userRole, User manager) {
        return userRepository.findByUserRoleAndManager(userRole, manager);
    }

    @Override
    public User findUserByUserRoleAndLocationOffice(String userRoleDescription, LocationOffice locationOffice) {
        return userRepository.findUserByUserRoleAndLocationOffice(userRoleDescription, locationOffice);
    }

    @Override
    public User findByUserRoleDescription(String userRoleDescription) {
        return userRepository.findByUserRoleDescription(userRoleDescription);
    }

}
