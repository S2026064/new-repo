package sars.pca.app.tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sars.pca.app.common.MergeTest;
import sars.pca.app.common.MergeUtility;

/**
 *
 * @author S2024726
 */
public class MergeTestCase {

    public MergeTestCase() {
    }

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
    public void testMergeScenario() {
        MergeTest source = new MergeTest();
        source.setDescription("This is the description of the first object");
        source.setEmailAddress("hasani@gmail.com");
        source.setFirstName("Vongani");
        source.setLastName("Maluleke");

        MergeTest target = new MergeTest();
        target.setDescription("This is the description of the second object");
        target.setEmailAddress("test@gmail.com");
        target.setFirstName("Vongani");
        target.setLastName("Maluleke");

        try {
            MergeTest merge = (MergeTest) MergeUtility.mergeObjects(source, target);
            System.out.println(merge.getDescription());
            System.out.println(merge.getEmailAddress());
            System.out.println(merge.getFirstName());
            System.out.println(merge.getLastName());
        } catch (Exception ex) {
            Logger.getLogger(MergeTestCase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
