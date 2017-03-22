import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class IETest extends BaseTest{

    @Test
    public void testIE(){
        System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
        System.setProperty("ie.ensureCleanSession", "true");
        driver = new InternetExplorerDriver();
        driver.get("http://www.facebook.com");
        driver.manage().window().maximize();
        testReporter.log(LogStatus.PASS,"Naviagted to Facebook.com in IE");
    }
}
