package sars.pca.app.common;

public enum CaseType {
    SAR("Suspicious Activity Report"),
    VDDL("Voluntary Disclosure or Demand Led");

    private final String name;

    CaseType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
