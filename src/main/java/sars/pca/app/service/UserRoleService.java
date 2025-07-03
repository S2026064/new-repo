package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.UserRole;
import sars.pca.app.persistence.UserRoleRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class UserRoleService implements UserRoleServiceLocal {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public UserRole update(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole deleteById(Long id) {
        UserRole userRole = findById(id);

        if (userRole != null) {
            userRoleRepository.delete(userRole);
        }
        return userRole;
    }

    @Override
    public List<UserRole> listAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public boolean isExist(UserRole userRole) {
        return userRoleRepository.findById(userRole.getId()) != null;
    }

    @Override
    public UserRole findByDescription(String description) {
        return userRoleRepository.findByDescription(description);
    }

}
