package sars.pca.app.common;

/**
 *
 * @author S2026015
 */
public enum NotificationType {
    //crsc office(REVIWER AND QA)-PAGE 58
    DISCARDED_SAR_TO_QA("Discarded SAR case to QA"),
    DISAPROVED_DISCARDED_CASE("Disapprove discarded case"),
    //CRC duplicate and merge(Exists Duplicate Case)-PAGE 67
    DISCARDED_DUPLICATE_CASE("Discard Duplicate Case"),
    COMPLETE_RA_STAGE("Complete RA Stage"),
    IN_DEPTH_ANALYSIS_STAGE("In-Depth Analysis Stage"),
    RA_APPROVA_STAGE("RA Approval Stage"),
    ALLOCATED_INVENTORY("Allocated Inventory"),
    DISCARD_SAR_IN_DEPTH_ANALYSIS_STAGE("Discarded SAR Case In-depth Analysis"),
    TECHNICAL_REVIEW("Technical Review Cases"),
    //SAR allocation-PAGE 74 and PAGE 78
    SAR_ALLOCATION("SAR Allocation"),
    SAR_REALLOCATION("SAR Re-allocation"),
    //SAR indepth analysis-PAGE 85
    ALLOCATE_INDEPTH_ANALYSIS_QA("In-depth Analysis allocate to QA"),
    REWORK_INDEPTH_ANALYSIS("Rework In-depth Analysis"),
    SENT_BACK_INDEPTH_ANALYSIS("Sent back indepth Analysis"),
    //RA CASE -PAGE 86
    APPROVE_DISCAREDED_RA("Approved discarded RA/case"),
    REJECTED_RA("Rejected RA CASE"),
    //PAGE 107 RA Approval
    APPROVE_RA("Approved RA/case"),
    REWORK_RA("Rework RA/case"),
    //Technical Review-PAGE113
    QA_TECHNICAL_REVIEW("QA Technical Review"),
    APPROVED_REJECT_TECHNICAL_REVIEW("Approved rejected Technical Review"),
    REJECTED_REJECT_TECHNICAL_REVIEW("Reject rejected Technical Review"),
    //rework RA PAGE 116
    RA_REWORK("Rework RA case"),
    REJECTED_RA_REWORK("Rejected RA rework"),
    //CL allocation -PAGE 122
    CI_ALLOCATION("CI Allocation"),
    CI_REALLOCATION("CI Re-llocation"),
    ALLOCATE_CRCS_OPS_SPECIALIST("Allocate case to CRCS OPS specialist"),
    //audit plan-PAGE 133
    AUDIT_PLAN_REVIEW("Audi plan"),
    AUDIT_PLAN_SENT_FOR_APPROVAL("Audit plan sent for approval"),
    REWORK_AUDIT_PLAN("Audit plan sent back for rework"),
    //review audit plan-140
    REJECT_AUDIT_PLAN("Reject Audit plan"),
    DISAPPROVE_REJECT_AUDIT_PLAN("Dissapprove reject Audit plan"),
    APPROVE_REJECT_AUDIT_PLAN("Dissapprove reject Audit plan"),
    AUDIT_PLAN_ACCEPTED("Audit plan accepted"),
    AUDIT_PLAN_APPROVED("Audit plan approved"),
    AUDIT_PLAN_REJECTED("Audit plan rejected"),
    AUDIT_PLAN_SUBMITTED("Audit plan submitted"),
    //Audit Case Report-PAGE 178
    AUDIT_REPORT_APPROVAL("Audit Report sent for approval"),
    AUDIT_REPORT_REWORK("Audit Report sent for rework"),
    AUDIT_REPORT_REVIEW("Audit Report review"),
    //rejected Audit Report-189
    REJECT_AUDIT_REPORT_APPROVAL("Reject audit report approval"),
    REJECT_AUDIT_REPORT_DISAPPROVAL("Reject audit report disapproval"),
    REJECT_AUDIT_REPORT("Reject audit report"),
    //page 194 GROUP MANAGER Approve
    AUDIT_REPORT_APPROVE("Reject audit report approved"),
    AUDIT_REPORT_APPROVED("Audit report approved"),
    ACCEPT_AUDIT_REPORT("Accept Audit report"),
    ACCEPT_AUDIT_FINALISED("Finalised Audit report"),
    //PAGE - 220
    DEBT_MANAGEMENT("Debt management"),
    DEBT_MANAGEMENT_COLLECTION("Debt management collection"),
    EXTEND_LOI("LOI due date extended"),
    TRANSFER("Transfer Case"),
    DISCARDED_POOL("Discarded pool"),
    INDEPTH_DISCARDED_POOL("Indepth Discarded pool"),
    TECHNICAL_POOL("Technical Review pool"),
    TECHNICAL_APPROVED("Technical Review approved");
    /*
    DISCARDED_SAR_TO_QA("Discarded SAR case to QA"),
    DISCARDED_SAR_DISAPPROVED_BY_QA_TO_REVIEWER("Discarded SAR case dissapproved by QA to Reviewer"),
    DISCARDED_DUPLICATE_CASE_TO_QA("Discarded duplicate case to QA"),
    COMPLETE_RA_STAGE_IN_CASE_OF_DUPLICATE_CASE("Complete RA Stage in case of Duplicate Case"),
    IN_DEPTH_ANALYSIS_STAGE_IN_CASE_OF_DUPLICATE_CASE("In-Depth Analysis Stage in case of Duplicate case"),
    IN_DEPTH_ANALYSIS_STAGE("In-Depth Analysis Stage"),
    RA_APPROVAL_STAGE_IN_CASE_DUPLICATE_CASE("RA Approval Stage in Case Duplicate Case"),
    RA_APPROVAL_STAGE("RA Approval Stage"),
    QA_TECHNICAL_REVIEW("Technical review cases"),
    ALLOCATED_INVENTORY_IN_CASE_OF_DUPLICATE_CASE("Allocated Inventory in case of Duplicate case"),
    DISCARDED_SAR_CASE_IN_DEPTH_ANALYSIS_IN_CASE_OF_DUPLICATE_CASE("Discarded SAR Case In-depth Analysis in case of Duplicate case"),
    CASE_ALLOCATION("Case allocation"),
    REVIEW_AUDIT_REPORTING_LETTER("Review Audit Reporting letters"),
    DISCARDED_CASE_IN_DEPTH_ANALYSIS("Discarded case in-indepth analysis"),
    CASE_SENT_FOR_REWORK("Case sent for rework"),
    CASE_BEEN_PENDED_BY_CRCS_OPS_SPECIALIST("Case been pended by CRCS Ops Specialist"),
    REJECTED_RA("Rejected Risk Assessment"),
    UNPEND_CASE_BY_CRCS_OPS_SPECIALIST("Unpend Case by CRCS Ops Specialist"),
    SUBMITTED_RA("Submitted RA"),
    REJECTED_QA_APPROVED_BY_OPS_MANAGER("Rejected case by QA approved by Ops Manager"),
    REJECTED_QA_DISAPPROVED_BY_OPS_MANAGER("Rejected case by Qa Disapproved by Ops Manager"),
    EXTEND_PEND_DATE_BY_CRCS_OPS_SPECIALIST("Extend pend date by CRCS Ops Specialist"),
    SENT_RA_FOR_APPROVAL("Sent Risk Assesment for approval"),
    RA_SENT_FOR_TECHNICAL_REVIEW("Sent Risk Assessment for technical review"),
    APPROVED_REJECTED_TECHNICAL_REVIEW("Approved rejected technical review"),
    DISAPPROVED_REJECTED_TECHNICAL_REVIEW("Disapproved rejected technical review"),
    DISAPPROVED_REJECTED_AUDIT_PLAN_TO_SENIOR_AUDIT("Disapprove Rejected Audit Plan to Senior Auditor"),
    DISAPPROVED_REJECTED_AUDIT_PLAN_CI_OPS_MANAGER("isapprove Rejected Audit Plan to CI Ops Manager"),
    DISAPPROVED_REJECTED_AUDIT_REPORTING("isapprove Rejected Audit Plan to CI Ops Manager"),
    REJECTED_AUDIT_REPORTING_SENT_TO_CRCS_OPS_MANAGER("Rejected Audit Reporting sent to CRCS Ops Manager"),
    ACCEPTED_AUDIT_REPORTING_SENT_FOR_APPROVAL("Accepted Audit Reporting sent for approval"),
    CI_ALLOCATION("CI Allocation"),
    CRCS_OPS_MANAGER_ALLOCATION("CRCS Ops Manager Allocation"),
    QA_ALLOCATION_SAR_CASE("QA Allocation SAR case"),
    REJECTED_CASE_FOR_CRCS_OPS_SPECIALIST("Rejected case for CRCS Ops Specialist"),
    CRCS_OPS_SPECAILIST_REWORK("CRCS Ops Specialist rework"),
    DISCARDED_RA_TO_CRCS_OPS_SPECIALIST("Discarded RA/Case to CRCS Ops Specialist"),
    DISCARDED_SAR_TO_CRCS_OPS_SPECIALIST_FOR_REWORK("Discarded SAR send to CRCS Ops Special for rework"),
    PENDING_RA("Pending risk assessment"),
    QA_REVIEW("QA to review"),
    AUDIT_PLAN_ALLOCATED("Audit Plan Allocated"),
    AUDIT_PLAN_RE_ALLOCATION("Audit Plan re-allocation"),
    AUDIT_PLAN_REVIEW("Audit plan review"),
    AUDIT_PLAN_SENT_FOR_APPROVAL("Audit Plan sent for approval"),
    AUDIT_PLAN_SENT_FOR_RE_WORK("Audit Plan sent for rework"),
    AUDIT_PLAN_SENT_FOR_1ST_TIER_APPROVAL("Audit Plan sent for 1st tier approval"),
    REJECTED_AUDIT_PLAN_TO_CRCS_OPS_MANAGER("Rejected Audit Plan to CRSC Ops Manager"),
    REJECTED_AUDIT_PLAN_DISAPPROVED_BY_CRCS_OPS_MANAGER("Rejected AuditPlan disapproved by CRCS Ops Manager"),
    REJECTED_AUDIT_PLAN_APPROVED_BY_CI_OPS_MANAGER("Rejected AuditPlan approved by CI Ops Manager"),
    REVIEWED_AUDIT_PLAN_BY_SENIOR_AUDITOR("Reviewed Audit Plan by Senior Auditor"),
    AUDIT_PLAN_APPROVAL("Audit Plan approval"),
    CRCS_OPS_SPECIALIST_REJECTED_CASE_REWORK("Rejected case send back to CRCS Ops Specialist for rework"),
    REJECTED_AUDIT_PLAN_TO_CI_AUDITOR("Rejected Audit Plan to CI Auditor"),
    AUDIT_REPORTING_REVIEW("Audit reporting review"),
    AUDIT_REPORTING_APPROVAL("Audit reporting approval"),
    AUDIT_REPORTING_SENT_FOR_REWORK("Audit reporting sent for rework"),
    AUDIT_REPORTING_SENT_TO_GROUP_MANAGER_FOR_REVIEW("Audit Reporting sent to group manager for review"),
    AUDIT_REPORTING_SENT_TO_CI_OPS_MANAGER_FOR_APPROVAL("Audit Report send to CI Ops Manager for approval"),
    AUDIT_REPORTING_REVIEWED("Audit reporting reviewed"),
    APPROVED_AUDIT_REPORT("Approved Audit report"),
    ASCALATED_AUDIT_REPORT("Ascalated Audit report"),
    AUDIT_REPORTING_SENT_FOR_DEBT_MANAGEMENT("Audit Reporting seny for Debt Management"),
    FULL_COLLECTION_OF_DEBT("Full collection of Debt"),
    DEBT_MANAGEMENT_SENT_FOR_CLOSURE("Debt Management sent for Closure"),
    EXTENDED_LOI_DATE("Extended LOI date"),
    PENDING_ALLOCATION("Pending Allocation"),
    DISAPROVED_DISCARDED_CASE("Disapproved discarded case");*/

    public final String name;

    NotificationType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
