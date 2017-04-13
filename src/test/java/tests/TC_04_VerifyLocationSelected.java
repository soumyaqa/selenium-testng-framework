package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/12/2017.
 */
public class TC_04_VerifyLocationSelected extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void hotelNameVerification() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One").verifySelectHotel();


        ;
    }
}