package sars.pca.app.domain;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.PersonType;
import sars.pca.app.common.Province;

/**
 * @author vongani
 *
 */
@DiscriminatorValue("person")
@MappedSuperclass
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Person extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_names")
    private String fullNames;

    @Column(name = "identity_num")
    private String identityNumber;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "initials")
    private String initials;

    @Column(name = "province")
    @Enumerated(EnumType.STRING)
    private Province province;

    @Column(name = "person_type")
    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "home_tel_number")
    private String homeTelephoneNumber;

    @Column(name = "work_tel_number")
    private String workTelephoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "web_address")
    private String webAddress;

    @Column(name = "country_name")
    private String countryName;

}
