package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/10/2017.
 */
public class TC_01_VerifyAdactinLogin extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void loginTest() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234");
        // .searchPage("","","","");
    }
}
