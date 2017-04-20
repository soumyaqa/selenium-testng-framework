package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/17/2017.
 */
public class TC_06_VerifyRoomCount extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void verifyRoomCount() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "3 - Three", "17/05/2017", "18/05/2017", "1 - One", "1 - One")
                .verifyRoomCount("3 Rooms")
        ;

    }
}
