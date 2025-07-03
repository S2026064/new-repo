package sars.pca.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;
import sars.pca.app.domain.LocationOffice;

/**
 *
 * @author S2026987
 */
@Repository
public interface LocationOfficeRepository extends JpaRepository<LocationOffice, Long> {

    List<LocationOffice> findByOfficeType(OfficeType officeType);

    LocationOffice findByAreaAndOfficeType(String area, OfficeType officeType);

    List<LocationOffice> findByOfficeTypeAndProvince(OfficeType officeType, Province province);
}
