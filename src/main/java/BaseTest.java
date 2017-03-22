/**
 * Created by GXP8655 on 3/20/2017.
 */

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/*
 * This is a base Test class. All testng tests needs to inherited from
 * this class. It contains common reporting infra
 */
public class BaseTest {
    private String testName;
    private String testDescription;
    protected WebDriver driver;
    protected ExtentTest testReporter;

    @BeforeMethod
    public void beforeMethod(Method caller) {
        //TODO : Get the test name & description from Excelsheet and pass it to the getTest method
        testName = caller.getName();
        testDescription = "This is a simple test case description";
        if (Reports.report == null)
            Reports.report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
        testReporter = Reports.report.startTest(testName, testDescription);
    }

    @AfterMethod
    public void afterMethod(Method caller) {
        Reports.report.endTest(testReporter);
    }

    @AfterClass
    public void quit() {
        driver.quit();
    }
    @AfterSuite
    public void endReport() {
        if (Reports.report != null) {
            Reports.report.flush();
            Reports.report.close();
        }
    }
}