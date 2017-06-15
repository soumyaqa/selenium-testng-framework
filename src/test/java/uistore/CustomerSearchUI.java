package uistore;

import org.openqa.selenium.By;

public class CustomerSearchUI {
    public static final By frame = By.id("customerProfileIframe");
    public static final By phone = By.xpath("//div[@class='search-no-filters']//input[@name='phone']");
    public static final By firstName = By.name("firstName");
    public static final By lastName = By.name("lastName");
    public static final By zip = By.name("nameZip");
    public static final By orderNumber = By.name("orderNumber");
    public static final By search = By.xpath("//*[@id='diyForm']/div/div[3]/div[1]/button");
    public static final By custProfileLabel = By.xpath("//*[@id=\"pageContainer\"]/div/div/div/header/div/div");
    public static final By createOrder = By.linkText("Create Order");
    public static final String label = "Search";
    public static final By orderCheck = By.xpath("//*[@id='diyContainer']/form/div[3]/div/div[2]/div[3]/div[2]/div[2]/ng-form/div[2]/div[2]/div/div[1]/label/span");
    public static final By saveButton = By.xpath("//*[@id='diyContainer']/form/div[2]/div[2]/div/div[1]/button");
    public static final By orderCreate = By.xpath("//*[@id='content']/div[2]/div/div[2]/div[2]/div/button[3]");

}
