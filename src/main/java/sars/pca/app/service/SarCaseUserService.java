package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.persistence.SarCaseUserRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class SarCaseUserService implements SarCaseUserServiceLocal {

    @Autowired
    private SarCaseUserRepository sarCaseUserRepository;

    @Override
    public SarCaseUser save(SarCaseUser sarCaseUser) {
        return sarCaseUserRepository.saveAndFlush(sarCaseUser);
    }

    @Override
    public SarCaseUser findById(Long id) {
        return sarCaseUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SarCaseUser update(SarCaseUser sarCaseUser) {
        return sarCaseUserRepository.saveAndFlush(sarCaseUser);
    }

    @Override
    public SarCaseUser deleteById(Long id) {
        SarCaseUser sarCaseUser = findById(id);

        if (sarCaseUser != null) {
            sarCaseUserRepository.delete(sarCaseUser);
        }
        return sarCaseUser;
    }

    @Override
    public List<SarCaseUser> listAll() {
        return sarCaseUserRepository.findAll();
    }

    @Override
    public SarCaseUser findBySarCase(SarCase sarCase) {
        return sarCaseUserRepository.findBySarCase(sarCase);
    }

    @Override
    public SarCaseUser findByUser(User user) {
        return sarCaseUserRepository.findByUser(user);
    }

    @Override
    public SarCaseUser findByUserSid(String sid) {
        return sarCaseUserRepository.findByUserSid(sid);
    }
//
//    @Override
//    public List<SarCaseUser> findByUser(User user, SarCaseStatus status) {
//        return sarCaseUserRepository.findByUser(user, status);
//    }

    @Override
    public List<SarCaseUser> findBySarCase(SarCase sarCase, User user) {
        return sarCaseUserRepository.findBySarCase(sarCase, user);
    }

    @Override
    public SarCaseUser findByUserRoleAndManagerSid(String description, String managerSid) {
        return sarCaseUserRepository.findByUserRoleAndManagerSid(description, managerSid);
    }

    @Override
    public SarCaseUser findUserByUserRoleAndSuspCase(String description, SuspiciousCase suspCase) {
        return sarCaseUserRepository.findUserByUserRoleAndSuspCase(description, suspCase);
    }

}
