package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.DutyType;
import sars.pca.app.domain.Duty;


public interface DutyServiceLocal {
    Duty save(Duty duty);

    Duty findById(Long id);

    Duty update(Duty duty);

    Duty deleteById(Long id);

    List<Duty> listAll();

    boolean isExist(Duty duty);

    List<Duty> findByDutyType(DutyType dutyType);
}
