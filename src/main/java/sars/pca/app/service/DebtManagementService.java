package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.DebtManagement;
import sars.pca.app.persistence.DebtManagementRepository;

/**
 *
 * @author S2028398
 */
@Service
@Transactional
public class DebtManagementService implements DebtManagementServiceLocal {

    @Autowired
    private DebtManagementRepository debtManagementRepository;

    @Override
    public DebtManagement save(DebtManagement debtManagement) {
        return debtManagementRepository.save(debtManagement);
    }

    @Override
    public DebtManagement findById(Long id) {
        return debtManagementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public DebtManagement update(DebtManagement debtManagement) {
        return debtManagementRepository.save(debtManagement);
    }

    @Override
    public DebtManagement deleteById(Long id) {
        DebtManagement debtManagement = findById(id);
        if (debtManagement != null) {
            debtManagementRepository.delete(debtManagement);
        }
        return debtManagement;
    }

    @Override
    public List<DebtManagement> listAll() {
        return debtManagementRepository.findAll();
    }

    @Override
    public boolean isExist(DebtManagement debtManagement) {
        return debtManagementRepository.findById(debtManagement.getId()) != null;
    }
}
