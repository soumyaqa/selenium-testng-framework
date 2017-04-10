package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/10/2017.
 */
public class Test_01_AdactinLogin extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void loginTest() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.Login("home1234", "root1234");
    }
}
