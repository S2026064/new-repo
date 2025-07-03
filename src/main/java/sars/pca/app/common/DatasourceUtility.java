package sars.pca.app.common;

import com.zaxxer.hikari.HikariDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author vongani
 */
public class DatasourceUtility {

    public static DataSource getDatasource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(10);
        dataSource.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");

//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAXR60\\SQLEXPRESS:2010;databaseName=PCA_DB");
//        dataSource.addDataSourceProperty("user", "Jiyeza");
//        dataSource.addDataSourceProperty("password", "P@ssw0rd");

//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAUS09\\SQLEXPRESS:1433;databaseName=PCA_DB");
//        dataSource.addDataSourceProperty("user", "sarsdev");
//        dataSource.addDataSourceProperty("password", "sarsdev");
//
//
//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAUX53\\SQLEXPRESS:2010;databaseName=PCA_DB");
//        dataSource.addDataSourceProperty("user", "Mpelwane");
//        dataSource.addDataSourceProperty("password", "Mpelwane12345");
//
//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAXN73\\SQLEXPRESS14:2010;databaseName=PCA_DB");
//        dataSource.addDataSourceProperty("user", "terry");
//        dataSource.addDataSourceProperty("password", "P@sswords.");
//        
          dataSource.addDataSourceProperty("url","jdbc:sqlserver://LPTAXX56\\SQLEXPRESS1:2010;databaseName=PCA_DB");
          dataSource.addDataSourceProperty("user", "tebx");
          dataSource.addDataSourceProperty("password", "tebx1234");
//       
//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://PTADVSQC05SQL:1433;databaseName=PCA_DB_DEV");
//        dataSource.addDataSourceProperty("user", "pca_user");
//        dataSource.addDataSourceProperty("password", "pass");
//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTBAC56\\SQLEXPRESS:1433;databaseName=PCA_DB");
//        dataSource.addDataSourceProperty("user", "sa");
//        dataSource.addDataSourceProperty("password", "P@ssw0rd@12345");
        return dataSource;
    }

    public static DataSource getDatasourceLookup() {
        try {
            InitialContext initialContext = new InitialContext();
            //DataSource dataSource = (DataSource) initialContext.lookup("jdbc/pcaDS");
            DataSource dataSource = (DataSource) initialContext.lookup("java:/pcaDS");
            return dataSource;
        } catch (NamingException ex) {
            Logger.getLogger(DatasourceUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
