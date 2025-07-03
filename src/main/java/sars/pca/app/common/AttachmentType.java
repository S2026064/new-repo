package sars.pca.app.common;

/**
 *
 * @author S2026015
 */
public enum AttachmentType {
    ID_COPY("ID Copy"),
    POP("Proof of Payment"),
    PASSPORT_COPY("Passport Copy"),
    AFFIDAVIT("Affidavit");

    private final String name;

    AttachmentType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
