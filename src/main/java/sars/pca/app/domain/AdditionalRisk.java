package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.YesNoEnum;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "additional_risk")
@Getter
@Setter
public class AdditionalRisk extends BaseEntity {

    @Column(name = "contravention_history")
    @Enumerated(EnumType.STRING)
    private YesNoEnum contraventionHistory;

    @Column(name = "contravention_history_desc")
    private String contraventionHistoryDescription;

    @Column(name = "origin_country")
    @Enumerated(EnumType.STRING)
    private YesNoEnum originCountry;

    @Column(name = "origin_country_desc")
    private String originCountryDescription;

    @Column(name = "duty_exemption")
    @Enumerated(EnumType.STRING)
    private YesNoEnum dutyExemption;

    @Column(name = "duty_exemption_desc")
    private String dutyExemptionDescription;

    @Column(name = "duty_suspension")
    @Enumerated(EnumType.STRING)
    private YesNoEnum dutySuspension;

    @Column(name = "duty_suspension_desc")
    private String dutySuspensionDescription;

    @Column(name = "licensing_and_reg")
    @Enumerated(EnumType.STRING)
    private YesNoEnum licensingAndRegistration;

    @Column(name = "licensing_and_reg_desc")
    private String licensingAndRegistrationDescription;

    @Column(name = "other_p_and_r")
    @Enumerated(EnumType.STRING)
    private YesNoEnum otherPAndR;

    @Column(name = "other_p_and_r_desc")
    private String otherPAndRDescription;

    @Column(name = "mode_of_transport")
    @Enumerated(EnumType.STRING)
    private YesNoEnum modeOfTransport;

    @Column(name = "mode_of_transport_desc")
    private String modeOfTransportDescription;

    @Column(name = "revenue_protection")
    @Enumerated(EnumType.STRING)
    private YesNoEnum revenueProtection;

    @Column(name = "revenue_protection_desc")
    private String revenueProtectionDescription;

    @Column(name = "rules_of_origin")
    @Enumerated(EnumType.STRING)
    private YesNoEnum rulesOfOrigin;

    @Column(name = "rules_of_origin_desc")
    private String rulesOfOriginDescription;

    @Column(name = "security")
    @Enumerated(EnumType.STRING)
    private YesNoEnum security;

    @Column(name = "security_desc")
    private String securityDescription;

    @Column(name = "tarrif")
    @Enumerated(EnumType.STRING)
    private YesNoEnum tarrif;

    @Column(name = "tarrif_desc")
    private String tarrifDescription;

    @Column(name = "trade_restrictions")
    @Enumerated(EnumType.STRING)
    private YesNoEnum tradeRestrictions;

    @Column(name = "trade_restriction_desc")
    private String tradeRestrictionDescription;

    @Column(name = "valuation")
    @Enumerated(EnumType.STRING)
    private YesNoEnum valuation;

    @Column(name = "valuation_desc")
    private String valuationDescription;

    @Column(name = "warehousing")
    @Enumerated(EnumType.STRING)
    private YesNoEnum warehousing;

    @Column(name = "warehousing_desc")
    private String warehousingDescription;

    public AdditionalRisk() {
        this.contraventionHistory = YesNoEnum.NO;
        this.originCountry = YesNoEnum.NO;
        this.dutyExemption = YesNoEnum.NO;
        this.dutySuspension = YesNoEnum.NO;
        this.licensingAndRegistration = YesNoEnum.NO;
        this.otherPAndR = YesNoEnum.NO;
        this.modeOfTransport = YesNoEnum.NO;
        this.revenueProtection = YesNoEnum.NO;
        this.rulesOfOrigin = YesNoEnum.NO;
        this.security = YesNoEnum.NO;
        this.tarrif = YesNoEnum.NO;
        this.tradeRestrictions = YesNoEnum.NO;
        this.valuation = YesNoEnum.NO;
        this.warehousing = YesNoEnum.NO;
    }

}
