package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/19/2017.
 */
public class TC_10_VerifyFinalBillWithGST extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void verifyTotalFinalBillWithGST() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelAppBuild2/");
        loginPage.login("home1234", "root1234")
                .searchPage("Melbourne", "Hotel Creek", "Standard", "2 - Two", "17/05/2017", "18/05/2017", "1 - One", "1 - One")
                .selectHotel()
                .verifyTotalPriceWithGST("AUD $ 275");
    }
}
