package sars.pca.app.tests;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sars.pca.app.common.AuditPlanType;
import sars.pca.app.common.AuditType;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.ClientType;
import sars.pca.app.common.CustomExcise;
import sars.pca.app.common.DutyType;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.OffenceClassificationType;
import sars.pca.app.common.OffenceDescriptionType;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;
import sars.pca.app.common.RiskArea;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.config.TestDataSourceConfiguration;
import sars.pca.app.domain.AuditPlan;
import sars.pca.app.domain.AuditReport;
import sars.pca.app.domain.Duty;
import sars.pca.app.domain.EmailNotification;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.OffenceAndPenalty;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.Program;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.domain.RevenueLiabilityDuty;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.domain.SarCaseUser;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;
import sars.pca.app.service.AuditPlanServiceLocal;
import sars.pca.app.service.AuditReportServiceLocal;
import sars.pca.app.service.DutyServiceLocal;
import sars.pca.app.service.EmailNotificationServiceLocal;
import sars.pca.app.service.HsChapterRiskServiceLocal;
import sars.pca.app.service.IbrDataServiceLocal;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.OffenceAndPenaltyServiceLocal;
import sars.pca.app.service.OffenceClassificationServiceLocal;
import sars.pca.app.service.OffenceDescriptionServiceLocal;
import sars.pca.app.service.ProgramServiceLocal;
import sars.pca.app.service.RelevantMaterialServiceLocal;
import sars.pca.app.service.RiskAssessmentServiceLocal;
import sars.pca.app.service.SarCaseServiceLocal;
import sars.pca.app.service.SectionRuleServiceLocal;
import sars.pca.app.service.SuspiciousCaseServiceLocal;
import sars.pca.app.service.UserRoleServiceLocal;
import sars.pca.app.service.UserServiceLocal;
import sars.pca.app.service.VddlServiceLocal;
import sars.pca.app.service.OffenceClassificationServiceLocal;
import sars.pca.app.service.SectionRuleServiceLocal;
import sars.pca.app.service.OffenceDescriptionServiceLocal;
import sars.pca.app.service.SlaConfigurationServiceLocal;

/**
 *
 * @author S2026987
 */
@Ignore
@EnableJpaAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestDataSourceConfiguration.class)
public class PcaTestCase {

    @Autowired
    private UserRoleServiceLocal userRoleService;
    @Autowired
    private UserServiceLocal userService;
    @Autowired
    private SlaConfigurationServiceLocal slaConfigurationService;
    @Autowired
    private LocationOfficeServiceLocal locationOfficeService;
    @Autowired
    private HsChapterRiskServiceLocal hsChapterRiskService;
    @Autowired
    private RelevantMaterialServiceLocal relevantMaterialService;
    @Autowired
    private ProgramServiceLocal programService;
    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;
    @Autowired
    private SarCaseServiceLocal sarCaseService;
    @Autowired
    private RiskAssessmentServiceLocal riskAssessmentService;
    @Autowired
    private AuditPlanServiceLocal auditPlanService;

    @Autowired
    private AuditReportServiceLocal auditReportService;

    @Autowired
    private IbrDataServiceLocal ibrDataService;

    @Autowired
    private VddlServiceLocal vddlService;

    @Autowired
    private DutyServiceLocal dutyService;

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;

    @Autowired
    private OffenceAndPenaltyServiceLocal offenceAndPenaltyService;

    @Autowired
    private OffenceClassificationServiceLocal classificationervice;
    @Autowired
    private SectionRuleServiceLocal sectionRuleService;
    @Autowired
    private OffenceDescriptionServiceLocal offenceDescriptionService;

    SuspiciousCase persistentSarCase;
    StringBuilder builder = new StringBuilder();

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testScenarioA() {
        UserRole adminRole = BootStrapHelper.getAdministratorUserRole("Administrator");
        userRoleService.save(adminRole);

        UserRole capturerRole = BootStrapHelper.getClientInterfaceUserUserRole("Client Interface Department");
        userRoleService.save(capturerRole);

        UserRole sarsInternalUser = BootStrapHelper.getSARSInternalUserUserRole("SARS internal User");
        userRoleService.save(sarsInternalUser);

        UserRole crcsReviewer = BootStrapHelper.getCRCSReviewerUserRole("CRCS Reviewer");
        userRoleService.save(crcsReviewer);

        UserRole crcsQualityAssurer = BootStrapHelper.getCRCSQualityAssureUserRole("CRCS Quality Assurer");
        userRoleService.save(crcsQualityAssurer);

        UserRole crcsOpsSpecialist = BootStrapHelper.getCRCSOpsSpecialistUserRole("CRCS Ops Specialist");
        userRoleService.save(crcsOpsSpecialist);

        UserRole crcsOpsManager = BootStrapHelper.getCRCSOpsManagerUserRole("CRCS Ops Manager");
        userRoleService.save(crcsOpsManager);

        UserRole ciOpsManager = BootStrapHelper.getCIOpsManagerUserRole("CI Ops Manager");
        userRoleService.save(ciOpsManager);

        UserRole ciAuditor = BootStrapHelper.getCIAuditorUserRole("CI Auditor");
        userRoleService.save(ciAuditor);

        UserRole management = BootStrapHelper.getManagementUserRole("Management");
        userRoleService.save(management);

        UserRole seniorAuditor = BootStrapHelper.getSeniorAuditorUserRole("Senior Auditor");
        userRoleService.save(seniorAuditor);

        UserRole groupManager = BootStrapHelper.getGroupManagerUserRole("Group Manager");
        userRoleService.save(groupManager);

        UserRole seniorManager = BootStrapHelper.getSeniorManagerUserRole("Senior Manager");
        userRoleService.save(seniorManager);

        UserRole executive = BootStrapHelper.getExecutiveUserRole("Executive");
        userRoleService.save(executive);
    }

    @Test
    public void testScenarioB() {
        LocationOffice locationOffice1 = BootStrapHelper.getLocationOffice(Province.GAUTENG, "Pretoria", OfficeType.CI_OFFICE);
        locationOfficeService.save(locationOffice1);

        LocationOffice locationOffice2 = BootStrapHelper.getLocationOffice(Province.GAUTENG, "Johannesburg", OfficeType.CRSC_OFFICE);
        locationOfficeService.save(locationOffice2);

        LocationOffice locationOffice3 = BootStrapHelper.getLocationOffice(Province.GAUTENG, "Pretoria", OfficeType.CRSC_OFFICE);
        locationOfficeService.save(locationOffice3);

        LocationOffice locationOffice4 = BootStrapHelper.getLocationOffice(Province.GAUTENG, "Johannesburg", OfficeType.CI_OFFICE);
        locationOfficeService.save(locationOffice4);

        LocationOffice locationOffice5 = BootStrapHelper.getLocationOffice(Province.KWAZULU_NATAL, "Durban", OfficeType.CI_OFFICE);
        locationOfficeService.save(locationOffice5);

        LocationOffice locationOffice6 = BootStrapHelper.getLocationOffice(Province.WESTERN_CAPE, "Cape Town", OfficeType.CRSC_OFFICE);
        locationOfficeService.save(locationOffice6);

        LocationOffice locationOffice7 = BootStrapHelper.getLocationOffice(Province.KWAZULU_NATAL, "Durban", OfficeType.CRSC_OFFICE);
        locationOfficeService.save(locationOffice7);

        LocationOffice locationOffice8 = BootStrapHelper.getLocationOffice(Province.WESTERN_CAPE, "Cape Town", OfficeType.CI_OFFICE);
        locationOfficeService.save(locationOffice8);
    }

    @Test
    public void testScenarioC() {
        SlaConfiguration slaConfiguration = new SlaConfiguration(30, 30, 30, 30, 1, 30, 7, 21, 10.5, 1000000.00, 100000.00, 10000000.00, 1, 1, 1, 1, 1, 1);
        slaConfiguration.setCreatedBy("s2024726");
        slaConfiguration.setCreatedDate(new Date());
        slaConfigurationService.save(slaConfiguration);
    }

    @Test
    public void testScenarioD() {
        UserRole administratorRole = userRoleService.findByDescription("Administrator");
        User capturerUser1 = BootStrapHelper.getUser(administratorRole, "s2024726", "South Africa", "Vongani", "Maluleke", "Vongani Maluleke", "VM", "9110105141088", "0669654428", "vongani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser1.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CRSC_OFFICE));
        userService.save(capturerUser1);

        UserRole executiveRole = userRoleService.findByDescription("Executive");
        User capturerUser2 = BootStrapHelper.getUser(executiveRole, "s2026080", "South Africa", "Andile", "Qumbisa", "Andile Qumbisa", "AQ", "9110105141088", "0669654428", "vongani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser2.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        User persistentExecutiveUser = userService.save(capturerUser2);

        UserRole seniorManagerRole = userRoleService.findByDescription("Senior Manager");
        User capturerUser3 = BootStrapHelper.getUser(seniorManagerRole, "s2028873", "South Africa", "Amogelang", "Phangisa", "Amogelang Phangisa", "AP", "9110105141088", "0669654428", "vongani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser3.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        capturerUser3.setManager(persistentExecutiveUser);
        User persistentSeniorManager = userService.save(capturerUser3);

        UserRole groupManagerRole = userRoleService.findByDescription("Group Manager");
        User capturerUser4 = BootStrapHelper.getUser(groupManagerRole, "s2028398", "South Africa", "Terry", "Ramurembiwa", "Terry Ramurembiwa", "TM", "9210105141088", "0669652328", "khensani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser4.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        capturerUser4.setManager(persistentSeniorManager);
        User persistentGroupManager = userService.save(capturerUser4);

        UserRole ciOpsManagerRole = userRoleService.findByDescription("CI Ops Manager");
        User capturerUser5 = BootStrapHelper.getUser(ciOpsManagerRole, "s2026064", "South Africa", "Tebogo", "Makau", "Tebogo Makau", "TM", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser5.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        capturerUser5.setManager(persistentGroupManager);
        User persistentCIOPSMangerUser = userService.save(capturerUser5);

        UserRole crcsOpsManagerRole = userRoleService.findByDescription("CRCS Ops Manager");
        User capturerUser6 = BootStrapHelper.getUser(crcsOpsManagerRole, "s2026015", "South Africa", "Mpelwane", "Morudi", "Mpelwane Morudi", "MM", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser6.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CRSC_OFFICE));
        capturerUser6.setManager(persistentGroupManager);
        User crcsOpsManagerUser = userService.save(capturerUser6);

        UserRole reviewerRole = userRoleService.findByDescription("CRCS Reviewer");
        User capturerUser7 = BootStrapHelper.getUser(reviewerRole, "s2027556", "South Africa", "Zandile", "Dube", "Zandile Dube", "ZD", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser7.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CRSC_OFFICE));
        capturerUser7.setManager(crcsOpsManagerUser);
        userService.save(capturerUser7);

        UserRole crcsOpsSpecialistRole = userRoleService.findByDescription("CRCS Ops Specialist");
        User capturerUser8 = BootStrapHelper.getUser(crcsOpsSpecialistRole, "s2030008", "South Africa", "Treswell", "Kgoedi", "Treswell Kgoedi", "TK", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser8.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CRSC_OFFICE));
        capturerUser8.setManager(crcsOpsManagerUser);
        userService.save(capturerUser8);

        UserRole seniorAuditorRole = userRoleService.findByDescription("Senior Auditor");
        User capturerUser9 = BootStrapHelper.getUser(seniorAuditorRole, "s2024727", "South Africa", "Tshepo", "Mahlangu", "Tshepo Mahlangu", "TM", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser9.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        capturerUser9.setManager(persistentCIOPSMangerUser);
        userService.save(capturerUser9);

        UserRole ciAuditorRole = userRoleService.findByDescription("CI Auditor");
        User capturerUser10 = BootStrapHelper.getUser(ciAuditorRole, "s1020246", "South Africa", "Ramatladi", "Kopotja", "Ramatladi Kopotja", "RM", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser10.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        capturerUser10.setManager(persistentCIOPSMangerUser);
        userService.save(capturerUser10);

        UserRole crcsQualityAssurerRole = userRoleService.findByDescription("CRCS Quality Assurer");
        User capturerUser11 = BootStrapHelper.getUser(crcsQualityAssurerRole, "s2017794", "South Africa", "Fortunate", "Mashinini", "Fortunate Mashinini", "FM", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser11.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CRSC_OFFICE));
        capturerUser11.setManager(crcsOpsManagerUser);
        userService.save(capturerUser11);

        UserRole sarsInternalUserRole = userRoleService.findByDescription("SARS internal User");
        User capturerUser12 = BootStrapHelper.getUser(sarsInternalUserRole, "s2022896", "South Africa", "Matsatsi", "Ntsoko", "Matsatsi Ntsoko", "MN", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser12.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CRSC_OFFICE));
        capturerUser12.setManager(crcsOpsManagerUser);
        userService.save(capturerUser12);

        UserRole managementRole = userRoleService.findByDescription("Management");
        User capturerUser13 = BootStrapHelper.getUser(managementRole, "s2026055", "South Africa", "Sibongile", "Mtimkhulu", "Sibongile Mtimkhulu", "SM", "9310105141088", "0669655428", "tsakani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser13.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
        //capturerUser13.setManager(crcsOpsManagerUser);
        userService.save(capturerUser13);
    }

    @Test
    public void testScenarioE() {
        User user = userService.findBySid("s2024726");
        HsChapterRisk hsChapterRisk = BootStrapHelper.getHsChapterRisk(user, "Live Animals");
        hsChapterRiskService.save(hsChapterRisk);

        HsChapterRisk hsChapterRisk1 = BootStrapHelper.getHsChapterRisk(user, "Meat and Edible Meat Offal");
        hsChapterRiskService.save(hsChapterRisk1);

        HsChapterRisk hsChapterRisk2 = BootStrapHelper.getHsChapterRisk(user, "Fish and Crustaceans, Molluscs and Other Aquatic Invertebrates");
        hsChapterRiskService.save(hsChapterRisk2);

        HsChapterRisk hsChapterRisk3 = BootStrapHelper.getHsChapterRisk(user, "Dairy Produce; Birds' Eggs; Natural Honey; Edible Products of Animal Origin, Not Elsewhere Specified or Included");
        hsChapterRiskService.save(hsChapterRisk3);

        HsChapterRisk hsChapterRisk4 = BootStrapHelper.getHsChapterRisk(user, "Products of Animal Origin, Not Elsewhere Specified or Included");
        hsChapterRiskService.save(hsChapterRisk4);

        HsChapterRisk hsChapterRisk5 = BootStrapHelper.getHsChapterRisk(user, "Live Trees and Other Plants; Bulbs, Roots and The Like; Cut Flowers and Ornamental Foliage");
        hsChapterRiskService.save(hsChapterRisk5);

        HsChapterRisk hsChapterRisk6 = BootStrapHelper.getHsChapterRisk(user, "Live Trees and Other Plants; Bulbs, Roots and The Like; Cut Flowers and Ornamental Foliage");
        hsChapterRiskService.save(hsChapterRisk6);

        HsChapterRisk hsChapterRisk7 = BootStrapHelper.getHsChapterRisk(user, "Edible Vegetables and Certain Roots and Tubers");
        hsChapterRiskService.save(hsChapterRisk7);

        HsChapterRisk hsChapterRisk8 = BootStrapHelper.getHsChapterRisk(user, "Edible Fruit and Nuts; Peel of Citrus Fruit or Melons");
        hsChapterRiskService.save(hsChapterRisk8);

        HsChapterRisk hsChapterRisk9 = BootStrapHelper.getHsChapterRisk(user, "Coffee, Tea, Maté and Spices");
        hsChapterRiskService.save(hsChapterRisk9);

        HsChapterRisk hsChapterRisk10 = BootStrapHelper.getHsChapterRisk(user, "Cereals");
        hsChapterRiskService.save(hsChapterRisk10);

        HsChapterRisk hsChapterRisk11 = BootStrapHelper.getHsChapterRisk(user, "Products of The Milling Industry; Malt; Starches; Inulin; Wheat Gluten");
        hsChapterRiskService.save(hsChapterRisk11);

        HsChapterRisk hsChapterRisk12 = BootStrapHelper.getHsChapterRisk(user, "Oil Seeds and Oleaginous Fruits; Miscellaneous Grains, Seeds and Fruit; Industrial or Medicinal    Plants; Straw and Fodder");
        hsChapterRiskService.save(hsChapterRisk12);

        HsChapterRisk hsChapterRisk13 = BootStrapHelper.getHsChapterRisk(user, "Lac; Gums, Resins and Other Vegetable Saps and Extracts");
        hsChapterRiskService.save(hsChapterRisk13);

        HsChapterRisk hsChapterRisk14 = BootStrapHelper.getHsChapterRisk(user, "Vegetable Plaiting Materials; Vegetable Products Not Elsewhere Specified or Included");
        hsChapterRiskService.save(hsChapterRisk14);

        HsChapterRisk hsChapterRisk15 = BootStrapHelper.getHsChapterRisk(user, "Animal or Vegetable Fats and Oils and Their Cleavage Products; Prepared Edible Fats; Animal or Vegetable Waxes");
        hsChapterRiskService.save(hsChapterRisk15);

        HsChapterRisk hsChapterRisk16 = BootStrapHelper.getHsChapterRisk(user, "Preparations of Meat, Of Fish or of Crustaceans, Molluscs or Other Aquatic Invertebrates");
        hsChapterRiskService.save(hsChapterRisk16);

        HsChapterRisk hsChapterRisk17 = BootStrapHelper.getHsChapterRisk(user, "Sugars and Sugar Confectionery");
        hsChapterRiskService.save(hsChapterRisk17);

        HsChapterRisk hsChapterRisk18 = BootStrapHelper.getHsChapterRisk(user, "Cocoa and Cocoa Preparations");
        hsChapterRiskService.save(hsChapterRisk18);

        HsChapterRisk hsChapterRisk19 = BootStrapHelper.getHsChapterRisk(user, "Preparations of Cereals, Flour, Starch or Milk; Pastrycooks Products");
        hsChapterRiskService.save(hsChapterRisk19);

        HsChapterRisk hsChapterRisk20 = BootStrapHelper.getHsChapterRisk(user, "Preparations of Vegetables, Fruit, Nuts or Other Parts of Plants");
        hsChapterRiskService.save(hsChapterRisk20);

        HsChapterRisk hsChapterRisk21 = BootStrapHelper.getHsChapterRisk(user, "Miscellaneous Edible Preparations");
        hsChapterRiskService.save(hsChapterRisk21);

        HsChapterRisk hsChapterRisk22 = BootStrapHelper.getHsChapterRisk(user, "Beverages, Spirits and Vinegar");
        hsChapterRiskService.save(hsChapterRisk22);

        HsChapterRisk hsChapterRisk23 = BootStrapHelper.getHsChapterRisk(user, "Residues and Waste from The Food Industries; Prepared Animal Fodder");
        hsChapterRiskService.save(hsChapterRisk23);

        HsChapterRisk hsChapterRisk24 = BootStrapHelper.getHsChapterRisk(user, "Tobacco and Manufactured Tobacco Substitutes");
        hsChapterRiskService.save(hsChapterRisk24);

        HsChapterRisk hsChapterRisk25 = BootStrapHelper.getHsChapterRisk(user, "Salt; Sulphur; Earths and Stone; Plastering Materials, Lime and Cement");
        hsChapterRiskService.save(hsChapterRisk25);

        HsChapterRisk hsChapterRisk26 = BootStrapHelper.getHsChapterRisk(user, "Ores, Slag and Ash");
        hsChapterRiskService.save(hsChapterRisk26);

        HsChapterRisk hsChapterRisk27 = BootStrapHelper.getHsChapterRisk(user, "Mineral Fuels, Mineral Oils and Products of Their Distillation; Bituminous Substances; Mineral Waxes");
        hsChapterRiskService.save(hsChapterRisk27);

        HsChapterRisk hsChapterRisk28 = BootStrapHelper.getHsChapterRisk(user, "Inorganic Chemicals; Organic or Inorganic Compounds of Precious Metals, Of Rare-Earth Metals, Of Radioactive Elements or of Isotopes");
        hsChapterRiskService.save(hsChapterRisk28);

        HsChapterRisk hsChapterRisk29 = BootStrapHelper.getHsChapterRisk(user, "Organic Chemicals");
        hsChapterRiskService.save(hsChapterRisk29);

        HsChapterRisk hsChapterRisk30 = BootStrapHelper.getHsChapterRisk(user, "Pharmaceutical Products");
        hsChapterRiskService.save(hsChapterRisk30);

        HsChapterRisk hsChapterRisk31 = BootStrapHelper.getHsChapterRisk(user, "Fertilizers");
        hsChapterRiskService.save(hsChapterRisk31);

        HsChapterRisk hsChapterRisk32 = BootStrapHelper.getHsChapterRisk(user, "Tanning or Dyeing Extracts; Tannins and Their Derivatives; Dyes, Pigments and Other Colouring Matter; Paints and Varnishes; Putty and Other Mastics; Inks");
        hsChapterRiskService.save(hsChapterRisk32);

        HsChapterRisk hsChapterRisk33 = BootStrapHelper.getHsChapterRisk(user, "Essential Oils and Resinoids; Perfumery, Cosmetic or Toilet Preparations");
        hsChapterRiskService.save(hsChapterRisk33);

        HsChapterRisk hsChapterRisk34 = BootStrapHelper.getHsChapterRisk(user, "Soap, Organic Surface-Active Agents, Washing Preparations, Lubricating Preparations, Artificial Waxes, Prepared Waxes, Polishing or Scouring Preparations, Candles and Similar Articles, Modelling Pastes, \"Dental Waxes\" And Dental Preparations with A Basis of Plaster");
        hsChapterRiskService.save(hsChapterRisk34);

        HsChapterRisk hsChapterRisk35 = BootStrapHelper.getHsChapterRisk(user, "Explosives; Pyrotechnic Products; Matches; Pyrophoric Alloys; Certain Combustible Preparations");
        hsChapterRiskService.save(hsChapterRisk35);

        HsChapterRisk hsChapterRisk36 = BootStrapHelper.getHsChapterRisk(user, "Photographic or Cinematographic Goods");
        hsChapterRiskService.save(hsChapterRisk36);

        HsChapterRisk hsChapterRisk37 = BootStrapHelper.getHsChapterRisk(user, "Miscellaneous Chemical Products");
        hsChapterRiskService.save(hsChapterRisk37);

        HsChapterRisk hsChapterRisk38 = BootStrapHelper.getHsChapterRisk(user, "Plastics and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk38);

        HsChapterRisk hsChapterRisk39 = BootStrapHelper.getHsChapterRisk(user, "Rubber and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk39);

        HsChapterRisk hsChapterRisk40 = BootStrapHelper.getHsChapterRisk(user, "Raw Hides and Skins (Other Than Furskins) And Leather");
        hsChapterRiskService.save(hsChapterRisk40);

        HsChapterRisk hsChapterRisk41 = BootStrapHelper.getHsChapterRisk(user, "Articles of Leather; Saddlery and Harness; Travel Goods, Handbags and Similar Containers; Articles of Animal Gut (Other Than Silk-Worm Gut)");
        hsChapterRiskService.save(hsChapterRisk41);

        HsChapterRisk hsChapterRisk42 = BootStrapHelper.getHsChapterRisk(user, "Furskins and Artificial Fur; Manufactures Thereof");
        hsChapterRiskService.save(hsChapterRisk42);

        HsChapterRisk hsChapterRisk43 = BootStrapHelper.getHsChapterRisk(user, "Wood and Articles of Wood; Wood Charcoal");
        hsChapterRiskService.save(hsChapterRisk43);

        HsChapterRisk hsChapterRisk44 = BootStrapHelper.getHsChapterRisk(user, "Cork and Articles of Cork");
        hsChapterRiskService.save(hsChapterRisk44);

        HsChapterRisk hsChapterRisk45 = BootStrapHelper.getHsChapterRisk(user, "Manufactures of Straw, Of Esparto or of Other Plaiting Materials; Basketware and Wickerwork");
        hsChapterRiskService.save(hsChapterRisk45);

        HsChapterRisk hsChapterRisk46 = BootStrapHelper.getHsChapterRisk(user, "Pulp of Wood or of Other Fibrous Cellulosic Material; Recovered (Waste and Scrap) Paper or Paperboard");
        hsChapterRiskService.save(hsChapterRisk46);

        HsChapterRisk hsChapterRisk47 = BootStrapHelper.getHsChapterRisk(user, "Paper and Paperboard; Articles of Paper Pulp, Of Paper or of Paperboard");
        hsChapterRiskService.save(hsChapterRisk47);

        HsChapterRisk hsChapterRisk48 = BootStrapHelper.getHsChapterRisk(user, "Printed Books, Newspapers, Pictures and Other Products of the Printing Industry; Manuscripts, Typescripts and Plans");
        hsChapterRiskService.save(hsChapterRisk48);

        HsChapterRisk hsChapterRisk49 = BootStrapHelper.getHsChapterRisk(user, "Silk");
        hsChapterRiskService.save(hsChapterRisk49);

        HsChapterRisk hsChapterRisk50 = BootStrapHelper.getHsChapterRisk(user, "Wool, Fine or Coarse Animal Hair; Horsehair Yarn and Woven Fabric");
        hsChapterRiskService.save(hsChapterRisk50);

        HsChapterRisk hsChapterRisk51 = BootStrapHelper.getHsChapterRisk(user, "Cotton");
        hsChapterRiskService.save(hsChapterRisk51);

        HsChapterRisk hsChapterRisk52 = BootStrapHelper.getHsChapterRisk(user, "Other Vegetable Textile Fibres; Paper Yarn and Woven Fabrics of Paper Yarn");
        hsChapterRiskService.save(hsChapterRisk52);

        HsChapterRisk hsChapterRisk53 = BootStrapHelper.getHsChapterRisk(user, "Man-Made Filaments; Strip and The Like of Man-Made Textile Materials");
        hsChapterRiskService.save(hsChapterRisk53);

        HsChapterRisk hsChapterRisk54 = BootStrapHelper.getHsChapterRisk(user, "Man-Made Staple Fibres");
        hsChapterRiskService.save(hsChapterRisk54);

        HsChapterRisk hsChapterRisk55 = BootStrapHelper.getHsChapterRisk(user, "Wadding, Felt and Nonwovens; Special Yarns; Twine, Cordage, Ropes and Cables and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk55);

        HsChapterRisk hsChapterRisk56 = BootStrapHelper.getHsChapterRisk(user, "Carpets and Other Textile Floor Coverings");
        hsChapterRiskService.save(hsChapterRisk56);

        HsChapterRisk hsChapterRisk57 = BootStrapHelper.getHsChapterRisk(user, "Special Woven Fabrics; Tufted Textile Fabrics; Lace; Tapestries; Trimmings; Embroidery");
        hsChapterRiskService.save(hsChapterRisk57);

        HsChapterRisk hsChapterRisk58 = BootStrapHelper.getHsChapterRisk(user, "Impregnated, Coated, Covered or Laminated Textile Fabrics; Textile Articles of a Kind Suitable for Industrial Use");
        hsChapterRiskService.save(hsChapterRisk58);

        HsChapterRisk hsChapterRisk59 = BootStrapHelper.getHsChapterRisk(user, "Knitted or Crocheted Fabrics");
        hsChapterRiskService.save(hsChapterRisk59);

        HsChapterRisk hsChapterRisk60 = BootStrapHelper.getHsChapterRisk(user, "Articles of Apparel and Clothing Accessories, Knitted or Crocheted");
        hsChapterRiskService.save(hsChapterRisk60);

        HsChapterRisk hsChapterRisk61 = BootStrapHelper.getHsChapterRisk(user, "Articles of Apparel and Clothing Accessories, Not Knitted or Crocheted");
        hsChapterRiskService.save(hsChapterRisk61);

        HsChapterRisk hsChapterRisk62 = BootStrapHelper.getHsChapterRisk(user, "Other Made Up Textile Articles; Sets; Worn Clothing and Worn Textile Articles; Rags");
        hsChapterRiskService.save(hsChapterRisk62);

        HsChapterRisk hsChapterRisk63 = BootStrapHelper.getHsChapterRisk(user, "Footwear, Gaiters and The Like; Parts of Such Articles");
        hsChapterRiskService.save(hsChapterRisk63);

        HsChapterRisk hsChapterRisk64 = BootStrapHelper.getHsChapterRisk(user, "Headgear and Parts Thereof");
        hsChapterRiskService.save(hsChapterRisk64);

        HsChapterRisk hsChapterRisk65 = BootStrapHelper.getHsChapterRisk(user, "Umbrellas, Sun Umbrellas, Walking-Sticks, Seat-Sticks, Whips, Riding-Crops and Parts Thereof");
        hsChapterRiskService.save(hsChapterRisk65);

        HsChapterRisk hsChapterRisk66 = BootStrapHelper.getHsChapterRisk(user, "Prepared Feathers and Down and Articles Made of Feathers or of Down; Artificial Flowers; Articles of Human Hair");
        hsChapterRiskService.save(hsChapterRisk66);

        HsChapterRisk hsChapterRisk67 = BootStrapHelper.getHsChapterRisk(user, "Articles of Stone, Plaster, Cement, Asbestos, Mica or Similar Materials");
        hsChapterRiskService.save(hsChapterRisk67);

        HsChapterRisk hsChapterRisk68 = BootStrapHelper.getHsChapterRisk(user, "Ceramic Products");
        hsChapterRiskService.save(hsChapterRisk68);

        HsChapterRisk hsChapterRisk69 = BootStrapHelper.getHsChapterRisk(user, "Glass and Glassware");
        hsChapterRiskService.save(hsChapterRisk69);

        HsChapterRisk hsChapterRisk70 = BootStrapHelper.getHsChapterRisk(user, "Natural or Cultured Pearls, Precious or Semi-Precious Stones, Precious Metals, Metals Clad with Precious Metal and Articles Thereof; Imitation Jewelery; Coin");
        hsChapterRiskService.save(hsChapterRisk70);

        HsChapterRisk hsChapterRisk71 = BootStrapHelper.getHsChapterRisk(user, "Iron and Steel");
        hsChapterRiskService.save(hsChapterRisk71);

        HsChapterRisk hsChapterRisk72 = BootStrapHelper.getHsChapterRisk(user, "Articles of Iron or Steel");
        hsChapterRiskService.save(hsChapterRisk72);

        HsChapterRisk hsChapterRisk73 = BootStrapHelper.getHsChapterRisk(user, "Copper and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk73);

        HsChapterRisk hsChapterRisk74 = BootStrapHelper.getHsChapterRisk(user, "Nickel and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk74);

        HsChapterRisk hsChapterRisk75 = BootStrapHelper.getHsChapterRisk(user, "Aluminium and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk75);

        HsChapterRisk hsChapterRisk76 = BootStrapHelper.getHsChapterRisk(user, "Lead and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk76);

        HsChapterRisk hsChapterRisk77 = BootStrapHelper.getHsChapterRisk(user, "Zinc and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk77);

        HsChapterRisk hsChapterRisk78 = BootStrapHelper.getHsChapterRisk(user, "Tin and Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk78);

        HsChapterRisk hsChapterRisk79 = BootStrapHelper.getHsChapterRisk(user, "Other Base Metals; Cermets; Articles Thereof");
        hsChapterRiskService.save(hsChapterRisk79);

        HsChapterRisk hsChapterRisk80 = BootStrapHelper.getHsChapterRisk(user, "Tools, Implements, Cutlery, Spoons and Forks, Of Base Metal; Parts Thereof of Base Metal");
        hsChapterRiskService.save(hsChapterRisk80);

        HsChapterRisk hsChapterRisk81 = BootStrapHelper.getHsChapterRisk(user, "Miscellaneous Articles of Base Metal");
        hsChapterRiskService.save(hsChapterRisk81);

        HsChapterRisk hsChapterRisk82 = BootStrapHelper.getHsChapterRisk(user, "Nuclear Reactors, Boilers, Machinery and Mechanical Appliances; Parts Thereof");
        hsChapterRiskService.save(hsChapterRisk82);

        HsChapterRisk hsChapterRisk83 = BootStrapHelper.getHsChapterRisk(user, "Electrical Machinery and Equipment and Parts Thereof; Sound Recorders and Reproducers, Television Image and Sound Recorders and Reproducers, And Parts and Accessories of Such Articles");
        hsChapterRiskService.save(hsChapterRisk83);

        HsChapterRisk hsChapterRisk84 = BootStrapHelper.getHsChapterRisk(user, "Live Trees and Other Plants; Bulbs, Roots and The Like; Cut Flowers and Ornamental Foliage");
        hsChapterRiskService.save(hsChapterRisk84);

        HsChapterRisk hsChapterRisk85 = BootStrapHelper.getHsChapterRisk(user, "Railway or Tramway Locomotives, Rolling-Stock and Parts Thereof; Railway or Tramway Track Fixtures and Fittings and Parts Thereof; Mechanical (Including Electro-Mechanical) Traffic Signalling Equipment of All Kinds");
        hsChapterRiskService.save(hsChapterRisk85);

        HsChapterRisk hsChapterRisk86 = BootStrapHelper.getHsChapterRisk(user, "Vehicles (Excluding Railway or Tramway Rolling-Stock), And Parts and Accessories Thereof");
        hsChapterRiskService.save(hsChapterRisk86);

        HsChapterRisk hsChapterRisk87 = BootStrapHelper.getHsChapterRisk(user, "Aircraft, Spacecraft, And Parts Thereof");
        hsChapterRiskService.save(hsChapterRisk87);

        HsChapterRisk hsChapterRisk88 = BootStrapHelper.getHsChapterRisk(user, "Ships, Boats and Floating Structures");
        hsChapterRiskService.save(hsChapterRisk88);

        HsChapterRisk hsChapterRisk89 = BootStrapHelper.getHsChapterRisk(user, "Optical, Photographic, Cinematographic, Measuring, Checking, Precision, Medical or Surgical Instruments and Apparatus; Parts and Accessories Thereof");
        hsChapterRiskService.save(hsChapterRisk89);

        HsChapterRisk hsChapterRisk90 = BootStrapHelper.getHsChapterRisk(user, "Clocks and Watches and Parts Thereof");
        hsChapterRiskService.save(hsChapterRisk90);

        HsChapterRisk hsChapterRisk91 = BootStrapHelper.getHsChapterRisk(user, "Musical Instruments; Part and Accessories of Such Articles");
        hsChapterRiskService.save(hsChapterRisk91);

        HsChapterRisk hsChapterRisk92 = BootStrapHelper.getHsChapterRisk(user, "Arms and Ammunition; Parts and Accessories Thereof");
        hsChapterRiskService.save(hsChapterRisk92);

        HsChapterRisk hsChapterRisk93 = BootStrapHelper.getHsChapterRisk(user, "Furniture; Bedding, Mattresses, Mattress Supports, Cushions and Similar Stuffed Furnishings; Lamps and Lighting Fittings, Not Elsewhere Specified or Included; Illuminated Signs, Illuminated Name-Plates and The Like; Prefabricated Buildings");
        hsChapterRiskService.save(hsChapterRisk93);

        HsChapterRisk hsChapterRisk94 = BootStrapHelper.getHsChapterRisk(user, "Toys, Games and Sports Requisites; Parts and Accessories Thereof");
        hsChapterRiskService.save(hsChapterRisk94);

        HsChapterRisk hsChapterRisk95 = BootStrapHelper.getHsChapterRisk(user, "Miscellaneous Manufactured Articles");
        hsChapterRiskService.save(hsChapterRisk95);

        HsChapterRisk hsChapterRisk96 = BootStrapHelper.getHsChapterRisk(user, "Works of Art, Collectors Pieces and Antioues");
        hsChapterRiskService.save(hsChapterRisk96);

        HsChapterRisk hsChapterRisk97 = BootStrapHelper.getHsChapterRisk(user, "Original Equipment Components");
        hsChapterRiskService.save(hsChapterRisk97);

        HsChapterRisk hsChapterRisk98 = BootStrapHelper.getHsChapterRisk(user, "Miscellaneous Classification Provisions");
        hsChapterRiskService.save(hsChapterRisk98);
    }

    @Test
    public void testScenarioF() {
        User user = userService.findBySid("s2023456");
        RelevantMaterial relevantMaterial = BootStrapHelper.getRelevantMaterial(user, "Contract of sale, other transactions/agreements/license and royalty agreements");
        relevantMaterialService.save(relevantMaterial);

        RelevantMaterial relevantMaterial1 = BootStrapHelper.getRelevantMaterial(user, "Transfer pricing policy between trade company and subsidiaries");
        relevantMaterialService.save(relevantMaterial1);

        RelevantMaterial relevantMaterial2 = BootStrapHelper.getRelevantMaterial(user, "Invoices and proof of payments");
        relevantMaterialService.save(relevantMaterial2);

        RelevantMaterial relevantMaterial3 = BootStrapHelper.getRelevantMaterial(user, "Customs Commercial Invoices");
        relevantMaterialService.save(relevantMaterial3);

        RelevantMaterial relevantMaterial4 = BootStrapHelper.getRelevantMaterial(user, "Customs Import documents");
        relevantMaterialService.save(relevantMaterial4);

        RelevantMaterial relevantMaterial5 = BootStrapHelper.getRelevantMaterial(user, "Packing slips");
        relevantMaterialService.save(relevantMaterial5);

        RelevantMaterial relevantMaterial6 = BootStrapHelper.getRelevantMaterial(user, "Customs declarations");
        relevantMaterialService.save(relevantMaterial6);

        RelevantMaterial relevantMaterial7 = BootStrapHelper.getRelevantMaterial(user, "Customs release notification (import)");
        relevantMaterialService.save(relevantMaterial7);

        RelevantMaterial relevantMaterial8 = BootStrapHelper.getRelevantMaterial(user, "Manufacturing, receiving, and production records");
        relevantMaterialService.save(relevantMaterial8);

        RelevantMaterial relevantMaterial9 = BootStrapHelper.getRelevantMaterial(user, "Customs Worksheets");
        relevantMaterialService.save(relevantMaterial9);

        RelevantMaterial relevantMaterial10 = BootStrapHelper.getRelevantMaterial(user, "Packing list");
        relevantMaterialService.save(relevantMaterial10);

        RelevantMaterial relevantMaterial11 = BootStrapHelper.getRelevantMaterial(user, "Air way Bill");
        relevantMaterialService.save(relevantMaterial11);

        RelevantMaterial relevantMaterial12 = BootStrapHelper.getRelevantMaterial(user, "Bills of entries");
        relevantMaterialService.save(relevantMaterial12);

        RelevantMaterial relevantMaterial13 = BootStrapHelper.getRelevantMaterial(user, "Suppliers Invoices");
        relevantMaterialService.save(relevantMaterial13);

        RelevantMaterial relevantMaterial14 = BootStrapHelper.getRelevantMaterial(user, "Credit Note");
        relevantMaterialService.save(relevantMaterial14);

        RelevantMaterial relevantMaterial15 = BootStrapHelper.getRelevantMaterial(user, "Purchase Orders");
        relevantMaterialService.save(relevantMaterial15);

        RelevantMaterial relevantMaterial16 = BootStrapHelper.getRelevantMaterial(user, "Worksheets");
        relevantMaterialService.save(relevantMaterial16);

        RelevantMaterial relevantMaterial17 = BootStrapHelper.getRelevantMaterial(user, "Certificate of Origin (if applicable)");
        relevantMaterialService.save(relevantMaterial17);

        RelevantMaterial relevantMaterial18 = BootStrapHelper.getRelevantMaterial(user, "Import Permits");
        relevantMaterialService.save(relevantMaterial18);

        RelevantMaterial relevantMaterial19 = BootStrapHelper.getRelevantMaterial(user, "Bill of Lading/Transport documents");
        relevantMaterialService.save(relevantMaterial19);

        RelevantMaterial relevantMaterial20 = BootStrapHelper.getRelevantMaterial(user, "Freight statement and proof of payment");
        relevantMaterialService.save(relevantMaterial20);

        RelevantMaterial relevantMaterial21 = BootStrapHelper.getRelevantMaterial(user, "Proof of payment to clearing agent");
        relevantMaterialService.save(relevantMaterial21);

        RelevantMaterial relevantMaterial22 = BootStrapHelper.getRelevantMaterial(user, "Clearing instructions");
        relevantMaterialService.save(relevantMaterial22);

        RelevantMaterial relevantMaterial23 = BootStrapHelper.getRelevantMaterial(user, "Product Literature");
        relevantMaterialService.save(relevantMaterial23);

        RelevantMaterial relevantMaterial24 = BootStrapHelper.getRelevantMaterial(user, "Other");
        relevantMaterialService.save(relevantMaterial24);
    }

    @Test
    public void testScenarioG() {
        User user = userService.findBySid("s2023456");
        Program program = BootStrapHelper.getProgram(user, "Tariff classification and end use");
        programService.save(program);

        Program program1 = BootStrapHelper.getProgram(user, "Origin (Tariff Treatment)");
        programService.save(program1);

        Program program2 = BootStrapHelper.getProgram(user, "Valuation and accounting of goods to SARS Customs");
        programService.save(program2);

        Program program3 = BootStrapHelper.getProgram(user, "Duty relief and duty suspension schemes");
        programService.save(program3);

        Program program4 = BootStrapHelper.getProgram(user, "Any other Customs related business");
        programService.save(program4);
    }

    @Test
    public void testScenarioH() {
       /* EmailNotification emailNotification = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_SAR_TO_QA, "This serves to notify you that case ", "has been sent to you for review on ", "by", "");
        emailNotificationService.save(emailNotification);

        EmailNotification emailNotification1 = BootStrapHelper.getEmailNotification(NotificationType.DISAPROVED_DISCARDED_CASE, "This serves to notify you that the discarded SAR case ", "has been disapproved and sent to you on ", "by", "");
        emailNotificationService.save(emailNotification1);

        EmailNotification emailNotification2 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_DUPLICATE_CASE_TO_QA, "This serves to notify you that case ", "has been sent to you for review on ", "by", "");
        emailNotificationService.save(emailNotification2);

        EmailNotification emailNotification3 = BootStrapHelper.getEmailNotification(NotificationType.COMPLETE_RA_STAGE_IN_CASE_OF_DUPLICATE_CASE, "This serves to notify you that case ", " allocated to you was updated with new noncompliance information. Please review the case and consider the updated noncompliance information.", "by", "");
        emailNotificationService.save(emailNotification3);

        EmailNotification emailNotification4 = BootStrapHelper.getEmailNotification(NotificationType.CASE_ALLOCATION, "This serves to notify you that case ", "allocated to you was updated with new noncompliance information. Please review the case and consider the updated noncompliance information.", "by", "");
        emailNotificationService.save(emailNotification4);

        EmailNotification emailNotification5 = BootStrapHelper.getEmailNotification(NotificationType.RA_APPROVAL_STAGE, "This serves to notify you that Case No.", "allocated to you and currently in the RA Approval stage was updated with new noncompliance information. Please review the case and the updated noncompliance details. If appropriate refer the case for rework in order for the updated noncompliance information to be considered in the risk assessment of the case.", "", "");
        emailNotificationService.save(emailNotification5);

        EmailNotification emailNotification6 = BootStrapHelper.getEmailNotification(NotificationType.ALLOCATED_INVENTORY_IN_CASE_OF_DUPLICATE_CASE, "This serves to notify you that case ", "allocated to you was updated with new noncompliance information. Please review the case and the updated noncompliance details ", "by", "");
        emailNotificationService.save(emailNotification6);

        EmailNotification emailNotification7 = BootStrapHelper.getEmailNotification(NotificationType.QA_TECHNICAL_REVIEW, "This serves to notify you that Case No. ", "allocated to you and currently in your Technical Review Cases inventory was updated with new noncompliance information. Please review the case and if appropriate refer the case for rework in order for the new noncompliance information to be considered in the risk assessment of the case.", "", "");
        emailNotificationService.save(emailNotification7);

        EmailNotification emailNotification8 = BootStrapHelper.getEmailNotification(NotificationType.CRCS_OPS_MANAGER_ALLOCATION, "This serves to notify you that case  ", "was allocated to you on ", "by", "");
        emailNotificationService.save(emailNotification8);

        EmailNotification emailNotification9 = BootStrapHelper.getEmailNotification(NotificationType.QA_ALLOCATION_SAR_CASE, "This serves to notify you that SAR case   ", "was allocated to you for Quality Assurance on ", "by", "");
        emailNotificationService.save(emailNotification9);

        EmailNotification emailNotification10 = BootStrapHelper.getEmailNotification(NotificationType.CRCS_OPS_SPECAILIST_REWORK, "This serves to notify you that SAR Case ", "has been sent to you for rework on  ", "by", "");
        emailNotificationService.save(emailNotification10);

        EmailNotification emailNotification11 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_RA_TO_CRCS_OPS_SPECIALIST, "This serves to notify you that the discarded RA/case ", "has been approved on  ", "by", "");
        emailNotificationService.save(emailNotification11);

        EmailNotification emailNotification12 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_SAR_TO_CRCS_OPS_SPECIALIST_FOR_REWORK, "This serves to notify you that the discarded SAR case    ", "has been sent back to you on  ", "by", "");
        emailNotificationService.save(emailNotification12);

        EmailNotification emailNotification13 = BootStrapHelper.getEmailNotification(NotificationType.PENDING_RA, "This serves to notify you that case   ", "has been pended on ", "", "");
        emailNotificationService.save(emailNotification13);

        EmailNotification emailNotification14 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_RA, "This serves to notify you that RA/case   ", "has been rejected on  ", "by", "");
        emailNotificationService.save(emailNotification14);

        EmailNotification emailNotification15 = BootStrapHelper.getEmailNotification(NotificationType.QA_REVIEW, "This serves to notify you that case    ", "has been sent to you for review on  ", "by", "");
        emailNotificationService.save(emailNotification15);

//        EmailNotification emailNotification16 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_SAR_TO_QA, "This serves to notify you that case ", "has been sent to you for review on ", "by", "");
//        emailNotificationService.save(emailNotification16);
        EmailNotification emailNotification17 = BootStrapHelper.getEmailNotification(NotificationType.UNPEND_CASE_BY_CRCS_OPS_SPECIALIST, "This serves to notify you that case  ", "has been un-pended on ", "", "");
        emailNotificationService.save(emailNotification17);

        EmailNotification emailNotification18 = BootStrapHelper.getEmailNotification(NotificationType.EXTEND_PEND_DATE_BY_CRCS_OPS_SPECIALIST, "This serves to notify you that case ", "has been pended on  ", "", "");
        emailNotificationService.save(emailNotification18);

        EmailNotification emailNotification19 = BootStrapHelper.getEmailNotification(NotificationType.SUBMITTED_RA, "This serves to notify you that case ", "has been sent to you for review on ", "by", "");
        emailNotificationService.save(emailNotification19);

        EmailNotification emailNotification20 = BootStrapHelper.getEmailNotification(NotificationType.CRCS_OPS_SPECAILIST_REWORK, "This serves to notify you that case ", "has been sent back to you for rework on  ", "by", "");
        emailNotificationService.save(emailNotification20);

        EmailNotification emailNotification21 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_CASE_FOR_CRCS_OPS_SPECIALIST, "This serves to notify you that case ", "has been rejected on  ", "by", "");
        emailNotificationService.save(emailNotification21);

        EmailNotification emailNotification22 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_QA_APPROVED_BY_OPS_MANAGER, "This serves to notify you that the rejected case  ", "has been approved on  ", "by", "");
        emailNotificationService.save(emailNotification22);

        EmailNotification emailNotification23 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_QA_DISAPPROVED_BY_OPS_MANAGER, "This serves to notify you that the rejected case ", "has been disapproved on ", "by", "");
        emailNotificationService.save(emailNotification23);

        EmailNotification emailNotification24 = BootStrapHelper.getEmailNotification(NotificationType.CRCS_OPS_SPECIALIST_REJECTED_CASE_REWORK, "This serves to notify you that the rejected case ", "has been sent to you for rework  ", "by", "");
        emailNotificationService.save(emailNotification24);

        EmailNotification emailNotification25 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_ALLOCATED, "This serves to notify you that case ", "was allocated to you on ", "by", "");
        emailNotificationService.save(emailNotification25);

        EmailNotification emailNotification26 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_RE_ALLOCATION, "This serves to notify you that case ", "was re-allocated to you on ", "by", "");
        emailNotificationService.save(emailNotification26);

        EmailNotification emailNotification27 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_REVIEW, "This serves to notify you that the audit plan for case ", "has been made available for you to review on ", "by", "");
        emailNotificationService.save(emailNotification27);

        EmailNotification emailNotification28 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_SENT_FOR_APPROVAL, "This serves to notify you that the reviewed audit plan for case ", "has been sent to you for approval on ", "by", "");
        emailNotificationService.save(emailNotification28);

        EmailNotification emailNotification30 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_SENT_FOR_RE_WORK, "This serves to notify you that audit plan for case  ", "has been sent back to you for rework on  ", "by", "");
        emailNotificationService.save(emailNotification30);

        EmailNotification emailNotification31 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_AUDIT_PLAN_TO_CRCS_OPS_MANAGER, "This serves to notify you that a rejected audit plan for case   ", "has been sent to you for approval or disapproval on ", "by", "");
        emailNotificationService.save(emailNotification31);

        EmailNotification emailNotification32 = BootStrapHelper.getEmailNotification(NotificationType.DISAPPROVED_REJECTED_AUDIT_PLAN_TO_SENIOR_AUDIT, "This serves to notify you that the rejected audit plan for case ", "has been disapproved on ", "by ", "");
        emailNotificationService.save(emailNotification32);

        EmailNotification emailNotification33 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_APPROVAL, "This serves to notify you that audit plan for case   ", "has been approved on ", "by ", "");
        emailNotificationService.save(emailNotification33);

        EmailNotification emailNotification34 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_SENT_FOR_RE_WORK, "This serves to notify you that audit plan for case   ", "has been sent back to you for rework on  ", "by ", "");
        emailNotificationService.save(emailNotification34);

        EmailNotification emailNotification35 = BootStrapHelper.getEmailNotification(NotificationType.DISAPPROVED_REJECTED_AUDIT_PLAN_CI_OPS_MANAGER, "This serves to notify you that the rejected audit plan for case ", "has been disapproved on ", "by ", "");
        emailNotificationService.save(emailNotification35);

        EmailNotification emailNotification36 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_AUDIT_PLAN_APPROVED_BY_CI_OPS_MANAGER, "This serves to notify you that the rejected audit plan for case ", "has been approved on ", "by ", "");
        emailNotificationService.save(emailNotification36);

        EmailNotification emailNotification37 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_SENT_FOR_1ST_TIER_APPROVAL, "This serves to notify you that the audit plan for case ", "has been sent to you for review on ", "by ", "");
        emailNotificationService.save(emailNotification37);

        EmailNotification emailNotification38 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORTING_SENT_TO_CI_OPS_MANAGER_FOR_APPROVAL, "This serves to notify you that the reviewed audit case report for ", "has been sent to you for approval on ", "by ", "");
        emailNotificationService.save(emailNotification38);

        EmailNotification emailNotification39 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORTING_SENT_FOR_REWORK, "This serves to notify you that audit case report case ", "has been sent back to you for rework on ", "by ", "");
        emailNotificationService.save(emailNotification39);

        EmailNotification emailNotification40 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORTING_REVIEWED, "This serves to notify you that audit case report for case ", "has been reviewed on ", "by ", "");
        emailNotificationService.save(emailNotification40);

        EmailNotification emailNotification41 = BootStrapHelper.getEmailNotification(NotificationType.APPROVED_AUDIT_REPORT, "This serves to notify you that audit case report for case ", "has been approved on ", "by ", "");
        emailNotificationService.save(emailNotification41);

        EmailNotification emailNotification42 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORTING_SENT_TO_GROUP_MANAGER_FOR_REVIEW, "This serves to notify you that the audit case report for case ", "has been sent to you for review on ", "by ", "");
        emailNotificationService.save(emailNotification42);

        EmailNotification emailNotification43 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORTING_REVIEW, "This serves to notify you that the audit report case for case ", "has been sent to you for review on ", "by ", "");
        emailNotificationService.save(emailNotification43);

        EmailNotification emailNotification44 = BootStrapHelper.getEmailNotification(NotificationType.DISAPPROVED_REJECTED_AUDIT_REPORTING, "This serves to notify you that the rejected audit case findings for case ", "has been disapproved on ", "by ", "");
        emailNotificationService.save(emailNotification44);

        EmailNotification emailNotification45 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_AUDIT_REPORTING_SENT_TO_CRCS_OPS_MANAGER, "This serves to notify you that the rejected audit case findings for case ", "has been sent to you to approve or disapprove on ", "by ", "");
        emailNotificationService.save(emailNotification45);

        EmailNotification emailNotification46 = BootStrapHelper.getEmailNotification(NotificationType.ACCEPTED_AUDIT_REPORTING_SENT_FOR_APPROVAL, "This serves to notify you that the accepted audit reporting for case ", "has been sent to you for approval on ", "by ", "");
        emailNotificationService.save(emailNotification46);

        EmailNotification emailNotification47 = BootStrapHelper.getEmailNotification(NotificationType.REVIEW_AUDIT_REPORTING_LETTER, "This serves to notify you that the audit reporting and / or audit letters for case ", "has been made available for you to review on ", "by ", "");
        emailNotificationService.save(emailNotification47);

        EmailNotification emailNotification48 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORTING_SENT_FOR_DEBT_MANAGEMENT, "This serves to inform you that a customs case ", "", "by ", "");
        emailNotificationService.save(emailNotification48);

        EmailNotification emailNotification49 = BootStrapHelper.getEmailNotification(NotificationType.EXTENDED_LOI_DATE, "This serves to notify you that the LOI due date for case", "has been extended to ", "by ", "");
        emailNotificationService.save(emailNotification49);

        EmailNotification emailNotification50 = BootStrapHelper.getEmailNotification(NotificationType.DEBT_MANAGEMENT_SENT_FOR_CLOSURE, "This serves to notify you that the Debt Management case", "has been sent to your for closure on ", "by ", "");
        emailNotificationService.save(emailNotification50);

        EmailNotification emailNotification51 = BootStrapHelper.getEmailNotification(NotificationType.CASE_ALLOCATION, "This serves to notify you that case ", " allocated to you was updated with new noncompliance information.  ", "Please review the case and consider the updated noncompliance information. ", " by");
        emailNotificationService.save(emailNotification51);*/
    }

    @Ignore
    @Test
    public void testScenarioI() {
        SuspiciousCase suspiciousCase1 = BootStrapHelper.getSarCase("s2022896", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase1.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C00652024", "s2022896"));
        suspiciousCase1.getSarCase().setCustomExciseCode("C00652024");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase1.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser1 = new SarCaseUser();
        sarCaseUser1.setCreatedBy("s2022896");
        sarCaseUser1.setCreatedDate(new Date());
        sarCaseUser1.setUser(userService.findBySid("s2022896"));
        suspiciousCase1.addSarCaseUser(sarCaseUser1);
        suspiciousCase1.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase1);

        SuspiciousCase suspiciousCase2 = BootStrapHelper.getSarCase("s2022896", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase2.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C21916754", "s2022896"));
        suspiciousCase2.getSarCase().setCustomExciseCode("C21916754");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase2.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser2 = new SarCaseUser();
        sarCaseUser2.setCreatedBy("s2022896");
        sarCaseUser2.setCreatedDate(new Date());
        sarCaseUser2.setUser(userService.findBySid("s2022896"));
        suspiciousCase2.addSarCaseUser(sarCaseUser2);
        suspiciousCase2.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase2);

        SuspiciousCase suspiciousCase3 = BootStrapHelper.getSarCase("s2026015", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase3.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C00308002", "s2026015"));
        suspiciousCase3.getSarCase().setCustomExciseCode("C00308002");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase3.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser3 = new SarCaseUser();
        sarCaseUser3.setCreatedBy("s2022896");
        sarCaseUser3.setCreatedDate(new Date());
        sarCaseUser3.setUser(userService.findBySid("s2022896"));
        suspiciousCase3.addSarCaseUser(sarCaseUser3);
        suspiciousCase3.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase3);

        SuspiciousCase suspiciousCase4 = BootStrapHelper.getSarCase("s2026015", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase4.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C20575030", "s2026015"));
        suspiciousCase4.getSarCase().setCustomExciseCode("C20575030");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase4.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser4 = new SarCaseUser();
        sarCaseUser4.setCreatedBy("s2022896");
        sarCaseUser4.setCreatedDate(new Date());
        sarCaseUser4.setUser(userService.findBySid("s2022896"));
        suspiciousCase4.addSarCaseUser(sarCaseUser4);
        suspiciousCase4.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase4);

        SuspiciousCase suspiciousCase5 = BootStrapHelper.getSarCase("s2026015", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase5.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C00138092", "s2026015"));
        suspiciousCase5.getSarCase().setCustomExciseCode("C00138092");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase5.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser5 = new SarCaseUser();
        sarCaseUser5.setCreatedBy("s2022896");
        sarCaseUser5.setCreatedDate(new Date());
        sarCaseUser5.setUser(userService.findBySid("s2022896"));
        suspiciousCase5.addSarCaseUser(sarCaseUser5);
        suspiciousCase5.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase5);

        SuspiciousCase suspiciousCase6 = BootStrapHelper.getSarCase("s2030008", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase6.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C21488882", "s2030008"));
        suspiciousCase6.getSarCase().setCustomExciseCode("C21488882");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase6.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser6 = new SarCaseUser();
        sarCaseUser6.setCreatedBy("s2022896");
        sarCaseUser6.setCreatedDate(new Date());
        sarCaseUser6.setUser(userService.findBySid("s2022896"));
        suspiciousCase6.addSarCaseUser(sarCaseUser6);
        suspiciousCase6.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase6);

        SuspiciousCase suspiciousCase7 = BootStrapHelper.getSarCase("s2030008", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase7.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C00456928", "s2030008"));
        suspiciousCase7.getSarCase().setCustomExciseCode("C00456928");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase7.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser7 = new SarCaseUser();
        sarCaseUser7.setCreatedBy("s2022896");
        sarCaseUser7.setCreatedDate(new Date());
        sarCaseUser7.setUser(userService.findBySid("s2022896"));
        suspiciousCase7.addSarCaseUser(sarCaseUser7);
        suspiciousCase7.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase7);

        SuspiciousCase suspiciousCase8 = BootStrapHelper.getSarCase("s2030008", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase8.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C20724110", "s2030008"));
        suspiciousCase8.getSarCase().setCustomExciseCode("C20724110");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase8.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser8 = new SarCaseUser();
        sarCaseUser8.setCreatedBy("s2022896");
        sarCaseUser8.setCreatedDate(new Date());
        sarCaseUser8.setUser(userService.findBySid("s2022896"));
        suspiciousCase8.addSarCaseUser(sarCaseUser8);
        suspiciousCase8.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase8);

        SuspiciousCase suspiciousCase9 = BootStrapHelper.getSarCase("s2022896", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase9.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C01179057", "s2022896"));
        suspiciousCase9.getSarCase().setCustomExciseCode("C01179057");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase9.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser9 = new SarCaseUser();
        sarCaseUser9.setCreatedBy("s2022896");
        sarCaseUser9.setCreatedDate(new Date());
        sarCaseUser9.setUser(userService.findBySid("s2022896"));
        suspiciousCase9.addSarCaseUser(sarCaseUser9);
        suspiciousCase9.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase9);

        SuspiciousCase suspiciousCase10 = BootStrapHelper.getSarCase("s2022896", ClientType.COMPANY_TRADER_IMPORTER_EXPORTER, 1, 1, CustomExcise.CUSTOM, YesNoEnum.YES, YesNoEnum.YES, new Date());
        suspiciousCase10.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C01899787", "s2022896"));
        suspiciousCase10.getSarCase().setCustomExciseCode("C01899787");
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase10.setCaseRefNumber(builder.toString());
        SarCaseUser sarCaseUser10 = new SarCaseUser();
        sarCaseUser10.setCreatedBy("s2022896");
        sarCaseUser10.setCreatedDate(new Date());
        sarCaseUser10.setUser(userService.findBySid("s2022896"));
        suspiciousCase10.addSarCaseUser(sarCaseUser10);
        suspiciousCase10.setCreatedBy("s2022896");
        suspiciousCaseService.save(suspiciousCase10);
    }

    @Ignore
    @Test
    public void testScenarioJ() {
        //ADDING RISK ASSESSMENT TO THE SARCASE
        for (SuspiciousCase suspiciousCase : suspiciousCaseService.listAll()) {
            if (suspiciousCase.getCaseType().equals(CaseType.SAR)) {
                HsChapterRisk chapterRisk = hsChapterRiskService.listAll().iterator().next();
                RiskAssessment riskAssessment = BootStrapHelper.getRiskAssessment(chapterRisk, "s2030008");
                suspiciousCase.getSarCase().setRiskAssessment(riskAssessment);
                suspiciousCaseService.update(suspiciousCase);
            }
        }
    }

    @Ignore
    @Test
    public void testScenarioK() {
        //ADDING AUDIT PLAN TO THE SARCASE
        for (SuspiciousCase suspiciousCase : suspiciousCaseService.listAll()) {
            AuditPlan auditPlan = BootStrapHelper.getAuditPlan(AuditPlanType.RISK, AuditType.FIELD, RiskArea.SECURITY, "s1020246");
            suspiciousCase.setAuditPlan(auditPlan);
            suspiciousCaseService.update(suspiciousCase);
        }
    }

    @Ignore
    @Test
    public void testScenarioL() {
        //ADDING AUDIT REPORT TO THE AUDIT PLAN OF THE SARCASE
        for (AuditPlan auditPlan : auditPlanService.listAll()) {
            AuditReport auditReport = BootStrapHelper.getAuditReport(auditPlan, "s1020246");
            auditPlan.setAuditReport(auditReport);
            auditPlanService.update(auditPlan);
        }
    }

    @Ignore
    @Test
    public void testScenarioM() {
        //CREATING A VDDL
        SuspiciousCase suspiciousCase1 = BootStrapHelper.getVddLedCase("s2022896");
        SarCaseUser sarCaseUser1 = new SarCaseUser();
        sarCaseUser1.setCreatedBy("s2022896");
        sarCaseUser1.setCreatedDate(new Date());
        sarCaseUser1.setUser(userService.findBySid("s2024726"));
        suspiciousCase1.addSarCaseUser(sarCaseUser1);
        suspiciousCase1.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C00520237", "s2024726"));
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase1.setCaseRefNumber(builder.toString());
        suspiciousCaseService.save(suspiciousCase1);

        //CREATING A VDDL
        SuspiciousCase suspiciousCase2 = BootStrapHelper.getVddLedCase("s2022896");
        SarCaseUser sarCaseUser2 = new SarCaseUser();
        sarCaseUser2.setCreatedBy("s2022896");
        sarCaseUser2.setCreatedDate(new Date());
        sarCaseUser2.setUser(userService.findBySid("s2024726"));
        suspiciousCase2.setIbrData(ibrDataService.getRegParticularsByCustomsCodePerIBRData("C20111742", "s2024726"));
        suspiciousCase2.addSarCaseUser(sarCaseUser2);
        persistentSarCase = suspiciousCaseService.findLastInsertedSarRecord();
        builder = new StringBuilder();
        if (persistentSarCase != null) {
            builder.append("CS");
            builder.append(Long.parseLong(persistentSarCase.getCaseRefNumber().substring(2)) + 1L);
        } else {
            builder.append("CS10000001");
        }
        suspiciousCase2.setCaseRefNumber(builder.toString());
        suspiciousCaseService.save(suspiciousCase2);
    }

//    @Ignore
//    @Test
//    public void testScenarioN() {
//      
//        for (VddLedCase vddLed : vddlService.listAll()) {
//            AuditPlan auditPlan = BootStrapHelper.getAuditPlan(AuditPlanType.VDDL, AuditType.FIELD, RiskArea.SECURITY, "s2024726");
//            vddLed.setAuditPlan(auditPlan);
//            VddLedUser vddLedUser = new VddLedUser();
//            vddLedUser.setCreatedDate(new Date());
//            vddLedUser.setCreatedBy("s2024726");
//            vddLedUser.setUser(userService.findBySid("s2024726"));
//            vddLed.addVddLedUser(vddLedUser);
//            vddlService.update(vddLed);
//        }
//    }
//
//    @Ignore
//    @Test
//    public void testScenarioO() {
//      
//        for (AuditPlan auditPlan : auditPlanService.findByAuditPlanType(AuditPlanType.VDDL)) {
//            AuditReport auditReport = BootStrapHelper.getAuditReport(auditPlan, "s2024726");
//            auditPlan.setAuditReport(auditReport);
//            auditPlanService.update(auditPlan);
//        }
//    }
//
//    @Test
//    public void testScenarioP() {
//        CHECK NUMBER OF USERS PER SARCASE AND THEIR ROLE
//        for (SarCase sarCase : sarCaseService.listAll()) {
//            for (SarCaseUser sarCaseUser : sarCase.getSarCaseUsers()) {
//                System.out.println(sarCaseUser.getUser().getSid() + ":::" + sarCaseUser.getUser().getUserRole().getDescription());
//            }
//        }
//    }
//
//    @Ignore
//    @Test
//    public void testScenarioQ() {
//        //ADD ATTACHMENTS
//        for (SarCase sarCase : sarCaseService.listAll()) {
//            sarCase.addAttachment(BootStrapHelper.getAttachment("s2024726"));
//            sarCase.addAttachment(BootStrapHelper.getAttachment("s2024726"));
//            sarCase.addAttachment(BootStrapHelper.getAttachment("s2024726"));
//            sarCase.addAttachment(BootStrapHelper.getAttachment("s2024726"));
//            sarCase.addAttachment(BootStrapHelper.getAttachment("s2024726"));
//            sarCase.addAttachment(BootStrapHelper.getAttachment("s2024726"));
//            sarCaseService.update(sarCase);
//        }
//    }
    @Ignore
    @Test
    public void testScenarioR() {
        User user = userService.findBySid("s2023456");
        OffenceAndPenalty offenceAndPenalty = BootStrapHelper.getOffenceAndPenalty(user,
                OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(7)", "Refuses or fails to comply with a lawful instruction to appear before an officer", 8000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty);

        OffenceAndPenalty offenceAndPenalty1 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(8A)(a) and (b)", "Refuses or fails to comply with a lawful instruction to appear before an officer for examination of goods using an X-ray scanner or any non-intrusive inspection method", 8000.00D, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty1);

        OffenceAndPenalty offenceAndPenalty2 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(10)", "Refuses or fails to comply with an order to stop or a request to be searched", 8000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty2);

        OffenceAndPenalty offenceAndPenalty3 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(10)", "Refuses or fails to comply with an order to stop or a request to be searched", 8000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty3);

        OffenceAndPenalty offenceAndPenalty4 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(12)", "Removes, breaks or interferes with any lock, seal, etc. placed by an officer", 200000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty4);

        OffenceAndPenalty offenceAndPenalty5 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(12)", "Removes any goods from a place locked or sealed by an officer", 200000, " 25% of underpayment, minimum R20 000", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty5);

        OffenceAndPenalty offenceAndPenalty6 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(12A)", "Refuses without good cause to produce the required documents upon instruction by the officer", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty6);

        OffenceAndPenalty offenceAndPenalty7 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_6, "Section 6A", "Refuses to comply with instructions from a Customs officer regarding the movement of goods / person in a Customs controlled area", 8000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty7);

        OffenceAndPenalty offenceAndPenalty8 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(1)", "Fails to report the arrival of a ship or aircraft within the prescribed time", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty8);

        OffenceAndPenalty offenceAndPenalty9 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(1B) and Rule 7.01", "Fails to deliver the manifest within the prescribed time or does not deliver the manifest at all", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty9);

        OffenceAndPenalty offenceAndPenalty10 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(1A)", "Fails to call at a designated point of entry or land at a Customs and Excise airport", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty10);

        OffenceAndPenalty offenceAndPenalty11 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(3)", "Fails to deliver a report outward of any ship or aircraft from any place outside South Africa or another place within South Africa", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty11);

        OffenceAndPenalty offenceAndPenalty12 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(6)", "Fails to obtain a certificate of clearance before departing from any appointed place of entry or any place appointed as a Customs and Excise airport", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty12);

        OffenceAndPenalty offenceAndPenalty13 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(7)", "Fails to obtain a fresh clearance after the initial clearance has lapsed as a result of not departing within the prescribed period", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty13);

        OffenceAndPenalty offenceAndPenalty15 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 8 and Rules 8.04, 8.05, 8.06, 8.07 and 8.08", "Fails to submit a complete manifest / report / list in respect of vessels, aircraft, rail or road transport", 5000, " R5 000 per declaration / report / list", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty15);

        OffenceAndPenalty offenceAndPenalty16 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_DECLARE_GOODS_SECTION_9, "Section 9(1)", "Failure by the master to declare sealable goods", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty16);

        OffenceAndPenalty offenceAndPenalty17 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_DECLARE_GOODS_SECTION_9, "Section 9(1)", "Failure by crew to declare sealable goods", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty17);

        OffenceAndPenalty offenceAndPenalty18 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LANDING_OF_UNENTERED_GOODS_SECTION_9, "Section 11(1)", "Fails to place goods landed before due entry thereof in a transit shed, container terminal, container depot, state warehouse or any other place approved by the Controller / Branch Manager", 10000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty18);

        OffenceAndPenalty offenceAndPenalty19 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REPORTING_OF_VEHICLES_AFTER_ARRIVAL, "Section 12(3)(a)", "Fails to place goods landed before due entry thereof in a transit shed, container terminal, container depot, state warehouse or any other place approved by the Controller / Branch Manager", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty19);

        OffenceAndPenalty offenceAndPenalty20 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REPORTING_OF_VEHICLES_AFTER_ARRIVAL, "Section 12(3)(a)", "Failure by a person in charge of a vehicle to report to the nearest controller/Branch Manager the arrival of such a vehihle in South Africa and / or the goods conveyed thereon and thjeir destination at a non-designated ", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty20);

        OffenceAndPenalty offenceAndPenalty21 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REPORTING_OF_GOODS_BROUGHT_INTO_THE_COUNTRY_ON_FOOT, "Section 12(5)(a), (b), (c)", "Fails to report goods brought into South Africa to the nearest Controller / Branch Manager and deals with such goods before release by the said Controller / Branch Manager (also see Sections ", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty21);

        OffenceAndPenalty offenceAndPenalty22 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITION_OF_GOODS_IMPORTED_BY_POST, "Section 13(5)", "Receives, removes, takes, or in any manner deals with, or in any goods, imported by post without payment of the correct duty to the Postmaster", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty22);

        OffenceAndPenalty offenceAndPenalty23 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA, "Section 15(1) and (2)", ") Fails to declare goods in his / her possession", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty23);

        OffenceAndPenalty offenceAndPenalty24 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA, "Section 15(1) and (2)", "Declares goods but fails to furnish the correct particulars i.e. of value and / or quantity", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty24);

        OffenceAndPenalty offenceAndPenalty25 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA, "Section 15(1) and (2)", "Leaving - Fails to declare goods in his/her possession", 1500, "  ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty25);

        OffenceAndPenalty offenceAndPenalty26 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.MISUSE_OF_TEMPORARY_IMPORT_PERMIT, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01", "Misuse of temporary import permit (TIP) Carnets in relation to: i) Vehicles used outside permit / carnet provisions; ii) Hire or lease vehicle after importation; and / or iii) Vehicle sold or otherwise disposed of", 1500, "  ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty26);

        OffenceAndPenalty offenceAndPenalty27 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.MISUSE_OF_TEMPORARY_IMPORT_PERMIT, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01", "Fails to declare goods for export including reexportation on carnet", 5000, "R5 000 per incident", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty27);

        OffenceAndPenalty offenceAndPenalty28 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.MISUSE_OF_TEMPORARY_IMPORT_PERMIT, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01", "Fails to submit proof of export (acquittal) in the prescribed period as indicated on the carnet", 5000, "R5 000 per incident", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty28);

        OffenceAndPenalty offenceAndPenalty29 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(3) read with Rule 18.07(a)", "Fails to obtain proof that goods have been removed to final destination within the specified period Repetitive failure must lead to further action; for example, agents may face de-licensing", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty29);

        OffenceAndPenalty offenceAndPenalty30 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(3) read with Rule 18.07(b)", "Fails to submit valid proof, information or documents within seven(7) days from request without valid reasons for non-submission(Repetitive failure myst lead to further action; for examplae, agents may face de-licensing)", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty30);

        OffenceAndPenalty offenceAndPenalty31 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10", "Second hand vehicle moving in transit or bond through South Africa on own wheels", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty31);

        OffenceAndPenalty offenceAndPenalty32 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10", "Second hand vehicle moving in transit or bond by person other than licensed remover of goods", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty32);

        OffenceAndPenalty offenceAndPenalty33 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10", "Second hand vehicle moving in transit or bond through South Africa dropped off before port of exit", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty33);

        OffenceAndPenalty offenceAndPenalty34 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(8)", "Takes delivery of goods removed in bond or removes such goods from the Controller / Branch Manager without due entry for home consumption", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty34);

        OffenceAndPenalty offenceAndPenalty35 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 19.02", "Fails to maintain warehouse, in terms of physical conditions and adequate security", 2000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty35);

        OffenceAndPenalty offenceAndPenalty36 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(3)", "Removes or breaks a State lock", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty36);

        OffenceAndPenalty offenceAndPenalty37 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(3)", "Enters any warehouse that is so locked and removes any goods without the permission of the Commissioner", 1500, "25% of underpayment", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty37);

        OffenceAndPenalty offenceAndPenalty38 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(4) and 20(5)", "Fails to allow the Controller / Branch Manager or any delegated officer to conduct a stock take at a Customs and Excise warehouse", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty38);

        OffenceAndPenalty offenceAndPenalty39 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(9)", "Goods retained in a Customs and Excise warehouse for a period longer than two (2) years without permission", 1500, " R1 500 per consignment or part thereof", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty39);

        OffenceAndPenalty offenceAndPenalty40 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 19.05", "Does not keep a proper / complete record of all receipts into or removals from the warehouse", 2000, " R1 500 per consignment or part thereof", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty40);

        OffenceAndPenalty offenceAndPenalty41 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rules 19.07", "Fails to notify the Controller / Branch Manager of change in legal identity, name and address of business, structure of warehouse / plant or goods manufactured", 1500, " R1 500 per consignment or part thereof", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty41);

        OffenceAndPenalty offenceAndPenalty42 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 20.03", "Fails to produce required documents for warehousing of goods", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty42);

        OffenceAndPenalty offenceAndPenalty43 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 20(4)", "Removes goods from a customs and Excise warehouse due entry level thereof", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty43);

        OffenceAndPenalty offenceAndPenalty44 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 20(4)(bis)", "Diverts goods entered for removal from or delivery to a Customs and Excise warehouse to any other destination without the permission of the Controller / Branch Manager", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty44);

        OffenceAndPenalty offenceAndPenalty45 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 20.08", "Open / examines goods in closed trade containers without the permission of the Controller / Branch Manager", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty45);

        OffenceAndPenalty offenceAndPenalty46 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 26", "Transfers ownership of goods from a Customs and Excise warehouse without the prior permission of the Controller / Branch Manager", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty46);

        OffenceAndPenalty offenceAndPenalty47 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 12(7)", "Failure to declare vehicle and goods carried thereon from South Africa", 1500, "25% of value of goods not declared, minimum of R1 500 and a maximum of R20 000", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty47);

        OffenceAndPenalty offenceAndPenalty48 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 18A(3)", "Fails to obtain proof of export in prescribed period of thirty (30) days", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty48);

        OffenceAndPenalty offenceAndPenalty49 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 18A(4)", "Failure to process export declaration for ex warehouse goods", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty49);

        OffenceAndPenalty offenceAndPenalty50 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 18A(9)", "Without permission of Commissioner diverts goods to a destination other than that declared", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty50);

        OffenceAndPenalty offenceAndPenalty51 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Counter / administrative error", 0.00, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty51);

        OffenceAndPenalty offenceAndPenalty52 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Duty incorrectly calculated", 1500, "10% of underpayment, minimum of R 1500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty52);

        OffenceAndPenalty offenceAndPenalty53 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Removes, receives, takes, delivers or deals with imported / exported goods without such goods being entered", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty53);

        OffenceAndPenalty offenceAndPenalty54 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Omits country of origin or incorrect country of origin on declaration", 100, "R 100 per declaration line", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty54);

        OffenceAndPenalty offenceAndPenalty55 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Omission of a line on the worksheet or declaration", 1500, "10% of underpayment, minimum of R 1500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty55);

        OffenceAndPenalty offenceAndPenalty56 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Incorrect rate of exchange", 500, "10% of underpayment, minimum of R 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty56);

        OffenceAndPenalty offenceAndPenalty57 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Incorrect currency use on declaration", 500, "10% of underpayment, minimum of R 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty57);

        OffenceAndPenalty offenceAndPenalty58 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Particulars on invoice correct nut incorrect tariff heading entered", 500, "10% of (potential) under-payment, minimum of R 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty58);

        OffenceAndPenalty offenceAndPenalty59 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Potential under-declarations where goods are entered for warehousing, where duty is rebated or the goods cleared in transit", 500, "10% of (potential) under-payment, minimum of R 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty59);

        OffenceAndPenalty offenceAndPenalty60 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Item appears on invoice but not on original declaration", 500, "10% of underentry, minimum of R 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty60);

        OffenceAndPenalty offenceAndPenalty61 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Using false or misleading documents", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty61);

        OffenceAndPenalty offenceAndPenalty62 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Enters goods under rebate not qualifying for such rebate", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty62);

        OffenceAndPenalty offenceAndPenalty63 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Omission of an invoice resulting in an underpayment", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty63);

        OffenceAndPenalty offenceAndPenalty64 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "Non-disclosure or non-application of tariff determination(TDN)", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty64);

        OffenceAndPenalty offenceAndPenalty65 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 39(1)", "Failure to pay all duties due on goods imported contrary to the conditions relating to the deferment agreement", 8000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty65);

        OffenceAndPenalty offenceAndPenalty66 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Rule 39.08", "Omits to declare a Customs code number or declares an invalid number despite being issued with one", 500, "R500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty66);

        OffenceAndPenalty offenceAndPenalty67 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 39(1), 40(1),  44(10) and 47(1) read with Section 113(2)", "Fails to inform SARS with regards to any substitutions, amendments or replacements on carnets", 5000, "R5 000 per incident", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty67);

        OffenceAndPenalty offenceAndPenalty68 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 40(1)", "Goods cleared for export which have been placed into export stacks, cargo depots, Customs controlled area or loaded onto any vehicle which will remove such from South Africa returned to South Africa without permission", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty68);

        OffenceAndPenalty offenceAndPenalty69 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 40(1)", "Goods cleared for export which have been placed into export stacks, cargo depots, Customs controlled area or loaded onto any vehicle which will remove such from South Africa returned to South Africa without permission", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty69);

        OffenceAndPenalty offenceAndPenalty70 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 41", "Failure to produce a detailed, correct and sufficient invoice", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty70);

        OffenceAndPenalty offenceAndPenalty71 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 54 read with Rule 54.01, 02", "Imports, removes or allows cigarettes to be removed from a Customs and Excise warehouse for home consumption without the South African Diamond stamp impression appearing on the containers as determined by the Commissioner", 20000, "R20 000 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty71);

        OffenceAndPenalty offenceAndPenalty72 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 119A and 39(1)(c) read with Rules 119A and 39(2B).02", "Failure to submit requested documents in the prescribed format at the place specified within two(2) working days from request where valid reasons for non-submission exist, no penalty should be imposed Repetitve failure must lead to further action; for example, agents may face de-lecensing", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty72);

        OffenceAndPenalty offenceAndPenalty73 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REGISTRATION, "Section 59A read with Rule 59A", "Failure to register for respective product type(example 317.02, 521.00 etc.)", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty73);

        OffenceAndPenalty offenceAndPenalty74 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64B read with 60", "Enters goods for reward on behalf of an importer or exporter without the prescribed licence", 1500, "R5 000 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty74);

        OffenceAndPenalty offenceAndPenalty75 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64B", "Utililses the security of another agent for the fulfilment of his /her (agent) obligations in the of this act", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty75);

        OffenceAndPenalty offenceAndPenalty76 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64C", "Searches any wreck or searches for any wreck without being licensed with the Commissioner to do so", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty76);

        OffenceAndPenalty offenceAndPenalty77 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64D(1) read with 60(1)", "No person, except if exempted by Rule, shall remove any goods in bond / transit by road in terms of Section 18(1)(a) or export by road (in terms of Sections 18A, 19, 19A, 20 or 21 or any other goods that may be specified by Rule) unless licensed as a remover of goods", 5000, "R5 000 per declaration", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty77);

        OffenceAndPenalty offenceAndPenalty78 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64D(1) and (7) read with 60(1) and Rules 64D.10(5) and 64D.11(5)", "Using security of another remover of goods in bond", 20000, "R20 000 per incident", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty78);

        OffenceAndPenalty offenceAndPenalty80 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.VALUATION, "Sections 65, 66, 67 read with 39(1)", "Non-disclosure or non-application of a value determination (VDN)", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty80);

        OffenceAndPenalty offenceAndPenalty81 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.VALUATION, "Sections 65, 66, 67 read with 39(1)", "Not including dutiable charges on original declaration", 1500, "25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty81);

        OffenceAndPenalty offenceAndPenalty82 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(1)(b)", "Fails to export goods originally imported within the specified period", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty82);

        OffenceAndPenalty offenceAndPenalty83 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "Sells without permission of the Comissioner an immigrants car of a value not exceeding R20 000", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty83);

        OffenceAndPenalty offenceAndPenalty84 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "b) Sells or disposes of other goods entered under rebate of duty without obtaining the permission of the Commissioner and payment of the duty due", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty84);

        OffenceAndPenalty offenceAndPenalty85 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "c) Sells or disposes of other goods entered under rebate of duty without obtaining the permission of the Commissioner and payment of the duty due", 1500, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty85);

        OffenceAndPenalty offenceAndPenalty86 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "d) Uses goods entered under rebate of duty otherwise than in accordance with the item under which entry was made", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty86);

        OffenceAndPenalty offenceAndPenalty87 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.06", "Moves goods to non-registered premises for further processing or operation without without the permission of the Controller / Branch Manager", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty87);

        OffenceAndPenalty offenceAndPenalty88 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(19)", "Diverts goods entered under rebate of duty without the permission of the Commissioner to a destination other than the destination declared on the rebate entry or delivers such goods or causes such goods to be delivered contrary to the provisions of this Act", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty88);

        OffenceAndPenalty offenceAndPenalty89 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(21)", "Keeps goods in a rebate store for longer than two (2) years", 1500, "R1 500 per consignment or part thereof", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty89);

        OffenceAndPenalty offenceAndPenalty90 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.10", "Keeps goods in his / her rebate store that have not been entered under the provisions of Schedule 3, 4 or 6", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty90);

        OffenceAndPenalty offenceAndPenalty91 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.14", "Fails to maintain a stock record as prescribed by Rule", 4000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty91);

        OffenceAndPenalty offenceAndPenalty92 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.15", "Does not keep proper / complete record of all receipts into or withdrawals from the rebate store", 5000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty92);

        OffenceAndPenalty offenceAndPenalty93 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 80(1)(h)", "Without lawful excuse (the proof of which shall rest upon him / her) brings into South Africa or has in his / her possession any blank or incomplete invoice or any billhead or other similar document capable of being filled up and used as an invoice for goods from outside South Africa", 500, " R500 per invoice / bill, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty93);

        OffenceAndPenalty offenceAndPenalty94 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 84(1)", "Makes a false statement in connection with any matter dealt with in the Act, or who makes use for the purposes of this Act of a declaration or document containing any such statement", 2000, " ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty94);

        OffenceAndPenalty offenceAndPenalty95 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 86(a)", "Fails to advise the Controller / Branch Manager of any amended invoice, debit or credit note that increases the duty", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty95);

        OffenceAndPenalty offenceAndPenalty96 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 88(1)", "Damages, destroys or disposes of any goods to prevent the securing or seizure thereof under the provisions of the Act or without permission, takes back any goods that are detained or have been seized detained or have been seized", 1500, "25% of value of goods seized, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty96);

        OffenceAndPenalty offenceAndPenalty97 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 88(1)(bA)", "Damages, destroys or disposes of any goods to prevent the securing or seizure thereof under the provisions of the Act or without permission, takes back any goods that are detained or have been seized detained or have been seized", 1500, "25% of value of goods seized, minimum of R1 500", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty97);

        OffenceAndPenalty offenceAndPenalty98 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "c) Incorrect tariff classification on the voucher of correction as well as on the original declaration – Tariff Section has advised that a third tariff heading is applicable", 1500, "25% of over claim, minimum of R 1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty98);

        OffenceAndPenalty offenceAndPenalty99 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "d) Refund submitted in terms of a ITAC permit which indicated specific dates for declaration to be used - declaration date on which the refund is based is not within the prescribed period therefore the applicant is not entitled to a refund", 1500, "25% of over claim, minimum of R 1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty99);

        OffenceAndPenalty offenceAndPenalty100 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "e) Refund submitted in terms of tariff amendment that is retrospectively backdated - assessment date not falling within the dates as specified in the amendment therefore the agent is not entitled to a refund", 1500, "25% of over claim, minimum of R 1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty100);

        OffenceAndPenalty offenceAndPenalty101 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "f) Agent / importer uses one (1) method (rate of exchange or factor) when calculating duties to frame declaration and uses a different method to calculate the claim amount when applying for a refund", 1500, "25% of over claim, minimum of R 1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty101);

        OffenceAndPenalty offenceAndPenalty102 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "g) Agent / importer claims refund of duties on goods used in manufacturing process but goods have not been exported (proof of export not provided or not adequate)", 1500, "25% of over claim, minimum of R 1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty102);

        OffenceAndPenalty offenceAndPenalty103 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "h) The agent / importer claims refund of duties on goods which were incorrectly supplied, or were faulty but the goods have not been cleared for home consumption (false documents)", 1500, "25% of over claim, minimum of R 1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty103);

        OffenceAndPenalty offenceAndPenalty104 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "i) Submit false proof of export to obtain refund of duties", 2500, "25% of over claim, minimum of R2 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty104);

        OffenceAndPenalty offenceAndPenalty105 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "j) Fails to produce sufficient proof of export", 2500, "25% of over claim, minimum of R2 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty105);

        OffenceAndPenalty offenceAndPenalty106 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "k) Resubmitting an application for a refund / drawback previously rejected without following the instructions of the officer", 2500, "25% of over claim, minimum of R2 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty106);

        OffenceAndPenalty offenceAndPenalty107 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "l) Submitting an application for a refund / drawback for an amount on a declaration which was originally not paid", 2500, "25% of over claim, minimum of R2 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty107);

        OffenceAndPenalty offenceAndPenalty108 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Section 76(1)", "Submits a duplicate application for refund of duty or other charge to the Controller / Branch Manager for an amount that has already been refunded", 2500, "25% of over claim, minimum of R2 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty108);

        OffenceAndPenalty offenceAndPenalty109 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Section 76B", "Time expired claim", 2500, "25% of over claim, minimum of R2 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty109);

        OffenceAndPenalty offenceAndPenalty110 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.KEEPING_OF_BOOKS_ACCOUNTS_AND_DOCUMENTS, "Section 101(1)(a) and (2) read with Rule 101.01", "a) Fails to keep the prescribed books, accounts or documents", 5000, "  ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty110);

        OffenceAndPenalty offenceAndPenalty111 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.KEEPING_OF_BOOKS_ACCOUNTS_AND_DOCUMENTS, "Section 101(1)(a) and (2) read with Rule 101.01", "b) Fails to produce the prescribed goods, accounts or documents on demand", 5000, "  ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty111);

        OffenceAndPenalty offenceAndPenalty112 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.KEEPING_OF_BOOKS_ACCOUNTS_AND_DOCUMENTS, "Section 101(2B)", "Fails to keep or produce on demand any data created by means of a computer as defined in Section 1 of the Computer Evidence Act, 1983", 5000, "  ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty112);

        OffenceAndPenalty offenceAndPenalty113 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ELECTRONIC_SUBMISSION, "Rule 101A.01A(2)(a)", "Fails to submit documents electronically, unless exempted temporarily or permanently by the Commissioner.", 5000, " R5 000 per declaration / report / list ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty113);

        OffenceAndPenalty offenceAndPenalty114 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CONDITIONS_FOR_RELEASE_OF_GOODS_UNDER_CUSTOMS_CONTROL, "Section 107(2)(a)", "Fails to comply with the conditions determined by the Commissioner for allowing goods detained to pass from his / her control", 1500, "  ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty114);

        OffenceAndPenalty offenceAndPenalty115 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_SUBJECT_TO_LIEN, "Section 114(2A)", "Removes goods subject to a lien [Section 114(1)(aA) or goods so detained Section 114(2)] ", 1500, "25% of value of goods subject  to the lien, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.SAR);
        offenceAndPenaltyService.save(offenceAndPenalty115);

        OffenceAndPenalty offenceAndPenalty116 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(7)", "Refuses or fails to comply with a lawful instruction to appear before an officer ", 8000, "N/A", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty116);

        OffenceAndPenalty offenceAndPenalty117 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(8A)(a) and (b)", "Refuses or fails to comply with a lawful instruction to appear before an officer for examination of goods using an X-ray scanner or any non-intrusive inspection method", 8000, "N/A", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty117);

        OffenceAndPenalty offenceAndPenalty118 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(10)", "Refuses or fails to comply with an order to stop or a request to be searched", 8000, "N/A", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty118);

        OffenceAndPenalty offenceAndPenalty119 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(12)", "a) Removes, breaks or interferes with any lock, seal, etc. placed by an officer", 8000, "N/A", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty119);

        OffenceAndPenalty offenceAndPenalty120 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(12)", "b) Removes any goods from a place locked or sealed by an officer", 20000, "25% of underpayment, minimum R20 000", 0.00, " Once the value", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty120);

        OffenceAndPenalty offenceAndPenalty121 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_4, "Section 4(12A)", "Refuses without good cause to produce the required documents upon instruction by the officer", 5000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty121);

        OffenceAndPenalty offenceAndPenalty122 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.IGNORE_INSTRUCTION_SECTION_6, "Section 6A", "Refuses to comply with instructions from a Customs officer regarding the movement of goods / person in a Customs controlled area", 8000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty122);

        OffenceAndPenalty offenceAndPenalty123 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(1)", "Fails to report the arrival of a ship or aircraft within the prescribed time", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty123);

        OffenceAndPenalty offenceAndPenalty124 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(1B) and Rule 7.01", "Fails to deliver the manifest within the prescribed time or does not deliver the manifest at all", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty124);

        OffenceAndPenalty offenceAndPenalty125 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(1A) ", "Fails to call at a designated point of entry or land at a Customs and Excise airport", 1500, " ", 0.00, "Oncce the value ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty125);

        OffenceAndPenalty offenceAndPenalty126 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(3)", "Fails to deliver a report outward of any ship or aircraft from any place outside South Africa or another place within South Africa", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty126);

        OffenceAndPenalty offenceAndPenalty127 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(6)", "Fails to obtain a certificate of clearance before departing from any appointed place of entry or any place appointed as a Customs and Excise airport ", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty127);

        OffenceAndPenalty offenceAndPenalty128 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 7(7)", "Fails to obtain a fresh clearance after the initial clearance has lapsed as a result of not departing within the prescribed period ", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty128);

        OffenceAndPenalty offenceAndPenalty129 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_SECTION_7_AND_8, "Section 8 and Rules 8.04, 8.05, 8.06, 8.07 and 8.08", "Fails to submit a complete manifest / report / list in respect of vessels, aircraft, rail or road transport", 5000, "R5 000 per declaration / report / list ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty129);

        OffenceAndPenalty offenceAndPenalty130 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_DECLARE_GOODS_SECTION_9, "Section 9(1)", "a) Failer by master to declare sealable goods", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty130);

        OffenceAndPenalty offenceAndPenalty131 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_DECLARE_GOODS_SECTION_9, "Section 9(1)", "b) Failure by crew to declare sealable goods", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty131);

        OffenceAndPenalty offenceAndPenalty132 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LANDING_OF_UNENTERED_GOODS_SECTION_9, "Section 11(1)", "Fails to place goods landed before due entry thereof in a transit shed, container terminal, container depot, state warehouse or any other place approved by the Controller / Branch Manager", 10000, " ", 0.00, " Once the value", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty132);

        OffenceAndPenalty offenceAndPenalty133 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REPORTING_OF_VEHICLES_AFTER_ARRIVAL, "Section 12(3)(a)", "a) Failer by a person to report an arrival a designated port", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty133);

        OffenceAndPenalty offenceAndPenalty134 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REPORTING_OF_VEHICLES_AFTER_ARRIVAL, "Section 12(3)(a)", " b) Failure by a person in charge of a vehicle to repor to the nearest Controller / Branch Manager the arrival of such a vehicle i Aouth Africa and /or the goods conveyed thereon and their destination at a non-designated port", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty134);

        OffenceAndPenalty offenceAndPenalty135 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REPORTING_OF_GOODS_BROUGHT_INTO_THE_COUNTRY_ON_FOOT, "Section 12(5)(a), (b), (c)", "Fails to report goods brought into South Africa to the nearest Controller / Branch Manager and deals with such goods before release by the said Controller / Branch Manager (also see Sections 38(1), 47(1) and 47A(1))", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty135);

        OffenceAndPenalty offenceAndPenalty136 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITION_OF_GOODS_IMPORTED_BY_POST, "Section 13(5)", "Receives, removes, takes, or in any manner deals with, or in any goods, imported by post without payment of the correct duty to the Postmaster", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, "Once the value ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty136);

        OffenceAndPenalty offenceAndPenalty137 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA, "Section 15(1) and (2)", "a) Fails to declare goods in his / her possession", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty137);

        OffenceAndPenalty offenceAndPenalty138 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA, "Section 15(1) and (2)", "b) Declares goods but fails to furnish the correct particulars i.e. of value and / or quantity", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty138);

        OffenceAndPenalty offenceAndPenalty139 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.GOODS_NOT_DECLARED_BY_PERSON_LEAVING_OR_ENTERING_SA, "Section 15(1) and (2)", "c) Leaving -Fails to declare goods in his her possession", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty139);

        OffenceAndPenalty offenceAndPenalty140 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.MISUSE_OF_TEMPORARY_IMPORT_PERMIT, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01", "a) Misuse of temporary important permit(TIP) or Carnets in relation to:i) Vehicles used outside permit/carnet provisions;ii) Hire or lease vehicle after importation ; and /or iii) Vehicle sold or otherwise disposed of", 20000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty140);

        OffenceAndPenalty offenceAndPenalty141 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.MISUSE_OF_TEMPORARY_IMPORT_PERMIT, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01", "b) Fails to declare goods for export including reexportation on carnet", 5000, "R5 000 per incident ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty141);

        OffenceAndPenalty offenceAndPenalty142 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.MISUSE_OF_TEMPORARY_IMPORT_PERMIT, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01", "c) Fails to submit proof of export (acquittal) in the prescribed period as indicated on the carnet", 5000, "R5 000 per incident ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty142);

        OffenceAndPenalty offenceAndPenalty143 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(3) read with Rule 18.07(a)", "Fails to obtain proof that goods have been removed to final destination within the specified period Repetitive failure must lead to further action; for example, agents may face de-licensing", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty143);

        OffenceAndPenalty offenceAndPenalty144 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(3) read with Rule 18.07(b)", " Fails to submit valid proof, information documents within seven (7) days from request without valid reasons for non-submission (Repetitive failure must lead to further action; for example, agents may face de-licensing)", 1500, "R1 500 per declaration", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty144);

        OffenceAndPenalty offenceAndPenalty145 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10", "a) Second hand vehicle moving in transit or bond through South Africa on own wheels", 1500, "R1 500 per vehicle", 5000, "Once the value or R5 000 whichever is lower ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty145);

        OffenceAndPenalty offenceAndPenalty146 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10", "b) Second hand vehicle moving in transit or bond by person other than licensed remover of goods", 1500, "R1 500 per vehicle", 5000, "Once the value or R5 000 whichever is lower ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty146);

        OffenceAndPenalty offenceAndPenalty147 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10", "c) Second hand vehicle moving in transit or bond through South Africa dropped off before port of exit", 1500, "R1 500 per vehicle", 5000, "Once the value or R5 000 whichever is lower ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty147);

        OffenceAndPenalty offenceAndPenalty148 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(8)", "Takes delivery of goods removed in bond removes such goods from the control of th Controller / Branch Manager without due entry for home consumption thereof", 1500, "25% of underpayment, minimum of R1 500", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty148);

        OffenceAndPenalty offenceAndPenalty149 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.ENTERING_OF_GOODS_REMOVED_IN_BOND, "Section 18(13)", "Without permission diverts goods removed in bond to a destination other than the destination declared on entry for removal in bond", 1500, "25% of underpayment, minimum of R1 500", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty149);

        OffenceAndPenalty offenceAndPenalty150 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 19.02", "Fails to maintain warehouse, in terms of physical conditions and adequate security", 2000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty150);

        OffenceAndPenalty offenceAndPenalty151 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(3)", "a) Removes or breaks a State lock", 5000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty151);

        OffenceAndPenalty offenceAndPenalty152 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 19.02", "b) Enters any warehouse that is so locked and removes any goods without the permission of the Commissioner", 1500, "25% of underpayment ", 0.00, "Once the value of goods so removed ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty152);

        OffenceAndPenalty offenceAndPenalty153 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(4) and 20(5)", "Fails to allow the Controller / Branch Manager or any delegated officer to conduct a stock take at a Customs and Excise warehouse", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty153);

        OffenceAndPenalty offenceAndPenalty154 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 19(9)", "Goods retained in a Customs and Excise warehouse for a period longer than two (2) years without permission", 1500, " R1 500 per consignment or part thereof", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty154);

        OffenceAndPenalty offenceAndPenalty155 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 19.05", "Does not keep a proper / complete record of all receipts into or removals from the warehouse", 2000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty155);

        OffenceAndPenalty offenceAndPenalty156 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 19.07", "Fails to notify the Controller / Branch Manager of change in legal identity, name and address of business, structure of warehouse / plant or goods manufactured", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty156);

        OffenceAndPenalty offenceAndPenalty157 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 20.03", "Fails to produce required documents for warehousing of goods.", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty157);

        OffenceAndPenalty offenceAndPenalty158 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 20(4)", "Removes goods from a Customs and Excise warehouse without due entry thereof", 1500, " 25% of underpayment, minimum of R1 500", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty158);

        OffenceAndPenalty offenceAndPenalty159 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 20(4)(bis)", "Diverts goods entered for removal from or delivery to a Customs and Excise warehouse to any other destination without the permission of the Controller / Branch Manager", 1500, " 25% of underpayment, minimum of R1 500", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty159);

        OffenceAndPenalty offenceAndPenalty160 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Rule 20.08", "Open / examines goods in closed trade containers without the permission of the Controller / Branch Manager", 1500, "  ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty160);

        OffenceAndPenalty offenceAndPenalty161 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.CUSTOMS_AND_EXCISE_WAREHOUSE, "Section 26", "Transfers ownership of goods from a Customs and Excise warehouse without the prior permission of the Controller / Branch Manager", 1500, "  ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty161);

        OffenceAndPenalty offenceAndPenalty162 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 12(7)", "Failure to declare vehicle and goods carried thereon from South Africa", 1500, "25% of value of goods not declared, minimum of R1 500 and a maximum of R20 000  ", 2000, "50% of value of goods not declared, minimum of R2 000 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty162);

        OffenceAndPenalty offenceAndPenalty163 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 18A(3)", "Fails to obtain proof of export in prescribed period of thirty (30) days", 1500, "  ", 2000, "  ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty163);

        OffenceAndPenalty offenceAndPenalty164 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 18A(4) ", "Failure to process export declaration for ex warehouse goods", 1500, "25% of value of goods not declared, minimum of R1 500 and a maximum of R20 000  ", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty164);

        OffenceAndPenalty offenceAndPenalty165 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 18A(9)", "Without permission of Commissioner diverts goods to a destination other than that declared", 1500, "25% of value of goods not declared, minimum of R1 500 and a maximum of R20 000  ", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty165);

        OffenceAndPenalty offenceAndPenalty167 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "a) Counter / administrative error", 0, " ", 1500, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty167);

        OffenceAndPenalty offenceAndPenalty168 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "b) Duty incorrectly calculated", 0, "10% of underpayment, minimum of R 1500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty168);

        OffenceAndPenalty offenceAndPenalty169 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "c) Removes, receives, takes, delivers or deals with imported / exported goods without such goods being entered", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty169);

        OffenceAndPenalty offenceAndPenalty170 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "d) Omits country of origin or incorrect country of origin on declaration", 100, "R 100 per declaration line ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty170);

        OffenceAndPenalty offenceAndPenalty171 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "e) Omission of a line on the worksheet or declaration", 1500, "10% of underpayment, minimum of R 1500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty171);

        OffenceAndPenalty offenceAndPenalty172 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "f) Incorrect rate of exchange", 500, "10% of underpayment, minimum of R 1500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty172);

        OffenceAndPenalty offenceAndPenalty173 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "g) Incorrect currency use on declaration", 500, "10% of underpayment, minimum of R 1500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty173);

        OffenceAndPenalty offenceAndPenalty174 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "i) Potential under-declarations where goods are entered for warehousing, where duty is rebated or the goods cleared in transit", 500, "10% of underpayment, minimum of R 1500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty174);

        OffenceAndPenalty offenceAndPenalty175 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "j) Item appears on invoice but not on originaldeclaration", 500, "10% of underpayment, minimum of R 1500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty175);

        OffenceAndPenalty offenceAndPenalty176 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "k) Using false or misleading documents", 1500, "25% of underpayment, minimum of R1 500", 2500, " 50% of value, minimum of R2 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty176);

        OffenceAndPenalty offenceAndPenalty177 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "l) Enters goods under rebate not qualifying for such rebate", 1500, "25% of underpayment, minimum of R1 500", 2500, " 50% of value, minimum of R2 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty177);

        OffenceAndPenalty offenceAndPenalty178 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "m) Omission of an invoice resulting in an underpayment", 1500, "25% of underpayment, minimum of R1 500", 2500, " 50% of value, minimum of R2 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty178);

        OffenceAndPenalty offenceAndPenalty179 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)", "n) Non-disclosure or non-application of tariff determination (TDN)", 1500, "25% of underpayment, minimum of R1 500", 2500, " 50% of value, minimum of R2 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty179);

        OffenceAndPenalty offenceAndPenalty180 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 39(1)", "Failure to pay all duties due on goods imported contrary to the conditions relating to the deferment agreement", 8000, " ", 0.00, "  ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty180);

        OffenceAndPenalty offenceAndPenalty181 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Rule 39.08", "Omits to declare a Customs code number or declares an invalid number despite being issued with one", 500, "R500 per declaration ", 0.00, "  ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty181);

        OffenceAndPenalty offenceAndPenalty182 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 38(1), 39(1), 40(1),  44(10) and 47(1) read with Section 113(2)", "Fails to inform SARS with regards to any substitutions, amendments or replacements on carnets", 5000, "R5 000 per incident ", 0.00, "  ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty182);

        OffenceAndPenalty offenceAndPenalty183 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 40(1)", "Goods cleared for export which have been placed into export stacks, cargo depots, Customs controlled area or loaded onto any vehicle which will remove such from South Africa returned to South Africa without permission", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000", 2000, " 50% of value, minimum of R2 000 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty183);

        OffenceAndPenalty offenceAndPenalty184 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 41", "Failure to produce a detailed, correct and sufficient invoice", 1500, "25% of value, minimum of R1 500 and a maximum of R20 000", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty184);

        OffenceAndPenalty offenceAndPenalty185 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Section 54 read with Rule 54.01, 02", "Imports, removes or allows cigarettes to be removed from a Customs and Excise warehouse for home consumption without the South African Diamond stamp impression appearing on the containers as determined by the Commissioner", 20000, "R20 000 per declaration", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty185);

        OffenceAndPenalty offenceAndPenalty186 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.FAILURE_TO_COMPLY_WITH_THE_CONDITIONS_FOR_ENTRY_GOODS, "Sections 119A and 39(1)(c) read with Rules 119A and 39(2B).02", "Failure to submit requested documents in the prescribed format at the place specified within two (2) working days from request Where valid reasons for non-submission exist, no penalty should be imposed Repetitive failure must lead to further action; for example, agents may face de-licensing", 20000, "R20 000 per declaration", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty186);

        OffenceAndPenalty offenceAndPenalty187 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REGISTRATION, "Section 59A read with Rule 59A", "Failure to register for respective product type (example 317.02, 521.00 etc.)", 5000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty187);

        OffenceAndPenalty offenceAndPenalty188 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64B read with 60", "Enters goods for reward on behalf of an importer or exporter without the prescribed licence", 5000, " R5 000 per declaration", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty188);

        OffenceAndPenalty offenceAndPenalty189 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64B", "Utilises the security of another agent for the fulfilment of his / her (agent) obligations in terms of this Act", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty189);

        OffenceAndPenalty offenceAndPenalty190 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64C", "Searches any wreck or searches for any wreck without being licensed with the Commissioner to do so", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty190);

        OffenceAndPenalty offenceAndPenalty191 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64D(1) read with 60(1)", "No person, except if exempted by Rule, shall remove any goods in bond / transit by road in terms of Section 18(1)(a) or export by road (in terms of Sections 18A, 19, 19A, 20 or 21 or any other goods that may be specified by Rule) unless licensed as a remover of goods", 5000, "R5 000 per declaration ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty191);

        OffenceAndPenalty offenceAndPenalty192 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.LICENCES, "Section 64D(1) and (7) read with 60(1) and Rules 64D.10(5) and 64D.11(5) 64D.10(5) and 64D.11(5)", "Using security of another remover of goods in bond", 2000, "R20 000 per incident ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty192);

        OffenceAndPenalty offenceAndPenalty193 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.VALUATION, "Sections 65, 66, 67 read with 39(1)", "Non-disclosure or non-application of a value determination (VDN)", 1500, "25% of underpayment, minimum of R1 500 ", 2500, "50% of value, minimum of R2 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty193);

        OffenceAndPenalty offenceAndPenalty194 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.VALUATION, "Sections 65, 66, 67 read with 39(1)", "Not including dutiable charges on original declaration", 1500, "25% of underpayment, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty194);

        OffenceAndPenalty offenceAndPenalty195 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(1)(b)", "Fails to export goods originally imported within the specified period", 5000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty195);

        OffenceAndPenalty offenceAndPenalty196 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "a) Sells without permission of the Commissioner an immigrant’s car of a value not exceeding R20 000", 5000, " ", 1500, "25% of value, minimum of R1 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty196);

        OffenceAndPenalty offenceAndPenalty197 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "b) Sells or disposes of other goods entered under rebate of duty without obtaining the permission of the Commissioner and payment of the duty due", 1500, "25% of underpayment, minimum of R1 500 ", 1500, " 25% of value, minimum of R1 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty197);

        OffenceAndPenalty offenceAndPenalty198 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "c) Sells or disposes of other goods entered under rebate of duty without obtaining the permission of the Commissioner and payment of the duty due", 0.00, " ", 2500, "50% of value, minimum of R2 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty198);

        OffenceAndPenalty offenceAndPenalty199 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(5)(a)(i)", "d) Uses goods entered under rebate of duty otherwise than in accordance with the item under which entry was made", 1500, "25% of underpayment, minimum of R1 500 ", 2500, "50% of value, minimum of R2 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty199);

        OffenceAndPenalty offenceAndPenalty200 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.06", "Moves goods to non-registered premises for further processing or operation without permission of the Controller / Branch Manager", 1500, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty200);

        OffenceAndPenalty offenceAndPenalty201 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(19)", "Diverts goods entered under rebate of duty without the permission of the Commissioner to a destination other than the destination declared on the rebate entry or delivers such goods or causes such goods to be delivered contrary to the provisions of this Act", 1500, "25% of underpayment, minimum of R1 500 ", 2500, "50% of value, minimum of R2 500 ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty201);

        OffenceAndPenalty offenceAndPenalty202 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Section 75(21)", "Keeps goods in a rebate store for longer than two (2) years", 1500, "R1 500 per consignment or part thereof", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty202);

        OffenceAndPenalty offenceAndPenalty203 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.10", "Keeps goods in his / her rebate store that have not been entered under the provisions of Schedule 3, 4 or 6", 5000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty203);

        OffenceAndPenalty offenceAndPenalty204 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.14", "Fails to maintain a stock record as prescribed by Rule", 4000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty204);

        OffenceAndPenalty offenceAndPenalty205 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REBATES_OF_DUTY, "Rule 75.15", "Does not keep proper / complete record of all receipts into or withdrawals from the rebate store", 5000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty205);

        OffenceAndPenalty offenceAndPenalty206 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 80(1)(h)", "Without lawful excuse (the proof of which shall rest upon him / her) brings into South Africa or has in his / her possession any blank or incomplete invoice or any billhead or other similar document capable of being filled up and used as an invoice for goods from outside South Africa", 500, "R500 per invoice / bill, minimum of R1 500 ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty206);

        OffenceAndPenalty offenceAndPenalty207 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 84(1)", "Makes a false statement in connection with any matter dealt with in the Act, or who makes use for the purposes of this Act of a declaration or document containing any such statement", 2000, " ", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty207);

        OffenceAndPenalty offenceAndPenalty208 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 86(a)", "Fails to advise the Controller / Branch Manager of any amended invoice, debit or credit note that increases the duty", 1500, " 25% of underpayment, minimum of R1 500", 500, " 50% of value, minimum of R 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty208);

        OffenceAndPenalty offenceAndPenalty209 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.SERIOUS_OFFENCES, "Section 88(1)", "Damages, destroys or disposes of any goods to prevent the securing or seizure thereof under the provisions of the Act or without permission, takes back any goods that are detained or have been seized", 1500, " 25% of underpayment, minimum of R1 500", 2500, " 50% of value, minimum of R 500", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty209);

        OffenceAndPenalty offenceAndPenalty210 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "a) Does not submit EUR1 certificate, DA 59, DCC, PAA or SIPC to claim preferential treatment at the time of refund application", 1500, " 25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty210);

        OffenceAndPenalty offenceAndPenalty211 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "b) Incorrect tariff classification on the refund voucher of correction but correct as originally entered on the declaration", 1500, " 25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty211);

        OffenceAndPenalty offenceAndPenalty212 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "c) Incorrect tariff classification on the voucher of correction as well as on the original declaration – Tariff Section has advised that a third tariff heading is applicable", 1500, " 25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty212);

        OffenceAndPenalty offenceAndPenalty213 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "d) Refund submitted in terms of a ITAC permit which indicated specific dates for declaration to be used - declaration date on which the refund is based is not within the prescribed period therefore the applicant is not entitled to a refund", 1500, " 25% of underpayment, minimum of R1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty213);

        OffenceAndPenalty offenceAndPenalty214 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "e) Refund submitted in terms of tariff amendment that is retrospectively backdated - assessment date not falling within the dates as specified in the amendment therefore the agent is not entitled to a refund", 1500, " 25% of over claim, minimum of R 1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty214);

        OffenceAndPenalty offenceAndPenalty215 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "f) Agent / importer uses one (1) method (rate of exchange or factor) when calculating duties to frame declaration and uses a different method to calculate the claim amount when applying for a refund", 1500, " 25% of over claim, minimum of R 1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty215);

        OffenceAndPenalty offenceAndPenalty216 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "g) Agent / importer claims refund of duties on goods used in manufacturing process but goods have not been exported (proof of export not provided or not adequate)", 1500, " 25% of over claim, minimum of R 1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty216);

        OffenceAndPenalty offenceAndPenalty217 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "h) The agent / importer claims refund of duties on goods which were incorrectly supplied, or were faulty but the goods have not been cleared for home consumption (false documents)", 1500, " 25% of over claim, minimum of R 1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty217);

        OffenceAndPenalty offenceAndPenalty218 = BootStrapHelper.getOffenceAndPenalty(user, OffenceDescriptionType.REFUNDS_OR_DRAWBACKS, "Sections 75, 76 and 76B read with 113(2)", "h) The agent / importer claims refund of duties on goods which were incorrectly supplied, or were faulty but the goods have not been cleared for home consumption (false documents)", 1500, " 25% of over claim, minimum of R 1 500", 0.00, " ", 0.00, CaseType.VDDL);
        offenceAndPenaltyService.save(offenceAndPenalty218);

    }

    @Test
    public void testScenarioS() {
        Duty excise1 = BootStrapHelper.getDuty("Duty Schedule 1 Part 2A", DutyType.EXCISE);
        dutyService.save(excise1);
        Duty excise2 = BootStrapHelper.getDuty("Duty Schedule 1 Part 2B", DutyType.EXCISE);
        dutyService.save(excise2);

        Duty customs1 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3A", DutyType.CUSTOM);
        Duty customs2 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3B", DutyType.CUSTOM);
        Duty customs3 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3C", DutyType.CUSTOM);
        Duty customs4 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3D", DutyType.CUSTOM);
        Duty customs5 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3D", DutyType.CUSTOM);
        Duty customs6 = BootStrapHelper.getDuty("Ordinary Customs Duty", DutyType.CUSTOM);
        Duty customs7 = BootStrapHelper.getDuty("Duty Schedule 2 Part 1", DutyType.CUSTOM);
        Duty customs8 = BootStrapHelper.getDuty("Duty Schedule 2 Part 2", DutyType.CUSTOM);
        Duty customs9 = BootStrapHelper.getDuty("Duty Schedule 2 Part 3", DutyType.CUSTOM);
        dutyService.save(customs1);
        dutyService.save(customs2);
        dutyService.save(customs3);
        dutyService.save(customs4);
        dutyService.save(customs5);
        dutyService.save(customs6);
        dutyService.save(customs7);
        dutyService.save(customs8);
        dutyService.save(customs9);

        Duty other1 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3A", DutyType.OTHER);
        Duty other2 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3B", DutyType.OTHER);
        Duty other3 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3C", DutyType.OTHER);
        Duty other4 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3D", DutyType.OTHER);
        Duty other5 = BootStrapHelper.getDuty("Duty Schedule 1 Part 3E", DutyType.OTHER);
        Duty other6 = BootStrapHelper.getDuty("Diamond Export Levy Amount", DutyType.OTHER);
        Duty other7 = BootStrapHelper.getDuty("Duty Schedule 1 Part 7A", DutyType.OTHER);
        dutyService.save(other1);
        dutyService.save(other2);
        dutyService.save(other3);
        dutyService.save(other4);
        dutyService.save(other5);
        dutyService.save(other6);
        dutyService.save(other7);
    }

    @Ignore
    @Test
    public void testScenarioU() {
        SuspiciousCase suspiciousCase = suspiciousCaseService.listAll().iterator().next();

        RevenueLiabilityDuty excise1 = new RevenueLiabilityDuty();
        excise1.setCreatedBy("s2024726");
        excise1.setCreatedDate(new Date());
        excise1.setAmount(5000.00);
        excise1.setDuty(dutyService.findByDutyType(DutyType.EXCISE).iterator().next());
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(excise1);

        RevenueLiabilityDuty excise2 = new RevenueLiabilityDuty();
        excise2.setCreatedBy("s2024726");
        excise2.setCreatedDate(new Date());
        excise2.setAmount(2000.00);
        excise2.setDuty(dutyService.findByDutyType(DutyType.EXCISE).iterator().next());
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(excise2);

        RevenueLiabilityDuty custom1 = new RevenueLiabilityDuty();
        custom1.setCreatedBy("s2024726");
        custom1.setCreatedDate(new Date());
        custom1.setAmount(3000.00);
        custom1.setDuty(dutyService.findByDutyType(DutyType.CUSTOM).iterator().next());
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(custom1);

        RevenueLiabilityDuty custom2 = new RevenueLiabilityDuty();
        custom2.setCreatedBy("s2024726");
        custom2.setCreatedDate(new Date());
        custom2.setAmount(4000.00);
        custom2.setDuty(dutyService.findByDutyType(DutyType.CUSTOM).iterator().next());
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(custom2);

        RevenueLiabilityDuty other1 = new RevenueLiabilityDuty();
        other1.setCreatedBy("s2024726");
        other1.setCreatedDate(new Date());
        other1.setAmount(9000.00);
        other1.setDuty(dutyService.findByDutyType(DutyType.OTHER).iterator().next());
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(other1);

        RevenueLiabilityDuty other2 = new RevenueLiabilityDuty();
        other2.setCreatedBy("s2024726");
        other2.setCreatedDate(new Date());
        other2.setAmount(15000.00);
        other2.setDuty(dutyService.findByDutyType(DutyType.OTHER).iterator().next());
        suspiciousCase.getAuditPlan().getAuditReport().getRevenueLiability().addRevenueLiabilityDuty(other2);
        suspiciousCaseService.update(suspiciousCase);
    }

    @Test
    public void testScenarioV() {
        OffenceClassification offenceClassification1 = BootStrapHelper.getOffenceClassification("Ignoring the instructions or actions of an officer – Sections 4", CaseType.SAR);
        classificationervice.save(offenceClassification1);
        OffenceClassification offenceClassification2 = BootStrapHelper.getOffenceClassification("Ignoring the instructions of an officer regarding the movement of persons and/or goods in the Customs controlled area – Section 6A", CaseType.SAR);
        classificationervice.save(offenceClassification2);
        OffenceClassification offenceClassification3 = BootStrapHelper.getOffenceClassification("Failure to comply with arrival and departure reports - Sections 7 and 8", CaseType.SAR);
        classificationervice.save(offenceClassification3);
        OffenceClassification offenceClassification4 = BootStrapHelper.getOffenceClassification("Failure to declare sealable goods – Section 9", CaseType.SAR);
        classificationervice.save(offenceClassification4);
        OffenceClassification offenceClassification5 = BootStrapHelper.getOffenceClassification("Landing of unentered goods – Section 11", CaseType.SAR);
        classificationervice.save(offenceClassification5);

        OffenceClassification offenceClassification6 = BootStrapHelper.getOffenceClassification("Reporting of vehicles after arrival in South Africa– Section 12", CaseType.SAR);
        classificationervice.save(offenceClassification6);

        OffenceClassification offenceClassification7 = BootStrapHelper.getOffenceClassification("Failure to comply with the conditions of goods imported by post – Section 13", CaseType.SAR);
        classificationervice.save(offenceClassification7);

        OffenceClassification offenceClassification8 = BootStrapHelper.getOffenceClassification("Goods not declared or under-declared by persons entering or leaving South Africa\n"
                + "– Section 15", CaseType.SAR);
        classificationervice.save(offenceClassification8);

        OffenceClassification offenceClassification9 = BootStrapHelper.getOffenceClassification("Misuse of temporary import permit / carnet – Section 15, 18, 18(A) and 38", CaseType.SAR);
        classificationervice.save(offenceClassification9);

        OffenceClassification offenceClassification10 = BootStrapHelper.getOffenceClassification("Entering of goods removed in bond – Section 18 and 18A", CaseType.SAR);
        classificationervice.save(offenceClassification10);
        OffenceClassification offenceClassification11 = BootStrapHelper.getOffenceClassification("Customs and Excise warehouses – Section 19 – 26", CaseType.SAR);
        classificationervice.save(offenceClassification11);
        OffenceClassification offenceClassification12 = BootStrapHelper.getOffenceClassification("Failure to comply with the conditions for the entry of goods – Sections 12, 18A, 38 – 41, 44, 47, 54, 113 and 119A ", CaseType.SAR);
        classificationervice.save(offenceClassification12);

        OffenceClassification offenceClassification13 = BootStrapHelper.getOffenceClassification("Registration – Section 59A and Rule 59A.03", CaseType.SAR);
        classificationervice.save(offenceClassification13);

        OffenceClassification offenceClassification14 = BootStrapHelper.getOffenceClassification("Licences – Section 60 – 64D and 64G", CaseType.SAR);
        classificationervice.save(offenceClassification14);

        OffenceClassification offenceClassification15 = BootStrapHelper.getOffenceClassification("Valuation", CaseType.SAR);
        classificationervice.save(offenceClassification15);

        OffenceClassification offenceClassification16 = BootStrapHelper.getOffenceClassification("Rebates of duty – Section 75", CaseType.SAR);
        classificationervice.save(offenceClassification16);

        OffenceClassification offenceClassification17 = BootStrapHelper.getOffenceClassification("Serious offences – Section 80, 84, 86 and 88", CaseType.SAR);
        classificationervice.save(offenceClassification17);

        OffenceClassification offenceClassification18 = BootStrapHelper.getOffenceClassification("Refunds / drawbacks – Section 75, 76, 76B and 113", CaseType.SAR);
        classificationervice.save(offenceClassification18);

        OffenceClassification offenceClassification19 = BootStrapHelper.getOffenceClassification("Keeping of books, accounts and documents – Section 101", CaseType.SAR);
        classificationervice.save(offenceClassification19);

        OffenceClassification offenceClassification20 = BootStrapHelper.getOffenceClassification("Electronic submission – Section Rule 101A.01A(2)(a)", CaseType.SAR);
        classificationervice.save(offenceClassification20);

        OffenceClassification offenceClassification21 = BootStrapHelper.getOffenceClassification("Conditions for the release of goods under Customs control – Section 107", CaseType.SAR);
        classificationervice.save(offenceClassification21);

        OffenceClassification offenceClassification22 = BootStrapHelper.getOffenceClassification("Goods subject to a lien – Section 114", CaseType.SAR);
        classificationervice.save(offenceClassification22);

        OffenceClassification offenceClassification24 = BootStrapHelper.getOffenceClassification("Reporting of goods brought into the country overland on foot – Section 12", CaseType.SAR);
        classificationervice.save(offenceClassification24);

    }

    @Test
    public void testScenarioW() {
        OffenceClassification offenceClassification1 = classificationervice.findByDescription("Ignoring the instructions or actions of an officer – Sections 4");
        SectionRule sectionRule1 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(7)");
        sectionRuleService.save(sectionRule1);

        SectionRule sectionRule2 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(8A)(a) and (b)");
        sectionRuleService.save(sectionRule2);

        SectionRule sectionRule3 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(10)");
        sectionRuleService.save(sectionRule3);

        SectionRule sectionRule4 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(12)");
        sectionRuleService.save(sectionRule4);

        SectionRule sectionRule5 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(12A)");
        sectionRuleService.save(sectionRule5);

        OffenceClassification offenceClassification2 = classificationervice.findByDescription("Ignoring the instructions of an officer regarding the movement of persons and/or goods in the Customs controlled area – Section 6A");
        SectionRule sectionRule6 = BootStrapHelper.getSectionRule(offenceClassification2, "Section 6A");
        sectionRuleService.save(sectionRule6);

        OffenceClassification offenceClassification3 = classificationervice.findByDescription("Failure to comply with arrival and departure reports - Sections 7 and 8");
        SectionRule sectionRule7 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(1)");
        sectionRuleService.save(sectionRule7);

        SectionRule sectionRule8 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(1B) and Rule 7.01");
        sectionRuleService.save(sectionRule8);

        SectionRule sectionRule9 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(1A)");
        sectionRuleService.save(sectionRule9);

        SectionRule sectionRule10 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(3)");
        sectionRuleService.save(sectionRule10);

        SectionRule sectionRule11 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(6)");
        sectionRuleService.save(sectionRule11);

        SectionRule sectionRule12 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(7)");
        sectionRuleService.save(sectionRule12);

        SectionRule sectionRule13 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 8 and Rules 8.04, 8.05, 8.06, 8.07 and 8.08");
        sectionRuleService.save(sectionRule13);

        OffenceClassification offenceClassification4 = classificationervice.findByDescription("Failure to declare sealable goods – Section 9");
        SectionRule sectionRule14 = BootStrapHelper.getSectionRule(offenceClassification4, "Section 9(1)");
        sectionRuleService.save(sectionRule14);

        OffenceClassification offenceClassification5 = classificationervice.findByDescription("Landing of unentered goods – Section 11");
        SectionRule sectionRule15 = BootStrapHelper.getSectionRule(offenceClassification5, "Section 11(1)");
        sectionRuleService.save(sectionRule15);

        OffenceClassification offenceClassification6 = classificationervice.findByDescription("Reporting of vehicles after arrival in South Africa– Section 12");
        SectionRule sectionRule16 = BootStrapHelper.getSectionRule(offenceClassification6, "Section 12(3)(a)");
        sectionRuleService.save(sectionRule16);

        OffenceClassification offenceClassification7 = classificationervice.findByDescription("Reporting of goods brought into the country overland on foot – Section 12");
        SectionRule sectionRule17 = BootStrapHelper.getSectionRule(offenceClassification7, "Section 12(5)(a), (b), (c)");
        sectionRuleService.save(sectionRule17);

        OffenceClassification offenceClassification8 = classificationervice.findByDescription("Failure to comply with the conditions of goods imported by post – Section 13");
        SectionRule sectionRule18 = BootStrapHelper.getSectionRule(offenceClassification8, "Section 13(5)");
        sectionRuleService.save(sectionRule18);

        OffenceClassification offenceClassification9 = classificationervice.findByDescription("Goods not declared or under-declared by persons entering or leaving South Africa\n"
                + "– Section 15");
        SectionRule sectionRule19 = BootStrapHelper.getSectionRule(offenceClassification9, "Section 15(1) and (2)");
        sectionRuleService.save(sectionRule19);

        OffenceClassification offenceClassification10 = classificationervice.findByDescription("Misuse of temporary import permit / carnet – Section 15, 18, 18(A) and 38");
        SectionRule sectionRule20 = BootStrapHelper.getSectionRule(offenceClassification10, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01");
        sectionRuleService.save(sectionRule20);

        OffenceClassification offenceClassification11 = classificationervice.findByDescription("Entering of goods removed in bond – Section 18 and 18A");
        SectionRule sectionRule21 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(3) read with Rule 18.07(a)");
        sectionRuleService.save(sectionRule21);

        SectionRule sectionRule22 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(3) read with Rule 18.07(b)");
        sectionRuleService.save(sectionRule22);

        SectionRule sectionRule23 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10");
        sectionRuleService.save(sectionRule23);

        SectionRule sectionRule24 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(8)");
        sectionRuleService.save(sectionRule24);

        SectionRule sectionRule25 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(13)");
        sectionRuleService.save(sectionRule25);

        OffenceClassification offenceClassification12 = classificationervice.findByDescription("Customs and Excise warehouses – Section 19 – 26");
        SectionRule sectionRule26 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 19.02");
        sectionRuleService.save(sectionRule26);

        SectionRule sectionRule27 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 19(3)");
        sectionRuleService.save(sectionRule27);

        SectionRule sectionRule28 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 19(4) and 20(5)");
        sectionRuleService.save(sectionRule28);

        SectionRule sectionRule29 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 19(9)");
        sectionRuleService.save(sectionRule29);

        SectionRule sectionRule30 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 19.05");
        sectionRuleService.save(sectionRule30);

        SectionRule sectionRule31 = BootStrapHelper.getSectionRule(offenceClassification12, "Rules 19.07");
        sectionRuleService.save(sectionRule31);

        SectionRule sectionRule32 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 20.03");
        sectionRuleService.save(sectionRule32);

        SectionRule sectionRule33 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 20(4)");
        sectionRuleService.save(sectionRule33);

        SectionRule sectionRule34 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 20(4)(bis)");
        sectionRuleService.save(sectionRule34);

        SectionRule sectionRule35 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 20.08");
        sectionRuleService.save(sectionRule35);

        SectionRule sectionRule36 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 26");
        sectionRuleService.save(sectionRule36);

        OffenceClassification offenceClassification13 = classificationervice.findByDescription("Failure to comply with the conditions for the entry of goods – Sections 12, 18A, 38 – 41, 44, 47, 54, 113 and 119A ");
        SectionRule sectionRule37 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 12(7)");
        sectionRuleService.save(sectionRule37);

        SectionRule sectionRule38 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 18A(3)");
        sectionRuleService.save(sectionRule38);

        SectionRule sectionRule39 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 18A(4)");
        sectionRuleService.save(sectionRule39);

        SectionRule sectionRule40 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 18A(9)");
        sectionRuleService.save(sectionRule40);

        SectionRule sectionRule41 = BootStrapHelper.getSectionRule(offenceClassification13, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)");
        sectionRuleService.save(sectionRule41);

        SectionRule sectionRule42 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 39(1)");
        sectionRuleService.save(sectionRule42);

        SectionRule sectionRule43 = BootStrapHelper.getSectionRule(offenceClassification13, "Rule 39.08");
        sectionRuleService.save(sectionRule43);

        SectionRule sectionRule44 = BootStrapHelper.getSectionRule(offenceClassification13, "Sections 38(1), 39(1), 40(1),  44(10) and 47(1) read with Section 113(2)");
        sectionRuleService.save(sectionRule44);

        SectionRule sectionRule45 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 40(1)");
        sectionRuleService.save(sectionRule45);

        SectionRule sectionRule46 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 41");
        sectionRuleService.save(sectionRule46);

        SectionRule sectionRule47 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 54 read with Rule 54.01, 02");
        sectionRuleService.save(sectionRule47);

        SectionRule sectionRule48 = BootStrapHelper.getSectionRule(offenceClassification13, "Sections 119A and 39(1)(c) read with Rules 119A and 39(2B).02");
        sectionRuleService.save(sectionRule48);

        OffenceClassification offenceClassification14 = classificationervice.findByDescription("Registration – Section 59A and Rule 59A.03");
        SectionRule sectionRule49 = BootStrapHelper.getSectionRule(offenceClassification14, "Section 59A read with Rule 59A");
        sectionRuleService.save(sectionRule49);

        OffenceClassification offenceClassification15 = classificationervice.findByDescription("Licences – Section 60 – 64D and 64G");
        SectionRule sectionRule50 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64B read with 60");
        sectionRuleService.save(sectionRule50);

        SectionRule sectionRule51 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64B");
        sectionRuleService.save(sectionRule51);

        SectionRule sectionRule52 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64C");
        sectionRuleService.save(sectionRule52);

        SectionRule sectionRule53 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64D(1) read with 60(1)");
        sectionRuleService.save(sectionRule53);

        SectionRule sectionRule54 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64D(1) and (7) read with 60(1) and Rules 64D.10(5) and 64D.11(5)");
        sectionRuleService.save(sectionRule54);

        OffenceClassification offenceClassification16 = classificationervice.findByDescription("Valuation");
        SectionRule sectionRule55 = BootStrapHelper.getSectionRule(offenceClassification16, "Sections 65, 66, 67 read with 39(1)");
        sectionRuleService.save(sectionRule55);

        OffenceClassification offenceClassification17 = classificationervice.findByDescription("Rebates of duty – Section 75");
        SectionRule sectionRule56 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(1)(b)");
        sectionRuleService.save(sectionRule56);

        SectionRule sectionRule57 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(5)(a)(i)");
        sectionRuleService.save(sectionRule57);

        SectionRule sectionRule58 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.06");
        sectionRuleService.save(sectionRule58);

        SectionRule sectionRule59 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(19)");
        sectionRuleService.save(sectionRule59);

        SectionRule sectionRule60 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(21)");
        sectionRuleService.save(sectionRule60);

        SectionRule sectionRule61 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.10");
        sectionRuleService.save(sectionRule61);

        SectionRule sectionRule62 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.14");
        sectionRuleService.save(sectionRule62);

        SectionRule sectionRule63 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.15");
        sectionRuleService.save(sectionRule63);

        OffenceClassification offenceClassification18 = classificationervice.findByDescription("Serious offences – Section 80, 84, 86 and 88");
        SectionRule sectionRule64 = BootStrapHelper.getSectionRule(offenceClassification18, "Section 80(1)(h)");
        sectionRuleService.save(sectionRule64);

        SectionRule sectionRule65 = BootStrapHelper.getSectionRule(offenceClassification18, "Section 86(a)");
        sectionRuleService.save(sectionRule65);

        SectionRule sectionRule66 = BootStrapHelper.getSectionRule(offenceClassification18, "Section 84(1)");
        sectionRuleService.save(sectionRule66);

        SectionRule sectionRule67 = BootStrapHelper.getSectionRule(offenceClassification18, "Section 88(1)");
        sectionRuleService.save(sectionRule67);

        SectionRule sectionRule68 = BootStrapHelper.getSectionRule(offenceClassification18, "Section 88(1)(bA)");
        sectionRuleService.save(sectionRule68);

        OffenceClassification offenceClassification19 = classificationervice.findByDescription("Refunds / drawbacks – Section 75, 76, 76B and 113");
        SectionRule sectionRule69 = BootStrapHelper.getSectionRule(offenceClassification19, "Sections 75, 76 and 76B read with 113(2)");
        sectionRuleService.save(sectionRule69);

        SectionRule sectionRule70 = BootStrapHelper.getSectionRule(offenceClassification19, "Section 76(1)");
        sectionRuleService.save(sectionRule70);

        SectionRule sectionRule71 = BootStrapHelper.getSectionRule(offenceClassification19, "Section 76B");
        sectionRuleService.save(sectionRule71);

        OffenceClassification offenceClassification20 = classificationervice.findByDescription("Keeping of books, accounts and documents – Section 101");
        SectionRule sectionRule72 = BootStrapHelper.getSectionRule(offenceClassification20, "Section 101(1)(a) and (2) read with Rule 101.01");
        sectionRuleService.save(sectionRule72);

        SectionRule sectionRule73 = BootStrapHelper.getSectionRule(offenceClassification20, "Section 101(2B)");
        sectionRuleService.save(sectionRule73);

        OffenceClassification offenceClassification21 = classificationervice.findByDescription("Electronic submission – Section Rule 101A.01A(2)(a)");
        SectionRule sectionRule74 = BootStrapHelper.getSectionRule(offenceClassification21, "Rule 101A.01A(2)(a)");
        sectionRuleService.save(sectionRule74);

        OffenceClassification offenceClassification22 = classificationervice.findByDescription("Conditions for the release of goods under Customs control – Section 107");
        SectionRule sectionRule75 = BootStrapHelper.getSectionRule(offenceClassification22, "Section 107(2)(a)");
        sectionRuleService.save(sectionRule75);

        OffenceClassification offenceClassification23 = classificationervice.findByDescription("Goods subject to a lien – Section 114");
        SectionRule sectionRule76 = BootStrapHelper.getSectionRule(offenceClassification23, "Section 114(2A)");
        sectionRuleService.save(sectionRule76);

    }

    @Test
    public void testScenarioX() {
        SectionRule sectionRule76 = sectionRuleService.findByDescription("Section 114(2A)");
        OffenceDescription offenceDescription1 = BootStrapHelper.getOffenceDescription(sectionRule76, "Removes goods subject to a lien [Section 114(1)(aA) or goods so detained Section 114(2)] ", 1500.00, "", "25% of value of goods subject  to the lien, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription1);

        SectionRule sectionRule77 = sectionRuleService.findByDescription("Section 107(2)(a)");
        OffenceDescription offenceDescription2 = BootStrapHelper.getOffenceDescription(sectionRule77, "Fails to comply with the conditions determined by the Commissioner for allowing goods detained to pass from his / her control", 1500.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription2);

        SectionRule sectionRule78 = sectionRuleService.findByDescription("Rule 101A.01A(2)(a)");
        OffenceDescription offenceDescription3 = BootStrapHelper.getOffenceDescription(sectionRule78, "Fails to submit documents electronically, unless exempted temporarily or permanently by the Commissioner.", 5000.00, "", "R5 000 per declaration / report / list", 0.00);
        offenceDescriptionService.save(offenceDescription3);

        SectionRule sectionRule79 = sectionRuleService.findByDescription("Section 101(2B)");
        OffenceDescription offenceDescription4 = BootStrapHelper.getOffenceDescription(sectionRule79, "Fails to keep or produce on demand any data created by means of a computer as defined in Section 1 of the Computer Evidence Act, 1983", 5000.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription4);

        SectionRule sectionRule80 = sectionRuleService.findByDescription("Section 101(1)(a) and (2) read with Rule 101.01");
        OffenceDescription offenceDescription5 = BootStrapHelper.getOffenceDescription(sectionRule80, "Fails to produce the prescribed goods,\n"
                + "accounts or documents on demand", 5000.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription5);

        OffenceDescription offenceDescription6 = BootStrapHelper.getOffenceDescription(sectionRule80, "Fails to keep the prescribed books, accounts or documents", 5000.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription6);

        SectionRule sectionRule81 = sectionRuleService.findByDescription("Section 76B");
        OffenceDescription offenceDescription7 = BootStrapHelper.getOffenceDescription(sectionRule81, "Time expired claim", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription7);

        SectionRule sectionRule82 = sectionRuleService.findByDescription("Section 76(1)");
        OffenceDescription offenceDescription8 = BootStrapHelper.getOffenceDescription(sectionRule82, "Submits a duplicate application for refund of duty or other charge to the Controller / Branch Manager for an amount that has already been refunded", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription8);

        SectionRule sectionRule83 = sectionRuleService.findByDescription("Sections 75, 76 and 76B read with 113(2)");
        OffenceDescription offenceDescription9 = BootStrapHelper.getOffenceDescription(sectionRule83, "Submitting an application for a refund / drawback for an amount on a declaration which was originally not paid", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription9);

        OffenceDescription offenceDescription10 = BootStrapHelper.getOffenceDescription(sectionRule83, "Resubmitting an application for a refund / drawback previously rejected without following the instructions of the officer", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription10);

        OffenceDescription offenceDescription11 = BootStrapHelper.getOffenceDescription(sectionRule83, "Fails to produce sufficient proof of export", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription11);

        OffenceDescription offenceDescription12 = BootStrapHelper.getOffenceDescription(sectionRule83, "Submit false proof of export to obtain refund of duties", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription12);

        OffenceDescription offenceDescription13 = BootStrapHelper.getOffenceDescription(sectionRule83, "The agent / importer claims refund of duties on goods which were incorrectly supplied, or were faulty but the goods have not been cleared for home consumption (false documents)", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription13);

        OffenceDescription offenceDescription14 = BootStrapHelper.getOffenceDescription(sectionRule83, "Agent / importer claims refund of duties on goods used in manufacturing process but goods have not been exported (proof of export not provided or not adequate)", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription14);

        OffenceDescription offenceDescription15 = BootStrapHelper.getOffenceDescription(sectionRule83, "Agent / importer uses one (1) method (rate of exchange or factor) when calculating duties to frame declaration and uses a different method to calculate the claim amount when applying for a refund", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription15);

        OffenceDescription offenceDescription16 = BootStrapHelper.getOffenceDescription(sectionRule83, "Refund submitted in terms of tariff amendment that is retrospectively backdated - assessment date not falling within the dates as specified in the amendment therefore the agent is not entitled to a refund", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription16);

        OffenceDescription offenceDescription17 = BootStrapHelper.getOffenceDescription(sectionRule83, "Refund submitted in terms of a ITAC permit which indicated specific dates for declaration to be used - declaration date on which the refund is based is not within the prescribed period therefore the applicant is not entitled to a refund", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription17);

        OffenceDescription offenceDescription18 = BootStrapHelper.getOffenceDescription(sectionRule83, "Incorrect tariff classification on the voucher of correction as well as on the original declaration – Tariff Section has advised that a third tariff heading is applicable", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription18);

        OffenceDescription offenceDescription19 = BootStrapHelper.getOffenceDescription(sectionRule83, "Incorrect tariff classification on the refund voucher of correction but correct as originally entered on the declaration", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription19);

        OffenceDescription offenceDescription20 = BootStrapHelper.getOffenceDescription(sectionRule83, "Does not submit EUR1 certificate, DA 59, DCC, PAA or SIPC to claim preferential treatment at the time of refund application", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription20);
    }

    @Test
    public void testScenarioY() {

    }

    @Test
    public void testScenarioZ() {

    }

    @Test
    public void testScenarioAA() {

    }
}
