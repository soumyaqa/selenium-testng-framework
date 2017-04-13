package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import framework.BasePage;
import framework.Element;
import org.openqa.selenium.WebDriver;
import uistore.SearchPageUI;
import uistore.SelectHotelPageUI;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class AdactinSelectPage extends BasePage {
    public AdactinSelectPage(WebDriver driver, ExtentTest report, Element element) {
        super(driver, report, element);
    }

    public AdactinBookPage selectHotel() {
        element.click(SelectHotelPageUI.selectHotel);
        element.click(SelectHotelPageUI.clickContinue);
        logStepWithScreenShot(LogStatus.PASS, "Hotel Selection", "Hotel Selected Successfully");
        return new AdactinBookPage(driver, report, element);
    }

    public AdactinSelectPage verifySelectHotel() {
        String name1 = element.getText(SearchPageUI.hotels);
        String name2 = element.getText(SelectHotelPageUI.hotelLocationVerification);

/*         //if (element.containsText(SearchPageUI.hotels. )!= (element.containsText(SelectHotelPageUI.hotelNameVerification)))
        if(name1== name2){
            //System.out.print(element.getText(SearchPageUI.locations));
            System.out.print(element.getText(SelectHotelPageUI.hotelLocationVerification));
        logStepWithScreenShot(LogStatus.PASS, "Hotel Name Should be Same", "Hotel Name Displayed in the Select Page is Same as Selected in Search Page");}

        else {
            logStepWithScreenShot(LogStatus.PASS, "Hotel Name Should be Same", "Hotel Name Displayed in the Select Page is Not Same as Selected in Search Page");
            System.out.print(element.getText(SelectHotelPageUI.hotelLocationVerification));
        }*/
        return this;
    }


}