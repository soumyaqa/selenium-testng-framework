import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class ChromeTest  extends BaseTest{

    @Test
    public void testChrome(){
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.google.com");
        driver.manage().window().maximize();
        testReporter.log(LogStatus.PASS,"Naviagted to Google.com in Chrome");
    }
}
