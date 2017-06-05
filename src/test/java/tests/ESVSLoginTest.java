package tests;

import com.relevantcodes.extentreports.LogStatus;
import framework.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import uistore.HomePageUI;
import uistore.LoginPageUI;

/**
 * Created by GXP8655 on 6/5/2017.
 */
public class ESVSLoginTest extends BaseTest {

    @Test
    public void loginTest() {

        openApplication("http://st5604.homedepot.com:12040/MMSVESVSWeb/main.html#");
        LoginPage loginPage = new LoginPage(driver,  testReporter, element);
        loginPage.Login()
                .clickOnCreateQuote();
    }
}
