package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum RegistrationType {
    EXPORTER("Exporter"),
    IMPORTER("Importer"),
    AGOA("AGOA"),
    EU_TDCA("EU TDCA"),
    SADC("SADC"),
    GSP("GSP"),
    EFTA("EFTA"),
    GENERAL("General");

    private final String name;

    RegistrationType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
