package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum TotDutyOther {
    THREE_A("Duty Schedule 1 Part 3A"),
    THREE_B("Duty Schedule 1 Part 3B"),
    THREE_C("Duty Schedule 1 Part 3C"),
    THREE_D("Duty Schedule 1 Part 3D"),
    THREE_E("Duty Schedule 1 Part 3E"),
    DIAMOND_EXPORT_LEVY("Diamond Export Levy Amount"),
    SEVEN_A("Duty Schedule 1 Part 7A");

    private final String name;

    TotDutyOther(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
