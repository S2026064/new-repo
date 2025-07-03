package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum LOIExtentionReason {
    TIME_EXTENSION("Time extension requested by client"),
    REVISED_LOI("Revised LOI"),
    MITIGATION_REVIEW("Mitigation Review"),
    OTHER("Other");

    private final String name;

    LOIExtentionReason(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
