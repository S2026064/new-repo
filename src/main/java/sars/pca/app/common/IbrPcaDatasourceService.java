/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.pca.app.common;

import com.zaxxer.hikari.HikariDataSource;
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
public class IbrPcaDatasourceService implements DatasourceService {

    @Override
    public Connection getDatasourceConnection() {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setInitializationFailTimeout(0);
            dataSource.setMaximumPoolSize(10);
            dataSource.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
            dataSource.addDataSourceProperty("url", "jdbc:sqlserver://PTAQASQC08SQL:1433;databaseName=IBR_PCAData"); //PTAQASQC05N1
            dataSource.addDataSourceProperty("user", "pca_ibr_user");
            dataSource.addDataSourceProperty("password", "pass");
            dataSource.setConnectionTimeout(5000);
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IbrPcaDatasourceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    @Override
//    public Connection getDatasourceConnection() {
//        try {
//            InitialContext initialContext = new InitialContext();
//            //DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("jdbc/ibrdata");
//            DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("java:/ibrdata");
//            return dataSource.getConnection();
//        } catch (NamingException | SQLException e) {
//            Logger.getLogger(EmployeeDatasourceService.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return null;
//    }
}
