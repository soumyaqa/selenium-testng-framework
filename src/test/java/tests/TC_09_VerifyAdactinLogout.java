package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/18/2017.
 */
public class TC_09_VerifyAdactinLogout extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void adactinLogout() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One", "17/05/2017", "18/05/2017", "1 - One", "1 - One")
                .selectHotel()
                .bookHotel("Udhayakumar", "Pasupathi", "108 OMR Road Chennai 97", "                                         1234123412341234", "American Express", "October", "2022", "132").verifyLogout();
    }
}
