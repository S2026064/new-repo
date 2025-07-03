package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "location_office")
@Getter
@Setter
public class LocationOffice extends BaseEntity {
    @Column(name = "province")
    @Enumerated(EnumType.STRING)
    private Province province;

    @Column(name = "area")
    private String area;

    @Column(name = "office_type")
    @Enumerated(EnumType.STRING)
    private OfficeType officeType;
}
