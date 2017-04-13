package tests;

import framework.BaseTest;
import org.testng.annotations.Test;
import pages.AdactinHomePage;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class Test_05_ViewBookedHotels extends BaseTest {
    AdactinHomePage loginPage;

    @Test
    public void viewBookedHotel() {
        loginPage = new AdactinHomePage(driver, testReporter, element);
        openApplication("http://www.adactin.com/HotelApp/");
        loginPage.login("home1234", "root1234")
                .searchPage("Sydney", "Hotel Creek", "Standard", "1 - One")
                .selectHotel()
                .bookHotel("Udhayakumar", "Pasupathi", "108 OMR Road Chennai 97", "                                         1234123412341234", "American Express", "October", "2022", "132")
                .viewBookedHotels();
    }
}