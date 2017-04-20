package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/12/2017.
 */
public class TC_02_VerifyCheckinDate extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void checkInDateVerification() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelAppBuild2/");
        loginPage.login("home1234", "root1234")
                .chechOutDate("Sydney", "Hotel Creek", "Standard", "2 - Two", "26/07/2017", "24/07/2017", "2 - Two", "1 - One")
        ;
    }
}