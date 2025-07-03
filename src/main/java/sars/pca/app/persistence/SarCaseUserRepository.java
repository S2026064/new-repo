package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;

/**
 *
 * @author S2026987
 */
@Repository
public interface SarCaseUserRepository extends JpaRepository<SarCaseUser, Long> {

    SarCaseUser findBySarCase(SarCase sarCase);

    SarCaseUser findByUser(User user);

//    @Query("SELECT e FROM SarCaseUser e WHERE e.user=:allocatedUser AND e.sarCase.status=:sarCaseStatus")
//    List<SarCaseUser> findByUser(@Param("allocatedUser") User user, @Param("sarCaseStatus") SarCaseStatus status);
    @Query("SELECT e FROM SarCaseUser e WHERE e.sarCase=:sarCase AND e.user=:allocatedUser")
    List<SarCaseUser> findBySarCase(@Param("sarCase") SarCase sarCase, @Param("allocatedUser") User user);

    @Query("SELECT e FROM SarCaseUser e WHERE e.user.sid=:userSid")
    SarCaseUser findByUserSid(@Param("userSid") String sid);

    @Query("SELECT e FROM SarCaseUser e WHERE e.user.userRole.description=:descript AND e.suspiciousCase=:suspCase")
    SarCaseUser findUserByUserRoleAndSuspCase(@Param("descript") String description, @Param("suspCase") SuspiciousCase suspCase);

    @Query("SELECT e FROM SarCaseUser e WHERE e.user.userRole.description=:descript AND e.user.manager.sid=:managerSid")
    SarCaseUser findByUserRoleAndManagerSid(@Param("descript") String description, @Param("managerSid") String managerSid);
}
