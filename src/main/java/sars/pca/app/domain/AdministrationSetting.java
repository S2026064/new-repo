package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "admin_sett")
@Getter
@Setter
public class AdministrationSetting extends BaseEntity {

    @Column(name = "sys_user_role")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean userRole;
    @Column(name = "sys_user")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean users;
    @Column(name = "sla_config")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean slaConfigs;
    @Column(name = "country")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean country;
    @Column(name = "location_office")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean locationOffice;
    @Column(name = "hs_chapter_risk")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean hsChapterRisk;
    @Column(name = "relevant_material")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean relevantMaterial;
    @Column(name = "offence_classification")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean offenceClassification;

    @Column(name = "section_rule")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean sectionRule;

    @Column(name = "offence_description")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean offenceDescription;

    @Column(name = "prioritisation_scope")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean prioritisationScore;

    @Column(name = "duty")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean duty;

    @Column(name = "program")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean program;
    @Column(name = "notification")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean notification;

    public AdministrationSetting() {
        this.userRole = Boolean.FALSE;
        this.users = Boolean.FALSE;
        this.slaConfigs = Boolean.FALSE;
        this.country = Boolean.FALSE;
        this.locationOffice = Boolean.FALSE;
        this.hsChapterRisk = Boolean.TRUE;
        this.relevantMaterial = Boolean.FALSE;
        this.offenceClassification = Boolean.FALSE;
        this.program = Boolean.FALSE;
        this.notification = Boolean.FALSE;
        this.prioritisationScore = Boolean.FALSE;
        this.duty = Boolean.FALSE;
    }

    public boolean isAdministrator() {
        return this.duty || this.prioritisationScore || this.offenceDescription || this.sectionRule || this.userRole || this.users || this.slaConfigs || this.country || this.locationOffice || this.hsChapterRisk || this.relevantMaterial || this.offenceClassification || this.program || this.notification;
    }
}
