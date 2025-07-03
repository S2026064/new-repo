package sars.pca.app.common;

/**
 *
 * @author S2026987
 */
public enum DutyType {
    CUSTOM("Custom"),
    EXCISE("Excise"),
    OTHER("Other");

    private final String name;

    DutyType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
