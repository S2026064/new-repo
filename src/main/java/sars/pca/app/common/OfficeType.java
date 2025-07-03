package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum OfficeType {
    CI_OFFICE("CI Office"),
    CRSC_OFFICE("CRCS Office");

    private final String name;

    OfficeType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
