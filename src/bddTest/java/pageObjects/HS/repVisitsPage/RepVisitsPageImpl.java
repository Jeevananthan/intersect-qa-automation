package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import utilities.GetProperties;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import utilities.GetProperties;
import utilities.GetProperties;
import java.util.*;
import java.util.List;
import static org.junit.Assert.fail;
import static junit.framework.TestCase.fail;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    //Creating RepVisitsPageImpl class object of for HE.
    pageObjects.HE.repVisitsPage.RepVisitsPageImpl repVisitsPageHEObj = new pageObjects.HE.repVisitsPage.RepVisitsPageImpl();

    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

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
        tabs.add("Blocked Days");
        tabs.add("Exceptions");
        tabs.add("Availability Settings");
        for (String tab : tabs) {
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!",link(tab).isDisplayed());
        }
    }

    public void verifyAvailabilitySettings(DataTable dataTable){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed",text("Visit Scheduling").isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed",text("Accept").isDisplayed());
        Assert.assertTrue("Listbox is not displayed",driver.findElement(By.cssSelector("div[class='ui selection dropdown'][role='listbox']")).isDisplayed());
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
        Assert.assertTrue("'Visit Confirmations' header is not displayed as expected!", text("Visits Confirmations").isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Automatically confirm all visit requests?' is not displaying as expected!", text("Automatically confirm all visit requests?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'Yes, accept all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='Yes, accept all incoming requests.']/input[@type='radio']")).isDisplayed());
        Assert.assertTrue("radiobutton 'No, I want to manually review all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='No, I want to manually review all incoming requests.']/input[@type='radio']")).isDisplayed());

        List<Map<String,String>> entities = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> availabilityData : entities ){
            for (String key : availabilityData.keySet()){
                switch (key){
                    // This is where we verify the actual editable values on the screen.
                    case "Accept":
                        String currentOption = driver.findElement(By.cssSelector("div[class='ui selection dropdown']>div[class='text']")).getText();
                        Assert.assertTrue("'Accept' value was not as expected.",currentOption.contains(availabilityData.get(key)));
                        break;
                    case "visits per day":
                        String actualVisitPerDay = driver.findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.",actualVisitPerDay.equals(availabilityData.get(key)));
                        break;
                    case "Who can see your availability?":
                        if (availabilityData.get(key).contains("All Repvisits Users")){
                            Assert.assertTrue("Availability option is not set as expected",driver.findElement(By.cssSelector("[name=availabilityVisible][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("Only Me")) {
                            Assert.assertTrue("Availability option is not set as expected", driver.findElement(By.cssSelector("[name=availabilityVisible][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit availability.");
                        }
                        break;
                    case "Automatically confirm all visit requests?":
                        if (availabilityData.get(key).contains("Yes, accept all incoming requests.")){
                            Assert.assertTrue("Confirmation option is not set as expected",driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("No, I want to manually review all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit request confirmations.");
                        }
                        break;
                    case "Prevent colleges from scheduling new visits less than":
                        String rsvpDeadline = driver.findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.",rsvpDeadline.equals(availabilityData.get(key)));
                        break;
                    case "Prevent colleges from cancelling or rescheduling less than":
                        String modifyDeadline = driver.findElement(By.cssSelector("input[name='modifyDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.",modifyDeadline.equals(availabilityData.get(key)));
                        break;
                }
            }
        }

    }

    public void setPreventCollegesSchedulingNewVisits(String Numberofdays){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(Numberofdays);
        button("Save Changes").click();
    }

    public void setPreventCollegesCancellingorRescheduling(String DaysInAdvance){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(DaysInAdvance);
        button("Save Changes").click();
    }

    public void setAcceptInVisitSchedulingToFullyBooked(String accept){
        setAcceptinAvailabilitySettings(accept, "1");
    }
    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement selectAccept = getDriver().findElement(By.cssSelector("div[class='ui selection dropdown']>div"));
        selectAccept.click();
        getDriver().findElement(By.xpath("//span[text()='" + accept + "']")).click();
        if(accept.equals("a maximum of...")) {
            WebElement visitsBox = getDriver().findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']"));
            visitsBox.clear();
            visitsBox.sendKeys(visitsPerDay);
        }
        button("Save Changes").click();
    }

    public void setVisitsConfirmations(String option){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
        options.findElement(By.xpath("div/label[text()[contains(., '"+ option +"')]]")).click();
        button("Save Changes").click();
    }

    public void accessVisitAvailability(String visitAvailability){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        driver.findElement(By.xpath("//label[text()='"+visitAvailability+"']/input[@type='radio']")).click();
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
        if(!visitAvailabiltyOption.equals("")) {
            Assert.assertTrue("The option " + visitAvailabiltyOption + " radio button is not checked", driver.findElement(By.xpath("//div[@class='grouped fields']//label[text()='"+visitAvailabiltyOption+"']/input[@type='radio']")).isSelected());
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

    public void verifyTimeSlotAdded(String hourStartTime, String minuteStartTime, String meridianStartTime) {

        Assert.assertTrue("The Time Slot was not added" , driver.findElement(By.cssSelector("table[class='ui unstackable basic table']")).findElement(By.xpath("//button[contains(text(), '"+hourStartTime+ ":"+ minuteStartTime + meridianStartTime +"')]")).isDisplayed());

    }


    public void findMonth(String month, String startOrEndDate) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month, startOrEndDate);

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {

                if (monthStatus){
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
                else {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
            }

        }
        catch (Exception e) {
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }

    public Boolean compareDate(String month, String startOrEndDate)  {

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
        return  before;

    }

    public void clickOnDay(String date) {

        try {

            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+"]")).click();

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    public void addNewTimeSlot(String day, String hourStartTime, String hourEndTime, String minuteStartTime, String minuteEndTime, String meridianStartTime, String meridianEndTime, String numVisits) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        text("ADD TIME SLOT").click();
        WebElement daySelector = getParent(text("MONDAY - FRIDAY"));
        daySelector.click();
        daySelector.findElement(By.xpath("//div/span[contains(text(),'" + day + "')]")).click();
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
        Assert.assertTrue(dayTableText + " Text is not displayed",
                startEndDatesForVisitsText.contains("MON TUE WED THU FRI"));
        Assert.assertTrue(displayStartEndForVisitsText + " Text is not displayed",
                startEndDatesForVisitsText.contains(displayStartEndForVisitsText));
        Assert.assertTrue("Button Start Date is not showing.",
                button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).isDisplayed());
        Assert.assertTrue("Button End Date is not showing.",
                button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).isDisplayed());
        Assert.assertTrue("Button Add Time Slot is not showing.",
                button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).isDisplayed());
        button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).click();
    }

    public void selectDayForSlotTime(String element, String day)
    {
        WebElement dayList = driver.findElement(By.cssSelector(element.toString()));
        dayList.click();
        driver.findElement(By.cssSelector("div[class='menu transition visible']")).findElement(By.xpath("div/span[contains(text(), '"+day+"')]")).click();
    }

    public void inputStartTime(String hour, String minute, String meridian)
    {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='startTime']"));
        inputStartTime.click();
        inputStartTime.sendKeys(hour);
        inputStartTime.sendKeys(Keys.TAB);
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);

    }

    public void inputEndTime(String hour, String minute, String meridian)
    {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='endTime']"));
        inputStartTime.click();
        inputStartTime.sendKeys(hour);
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);
        inputStartTime.sendKeys(Keys.TAB);
    }

    public void visitsNumber(String numVisits)
    {
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

        // Set End Date to 07/15/2018 and Start Date to 07/15/2017 so that we can set our real dates
        // without the UI stopping us from choosing dates that would be invalid.
        setAvailabilityToWholeSchoolYear();

        // Set the dates we actually want.
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        setDate(startDate);
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        setDate(endDate);
    }

    private void setAvailabilityToWholeSchoolYear() {
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        setDate("July 15 2018");
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        String startDate = "July 15 2017";
        String[] parts = startDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
        text("UPDATE DATE").click();
    }

    public void setDate(String inputDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }
    public void setDateDoubleClick(String inputDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        WebElement Date = driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[contains(text(), "+parts[1]+")]"));
        doubleClick(Date);
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month) {

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            int i = 0;
            while (!DayPickerCaption.contains(month) && i < 12) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                i++;
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
            while (!DayPickerCaption.contains(month) && i > -12) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                i--;
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        }
        catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.\n" + e.getMessage());
        }
    }

    public void accessWelcomeSetupWizard(String optionToSelect) {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        if (!optionToSelect.equals("")) {
            if (optionToSelect.equalsIgnoreCase("VISITS")) {
                driver.findElement(By.xpath("//input[@value='VISITS']")).click();
                waitUntilPageFinishLoading();
            } else if (optionToSelect.equalsIgnoreCase("FAIRS")) {
                driver.findElement(By.xpath("//input[@value='FAIRS']")).click();
                waitUntilPageFinishLoading();
            } else if (optionToSelect.equalsIgnoreCase("VISITS AND FAIRS")) {
                driver.findElement(By.xpath("//input[@value='VISITS_AND_FAIRS']")).click();
                waitUntilPageFinishLoading();
            } else {
                Assert.fail("The given option to select is not a valid one");
            }
        }
        driver.findElement(By.xpath("//button/span[text()='Next']")).click();
        waitUntilPageFinishLoading();
    }
     public void accessHighschoolInformationSetupWizard(String timeZone) {
         if (!timeZone.equals("")) {
             WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
             EntertimeZone.click();
             getDriver().findElement(By.xpath("//span[text()='"+timeZone+"']")).click();
         }
         driver.findElement(By.xpath("//button/span[text()='Next']")).click();
     }
     public void verifyFairOverview() {
//        Assert.assertTrue("College Fair at Int QA High School is not displayed",text("CollegeFairs at Int QA High School 4").isDisplayed());
     }


    public void accessOneLastStepSetupWizard(String visitAvailability) {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Complete!']")).size() == 0) {
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(button("NEXT")));
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("Complete page is not displayed", text("Visit Availability").isDisplayed());
        if (!visitAvailability.equals("")) {
            if (visitAvailability.equalsIgnoreCase("All RepVisits Users")) {
                driver.findElement(By.xpath("//label[text()='All RepVisits Users']/input[@type='radio']")).click();
            } else if (visitAvailability.equalsIgnoreCase("Only Me")) {
                driver.findElement(By.xpath("//label[text()='Only Me']/input[@type='radio']")).click();
            } else {
                Assert.fail("The given option for the visitAvailability is not a valid one");
            }
        }
        driver.findElement(By.xpath("//button/span[text()='Next']")).click();
    }

    public void verifyYouAreAllSetPage(String visibilitySetting) {
        if (visibilitySetting.equalsIgnoreCase("Only Me")) {
            Assert.assertTrue("Only Me Ack is not displayed ", text("Your visit availability has been successfully set up!").isDisplayed());
        } else if (visibilitySetting.equalsIgnoreCase("All RepVisits Users")) {
            Assert.assertTrue("All RepVisits Ack page is not displayed", text("https://CounselorCommunity.com").isDisplayed());
        } else {
            Assert.fail("Error: " + visibilitySetting + " is not a valid option for Visit Availability");
        }
        driver.findElement(By.xpath("//span[text()='Take me to my visits']")).click();
        Assert.assertTrue("Calendar is not displayed", text("Today").isDisplayed());

        driver.navigate().back();
        waitUntilPageFinishLoading();

        button("Get started with college fairs").click();
        Assert.assertTrue("College Fair at Int QA High School is not displayed", text("CollegeFairs at Int Qa High School 4").isDisplayed());

    }

    public void verifyStartAndEndDates(String startDate, String endDate){
        String[] partsStartDate = startDate.split(" ");
        String[] partsEndDate = endDate.split(" ");
        String startMonth = partsStartDate[0].substring(0, 3);
        String startDay = partsStartDate[1];
        String startYear = partsStartDate [2];
        String endMonth = partsEndDate[0].substring(0, 3);
        String endDay = partsEndDate[1];
        String endYear = partsEndDate [2];

        try {

            Assert.assertTrue("Button Start Date is not showing.",
                    driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).
                            findElement(By.xpath("//span[contains(text(), '" + startMonth + " " + startDay + ", " + startYear + "')]")).isDisplayed());

            Assert.assertTrue("Button End Date is not showing.",
                    driver.findElement(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).
                            findElement(By.xpath("//span[contains(text(), '" + endMonth + " " + endDay + ", " + endYear + "')]")).isDisplayed());

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    public void verifyTimeZonePage(String ValueTZ){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Time Zone").click();
        Assert.assertTrue("Tell us about your high school text is not present",getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present",getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.",TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match",getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed",getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());
    }

    public void verifyManualBlockedHolidays(String holiday) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        waitUntilPageFinishLoading();

        Boolean checkBoxLaborHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxColumbusHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxVeteransHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxTanksGivenHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxDayAfterThanksGivenHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxChristmasEveHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxChristmasDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxNewYearEveHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxNewYearDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxMartinLutherDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxPresidentDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxMemorialDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");
        Boolean checkBoxIndependenceDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[title='"+holiday+"']")));
        if (!getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")).getAttribute("class").contains("checked")) {
            getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).click();
            //Click on SAVE BLOCKED HOLIDAYS button
            getDriver().findElement(By.cssSelector("button[class='ui primary button']")).click();
        }

        link("Availability & Settings").click();
        link("Blocked Days").click();

        switch (holiday){
            case "LABOR_DAY":
                verifyHolidayCheckBoxStatus(checkBoxLaborHolidayStatus, holiday, 1);
                break;
            case "COLUMBUS_DAY":
                verifyHolidayCheckBoxStatus(checkBoxColumbusHolidayStatus, holiday, 2);
                break;
            case "VETERANS_DAY":
                verifyHolidayCheckBoxStatus(checkBoxVeteransHolidayStatus, holiday, 3);
                break;
            case "THANKSGIVING_DAY":
                verifyHolidayCheckBoxStatus(checkBoxTanksGivenHolidayStatus, holiday, 4);
                break;
            case "DAY_AFTER_THANKSGIVING":
                verifyHolidayCheckBoxStatus(checkBoxDayAfterThanksGivenHolidayStatus, holiday, 5);
                break;
            case "CHRISTMAS_EVE":
                verifyHolidayCheckBoxStatus(checkBoxChristmasEveHolidayStatus, holiday, 6);
                break;
            case "CHRISTMAS_DAY":
                verifyHolidayCheckBoxStatus(checkBoxChristmasDayHolidayStatus, holiday, 7);
                break;
            case "NEW_YEAR_EVE":
                verifyHolidayCheckBoxStatus(checkBoxNewYearEveHolidayStatus, holiday, 8);
                break;
            case "NEW_YEAR_DAY":
                verifyHolidayCheckBoxStatus(checkBoxNewYearDayHolidayStatus, holiday, 9);
                break;
            case "MARTIN_LUTHER_DAY":
                verifyHolidayCheckBoxStatus(checkBoxMartinLutherDayHolidayStatus, holiday, 10);
                break;
            case "PRESIDENTS_DAY":
                verifyHolidayCheckBoxStatus(checkBoxPresidentDayHolidayStatus, holiday, 11);
                break;
            case "MEMORIAL_DAY":
                verifyHolidayCheckBoxStatus(checkBoxMemorialDayHolidayStatus, holiday, 12);
                break;
            case "INDEPENDENCE_DAY":
                verifyHolidayCheckBoxStatus(checkBoxIndependenceDayHolidayStatus, holiday, 13);
                break;
            default:
                logger.info("Wrong Holiday!.");
        }
    }

    public void searchforSchool(String school,String location, String Date)
    {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).sendKeys(school);
        driver.findElement(By.xpath("//form[@class='ui form _2Z8eaWTc5nFILCDiDTD__z']//button[contains(@class,'ui button')]")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("location is not displayed",driver.findElement(By.xpath("//td[text()='"+location+"']")).isDisplayed());
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
        //driver.findElement(By.cssSelector("button[class='ui right labeled tiny icon button _1alys3gHE0t2ksYSNzWGgY right floated']")).click();
        driver.findElement(By.className("_135QG0V-mOkCAZD0s14PUf")).findElement(By.xpath("button")).click();
        setDate(Date, "Start");
        waitUntilPageFinishLoading();
        Assert.assertTrue("Was not set Blocked!", getParent(getDriver().findElement(By.className("JIilVAK-W5DJoBrTmFeUG"))).findElement(By.tagName("p")).getText().toLowerCase().contains("holiday"));

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

    public Boolean compareHEDate(String month, String startOrEndDate)  {

        String dateCaption = null;
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        if (startOrEndDate.contains("Start")) {
              dateCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
        } else {
            dateCaption = driver.findElement(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).getText();
        }

        //Logic to compare dates before? or not
        Date first = null;
        try {
            first = formatDate.parse(dateCaption);
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
        return  before;

    }

    public void setTimeZone(String timeZone){
        if (!isLinkActive(link("Time Zone"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Time Zone").click();
        }
        setTimeZoneValue(timeZone);
        button("Update time zone").click();
    }

    public void verifyHolidayCheckBoxStatus(Boolean holidayStatus, String holiday, int index){
        if (holidayStatus) {
            Assert.assertTrue("Was not set Holiday!", getDriver().findElement(By.cssSelector("div[title='"+ holiday +"']")).isDisplayed() && getDriver().findElement(By.xpath("(//div[starts-with(@class, 'ui checked checkbox _1mJoFw7NZ4cPX_1EWFCyjY')])['"+ index +"']")).isDisplayed());
        }
        else{
            getDriver().findElement(By.cssSelector("div[title='"+ holiday +"']")).click();
            Assert.assertTrue("Was not set Holiday!", getDriver().findElement(By.cssSelector("div[title='"+ holiday +"']")).isDisplayed() && getDriver().findElement(By.xpath("(//div[starts-with(@class, 'ui checked checkbox _1mJoFw7NZ4cPX_1EWFCyjY')])['"+ index +"']")).isDisplayed());
        }
    }

    public void clickLinkAvailability() {
        if (!isLinkActive(link("Availability"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Availability").click();
        }
    }

    public void verifyCalendarSyncMilestoneInSetupWizard(){
        waitForUITransition();
        waitForUITransition();
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Calendar Sync']")).size()==0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }

        //verify 'Back' button and 'Next' button is displayed
        Assert.assertTrue(button("Back").isDisplayed());
        Assert.assertTrue(button("Next").isDisplayed());

        //verify UI text
        Assert.assertTrue("'Calendar Sync' page is not displayed", text("iCal/Outlook Subscription").isDisplayed());

        button("Next").click();
        Assert.assertTrue("'Naviance setting page' page is not displayed", text("Connecting Naviance and RepVisits").isDisplayed());

        button("Back").click();
        waitUntilPageFinishLoading();
        button("Back").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Notification and Primary contact' page is not displayed", text("Primary Contact for Visits").isDisplayed());


    }

    private void setTimeZoneValue(String timeZone) {
        WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
        EntertimeZone.click();
        getDriver().findElement(By.xpath("//span[text()='"+ timeZone +"']")).click();
    }

    public void verifyCalendarViewOnRepVisits() {
        navBar.goToRepVisits();
        link("Calendar").click();

        //Verify Small Calendar
        Assert.assertTrue("add visit button is not displayed",button("add visit").isDisplayed());
        Assert.assertTrue("Small Calendar is not displayed",driver.findElement(By.cssSelector("div[role='application']")).isDisplayed());
        Assert.assertTrue("small calendar next button is not displayed",driver.findElement(By.cssSelector("button[title='right']>i")).isDisplayed());
        Assert.assertTrue("small calendar previous button is not displayed",driver.findElement(By.cssSelector("button[title='left']>i")).isDisplayed());


        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement mainCalendarNextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Forwards']>i")));

        //Verify Main Calendar
        Assert.assertTrue("Main Calendar is not displayed",driver.findElement(By.cssSelector("div[class='rbc-calendar rep-visits-rbc-calendar']")).isDisplayed());
        Assert.assertTrue("Main calendar next button is not displayed",driver.findElement(By.cssSelector("button[title='Forwards']>i")).isDisplayed());
        Assert.assertTrue("Main calendar previous button is not displayed",driver.findElement(By.cssSelector("button[title='Backwards']>i")).isDisplayed());

        //verify day view
        Assert.assertTrue(" Day view button is not displayed",button("Day").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Day']")).click();
        Assert.assertTrue("Day view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify week view
        Assert.assertTrue(" Week view button is not displayed",button("Week").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Week']")).click();
        Assert.assertTrue("Week view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify month view
        Assert.assertTrue(" Month view button is not displayed",button("Month").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Month']")).click();
        Assert.assertTrue("Month view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-month-view']")).isDisplayed());

        // Appointments are clickable
        driver.findElement(By.cssSelector("div[class='_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa']")).click();
        waitForUITransition();
        Assert.assertTrue("Appointment details drawer is not displayed",driver.findElement(By.cssSelector("div[class='ui overlay right very wide visible sidebar _1bTs4IjZQSsADQ671qHLL3']")).isDisplayed());
        driver.findElement(By.xpath("//button[@aria-label='Close']")).click();

        //verify Appointment Keys
        //Visits
        Assert.assertTrue(" visit confirmed option is not displayed",text("Visits - Confirmed").isDisplayed());
        Assert.assertTrue("Visit confirmed Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='visit' and @type='checkbox']/following::label")).isDisplayed());
        String visitColor = "rgba(0, 0, 0, 0.87)";
        String actualVisitColor = driver.findElement(By.xpath("//input[@id='visit']")).getCssValue("color");
        Assert.assertTrue("Background Color for the Visit checkbox are not displayed",actualVisitColor.equals(visitColor));
        driver.findElement(By.xpath("//input[@id='visit']/following::label")).click();

        //Fairs
        Assert.assertTrue(" Fair confirmed option is not displayed",text("College Fair - Confirmed").isDisplayed());
        Assert.assertTrue("Fair confirmed Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='fair'and @type='checkbox']/following::label")).isDisplayed());
        String fairColor = "rgba(0, 0, 0, 0.87)";
        String actualFairColor = driver.findElement(By.xpath("//input[@id='fair']")).getCssValue("color");
        Assert.assertTrue("Background Color for the fair checkbox are not displayed",actualFairColor.equals(fairColor));
        driver.findElement(By.xpath("//input[@id='fair']/following::label")).click();

         //Pending
        Assert.assertTrue("Pending option is not displayed",text("Pending").isDisplayed());
        Assert.assertTrue("Pending Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='pending'and @type='checkbox']/following::label")).isDisplayed());
        String pendingColor = "rgba(0, 0, 0, 0.87)";
        String actualPendingColor = driver.findElement(By.xpath("//input[@id='pending']")).getCssValue("color");
        Assert.assertTrue("Background Color for the Pending checkbox are not displayed",actualPendingColor.equals(pendingColor));
        driver.findElement(By.xpath("//input[@id='pending']/following::label")).click();

    }

    public void verifyOverviewPage(){
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyOverviewPage();
    }

    public  void verifyRepvisitsSetupWizardTimeZoneMilestones() {

        load(GetProperties.get("hs.WizardAppSelect.url"));

        // getStartedBtn().click();
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='High School Information']")).size()==0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        //verify UI
        Assert.assertTrue("'Tell us about your high school' is not displayed", text("Tell us about your high school.").isDisplayed());
        Assert.assertTrue("'Please specify your high school's time zone.' is not showing", text("Please specify your high school's time zone.").isDisplayed());

        Assert.assertTrue(button("Back").isDisplayed());
        Assert.assertTrue(button("Next").isDisplayed());

    }


    public void navigateToFairsAndVisistsAndVerifyEachScreen(){

        //verifying the navigation of corresponding screen for 'Fairs' , 'Visits' and 'Visits and Fairs'

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='VISITS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Availability']")).size()==0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }

        Assert.assertTrue("'Availability' is not displayed", text("Regular Weekly Hours").isDisplayed());

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='VISITS_AND_FAIRS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Availability']")).size()==0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("'Availability' is not displayed", text("Regular Weekly Hours").isDisplayed());

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='FAIRS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//a[@class='menu-link active']/span[text()='College Fairs']")).size()==0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("'College Fairs' is not displayed", text("College Fairs").isDisplayed());

    }


    public void verifyTimeZoneInRepVisits(String alreadySelectedTimeZone,String newTimeZone){

        String timeZoneToSet;
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        button("Next").click();
        //verify time zone saving properly
        String timeZoneBeforeChange = driver.findElement(By.xpath("//div[@class='ui search selection dropdown']//div[@class='text']")).getText();
        if(timeZoneBeforeChange.contains(alreadySelectedTimeZone)) {
             timeZoneToSet = newTimeZone ;
        }
         else{
            timeZoneToSet = alreadySelectedTimeZone ;
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

    public void clickUpdateButtonInRepVisits(){
        if(updateBtn().isDisplayed()){
            updateBtn().click();
        }
    }

    public void verifyStartDateAndEndDateInAvailabilitySetting(String startDate,String endDate){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        String valStartDate = driver.findElement(By.xpath("//div[@style='display: inline-block;']/button[1]/b/span")).getText();
        String valEndDate = driver.findElement(By.xpath("//div[@style='display: inline-block;']/button[2]/b/span")).getText();

        Assert.assertTrue("Start date is not as expected",startDate.contains(valStartDate));
        Assert.assertTrue("End date is not as expected",endDate.contains(valEndDate));
    }


    public void verifyWelcomeWizard(){
        load(GetProperties.get("hs.WizardAppWelcome.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Welcome to Repvisits' text is not displayed",text("Welcome to RepVisits").isDisplayed());
        Assert.assertTrue("'Get Started' button is not displayed",getStartedBtn().isDisplayed());
    }

    public void clickGetStartedBtn(){
        if(getStartedBtn().isDisplayed()){
            getStartedBtn().click();
        }

    }

    private WebElement getStartedBtn(){
        return button("Get Started!");
    }
    public void goToWelcomeWizard(){
        load(GetProperties.get("hs.WizardAppWelcome.url"));
        waitUntilPageFinishLoading();
        button("Get Started!").click();
    }
    public void navigateToRepvisitWizard(String wizardName){
        while(driver.findElements(By.xpath("//div[@class='active step' and @name ='"+wizardName+"']")).size()==0){
            button("Next").click();
        }
    }

    public void addTimeSlotInRegularWeeklyHours(String day,String startTime,String endTime,String noOfVisits){

        Assert.assertTrue("Regular Weekly Hours is not displayed",text("Regular Weekly Hours").isDisplayed());

        driver.findElement(By.xpath("//button/span[text()='ADD TIME SLOT']")).click();

        WebElement dropdown = getParent(text("Monday - Friday"));
        doubleClick(dropdown);
        driver.findElement(By.xpath("//span[text()='"+day+"']")).click();

        driver.findElement(By.xpath("//input[@name='startTime']")).sendKeys(startTime);
        driver.findElement(By.xpath("//input[@name='endTime']")).sendKeys(endTime);

        driver.findElement(By.xpath("//input[@title='numVisits']")).sendKeys(noOfVisits);

        button("ADD TIME SLOT").click();

        button("Back").click();
        Assert.assertTrue("High school information is not displayed",text("Tell us about your high school.").isDisplayed());

        button("Next").click();
    }

    public void navigateToSubTabsInAvailabilityWizard(String tabName){
        while(driver.findElements(By.xpath("//li[@class='_1gXbsnbxcvr12eMqyC1xjb']/span[text()='"+tabName+"']")).size()==0){
            button("Next").click();
        }
    }

    public void selectBlockDaysAndSave(String dayName){
        button("Back").click();
        Assert.assertTrue("Regular Weekly Hours tab is not loaded",text("Regular Weekly Hours").isDisplayed());
        button("Next").click();
        driver.findElement(By.xpath("//div[@title='"+dayName+"']/input/following-sibling::label")).click();
    }

    public void changeWeekAvailability(String changeWeek){
        button("Back").click();
        Assert.assertTrue("Blocked Days tab is not loaded",text("Blocked Days").isDisplayed());
        button("Next").click();
        driver.findElement(By.xpath("//button[@title='"+changeWeek+"']")).click();
    }

    public void availabilityandSettingsPage(String visitsConfirmations ,String preventCollegesSchedulingNewVisits, String preventCollegesCancellingorRescheduling){
        button("Back").click();
        Assert.assertTrue("Exceptions tab is not loaded",text("Exceptions").isDisplayed());
        button("Next").click();
        Assert.assertTrue("Availability Settings tab is not loaded",text("Availability Settings").isDisplayed());
        //Visit Confirmation
        WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
        options.findElement(By.xpath("div/label[text()[contains(., '"+ visitsConfirmations +"')]]")).click();
        //Prevent Colleges Scheduling New Visits
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(preventCollegesSchedulingNewVisits);
        //Prevent Colleges Cancelling or Rescheduling
        WebElement cancellingBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        cancellingBox.clear();
        cancellingBox.sendKeys(preventCollegesCancellingorRescheduling);
        button("Next").click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='active step' and @name ='Messaging Options']")));
        Assert.assertTrue("Messaging Options tab is not loaded",driver.findElement(By.xpath("//div[@class='active step' and @name ='Messaging Options']")).isDisplayed());
    }

    public void setBlockedDate(String blockdate,String reason){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        chooseDates().click();
        setDate(blockdate);
        setDateDoubleClick(blockdate);
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        try {wait(2000);} catch (Exception e) {}
        selectReason.click();
        WebElement pickReason = driver.findElement(By.xpath("//span[text()='"+reason+"']"));
        pickReason.click();
        addBlockedTime().click();
    }

    public void RemoveBlockedDate(String startDate, String endDate){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        WebElement BlockedDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td[text()='"+startDate+"']/following-sibling::td[text()='"+endDate+"']/following-sibling::td/span[text()='Remove']"));
        BlockedDate.click();
    }

    public void verifyBlockedHolidaysAdded(String startDate,String endDate,String reason) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();

        int columnID = getColumnIdByFieldName( "//table[@class='ui basic table']//thead", "Start Date");
        int rowID = getRowIdByColumnId("//table[@class='ui basic table']//tbody", columnID, startDate);
        columnID = columnID+1;
        rowID = rowID+1;
        //verify Start Date
        String ActualStartDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr["+rowID+"]//td["+columnID+"]")).getText();
        Assert.assertTrue("Start date " + startDate + "is not displayed", startDate.equals(ActualStartDate));
        //verify End date
        String ActualEnddate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr["+rowID+"]//td[2]")).getText();
        Assert.assertTrue("End date " + endDate + "is not displayed", endDate.equals(ActualEnddate));
        //verify Reason
        String ActualReason = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr["+rowID+"]//td[1]")).getText();
        Assert.assertTrue("Reason " + reason + " is not displayed", reason.equals(ActualReason));
        //verify Remove link
        Assert.assertTrue("Reason " + reason + " is not displayed",driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr["+rowID+"]//td[3]")).isDisplayed());
    }


    public void verifyBlockedHolidaysRemoved(String startDate, String endDate,String reason) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        int columnID = getColumnIdByFieldName( "//table[@class='ui basic table']//thead", "Start Date");
        int rowID = getRowIdByColumnId("//table[@class='ui basic table']//tbody", columnID, startDate);

        if(rowID == -1){
            logger.info("The Given row with Start Date '"+startDate+"',End Date '"+endDate+"'with a '"+reason+"'was not found on the table");
        }else{
            Assert.fail("The Given row was found on the table");
        }
      }

    //Function Name  : getColumnIdByFieldName()
    public int  getColumnIdByFieldName(String tableHeaderLocator, String fieldName)
    {
        int columnId=-1, colCount = 0;
        String presentField=null;

        WebElement webEleTableHeader=driver.findElement(By.xpath(tableHeaderLocator));
        List<WebElement> webEleRows=webEleTableHeader.findElements(By.tagName("tr"));
        List<WebElement> webEleColumns=webEleRows.get(0).findElements(By.tagName("th"));
        colCount = webEleColumns.size();

        if(colCount > 0)
        {
            for(int colNum=1;colNum<colCount;colNum++)
            {
                presentField = webEleColumns.get(colNum).getText().trim().replace("\"", "");

                if(presentField.equals(fieldName))
                {
                    columnId = colNum;
                    break;
                }
            }
        }

        return columnId;
    }

    //Function Name  : getRowIdByColumnId()
    public int  getRowIdByColumnId(String tableBodyLocator, int columnId, String dataToFind)
    {
        int rowId=-1, rowCount = 0;

        WebElement webEleTableBody=driver.findElement(By.xpath(tableBodyLocator));
        List<WebElement> webEleRows=webEleTableBody.findElements(By.tagName("tr"));
        rowCount = webEleRows.size();

        if(rowCount > 0)
        {
            for(int rowNum=0;rowNum<rowCount;rowNum++)
            {
                List<WebElement> webEleColumns=webEleRows.get(rowNum).findElements(By.tagName("td"));
                if(webEleColumns.get(columnId).getText().equals(dataToFind))
                {
                    rowId = rowNum;
                    break;
                }
            }
        }

        return rowId;
    }
    public void verifyNotificationAndPrimaryContactInSetupWizard(String primaryUser,String changeNewUser){


        String userTochange;

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Notifications & Primary Contact']")).size()==0) {
            button("Next").click();
            waitUntilElementExists(button("Next"));
            waitUntilPageFinishLoading();
        }

        waitForUITransition();
        //verify 'Back' button and 'Next' button is displayed
        Assert.assertTrue(button("Back").isDisplayed());
        Assert.assertTrue(button("Next").isDisplayed());

        //verify UI text
        Assert.assertTrue("'Primary Contact for Visits' page is not displayed", text("Primary Contact for Visits").isDisplayed());

        Assert.assertTrue("Primary Contact Number field is not displayed",driver.findElement(By.id("notification_contacts_primary_contact_phone")).isDisplayed());

        String primaryContactName = driver.findElement(By.xpath("//div[@name='primaryContact']//div[@class='text']")).getText();

        if(primaryContactName.contains(primaryUser)) {
             userTochange = changeNewUser;
            logger.info("Primary user is selected in primary contact name");

        }
        else{
             userTochange = primaryUser;
        }
        driver.findElement(By.xpath("//div[@name='primaryContact']/div[@class='text']")).click();
        driver.findElement(By.xpath("//div[@class='menu transition visible']/div/span[text()='" + userTochange + "']")).click();

        button("Next").click();
        Assert.assertTrue("Calendar Sync page is not displayed", text("iCal/Outlook Subscription").isDisplayed());

        button("Back").click();
        primaryContactName = driver.findElement(By.xpath("//div[@class='ui selection dropdown']//div[@class='text']")).getText();
        Assert.assertTrue("Primary contact name is not saved properly",primaryContactName.equalsIgnoreCase(userTochange));

        if(primaryContactName.contains(primaryUser)) {
            userTochange = changeNewUser;
            logger.info("Primary user is selected in primary contact name");

        }
        else{
            userTochange = primaryUser;

        }


        button("Back").click();
        Assert.assertTrue("'Confirmation Message' page is not displayed", text("Confirmation Message").isDisplayed());
        button("Next").click();
        Assert.assertTrue("Primary contact name is not saved properly",!primaryContactName.contains(userTochange));

    }

    private WebElement updateBtn(){
        return text("UPDATE DATE");
    }
    public void verifyPillsNotAvailableinNewScheduleVisitPage(){
        navBar.goToRepVisits();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(currentDateInCalendar());
        driver.findElement(By.cssSelector("button[class='ui teal button _2vMIFbyypA0b_pLiQmz0hV']")).click();
        waitForUITransition();
        waitUntilElementExists(calendarappointmentsInNewScheduleVisitPage());
        previousWeekInNewScheduleVisitPage().click();
        waitForUITransition();
        Assert.assertTrue("'No availability this week' message is not displayed",noAvailabilityInNewScheduleVisitPage().isDisplayed());
    }
    public void verifyPastDatesDisabledInNewScheduleVisitPage(){
        addvVisitManuallyInNewScheduleVisitPage().click();
        waitForUITransition();
        driver.findElement(By.cssSelector("button[class='ui small fluid button _3VnqII6ynYglzDU1flY9rw']")).click();
        waitForUITransition();
        String date = getSpecificDate(-1);
        String disabled = driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--disabled' and @aria-label='"+date+"']")).getAttribute("aria-disabled");
        Assert.assertTrue("Past dates are not disabled",disabled.equalsIgnoreCase("true"));
    }
    public void verifyPillsNotAvailableinReScheduleVisitPage(){
        navBar.goToCommunity();
        navBar.goToRepVisits();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(currentDateInCalendar());
        monthInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        driver.findElement(By.cssSelector("div[class='_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa']")).click();
        waitForUITransition();
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        rescheduleButtonInReScheduleVisitPage().click();
        waitForUITransition();
        reScheduleTextboxInReScheduleVisitPage().sendKeys(Keys.PAGE_UP);
        waitForUITransition();
        driver.findElement(By.xpath("//button[@class='ui tiny button _3GJIUrSQadO6hk9FZvH28D']")).click();
        waitForUITransition();
        setDate(getCurrentDate());
        previousWeekInNewScheduleVisitPage().click();
        Assert.assertTrue("verify the Message 'No availability this week'",driver.findElement(By.xpath("//table[@class='ui unstackable basic table']//span[text()='No availability this week']")).isDisplayed());
    }

    public void verifyRepvisitsSetupWizardMessagingOptions(String confirmationMessage, String specialInstructionforRepVisits) {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        if(getStartedBtn().isDisplayed()){
            getStartedBtn().click();
            waitUntilPageFinishLoading();
        }
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Messaging Options']")).size()==0) {
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        //verify UI
        Assert.assertTrue("Confirmation Message header is not showing", text("Confirmation Message").isDisplayed());
        Assert.assertTrue("Special Instruction for RepVisits header is not showing", text("Special Instruction for RepVisits").isDisplayed());
        Assert.assertTrue("Confirmation message Information Label text is not showing", getDriver().findElement(By.cssSelector("label[for='emailInstructions']")).getText().contains("When appointments are confirmed, we will automatically email the higher education institution and include appointment details as well as your contact information. If you would like to add additional information, you can do so here."));
        Assert.assertTrue("webInstructions Message text is not showing", getDriver().findElement(By.cssSelector("label[for='webInstructions']")).getText().contains("Is there anything you would like the representative to know before scheduling a visit? If so, include that information here. Please note we will display your contact information and the school's address."));
        Assert.assertTrue("Email Instructions textbox is not showing", getDriver().findElement(By.xpath("//textarea[@id='emailInstructions']")).isDisplayed());
        Assert.assertTrue("Web Instructions textbox is not showing", getDriver().findElement(By.xpath("//textarea[@id='webInstructions' and @maxlength='250']")).isDisplayed());
        Assert.assertTrue(button("Back").isDisplayed());
        Assert.assertTrue(button("Next").isDisplayed());

        if(!confirmationMessage.equals("")) {
            String actualConfirmationMessage = driver.findElement(By.xpath("//textarea[@id='emailInstructions']")).getText();
            Assert.assertTrue("'Confirmation Message' value was not as expected.", actualConfirmationMessage.contains(confirmationMessage));
        }
         if(!specialInstructionforRepVisits.equals("")) {
            String actualSpecialInstructionforRepVisits = driver.findElement(By.xpath("//textarea[@id='webInstructions' and @maxlength='250']")).getText();
            Assert.assertTrue("'Special Instruction for RepVisits' were not set as expected.", actualSpecialInstructionforRepVisits.equals(specialInstructionforRepVisits));
         }
    }



    public void accessRepvisitsSetupWizardMessagingOptions(String confirmationMessage, String specialInstructionforRepVisits, String buttonToClick){

        if(!confirmationMessage.equals("")) {
            WebElement actualConfirmationMessage = driver.findElement(By.xpath("//textarea[@id='emailInstructions']"));
            actualConfirmationMessage.sendKeys(confirmationMessage);
        }
        if(!specialInstructionforRepVisits.equals("")) {
            WebElement actualSpecialInstructionforRepVisits = driver.findElement(By.xpath("//textarea[@id='webInstructions' and @maxlength='250']"));
            actualSpecialInstructionforRepVisits.sendKeys(specialInstructionforRepVisits);
        }
        if(!buttonToClick.equals("")) {
            if (buttonToClick.equals("Back")) {
                driver.findElement(By.xpath("//button/span[text()='Back']")).click();
            } else if (buttonToClick.equals("Next")) {
                driver.findElement(By.xpath("//button/span[text()='Next']")).click();
            } else {
                fail("The given option is not a valid one");
            }
        }
    }

    public void verifyPrimaryContactVisitsPage(String buttonToClick){
        Assert.assertTrue("'Primary Contact for Visits' page is not displayed", text("Primary Contact for Visits").isDisplayed());
        if (buttonToClick.equals("Back")) {
            driver.findElement(By.xpath("//button/span[text()='Back']")).click();
        } else if (buttonToClick.equals("Next")) {
            driver.findElement(By.xpath("//button/span[text()='Next']")).click();
        } else {
            fail("The given option is not a valid one");
        }
    }

    public void verifyAvailabilitySettingsPage(String buttonToClick){
        Assert.assertTrue("'Availability Settings' page is not displayed", text("Availability Settings").isDisplayed());
        if (buttonToClick.equals("Back")) {
            driver.findElement(By.xpath("//button/span[text()='Back']")).click();
        } else if (buttonToClick.equals("Next")) {
            driver.findElement(By.xpath("//button/span[text()='Next']")).click();
        } else {
            fail("The given option is not a valid one");
        }
    }

    //locators
    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }
    private void doubleClick(WebElement elementLocator) {
        Actions actions = new Actions(driver);
        actions.doubleClick(elementLocator).perform();

    }
    private WebElement getSearchAndScheduleBtn() {
        return link("Search and Schedule");
    }

    public void clicklinkCollegeFair() {
            navBar.goToRepVisits();
            link("College Fairs").click();
    }
    public void verifyCollgeFairBlankDashBoard(){
        Assert.assertTrue("College Fairs Header is not present",getDriver().findElement(By.cssSelector("h1")).isDisplayed());
        Assert.assertTrue("Welcome to College Fairs Message is not present",text("Welcome to College Fairs").isDisplayed());
        Assert.assertTrue("College Fair Default Message is not present",text("Details about past and upcoming College Fairs you have created will appear here").isDisplayed());
        Assert.assertTrue("Add a College Fair Button is not present",getDriver().findElement(By.cssSelector(".ui.small.button.ui.primary.button")).isDisplayed());

    }

    public void SetSpecialInstructionsForHEUser( String Instructions) {
        getWebInstructions().clear();
        getWebInstructions().sendKeys(Instructions);
        button("Update Messaging").click();

    }
    private WebElement chooseDates(){
        return button(By.cssSelector("button[class='ui button _2TgAEzclbuAyQiI-jXUoxe']"));
    }
    private WebElement addBlockedTime(){
        return button(By.cssSelector("button[class='ui primary button _2r0LAIwbM94mTAqf-2YGUG']"));
    }

    public void VerifySpecialInstructionsForHE( String instructionsText){
       Assert.assertTrue("Special Instructions for RepVisits Text is not similar",getDriver().findElement(By.id("webInstructions")).getText().contains(instructionsText));
    }

    public String getSpecificDate(int addDays) {
        String DATE_FORMAT_NOW = "MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }
    public String getCurrentDate() {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }
    /*locators for Messaging Options Page*/
    private WebElement getWebInstructions() {
        return getDriver().findElement(By.id("webInstructions"));
    }

    private WebElement currentDateInCalendar()
    {
        WebElement day=driver.findElement(By.xpath("//button[@title='Today']"));
        waitUntilElementExists(day);
        return  day;
    }

    private WebElement calendarappointmentsInNewScheduleVisitPage()
    {
        WebElement calendar=driver.findElement(By.cssSelector("form[id='add-calendar-appointment']"));
        waitUntilElementExists(calendar);
        return  calendar;
    }

    private WebElement previousWeekInNewScheduleVisitPage()
    {
        WebElement button=driver.findElement(By.cssSelector("button[aria-label='Previous week']"));
        return  button;
    }

    private WebElement monthInReScheduleVisitPage()
    {

        WebElement month=driver.findElement(By.xpath("//button[@title='Month']"));
        return  month;
    }

    private WebElement rescheduleButtonInReScheduleVisitPage()
    {
        WebElement reschedule=driver.findElement(By.xpath("//button/span[text()='Reschedule']"));
        return  reschedule;
    }

    private WebElement hsNotesInReScheduleVisitPage()
    {
        WebElement notes=driver.findElement(By.xpath("//input[@name='hsNotes']"));
        return  notes;
    }

    private WebElement reScheduleTextboxInReScheduleVisitPage()
    {
        WebElement textBox= driver.findElement(By.xpath("//textarea[@id='rescheduleMessage']"));
        return textBox;
    }

    private WebElement noAvailabilityInNewScheduleVisitPage()
    {
        WebElement avialability=driver.findElement(By.xpath("//table[@class='ui unstackable basic table']//tbody//td/span[text()='No availability this week']"));
        return  avialability;
    }

    private WebElement addvVisitManuallyInNewScheduleVisitPage()
    {
        WebElement addVisit=driver.findElement(By.xpath("//div/span[text()='Want a custom time? Add it manually']"));
        return  addVisit;
    }
}
