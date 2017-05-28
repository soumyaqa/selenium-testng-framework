package testcases;

import framework.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * Created by gunap on 5/28/2017.
 */
public class LoginTest extends BaseTest {

    @Test
    public void verify_if_user_is_able_to_login_to_adactin_hotrl_application() {
        openApplication(configProperties.getProperty("app.url"));
        new LoginPage(driver, testReporter, element)
                .login("home1234","root1234");

    }
}
