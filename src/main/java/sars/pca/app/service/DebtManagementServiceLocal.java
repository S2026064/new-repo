package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.DebtManagement;

/**
 *
 * @author S2028398
 */
public interface DebtManagementServiceLocal {

    DebtManagement save(DebtManagement debtManagement);

    DebtManagement findById(Long id);

    DebtManagement update(DebtManagement debtManagement);

    DebtManagement deleteById(Long id);

    List<DebtManagement> listAll();
    
    boolean isExist(DebtManagement debtManagement);
}
