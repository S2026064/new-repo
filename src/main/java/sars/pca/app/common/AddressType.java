package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum AddressType {
    PHYSICAL("Physical Address"),
    BUSINESS("Business Address"),
    POSTAL("Postal Address");

    private final String name;
    AddressType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
