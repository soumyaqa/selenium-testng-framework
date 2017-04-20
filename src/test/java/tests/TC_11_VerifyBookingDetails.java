package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/15/2017.
 */
public class TC_11_VerifyBookingDetails extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void bookingDetailsVerification() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One", "17/05/2017", "18/05/2017", "1 - One", "1 - One")
                .selectHotel().verifyBookingDetails("Hotel Creek", "Sydney", "Standard", "1 Day(s)", "AUD $ 125");

    }
}
