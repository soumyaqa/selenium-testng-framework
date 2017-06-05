package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import uistore.HomePageUI;
import uistore.LoginPageUI;

/**
 * Created by GXP8655 on 6/5/2017.
 */
public class LoginPage extends BasePage {

    public LoginPage (WebDriver driver, ExtentTest report, Element element){
        super(driver, report, element);
    }

    public HomePage Login() {
        try {
            element.click(LoginPageUI.storeLocationRadio);
            element.enterText(LoginPageUI.storeNum, "5604");
            element.enterText(LoginPageUI.userName, "stm001");
            element.enterText(LoginPageUI.password, "qa02Test!");
            element.click(LoginPageUI.signinButton);
            if (!waitUntilEnabled(HomePageUI.CreateQuoteButton))
                logStepWithScreenShot(LogStatus.FAIL, "user will see ESVS home page", "ESVS home page is not displayed");
            else
                logStep(LogStatus.PASS, "user will see ESVS home page", "ESVS home page is displayed");
        } catch (Exception e) {
            element.acceptAlert();
            logStepWithScreenShot(LogStatus.FAIL, "Login", "Error Logging In");
            Assert.fail("Login attempt failed");
        }
        return new HomePage(driver, report, element);
    }
}
