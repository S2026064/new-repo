package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum TotExciseDuty {
    TWO_A("Duty Schedule 1 Part 2A"),
    TWO_B("Duty Schedule 1 Part 2B");

    private final String name;

    TotExciseDuty(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
