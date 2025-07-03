package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.DutyType;
import sars.pca.app.domain.Duty;
import sars.pca.app.persistence.DutyRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class DutyService implements DutyServiceLocal {

    @Autowired
    private DutyRepository dutyRepository;

    @Override
    public Duty save(Duty duty) {
        return dutyRepository.save(duty);
    }

    @Override
    public Duty findById(Long id) {
        return dutyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public Duty update(Duty duty) {
        return dutyRepository.save(duty);
    }

    @Override
    public Duty deleteById(Long id) {
        Duty duty = findById(id);
        if (duty != null) {
            dutyRepository.delete(duty);
        }
        return duty;
    }

    @Override
    public List<Duty> listAll() {
        return dutyRepository.findAll();
    }

    @Override
    public boolean isExist(Duty duty) {
        return dutyRepository.findById(duty.getId()) != null;
    }

    @Override
    public List<Duty> findByDutyType(DutyType dutyType) {
        return dutyRepository.findByDutyType(dutyType);
    }
}
