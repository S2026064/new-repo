package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum CaseStatus {
    NEW("New"),
    ACTIVE_SAR("Active: SAR"),
    REVIEWED_PENDING_ALLOCATION("Reviewed: Pending Allocation"),
    ALLOCATED_PENDING_INDEPTH_ANALYSIS("Allocated SAR: Pending In-depth Analysis"),
    ACTIVE_CRCS_ANALYSIS("Active: CRCS Analysis"),
    ACTIVE_CRCS_RA("Active: CRCS RA"),
    ACTIVE_RA_APPROVAL("Active: RA Approval"),
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
    //SAR Case Review Alternatives
    //PENDING_QA_REVIEW_SAR_DISCARDED("Pending QA Review: SAR Case Discarded"),
    PENDING_QA_REVIEW_SAR_DISCARDED_INDEPTH_ANALYSIS("Indepth Analysis Discarded"),
    REVIEWED_CASE_CLOSED("Reviewed: SAR Case Closed"),
    //Indepth Analysis alternatives
    DISCARDED_PENDING_QA_REVIEW("CRCS Review Discarded"),
    //INDEPTH_ANALYSIS_PEND("Allocated SAR: Pending In-depth Analysis"),
    //Risk Assessment Review Alternatives
    ACTIVE_RA_SEND_BACK_FOR_REWORK("Active: RA Send Back for Rework"),
    ACTIVE_RA_TECHNICAL_REVIEW_REJECTED("Active: RA Technical Review Rejected"),
    ACTIVE_RA_REJECTED("Active: RA Rejected"),
    ACTIVE_RA_QA("Active: RA QA"),
    //RISK_ASSESSMENT_PEND("Active: CRCS Analysis"),
    //Audit Plan Review Alternatives
    CI_PLAN_REWORK("CI: Plan Re-Work"),
    CI_OPS_MANAGER_AUDIT_PLAN_SEND_FOR_APPROVAL("CI: Ops Manager Audit Plan Send for Approval"),
    CI_MANAGER_REJECTED_AUDIT_PLAN("CRCS: Rejected Audit Plan Pending Review"),
    SENIOR_AUDITOR_REJECTED_AUDIT_PLAN("CRCS: Rejected Audit Plan Pending Review"),
    CRCS_APPROVED_REJECTED_AUDIT_PLAN("CRCS: Approved Rejected Audit Plan"),
    CI_SENIOR_AUDITOR_AUDIT_PLAN_SEND_FOR_APPROVAL("CI: Senior Auditor Audit Plan Send for Approval"),
    //Audit reporting alternatives
    CI_REJECTED_AUDIT_CASE("CI: Rejected Audit Case"),
    AUDIT_REPORTING_RE_WORK("CI: Audit Reporting Sent for Re-work"),
    AUDIT_REPORTING_SEND_FOR_APPROVAL("CI: Audit Reporting Sent for approval"),
    AUDIT_REPORTING_ACCEPTED_SEND_FOR_APPROVAL("CI: Audit Reporting Accepted and Sent for approval"),
    DELETED("Deleted"),
    //Send to senior Manager Alternatives
    THIRD_TIER_APPROVAL("3rd Tier Approval"),
    //Send Executive Alternatives
    FOURTH_TIER_APPROVAL("4rd Tier Approval"),
    REJECTED_DISCARDED_SAR("Rejected Discarded SarCase"),
    REJECTED_DISCARDED_INDEPTH_ANALYSIS("Rejected Discarded Indepth Analysis"),
    CRCS_REVIEW_POOL("CRCS Review Pool"),
    QA_REVIEW_POOL("QA Reivew Pool"),
    TECHNICAL_REVIEW_POOL("Technical Review Pool"),
    //CI_ALLOCATION_POOL("CI Allocation Pool"),
    DEBT_MANAGEMENT_POOL("Debt Management Pool"),
    FULL_SCOPE_POOL("Full Scope Pool"),
    LIMITED_SCOPE_POOL("Limited Scope Pool"),
    INTEGRATED_SCOPE_POOL("Integrated Scope Pool"),
    VDDL_POOL("VDDL Pool"),
    ACTIVE_VDDL("Active VDDL"),
    TECHNICAL_REVIEW_APPROVED("Technical review approved");

    private final String name;

    CaseStatus(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
