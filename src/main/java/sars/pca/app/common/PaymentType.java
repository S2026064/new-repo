package sars.pca.app.common;

/**
 *
 * @author S2026015
 */
public enum PaymentType {
    FULL_PAYMENT("Full Payment"),
    NO_PAYMENT("No Payment"),
    PARTIAL_PAYMENT("Partial Payment");

    private final String name;

    PaymentType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
