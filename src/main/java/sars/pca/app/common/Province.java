package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum Province {
    GAUTENG("Gauteng"),
    MPUMALANGA("Mpumalanga"),
    NORTH_WEST("North West"),
    NORTHERN_CAPE("Northern Cape"),
    LIMPOPO("Limpopo"),
    WESTERN_CAPE("Western Cape"),
    EASTERN_CAPE("Eastern Cape"),
    FREE_STATE("Free State"),
    KWAZULU_NATAL("Kwazulu Natal");

    private final String name;

    Province(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
