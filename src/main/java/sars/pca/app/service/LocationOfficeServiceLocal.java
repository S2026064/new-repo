package sars.pca.app.service;

import java.util.List;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;
import sars.pca.app.domain.LocationOffice;

/**
 *
 * @author S2026987
 */
public interface LocationOfficeServiceLocal {

    LocationOffice save(LocationOffice locationOffice);

    LocationOffice findById(Long id);

    LocationOffice update(LocationOffice locationOffice);

    LocationOffice deleteById(Long id);

    List<LocationOffice> listAll();

    boolean isExist(LocationOffice locationOffice);

    List<LocationOffice> findByOfficeType(OfficeType officeType);
    
    LocationOffice findByAreaAndOfficeType(String area, OfficeType officeType);
    
    List<LocationOffice> findByOfficeTypeAndProvince(OfficeType officeType, Province province);
}
