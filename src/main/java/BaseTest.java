/**
 * Created by GXP8655 on 3/20/2017.
 */

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
 * This is a base Test class. All testng tests needs to inherited from
 * this class. It contains common reporting infra
 */
public class BaseTest {
    protected WebDriver driver;
    protected ExtentTest testReporter;
    protected Properties configProperties;
    protected Map<String,String> jenkinsProperties;
    private String testName;
    private String testDescription;
    private String runMode;
    private String browserName;
    private String platform;

    public BaseTest() {
        //load configs
        configProperties = new Properties();
        try {
            InputStream in = new FileInputStream("./config.properties");
            configProperties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading config.properties");
        }
        runMode = getConfigProperty("run.mode");
        browserName = getConfigProperty("browserName");
        platform = getConfigProperty("platform");
        //Override with Jenkins properties
        Properties systemProperties = System.getProperties();
/*        for (String key : systemProperties.stringPropertyNames())
        {
            if(key.contains("jenkins"))
                jenkinsProperties.put(key,systemProperties.getProperty(key));
        }*/
        for (String key : systemProperties.stringPropertyNames()){
            if(key.contains("run.mode"))
                runMode = systemProperties.getProperty(key);
            else if(key.contains("browserName"))
                browserName = systemProperties.getProperty(key);
            else if(key.contains("platform"))
                platform = systemProperties.getProperty(key);
        }
        //initialize drivers
        if (runMode.equalsIgnoreCase("local")) {
            if (getConfigProperty("capabilities.source").equalsIgnoreCase("config")) {
                // Reports.report.addSystemInfo("Browser", getConfigProperty("capabilities.browser"));
                if (browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("INTERNET EXPLORER"))
                    driver = new DriverManager().getLocalIEDriver();
                else if (browserName.equalsIgnoreCase("CHROME"))
                    driver = new DriverManager().getLocalChromeDriver();
                else {
                    System.out.println("invalid browser config. Provide either IE or Chrome");
                    System.exit(1);
                }

            }
            //write else for excel configs

        }
        //add else for remote execution
        //Reports.report.addSystemInfo("Browser", "Multi-Browser");
        else if (runMode.equalsIgnoreCase("remote")) {
            URL remoteURL = null;
            DesiredCapabilities capabilities = null;

            if (getConfigProperty("capabilities.source").equalsIgnoreCase("config")) {
                // Reports.report.addSystemInfo("Browser", getConfigProperty("capabilities.browser"));
                if (browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("INTERNET EXPLORER")) {
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability("ignoreProtectedModeSettings", true);
                    capabilities.setCapability("ensureCleanSession", true);

                    try {
                        Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error getting local IE driver instance");
                    }
                } else if (browserName.equalsIgnoreCase("CHROME"))
                    capabilities = DesiredCapabilities.chrome();
                else {
                    System.out.println("invalid browser config. Provide either IE or Chrome");
                    System.exit(1);
                }
            }
            //write else for excel configs

            switch (platform.toUpperCase()) {
                case "WINDOWS":
                    capabilities.setPlatform(Platform.WINDOWS);
                    break;
                case "LINUX":
                    capabilities.setPlatform(Platform.LINUX);
                    break;
                default:
                    capabilities.setPlatform(Platform.ANY);
            }
            try {
                remoteURL = new URL(getConfigProperty("grid.url"));
                driver = new RemoteWebDriver(remoteURL, capabilities);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(60L, TimeUnit.SECONDS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

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

/*    @AfterSuite
    public void endReport() throws InterruptedException {
        if (Reports.report != null) {
            Reports.report.flush();
            Reports.report.close();
        }
    }*/

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

    protected String getConfigProperty(String key) {
        return configProperties.getProperty(key);
    }
}