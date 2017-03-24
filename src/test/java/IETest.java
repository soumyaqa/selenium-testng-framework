import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class IETest extends BaseTest{

    @Test
    public void testIE(){

        StringBuilder driverPath = new StringBuilder();
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        File ieDriverPath = new File("./drivers/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", ieDriverPath.getAbsolutePath());
        System.setProperty("ie.ensureCleanSession", "true");
        driver = new InternetExplorerDriver(capabilities);

        driver.get("http://www.facebook.com");
        driver.manage().window().maximize();
        System.out.println(waitForElement(By.id("email")));
        logStepWithScreenShot(LogStatus.PASS,"Navigate to Facebook", "Successfully Navigated to Facebook");
    }
}
