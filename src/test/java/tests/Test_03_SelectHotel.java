package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class Test_03_SelectHotel extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void selectHotel() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One")
                .selectHotel();
    }
}
