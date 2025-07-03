package sars.pca.app.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.AddressType;
import sars.pca.app.common.Province;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address extends BaseEntity {
    @Column(name = "add_line1")
    private String line1;

    @Column(name = "add_line2")
    private String line2;

    @Column(name = "add_line3")
    private String line3;

    @Column(name = "add_line4")
    private String line4;

    @Column(name = "add_line5")
    private String line5;

    @Column(name = "area")
    private String area;

    @Column(name = "code")
    private String code;

    @Column(name = "add_type")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name = "countryName")
    private String countryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "province")
    private Province province;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.line1);
        hash = 83 * hash + Objects.hashCode(this.line2);
        hash = 83 * hash + Objects.hashCode(this.line3);
        hash = 83 * hash + Objects.hashCode(this.line4);
        hash = 83 * hash + Objects.hashCode(this.line5);
        hash = 83 * hash + Objects.hashCode(this.area);
        hash = 83 * hash + Objects.hashCode(this.code);
        hash = 83 * hash + Objects.hashCode(this.addressType);
        hash = 83 * hash + Objects.hashCode(this.countryName);
        hash = 83 * hash + Objects.hashCode(this.province);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.line1, other.line1)) {
            return false;
        }
        if (!Objects.equals(this.line2, other.line2)) {
            return false;
        }
        if (!Objects.equals(this.line3, other.line3)) {
            return false;
        }
        if (!Objects.equals(this.line4, other.line4)) {
            return false;
        }
        if (!Objects.equals(this.line5, other.line5)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.countryName, other.countryName)) {
            return false;
        }
        if (this.addressType != other.addressType) {
            return false;
        }
        return this.province == other.province;
    }   
}
