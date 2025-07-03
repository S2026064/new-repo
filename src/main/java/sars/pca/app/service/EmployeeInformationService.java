package sars.pca.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import sars.pca.app.common.ConnectionType;
import sars.pca.app.common.DatasourceFactory;
import sars.pca.app.common.DatasourceService;
import sars.pca.app.common.PersonType;
import sars.pca.app.domain.User;

/**
 *
 * @author S2026987
 */
@Service
public class EmployeeInformationService implements EmployeeInformationServiceLocal {

    @Override
    public User getEmployeeUserBySid(String sid, String userSid) {

        User user = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.EMPLOYEE_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.GetEmployeeBySID_Amakhwezi(?)}");
            stmt.setString(1, sid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("costCentreNumber") != null) {
                    user = new User();
                    user.setCreatedBy(userSid);
                    user.setCreatedDate(new Date());
                    user.setSid(sid);
                    user.setFullNames(rs.getString("fullName"));
//                    user.setLastName(rs.getString("Surname"));
                    user.setInitials(rs.getString("Initials"));
                    user.setPersonType(PersonType.SYSTEM_USER);
                    user.setDivisionCode(rs.getString("divisionCode"));
                    user.setDivisionName(rs.getString("devisionName"));
                    user.setRegionCode(rs.getString("Region"));
                    user.setRegionName(rs.getString("RegionName"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(EmployeeInformationService.class.getName() + ":" + sid).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(EmployeeInformationService.class.getName() + ":" + sid).log(Level.SEVERE, null, e);
            }
        }
        return user;
    }

}
