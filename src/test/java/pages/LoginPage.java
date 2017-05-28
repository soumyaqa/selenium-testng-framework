package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import uistore.Home;
import uistore.LoginUI;

/**
 * Created by gunap on 5/28/2017.
 */
public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver, ExtentTest report, Element element) {
            super(driver,report, element);
    }

    public HomePage login(String userName, String password){
        element.enterText(LoginUI.userName,userName);
        element.enterText(LoginUI.password,password);
        element.click(LoginUI.loginButton);
        if (element.waitForElement(Home.userNameShown))
            logStepWithScreenShot(LogStatus.PASS, "Verify is Adactin home page is displayed","System displays Adactin Home page");
        else {
            logStepWithScreenShot(LogStatus.FAIL, "Verify is Adactin home page is displayed", "Error : System does not display Adactin Home page");
            Assert.fail();
        }

        return new HomePage();
    }
}
