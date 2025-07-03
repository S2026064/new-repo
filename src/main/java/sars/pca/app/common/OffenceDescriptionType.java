package sars.pca.app.common;

/**
 *
 * @author S2026064
 */
public enum OffenceDescriptionType {
    IGNORE_INSTRUCTION_SECTION_4("Ignoring the instructions or actions of an officer – Sections 4"),
    IGNORE_INSTRUCTION_SECTION_6("Ignoring the instructions of an officer regarding the movement of persons and/or goods in the Customs controlled area – Section 6A"),
    FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8("Failure to comply with arrival and departure reports - Sections 7 and 8"),
    FAILURE_TO_DECLARE_GOODS_SECTION_9("Failure to declare sealable goods – Section 9"),
    LANDING_OF_UNENTERED_GOODS_SECTION_9("Landing of unentered goods – Section 11"),
    REPORTING_OF_VEHICLES_AFTER_ARRIVAL("Reporting of vehicles after arrival in South Africa– Section 12"),
    REPORTING_OF_GOODS_BROUGHT_INTO_THE_COUNTRY_ON_FOOT("Reporting of goods brought into the country overland on foot – Section 12"),
    FAILURE_TO_COMPLY_WITH_THE_CONDITION_OF_GOODS_IMPORTED_BY_POST("Failure to comply with the conditions of goods imported by post – Section 13"),
    GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA("Goods not declared or under-declared by persons entering or leaving South Africa – Section 15"),
    MISUSE_OF_TEMPORARY_IMPORT_PERMIT("Misuse of temporary import permit / carnet – Section 15, 18, 18(A) and 38"),
    ENTERING_OF_GOODS_REMOVED_IN_BOND("Entering of goods removed in bond – Section 18 and 18A"),
    CUSTOMS_AND_EXCISE_WAREHOUSE("Customs and Excise warehouses – Section 19 – 26"),
    FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS("Failure to comply with the conditions for the entry of goods – Sections 12, 18A, 38 – 41, 44, 47, 54, 113 and 119A "),
    REGISTRATION("Registration – Section 59A and Rule 59A.03"),
    LICENCES("Licences – Section 60 – 64D and 64G"),
    VALUATION("Valuation"),
    REBATES_OF_DUTY("Rebates of duty – Section 75"),
    SERIOUS_OFFENCES("Serious offences – Section 80, 84, 86 and 88"),
    REFUNDS_OR_DRAWBACKS("Refunds / drawbacks – Section 75, 76, 76B and 113"),
    KEEPING_OF_BOOKS_ACCOUNTS_AND_DOCUMENTS("Keeping of books, accounts and documents – Section 101"),
    ELECTRONIC_SUBMISSION("Electronic submission – Section Rule 101A.01A(2)(a)"),
    CONDITIONS_FOR_RELEASE_OF_GOODS_UNDER_CUSTOMS_CONTROL("Conditions for the release of goods under Customs control – Section 107"),
    GOODS_SUBJECT_TO_LIEN("Goods subject to a lien – Section 114");

    private final String name;

    OffenceDescriptionType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
