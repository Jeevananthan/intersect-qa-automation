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

    /**
     * Uses JavaScript to click on an element.  This is occasionally necessary because Selenium thinks that something
     * isn't visible to the user, even when it really is.  JS gets around this by sending the click directly.
     *
     * @param element - WebElement to send the click action to
     */
    protected void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();",element);
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

}
