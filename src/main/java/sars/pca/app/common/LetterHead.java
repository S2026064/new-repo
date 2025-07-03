package sars.pca.app.common;

/**
 *
 * @author S2026015
 */
public enum LetterHead {

    REQUEST_FOR_DOCUMENTATION("Request For Documentation"),
    NOTIFICATION_OF_AUDIT_DESK("Notification Of Audit - DESK"),
    NOTIFICATION_OF_AUDIT_FIELD("Notification Of Audit - ON SITE"),
    LETTER_OF_FINDINGS("Letter of Findings"),
    LETTER_OF_DEMAND("Letter Of Demand"),
    FINALIZATION_OF_AUDIT( "finalization of Audit "),
    IMPOSITION_OF_PENALTY("Imposition Of Penalty"),
    NOTICE_OF_INTENT("Notice Of Intent");


    private final String name;

    LetterHead(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
