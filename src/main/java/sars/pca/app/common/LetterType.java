package sars.pca.app.common;

/**
 *
 * @author S2026015
 */
public enum LetterType {

    RISK_ASSESSMENT("Risk Assessment"),
    AUDIT_PLAN("Audit Plan"),
    AUDIT_REPORTING("Audit Reporting");

    private final String name;

    LetterType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
