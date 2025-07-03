package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.Office;
import sars.pca.app.persistence.OfficeRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class OfficeService implements OfficeServiceLocal {

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public Office save(Office office) {
        return officeRepository.save(office);
    }

    @Override
    public Office findById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public Office update(Office office) {
        return officeRepository.save(office);
    }

    @Override
    public Office deleteById(Long id) {
        Office office = findById(id);

        if (office != null) {
            officeRepository.delete(office);
        }
        return office;
    }

    @Override
    public List<Office> listAll() {
        return officeRepository.findAll();
    }

    @Override
    public boolean isExist(Office office) {
        return officeRepository.findById(office.getId()) != null;
    }

}
