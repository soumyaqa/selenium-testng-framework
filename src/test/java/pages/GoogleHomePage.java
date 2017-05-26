package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import uistore.GoogleHomePageUI;

/**
 * Created by GXP8655 on 4/6/2017.
 */
public class GoogleHomePage extends BasePage{

    public GoogleHomePage (WebDriver driver, ExtentTest report, Element element){
            super(driver, report, element);
    }
    public GoogleHomePage search(String searchText){
        element.enterText(GoogleHomePageUI.searchTextField,searchText);
        element.enterKeys(GoogleHomePageUI.searchTextField, Keys.ENTER);
        element.wait(2);
        logStepWithScreenShot(LogStatus.PASS,"Search test", "results for 'test' is returned");
        return new GoogleHomePage(driver, report, element);
    }

}
