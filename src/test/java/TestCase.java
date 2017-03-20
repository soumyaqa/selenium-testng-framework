import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;

/**
 * Created by GXP8655 on 3/20/2017.
 */

public class TestCase extends BaseTest {

    @Test
    public void test001() {
        ExtentTest testReporter = Reports.getTest();
        testReporter.log(LogStatus.INFO, "first test case");
    }
    @Test
    public void test002() {

        ExtentTest testReporter = Reports.getTest();
        testReporter.log(LogStatus.INFO, "second test case");
    }
}