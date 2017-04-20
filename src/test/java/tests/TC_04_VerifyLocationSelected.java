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
        String user = "home1234";
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login(user, "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One", "17/05/2017", "18/05/2017", "1 - One", "1 - One")
                .verifySelectHotel("Sydney");


        ;
    }
}