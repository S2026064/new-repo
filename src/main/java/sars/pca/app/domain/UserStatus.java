package sars.pca.app.domain;

/**
 *
 * @author S2024726
 */
public enum UserStatus {
    ACTIVE("Active"),
    IN_ACTIVE("In-Active");

    private final String name;

    UserStatus(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
