package framework; /**
 * Created by GXP8655 on 3/20/2017.
 */

import com.google.common.base.Function;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.*;
import uistore.CustomerSearchUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
 * This is a base Test class. All testng tests needs to inherited from
 * this class. It contains common reporting infra
 */
public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports report;
    protected ExtentTest testReporter;
    protected Properties configProperties;
    //protected Map<String, String> jenkinsProperties;
    private String testName;
    private String testDescription;
    private String runMode;
    private String browserName;
    private String platform;
    private boolean isJenkinsRun = false;
    protected Element element;

    public BaseTest() {
        loadConfig();
        setRunConfigs();
        //initialize drivers
        if (runMode.equalsIgnoreCase("local"))
            getLocalDriver();
            //add else for remote execution
            //report.addSystemInfo("Browser", "Multi-Browser");
        else if (runMode.equalsIgnoreCase("remote"))
            getRemoteDriver();
        element = new Element(driver);
    }

    @BeforeMethod
    public void beforeMethod(Method caller) {
        //TODO : Get the test name & description from Excelsheet and pass it to the getTest method
        testName = caller.getName();
        testDescription = "This is a simple test case description";
        if (report == null) {
            Utils.deleteDirectory("./reports");
            report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
        }
        testReporter = report.startTest(testName, testDescription);
    }

    @AfterMethod
    public void afterMethod(Method caller) {
        report.endTest(testReporter);
    }

    @AfterClass
    public void quit() {
        if (driver != null)
            driver.quit();
    }

    @BeforeSuite
    public void initReport() {
        Utils.deleteDirectory("./reports");
        BaseTest.report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
    }

    @AfterSuite
    public void endReport() {

        if (report != null) {
            report.flush();
            report.close();
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

        testReporter.log(status, expected, actual + testReporter.addScreenCapture("./images/" + number + ".jpg"));
    }

    public void openApplication(String appURL) {
        try {
            driver.get(appURL);
            driver.manage().window().maximize();
            logStepWithScreenShot(LogStatus.PASS, "Navigate to " + appURL, "Successfully navigated to " + appURL);
        } catch (Exception e) {
            logStepWithScreenShot(LogStatus.FAIL, "Navigate to " + appURL, "Failed to navigated to " + appURL);
            Assert.fail();
        }
    }
    public void openApplication() {
        String appURL = "http://stXXXX.homedepot.com:12040/MMSVESVSWeb/main.html#";
        appURL = appURL.replace("XXXX", configProperties.getProperty("storeNumber"));
        try {
            driver.get("http://st5604.homedepot.com:12040/MMSVESVSWeb/main.html#");
            driver.manage().window().maximize();
            logStep(LogStatus.PASS, "Navigate to : <br><a href = '" + appURL+ "'> "+appURL+"  </a>", "Successfully navigated to : <br><a href = '" + appURL+ "'>"+appURL+" </a>");
        } catch (Exception e) {
            logStepWithScreenShot(LogStatus.FAIL, "Navigate to " + appURL, "Failed to navigated to " + appURL);
            Assert.fail();
        }
    }

    public void getLocalChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
    }

    public void getLocalIEDriver() {
        File ieDriverPath = new File("./drivers/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", ieDriverPath.getAbsolutePath());
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("ensureCleanSession", true);
        driver = new InternetExplorerDriver(capabilities);
/*        driver.manage().deleteAllCookies();
        try {
            Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error getting local IE driver instance");
        }*/
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(100L, TimeUnit.SECONDS);
    }


    protected String getConfigProperty(String key) {
        return configProperties.getProperty(key);
    }

    protected void loadConfig() {
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
    }

    public void getLocalDriver() {
        if (getConfigProperty("capabilities.source").equalsIgnoreCase("config")) {
            //report.addSystemInfo("Browser", getConfigProperty("capabilities.browser"));
            if (browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("INTERNET EXPLORER"))
                getLocalIEDriver();
            else if (browserName.equalsIgnoreCase("CHROME"))
                getLocalChromeDriver();
            else {
                System.out.println("invalid browser config. Provide either IE or Chrome");
                System.exit(1);
            }

        }
        //write else for excel configs
    }

    public void getRemoteDriver() {
        URL remoteURL = null;
        DesiredCapabilities capabilities = null;

        if (getConfigProperty("capabilities.source").equalsIgnoreCase("config")) {
            // report.addSystemInfo("Browser", getConfigProperty("capabilities.browser"));
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
            driver.manage().timeouts().implicitlyWait(100L, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setRunConfigs() {
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
        for (String key : systemProperties.stringPropertyNames()) {
            if (key.contains("run.mode")) {
                runMode = systemProperties.getProperty(key);
                isJenkinsRun = true;
            } else if (key.contains("browserName")) {
                browserName = systemProperties.getProperty(key);
                isJenkinsRun = true;
            } else if (key.contains("platform")) {
                platform = systemProperties.getProperty(key);
                isJenkinsRun = true;
            }
        }
    }

    //FLASH

    //Project specific
    String flashObjectId = "main";

    private String compId = "";
    private String value = "";
    private Boolean isVisible = false;
    private Boolean isEnabled = false;
    private Boolean isNotEmpty = false;

    public String call(String functionName, String... args) {
        Object result = ((JavascriptExecutor) driver).executeScript(makeJsFunction(functionName, args), new Object[0]);
        return result != null ? result.toString() : null;
    }

    private String makeJsFunction(String functionName, String... args) {
        StringBuffer functionArgs = new StringBuffer();

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (i > 0)
                    functionArgs.append(",");
                functionArgs.append(String.format("'%1$s'", args[i]));
            }
        }
        return String.format(
                "return document.%1$s.%2$s(%3$s);",
                flashObjectId,
                functionName,
                functionArgs);
    }

    public String getProperty(String objectId, String property) {
        return call("getFlexProperty", objectId, property);
    }

    public String getGridCellText(String gridId, int rowIndex, String columnHeaderId) {
        String cellText = "";
        waitUntilVisible(gridId);
        if (getProperty(gridId, "className").contains("ADG")) {
            cellText = call("getADGHiearchicalDataCellText", gridId, Integer.toString(rowIndex), columnHeaderId);
        } else {
            cellText = call("getFlexDataGridFieldValueForGridRow", gridId + "," + columnHeaderId + "," + rowIndex);
        }

        return cellText;
    }

    public int getGridRowIndex(String gridId, String columnHeaderId, String fieldValue) {
        int rowIndex = -1;
        int rowCount;
        String value;
        String sIndex = "";
        String skuGridProductEntry="skuDetailDataGrid"; //Project specific
        if (gridId.equalsIgnoreCase(skuGridProductEntry)) {
            rowCount = getGridRowCount(gridId);
            for (int i = 0; i < rowCount; i++) {
                value = getGridCellText(gridId, i, columnHeaderId);
                if (fieldValue.equalsIgnoreCase(value)) {
                    rowIndex = i;
                    break;
                }
            }
        } else if (getProperty(gridId, "className").contains("ADG")) {
            sIndex = call("getADGHiearchicalDataRowIndex", gridId, fieldValue, columnHeaderId);
            if ((sIndex != null) && (!sIndex.isEmpty())) {
                rowIndex = Integer.parseInt(sIndex);
            }
        } else {
            rowCount = getGridRowCount(gridId);
            for (int i = 0; i < rowCount; i++) {
                value = getGridCellText(gridId, i, columnHeaderId);
                if (fieldValue.equalsIgnoreCase(value)) {
                    rowIndex = i;
                    break;
                }
            }
        }

        return rowIndex;
    }

    public void clickTab(String tabId, String tabName) throws Exception {
        this.click(tabId, tabName);
    }

    /**
     * Click tab and waits for element on tab page to appear.  Re-tries if element not visible.
     *
     * @param tabId
     * @param tabName
     * @param expectedElemId
     */
    public void clickTab(String tabId, String tabName, String expectedElemId) throws Exception {
        clickTab(tabId, tabName);
        for (int i = 0; i < 5; i++) {
            if (isVisible(expectedElemId)) {
                break;
            }
            element.wait(2);
            this.click(tabId, tabName);
        }
    }

    public void click(String objectId, String args) throws Exception {
        this.waitForElement(objectId);
        call("doFlexClick", objectId, args);
    }

    public void clickGridItem(String gridId, String itemType, int rowNumber, int colNumber) throws Exception {
        this.waitForElement(gridId);
        if (getProperty(gridId, "className").contains("ADG")) {
            call("triggerADGItemRendererClickEvent", gridId, itemType, Integer.toString(rowNumber), Integer.toString(colNumber));
        } else {
            call("triggerMouseEventOnDataGridItemRenderer", gridId, "click", Integer.toString(rowNumber), Integer.toString(colNumber), itemType);
        }

    }

    public void click(String objectId) throws Exception {
        try {
            if (waitForElement(objectId)) {
                call("doFlexClick", objectId, "");
            } else {
                testReporter.log(LogStatus.FAIL, "FlexWebDriver.click", objectId + "not found or not visible");
            }
        } catch (Exception e) {
            e.getMessage();
            testReporter.log(LogStatus.FAIL,"FlexWebDriver.click", "Error clicking " + objectId);
        }
    }

    public void mouseClick(String objectId) throws Exception {
        try {
            if (waitForElement(objectId)) {
                call("triggerMouseEvent", "click", objectId);
            } else {
                testReporter.log(LogStatus.FAIL,"FlexWebDriver.click", objectId + "not found or not visible");
            }
        } catch (Exception e) {
            e.getMessage();
            testReporter.log(LogStatus.FAIL,"FlexWebDriver.click", "Error clicking " + objectId);
        }
    }

    public String clickInGrid(String gridId, int colIndex, String text) {
        try {
            this.waitForElement(gridId);
            return call("doFlexClickDataGridItem", gridId, Integer.toString(colIndex) + "," + text);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void type(String objectId, String textToType) {
        try {
            this.waitForElement(objectId);
            call("doFlexType", objectId, "");
            call("doFlexType", objectId, textToType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressEnter(String objectId) {
        try {
            this.waitForElement(objectId);
            call("triggerEvent", "13", "keyDown", objectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressEnter2(String objectId) throws Exception {
        this.waitForElement(objectId);
        element.wait(1);
        call("doFlexEnterKey", objectId, "");
        element.wait(1);
    }

    public void typeAndPressEnter(String objectId, String textToType) {
        try {
            this.waitForElement(objectId);
            this.focusIn(objectId);
            this.type(objectId, textToType);
            this.pressEnter(objectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkBox(String checkboxId) throws Exception {
        this.waitForElement(checkboxId);
        call("doFlexCheckBox", checkboxId, "check");
    }

    public void uncheckBox(String checkboxId) throws Exception {
        this.waitForElement(checkboxId);
        call("doFlexCheckBox", checkboxId, "uncheck");
    }

    public String select(String objectId, String value) {
        try {
            this.waitForElement(objectId);
            return call("doFlexSelectIndex", objectId, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String select(String objectId, int index) {
        try {
            this.waitForElement(objectId);
            return call("doFlexSelectIndex", objectId, Integer.toString(index));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void gridScroll(String gridId, int rowIndex) {
        try {
            select(gridId, rowIndex);
            call("doFlexADGScroll", gridId, Integer.toString(rowIndex));
            element.wait(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectInPopup(String objectId, int index) {
        try {
            this.waitForPopup(objectId);
            element.wait(3);
            call("triggerSelectEventOnPopupWindow", Integer.toString(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickInPopup(String objectId) {
        try {
            //    this.waitForPopup(objectId);
            element.wait(3);
            call("triggerClickEventOnPopupWindowField", "click", objectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean waitForPopup(String popupClass) {
        compId = popupClass;
        isVisible = false;
        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, SECONDS)
                .pollingEvery(50, TimeUnit.MILLISECONDS)
                .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
        wait.until(new Function() {
            @Override
            public Boolean apply(Object arg0) {
                isVisible = Boolean.valueOf(call("getPopupPresent", compId));
                return isVisible;
            }
        });
        return isVisible;
    }

    public Boolean waitForAlert() {
        isVisible = false;
        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(50, TimeUnit.MILLISECONDS)
                .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
        wait.until(new Function() {
            public Boolean apply(Object arg0) {
                isVisible = Boolean.valueOf(call("getFlexAlertPresent", "", ""));
                return isVisible;
            }
        });
        return isVisible;
    }

    public boolean isPopupPresent(String popupClass) {
        call("getPopupPresent", popupClass);
        return false;
    }

    public void waitForGridToLoad(String gridId) {
        this.waitForRowAdded(gridId, 0);
    }

    public void waitForRowAdded(String gridId, int previousCount) {
        try {
            for (int i = 0; i < 30; i++) {
                if (this.getGridRowCount(gridId) > previousCount) {
                    break;
                } else {
                    element.wait(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGridRowCount(String objectId) {
        try {
            this.waitUntilVisible(objectId);
            return Integer.parseInt(call("getFlexDataGridRowCount", objectId, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean waitForText(String elemId) throws Exception {
        compId = elemId;
        isNotEmpty = false;
        waitUntilVisible(elemId);
        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, SECONDS)
                .pollingEvery(50, TimeUnit.MILLISECONDS)
                .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
        wait.until(new Function() {
            @Override
            public Boolean apply(Object arg0) {
                isNotEmpty = !Boolean.valueOf(call("getFlexText", compId, "").isEmpty());
                return isNotEmpty;
            }
        });
        return isNotEmpty;
    }

    public void waitForTextChange(String elemId, String text) throws Exception {
        compId = elemId;
        value = text;
        waitUntilVisible(elemId);
        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(50, TimeUnit.MILLISECONDS)
                .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
        wait.until(new Function() {
            @Override
            public Boolean apply(Object arg0) {
                value.equalsIgnoreCase((call("getFlexText", compId, "")));
                return true;
            }
        });
        element.wait(1);
    }

    public void selectDateTimePref(String calId, String dateTimeLabelId, String date, String timePref, String flowType) {
        call("doFlexDate", calId, date);
        call("setDeliveryDateTimePref", flowType, calId, dateTimeLabelId, timePref);
        type(dateTimeLabelId, date + "/" + timePref);
    }

    public void waitForBlockerNotPresent() {
        this.waitForElementNotPresent("blocker");
        this.waitForElementNotPresent("modalWindow");
    }

    public void waitForElementNotPresent(String elemId) {
        int i = 0;
        element.wait(2);
        while (true) {

            if (isVisible(elemId)) {
                element.wait(1);
            } else {
                break;
            }

            if (i == 30) {

                break;
            }

            i++;
        }
    }

    public String getText(String objectId) {
        try {
            this.waitUntilVisible(objectId);
            return call("getFlexText", objectId, "");
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public String getTabName(String objectId) {
        try {
            this.waitUntilVisible(objectId);
            return call("getSelectedTabName", objectId);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public void clickMenuItem(String menuId, String menuItemText) {
        call("triggerMenuItemEvent", "itemClick", "label", menuItemText, menuId);
    }

    public void clickMenuItemInPopUp(String menuId, String menuItemText) {
        call("triggerMenuItemEventOnPopup", "itemClick", "label", menuItemText, menuId);
    }

    public String focusIn(String objectId) {
        return call("triggerFocusEvent", "focusIn", objectId);

    }

    public String focusOut(String objectId) {
        return call("triggerFocusEvent", "focusOut", objectId);
    }

    public int PercentLoaded() {
        return (new Integer(this.call("PercentLoaded", new String[0]))).intValue();
    }

    public void waitUntilLoaded() throws Exception {
        while (this.PercentLoaded() != 100) {
            ;
        }
        Thread.sleep(100L);
    }

    public Boolean isVisible(String elemId) {
        return Boolean.valueOf(call("getFlexVisible", elemId, ""));
    }

    public Boolean isEnabled(String elemId) {
        return Boolean.valueOf(call("getFlexEnabled", elemId, ""));
    }

    public Boolean waitForElement(String elemId) throws Exception {
        try {
            isVisible = waitUntilVisible(elemId);
            if (!isVisible) {
                return false;
            }
            isEnabled = waitUntilEnabled(elemId);
            if (isVisible && isEnabled) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("wait " + elemId + " failed");
            return false;
        }
    }

    public Boolean alertExists() {
        element.wait(3);
        return Boolean.parseBoolean(call("getFlexAlertPresent", "", ""));
    }

    public String getAlertText() {
        return call("getFlexAlertText");
    }

    public void clickAlertYes() {
        call("doFlexAlertResponse", "Yes", "Yes");
    }

    public void clickAlertNo() {
        call("doFlexAlertResponse", "No", "No");
    }

    public void handleAlertYes() {
        if (alertExists()) {
            clickAlertYes();
        }
    }

    public void handleAlertOk() {
        if (alertExists()) {
            clickAlertOk();
        }
    }

    public void clickAlertOk() {
        call("doFlexAlertResponse", "OK", "OK");
    }

    public String selectComboBoxValueInGrid(String elemId, int row, int col, String value) throws Exception {
        this.waitForElement(elemId);
        return call("doFlexSelectComboInDGItemRendererByLabel", elemId, String.valueOf(row), String.valueOf(col), value, "ComboBox");
    }

    public void selectComboBoxValue(String elemId, String value) {
        try {
            this.waitForElement(elemId);
            call("doFlexSelectComboByLabel", elemId, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean waitUntilVisible(String elemId) {
        try {
            compId = elemId;
            isVisible = false;
            Wait wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(60, SECONDS)
                    .pollingEvery(50, TimeUnit.MILLISECONDS)
                    .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
            wait.until(new Function() {
                @Override
                public Boolean apply(Object arg0) {
                    isVisible = Boolean.valueOf(call("getFlexVisible", compId, ""));
                    return isVisible;
                }
            });
            return isVisible;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Boolean waitUntilEnabled(String elemId) throws Exception {
        compId = elemId;
        isEnabled = false;
        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
        wait.until(new Function() {
            @Override
            public Boolean apply(Object arg0) {
                isEnabled = Boolean.valueOf(call("getFlexEnabled", compId, ""));
                return isEnabled;
            }
        });
        return isEnabled;
    }

    public Boolean exists(String elemId) {
        return Boolean.parseBoolean(call("getFlexExists", elemId, ""));
    }


    public void selectTab(String elementId, String labelName) {
        if (waitUntilVisible(elementId)) {
            call("selectTabByLabel", elementId, labelName);
        } else {
            testReporter.log(LogStatus.FAIL,"FlexWebDriver.SelectTab", elementId + "not found or not visible");
        }
    }

    public void triggerMouseEventOnPopup(String objectId) {
        try {
            //this.waitForPopup(objectId);
            element.wait(3);
            call("triggerClickEventOnPopupWindowField", "mouseUp", objectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}