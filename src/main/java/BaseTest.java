/**
 * Created by GXP8655 on 3/20/2017.
 */

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

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
        if (Reports.report == null) {
            Utils.deleteDirectory("./reports");
            Reports.report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
        }
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

    public void logStep(LogStatus status, String expected, String actual) {
        testReporter.log(status, expected, actual);
    }

    public void logStepWithScreenShot(LogStatus status, String expected, String actual) {
        long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
        try {
            File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(image, new File("./reports/images/" + number + ".jpg"));
        } catch (WebDriverException | IOException e) {
            e.printStackTrace();
        }
        testReporter.log(status, expected, actual + testReporter.addScreenCapture("./../reports/images/" + number + ".jpg"));
    }

    public boolean waitForElement(By by) {
        boolean isElementPresent = false;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(30, SECONDS)
                    .pollingEvery(50, TimeUnit.MILLISECONDS)
                    .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
            //WebElement found = wait.until((arg0)->{return driver.findElement(by);});

            WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(by);
                }
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}