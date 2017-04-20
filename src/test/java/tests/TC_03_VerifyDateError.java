package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/19/2017.
 */
public class TC_03_VerifyDateError extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void checkDateErrorVerification() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelAppBuild2/");
        loginPage.login("home1234", "root1234")
                .chechOutDateError("Sydney", "Hotel Creek", "Standard", "2 - Two", "14/04/2017", "16/04/2017", "2 - Two", "1 - One")

        ;
    }
}
