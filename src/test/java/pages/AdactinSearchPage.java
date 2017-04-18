package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import uistore.SearchPageUI;

/**
 * Created by Udhayakumar on 4/10/2017.
 */
public class AdactinSearchPage extends BasePage {

    public AdactinSearchPage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public AdactinSelectPage searchPage(String location, String hotels, String roomType, String noofRooms, String arrivalDate, String depatureDate, String adult, String children) {
/*
            element.enterText(SearchPageUI.userName, username);
            element.enterText(SearchPageUI.passWord, password);
            element.click(SearchPageUI.login);*/
        element.selectTextByValue(SearchPageUI.locations, location);
        element.selectByText(SearchPageUI.hotels, hotels);
        element.selectByText(SearchPageUI.roomType, roomType);
        element.selectByText(SearchPageUI.noOfRooms, noofRooms);
        element.enterText(SearchPageUI.chechInDate, arrivalDate);
        element.enterText(SearchPageUI.checkOutDate, depatureDate);
        element.selectByText(SearchPageUI.adults, adult);
        element.selectByText(SearchPageUI.children, children);
        element.click(SearchPageUI.search);
        element.wait(3);
        logStepWithScreenShot(LogStatus.PASS, "Hotel Search", "Hotel Searched Successfully");
        // return this;


        return new AdactinSelectPage(driver, report, element);
    }


    public AdactinSearchPage chechOutDate(String location, String hotels, String roomType, String noOfRooms,
                                          String chechINDate, String checkOUTDate, String adults, String children) {
        element.selectTextByValue(SearchPageUI.locations, location);
        element.selectByText(SearchPageUI.hotels, hotels);
        element.selectByText(SearchPageUI.roomType, roomType);
        element.selectByText(SearchPageUI.noOfRooms, noOfRooms);
        element.enterText(SearchPageUI.chechInDate, chechINDate);
        element.enterText(SearchPageUI.checkOutDate, checkOUTDate);
        element.selectByText(SearchPageUI.adults, adults);
        element.selectByText(SearchPageUI.children, children);
        element.click(SearchPageUI.search);
        logStepWithScreenShot(LogStatus.PASS, "Check-In Date Verification", "Check-In Date Shall Before than Check-Out Date");
        //element.getText(SearchPageUI.chechInError);
        return this;
    }


}
