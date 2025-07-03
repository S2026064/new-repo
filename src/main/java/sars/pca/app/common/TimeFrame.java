package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum TimeFrame {
    THE_INCIDENT_WILL_BE_HAPPENING_IN_THE_NEXT_1_TO_7_DAYS("The incident will be happening in the next 1-7 days"),
    THE_INCIDENT_IS_GOING_TO_HAPPEN_SOMETIME_IN_THE_FUTURE("The incident is going to happen sometime in the future"),
    THE_INCIDENT_WILL_HAPPEN_TODAY("The incident will happen today"),
    THE_INCIDENT_HAPPENED_IN_THE_PAST("The incident happened in the past");

    private final String name;

    TimeFrame(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
