package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum VddlStatus {
    NEW("New"),
    ACTIVE_VDDL("Active: VD/DL"),
    CI_ALLOCATION("CI: Allocation"),
    CI_SENIOR_AUDITOR_AUDIT_PLAN_REVIEW("CI: Senior Auditor Audit Plan Review"),
    ACTIVE_CI_PLAN("Active: CI Plan"),
    CI_PLAN_REVIEW("CI: Plan Review"),
    CI_SENIOR_AUDITOR_AUDIT_REPORTING_REVIEW("CI: Senior Auditor Audit Reporting Review"),
    CI_AUDIT_REPORTING_EXECUTE("CI: Audit Reporting Execute"),
    CI_AUDIT_REPORTING_REVIEW("CI: Audit Reporting Review"),
    DM_SENT_FOR_DEBT_MANAGEMENT("DM: Case Reffered to Debt Management for Collection"),
    DM_TEAM_MEMBER_ALLOCATION("DM: Case Allocated to DM Team Member"),
    DM_CASE_REFERED_TO_CI_FOR_CLOSURE("DM: Case Reffered to CI for Closure"),
    CLOSURE("Closure"),
    //Audit Plan Review Alternatives
    CI_PLAN_REWORK("CI: Plan Re-Work"),
    CI_OPS_MANAGER_AUDIT_PLAN_SEND_FOR_APPROVAL("CI: Ops Manager Audit Plan Send for Approval"),
    CRCS_REJECTED_AUDIT_PLAN_PENDING_REVIEW("CRCS: Rejected Audit Plan Pending Review"),
    AUDIT_PLAN_PEND("CI: Allocation"),
    CI_REJECTED_AUDIT_CASE("CI: Rejected Audit Case"),
    CRCS_APPROVED_REJECTED_AUDIT_PLAN("CRCS: Approved Rejected Audit Plan"),
    //Audit reporting alternatives
    AUDIT_REPORTING_PEND("CI: Plan Review"),
    AUDIT_REPORTING_RE_WORK("CI: Audit Reporting Sent for Re-work"),
    AUDIT_REPORTING_SEND_FOR_APPROVAL("CI: Audit Reporting Sent for approval"),
    AUDIT_REPORTING_ACCEPTED_SEND_FOR_APPROVAL("CI: Audit Reporting Accepted and Sent for approval"),
    //Send to senior manager
    THIRD_TIER_APPROVAL("3rd Tier Approval"),
    //Send Executive Alternatives
    FOURTH_TIER_APPROVAL("4rd Tier Approval"),
    DELETED("Deleted");

    private final String name;

    VddlStatus(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
