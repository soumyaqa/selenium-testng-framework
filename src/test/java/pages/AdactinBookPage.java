package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import uistore.BookHotelPageUI;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class AdactinBookPage extends BasePage {


    public AdactinBookPage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public AdactinBookingConfirmationPage bookHotel(String firstName, String lastName, String billAddress, String cardNumber, String cardType, String expiryMonth, String expiryYear, String cvv) {
        element.enterText(BookHotelPageUI.firstName, firstName);
        element.enterText(BookHotelPageUI.lastName, lastName);
        element.enterText(BookHotelPageUI.billAddress, billAddress);
        element.enterText(BookHotelPageUI.cardNumber, cardNumber);
        element.selectByText(BookHotelPageUI.cardType, cardType);
        element.selectByText(BookHotelPageUI.expiryMonth, expiryMonth);
        element.selectByText(BookHotelPageUI.expiryYear, expiryYear);
        element.enterText(BookHotelPageUI.cvv, cvv);
        element.click(BookHotelPageUI.clickBookNow);
        element.wait(10);
        logStepWithScreenShot(LogStatus.PASS, "Hotel Booking", "Hotel Booked Successfully");
        return new AdactinBookingConfirmationPage(driver, report, element);
    }

    public AdactinBookingConfirmationPage verifyBookingDetails(String hotelName, String location, String roomType, String totalDay, String pricePerNight) {
        String hotelNameDisplayed, locationDisplayed, roomTypeDisplayed, totalDayDisplayed, pricePerNightDisplayed;
        hotelNameDisplayed = element.getText(BookHotelPageUI.hotelName);
        locationDisplayed = element.getText(BookHotelPageUI.location);
        roomTypeDisplayed = element.getText(BookHotelPageUI.roomType);
        totalDayDisplayed = element.getText(BookHotelPageUI.totalDays);
        pricePerNightDisplayed = element.getText(BookHotelPageUI.pricePerNight);
        if (hotelNameDisplayed.equalsIgnoreCase(hotelName) && locationDisplayed.equalsIgnoreCase(location) && roomTypeDisplayed.equalsIgnoreCase(roomType) && totalDayDisplayed.equalsIgnoreCase(totalDay) && pricePerNightDisplayed.equalsIgnoreCase(pricePerNight))
            logStepWithScreenShot(LogStatus.PASS, "Details Match with Selected hotel data", "Both the Details are Same");
        else
            logStepWithScreenShot(LogStatus.FAIL, "Details Match with Selected hotel data", "Both the Details are Not Same");

        return new AdactinBookingConfirmationPage(driver, report, element);
    }

    public AdactinBookPage verifyTotalPrice(String totalPrice) {
        String totalPriceCalculated = element.getText();
        return this;
    }
}
