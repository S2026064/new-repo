package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.UserRole;

/**
 *
 * @author S2026987
 */
public interface UserRoleServiceLocal {
    UserRole save(UserRole userRole);

    UserRole findById(Long id);

    UserRole update(UserRole userRole);

    UserRole deleteById(Long id);
    
    List<UserRole> listAll();
    
    boolean isExist(UserRole userRole);
    
    UserRole findByDescription(String description);
}
