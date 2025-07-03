package sars.pca.app.common;

/**
 *
 * @author S2026080
 */
public class DatasourceFactory {

    public static DatasourceService getDatabase(ConnectionType connectionType) {
        if (connectionType.equals(ConnectionType.EMPLOYEE_DATABASE)) {
            return new EmployeeDatasourceService();
        } else if (connectionType.equals(ConnectionType.IBR_PCA_DATABASE)) {
            return new IbrPcaDatasourceService();
        }
        return null;
    }
}
