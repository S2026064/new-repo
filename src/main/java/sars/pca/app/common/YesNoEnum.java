package sars.pca.app.common;

/**
 *
 * @author S2026987
 */
public enum YesNoEnum {
    YES("Yes"),
    NO("No");

    private final String name;

    YesNoEnum(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
