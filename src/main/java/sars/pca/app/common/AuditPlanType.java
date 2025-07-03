package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum AuditPlanType {
    VDDL("Voluntary Disclosure/Demand Led"),
    RISK("Risk Assessment Report");

    private final String name;

    AuditPlanType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
