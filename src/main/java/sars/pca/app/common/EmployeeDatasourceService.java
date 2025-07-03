package sars.pca.app.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author S2026987
 */
public class EmployeeDatasourceService implements DatasourceService {

    @Override
    public Connection getDatasourceConnection() {
        try {
            InitialContext initialContext = new InitialContext();
            //DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("jdbc/users");
            DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("java:/users");
            return dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            Logger.getLogger(EmployeeDatasourceService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
