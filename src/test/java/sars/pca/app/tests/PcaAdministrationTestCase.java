package sars.pca.app.tests;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sars.pca.app.common.CaseType;
import sars.pca.app.common.DutyType;
import sars.pca.app.common.NotificationType;
import sars.pca.app.common.OfficeType;
import sars.pca.app.common.Province;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.config.TestDataSourceConfiguration;
import sars.pca.app.domain.Duty;
import sars.pca.app.domain.EmailNotification;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.domain.LocationOffice;
import sars.pca.app.domain.OffenceClassification;
import sars.pca.app.domain.OffenceDescription;
import sars.pca.app.domain.PrioritisationScore;
import sars.pca.app.domain.Program;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.domain.RiskRatingConsequence;
import sars.pca.app.domain.RiskRatingLikelihood;
import sars.pca.app.domain.SectionRule;
import sars.pca.app.domain.SlaConfiguration;
import sars.pca.app.domain.User;
import sars.pca.app.domain.UserRole;
import sars.pca.app.service.DutyServiceLocal;
import sars.pca.app.service.EmailNotificationServiceLocal;
import sars.pca.app.service.HsChapterRiskServiceLocal;
import sars.pca.app.service.LocationOfficeServiceLocal;
import sars.pca.app.service.OffenceClassificationServiceLocal;
import sars.pca.app.service.OffenceDescriptionServiceLocal;
import sars.pca.app.service.PrioritisationScoreServiceLocal;
import sars.pca.app.service.ProgramServiceLocal;
import sars.pca.app.service.RelevantMaterialServiceLocal;
import sars.pca.app.service.RiskRatingConsequenceServiceLocal;
import sars.pca.app.service.RiskRatingLikelihoodServiceLocal;
import sars.pca.app.service.SectionRuleServiceLocal;
import sars.pca.app.service.UserRoleServiceLocal;
import sars.pca.app.service.UserServiceLocal;
import sars.pca.app.service.SlaConfigurationServiceLocal;

/**
 *
 * @author S2026987
 */
@EnableJpaAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestDataSourceConfiguration.class)
public class PcaAdministrationTestCase {

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
    private DutyServiceLocal dutyService;

    @Autowired
    private OffenceClassificationServiceLocal classificationervice;
    @Autowired
    private SectionRuleServiceLocal sectionRuleService;
    @Autowired
    private OffenceDescriptionServiceLocal offenceDescriptionService;
    @Autowired
    private PrioritisationScoreServiceLocal prioritisationScoreService;
    @Autowired
    private RiskRatingLikelihoodServiceLocal riskRatingLikelihoodService;
    @Autowired
    private RiskRatingConsequenceServiceLocal riskRatingConsequenceService;

    //SuspiciousCase persistentSarCase;
    //StringBuilder builder = new StringBuilder();
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

        UserRole executiveRole = userRoleService.findByDescription("Administrator");
        User capturerUser2 = BootStrapHelper.getUser(executiveRole, "s2026080", "South Africa", "Andile", "Qumbisa", "Andile Qumbisa", "AQ", "9110105141088", "0669654428", "vongani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser2.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
      userService.save(capturerUser2);
//
//        UserRole seniorManagerRole = userRoleService.findByDescription("Senior Manager");
//        User capturerUser3 = BootStrapHelper.getUser(seniorManagerRole, "s2028873", "South Africa", "Amogelang", "Phangisa", "Amogelang Phangisa", "AP", "9110105141088", "0669654428", "vongani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
//        capturerUser3.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
//        capturerUser3.setManager(persistentExecutiveUser);
//        User persistentSeniorManager = userService.save(capturerUser3);

        UserRole groupManagerRole = userRoleService.findByDescription("Administrator");
        User capturerUser4 = BootStrapHelper.getUser(groupManagerRole, "s2028398", "South Africa", "Terry", "Ramurembiwa", "Terry Ramurembiwa", "TM", "9210105141088", "0669652328", "khensani@sars.gov.za", "www.sars.gov.za", "0669657728", "0669657728");
        capturerUser4.setLocationOffice(locationOfficeService.findByAreaAndOfficeType("Pretoria", OfficeType.CI_OFFICE));
//        capturerUser4.setManager(persistentSeniorManager);
       userService.save(capturerUser4);

       /* UserRole ciOpsManagerRole = userRoleService.findByDescription("CI Ops Manager");
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
        userService.save(capturerUser13);*/
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
        EmailNotification emailNotification = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_SAR_TO_QA, "This serves to notify you that case ", "has been sent to you for review on", "by", "");
        emailNotificationService.save(emailNotification);

        EmailNotification emailNotification1 = BootStrapHelper.getEmailNotification(NotificationType.DISAPROVED_DISCARDED_CASE, "This serves to notify you that the discarded SAR case ", "has been disapproved and sent to you on", "by", "");
        emailNotificationService.save(emailNotification1);

        EmailNotification emailNotification3 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_DUPLICATE_CASE, "This serves to notify you that case ", "has been sent to you for review on", "by", "");
        emailNotificationService.save(emailNotification3);

        EmailNotification emailNotification4 = BootStrapHelper.getEmailNotification(NotificationType.COMPLETE_RA_STAGE, "This serves to notify you that case ", "was allocated to you was updated with new noncompliance information. Please review the case and consider the updated noncompliance information.", "by", "");
        emailNotificationService.save(emailNotification4);

        EmailNotification emailNotification5 = BootStrapHelper.getEmailNotification(NotificationType.IN_DEPTH_ANALYSIS_STAGE, "This serves to notify you that case ", "was allocated to you was updated with new noncompliance information. Please review the case and consider the updated noncompliance information.", "by", "");
        emailNotificationService.save(emailNotification5);

        EmailNotification emailNotification6 = BootStrapHelper.getEmailNotification(NotificationType.RA_APPROVA_STAGE, "This serves to notify you that case NO. ", "was allocated to you and currently in the RA Approval stage was updated with new noncompliance information. Please review the case and the updated noncompliance details. If appropriate refer the case for rework in order for the updated noncompliance information to be considered in the risk assessment of the case", "", "");
        emailNotificationService.save(emailNotification6);

        EmailNotification emailNotification7 = BootStrapHelper.getEmailNotification(NotificationType.ALLOCATED_INVENTORY, "This serves to notify you that case ", "was allocated to you was updated with new noncompliance information. Please review the case and the updated noncompliance details", "by", "");
        emailNotificationService.save(emailNotification7);

//        EmailNotification emailNotification8 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_CASE_IN_DEPTH_ANALYSIS, "This serves to notify you that case ", "was allocated to you was updated with new noncompliance information. Please review the case and if appropriate disagree with the rejection of the case in order for the new noncompliance information to be considered in the risk assessment of the case", "by", "");
//        emailNotificationService.save(emailNotification8);
        EmailNotification emailNotification9 = BootStrapHelper.getEmailNotification(NotificationType.TECHNICAL_REVIEW, "This serves to notify you that case ", "was allocated to you and currently in your Technical Review Cases inventory was updated with new noncompliance information. Please review the case and if appropriate refer the case for rework in order for the new noncompliance information to be considered in the risk assessment of the case.", "", "");
        emailNotificationService.save(emailNotification9);

        EmailNotification emailNotification10 = BootStrapHelper.getEmailNotification(NotificationType.SAR_ALLOCATION, "This serves to notify you that case ", "was allocated to you on", "by", "");
        emailNotificationService.save(emailNotification10);

        EmailNotification emailNotification11 = BootStrapHelper.getEmailNotification(NotificationType.SAR_REALLOCATION, "This serves to notify you that case ", "was re-allocated to you on", "by", "");
        emailNotificationService.save(emailNotification11);

        EmailNotification emailNotification12 = BootStrapHelper.getEmailNotification(NotificationType.ALLOCATE_INDEPTH_ANALYSIS_QA, "This serves to notify you that case ", "was allocated to you for Quality Assurance on", "by", "");
        emailNotificationService.save(emailNotification12);

        EmailNotification emailNotification13 = BootStrapHelper.getEmailNotification(NotificationType.REWORK_INDEPTH_ANALYSIS, "This serves to notify you that case ", "has been sent to you for rework on", "by", "");
        emailNotificationService.save(emailNotification13);

        EmailNotification emailNotification14 = BootStrapHelper.getEmailNotification(NotificationType.APPROVE_DISCAREDED_RA, "This serves to notify you that the discarded RA/case ", "has been approved on", "by", "");
        emailNotificationService.save(emailNotification14);

        EmailNotification emailNotification15 = BootStrapHelper.getEmailNotification(NotificationType.SENT_BACK_INDEPTH_ANALYSIS, "This serves to notify you that the discarded SAR case ", "has been sent back to you on", "by", "");
        emailNotificationService.save(emailNotification15);

        EmailNotification emailNotification16 = BootStrapHelper.getEmailNotification(NotificationType.APPROVE_RA, "This serves to notify you that case ", "has been sent to you for review on ", "by", "");
        emailNotificationService.save(emailNotification16);

        EmailNotification emailNotification17 = BootStrapHelper.getEmailNotification(NotificationType.REWORK_RA, "This serves to notify you that case ", "has been sent back to you for rework on", "by", "");
        emailNotificationService.save(emailNotification17);

        EmailNotification emailNotification18 = BootStrapHelper.getEmailNotification(NotificationType.QA_TECHNICAL_REVIEW, "This serves to notify you that case ", "has been sent to you for review on", "by", "");
        emailNotificationService.save(emailNotification18);

        EmailNotification emailNotification19 = BootStrapHelper.getEmailNotification(NotificationType.APPROVED_REJECT_TECHNICAL_REVIEW, "This serves to notify you that the rejected case", "has been approved on", "by", "");
        emailNotificationService.save(emailNotification19);

        EmailNotification emailNotification20 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_REJECT_TECHNICAL_REVIEW, "This serves to notify you that the rejected case", "has been disapproved on", "by", "");
        emailNotificationService.save(emailNotification20);

//        EmailNotification emailNotification21 = BootStrapHelper.getEmailNotification(NotificationType.REWORK_RA, "This serves to notify you that the case", "has been sent to you for rework on", "by", "");
//        emailNotificationService.save(emailNotification21);
        EmailNotification emailNotification22 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_RA_REWORK, "This serves to notify you that the rejected case", "has been sent to you for rework on", "by", "");
        emailNotificationService.save(emailNotification22);

        EmailNotification emailNotification23 = BootStrapHelper.getEmailNotification(NotificationType.CI_ALLOCATION, "This serves to notify you that the case", "was allocated to you on", "by", "");
        emailNotificationService.save(emailNotification23);

        EmailNotification emailNotification24 = BootStrapHelper.getEmailNotification(NotificationType.CI_REALLOCATION, "This serves to notify you that the case", "was re-allocated to you on", "by", "");
        emailNotificationService.save(emailNotification24);

        EmailNotification emailNotification25 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_REVIEW, "This serves to notify you that the audit plan for case ", "has been made available for you to review on", "by", "");
        emailNotificationService.save(emailNotification25);

        EmailNotification emailNotification26 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_SENT_FOR_APPROVAL, "This serves to notify you that the reviewed audit plan for case", "has been sent to you for approval on", "by", "");
        emailNotificationService.save(emailNotification26);

        EmailNotification emailNotification27 = BootStrapHelper.getEmailNotification(NotificationType.REWORK_AUDIT_PLAN, "This serves to notify you that audit plan for case", "has been sent back to you for rework on", "by", "");
        emailNotificationService.save(emailNotification27);

        EmailNotification emailNotification28 = BootStrapHelper.getEmailNotification(NotificationType.REJECT_AUDIT_PLAN, "This serves to notify you that a rejected audit plan for case", "has been sent to you for approval or disapproval on", "by", "");
        emailNotificationService.save(emailNotification28);

        EmailNotification emailNotification29 = BootStrapHelper.getEmailNotification(NotificationType.DISAPPROVE_REJECT_AUDIT_PLAN, "This serves to notify you that the rejected audit plan for case", "has been disapproved on", "by", "");
        emailNotificationService.save(emailNotification29);

        EmailNotification emailNotification30 = BootStrapHelper.getEmailNotification(NotificationType.APPROVE_REJECT_AUDIT_PLAN, "This serves to notify you that the rejected audit plan for case", "has been approved on", "by", "");
        emailNotificationService.save(emailNotification30);

        EmailNotification emailNotification31 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_ACCEPTED, "This serves to notify you that case", "has been reviewed on", "by", "");
        emailNotificationService.save(emailNotification31);

        EmailNotification emailNotification32 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_SUBMITTED, "This serves to notify you that case", "has been sent to you for review on", "by", "");
        emailNotificationService.save(emailNotification32);

        EmailNotification emailNotification33 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_PLAN_APPROVED, "This serves to notify you that case", "has been approved on", "by", "");
        emailNotificationService.save(emailNotification33);

        EmailNotification emailNotificationApprove = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORT_APPROVAL, "This serves to notify you that audit plan for case", "has been approved on", "by", "");
        emailNotificationService.save(emailNotificationApprove);

        EmailNotification emailNotification34 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORT_REWORK, "This serves to notify you that audit case report case", "has been sent back to you for rework on", "by", "");
        emailNotificationService.save(emailNotification34);

        EmailNotification emailNotification35 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORT_REVIEW, "This serves to notify you that audit case report for case", "has been reviewed on", "by", "");
        emailNotificationService.save(emailNotification35);

        EmailNotification emailNotification36 = BootStrapHelper.getEmailNotification(NotificationType.REJECT_AUDIT_REPORT_APPROVAL, "This serves to notify you that the rejected audit case findings for case", "has been approved on", "by", "");
        emailNotificationService.save(emailNotification36);

        EmailNotification emailNotification37 = BootStrapHelper.getEmailNotification(NotificationType.REJECT_AUDIT_REPORT_DISAPPROVAL, "This serves to notify you that the rejected audit case findings for case", "has been disapproved on", "by", "");
        emailNotificationService.save(emailNotification37);

        EmailNotification emailNotification38 = BootStrapHelper.getEmailNotification(NotificationType.REJECT_AUDIT_REPORT, "This serves to notify you that the rejected audit case findings for case", "has been sent to you to approve or disapprove on", "by", "");
        emailNotificationService.save(emailNotification38);

        EmailNotification emailNotification39 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORT_APPROVE, "This serves to notify you that the accepted audit reporting for case", "has been sent to you for approval on", "by", "");
        emailNotificationService.save(emailNotification39);

        EmailNotification emailNotification40 = BootStrapHelper.getEmailNotification(NotificationType.AUDIT_REPORT_APPROVED, "This serves to notify you that the audit reporting for case", "has been approved on", "by", "");
        emailNotificationService.save(emailNotification40);

        EmailNotification emailNotification41 = BootStrapHelper.getEmailNotification(NotificationType.ACCEPT_AUDIT_REPORT, "This serves to notify you that the accepted audit reporting for case", "has been sent to you for approval on", "by", "");
        emailNotificationService.save(emailNotification41);

        EmailNotification emailNotification42 = BootStrapHelper.getEmailNotification(NotificationType.ACCEPT_AUDIT_FINALISED, "This serves to notify you that the audit reporting and / or audit letters for case", "has been made available for you to review on", "by", "Kindly locate the case in your WIP Review Inventory or Audit Reporting Review Inventory and action accordingly.");
        emailNotificationService.save(emailNotification42);

        EmailNotification emailNotification43 = BootStrapHelper.getEmailNotification(NotificationType.DEBT_MANAGEMENT, "This serves to inform you that a customs case", "has been forwarded to you for allocation on", "by", "");
        emailNotificationService.save(emailNotification43);

        EmailNotification emailNotification44 = BootStrapHelper.getEmailNotification(NotificationType.EXTEND_LOI, "This serves to notify you that the LOI due date for case", "has been extended to", "by", "");
        emailNotificationService.save(emailNotification44);

        EmailNotification emailNotification45 = BootStrapHelper.getEmailNotification(NotificationType.REJECTED_RA, "This serves to notify you that the case ", "has been rejected on", "by", "");
        emailNotificationService.save(emailNotification45);

        EmailNotification emailNotification48 = BootStrapHelper.getEmailNotification(NotificationType.TRANSFER, "This serves to notify you that the case", "was transfer to you office", "by", "");
        emailNotificationService.save(emailNotification48);

        EmailNotification emailNotification49 = BootStrapHelper.getEmailNotification(NotificationType.DEBT_MANAGEMENT_COLLECTION, "Kindly note that the outstanding debt amounting to", "on case", "has been fully collected, and the case may regarded as finalised from the Debt Management team, You can therefore proceed with closing the case on the system.", "");
        emailNotificationService.save(emailNotification49);

        EmailNotification emailNotification50 = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_POOL, "This serves to notify you that the case ", "has been sent to discarded pool on", "by", "");
        emailNotificationService.save(emailNotification50);

        EmailNotification emailNotification51 = BootStrapHelper.getEmailNotification(NotificationType.INDEPTH_DISCARDED_POOL, "This serves to notify you that the case ", "has been sent to Indepth discarded pool on", "by", "");
        emailNotificationService.save(emailNotification51);

        EmailNotification emailNotification52 = BootStrapHelper.getEmailNotification(NotificationType.TECHNICAL_POOL, "This serves to notify you that case ", "has been sent to Technical Review pool on", "", "");
        emailNotificationService.save(emailNotification52);

        EmailNotification emailNotification53 = BootStrapHelper.getEmailNotification(NotificationType.TECHNICAL_APPROVED, "This serves to notify you that case ", "Technical Review has been approved on", "by", "");
        emailNotificationService.save(emailNotification53);
        /*EmailNotification emailNotification = BootStrapHelper.getEmailNotification(NotificationType.DISCARDED_SAR_TO_QA, "This serves to notify you that case ", "has been sent to you for review on ", "by", "");
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

    //REMEMBER TO CREATE FRONT END FOR THIS TEST CASE IN THE ADMINISTRATION MODULE
    @Test
    public void testScenarioI() {
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

    @Test
    public void testScenarioJ() {
//        OffenceClassification offenceClassification1 = BootStrapHelper.getOffenceClassification("Ignoring the instructions or actions of an officer – Sections 4", CaseType.SAR);
//        classificationervice.save(offenceClassification1);
//        OffenceClassification offenceClassification2 = BootStrapHelper.getOffenceClassification("Ignoring the instructions of an officer regarding the movement of persons and/or goods in the Customs controlled area – Section 6A", CaseType.SAR);
//        classificationervice.save(offenceClassification2);
//        OffenceClassification offenceClassification3 = BootStrapHelper.getOffenceClassification("Failure to comply with arrival and departure reports - Sections 7 and 8", CaseType.SAR);
//        classificationervice.save(offenceClassification3);
//        OffenceClassification offenceClassification4 = BootStrapHelper.getOffenceClassification("Failure to declare sealable goods – Section 9", CaseType.SAR);
//        classificationervice.save(offenceClassification4);
//        OffenceClassification offenceClassification5 = BootStrapHelper.getOffenceClassification("Landing of unentered goods – Section 11", CaseType.SAR);
//        classificationervice.save(offenceClassification5);
//
//        OffenceClassification offenceClassification6 = BootStrapHelper.getOffenceClassification("Reporting of vehicles after arrival in South Africa– Section 12", CaseType.SAR);
//        classificationervice.save(offenceClassification6);
//
//        OffenceClassification offenceClassification7 = BootStrapHelper.getOffenceClassification("Failure to comply with the conditions of goods imported by post – Section 13", CaseType.SAR);
//        classificationervice.save(offenceClassification7);
//
//        OffenceClassification offenceClassification8 = BootStrapHelper.getOffenceClassification("Goods not declared or under-declared by persons entering or leaving South Africa\n"
//                + "– Section 15", CaseType.SAR);
//        classificationervice.save(offenceClassification8);
//
//        OffenceClassification offenceClassification9 = BootStrapHelper.getOffenceClassification("Misuse of temporary import permit / carnet – Section 15, 18, 18(A) and 38", CaseType.SAR);
//        classificationervice.save(offenceClassification9);
//
//        OffenceClassification offenceClassification10 = BootStrapHelper.getOffenceClassification("Entering of goods removed in bond – Section 18 and 18A", CaseType.SAR);
//        classificationervice.save(offenceClassification10);
//        OffenceClassification offenceClassification11 = BootStrapHelper.getOffenceClassification("Customs and Excise warehouses – Section 19 – 26", CaseType.SAR);
//        classificationervice.save(offenceClassification11);
//        OffenceClassification offenceClassification12 = BootStrapHelper.getOffenceClassification("Failure to comply with the conditions for the entry of goods – Sections 12, 18A, 38 – 41, 44, 47, 54, 113 and 119A ", CaseType.SAR);
//        classificationervice.save(offenceClassification12);
//
//        OffenceClassification offenceClassification13 = BootStrapHelper.getOffenceClassification("Registration – Section 59A and Rule 59A.03", CaseType.SAR);
//        classificationervice.save(offenceClassification13);
//
//        OffenceClassification offenceClassification14 = BootStrapHelper.getOffenceClassification("Licences – Section 60 – 64D and 64G", CaseType.SAR);
//        classificationervice.save(offenceClassification14);
//
//        OffenceClassification offenceClassification15 = BootStrapHelper.getOffenceClassification("Valuation", CaseType.SAR);
//        classificationervice.save(offenceClassification15);
//
//        OffenceClassification offenceClassification16 = BootStrapHelper.getOffenceClassification("Rebates of duty – Section 75", CaseType.SAR);
//        classificationervice.save(offenceClassification16);
//
//        OffenceClassification offenceClassification17 = BootStrapHelper.getOffenceClassification("Serious offences – Section 80, 84, 86 and 88", CaseType.SAR);
//        classificationervice.save(offenceClassification17);
//
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

//        OffenceClassification offenceClassification24 = BootStrapHelper.getOffenceClassification("Reporting of goods brought into the country overland on foot – Section 12", CaseType.SAR);
//        classificationervice.save(offenceClassification24);
    }

    @Test
    public void testScenarioK() {
//        OffenceClassification offenceClassification1 = classificationervice.findByDescription("Ignoring the instructions or actions of an officer – Sections 4");
//        SectionRule sectionRule1 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(7)");
//        sectionRuleService.save(sectionRule1);
//
//        SectionRule sectionRule2 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(8A)(a) and (b)");
//        sectionRuleService.save(sectionRule2);
//
//        SectionRule sectionRule3 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(10)");
//        sectionRuleService.save(sectionRule3);
//
//        SectionRule sectionRule4 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(12)");
//        sectionRuleService.save(sectionRule4);
//
//        SectionRule sectionRule5 = BootStrapHelper.getSectionRule(offenceClassification1, "Section 4(12A)");
//        sectionRuleService.save(sectionRule5);
//
//        OffenceClassification offenceClassification2 = classificationervice.findByDescription("Ignoring the instructions of an officer regarding the movement of persons and/or goods in the Customs controlled area – Section 6A");
//        SectionRule sectionRule6 = BootStrapHelper.getSectionRule(offenceClassification2, "Section 6A");
//        sectionRuleService.save(sectionRule6);
//
//        OffenceClassification offenceClassification3 = classificationervice.findByDescription("Failure to comply with arrival and departure reports - Sections 7 and 8");
//        SectionRule sectionRule7 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(1)");
//        sectionRuleService.save(sectionRule7);
//
//        SectionRule sectionRule8 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(1B) and Rule 7.01");
//        sectionRuleService.save(sectionRule8);
//
//        SectionRule sectionRule9 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(1A)");
//        sectionRuleService.save(sectionRule9);
//
//        SectionRule sectionRule10 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(3)");
//        sectionRuleService.save(sectionRule10);
//
//        SectionRule sectionRule11 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(6)");
//        sectionRuleService.save(sectionRule11);
//
//        SectionRule sectionRule12 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 7(7)");
//        sectionRuleService.save(sectionRule12);
//
//        SectionRule sectionRule13 = BootStrapHelper.getSectionRule(offenceClassification3, "Section 8 and Rules 8.04, 8.05, 8.06, 8.07 and 8.08");
//        sectionRuleService.save(sectionRule13);
//
//        OffenceClassification offenceClassification4 = classificationervice.findByDescription("Failure to declare sealable goods – Section 9");
//        SectionRule sectionRule14 = BootStrapHelper.getSectionRule(offenceClassification4, "Section 9(1)");
//        sectionRuleService.save(sectionRule14);
//
//        OffenceClassification offenceClassification5 = classificationervice.findByDescription("Landing of unentered goods – Section 11");
//        SectionRule sectionRule15 = BootStrapHelper.getSectionRule(offenceClassification5, "Section 11(1)");
//        sectionRuleService.save(sectionRule15);
//
//        OffenceClassification offenceClassification6 = classificationervice.findByDescription("Reporting of vehicles after arrival in South Africa– Section 12");
//        SectionRule sectionRule16 = BootStrapHelper.getSectionRule(offenceClassification6, "Section 12(3)(a)");
//        sectionRuleService.save(sectionRule16);
//
//        OffenceClassification offenceClassification7 = classificationervice.findByDescription("Reporting of goods brought into the country overland on foot – Section 12");
//        SectionRule sectionRule17 = BootStrapHelper.getSectionRule(offenceClassification7, "Section 12(5)(a), (b), (c)");
//        sectionRuleService.save(sectionRule17);
//
//        OffenceClassification offenceClassification8 = classificationervice.findByDescription("Failure to comply with the conditions of goods imported by post – Section 13");
//        SectionRule sectionRule18 = BootStrapHelper.getSectionRule(offenceClassification8, "Section 13(5)");
//        sectionRuleService.save(sectionRule18);
//
//        OffenceClassification offenceClassification9 = classificationervice.findByDescription("Goods not declared or under-declared by persons entering or leaving South Africa\n"
//                + "– Section 15");
//        SectionRule sectionRule19 = BootStrapHelper.getSectionRule(offenceClassification9, "Section 15(1) and (2)");
//        sectionRuleService.save(sectionRule19);
//
//        OffenceClassification offenceClassification10 = classificationervice.findByDescription("Misuse of temporary import permit / carnet – Section 15, 18, 18(A) and 38");
//        SectionRule sectionRule20 = BootStrapHelper.getSectionRule(offenceClassification10, "Sections 15, 18, 18(A) and 38(1) read with Rules 15.01, 18.01 to 18.15, 18A.01 to 18A.10 and 120A.01");
//        sectionRuleService.save(sectionRule20);
//
//        OffenceClassification offenceClassification11 = classificationervice.findByDescription("Entering of goods removed in bond – Section 18 and 18A");
//        SectionRule sectionRule21 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(3) read with Rule 18.07(a)");
//        sectionRuleService.save(sectionRule21);
//
//        SectionRule sectionRule22 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(3) read with Rule 18.07(b)");
//        sectionRuleService.save(sectionRule22);
//
//        SectionRule sectionRule23 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(7), 18A(6), 64D(1) and (2), Rule 18A.10");
//        sectionRuleService.save(sectionRule23);
//
//        SectionRule sectionRule24 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(8)");
//        sectionRuleService.save(sectionRule24);
//
//        SectionRule sectionRule25 = BootStrapHelper.getSectionRule(offenceClassification11, "Section 18(13)");
//        sectionRuleService.save(sectionRule25);
//
//        OffenceClassification offenceClassification12 = classificationervice.findByDescription("Customs and Excise warehouses – Section 19 – 26");
//        SectionRule sectionRule26 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 19.02");
//        sectionRuleService.save(sectionRule26);
//
//        
//        
//        SectionRule sectionRule27 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 19(3)");
//        sectionRuleService.save(sectionRule27);
//
//        SectionRule sectionRule28 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 19(4) and 20(5)");
//        sectionRuleService.save(sectionRule28);
//
//        SectionRule sectionRule29 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 19(9)");
//        sectionRuleService.save(sectionRule29);
//
//        SectionRule sectionRule30 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 19.05");
//        sectionRuleService.save(sectionRule30);
//
//        SectionRule sectionRule31 = BootStrapHelper.getSectionRule(offenceClassification12, "Rules 19.07");
//        sectionRuleService.save(sectionRule31);
//
//        SectionRule sectionRule32 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 20.03");
//        sectionRuleService.save(sectionRule32);
//
//        SectionRule sectionRule33 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 20(4)");
//        sectionRuleService.save(sectionRule33);
//
//        SectionRule sectionRule34 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 20(4)(bis)");
//        sectionRuleService.save(sectionRule34);
//
//        SectionRule sectionRule35 = BootStrapHelper.getSectionRule(offenceClassification12, "Rule 20.08");
//        sectionRuleService.save(sectionRule35);
//
//        SectionRule sectionRule36 = BootStrapHelper.getSectionRule(offenceClassification12, "Section 26");
//        sectionRuleService.save(sectionRule36);
//
//        OffenceClassification offenceClassification13 = classificationervice.findByDescription("Failure to comply with the conditions for the entry of goods – Sections 12, 18A, 38 – 41, 44, 47, 54, 113 and 119A ");
//        SectionRule sectionRule37 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 12(7)");
//        sectionRuleService.save(sectionRule37);
//
//        SectionRule sectionRule38 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 18A(3)");
//        sectionRuleService.save(sectionRule38);
//
//        SectionRule sectionRule39 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 18A(4)");
//        sectionRuleService.save(sectionRule39);
//
//        SectionRule sectionRule40 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 18A(9)");
//        sectionRuleService.save(sectionRule40);
//
//        SectionRule sectionRule41 = BootStrapHelper.getSectionRule(offenceClassification13, "Sections 38(1), 38(3), 40(1), 46(1), 47(1) and 49(1)");
//        sectionRuleService.save(sectionRule41);
//
//        SectionRule sectionRule42 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 39(1)");
//        sectionRuleService.save(sectionRule42);
//
//        SectionRule sectionRule43 = BootStrapHelper.getSectionRule(offenceClassification13, "Rule 39.08");
//        sectionRuleService.save(sectionRule43);
//
//        SectionRule sectionRule44 = BootStrapHelper.getSectionRule(offenceClassification13, "Sections 38(1), 39(1), 40(1),  44(10) and 47(1) read with Section 113(2)");
//        sectionRuleService.save(sectionRule44);
//
//        SectionRule sectionRule45 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 40(1)");
//        sectionRuleService.save(sectionRule45);
//
//        SectionRule sectionRule46 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 41");
//        sectionRuleService.save(sectionRule46);
//
//        SectionRule sectionRule47 = BootStrapHelper.getSectionRule(offenceClassification13, "Section 54 read with Rule 54.01, 02");
//        sectionRuleService.save(sectionRule47);
//
//        SectionRule sectionRule48 = BootStrapHelper.getSectionRule(offenceClassification13, "Sections 119A and 39(1)(c) read with Rules 119A and 39(2B).02");
//        sectionRuleService.save(sectionRule48);
//
//        OffenceClassification offenceClassification14 = classificationervice.findByDescription("Registration – Section 59A and Rule 59A.03");
//        SectionRule sectionRule49 = BootStrapHelper.getSectionRule(offenceClassification14, "Section 59A read with Rule 59A");
//        sectionRuleService.save(sectionRule49);
//
//        OffenceClassification offenceClassification15 = classificationervice.findByDescription("Licences – Section 60 – 64D and 64G");
//        SectionRule sectionRule50 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64B read with 60");
//        sectionRuleService.save(sectionRule50);
//
//        SectionRule sectionRule51 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64B");
//        sectionRuleService.save(sectionRule51);
//
//        SectionRule sectionRule52 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64C");
//        sectionRuleService.save(sectionRule52);
//
//        SectionRule sectionRule53 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64D(1) read with 60(1)");
//        sectionRuleService.save(sectionRule53);
//
//        SectionRule sectionRule54 = BootStrapHelper.getSectionRule(offenceClassification15, "Section 64D(1) and (7) read with 60(1) and Rules 64D.10(5) and 64D.11(5)");
//        sectionRuleService.save(sectionRule54);
//
//        OffenceClassification offenceClassification16 = classificationervice.findByDescription("Valuation");
//        SectionRule sectionRule55 = BootStrapHelper.getSectionRule(offenceClassification16, "Sections 65, 66, 67 read with 39(1)");
//        sectionRuleService.save(sectionRule55);
//
//        OffenceClassification offenceClassification17 = classificationervice.findByDescription("Rebates of duty – Section 75");
//        SectionRule sectionRule56 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(1)(b)");
//        sectionRuleService.save(sectionRule56);
//
//        SectionRule sectionRule57 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(5)(a)(i)");
//        sectionRuleService.save(sectionRule57);
//
//        SectionRule sectionRule58 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.06");
//        sectionRuleService.save(sectionRule58);
//
//        SectionRule sectionRule59 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(19)");
//        sectionRuleService.save(sectionRule59);
//
//        SectionRule sectionRule60 = BootStrapHelper.getSectionRule(offenceClassification17, "Section 75(21)");
//        sectionRuleService.save(sectionRule60);
//
//        SectionRule sectionRule61 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.10");
//        sectionRuleService.save(sectionRule61);
//
//        SectionRule sectionRule62 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.14");
//        sectionRuleService.save(sectionRule62);
//
//        SectionRule sectionRule63 = BootStrapHelper.getSectionRule(offenceClassification17, "Rule 75.15");
//        sectionRuleService.save(sectionRule63);
//
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
    public void testScenarioL() {
//        SectionRule sectionRule76 = sectionRuleService.findByDescription("Section 114(2A)");
//        OffenceDescription offenceDescription1 = BootStrapHelper.getOffenceDescription(sectionRule76, "Removes goods subject to a lien [Section 114(1)(aA) or goods so detained Section 114(2)] ", 1500.00, "", "25% of value of goods subject  to the lien, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription1);
//
//        SectionRule sectionRule77 = sectionRuleService.findByDescription("Section 107(2)(a)");
//        OffenceDescription offenceDescription2 = BootStrapHelper.getOffenceDescription(sectionRule77, "Fails to comply with the conditions determined by the Commissioner for allowing goods detained to pass from his / her control", 1500.00, "", "", 0.00);
//        offenceDescriptionService.save(offenceDescription2);
//
//        SectionRule sectionRule78 = sectionRuleService.findByDescription("Rule 101A.01A(2)(a)");
//        OffenceDescription offenceDescription3 = BootStrapHelper.getOffenceDescription(sectionRule78, "Fails to submit documents electronically, unless exempted temporarily or permanently by the Commissioner.", 5000.00, "", "R5 000 per declaration / report / list", 0.00);
//        offenceDescriptionService.save(offenceDescription3);
//
//        SectionRule sectionRule79 = sectionRuleService.findByDescription("Section 101(2B)");
//        OffenceDescription offenceDescription4 = BootStrapHelper.getOffenceDescription(sectionRule79, "Fails to keep or produce on demand any data created by means of a computer as defined in Section 1 of the Computer Evidence Act, 1983", 5000.00, "", "", 0.00);
//        offenceDescriptionService.save(offenceDescription4);
//
//        SectionRule sectionRule80 = sectionRuleService.findByDescription("Section 101(1)(a) and (2) read with Rule 101.01");
//        OffenceDescription offenceDescription5 = BootStrapHelper.getOffenceDescription(sectionRule80, "Fails to produce the prescribed goods,\n"
//                + "accounts or documents on demand", 5000.00, "", "", 0.00);
//        offenceDescriptionService.save(offenceDescription5);
//
//        OffenceDescription offenceDescription6 = BootStrapHelper.getOffenceDescription(sectionRule80, "Fails to keep the prescribed books, accounts or documents", 5000.00, "", "", 0.00);
//        offenceDescriptionService.save(offenceDescription6);
//
//        SectionRule sectionRule81 = sectionRuleService.findByDescription("Section 76B");
//        OffenceDescription offenceDescription7 = BootStrapHelper.getOffenceDescription(sectionRule81, "Time expired claim", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
//        offenceDescriptionService.save(offenceDescription7);
//
//        SectionRule sectionRule82 = sectionRuleService.findByDescription("Section 76(1)");
//        OffenceDescription offenceDescription8 = BootStrapHelper.getOffenceDescription(sectionRule82, "Submits a duplicate application for refund of duty or other charge to the Controller / Branch Manager for an amount that has already been refunded", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
//        offenceDescriptionService.save(offenceDescription8);
//
//        SectionRule sectionRule83 = sectionRuleService.findByDescription("Sections 75, 76 and 76B read with 113(2)");
//        OffenceDescription offenceDescription9 = BootStrapHelper.getOffenceDescription(sectionRule83, "Submitting an application for a refund / drawback for an amount on a declaration which was originally not paid", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
//        offenceDescriptionService.save(offenceDescription9);
//
//        OffenceDescription offenceDescription10 = BootStrapHelper.getOffenceDescription(sectionRule83, "Resubmitting an application for a refund / drawback previously rejected without following the instructions of the officer", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
//        offenceDescriptionService.save(offenceDescription10);
//
//        OffenceDescription offenceDescription11 = BootStrapHelper.getOffenceDescription(sectionRule83, "Fails to produce sufficient proof of export", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
//        offenceDescriptionService.save(offenceDescription11);
//
//        OffenceDescription offenceDescription12 = BootStrapHelper.getOffenceDescription(sectionRule83, "Submit false proof of export to obtain refund of duties", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
//        offenceDescriptionService.save(offenceDescription12);
//
//        OffenceDescription offenceDescription13 = BootStrapHelper.getOffenceDescription(sectionRule83, "The agent / importer claims refund of duties on goods which were incorrectly supplied, or were faulty but the goods have not been cleared for home consumption (false documents)", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription13);
//
//        OffenceDescription offenceDescription14 = BootStrapHelper.getOffenceDescription(sectionRule83, "Agent / importer claims refund of duties on goods used in manufacturing process but goods have not been exported (proof of export not provided or not adequate)", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription14);
//
//        OffenceDescription offenceDescription15 = BootStrapHelper.getOffenceDescription(sectionRule83, "Agent / importer uses one (1) method (rate of exchange or factor) when calculating duties to frame declaration and uses a different method to calculate the claim amount when applying for a refund", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription15);
//
//        OffenceDescription offenceDescription16 = BootStrapHelper.getOffenceDescription(sectionRule83, "Refund submitted in terms of tariff amendment that is retrospectively backdated - assessment date not falling within the dates as specified in the amendment therefore the agent is not entitled to a refund", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription16);
//
//        OffenceDescription offenceDescription17 = BootStrapHelper.getOffenceDescription(sectionRule83, "Refund submitted in terms of a ITAC permit which indicated specific dates for declaration to be used - declaration date on which the refund is based is not within the prescribed period therefore the applicant is not entitled to a refund", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription17);
//
//        OffenceDescription offenceDescription18 = BootStrapHelper.getOffenceDescription(sectionRule83, "Incorrect tariff classification on the voucher of correction as well as on the original declaration – Tariff Section has advised that a third tariff heading is applicable", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription18);
//
//        OffenceDescription offenceDescription19 = BootStrapHelper.getOffenceDescription(sectionRule83, "Incorrect tariff classification on the refund voucher of correction but correct as originally entered on the declaration", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription19);
//
//        OffenceDescription offenceDescription20 = BootStrapHelper.getOffenceDescription(sectionRule83, "Does not submit EUR1 certificate, DA 59, DCC, PAA or SIPC to claim preferential treatment at the time of refund application", 1500.00, "", "25% of over claim, minimum of R1 500", 0.00);
//        offenceDescriptionService.save(offenceDescription20);

        SectionRule sectionRule76 = sectionRuleService.findByDescription("Section 114(2A)");
        OffenceDescription offenceDescription1 = BootStrapHelper.getOffenceDescription(sectionRule76, "Removes goods subject to a lien [Section 114(1)(aA) or goods so detained Section 114(2)] ", 1100.00, "", "25% of value of goods subject  to the lien, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription1);

        SectionRule sectionRule77 = sectionRuleService.findByDescription("Section 107(2)(a)");
        OffenceDescription offenceDescription2 = BootStrapHelper.getOffenceDescription(sectionRule77, "Fails to comply with the conditions determined by the Commissioner for allowing goods detained to pass from his / her control", 1200.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription2);

        SectionRule sectionRule78 = sectionRuleService.findByDescription("Rule 101A.01A(2)(a)");
        OffenceDescription offenceDescription3 = BootStrapHelper.getOffenceDescription(sectionRule78, "Fails to submit documents electronically, unless exempted temporarily or permanently by the Commissioner.", 5100.00, "", "R5 000 per declaration / report / list", 0.00);
        offenceDescriptionService.save(offenceDescription3);

        SectionRule sectionRule79 = sectionRuleService.findByDescription("Section 101(2B)");
        OffenceDescription offenceDescription4 = BootStrapHelper.getOffenceDescription(sectionRule79, "Fails to keep or produce on demand any data created by means of a computer as defined in Section 1 of the Computer Evidence Act, 1983", 5200.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription4);

        SectionRule sectionRule80 = sectionRuleService.findByDescription("Section 101(1)(a) and (2) read with Rule 101.01");
        OffenceDescription offenceDescription5 = BootStrapHelper.getOffenceDescription(sectionRule80, "Fails to produce the prescribed goods,\n"
                + "accounts or documents on demand", 5000.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription5);

        OffenceDescription offenceDescription6 = BootStrapHelper.getOffenceDescription(sectionRule80, "Fails to keep the prescribed books, accounts or documents", 5300.00, "", "", 0.00);
        offenceDescriptionService.save(offenceDescription6);

        SectionRule sectionRule81 = sectionRuleService.findByDescription("Section 76B");
        OffenceDescription offenceDescription7 = BootStrapHelper.getOffenceDescription(sectionRule81, "Time expired claim", 2100.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription7);

        SectionRule sectionRule82 = sectionRuleService.findByDescription("Section 76(1)");
        OffenceDescription offenceDescription8 = BootStrapHelper.getOffenceDescription(sectionRule82, "Submits a duplicate application for refund of duty or other charge to the Controller / Branch Manager for an amount that has already been refunded", 2200.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription8);

        SectionRule sectionRule83 = sectionRuleService.findByDescription("Sections 75, 76 and 76B read with 113(2)");
        OffenceDescription offenceDescription9 = BootStrapHelper.getOffenceDescription(sectionRule83, "Submitting an application for a refund / drawback for an amount on a declaration which was originally not paid", 2300.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription9);

        OffenceDescription offenceDescription10 = BootStrapHelper.getOffenceDescription(sectionRule83, "Resubmitting an application for a refund / drawback previously rejected without following the instructions of the officer", 2400.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription10);

        OffenceDescription offenceDescription11 = BootStrapHelper.getOffenceDescription(sectionRule83, "Fails to produce sufficient proof of export", 2500.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription11);

        OffenceDescription offenceDescription12 = BootStrapHelper.getOffenceDescription(sectionRule83, "Submit false proof of export to obtain refund of duties", 2600.00, "", "25% of over claim, minimum of R2 500", 0.00);
        offenceDescriptionService.save(offenceDescription12);

        OffenceDescription offenceDescription13 = BootStrapHelper.getOffenceDescription(sectionRule83, "The agent / importer claims refund of duties on goods which were incorrectly supplied, or were faulty but the goods have not been cleared for home consumption (false documents)", 1700.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription13);

        OffenceDescription offenceDescription14 = BootStrapHelper.getOffenceDescription(sectionRule83, "Agent / importer claims refund of duties on goods used in manufacturing process but goods have not been exported (proof of export not provided or not adequate)", 1800.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription14);

        OffenceDescription offenceDescription15 = BootStrapHelper.getOffenceDescription(sectionRule83, "Agent / importer uses one (1) method (rate of exchange or factor) when calculating duties to frame declaration and uses a different method to calculate the claim amount when applying for a refund", 1900.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription15);

        OffenceDescription offenceDescription16 = BootStrapHelper.getOffenceDescription(sectionRule83, "Refund submitted in terms of tariff amendment that is retrospectively backdated - assessment date not falling within the dates as specified in the amendment therefore the agent is not entitled to a refund", 2000.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription16);

        OffenceDescription offenceDescription17 = BootStrapHelper.getOffenceDescription(sectionRule83, "Refund submitted in terms of a ITAC permit which indicated specific dates for declaration to be used - declaration date on which the refund is based is not within the prescribed period therefore the applicant is not entitled to a refund", 2150.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription17);

        OffenceDescription offenceDescription18 = BootStrapHelper.getOffenceDescription(sectionRule83, "Incorrect tariff classification on the voucher of correction as well as on the original declaration – Tariff Section has advised that a third tariff heading is applicable", 2250.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription18);

        OffenceDescription offenceDescription19 = BootStrapHelper.getOffenceDescription(sectionRule83, "Incorrect tariff classification on the refund voucher of correction but correct as originally entered on the declaration", 2350.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription19);

        OffenceDescription offenceDescription20 = BootStrapHelper.getOffenceDescription(sectionRule83, "Does not submit EUR1 certificate, DA 59, DCC, PAA or SIPC to claim preferential treatment at the time of refund application", 2450.00, "", "25% of over claim, minimum of R1 500", 0.00);
        offenceDescriptionService.save(offenceDescription20);
    }

    @Test
    public void testScenarioM() {
        PrioritisationScore customsValue1 = new PrioritisationScore(0.00, 1000000.00, 1, ScoreType.CUSTOMS_VALUE);
        customsValue1.setCreatedBy("S2026015");
        customsValue1.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue1);

        PrioritisationScore customsValue2 = new PrioritisationScore(1000001.00, 10000000.00, 2, ScoreType.CUSTOMS_VALUE);
        customsValue2.setCreatedBy("S2026015");
        customsValue2.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue2);

        PrioritisationScore customsValue3 = new PrioritisationScore(10000001.00, 50000000.00, 3, ScoreType.CUSTOMS_VALUE);
        customsValue3.setCreatedBy("S2026015");
        customsValue3.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue3);

        PrioritisationScore customsValue4 = new PrioritisationScore(50000001.00, 100000000.00, 4, ScoreType.CUSTOMS_VALUE);
        customsValue4.setCreatedBy("S2026015");
        customsValue4.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue4);

        PrioritisationScore customsValue5 = new PrioritisationScore(100000001.00, 200000000.00, 5, ScoreType.CUSTOMS_VALUE);
        customsValue5.setCreatedBy("S2026015");
        customsValue5.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue5);

        PrioritisationScore customsValue6 = new PrioritisationScore(200000001.00, 500000000.00, 6, ScoreType.CUSTOMS_VALUE);
        customsValue6.setCreatedBy("S2026015");
        customsValue6.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue6);

        PrioritisationScore customsValue7 = new PrioritisationScore(500000001.00, 1000000000.00, 7, ScoreType.CUSTOMS_VALUE);
        customsValue7.setCreatedBy("S2026015");
        customsValue7.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue7);

        PrioritisationScore customsValue8 = new PrioritisationScore(1000000001.00, 5000000000.00, 8, ScoreType.CUSTOMS_VALUE);
        customsValue8.setCreatedBy("S2026015");
        customsValue8.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue8);

        PrioritisationScore customsValue9 = new PrioritisationScore(5000000001.00, 10000000000.00, 9, ScoreType.CUSTOMS_VALUE);
        customsValue9.setCreatedBy("S2026015");
        customsValue9.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue9);

        PrioritisationScore customsValue10 = new PrioritisationScore(10000000001.00, 999999999999.99, 10, ScoreType.CUSTOMS_VALUE);
        customsValue10.setCreatedBy("S2026015");
        customsValue10.setCreatedDate(new Date());
        prioritisationScoreService.save(customsValue10);

        PrioritisationScore line1 = new PrioritisationScore(0, 100, 1, ScoreType.NUMBER_OF_LINES);
        line1.setCreatedBy("S2026015");
        line1.setCreatedDate(new Date());
        prioritisationScoreService.save(line1);

        PrioritisationScore line2 = new PrioritisationScore(101, 1000, 2, ScoreType.NUMBER_OF_LINES);
        line2.setCreatedBy("S2026015");
        line2.setCreatedDate(new Date());
        prioritisationScoreService.save(line2);

        PrioritisationScore line3 = new PrioritisationScore(1001, 2000, 3, ScoreType.NUMBER_OF_LINES);
        line3.setCreatedBy("S2026015");
        line3.setCreatedDate(new Date());
        prioritisationScoreService.save(line3);

        PrioritisationScore line4 = new PrioritisationScore(2001, 5000, 4, ScoreType.NUMBER_OF_LINES);
        line4.setCreatedBy("S2026015");
        line4.setCreatedDate(new Date());
        prioritisationScoreService.save(line4);

        PrioritisationScore line5 = new PrioritisationScore(5001, 10000, 5, ScoreType.NUMBER_OF_LINES);
        line5.setCreatedBy("S2026015");
        line5.setCreatedDate(new Date());
        prioritisationScoreService.save(line5);

        PrioritisationScore line6 = new PrioritisationScore(10001, 20000, 6, ScoreType.NUMBER_OF_LINES);
        line6.setCreatedBy("S2026015");
        line6.setCreatedDate(new Date());
        prioritisationScoreService.save(line6);

        PrioritisationScore line7 = new PrioritisationScore(20001, 40000, 7, ScoreType.NUMBER_OF_LINES);
        line7.setCreatedBy("S2026015");
        line7.setCreatedDate(new Date());
        prioritisationScoreService.save(line7);

        PrioritisationScore line8 = new PrioritisationScore(40001, 80000, 8, ScoreType.NUMBER_OF_LINES);
        line8.setCreatedBy("S2026015");
        line8.setCreatedDate(new Date());
        prioritisationScoreService.save(line8);

        PrioritisationScore line9 = new PrioritisationScore(80001, 100000, 9, ScoreType.NUMBER_OF_LINES);
        line9.setCreatedBy("S2026015");
        line9.setCreatedDate(new Date());
        prioritisationScoreService.save(line9);

        PrioritisationScore line10 = new PrioritisationScore(100001, 999999999, 10, ScoreType.NUMBER_OF_LINES);
        line10.setCreatedBy("S2026015");
        line10.setCreatedDate(new Date());
        prioritisationScoreService.save(line10);

        PrioritisationScore commodity1 = new PrioritisationScore(10, ScoreType.COMMODITY_ALIGNMENT, YesNoEnum.YES);
        commodity1.setCreatedBy("S2026015");
        commodity1.setCreatedDate(new Date());
        prioritisationScoreService.save(commodity1);

        PrioritisationScore commodity2 = new PrioritisationScore(0, ScoreType.COMMODITY_ALIGNMENT, YesNoEnum.NO);
        commodity2.setCreatedBy("S2026015");
        commodity2.setCreatedDate(new Date());
        prioritisationScoreService.save(commodity2);

        PrioritisationScore riskRating = new PrioritisationScore(1, ScoreType.RISK_RATING);
        riskRating.setCreatedBy("S2026015");
        riskRating.setCreatedDate(new Date());
        prioritisationScoreService.save(riskRating);

        PrioritisationScore riskIdentified = new PrioritisationScore(1, ScoreType.RISK_IDENTIFIED);
        riskIdentified.setCreatedBy("S2026015");
        riskIdentified.setCreatedDate(new Date());
        prioritisationScoreService.save(riskIdentified);

        PrioritisationScore revenueRisk = new PrioritisationScore(0.00, 100000.00, 1, ScoreType.REVENUE_AT_RISK);
        revenueRisk.setCreatedBy("S2026015");
        revenueRisk.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk);

        PrioritisationScore revenueRisk2 = new PrioritisationScore(100001.00, 500000.00, 2, ScoreType.REVENUE_AT_RISK);
        revenueRisk2.setCreatedBy("S2026015");
        revenueRisk2.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk2);

        PrioritisationScore revenueRisk3 = new PrioritisationScore(500001.00, 1000000.00, 3, ScoreType.REVENUE_AT_RISK);
        revenueRisk3.setCreatedBy("S2026015");
        revenueRisk3.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk3);

        PrioritisationScore revenueRisk4 = new PrioritisationScore(1000001.00, 2000000.00, 4, ScoreType.REVENUE_AT_RISK);
        revenueRisk4.setCreatedBy("S2026015");
        revenueRisk4.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk4);

        PrioritisationScore revenueRisk5 = new PrioritisationScore(2000001.00, 5000000.00, 5, ScoreType.REVENUE_AT_RISK);
        revenueRisk5.setCreatedBy("S2026015");
        revenueRisk5.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk5);

        PrioritisationScore revenueRisk6 = new PrioritisationScore(5000001.00, 10000000.00, 6, ScoreType.REVENUE_AT_RISK);
        revenueRisk6.setCreatedBy("S2026015");
        revenueRisk6.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk6);

        PrioritisationScore revenueRisk7 = new PrioritisationScore(10000001.00, 20000000.00, 7, ScoreType.REVENUE_AT_RISK);
        revenueRisk7.setCreatedBy("S2026015");
        revenueRisk7.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk7);

        PrioritisationScore revenueRisk8 = new PrioritisationScore(20000001.00, 40000000.00, 8, ScoreType.REVENUE_AT_RISK);
        revenueRisk8.setCreatedBy("S2026015");
        revenueRisk8.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk8);

        PrioritisationScore revenueRisk9 = new PrioritisationScore(40000001.00, 60000000.00, 9, ScoreType.REVENUE_AT_RISK);
        revenueRisk9.setCreatedBy("S2026015");
        revenueRisk9.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk9);

        PrioritisationScore revenueRisk10 = new PrioritisationScore(60000001.00, 999999999999.99, 10, ScoreType.REVENUE_AT_RISK);
        revenueRisk10.setCreatedBy("S2026015");
        revenueRisk10.setCreatedDate(new Date());
        prioritisationScoreService.save(revenueRisk10);
    }

    @Test
    public void testScenarioN() {
        RiskRatingLikelihood riskRatingLikelihood1 = new RiskRatingLikelihood("Rare", 1);
        riskRatingLikelihood1.setCreatedBy("s2024726");
        riskRatingLikelihood1.setCreatedDate(new Date());
        riskRatingLikelihoodService.save(riskRatingLikelihood1);
        RiskRatingLikelihood riskRatingLikelihood2 = new RiskRatingLikelihood("Unlikely", 2);
        riskRatingLikelihood2.setCreatedBy("s2024726");
        riskRatingLikelihood2.setCreatedDate(new Date());
        riskRatingLikelihoodService.save(riskRatingLikelihood2);
        RiskRatingLikelihood riskRatingLikelihood3 = new RiskRatingLikelihood("Moderate", 3);
        riskRatingLikelihood3.setCreatedBy("s2024726");
        riskRatingLikelihood3.setCreatedDate(new Date());
        riskRatingLikelihoodService.save(riskRatingLikelihood3);
        RiskRatingLikelihood riskRatingLikelihood4 = new RiskRatingLikelihood("Likely", 4);
        riskRatingLikelihood4.setCreatedBy("s2024726");
        riskRatingLikelihood4.setCreatedDate(new Date());
        riskRatingLikelihoodService.save(riskRatingLikelihood4);
        RiskRatingLikelihood riskRatingLikelihood5 = new RiskRatingLikelihood("Almost Certain", 5);
        riskRatingLikelihood5.setCreatedBy("s2024726");
        riskRatingLikelihood5.setCreatedDate(new Date());
        riskRatingLikelihoodService.save(riskRatingLikelihood5);

        RiskRatingConsequence riskRatingConsequence1 = new RiskRatingConsequence("Minor", 1);
        riskRatingConsequence1.setCreatedBy("s2024726");
        riskRatingConsequence1.setCreatedDate(new Date());
        riskRatingConsequenceService.save(riskRatingConsequence1);
        RiskRatingConsequence riskRatingConsequence2 = new RiskRatingConsequence("Insignificant", 2);
        riskRatingConsequence2.setCreatedBy("s2024726");
        riskRatingConsequence2.setCreatedDate(new Date());
        riskRatingConsequenceService.save(riskRatingConsequence2);
        RiskRatingConsequence riskRatingConsequence3 = new RiskRatingConsequence("Moderate", 3);
        riskRatingConsequence3.setCreatedBy("s2024726");
        riskRatingConsequence3.setCreatedDate(new Date());
        riskRatingConsequenceService.save(riskRatingConsequence3);
        RiskRatingConsequence riskRatingConsequence4 = new RiskRatingConsequence("Major", 4);
        riskRatingConsequence4.setCreatedBy("s2024726");
        riskRatingConsequence4.setCreatedDate(new Date());
        riskRatingConsequenceService.save(riskRatingConsequence4);
        RiskRatingConsequence riskRatingConsequence5 = new RiskRatingConsequence("Catastrophic", 5);
        riskRatingConsequence5.setCreatedBy("s2024726");
        riskRatingConsequence5.setCreatedDate(new Date());
        riskRatingConsequenceService.save(riskRatingConsequence5);
    }

    @Test
    public void testScenarioP() {

    }
}
