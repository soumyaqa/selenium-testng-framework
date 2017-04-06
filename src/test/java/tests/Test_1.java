package tests;

import com.relevantcodes.extentreports.LogStatus;
import framework.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.GoogleHomePage;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class Test_1 extends BaseTest {
    GoogleHomePage homePage;
    @Test
    public void testGoogle() {
        homePage = new GoogleHomePage(driver, testReporter, element);
        openApplication("http://www.google.com");
        homePage.search("testing framework");
    }
}
