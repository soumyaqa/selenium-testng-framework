package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/19/2017.
 */
public class TC_08_VerifyTotalPrice extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void verifyTotalPrice() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelAppBuild2/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "2 - Two", "17/05/2017", "18/05/2017", "1 - One", "1 - One")
                .selectHotel()
                .verifyTotalPrice("AUD $ 250");
    }
}