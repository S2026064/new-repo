package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;

/**
 *
 * @author S2026987
 */
public interface SarCaseUserServiceLocal {

    SarCaseUser save(SarCaseUser sarCaseUser);

    SarCaseUser findById(Long id);

    SarCaseUser update(SarCaseUser sarCaseUser);

    SarCaseUser deleteById(Long id);

    List<SarCaseUser> listAll();

    SarCaseUser findBySarCase(SarCase sarCase);

    SarCaseUser findByUser(User user);

    SarCaseUser findByUserSid(String sid);

//    List<SarCaseUser> findByUser(User user, SarCaseStatus status);
    List<SarCaseUser> findBySarCase(SarCase sarCase, User user);

    SarCaseUser findUserByUserRoleAndSuspCase(String description, SuspiciousCase suspCase);

    SarCaseUser findByUserRoleAndManagerSid(String description, String managerSid);

}
