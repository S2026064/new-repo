package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum OffenceClassificationType {
    RISK_ASSESSMENT("Risk Assessment"),
    VDDL("VD/DL");

    private final String name;

    OffenceClassificationType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
