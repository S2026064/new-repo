package sars.pca.app.domain;

import java.lang.reflect.Field;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.TimeFrame;
import sars.pca.app.common.YearlyLoss;
import sars.pca.app.common.YesNoEnum;

@Entity
@Table(name = "non_compliance_details")
@Getter
@Setter
public class NonComplianceDetails extends BaseEntity {

    @Column(name = "time_frame")
    @Enumerated(EnumType.STRING)
    private TimeFrame timeFrame;

    @Column(name = "yearly_loss")
    @Enumerated(EnumType.STRING)
    private YearlyLoss yearlyLoss;

    @Column(name = "other_description")
    private String otherDescription;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "location", length = 4000)
    private String location;

    @Column(name = "day_time", length = 4000)
    private String dayTime;

    @Column(name = "additional_information", length = 4000)
    private String additionalInformation;

    @Column(name = "licensing_registration")
    @Enumerated(EnumType.STRING)
    private YesNoEnum licensingRegistration;

    @Column(name = "submission")
    @Enumerated(EnumType.STRING)
    private YesNoEnum submission;

    @Column(name = "declaration")
    @Enumerated(EnumType.STRING)
    private YesNoEnum declaration;

    @Column(name = "payment")
    @Enumerated(EnumType.STRING)
    private YesNoEnum payment;

    @Column(name = "illegal_activities")
    @Enumerated(EnumType.STRING)
    private YesNoEnum illegalActivities;

    @Column(name = "other")
    @Enumerated(EnumType.STRING)
    private YesNoEnum other;

    @Column(name = "incident_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date incidentDate;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = SarCase.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sarcase_id")
    private SarCase sarCase;

    public NonComplianceDetails() {
        this.timeFrame = TimeFrame.THE_INCIDENT_WILL_HAPPEN_TODAY;
        this.yearlyLoss = YearlyLoss.BETWEEN_100001_AND_500000;
        this.licensingRegistration = YesNoEnum.NO;
        this.submission = YesNoEnum.NO;
        this.declaration = YesNoEnum.NO;
        this.payment = YesNoEnum.NO;
        this.illegalActivities = YesNoEnum.NO;
        this.other = YesNoEnum.NO;
        this.incidentDate = new Date();
    }

    public void merge(Object newObject) {
        assert this.getClass().getName().equals(newObject.getClass().getName());
        for (Field field : this.getClass().getDeclaredFields()) {
            for (Field newField : newObject.getClass().getDeclaredFields()) {
                if (field.getName().equals(newField.getName())) {
                    try {
                        field.set(
                                this,
                                newField.get(newObject) == null
                                ? field.get(this)
                                : newField.get(newObject));

                    } catch (IllegalAccessException ignore) {
                        // Field update exception on final modifier and other cases.
                    }
                }
            }
        }
    }
}
