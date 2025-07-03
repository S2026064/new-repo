package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum ScoreType {
    CUSTOMS_VALUE("Customs Value"),
    RISK_IDENTIFIED("Risk Identified"),
    REVENUE_AT_RISK("Revenue at Risk"),
    COMMODITY_ALIGNMENT("Commodity Alignment"),
    RISK_RATING("Risk Rating"),
    NUMBER_OF_LINES("Number of Lines");
    private final String name;

    ScoreType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
