package pageObjects.COMMON;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.SeleniumBase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

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
    protected void switchToWindowAndVerifyURL(String navigateToPage){
        String currentWindow = driver.getWindowHandle();
        String TermsofServiceUrl = link(navigateToPage).getUrl();
        String PrivacyPolicyURL = link(navigateToPage).getUrl();
        String TermsofServiceWindow = null;
        String privacyPolicyWindow = null;
        if(navigateToPage.equals("Terms of Service")) {
            link(navigateToPage).click();
            waitUntilPageFinishLoading();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    TermsofServiceWindow = thiswindow;
                }
            }
            driver.switchTo().window(TermsofServiceWindow);
            Assert.assertTrue("Terms of Service page is not displayed",driver.findElement(By.xpath("//span[text()='Intersect Terms of Use ']")).isDisplayed());
            String TermsofServiceCurrentURL = driver.getCurrentUrl();
            Assert.assertTrue("Given URL is not present in the page", TermsofServiceUrl.equals(TermsofServiceCurrentURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilElementExists(link(navigateToPage));
            waitUntilPageFinishLoading();
        } else if(navigateToPage.equals("Privacy Policy")){
            link(navigateToPage).click();
            waitUntilPageFinishLoading();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    privacyPolicyWindow = thiswindow;
                }
            }
            driver.switchTo().window(privacyPolicyWindow);
            waitUntilPageFinishLoading();
            Assert.assertTrue("Privacy Policy page is not displayed",driver.findElement(By.xpath("//span[text()='User Provided Information: ']")).isDisplayed());
            String privacyPolicyCurrentURL = driver.getCurrentUrl();
            Assert.assertTrue("Given URL is not present in the page",PrivacyPolicyURL.equals(privacyPolicyCurrentURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilPageFinishLoading();
        } else{
            Assert.fail("The given option is not valid one");
        }
    }

    protected void switchToCommunityGuidelinesWindowAndVerifyURL(String navigateToPage,String option){
        waitUntilPageFinishLoading();
        waitForUITransition();
        String currentWindow = driver.getWindowHandle();
        String communityGuidelinesWindow = null;
        if(navigateToPage.equals("Counselor Community Guidelines")){
        driver.findElement(By.id("helpNav-dropdown")).click();
        Assert.assertTrue("Help center is not displayed",driver.findElement(By.id("js-helpNavMenu-help-menu-link")).isDisplayed());
        driver.findElement(By.id("js-helpNavMenu-help-menu-link")).click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitForUITransition();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    communityGuidelinesWindow = thiswindow;
                }
            }
            driver.switchTo().window(communityGuidelinesWindow);
            waitForUITransition();
            if(option.equals("HE")) {
                driver.findElement(By.xpath("//span/a/b[text()='Hobsons Counselor Community']")).click();
            }else if(option.equals("HS")){
                driver.findElement(By.xpath("//span/a[text()='Hobsons Counselor Community']")).click();
            }else {
                Assert.fail("The option given for the Hobsons counselor community is a Invalid one");
            }
            String communityGuidelinesURL = link("Counselor Community Guidelines").getUrl();
            link("Counselor Community Guidelines").click();
            waitUntilPageFinishLoading();
            String getCurrentPageUrl = driver.getCurrentUrl();
            Assert.assertTrue("Counselor Community Guidelines page is not displayed",driver.findElement(By.xpath("//span[normalize-space(text())='To be part of the Hobsons Counselor Community, you must be 18 or older and be authorized by your school, district or higher education institution to participate.']")).isDisplayed());
            Assert.assertTrue("Given URL is not present in the page",getCurrentPageUrl.equals(communityGuidelinesURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilPageFinishLoading();
        }else {
            Assert.fail("Invalid option");
        }

    }

    protected void verifyLinksInFooter(String footerLink){
        waitUntilPageFinishLoading();
        if(footerLink.equals("Terms of Service")){
            Assert.assertTrue("Terms of Service link is not displayed",link(footerLink).isDisplayed());
        }else if(footerLink.equals("Privacy Policy")) {
            Assert.assertTrue("privacy Policy link is not displayed", link(footerLink).isDisplayed());
        }else {
            Assert.fail("The given value is Invalid");
        }
    }


    private WebElement datePickerMonthYearText() { return driver.findElement(By.cssSelector("div.DayPicker-Caption")); }
    private WebElement datePickerNextMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--next")); }
    private WebElement datePickerPrevMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--prev")); }

}
