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
    public void createQuote() {
        openApplication();
        LoginPage loginPage = new LoginPage(driver,  testReporter, element);
        //Login Page will take care of opening the application
        loginPage.Login()
                .clickOnCreateQuote()
                .searchCustomer();
    }
}
