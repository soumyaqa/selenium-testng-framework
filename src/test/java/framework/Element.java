package framework;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by GXP8655 on 4/6/2017.
 */
@SuppressWarnings("Duplicates")
public class Element {

    WebDriver driver;

    public Element(WebDriver driver) {
        this.driver = driver;
    }

    /***************************************************************************************************************
     *********************************************BUTTON************************************************************
     ***************************************************************************************************************/

    public boolean click(By by) {
        if (wait(by)) {
            driver.findElement(by).click();
            return true;
        }
        return false;
    }

    /***************************************************************************************************************
     **********************************************TEXT*************************************************************
     ***************************************************************************************************************/

    public boolean enterText(By by, String text) {
        if (wait(by)) {
            driver.findElement(by).sendKeys(text);
            return true;
        }
        return false;
    }

    public boolean enterKeys(By by, Keys key) {
        if (wait(by)) {
            driver.findElement(by).sendKeys(key);
            return true;
        }
        return false;
    }

    public boolean clear(By by) {
        if (wait(by)) {
            driver.findElement(by).clear();
            return true;
        }
        return false;
    }

    /***************************************************************************************************************
     **********************************************DROP DOWN********************************************************
     ***************************************************************************************************************/
    public boolean selectByText(By by, String text) {
        if (wait(by)) {
            new Select(driver.findElement(by)).selectByVisibleText(text);
            return true;
        }
        return false;
    }

    public boolean selectTextByIndex(By by, int index) {
        if (wait(by)) {
            new Select(driver.findElement(by)).selectByIndex(index);
            return true;
        }
        return false;
    }

    public boolean selectTextByValue(By by, String value) {
        if (wait(by)) {
            new Select(driver.findElement(by)).selectByValue(value);
            return true;
        }
        return false;
    }

    public List<String> getAllOptions(By by) {
        List<WebElement> options = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (wait(by)) {
            Select select = new Select(driver.findElement(by));
            options = select.getOptions();
            for (WebElement element : options)
                values.add(element.getText());
            return values;
        }
        return values;
    }

    public boolean isOptionPresent(By by, String option) {
        List<WebElement> options = new ArrayList<>();
        if (wait(by)) {
            Select select = new Select(driver.findElement(by));
            options = select.getOptions();
            for (WebElement element : options) {
                if (element.getText().equals(option))
                    return true;
            }
        }
        return false;
    }

    public boolean isOptionPresentIgnoreCase(By by, String option) {
        List<WebElement> options = new ArrayList<>();
        if (wait(by)) {
            Select select = new Select(driver.findElement(by));
            options = select.getOptions();
            for (WebElement element : options) {
                if (element.getText().equalsIgnoreCase(option))
                    return true;
            }
        }
        return false;
    }


    /***************************************************************************************************************
     **********************************************ALERTS******************************************************
     ***************************************************************************************************************/

    public boolean acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cancelAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();

        } catch (Exception e) {
            return "";
        }
    }

    public boolean sendTextToAlert(String text) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
            alert.accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /***************************************************************************************************************
     **********************************************ACTIONS********************************************************
     ***************************************************************************************************************/
    public boolean mouseOver(By by) {
        if (wait(by)) {
            new Actions(driver).moveToElement(driver.findElement(by)).build().perform();
            return true;
        }
        return false;
    }

    public boolean doubleClick(By by) {
        if (wait(by)) {
            new Actions(driver).doubleClick(driver.findElement(by)).build().perform();
            return true;
        }
        return false;
    }

    public boolean clickAndHold(By by) {
        if (wait(by)) {
            new Actions(driver).clickAndHold(driver.findElement(by)).build().perform();
            return true;
        }
        return false;
    }

    public boolean dragAndDrop(By src, By dest) {
        if (wait(src) && wait(dest)) {
            new Actions(driver).dragAndDrop(driver.findElement(src), driver.findElement(dest)).build().perform();
            return true;
        }
        return false;
    }


    /***************************************************************************************************************
     **********************************************PAGE LEVEl********************************************************
     ***************************************************************************************************************/


    /***************************************************************************************************************
     **********************************************COMMON********************************************************
     ***************************************************************************************************************/
    //TODO : Check getText for all UI elements like button, textbox, etc -- It may not work as expected
    public String getText(By by) {
        if (wait(by)) {
            String text = driver.findElement(by).getText();
            if (text == null || text.equalsIgnoreCase(""))
                text = driver.findElement(by).getAttribute("value");
            return text;
        }
        return "";
    }

    public boolean containsText(By by, String searchText) {
        if (wait(by)) {
            String text = driver.findElement(by).getText();
            if (text == null || text.equalsIgnoreCase(""))
                text = driver.findElement(by).getAttribute("value");
            return text.contains(searchText);
        }
        return false;
    }

    public boolean wait(By by) {
        boolean isElementPresent = false;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(30, SECONDS)
                    .pollingEvery(50, TimeUnit.MILLISECONDS)
                    .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
            wait.until(driver -> driver.findElement(by));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean wait(By by, int seconds) {
        boolean isElementPresent = false;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(seconds, SECONDS)
                    .pollingEvery(50, TimeUnit.MILLISECONDS)
                    .ignoring(WebDriverException.class, java.util.NoSuchElementException.class);
            wait.until(driver -> driver.findElement(by));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            return true;
        } catch (Exception e) {
            return false;
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
        String skuGridProductEntry = "skuDetailDataGrid"; //Project specific
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
            wait(2);
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

    public boolean click(String objectId) throws Exception {
        try {
            if (waitForElement(objectId)) {
                call("doFlexClick", objectId, "");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean mouseClick(String objectId) throws Exception {
        try {
            if (waitForElement(objectId)) {
                call("triggerMouseEvent", "click", objectId);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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
        wait(1);
        call("doFlexEnterKey", objectId, "");
        wait(1);
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
            wait(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectInPopup(String objectId, int index) {
        try {
            this.waitForPopup(objectId);
            wait(3);
            call("triggerSelectEventOnPopupWindow", Integer.toString(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickInPopup(String objectId) {
        try {
            //    this.waitForPopup(objectId);
            wait(3);
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
                } else
                    wait(1);
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
        wait(1);
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
        wait(2);
        while (true) {
            if (isVisible(elemId))
                wait(1);
            else
                break;
            if (i == 30)
                break;
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
        wait(3);
        return Boolean.parseBoolean(call("getFlexAlertPresent", "", ""));
    }

    public String getFlexAlertText() {
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


    public boolean selectTab(String elementId, String labelName) {
        if (waitUntilVisible(elementId)) {
            call("selectTabByLabel", elementId, labelName);
            return true;
        }
        return false;
    }

    public void triggerMouseEventOnPopup(String objectId) {
        try {
            //this.waitForPopup(objectId);
            wait(3);
            call("triggerClickEventOnPopupWindowField", "mouseUp", objectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
