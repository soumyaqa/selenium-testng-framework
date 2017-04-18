package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import uistore.SelectHotelPageUI;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class AdactinSelectPage extends BasePage {
    public AdactinSelectPage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public AdactinBookPage selectHotel() {
        element.click(SelectHotelPageUI.selectHotel);
        element.click(SelectHotelPageUI.clickContinue);
        logStepWithScreenShot(LogStatus.PASS, "Hotel Selection", "Hotel Selected Successfully");
        return new AdactinBookPage(driver, report, element);
    }

    public AdactinSelectPage verifySelectHotel(String hotel) {
        String selectedHotel = element.getText(SelectHotelPageUI.hotelLocationVerification);
        if (selectedHotel.equalsIgnoreCase(hotel))
            logStepWithScreenShot(LogStatus.PASS, "Hotel Name Should be Same", "Hotel Name Displayed in the Select Page is Same as Selected in Search Page");

        else {

            logStepWithScreenShot(LogStatus.FAIL, "Hotel Name Should be Same", "Hotel Name Displayed in the Select Page is Not Same as Selected in Search Page");
            Assert.fail("Hotel displayed does not match the input provided");

        }
        return this;
    }

    public AdactinSelectPage verifyDates(String checkInDate, String checkOutDate) {
        String arrivalDate, depatureDate;
        arrivalDate = element.getText(SelectHotelPageUI.arrivalDate);
        depatureDate = element.getText(SelectHotelPageUI.depatureDate);
        if (arrivalDate.equalsIgnoreCase(checkInDate) && depatureDate.equalsIgnoreCase(checkOutDate))
            logStepWithScreenShot(LogStatus.PASS, "Arrival and Depature Date Should be Same", "Dates Displayed Match With the Entered Date");
        else {
            logStepWithScreenShot(LogStatus.FAIL, "Arrival and Depature Date Should be Same", "Dates Not Matchd With Test Data");

        }
        return this;
    }

    public AdactinSelectPage verifyRoomCount(String noofRoomsSelected) {
        String roomCount = element.getText(SelectHotelPageUI.noofRoomsSelected);
        if (roomCount.equalsIgnoreCase(noofRoomsSelected))
            logStepWithScreenShot(LogStatus.PASS, "Room Count Should be Same", "Room Count Matched with Test Data");
        else {
            logStepWithScreenShot(LogStatus.FAIL, "Room Count Should be Same", "Room Count Not Matched with Test Data");

        }
        return this;
    }

    public AdactinSelectPage verifyRoomType(String roomTypeSelected) {
        String roomType = element.getText(SelectHotelPageUI.roomType);
        if (roomType.equalsIgnoreCase(roomTypeSelected))
            logStepWithScreenShot(LogStatus.PASS, "Room Type Should be Same", "Room Type Matched with Test Data");
        else {
            logStepWithScreenShot(LogStatus.FAIL, "Room Type Should be Same", "Room Type Not Matched with Test Data");

        }
        return this;
    }

}