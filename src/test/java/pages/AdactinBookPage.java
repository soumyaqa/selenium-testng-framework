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
}
