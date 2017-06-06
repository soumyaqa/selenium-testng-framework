package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import uistore.CustomerSearchUI;
import uistore.ProductEntryUI;

/**
 * Created by GXP8655 on 6/5/2017.
 */
public class CustomerSearchPage extends BasePage {

    private String orderNumber;

    public CustomerSearchPage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public ProductEntryPage searchCustomer() {
        try {
            //Enter customer Phone Number
            element.enterText(CustomerSearchUI.phone, configProperties.getProperty("customerPhoneNumber"));
            //Click Search button
            if(!element.click(CustomerSearchUI.search))
                logStepWithScreenShot(LogStatus.FAIL,"Click Search button","Search button is not found");
            else
                logStep(LogStatus.PASS,"Click Search button","Search results is displayed");
            //Click on Create Quote button shown against the first customer listed
            element.click(CustomerSearchUI.createOrder);
            driver.switchTo().defaultContent();
            if (element.waitForElement(ProductEntryUI.orderNumber)) {
                orderNumber = element.getText(ProductEntryUI.orderNumber);
                logStep(LogStatus.PASS, "Click 'Create Order' button", "Product Entry page is displayed");
                logStep(LogStatus.PASS, "Order Number ",orderNumber );
            }
            else {
                logStepWithScreenShot(LogStatus.FAIL, "Click 'Create Order' button", "Product Entry page is not displayed");
                Assert.fail("Product entry page is not displayed");
            }
        } catch (Exception e) {
            Assert.fail("Error in customer Search");
        }
        return new ProductEntryPage(driver, report, element);
    }
}
