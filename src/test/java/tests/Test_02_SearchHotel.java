package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/10/2017.
 */
public class Test_02_SearchHotel extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void setSearchPage() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One", "17/04/2017", "18/04/2017", "1 - One", "1 - One");

    }
}
