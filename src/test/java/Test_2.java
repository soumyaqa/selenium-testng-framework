import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class Test_2 extends BaseTest {

    @Test
    public void testFacebook() {

        driver.get("http://www.facebook.com");
        driver.manage().window().maximize();
        logStepWithScreenShot(LogStatus.PASS, "Navigate to Facebook", "Successfully Navigated to Facebook");
    }
}
