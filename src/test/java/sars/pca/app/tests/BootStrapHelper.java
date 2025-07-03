package sars.pca.app.tests;

import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import sars.pca.app.common.AddressType;
import sars.pca.app.common.AuditPlanType;
import sars.pca.app.common.AuditType;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.ClientType;
import sars.pca.app.common.CustomExcise;
import sars.pca.app.common.DutyType;
import sars.pca.app.common.LOIExtentionReason;
import sars.pca.app.common.MainIndustry;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.OffenceDescriptionType;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.PaymentType;
import sars.pca.app.common.PersonType;
import sars.pca.app.common.Province;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.TimeFrame;
import sars.pca.app.common.YearlyLoss;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.AdditionalRisk;
import sars.pca.app.domain.Address;
import sars.pca.app.domain.AdministrationSetting;
import sars.pca.app.domain.Attachment;
import sars.pca.app.domain.AuditDetails;
import sars.pca.app.domain.AuditPlan;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.AuditingSettting;
import sars.pca.app.domain.Comment;
import sars.pca.app.domain.CustomsExciseClientType;
import sars.pca.app.domain.DebtManagementSetting;
import sars.pca.app.domain.Duty;
import sars.pca.app.domain.EmailNotification;
import sars.pca.app.domain.EntityTypeDetails;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.NonComplianceDetails;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.Permission;
import sars.pca.app.domain.Program;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.domain.ReportSetting;
import sars.pca.app.domain.ReportedPerson;
import sars.pca.app.domain.ReportingPerson;
import sars.pca.app.domain.RevenueLiability;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.domain.RiskAssessmentDetails;
import sars.pca.app.domain.RiskManagementSettting;
import sars.pca.app.domain.SarCase;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.TotalLiability;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;
import sars.pca.app.domain.UserStatus;
import sars.pca.app.domain.VddLedCase;
import sars.pca.app.domain.VddLedType;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.RiskRatingConsequence;
import sars.pca.app.domain.RiskRatingLikelihood;
import sars.pca.app.domain.VddlOrSarCaseSetting;

/**
 *
 * @author S2026987
 */
public class BootStrapHelper {

    public static UserRole getAdministratorUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.TRUE);
        administrationSetting.setUsers(Boolean.TRUE);
        administrationSetting.setSlaConfigs(Boolean.TRUE);
        administrationSetting.setLocationOffice(Boolean.TRUE);
        administrationSetting.setHsChapterRisk(Boolean.TRUE);
        administrationSetting.setRelevantMaterial(Boolean.TRUE);
        administrationSetting.setOffenceClassification(Boolean.TRUE);
        administrationSetting.setProgram(Boolean.TRUE);
        administrationSetting.setNotification(Boolean.TRUE);
        administrationSetting.setCountry(Boolean.TRUE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(true);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());

        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);

        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getClientInterfaceUserUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.TRUE);
        administrationSetting.setUsers(Boolean.TRUE);
        administrationSetting.setSlaConfigs(Boolean.TRUE);
        administrationSetting.setLocationOffice(Boolean.TRUE);
        administrationSetting.setHsChapterRisk(Boolean.TRUE);
        administrationSetting.setRelevantMaterial(Boolean.TRUE);
        administrationSetting.setOffenceClassification(Boolean.TRUE);
        administrationSetting.setProgram(Boolean.TRUE);
        administrationSetting.setNotification(Boolean.TRUE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(true);
        vddlOrSarCaseSetting.setCreateSarCase(true);
        vddlOrSarCaseSetting.setReverseOrReassign(true);
        vddlOrSarCaseSetting.setCreateVddl(true);
        vddlOrSarCaseSetting.setReviewSarCase(true);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());

        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);

        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(true);
        auditingSettting.setCreateAuditPlan(true);
        auditingSettting.setCreateAuditReport(true);
        auditingSettting.setReviewAuditPlan(true);
        auditingSettting.setReviewAuditReport(true);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(true);
        debtManagementSetting.setReview(true);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getSARSInternalUserUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(true);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(true);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getCRCSReviewerUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(true);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(true);
        vddlOrSarCaseSetting.setReviewSarCase(true);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getCRCSQualityAssureUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(true);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(true);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(true);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getCRCSOpsSpecialistUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(true);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(true);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(true);
        riskManagementSettting.setReworkRisk(true);
        riskManagementSettting.setCompletedRisk(true);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(true);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getCRCSOpsManagerUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(true);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(true);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(true);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getCIOpsManagerUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(true);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(true);
        auditingSettting.setReviewAuditReport(true);
        auditingSettting.setReAssignAuditPlan(true);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getCIAuditorUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(true);
        auditingSettting.setCreateAuditReport(true);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(true);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getManagementUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(false);
        auditingSettting.setReviewAuditReport(false);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(true);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getSeniorAuditorUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(true);
        auditingSettting.setReviewAuditReport(true);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getGroupManagerUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(true);
        auditingSettting.setReviewAuditReport(true);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getSeniorManagerUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(true);
        auditingSettting.setReviewAuditReport(true);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static UserRole getExecutiveUserRole(String description) {
        UserRole userRole = new UserRole();
        userRole.setCreatedBy("s2024726");
        userRole.setCreatedDate(new Date());
        userRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2024726");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setUserRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setSlaConfigs(Boolean.FALSE);
        administrationSetting.setLocationOffice(Boolean.FALSE);
        administrationSetting.setHsChapterRisk(Boolean.FALSE);
        administrationSetting.setRelevantMaterial(Boolean.FALSE);
        administrationSetting.setOffenceClassification(Boolean.FALSE);
        administrationSetting.setProgram(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        userRole.setAdminSetting(administrationSetting);

        VddlOrSarCaseSetting vddlOrSarCaseSetting = new VddlOrSarCaseSetting();
        vddlOrSarCaseSetting.setCreatedBy("s2024726");
        vddlOrSarCaseSetting.setCreatedDate(new Date());
        vddlOrSarCaseSetting.setAllocateSarCase(false);
        vddlOrSarCaseSetting.setCreateSarCase(false);
        vddlOrSarCaseSetting.setReverseOrReassign(false);
        vddlOrSarCaseSetting.setCreateVddl(false);
        vddlOrSarCaseSetting.setReviewSarCase(false);
        userRole.setVddlOrSarCaseSetting(vddlOrSarCaseSetting);

        RiskManagementSettting riskManagementSettting = new RiskManagementSettting();
        riskManagementSettting.setCreatedBy("s2024726");
        riskManagementSettting.setCreatedDate(new Date());
        riskManagementSettting.setNewRisk(false);
        riskManagementSettting.setReworkRisk(false);
        riskManagementSettting.setCompletedRisk(false);
        riskManagementSettting.setReviewRisk(false);
        riskManagementSettting.setIndepthAnalysis(false);
        userRole.setRiskManagementSettting(riskManagementSettting);

        AuditingSettting auditingSettting = new AuditingSettting();
        auditingSettting.setCreatedBy("s2024726");
        auditingSettting.setCreatedDate(new Date());
        auditingSettting.setAllocateAuditPlan(false);
        auditingSettting.setCreateAuditPlan(false);
        auditingSettting.setCreateAuditReport(false);
        auditingSettting.setReviewAuditPlan(true);
        auditingSettting.setReviewAuditReport(true);
        auditingSettting.setReAssignAuditPlan(false);
        userRole.setAuditingSetting(auditingSettting);

        DebtManagementSetting debtManagementSetting = new DebtManagementSetting();
        debtManagementSetting.setCreatedBy("s2024726");
        debtManagementSetting.setCreatedDate(new Date());
        debtManagementSetting.setCreate(false);
        debtManagementSetting.setReview(false);
        userRole.setDebtManagementSetting(debtManagementSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2024726");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.FALSE);
        userRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2024726");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        userRole.setPermission(permission);
        return userRole;
    }

    public static User getUser(UserRole userRole, String sid, String countryName, String firstname, String lastName, String fullNames, String initials, String identityNumber, String cellphone, String email, String websiteUrl, String fax, String telephone) {
        User user = new User();
        user.setCreatedBy("s2024726");
        user.setCreatedDate(new Date());
        user.setSid(sid);
        user.setUserRole(userRole);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setCountryName(countryName);
        user.setDivisionCode("XYZ");
        user.setDivisionName("ABC");
        user.setRegionCode("XYZ");
        user.setRegionName("ABC");

        //user.setManager(user);
        user.setFirstName(firstname);
        user.setLastName(lastName);
        user.setFullNames(fullNames);
        user.setInitials(initials);
        user.setIdentityNumber(RandomStringUtils.randomNumeric(13));
        user.setPassportNumber(RandomStringUtils.randomNumeric(13));
        user.setPersonType(PersonType.SYSTEM_USER);
        user.setBirthDate(new Date());
        user.setProvince(Province.GAUTENG);

        user.setEmailAddress(email);
        user.setFaxNumber(fax);
        user.setMobileNumber(cellphone);
        user.setHomeTelephoneNumber(telephone);
        user.setWebAddress(websiteUrl);

        Address physicalAddress = new Address();
        physicalAddress.setCreatedBy("s2024726");
        physicalAddress.setCreatedDate(new Date());
        physicalAddress.setAddressType(AddressType.PHYSICAL);
        physicalAddress.setLine1("Unit 1 Estate View");
        physicalAddress.setLine2("123 Street Road");
        physicalAddress.setLine3("Zambezi Juntion");
        physicalAddress.setArea("Pretoria");
        physicalAddress.setCode("0002");
        user.addAddress(physicalAddress);

        Address postalAddress = new Address();
        postalAddress.setCreatedBy("s2024726");
        postalAddress.setCreatedDate(new Date());
        postalAddress.setAddressType(AddressType.POSTAL);
        postalAddress.setLine1("P.O Box 123");
        postalAddress.setArea("Zambezi");
        postalAddress.setCode("0002");
        user.addAddress(postalAddress);
        return user;
    }

    public static SlaConfiguration getSlaConfiguration(Integer auditPlanAutoApprove, Integer auditReportingAutoApprove, Integer crcsPrioritisationScoringWeight, Integer raTechnicalReviewMinimum, Integer riskAssessmentAutoApprove, Integer autoDiscardSavedCasesSettings) {
        SlaConfiguration slaConfiguration = new SlaConfiguration();
        slaConfiguration.setCreatedBy("s2024726");
        slaConfiguration.setCreatedDate(new Date());
        slaConfiguration.setAuditPlanAutoApprove(auditPlanAutoApprove);
        slaConfiguration.setAuditReportingAutoApprove(auditReportingAutoApprove);
        slaConfiguration.setCrcsPrioritisationScoringWeight(crcsPrioritisationScoringWeight);
        slaConfiguration.setRaTechnicalReviewMinimum(raTechnicalReviewMinimum);
        slaConfiguration.setRiskAssessmentAutoApprove(riskAssessmentAutoApprove);
        slaConfiguration.setAutoDiscardSavedCasesSettings(autoDiscardSavedCasesSettings);

        slaConfiguration.setAlignmentOfComidity(auditPlanAutoApprove);

        return slaConfiguration;
    }

    public static LocationOffice getLocationOffice(Province province, String area, OfficeType officeType) {
        LocationOffice locationOffice = new LocationOffice();
        locationOffice.setCreatedBy("s2024726");
        locationOffice.setCreatedDate(new Date());
        locationOffice.setProvince(province);
        locationOffice.setOfficeType(officeType);
        locationOffice.setArea(area);
        return locationOffice;
    }

    public static HsChapterRisk getHsChapterRisk(User user, String description) {
        HsChapterRisk hsChapterRisk = new HsChapterRisk();
        hsChapterRisk.setCreatedBy("S2026015");
        hsChapterRisk.setCreatedDate(new Date());
        hsChapterRisk.setDescription(description);
        return hsChapterRisk;

    }

    public static RelevantMaterial getRelevantMaterial(User user, String description) {
        RelevantMaterial relevantMaterial = new RelevantMaterial();
        relevantMaterial.setCreatedBy("S2026015");
        relevantMaterial.setCreatedDate(new Date());
        relevantMaterial.setDescription(description);
        return relevantMaterial;
    }

    public static Program getProgram(User user, String description) {
        Program program = new Program();
        program.setCreatedBy("S2026015");
        program.setCreatedDate(new Date());
        program.setDescription(description);

        return program;
    }

    public static EmailNotification getEmailNotification(NotificationType notificationType, String line1, String line2, String line3, String line4) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setCreatedBy("S2026064");
        emailNotification.setCreatedDate(new Date());
        emailNotification.setLine1(line1);
        emailNotification.setLine2(line2);
        emailNotification.setLine3(line3);
        emailNotification.setLine4(line4);
        emailNotification.setNotificationType(notificationType);
        return emailNotification;

    }

    public static Attachment getAttachment(String createdBy) {
        Attachment attachment = new Attachment();
        attachment.setCreatedBy(createdBy);
        attachment.setCreatedDate(new Date());
        attachment.setCode(RandomStringUtils.randomNumeric(5));
        attachment.setDescription(RandomStringUtils.randomAlphabetic(15));
        attachment.setDocumentSize(Double.valueOf(RandomStringUtils.randomNumeric(6)));
        return attachment;
    }

    public static SuspiciousCase getSarCase(String createdBy, ClientType clientType, Integer numberOfTravellerIndividual, Integer numberOfCompanyTraderImportExport, CustomExcise customExcise, YesNoEnum disclosePersonalDetails, YesNoEnum affidavit, Date pendEndDate) {
        SuspiciousCase suspiciousCase = new SuspiciousCase();
        suspiciousCase.setCreatedDate(new Date());
        suspiciousCase.setCreatedBy(createdBy);
        suspiciousCase.setCaseRefNumber(RandomStringUtils.randomAlphanumeric(12));
        suspiciousCase.setCaseType(CaseType.SAR);
        suspiciousCase.setStatus(CaseStatus.NEW);

        SarCase sarCase = new SarCase();
        sarCase.setCreatedDate(new Date());
        sarCase.setCreatedBy(createdBy);
        sarCase.setVat(RandomStringUtils.randomNumeric(9));
        sarCase.setCompanyNumber(RandomStringUtils.randomNumeric(5));
        sarCase.setIncomeTax(RandomStringUtils.randomNumeric(9));
        sarCase.setNumberOfTravellerIndividual(numberOfTravellerIndividual);
        sarCase.setNumberOfCompanyTraderImportExport(numberOfCompanyTraderImportExport);
        sarCase.setClientType(clientType);
        sarCase.setCustomExcise(customExcise);
        sarCase.setDisclosePersonalDetails(disclosePersonalDetails);
        sarCase.setAffidavit(affidavit);
        sarCase.setPendEndDate(pendEndDate);

        NonComplianceDetails nonComplianceDetails = new NonComplianceDetails();
        nonComplianceDetails.setCreatedDate(new Date());
        nonComplianceDetails.setCreatedBy(createdBy);
        nonComplianceDetails.setAdditionalInformation(RandomStringUtils.randomAlphanumeric(12));
        nonComplianceDetails.setDayTime("12h00");
        nonComplianceDetails.setDeclaration(YesNoEnum.YES);
        nonComplianceDetails.setDescription(RandomStringUtils.randomAlphanumeric(12));
        nonComplianceDetails.setIllegalActivities(YesNoEnum.YES);
        nonComplianceDetails.setIncidentDate(new Date());
        nonComplianceDetails.setLicensingRegistration(YesNoEnum.YES);
        nonComplianceDetails.setLocation("Centurion");
        nonComplianceDetails.setLoggedOnUserFullName("s2024726");
        nonComplianceDetails.setSubmission(YesNoEnum.YES);
        nonComplianceDetails.setTimeFrame(TimeFrame.THE_INCIDENT_WILL_HAPPEN_TODAY);
        nonComplianceDetails.setYearlyLoss(YearlyLoss.BETWEEN_500001_AND_5000000);
        nonComplianceDetails.setPayment(YesNoEnum.YES);
        nonComplianceDetails.setOther(YesNoEnum.YES);
        sarCase.addNonComplianceDetails(nonComplianceDetails);

        Address address = new Address();
        address.setCreatedDate(new Date());
        address.setCreatedBy(createdBy);
        address.setAddressType(AddressType.BUSINESS);
        address.setArea("Gauteng");
        address.setCode("0157");
        address.setCountryName("South Africa");
        address.setLine1("846 Thorn Field Estate");
        address.setLine2("101 Perdeblom Street");
        address.setLine3("Monavoni Ext 6");
        address.setLine4("Centurion");
        address.setLine5("Tshwane");
        address.setProvince(Province.GAUTENG);
        sarCase.addAddress(address);

        EntityTypeDetails entityTypeDetails1 = new EntityTypeDetails();
        entityTypeDetails1.setCreatedDate(new Date());
        entityTypeDetails1.setCreatedBy(createdBy);
        entityTypeDetails1.setCompanyNumber(RandomStringUtils.randomNumeric(10));
        entityTypeDetails1.setRegisteredName("Standard Bank");
        entityTypeDetails1.setTradingName("SBSA");

        Address entityTypeDetailsAddress1 = new Address();
        entityTypeDetailsAddress1.setCreatedBy(createdBy);
        entityTypeDetailsAddress1.setCreatedDate(new Date());
        entityTypeDetailsAddress1.setAddressType(AddressType.BUSINESS);
        entityTypeDetails1.setBusinessAddress(entityTypeDetailsAddress1);
        sarCase.addEntityTypeDetails(entityTypeDetails1);

        EntityTypeDetails entityTypeDetails2 = new EntityTypeDetails();
        entityTypeDetails2.setCreatedDate(new Date());
        entityTypeDetails2.setCreatedBy(createdBy);
        entityTypeDetails2.setCompanyNumber(RandomStringUtils.randomNumeric(10));
        entityTypeDetails2.setRegisteredName("Standard Bank");
        entityTypeDetails2.setTradingName("SBSA");
        Address entityTypeDetailsAddress2 = new Address();
        entityTypeDetailsAddress2.setCreatedBy(createdBy);
        entityTypeDetailsAddress2.setCreatedDate(new Date());
        entityTypeDetailsAddress2.setAddressType(AddressType.BUSINESS);
        entityTypeDetails2.setBusinessAddress(entityTypeDetailsAddress2);
        //sarCase.addEntityTypeDetails(entityTypeDetails2);

        ReportedPerson reportPerson = new ReportedPerson();
        reportPerson.setBirthDate(new Date());
        reportPerson.setCountryName("South Africa");
        reportPerson.setCreatedBy(createdBy);
        reportPerson.setCreatedDate(new Date());
        reportPerson.setFaxNumber("0112343546");
        reportPerson.setEmailAddress("hasani@gmail.com");
        reportPerson.setFirstName("Hasani");
        reportPerson.setFullNames("Hasani Maluleke");
        reportPerson.setHomeTelephoneNumber("0115437465");
        reportPerson.setIdentityNumber(RandomStringUtils.randomNumeric(13));
        reportPerson.setInitials("HM");
        reportPerson.setLastName("Maluleke");
        reportPerson.setMobileNumber("0722776540");
        reportPerson.setPassportNumber(RandomStringUtils.randomNumeric(13));
        reportPerson.setPersonType(PersonType.REPORTED_PERSON);
        reportPerson.setProvince(Province.GAUTENG);
        reportPerson.setWebAddress("www.hklsolarpower.co.za");
        reportPerson.setWorkTelephoneNumber("0117654653");

        Address reportedPersonPhysicalAddress = new Address();
        reportedPersonPhysicalAddress.setCreatedDate(new Date());
        reportedPersonPhysicalAddress.setCreatedBy(createdBy);
        reportedPersonPhysicalAddress.setAddressType(AddressType.PHYSICAL);
        reportedPersonPhysicalAddress.setArea("Gauteng");
        reportedPersonPhysicalAddress.setCode("0157");
        reportedPersonPhysicalAddress.setCountryName("South Africa");
        reportedPersonPhysicalAddress.setLine1("654 Mamelodi");
        reportedPersonPhysicalAddress.setLine2("101 Perdeblom Street");
        reportedPersonPhysicalAddress.setLine3("Monavoni Ext 6");
        reportedPersonPhysicalAddress.setLine4("Centurion");
        reportedPersonPhysicalAddress.setLine5("Tshwane");
        reportedPersonPhysicalAddress.setProvince(Province.GAUTENG);
        reportPerson.setResidentialAddress(reportedPersonPhysicalAddress);
        sarCase.addReportedPerson(reportPerson);

        ReportingPerson reportingPerson = new ReportingPerson();
        reportingPerson.setPersonType(PersonType.REPORTING_PERSON);
        reportingPerson.setCreatedDate(new Date());
        reportingPerson.setCreatedBy(createdBy);
        reportingPerson.setBirthDate(new Date());

        Address reportingPersonAddress = new Address();
        reportingPersonAddress.setCreatedDate(new Date());
        reportingPersonAddress.setCreatedBy(createdBy);
        reportingPersonAddress.setAddressType(AddressType.PHYSICAL);
        reportingPersonAddress.setArea("Gauteng");
        reportingPersonAddress.setCode("0157");
        reportingPersonAddress.setCountryName("South Africa");
        reportingPersonAddress.setLine1("654 Mamelodi");
        reportingPersonAddress.setLine2("101 Perdeblom Street");
        reportingPersonAddress.setLine3("Monavoni Ext 6");
        reportingPersonAddress.setLine4("Centurion");
        reportingPersonAddress.setLine5("Tshwane");
        reportingPersonAddress.setProvince(Province.GAUTENG);
        reportingPerson.setResidentialAddress(reportingPersonAddress);

        reportingPerson.setEmailAddress("Joseph@absa.co.za");
        reportingPerson.setFaxNumber("0115882543");
        reportingPerson.setFirstName("Joseph");
        reportingPerson.setLastName("Makau");
        reportingPerson.setFullNames("Joseph Makau");
        reportingPerson.setHomeTelephoneNumber("0126653654");
        reportingPerson.setIdentityNumber(RandomStringUtils.randomNumeric(13));
        reportingPerson.setPassportNumber(RandomStringUtils.randomNumeric(13));
        reportingPerson.setInitials("JD");
        reportingPerson.setMobileNumber("0722334765");
        reportingPerson.setProvince(Province.GAUTENG);
        reportingPerson.setWebAddress("www.hklsolarpower.com");
        reportingPerson.setWorkTelephoneNumber("0118722676");
        sarCase.setReportingPerson(reportingPerson);

        Comment comment1 = new Comment();
        comment1.setCreatedDate(new Date());
        comment1.setCreatedBy(createdBy);
        comment1.setDescription(RandomStringUtils.randomAlphabetic(55));

        Comment comment2 = new Comment();
        comment2.setCreatedDate(new Date());
        comment2.setCreatedBy(createdBy);
        comment2.setDescription(RandomStringUtils.randomAlphabetic(55));

        Comment comment3 = new Comment();
        comment3.setCreatedDate(new Date());
        comment3.setCreatedBy(createdBy);
        comment3.setDescription(RandomStringUtils.randomAlphabetic(55));
        suspiciousCase.addComment(comment1);
        suspiciousCase.addComment(comment2);
        suspiciousCase.addComment(comment3);
        suspiciousCase.setSarCase(sarCase);

        return suspiciousCase;
    }

    public static SuspiciousCase getVddLedCase(String createdBy) {
        SuspiciousCase suspiciousCase = new SuspiciousCase();
        suspiciousCase.setCreatedDate(new Date());
        suspiciousCase.setCreatedBy(createdBy);
        suspiciousCase.setCaseRefNumber(RandomStringUtils.randomAlphanumeric(12));
        suspiciousCase.setCaseType(CaseType.VDDL);
        suspiciousCase.setStatus(CaseStatus.NEW);

        VddLedCase vddLed = new VddLedCase();
        vddLed.setCreatedDate(new Date());
        vddLed.setCreatedBy(createdBy);
        CustomsExciseClientType customsExciseClientType = new CustomsExciseClientType();
        customsExciseClientType.setCreatedDate(new Date());
        customsExciseClientType.setCreatedBy(createdBy);
        customsExciseClientType.setAgent(true);
        customsExciseClientType.setDepot(true);
        customsExciseClientType.setExporter(true);
        customsExciseClientType.setImporter(true);
        customsExciseClientType.setLicensee(true);
        customsExciseClientType.setManufacturer(true);
        customsExciseClientType.setOther(true);
        customsExciseClientType.setOtherDescription(RandomStringUtils.randomAlphabetic(10));
        customsExciseClientType.setRegistrant(true);
        customsExciseClientType.setRemover(true);
        customsExciseClientType.setTerminal(true);
        customsExciseClientType.setWarehouse(true);
        vddLed.setCustomsExciseClientType(customsExciseClientType);
        vddLed.setInstruction(RandomStringUtils.randomAlphabetic(10));
        vddLed.setPendEndDate(new Date());

//        PublicOfficer publicOfficer = new PublicOfficer();
//        publicOfficer.setPersonType(PersonType.REPORTED_PERSON);
//        publicOfficer.setCreatedDate(new Date());
//        publicOfficer.setCreatedBy(createdBy);
//        publicOfficer.setBirthDate(new Date());
//
//        Address publicOfficerAddress = new Address();
//        publicOfficerAddress.setCreatedDate(new Date());
//        publicOfficerAddress.setCreatedBy(createdBy);
//        publicOfficerAddress.setAddressType(AddressType.PHYSICAL);
//        publicOfficerAddress.setArea("Gauteng");
//        publicOfficerAddress.setCode("0157");
//        publicOfficerAddress.setCountryName("South Africa");
//        publicOfficerAddress.setLine1("743 Century Manor Estate");
//        publicOfficerAddress.setLine2("101 Perdeblom Street");
//        publicOfficerAddress.setLine3("Monavoni Ext 6");
//        publicOfficerAddress.setLine4("Centurion");
//        publicOfficerAddress.setLine5("Tshwane");
//        publicOfficerAddress.setProvince(Province.GAUTENG);
//        //publicOfficer.addAddress(publicOfficerAddress);
//
//        publicOfficer.setEmailAddress("morris@absa.co.za");
//        publicOfficer.setFaxNumber("0115342543");
//        publicOfficer.setFirstName("Morris");
//        publicOfficer.setLastName("Mabasa");
//        publicOfficer.setFullNames("Morris Mabasa");
//        publicOfficer.setHomeTelephoneNumber("0126453654");
//        publicOfficer.setIdentityNumber(RandomStringUtils.randomNumeric(13));
//        publicOfficer.setPassportNumber(RandomStringUtils.randomNumeric(13));
//        publicOfficer.setInitials("TV");
//        publicOfficer.setMobileNumber("0722114765");
//        publicOfficer.setProvince(Province.GAUTENG);
//        publicOfficer.setWebAddress("www.hklsolarpower.com");
//        publicOfficer.setWorkTelephoneNumber("0118744676");
//        vddLed.setPublicOfficer(publicOfficer);
        VddLedType vddlType = new VddLedType();
        vddlType.setCreatedDate(new Date());
        vddlType.setCreatedBy(createdBy);
        vddlType.setAcquittals(true);
        vddlType.setApdpAccount(true);
        vddlType.setAppDutyAccount(true);
        vddlType.setAppSubstitutionOvertimeGoods(true);
        vddlType.setApprovedExporterVerifications(true);
        vddlType.setBranchOpHandOver(true);
        vddlType.setCancellations(true);
        vddlType.setDefaultOnDefement(true);
        vddlType.setHeadOfficeCaseReferral(true);
        vddlType.setLiquidations(true);
        vddlType.setMaaVerificationsRequest(true);
        vddlType.setOther(true);
        vddlType.setOtherAppSureties(true);
        vddlType.setOtherDescription(RandomStringUtils.randomAlphabetic(10));
        vddlType.setRebateApprovals(true);
        vddlType.setRefunds(true);
        vddlType.setRuleOfOrigin(true);
        vddlType.setSarsServiceManagerCaseReferral(true);
        vddlType.setStocklistAccountDa490(true);
        vddlType.setTiuCaseHandOver(true);
        vddlType.setVdnTdnApp(true);
        vddlType.setWarehouseApprovals(true);
        vddLed.setVddlType(vddlType);
        suspiciousCase.setVddLed(vddLed);
        return suspiciousCase;
    }

    public static RiskAssessment getRiskAssessment(HsChapterRisk hsChapterRisk, String createdBy) {
        RiskAssessment riskAssessment = new RiskAssessment();
        riskAssessment.setCreatedDate(new Date());
        riskAssessment.setCreatedBy(createdBy);
        riskAssessment.setContraventionHistory(1);
        riskAssessment.setContraventionHistoryOption(YesNoEnum.YES);
        riskAssessment.setCustomsRegistrationParticular(RandomStringUtils.randomAlphabetic(10));
        riskAssessment.setDutyExemption(1);
        riskAssessment.setDutySuspensionOption(YesNoEnum.YES);
        riskAssessment.setDutySuspension(1);
        riskAssessment.setDutyExemptionOption(YesNoEnum.YES);
        riskAssessment.setLicensingAndRegistration(1);
        riskAssessment.setLicensingAndRegistrationOption(YesNoEnum.YES);
        riskAssessment.setMainIndustry(MainIndustry.BASE_METALS);
        riskAssessment.setModeOfTransport(1);
        riskAssessment.setModeOfTransportOption(YesNoEnum.YES);
        riskAssessment.setOriginCountry(1);
        riskAssessment.setOriginCountryOption(YesNoEnum.YES);
        riskAssessment.setSecurity(1);
        riskAssessment.setSecurityOption(YesNoEnum.YES);
        riskAssessment.setTarrif(1);
        riskAssessment.setTarrifOption(YesNoEnum.NO);
        riskAssessment.setTradeRestrictions(1);
        riskAssessment.setTradeRestrictionsOption(YesNoEnum.YES);
        riskAssessment.setValuation(1);
        riskAssessment.setValuationOption(YesNoEnum.NO);
        riskAssessment.setWarehousing(1);
        riskAssessment.setWarehousingOption(YesNoEnum.YES);

        RiskAssessmentDetails riskAssessmentDetails = new RiskAssessmentDetails();
        riskAssessmentDetails.setCreatedDate(new Date());
        riskAssessmentDetails.setCreatedBy(createdBy);
        riskAssessmentDetails.setDescription(RandomStringUtils.randomAlphabetic(10));
        riskAssessmentDetails.setOverallRiskRating(5);
        riskAssessmentDetails.setRecommendation(RandomStringUtils.randomAlphabetic(10));
        riskAssessmentDetails.setRevenueAtRisk(100000.00);
        riskAssessmentDetails.setRiskArea(RiskArea.TARRIF);
        riskAssessmentDetails.setRevenueAtRisk(100000.00);
        riskAssessmentDetails.setRiskAssessmentRelate(YesNoEnum.YES);
//        
//        riskAssessmentDetails.setRiskRatingConsequence(RiskRatingConsequence.INSIGNIFICANT);
//        riskAssessmentDetails.setRiskRatingLikehood(RiskRatingLikelihood.UNLIKEY);

        riskAssessmentDetails.setHsChapterRisk(hsChapterRisk);
        riskAssessment.addRiskAssessmentDetails(riskAssessmentDetails);
        return riskAssessment;
    }

    public static AuditPlan getAuditPlan(AuditPlanType auditPlanType, AuditType auditType, RiskArea riskArea, String createdBy) {
        AuditPlan auditPlan = new AuditPlan();
        auditPlan.setCreatedDate(new Date());
        auditPlan.setCreatedBy(createdBy);

        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedDate(new Date());
        auditDetails.setCreatedBy(createdBy);
        auditDetails.setObjective(RandomStringUtils.randomAlphabetic(10));
        auditDetails.setRiskArea(riskArea);
        auditDetails.setScope(RandomStringUtils.randomAlphabetic(10));
        auditPlan.addAuditDetails(auditDetails);

        auditPlan.setAuditType(auditType);
        auditPlan.setCancelDate(new Date());
        auditPlan.setStartDate(new Date());
        auditPlan.setVddlAuditObjective(RandomStringUtils.randomAlphabetic(10));
        auditPlan.setVddlAuditScope(RandomStringUtils.randomAlphabetic(10));
        return auditPlan;
    }

    public static AuditReport getAuditReport(AuditPlan auditPlan, String createdBy) {
        AuditReport auditReport = new AuditReport();
        auditReport.setCreatedDate(new Date());
        auditReport.setCreatedBy(createdBy);
        //auditReport.setAuditPlan(auditPlan);
        auditReport.setAuditorProofOfPayment(true);
        auditReport.setClientRequestGoodsBack(YesNoEnum.YES);
        auditReport.setDueDateExtend(new Date());
        auditReport.setExtendDueDate(true);
        auditReport.setExtendReason(RandomStringUtils.randomAlphabetic(10));
        auditReport.setFindingsSummary(RandomStringUtils.randomAlphabetic(10));
        auditReport.setLodDate(new Date());
        auditReport.setLodIssued(true);
        auditReport.setLoiDate(new Date());
        auditReport.setLoiExtentionReason(LOIExtentionReason.TIME_EXTENSION);
        auditReport.setLoiIssued(true);
        auditReport.setOutstandingAmount(4500);
        auditReport.setPayedAmount(2300);
        auditReport.setPaymentType(PaymentType.PARTIAL_PAYMENT);
        auditReport.setSeizureAndDetained(YesNoEnum.YES);
        auditReport.setSubPenaltyTotal(1500);
        auditReport.setThereRevenueLiability(YesNoEnum.YES);
        auditReport.setTotalRandValuePerSection93(3500);
        auditReport.setWasLienOnGood(YesNoEnum.NO);
        auditReport.setWasOffenceDetected(YesNoEnum.YES);
        AdditionalRisk additionalRisk = new AdditionalRisk();
        additionalRisk.setCreatedDate(new Date());
        additionalRisk.setCreatedBy(createdBy);
        additionalRisk.setContraventionHistory(YesNoEnum.YES);
        additionalRisk.setContraventionHistoryDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setDutyExemption(YesNoEnum.YES);
        additionalRisk.setDutyExemptionDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setDutySuspension(YesNoEnum.YES);
        additionalRisk.setDutySuspensionDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setLicensingAndRegistration(YesNoEnum.YES);
        additionalRisk.setLicensingAndRegistrationDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setModeOfTransport(YesNoEnum.YES);
        additionalRisk.setModeOfTransportDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setOriginCountry(YesNoEnum.NO);
        additionalRisk.setOriginCountryDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setOtherPAndR(YesNoEnum.YES);
        additionalRisk.setOtherPAndRDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setRevenueProtection(YesNoEnum.NO);
        additionalRisk.setRevenueProtectionDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setRulesOfOrigin(YesNoEnum.YES);
        additionalRisk.setRulesOfOriginDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setSecurity(YesNoEnum.YES);
        additionalRisk.setSecurityDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setTarrif(YesNoEnum.YES);
        additionalRisk.setTarrifDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setTradeRestrictionDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setTradeRestrictions(YesNoEnum.YES);
        additionalRisk.setValuation(YesNoEnum.NO);
        additionalRisk.setValuationDescription(RandomStringUtils.randomAlphabetic(10));
        additionalRisk.setWarehousing(YesNoEnum.YES);
        additionalRisk.setWarehousingDescription(RandomStringUtils.randomAlphabetic(10));
        auditReport.setAdditionalRisk(additionalRisk);

        RevenueLiability revenueLiability1 = new RevenueLiability();
        revenueLiability1.setCreatedDate(new Date());
        revenueLiability1.setCreatedBy(createdBy);
        revenueLiability1.setCustomsInterest(150);
        revenueLiability1.setForfieture(250);
        revenueLiability1.setTotalSubLiability(130);
        revenueLiability1.setVat(1500);
        revenueLiability1.setVatInterest(250);
        revenueLiability1.setVatPenalty(450);

//        RevenueLiability revenueLiability2 = new RevenueLiability();
//        revenueLiability2.setCreatedDate(new Date());
//        revenueLiability2.setCreatedBy(createdBy);
//
//        revenueLiability2.setCustomsInterest(150);
//
//        revenueLiability2.setForfieture(250);
//        revenueLiability2.setTotalSubLiability(130);
//        revenueLiability2.setVat(1500);
//        revenueLiability2.setVatInterest(250);
//        revenueLiability2.setVatPenalty(450);
        auditReport.setRevenueLiability(revenueLiability1);

        TotalLiability totalLiability = new TotalLiability();
        totalLiability.setCreatedDate(new Date());
        totalLiability.setCreatedBy(createdBy);
        totalLiability.setLiabilitiesTotal(150);
        totalLiability.setTotalCustomsDuty(150);
        totalLiability.setTotalCustomsInterest(150);
        totalLiability.setTotalDutyOther(150);
        totalLiability.setTotalExciseDuty(150);
        totalLiability.setTotalForfieture(150);
        totalLiability.setTotalPenalty(150);
        totalLiability.setTotalVat(150);
        totalLiability.setTotalVatInterest(150);
        totalLiability.setTotalVatPenalty(150);
        auditReport.setTotalLiability(totalLiability);
        AuditReportOffencePenalty auditReportOffence1 = new AuditReportOffencePenalty();
        auditReportOffence1.setCreatedDate(new Date());
        auditReportOffence1.setCreatedBy(createdBy);
        auditReportOffence1.setAuditReport(auditReport);
        OffenceAndPenalty offenceAndPenalty1 = new OffenceAndPenalty();
        offenceAndPenalty1.setCreatedDate(new Date());
        offenceAndPenalty1.setCreatedBy(createdBy);

//        offenceAndPenalty1.setDeposit(150);
//        offenceAndPenalty1.setDescription(RandomStringUtils.randomAlphabetic(10));
//        offenceAndPenalty1.setNotes(RandomStringUtils.randomAlphabetic(10));
//        offenceAndPenalty1.setNotesAmount(RandomStringUtils.randomAlphabetic(10));
//        offenceAndPenalty1.setOffenceClassificationType(CaseType.VDDL);
//        offenceAndPenalty1.setOffenceDescriptionType(OffenceDescriptionType.ELECTRONIC_SUBMISSION);
//        offenceAndPenalty1.setSectionAmount(0.00);
//        offenceAndPenalty1.setSectionRule(RandomStringUtils.randomAlphabetic(10));
        offenceAndPenalty1.setSubOffenceOrPenaltyAmount(2500);
//        auditReportOffence1.setOffenceAndPenalty(offenceAndPenalty1);
        AuditReportOffencePenalty auditReportOffence2 = new AuditReportOffencePenalty();
        auditReportOffence2.setCreatedDate(new Date());
        auditReportOffence2.setCreatedBy(createdBy);
        auditReportOffence2.setAuditReport(auditReport);
        OffenceAndPenalty offenceAndPenalty2 = new OffenceAndPenalty();
        offenceAndPenalty2.setCreatedDate(new Date());
        offenceAndPenalty2.setCreatedBy(createdBy);

//        offenceAndPenalty2.setDeposit(150);
//        offenceAndPenalty2.setDescription(RandomStringUtils.randomAlphabetic(10));
//        offenceAndPenalty2.setNotes(RandomStringUtils.randomAlphabetic(10));
//        offenceAndPenalty2.setNotesAmount(RandomStringUtils.randomAlphabetic(10));
//        offenceAndPenalty2.setOffenceClassificationType(CaseType.SAR);
//        offenceAndPenalty2.setOffenceDescriptionType(OffenceDescriptionType.REGISTRATION);
//        offenceAndPenalty2.setSectionAmount(0.00);
//        offenceAndPenalty2.setSectionRule(RandomStringUtils.randomAlphabetic(10));
        offenceAndPenalty2.setSubOffenceOrPenaltyAmount(2500);
//        auditReportOffence2.setOffenceAndPenalty(offenceAndPenalty2);
        //auditReport.addOffenceAndPenalty(auditReportOffence1);
        //auditReport.addOffenceAndPenalty(auditReportOffence2);
        return auditReport;
    }

    public static OffenceAndPenalty getOffenceAndPenalty(User user, OffenceDescriptionType offenceDescriptionType, String sectionRule, String offenceDescription, double deposit, String notes, double sectionAmount, String notesAmount, double subOffenceOrPenaltyAmount, CaseType offenceClassificationType) {
        OffenceAndPenalty offenceAndPenalty = new OffenceAndPenalty();
        offenceAndPenalty.setCreatedBy("S2026015");
        offenceAndPenalty.setCreatedDate(new Date());

//        offenceAndPenalty.setOffenceClassificationType(offenceClassificationType);
//        offenceAndPenalty.setSectionRule(sectionRule);
//        offenceAndPenalty.setDescription(offenceDescription);
//        offenceAndPenalty.setDeposit(deposit);
//        offenceAndPenalty.setOffenceDescriptionType(offenceDescriptionType);
//        offenceAndPenalty.setDeposit(deposit);
//        offenceAndPenalty.setOffenceDescriptionType(offenceDescriptionType);
//        offenceAndPenalty.setNotes(notes);
//        offenceAndPenalty.setSectionAmount(sectionAmount);
//        offenceAndPenalty.setNotesAmount(notesAmount);
//        offenceAndPenalty.setSubOffenceOrPenaltyAmount(subOffenceOrPenaltyAmount);
//        offenceAndPenalty.setOffenceDescriptionType(offenceDescriptionType);
        return offenceAndPenalty;
    }

    public static Duty getDuty(String description, DutyType dutyType) {
        Duty duty = new Duty();
        duty.setCreatedBy("S2026015");
        duty.setCreatedDate(new Date());
        duty.setDescription(description);
        duty.setDutyType(dutyType);
        return duty;
    }
//    public static RevenueLiability getRevenueLiability() {
//        RevenueLiability revenueLiability = new RevenueLiability();
//        revenueLiability.setCreatedBy("s2024726");
//        revenueLiability.setCreatedDate(new Date());
//        revenueLiability.setCustomsDutySubLiability(RandomStringUtils.randomAlphabetic(10));
//        revenueLiability.setCustomsInterest(0.00);
//        revenueLiability.setDutyOtherSubLiability(RandomStringUtils.randomAlphabetic(10));
//        revenueLiability.setExciseDutySubLiability(RandomStringUtils.randomAlphabetic(10));
//        revenueLiability.setForfieture(0.00);
//        revenueLiability.setTotalSubLiability(0.00);
//        revenueLiability.setVat(0.00);
//        revenueLiability.setVatInterest(0.00);
//        revenueLiability.setVatPenalty(0.00);
//        return revenueLiability;
//    }

    public static OffenceClassification getOffenceClassification(String description, CaseType caseType) {
        OffenceClassification offenceClassification = new OffenceClassification();
        offenceClassification.setCreatedBy("S2026015");
        offenceClassification.setCreatedDate(new Date());
        offenceClassification.setDescription(description);
        offenceClassification.setClassificationType(caseType);
        return offenceClassification;

    }

    public static SectionRule getSectionRule(OffenceClassification offenceClassification, String description) {
        SectionRule sectionRule = new SectionRule();
        sectionRule.setCreatedBy("S2026015");
        sectionRule.setCreatedDate(new Date());
        sectionRule.setDescription(description);
        sectionRule.setOffenceClassification(offenceClassification);
        return sectionRule;

    }

    public static OffenceDescription getOffenceDescription(SectionRule sectionRule, String description, Double deposit, String notes, String notesAmount, Double sectionAmount) {
        OffenceDescription offenceDescription = new OffenceDescription();
        offenceDescription.setCreatedBy("S2026015");
        offenceDescription.setCreatedDate(new Date());
        offenceDescription.setDescription(description);
        offenceDescription.setSectionRule(sectionRule);
        offenceDescription.setDeposit(deposit);
        offenceDescription.setNotes(notes);
        offenceDescription.setNotesAmount(notesAmount);
        offenceDescription.setSectionAmount(sectionAmount);
        return offenceDescription;

    }
}
