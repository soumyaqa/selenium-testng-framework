import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by GXP8655 on 3/24/2017.
 */
public class DriverManager {
    WebDriver driver = null;

    public WebDriver getLocalChromeDriver(){
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        return driver;
    }

    public WebDriver getLocalIEDriver(){
        File ieDriverPath = new File("./drivers/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", ieDriverPath.getAbsolutePath());
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("ensureCleanSession", true);
        driver = new InternetExplorerDriver(capabilities);
        driver.manage().deleteAllCookies();
        try {
            Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error getting local IE driver instance");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60L, TimeUnit.SECONDS);
        return driver;
    }

}
