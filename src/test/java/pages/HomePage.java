package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import uistore.CustomerSearchUI;
import uistore.HomePageUI;

/**
 * Created by GXP8655 on 6/5/2017.
 */
public class HomePage extends BasePage {

    public HomePage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public HomePage clickOnCreateQuote() {
        boolean flag = false;
        try {
            //Click Create and Maintain Button
            click(HomePageUI.CreateQuoteButton);
            //Switching from Flex to HTML
            driver.switchTo().frame(driver.findElement(CustomerSearchUI.frame));
            flag = VerifyCustomerProfileDisplayed();
            if (!flag) {
                logStepWithScreenShot(LogStatus.FAIL, "System should display the Customer Profile screen", "System failed to display the Customer Profile screen");
                Assert.fail("Error Loading customer search screen");
            } else
                logStepWithScreenShot(LogStatus.PASS, "System should display the Customer Profile screen", "System failed to display the Customer Profile screen");
        } catch (Exception e) {
            e.printStackTrace();
            logStepWithScreenShot(LogStatus.FAIL, "Click on Create Quote button", "Customer Search screen is not displayed");
            Assert.fail("clickOnCreateQuote");
        }
        logStepWithScreenShot(LogStatus.PASS, "Click on Create Quote button", "Customer Search screen is displayed");
        return this;
    }

    private boolean VerifyCustomerProfileDisplayed() {
        try {
            String label = element.getText(CustomerSearchUI.custProfileLabel);
            if (label.equals(CustomerSearchUI.label) && element.waitForElement(CustomerSearchUI.search)) {
                logStepWithScreenShot(LogStatus.PASS, "System should display the Customer Profile screen", "System displays the Customer Profile screen successfully");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
