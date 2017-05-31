package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
    }

    public void verifyAvailabilityAndSettingsPage(){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        List<String> tabs = new ArrayList<>();
        //Left Menu
        tabs.add("Availability");
        tabs.add("Messaging Options");
        tabs.add("Time Zone");
        tabs.add("Notifications & Primary Contact");
        tabs.add("Calendar Sync");
        tabs.add("Naviance Settings");
        //Top menu
        tabs.add("Regular Weekly Hours");
        tabs.add("Holidays");
        tabs.add("Exceptions");
        tabs.add("Availability Settings");
        for (String tab : tabs) {
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!",link(tab).isDisplayed());
        }
    }

    public void verifyContentsOfNavianceSettings() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Naviance Settings").click();
        waitUntilPageFinishLoading();

        String publishVisitsToNavianceText = getDriver().findElement(By.id("form-naviance-settings")).getText();
        String likeToPublishAutomaticallyOptionsText = "Automatically publish confirmed visits.";
        String likeToPublishManuallyOptionsText = "Manually choose which visits to publish. (If any)";
        String optionalNotificationText = "When publishing to Naviance, do you want to notify students who have expressed interest in that institution?";
        String displayDeadLineAndStatus = "Do you want to display a deadline and status to students?";

        Assert.assertTrue(likeToPublishAutomaticallyOptionsText + " Text is not displayed",
                publishVisitsToNavianceText.contains(likeToPublishAutomaticallyOptionsText));
        Assert.assertTrue(likeToPublishManuallyOptionsText + " Text is not displayed",
                publishVisitsToNavianceText.contains(likeToPublishManuallyOptionsText));
        Assert.assertTrue(optionalNotificationText + " Text is not displayed",
                publishVisitsToNavianceText.contains(optionalNotificationText));
        Assert.assertTrue("Max number of students is not showing.", textbox(By.id("maxNumStudents")).isDisplayed());
        Assert.assertTrue("Location is not showing is not showing.", textbox(By.id("locationWithinSchool")).isDisplayed());
        Assert.assertTrue("Registration will close is not showing.", textbox(By.id("rsvpNumber")).isDisplayed());
        Assert.assertTrue(displayDeadLineAndStatus + " Text is not displayed",
                publishVisitsToNavianceText.contains(displayDeadLineAndStatus));
        Assert.assertTrue("Button Save Changes is not showing.", button(By.cssSelector("button[class='ui primary button']")).isDisplayed());
    }

    public void verifyDatesRangeForStartAndEndDate(String startDate, String endDate) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        setDate(startDate);
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(4)")).click();
        setDate(endDate);
    }

    public void setDate(String inputDate) {

        List<WebElement> allWeeks = driver.findElements(By.cssSelector("div[class='DayPicker-Month']"));
        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        for(WebElement elem:allWeeks)
        {
            switch (calendarHeading) {
                case "July 2017":
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Jul", parts[1], parts[2]);
                    break;
                case "August 2017":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Aug", parts[1], parts[2]);
                    break;
                case "September 2017":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Sep", parts[1], parts[2]);
                    break;
                case "October 2017":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Oct", parts[1], parts[2]);
                    break;
                case "November 2017":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Nov", parts[1], parts[2]);
                    break;
                case "December 2017":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Dec", parts[1], parts[2]);
                    break;
                case "January 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Jan", parts[1], parts[2]);
                    break;
                case "February 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Feb", parts[1], parts[2]);
                    break;
                case "March 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Mar", parts[1], parts[2]);
                    break;
                case "April 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Apr", parts[1], parts[2]);
                    break;
                case "May 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("May", parts[1], parts[2]);
                    break;
                case "Jun 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Jun", parts[1], parts[2]);
                    break;
                case "July 2018":
                    findMonth(calendarHeading);
                    clickOnDate(parts[1]);
                    waitUntilPageFinishLoading();
                    verifyCorrectDateRange("Jul", parts[1], parts[2]);
                    break;
            }

        }
    }
    public void findMonth(String month) {
        switch (month) {
            case "August 2017":
                for(int i=1; i<=2; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "September 2017":
                for(int i=1; i<=3; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "October 2017":
                for(int i=1; i<=4; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "November 2017":
                for(int i=1; i<=5; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "December 2017":
                for(int i=1; i<=6; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "January 2018":
                for(int i=1; i<=7; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "February 2018":
                for(int i=1; i<=8; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "March 2018":
                for(int i=1; i<=9; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "May 2018":
                for(int i=1; i<=10; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "Jun 2018":
                for(int i=1; i<=11; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
            case "July 2018":
                for(int i=1; i<=12; i++) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
                waitUntilPageFinishLoading();
                break;
        }
    }

    public void clickOnDate(String date) {

        driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[contains(text(), "+date+")]")).click();
    }

    public void verifyCorrectDateRange(String month, String day, String year){
        try {
            Assert.assertTrue("Button Start Date is not showing.",
                    driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).
                            findElement(By.xpath("//span[contains(text(), '" + month + " " + day + ", " + year + "')]")).isDisplayed());

        } catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }

    }
}
