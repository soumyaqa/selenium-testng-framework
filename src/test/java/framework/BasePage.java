package framework;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by GXP8655 on 4/6/2017.
 */
@SuppressWarnings("Duplicates")
public class BasePage {

    protected WebDriver driver;
    protected ExtentTest report;
    protected Element element;
    protected Properties configProperties;

    public BasePage(WebDriver driver, ExtentTest report, Element element) {
        this.driver = driver;
        this.report = report;
        this.element = element;
        loadConfig();
    }

    public void logStep(LogStatus status, String expected, String actual) {
        report.log(status, expected, actual);
    }

    public void logStepWithScreenShot(LogStatus status, String expected, String actual) {
        long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
        try {
            File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(image, new File("./reports/images/" + number + ".jpg"));
        } catch (WebDriverException | IOException e) {
            e.printStackTrace();
        }
        report.log(status, expected, actual + report.addScreenCapture("./images/" + number + ".jpg"));
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

}
