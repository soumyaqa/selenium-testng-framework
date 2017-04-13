package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import uistore.ViewBookingPageUI;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class AdactinBookingConfirmationPage extends BasePage {
    public AdactinBookingConfirmationPage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public AdactinViewBookingPage viewBookedHotels() {
        element.click(ViewBookingPageUI.viewBookedHotels);
        logStepWithScreenShot(LogStatus.PASS, "Show Booked Hotels", "Booked Hotels Showed Successfully");
        return new AdactinViewBookingPage(driver, report, element);
    }


}
