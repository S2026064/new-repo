package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.persistence.LocationOfficeRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class LocationOfficeService implements LocationOfficeServiceLocal {

    @Autowired
    private LocationOfficeRepository locationOfficeRepository;

    @Override
    public LocationOffice save(LocationOffice locationOffice) {
        return locationOfficeRepository.save(locationOffice);
    }

    @Override
    public LocationOffice findById(Long id) {
        return locationOfficeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public LocationOffice update(LocationOffice locationOffice) {
        return locationOfficeRepository.save(locationOffice);
    }

    @Override
    public LocationOffice deleteById(Long id) {
        LocationOffice locationOffice = findById(id);
        if (locationOffice != null) {
            locationOfficeRepository.delete(locationOffice);
        }
        return locationOffice;
    }

    @Override
    public List<LocationOffice> listAll() {
        return locationOfficeRepository.findAll();
    }

    @Override
    public boolean isExist(LocationOffice locationOffice) {
        return locationOfficeRepository.findById(locationOffice.getId()) != null;
    }

    @Override
    public List<LocationOffice> findByOfficeType(OfficeType officeType) {
        return locationOfficeRepository.findByOfficeType(officeType);
    }

    @Override
    public LocationOffice findByAreaAndOfficeType(String area, OfficeType officeType) {
        return locationOfficeRepository.findByAreaAndOfficeType(area, officeType);
    }

    @Override
    public List<LocationOffice> findByOfficeTypeAndProvince(OfficeType officeType, Province province) {
        return locationOfficeRepository.findByOfficeTypeAndProvince(officeType, province);
    }

}
