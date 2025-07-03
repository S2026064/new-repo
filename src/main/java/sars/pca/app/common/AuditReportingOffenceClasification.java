package sars.pca.app.common;

/**
 *
 * @author S2026064
 */
public enum AuditReportingOffenceClasification {
    GOODS_SUBJECT_TO_A_LIEN("Goods subject to a alien - Section 114"),
    CONDITION_FOR_RELEASE_OF_GOODS("Conditions for the release of goods under customs control - Section 107"),
    ELECTRONIC_SUBMISSON("Electronic submission - Section Rule 101A.01A(2)(a) "),
    KEEPING_OF_BOOKS("Keeping of books, accounts and documents - Section 101"),
    REFUNDS_DRAWBACKS("Refuns/drawbacks - Section 75, 76, 76Band 113");

    private final String name;

    AuditReportingOffenceClasification(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
