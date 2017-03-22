import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;

/**
 * Created by GXP8655 on 3/20/2017.
 */

public class TestCase extends BaseTest {

    @Test
    public void launch_Google() {
        ExtentTest testReporter = Reports.getTest();
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com");
        driver.manage().window().maximize();
        driver.quit();

    }
    @Test
    public void launch_Facebook() {

        ExtentTest testReporter = Reports.getTest();

        System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
        InternetExplorerDriver driver=new InternetExplorerDriver();
        driver.get("http://localhost:8888");
        driver.get("http://www.facebook.com");
        driver.manage().window().maximize();
        driver.quit();

    }
}