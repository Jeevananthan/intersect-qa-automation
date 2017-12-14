package pageObjects.COMMON;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.SeleniumBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PageObjectFacadeImpl extends SeleniumBase {

    private Logger logger;
    public NavBarImpl navBar;
    public GlobalSearch globalSearch;

    protected PageObjectFacadeImpl() {
        logger = Logger.getLogger(PageObjectFacadeImpl.class);
        navBar = new NavBarImpl();
        globalSearch = new GlobalSearch();
    }

    public void waitForUITransition() {
        try {
            System.out.println("\nWaiting 3 seconds for UI Transition.\n");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("\nError waiting 3 seconds: " + e.getMessage() + e.getStackTrace() + "\n");
        }
    }

    protected void communityFrame() {
        // This shouldn't navigate, it should only jump into the iFrame.  Use navBar.goToCommunity() instead for that.
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    /**
     * Generates a Calendar object with a day in the future (or past for negative numbers) from the current date.
     *
     * @param delta - Integer for the number of days from now.  Negative numbers = days in the past.
     * @return Calendar object with the date set to delta days from current date.
     */
    protected Calendar getDeltaDate(int delta) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, delta);
        return cal;
    }

    /**
     * Returns a String representing the day from a Calendar object
     *
     * @param cal - Calendar object
     * @return String containing the day in dd (e.g.: 08) format
     */
    protected String getDay(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(cal.getTime());
    }

    /**
     * Returns a String representing the month from a Calendar object
     *
     * @param cal - Calendar object
     * @return String containing the month in MMMM (e.g.: August) format
     */
    protected String getMonth(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        return sdf.format(cal.getTime());
    }

    /**
     * Returns a String representing the numeric month from a Calendar object
     *
     * @param cal - Calendar object
     * @return String containing the month in MM (e.g.: 08) format
     */
    protected String getMonthNumber(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(cal.getTime());
    }

    /**
     * Returns a String representing the year from a Calendar object
     *
     * @param cal - Calendar object
     * @return String containing the year in yyyy (e.g.: 2014) format
     */
    protected String getYear(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(cal.getTime());
    }

    /**
     * Picks a date in the calendars of 'DatePicker' type. You can find one of these calendars
     * in the Create Event page, in Event Start
     *
     * @param date - Calendar object with the desired date
     */
    protected void pickDateInDatePicker(Calendar date) {
        Calendar todaysDate = Calendar.getInstance();
        if (date.before(todaysDate)) {
            while (!datePickerMonthYearText().getText().equals(getMonth(date) + " " + getYear(date))) {
                datePickerPrevMonthButton().click();
            }
        } else if (date.after(todaysDate)) {
            while (!datePickerMonthYearText().getText().equals(getMonth(date) + " " + getYear(date))) {
                datePickerNextMonthButton().click();
            }
        }
        driver.findElement(By.xpath("//div[@class='DayPicker-Day'][text()='" + getDay(date) + "']")).click();
    }

    private WebElement datePickerMonthYearText() { return driver.findElement(By.cssSelector("div.DayPicker-Caption")); }
    private WebElement datePickerNextMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--next")); }
    private WebElement datePickerPrevMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--prev")); }


}