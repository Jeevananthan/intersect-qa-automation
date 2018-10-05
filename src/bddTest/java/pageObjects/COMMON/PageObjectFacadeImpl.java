package pageObjects.COMMON;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.SeleniumBase;
import stepDefinitions.GlobalSteps;
import utilities.GetProperties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PageObjectFacadeImpl extends SeleniumBase {

    private Logger logger;
    public NavBarImpl navBar;
    public  NavigationBarImpl navigationBar;
    public GlobalSearch globalSearch;

    protected PageObjectFacadeImpl() {
        logger = Logger.getLogger(PageObjectFacadeImpl.class);
        navBar = new NavBarImpl();
        globalSearch = new GlobalSearch();
    }

      public NavigationBarImpl getNavigationBar(){
        return new NavigationBarImpl();
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
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[title=Community]")));
        waitForUITransition();
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
     * Generates a Calendar object with a time in the future (or past for negative numbers) from the current date.
     *
     * @param delta - Integer for the number of minutes from now.  Negative numbers = minutes in the past.
     * @return Calendar object with the date set to delta minutes from current time.
     */
    protected Calendar getDeltaTime(int delta) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, delta);
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

        String dateString = getDay(date);
        if (Character.valueOf(dateString.charAt(0)).equals('0')) {
            dateString = dateString.substring(1);
        }

        if (date.before(todaysDate)) {
            while (!datePickerMonthYearText().getText().equals(getMonth(date) + " " + getYear(date))) {
                datePickerPrevMonthButton().click();
            }
        } else if (date.after(todaysDate)) {
            while (!datePickerMonthYearText().getText().equals(getMonth(date) + " " + getYear(date))) {
                datePickerNextMonthButton().click();
            }
        }
        waitForUITransition();
        driver.findElement(By.xpath("//div[@class='DayPicker-Day' or @class='DayPicker-Day DayPicker-Day--today'" +
                "or @class='DayPicker-Day DayPicker-Day--selected' or @class = 'DayPicker-Day DayPicker-Day--selected " +
                "DayPicker-Day--today'][text()='" + dateString + "']")).click();
    }

    /**
     * Returns a String representing the day of the week from a Calendar object
     *
     * @param cal - Calendar object
     * @return String containing the week day (e.g.: Monday)
     */
    protected String getDayOfWeek(Calendar cal) {
        String result = "";
        switch (cal.get(cal.DAY_OF_WEEK)) {
            case 1 : result = "Sunday";
                break;
            case 2 : result = "Monday";
                break;
            case 3 : result = "Tuesday";
                break;
            case 4 : result = "Wednesday";
                break;
            case 5 : result = "Thursday";
                break;
            case 6 : result = "Friday";
                break;
            case 7 : result = "Saturday";
                break;
        }
        return result;
    }

    /**
     * Returns a boolean indicating if the passed string is a number or not
     *
     * @param num - String object
     * @return boolean indicating if the passed string is a number or not
     */
    protected boolean isStringNumber(String num) {
        try {
            double number = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a String representing the time from a Calendar object
     *
     * @param cal - Calendar object
     * @return String containing the time in hh:mm a (e.g.: 10:30 AM) format
     */
    protected String getTime(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(cal.getTime());
    }

    /**
     * Waits until a given path exists.
     * @param path
     */
    public void waitUntilFileExists(String path){
        ExpectedCondition<Boolean> expectation = webDriver -> Files.exists(Paths.get(path));
        try{
            waitUntil(expectation);
        }
        catch (Exception e){
            throw  new AssertionError(String.format("There was a problem waiting for the file: %s, error: %s",
                    path, e.toString()));
        }
    }

    /**
     * Returns a list of String based on a list in a properties file
     *
     * @param propertiesFilePath - String representing the path to the properties files to be used
     * @param separator - Separation character
     * @param propertiesEntry - String that corresponds to the appropriate list in the properties file
     * @return List<String> - List of String containing the data in the entry of the properties file
     */
    public List<String> getListFromPropFile(String propertiesFilePath, String separator, String propertiesEntry) {
        Properties properties = new Properties();
        InputStream input = null;
        List<String> resultList = new ArrayList<>();
        try {
            input = new FileInputStream(propertiesFilePath);
            properties.load(input);
            resultList = Arrays.asList(properties.getProperty(propertiesEntry).split(separator));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Returns a String corresponding to the entry in a properties file
     *
     * @param propertiesFilePath - String representing the path to the properties file to be used
     * @param propertiesEntry - String that corresponds to the appropriate list in the properties file
     * @return String - String containing the data in the entry of the properties file
     */
    public String getStringFromPropFile(String propertiesFilePath, String propertiesEntry) {
        Properties properties = new Properties();
        InputStream input = null;
        String resultString = "";
        try {
            input = new FileInputStream(propertiesFilePath);
            properties.load(input);
            resultString = properties.getProperty(propertiesEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * Sets the Implicit Wait timeout for the current WebDriver to the value passed in 'seconds'
     *
     * @param seconds - Value to set the Driver's implicit Wait timeout to
     */
    protected void setImplicitWaitTimeout(Integer seconds) {
        getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Sets the Implicit Wait timeout for the current WebDriver to the value stored in the Env file
     */
    public void resetImplicitWaitTimeout() {
        getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
    }

    /**
     * Sets the Implicit Wait timeout untill both the condition are not match
     * NOTE : FIRST PARAMETER IS WEBELEMENT AND SECOND IS THE EXPECTED VALUE
     * @param seconds - Value to set the Driver's implicit Wait timeout to
     */
    protected void explicitWaitWithTwoConditionCheck(WebElement actCondition, String expCondition){
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return actCondition.getText().equals(expCondition);
            }
        });
    }

    /**
     * Returns a SoftAssertions object to check things that you care about, but don't want to stop test
     *      execution over if the assertion fails.
     *
     * @return The static SoftAssertions object from GlobalSteps to be checked on Scenario teardown.
     */
    public SoftAssertions softly() {
        return GlobalSteps.softly;
    }

    protected WebElement datePickerMonthYearText() { return driver.findElement(By.cssSelector("div.DayPicker-Caption")); }
    protected WebElement datePickerNextMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--next")); }
    protected WebElement datePickerPrevMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--prev")); }

}
