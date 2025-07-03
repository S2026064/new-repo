package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;

/**
 *
 * @author S2026987
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findBySid(String sid);

    List<User> findByUserRole(UserRole userRole);

    @Query("SELECT e FROM User e WHERE e.userRole.description=:descript")
    List<User> findUserByUserRoleDescription(@Param("descript") String description);

    @Query("SELECT e FROM User e WHERE e.sid LIKE %:sid%")
    List<User> searchForSystemUsers(String sid);

    @Query("SELECT e FROM User e WHERE e.sid LIKE %:sid% OR e.fullNames LIKE %:fullnames%")
    List<User> findBySidOrFirstNameOrLastName(@Param("sid") String sid, @Param("fullnames") String fullnames);

    List<User> findByUserRoleAndLocationOffice(UserRole userRole, LocationOffice locationOffice);

    @Query("SELECT e FROM User e WHERE e.userRole.description=:userRoleDesc AND e.locationOffice=:locOffice")
    List<User> findByUserRoleAndLocationOffice(@Param("userRoleDesc") String userRoleDescription, @Param("locOffice") LocationOffice locationOffice);

    //for email notification
    @Query("SELECT e FROM User e WHERE e.userRole.description=:userRoleDesc AND e.manager=:manager")
    List<User> findByUserRoleAndManager(@Param("userRoleDesc") String userRole, @Param("manager") User manager);

    @Query("SELECT e FROM User e WHERE e.userRole.description=:userRoleDesc AND e.locationOffice=:locOffice")
    User findUserByUserRoleAndLocationOffice(@Param("userRoleDesc") String userRoleDescription, @Param("locOffice") LocationOffice locationOffice);

    @Query("SELECT e FROM User e WHERE e.userRole.description=:userRoleDesc")
    User findByUserRoleDescription(@Param("userRoleDesc") String userRoleDescription);

//    @Query("SELECT e FROM User e inner join subordinates u WHERE u.userRole.description =:userRoleDesc AND e.manager=:opsMan")
//    List<User> findByOpsSpecialistsByManagerandRole(@Param("userRoleDesc") String userRoleDescription, @Param("opsMan") User manager);
}
