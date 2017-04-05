package tests;

import com.relevantcodes.extentreports.LogStatus;
import framework.BaseTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class Test_1 extends BaseTest {

    @Test
    public void testGoogle() {

        driver.get("http://www.google.com");
        driver.manage().window().maximize();
      //  testReporter.log(,"Naviagted to Google.com in Chrome");
        logStepWithScreenShot(LogStatus.PASS,"Navigate to Google", "Successfully Navigated to Google");
    }
}
