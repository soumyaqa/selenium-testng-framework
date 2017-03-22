/**
 * Created by GXP8655 on 3/20/2017.
 */
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/*
 * This is a base Test class. All testng tests needs to inherited from
 * this class. It contains common reporting infra
 */
public class BaseTest {
    private String testName;
    private String testDescription;

    @BeforeSuite
public void beforeSuite(){Reports.getExtentReport();}
    @BeforeMethod
    public void beforeMethod(Method caller) {
        //TODO : Get the test name & description from Excelsheet and pass it to the getTest method
        testName = caller.getName();
        testDescription = "This is a simple test case description";
        Reports.getTest(testName, testDescription);
    }

    @AfterMethod
    public void afterMethod(Method caller) {
        Reports.closeTest(caller.getName());
    }
    /*
     * After suite will be responsible to close the report properly at the end
     * You an have another afterSuite as well in the derived class and this one
     * will be called in the end making it the last method to be called in test exe
     */
    @AfterSuite
    public void afterSuite() {
        Reports.closeReport();
    }

}