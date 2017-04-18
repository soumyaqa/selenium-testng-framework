package uistore;

import org.openqa.selenium.By;

/**
 * Created by Udhayakumar on 4/11/2017.
 */
public class BookHotelPageUI {
    /*verifying booking entries */
    public static final By hotelName = By.id("hotel_name_dis");
    public static final By location = By.id("location_dis");
    public static final By roomType = By.id("room_type_dis");
    public static final By totalDays = By.id("total_days_dis");
    public static final By pricePerNight = By.id("price_night_dis");

    /*booking hotel */
    public static final By firstName = By.id("first_name");
    public static final By lastName = By.id("last_name");
    public static final By billAddress = By.id("address");
    public static final By cardNumber = By.id("cc_num");
    public static final By cardType = By.id("cc_type");
    public static final By expiryMonth = By.id("cc_exp_month");
    public static final By expiryYear = By.id("cc_exp_year");
    public static final By cvv = By.id("cc_cvv");
    public static final By clickBookNow = By.id("book_now");
}

