package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import cucumber.api.java.en_lol.WEN;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import javax.xml.soap.Text;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.fail;

import static org.junit.Assert.fail;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    //Creating instance of HE.RepVisitsPageImpl
    pageObjects.HE.repVisitsPage.RepVisitsPageImpl repVisitsPageHEObj = new pageObjects.HE.repVisitsPage.RepVisitsPageImpl();

    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

    public void checkRepVisitsSubTabs(DataTable dataTable) {
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.", link(repVisitsSubItem).isDisplayed());
        }
    }

    public void verifyAvailabilityAndSettingsPage() {
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
        tabs.add("Blocked Days");
        tabs.add("Exceptions");
        tabs.add("Availability Settings");
        for (String tab : tabs) {
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!", link(tab).isDisplayed());
        }
    }

    public void verifyAvailabilitySettings(DataTable dataTable) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed", text("Visit Scheduling").isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed", text("Accept").isDisplayed());
        Assert.assertTrue("Listbox is not displayed", driver.findElement(By.cssSelector("div[class='ui selection dropdown'][role='listbox']")).isDisplayed());
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
        Assert.assertTrue("'Visit Confirmations' header is not displayed as expected!", text("Visits Confirmations").isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Automatically confirm all visit requests?' is not displaying as expected!", text("Automatically confirm all visit requests?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'Yes, accept all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='Yes, accept all incoming requests.']/input[@type='radio']")).isDisplayed());
        Assert.assertTrue("radiobutton 'No, I want to manually review all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='No, I want to manually review all incoming requests.']/input[@type='radio']")).isDisplayed());

        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> availabilityData : entities) {
            for (String key : availabilityData.keySet()) {
                switch (key) {
                    // This is where we verify the actual editable values on the screen.
                    case "Accept":
                        String currentOption = driver.findElement(By.cssSelector("div[class='ui selection dropdown']>div[class='text']")).getText();
                        Assert.assertTrue("'Accept' value was not as expected.", currentOption.contains(availabilityData.get(key)));
                        break;
                    case "visits per day":
                        String actualVisitPerDay = driver.findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.", actualVisitPerDay.equals(availabilityData.get(key)));
                        break;
                    case "Who can see your availability?":
                        if (availabilityData.get(key).contains("All Repvisits Users")) {
                            Assert.assertTrue("Availability option is not set as expected", driver.findElement(By.cssSelector("[name=availabilityVisible][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("Only Me")) {
                            Assert.assertTrue("Availability option is not set as expected", driver.findElement(By.cssSelector("[name=availabilityVisible][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit availability.");
                        }
                        break;
                    case "Automatically confirm all visit requests?":
                        if (availabilityData.get(key).contains("Yes, accept all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("No, I want to manually review all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit request confirmations.");
                        }
                        break;
                    case "Prevent colleges from scheduling new visits less than":
                        String rsvpDeadline = driver.findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.", rsvpDeadline.equals(availabilityData.get(key)));
                        break;
                    case "Prevent colleges from cancelling or rescheduling less than":
                        String modifyDeadline = driver.findElement(By.cssSelector("input[name='modifyDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.", modifyDeadline.equals(availabilityData.get(key)));
                        break;
                }
            }
        }

    }

    public void setPreventCollegesSchedulingNewVisits(String Numberofdays) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(Numberofdays);
        button("Save Changes").click();
    }

    public void setPreventCollegesCancellingorRescheduling(String DaysInAdvance) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(DaysInAdvance);
        button("Save Changes").click();
    }

    public void setAcceptInVisitSchedulingToFullyBooked(String accept) {
        setAcceptinAvailabilitySettings(accept, "1");
    }

    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement selectAccept = getDriver().findElement(By.cssSelector("div[class='ui selection dropdown']>div"));
        selectAccept.click();
        getDriver().findElement(By.xpath("//span[text()='" + accept + "']")).click();
        if (accept.equals("a maximum of...")) {
            WebElement visitsBox = getDriver().findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']"));
            visitsBox.clear();
            visitsBox.sendKeys(visitsPerDay);
        }
        button("Save Changes").click();
    }

    public void setVisitsConfirmations(String option) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
        options.findElement(By.xpath("div/label[text()[contains(., '" + option + "')]]")).click();
        button("Save Changes").click();
    }

    public void accessVisitAvailability(String visitAvailability) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        driver.findElement(By.xpath("//label[text()='" + visitAvailability + "']/input[@type='radio']")).click();
        driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
    }

    public void verifyVisitAvailability(String visitAvailabiltyOption) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        //Verify the Title
        Assert.assertTrue("'Visit Availability' header is not displayed as expected!", text("Visit Availability").isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Who can see your availability?'  is not displaying as expected!", text("Who can see your availability?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'All Repvisits Users' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='grouped fields']//label[text()='All RepVisits Users']/input[@type='radio']")).isDisplayed());
        Assert.assertTrue("radiobutton 'Only Me' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='grouped fields']//label[text()='Only Me']/input[@type='radio']")).isDisplayed());
        Assert.assertTrue("radiobutton Label 'All Repvisits Users' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='grouped fields']//label[text()='All RepVisits Users']")).isDisplayed());
        Assert.assertTrue("radiobutton Label 'Only Me' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='grouped fields']//label[text()='Only Me']")).isDisplayed());
        if (!visitAvailabiltyOption.equals("")) {
            Assert.assertTrue("The option " + visitAvailabiltyOption + " radio button is not checked", driver.findElement(By.xpath("//div[@class='grouped fields']//label[text()='" + visitAvailabiltyOption + "']/input[@type='radio']")).isSelected());
        }
        //'Save Changes' button
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
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

        Assert.assertTrue(likeToPublishAutomaticallyOptionsText + " Text is not displayed", publishVisitsToNavianceText.contains(likeToPublishAutomaticallyOptionsText));
        Assert.assertTrue(likeToPublishManuallyOptionsText + " Text is not displayed", publishVisitsToNavianceText.contains(likeToPublishManuallyOptionsText));
        Assert.assertTrue(optionalNotificationText + " Text is not displayed", publishVisitsToNavianceText.contains(optionalNotificationText));
        Assert.assertTrue("Max number of students is not showing.", textbox(By.id("maxNumStudents")).isDisplayed());
        Assert.assertTrue("Location is not showing is not showing.", textbox(By.id("locationWithinSchool")).isDisplayed());
        Assert.assertTrue("Registration will close is not showing.", textbox(By.id("rsvpNumber")).isDisplayed());
        Assert.assertTrue(displayDeadLineAndStatus + " Text is not displayed", publishVisitsToNavianceText.contains(displayDeadLineAndStatus));
        Assert.assertTrue("Button Save Changes is not showing.", button(By.cssSelector("button[class='ui primary button']")).isDisplayed());
    }

    public void verifyTimeSlotAdded(String hourStartTime, String minuteStartTime, String meridianStartTime) {

        Assert.assertTrue("The Time Slot was not added", driver.findElement(By.cssSelector("table[class='ui unstackable basic table']")).findElement(By.xpath("//button[contains(text(), '" + hourStartTime + ":" + minuteStartTime + meridianStartTime + "')]")).isDisplayed());

    }


    public void setDate(String inputDate, String startOrEndDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        if (startOrEndDate.contains("Start")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        } else {
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        }
        findMonth(calendarHeading, startOrEndDate);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month, String startOrEndDate) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month, startOrEndDate);

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try {
            while (!DayPickerCaption.contains(month)) {

                if (monthStatus) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                } else {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
            }

        } catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }
    }

    public Boolean compareDate(String month, String startOrEndDate) {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        if (startOrEndDate.contains("Start")) {
            dateCaption = driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).getText();
        } else {
            dateCaption = driver.findElement(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).getText();
        }

        //Logic to compare dates before? or not
        Date first = null;
        try {
            first = format.parse(dateCaption);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date second = null;
        try {
            second = formatDate.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean before = (first.before(second));
        return before;

    }

    public void clickOnDay(String date) {

        try {

            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()=" + date + "]")).click();

        } catch (Exception e) {
            fail("The Date selected is out of RANGE.");
        }

    }

    public void addNewTimeSlot(String day, String hourStartTime, String hourEndTime, String minuteStartTime, String minuteEndTime, String meridianStartTime, String meridianEndTime, String numVisits) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).click();
        selectDayForSlotTime("div[class='ui button labeled icon QhYtAi_-mVgTlz73ieZ5W dropdown']", day);
        inputStartTime(hourStartTime, minuteStartTime, meridianStartTime);
        inputEndTime(hourEndTime, minuteEndTime, meridianEndTime);
        visitsNumber(numVisits);
        driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
    }

    public void verifyContentsOfRegularWeeklyHours() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        String startEndDatesForVisitsText = getDriver().findElement(By.cssSelector("div[class='availability _17ehxT_27Cme444W6HfdTN']")).getText();
        //String dayTableText = getDriver().findElement(By.cssSelector("table[class='ui basic table']")).getText();
        String dayTableText = getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")).getText();
        String displayStartEndForVisitsText = "2017-2018 Start and End Dates For Visits";
        Assert.assertTrue(dayTableText + " Text is not displayed", startEndDatesForVisitsText.contains("MON TUE WED THU FRI"));
        Assert.assertTrue(displayStartEndForVisitsText + " Text is not displayed", startEndDatesForVisitsText.contains(displayStartEndForVisitsText));
        Assert.assertTrue("Button Start Date is not showing.", button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).isDisplayed());
        Assert.assertTrue("Button End Date is not showing.", button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).isDisplayed());
        Assert.assertTrue("Button Add Time Slot is not showing.", button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).isDisplayed());
        button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).click();
    }

    public void selectDayForSlotTime(String element, String day) {
        WebElement dayList = driver.findElement(By.cssSelector(element.toString()));
        dayList.click();
        driver.findElement(By.cssSelector("div[class='menu transition visible']")).findElement(By.xpath("div/span[contains(text(), '" + day + "')]")).click();
    }

    public void inputStartTime(String hour, String minute, String meridian) {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='startTime']"));
        inputStartTime.click();
        inputStartTime.sendKeys(hour);
        inputStartTime.sendKeys(Keys.TAB);
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);

    }

    public void inputEndTime(String hour, String minute, String meridian) {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='endTime']"));
        inputStartTime.click();
        inputStartTime.sendKeys(hour);
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);
        inputStartTime.sendKeys(Keys.TAB);
    }

    public void visitsNumber(String numVisits) {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='numVisits']"));
        inputStartTime.sendKeys(numVisits);
    }

    public void VerifyMessagingOptionsUI() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Messaging Options").click();
        waitUntilPageFinishLoading();

        Assert.assertTrue("Confirmation Message header is not showing", text("Confirmation Message").isDisplayed());
        Assert.assertTrue("Special Instruction for RepVisits header is not showing", text("Special Instruction for RepVisits").isDisplayed());
        Assert.assertTrue("Confirmation message Information text is not showing", getDriver().findElement(By.cssSelector("[for=\"emailInstructions\"]")).getText().
                contains("When appointments are confirmed, we will automatically email the higher education institution and include appointment details as well as your contact information. If you would like to add additional information, you can do so here."));
        Assert.assertTrue("webInstructions Message text is not showing", getDriver().findElement(By.cssSelector("[for=\"webInstructions\"]")).getText().
                contains("Is there anything you would like the representative to know before scheduling a visit? If so, include that information here. Please note we will display your contact information and the school's address."));
        Assert.assertTrue("Email Instructions textbox is not showing", getDriver().findElement(By.cssSelector("[id=\"emailInstructions\"]")).isDisplayed());
        Assert.assertTrue("Web Instructions textbox is not showing", getDriver().findElement(By.cssSelector("[id=\"webInstructions\"]")).isDisplayed());
        Assert.assertTrue(button("Update Messaging").isDisplayed());
    }

    public void setStartAndEndDates(String startDate, String endDate) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        setDate(startDate);
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        setDate(endDate);
    }

    public void setDate(String inputDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month) {

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try {
            while (!DayPickerCaption.contains(month)) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        } catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }
    }

    public void verifyStartAndEndDates(String startDate, String endDate) {
        String[] partsStartDate = startDate.split(" ");
        String[] partsEndDate = endDate.split(" ");
        String startMonth = partsStartDate[0].substring(0, 3);
        String startDay = partsStartDate[1];
        String startYear = partsStartDate[2];
        String endMonth = partsEndDate[0].substring(0, 3);
        String endDay = partsEndDate[1];
        String endYear = partsEndDate[2];

        try {

            Assert.assertTrue("Button Start Date is not showing.", driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).
                    findElement(By.xpath("//span[contains(text(), '" + startMonth + " " + startDay + ", " + startYear + "')]")).isDisplayed());

            Assert.assertTrue("Button End Date is not showing.", driver.findElement(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).
                    findElement(By.xpath("//span[contains(text(), '" + endMonth + " " + endDay + ", " + endYear + "')]")).isDisplayed());

        } catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }

    }

    public void verifyTimeZonePage(String ValueTZ) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Time Zone").click();
        Assert.assertTrue("Tell us about your high school text is not present", getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present", getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.", TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match", getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed", getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());
    }

    public void setTimeZone(String timeZone) {
        if (!isLinkActive(link("Time Zone"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Time Zone").click();
        }
        setTimeZoneValue(timeZone);
        button("Update time zone").click();
    }

    public void clickLinkAvailability() {
        if (!isLinkActive(link("Availability"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Availability").click();
        }
    }

    private void setTimeZoneValue(String timeZone) {
        WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
        EntertimeZone.click();
        getDriver().findElement(By.xpath("//span[text()='" + timeZone + "']")).click();
    }

    public void verifyOverviewPage() {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyOverviewPage();
    }

    public void verifyRepvisitsSetupWizardTimeZoneMilestones() {

        load(GetProperties.get("hs.WizardAppSelect.url"));

        // getStartedBtn().click();
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='High School Information']")).size() == 0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        //verify UI
        Assert.assertTrue("'Tell us about your high school' is not displayed", text("Tell us about your high school.").isDisplayed());
        Assert.assertTrue("'Please specify your high school's time zone.' is not showing", text("Please specify your high school's time zone.").isDisplayed());

        Assert.assertTrue(button("Back").isDisplayed());
        Assert.assertTrue(button("Next").isDisplayed());

    }


    public void navigateToFairsAndVisistsAndVerifyEachScreen() {

        //verifying the navigation of corresponding screen for 'Fairs' , 'Visits' and 'Visits and Fairs'

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='VISITS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Availability']")).size() == 0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }

        Assert.assertTrue("'Availability' is not displayed", text("Regular Weekly Hours").isDisplayed());

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='VISITS_AND_FAIRS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Availability']")).size() == 0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("'Availability' is not displayed", text("Regular Weekly Hours").isDisplayed());

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='FAIRS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//a[@class='menu-link active']/span[text()='College Fairs']")).size() == 0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("'College Fairs' is not displayed", text("College Fairs").isDisplayed());

    }


    public void verifyTimeZoneInRepVisits(String alreadySelectedTimeZone, String newTimeZone) {

        String timeZoneToSet;
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        button("Next").click();
        //verify time zone saving properly
        String timeZoneBeforeChange = driver.findElement(By.xpath("//div[@class='ui search selection dropdown']//div[@class='text']")).getText();
        if (timeZoneBeforeChange.contains(alreadySelectedTimeZone)) {
            timeZoneToSet = newTimeZone;
        } else {
            timeZoneToSet = alreadySelectedTimeZone;
        }
        setTimeZoneValue(timeZoneToSet);

        button("Back").click();
        button("Next").click();

        String actualTimeZoneWhenBackBtnClicked = driver.findElement(By.xpath("//div[@class='ui search selection dropdown']//div[@class='text']")).getText();
        Assert.assertTrue("'Timezone saved when click on back button'", !actualTimeZoneWhenBackBtnClicked.contains(timeZoneToSet));

        setTimeZoneValue(timeZoneToSet);
        button("Next").click();

        button("Back").click();

        String actualTimeZoneWhenNextButtonClicked = driver.findElement(By.xpath("//div[@class='ui search selection dropdown']//div[@class='text']")).getText();
        Assert.assertTrue("'Timezone is not saved when click on Next button'", actualTimeZoneWhenNextButtonClicked.contains(timeZoneToSet));

        button("Back").click();

    }


    public void verifyWelcomeWizard() {

        load(GetProperties.get("hs.WizardAppWelcome.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Welcome to Repvisits' text is not displayed", text("Welcome to RepVisits").isDisplayed());
        Assert.assertTrue("'Get Started' button is not displayed", getStartedBtn().isDisplayed());

    }

    public void clickGetStartedBtn() {
        if (getStartedBtn().isDisplayed()) {
            getStartedBtn().click();
        }

    }

    private WebElement getStartedBtn() {
        return button("Get Started!");
    }


    //locators
    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }

    public void SetSpecialInstructionsForHEUser(String Instructions) {
        getWebInstructions().clear();
        getWebInstructions().sendKeys(Instructions);
        button("Update Messaging").click();

    }

    public void VerifySpecialInstructionsForHE(String instructionsText) {
        Assert.assertTrue("Special Instructions for RepVisits Text is not similar", getDriver().findElement(By.id("webInstructions")).getText().contains(instructionsText));
    }

    public void clicklinkCollegeFair() {
        navBar.goToRepVisits();
        link("College Fair").click();
    }

    public void clickViewDetails(String FairDetails, String FairName) {
        String columnNumber = "";
        if (FairDetails.equals("View Details")) {
            columnNumber = "6";
        }

        driver.findElement(By.xpath("//td[text()='" + FairName + "']/../td[" + columnNumber + "]")).click();
    }

    public void clickMessageCollegesButton() {
        button("MESSAGE COLLEGES").click();
    }

    public void massEmailMessageForAttendees(String emailMessage) {
        getcollegefairattendeemsg().clear();
        getcollegefairattendeemsg().sendKeys(emailMessage);
    }

    public void sendMessage() {
        button("SEND MESSAGE").click();
    }

    public void verifySentEmailConfirmationMessage() {

        Assert.assertTrue("Email Message Sent Confirmation  Message displayed", getDriver().findElement(By.cssSelector("#success-message-grid>p")).isDisplayed());
    }

    public void closeSendEmailMessageBox() {
        button("Close").click();
    }

    public void clickAddCollegeFairButton() {
        button("Add a College Fair").click();
    }

    public void clickSaveButtonToSaveFairDetails() {
        button("Save").click();
    }

    public void clickFairClose() {
        button("Close").click();

    }

    public void addCollegeFairdetails(DataTable CollegeFairDetails) {
        List<List<String>> collegeFairData = CollegeFairDetails.asLists(String.class);
        for (List<String> fieldrow : collegeFairData) {
            switch (fieldrow.get(0)) {
                case "College Fair Name":
                    collegeFairNameTextBox().clear();
                    collegeFairNameTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "Date":
                    collegeFairDateCalendar().click();
                    while (!collegeFairCurrentMonth().getText().contains(fieldrow.get(1).split(" ")[0])) {
                        collegeFairMonthToSelect().click();
                    }
                    collegeFairDayofMonth(fieldrow.get(1).split(" ")[1]).sendKeys(fieldrow.get(1));

                    break;
                case "Start Time":
                    collegeFairStartTime("College Fair Start Time").sendKeys(fieldrow.get(1));
                    break;
                case "End Time":
                    collegeFairEndTime("college Fair End Time").sendKeys(fieldrow.get(1));
                    break;
                case "RSVP Deadline":
                    collegeFairRSVPDeadlineDate().click();
                    while (!collegeFairCurrentMonth().getText().contains(fieldrow.get(1).split(" ")[0])) {
                        collegeFairMonthToSelect().click();
                    }
                    collegeFairDayofMonth(fieldrow.get(1).split(" ")[1]).sendKeys(fieldrow.get(1));
                    break;
                case "Cost":
                    collegeFairCost().clear();
                    collegeFairCost().sendKeys(fieldrow.get(1));
                    break;
                case "Max Number of Colleges":
                    collegeFairMaxNumberColleges().clear();
                    collegeFairMaxNumberColleges().sendKeys(fieldrow.get(1));
                    break;
                case "Number of Students Expected":
                    collegeFairNumberOfExpectedStudents().clear();
                    collegeFairNumberOfExpectedStudents().sendKeys(fieldrow.get(1));
                    break;
                case "Instructions for College Representatives":
                    collegeFairInstructions().clear();
                    collegeFairInstructions().sendKeys(fieldrow.get(1));
                    break;
                case "Email Message to colleges after confirmation":
                    collegeFairEmailMessageToColleges().sendKeys(fieldrow.get(1));
                    break;
            }
        }
    }

    public void viewFairDetails(String fairName) {
        getDriver().findElement(By.xpath("//div[@class='_1743W0qaWdOtlS0jkveD7o'][1]/table/tbody/tr[1]/td[text()='eee']/following-sibling::td[4]/a/span")).click();

    }

    public void clickAddAttendeetovisit() {
        addSchoolAttendees().click();
    }

    public void clickAddAttendees() {
        button("Add Attendees").click();

    }

    public void addAttendeeFromList(String attendeename) {
        addchooluserfromlist().clear();
        addchooluserfromlist().sendKeys(attendeename);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='results transition visible']/div/div[2]"), 1));
        attendeeUser().click();
    }

    public void addSchoolsToFair() {
        waitUntilPageFinishLoading();
        getDriver().findElement(By.cssSelector("button#add-attendee")).click();
    }

    public void noIamDoneclick() {
                noIamDone().sendKeys(Keys.RETURN);
    }

    public void addSchoolUserManually() {
        linkToAddSchoolUser().click();

    }


    public void addDataToAddAttendeeManually(DataTable AttendeeDetails){
        List<List<String>> AttendeeInformation = AttendeeDetails.asLists(String.class);
        for (List<String> fieldrow : AttendeeInformation) {
            switch (fieldrow.get(0)) {
                case "First Name":
                    attendeeFirstNameTextBox().clear();
                    attendeeFirstNameTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "Last Name":
                    attendeeLastNameTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "Email ":
                    attendeeEmailTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "Phone":
                    attendeePhoneTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "Position":
                    attendeePositionTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "Institution":
                    attendeeInstitutionTextBox().sendKeys(fieldrow.get(1));
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(".//*[@id='undefined-results']/div[1]/div/div[1]"), 1));
                    attendeeCollege().click();
                    break;

            }
        }
    }


    /*locators for Messaging Options Page*/
    private WebElement getWebInstructions() {
        return getDriver().findElement(By.id("webInstructions"));
    }

    private WebElement getcollegefairattendeemsg() {
        return getDriver().findElement(By.id("college-fair-attendee-msg"));

    }

    /* College fair locators*/
    private WebElement collegeFairNameTextBox() {
        return getDriver().findElement(By.cssSelector("input#college-fair-name"));
    }

    private WebElement collegeFairDateCalendar() {
        return getDriver().findElement(By.cssSelector("input#college-fair-date + i"));
    }

    private WebElement collegeFairMonthToSelect() {
        return getDriver().findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--next"));
    }

    private WebElement collegeFairCurrentMonth() {
        return getDriver().findElement(By.cssSelector("div.DayPicker-Caption"));
    }

    private WebElement collegeFairCost() {
        return getDriver().findElement(By.cssSelector("input#college-fair-cost"));
    }

    private WebElement collegeFairMaxNumberColleges() {
        return getDriver().findElement(By.cssSelector("input#college-fair-max-number-colleges"));
    }

    private WebElement collegeFairNumberOfExpectedStudents() {
        return getDriver().findElement(By.cssSelector("input#college-fair-number-expected-students"));
    }

    private WebElement collegeFairInstructions() {
        return getDriver().findElement(By.cssSelector("textarea#college-fair-instructions"));
    }

    private WebElement collegeFairEmailMessageToColleges() {
        return getDriver().findElement(By.cssSelector("textarea#college-fair-email-message-to-colleges"));

    }

     private WebElement collegeFairDayofMonth(String dayText) {
        return getDriver().findElement(By.xpath("//div[@class='DayPicker-Day' and text()='" + dayText + "']"));
    }
    private  WebElement collegeFairStartTime(String startTime){
        return getDriver().findElement(By.cssSelector("input#college-fair-start-time"));
    }
    private  WebElement collegeFairEndTime(String endTime){
        return  getDriver().findElement(By.cssSelector("input#college-fair-end-time"));
    }
    private  WebElement collegeFairRSVPDeadlineDate(){
        return getDriver().findElement(By.cssSelector("input#college-fair-rsvp-deadline + i"));
    }
    private  WebElement searchAttendees(){
        return getDriver().findElement((By.cssSelector("input[type='text']:not(#global-search-box-input)")));
    }
    private  WebElement attendeeUser(){
        return  getDriver().findElement(By.xpath("//div[@class='results transition visible']/div/div[2]"));
    }
    private WebElement privacyPolicy(){
        return getDriver().findElement(By.cssSelector("div.computer.only.row div.three.wide.column:not(.right)"));
    }
    private WebElement noIamDone(){
        return  getDriver().findElement(By.cssSelector("button.ui.basic.primary.button:not(.small):not(.tiny):not(.right)"));
    }
    private WebElement addchooluserfromlist(){
        return  getDriver().findElement((By.cssSelector("div.ui.transparent.input._2PrMqJV_VVSbAUG-ldyM-g input")));
    }
    private WebElement addSchoolAttendees(){
        return getDriver().findElement(By.cssSelector("button.ui.primary.right.floated.button:not(.small):not(.basic)"));
    }
    private WebElement linkToAddSchoolUser(){
        return getDriver().findElement(By.cssSelector("a.KKyfdym6DkswwZqeWN7Ck"));

    }
    private WebElement attendeeFirstNameTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-first-name"));
    }
    private WebElement attendeeLastNameTextBox(){
        return getDriver().findElement(By.cssSelector("input#add-rep-last-name"));
    }
    private WebElement attendeeEmailTextBox(){
        return getDriver().findElement(By.cssSelector("input#add-rep-email"));
    }
    private WebElement attendeePhoneTextBox(){
        return getDriver().findElement(By.cssSelector("input#add-rep-phone"));
    }
    private WebElement attendeePositionTextBox(){
        return getDriver().findElement(By.cssSelector("input#add-rep-position"));
    }
    private WebElement attendeeInstitutionTextBox(){
        return getDriver().findElement(By.cssSelector("input#add-rep-institution"));


    }
    private WebElement attendeeCollege(){
        return  getDriver().findElement(By.xpath(".//*[@id='undefined-results']/div[1]/div/div[1]"));
    }


}