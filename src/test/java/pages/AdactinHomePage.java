package pages;

import com.relevantcodes.extentreports.ExtentTest;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import uistore.AdactinLoginPageUI;

/**
 * Created by Udhayakumar on 4/10/2017.
 */
public class AdactinHomePage extends BasePage {
    public AdactinHomePage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public AdactinHomePage Login(String username, String pasword) {
        element.enterText(AdactinLoginPageUI.userName, username);
        element.enterText(AdactinLoginPageUI.passWord, pasword);
        element.click(AdactinLoginPageUI.login);
        return new AdactinHomePage(driver, report, element);
    }
}

