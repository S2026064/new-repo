package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum TotCustomsDuty {
    THREE_A("Duty Schedule 1 Part 3A"),
    THREE_B("Duty Schedule 1 Part 3B"),
    THREE_C("Duty Schedule 1 Part 3C"),
    THREE_D("Duty Schedule 1 Part 3D"),
    THREE_E("Duty Schedule 1 Part 3D"),
    ORDINARY("Ordinary Customs Duty"),
    ONE("Duty Schedule 2 Part 1"),
    TWO("Duty Schedule 2 Part 2"),
    THREE("Duty Schedule 2 Part 3");

    private final String name;

    TotCustomsDuty(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
