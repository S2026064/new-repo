package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum ClientType {
    TRAVELLER_INDIVIDUAL("Traveller Individual"),
    COMPANY_TRADER_IMPORTER_EXPORTER("Company Trader Import/Export");
    private final String name;

    ClientType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
