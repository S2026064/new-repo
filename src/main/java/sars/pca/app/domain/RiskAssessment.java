package sars.pca.app.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.pca.app.common.MainIndustry;
import sars.pca.app.common.YesNoEnum;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "risk_assessment")
@Getter
@Setter
public class RiskAssessment extends BaseEntity {

    @Column(name = "custom_reg_particular")
    private String customsRegistrationParticular;

    @Column(name = "contrav_history_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum contraventionHistoryOption;

    @Column(name = "contrav_history")
    private Integer contraventionHistory;

    @Column(name = "origin_country_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum originCountryOption;

    @Column(name = "origin_country")
    private Integer originCountry;

    @Column(name = "duty_exemption_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum dutyExemptionOption;

    @Column(name = "duty_exemption")
    private Integer dutyExemption;

    @Column(name = "duty_suspension_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum dutySuspensionOption;

    @Column(name = "duty_suspension")
    private Integer dutySuspension;

    @Column(name = "licensing_reg_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum licensingAndRegistrationOption;

    @Column(name = "licensing_regist")
    private Integer licensingAndRegistration;

    @Column(name = "other_p_r_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum otherPAndROption;

    @Column(name = "other_p_and_r")
    private Integer otherPAndR;

    @Column(name = "transport_mode_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum modeOfTransportOption;

    @Column(name = "transport_mode")
    private Integer modeOfTransport;

    @Column(name = "revenue_protection_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum revenueProtectionOption;

    @Column(name = "revenue_protection")
    private Integer revenueProtection;

    @Column(name = "rules_of_origin_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum rulesOfOriginOption;

    @Column(name = "rules_of_origin")
    private Integer rulesOfOrigin;

    @Column(name = "security_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum securityOption;

    @Column(name = "security")
    private Integer security;

    @Column(name = "tarrif_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum tarrifOption;

    @Column(name = "tarrif")
    private Integer tarrif;

    @Column(name = "trade_restrictions_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum tradeRestrictionsOption;

    @Column(name = "trade_restrictions")
    private Integer tradeRestrictions;

    @Column(name = "valuation_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum valuationOption;

    @Column(name = "valuation")
    private Integer valuation;

    @Column(name = "warehousing_option")
    @Enumerated(EnumType.STRING)
    private YesNoEnum warehousingOption;

    @Column(name = "warehousing")
    private Integer warehousing;

    @Column(name = "other_risk")
    @Enumerated(EnumType.STRING)
    private YesNoEnum otherRisk;

    @Column(name = "main_industry")
    @Enumerated(EnumType.STRING)
    private MainIndustry mainIndustry;

    @ElementCollection
    @Column(name = "risk_period")
    private Collection<Date> riskPeriod = new ArrayList<>();

    @OneToOne(mappedBy = "riskAssessment", fetch = FetchType.LAZY)
    private SarCase sarCase;

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = RiskAssessmentDetails.class, orphanRemoval = true, mappedBy = "riskAssessment")
    private List<RiskAssessmentDetails> riskAssessmentDetailses = new ArrayList();

    public RiskAssessment() {
        this.contraventionHistory = 0;
        this.originCountry = 0;
        this.dutyExemption = 0;
        this.dutySuspension = 0;
        this.licensingAndRegistration = 0;
        this.otherPAndR = 0;
        this.modeOfTransport = 0;
        this.revenueProtection = 0;
        this.rulesOfOrigin = 0;
        this.security = 0;
        this.tarrif = 0;
        this.tradeRestrictions = 0;
        this.valuation = 0;
        this.warehousing = 0;
        setContraventionHistoryOption(YesNoEnum.NO);
        setDutySuspensionOption(YesNoEnum.NO);
        setDutyExemptionOption(YesNoEnum.NO);
        setLicensingAndRegistrationOption(YesNoEnum.NO);
        setModeOfTransportOption(YesNoEnum.NO);
        setOriginCountryOption(YesNoEnum.NO);
        setOtherPAndROption(YesNoEnum.NO);
        setOtherRisk(YesNoEnum.NO);
        setTradeRestrictionsOption(YesNoEnum.NO);
        setTarrifOption(YesNoEnum.NO);
        setSecurityOption(YesNoEnum.NO);
        setRevenueProtectionOption(YesNoEnum.NO);
        setValuationOption(YesNoEnum.NO);
        setWarehousingOption(YesNoEnum.NO);
        setRulesOfOriginOption(YesNoEnum.NO);
        setDutyExemptionOption(YesNoEnum.NO);
    }

    public void addRiskAssessmentDetails(RiskAssessmentDetails riskAssessmentDetail) {
        if (!riskAssessmentDetailses.contains(riskAssessmentDetail)) {
            riskAssessmentDetail.setRiskAssessment(this);
            riskAssessmentDetailses.add(riskAssessmentDetail);
        }
    }

    public void removeRiskAssessmentDetails(RiskAssessmentDetails riskAssessmentDetail) {
        riskAssessmentDetail.setRiskAssessment(null);
        riskAssessmentDetailses.remove(riskAssessmentDetail);
    }
}
