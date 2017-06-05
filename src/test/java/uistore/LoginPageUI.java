package uistore;

import org.openqa.selenium.By;

/**
 * Created by GXP8655 on 6/5/2017.
 */
public class LoginPageUI {
    public static final By storeLocationRadio = By.name("j_userbelongsto");
    public static final By storeNum = By.name("j_storenumber");
    public static final By userName = By.name("j_username");
    public static final By password = By.name("j_password");
    public static final By signinButton = By.id("submit");
    public static final String appTitle = "The Home Depot: Application Login";
}
