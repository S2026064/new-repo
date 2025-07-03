package sars.pca.app.common;

/**
 *
 * @author S2026080
 */
public enum RiskArea {
    CONTRAVENTION_HISTORY("Contravention History"),
    COUNTRY_OF_ORIGIN("Country of Origin"),
    DUTY_EXEMPTION_REGIMES("Duty Exemption Regimes"),
    DUTY_SUSPENSSION("Duty Suspension"),
    LICENSING_AND_REGISTRATION("Licensing and Registration"),
    MODE_OF_TRANSPORT("Mode of Transport"),
    OTHER_P_AND_R("Othet P and R"),
    REVENUE_PROTECTION("Revenue Protection"),
    RULES_OF_ORIGIN("Rules of Origin"),
    SECURITY("Security"),
    TARRIF("Tarrif"),
    TRADE_RESTRICTIONS("Trade Restriction"),
    VALUATION("Valuation"),
    WAREHOUSING("Warehousing");

    private final String name;

    RiskArea(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
