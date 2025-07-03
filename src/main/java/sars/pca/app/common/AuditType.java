package sars.pca.app.common;

/**
 *
 * @author S2026080
 */
public enum AuditType {
    DESK("Desk"),
    FIELD("On Site");

    private final String name;

    AuditType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
