package pages;

import com.relevantcodes.extentreports.ExtentTest;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
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
        return new HomePage();
    }
}
