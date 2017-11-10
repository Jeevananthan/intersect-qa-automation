package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.fail;
import static org.openqa.selenium.Keys.*;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    //Creating instance of HE.RepVisitsPageImpl
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
        tabs.add("Holidays");
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

    public void verifyContentsOfRegularWeeklyHours() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        String startEndDatesForVisitsText = getDriver().findElement(By.cssSelector("div[class='availability _17ehxT_27Cme444W6HfdTN']")).getText();
        String dayTableText = getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")).getText();
        String displayStartEndForVisitsText = "2017-2018 Start and End Dates For Visits";
        Assert.assertTrue(dayTableText + " Text is not displayed",
                startEndDatesForVisitsText.contains("MON TUE WED THU FRI"));
        Assert.assertTrue(displayStartEndForVisitsText + " Text is not displayed",
                startEndDatesForVisitsText.contains(displayStartEndForVisitsText));
        Assert.assertTrue("Button Start Date is not showing.",
                button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).isDisplayed());
        Assert.assertTrue("Button End Date is not showing.",
                button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(3)")).isDisplayed());
        Assert.assertTrue("Button Add Time Slot is not showing.",
                button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).isDisplayed());
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

        try{
            while (!DayPickerCaption.contains(month)) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        }
        catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }
    }

    public void clickOnDay(String date) {
        if (!date.contains("17")) {
            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[contains(text(), " + date + ")]")).click();
        }
        else
        {
            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("(//div[contains(text(), " + date + ")])[2]")).click();
        }
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
            fail("The Date selected it's out of RANGE.");
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

    public void setTimeZone(String timeZone){
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
        getDriver().findElement(By.xpath("//span[text()='"+ timeZone +"']")).click();
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

    public void accessCreateCollegeFair(String collegeFairName,String date,String startTime,String endTime,String RSVPDate,String cost,String maxNumberofColleges,String numberofStudentsExpected,String buttonToClick){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement fairs=link("College Fairs");
        waitUntilElementExists(fairs);
        link("College Fairs").click();
        button(By.id("add-college")).click();

        if(!collegeFairName.equals("")) {
            Assert.assertTrue("College Fair TextBox is not displayed",textbox(By.id("college-fair-name")).isDisplayed());
            textbox(By.id("college-fair-name")).sendKeys(collegeFairName);
        }
        if(!date.equals("")) {
            Assert.assertTrue("Date Textbox are not displayed",textbox(By.id("college-fair-date")).isDisplayed());
            WebElement datepicker = driver.findElement(By.xpath("//input[@id='college-fair-date']/following-sibling::i[@class='calendar large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
            datepicker.click();
            setSpecificDate(35);
        }
        if(!startTime.equals("")) {
            Assert.assertTrue("Start Time TextBox is not displayed",textbox(By.id("college-fair-start-time")).isDisplayed());
            driver.findElement(By.xpath("//form//input[@id='college-fair-start-time']")).sendKeys(startTime);
        }
        if(!endTime.equals("")) {
            Assert.assertTrue("End Time TextBox is not displayed",textbox(By.id("college-fair-end-time")).isDisplayed());
            driver.findElement(By.xpath("//form//input[@id='college-fair-end-time']")).sendKeys(endTime);
        }
        if(!RSVPDate.equals("")) {
            Assert.assertTrue("RSVP Deadline TextBox is not displayed",textbox(By.id("college-fair-rsvp-deadline")).isDisplayed());
            WebElement RSVPdatepicker = driver.findElement(By.xpath("//input[@id='college-fair-rsvp-deadline']/following-sibling::i[@class='calendar large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
            RSVPdatepicker.click();
            setSpecificDate(7);
        }
        if(!cost.equals("")) {
            Assert.assertTrue("Cost TextBox is not displayed",textbox(By.id("college-fair-cost")).isDisplayed());
            textbox(By.id("college-fair-cost")).sendKeys(cost);
        }
        if(!maxNumberofColleges.equals("")) {
            Assert.assertTrue("'Max Number of Colleges' TextBox is not displayed",textbox(By.id("college-fair-max-number-colleges")).isDisplayed());
            textbox(By.id("college-fair-max-number-colleges")).sendKeys(maxNumberofColleges);
        }
        if(!numberofStudentsExpected.equals("")) {
            Assert.assertTrue("'Number of Students Expected' TextBox is not displayed",textbox(By.id("college-fair-number-expected-students")).isDisplayed());
            textbox(By.id("college-fair-number-expected-students")).sendKeys(numberofStudentsExpected);
        }
        Assert.assertTrue("",driver.findElement(By.xpath("//input[@id='college-fair-automatic-request-confirmation-no']")).isDisplayed());
        driver.findElement(By.xpath("//input[@id='college-fair-automatic-request-confirmation-no']")).click();
        driver.findElement(By.id("college-fair-number-expected-students")).sendKeys(Keys.PAGE_DOWN);

        if(!buttonToClick.equals("")) {
            if(buttonToClick.equals("Cancel This College Fair")) {
                Assert.assertTrue("'Cancel This College Fair' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).click();
            }else if(buttonToClick.equals("Save")){
                waitUntilElementExists(save());
                Assert.assertTrue("'Save' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Save']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Save']")).click();
            }else {
                fail("The option for the button to click ="+buttonToClick+" is not a valid one");
            }
        }
    }

    public void setSpecificDate(int addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }


    public void verifySuccessMessageforCreateFair(String createFairName) {
        Assert.assertTrue("Success Message for the fair " + createFairName + " is not displayed", driver.findElement(By.xpath("//p/span[text()='"+ createFairName +"']/parent::p")).isDisplayed());
        Assert.assertTrue("'Close' button is not displayed",driver.findElement(By.cssSelector("button[class='ui basic primary button']")).isDisplayed());
    }

    public void accessSuccessMessageforFair(String buttonName){
        if(buttonName.equals("Close")){
            driver.findElement(By.cssSelector("button[class='ui basic primary button']")).click();
        }else if(buttonName.equals("Add Attendees")){
            driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
        }else{
            fail("The given option is not a valid one");
        }
    }
    public void accessCollegeFairDetailsPage(String buttonName){
        if(buttonName.equals("Edit")){
            driver.findElement(By.cssSelector("button[id='edit-college-fair']")).click();
        }else{
            fail("The given option '"+buttonName+"' is not a valid one");
        }
    }
    public void verifyEditCollegeFair(String collegeFairName,String date,String startTime,String endTime,String RSVPDate,String cost,String maxNumberofColleges,String numberofStudentsExpected){
        Assert.assertTrue("Page Title is not displayed",driver.findElement(By.xpath("//h1/span[text()='Edit College Fair']")).isDisplayed());
        Assert.assertTrue("'College Fair Details' Title is not displayed",driver.findElement(By.xpath("//h2/span[text()='College Fair Details']")).isDisplayed());
        Assert.assertTrue("'College Fair Name' Label is not displayed",text("College Fair Name").isDisplayed());
        Assert.assertTrue("'Date' Label is not displayed",driver.findElement(By.xpath("//label[text()='Date']")).isDisplayed());
        Assert.assertTrue("'Start Time' Label is not displayed",driver.findElement(By.xpath("//label[text()='Start Time']")).isDisplayed());
        Assert.assertTrue("'End Time' Label is not displayed",driver.findElement(By.xpath("//label[text()='End Time']")).isDisplayed());
        Assert.assertTrue("'RSVP Deadline' Label is not displayed",driver.findElement(By.xpath("//label[text()='RSVP Deadline']")).isDisplayed());
        Assert.assertTrue("'Cost' Label is not displayed",driver.findElement(By.xpath("//label[text()='Cost']")).isDisplayed());
        Assert.assertTrue("'Max Number of Colleges' Label is not displayed",driver.findElement(By.xpath("//label[text()='RSVP Deadline']")).isDisplayed());
        Assert.assertTrue("'Number of Students Expected' Label is not displayed",driver.findElement(By.xpath("//label[text()='Max Number of Colleges']")).isDisplayed());
        Assert.assertTrue("'Instructions for College Representatives' Label is not displayed",driver.findElement(By.xpath("//label[text()='Number of Students Expected']")).isDisplayed());
        Assert.assertTrue("'Automatically Confirm Incoming Requests From Colleges?' Label is not displayed",driver.findElement(By.xpath("//label[text()='Instructions for College Representatives']")).isDisplayed());
        Assert.assertTrue("'Settings' Label is not displayed",driver.findElement(By.xpath("//h2/span[text()='Settings']")).isDisplayed());
        Assert.assertTrue("'Email Message to Colleges After Confirmation' Label is not displayed",driver.findElement(By.xpath("//label[text()='Email Message to Colleges After Confirmation']")).isDisplayed());
        Assert.assertTrue("'Cancel This College Fair' Button is not displayed",driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
        Assert.assertTrue("'Publish/Unpublish' Button is not displayed",driver.findElement(By.cssSelector("button[class='ui basic primary button']")).isDisplayed());

        if(!collegeFairName.equals("")) {
            String currentFairName = textbox(By.id("college-fair-name")).getAttribute("value");
            Assert.assertTrue("'College Fair Name' value was not as expected.",currentFairName.equals(collegeFairName));
            driver.findElement(By.xpath("//input[@id='college-fair-name']")).sendKeys(Keys.PAGE_DOWN);
        }
        if(!date.equals("")) {
            String actualDate = driver.findElement(By.id("college-fair-date")).getAttribute("value");
            String fairDate = selectdate(35);
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if(!startTime.equals("")) {
            String currentStartTime = textbox(By.id("college-fair-start-time")).getAttribute("value");
            Assert.assertTrue("'Start Time' value was not as expected.",currentStartTime.equals(startTime));
        }
        if(!endTime.equals("")) {
            String currentEndTime = textbox(By.id("college-fair-end-time")).getAttribute("value");
            Assert.assertTrue("'End Time' value was not as expected.",currentEndTime.equals(endTime));
        }
        if(!RSVPDate.equals("")) {
            String actualDate = driver.findElement(By.id("college-fair-rsvp-deadline")).getAttribute("value");
            String fairDate = selectdate(2);
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if(!cost.equals("")) {
            String currentCost = textbox(By.id("college-fair-cost")).getAttribute("value");
            Assert.assertTrue("'Cost' value was not as expected.",currentCost.equals(cost));
        }
        if(!maxNumberofColleges.equals("")) {
            String currentMaxNumberofColleges = textbox(By.id("college-fair-max-number-colleges")).getAttribute("value");
            Assert.assertTrue("'Max Number of Colleges' value was not as expected.",currentMaxNumberofColleges.equals(maxNumberofColleges));
        }
        if(!numberofStudentsExpected.equals("")) {
            String currentNumberofStudentsExpected = textbox(By.id("college-fair-number-expected-students")).getAttribute("value");
            Assert.assertTrue("'Max Number of Colleges' value was not as expected.",currentNumberofStudentsExpected.equals(numberofStudentsExpected));
        }

    }


    public String selectdate(int addDays)
    {
        String DATE_FORMAT_NOW = "EEEE, MMM dd, yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void accessEditCollegeFair(String collegeFairName,String date,String cost,String maxNumberofColleges,String numberofStudentsExpected, String instructionsforCollegeRepresentatives,String emailMessagetoCollegesAfterConfirmation , String RSVPDate, String buttonToClick){
        if(!collegeFairName.equals("")) {
            WebElement currentCollegeFairName = textbox(By.id("college-fair-name"));
            currentCollegeFairName.clear();
            currentCollegeFairName.sendKeys(collegeFairName);
        }
        if(!date.equals("")) {
            WebElement datepicker = driver.findElement(By.xpath("//input[@id='college-fair-date']/following-sibling::i[@class='calendar large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
            datepicker.click();
            setSpecificDate(30);
        }

        if(!cost.equals("")) {
            WebElement currentCost = textbox(By.id("college-fair-cost"));
            currentCost.clear();
            currentCost.sendKeys(cost);
        }
        if(!maxNumberofColleges.equals("")) {
            WebElement currentMaxNumberofColleges = textbox(By.id("college-fair-max-number-colleges"));
            currentMaxNumberofColleges.clear();
            currentMaxNumberofColleges.sendKeys(maxNumberofColleges);
        }
        if(!numberofStudentsExpected.equals("")) {
            WebElement currentNumberofStudentsExpected = textbox(By.id("college-fair-number-expected-students"));
            currentNumberofStudentsExpected.clear();
            currentNumberofStudentsExpected.sendKeys(numberofStudentsExpected);
        }
        if(!instructionsforCollegeRepresentatives.equals("")) {
            WebElement currentInstructionsforCollegeRepresentatives = driver.findElement(By.cssSelector("textarea[id='college-fair-instructions']"));
            currentInstructionsforCollegeRepresentatives.clear();
            currentInstructionsforCollegeRepresentatives.sendKeys(instructionsforCollegeRepresentatives);
        }

        if(!emailMessagetoCollegesAfterConfirmation.equals("")) {
            WebElement currentEmailMessagetoCollegesAfterConfirmation = driver.findElement(By.cssSelector("textarea[id='college-fair-email-message-to-colleges']"));
            currentEmailMessagetoCollegesAfterConfirmation.clear();
            currentEmailMessagetoCollegesAfterConfirmation.sendKeys(emailMessagetoCollegesAfterConfirmation);
        }
        if(!buttonToClick.equals("")) {
            if(buttonToClick.equals("Cancel This College Fair")) {
                Assert.assertTrue("'Cancel This College Fair' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).click();
            }else if(buttonToClick.equals("Save")){
                Assert.assertTrue("'Save' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Save']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Save']")).click();
            }else {
                fail("The option for the button to click ="+buttonToClick+" is not a valid one");
            }
        }
    }

    public void verifySuccessMessageforEditFair(String EditFairName){
        Assert.assertTrue("Success Message for the fair " + EditFairName + " is not displayed", driver.findElement(By.xpath("//p/span[text()='"+EditFairName+"']/parent::p")).isDisplayed());
    }

    public void accessViewDetailsPageforFair(String fairNametoClickViewDetails){
        navBar.goToRepVisits();
        link("College Fairs").click();
        while (link("View More Upcoming Events").isDisplayed()){
            link("View More Upcoming Events").click();
        }
        driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[text()='QA Fair New']/parent::tr/td/a[span='View Details']")).click();
    }

    public void accessCollegeFairOverviewPage(String fairName) {
        navBar.goToRepVisits();
        link("College Fairs").click();
        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
            WebElement element=link("View More Upcoming Events");
//            waitUntilElementExists(element);
        }
        WebElement viewDetails = driver.findElement(By.xpath("//td[text()='"+fairName+"']/../td/following-sibling::td/a/span[text()='View Details']"));
        waitUntilElementExists(viewDetails);
        viewDetails.click();
    }

    private WebElement getStartedBtn(){
        return button("Get Started!");
    }
    private WebElement saveChanges()
    {
        WebElement saveChanges=button("Save Changes");
        waitUntilElementExists(saveChanges);
        return  saveChanges;
    }
    private WebElement save()
    {
        WebElement button=button("Save");
        waitUntilElementExists(button);
        return button;
    }

    //locators
    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }
}




