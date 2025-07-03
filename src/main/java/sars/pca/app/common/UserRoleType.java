package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum UserRoleType {
    ADMIN("Administrator"),
    CLIENT_INTERFACE_DEPARTMENT("Client Interface Department"),
    SARS_INTERNAL_USER("SARS internal User"),
    CRCS_QUALITY_ASSURER("CRCS Quality Assurer"),
    CRCS_OPS_SPECIALIST("CRCS Ops Specialist"),
    CRCS_OPS_MANAGER("CRCS Ops Manager"),
    CI_OPS_MANAGER("CI Ops Manager"),
    CI_AUDITOR("CI Auditor"),
    MANAGEMENT("Management"),
    SENIOR_AUDITOR("Senior Auditor"),
    GROUP_MANAGER("Group Manager"),
    SENIOR_MANAGER("Senior Manager"),
    EXECUTIVE("Executive"),
    CRCS_REVIEWER("CRCS Reviewer");
    private final String name;

    UserRoleType(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
