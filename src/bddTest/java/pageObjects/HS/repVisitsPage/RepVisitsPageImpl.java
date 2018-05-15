package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.security.Key;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import java.text.DateFormat;
import utilities.GetProperties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.fail;
import static junit.framework.TestCase.fail;
import utilities.File.CsvFileUtility;

import utilities.File.FileManager;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static String StartTime;
    public static String FairName;
    public static String generatedDateForExceptions;
    public static String ManualStartTime;

    public static String generatedDate;
    public static String generatedDateDayOfWeek;
    public static String time;

    //Creating RepVisitsPageImpl class object of for HE.
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

    public void confirmDeclineCollegeAttendanceRequest(String requestOption, String fairName){
        logger.info("Validating the functionality of the " + requestOption + " button for College Fair attendance request.");
        navBar.goToRepVisits();
        link("College Fairs").click();
        button(By.xpath("//a[@aria-label='"+fairName+"']")).click();
        boolean expectedResult = false;
        switch(requestOption) {
            case "Decline":
                button("DECLINE").click();
                if(button(By.xpath("//span[contains(text(), 'No, go back')]")).isDisplayed() && getDriver().findElement(By.xpath("//button[@class = 'ui red small disabled right floated button']")).isDisplayed()) {
                    getDriver().findElement(By.xpath("//textarea[@id='attendee-decline-message']")).sendKeys("Attendance Denied.");
                    if(button("No, go back").isDisplayed() && button("Yes, decline visit").isDisplayed()) {
                        button(By.xpath("//span[contains(text(), 'No, go back')]")).click();
                        button("DECLINE").click();
                        button("Yes, decline visit").click();
                        button("Close").click();
                        expectedResult = true;
                    }
                }
                break;
            case "Confirm":
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'CONFIRM')]")));
                button(By.xpath("//span[contains(text(), 'CONFIRM')]")).click();
                waitUntilPageFinishLoading();
                if(getDriver().findElement(By.xpath("//tr/td[contains(text(), 'Attending')]")).isDisplayed() && button(By.xpath("//span[contains(text(), 'CANCEL')]")).isDisplayed()) {
                    button("CANCEL").click();
                    getDriver().findElement(By.xpath("//textarea[@id='attendee-cancellation-message']")).sendKeys("Attendance Cancelled.");
                    button("Yes, cancel attendee").click();//-------------need to update this script here--------------
                    button("Close").click();
                    expectedResult = true;
                }

                break;
            case "default":
                expectedResult = false;
                break;
        }
        Assert.assertTrue(requestOption + " button is not working properly, or you did not choose to decline or confirm request.", expectedResult);

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

    public void verifyEmptyContactPage(){
        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed",driver.findElement(By.xpath("//h1[@class='ui header _2GIsNevIB_s082IZwcYen3']")).isDisplayed());
        Assert.assertTrue("Instruction text is not displayed",driver.findElement(By.xpath("//div[@class='sub header _240ldPuujUDvP5vNIGw15H']")).isDisplayed());
    }
    public void verifyFullContactPage(){
        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed",driver.findElement(By.xpath("//h1[@class='ui header _2GIsNevIB_s082IZwcYen3']")).isDisplayed());
        Assert.assertTrue("Instruction text is not displayed",driver.findElement(By.xpath("//div[@class='sub header _240ldPuujUDvP5vNIGw15H']")).isDisplayed());
        List<WebElement> searchedValueOfName = driver.findElements(By.className("_1ijSBYwG-OqiUP1_S7yMUN"));
        int size = searchedValueOfName.size();
        Assert.assertTrue("RepVisits contact are not displayed",size>0);
    }
    public void verifyFullorEmpty(){
        try{ if(text("Welcome to Contacts").isDisplayed())
        {
            logger.info("you have no Contacts");
        }
        else if(driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed()) {
            while (driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed()){
                driver.findElement(By.xpath("//span[text()='Show More']")).click();
            }}else{}}
        catch(Exception e){}}

    public void sortingContacts()
    {
        navBar.goToRepVisits();
        getContactsBtn().click();
        driver.findElement(By.xpath("//input[@name='contacts-search']")).clear();
        ArrayList<String> original=new ArrayList<>();
        List<WebElement> elements=driver.findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[2]/div[1]"));
        for(WebElement we:elements)
        {
            original.add(we.getText());
        }
        ArrayList<String> sortedList=new ArrayList<>();
        for(String s:original)
        {
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        int i = 0;
        for (String entry : sortedList) {
            Assert.assertTrue("Entry in sorted list doesn't match an entry in the original list. Sorted: " + entry + ", Original: " + original.get(i),entry.equalsIgnoreCase(original.get(i)));
            i++;
        }
        //Assert.assertTrue(sortedList.equals(original));
    }

    public void validatingthePaginationof25Contacts()
    {
        int count;
        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contacts is not displayed",driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).isDisplayed());
        count=driver.findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).size();
        try{ logger.info(count);}catch(Exception e){}
        if (count>=25) {
            Assert.assertTrue("There are 25 contacts, but no 'Show More' button is present!",driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed());
        }
    }

    public void verifyContactDetails(DataTable dataTable){
        navBar.goToRepVisits();
        getContactsBtn().click();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",text(repVisitsSubItem).isDisplayed());
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
    public void verifyinvalidcontact(String invalidData){
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(invalidData);
        Assert.assertTrue("the message of 'Your search did not return any contacts.' is not displayed",text("Your search did not return any contacts.").isDisplayed());
    }

    public void searchforContact(String institutionName){
        navBar.goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(institutionName);
        Assert.assertTrue("the specified schoolname is not displayed",driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[@class='five wide hidden-mobile']/div[contains(text(),'"+institutionName+"')]")).isDisplayed());
    }
    public void partialsearchforContact(String institutionName){
        navBar.goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(institutionName);
        List<WebElement> searchedValueOfinstitutionName = driver.findElements(By.xpath("//td/div[contains(text(),'"+institutionName+"')]"));
        for(int i=0;i<searchedValueOfinstitutionName.size();i++){
            String value = searchedValueOfinstitutionName.get(i).getText();
            Assert.assertTrue("Partial matching on institution name is not available",value.contains(institutionName));
        }
    }

    public void addNewTimeSlot(String day, String hourStartTime, String hourEndTime, String minuteStartTime, String minuteEndTime, String meridianStartTime, String meridianEndTime, String numVisits) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//button[@class='ui button _1RspRuP-VqMAKdEts1TBAC']")).sendKeys(Keys.PAGE_DOWN);
        button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).click();
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).build().perform();
        waitUntilElementExists(selectDay());
        selectDayForSlotTime("div[class='ui button labeled dropdown icon QhYtAi_-mVgTlz73ieZ5W']", day);
        inputStartTime(hourStartTime, minuteStartTime, meridianStartTime);
        inputEndTime(hourEndTime, minuteEndTime, meridianEndTime);
        visitsNumber(numVisits);
        waitUntilElementExists(submit());
        driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
        if(driver.findElements(By.xpath("//div[@class='ui small modal transition visible active']")).size()>0){
            selectOptionInReviewPreviouslyDeletedTimeSlotsModal
                    ("Add time slot to regular hours, and create for the dates above");
        }
    }

    /**
     *
     * Selects the given option in Review Previously Deleted Time Slots Modal
     * @param option to be selected
     */
    public void selectOptionInReviewPreviouslyDeletedTimeSlotsModal(String option){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='ui small modal transition visible active']")));
        driver.findElement(By.xpath(String.format("//label[text()='%s']",option))).click();
        button("ADD REGULAR HOURS").click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='ui small modal transition visible active']")));
    }

    public void setPreventCollegesSchedulingNewVisits(String Numberofdays){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"),1));
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(Numberofdays);
        button("Save Changes").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']"), 1));
    }
    public void setPreventCollegesCancellingorRescheduling(String DaysInAdvance){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"),1));
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(DaysInAdvance);
        button("Save Changes").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']"), 1));
    }

    public void setAcceptInVisitSchedulingToFullyBooked(String accept){
        setAcceptinAvailabilitySettings(accept, "1");
    }

    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(link("Availability & Settings"));
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"),1));
        WebElement selectAccept = getDriver().findElement(By.cssSelector("div[class='ui selection dropdown']>div"));
        waitUntilPageFinishLoading();
        selectAccept.click();
        getDriver().findElement(By.xpath("//span[text()='" + accept + "']")).click();
        if (accept.equals("a maximum of...")) {
            WebElement visitsBox = getDriver().findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']"));
            visitsBox.clear();
            visitsBox.sendKeys(visitsPerDay);
        }
        button("Save Changes").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']"), 1));
    }
    public void setVisitsConfirmations(String option){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement element=link("Availability & Settings");
        waitUntilElementExists(element);
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Availability").click();
        waitUntilPageFinishLoading();
        link("Availability Settings").click();
        waitUntilElementExists(saveChanges());
        WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
        options.findElement(By.xpath("div/label[text()[contains(., '"+ option +"')]]")).click();
        button("Save Changes").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']"), 1));
    }

    public void accessVisitAvailability(String visitAvailability){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Availability Settings").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOf(saveChanges()));
        waitUntilElementExists(driver.findElement(By.cssSelector("button[class='ui primary button']")));
        waitUntilElementExists(driver.findElement(By.xpath("//label[text()='"+visitAvailability+"']/input[@type='radio']")));
        driver.findElement(By.xpath("//label[text()='"+visitAvailability+"']/input[@type='radio']")).click();
        driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']"), 1));
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


    public void addDefaultMessage(String message){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Messaging Options").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.cssSelector("textarea[name='emailInstructions']")));
        writeInConfirmationMessage().clear();
        writeInConfirmationMessage().sendKeys(message);
        waitUntilPageFinishLoading();
        writeInSpecialInstructionForRepViists(message);
        button("Update Messaging").click();
    }

    public void verifyMessageUpdated(String message){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Messaging Options").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.cssSelector("textarea[name='emailInstructions']")));
        String confirmationMessageText = writeInConfirmationMessage().getText();
        Assert.assertTrue(confirmationMessageText + " Text is not displayed",
                confirmationMessageText.contains(message));
    }

    public void verifyMessageConfirmation(){
        Assert.assertTrue("The update message was not displayed",text("You've updated your messaging.")
                .isDisplayed());
    }

    public void verifyContentsOfNavianceSettings() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Naviance Settings").click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(getDriver().findElement(By.id("form-naviance-settings")));
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
        Assert.assertTrue("Button Save Changes is not showing.", button(By.cssSelector("button[class='ui primary button _2HLJ9vb6A1xl1gXrUdQ0kd']")).isDisplayed());

    }

    public void verifyTimeSlotAdded(String hourStartTime, String minuteStartTime, String meridianStartTime) {

        Assert.assertTrue("The Time Slot was not added" , driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']")).findElement(By.xpath("//button[contains(text(), '"+hourStartTime+ ":"+ minuteStartTime + meridianStartTime +"')]")).isDisplayed());

    }

    public Boolean compareDate(String month)  {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MMM yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        dateCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

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

            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[@aria-disabled='false'][text()="+date+"]")).click();

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    public void verifyContentsOfRegularWeeklyHours() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        String startEndDatesForVisitsText = getDriver().findElement(By.cssSelector("div[class='availability']")).getText();
        //String dayTableText = getDriver().findElement(By.cssSelector("table[class='ui basic table']")).getText();
        String dayTableText = getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")).getText();
        String displayStartEndForVisitsText = "Start and End Dates For Visits";
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
        inputStartTime.sendKeys(hour + ":");
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);

    }

    public void inputEndTime(String hour, String minute, String meridian)
    {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='endTime']"));
        inputStartTime.click();
        inputStartTime.sendKeys(hour + ":");
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);
        inputStartTime.sendKeys(Keys.TAB);
    }

    public void visitsNumber(String numVisits) {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='numVisits']"));
        inputStartTime.clear();
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
        setDefaultDateforStartAndEndDate();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        if (endDate.length() < 3) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd YYYY");
            endDate = sdf.format(getDeltaDate(Integer.parseInt(endDate)).getTime());
        }
        setDateFixed(endDate, "End");
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        if (startDate.length() < 3) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd YYYY");
            startDate = sdf.format(getDeltaDate(Integer.parseInt(startDate)).getTime());
        }
        setDateFixed(startDate, "Start");
        waitUntilElementExists(text("UPDATE DATE"));
        text("UPDATE DATE").click();
    }

    public void setAvailabilityToFullSchoolYear() {
        setStartAndEndDates(GetProperties.get("repvisits.schoolyear.startdate"),GetProperties.get("repvisits.schoolyear.enddate"));
    }

    public void setStartandEndDates(String startDate,String endDate) {
        setDefaultDateforStartAndEndDate();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Availability").click();
        waitUntilPageFinishLoading();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        String EndDate = getSpecificDate(endDate);
        setDate(EndDate, "End");
        String StartDate = getSpecificDate(startDate);
        setDate(StartDate, "Start");
    }

    public String getSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void setDateDoubleClick(String inputDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        WebElement Date = driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+parts[1]+"]"));
        doubleClick(Date);
        jsClick(Date);
        waitUntilPageFinishLoading();
    }

    public void accessWelcomeSetupWizard(String optionToSelect) {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//label/input[@value='VISITS']"),1));
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

    public void accessOneLastStepSetupWizard(String visitAvailability) {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@value='VISITS_AND_FAIRS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Complete!']")).size() == 0) {
            waitUntilElementExists( button("Next"));
            waitUntilElementExists( button("Next"));
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
        waitUntilPageFinishLoading();
        waitUntilElementExists(text("Today"));
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
        String startYear = partsStartDate[2];
        String endMonth = partsEndDate[0].substring(0, 3);
        String endDay = partsEndDate[1];
        String endYear = partsEndDate[2];

        try {

            Assert.assertTrue("Button Start Date is not showing.",
                    driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).
                            findElement(By.xpath("//span[contains(text(), '" + startDay + "/" + startYear + "')]")).isDisplayed());

            Assert.assertTrue("Button End Date is not showing.",
                    driver.findElement(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).
                            findElement(By.xpath("//span[contains(text(), '" + endDay + "/" + endYear + "')]")).isDisplayed());

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    private int getMonthNumber(String monthName) {
        Date date = null;
        try {
            date = new SimpleDateFormat("MMMM").parse(monthName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public void verifyTimeZonePage(String ValueTZ) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Time Zone").click();
        Assert.assertTrue("Tell us about your high school text is not present",getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present",getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.",TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match",getDriver().findElement(By.cssSelector(".search[class=\"search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed",getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());
    }

    public void verifyManualBlockedHolidays(String holiday) {
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[title='"+holiday+"']")));
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

    public void searchforSchool(String school,String location, String Date) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getSearchAndScheduleBtn().click();
        driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).sendKeys(school);
        driver.findElement(By.xpath("//button[contains(@class,'ui button')]")).click();
        waitForUITransition();
        waitUntilElementExists(driver.findElement(By.xpath("//td[text()='"+location+"']")));
        Assert.assertTrue("location is not displayed",driver.findElement(By.xpath("//td[text()='"+location+"']")).isDisplayed());
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
        waitForUITransition();
        driver.findElement(By.className("_135QG0V-mOkCAZD0s14PUf")).findElement(By.xpath("button")).click();
        setDateFixed(Date, "Start");
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(getDriver().findElement(By.className("JIilVAK-W5DJoBrTmFeUG")));
        Assert.assertTrue("Was not set Blocked!", getParent(getDriver().findElement(By.className("JIilVAK-W5DJoBrTmFeUG"))).findElement(By.tagName("p")).getText().toLowerCase().contains("holiday"));

    }

    public void setDate(String inputDate, String startOrEndDate){

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        if (startOrEndDate.contains("Start")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("End")) {
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
            findMonth(calendarHeading, startOrEndDate);
        }else if(startOrEndDate.contains("other")){
            button(By.cssSelector("button[class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']")).click();
            findMonth(calendarHeading);}
        else {
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
            findMonth(calendarHeading);
        }
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();}

    public void setDateFixed(String inputDate, String startOrEndDate){

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

//        if (startOrEndDate.contains("Start")) {
//            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
//        } else {
//            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
//        }
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
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
        waitUntilElementExists(driver.findElement(By.xpath("//label[text()='Visits and Fairs']/input[@type='radio']")));
        driver.findElement(By.xpath("//label[text()='Visits and Fairs']/input[@type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Calendar Sync']")).size()==0) {
            waitUntilPageFinishLoading();
            waitForUITransition();
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
        waitForUITransition();
        waitUntilPageFinishLoading();
        WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[class=\"search\"] + div"));
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

    public void cancelFair(String fairName, String cancelationReason) {
        navBar.goToRepVisits();
        collegeFairsButton().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        if (fairName.equalsIgnoreCase("PreviouslySetFair"))
            fairName = FairName;
        while (getDriver().findElements(By.xpath("//div[@class='_1743W0qaWdOtlS0jkveD7o'][1]/table/tbody/tr/td" +
                "[text()='" + fairName + "']/following-sibling::td[4]/a/span")).size() < 1) {
            viewMoreUpcomingEventsLink().click();
        }
        fairElementDetails(fairName).click();
        editFairButton().click();
        waitUntil(ExpectedConditions.visibilityOf(cancelThisCollegeFair()));
        cancelThisCollegeFair().click();
        if (driver.findElements(By.id(cancelMessageTextBoxLocator())).size() > 0) {
            driver.findElement(By.id(cancelMessageTextBoxLocator())).sendKeys(cancelationReason);
            cancelFairButton().click();
        } else {
            cancelFairNoAutoApprovals().click();
        }
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(closeButton()));
    }

    public void createFair(DataTable details) {
        List<List<String>> fairDetails = details.asLists(String.class);
        navBar.goToRepVisits();
        collegeFairsButton().click();
        addFairButton().click();
        fairNameTextBox().sendKeys(fairDetails.get(0).get(1));
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(dateCalendarIcon()));
        dateCalendarIcon().click();
        String fairDay;
        String fairMonth;

        if(fairDetails.get(1).get(1).contains("In")&& fairDetails.get(1).get(1).contains("day")){
            int days = Integer.parseInt(fairDetails.get(1).get(1).replaceAll("[^0-9]",""));
            fairDay = getRelativeDate(days).split(" ")[0];
            fairMonth = getRelativeDate(days).split(" ")[1];
        } else{
            fairDay = fairDetails.get(1).get(1).split(" ")[1];
            fairMonth = fairDetails.get(1).get(1).split(" ")[0];
        }
        repVisitsPageHEObj.pressMiniCalendarArrowUntil("right", fairMonth, 10);
        repVisitsPageHEObj.miniCalendarDayCell(fairDay).click();
        startTimeTextBox().sendKeys(fairDetails.get(2).get(1).replace(" ", ""));
        endTimeTextBox().sendKeys(fairDetails.get(3).get(1).replace(" ", ""));
        rsvpCalendarIcon().click();
        String rsvpDay;
        String rsvpMonth;
        if(fairDetails.get(4).get(1).contains("In")&& fairDetails.get(4).get(1).contains("day")){
            int days = Integer.parseInt(fairDetails.get(4).get(1).replaceAll("[^0-9]",""));
            rsvpDay = getRelativeDate(days).split(" ")[0];
            rsvpMonth = getRelativeDate(days).split(" ")[1];
        } else{
            rsvpDay = fairDetails.get(4).get(1).split(" ")[1];
            rsvpMonth = fairDetails.get(4).get(1).split(" ")[0];
        }
        repVisitsPageHEObj.pressMiniCalendarArrowUntil("right", rsvpMonth, 10);
        repVisitsPageHEObj.miniCalendarDayCell(rsvpDay).click();
        costTextBox().sendKeys(fairDetails.get(5).get(1));
        maxNumOfColleges().sendKeys(fairDetails.get(6).get(1));
        numOfStudents().sendKeys(fairDetails.get(7).get(1));
        driver.findElement(By.cssSelector("a.menu-link.active")).sendKeys(Keys.PAGE_DOWN);
        if (fairDetails.get(8).get(1).equals("Yes")) {
            autoApprovalYesRadButton().sendKeys(Keys.RETURN);
        } else if (fairDetails.get(8).get(1).equals("No")) {
            autoApprovalNoRadButton().sendKeys(Keys.RETURN);
        }
        driver.findElement(By.cssSelector("a.menu-link.active")).sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.elementToBeClickable(closeButton()));
        closeButton().click();
        waitUntilPageFinishLoading();
    }

    private String getRelativeDate(int addDays){
        String day;
        Calendar relativeDate = getDeltaDate(addDays);
        day =getDay(relativeDate);
        if(day.startsWith("0")){
            day = day.replace("0","");
        }
        return day+" "+getMonth(relativeDate)+" "+getYear(relativeDate);
    }

    public void removeTimeSlotAdded(String hourStartTime, String minuteStartTime, String meridianStartTime) {

        WebElement tableBody = driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']>tbody"));
        List<WebElement> tableRows = tableBody.findElements(By.cssSelector("i[class='trash outline icon _26AZia1UzBMUnJh9vMujjF']"));
        for (WebElement deleteTimeSlot : tableRows) {
            //Click on REMOVE button
                waitUntilPageFinishLoading();
                waitUntilElementExists(driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']>tbody>tr>td>div")));
                if (driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']>tbody>tr>td>div")).findElement(By.xpath("//button[contains(text(), '" + hourStartTime + ":" + minuteStartTime + meridianStartTime + "')]")).isDisplayed()) {
                    waitUntilPageFinishLoading();
                    deleteTimeSlot.click();
                    waitUntilElementExists(driver.findElement(By.cssSelector("button[class='ui primary button']>span")));
                    driver.findElement(By.cssSelector("button[class='ui primary button']>span")).click();
                    waitUntilPageFinishLoading();
                    break;
                }
        }
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
        waitForUITransition();
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
        waitForUITransition();
        driver.findElement(By.xpath("//label[text()='Visits and Fairs']/input[@type='radio']")).click();
        button("Next").click();
        waitUntilPageFinishLoading();
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
        waitUntilPageFinishLoading();
        button("Back").click();
        //driver.navigate().back();

        String actualTimeZoneWhenNextButtonClicked = driver.findElement(By.xpath("//div[@class='ui search selection dropdown']//div[@class='text']")).getText();
        Assert.assertTrue("'Timezone is not saved when click on Next button'", actualTimeZoneWhenNextButtonClicked.contains(timeZoneToSet));
        setTimeZoneValue(newTimeZone);
        button("Next").click();
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

    public void verifyTimeSlotRemoved(String hourStartTime, String minuteStartTime, String meridianStartTime) {

        try {
            WebElement tableBody = driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']>tbody"));
            List<WebElement> tableRows = tableBody.findElements(By.cssSelector("i[class='trash outline icon _26AZia1UzBMUnJh9vMujjF']"));
            for (WebElement deleteTimeSlot : tableRows) {
                waitUntilPageFinishLoading();
                waitUntilElementExists(driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']>tbody>tr>td>div")));
                if (deleteTimeSlot.getText().toLowerCase().contains(hourStartTime + ":" + minuteStartTime + meridianStartTime)) {
                    Assert.fail("The time slot was not removed");
                    break;
            }else{
                    Assert.assertTrue("The Time Slot was removed", true );
                }
            }

        } catch (Exception e) {
            Assert.fail("Time Slot was not removed.");
        }
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

    /**
     * Sets the date in a RepVisits date picker to the date 'addDays' from the date of execution.
     * @param addDays integer representing the number of days from now (positive or negative) to select.
     *                i.e.:  This runs on Jan 1st and you pass 4, the date selected will be Jan 5th
     */
    public void setRelativeDate(int addDays) {
        Calendar relativeDate = getDeltaDate(addDays);
        String calendarHeading = getMonth(relativeDate) + " " + getYear(relativeDate);
        findMonth(calendarHeading);
        clickOnDay(getDay(relativeDate));
        waitUntilPageFinishLoading();
    }


    public void verifySuccessMessageforCreateFair(String createFairName) {
        Assert.assertTrue("Success Message for the fair " + createFairName + " is not displayed", driver.findElement(By.xpath("//p/span[text()='"+ FairName +"']/parent::p")).isDisplayed());
        Assert.assertTrue("'Close' button is not displayed",driver.findElement(By.cssSelector("button[class='ui basic primary button']")).isDisplayed());
    }

    public void accessSuccessMessageforFair(String buttonName){
        if(buttonName.equals("Close")){
            driver.findElement(By.cssSelector("button[class='ui basic primary button']")).click();
        }else if(buttonName.equals("Add Attendees")){
            driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
        }else{
            Assert.fail("The given option is not a valid one");
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
            setRelativeDate(30);
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
                Assert.fail("The option for the button to click ="+buttonToClick+" is not a valid one");
            }
        }
    }

    public void verifySuccessMessageforEditFair(String EditFairName){
        Assert.assertTrue("Success Message for the fair " + EditFairName + " is not displayed", driver.findElement(By.xpath("//p/span[text()='"+EditFairName+"']/parent::p")).isDisplayed());
    }

    public void accessViewDetailsPageforFair(String fairNametoClickViewDetails){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("College Fairs").click();
        waitUntilPageFinishLoading();
        while (link("View More Upcoming Events").isDisplayed()){
            link("View More Upcoming Events").click();
            waitForUITransition();
        }
        driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[text()='"+FairName+"']/parent::tr/td/a[span='View Details']")).click();

    }

    public void accessCollegeFairOverviewPage(String fairName) {
        navBar.goToRepVisits();
        link("College Fairs").click();
        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
            WebElement element=link("View More Upcoming Events");
//            waitUntilElementExists(element);
        }
        // If we're using a previously set Dynamic fair name, look for that instead.
        if (fairName.equalsIgnoreCase("PreviouslySetFair"))
            fairName = FairName;
        WebElement viewDetails = driver.findElement(By.xpath("//td[text()='"+fairName+"']/../td/following-sibling::td/a/span[text()='View Details']"));
        waitUntilElementExists(viewDetails);
        viewDetails.click();
    }

    public void findMonth(String month) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month);

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

    public void primaryContactDetailsforVisitsAndFairs() {
        while (getRepVisitsPrimaryContactPhoneNumerField().getAttribute("value").length() > 0)
            getRepVisitsPrimaryContactPhoneNumerField().sendKeys(Keys.BACK_SPACE);
        waitUntilElementExists(button("Next"));
        button("Next").click();
        Assert.assertTrue("Phone number is a required field, but the error message was not displayed.",driver.findElement(By.xpath("//span[contains(text(),'Please enter a phone number. Ex: (555) 555-5555')]")).isDisplayed());
        getRepVisitsPrimaryContactPhoneNumerField().sendKeys("1234567890");
        waitUntilElementExists(button("Next"));
        button("Next").click();
    }

    public void navigateToVisitsAndFairsWizard() {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();

        Assert.assertTrue("welcome wizard is not displayed", text("Tell us about your High School").isDisplayed());
        driver.findElement(By.xpath("//input[@value='VISITS_AND_FAIRS']")).click();

        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Notifications & Primary Contact']")).size()==0) {
            waitUntilElementExists(button("Next"));
            button("Next").click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("Primary Contact detail is not displayed", text("Primary Contact for Visits").isDisplayed());
        Assert.assertTrue("Primary Contact Phone Number is not displayed", text("Primary Contact Phone Number").isDisplayed());
        Assert.assertTrue("Primary Contact Phone Number TextBox is not displayed", driver.findElement(By.cssSelector("input[id='notification_contacts_primary_contact_phone']")).isDisplayed());
    }

    public void navigateToAvailabilityAndSettings() {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//span[text()='Availability & Settings']")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Availability is not displayed", text("Availability").isDisplayed());
        driver.findElement(By.xpath("//span[text()='Notifications & Primary Contact']")).click();
        waitUntilPageFinishLoading();
    }

    public void navigateToCollegeFairSettings() {
        navBar.goToRepVisits();
        driver.findElement(By.xpath("//span[text()='College Fairs']")).click();
        Assert.assertTrue("College Fairs is not displayed", text("Add a College Fair").isDisplayed());
        driver.findElement(By.cssSelector("a[href='rep-visits/collegefairs/settings']>span")).click();
        waitUntilPageFinishLoading();
    }

    public void primaryContactDetailsforFairs() {
        while (getCollegeFairsPrimaryContactPhoneNumberField().getAttribute("value").length() > 0)
            getCollegeFairsPrimaryContactPhoneNumberField().sendKeys(Keys.BACK_SPACE);
        button("Save Settings").click();
        Assert.assertTrue("Phone number is a required field, but the error message was not displayed.",driver.findElement(By.xpath("//span[contains(text(),'Please enter a phone number. Ex: (555) 555-5555')]")).isDisplayed());
        getCollegeFairsPrimaryContactPhoneNumberField().sendKeys("1234567890");
        button("Save Settings").click();
    }

    public void primaryContactDetailsinAvailabilityandSettings() {
        while (getRepVisitsPrimaryContactPhoneNumerField().getAttribute("value").length() > 0)
            getRepVisitsPrimaryContactPhoneNumerField().sendKeys(Keys.BACK_SPACE);
        button("Save changes").click();
        Assert.assertTrue("Phone number is a required field, but the error message was not displayed.",driver.findElement(By.xpath("//span[text()='Please enter a phone number. Ex: (555) 555-5555']")).isDisplayed());
        getRepVisitsPrimaryContactPhoneNumerField().sendKeys("1234567890");
        button("Save changes").click();
    }


    private WebElement getStartedBtn() {
        return button("Get Started!");
    }

    private WebElement save()
    {
        WebElement button=button("Save");
        waitUntilElementExists(button);
        return button;
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

    public void addTimeSlotInRegularWeeklyHours(String day,String startTime,String endTime,String noOfVisits) {

        Assert.assertTrue("Regular Weekly Hours is not displayed", text("Regular Weekly Hours").isDisplayed());

        driver.findElement(By.xpath("//button/span[text()='ADD TIME SLOT']")).click();

        WebElement dropdown = getParent(text("Monday - Friday"));
        doubleClick(dropdown);
        driver.findElement(By.xpath("//span[text()='" + day + "']")).click();

        driver.findElement(By.xpath("//input[@name='startTime']")).sendKeys(startTime);
        driver.findElement(By.xpath("//input[@name='endTime']")).sendKeys(endTime);

        driver.findElement(By.xpath("//input[@title='numVisits']")).sendKeys(noOfVisits);

        button("ADD TIME SLOT").click();

        button("Back").click();
        Assert.assertTrue("High school information is not displayed", text("Tell us about your high school.").isDisplayed());
    }

    public void openExceptions() {
        navBar.goToRepVisits();
        availabilityAndSettingsButton().click();
        innerExceptionsButton().click();
    }

    public void selectDateInExceptions(String date) {
        chooseADateButton().click();
        repVisitsPageHEObj.pressMiniCalendarArrowUntil("right", date.split(",")[0], 10);
        repVisitsPageHEObj.miniCalendarDayCell(date.split(",")[1].split(" ")[1]).click();
    }

    public void addTimeSlot(DataTable timeSlotData) {
        List<List<String>> slotData = timeSlotData.asLists(String.class);
        newTimeSlotStartTime().sendKeys(slotData.get(0).get(1));
        newTimeSlotEndTime().sendKeys(slotData.get(1).get(1));
        newTimeSlotVisits().sendKeys(slotData.get(2).get(1));
        addTimeSlotButton().click();
    }

    public void verifyTimeSlot(String date, String startTime) {
        Assert.assertTrue("The time slot was not added", timeSlot(date, startTime).isDisplayed());
    }

    public void deleteTimeSlot(String date, String startTime) {
        timeSlotDeleteIcon(date, startTime).click();
        removeSlotConfirmButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("button.ui.loading.primary.button"), 1));
    }

    public void verifyAbsenceOfTimeSlot(String date, String startTime) {
        Assert.assertTrue("The time slot was not removed", getDriver().findElements(By.xpath("//span[text()='" + date + "']/../../../../following-sibling::tbody/tr/td/div/button[text()='" + startTime + "']")).size() < 1);
    }

    public void clearDay() {
        removeOpenings().click();
        removeSlotConfirmButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("button.ui.loading.primary.button"), 1));
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
        waitUntilElementExists(driver.findElement(By.xpath("//button[@title='"+changeWeek+"']")));
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
        setDateFixed(blockdate, "End");
        setDateDoubleClick(blockdate);
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        selectReason.click();
        waitUntilPageFinishLoading();
        Actions action = new Actions(getDriver());
        WebElement element = driver.findElement(By.xpath("//span[text()='"+reason+"']"));
        action.click(element).build().perform();
        addBlockedTime().click();
    }

    public void RemoveBlockedDate(String startDate, String endDate){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        WebElement BlockedDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='"+startDate+"']/../following-sibling::td[@class='_1DmNQ0_pLQlqak2JJluwxn']/span"));
        BlockedDate.click();
    }

    private List<WebElement> getTables() {
        waitUntilPageFinishLoading();
        //Get the table body
        List<WebElement> tablesCollection = getDriver().findElements(By.cssSelector("div[class='igoATb7tmfPWBM8CX8CkN']>table"));
        //Get the table rows

        return tablesCollection;
    }

    public void findAndVerifyAppointments(String appointmentsStatus, String color) {
        waitUntilPageFinishLoading();
        List<WebElement> tables = getTables();

        for (WebElement row : tables) {
            String appointmentInfo = row.getText();

            try {
                boolean containsAppointmentStatus = appointmentInfo.contains(appointmentsStatus);
                if (containsAppointmentStatus && appointmentsStatus.contains("Max visits met")) {
                    String colorString = driver.findElement(By.cssSelector("table[class='ui collapsing unstackable basic table _21jBvV68Sef1d0Un31tmtH _3S4SmOSH8T6zHU5ljMEg-I']")).getCssValue("background-color");
                    Assert.assertTrue((colorString.equals(color)));
                    String pillColorString = driver.findElement(By.cssSelector("button[class='ui small button _2-OMVouKECaUV5WyAtvs-Z']")).getCssValue("background-color");
                    Assert.assertTrue((pillColorString.equals("rgba(255, 255, 255, 1)")));
                } else {
                    if (containsAppointmentStatus) {
                        String colorString = driver.findElement(By.cssSelector("td[class='three wide _3zhio8osKNifGJpvKFwCYC cv3lYT7gz-yQ--WLrZI0A']")).getCssValue("background-color");
                        Assert.assertTrue((colorString.equals(color)));
                    }

                }
            } catch (Exception e) {
                logger.info("Exception while retrieving Appointment Statuses: " + e.getMessage());
                e.printStackTrace();
                Assert.fail("The Appointment Status and color was not recovered for verification.");
            }
        }
    }

    public void verifyPillsColorDays(String appointmentsStatus, String color){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Exceptions").click();
        waitUntilPageFinishLoading();
        findAndVerifyAppointments(appointmentsStatus, color);
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
        String ActualStartDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr//td["+columnID+"]")).getText();
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
            for(int colNum=0;colNum<colCount;colNum++)
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

    public void navaigateToAccountSettings(String accountSettings)
    {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        userDropdown().click();
        Assert.assertTrue("AccountSettings is not displayed",accountSettings(accountSettings).isDisplayed());
        accountSettings(accountSettings).click();
        waitUntilPageFinishLoading();
    }

    public void verifyAccountsettings(String leftSubMenuInaccountSettings) {
            Assert.assertTrue("Tab " + leftSubMenuInaccountSettings + " is not displaying as expected!",driver.findElement(By.xpath("//a/span[text()='"+leftSubMenuInaccountSettings+"']")).isDisplayed());
    }

    public void verifyPasswordFields(String AccountInformationPage,String firstName,String lastName,String eMail,DataTable dataTable) {
        String details[] = AccountInformationPage.split(",");
        for(int i=0;i<details.length-1;i++) {
            Assert.assertTrue(details[i] + " is not showing.", text(details[i]).isDisplayed());}
        currentPasswordInput().sendKeys(Keys.PAGE_DOWN);
        List<String> list = dataTable.asList(String.class);
        for (String passwordCriteria : list) {
            Assert.assertTrue(passwordCriteria + " is not showing.",text(passwordCriteria).isDisplayed());
        }
        String firstname=firstNameTextbox().getAttribute("value");
        Assert.assertTrue("FirstName is not displayed",firstname.equals(firstName));
        String lastname=lastNameTextbox().getAttribute("value");
        Assert.assertTrue("LastName is not displayed",lastname.equals(lastName));
        String email=eMailTextbox().getAttribute("value");
        Assert.assertTrue("Email is not displayed",email.equals(eMail));
    }

    public void validatePassword(String userType,String oldPassword,String minimum8character,String lowercaseletter,String uppercaseletter,String withoutNumber,String withoutspecialcharacter) {
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(oldPassword);
        //verify the password policy of minimum of 8 characters
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        //verify the password policy of minimum of 8 characters
        newPasswordInput().sendKeys(minimum8character);
        confirmPasswordInput().sendKeys(minimum8character);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of lowercase letter
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(lowercaseletter);
        confirmPasswordInput().sendKeys(lowercaseletter);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of uppercase letter
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(uppercaseletter);
        confirmPasswordInput().sendKeys(uppercaseletter);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of without the number
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(withoutNumber);
        confirmPasswordInput().sendKeys(withoutNumber);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of without the Special Characters
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(withoutspecialcharacter);
        confirmPasswordInput().sendKeys(withoutspecialcharacter);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password accepted with the password policy
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(GetProperties.get("hs."+ userType + ".password"));
        confirmPasswordInput().sendKeys(GetProperties.get("hs."+ userType + ".password"));
        saveButton().click();
        waitForUITransition();
        List<WebElement> list=driver.findElements(By.xpath("//div[@class='ui negative message']/div/span"));
        if(list.size()==1) {
            logger.info("Error Message is displayed");
            TestCase.fail();
        }
        waitUntilPageFinishLoading();
    }

        public void resetPassword(String oldPassword,String newPassword)
        {
            currentPasswordInput().clear();
            currentPasswordInput().sendKeys(oldPassword);
            newPasswordInput().sendKeys(newPassword);
            confirmPasswordInput().sendKeys(newPassword);
            saveButton().click();
            waitForUITransition();
            List<WebElement> list=driver.findElements(By.xpath("//div[@class='ui negative message']/div/span"));
            if(list.size()==1) {
                logger.info("Error Message is displayed");
                TestCase.fail();
            }
        }

        public void verifySuccessMessageinAccountSettingsPage(String message){
        String SuccessMessage = driver.findElement(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/span")).getText();
        Assert.assertTrue("Success message is not displayed",message.equals(SuccessMessage));
        }


    public void naviagateToAvailbilityandSettings()
    {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        navianceSettings().click();
    }

    public void verifyNavianceSuccessMessage(){
      saveSettings().click();
      waitUntilPageFinishLoading();
      String successMessage="You've updated Naviance settings.";
      String actualSuccessMessage=driver.findElement(By.xpath("//span[text()='Great!']/following-sibling::span")).getText();
      waitUntilPageFinishLoading();
      Assert.assertTrue("Success Message is not displayed",successMessage.equals(actualSuccessMessage));
    }

    public void verifyNotificationAndPrimaryContactInSetupWizard(String primaryUser,String changeNewUser){


        String userTochange;

        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Notifications & Primary Contact']")).size()==0) {
            waitUntilElementExists(button("Next"));
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
        link("Availability & Settings").click();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(currentDateInCalendar());
        driver.findElement(By.cssSelector("button[class='ui teal button _2vMIFbyypA0b_pLiQmz0hV']")).click();
        waitForUITransition();
    }
    public void verifyPastDatesDisabledInNewScheduleVisitPage(){
        addvVisitManuallyInNewScheduleVisitPage().click();
        waitForUITransition();
        driver.findElement(By.cssSelector("button[class='ui small fluid button _3VnqII6ynYglzDU1flY9rw']")).click();
        waitForUITransition();
        String date = getSpecificDate(-1, null);
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
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        rescheduleButtonInReScheduleVisitPage().click();
        waitForUITransition();
        reScheduleTextboxInReScheduleVisitPage().sendKeys(Keys.PAGE_UP);
        waitForUITransition();
        driver.findElement(By.xpath("//button[@class='ui tiny button _3GJIUrSQadO6hk9FZvH28D']")).click();
        waitForUITransition();
        setDateFixed(getCurrentDate(), getCurrentDate());
//        previousWeekInNewScheduleVisitPage().click();
        Assert.assertTrue("verify the Message 'No availability this week'",driver.findElement(By.xpath("//table[@class='ui unstackable basic table']//span[text()='No availability this week']")).isDisplayed());
    }

    public void selectGeneratedDateInExceptions(String daysFromNow) {
        Calendar calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow));
        if(getDayOfWeek(calendarStartDate).equals("Saturday")) {
            calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow) - 1);
        } else if(getDayOfWeek(calendarStartDate).equals("Sunday")) {
            calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow) + 1);
        }

        generatedDateForExceptions = getMonthNumber(calendarStartDate) + "/" + getDay(calendarStartDate) + "/"
                + getYear(calendarStartDate).substring(2);

        chooseADateButton().click();
        pickDateInDatePicker(calendarStartDate);
    }

    public void verifyTimeSlotWithGeneratedDate(String time) {
        Assert.assertTrue("The time slot was not added", timeSlot(generatedDateForExceptions, time).isDisplayed());
    }

    public void deleteTimeSlotWithGeneratedDate(String time) {
        timeSlotDeleteIcon(generatedDateForExceptions, time).click();
        removeSlotConfirmButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("button.ui.loading.primary.button"), 1));
    }

    public void verifyAbsenceOfTimeSlotWithGeneratedDate(String time) {
        Assert.assertTrue("The time slot was not removed", getDriver().findElements(By.xpath
                ("//span[text()='" + generatedDateForExceptions + "']/../../../../following-sibling::tbody/tr/td/div/" +
                        "button[text()='" + time + "']")).size() < 1);
    }

    public void verifyRepvisitsSetupWizardMessagingOptions(String confirmationMessage, String specialInstructionforRepVisits) {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        if(getStartedBtn().isDisplayed()){
            getStartedBtn().click();
            waitUntilPageFinishLoading();
        }
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Messaging Options']")).size()==0) {
            if (driver.findElements(By.xpath("//span[contains(text(),'Availability Settings')]")).size() == 1) {
                if (getParent(driver.findElement(By.xpath("//span[contains(text(),'Availability Settings')]"))).getAttribute("class").equalsIgnoreCase("_1gXbsnbxcvr12eMqyC1xjb")) {
                    button("Next").click();
                    // The Messaging Options page takes a really long time to load, for some reason, so we need to wait.
                    waitForUITransition();
                    waitForUITransition();
                } else {
                    button("Next").click();
                    waitUntilPageFinishLoading();
                }
            } else {
                button("Next").click();
                waitUntilPageFinishLoading();
            }
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
            actualConfirmationMessage.clear();
            actualConfirmationMessage.sendKeys(confirmationMessage);
        }
        if(!specialInstructionforRepVisits.equals("")) {
            WebElement actualSpecialInstructionforRepVisits = driver.findElement(By.xpath("//textarea[@id='webInstructions' and @maxlength='250']"));
            actualSpecialInstructionforRepVisits.clear();
            actualSpecialInstructionforRepVisits.sendKeys(specialInstructionforRepVisits);
        }
        if(!buttonToClick.equals("")) {
            if (buttonToClick.equals("Back")) {
                driver.findElement(By.xpath("//button/span[text()='Back']")).click();
            } else if (buttonToClick.equals("Next")) {
                driver.findElement(By.xpath("//button/span[text()='Next']")).click();
            } else {
                Assert.fail("The given option is not a valid one");
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
            Assert.fail("The given option is not a valid one");
        }
    }

    public void verifyAvailabilitySettingsPage(String buttonToClick){
        Assert.assertTrue("'Availability Settings' page is not displayed", text("Availability Settings").isDisplayed());
        if (buttonToClick.equals("Back")) {
            driver.findElement(By.xpath("//button/span[text()='Back']")).click();
        } else if (buttonToClick.equals("Next")) {
            driver.findElement(By.xpath("//button/span[text()='Next']")).click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }
    public void verifyUserDropdownforNonNaviance(){
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyUserDropdownforHE();
    }

    public void verifyNavigationUserDropdownforNonNaviance(){
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyNavigationUserDropdownforHE();
     }

    public void verifyUserAdminorNot(String option){
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyUserAdminorNot(option);
    }

    public void verifyHelpCentreforNonNaviance(){
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyHelpCentreforHE();
    }


    public void verifyUserDropdownforNaviance() {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        userDropdown().click();
        Assert.assertTrue("'Your Profile' option is not displayed",getYourProfileBtn().isDisplayed());
        Assert.assertTrue("'Institution Profile' option is not displayed",getInstitutionProfileBtn().isDisplayed());
        Assert.assertTrue("'Logged In As' Text is not displayed",loggedInText().isDisplayed());
        Assert.assertTrue("'Sign Out' option is not displayed",signOut().isDisplayed());
    }

    public void verifyNavigationinUserDropdownforNaviance() {
        getYourProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.switchTo().frame(frameInCommunity());
        Assert.assertTrue("'User Profile' is not displayed",userProfilePage().isDisplayed());
        driver.switchTo().defaultContent();
        waitUntilPageFinishLoading();
        userDropdown().click();
        getInstitutionProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.switchTo().frame(frameInCommunity());
        Assert.assertTrue("'Institution Profile' is not displayed",institutionProfilePage().isDisplayed());
        driver.switchTo().defaultContent();
        waitUntilPageFinishLoading();
    }

    private WebElement getCollegeFairsPrimaryContactPhoneNumberField() {
        return driver.findElement(By.id("notification_fairs_phone_number"));
    }
    private WebElement getRepVisitsPrimaryContactPhoneNumerField() {
        return driver.findElement(By.id("notification_contacts_primary_contact_phone"));
    }
    public void cancelCollegeFair(String fairName) {
        logger.info("Cancelling Job Fair " + fairName + " under RepVisits.");
        navBar.goToRepVisits();
        link("College Fairs").click();
        button(By.xpath("//a[@aria-label='"+fairName+"']")).click();
        button("Edit").click();
        button("Cancel This College Fair").click();
        if (getDriver().findElements(By.xpath("//span[contains(text(), 'Yes, Cancel this fair')]")).size() >= 1) {
            button("yes, cancel this fair").click();
        }
        else if (getDriver().findElements(By.xpath("//span[contains(text(), 'Cancel fair and notify colleges')]")).size() >= 1) {
            textbox(By.name("cancellationMessage")).sendKeys("College fair has been cancelled.");
            button("cancel fair and notify colleges").click();
            button("Close").click();
        }
        else{
            Assert.assertTrue("There were no job fairs registered for this high school.", false);
        }
        Assert.assertFalse("College fair was not canceled.", getDriver().findElements(By.xpath("//span[contains(text(), 'Upcoming Events')]/../../following-sibling::table/tbody/tr/td[contains(text(), '"+fairName+"')]")).size() >= 1);
    }

    public void createCollegeFair(DataTable dataTable) {
        logger.info("Creating a Job Fair under RepVisits.");
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("College Fairs").click();
        waitUntilPageFinishLoading();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(text(By.id("add-college"))));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(text(By.id("add-college"))));
        button("ADD A COLLEGE FAIR").click();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if(key.equals("Date")){
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairDateToFill);
            }
            else if (key.equals("RSVP Deadline")){
                String fairRSVPDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairRSVPDateToFill);
            }else
                enterCollegeFairData(key, data.get(key));

        }
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));
        button("Save").click();
    }

    /**
     * Creates a college fair with a random number appended to the end of the supplied name.
     * This reduces name collisions with repeated tests.
     * @param dataTable - Data Table containing all the fields for the College Fair
     */
    public void createDynamicCollegeFair(DataTable dataTable) {
        logger.info("Creating a Job Fair under RepVisits.");
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("College Fairs").click();
        waitUntilPageFinishLoading();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(text(By.id("add-college"))));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(text(By.id("add-college"))));
        button("ADD A COLLEGE FAIR").click();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if(key.equals("Date")){
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairDateToFill);
            }
            else if (key.equals("RSVP Deadline")){
                String fairRSVPDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairRSVPDateToFill);
            } else if (key.equals("College Fair Name")) {
                String fairName = randomizeFairName(data.get(key));
                FairName = fairName;
                enterCollegeFairData(key, fairName);
            } else
                enterCollegeFairData(key, data.get(key));

        }
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));
        button("Save").click();
    }

    public void unpublishCollegeFair() {
        waitUntilElementExists(getUnpublishButton());
        getUnpublishButton().click();
        waitUntilElementExists(close());
        close().click();
    }

    public void addEmailInNotificationandPrimaryContactPage(String Email){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Notifications & Primary Contact").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.findElement(By.id("notification_contacts_primary_contact_phone")).sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        driver.findElement(By.id("notification_contacts_additional_emails")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("notification_contacts_additional_emails")).clear();
        driver.findElement(By.id("notification_contacts_additional_emails")).sendKeys(Email);
        button("Save changes").click();
        waitUntilPageFinishLoading();
    }

    public String createFutureDateForFair(int days) {
        DateFormat dateFormat_3 = DateFormat.getDateInstance(DateFormat.LONG);
        Calendar cal_1 = Calendar.getInstance();
        cal_1.setTime(new Date());
        cal_1.add(Calendar.DATE, days);
        String fixDate = dateFormat_3.format(cal_1.getTime());
        return fixDate;
    }

    public void scrollDown(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)", "");
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void enterCollegeFairData(String field, String data){
        switch (field) {
            case "College Fair Name":
                textbox(By.id("college-fair-name")).sendKeys(data);
                break;
            case "Date":
                getDriver().findElement(By.xpath("//div[@class='ui fluid input']/input[@id = 'college-fair-date']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> dayCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : dayCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Start Time":
                WebElement startTime = getDriver().findElement(By.xpath("//input[@id='college-fair-start-time']"));
                startTime.click();
                startTime.sendKeys(data);
                break;
            case "End Time":
                WebElement endTime = getDriver().findElement(By.xpath("//input[@id='college-fair-end-time']"));
                endTime.click();
                endTime.sendKeys(data);
                break;
            case "RSVP Deadline":
                getDriver().findElement(By.xpath("//div[@class='ui input']/input[@id = 'college-fair-rsvp-deadline']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> RSVPCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : RSVPCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Cost":
                fairCost().sendKeys(data);
                break;
            case "Max Number of Colleges":
                textbox(By.id("college-fair-max-number-colleges")).sendKeys(data);
                break;
            case "Number of Students Expected":
                textbox(By.id("college-fair-number-expected-students")).sendKeys(data);
                break;
            case "Instructions for College Representatives":
                textbox(By.id("college-fair-instructions")).sendKeys(data);
                break;
            case "Automatically Confirm Incoming Requestions From Colleges?":
                scrollDown(driver.findElement(By.id("college-fair-automatic-request-confirmation-yes")));
                if(data.equalsIgnoreCase("yes")) {
                    radioButton(By.id("college-fair-automatic-request-confirmation-yes")).click();
                }
                else {
                    radioButton(By.id("college-fair-automatic-request-confirmation-no")).click();
                }
                break;
            case "Email Message to Colleges After Confirmation":
                scrollDown(driver.findElement(By.id("college-fair-email-message-to-colleges")));
                textbox(By.id("college-fair-email-message-to-colleges")).sendKeys(data);
                break;
            default:
                logger.info("Something went wrong during filling data for creatinf Fair.....");
        }
    }

    public void navigateToVisitPage(){
        waitUntilPageFinishLoading();
        waitForUITransition();
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.findElement(By.xpath("//input[@value='VISITS_AND_FAIRS' and @type='radio']")).click();
        while (driver.findElements(By.xpath("//div[@class='active step' and @name='Complete!']")).size()==0) {
            WebElement button =  button("Next");
            waitUntil(ExpectedConditions.visibilityOf(button),20);
            button("Next").click();
            waitForUITransition();
            waitUntilPageFinishLoading();
        }
        driver.findElement(By.xpath("//label[text()='All RepVisits Users']")).click();
        button("Next").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Take me to my visits button is not displayed",button("Take me to my visits").isDisplayed());
        button("Take me to my visits").click();
        waitUntilPageFinishLoading();
    }

    public void verifydefaultRepVisitPage(){
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Calendar page is not active",driver.findElement(By.xpath("//a[@class='menu-link active']/span[text()='Calendar']")).isDisplayed());
        WebElement addVisitButton = button("add visit");
        waitUntil(ExpectedConditions.visibilityOf(addVisitButton),20);
        Assert.assertTrue("Add visit button is not displayed",addVisitButton.isDisplayed());
        WebElement calendarMonth = driver.findElement(By.xpath("//button[normalize-space(@class)='ui pink button GFr3D5C_jMOwFFfwEoOXq _2I9ZFEPwCCOsGFn0AAk1Gl' and @title='Month']"));
        waitUntil(ExpectedConditions.visibilityOf(calendarMonth),20);
        Assert.assertTrue("Calendar is not displayed in Month view",calendarMonth.isDisplayed());
    }


    /*public void fillAddCollegeFairFields(String field, String data) {
        switch (field) {
            case "Automatically Confirm Incoming Requestions From Colleges?":
                // Scroll to the end of the form
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,250)", "");
                Actions actions = new Actions(driver);
                actions.moveToElement(driver.findElement(By.id("college-fair-automatic-request-confirmation-yes"))).perform();
                if(data.equalsIgnoreCase("yes")) {
                    radioButton(By.id("college-fair-automatic-request-confirmation-yes")).click();
                }
                else {
                    radioButton(By.id("college-fair-automatic-request-confirmation-no")).click();
                }
                break;
            case "College Fair Name":
                textbox(By.id("college-fair-name")).sendKeys(data);
                break;
            case "Date":
                getDriver().findElement(By.xpath("//div[@class='ui fluid input']/input[@id = 'college-fair-date']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> dayCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : dayCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Email Message to Colleges After Confirmation":
                textbox(By.name("emailInstructions")).sendKeys(data);
                break;
            case "End Time":
                WebElement endTime = getDriver().findElement(By.xpath("//input[@id='college-fair-end-time']"));
                endTime.click();
                endTime.sendKeys(data);
                break;
            case "Instructions for College Representatives":
                textbox(By.name("webInstructions")).sendKeys(data);
                break;
            case "Max Number of Colleges":
                textbox(By.name("maxColleges")).sendKeys(data);
                break;
            case "Number of Students Expected":
                textbox(By.name("numberOfExpectedStudents")).sendKeys(data);
                break;
            case "RSVP Deadline":
                getDriver().findElement(By.xpath("//div[@class='ui input']/input[@id = 'college-fair-rsvp-deadline']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> RSVPCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : RSVPCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Start Time":
                WebElement startTime = getDriver().findElement(By.xpath("//input[@id='college-fair-start-time']"));
                startTime.click();
                startTime.sendKeys(data);
                break;
            case "Cost":
                fairCost().sendKeys(data);
                break;
            default:
                textbox(By.name(field.toLowerCase())).sendKeys(data);
                break;
        }
    }*/


    public void navigateToNavianceSettingsPage(){
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
            while(driver.findElements(By.xpath("//div[@class='active step' and @name='Naviance Settings']")).size()==0){
                waitUntilElementExists(button("Next"));
               button("Next").click();
               waitForUITransition();
        }
    }

 //locators
    //locators
    public void accessAddAttendeePopUp(String attendeeName) {
        if (!attendeeName.equals("")) {
            driver.findElement(By.xpath("//input[@placeholder='Start Typing ...']")).sendKeys(attendeeName);
            WebElement element = driver.findElement(By.xpath("//div[contains(text(),'" + attendeeName + "')]"));
            doubleClick(element);
        }
        button("Add Attendees").click();
        waitUntilPageFinishLoading();
    }

    public void accessSuccessMessageforAddAttendees(String buttonName) {
        if (buttonName.equals("No, I'm Done")) {
            driver.findElement(By.cssSelector("button[class='ui basic primary button']")).click();
        } else if (buttonName.equals("Yes, Add More")) {
            driver.findElement(By.cssSelector("button[id='next-action']")).click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }

    public void accessCollegeFairDetailsPage(String buttonToClick) {
        if(buttonToClick.equals("Edit")){
            driver.findElement(By.cssSelector("button[id='edit-college-fair']")).click();
        }else if(buttonToClick.equals("Add Attendee")){
            driver.findElement(By.cssSelector("button[id='add-attendee']")).click();
        }else if(buttonToClick.equals("")){
            driver.findElement(By.cssSelector("button[id='message-colleges']")).click();
        }else{
            Assert.fail("The given option is not a valid one");
        }
    }

    public void verifyFairsAreClickable(String collegeFairName,String date,String startTime,String endTime,String RSVPDate,String cost,String maxNumberofColleges,String numberofStudentsExpected){
        navBar.goToRepVisits();
        link("Calendar").click();
        waitUntilPageFinishLoading();

        driver.findElement(By.cssSelector("button[title= 'Forwards']")).click();
        waitForUITransition();
        while(!driver.findElement(By.xpath("//*[contains(text(), '" + collegeFairName + "')]")).isDisplayed()){
            driver.findElement(By.cssSelector("button[title= 'Forwards']")).click();
        }

        new WebDriverWait(getDriver(), 60).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[title^= '" + collegeFairName +"']")));
        driver.findElement(By.cssSelector("div[title^='" + collegeFairName + "']")).click();
        WebElement popup = new WebDriverWait(getDriver(), 60).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form[id='college-fair-form']")));
        Assert.assertTrue("Tray was not displayed!", driver.findElement(By.cssSelector("form[id='college-fair-form']")).isDisplayed());
        Actions action = new Actions(driver);
        action.sendKeys(Keys.END).build().perform();
        textbox(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Tray was not displayed!", driver.findElement(By.cssSelector("button[class='ui primary right floated button']")).isDisplayed());
        Assert.assertTrue("'College Fair Details' Title is not displayed",driver.findElement(By.xpath("//h2/span[text()='College Fair Details']")).isDisplayed());
        Assert.assertTrue("'College Fair Name' Label is not displayed",text("College Fair Name").isDisplayed());
        Assert.assertTrue("'Date' Label is not displayed",driver.findElement(By.xpath("//label[text()='Date']")).isDisplayed());
        textbox(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_UP);
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
            Assert.assertTrue("'College Fair Name' value was not as expected.",currentFairName.contains(collegeFairName));
            driver.findElement(By.xpath("//input[@id='college-fair-name']")).sendKeys(Keys.PAGE_DOWN);
        }
        if(!date.equals("")) {
            String actualDate = driver.findElement(By.id("college-fair-date")).getAttribute("value");
            String fairDate = selectdate(12);
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
            String fairDate = selectdate(4);
            waitUntilPageFinishLoading();
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if(!cost.equals("")) {
            String currentCost = textbox(By.id("college-fair-cost")).getAttribute("value");
            waitUntilPageFinishLoading();
            Assert.assertTrue("'Cost' value was not as expected.",currentCost.equals(cost));
        }

        if(!numberofStudentsExpected.equals("")) {
            String currentNumberofStudentsExpected = textbox(By.id("college-fair-number-expected-students")).getAttribute("value");
            waitUntilPageFinishLoading();
            Assert.assertTrue("'Max Number of Colleges' value was not as expected.",currentNumberofStudentsExpected.equals(numberofStudentsExpected));
        }
    }

    public void verifyCollegeFairDetailsPage(String collegeFairName, String date, String instructionsforCollegeRepresentatives) {
        Assert.assertTrue("Edit Button is not Displayed", button(By.id("edit-college-fair")).isDisplayed());
        Assert.assertTrue("MESSAGE COLLEGES Button is not Displayed", button(By.id("message-colleges")).isDisplayed());
        Assert.assertTrue("ADD ATTENDEE Button is not Displayed", button(By.id("add-attendee")).isDisplayed());
        Assert.assertTrue("Colleges Attending Heading is not Displayed", driver.findElement(By.xpath(" //h2/span[text()='Colleges Attending']")).isDisplayed());
        Assert.assertTrue("ADD ATTENDEE Button is not Displayed", button(By.id("add-attendee")).isDisplayed());

        //Verify the Headers of the table
        List<String> columns = new ArrayList<>();
        columns.add("Name");
        columns.add("Contact");
        columns.add("Notes");
        columns.add("Status");

        for (String column : columns) {
            Assert.assertTrue("column Name " + column + " is not displaying as expected!", driver.findElement(By.xpath("//table[@id='he-account-dashboard']/thead/tr//th/span[text()='" + column + "']")).isDisplayed());
        }
        //verify the College Fairs Details
        if(!collegeFairName.equals("")) {
            if (collegeFairName.equalsIgnoreCase("PreviouslySetFair"))
                collegeFairName = FairName;
            Assert.assertTrue("College Fair Name is not Displayed",driver.findElement(By.xpath("//h1[text()='"+collegeFairName+"']")).isDisplayed());;
        }
        if(!date.equals("")) {
            String actualDate = driver.findElement(By.xpath("//div[@class='thirteen wide column']//b/span")).getText();
            String fairDate = verifySpecificDate(Integer.parseInt(date));
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if(!instructionsforCollegeRepresentatives.equals("")) {
            String actualInstructionsforCollegeRepresentatives = driver.findElement(By.xpath("//div[@class='column']//p")).getText();
            Assert.assertTrue("Date is not Displayed.", actualInstructionsforCollegeRepresentatives.equals(instructionsforCollegeRepresentatives));
        }
    }

    public void accessListoffairAttendees(String buttonToClick, String name){
        int nameID = getColumnIdByFieldName( "//table[@id='he-account-dashboard']//thead","Name");
        int rowID = getRowIdByColumnId("//table[@id='he-account-dashboard']//tbody", nameID,name);
        nameID = nameID+1;
        rowID = rowID+1;
        if(buttonToClick.equals("Cancel")){
            WebElement cancelbutton = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody//tr["+rowID+"]//td//button/span[text()='CANCEL']"));
            cancelbutton.click();
            waitUntilPageFinishLoading();
        }
    }

    public void accessConfirmCancelPopup(String cancellationMessage, String buttonToClick){

        if(!cancellationMessage.equals("")) {
            Assert.assertTrue("Cancellation Message TextBox is not displayed",driver.findElement(By.xpath("//textarea[@id='attendee-cancellation-message']")).isDisplayed());
            driver.findElement(By.xpath("//textarea[@id='attendee-cancellation-message']")).clear();
            driver.findElement(By.xpath("//textarea[@id='attendee-cancellation-message']")).sendKeys(cancellationMessage);
            //verify 'Yes, cancel visit" Button
            Assert.assertTrue("Button 'Yes, cancel visit' is not displayed",driver.findElement(By.cssSelector("button[class='ui red small right floated button']")).isDisplayed());
        }

        if(!buttonToClick.equals("")) {
            if(buttonToClick.equals("No, go back")){
                WebElement goBackButton = driver.findElement(By.cssSelector("button[id='go-back']"));
                goBackButton.click();
                waitUntilPageFinishLoading();
            }else if(buttonToClick.equals("Yes, cancel visit")){
                WebElement yesCancelVisit = driver.findElement(By.cssSelector("button[class='ui red small right floated button']"));
                yesCancelVisit.click();
                waitUntilPageFinishLoading();
            }else{
                Assert.fail("The given option for the button to click is not a valid one");
            }
        }
    }

    public void verifyListofRegisteredAttendee(String name, String contact, String notes, String status, String action) {
        int nameID = getColumnIdByFieldName("//table[@id='he-account-dashboard']//thead","Name");
        int rowID = getRowIdByColumnId("//table[@id='he-account-dashboard']//tbody",nameID,name);
        nameID = nameID+1;
        rowID = rowID+1;

        if(!name.equals("")) {
            String actualName = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td["+nameID+"]")).getText();
            Assert.assertTrue("College Attendee Name is not Displayed.", actualName.equals(name));
        }
        if(!contact.equals("")) {
            String[] contacts = contact.split(",");
            String actualContact = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td[2]")).getText();
            int contactsCount = contacts.length;
            for(int i=0;i<contactsCount;i++){
                Assert.assertTrue("College Contacts "+contacts[i]+" is not Displayed", actualContact.contains(contacts[i]));
            }
        }
        if(!notes.equals("")) {
            String actualNotes = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td[3]")).getText();
            Assert.assertTrue("Notes is not Displayed.", actualNotes.equals(notes));
        }
        if(!status.equals("")) {
            String actualStatus = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td[4]")).getText();
            Assert.assertTrue("Status is not Displayed.  Expected: " + status + "  Actual: " + actualStatus, actualStatus.equals(status));
        }
        if(!action.equals("")) {
            Assert.assertTrue("Cancel Button is not Displayed.", driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td[5]//button/span[text()='CANCEL']")).isDisplayed());
        }
    }

    public void removeTimeSlotsInRegularWeeklyHoursTab(String date, String time) {
        time = StartTime.toUpperCase();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        int Date = Integer.parseInt(date);
        String Day = getSpecificDate(Date,"EEE").toUpperCase();
        int columnID = getColumnIdFromTable( "//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']/thead",Day );
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody", columnID, time);

        if(columnID>= 0 && rowID>= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            //Remove Time slot
            WebElement removeIcon = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody//tr[" + rowID + "]//td[" + columnID + "]//i[@class='trash outline icon _26AZia1UzBMUnJh9vMujjF']"));
            jsClick(removeIcon);
            waitUntilPageFinishLoading();
            driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
            waitUntilPageFinishLoading();
        }else{
            Assert.fail("The Time Slot "+time+"is not displayed in the Regular weekly hours ");
        }
    }

    /**
     * deletes a slot time in the regular weekly hours
     *
     * @param day,  the day of the slot time to be deleted: MON, TUE, WED, THU, FRI
     * @param time, the time of the slot time to be deleted: 8:25PM
     */
    public void removeSlotTimeByDayAndTime(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("REGULAR WEEKLY HOURS").click();
        boolean slotWasDeleted = false;
        Map<String, String> days = new HashMap<String, String>();
        days.put("Mon", "0");
        days.put("Tue", "1");
        days.put("Wed", "2");
        days.put("Thu", "3");
        days.put("Fri", "4");
        WebElement table = driver.findElement(By.xpath(".//tbody"));
        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
        for (WebElement row : rows) {
            List<WebElement> timeSlots = row.findElements(By.xpath(".//td"));
            if (!timeSlots.get(Integer.parseInt(days.get(day))).findElements
                    (By.xpath(String.format(".//div/button[text()='%s']", time))).isEmpty()) {
                WebElement deleteButton = timeSlots.get(Integer.parseInt(days.get(day)))
                        .findElement(By.xpath(".//span/i"));
                deleteButton.click();
                getRemoveTimeSlotButton().click();
                slotWasDeleted = true;
                break;
            }
        }
        Assert.assertTrue(String.format("The time slot with day: %s and time: %s was not deleted", day, time), slotWasDeleted);
    }

    //Function Name  : getColumnIdByUsingColumnName() for Regular Weekly Hours
    public int  getColumnIdFromTable(String tableHeaderLocator, String fieldName)
    {
        int columnId=-1, colCount = 0;
        String presentField=null;

        WebElement webEleTableHeader=driver.findElement(By.xpath(tableHeaderLocator));
        List<WebElement> webEleRows=webEleTableHeader.findElements(By.tagName("tr"));
        logger.info("webEleRows ="+webEleRows.size());
        List<WebElement> webEleColumns=webEleRows.get(0).findElements(By.tagName("th"));
        colCount = webEleColumns.size();
        logger.info("webEleColumns ="+webEleColumns.size());

        if(colCount > 0)
        {
            for(int colNum=0;colNum<colCount;colNum++)
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

    //Function Name  : getRowIdByColumnId()  for Regular Weekly Hours
    public int  getRowIdByColumn(String tableBodyLocator, int columnId, String dataToFind) {
        int rowId=-1, rowCount = 0;

        WebElement webEleTableBody=driver.findElement(By.xpath(tableBodyLocator));
        List<WebElement> webEleRows=webEleTableBody.findElements(By.tagName("tr"));
        rowCount = webEleRows.size();

        if(rowCount > 0)
        {
            for(int rowNum=0;rowNum<rowCount;rowNum++)
            {
                List<WebElement> webEleColumns=webEleRows.get(rowNum).findElements(By.tagName("td"));
                webEleColumns.size();
                logger.info("Time Slot ="+webEleColumns.get(columnId).findElement(By.tagName("button")).getText());
                if(webEleColumns.get(columnId).findElement(By.tagName("button")).getText().equals(dataToFind))
                {
                    rowId = rowNum;
                    break;
                }
            }
        }

        return rowId;
    }

    public void verifyAddvisitButtonInCalendarPage(String option){
        if(option.equals("Disabled")){
            waitUntilElementExists(button("Next"));
            button("Next").click();
            link("Calendar").click();
            waitUntilPageFinishLoading();
            WebElement buttonDisabled = driver.findElement(By.xpath("//button[@class='ui teal disabled button _2vMIFbyypA0b_pLiQmz0hV' and @disabled='']"));
            Assert.assertTrue("AddVisit button is enabled in the calendar page",buttonDisabled.isDisplayed());
        } else if(option.equals("Enabled")){
            while (driver.findElements(By.xpath("//div[@class='active step' and @name='Complete!']")).size() == 0) {
                waitUntilElementExists( button("Next"));
                button("Next").click();
                waitForUITransition();
                waitUntilPageFinishLoading();
            }
            Assert.assertTrue("Complete page is not displayed", text("Visit Availability").isDisplayed());
            button("Next").click();
            waitUntilPageFinishLoading();
            waitForUITransition();
            driver.findElement(By.xpath("//span[text()='Take me to my visits']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Add visit button is disabled",button("add visit").isEnabled());
            waitForUITransition();
        }else {
            logger.info("Invalid option");
        }
    }

    public void addVisitsManuallyFromCalendarTab(String date,String time,String attendee,String location) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span/span[text()='Go to date']"),1));
        doubleClick(goToDateButton());
        setSpecificDateforManuallyCreatingVisit(date);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//td/button[text()='"+StartTime+"']"),1));
        WebElement button = driver.findElement(By.xpath("//td/button[text()='"+StartTime+"']"));
        moveToElement(button);
        driver.findElement(By.xpath("//td/button[text()='"+StartTime+"']")).click();
        moveToElement(attendeeTextBox());
        attendeeTextBox().sendKeys(Keys.PAGE_DOWN);
        attendeeTextBox().sendKeys(attendee);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/div[text()='"+attendee+"']"),1));
        waitUntilElementExists(driver.findElement(By.xpath("//div/div[text()='"+attendee+"']")));
        Assert.assertTrue("Attendee name is not displayed in the dropdown",driver.findElement(By.xpath("//div/div[text()='"+attendee+"']")).isDisplayed());
        selectAttendeeOrInstitution(attendee).click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("calendar-search-reps"),1));
        String selectedAttendeeValue = driver.findElement(By.id("calendar-search-reps")).getAttribute("value");
        Assert.assertTrue("Representative is not present in the Representative text box",selectedAttendeeValue.equals(attendee));
        eventLocationTextboxInSchedulePopup().clear();
        eventLocationTextboxInSchedulePopup().sendKeys(location);
        waitForUITransition();
        eventLocationTextboxInSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']"),1));
        moveToElement(addVisitButtonInVisitSchedulePopup());
        Assert.assertTrue("AddVisit button is not Enabled",addVisitButtonInVisitSchedulePopup().isEnabled());
        addVisitButtonInVisitSchedulePopup().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }

    public void addContactManually(String date,String time,String fName,String lName,String email,String phNo,String position,String institution) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("button[class$='_3GJIUrSQadO6hk9FZvH28D']"),1));
        doubleClick( DateButton());
        waitForUITransition();
        setSpecificDateforManuallyCreatingVisit(date);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//td/button[text()='"+StartTime+"']"),1));
        WebElement button = driver.findElement(By.xpath("//td/button[text()='"+StartTime+"']"));
        moveToElement(button);
        button.click();
        moveToElement(addcontactManually());
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Not in the list? Add them manually']"),1));
        addcontactManually().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("add-rep-first-name"),1));
        moveToElement(firstNameTextBox());
        firstNameTextBox().sendKeys(fName);
        lastNameTextBox().sendKeys(lName);
        lastNameTextBox().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("add-rep-email"),1));
        moveToElement(emailTextBox());
        emailTextBox().sendKeys(email);
        phoneNoTextBox().sendKeys(phNo);
        positionTextBox().sendKeys(position);
        institutionTextBox().sendKeys(institution);
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/div[text()='"+institution+"']"),1));
        Assert.assertTrue("Attendee name is not displayed in the dropdown",driver.findElement(By.xpath("//div/div[text()='"+institution+"']")).isDisplayed());
        selectAttendeeOrInstitution(institution).click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("add-rep-institution"),1));
        String selectedAttendeeValue = driver.findElement(By.id("add-rep-institution")).getAttribute("value");
        Assert.assertTrue("Representative is not present in the Representative text box",selectedAttendeeValue.equals(institution));
        institutionTextBox().sendKeys(Keys.PAGE_DOWN);
        eventLocationTextboxInSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']"),1));
        moveToElement(addVisitButtonInVisitSchedulePopup());
        Assert.assertTrue("AddVisit button is not Enabled",addVisitButtonInVisitSchedulePopup().isEnabled());
        addVisitButtonInVisitSchedulePopup().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }

    public void verifyAppointmentsIncalendar(String date,String time,String institution){
        String startTime=getVisitStartTimeforcalendar();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        collegeFairTextBoxInCalendarPage().click();
        pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitForUITransition();
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + institution + "']")).isDisplayed());
        Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + institution + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
        waitUntilPageFinishLoading();
    }

    public void scheduleNewVisitusingCustomTime(String date,String startTime,String endTime,String attendee,String location) {
        link("Availability & Settings").click();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Want a custom time? Add it manually']"),1));
        addVisitManually().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("button[class$='_3VnqII6ynYglzDU1flY9rw']"),1));
        waitUntilElementExists(selectDateButton());
        doubleClick( selectDateButton());
        setSpecificDateforManuallyCreatingVisit(date);
        ManualStartTime = startTime(startTime);
        logger.info("Start Time = "+ManualStartTime);
        moveToElement(manualEndTime());
        manualStartTime().sendKeys(ManualStartTime);
        manualEndTime().sendKeys(endTime);
        addAttendee().sendKeys(attendee);
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/div[text()='"+attendee+"']"),1));
        Assert.assertTrue("Attendee name is not displayed in the dropdown",driver.findElement(By.xpath("//div/div[text()='"+attendee+"']")).isDisplayed());
        attendee(attendee).click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("calendar-search-reps"),1));
        String selectedAttendeeValue = driver.findElement(By.id("calendar-search-reps")).getAttribute("value");
        Assert.assertTrue("Representative is not present in the Representative text box",selectedAttendeeValue.equals(attendee));
        manualStartTime().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        moveToElement(eventLocation());
        eventLocation().clear();
        eventLocation().sendKeys(location);
        eventLocation().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']"),1));
        moveToElement(addVisitButtonInVisitSchedulePopup());
        Assert.assertTrue("AddVisit button is not Enabled",addVisitButtonInVisitSchedulePopup().isEnabled());
        addVisitButtonInVisitSchedulePopup().click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void verifyConfirmationMessage(String message) {
        waitUntil(ExpectedConditions.visibilityOf(confirmationMessageCreatedVisit(message)),10);
        Assert.assertTrue("Confirmation Message is not displayed",confirmationMessageCreatedVisit(message).isDisplayed());
    }

    public void verifyCalendarPageIsDisplayed() {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        Assert.assertTrue("add visit button is not displayed",addVisitButton().isDisplayed());
        Assert.assertTrue("calendar page is not displayed",calendar().isDisplayed());
    }

    public void verifyCloseDrawerInVisitSchedulePopup(){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"),1));
        Assert.assertTrue("close drawer is not displayed",driver.findElement(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']")).isDisplayed());
        Assert.assertTrue("schedule new visit is not displayed",textInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("select an available time slot is not displayed",verifyTextInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("Go to date is not displayed",gotoDateButtonInVisitSchedulePopup().isDisplayed());
    }

    public void verifyLinkInVisitSchedulePopup(String link){
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='"+link+"']"),1));
        Assert.assertTrue(link+"is not displayed",driver.findElement(By.xpath("//span[text()='"+link+"']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='"+link+"']")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyGobacktoListLinkInVisitSchedulePopup(String link){
        Assert.assertTrue("Go back to list link is not displayed",driver.findElement(By.xpath("//span[text()='"+link+"']")).isDisplayed());
    }

    public void verifyStartTypingTextInVisitSchedulePopup(String text){
        Assert.assertTrue("Find Representative text is not displayed",driver.findElement(By.xpath("//span[text()='Find Representative']")).isDisplayed());
        Assert.assertTrue("Start Typing text is not displayed in the placeholder",driver.findElement(By.xpath("//input[@placeholder='"+text+"']")).isDisplayed());
        driver.findElement(By.xpath("//input[@placeholder='"+text+"']")).sendKeys(Keys.PAGE_DOWN);
    }

    public void verifyTextboxInVisitSchedulePopup(String firstName,String lastName,String email,String phone,String position){
        Assert.assertTrue("Rep-firstname textbox is not displayed",driver.findElement(By.id(firstName)).isDisplayed());
        Assert.assertTrue("Rep-lastname textbox is not displayed",driver.findElement(By.id(lastName)).isDisplayed());
        driver.findElement(By.id(lastName)).sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id(email),1));
        Assert.assertTrue("Rep-email textbox is not displayed",driver.findElement(By.id(email)).isDisplayed());
        Assert.assertTrue("Rep-phone textbox is not displayed",driver.findElement(By.id(phone)).isDisplayed());
        Assert.assertTrue("Rep-position textbox is not displayed",driver.findElement(By.id(position)).isDisplayed());
        driver.findElement(By.id(position)).sendKeys(Keys.PAGE_DOWN);
    }

    public void verifyRequiredFieldInVisitSchedulePopup(String requiredField){
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='field required field']/label/span[text()='"+requiredField+"']"),1));
        Assert.assertTrue("Required field text box is not displayed",driver.findElement(By.xpath("//div[@class='field required field']/label/span[text()='"+requiredField+"']")).isDisplayed());
    }

    public void verifyTextInVisitSchedulepopup(String actualValue,String text){
        Assert.assertTrue("Rep-Institiution textbox is not displayed",driver.findElement(By.id(text)).isDisplayed());
        String originalText = driver.findElement(By.xpath("//span[@class='_32cVj2iOoilFhz0dhZXXd']/span")).getText();
        Assert.assertTrue("If their school isn't in the list, you can simply type it in text is not displayed",originalText.equals(actualValue));
        addrepInstitution().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//input[@aria-label='Event Location']"),1));
        Assert.assertTrue("event location is not displayed",eventLocationTextboxInSchedulePopup().isDisplayed());
        eventLocationTextboxInSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/textarea[@id='internalNotes']"),1));
        Assert.assertTrue("internal notes is not displayed",internalNotesTextBoxInVisitSchedulePopup().isDisplayed());
    }

    public void verifyAddvisitButtoninVisitSchedulepopup(String addVisitButton){
        Assert.assertTrue("add visit button is not displayed",button(addVisitButton).isDisplayed());
        driver.findElement(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']")).click();
    }

    private WebElement getAddAttendeeSearchBox() {
        return driver.findElement(By.cssSelector("input[placeholder='Start Typing ...']"));
    }
    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }
    private WebElement collegeFairsButton() { return getDriver().findElement(By.xpath("//span[text()='College Fairs']")); }
    private WebElement viewMoreUpcomingEventsLink() { return getDriver().findElement(By.xpath("//span[text()='View More Upcoming Events']/..")); }
    private WebElement fairElementDetails(String fairName) { return getDriver().findElement(By.xpath
            ("//div[@class='_1743W0qaWdOtlS0jkveD7o'][1]/table/tbody/tr/td[text()='" + fairName + "']/following-sibling::td[4]/a/span")); }
    private WebElement editFairButton() { return getDriver().findElement(By.cssSelector("#edit-college-fair")); }
    private WebElement cancelThisCollegeFair() { return getDriver().findElement(By.cssSelector("button.ui.red.basic.button")); }
    private String cancelMessageTextBoxLocator() { return "college-fair-cancellation-message"; }
    private WebElement cancelFairButton() { return getDriver().findElement(By.cssSelector("button[type='submit']")); }
    private WebElement closeButton() { return getDriver().findElement(By.xpath("//button[text()='Close']")); }
    private WebElement addFairButton() { return getDriver().findElement(By.cssSelector("#add-college")); }
    private WebElement fairNameTextBox() { return getDriver().findElement(By.cssSelector("#college-fair-name")); }
    private WebElement dateCalendarIcon() { return getDriver().findElement(By.cssSelector("div.ui.fluid.input i.calendar.large.link.icon")); }
    private WebElement startTimeTextBox() { return getDriver().findElement(By.cssSelector("#college-fair-start-time")); }
    private WebElement endTimeTextBox() { return getDriver().findElement(By.cssSelector("#college-fair-end-time")); }
    private WebElement rsvpCalendarIcon() { return getDriver().findElement(By.cssSelector("#college-fair-rsvp-deadline + i")); }
    private WebElement costTextBox() { return getDriver().findElement(By.cssSelector("#college-fair-cost")); }
    private WebElement maxNumOfColleges() { return getDriver().findElement(By.cssSelector("#college-fair-max-number-colleges")); }
    private WebElement numOfStudents() { return getDriver().findElement(By.cssSelector("#college-fair-number-expected-students")); }
    private WebElement autoApprovalYesRadButton() { return getDriver().findElement(By.cssSelector("#college-fair-automatic-request-confirmation-yes")); }
    private WebElement autoApprovalNoRadButton() { return getDriver().findElement(By.cssSelector("#college-fair-automatic-request-confirmation-no")); }
    private WebElement saveButton() { return getDriver().findElement(By.cssSelector("button[type=\"submit\"]")); }
    private WebElement cancelFairNoAutoApprovals() { return driver.findElement(By.cssSelector("button.ui.primary.right.floated.button._4kmwcVf4F-UxKXuNptRFQ span")); }
    private WebElement availabilityAndSettingsButton() { return getDriver().findElement(By.xpath("//span[text()='Availability & Settings']")); }
    private WebElement innerExceptionsButton() { return getDriver().findElement(By.cssSelector("ul.ui.pointing.secondary.fourth.menu li:nth-of-type(3) a")); }
    private WebElement chooseADateButton() { return getDriver().findElement(By.cssSelector("button.ui.small.button i")); }
    private WebElement newTimeSlotStartTime() { return getDriver().findElement(By.cssSelector("input[title=\"start time\"]")); }
    private WebElement newTimeSlotEndTime() { return getDriver().findElement(By.cssSelector("input[title=\"end time\"]")); }
    private WebElement newTimeSlotVisits() { return  getDriver().findElement(By.cssSelector("input[title=\"numVisits\"]")); }
    private WebElement addTimeSlotButton() { return getDriver().findElement(By.xpath("//span[text()='ADD TIME SLOT']")); }
    private WebElement timeSlot(String date, String startTime) { return getDriver().findElement(By.xpath("//span[text()='" + date + "']/../../../../following-sibling::tbody/tr/td/div/button[text()='" + startTime + "']")); }
    private WebElement timeSlotDeleteIcon(String date, String startTime) { return getDriver().findElement(By.xpath("//span[text()='" + date + "']/../../../../following-sibling::tbody/tr/td/div/button[text()='" + startTime + "']/../span/i")); }
    private WebElement removeSlotConfirmButton() { return getDriver().findElement(By.cssSelector("div.ui.modal.transition.visible.active button.ui.primary.button")); }
    private WebElement removeOpenings() { return getDriver().findElement(By.cssSelector("i.teal.trash.outline.icon")); }
    private WebElement writeInConfirmationMessage() { return getDriver().findElement(By.cssSelector("textarea[name='emailInstructions']"));}
    private void writeInSpecialInstructionForRepViists(String message) {
        getDriver().findElement(By.cssSelector("textarea[name='webInstructions']")).clear();
        getDriver().findElement(By.cssSelector("textarea[name='webInstructions']")).sendKeys(message);
    }
    private WebElement getContactsBtn() {
        return link("Contacts");
    }
    private WebElement getSearchBoxforContact() { return driver.findElement(By.name("contacts-search"));}
    private WebElement getSearchButton() { return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));}
    private WebElement fairCost() { return getDriver().findElement(By.id("college-fair-cost")); }

    public String verifySpecificDate(int addDays) {
        String DATE_FORMAT_NOW = "MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
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

    public void navigateToRepVisitsSection(String pageName) {
        navBar.goToRepVisits();
        if (pageName.equalsIgnoreCase("visit feedback")) {
            getVisitsFeedbackBtn().click();
        } else {
            link(pageName).click();
        }
        waitUntilPageFinishLoading();
    }

    public void  verifyRepVisitsPageWhenNoVisitsScheduledForNext7Days() {

        Assert.assertTrue("'You don't have any visits or fairs for the next week' text is not displayed",
                driver.findElement(By.xpath("//div[@class='column _2oJjQM9p7RJ4cIsu8IJs-y _2yDDA7hJhhRjDz5LtnmL-e']")).getText().equals("You don't have any visits or fairs for the next week."));
        Assert.assertTrue("Calendar icon is not displayed", driver.findElement(By.xpath("(//div[@class='column _2oJjQM9p7RJ4cIsu8IJs-y'])[1]")).isDisplayed());
        Assert.assertTrue("'It looks like you don't have any upcoming visits or fairs in the next 7 days.' text is not displayed",
                driver.findElement(By.xpath("(//div[@class='column _2oJjQM9p7RJ4cIsu8IJs-y'])[2]")).getText().equals("It looks like you don't have any upcoming visits or fairs in the next 7 days.\n" +
                "You can always check your calendar for more upcoming appointments."));
        link("calendar").click();

        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.getCurrentUrl().contains("/calendar");
            }
        };

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(condition);

    }

    public void cancelAllEventsForNext7Days() {

        String eventType = null;

        for(int day = 0; day < 7; day++)
        {
                String date = getSpecificDate(day, "MMMMM dd yyyy");
                List<WebElement> events = getEventsScheduledForDate(date.split(" ")[1], date.split(" ")[0], date.split(" ")[2]).get("EVENTS");

                while(events.size() != 0)
                {
                    WebElement event = events.get(0);

                    String cssClass = event.findElement(By.xpath("./div[@class='rbc-event-content']/div")).getAttribute("class");

                    if(cssClass.equals("_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa"))
                        eventType = "VISIT";

                    if(cssClass.equals("_2_SLvlPA02MerU8g5DX1vz _2CrNWdVYs3sN4k1Rgr_RDv"))
                        eventType = "COLLEGE_FAIR";

                    if(eventType.equals("VISIT"))
                    {
                        WebDriverWait wait = new WebDriverWait(driver, 10);
                        event.click();

                        WebElement cancelThisVisitLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Cancel This Visit']")));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelThisVisitLink);
                        wait.until(ExpectedConditions.visibilityOf(cancelThisVisitLink)).click();

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repVisit-cancelation-message"))).sendKeys("comment for cancellation");
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Yes, cancel visit']"))).click();

                    }
                    else if(eventType.equals("COLLEGE_FAIR"))
                    {
                        WebDriverWait wait = new WebDriverWait(driver, 10);
                        event.click();

                        WebElement cancelThisCollegeFairButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Cancel This College Fair']")));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelThisCollegeFairButton);
                        cancelThisCollegeFairButton.click();

                        button("Yes, Cancel this fair").click();
                        button("Close").click();
                    }

                    events = getEventsScheduledForDate(date.split(" ")[1], date.split(" ")[0], date.split(" ")[2]).get("EVENTS");
                }
        }
    }

    public HashMap<String, List<WebElement>> getEventsScheduledForDate(String day, String month, String year)
    {
        ArrayList<WebElement> eventsScheduledForDateElements = new ArrayList<WebElement>();
        ArrayList<WebElement> showMoreLinkElement = new ArrayList<WebElement>();
        HashMap<String, List<WebElement>> allElementsInDayCell = new HashMap<String, List<WebElement>>();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'rbc-calendar')]//*")));

        String currentMonthAndYear =  driver.findElement(By.xpath("//div[@class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']")).getText();

        String currentMonth = currentMonthAndYear.split(" ")[0];
        String currentYear = currentMonthAndYear.split(" ")[1];

        if(month.equals(currentMonth) == false  || year.equals(currentYear) == false) {
            driver.findElement(By.xpath("//button[@title='Forwards']")).click();
        }

        WebElement dateCell = driver.findElement(By.xpath("//div[@class='rbc-month-view']//div[contains(@class, 'rbc-date-cell') and not(contains(@class, 'rbc-off-range'))]/a[text()='" + day + "']"));

        HashMap<String, Integer> dateCellIndices = getDateCellIndicesInGrid(dateCell);

        int rowIndex = dateCellIndices.get("ROW_INDEX");
        int columnIndex = dateCellIndices.get("COLUMN_INDEX");

        WebElement dayCell = driver.findElement(By.xpath("//div[@class='rbc-month-row'][" + rowIndex + "]//div[contains(@class, 'rbc-day-bg')][" + columnIndex + "]"));
        int dayCellStartXCoordinate = dayCell.getLocation().getX();
        int dayCellEndXCoordinate = dayCellStartXCoordinate + dayCell.getSize().getWidth();

            //List<WebElement> rbcEventElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rbc-month-row'][" + rowIndex + "]//div[@class='rbc-event']")));
        List<WebElement> rbcEventElements = driver.findElements(By.xpath("//div[@class='rbc-month-row'][" + rowIndex + "]//div[@class='rbc-event']"));

        for (WebElement rbcEventElement : rbcEventElements) {
            if (rbcEventElement.getLocation().getX() > dayCellStartXCoordinate && rbcEventElement.getLocation().getX() < dayCellEndXCoordinate) {
                eventsScheduledForDateElements.add(rbcEventElement);
            }
        }

        WebElement showMoreLink = null;

        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            showMoreLink = driver.findElement(By.xpath("//div[@class='rbc-show-more']"));
        } catch (Exception ex) { }

        showMoreLinkElement.add(showMoreLink);

        allElementsInDayCell.put("EVENTS", new ArrayList(eventsScheduledForDateElements));
        allElementsInDayCell.put("SHOW_MORE", new ArrayList(showMoreLinkElement));


        return allElementsInDayCell;
    }

    public void addnewTimeSlot(String day, String startTime, String endTime, String numVisits) {
        navBar.goToRepVisits();
        WebElement element = link("Availability & Settings");
        waitUntilElementExists(element);
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Availability").click();
        waitUntilPageFinishLoading();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        startOrEndDate().sendKeys(Keys.PAGE_DOWN);
        addTimeSlot().click();
        List<WebElement> slot= driver.findElements(By.cssSelector("button[class='ui small button IHDZQsICrqtWmvEpqi7Nd']"));
        if(slot.size()>0){
            availabilityButton().sendKeys(Keys.PAGE_DOWN);
        }
        availabilityEndtimeTextbox().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        waitUntilElementExists(selectDay());
        day = day(day);
        selectDayForSlotTime("div[class='ui button labeled dropdown icon QhYtAi_-mVgTlz73ieZ5W']", day);
        StartTime = startTime(startTime);
        logger.info("Start Time = " + StartTime);
        addStartTime().sendKeys(StartTime);
        addEndTime().sendKeys(endTime);
        logger.info("End Time = " + endTime);
        visitsNumber(numVisits);
        waitUntilElementExists(submit());
        addTimeSlotSubmit().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> displayingPopup = driver.findElements(By.xpath("//div/span[text()='Review Previously Deleted Time Slots']"));
        if(displayingPopup.size()==1){
            driver.findElement(By.id("ignore-time-slots")).click();
            button("Add regular hours").click();
            waitUntilPageFinishLoading();
            waitForUITransition();
        }
    }

    public void accessCreateCollegeFair(String collegeFairName,String date,String startTime,String endTime,String RSVPDate,String cost,String maxNumberofColleges,String numberofStudentsExpected,String buttonToClick){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement fairs=link("College Fairs");
        waitUntilElementExists(fairs);
        link("College Fairs").click();
        waitUntilPageFinishLoading();
        button(By.id("add-college")).click();
        FairName = randomizeFairName(collegeFairName);
        //FairName = collegeFairName;

        if(!collegeFairName.equals("")) {
            Assert.assertTrue("College Fair TextBox is not displayed",textbox(By.id("college-fair-name")).isDisplayed());
            textbox(By.id("college-fair-name")).sendKeys(FairName);
        }
        if(!date.equals("")) {
            Assert.assertTrue("Date Textbox are not displayed",textbox(By.id("college-fair-date")).isDisplayed());
            WebElement datepicker = driver.findElement(By.xpath("//input[@id='college-fair-date']/following-sibling::i[@class='calendar large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
            datepicker.click();
            setSpecificDate(date);
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
            setSpecificDate(RSVPDate);
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
        // Send a JavaScript click to this control because it will be off-screen.
        jsClick(driver.findElement(By.id("college-fair-automatic-request-confirmation-no")));
        driver.findElement(By.id("college-fair-number-expected-students")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("college-fair-email-message-to-colleges")).sendKeys(Keys.PAGE_DOWN);

        if(!buttonToClick.equals("")) {
            if(buttonToClick.equals("Cancel This College Fair")) {
                Assert.assertTrue("'Cancel This College Fair' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).click();
                waitUntilPageFinishLoading();
            }else if(buttonToClick.equals("Save")){
                waitUntilElementExists(save());
                Assert.assertTrue("'Save' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Save']")).isDisplayed());
                jsClick(getDriver().findElement(By.xpath("//button/span[text()='Save']")));
                waitUntilPageFinishLoading();
            }else {
                Assert.fail("The option for the button to click ="+buttonToClick+" is not a valid one");
            }
        }
    }

    public void verifyRequestNotificationTab(String user, String university, String time,String date) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement element=button("Notifications & Tasks");
        waitUntilElementExists(element);
        button("Notifications & Tasks").click();
        waitForUITransition();
        waitUntilElementExists(confirm());
        WebElement Declinebutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+StartTime+"')]/../../following-sibling::div/button/span[text()='Decline']"));
        waitUntilElementExists(Declinebutton);
        WebElement Confirmbutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+StartTime+"')]/../../following-sibling::div/button/span[text()='Confirm']"));
        WebElement Username=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]"));
        WebElement University=driver.findElement(By.xpath("//strong[text()='"+university+"']"));
        Assert.assertTrue("user name is not displayed",Username.isDisplayed());
        Assert.assertTrue("University name is not displayed",University.isDisplayed());
        Assert.assertTrue("Confirm Button is not displayed",Confirmbutton.isDisplayed());
        Assert.assertTrue("Decline Button is not displayed",Declinebutton.isDisplayed());
    }

    public void selectoption(String option,String user,String time,String university) {
        waitUntilPageFinishLoading();
        WebElement Confirmbutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+StartTime+"')]/../../following-sibling::div/button/span[text()='Confirm']"));
        WebElement Declinebutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+StartTime+"')]/../../following-sibling::div/button/span[text()='Decline']"));
        if(option.equals("Confirm")) {
            jsClick(Confirmbutton);
        }else if(option.equals("Decline")){
            jsClick(Declinebutton);
            while(!driver.findElement(By.xpath("//span[text()='Are you sure you want to decline?']")).isDisplayed()){
                driver.findElement(By.id("global-search-box-input")).sendKeys(Keys.PAGE_UP);}
        }else{
            Assert.fail("The given option for the notification is not a valid one");
        }
    }

    public void verifyDeclinePopup(String user,String institution,String time,String date) {
        waitUntilPageFinishLoading();
        moveToElement(declinePopupMessage());
        waitUntilElementExists(goBack());
        Assert.assertTrue("Close button is not displayed",driver.findElement(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']")).isDisplayed());
        Assert.assertTrue("Decline message is not displayed",driver.findElement(By.xpath("//div/span[text()='Are you sure you want to decline?']")).isDisplayed());
        Assert.assertTrue("Attendee Details text is not displayed",driver.findElement(By.xpath("//span[text()='Attendee Details']")).isDisplayed());
        Assert.assertTrue("Text box message is not displayed",driver.findElement(By.xpath("//div/p/span[text()='Please send the attendee a message to explain why you are declining.']")).isDisplayed());
        WebElement UserName=driver.findElement(By.xpath("//strong[contains(text(),'"+user+"')]"));
        WebElement Institution=driver.findElement(By.xpath("//strong[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/strong[contains(text(),'"+institution+"')]"));
        Assert.assertTrue("user name is not displayed",UserName.isDisplayed());
        Assert.assertTrue("institution is not displayed",Institution.isDisplayed());
        Assert.assertTrue("Cancellation Message textbox is not displayed",cancellationMessage().isDisplayed());
        Assert.assertTrue("No, Go back button is not displayed",goBack().isDisplayed());
        //verify YES decline Button is disabled
        WebElement buttonDisabled = driver.findElement(By.xpath("//button[@class='ui red small disabled right floated button' and @disabled='']"));
        Assert.assertTrue("Yes, Decline button is not disabled",buttonDisabled.isDisplayed());
    }


    public void declineConfirmation(String option,String message,String user){
        if(option.equals("Yes, Decline")){
            jsClick(cancellationMessage());
            cancellationMessage().sendKeys(Keys.PAGE_DOWN);
            waitForUITransition();
            cancellationMessage().sendKeys(message);
            //verify the button is enabled After entering declining message in the textbox
            Assert.assertTrue("Yes, Decline button is not enabled",button("Yes, Decline").isEnabled());
            button(option).click();
            waitForUITransition();
            WebElement closebutton = button("Close");
            Assert.assertTrue("Close button is not displayed",closebutton.isDisplayed());
            Assert.assertTrue("User name with declined message are not displayed",driver.findElement(By.xpath("//span[text()='"+user+"']/parent::p[text()='has been declined.']")).isDisplayed());
            closebutton.click();
            navBar.goToRepVisits();
            waitUntilPageFinishLoading();
        }else if(option.equals("No, go back")){
            jsClick(cancellationMessage());
            Assert.assertTrue("No, Go back button is not displayed",goBack().isDisplayed());
            goBack().click();
            waitUntilPageFinishLoading();
        }
    }

    public void verifyRequestNotificationTabforFairs(String user, String university, String time,String date) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement element=button("Notifications & Tasks");
        waitUntilElementExists(element);
        button("Notifications & Tasks").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(confirm());
        WebElement Declinebutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+time+"')]/../../following-sibling::div/button/span[text()='Decline']"));
        waitUntilElementExists(Declinebutton);
        WebElement Confirmbutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+time+"')]/../../following-sibling::div/button/span[text()='Confirm']"));
        WebElement Username=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]"));
        WebElement University=driver.findElement(By.xpath("//strong[text()='"+university+"']"));
        Assert.assertTrue("user name is not displayed",Username.isDisplayed());
        Assert.assertTrue("University name is not displayed",University.isDisplayed());
        Assert.assertTrue("Confirm Button is not displayed",Confirmbutton.isDisplayed());
        Assert.assertTrue("Decline Button is not displayed",Declinebutton.isDisplayed());
    }

    public void verifyDeclinePopupforFairs(String user,String institution,String time,String date) {
        waitUntilPageFinishLoading();
        moveToElement(declinePopupMessage());
        WebElement UserName=driver.findElement(By.xpath("//strong[contains(text(),'"+user+"')]"));
        moveToElement(UserName);
        waitUntilElementExists(goBack());
        Assert.assertTrue("Close button is not displayed",driver.findElement(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']")).isDisplayed());
        Assert.assertTrue("Decline message is not displayed",driver.findElement(By.xpath("//div/span[text()='Are you sure you want to decline?']")).isDisplayed());
        Assert.assertTrue("Attendee Details text is not displayed",driver.findElement(By.xpath("//span[text()='Attendee Details']")).isDisplayed());
        Assert.assertTrue("Text box message is not displayed",driver.findElement(By.xpath("//div/p/span[text()='Please send the attendee a message to explain why you are declining.']")).isDisplayed());
        WebElement Institution=driver.findElement(By.xpath("//strong[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/strong[contains(text(),'"+institution+"')]"));
        Assert.assertTrue("user name is not displayed",UserName.isDisplayed());
        Assert.assertTrue("institution is not displayed",Institution.isDisplayed());
        Assert.assertTrue("Cancellation Message textbox is not displayed",cancellationMessage().isDisplayed());
        Assert.assertTrue("No, Go back button is not displayed",goBack().isDisplayed());
        //verify YES decline Button is disabled or not
        WebElement buttonDisabled = driver.findElement(By.xpath("//button[@class='ui red small disabled right floated button' and @disabled='']"));
        Assert.assertTrue("Yes, Decline button is not disabled",buttonDisabled.isDisplayed());
    }

    public void selectoptionforFairs(String option,String user,String time,String university) {
        waitUntilPageFinishLoading();
        WebElement Confirmbutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+time+"')]/../../following-sibling::div/button/span[text()='Confirm']"));
        WebElement Declinebutton=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+time+"')]/../../following-sibling::div/button/span[text()='Decline']"));
        if(option.equals("Confirm")) {
            jsClick(Confirmbutton);
            waitUntilPageFinishLoading();
        }else if(option.equals("Decline")){
            jsClick(Declinebutton);
            waitUntilPageFinishLoading();
            while(!driver.findElement(By.xpath("//span[text()='Are you sure you want to decline?']")).isDisplayed()){
                driver.findElement(By.id("global-search-box-input")).sendKeys(Keys.PAGE_UP);}
        }else{
            Assert.fail("The given option for the notification is not a valid one");
        }
    }

    public void verifyCollegeFairOverview(String fairName,String date, String attendees, String RSVPBy, String time,String viewDetails){
        navBar.goToRepVisits();
        link("College Fairs").click();
        while (link("View More Upcoming Events").isDisplayed()){
            link("View More Upcoming Events").click();
        }
        //Verify UI
        Assert.assertTrue("'Settings' button is not displayed",driver.findElement(By.xpath("//a/span[text()='SETTINGS']")).isDisplayed());
        Assert.assertTrue("'Add a College Fair' button is not displayed",driver.findElement(By.cssSelector("button[id='add-college']")).isDisplayed());
        Assert.assertTrue("'Upcoming Events' Heading ",driver.findElement(By.xpath("//div/span[text()='Upcoming Events']")).isDisplayed());
        Assert.assertTrue("'Past Events' Heading",driver.findElement(By.xpath("//div/span[text()='PAST EVENTS']")).isDisplayed());

        //Verify the Headers of the table
        List<String> columns = new ArrayList<>();
        columns.add("Fair Date");
        columns.add("Fair Name");
        columns.add("Attendees");
        columns.add("RSVP By");
        columns.add("Time");

        for (String column : columns) {
            Assert.assertTrue("column Name " + column + " is not displaying as expected!", driver.findElement(By.xpath("//table[@class='ui unstackable table']//thead/tr//th/span[text()='"+column+"']")).isDisplayed());
        }

        if (fairName.equals("PreviouslySetFair")) {
            fairName = FairName;
        }

        //Verify the data present in the table
        int nameColumnID = getColumnIdByFieldName("//table[@class='ui unstackable table']//thead", "Fair Name");
        int rowID = getRowIdByColumnId("//table[@class='ui unstackable table']//tbody", nameColumnID, fairName);
        nameColumnID = nameColumnID+1;
        rowID = rowID+1;


        if(!fairName.equals("")) {
            String actualName = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr["+rowID+"]//td["+nameColumnID+"]")).getText();
            Assert.assertTrue("Name Column is not present.",actualName.equals(fairName));
        }

        if(!date.equals("")) {
            String actualDate = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr["+rowID+"]//td[1]")).getText();
            String fairDate = verifySpecificDate(Integer.parseInt(date));
            Assert.assertTrue("Date Column is not present.",actualDate.contains(fairDate));

        }
        if(!attendees.equals("")) {
            String actualcollegesRegistered = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr["+rowID+"]//td[3]")).getText();
            Assert.assertTrue("Colleges Registered Column is not present.",actualcollegesRegistered.equals(attendees));
        }
        if(!RSVPBy.equals("")) {
            String actualRSVPBy = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr["+rowID+"]//td[4]")).getText();
            String RSVPfairDate = verifySpecificDate(Integer.parseInt(RSVPBy));
            Assert.assertTrue("RSVP By Column is not present.",actualRSVPBy.equals(RSVPfairDate));
        }
        if(!time.equals("")) {
            String actualTime = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr["+rowID+"]//td[5]")).getText();
            Assert.assertTrue("RSVP By Column is not present.",actualTime.equals(time));
        }
        if(!viewDetails.equals("")){
            WebElement actualviewDetails = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr["+rowID+"]//td//a/span[text()='View Details']"));
            actualviewDetails.isDisplayed();
        }
    }

    public void verifyExportButtonInCalendar(){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitForUITransition();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Export button is not enabled",button("Export").isEnabled());
    }

    public void exportAppointmentsInCalendarPage(String startDate,String endDate){
        button("Export").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Export Settings text is not displayed in the Export button",driver.findElement(By.xpath("//div/span[text()='Export Settings']")).isDisplayed());
        Assert.assertTrue("From Text is not displayed in the Export button",driver.findElement(By.xpath("//span[text()='From']")).isDisplayed());
        Assert.assertTrue("To text is not displayed in the Export button",driver.findElement(By.xpath("//span[text()='To']")).isDisplayed());
        String From = selectdateforExportAppointmentsIncalendar(startDate);
        setDateforCalendarPage(From, "From");
        String To = selectdateforExportAppointmentsIncalendar(endDate);
        setDateforCalendarPage(To, "To");
        Assert.assertTrue("Download button is not displayed",button("Download").isDisplayed());
        button("Download").click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void verifyDownloadedCsvFileInCalendar(String csvFile,DataTable headers){
        List<String> expectedHeaderValues=headers.asList(String.class);
        String home = System.getProperty("user.home");
        String[] actualHeaderValues = CsvFileUtility.getCsvHeaders(String.format("%s/Downloads/%s",home,csvFile));
        for(int i=0; i<expectedHeaderValues.size(); i++){
            Assert.assertTrue(String.format("The Csv header is not correct, actual: %s, expected: %s ",
                    actualHeaderValues[i], expectedHeaderValues.get(i)),
                    actualHeaderValues[i].contains(expectedHeaderValues.get(i)));
        }
    }

    public void deleteDownloadedFileInCalendar(String filePath){
        String home = System.getProperty("user.home");
        FileManager.deleteFile(String.format("%s/Downloads/%s",home,filePath));
    }

    public void verifySuccessMessage(String originalMessage){
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']"),1));
        String successMessage = driver.findElement(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/parent::div")).getText();
        Assert.assertTrue("Success Message is not equal",successMessage.equals(originalMessage));
    }

    public void setDateforCalendarPage(String date,String fromOrTo){
        String[] parts = date.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        if(fromOrTo.equals("From")){
            driver.findElement(By.xpath("//button[@class='ui basic button _1wqaQnL4wzTmKK7w_sXTwT']")).click();
        }else if (fromOrTo.equals("To")){
            driver.findElement(By.xpath("//button[@class='ui basic button _2pj6YkRYwl9szEe_wh7dxF']")).click();
        }else {
            logger.info("Invalid option");
        }
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void setSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        if (addDays.length() > 2) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(addDays, formatter);
            int days = date.getMonthValue();
            cal.add(Calendar.DATE, days);
        }
        else {
            cal = getDeltaDate(Integer.parseInt(addDays));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void setSpecificDateforManuallyCreatingVisit(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public HashMap<String, Integer> getDateCellIndicesInGrid(WebElement dateCell)
    {
        HashMap<String, Integer> dateCellIndices = new HashMap<String, Integer>();

        WebElement weekRow = dateCell.findElement(By.xpath(".//ancestor::div[@class='rbc-month-row']"));
        List<WebElement> weekRows = driver.findElements(By.xpath("//div[@class='rbc-month-row']"));

        int rowIndex = weekRows.indexOf(weekRow);

        WebElement dayColumn = dateCell.findElement(By.xpath(".//ancestor::div[contains(@class, 'rbc-date-cell') and not(contains(@class, 'rbc-off-range'))]"));
        List<WebElement> dayColumns = dateCell.findElements(By.xpath("./ancestor::div[@class='rbc-row']//div[contains(@class, 'rbc-date-cell')]"));

        int columnIndex = dayColumns.indexOf(dayColumn);

        dateCellIndices.put("ROW_INDEX", new Integer(rowIndex + 1));
        dateCellIndices.put("COLUMN_INDEX", new Integer(columnIndex + 1));

        return dateCellIndices;
    }


    public String getSpecificDate(int addDays, String format) {
        String DATE_FORMAT_NOW = "MMMM d, yyyy";

        if(format != null)
          DATE_FORMAT_NOW = format;

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

    public void verifyUIofNavianceSettingsPageinSetupWizard() {
       Assert.assertTrue("'Connecting Naviance and RepVisits' Title is not displayed",getDriver().findElement(By.xpath("//span[text()='Connecting Naviance and RepVisits']")).isDisplayed());
       Assert.assertTrue("Text under the Title 'Connecting Naviance and RepVisits' is not displayed",getDriver().findElement(By.xpath("//span[text()='By Connecting RepVisits and Naviance, all of your Naviance College Visits will be managed within RepVisits unless you choose to opt-out of the integration in the future.']")).isDisplayed());
       Assert.assertTrue("'How the RepVisits-Naviance Connection Works:' Title is not displayed",getDriver().findElement(By.xpath("//span[text()='How It Works']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection Works:' is not displayed",getDriver().findElement(By.xpath("//span[text()='Appointments scheduled via RepVisits can be published to Naviance College Visits (so students can see them and sign up).']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection Works:' is not displayed",getDriver().findElement(By.xpath("//span[text()='If a visit is rescheduled or cancelled in RepVisits, it will automatically update in Naviance College Visits and Family Connection.']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection Works:' is not displayed",getDriver().findElement(By.xpath("//span[text()='You will be able to view your visits and student registrations directly in Naviance (staff view). However, you will need to use RepVisits to create, edit, or cancel visits.']")).isDisplayed());
       Assert.assertTrue("'How the RepVisits-Naviance Connection is Set Up:' Title is not displayed",getDriver().findElement(By.xpath("//span[text()='Set Up']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed",getDriver().findElement(By.xpath("//span[text()='Publishing Options: You will be able to specify whether RepVisits should be published to Naviance College Visits automatically or manually on a visit-by-visit basis.']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed",getDriver().findElement(By.xpath("//span[text()='Visit Settings: You will be able to create default settings for Naviance College Visits (location, notes, registration deadline, maximum number of students) within RepVisits. You will also be able to adjust these settings on a visit-by-visit basis.']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed",getDriver().findElement(By.xpath("//span[normalize-space(text())='Importing from Naviance: Your current College Visits in Naviance will be imported into RepVisits. We will automatically match these to any existing visits in RepVisits for the same colleges on the same day. You will have a chance to review and validate these matches before the Naviance and RepVisits entries are linked.']")).isDisplayed());
       Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed",getDriver().findElement(By.xpath("//span[text()='Opt-In/Opt-Out: Once you choose to connect RepVisits and Naviance, you will have the option to disconnect the sync by returning to Naviance Settings.']")).isDisplayed());
       Assert.assertTrue("Radio button with the label 'Yes, I would like to connect Naviance and RepVisits' is not displayed",getDriver().findElement(By.xpath("//label[text()='Yes, I would like to connect Naviance and RepVisits']/input[@type='radio']")).isDisplayed());
       Assert.assertTrue("Radio button with the label 'No, I would like to use RepVisits without the Naviance integration' is not displayed",getDriver().findElement(By.xpath("//label[text()='No, I would like to use RepVisits without the Naviance integration']/input[@type='radio']")).isDisplayed());
    }

    public String selectdate(String addDays)
    {
        String DATE_FORMAT_NOW = "EEEE, MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifynoNotificationMessage(String message) {
        navBar.goToRepVisits();
        WebElement element=button("Notifications & Tasks");
        waitUntilElementExists(element);
        button("Notifications & Tasks").click();
        waitForUITransition();
        removeNotificationRequestSubtab("QA Declined","Yes, Decline");
        waitForUITransition();
        Assert.assertTrue("'You currently have no notifications' is not displayed",text(message).isDisplayed());
    }

    public void removeNotificationRequestSubtab(String message,String submit){
        List<WebElement> Notificationsize = driver.findElements(By.xpath("//button[@class='ui mini basic primary button _3wYCijG-cEpNomL_5h1LcD']"));
        while(Notificationsize.size()>0){
            WebElement button = driver.findElement(By.xpath("//button[1]/span[text()='Decline']"));
            jsClick(button);
            waitUntilPageFinishLoading();
            jsClick(cancellationMessage());
            cancellationMessage().sendKeys(message);
            button(submit).click();
            waitUntilPageFinishLoading();
            waitForUITransition();
            WebElement bt =  getDriver().findElement(By.cssSelector("div[id='success-message-grid']>button>span"));
            jsClick(bt);
            Notificationsize = driver.findElements(By.xpath("//button[@class='ui mini basic primary button _3wYCijG-cEpNomL_5h1LcD']"));
        }}

    public void cancelRgisteredCollegeFair(String edit,String fairName){
        Assert.assertTrue("Edit button is not displayed",button(edit).isDisplayed());
        button(edit).click();
        waitUntilPageFinishLoading();
        String displayedFairName = driver.findElement(By.id("college-fair-name")).getAttribute("value");
        Assert.assertTrue("FairName is displayed",displayedFairName.equals(FairName));
        driver.findElement(By.id("college-fair-start-time")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("college-fair-email-message-to-colleges")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This College Fair button is not displayed",button("Cancel This College Fair").isDisplayed());
        button("Cancel This College Fair").click();
        waitUntilPageFinishLoading();
//        driver.findElement(By.id("college-fair-cancellation-message")).sendKeys("by QA");
//        driver.findElement(By.id("college-fair-cancellation-message")).sendKeys(Keys.PAGE_DOWN);
        button("Yes, Cancel this fair").click();
        waitUntilPageFinishLoading();
        button("Close").click();
        waitUntilPageFinishLoading();
    }

    public String selectdateforExportAppointmentsIncalendar(String addDays)
    {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }
  
   public String getcurrentBlockedDate(String addDays) {
        String DATE_FORMAT_NOW = "MM/dd/yy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    private void setDefaultDateforStartAndEndDate(){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        String date = verifyStartDate();
        if(verifyDateIsEnabledOrDisabled(date)){
            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+" and @aria-disabled='false']")).click();
            date = verifyEndDate();
            if(verifyDateIsEnabledOrDisabled(date)) {
                driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+" and @aria-disabled='false']")).click();
            }
        }else {
            date = verifyEndDate();
            if(verifyDateIsEnabledOrDisabled(date)) {
                driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+" and @aria-disabled='false']")).click();
            }
            date = verifyStartDate();
            if(verifyDateIsEnabledOrDisabled(date)) {
                driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+" and @aria-disabled='false']")).click();
            }
        }
        clickUpdateButtonInRepVisits();
    }

    private boolean verifyDateIsEnabledOrDisabled(String date){
        boolean enabledDate = false;
        List<WebElement> enabledOrDisabled = driver.findElements(By.xpath("//div[text()="+date+" and @aria-disabled='false']"));
        if(enabledOrDisabled.size()!=0) {
            enabledDate = true;
        } else if(enabledOrDisabled.size()==0){
            enabledDate = false;
        }else {
            logger.info("The Date selected is out of RANGE.");
        }
        return enabledDate;
    }

    private String verifyStartDate(){
        String startDate=getSpecificDate("-1");
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        String[] parts = startDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        String date = parts[1];
        findMonth(calendarHeading,"Start");
        return date;
    }
    private String verifyEndDate(){
        String endDate=getSpecificDate("1");
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        String[] parts = endDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        String date = parts[1];
        findMonth(calendarHeading,"End");
        return date;
    }

    public void clickLinkNotificationsAndTasks() {
        navBar.goToRepVisits();
        link("Notifications & Tasks").click();
    }

    public void verifyTextAskingHSUserForFeedbackOnHEVisit() {

        if(text("No visits to submit feedback, yet").isDisplayed()) {
            logger.warn("'No visits to submit feedback, yet' message is displayed");
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, 10);

        List<WebElement> listOfEntriesInPendingTab = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='_3eL2jveDiva_TtJk49-Jdt']")));
        List<WebElement> listOfEntriesContainingText = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='_3eL2jveDiva_TtJk49-Jdt']//*[contains(text(), 'has asked for feedback on their recent visit.')]")));


        Assert.assertEquals("'Asked for feedback' message is not displayed in all entries in pending tab", listOfEntriesInPendingTab.size(), listOfEntriesContainingText.size());
    }

    public void clickLinkVisitFeedback() {
        link("Visit Feedback").click();
    }

    public void createFairWithGeneratedDate(String daysFromNow, DataTable fairDetails) {
        Calendar calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow));
        generatedDate = getMonth(calendarStartDate);
        generatedDateDayOfWeek = getDayOfWeek(calendarStartDate) + " " + getDay(calendarStartDate);
        Calendar calendarRSVPDate = getDeltaDate(Integer.parseInt(daysFromNow) - 1);
        waitForUITransition();
        addFairButton().click();
        waitForUITransition();
        dateCalendarIcon().click();
        pickDateInDatePicker(calendarStartDate);
        rsvpCalendarIcon().click();
        pickDateInDatePicker(calendarRSVPDate);
        fillFairForm(fairDetails);
        saveButton().click();
    }

    public void fillFairForm(DataTable fairDetails) {
        List<List<String>> details = fairDetails.asLists(String.class);
        for (List<String> row : details) {
            switch (row.get(0)) {
                case "Name":
                    fairNameTextBox().sendKeys(row.get(1));
                    break;
                case "Date":
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyy");
                    dateCalendarIcon().click();
                    Calendar date = Calendar.getInstance();
                    try {
                        Date formattedDate = formatter.parse(row.get(1));
                        date.setTime(formattedDate);
                        pickDateInDatePicker(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case "Start time":
                    startTimeTextBox().sendKeys(row.get(1));
                    time = row.get(1).replaceFirst("0", "");
                    break;
                case "End time":
                    endTimeTextBox().sendKeys(row.get(1));
                    break;
                case "RSVP deadline":
                    rsvpCalendarIcon().click();
                    SimpleDateFormat rsvpFormatter = new SimpleDateFormat("MM-dd-yyy");
                    Calendar rsvpDate = Calendar.getInstance();
                    try {
                        Date formattedDate = rsvpFormatter.parse(row.get(1));
                        rsvpDate.setTime(formattedDate);
                        pickDateInDatePicker(rsvpDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case "Cost":
                    costTextBox().sendKeys(row.get(1));
                    break;
                case "Max colleges":
                    maxNumOfColleges().sendKeys(row.get(1));
                    break;
                case "Max students":
                    numOfStudents().sendKeys(row.get(1));
                    break;
                case "Instructions":
                    instructionsTextBox().sendKeys(row.get(1));
                    break;
                case "Auto Approvals":
                    if (row.get(1).equals("Yes")) {
                        autoApprovalYesRadButton().click();
                    } else if (row.get(1).equals("No")) {
                        autoApprovalNoRadButton().click();
                    }
            }
        }
    }

    /**
     * Schedules a new visit
     * @param day
     * @param time
     * @param representativeFirstName
     * @param representativeLastName
     * @param representativeInstitution
     * @param location
     * @param maxNumberOfStudents
     * @param registrationWillClose
     */
    public void scheduleNewVisit(String day, String time, String representativeFirstName, String representativeLastName,
                                 String representativeInstitution ,String location, String maxNumberOfStudents,
                                 String registrationWillClose) {
        navBar.goToRepVisits();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        getAddVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(
                ".//h1/span[text()='schedule new visit']"), 1));
        Map<String, String> days = new HashMap<String, String>();
        days.put("Mon", "0");
        days.put("Tue", "1");
        days.put("Wed", "2");
        days.put("Thu", "3");
        days.put("Fri", "4");
        WebElement table = driver.findElement(By.xpath(".//tbody"));
        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
        boolean timeSlotWasSelected = false;
        for (WebElement row : rows) {
            List<WebElement> timeSlots = row.findElements(By.xpath(".//td"));
            if (!timeSlots.get(Integer.parseInt(days.get(day))).findElements(By.xpath(
                    String.format(".//button[text()='%s']", time))).isEmpty()) {
                timeSlots.get(Integer.parseInt(days.get(day))).click();
                timeSlotWasSelected = true;
                break;
            }
        }
        if (timeSlotWasSelected) {
            Actions action = new Actions(getDriver());
            action.sendKeys(Keys.PAGE_DOWN).build().perform();
            waitForUITransition();
            getAddRepresentativeManuallyLink().click();
            getRepresentativeFirstNameTextBox().sendKeys(representativeFirstName);
            action.sendKeys(Keys.PAGE_UP).build().perform();
            getRepresentativeLastName().sendKeys(representativeLastName);
            action.sendKeys(Keys.PAGE_DOWN).build().perform();
            getRepresentativeInstitutionTextBox().sendKeys(representativeInstitution);
            action.sendKeys(Keys.TAB).sendKeys(Keys.TAB).build().perform();
            getEventLocationTextBox().sendKeys(location);
            getMaxNumberOfStudentsTextBox().sendKeys(maxNumberOfStudents);
            getRegistrationWillCloseTextBox().sendKeys(registrationWillClose.split(" ")[0]);
            getRegistrationWillCloseDropDown().click();
            getDriver().findElement(By.xpath(String.format(".//span[text()='%s']",
                    registrationWillClose.split(" ")[1]))).click();
            action.sendKeys(Keys.TAB).build().perform();
            getInternalNotesLabel().click();
            action.sendKeys(Keys.END).build().perform();
            waitForUITransition();
            getAddVisitCalendarAppointmentButton().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(
                    By.xpath(".//form[@id='add-calendar-appointment']/div/button/span[text()='add visit']"), 0));
        } else {
            new AssertionFailedError(String.format("The time slot with day: %s and time: %s could not be selected"
                    , day, time));
        }
    }

    /**
     * Verifies if the block this time slot button is displayed when clicking on a slot time in the exceptions tab
     *
     * @param day  of the time slot to be selected
     * @param time of the time slot to be selected
     */
    public void verifyBlockThisTimeSlotButtonIsDisplayed(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Exceptions").click();
        selectTimeSlot(day, time);
        Assert.assertTrue("Block this time slot button is not present", getBlockThisTimeSlotButton()
                .isDisplayed());
        getCancelExceptionButton().click();
    }

    /**
     * Clicks a time slot given the day and time in the exceptions tab
     *
     * @param time, string with the time to find a slot time, example: 12:00AM
     * @param day,  string with the day to find a slot time, it could be: MON, TUE, WED, THU, FRI
     */
    public void selectTimeSlot(String day, String time) {
        button(By.xpath(String.format(".//table[.//caption/span ='%s']//button[text()='%s']", day, time)))
                .click();
    }

    /**
     * Verifies that the block this time tooltip is displayed when blocking a time slot in exceptions tab
     *
     * @param day  of the time slot to be selected
     * @param time of the time slot to be selected
     */
    public void verifyBlockThisTimeSlotToolTipIsDisplayed(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Exceptions").click();
        selectTimeSlot(day, time);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getWhatDoesThisMeanLabel()).build().perform();
        Assert.assertTrue("Block this time tool tip is not present", getBlockThisTimeSlotToolTip()
                .isDisplayed());
        getCancelExceptionButton().click();
    }

    /**
     * Blocks a given time slot
     *
     * @param day,  the day of the time slot to block
     * @param time, the time of the time slot to block
     */
    public void blockTimeSlot(String day, String time) {
        selectTimeSlot(day, time);
        getBlockThisTimeSlotButton().click();
        waitForUITransition();
    }

    /**
     * Verifies if the unblock this time slot button is displayed when clicking on a slot time in the exceptions tab
     *
     * @param day  of the time slot to be slected
     * @param time of the time slot to be selected
     */
    public void verifyUnblockThisTimeSlotButtonIsDisplayed(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Exceptions").click();
        selectTimeSlot(day, time);
        Assert.assertTrue("Unblock this time slot button is not present", getUnblockThisTimeSlotButton()
                .isDisplayed());
        getCancelExceptionButton().click();
    }

    /**
     * @param day,  the day of the slot to check the blocked label
     * @param time, the time of the slot to check the blocked label
     */
    public void verifyBlockedLabelIsDisplayedInTimeSlot(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Exceptions").click();
        Assert.assertTrue(String.format("The blocked label is not dosplayed in the time slot with day: %s and time: %s"
                , day, time), getDriver().findElement(By.xpath(String.format(
                ".//table[.//caption/span ='%s']//button[text()='%s']/preceding-sibling::span[text()='Blocked']"
                , day, time))).isDisplayed());
    }

    /**
     * Verifies if an appointment can be set in the calendar tab for a given slot time
     *
     * @param day,  the day of the slot time to try to set the appointment, Mon, Tue, Wed, Thu, Fri
     * @param time, the time of the slot time to try to set the appointment, 2:30PM
     */
    public void verifyNewVisitCannotBeSet(String day, String time) {
        navBar.goToRepVisits();
        link("Calendar").click();
        getAddVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(
                ".//h1/span[text()='schedule new visit']"), 1));
        WebElement table = driver.findElement(By.xpath(".//tbody"));
        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
        for (WebElement row : rows) {
            List<WebElement> timeSlots = row.findElements(By.xpath(".//td"));
            if (timeSlots.size() > 1) {
                try {
                    (new WebDriverWait(getDriver(), 3))
                            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format("//button[text()='$s']", time))));
                    new AssertionFailedError(String.format
                            ("It is possible to set a new visit with day: %s and time: %s", day, time));
                } catch (Exception e) {
                }
            }
        }
        getCloseScheduleNewVisitButton().click();
        waitForUITransition();
    }

    /**
     * Unblocks a given time slot
     *
     * @param day,  the day of the time slot to unblock
     * @param time, the time of the time slot to unblock
     */
    public void unblockTimeSlot(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Exceptions").click();
        selectTimeSlot(day, time);
        getUnblockThisTimeSlotButton().click();
        waitForUITransition();
    }

    /**
     * Verifies that the blocked label is not displayed for a given time slot
     *
     * @param day  of the time slot to be verified
     * @param time of the time slot to be verified
     */
    public void verifyBlockedLabelIsNotDisplayedInTimeSlot(String day, String time) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Exceptions").click();
        try {
            (new WebDriverWait(getDriver(), 3)).until(ExpectedConditions.
                    presenceOfElementLocated(By.xpath(String.format(
                            ".//table[.//caption/span ='%s']//button[text()='%s']/preceding-sibling::span[text()='Blocked']"
                            , day, time))));
            new AssertionFailedError(String.format("The blocked label is displayed for time slot with day: %s and time: %s"
                    , day, time));
        } catch (Exception e) {
        }
    }

    /**
     * Verifies the number of visits of a given time slot in exceptions tab
     *
     * @param day,            the day of the slot to verify the number of visits
     * @param time,           the time of the slot to verify the number of visits
     * @param numberOfVisits, the expected number of visits for the given time slot
     */
    public void verifyNumberOfVisits(String day, String time, String numberOfVisits) {
        selectTimeSlot(day, time);
        String actualNumberOfVisits = getNumberOfVisitsTextBox().getAttribute("value");
        Assert.assertEquals("The number of visits are not equal, actual: " + actualNumberOfVisits + ", expected: " +
                numberOfVisits, numberOfVisits, actualNumberOfVisits);
    }

    /**
     * Cancels a visit in the calendar
     *
     * @param time,    the time of the appointment to be canceled
     * @param college, the college of the appointment to be canceled
     * @param note,    the note to cancel the appointment
     */
    public void cancelVisit(String time, String college, String note) {
        navBar.goToRepVisits();
        link("Calendar").click();
        waitUntil(ExpectedConditions.visibilityOf(getAddVisitButton()));
        waitForUITransition();
        getDriver().findElement(By.xpath(String.format(".//div[span/text()='%s' and span/text()='%s']", time, college))).click();
        waitForUITransition();
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.END).build().perform();
        getCancelThisVisitButon().click();
        getMessageRegardingCancellationTextBox().sendKeys(note);
        getYesCancelVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("repVisit-cancelation-message"), 0));
        waitForUITransition();
    }

    /**
     * Adds atendees to a college fair that was just created.
     * Prerequisite:  User has the "Close or Add Attendees" drawer open after saving a new college fair.
     * @param dataTable Data Table containing all the names for the attendees to be added.
     */
    public void addAttendees(DataTable dataTable) {
        getAddAttendeesButton().click();
        List<String> names = dataTable.asList(String.class);
        for (String name : names) {
            getAddAttendeeSearchBox().sendKeys(name);
            getDriver().findElement(By.xpath("//div[text()[contains(.,'"+name+"')]]")).click();
        }
        getDriver().findElement(By.xpath("//button/span[text()='Add Attendees']")).click();
    }

    public void verifyTimeslotInException(String date, String time)
    {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Exceptions").click();
        String startDate = getSpecificDate(date);
        setDate(startDate, "other");
        String formattedDate = selectCurrentDate(date);
        waitForUITransition();
        //verify Timeslot
        if (time.equals("PreviouslySetTime"))
            time = StartTime;
        Assert.assertTrue("Timeslot is not displayed",driver.findElement(By.xpath("//div/span[text()='"+formattedDate+"']/parent::div/parent::th/parent::tr/parent::thead/following-sibling::tbody/tr/td/div/button[text()='"+time+"']")).isDisplayed());
    }

    public String selectCurrentDate(String addDays)
    {
        String DATE_FORMAT_NOW = "MM/dd/yy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifyStaffNotifications(String primaryContactName, String alternativePrimaryContact) {
        navBar.goToRepVisits();
        link("College Fairs").click();
        waitUntilPageFinishLoading();
        link("CollegeFairs/Settings").click();
        waitUntilPageFinishLoading();

        /* Primary contact is selected using a drop down verification */
        WebElement PrimaryContactDropDown = primaryContact();
        primaryContact().click();
        primaryContactName(primaryContactName).click();
        Assert.assertTrue("Primary Contact was not found.", PrimaryContactDropDown.findElement(By.className("text")).getText().contains(primaryContactName));

        /* Dropdown is populated with a list of all community members that are tied to my school */

        /* Primary contact is shown in the list of users (check boxes below), but cannot be unchecked (as primary must always receive notifications) */

        Assert.assertFalse("Primary Contact can be checked.", checkbox(By.cssSelector("[name='Q29tbXVuaXR5UGVyc29uOjczNTg1MTAwLWVkZWUtYTQ4NS0xNmMzLTNhMjQzNmMyMjIxZg==']")).isEnabled());

        /* If primary contact is changed, previous primary user becomes checkable in the list and currently selected primary user is checked and not un-checkable.   */

        primaryContact().click();
        primaryContactName(alternativePrimaryContact).click();
        Assert.assertTrue("Primary Contact was not found.", PrimaryContactDropDown.findElement(By.className("text")).getText().contains(alternativePrimaryContact));
        Assert.assertFalse("New Primary Contact can be checked.", checkbox(By.cssSelector("[name='Q29tbXVuaXR5UGVyc29uOjBjOTlmZDdjLTNkNjctNDhkMC1iZmFiLTY0M2Q1NDUyNDkwYQ==']")).isEnabled());

        /* Heading: "Notifications for Visits"  */
        Assert.assertTrue("Notification Header was not found.", getDriver().findElement(By.cssSelector("div[class='ui header']:nth-child(3)")).getText().contains("Notifications for Fairs"));

        /* Text: "Choose users who will receive mail notifications. Users are notified when visit requests are made, confirmed, denied, rescheduled, canceled, or manually added."  */

        waitUntilPageFinishLoading();
        String textNotificationVerification = getDriver().findElement(By.cssSelector("form[id='form-repvisits-notifications-and-fair-settings']>span")).getText();
        Assert.assertTrue("Notification Header was not found.", textNotificationVerification.
                contains("Choose users who will receive email notifications. Users are notified when visit requests are made, confirmed, denied, rescheduled, canceled or manually added."));

        /*  A list of all community members from the current high school is shown.
            The list is in two columns
            The list includes a check box next to each name
            Primary contact's check box (a) is checked, and (b) cannot be unchecked */

        Assert.assertTrue("Two columns are not displayed",getDriver().findElement(By.cssSelector("div[class='grouped fields _3wL_DuaLhBL9_OQEhGZg0p']")).isDisplayed());
        Assert.assertTrue("Two columns are not displayed",getDriver().findElement(By.cssSelector("div[class='grouped fields _3wL_DuaLhBL9_OQEhGZg0p']:nth-child(2)")).isDisplayed());

        Assert.assertFalse("Primary Contact can be checked.", checkbox(By.cssSelector("[name='Q29tbXVuaXR5UGVyc29uOjBjOTlmZDdjLTNkNjctNDhkMC1iZmFiLTY0M2Q1NDUyNDkwYQ==']")).isEnabled());

        Assert.assertTrue("Check boxes for Primary contacts are not displayed ",getDriver().findElement(By.cssSelector("div[class='ui checkbox']")).isDisplayed());

        /* When a user is checked, and the save changes button has been clicked, that user receives notifications when visit requests are made, confirmed, denied, rescheduled, canceled, or manually added. */

        primaryContact().click();
        primaryContactName(alternativePrimaryContact).findElement(By.xpath("//span[@class='text'][contains(text(), '"+ alternativePrimaryContact +"')]")).click();
        checkbox(By.cssSelector("button[class='ui primary right floated button'")).click();

    }

    public void verifyCollegeFairNotificationWasReceived(String collegeFair, String attendee) {
        if (collegeFair.equals("PreviouslySetFair"))
            collegeFair = FairName;
        navBar.goToRepVisits();
        link("Notifications & Tasks").click();
        waitUntilPageFinishLoading();
        link("Activity").click();
        waitUntilPageFinishLoading();
        getDriver().findElements(By.xpath("//span[text()='View Full Details']")).get(0).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Fairs does not contains New Fair", getDriver().findElement(By.cssSelector("div[class='ui segments _3pzgJh2J1gbNBaq2S9asNJ']")).getText().contains(collegeFair));
        Assert.assertTrue("Fairs does not contains Attendee name", getDriver().findElement(By.cssSelector("div[class='ui segments _3pzgJh2J1gbNBaq2S9asNJ']")).getText().contains(attendee));
    }

    public void verifyNotificationsToNonMembersSection(String correctEmail, String incorrectEmail) {
        navBar.goToRepVisits();
        link("College Fairs").click();
        waitUntilPageFinishLoading();
        link("CollegeFairs/Settings").click();
        waitUntilPageFinishLoading();
        try {
            Assert.assertTrue("Notification Area for Non Community Does not exist.", getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).isDisplayed());
            getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).clear();
            getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).sendKeys(incorrectEmail);
            getDriver().findElement(By.cssSelector("button[class='ui primary right floated button']")).click();
            Assert.assertTrue("Invalid email format error isn't being shown correctly", getDriver().findElement(By.xpath("//span[text() = 'Emails must be valid and separated by comma']")).isDisplayed());
            getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).clear();
            getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).sendKeys(correctEmail);
            getDriver().findElement(By.cssSelector("button[class='ui primary right floated button']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Saved was not successfully", getDriver().findElement(By.cssSelector("div[class='ui small icon success message toast _2Z22tp5KKn_l5Zn5sV3zxY']")).getText().contains("You've updated College Fair settings"));
        }catch (Exception e) {
            logger.info("Notifications displayed in bad format: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Notification it's failing.");
        }
    }
  
   public void verifyCalendarPageForaddVisit() {  
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        Assert.assertTrue("add visit button is not displayed",addVisitButton().isDisplayed());
        Assert.assertTrue("calendar page is not displayed",calendar().isDisplayed());
        addVisitButton().click();
    }

    public void verifyVisitSchedulepopup() {
        waitForUITransition();
        waitUntilElementExists(gotoDateButtonInVisitSchedulePopup());
        Assert.assertTrue("schedule new visit is not displayed",textInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("select an available time slot is not displayed",verifyTextInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("Go to date is not displayed",gotoDateButtonInVisitSchedulePopup().isDisplayed());
        addAttendee().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        Assert.assertTrue("event location is not displayed",eventLocationTextboxInAddVisitSchedulePopup().isDisplayed());
        eventLocationTextboxInAddVisitSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        Assert.assertTrue("internal notes is not displayed",internalNotesTextBoxInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("add visit button is not displayed",verifyAddVisitButtonInVisitSchedulePopup().isDisplayed());
        internalNotesTextBoxInVisitSchedulePopup().sendKeys(Keys.PAGE_UP);
        eventLocationTextboxInAddVisitSchedulePopup().sendKeys(Keys.PAGE_UP);
        waitForUITransition();
        findRepTextbox().sendKeys(Keys.PAGE_UP);
        waitForUITransition();
    }

    public void removeManuallyAddedBlockedDate(String startDate, String endDate){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        waitUntilPageFinishLoading();
        link("Blocked Days").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        startDate = getSpecificDateFormat(startDate);
        //endDate = getSpecificDateFormat(endDate);
        WebElement BlockedDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='"+startDate+"']/../following-sibling::td[@class='_1DmNQ0_pLQlqak2JJluwxn']/span"));
        moveToElement(BlockedDate);
        jsClick(BlockedDate);
        waitUntilPageFinishLoading();
    }

    public void setSpecificBlockedDate(String reason,String blockdate){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Blocked Days").click();
        chooseDates().click();
        String Date=getSpecificDate(blockdate);
        setDateDoubleClick(Date);
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        selectReason.click();
        waitUntilPageFinishLoading();
        WebElement element = driver.findElement(By.xpath("//span[text()='"+reason+"']"));
        moveToElement(element);
        jsClick(element);
        addBlockedTime().click();
    }

    public String getSpecificDateFormat(String addDays) {
        String DATE_FORMAT_NOW = "MMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }
  
     public void navigateToException() {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Availability & Settings").click();
        exceptionLink().click();
        waitForUITransition();
    }

    public void verifyBlockedDayInException(String date) {
        Assert.assertTrue("Date button is not displayed", dateButtonInException().isDisplayed());
        dateButtonInException().click();
        setSpecificDate(date);
        waitForUITransition();
        String currentDate = getcurrentBlockedDate(date);
        Assert.assertTrue("Blocked day is not displayed", driver.findElement(By.xpath("//div/span[text()='" + currentDate + "']/parent::div/following-sibling::div/span[text()='Blocked - Holiday']")).isDisplayed());
    }

    /*locators for Messaging Options Page*/

    private WebElement primaryContact() {
        return getDriver().findElement(By.cssSelector("div[name='primaryContact'"));
    }
    private WebElement primaryContactName(String primaryContactName) {
        return getDriver().findElement(By.xpath("//span[@class='text'][contains(text(), '"+ primaryContactName +"')]"));
    }

    private WebElement getWebInstructions() {
        return getDriver().findElement(By.id("webInstructions"));
    }

    private WebElement getAddAttendeesButton() { return getDriver().findElement(By.id("next-action-add"));}

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

    private WebElement getVisitsFeedbackBtn() {return link("Visit Feedback"); }

    private WebElement saveChanges()
    {
        WebElement saveChanges=button("Save Changes");
        waitUntilElementExists(saveChanges);
        return  saveChanges;
    }

    private WebElement selectDay()
    {
        WebElement selectDay=driver.findElement(By.xpath("//div[@class='ui button labeled dropdown icon QhYtAi_-mVgTlz73ieZ5W']"));
        waitUntilElementExists(selectDay);
        return selectDay;
    }

    private  WebElement submit()
    {
        WebElement submit=driver.findElement(By.cssSelector("button[class='ui primary button']"));
        waitUntilElementExists(submit);
        return  submit;
    }

    private WebElement confirm()
    {
        WebElement button=button("Confirm");
        waitUntilElementExists(button);
        return button;
    }

    private WebElement close()
    {
        WebElement button=button("Close");
        return  button;
    }

    private WebElement goBack()
    {
        WebElement button=button("No, go back");
        waitUntilElementExists(button);
        return button;
    }

    private String day(String day)
    {
        String date=selectdate(day);
        String selectDay[]=date.split(",");
        String currentDay=selectDay[0];
        return  currentDay;
    }

    private WebElement startOrEndDate()
    {
        WebElement date=driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']"));
        return  date;
    }

    private WebElement addTimeSlot()
    {
        WebElement add= button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']"));
        return  add;
    }

    private WebElement availabilityButton()
    {
        WebElement pills= driver.findElement(By.cssSelector("button[class='ui small button IHDZQsICrqtWmvEpqi7Nd']"));
        return pills;
    }

    private WebElement availabilityEndtimeTextbox()
    {
        WebElement textbox=driver.findElement(By.id("availability-end-time"));
        return textbox;
    }

    private WebElement addTimeSlotSubmit()
    {
        WebElement submit=driver.findElement(By.cssSelector("button[class='ui primary button']"));
        return  submit;
    }
    private WebElement addStartTime()
    {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='startTime']"));
        return  inputStartTime;
    }
    private WebElement addEndTime()
    {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='endTime']"));
        return  inputStartTime;
    }
    private WebElement declineButton(String user,String university,String time)
    {
        WebElement button=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+time+"')]/../../following-sibling::div/button/span[text()='Decline']"));
        return  button;
    }
    private WebElement userName(String user)
    {
        WebElement userName=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]"));
        return userName;
    }
    private WebElement university(String University)
    {
        WebElement university=driver.findElement(By.xpath("//strong[text()='"+University+"']"));
        return  university;
    }
    private WebElement confirmButton(String user,String university,String time)
    {
        WebElement button=driver.findElement(By.xpath("//div[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'"+university+"')]/parent::div/following-sibling::div/span[contains(text(),'"+time+"')]/../../following-sibling::div/button/span[text()='Confirm']"));
        return  button;
    }
    private WebElement usernameInDeclinePopup(String user)
    {
        WebElement userName=driver.findElement(By.xpath("//strong[contains(text(),'"+user+"')]"));
        return userName;
    }
    private WebElement institutionInDeclinePopup(String user,String institution)
    {
        WebElement Institution=driver.findElement(By.xpath("//strong[contains(text(),'"+user+"')]/parent::div/parent::div/following-sibling::div/div/strong[contains(text(),'"+institution+"')]"));
        return Institution;
    }
    private WebElement getDateInDeclinePopup(String institution,String time)
    {
        WebElement date=driver.findElement(By.xpath("//strong[text()='"+institution+"']/../following-sibling::div/span[contains(text(),'"+time+"')]/span"));
        return date;
    }
    private WebElement cancellationMessage()
    {
        WebElement text=driver.findElement(By.name("cancellationMessage"));
        return text;
    }
    private WebElement textBoxInDeclineConfirmation()
    {
        WebElement text=driver.findElement(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"));
        return text;
    }
    private WebElement declineNotificationMessage(String school)
    {
        WebElement msg=driver.findElement(By.xpath("//p/span[contains(text(),'"+school+"')]/parent::p[text()='has been declined.']"));
        return msg;
    }
    private WebElement addVisitButton() {
        WebElement button=button("add visit");
        return button;
    }
    private WebElement goToDateButton() {
        WebElement button=driver.findElement(By.xpath("//button/span/span[text()='Go to date']"));
        return button;
    }
    private WebElement attendeeTextBox() {
        WebElement id=driver.findElement(By.id("calendar-search-reps"));
        return id;
    }
    private WebElement selectAttendeeOrInstitution(String AttendeeOrInstitution) {
        WebElement attendee=driver.findElement(By.xpath("//div[text()='"+AttendeeOrInstitution+"']"));
        return attendee;

    }
    private WebElement eventLocationTextBox() {
        WebElement id=driver.findElement(By.xpath("//input[@aria-label='Event Location']"));
        return id;
    }
    private WebElement firstNameTextBox() {
        WebElement text=driver.findElement(By.id("add-rep-first-name"));
        return text;
    }
    private WebElement lastNameTextBox() {
        WebElement text=driver.findElement(By.id("add-rep-last-name"));
        return text;
    }
    private WebElement emailTextBox() {
        WebElement text=driver.findElement(By.id("add-rep-email"));
        return text;
    }
    private WebElement phoneNoTextBox() {
        WebElement text=driver.findElement(By.id("add-rep-phone"));
        return text;
    }
    private WebElement positionTextBox() {
        WebElement text=driver.findElement(By.id("add-rep-position"));
        return text;
    }
    private WebElement institutionTextBox() {
        WebElement text=driver.findElement(By.id("add-rep-institution"));
        return text;
    }
    private WebElement confirmationMessageCreatedVisit(String message) {
        String successMsg[] = message.split(",");
        WebElement msg=driver.findElement(By.xpath("//span[text()='"+successMsg[0]+"']/following-sibling::span[text()='"+successMsg[1]+"']"));
        return msg;
    }
    private WebElement addcontactManually() {
        WebElement link=driver.findElement(By.xpath("//span[text()='Not in the list? Add them manually']"));
        return link;
    }
    public WebElement visitsNumber()
    {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='numVisits']"));
        return inputStartTime;
    }
    public String startTime(String Time) {
        String startTime[] = Time.split(":");
        String randomNo = randomNumberGenerator();
        logger.info("randomNo = "+randomNo);
        String time;
        if (Integer.parseInt(startTime[0]) < 4 || Integer.parseInt(startTime[0]) == 12 ) {
            time = startTime[0]+":"+randomNo+"pm";
        } else {
            time = startTime[0]+":"+randomNo+"am";
        }
        logger.info("Time = "+time);
        return time;
    }
    public String randomizeFairName(String fair) {
        String randomNo = Integer.toString(new Random().nextInt(9999));
        logger.info("random suffix = "+randomNo + "\n");
        String FairName = fair+""+randomNo;
        logger.info("New FairName = "+FairName + "\n");
        return FairName;
    }
    public String randomNumberGenerator() {
        Random random = new Random();
        int n = random.nextInt(49) + 10;
        String time = Integer.toString(n);
        logger.info("random = "+time );
        return time;
    }
    private WebElement getRepVisitsBtn() {
        return link(By.id("js-main-nav-rep-visits-menu-link"));
    }
    private WebElement userDropdown() {
        WebElement dropdown=driver.findElement(By.id("user-dropdown"));
        return  dropdown;
    }
    private WebElement getInstitutionProfileBtn() { return driver.findElement(By.xpath("//span[text()='Institution Profile']")); }
    private WebElement getYourProfileBtn() { return driver.findElement(By.xpath("//span[text()='Your Profile']")); }
    private WebElement signOut() {
        WebElement signOut=driver.findElement(By.xpath("//span[text()='Sign Out']"));
        return  signOut;
    }
    private WebElement loggedInText() {
        WebElement text=driver.findElement(By.xpath("//span[contains(text(),'Logged in as')]"));
        return  text;
    }
    private WebElement frameInCommunity() {
        WebElement frame=driver.findElement(By.xpath("//iframe[@title='Community']"));
        return frame;
    }
    private WebElement userProfilePage() {
        WebElement profile=driver.findElement(By.xpath("//a[@class='active' and text()='Profile']"));
        return  profile;
    }
    private WebElement institutionProfilePage() {
        WebElement institution=driver.findElement(By.xpath("//a[@class='active' and text()='Institution']"));
        return  institution;
    }
    private WebElement accountSettings(String accountSettings)
    {
        WebElement label= driver.findElement(By.xpath("//span[text()='"+accountSettings+"']"));
        return  label;
    }
    private WebElement currentPasswordInput() {
        WebElement currentPassword=driver.findElement(By.id("current-password-input"));
        return  currentPassword;
    }
    private WebElement newPasswordInput() {
        WebElement newPassword=driver.findElement(By.id("new-password-input"));
        return  newPassword;
    }
    private WebElement confirmPasswordInput() {
        WebElement confirmPassword=driver.findElement(By.id("confirm-password-input"));
        return  confirmPassword;}
    private WebElement passwordErrorMessage() {
        WebElement msg=driver.findElement(By.xpath("//span[contains(text(),'The new password failed to satisfy security requirements')]"));
        return  msg;
    }
    private WebElement firstNameTextbox() {
        WebElement text=driver.findElement(By.id("user-form-first-name"));
        return  text;
    }
    private WebElement lastNameTextbox() {
        WebElement text=driver.findElement(By.id("user-form-last-name"));
        return  text;
    }
    private WebElement eMailTextbox() {
        WebElement text=driver.findElement(By.id("user-form-email"));
        return  text;
    }
    private WebElement availabilityAndSettings() {
        return link("Availability & Settings");
    }
    public void clickAddCollegeFairButton() {
        button("Add a College Fair").click();
    }
    public void noteForSchools(String note){
        Assert.assertTrue("Note For Schools is not displayed",noteDeclaration().getText().contains(note));
    }
    public void closeAddEditFairScreen(){
        closeFairScreen().click();
        waitForUITransition();
    }
    public void viewFairDetails() {
        waitUntilPageFinishLoading();
        getDriver().findElement(By.xpath("//div[@class='_1743W0qaWdOtlS0jkveD7o'][1]/table/tbody/tr[1]/td/following-sibling::td[4]/a/span")).click();
    }
    public void editFair(){
        waitUntilPageFinishLoading();
        editButton().click();
    }
    private WebElement noteDeclaration(){
        return getDriver().findElement(By.cssSelector("p._2jKMD8r6D3Vkw7TQidWlZ_"));
    }
    private WebElement closeFairScreen(){
        return getDriver().findElement(By.cssSelector("button.ui.black.basic.circular.icon.button._1zaSIpaNy8bj4C9yOAOsXw"));
    }
    private  WebElement editButton(){
        return getDriver().findElement(By.cssSelector("button#edit-college-fair.ui.basic.primary.right.floated.button._2WIBPMrHDvfagooC6zkFpq"));
    }

    private WebElement getUnpublishButton() {
        return getDriver().findElement(By.xpath("//button[text()[contains(.,'Unpublish')]]"));
    }
    public void moveToElement(WebElement element){
        Actions builder = new Actions(driver);
        builder.moveToElement(element ).build().perform();
    }
    private WebElement declinePopupMessage(){
        WebElement message = driver.findElement(By.xpath("//span[text()='Are you sure you want to decline?']"));
        return message;
    }
    private WebElement availability()
    {
        WebElement availability=  link("Availability");
        return  availability;
    }
    private WebElement regularWeeklyHours()
    {
        WebElement regularWeeklyHours= link("Regular Weekly Hours");
        return  regularWeeklyHours;
    }

    /**
     * Gets the add representative manually link
     *
     * @return webelement
     */
    private WebElement getAddRepresentativeManuallyLink() {
        return getDriver().findElement(By.xpath(".//span[text()='Not in the list? Add them manually']"));
    }

    /**
     * Gets the add visit button
     *
     * @return webelement
     */
    private WebElement getAddVisitButton() {
        return getDriver().findElement(By.cssSelector("i[class='plus icon']"));
    }

    /**
     * Gets the representative first name text box
     *
     * @return webelement
     */
    private WebElement getRepresentativeFirstNameTextBox() {
        return getDriver().findElement(By.id("add-rep-first-name"));
    }

    /**
     * Gets the representative las name textbox
     * @return webelement
     */
    private WebElement getRepresentativeLastName(){
        return getDriver().findElement(By.id("add-rep-last-name"));
    }

    /**
     * Gets the representative institution textbox
     * @return webelement
     */
    private WebElement getRepresentativeInstitutionTextBox(){
        return getDriver().findElement(By.id("add-rep-institution"));
    }

    /**
     * Gets the event location textbox
     *
     * @return webelement
     */
    private WebElement getEventLocationTextBox() {
        return textbox(By.cssSelector("input[aria-label='Event Location']"));
    }

    /**
     * Gets the max number of students textbox
     *
     * @return webelement
     */
    private WebElement getMaxNumberOfStudentsTextBox() {
        return textbox(By.cssSelector("input[aria-label='Max Number of Students']"));
    }

    /**
     * Gets the registration will close textbox
     *
     * @return webelement
     */
    private WebElement getRegistrationWillCloseTextBox() {
        return textbox(By.cssSelector("input[aria-label='Student Registration Deadline']"));
    }

    /**
     * Gets the registration will close dropdown
     *
     * @return webelement
     */
    private WebElement getRegistrationWillCloseDropDown() {
        return getDriver().findElement(By.cssSelector("div[class='ui button labeled dropdown icon _1aL4UbWpFb0cG9xlCe1orC']"));
    }

    /**
     * Gets the internal notes label
     *
     * @return webelement
     */
    private WebElement getInternalNotesLabel() {
        return getDriver().findElement(By.cssSelector("p[class='oIgGvJmf_cKi9PQcEeG3Z']"));
    }

    /**
     * Gets the add visit appointment button
     *
     * @return webelement
     */
    private WebElement getAddVisitCalendarAppointmentButton() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "button[class='ui teal right floated button']")));
        return getDriver().findElement(By.cssSelector(
                "button[class='ui teal right floated button']"));
    }

    /**
     * Gets the cancel button of the exceptions tab
     *
     * @return webelement
     */
    private WebElement getCancelExceptionButton() {
        return button("Cancel");
    }

    /**
     * Gets the block this time button
     *
     * @return, webelement, the block this time button
     */
    private WebElement getBlockThisTimeSlotButton() {
        return getDriver().findElement(By.xpath("//span[text()='Block this time slot']"));
    }

    /**
     * Gets the what does this mean label
     *
     * @return webelement, the what does this mean label
     */
    private WebElement getWhatDoesThisMeanLabel() {
        return getDriver().findElement(By.xpath("*//span[text()='What does this mean?']"));
    }

    /**
     * Gets the block this time slot tooltip
     *
     * @return, webelement, the block this time slot tooltip
     */
    private WebElement getBlockThisTimeSlotToolTip() {
        return getDriver().findElement(By.xpath("//div[text()='Blocking this time slot will prevent new appointments from being scheduled for " +
                "this particular date. Blocking this time slot will not cancel pre-existing appointments.']"));
    }

    /**
     * Gets the unblok this time slot button
     *
     * @return webelement
     */
    private WebElement getUnblockThisTimeSlotButton() {
        return getDriver().findElement(By.xpath("//span[text()='Unblock this time slot']"));
    }

    /**
     * Gets the close schedule new visit button
     *
     * @return webelement
     */
    private WebElement getCloseScheduleNewVisitButton() {
        return getDriver().findElement(By.cssSelector("i[class='close icon']"));
    }

    /**
     * Gets the number of visits textbox
     *
     * @return webelemet
     */
    private WebElement getNumberOfVisitsTextBox() {
        return getDriver().findElement(By.cssSelector("input[type='number']"));
    }

    /**
     * Gets the cancel this visit button
     *
     * @return webelement
     */
    private WebElement getCancelThisVisitButon() {
        return button("Cancel This Visit");
    }

    /**
     * Gets the message regarding cancellation textbox
     *
     * @return webelement
     */
    private WebElement getMessageRegardingCancellationTextBox() {
        return getDriver().findElement(By.id("repVisit-cancelation-message"));
    }

    /**
     * Gets the yes cancel visit button
     *
     * @return webelement
     */
    private WebElement getYesCancelVisitButton() {
        return button("Yes, cancel visit");
    }

    /**
     * Gets the remove slot button
     *
     * @return webelement
     */
    private WebElement getRemoveTimeSlotButton() {
        return button("REMOVE");
    }
    private WebElement instructionsTextBox() { return getDriver().findElement(By.cssSelector("#college-fair-instructions")); }

    private WebElement navianceSettings()
    {
        WebElement navianceSettings= link("Naviance Settings");
        return  navianceSettings;
    }

    private WebElement autopublishInNavianceSettings(String option)
    {
        WebElement publish=driver.findElement(By.xpath("//input[@name='autoPublish']/parent::label[text()='"+option+"']"));
        return publish;
    }
    private WebElement notifyStudents(String option)
    {
        WebElement notifyStudents=driver.findElement(By.xpath("//input[@name='notifyStudents']/parent::label[text()='"+option+"']"));
        return notifyStudents;
    }
    private WebElement displayDeadlines(String option)
    {
        WebElement displayDeadlines=driver.findElement(By.xpath("//input[@name='displayDeadline']/parent::label[text()='"+option+"']"));
        return displayDeadlines;
    }
    private WebElement saveSettings()
    {
        WebElement button=button("Save changes");
        return button;
    }
   private String getVisitStartTimeforcalendar(){
        String[] time=StartTime.split("am");
        String startTime=time[0]+"AM";
        return startTime;
    }
    private WebElement collegeFairTextBoxInCalendarPage()
    {
        WebElement check= driver.findElement(By.xpath("//div/label[text()='College Fair - Confirmed']"));
        return check;
    }
    private WebElement pendingCheckBoxInCalendarPage()
    {
        WebElement check=driver.findElement(By.xpath("//div/label[text()='Pending']"));
        return check;
    }
    private String month(String date)
    {
        String month=getSpecificDate(date);
        String selectMonth[]=month.split(" ");
        String currentMonth=selectMonth[0];
        return  currentMonth;
    }
    private WebElement currentMonthInCalendarPage()
    {
        WebElement month=driver.findElement(By.xpath("//div[@class='three wide column']/div[@class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']"));
        return month;
    }
    private WebElement nextMonthButton()
    {
        WebElement button=driver.findElement(By.xpath("//button[@class='ui teal basic icon button _38R7SJgG4fJ86m-eLYYZJw']"));
        return button;
    }
    private WebElement eventLocationTextboxInSchedulePopup() {
        WebElement text=driver.findElement(By.xpath("//input[@aria-label='Event Location']"));
        return text;
    }
    private WebElement addVisitManually() {
        WebElement link=driver.findElement(By.xpath("//span[text()='Want a custom time? Add it manually']"));
        return link;
    }
    private WebElement selectDateButton() {
        WebElement button=driver.findElement(By.cssSelector("button[class$='_3VnqII6ynYglzDU1flY9rw']"));
        return button;
    }
    private WebElement manualStartTime() {
        WebElement text=driver.findElement(By.id("manualStartTime"));
        return text;
    }
    private WebElement manualEndTime() {
        WebElement text=driver.findElement(By.id("manualEndTime"));
        return text;
    }
    private WebElement addAttendee() {
        WebElement text=driver.findElement(By.id("calendar-search-reps"));
        return text;
    }
    private WebElement attendee(String Attendee) {
        WebElement attendee= driver.findElement(By.xpath("//div[text()='"+Attendee+"']"));
        return attendee;
    }
    private WebElement eventLocation() {
        WebElement location=driver.findElement(By.xpath("//div/input[@id='eventLocation']"));
        return location;
    }
    private WebElement addVisitButtonInVisitSchedulePopup() {
        WebElement button=driver.findElement(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']"));
        return button;
    }
    private WebElement DateButton() {
        WebElement button=driver.findElement(By.cssSelector("button[class$='_3GJIUrSQadO6hk9FZvH28D']"));
        return button;
    }
    private WebElement internalNotesTextBoxInVisitSchedulePopup() {
        WebElement text=driver.findElement(By.xpath("//div/textarea[@id='internalNotes']"));
        return text;
    }
    private WebElement addrepInstitution(){
        WebElement id = driver.findElement(By.id("add-rep-institution"));
        return id;
    }
          private WebElement studentsNotes(){
        WebElement text=driver.findElement(By.xpath("//div/textarea[@id='internalNotes']"));
        return text;
    }
    private WebElement findRepTextbox()
    {
        WebElement text=driver.findElement(By.id("calendar-search-reps"));
        return text;
    }
    private WebElement exceptionLink() {
        WebElement link=link("Exceptions");
        return link;
    }
    private WebElement dateButtonInException() {
        WebElement button=driver.findElement(By.xpath("//button[@class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']"));
        return button;
    }
    private WebElement gotoDateButtonInVisitSchedulePopup() {
        WebElement button=driver.findElement(By.xpath("//span[contains(text(),'Go to date')]"));
        return button;
    }
    private WebElement textInVisitSchedulePopup() {
        WebElement text=driver.findElement(By.xpath("//span[contains(text(),'schedule new visit')]"));
        return text;
    }
    private WebElement verifyTextInVisitSchedulePopup() {
        WebElement text=driver.findElement(By.xpath("//span[contains(text(),'select an available time slot')]"));
        return text;
    }
    private WebElement calendar() {
        WebElement page=driver.findElement(By.xpath("//div[text()='Calendars']"));
        return page;
    }
    private WebElement eventLocationTextboxInAddVisitSchedulePopup() {
        WebElement text=driver.findElement(By.xpath("//div/input[@id='eventLocation']"));
        return text;
    }
    private WebElement verifyAddVisitButtonInVisitSchedulePopup() {
        WebElement button=driver.findElement(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal disabled right floated button']"));
        return button;
    }
}
