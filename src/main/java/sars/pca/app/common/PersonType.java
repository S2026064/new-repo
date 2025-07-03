package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum PersonType {
    SYSTEM_USER("System User"),
    REPORTED_PERSON("Reported Person"),
    REPORTING_PERSON("Reporting Person"),
    PUBLIC_OFFICER("Public Officer");
    private final String name;

    PersonType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
