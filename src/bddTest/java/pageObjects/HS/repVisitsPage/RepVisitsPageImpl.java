package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.fail;

import java.text.DateFormat;

import utilities.GetProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.openqa.selenium.*;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Map;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import utilities.File.CsvFileUtility;

import utilities.File.FileManager;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static String StartTime;
    public static String FairName;
    public static String generatedDateForExceptions;
    public static String ManualStartTime;
    public static String RescheduleStartTimeforNewVisit;
    public static String CollegeFairName;
    public static String generatedDate;
    public static String generatedDateDayOfWeek;
    public static String time;
    public static String formattedDate;

    //Creating RepVisitsPageImpl class object of for HE.
    pageObjects.HE.repVisitsPage.RepVisitsPageImpl repVisitsPageHEObj = new pageObjects.HE.repVisitsPage.RepVisitsPageImpl();


    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

    public void checkRepVisitsSubTabs(DataTable dataTable) {
        getNavigationBar().goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.", driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='" + repVisitsSubItem + "']")) != null);
            driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='" + repVisitsSubItem + "']")).click();
        }
    }

    public void confirmDeclineCollegeAttendanceRequest(String requestOption, String fairName) {
        logger.info("Validating the functionality of the " + requestOption + " button for College Fair attendance request.");
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        button(By.xpath("//a[@aria-label='" + fairName + "']")).click();
        boolean expectedResult = false;
        switch (requestOption) {
            case "Decline":
                button("DECLINE").click();
                if (button(By.xpath("//span[contains(text(), 'No, go back')]")).isDisplayed() && getDriver().findElement(By.xpath("//button[@class = 'ui red small disabled right floated button']")).isDisplayed()) {
                    getDriver().findElement(By.xpath("//textarea[@id='attendee-decline-message']")).sendKeys("Attendance Denied.");
                    if (button("No, go back").isDisplayed() && button("Yes, decline visit").isDisplayed()) {
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
                if (getDriver().findElement(By.xpath("//tr/td[contains(text(), 'Attending')]")).isDisplayed() && button(By.xpath("//span[contains(text(), 'CANCEL')]")).isDisplayed()) {
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

    public void verifyAvailabilityAndSettingsPage() {
        waitUntilElementExists(driver.findElement(By.cssSelector("div[id='app']")));
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
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
        for (String tab : tabs) {

            if (tab.contains("Regular Weekly Hours") || tab.contains("Blocked Days") || tab.contains("Exceptions") || tab.contains("Availability Settings")) {
                Assert.assertTrue("Tab " + tab + " is not displaying as expected!", driver.findElement(By.xpath("//h2[@class='ui header XgpYufXdBrkgn-HSc74qf']/span[text()='" + tab + "']")).isDisplayed());
            } else {
                Assert.assertTrue("Tab " + tab + " is not displaying as expected!", driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='" + tab + "']")).isDisplayed());
            }

            if (tab.contains("Availability")) {
                Assert.assertTrue("Tab " + tab + " is not displaying as expected!", driver.findElement(By.xpath("//a[@class='menu-link active']/span[text()='" + tab + "']")).isDisplayed());
            }
        }
    }

    public void verifyEmptyContactPage() {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed", driver.findElement(By.xpath("//h1[@class='ui header _2GIsNevIB_s082IZwcYen3']")).isDisplayed());
        Assert.assertTrue("Instruction text is not displayed", driver.findElement(By.xpath("//div[@class='sub header _240ldPuujUDvP5vNIGw15H']")).isDisplayed());
    }

    public void verifyFullContactPage() {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed", driver.findElement(By.xpath("//h1[@class='ui header _2GIsNevIB_s082IZwcYen3']")).isDisplayed());
        Assert.assertTrue("Instruction text is not displayed", driver.findElement(By.xpath("//div[@class='sub header _240ldPuujUDvP5vNIGw15H']")).isDisplayed());
        List<WebElement> searchedValueOfName = driver.findElements(By.className("_1ijSBYwG-OqiUP1_S7yMUN"));
        int size = searchedValueOfName.size();
        Assert.assertTrue("RepVisits contact are not displayed", size > 0);
    }

    public void verifyFullorEmpty() {
        try {
            if (text("Welcome to Contacts").isDisplayed()) {
                logger.info("you have no Contacts");
            } else if (driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed()) {
                while (driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed()) {
                    driver.findElement(By.xpath("//span[text()='Show More']")).click();
                }
            } else {
            }
        } catch (Exception e) {
        }
    }

    public void sortingContacts() {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        driver.findElement(By.xpath("//input[@name='contacts-search']")).clear();
        ArrayList<String> original = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[2]/div[1]"));
        for (WebElement we : elements) {
            original.add(we.getText());
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (String s : original) {
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        int i = 0;
        for (String entry : sortedList) {
            Assert.assertTrue("Entry in sorted list doesn't match an entry in the original list. Sorted: " + entry + ", Original: " + original.get(i), entry.equalsIgnoreCase(original.get(i)));
            i++;
        }
    }

    public void validatingthePaginationof25Contacts() {
        int count;
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        waitForUITransition();
        Assert.assertTrue("Contacts is not displayed", driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).isDisplayed());
        count = driver.findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).size();
        try {
            logger.info(count);
        } catch (Exception e) {
        }
        if (count >= 25) {
            Assert.assertTrue("There are 25 contacts, but no 'Show More' button is present!", driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed());
        }
    }

    public void verifyContactDetails(DataTable dataTable) {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.", text(repVisitsSubItem).isDisplayed());
        }
    }

    public void verifyAvailabilitySettings(DataTable dataTable) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availabilitySettings().click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed", text("Visit Scheduling").isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed", text("Accept").isDisplayed());
        Assert.assertTrue("Listbox is not displayed", driver.findElement(By.cssSelector("div[class='ui selection dropdown'][role='listbox']")).isDisplayed());
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
        Assert.assertTrue("'Visit Confirmations' header is not displayed as expected!", text("Visits Confirmations").isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Automatically confirm all visit requests?' is not displaying as expected!", text("Automatically confirm all visit requests?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'Yes, accept all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//label[text()='Yes, accept all incoming requests.']")).isDisplayed());
        Assert.assertTrue("radiobutton 'No, I want to manually review all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//label[text()='No, I want to manually review all incoming requests.']")).isDisplayed());

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
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[id=autoConfirmVisit-yes]")).isSelected());
                        } else if (availabilityData.get(key).contains("No, I want to manually review all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[id=autoConfirmVisit-no]")).isSelected());
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

    public void verifyinvalidcontact(String invalidData) {
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(invalidData);
        Assert.assertTrue("the message of 'Your search did not return any contacts.' is not displayed", text("Your search did not return any contacts.").isDisplayed());
    }

    public void searchforContact(String institutionName) {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(institutionName);
        waitForUITransition();
        waitUntilElementExists(driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[@class='five wide hidden-mobile']/div[contains(text(),'" + institutionName + "')]")));
        Assert.assertTrue("the specified schoolname is not displayed", driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[@class='five wide hidden-mobile']/div[contains(text(),'" + institutionName + "')]")).isDisplayed());
    }

    public void partialsearchforContact(String institutionName) {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(institutionName);
        List<WebElement> searchedValueOfinstitutionName = driver.findElements(By.xpath("//td/div[contains(text(),'" + institutionName + "')]"));
        for (int i = 0; i < searchedValueOfinstitutionName.size(); i++) {
            String value = searchedValueOfinstitutionName.get(i).getText();
            Assert.assertTrue("Partial matching on institution name is not available", value.contains(institutionName));
        }
    }

    public void addNewTimeSlot(String day, String hourStartTime, String hourEndTime, String minuteStartTime, String minuteEndTime, String meridianStartTime, String meridianEndTime, String numVisits) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        regularWeeklyHours().click();
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
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[class='availability']")));
        if (driver.findElements(By.xpath("//div[@class='ui small modal transition visible active']")).size() > 0) {
            selectOptionInReviewPreviouslyDeletedTimeSlotsModal
                    ("Add time slot to regular hours, and create for the dates above");
        }
    }

    /**
     * Selects the given option in Review Previously Deleted Time Slots Modal
     *
     * @param option to be selected
     */
    public void selectOptionInReviewPreviouslyDeletedTimeSlotsModal(String option) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='ui small modal transition visible active']")));
        driver.findElement(By.xpath(String.format("//label[text()='%s']", option))).click();
        button("ADD REGULAR HOURS").click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='ui small modal transition visible active']")));
    }

    public void setPreventCollegesSchedulingNewVisits(String Numberofdays) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        availability();
        availabilitySettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"), 1));
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(Numberofdays);
        button("Save Changes").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void setPreventCollegesCancellingorRescheduling(String DaysInAdvance) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        availability().click();
        availabilitySettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"), 1));
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(DaysInAdvance);
        button("Save Changes").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void setAcceptInVisitSchedulingToFullyBooked(String accept) {
        setAcceptinAvailabilitySettings(accept, "2");
    }

    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay) {
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[id='app']")));
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(availabilityAndSettings());
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilElementExists(availability());
        availability().click();
        availabilitySettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"), 1));
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
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void setVisitsConfirmations(String option) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement element = availabilityAndSettings();
        waitUntilElementExists(element);
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        waitUntilPageFinishLoading();
        availabilitySettings().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(saveChangesAvailabilityLocator()));
        visitConfirmation(option).click();
        waitUntilPageFinishLoading();
        saveChangesAvailability().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void accessVisitAvailability(String visitAvailability) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availabilitySettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(saveChangesAvailabilityLocator()));
        waitUntilElementExists(getDriver().findElement(By.cssSelector("button[class='ui primary button']")));
        waitUntilElementExists(getDriver().findElement(By.xpath("//label[text()='" + visitAvailability + "']")));
        getDriver().findElement(By.xpath("//label[text()='" + visitAvailability + "']")).click();
        getDriver().findElement(By.cssSelector("button[class='ui primary button']")).click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void verifyVisitAvailability(String visitAvailabiltyOption) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availabilitySettings().click();
        //Verify the Title
        Assert.assertTrue("'Visit Availability' header is not displayed as expected!", visitAvailabilityText().isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Who can see your availability?'  is not displaying as expected!", text("Who can see your availability?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'All Repvisits Users' is not displaying as expected!", allRepVisitsUsersRadioButton().isDisplayed());
        Assert.assertTrue("radiobutton 'Only Me' is not displaying as expected!", onlyMeRadioButton().isDisplayed());
        Assert.assertTrue("radiobutton Label 'All Repvisits Users' is not displaying as expected!", allRepVisitsUsersRadioButton().isDisplayed());
        Assert.assertTrue("radiobutton Label 'Only Me' is not displaying as expected!", onlyMeRadioButton().isDisplayed());
        if (!visitAvailabiltyOption.equals("")) {
            Assert.assertFalse("The option " + visitAvailabiltyOption + " radio button is not checked", driver.findElement(By.xpath("//label[text()='" + visitAvailabiltyOption + "']")).isSelected());
        }
        //'Save Changes' button
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
    }


    public void addDefaultMessage(String message) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        messageOptions().click();
        waitUntilPageFinishLoading();
        waitUntil(presenceOfElementLocated(By.cssSelector("textarea[name='emailInstructions']")));
        writeInConfirmationMessage().clear();
        writeInConfirmationMessage().sendKeys(message);
        waitUntilPageFinishLoading();
        writeInSpecialInstructionForRepViists(message);
        button("Update Messaging").click();
    }

    public void verifyMessageUpdated(String message) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        messageOptions().click();
        waitUntilPageFinishLoading();
        waitUntil(presenceOfElementLocated(By.cssSelector("textarea[name='emailInstructions']")));
        String confirmationMessageText = writeInConfirmationMessage().getText();
        Assert.assertTrue(confirmationMessageText + " Text is not displayed",
                confirmationMessageText.contains(message));
    }

    public void verifyMessageConfirmation() {
        Assert.assertTrue("The update message was not displayed", text("You've updated your messaging.")
                .isDisplayed());
    }

    public void verifyContentsOfNavianceSettings() {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        navianceSettings().click();
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

        Assert.assertTrue("The Time Slot was not added", driver.findElement(By.cssSelector("table[class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']")).findElement(By.xpath("//button[contains(text(), '" + hourStartTime + ":" + minuteStartTime + meridianStartTime + "')]")).isDisplayed());

    }

    public Boolean compareDate(String month) {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MMM yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='DayPicker-Caption']")));
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
        return before;

    }

    public void clickOnDay(String date) {

        try {

            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[@aria-disabled='false'][text()=" + date + "]")).click();

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    public void clickDisabledDate(String date) {
        driver.findElement(By.cssSelector("div[class='DayPicker-Body']")).findElement(By.xpath("//div[contains(@class,'DayPicker-Day DayPicker-Day--disabled') and @aria-disabled='true' and text()='" + date + "']")).click();
    }

    public void verifyContentsOfRegularWeeklyHours() {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();

        String startEndDatesForVisitsText = getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")).getText();
        String dayTableText = getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")).getText();
        String displayStartEndForVisitsText = "Start and End Dates For Visits";
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")));
        Assert.assertTrue(dayTableText + " Text is not displayed",
                dayTableText.contains("MON TUE WED THU FRI"));
        Assert.assertTrue(displayStartEndForVisitsText + " Text is not displayed",
                dayTableText.contains(displayStartEndForVisitsText));
        Assert.assertTrue("Button Start Date is not showing.",
                button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).isDisplayed());
        Assert.assertTrue("Button End Date is not showing.",
                button(By.cssSelector("div[style='display: inline-block; position: relative;'] :nth-child(3)")).isDisplayed());
    }

    public void selectDayForSlotTime(String element, String day) {
        WebElement dayList = driver.findElement(By.cssSelector(element.toString()));
        Actions action = new Actions(getDriver());
        action.moveToElement(dayList).build().perform();
        dayList.click();
        if (day.contains("Saturday") || day.contains("Sunday")) {
            driver.findElement((By.cssSelector("[class='visible menu transition']"))).findElement(By.xpath("div/span[contains(text(), 'Monday')]")).click();
        } else {
            driver.findElement((By.cssSelector("[class='visible menu transition']"))).findElement(By.xpath("div/span[contains(text(), '" + day + "')]")).click();
        }
    }


    public void inputStartTime(String hour, String minute, String meridian) {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='startTime']"));
        inputStartTime.click();
        inputStartTime.sendKeys(hour + ":");
        inputStartTime.sendKeys(minute);
        inputStartTime.sendKeys(meridian);

    }

    public void inputEndTime(String hour, String minute, String meridian) {
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
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        messageOptions().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(updateMessagingButton());

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
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        button(By.cssSelector("div[style='display: inline-block; position: relative;'] :nth-child(3)")).click();
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
        setStartAndEndDates(GetProperties.get("repvisits.schoolyear.startdate"), GetProperties.get("repvisits.schoolyear.enddate"));
    }

    public void setStartandEndDates(String startDate, String endDate) {
        setDefaultDateforStartAndEndDate();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        waitUntilPageFinishLoading();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        int EDate = Integer.parseInt(endDate);
        String EndDate = getSpecificDate(EDate, "MMMM d yyyy");
        setDate(EndDate, "End");
        int SDate = Integer.parseInt(startDate);
        String StartDate = getSpecificDate(SDate, "MMMM d yyyy");
        setDate(StartDate, "Start");
        clickUpdateButtonInRepVisits();
    }

    public void setStartDateAndEndDateInAgendaView(String numberOfDaysFromNowTillStartDate, String numberOfDaysFromNowTillEndDate) {
        agendaViewEndDatePicker().click();
        setSpecificDateforManuallyCreatingVisit(numberOfDaysFromNowTillEndDate);
        agendaViewStartDatePicker().click();
        setSpecificDateforManuallyCreatingVisit(numberOfDaysFromNowTillStartDate);
    }

    public void completeSetupWizardFromAnylocation() {
        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
    }

    public void verifyNumberOfVisitsDisplayedInAgendaView(String numberOfVisits) {
        Assert.assertTrue("The number of visits to be displayed in Agenda view is " + numberOfVisits
                + " but instead is " + listOfEventsDisplayedInAgendaView().size(), listOfEventsDisplayedInAgendaView().size() > Integer.parseInt(numberOfVisits));
    }

    public void verifyUserCannotSelectEndDateWhichIsLessThanStartDateInAgendaView(String numberOfDaysFromNowTillEndDate,
                                                                                  String numberOfDaysFromNowTillStartDate) {
        if (Integer.parseInt(numberOfDaysFromNowTillEndDate) < Integer.parseInt(numberOfDaysFromNowTillStartDate)) {
            try {
                setStartDateAndEndDateInAgendaView(numberOfDaysFromNowTillStartDate, numberOfDaysFromNowTillEndDate);
            } catch (AssertionError e) {
                Assert.assertEquals("User is allowed to select an end date which is less than start date", e.getMessage(), "The Date selected is out of RANGE.");
            }
        } else {
            Assert.assertTrue("End date is not less than Start date", false);
        }
    }

    /**
     * Verify the date Availability in the specific range dates
     *
     * @startDate The start date
     * @endDate The end date
     * @day specific date to verify
     */

    public void selectOptionforManuallyorAutomatically(String option) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        navianceSettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(navianceSyncSettings()));
        visitAvailability(option).click();
        locationTextBoxInNaviance().sendKeys(Keys.PAGE_DOWN);
        saveChangesNaviance().click();
        waitUntilPageFinishLoading();
    }

    public void verifyNavianceSyncTab(String option, String university, String time, String date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        notificationAndTasks().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        navianceSync().click();
        waitForUITransition();
        time = StartTime;
        List<WebElement> appointment = driver.findElements(By.xpath("//div[text()='" + university + "']/parent::div/div/span[contains(text(),'" + time + "')]"));
        if (option.equals("displaying")) {
            Assert.assertTrue("Naviance header is not displayed", pushToNaviancelinkText().isDisplayed());
            Assert.assertTrue("university name is not displayed", driver.findElement(By.xpath("//div[text()='" + university + "']")).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//div[text()='" + university + "']/parent::div/div/span[contains(text(),'" + time + "')]")).isDisplayed());
            logger.info("There we have no notification in naviance sync tab");
        } else if (option.equals("not displaying")) {
            Assert.assertTrue("Appointments are not pushed to naviance", appointment.size() == 0);
        }
    }

    public void verifyRescheduledNotificationInNavianceSyncTab(String option, String university, String time, String date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        notificationAndTasks().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        navianceSync().click();
        waitForUITransition();
        time = RescheduleStartTimeforNewVisit;
        List<WebElement> appointment = driver.findElements(By.xpath("//div[text()='" + university + "']/parent::div/div/span[contains(text(),'" + time + "')]"));
        if (option.equals("displaying")) {
            Assert.assertTrue("Naviance header is not displayed", pushToNaviancelinkText().isDisplayed());
            Assert.assertTrue("university name is not displayed", driver.findElement(By.xpath("//div[text()='" + university + "']")).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//div[text()='" + university + "']/parent::div/div/span[contains(text(),'" + time + "')]")).isDisplayed());
            logger.info("There we have no notification in naviance sync tab");
        } else if (option.equals("not displaying")) {
            Assert.assertTrue("Appointments are not pushed to naviance", appointment.size() == 0);
        }
    }

    public void selectCalendar() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
    }

    public void verifyAvaliabilityDates(String startDate, String endDate, String stardDay,String endDay) {
        waitUntilElementExists(driver.findElement(By.cssSelector("div[id='app']")));
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        waitUntilPageFinishLoading();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        findSpecificDate(startDate,endDate, stardDay,endDay,"startDate");
        findSpecificDate(startDate,endDate, stardDay,endDay,"endDate");
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

    public String getSpecificDateForFairsEdit(String addDays) {
        String DATE_FORMAT_NOW = "EEEE, MMM dd, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getSpecificDateForFairsPage(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getSpecificDateForReschedule(String addDays) {
        String DATE_FORMAT_NOW = "MM/dd/yyyy";
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
        WebElement Date = driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()=" + parts[1] + "]"));
        doubleClick(Date);
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        waitUntilPageFinishLoading();
    }

    public void accessWelcomeSetupWizard(String optionToSelect) {
        loadSetupWizardPage();
        if (!optionToSelect.equals("")) {
            if (optionToSelect.equalsIgnoreCase("VISITS")) {
                jsClick(visitButton());
            } else if (optionToSelect.equalsIgnoreCase("FAIRS")) {
                jsClick(fairsButton());
            } else if (optionToSelect.equalsIgnoreCase("VISITS AND FAIRS")) {
                jsClick(visitAndFairsButton());
            } else {
                Assert.fail("The given option to select is not a valid one");
            }
        }
        nextButton().click();
        waitUntilPageFinishLoading();
    }

    public void accessHighschoolInformationSetupWizard(String timeZone) {
        if (!timeZone.equals("")) {
            WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
            EntertimeZone.click();
            getDriver().findElement(By.xpath("//span[text()='" + timeZone + "']")).click();
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

    /**
     * Find the specific date in the calendar
     *
     * @date The calendar date
     * @day specific date to verify
     */
    public void findSpecificDate(String startDate,String endDate, String startDay,String endDay,String dateOption) {
        if(dateOption.equals("startDate")){
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(1)")).click();
            findMonth(startDate);
            verifyDisabledDates(startDate, startDay);
            findMonth(endDate);
            verifyEnabledDates(endDate, endDay);
        }else if(dateOption.equals("endDate")){
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(3)")).click();
            findMonth(startDate);
            verifyDisabledDates(startDate, startDay);
            findMonth(endDate);
            verifyEnabledDates(endDate, endDay);
        }else {
            Assert.fail("Invalid option");
        }
    }

    public String getSpecificDateForNotification(String addDays) {
        String DATE_FORMAT_NOW = "EEEE, d MMMM yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void accessOneLastStepSetupWizard(String visitAvailability) {
        loadSetupWizardPage();
        jsClick(visitAndFairsButton());
        while (completeWizardActiveStep().size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            nextButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("Complete page is not displayed", visitAvailabilityText().isDisplayed());
        if (!visitAvailability.equals("")) {
            if (visitAvailability.equalsIgnoreCase("All RepVisits Users")) {
                jsClick(allRepVisitsUsersRadioButton());
            } else if (visitAvailability.equalsIgnoreCase("Only Me")) {
                jsClick(onlyMeRadioButton());
            } else {
                Assert.fail("The given option for the visitAvailability is not a valid one");
            }
        }
        nextButton().click();
    }

    public void verifyYouAreAllSetPage(String visibilitySetting) {
        if (visibilitySetting.equalsIgnoreCase("Only Me")) {
            Assert.assertTrue("Only Me Ack is not displayed ", successMessageText().isDisplayed());
        } else if (visibilitySetting.equalsIgnoreCase("All RepVisits Users")) {
            Assert.assertTrue("All RepVisits Ack page is not displayed", repVisitAck().isDisplayed());
        } else {
            Assert.fail("Error: " + visibilitySetting + " is not a valid option for Visit Availability");
        }
        takeMeToMyVisitsButton().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(todayText());
        Assert.assertTrue("Calendar is not displayed", todayText().isDisplayed());

        getDriver().navigate().back();
        waitUntilPageFinishLoading();

        startedWithCollegeFairButton().click();

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

            Assert.assertTrue("Button Start Date is not showing.",
                    driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).
                            findElement(By.xpath("//span[contains(text(), '" + startDay + "/" + startYear + "')]")).isDisplayed());

            Assert.assertTrue("Button End Date is not showing.",
                    driver.findElement(By.cssSelector("div[style='display: inline-block; position: relative;'] :nth-child(3)")).
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
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        timeZone().click();
        Assert.assertTrue("Tell us about your high school text is not present", getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present", getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.", TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match", getDriver().findElement(By.cssSelector(".search[class=\"search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed", getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());
    }

    public void verifyManualBlockedHolidays(String holiday) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        blockedDays().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")));
        Boolean checkBoxLaborHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxColumbusHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxVeteransHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxTanksGivenHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxDayAfterThanksGivenHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxChristmasEveHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxChristmasDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxNewYearEveHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxNewYearDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxMartinLutherDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxPresidentDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxMemorialDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");
        Boolean checkBoxIndependenceDayHolidayStatus = getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[title='" + holiday + "']")));
        if (!getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).getAttribute("class").contains("checked")) {
            doubleClick(getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")));
            //Click on SAVE BLOCKED HOLIDAYS button
            getDriver().findElement(By.cssSelector("button[class='ui primary button']")).click();
        }

        availabilityAndSettings().click();
        blockedDays().click();

        switch (holiday) {
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

    public void searchforSchool(String school, String location, String Date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getSearchAndScheduleBtn().click();
        getSearchBox().sendKeys(school);
        driver.findElement(By.cssSelector("button[class='ui icon button _3pWea2IV4hoAzTQ12mEux-']")).click();
        waitForUITransition();
        waitUntilElementExists(driver.findElement(By.xpath("//td[text()='" + location + "']")));
        Assert.assertTrue("location is not displayed", driver.findElement(By.xpath("//td[text()='" + location + "']")).isDisplayed());
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
        waitForUITransition();
        driver.findElement(By.className("_135QG0V-mOkCAZD0s14PUf")).findElement(By.xpath("button")).click();
        setDateFixed(Date, "Start");
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(getDriver().findElement(By.className("JIilVAK-W5DJoBrTmFeUG")));
        Assert.assertTrue("Was not set Blocked!", getDriver().findElement(By.xpath("//span[text()='Blocked']/parent::span/following-sibling::p[text()='holiday']")).isDisplayed());
    }

    public void setDate(String inputDate, String startOrEndDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        if (startOrEndDate.contains("Start")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(1)")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if (startOrEndDate.contains("End")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(3)")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if (startOrEndDate.contains("FirstDate")) {
            driver.findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if (startOrEndDate.contains("LastDate") || startOrEndDate.equals("DisabledDate")) {
            driver.findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/following-sibling::button")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if (startOrEndDate.contains("other")) {
            button(By.cssSelector("button[class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']")).click();
            findMonth(calendarHeading);
        } else {
            button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).click();
            findMonth(calendarHeading);
        }
        if (parts[1].startsWith("0")) {
            parts[1] = parts[1].replace("0", "");
            clickOnDay(parts[1]);
        } else if (startOrEndDate.equals("DisabledDate")) {
            clickDisabledDate(parts[1]);
        } else {
            clickOnDay(parts[1]);
            waitUntilPageFinishLoading();
        }
    }

    public void setDateFixed(String inputDate, String startOrEndDate) {

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

    public void goToDisabledDate(String inputDate) {
        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        waitUntilPageFinishLoading();
    }

    public Boolean compareHEDate(String month, String startOrEndDate) {

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
        return before;

    }

    public void setTimeZone(String timeZone) {
        if (!isLinkActive(timeZone())) {
            getNavigationBar().goToRepVisits();
            availabilityAndSettings().click();
            timeZone().click();
        }
        setTimeZoneValue(timeZone);
        button("Update time zone").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='content']")));
    }

    public void verifyHolidayCheckBoxStatus(Boolean holidayStatus, String holiday, int index) {
        if (holidayStatus) {
            Assert.assertTrue("Was not set Holiday!", getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).isDisplayed() && getDriver().findElement(By.xpath("(//div[starts-with(@class, 'ui checked checkbox _1mJoFw7NZ4cPX_1EWFCyjY')])['" + index + "']")).isDisplayed());
        } else {
            getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).click();
            Assert.assertTrue("Was not set Holiday!", getDriver().findElement(By.cssSelector("div[title='" + holiday + "']")).isDisplayed() && getDriver().findElement(By.xpath("(//div[starts-with(@class, 'ui checked checkbox _1mJoFw7NZ4cPX_1EWFCyjY')])['" + index + "']")).isDisplayed());
        }
    }

    public void clickLinkAvailability() {
        if (!isLinkActive(availability())) {
            getNavigationBar().goToRepVisits();
            availabilityAndSettings().click();
            availability().click();
        }
    }

    public void verifyCalendarSyncMilestoneInSetupWizard() {
        doubleClick(visitsAndFairsRadioButton());
        while (calendarSyncActiveInSetupWizard().size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            nextButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
        }

        //verify 'Back' button and 'Next' button is displayed
        Assert.assertTrue(backButton().isDisplayed());
        Assert.assertTrue(nextButton().isDisplayed());

        //verify UI text
        Assert.assertTrue("'Calendar Sync' page is not displayed", subscriptionText().isDisplayed());

        nextButton().click();
        Assert.assertTrue("'Naviance setting page' page is not displayed", navianceSettingsPageText().isDisplayed());

        backButton().click();
        waitUntilPageFinishLoading();
        backButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Notification and Primary contact' page is not displayed", primaryContactText().isDisplayed());


    }

    private void setTimeZoneValue(String timeZone) {
        waitForUITransition();
        waitUntilPageFinishLoading();
        WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[class=\"search\"] + div"));
        waitUntilElementExists(EntertimeZone);
        EntertimeZone.click();
        jsClick(getDriver().findElement(By.xpath("//span[text()='" + timeZone + "']")));
    }

    public void verifyCalendarViewOnRepVisits() {
        getNavigationBar().goToRepVisits();
        calendar().click();

        //Verify Small Calendar
        Assert.assertTrue("add visit button is not displayed", driver.findElement(By.cssSelector("button[class='ui teal button _2vMIFbyypA0b_pLiQmz0hV']")).isDisplayed());
        Assert.assertTrue("Small Calendar is not displayed", driver.findElement(By.cssSelector("div[role='application']")).isDisplayed());
        Assert.assertTrue("small calendar next button is not displayed", driver.findElement(By.cssSelector("button[title='right']>i")).isDisplayed());
        Assert.assertTrue("small calendar previous button is not displayed", driver.findElement(By.cssSelector("button[title='left']>i")).isDisplayed());


        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement mainCalendarNextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Forwards']>i")));

        //Verify Main Calendar
        Assert.assertTrue("Main Calendar is not displayed", driver.findElement(By.cssSelector("div[class='rbc-calendar rep-visits-rbc-calendar']")).isDisplayed());
        Assert.assertTrue("Main calendar next button is not displayed", driver.findElement(By.cssSelector("button[title='Forwards']>i")).isDisplayed());
        Assert.assertTrue("Main calendar previous button is not displayed", driver.findElement(By.cssSelector("button[title='Backwards']>i")).isDisplayed());

        //verify day view
        Assert.assertTrue(" Day view button is not displayed", button("Day").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Day']")).click();
        Assert.assertTrue("Day view is not displayed", driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify week view
        Assert.assertTrue(" Week view button is not displayed", button("Week").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Week']")).click();
        Assert.assertTrue("Week view is not displayed", driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify month view
        Assert.assertTrue(" Month view button is not displayed", button("Month").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Month']")).click();
        Assert.assertTrue("Month view is not displayed", driver.findElement(By.cssSelector("div[class='rbc-month-view']")).isDisplayed());

        // Appointments are clickable
        addVisitHS().click();
        waitForUITransition();
        Assert.assertTrue("Appointment details drawer is not displayed", driver.findElement(By.cssSelector("div[class='ui overlay right very wide visible sidebar _1bTs4IjZQSsADQ671qHLL3']")).isDisplayed());
        driver.findElement(By.xpath("//button[@aria-label='Close']")).click();

        //verify Appointment Keys
        //Visits
        Assert.assertTrue(" visit confirmed option is not displayed", text("Visits - Confirmed").isDisplayed());
        Assert.assertTrue("Visit confirmed Checkbox is not displayed", driver.findElement(By.xpath("//input[@id='visit' and @type='checkbox']/following::label")).isDisplayed());
        String visitColor = "rgba(0, 0, 0, 1)";
        String actualVisitColor = driver.findElement(By.xpath("//input[@id='visit']")).getCssValue("color");
        Assert.assertTrue("Background Color for the Visit checkbox are not displayed", actualVisitColor.equals(visitColor));
        driver.findElement(By.xpath("//input[@id='visit']/following::label")).click();

        //Fairs
        Assert.assertTrue(" Fair confirmed option is not displayed", text("College Fair - Confirmed").isDisplayed());
        Assert.assertTrue("Fair confirmed Checkbox is not displayed", driver.findElement(By.xpath("//input[@id='fair'and @type='checkbox']/following::label")).isDisplayed());
        /*Color not is needed to verify because constantly changes*/
//        String fairColor = "rgba(0, 0, 0, 0.87)";
//        String actualFairColor = driver.findElement(By.xpath("//input[@id='fair']")).getCssValue("color");
//        Assert.assertTrue("Background Color for the fair checkbox are not displayed",actualFairColor.equals(fairColor));
        driver.findElement(By.xpath("//input[@id='fair']/following::label")).click();

        //Pending
        Assert.assertTrue("Pending option is not displayed", text("Pending").isDisplayed());
        Assert.assertTrue("Pending Checkbox is not displayed", driver.findElement(By.xpath("//input[@id='pending'and @type='checkbox']/following::label")).isDisplayed());
        /*Color not is needed to verify because constantly changes*/
//        String pendingColor = "rgba(0, 0, 0, 0.87)";
//        String actualPendingColor = driver.findElement(By.xpath("//input[@id='pending']")).getCssValue("color");
//        Assert.assertTrue("Background Color for the Pending checkbox are not displayed",actualPendingColor.equals(pendingColor));
        driver.findElement(By.xpath("//input[@id='pending']/following::label")).click();

    }

    public void verifyOverviewPage() {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyOverviewPage();
    }

    public void cancelFair(String fairName, String cancelationReason) {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        fairName = FairName;
        waitUntilPageFinishLoading();
        while (driver.findElements(By.xpath("//span[text()='View More Upcoming Events']/..")).size() > 0) {
            viewMoreUpcomingEventsLink().click();
            waitUntilPageFinishLoading();
        }
        waitUntil(ExpectedConditions.visibilityOf(fairElementDetails(fairName)));
        fairElementDetails(fairName).click();
        editFairButton().click();
        waitUntil(ExpectedConditions.visibilityOf(cancelThisCollegeFair()));
        cancelThisCollegeFair().click();
        try {
            noGoBackButton().isDisplayed();
        } catch (NoSuchElementException e) {
            cancelThisCollegeFair().click();
        }
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
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        addFairButton().click();
        fairNameTextBox().sendKeys(fairDetails.get(0).get(1));
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(dateCalendarIcon()));
        dateCalendarIcon().click();
        String fairDay;
        String fairMonth;

        if (fairDetails.get(1).get(1).contains("In") && fairDetails.get(1).get(1).contains("day")) {
            int days = Integer.parseInt(fairDetails.get(1).get(1).replaceAll("[^0-9]", ""));
            fairDay = getRelativeDate(days).split(" ")[0];
            fairMonth = getRelativeDate(days).split(" ")[1];
        } else {
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
        if (fairDetails.get(4).get(1).contains("In") && fairDetails.get(4).get(1).contains("day")) {
            int days = Integer.parseInt(fairDetails.get(4).get(1).replaceAll("[^0-9]", ""));
            rsvpDay = getRelativeDate(days).split(" ")[0];
            rsvpMonth = getRelativeDate(days).split(" ")[1];
        } else {
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

    public void selectCancel() {
        moveToElement(cancellationMessageTextBox());
        cancellationMessageTextBox().sendKeys("by QA");
        cancelVisitButton().click();
        waitForUITransition();
    }

    public void verifyCalendarPageForCancelVisit(String university, String time, String date) {
        String startTime = getRescheduleVisitStartTimeInCalendar();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/label[text()='College Fair - Confirmed']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        //  pendingCheckBoxInCalendarPage().click();
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
        if (calendarAppointments(startTime, university).size() == 1) {
            selectAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    selectAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        selectAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                selectAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void verifyActivityforFairs(String option, String user, String university, String date, String time) {
        getNavigationBar().goToRepVisits();
        notificationAndTasks().click();
        activityTab().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='" + user + "']")));
        Assert.assertTrue("user name is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']")).isDisplayed());
        Assert.assertTrue("notification is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        Assert.assertTrue(option + " is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        String Date = driver.findElement(By.xpath("//b[text()='" + university + "']/parent::div/following-sibling::div/span[contains(text(),'" + time + "')]/parent::div/span")).getText();
        String actualDate[] = Date.split(" at");
        String originalDate = actualDate[0];
        String fairsDate = selectdateForActivity(date);
        Assert.assertTrue("Date is not equal", originalDate.equals(fairsDate));
    }

    public void accessViewDetailsPageforFair(String fairNametoClickViewDetails) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        collegeFairs().click();
        waitUntilPageFinishLoading();


        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
            waitUntilPageFinishLoading();
        }


        fairNametoClickViewDetails = FairName;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(fairsViewDetails(fairNametoClickViewDetails)));
        selectViewDetails(fairNametoClickViewDetails).click();
        waitUntilPageFinishLoading();
    }

    public void viewDetailsPageforEditedFair(String fairNametoClickViewDetails) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
            waitUntilPageFinishLoading();
        }
        fairNametoClickViewDetails = FairName;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(fairsViewDetails(fairNametoClickViewDetails)));
        selectViewDetails(fairNametoClickViewDetails).click();
        waitUntilPageFinishLoading();
    }

    public void verifyAndSelectAppointmentInCalendarPage(String university, String time, String date, String option) {
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() == 1) {
            selectAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    selectAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        selectAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                selectAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void rescheduleVisitInCalendar(String university, String time, String date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarDayButton()));
        collegeFairTextBoxInCalendarPage().click();
        //  pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        String startTime = getCalendarVisitTime().toUpperCase();
        if (calendarAppointments(startTime, university).size() == 1) {
            selectAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    selectAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        selectAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                selectAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public int getAppointmentFromCalendar(String startTime, String university) {
        if (getMonthRow().size() > 0) {
            outerloop:
            for (int i = 1; i <= 7; i++) {
                for (int j = 1; j <= 7; j++) {
                    int rowCount = getShowMoreRowCount(i, j);
                    if (rowCount > 0) {
                        jsClick(selectShowMoreButton(i, j, rowCount));
                        waitUntilPageFinishLoading();
                        if (calendarAppointments(startTime, university).size() == 1) {
                            break outerloop;
                        } else {
                            jsClick(selectShowMoreButton(i, j, rowCount));
                            waitUntilPageFinishLoading();
                        }
                    }
                }
            }
        }
        return calendarAppointments(startTime, university).size();
    }

    public void selectAppointmentInCalendar(String startTime, String university) {
        Assert.assertTrue("Appointment is not displayed", calendarAppointment(startTime, university).isDisplayed());
        calendarAppointment(startTime, university).click();
        waitUntilPageFinishLoading();
    }

    private int getShowMoreRowCount(int firstIndex, int secondIndex) {
        int count = 0, i;
        for (i = 3; i <= 7; i++) {
            if (showMoreButton(firstIndex, secondIndex, i).size() == 1) {
                count = i;
                break;
            }
        }
        return count;
    }

    public void completeTheSetupWizard() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        if (availabilityandSettings().size() == 1) {
            availabilityAndSettings().click();
            waitUntilPageFinishLoading();
            waitForUITransition();
        }
        if (regularWeeklyHoursTab().size() == 0) {
            while (takeMetoMyVisitsButton().size() == 0) {
                if (optinYesRadioButton().size() == 1) {
                    jsClick(optInYesRadioButton());
                } else if (completePage().size() == 1) {
                    jsClick(allRepVisitsUsersRadioButton());
                } else if (getDriver().findElements(By.cssSelector("input#notification_contacts_primary_contact_phone")).size() > 0) {
                    if (primaryContactPhone().getText().length() < 10)
                        primaryContactPhone().sendKeys("1234567890");
                }
                nextButton().click();
                waitForUITransition();
            }
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            waitUntilElementExists(todayText());
        }
    }

    public void createVisitForNaviance(String date, String startTime, String endTime, String numVisits, String attendee, String location) {
        addNewTimeSlotAndAddVisit(date, startTime, endTime, numVisits, attendee, location);
        waitForUITransition();
    }

    public void verifyEditFairPopup(String fairName, String fairStartTime, String date) {
        fairName = FairName;
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("edit-college-fair"), 1));
        waitUntilPageFinishLoading();
        Assert.assertTrue("Edit button is not displayed", editButtonInCollegeFair().isDisplayed());
        editButtonInCollegeFair().click();
        waitUntilPageFinishLoading();
        String displayedFairName = fairsNameInEditFairsPopup().getAttribute("value");
        Assert.assertTrue("Fairname is not equal", fairName.equals(displayedFairName));
        String originalDate = getSpecificDateForFairsEdit(date);
        String displayedDate = fairsDateInEditFairsPopup().getAttribute("value");
        Assert.assertTrue("Date is not equal", displayedDate.equals(originalDate));
    }

    public void rescheduleFairs(String newFairsStartTime) {
        editFairsStartTimeTextBox().click();
        editFairsStartTimeTextBox().sendKeys(newFairsStartTime);
        editFairsEndTimeTextBox().sendKeys(Keys.PAGE_DOWN);
        waitUntilPageFinishLoading();
        editFairsMaxNoOfCollegesTextBox().sendKeys(Keys.PAGE_DOWN);
        waitUntilPageFinishLoading();
        editFairsEmailMsgTextBox().sendKeys(Keys.PAGE_DOWN);
        waitUntilPageFinishLoading();
        editFairsSubmitButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Close')]")));
        closeFairCancelConfiration().click();
        waitForUITransition();
    }

    public void cancelCollegeFairs() {
        logger.info("Cancelling Job Fair " + FairName + " under RepVisits.");
        getDriver().navigate().refresh();
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        try {
            while (viewDetails().size() > 0) {
                waitUntilElementExists(viewDetailsLink());
                viewDetailsLink().click();
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='edit-college-fair']")));
                editCollegFair().click();
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Cancel This College Fair']")));
                jsClick(cancelFairsButton());
                waitForUITransition();
                if (getDriver().findElements(By.xpath("//span[contains(text(), 'Yes, Cancel this fair')]")).size() >= 1) {
                    button("yes, cancel this fair").click();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='Close']"), 1));
                } else if (getDriver().findElements(By.xpath("//span[contains(text(), 'Cancel fair and notify colleges')]")).size() >= 1) {
                    textbox(By.name("cancellationMessage")).sendKeys("College fair has been cancelled.");
                    button("cancel fair and notify colleges").click();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Close')]")));
                    closeFairCancelConfiration().click();
                }
                collegeFairs().click();
                waitForUITransition();
            }
        } catch (Exception e) {
        }
        getDriver().navigate().refresh();
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
    }

    public void cleanVisitsForParticularMonth(String date){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        goToCurrentMonthInCalendar(date);
        selectAndCleanVisits(date);
    }

    public void verifyTimeSlotIsDisplayedInRegularWeeklyHoursTab(String startDate,String startTime,String endTime){
        time = StartTime.toUpperCase();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        navigateToException();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        int Date = Integer.parseInt(startDate);
        String Day = getSpecificDate(Date,"EEE").toUpperCase();
        int columnID = getColumnIdFromTable( "//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']/thead",Day );
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody", columnID, time);

        if(columnID>= 0 && rowID>= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            //Remove Time slot
            WebElement timeSlot = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody//tr[" + rowID + "]//td[" + columnID + "]//button"));
            timeSlot.click();
            Assert.assertTrue("Added time slot is not displayed",driver.findElement(By.xpath("//span[contains(text(),'"+StartTime+" - "+endTime+"')]")).isDisplayed());

        }else{
            Assert.fail("The Time Slot "+time+"is not displayed in Regular weekly hours ");
        }
    }

    /**
     * verify the request subtab is enabled by default, after clicking notification and tasks
     */
    public void verifySubTabInNotifications() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(notificationsAndTasks());
        notificationsAndTasks().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/rep-visits/notifications/requests']")));
        String value=getDriver().findElement(By.cssSelector("a[href='/rep-visits/notifications/requests']")).getAttribute("class");
        Assert.assertTrue("Request SubTab is not active",value.equals("menu-link active"));
    }

    public void selectAndCleanVisits(String date) {
        WebElement visitInOverlay;
       try{ while (driver.findElements(By.cssSelector(appointmentLocator)).size() > 0) {
            visitInOverlay = driver.findElements(By.cssSelector(appointmentLocator)).get(0);
            visitInOverlay.click();
            if (driver.findElements(By.xpath(noGoBackButtonLocator)).size() > 0) {
                driver.findElements(By.xpath(noGoBackButtonLocator)).get(0).click();
            }
            waitUntil(ExpectedConditions.elementToBeClickable(button("Cancel This Visit")));
            for (int k = 0; k < 5; k++) {
                button("Cancel This Visit").click();
                if (driver.findElements(By.cssSelector(cancelMessageFieldLocator)).size() == 0) {
                    button("Cancel This Visit").click();
                } else {
                    break;
                }
            }
            getMessageRegardingCancellationTextBox().sendKeys("Cancel");
            cancelVisitButton().click();
            if (driver.findElements(By.xpath(failedToCancelMessageLocator)).size() > 0) {
                driver.get(driver.getCurrentUrl());
                waitUntilPageFinishLoading();
                collegeFairConfirmedCheckbox().click();
                goToCurrentMonthInCalendar(date);
            } else {
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(visitFormLocator), 0));
            }
        }
       }catch (Exception e){}
    }

    public void goToCurrentMonthInCalendar(String date){
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
    }

    public void verifyAnnouncementTitleIsDisplaying(String announcementTitle){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(announcementTitle(announcementTitle)));
        Assert.assertTrue("Announcement title is not displayed",verifyAnnouncementTitle(announcementTitle).isDisplayed());
    }

    public void verifyReadMoreButtonIsDisplaying(){
        Assert.assertTrue("Read more button is not displayed",readMoreButton().isDisplayed());
    }

    public void verifyAnnouncementDetailsAfterSuccessMessage(String announcement){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(announcementTitle(announcement)));
        Assert.assertTrue("Announcement is not displayed",verifyAnnouncementTitle(announcement).isDisplayed());
    }

    public void verifyDismissButtonInAnnouncementDetails(){
        Assert.assertTrue("Dismiss button is not displayed",announcementDismissButton().isDisplayed());
    }

    public void clickDismissButton(){
        waitUntil(ExpectedConditions.elementToBeClickable(dismissButton()));
        jsClick(announcementDismissButton());
    }

    public void verifyAnnouncementIsNotDisplaying(){
        Assert.assertTrue("Announcement is displayed",announcementsDismissButton().size()==0);
    }

    public void verifyProductAnnouncementContent(String content){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(content(content)));
        Assert.assertTrue("Content is not displaying",announcementContent(content).isDisplayed());
    }

    public void verifyReadMoreButtonIsNotDisplaying(){
        Assert.assertTrue("Read more button is displayed",readMorebutton().size()==0);
    }

    public void verifyTextInReschedulePopup(String expectedText) {
        String actualText = driver.findElement(By.xpath("//span[@class='_25XyePHsmpWU1qQ18ojKip']/span")).getText();
        Assert.assertTrue(expectedText + " is not displayed", actualText.equals(expectedText));
    }

    public void verifyUniversityInReschedulePopup(String university) {
        Assert.assertTrue(university + " is not displayed", driver.findElement(By.xpath("//div[text()='" + university + "']")).isDisplayed());
    }

    public void verifyDateInReschedulePopup(String date) {
        String actualDate = getSpecificDateForReschedule(date);
        Assert.assertTrue("Date is not displayed", driver.findElement(By.xpath("//div/span[text()='" + actualDate + "']")).isDisplayed());
    }

    public void verifyTimeInReschedulePopup(String time) {
        time = getVisitStartTimeInReschedule();
        Assert.assertTrue("Time is not displayed", driver.findElement(By.xpath("//div/span[text()='" + time + "']")).isDisplayed());
    }

    public void verifyCalendarPageForCanceltheVisit(String university, String time, String date) {
        String startTime = getVisitSchedulePopupStartTimeInCalendar();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/label[text()='College Fair - Confirmed']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        //  pendingCheckBoxInCalendarPage().click();
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
        Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']")).isDisplayed());
        Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyCancelVisitPopup(String option, String user, String time, String institution, String date, String startTime) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[contains(text(),'" + institution + "')]"), 1));
        startTime = getVisitSchedulePopupStartTime();
        Assert.assertTrue("Institution name is not displayed", driver.findElement(By.xpath("//div[contains(text(),'" + institution + "')]")).isDisplayed());
        Assert.assertTrue("Username is not displayed", driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]")).isDisplayed());
        String actualDate = selectdate(date);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='" + actualDate + "']"), 1));
        Assert.assertTrue("Date is not displayed", driver.findElement(By.xpath("//span[text()='" + actualDate + "']")).isDisplayed());
        Assert.assertTrue("Time is not displayed", driver.findElement(By.xpath("//div/span[text()='" + startTime + "']")).isDisplayed());
        eventLocationTextboxInSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        internalNotesTextBoxInReschedulePopup().sendKeys(Keys.PAGE_DOWN);
        button(option).click();
    }

    private String getRelativeDate(int addDays) {
        String day;
        Calendar relativeDate = getDeltaDate(addDays);
        day = getDay(relativeDate);
        if (day.startsWith("0")) {
            day = day.replace("0", "");
        }
        return day + " " + getMonth(relativeDate) + " " + getYear(relativeDate);
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

    public void loadSetupWizardPage() {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardCheckmarkButton()));
        List<WebElement> takeMeToVisits = getDriver().findElements(By.xpath("//button/span[text()='Take me to my visits']"));
        List<WebElement> welcomeWizard = getDriver().findElements(By.xpath("//h1/span[text()='Tell us about your High School']"));
        if(takeMeToVisits.size()==1){
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Today']")));
            load(GetProperties.get("hs.WizardAppSelect.url"));
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardText()));
        }else if(completeWizardActiveStep().size() == 1){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            jsClick(allRepVisitsUsersRadioButton());
            nextButton().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Take me to my visits']")));
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Today']")));
            load(GetProperties.get("hs.WizardAppSelect.url"));
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardText()));
        }else if(welcomeWizard.size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            while (navianceSettingsActiveStep().size() == 0) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
                nextButton().click();
                waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            }
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            WebElement yesOption = getDriver().findElement(By.xpath("//label[text()='Yes, I would like to connect Naviance and RepVisits']/parent::div/input"));
            jsClick(yesOption);
            nextButton().click();
            waitUntilPageFinishLoading();
            while (completeWizardActiveStep().size() == 0) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
                nextButton().click();
                waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            }
            jsClick(allRepVisitsUsersRadioButton());
            nextButton().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Take me to my visits']")));
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Today']")));
            load(GetProperties.get("hs.WizardAppSelect.url"));
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardText()));
        } else if(welcomeWizard.size() == 1) {
            logger.info("The Setup Wizard Welcome page is displayed");
        } else {
            Assert.fail("Error in the Setup Wizard page");
        }
    }


    public void verifyRepvisitsSetupWizardTimeZoneMilestones() {
        while (highSchoolInformation().size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            nextButton().click();
            waitUntilPageFinishLoading();
        }
        //verify UI
        Assert.assertTrue("'Tell us about your high school' is not displayed", highSchoolTextInSetupWizard().isDisplayed());
        Assert.assertTrue("'Please specify your high school's time zone.' is not showing", timeZoneText().isDisplayed());

        Assert.assertTrue(backButton().isDisplayed());
        Assert.assertTrue(nextButton().isDisplayed());

    }
    /*
       Fetching the college Fair and storing
        */
    public String storeCollegeFairName(){
        CollegeFairName= collegeFair().getText();
        return CollegeFairName;
    }

    private WebElement collegeFair(){
        return getDriver().findElement(By.className("_3y2dJIL3bALBmrjH1BQ_yy"));
    }
    /*
      Performing click on Unpublish in College Fairs
     */
    public void chooseUnpublish(){
        List<WebElement> closeEle=driver.findElements(By.xpath("//i[contains(@class,'close icon _3AcltzPxtgX0rUCbxyMhN_')]"));
        if(!closeEle.isEmpty()){
            closeEle.get(0).click();
        }
        WebElement ele=unpublishOption();
        scrollDown(ele);
        ele.click();

    }
    /*
       Verifying the text after Un publishing it
     */
    public void assertUnpublish(){
        String  unpublishMsg =UnpublishedTextEle().getText();
        String successTxt=CollegeFairName;
        successTxt.concat(" has been unpublished.");
        Assert.assertTrue("FAIL:Unable to Unpublish the Fair:"+CollegeFairName,unpublishMsg.contains(successTxt));
    }

    public void navigateToFairsAndVisistsAndVerifyEachScreen() {

        //verifying the navigation of corresponding screen for 'Visits'
        loadSetupWizardPage();
        doubleClick(visitsRadioButton());
        while (availabilityActiveInSetupWizard().size() == 0) {
            nextButton().click();
            waitUntilPageFinishLoading();
        }

        Assert.assertTrue("'Availability' is not displayed", regularWeeklyHoursText().isDisplayed());

        //verifying the navigation of corresponding screen for 'Fairs'
        loadSetupWizardPage();
        doubleClick(fairsRadioButton());
        while (collegeFairsActiveInSetupWizard().size() > 0) {
            waitUntilElementExists(nextButton());
            nextButton().click();
            waitUntilPageFinishLoading();
        }
        nextButton().click();
        nextButton().click();
        Assert.assertTrue("'College Fairs' is not displayed", collegeFairText().isDisplayed());

        //verifying the navigation of corresponding screen for 'Visits and Fairs'
        loadSetupWizardPage();
        jsClick(visitsAndFairsRadioButton());
        while (availabilityActiveInSetupWizard().size() == 0) {
            nextButton().click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("'Availability' is not displayed", regularWeeklyHoursText().isDisplayed());
    }


    public void verifyTimeZoneInRepVisits(String alreadySelectedTimeZone, String newTimeZone) {
        String timeZoneToSet;
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardText()));
        doubleClick(visitsAndFairsRadioButton());
        waitUntilPageFinishLoading();
        nextButton().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        //verify time zone saving properly
        waitUntilElementExists(nextButton());
        String timeZoneBeforeChange = searchSelectionDropDown().getText();
        if (timeZoneBeforeChange.contains(alreadySelectedTimeZone)) {
            timeZoneToSet = newTimeZone;
        } else {
            timeZoneToSet = alreadySelectedTimeZone;
        }
        setTimeZoneValue(timeZoneToSet);

        backButton().click();
        nextButton().click();

        waitForUITransition();
        String actualTimeZoneWhenBackBtnClicked = searchSelectionDropDown().getText();
        Assert.assertTrue("'Timezone saved when click on back button'", !actualTimeZoneWhenBackBtnClicked.contains(timeZoneToSet));

        setTimeZoneValue(timeZoneToSet);
        nextButton().click();
        waitUntilPageFinishLoading();
        backButton().click();
        //driver.navigate().back();

        String actualTimeZoneWhenNextButtonClicked = searchSelectionDropDown().getText();
        Assert.assertTrue("'Timezone is not saved when click on Next button'", actualTimeZoneWhenNextButtonClicked.contains(timeZoneToSet));
        setTimeZoneValue(newTimeZone);
        nextButton().click();
    }

    public void clickUpdateButtonInRepVisits() {
        if (driver.findElements(By.xpath("//button[@class='ui primary button _3M36944K6FppJHU9VD7kZy']")).size() > 0) {
            waitUntil(ExpectedConditions.visibilityOf(updateBtn()));
            updateBtn().click();
        }
    }

    public void verifyStartDateAndEndDateInAvailabilitySetting(String startDate, String endDate) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();

        String valStartDate = driver.findElement(By.xpath("//div[@style='display: inline-block; position: relative;']/button[1]/b/span")).getText();
        String valEndDate = driver.findElement(By.xpath("//div[@style='display: inline-block; position: relative;']/button[2]/b/span")).getText();

        Assert.assertTrue("Start date is not as expected", startDate.contains(valStartDate));
        Assert.assertTrue("End date is not as expected", endDate.contains(valEndDate));
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
                } else {
                    Assert.assertTrue("The Time Slot was removed", true);
                }
            }

        } catch (Exception e) {
            Assert.fail("Time Slot was not removed.");
        }
    }

    public void loadSetupWizardWelcomePage() {
        waitForUITransition();
        load(GetProperties.get("hs.WizardAppWelcome.url"));
        waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> welcomeWizardPage = getDriver().findElements(By.xpath("//h1/span[text()='Welcome to RepVisits']"));
        if(completeWizardActiveStep().size() == 1){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            jsClick(allRepVisitsUsersRadioButton());
            nextButton().click();
            waitUntilPageFinishLoading();
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            load(GetProperties.get("hs.WizardAppWelcome.url"));
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardWelcomePageText()));
        }else if(welcomeWizardPage.size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            while (navianceSettingsActiveStep().size() == 0) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
                nextButton().click();
                waitUntilPageFinishLoading();
            }
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            WebElement yesOption = getDriver().findElement(By.xpath("//label[text()='Yes, I would like to connect Naviance and RepVisits']/parent::div/input"));
            jsClick(yesOption);
            nextButton().click();
            waitUntilPageFinishLoading();
            while (completeWizardActiveStep().size() == 0) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
                nextButton().click();
                waitUntilPageFinishLoading();
            }
            jsClick(allRepVisitsUsersRadioButton());
            nextButton().click();
            waitUntilPageFinishLoading();
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            load(GetProperties.get("hs.WizardAppWelcome.url"));
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardWelcomePageText()));
        } else if(welcomeWizardPage.size() == 1) {
            logger.info("The Setup Wizard Welcome page is displayed");
        } else {
            Assert.fail("Error in the Setup Wizard page");
        }
    }

    public void verifyWelcomeWizard() {
        waitForUITransition();
        loadSetupWizardWelcomePage();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Welcome to Repvisits' text is not displayed", setupWizardWelcomeText().isDisplayed());
        Assert.assertTrue("'Get Started' button is not displayed", getStartedBtn().isDisplayed());
    }

    public void verifyRepvisitsSetupWizardNonNaviance() {
        driver.navigate().to("https://qa-hs.intersect.hobsons.com/rep-visits/setup/welcome/");
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOf(button("GET STARTED")), 30);
        button("GET STARTED").click();
        waitUntilPageFinishLoading();
        String url = getDriver().getCurrentUrl();
        while (!url.equalsIgnoreCase("https://qa-hs.intersect.hobsons.com/rep-visits/setup/completed/visible")) {
            if (!url.toLowerCase().contains("naviance")) {
                waitForUITransition();
                try {
                    waitUntilElementExists(nextButton());
                    nextButton().click();
                    url = getDriver().getCurrentUrl();
                } catch (Exception e) {
                    waitUntilElementExists(takeMeToMyVisitsButton());
                    takeMeToMyVisitsButton().click();
                    break;
                }

            } else
                Assert.assertTrue("Naviance options are available in RepVisits setup wizard for a non-Naviance HS.", false);
        }
//        waitUntilElementExists(driver.findElement(By.xpath("//button/span[text()='Take me to my visits']")));
//        driver.findElement(By.xpath("//button/span[text()='Take me to my visits']")).click();
    }

    public void setRepVisitWelcomeRadioOptions(String visitsAndOrFairs) {
        String radioOption = "";
        switch (visitsAndOrFairs) {
            case "Visits":
                radioOption = "VISITS";
                break;
            case "Fairs":
                radioOption = "FAIRS";
                break;
            case "Visits and Fairs":
                radioOption = "VISITS_AND_FAIRS";
                break;
            default:
                Assert.assertTrue("Option entered is not available, the only options available are Visits, Fairs, or Visits and Fairs.", false);
        }
        getDriver().findElement(By.xpath("//input[@value='" + radioOption + "']")).click();

    }

    public void setRepVisitsHighSchoolInformationDropdown(String timezone) {
        getDriver().findElement(By.xpath("//div[@class='ui search selection dropdown']/i[@class='dropdown icon']")).click();
        getDriver().findElement(By.xpath("//span[text()='" + timezone + "']")).click();
    }

    public void clickGetStartedBtn() {
        if (getStartedBtn().isDisplayed()) {
            getStartedBtn().click();
        }
    }

    /**
     * Sets the date in a RepVisits date picker to the date 'addDays' from the date of execution.
     *
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
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p/span[text()='" + FairName + "']/parent::p")));
        Assert.assertTrue("Success Message for the fair " + createFairName + " is not displayed", fairsSuccessMessage().isDisplayed());
        Assert.assertTrue("'Close' button is not displayed", fairsCloseButton().isDisplayed());
    }

    public void accessSuccessMessageforFair(String buttonName) {
        if (buttonName.equals("Close")) {
            waitForUITransition();
            fairsCloseButton().click();
        } else if (buttonName.equals("Add Attendees")) {
            addAttendeeButton().click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }

    public void verifyEditCollegeFair(String collegeFairName, String date, String startTime, String endTime, String RSVPDate, String cost, String maxNumberofColleges, String numberofStudentsExpected) {
        Assert.assertTrue("Page Title is not displayed", driver.findElement(By.xpath("//h1/span[text()='Edit College Fair']")).isDisplayed());
        Assert.assertTrue("'College Fair Details' Title is not displayed", driver.findElement(By.xpath("//h2/span[text()='College Fair Details']")).isDisplayed());
        Assert.assertTrue("'College Fair Name' Label is not displayed", text("College Fair Name").isDisplayed());
        Assert.assertTrue("'Date' Label is not displayed", driver.findElement(By.xpath("//label[text()='Date']")).isDisplayed());
        Assert.assertTrue("'Start Time' Label is not displayed", driver.findElement(By.xpath("//label[text()='Start Time']")).isDisplayed());
        Assert.assertTrue("'End Time' Label is not displayed", driver.findElement(By.xpath("//label[text()='End Time']")).isDisplayed());
        Assert.assertTrue("'RSVP Deadline' Label is not displayed", driver.findElement(By.xpath("//label[text()='RSVP Deadline']")).isDisplayed());
        Assert.assertTrue("'Cost' Label is not displayed", driver.findElement(By.xpath("//label[text()='Cost']")).isDisplayed());
        Assert.assertTrue("'Max Number of Colleges' Label is not displayed", driver.findElement(By.xpath("//label[text()='RSVP Deadline']")).isDisplayed());
        Assert.assertTrue("'Number of Students Expected' Label is not displayed", driver.findElement(By.xpath("//label[text()='Max Number of Colleges']")).isDisplayed());
        Assert.assertTrue("'Instructions for College Representatives' Label is not displayed", driver.findElement(By.xpath("//label[text()='Number of Students Expected']")).isDisplayed());
        Assert.assertTrue("'Automatically Confirm Incoming Requests From Colleges?' Label is not displayed", driver.findElement(By.xpath("//label[text()='Instructions for College Representatives']")).isDisplayed());
        Assert.assertTrue("'Settings' Label is not displayed", driver.findElement(By.xpath("//h2/span[text()='Settings']")).isDisplayed());
        Assert.assertTrue("'Email Message to Colleges After Confirmation' Label is not displayed", driver.findElement(By.xpath("//label[text()='Email Message to Colleges After Confirmation']")).isDisplayed());
        Assert.assertTrue("'Cancel This College Fair' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
        Assert.assertTrue("'Publish/Unpublish' Button is not displayed", driver.findElement(By.cssSelector("button[class='ui basic primary button']")).isDisplayed());

        if (!collegeFairName.equals("")) {
            String currentFairName = textbox(By.id("college-fair-name")).getAttribute("value");
            Assert.assertTrue("'College Fair Name' value was not as expected.", currentFairName.equals(collegeFairName));
            driver.findElement(By.xpath("//input[@id='college-fair-name']")).sendKeys(Keys.PAGE_DOWN);
        }
        if (!date.equals("")) {
            String actualDate = driver.findElement(By.id("college-fair-date")).getAttribute("value");
            String fairDate = selectdate(35);
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if (!startTime.equals("")) {
            String currentStartTime = textbox(By.id("college-fair-start-time")).getAttribute("value");
            Assert.assertTrue("'Start Time' value was not as expected.", currentStartTime.equals(startTime));
        }
        if (!endTime.equals("")) {
            String currentEndTime = textbox(By.id("college-fair-end-time")).getAttribute("value");
            Assert.assertTrue("'End Time' value was not as expected.", currentEndTime.equals(endTime));
        }
        if (!RSVPDate.equals("")) {
            String actualDate = driver.findElement(By.id("college-fair-rsvp-deadline")).getAttribute("value");
            String fairDate = selectdate(2);
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if (!cost.equals("")) {
            String currentCost = textbox(By.id("college-fair-cost")).getAttribute("value");
            Assert.assertTrue("'Cost' value was not as expected.", currentCost.equals(cost));
        }
        if (!maxNumberofColleges.equals("")) {
            String currentMaxNumberofColleges = textbox(By.id("college-fair-max-number-colleges")).getAttribute("value");
            Assert.assertTrue("'Max Number of Colleges' value was not as expected.", currentMaxNumberofColleges.equals(maxNumberofColleges));
        }
        if (!numberofStudentsExpected.equals("")) {
            String currentNumberofStudentsExpected = textbox(By.id("college-fair-number-expected-students")).getAttribute("value");
            Assert.assertTrue("'Max Number of Colleges' value was not as expected.", currentNumberofStudentsExpected.equals(numberofStudentsExpected));
        }

    }


    public String selectdate(int addDays) {
        String DATE_FORMAT_NOW = "EEEE, MMM dd, yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void accessEditCollegeFair(String collegeFairName, String date, String cost, String maxNumberofColleges, String numberofStudentsExpected, String instructionsforCollegeRepresentatives, String emailMessagetoCollegesAfterConfirmation, String RSVPDate, String buttonToClick) {
        if (!collegeFairName.equals("")) {
            WebElement currentCollegeFairName = textbox(By.id("college-fair-name"));
            currentCollegeFairName.clear();
            currentCollegeFairName.sendKeys(FairName);
        }
        if (!date.equals("")) {
            WebElement datepicker = driver.findElement(By.xpath("//input[@id='college-fair-date']/following-sibling::i[@class='calendar alternate outline large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
            datepicker.click();
            setRelativeDate(30);
        }

        if (!cost.equals("")) {
            WebElement currentCost = textbox(By.id("college-fair-cost"));
            currentCost.clear();
            currentCost.sendKeys(cost);
        }
        if (!maxNumberofColleges.equals("")) {
            WebElement currentMaxNumberofColleges = textbox(By.id("college-fair-max-number-colleges"));
            currentMaxNumberofColleges.clear();
            currentMaxNumberofColleges.sendKeys(maxNumberofColleges);
        }
        if (!numberofStudentsExpected.equals("")) {
            WebElement currentNumberofStudentsExpected = textbox(By.id("college-fair-number-expected-students"));
            currentNumberofStudentsExpected.clear();
            currentNumberofStudentsExpected.sendKeys(numberofStudentsExpected);
        }
        if (!instructionsforCollegeRepresentatives.equals("")) {
            WebElement currentInstructionsforCollegeRepresentatives = driver.findElement(By.cssSelector("textarea[id='college-fair-instructions']"));
            currentInstructionsforCollegeRepresentatives.clear();
            currentInstructionsforCollegeRepresentatives.sendKeys(instructionsforCollegeRepresentatives);
        }

        if (!emailMessagetoCollegesAfterConfirmation.equals("")) {
            WebElement currentEmailMessagetoCollegesAfterConfirmation = driver.findElement(By.cssSelector("textarea[id='college-fair-email-message-to-colleges']"));
            currentEmailMessagetoCollegesAfterConfirmation.clear();
            currentEmailMessagetoCollegesAfterConfirmation.sendKeys(emailMessagetoCollegesAfterConfirmation);
        }
        if (!buttonToClick.equals("")) {
            if (buttonToClick.equals("Cancel This College Fair")) {
                Assert.assertTrue("'Cancel This College Fair' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).click();
            } else if (buttonToClick.equals("Save")) {
                Assert.assertTrue("'Save' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Save']")).isDisplayed());
                driver.findElement(By.xpath("//button/span[text()='Save']")).click();
            } else {
                Assert.fail("The option for the button to click =" + buttonToClick + " is not a valid one");
            }
        }
    }

    public void verifySuccessMessageforEditFair(String EditFairName) {
        Assert.assertTrue("Success Message for the fair " + EditFairName + " is not displayed", driver.findElement(By.xpath("//p/span[text()='" + EditFairName + "']/parent::p")).isDisplayed());
    }

    public void accessCollegeFairOverviewPage(String fairName) {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("add-college")));
        while (moreUpComingEvents().size() > 0) {
            viewMoreUpcomingEventsLink().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
        }
        fairName = FairName;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(fairsViewDetails(fairName)));
        selectViewDetails(fairName).click();
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month);

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
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }

    /**
     * Verify the correct Availability of the dates range from April 1st 2018 to July 15th 2019
     *
     * @date String with the date selected Available and unavailable
     * @day String with the day selected Available and unavailable
     */
    public void verifyEnabledDates(String date, String day) {
        Boolean verifyDateEnabled = dateEnabled(date, day);
        assertTrue("The Date is not available to select ", verifyDateEnabled);
        getDriver().findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
        Boolean verifyDateDisabled = dateDisabled(date, day);
        assertTrue("The Date is not available to select ", verifyDateDisabled);
    }

    /**
     * Verify the correct unavailability of the dates range from April 1st 2018 to July 15th 2019
     *
     * @date String with the date selected Available and unavailable
     * @day String with the day selected Available and unavailable
     */
    public void verifyDisabledDates(String date, String day) {
        Boolean verifyDateEnabled = dateEnabled(date, day);
        assertTrue("The Date is not available to select ", verifyDateEnabled);
        getDriver().findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
        Boolean verifyDateDisabled = dateDisabled(date, day);
        assertTrue("The Date is not available to select ", verifyDateDisabled);
    }

    /**
     * Return a boolean value if date it's available
     *
     * @date String with the date selected Available and unavailable
     * @day String with the day selected Available and unavailable
     */
    public boolean dateEnabled(String date, String day) {
        Boolean enabledOrDisabledDate = false;
        try {
            enabledOrDisabledDate = getDriver().findElement(By.cssSelector("div[class='DayPicker-Body']")).findElement(By.xpath("//div[contains(@class,'DayPicker-Day') and @aria-disabled='false' and text()='" + day + "']")).isDisplayed();
        } catch (final UnsupportedOperationException e) {
            logger.error(e.getMessage());
        } catch (final WebDriverException e) {
            Assert.fail("The date " + day + " for next month than " + date + " was not disabled: " + e.getMessage());
        }
        return enabledOrDisabledDate;

    }

    /**
     * Return a boolean value if date it's unavailable
     *
     * @date String with the date selected Available and unavailable
     * @day String with the day selected Available and unavailable
     */
    public boolean dateDisabled(String date, String day) {
        Boolean enabledOrDisabledDate = false;
        try {
            enabledOrDisabledDate = getDriver().findElement(By.cssSelector("div[class='DayPicker-Body']")).findElement(By.xpath("//div[contains(@class,'DayPicker-Day DayPicker-Day--disabled') and @aria-disabled='true' and text()='" + day + "']")).isDisplayed();
        } catch (final UnsupportedOperationException e) {
            logger.error(e.getMessage());
        } catch (final WebDriverException e) {
            Assert.fail("The date " + day + " for previous month than " + date + " was not disabled: " + e.getMessage());
        }

        return enabledOrDisabledDate;

    }

    public void primaryContactDetailsforVisitsAndFairs() {
        while (getRepVisitsPrimaryContactPhoneNumerField().getAttribute("value").length() > 0)
            getRepVisitsPrimaryContactPhoneNumerField().sendKeys(Keys.BACK_SPACE);
        waitUntilElementExists(nextButton());
        nextButton().click();
        Assert.assertTrue("Phone number is a required field, but the error message was not displayed.", driver.findElement(By.xpath("//span[contains(text(),'Please enter a phone number. Ex: (555) 555-5555')]")).isDisplayed());
        getRepVisitsPrimaryContactPhoneNumerField().sendKeys("1234567890");
        waitUntilElementExists(nextButton());
        nextButton().click();
    }

    public void navigateToVisitsAndFairsWizard() {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(setUpWizardText()));
        Assert.assertTrue("welcome wizard is not displayed", welcomeWizardText().isDisplayed());
        visitAndFairsButton().click();

        while (notificationAndPrimaryContactActiveInSetupWizard().size() == 0) {
            waitUntilElementExists(nextButton());
            nextButton().click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("Primary Contact detail is not displayed", primaryContactText().isDisplayed());
        Assert.assertTrue("Primary Contact Phone Number is not displayed", phoneNoText().isDisplayed());
        Assert.assertTrue("Primary Contact Phone Number TextBox is not displayed", getRepVisitsPrimaryContactPhoneNumerField().isDisplayed());
    }

    public void navigateToAvailabilityAndSettings() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Availability is not displayed", driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Availability & Settings']")).isDisplayed());
        availabilityAndSettings().click();
        notificationAndPrimaryContact().click();
        waitUntilPageFinishLoading();
    }

    public void navigateToCollegeFairSettings() {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        Assert.assertTrue("College Fairs is not displayed", driver.findElement(By.xpath("//a[@class='menu-link qxSNjKWAyYiOIN9yZHE_d']/span[text()='College Fairs']")).isDisplayed());
//        driver.findElement(By.cssSelector("a[href='rep-visits/collegefairs/settings']>span")).click();
        waitForUITransition();
        waitUntilElementExists(collegeFairsSettings());
        collegeFairsSettings().click();
        waitUntilPageFinishLoading();
    }

    public void primaryContactDetailsforFairs() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id='notification_fairs_phone_number']")));
        while (getCollegeFairsPrimaryContactPhoneNumberField().getAttribute("value").length() > 0)
            getCollegeFairsPrimaryContactPhoneNumberField().sendKeys(Keys.BACK_SPACE);
        button("Save Settings").click();
        Assert.assertTrue("Phone number is a required field, but the error message was not displayed.", driver.findElement(By.xpath("//span[contains(text(),'Please enter a phone number. Ex: (555) 555-5555')]")).isDisplayed());
        getCollegeFairsPrimaryContactPhoneNumberField().sendKeys("1234567890");
        button("Save Settings").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void primaryContactDetailsinAvailabilityandSettings() {
        while (getRepVisitsPrimaryContactPhoneNumerField().getAttribute("value").length() > 0)
            getRepVisitsPrimaryContactPhoneNumerField().sendKeys(Keys.BACK_SPACE);
        button("Save changes").click();
        Assert.assertTrue("Phone number is a required field, but the error message was not displayed.", driver.findElement(By.xpath("//span[text()='Please enter a phone number. Ex: (555) 555-5555']")).isDisplayed());
        getRepVisitsPrimaryContactPhoneNumerField().sendKeys("1234567890");
        button("Save changes").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }


    private WebElement getStartedBtn() {
        return button("Get Started!");
    }

    public void goToWelcomeWizard() {
        waitForUITransition();
        loadSetupWizardWelcomePage();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
        nextButton().click();
    }

    public void navigateToRepvisitWizard(String wizardName) {
        while (activeWizard(wizardName).size() == 0) {
            nextButton().click();
        }
    }

    public void addTimeSlotInRegularWeeklyHours(String day, String startTime, String endTime, String noOfVisits, String option) {

        Assert.assertTrue("Regular Weekly Hours is not displayed", regularWeeklyHoursText().isDisplayed());
        deleteDuplicateSlot(startTime);
        addTimeSlotButton().click();

        doubleClick(selectDayDropDown());
        selectDay(day).click();

        visitStartTimeTextBox().sendKeys(startTime);
        visitEndTimeTextBox().sendKeys(endTime);
        numVisitsTextBox().sendKeys(noOfVisits);

        addTimeSlotButton().click();
        waitForUITransition();
        List<WebElement> displayingPopup = driver.findElements(By.xpath("//div/span[text()='Review Previously Deleted Time Slots']"));

        if (displayingPopup.size() == 1) {
            ignoreTimeSlotOption().click();
            addRegularHoursButton().click();
            waitUntilPageFinishLoading();
        } else if (option.contains("2")) {
            addTimeSlot().click();
            addRegularHoursButton().click();
        } else {
            logger.info("'Review Previously Deleted Timeslot' Popup are not found");
        }

        backButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("High school information is not displayed", highSchoolTextInSetupWizard().isDisplayed());
    }

    public void openExceptions() {
        getNavigationBar().goToRepVisits();
        availabilityAndSettingsButton().click();
        innerExceptionsButton().click();
    }

    public void selectDateInExceptions(String date) {
        chooseADateButton().click();
        repVisitsPageHEObj.pressMiniCalendarArrowUntil("right", date.split(",")[0], 10);
        repVisitsPageHEObj.miniCalendarDayCell(date.split(",")[1].split(" ")[1]).click();
    }

    public void addTimeSlot(DataTable timeSlotData) {
        waitUntil(ExpectedConditions.elementToBeClickable(newTimeSlotStartTime()));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(timeSlotStartTime()));
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

    public void navigateToSubTabsInAvailabilityWizard(String tabName) {
        while (setupWizardAvailability(tabName).size() == 0) {
            nextButton().click();
        }
    }

    public void selectBlockDaysAndSave(String dayName) {
        backButton().click();
        Assert.assertTrue("Regular Weekly Hours tab is not loaded", regularWeeklyHoursText().isDisplayed());
        nextButton().click();
        selectDayName(dayName).click();
    }

    public void changeWeekAvailability(String changeWeek) {
        backButton().click();
        Assert.assertTrue("Blocked Days tab is not loaded", blockedDayText().isDisplayed());
        nextButton().click();
        waitUntilElementExists(changeWeek(changeWeek));
        changeWeek(changeWeek).click();
    }

    public void availabilityandSettingsPage(String visitsConfirmations, String preventCollegesSchedulingNewVisits, String preventCollegesCancellingorRescheduling) {
        backButton().click();
        Assert.assertTrue("Exceptions tab is not loaded", exceptionText().isDisplayed());
        nextButton().click();
        Assert.assertTrue("Availability Settings tab is not loaded", availabilitySettingsText().isDisplayed());
        //Visit Confirmation
        visitConfirmation(visitsConfirmations).click();
        //Prevent Colleges Scheduling New Visits
        visitBox().clear();
        visitBox().sendKeys(preventCollegesSchedulingNewVisits);
        //Prevent Colleges Cancelling or Rescheduling
        cancellationBox().clear();
        cancellationBox().sendKeys(preventCollegesCancellingorRescheduling);
        nextButton().click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(messagingOptions()));
        Assert.assertTrue("Messaging Options tab is not loaded", messagingOptionsActive().isDisplayed());
    }

    public void setBlockedDate(String blockdate, String reason) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        blockedDays().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/div/span[text()='Choose Dates']"), 1));
        chooseDates().click();
        String Date = getSpecificDate(blockdate);
        setDateDoubleClick(Date);
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        selectReason.click();
        waitUntilPageFinishLoading();
        //       Actions action = new Actions(getDriver());
        WebElement element = driver.findElement(By.xpath("//span[text()='" + reason + "']"));
        //   action.click(element).build().perform();
        moveToElement(element);
        jsClick(element);
        addBlockedTime().click();
    }

    public void RemoveBlockedDate(String startDate, String endDate) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        blockedDays().click();
        //Remove specific Blocked Day
        //WebElement BlockedDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='"+startDate+"']/../following-sibling::td[@class='_1DmNQ0_pLQlqak2JJluwxn']/span"));
        //Remove all Blocked Days
        while (driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='Remove']")).getText() != "Remove") {
            waitUntilPageFinishLoading();
            WebElement BlockedDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='Remove']"));
            BlockedDate.click();
            waitForUITransition();

            int columnID = getColumnIdByFieldName("//table[@class='ui basic table']//thead", "Start Date");
            int rowID = getRowIdByColumnId("//table[@class='ui basic table']//tbody", columnID, startDate);
            rowID = rowID + 1;
            if (rowID < 1) {
                break;
            }
        }
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

    public void verifyPillsColorDays(String appointmentsStatus, String color) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        waitUntilElementExists(exception());
        exception().click();
        waitUntilPageFinishLoading();
        findAndVerifyAppointments(appointmentsStatus, color);
    }

    public void verifyBlockedHolidaysAdded(String startDate, String endDate, String reason) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        blockedDays().click();

        int columnID = getColumnIdByFieldName("//table[@class='ui basic table']//thead", "Start Date");
        int rowID = getRowIdByColumnId("//table[@class='ui basic table']//tbody", columnID, startDate);
        columnID = columnID + 1;
        rowID = rowID + 2;

        String verifyStartDate = getSpecificDateFormat(startDate);
        String verifyEndDate = getSpecificDateFormat(endDate);

        //verify Start Date
        String ActualStartDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr//td[" + columnID + "]")).getText();
        Assert.assertTrue("Start date " + verifyStartDate + "is not displayed", verifyStartDate.equals(ActualStartDate));
        //verify End date
        String ActualEnddate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr[" + rowID + "]//td[3]")).getText();
        Assert.assertTrue("End date " + verifyEndDate + "is not displayed", verifyEndDate.equals(ActualEnddate));
        //verify Reason
        String ActualReason = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr[" + rowID + "]//td[1]")).getText();
        Assert.assertTrue("Reason " + reason + " is not displayed", reason.equals(ActualReason));
        //verify Remove link
        Assert.assertTrue("Reason " + reason + " is not displayed", driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr[" + rowID + "]//td[3]")).isDisplayed());
    }


    public void verifyBlockedHolidaysRemoved(String startDate, String endDate, String reason) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        blockedDays().click();
        int columnID = getColumnIdByFieldName("//table[@class='ui basic table']//thead", "Start Date");
        int rowID = getRowIdByColumnId("//table[@class='ui basic table']//tbody", columnID, startDate);

        if (rowID == -1) {
            logger.info("The Given row with Start Date '" + startDate + "',End Date '" + endDate + "'with a '" + reason + "'was not found on the table");
        } else {
            Assert.fail("The Given row was found on the table");
        }
    }

    //Function Name  : getColumnIdByFieldName()
    public int getColumnIdByFieldName(String tableHeaderLocator, String fieldName) {
        int columnId = -1, colCount = 0;
        String presentField = null;

        WebElement webEleTableHeader = driver.findElement(By.xpath(tableHeaderLocator));
        List<WebElement> webEleRows = webEleTableHeader.findElements(By.tagName("tr"));
        List<WebElement> webEleColumns = webEleRows.get(0).findElements(By.tagName("th"));
        colCount = webEleColumns.size();

        if (colCount > 0) {
            for (int colNum = 0; colNum < colCount; colNum++) {
                presentField = webEleColumns.get(colNum).getText().trim().replace("\"", "");

                if (presentField.equals(fieldName)) {
                    columnId = colNum;
                    break;
                }
            }
        }

        return columnId;
    }

    //Function Name  : getRowIdByColumnId()
    public int getRowIdByColumnId(String tableBodyLocator, int columnId, String dataToFind) {
        int rowId = -1, rowCount = 0;

        WebElement webEleTableBody = driver.findElement(By.xpath(tableBodyLocator));
        List<WebElement> webEleRows = webEleTableBody.findElements(By.tagName("tr"));
        rowCount = webEleRows.size();

        if (rowCount > 0) {
            for (int rowNum = 0; rowNum < rowCount; rowNum++) {
                List<WebElement> webEleColumns = webEleRows.get(rowNum).findElements(By.tagName("td"));
                if (webEleColumns.get(columnId).getText().equals(dataToFind)) {
                    rowId = rowNum;
                    break;
                }
            }
        }

        return rowId;
    }

    public void navaigateToAccountSettings(String accountSettings) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropdown().click();
        Assert.assertTrue("AccountSettings is not displayed", accountSettings(accountSettings).isDisplayed());
        accountSettings(accountSettings).click();
        waitUntilPageFinishLoading();
    }

    public void verifyAccountsettings(String leftSubMenuInaccountSettings) {
        Assert.assertTrue("Tab " + leftSubMenuInaccountSettings + " is not displaying as expected!", driver.findElement(By.xpath("//a/span[text()='" + leftSubMenuInaccountSettings + "']")).isDisplayed());
    }

    public void verifyPasswordFields(String AccountInformationPage, String firstName, String lastName, String eMail, DataTable dataTable) {
        String details[] = AccountInformationPage.split(",");
        for (int i = 0; i < details.length - 1; i++) {
            Assert.assertTrue(details[i] + " is not showing.", text(details[i]).isDisplayed());
        }
        currentPasswordInput().sendKeys(Keys.PAGE_DOWN);
        List<String> list = dataTable.asList(String.class);
        for (String passwordCriteria : list) {
            Assert.assertTrue(passwordCriteria + " is not showing.", text(passwordCriteria).isDisplayed());
        }
        String firstname = firstNameTextbox().getAttribute("value");
        Assert.assertTrue("FirstName is not displayed", firstname.equals(firstName));
        String lastname = lastNameTextbox().getAttribute("value");
        Assert.assertTrue("LastName is not displayed", lastname.equals(lastName));
        String email = eMailTextbox().getAttribute("value");
        Assert.assertTrue("Email is not displayed", email.equals(eMail));
    }

    public void validatePassword( String oldPassword,String newPassword, String minimum8character, String lowercaseletter, String uppercaseletter, String withoutNumber, String withoutspecialcharacter) {
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(newPassword);
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
        newPasswordInput().sendKeys(oldPassword);
        confirmPasswordInput().sendKeys(oldPassword);
        saveButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='ui small icon success message toast']")));
        List<WebElement> list=driver.findElements(By.xpath("//div[@class='ui negative message']/div/span"));
        if(list.size()==1) {
            Assert.fail("Error Message is displayed");
        }
        waitUntilPageFinishLoading();
    }

    public void resetPassword(String oldPassword,String newPassword) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("current-password-input")));
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(oldPassword);
        newPasswordInput().sendKeys(newPassword);
        confirmPasswordInput().sendKeys(newPassword);
        saveButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='ui small icon success message toast']")));
        List<WebElement> list=driver.findElements(By.xpath("//div[@class='ui negative message']/div/span"));
        if(list.size()==1) {
            logger.info("Error Message is displayed");
            TestCase.fail();
        }
    }

    public void verifySuccessMessageinAccountSettingsPage(String message) {
        String SuccessMessage = driver.findElement(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/span")).getText();
        Assert.assertTrue("Success message is not displayed", message.equals(SuccessMessage));
    }


    public void naviagateToAvailbilityandSettings() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        navianceSettings().click();
    }

    public void verifyNavianceSuccessMessage() {
        saveSettings().click();
        waitUntilPageFinishLoading();
        String successMessage = "You've updated Naviance settings.";
        String actualSuccessMessage = driver.findElement(By.xpath("//div[text()='Great!']/following-sibling::p")).getText();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Success Message is not displayed", successMessage.equals(actualSuccessMessage));
    }

    public void verifyNotificationAndPrimaryContactInSetupWizard(String primaryUser, String changeNewUser) {
        String userTochange;
        while (notificationAndPrimaryContactActiveInSetupWizard().size() == 0) {
            waitUntilElementExists(nextButton());
            nextButton().click();
            waitUntilElementExists(nextButton());
            waitUntilPageFinishLoading();
        }

        waitForUITransition();
        //verify 'Back' button and 'Next' button is displayed
        Assert.assertTrue(backButton().isDisplayed());
        Assert.assertTrue(nextButton().isDisplayed());

        //verify UI text
        Assert.assertTrue("'Primary Contact for Visits' page is not displayed", primaryContactText().isDisplayed());

        Assert.assertTrue("Primary Contact Number field is not displayed", notificationPrimaryContactPhone().isDisplayed());

        String primaryContactName = primaryContactName().getText();

        if (primaryContactName.contains(primaryUser)) {
            userTochange = changeNewUser;
            logger.info("Primary user is selected in primary contact name");

        } else {
            userTochange = primaryUser;
        }

        primaryContactName().click();
        clickUsingJavaScript(selectPrimaryContact(userTochange));


        nextButton().click();
        Assert.assertTrue("Calendar Sync page is not displayed", subscriptionText().isDisplayed());

        backButton().click();
        primaryContactName = primaryContactName().getText();
        Assert.assertTrue("Primary contact name is not saved properly", primaryContactName.equalsIgnoreCase(userTochange));

        if (primaryContactName.contains(primaryUser)) {
            userTochange = changeNewUser;
            logger.info("Primary user is selected in primary contact name");

        } else {
            userTochange = primaryUser;

        }


        backButton().click();
        Assert.assertTrue("'Confirmation Message' page is not displayed", confirmationMessage().isDisplayed());
        nextButton().click();
        Assert.assertTrue("Primary contact name is not saved properly", !primaryContactName.contains(userTochange));

    }

    public void verifyCityAndStateInRequestNotificationsubTab(String cityAndState, String institution) {
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButton("Request", city, state, institution);
        Assert.assertTrue("City and state are not displayed", verifyCityAndStateInRequest(city, state, institution).isDisplayed());
    }

    public void verifyCityAndStateInActivitysubTab(String cityAndState, String institution) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(notificationAndTaskButton()));
        notificationAndTasks().click();
        activityTab().click();
        waitUntilPageFinishLoading();
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButton("Activity", city, state, institution);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cityAndStateInActivity(city, state, institution)));
        Assert.assertTrue("City and state are not displayed", verifyCityAndStateInActivity(city, state, institution).isDisplayed());
    }

    public void verifyCityAndStateInRequestNotificationsubTabforFairs(String cityAndState, String institution) {
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButtonforFairs("Request", city, state, institution);
        Assert.assertTrue("City and state are not displayed", cityAndStateInRequestTabforFairs(city, state, institution).isDisplayed());
    }

    public void verifyCityAndStateInActivitysubTabforFairs(String cityAndState, String institution) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(notificationAndTaskButton()));
        notificationAndTasks().click();
        activityTab().click();
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButtonforFairs("Activity", city, state, institution);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cityAndStateInActivityforFairs(city, state, institution)));
        Assert.assertTrue("City and state are not displayed", verifyCityAndStateInActivityforFairs(city, state, institution).isDisplayed());
    }

    private void verifyAndClickShowMoreButton(String option, String city, String state, String institution) {
        try {
            if (option.equals("Activity")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='row _2uF8wg87pViIg2XTJogfSA _1NZKggbuSDhvogNBhmwC2t']")));
                if (verifyCityAndStateInActivityTab(city, state, institution).size() == 0) {
                    if (showmore().size() == 1) {
                        while (verifyCityAndStateInActivityTab(city, state, institution).size() == 1) {
                            if (showmore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            } else if (option.equals("Request")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Confirm']")));
                if (cityAndStateInRequestTab(city, state, institution).size() == 0) {
                    if (showmore().size() == 1) {
                        while (cityAndStateInRequestTab(city, state, institution).size() == 0) {
                            if (showmore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Assert.fail("Notification is not displayed");
        }
    }

    private void verifyAndClickShowMoreButtonforFairs(String option, String city, String state, String institution) {
        try {
            if (option.equals("Request")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Confirm']")));
                if (cityAndStateInRequestforFairs(city, state, institution).size() == 0) {
                    if (showmore().size() == 1) {
                        while (cityAndStateInRequestforFairs(city, state, institution).size() == 0) {
                            if (showmore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            } else if (option.equals("Activity")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='row _2uF8wg87pViIg2XTJogfSA _1NZKggbuSDhvogNBhmwC2t']")));
                if (cityAndStateInActivityTabforFairs(city, state, institution).size() == 0) {
                    if (showmore().size() == 1) {
                        while (cityAndStateInActivityTabforFairs(city, state, institution).size() == 0) {
                            if (showmore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Assert.fail("Notification is not displayed");
        }
    }

    private WebElement updateBtn() {
        return driver.findElement(By.xpath("//button[@class='ui primary button _3M36944K6FppJHU9VD7kZy']"));
    }

    public void verifyPillsNotAvailableinNewScheduleVisitPage() {
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(calendar());
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(currentDateInCalendar());
        addVisitButton().click();
        waitForUITransition();
        waitUntilElementExists(calendarappointmentsInNewScheduleVisitPage());
        previousWeekInNewScheduleVisitPage().click();
        waitForUITransition();
        Assert.assertTrue("'No availability this week' message is not displayed", noAvailabilityInNewScheduleVisitPage().isDisplayed());
        availabilityAndSettings().click();
        calendarHS().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(currentDateInCalendar());
        driver.findElement(By.cssSelector("button[class='ui teal button _2vMIFbyypA0b_pLiQmz0hV']")).click();
        waitForUITransition();
    }

    public void verifyPastDatesDisabledInNewScheduleVisitPage() {
        addvVisitManuallyInNewScheduleVisitPage().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='ui small fluid button _3VnqII6ynYglzDU1flY9rw']")));
        getDriver().findElement(By.cssSelector("button[class='ui small fluid button _3VnqII6ynYglzDU1flY9rw']")).click();
        String date = getSpecificDate(-1, null);
        goToDisabledDate(getCurrentDate());
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--disabled' and @aria-label='" + date + "']")));
        String disabled = getDriver().findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--disabled' and @aria-label='" + date + "']")).getAttribute("aria-disabled");
        Assert.assertTrue("Past dates are not disabled", disabled.equalsIgnoreCase("true"));
        getDriver().navigate().refresh();
        waitUntilElementExists(forwardDayButton());
    }

    public void verifyPillsNotAvailableinReScheduleVisitPage() {
        getDriver().navigate().refresh();
        getNavigationBar().goToRepVisits();
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(currentDateInCalendar());
        if (driver.findElements(By.cssSelector(pillInCalendarLocator)).size() == 0) {
            forwardDayButton().click();
        }
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pillInCalendarLocator)));
        jsClick(selectVisitInTheCalendar());
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(notesFieldLocator)));
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        hsNotesInReScheduleVisitPage().sendKeys(Keys.PAGE_DOWN);
        rescheduleButtonInReScheduleVisitPage().click();
        waitForUITransition();
        reScheduleTextboxInReScheduleVisitPage().sendKeys(Keys.PAGE_UP);
        waitForUITransition();
        goToDateButton().click();
        waitForUITransition();
        setDateFixed("October 02 2018", getCurrentDate());
        Assert.assertTrue("verify the Message 'No availability this week'", noAvailabilityInNewScheduleVisitPage().isDisplayed());
    }

    public void selectGeneratedDateInExceptions(String daysFromNow) {
        Calendar calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow));
        if (getDayOfWeek(calendarStartDate).equals("Saturday")) {
            calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow) - 2);
        } else if (getDayOfWeek(calendarStartDate).equals("Sunday")) {
            calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow) + 2);
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
        waitForUITransition();
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilPageFinishLoading();
        if (getStartedBtn().isDisplayed()) {
            getStartedBtn().click();
            waitUntilPageFinishLoading();
        }
        while (messageOptionsActiveStep().size() == 0) {
            if (setupWizardAvailabilitySettings().size() == 1) {
                if (getParent(availabilitysettings()).getAttribute("class").equalsIgnoreCase("_1gXbsnbxcvr12eMqyC1xjb")) {
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
                    nextButton().click();
                    // The Messaging Options page takes a really long time to load, for some reason, so we need to wait.
                    waitForUITransition();
                    waitForUITransition();
                } else {
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
                    nextButton().click();
                    waitUntilPageFinishLoading();
                }
            } else {
                nextButton().click();
                waitUntilPageFinishLoading();
            }
        }
        //verify UI
        Assert.assertTrue("Confirmation Message header is not showing", confirmationMessageText().isDisplayed());
        Assert.assertTrue("Special Instruction for RepVisits header is not showing", specialInstructionMessageText().isDisplayed());
        Assert.assertTrue("Confirmation message Information Label text is not showing", emailInstructionsText().getText().contains("When appointments are confirmed, we will automatically email the higher education institution and include appointment details as well as your contact information. If you would like to add additional information, you can do so here."));
        Assert.assertTrue("webInstructions Message text is not showing", webInstructionsText().getText().contains("Is there anything you would like the representative to know before scheduling a visit? If so, include that information here. Please note we will display your contact information and the school's address."));
        Assert.assertTrue("Email Instructions textbox is not showing", emailInstructionTextbox().isDisplayed());
        Assert.assertTrue("Web Instructions textbox is not showing", webInstructionsTextbox().isDisplayed());
        Assert.assertTrue(backButton().isDisplayed());
        Assert.assertTrue(nextButton().isDisplayed());

        if (!confirmationMessage.equals("")) {
            String actualConfirmationMessage = emailInstructionTextbox().getText();
            Assert.assertTrue("'Confirmation Message' value was not as expected.", actualConfirmationMessage.contains(confirmationMessage));
        }
        if (!specialInstructionforRepVisits.equals("")) {
            String actualSpecialInstructionforRepVisits = webInstructionsTextbox().getText();
            Assert.assertTrue("'Special Instruction for RepVisits' were not set as expected.", actualSpecialInstructionforRepVisits.equals(specialInstructionforRepVisits));
        }
    }


    public void accessRepvisitsSetupWizardMessagingOptions(String confirmationMessage, String specialInstructionforRepVisits, String buttonToClick) {

        if (!confirmationMessage.equals("")) {
            WebElement actualConfirmationMessage = emailInstructionTextbox();
            actualConfirmationMessage.clear();
            actualConfirmationMessage.sendKeys(confirmationMessage);
        }
        if (!specialInstructionforRepVisits.equals("")) {
            WebElement actualSpecialInstructionforRepVisits = webInstructionsTextbox();
            actualSpecialInstructionforRepVisits.clear();
            actualSpecialInstructionforRepVisits.sendKeys(specialInstructionforRepVisits);
        }
        if (!buttonToClick.equals("")) {
            if (buttonToClick.equals("Back")) {
                backButton().click();
            } else if (buttonToClick.equals("Next")) {
                nextButton().click();
            } else {
                Assert.fail("The given option is not a valid one");
            }
        }
    }

    public void verifyPrimaryContactVisitsPage(String buttonToClick) {
        Assert.assertTrue("'Primary Contact for Visits' page is not displayed", primaryContactText().isDisplayed());
        if (buttonToClick.equals("Back")) {
            backButton().click();
        } else if (buttonToClick.equals("Next")) {
            nextButton().click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }

    public void verifyAvailabilitySettingsPage(String buttonToClick) {
        Assert.assertTrue("'Availability Settings' page is not displayed", availabilitySettingsText().isDisplayed());
        if (buttonToClick.equals("Back")) {
            backButton().click();
        } else if (buttonToClick.equals("Next")) {
            nextButton().click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }

    public void verifyUserDropdownforNonNaviance() {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyUserDropdownforHE();
    }

    public void verifyNavigationUserDropdownforNonNaviance() {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyNavigationUserDropdownforHS();
    }

    public void verifyUserAdminorNot(String option) {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyUserAdminorNot(option);
    }

    public void verifyHelpCentreforNonNaviance() {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyHelpCentreforHE();
    }


    public void verifyUserDropdownforNaviance() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropdown().click();
        Assert.assertTrue("'Your Profile' option is not displayed", getYourProfileBtn().isDisplayed());
        Assert.assertTrue("'Institution Profile' option is not displayed", getInstitutionProfileBtn().isDisplayed());
        Assert.assertTrue("'Logged In As' Text is not displayed", loggedInText().isDisplayed());
        Assert.assertTrue("'Sign Out' option is not displayed", signOut().isDisplayed());
    }

    public void verifyNavigationinUserDropdownforNaviance() {
        getYourProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.switchTo().frame(frameInCommunity());
        Assert.assertTrue("'User Profile' is not displayed", userProfilePage().isDisplayed());
        driver.switchTo().defaultContent();
        waitUntilPageFinishLoading();
        userDropdown().click();
        getInstitutionProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.switchTo().frame(frameInCommunity());
        Assert.assertTrue("'Institution Profile' is not displayed", institutionProfilePage().isDisplayed());
        driver.switchTo().defaultContent();
        waitUntilPageFinishLoading();
    }

    public void verifyNavianceSettingsPage(DataTable dataTable) {
        List<String> list = dataTable.asList(String.class);
        for (String text : list) {
            waitUntilElementExists(driver.findElement(By.xpath("//span[text()='" + text + "']")));
            Assert.assertTrue("Given data is not displayed", driver.findElement(By.xpath("//span[text()='" + text + "']")).isDisplayed());
        }
    }

    public void navigateToNavianceSettingsInAvailabilitySettingsPage() {
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(driver, 5);
        wait.until(presenceOfElementLocated(By.className("_3ygB2WO7tlKf42qb0NrjA3")));
        getNavigationBar().goToCommunityInHS();
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(availabilityAndSettingsButton());
        availabilityAndSettingsButton().click();
        waitUntilPageFinishLoading();
        navianceSettings().click();
    }

    public void navigateToNavianceSync(String wizardName) {
        waitUntilElementExists(driver.findElement(By.xpath("//span[text()='Next']")));
        while (driver.findElements(By.xpath("//div[@class='active step' and @name ='" + wizardName + "']")).size() == 0) {
            waitUntilElementExists(nextButton());
            nextButton().click();
            waitUntilElementExists(nextButton());
        }

        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilPageFinishLoading();
    }

//    public void navigateToRepvisitWizardPage(String wizardName){
//        waitUntilElementExists(driver.findElement(By.xpath("//span[text()='Next']")));
//        while(driver.findElements(By.xpath("//div[@class='active step' and @name ='"+wizardName+"']")).size()==0){
//            nextButton().click();
//            waitUntilElementExists(nextButton());
//            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Next']"),1));
//            }
//        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Next']"),1));
//        nextButton().click();
//        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Next']"),1));
//        nextButton().click();
//        waitUntilPageFinishLoading();
//    }

    public void goToCalendarInWizardLastStepPage(String visitAvailability) {
        while (completeWizardActiveStep().size() == 0) {
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Next']"), 1));
            nextButton().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Next']"), 1));
        }
        Assert.assertTrue("Complete page is not displayed", visitAvailabilityText().isDisplayed());
        if (!visitAvailability.equals("")) {
            if (visitAvailability.equalsIgnoreCase("All RepVisits Users")) {
                jsClick(allRepVisitsUsersRadioButton());
            } else if (visitAvailability.equalsIgnoreCase("Only Me")) {
                jsClick(onlyMeRadioButton());
            } else {
                Assert.fail("The given option for the visitAvailability is not a valid one");
            }
        }
        nextButton().click();
        waitUntilElementExists(takeMeToMyVisitsButton());
        takeMeButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='Today']"), 1));
    }

    public void completeSetupWizard(String visitAvailability) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(availabilityAndSettings());
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        if (regularweeklyHours().size() == 0) {
            while (completeWizardActiveStep().size() == 0) {
                waitUntilElementExists(nextButton());
                nextButton().click();
                waitUntilElementExists(nextButton());
            }
            Assert.assertTrue("Complete page is not displayed", visitAvailabilityText().isDisplayed());
            if (!visitAvailability.equals("")) {
                if (visitAvailability.equalsIgnoreCase("All RepVisits Users")) {
                    allRepVisitsUsersRadioButton().click();
                } else if (visitAvailability.equalsIgnoreCase("Only Me")) {
                    jsClick(onlyMeRadioButton());
                } else {
                    Assert.fail("The given option for the visitAvailability is not a valid one");
                }
            }
            nextButton().click();
            waitUntilElementExists(takeMeToMyVisitsButton());
            takeMeButton().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='Today']"), 1));
        } else {
            logger.info("Calendar page is displayed");
        }
    }

    public void completeSetupWizardBroken() {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntilElementExists(nextButton());
        while (nextButtons().size()== 1 && takeMeToMyVisitsButtons().size()!= 1) {
            waitUntilElementExists(nextButton());
            nextButton().click();
            if(navianceImport().size() == 1) {
                waitUntilElementExists(nextButton());
            }
        }
        takeMeToMyVisitsButton().click();
    }

    public void verifyAttendeeDetailsInEditFairs(String attendee) {
        Assert.assertTrue("Attendee details is not displayed", driver.findElement(By.xpath("//div[text()='" + attendee + "']/parent::td/following-sibling::td[text()='Pending']")).isDisplayed());
    }

    public void cancelRegisteredCollegeFair(String fair) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
            waitUntilPageFinishLoading();
        }
        List<WebElement> fairName = driver.findElements(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[contains(text(),'" + fair + "')]/parent::tr/td/a[span='View Details']"));
        if (fairName.size() > 0) {
            driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[contains(text(),'" + fair + "')]/parent::tr/td/a[span='View Details']")).click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-college-fair")));
            Assert.assertTrue("Edit button is not displayed", editButtonInCollegeFair().isDisplayed());
            editButtonInCollegeFair().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("college-fair-start-time")));
            driver.findElement(By.id("college-fair-start-time")).sendKeys(Keys.PAGE_DOWN);
            driver.findElement(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_DOWN);
            driver.findElement(By.id("college-fair-email-message-to-colleges")).sendKeys(Keys.PAGE_DOWN);
            List<WebElement> button = driver.findElements(By.xpath("//button/span[text()='Cancel This College Fair']"));
            if (button.size() > 0) {
                Assert.assertTrue("Cancel This College Fair button is not displayed", button("Cancel This College Fair").isDisplayed());
                button("Cancel This College Fair").click();
                waitUntilPageFinishLoading();
                List<WebElement> textbox = driver.findElements(By.id("college-fair-cancellation-message"));
                if (textbox.size() > 0) {
                    driver.findElement(By.id("college-fair-cancellation-message")).sendKeys("by QA");
                    driver.findElement(By.id("college-fair-cancellation-message")).sendKeys(Keys.PAGE_DOWN);
                    button("Cancel fair and notify colleges").click();
                } else {
                    button("Yes, Cancel this fair").click();
                }
                waitUntilPageFinishLoading();
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Close']")));
                driver.findElement(By.xpath("//button[text()='Close']")).click();
                waitUntilPageFinishLoading();
            }
        }
        if(fairsSubmitButton().size()==1) {
            editFairsSubmitButton().click();
            waitUntilElementExists(closeButton());
            closeButton().click();
        }
    }

    private WebElement getCollegeFairsPrimaryContactPhoneNumberField() {
        return getDriver().findElement(By.cssSelector("input[id='notification_fairs_phone_number']"));
    }

    private WebElement getRepVisitsPrimaryContactPhoneNumerField() {
        return driver.findElement(By.cssSelector("input[id='notification_contacts_primary_contact_phone']"));
    }

    public void cancelCollegeFair(String fairName) {
        logger.info("Cancelling Job Fair " + FairName + " under RepVisits.");
        getDriver().navigate().refresh();
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitForUITransition();
        try {
            while (viewMoreUpcomingEventsLink().isDisplayed()) {
                viewMoreUpcomingEventsLink().click();
                waitUntilElementExists(viewMoreUpcomingEventsLink());
            }
        } catch (WebDriverException e) {
            button(By.xpath("//a[@aria-label='" + FairName + "']")).click();
            button(By.xpath("//button[@id='edit-college-fair']")).click();
            cancelFairsButton().click();
            if (getDriver().findElements(By.xpath("//span[contains(text(), 'Yes, Cancel this fair')]")).size() >= 1) {
                button("yes, cancel this fair").click();
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='Close']"), 1));
            } else if (getDriver().findElements(By.xpath("//span[contains(text(), 'Cancel fair and notify colleges')]")).size() >= 1) {
                textbox(By.name("cancellationMessage")).sendKeys("College fair has been cancelled.");
                button("cancel fair and notify colleges").click();
                waitForUITransition();
                closeFairCancelConfiration().click();
            } else {
                Assert.assertTrue("There were no job fairs registered for this high school.", false);
            }
            Assert.assertFalse("College fair was not canceled.", getDriver().findElements(By.xpath("//span[contains(text(), 'Upcoming Events')]/../../following-sibling::table/tbody/tr/td[contains(text(), '" + fairName + "')]")).size() >= 1);

        }

    }

    public void cleanCollegeFair() {
        logger.info("Cancelling Job Fair " + FairName + " under RepVisits.");
        getDriver().navigate().refresh();
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitForUITransition();
        try {
            while (viewMoreUpcomingEventsLink().isDisplayed()) {
                viewMoreUpcomingEventsLink().click();
                waitUntilElementExists(viewMoreUpcomingEventsLink());
            }
        } catch (WebDriverException e) {
            try {
                while (driver.findElements(By.xpath(viewDetailsLocator)).size() > 0) {
                    getNavigationBar().goToRepVisits();
                    waitUntilElementExists(collegeFairs());
                    collegeFairs().click();
                    waitUntilElementExists(viewDetailsLink());
                    viewDetailsLink().click();
                    waitUntilElementExists(editCollegFair());
                    editCollegFair().click();
                    cancelFairsButton().click();
                    waitForUITransition();
                    if (getDriver().findElements(By.xpath(yesCancelThisFairButtonLocator)).size() >= 1) {
                        button("yes, cancel this fair").click();
                        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(closeButtonLocator), 1));
                    } else if (getDriver().findElements(By.xpath(cancelFairAndNotifyCollegesButtonLocator)).size() >= 1) {
                        textbox(By.name("cancellationMessage")).sendKeys("College fair has been cancelled.");
                        button("cancel fair and notify colleges").click();
                        waitForUITransition();
                        closeFairCancelConfiration().click();
                    } else if (driver.findElements(By.xpath(cancelThisCollegeFairButtonLocator)).size() >= 1) {
                        driver.findElement(By.xpath(cancelThisCollegeFairButtonLocator)).click();
                        button("yes, cancel this fair").click();
                    } else {
                        Assert.assertTrue("There were no job fairs registered for this high school.", false);
                    }
                    collegeFairs().click();
                    waitForUITransition();
                }

            } catch (WebDriverException f) {
                getDriver().navigate().refresh();

            }
        }

    }

    public void cleanVisits() {
        getDriver().navigate().refresh();
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(calendarsTitle());
        collegeFairConfirmedCheckbox().click();
        cleanGroupedVisitsInCurrentMonth();
        cleanNonGroupedVisitsInCalendar();
        driver.findElement(By.xpath(forwardDayButtonLocator)).click();
        driver.get(driver.getCurrentUrl());
        collegeFairConfirmedCheckbox().click();
        cleanGroupedVisitsInCurrentMonth();
        cleanNonGroupedVisitsInCalendar();
    }

    public void cleanNonGroupedVisitsInCalendar() {
        while (driver.findElements(By.cssSelector(pillInCalendarLocator)).size() > 0) {
            driver.findElements(By.cssSelector(pillInCalendarLocator)).get(0).click();
            if (driver.findElements(By.xpath(noGoBackButtonLocator)).size() > 0) {
                driver.findElements(By.xpath(noGoBackButtonLocator)).get(0).click();
            }
            for (int k = 0; k < 5; k++) {
                button("Cancel This Visit").click();
                if (driver.findElements(By.cssSelector(cancelMessageFieldLocator)).size() == 0) {
                    button("Cancel This Visit").click();
                } else {
                    break;
                }
            }
            getMessageRegardingCancellationTextBox().sendKeys("Cancel");
            cancelVisitButton().click();
            if (driver.findElements(By.xpath(failedToCancelMessageLocator)).size() > 0) {
                driver.get(driver.getCurrentUrl());
                waitUntilPageFinishLoading();
                collegeFairConfirmedCheckbox().click();
            } else {
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(visitFormLocator), 0));
            }
        }
    }

    public void cleanGroupedVisitsInCurrentMonth() {
        List<WebElement> moreLinks = driver.findElements(calendarMoreLinks());
        int numberOfMoreLinks = moreLinks.size();
        WebElement moreLink;
        for (int i = 0; i < numberOfMoreLinks; i++) {
            moreLink = driver.findElements(calendarMoreLinks()).get(i);
            waitUntilElementExists(moreLink);
            moreLink.click();
            WebElement visitInOverlay;
            while (driver.findElements(By.cssSelector(pillInOverlayLocator)).size() > 1) {
                visitInOverlay = driver.findElements(By.cssSelector(pillInOverlayLocator)).get(0);
                visitInOverlay.click();
                if (driver.findElements(By.xpath(noGoBackButtonLocator)).size() > 0) {
                    driver.findElements(By.xpath(noGoBackButtonLocator)).get(0).click();
                }
                waitUntil(ExpectedConditions.elementToBeClickable(button("Cancel This Visit")));
                for (int k = 0; k < 5; k++) {
                    button("Cancel This Visit").click();
                    if (driver.findElements(By.cssSelector(cancelMessageFieldLocator)).size() == 0) {
                        button("Cancel This Visit").click();
                    } else {
                        break;
                    }
                }
                getMessageRegardingCancellationTextBox().sendKeys("Cancel");
                cancelVisitButton().click();
                if (driver.findElements(By.xpath(failedToCancelMessageLocator)).size() > 0) {
                    driver.get(driver.getCurrentUrl());
                    waitUntilPageFinishLoading();
                    collegeFairConfirmedCheckbox().click();
                    if (driver.findElements(calendarMoreLinks()).size() > 0) {
                        driver.findElements(calendarMoreLinks()).get(i).click();
                    }
                } else {
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(visitFormLocator), 0));
                    if (driver.findElements(calendarMoreLinks()).size() > 0) {
                        driver.findElements(calendarMoreLinks()).get(i).click();
                    }
                }
            }
        }
    }

    public void setDateInCalendarAgenda(String startDate, String endDate, String agenda) {
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.partialLinkText("Calendar"), 1));
        calendar().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='" + agenda + "']"), 1));
        jsClick(agendaButton());
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button"), 1));
        String EndDate = getSpecificDateForCalendar(endDate);
        setDate(EndDate, "LastDate");
        String StartDate = getSpecificDateForCalendar(startDate);
        setDate(StartDate, "FirstDate");
    }

    public void verifyDisabledDateIsNotClickableInEndDate(String disabledDate) {
        String DisabledDate = getSpecificDateForCalendar(disabledDate);
        setDate(DisabledDate, "DisabledDate");
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button"), 1));
        //verify the dates are not equal
        String date = getStartDateInAgenda().getText();
        String displayingDate = getDisplayingDateInCalendarAgenda(disabledDate);
        Assert.assertTrue("Selected date is not displayed", !date.equals(displayingDate));
        getNavigationBar().goToCommunityInHS();
        waitUntilPageFinishLoading();
    }

    public void createCollegeFair(DataTable dataTable) {
        logger.info("Creating a Job Fair under RepVisits.");
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(collegeFairs());
        collegeFairs().click();
        waitUntilPageFinishLoading();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(text(By.id("add-college"))));
        button("ADD A COLLEGE FAIR").click();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if (key.equals("Date")) {
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairDateToFill);
            } else if (key.equals("RSVP Deadline")) {
                String fairRSVPDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairRSVPDateToFill);
            } else
                enterCollegeFairData(key, data.get(key));

        }
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));

        fairSave().click();
    }

    /**
     * Creates a college fair with a random number appended to the end of the supplied name.
     * This reduces name collisions with repeated tests.
     *
     * @param dataTable - Data Table containing all the fields for the College Fair
     */
    public void createDynamicCollegeFair(DataTable dataTable) {
        logger.info("Creating a Job Fair under RepVisits.");
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        collegeFairs().click();
        waitUntilPageFinishLoading();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(text(By.id("add-college"))));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(text(By.id("add-college"))));
        button("ADD A COLLEGE FAIR").click();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if (key.equals("Date")) {
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairDateToFill);
            } else if (key.equals("RSVP Deadline")) {
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
        driver.findElement(By.xpath("//button[@class='ui primary right floated button']")).click();
    }

    /**
     * Edit a college fair with a random number appended to the end of the supplied name.
     * This reduces name collisions with repeated tests.
     *
     * @param dataTable - Data Table containing all the fields for the College Fair
     */
    public void editCollegeFair(DataTable dataTable) {
        editCFFair();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if (key.equals("Date")) {
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairDateToFill);
            } else if (key.equals("RSVP Deadline")) {
                String fairRSVPDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                enterCollegeFairData(key, fairRSVPDateToFill);
            } else if (key.equals("College Fair Name")) {
                String fairName = data.get(key);
                FairName = fairName;
                enterCollegeFairData(key, fairName);
            } else
                enterCollegeFairData(key, data.get(key));

        }
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));
        driver.findElement(By.xpath("//button[@class='ui primary right floated button']")).click();
        button("Close").click();
    }

    /**
     * Verify Edit a college fair with a random number appended to the end of the supplied name.
     * This reduces name collisions with repeated tests.
     *
     * @param dataTable - Data Table containing all the fields for the College Fair
     */
    public void verifyDataCollegeFair(DataTable dataTable) {
        editCFFair();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if (key.equals("Date")) {
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                String currentFairDate = textbox(By.id("college-fair-date")).getAttribute("value");
                fairDateToFill = formatterDate(fairDateToFill);
                Assert.assertTrue(currentFairDate.contains(fairDateToFill));
            } else if (key.equals("RSVP Deadline")) {
                String fairRSVPDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                String currentRSVP = driver.findElement(By.cssSelector("input[id='college-fair-rsvp-deadline']")).getAttribute("value");
                fairRSVPDateToFill = formatterDate(fairRSVPDateToFill);
                Assert.assertTrue(currentRSVP.contains(fairRSVPDateToFill));
            } else if (key.equals("College Fair Name")) {
                String fairName = data.get(key);
                String currentFairName = textbox(By.id("college-fair-name")).getAttribute("value");
                Assert.assertTrue(currentFairName.contains(fairName));
            } else
                enterCollegeFairData(key, data.get(key));

        }
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));
        driver.findElement(By.xpath("//button[@class='ui primary right floated button']")).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Close']")));
        fairCloseButton().click();
        waitUntilPageFinishLoading();
    }

    public void unpublishCollegeFair() {
        waitUntilElementExists(getUnpublishButton());
        getUnpublishButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(closeButtonLocator()));
        jsClick(close());
    }

    public void addEmailInNotificationandPrimaryContactPage(String Email) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        notificationAndPrimaryContact().click();
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

    /**
     * Format the date from "Fri, June 7 2013" to "Fri, Jun 07 2013"
     *
     * @param dateToFormat, date to formmatter
     */
    public String formatterDate(String dateToFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return dateToFormat = formatter.format(date);
    }

    public void scrollDown(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)", "");
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void enterCollegeFairData(String field, String data) {
        switch (field) {
            case "College Fair Name":
                FairName = randomizeFairName(data);
                textbox(By.id("college-fair-name")).sendKeys(FairName);
                break;
            case "Date":
                getDriver().findElement(By.xpath("//div[@class='ui fluid input']/input[@id = 'college-fair-date']/../i")).click();
                // Only goes out 12 months
                for (int i = 0; i <= 12; i++) {
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
                    if (i < 12) {
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    } else if (i == 12) {
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
                for (int i = 0; i <= 12; i++) {
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
                    if (i < 12) {
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    } else if (i == 12) {
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
                if (data.equalsIgnoreCase("yes")) {
                    jsClick(driver.findElement(By.id("college-fair-automatic-request-confirmation-yes")));
                } else {
                    jsClick(driver.findElement(By.id("college-fair-automatic-request-confirmation-no")));
                    ;
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

    public void navigateToVisitPage() {
        jsClick(visitsAndFairsRadioButton());
        while (completeWizardActiveStep().size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Next']")));
            nextButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
        }
        jsClick(allRepVisitsUsersRadioButton());
        nextButton().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Take me to my visits button is not displayed", takeMeToMyVisitsButton().isDisplayed());
        takeMeToMyVisitsButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifydefaultRepVisitPage() {
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(calendar());
        Assert.assertTrue("Calendar page is not active", calendar().isDisplayed());
        waitUntil(ExpectedConditions.visibilityOfElementLocated(addvisitButton()));
        Assert.assertTrue("Add visit button is not displayed", getDriver().findElement(addvisitButton()).isDisplayed());
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarMonth()));
        Assert.assertTrue("Calendar is not displayed in Month view", getDriver().findElement(calendarMonth()).isDisplayed());
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


    public void navigateToNavianceSettingsPage() {
        load(GetProperties.get("hs.WizardAppSelect.url"));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardCheckmarkButton()));
        while (activeStepNavianceSettings().size() == 0) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            nextButton().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
        }
    }

    public void verifyToastInSetupWizardIncomplete(String option) {
        if (option.contains("is")) {
            load(GetProperties.get("hs.WizardAppNaviance.url"));
            waitUntilPageFinishLoading();
            Assert.assertTrue("The Naviance Settings Not Yet Complete  was not displayed", text("Naviance Settings Not Yet Complete")
                    .isDisplayed());
        }
    }


    public void connectRVWithNaviance(String option) {
        if (option.contains("No")) {
            optInNoRadioButton().click();
            nextButton().click();
        } else {
            optInYesRadioButton().click();
            nextButton().click();
        }
    }

    public void verifyInputValidationsForStuRegDeadline(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        studentRegistrationDeadlineField().sendKeys(data.get("Registration will close"));
        hoursDaysDropdown().click();
        hoursDaysOption("days").click();
        hoursDaysDropdown().click();
        hoursDaysOption(data.get("Hours or Days option")).click();
        Assert.assertTrue("The value greater than 255 was not turned into 255", studentRegistrationDeadlineField().getAttribute("value").contains("255"));
    }

    //locators
    public void accessAddAttendeePopUp(String attendeeName) {
        if (!attendeeName.equals("")) {
            //Fixed Attendee selection
            //            driver.findElement(By.xpath("//input[@placeholder='Start Typing ...']")).sendKeys(attendeeName);
            //            WebElement element = driver.findElement(By.xpath("//div[contains(text(),'" + attendeeName + "')]"));
            //            doubleClick(element);
            link("Can't find someone? Add them manually").click();
            driver.findElement(By.xpath("//input[@id='add-rep-first-name']")).sendKeys(attendeeName);
            driver.findElement(By.xpath("//input[@id='add-rep-institution']")).sendKeys(attendeeName);
        }
        driver.findElement(By.cssSelector("button[class='ui primary right floated button']")).click();
        //   button("Add Attendee").click();
        waitUntilPageFinishLoading();
    }

    public void rescheduleVisitStartTime() {
        RescheduleStartTimeforNewVisit = StartTime;
    }

    public void verifyRequestNotificationTab(String user, String university, String time, String date) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(notificationAndTaskButton()));
        notificationAndTasks().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(requestTabUserName(user)));
        Assert.assertTrue("user name is not displayed", userNameInRequestTab(user).isDisplayed());
        Assert.assertTrue("University name is not displayed", universityNameInRequestTab(university).isDisplayed());
        Assert.assertTrue("Confirm Button is not displayed", verifyConfirmButtonInRequestNotification(user, university).isDisplayed());
        Assert.assertTrue("Decline Button is not displayed", verifyDeclineButtonInRequestNotification(user, university).isDisplayed());
    }

    public void selectoption(String option, String user, String time, String university) {
        waitUntilPageFinishLoading();
        WebElement Confirmbutton = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Confirm']"));
        WebElement Declinebutton = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Decline']"));
        if (option.equals("Confirm")) {
            jsClick(Confirmbutton);
            waitUntil(ExpectedConditions.visibilityOfElementLocated(requestTabCloseButton()));
            jsClick(closeButtonInRequestTab());
            waitForUITransition();
        } else if (option.equals("Decline")) {
            jsClick(Declinebutton);
            while (!driver.findElement(By.xpath("//span[text()='Are you sure you want to decline?']")).isDisplayed()) {
                driver.findElement(By.id("global-search-box-input")).sendKeys(Keys.PAGE_UP);
            }
        } else {
            Assert.fail("The given option for the notification is not a valid one");
        }
    }

    public void verifyDeclinePopup(String user, String institution, String time, String date) {
        waitUntilPageFinishLoading();
        moveToElement(declinePopupMessage());
        waitUntilElementExists(goBack());
        Assert.assertTrue("Close button is not displayed", circularButton().isDisplayed());
        Assert.assertTrue("Decline message is not displayed", driver.findElement(By.xpath("//div/span[text()='Are you sure you want to decline?']")).isDisplayed());
        Assert.assertTrue("Attendee Details text is not displayed", driver.findElement(By.xpath("//span[text()='Attendee Details']")).isDisplayed());
        Assert.assertTrue("Text box message is not displayed", driver.findElement(By.xpath("//div/p/span[text()='Please send the attendee a message to explain why you are declining.']")).isDisplayed());
        WebElement UserName = driver.findElement(By.xpath("//strong[contains(text(),'" + user + "')]"));
        WebElement Institution = driver.findElement(By.xpath("//strong[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/strong[contains(text(),'" + institution + "')]"));
        Assert.assertTrue("user name is not displayed", UserName.isDisplayed());
        Assert.assertTrue("institution is not displayed", Institution.isDisplayed());
        Assert.assertTrue("Cancellation Message textbox is not displayed", cancellationMessage().isDisplayed());
        Assert.assertTrue("No, Go back button is not displayed", goBack().isDisplayed());
        //verify YES decline Button is disabled
        WebElement buttonDisabled = driver.findElement(By.xpath("//button[@class='ui red small disabled right floated button' and @disabled='']"));
        Assert.assertTrue("Yes, Decline button is not disabled", buttonDisabled.isDisplayed());
    }

    public void declineConfirmation(String option, String message, String user) {
        if (option.equals("Yes, Decline")) {
            jsClick(cancellationMessage());
            cancellationMessage().sendKeys(Keys.PAGE_DOWN);
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span[text()='Yes, decline']"), 1));
            cancellationMessage().sendKeys(message);
            //verify the button is enabled After entering declining message in the textbox
            Assert.assertTrue("Yes, Decline button is not enabled", button("Yes, Decline").isEnabled());
            button(option).click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span[text()='Close']"), 1));
            WebElement closebutton = button("Close");
            Assert.assertTrue("Close button is not displayed", closebutton.isDisplayed());
            Assert.assertTrue("User name with declined message are not displayed", driver.findElement(By.xpath("//span[text()='" + user + "']/parent::p[text()='has been declined.']")).isDisplayed());
            closebutton.click();
            getNavigationBar().goToRepVisits();
            waitUntilPageFinishLoading();
        } else if (option.equals("No, go back")) {
            jsClick(cancellationMessage());
            Assert.assertTrue("No, Go back button is not displayed", goBack().isDisplayed());
            goBack().click();
            waitUntilPageFinishLoading();
        }
    }

    public void accessSuccessMessageforAddAttendees(String buttonName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='ui button']")));
        if (buttonName.equals("No, I'm Done")) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='ui button']")));
            waitForUITransition();
            iamDoneButton().click();
        } else if (buttonName.equals("Yes, Add More")) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='next-action']")));
            addMoreButton().click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }

    public void clickMessageCollegesButton() {
        button("MESSAGE COLLEGES").click();
    }

    public void massEmailMessageForAttendees(String emailMessage) {
        getcollegefairattendeemsg().clear();
        getcollegefairattendeemsg().sendKeys(emailMessage);
    }

    private WebElement getcollegefairattendeemsg() {
        return getDriver().findElement(By.id("college-fair-attendee-msg"));

    }

    public void sendMessage() {
        button("SEND MESSAGE").click();
    }

    public void verifySentEmailConfirmationMessage() {
        waitForUITransition();
        Assert.assertTrue("Email Message sent Confirmation Message is Not displayed", driver.findElement(By.cssSelector("div#success-message-grid.middle.aligned.column p")).isDisplayed());
        // Assert.assertTrue("Email Message Sent Confirmation  Message displayed", driver.findElement(By.cssSelector(".QEnylOWAf2zj5xHiILoO-")).isDisplayed());
        waitForUITransition();
    }

    public void closeSendEmailMessageBox() {
        closeFairCancelConfiration().click();
    }


    public void accessCollegeFairDetailsPage(String buttonToClick) {
        if (buttonToClick.equals("Edit")) {
            editCollegFair().click();
        } else if (buttonToClick.equals("Add Attendee")) {
            collegeFairAddAttendee().click();
        } else if (buttonToClick.equals("")) {
            messageCollegesButton().click();
        } else {
            Assert.fail("The given option is not a valid one");
        }
    }

    public void verifyRequestNotificationTabforFairs(String user, String university, String time, String date) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(notificationAndTaskButton()));
        notificationAndTasks().click();
        String fairsDate = selectdateForRequestSubTab(date);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(confirmButtonInRequestTab(user, university, fairsDate, time)));
        Assert.assertTrue("Confirm Button is not displayed", verifyConfirmButtonInRequestNotificationforFairs(user, university, fairsDate, time).isDisplayed());
        Assert.assertTrue("Decline Button is not displayed", verifyDeclineButtonInRequestNotificationforFairs(user, university, fairsDate, time).isDisplayed());
    }

    public void verifyDeclinePopupforFairs(String user, String institution, String time, String date) {
        waitUntilPageFinishLoading();
        moveToElement(declinePopupMessage());
        WebElement UserName = driver.findElement(By.xpath("//strong[contains(text(),'" + user + "')]"));
        moveToElement(UserName);
        waitUntilElementExists(goBack());
        Assert.assertTrue("Close button is not displayed", circularButton().isDisplayed());
        Assert.assertTrue("Decline message is not displayed", driver.findElement(By.xpath("//div/span[text()='Are you sure you want to decline?']")).isDisplayed());
        Assert.assertTrue("Attendee Details text is not displayed", driver.findElement(By.xpath("//span[text()='Attendee Details']")).isDisplayed());
        Assert.assertTrue("Text box message is not displayed", driver.findElement(By.xpath("//div/p/span[text()='Please send the attendee a message to explain why you are declining.']")).isDisplayed());
        WebElement Institution = driver.findElement(By.xpath("//strong[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/strong[contains(text(),'" + institution + "')]"));
        Assert.assertTrue("user name is not displayed", UserName.isDisplayed());
        Assert.assertTrue("institution is not displayed", Institution.isDisplayed());
        Assert.assertTrue("Cancellation Message textbox is not displayed", cancellationMessage().isDisplayed());
        Assert.assertTrue("No, Go back button is not displayed", goBack().isDisplayed());
        //verify YES decline Button is disabled or not
        WebElement buttonDisabled = driver.findElement(By.xpath("//button[@class='ui red small disabled right floated button' and @disabled='']"));
        Assert.assertTrue("Yes, Decline button is not disabled", buttonDisabled.isDisplayed());
    }

    public void selectoptionforFairs(String option, String user, String date, String fairsStartTime, String university) {
        waitUntilPageFinishLoading();
        String fairsDate = selectdateForRequestSubTab(date);
        WebElement Confirmbutton = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]/ancestor::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span/span[contains(text(),'" + fairsDate + "')]/parent::span[contains(text(),'" + fairsStartTime + "')]/../../following-sibling::div/button/span[text()='Confirm']"));
        WebElement Declinebutton = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]/ancestor::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span/span[contains(text(),'" + fairsDate + "')]/parent::span[contains(text(),'" + fairsStartTime + "')]/../../following-sibling::div/button/span[text()='Decline']"));
        if (option.equals("Confirm")) {
            jsClick(Confirmbutton);
            waitUntil(ExpectedConditions.visibilityOfElementLocated(requestTabCloseButton()));
            jsClick(closeButtonInRequestTab());
            waitForUITransition();
        } else if (option.equals("Decline")) {
            jsClick(Declinebutton);
            waitUntilPageFinishLoading();
            waitForUITransition();
            while (!driver.findElement(By.xpath("//span[text()='Are you sure you want to decline?']")).isDisplayed()) {
                driver.findElement(By.id("global-search-box-input")).sendKeys(Keys.PAGE_UP);
            }
        } else {
            Assert.fail("The given option for the notification is not a valid one");
        }
    }

    public void verifyNotificationMessage(String message) {
        try {
            if (text(message).isDisplayed()) {
                logger.info("Notification is not displayed");
            } else {
                logger.info("Notification is displayed");
            }
        } catch (Exception e) {
        }
    }

    public void verify25Entries(String option) {
        int count = driver.findElements(By.xpath("//div[@class='_12QfCShNjFFA8a-x4K3-yn']/div/div")).size();
        try {
            if (count > 25) {
                logger.info(option + "option is displayed");
            } else {
                logger.info("Less than 25 notifictions are displayed");
            }
        } catch (Exception e) {
        }
    }

    public void reschedulevisit(String university, String time, String date) {
        String startTime = getVisitStartTimeInCalendar();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        //  pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        List<WebElement> calendarTime = driver.findElements(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']"));
        if (calendarTime.size() == 1) {
            Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']")).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
            driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).click();
            waitUntilPageFinishLoading();
        } else {
            startTime = getCalendarStartTime();
            Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']")).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
            driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).click();
            waitUntilPageFinishLoading();
        }
    }

    public void verifyReschedulepopup(String user, String university, String time, String date) {
        waitForUITransition();
        time = getVisitSchedulePopupStartTime();
        String actualDate = getDate(university).getText();
        String visitDate = selectdate(date);
        Assert.assertTrue("Date is not equal", actualDate.equals(visitDate));
        Assert.assertTrue("time is not displayed", timeInReschedulePopup(time).isDisplayed());
        eventLocationInVisitSchedule().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(internalNotesInReschedule()));
        internalNotesTextBoxInReschedulePopup().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Reschedule button is not displayed", rescheduleButtonInReScheduleVisitPage().isDisplayed());
        jsClick(rescheduleButtonInReScheduleVisitPage());
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(reschedulePopupText()));
    }

    public void selectReschedule(String time, String reason, String date) {
        dateButtonInVisitReschedule().click();
        setSpecificDate(date);
        waitForUITransition();
        rescheduleDateButton().sendKeys(Keys.PAGE_DOWN);
        waitUntilPageFinishLoading();
        int Date = Integer.parseInt(date);
        time = RescheduleStartTimeforNewVisit.toUpperCase();
        String Day = getSpecificDate(Date, "EEE").toUpperCase();
        int columnID = getColumnIdFromTableforManuallyAddVisit("//table[@class='ui unstackable basic table']/thead", Day);
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table']//tbody", columnID, time);
        if (columnID >= 0 && rowID >= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            WebElement appointmentSlot = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table']//tbody//tr[" + rowID + "]//td[" + columnID + "]//button[text()='" + RescheduleStartTimeforNewVisit + "']"));
            jsClick(appointmentSlot);
            waitUntilPageFinishLoading();
        } else {
            Assert.fail("The Time Slot " + StartTime + "is not displayed in the Manually adding appointment page ");
        }
        driver.findElement(By.xpath("//td/button[text()='" + RescheduleStartTimeforNewVisit + "']")).sendKeys(Keys.PAGE_DOWN);
        rescheduleMessageTextBox().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("rescheduleMessage textbox is not displayed", rescheduleMessageTextBox().isDisplayed());
        rescheduleMessageTextBox().sendKeys(reason);
        Assert.assertTrue("Reschedule Visit is not displayed", rescheduleVisitButton().isDisplayed());
        rescheduleVisitButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }

    public void addContactManually(String date, String Time, String FName, String LName, String Email, String PhNo, String Position, String Institution) {
        waitUntilPageFinishLoading();
        try {
            fillVisitForm(date, Time, FName, LName, Email, PhNo, Position, Institution);
        } catch (WebDriverException e) {
            while (driver.findElements(By.xpath(errorsWithASubmissionMessageLocator)).size() > 0) {
                cleanVisits();
                fillVisitForm(date, Time, FName, LName, Email, PhNo, Position, Institution);
                waitForUITransition();
            }
        }
        waitForUITransition();
    }

    public void fillVisitForm(String date, String Time, String FName, String LName, String Email, String PhNo, String Position, String Institution) {
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("button[class$='_3GJIUrSQadO6hk9FZvH28D']"), 1));
        doubleClick(DateButton());
        goToDateButton().click();
        waitForUITransition();
        setSpecificDate(date);
        waitForUITransition();
        int Date = Integer.parseInt(date);
        Time = StartTime.toUpperCase();
        String Day = getSpecificDate(Date, "EEE").toUpperCase();
        int columnID = getColumnIdFromTableforManuallyAddVisit("//table[@class='ui unstackable basic table']/thead", Day);
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table']//tbody", columnID, Time);
        if (columnID >= 0 && rowID >= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            WebElement appointmentSlot = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table']//tbody//tr[" + rowID + "]//td[" + columnID + "]//button[text()='" + StartTime + "']"));
            jsClick(appointmentSlot);
            waitUntilPageFinishLoading();
        } else {
            Assert.fail("The Time Slot " + StartTime + "is not displayed in the Manually adding appointment page ");
        }
        DateButton().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Not in the list? Add them manually']"), 1));
        addcontactManually().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("add-rep-first-name"), 1));
        firstNameTextBox().sendKeys(FName);
        lastNameTextBox().sendKeys(LName);
        lastNameTextBox().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("add-rep-email"), 1));
        emailTextBox().sendKeys(Email);
        phoneNoTextBox().sendKeys(PhNo);
        positionTextBox().sendKeys(Position);
        institutionTextBox().clear();
        institutionTextBox().sendKeys(Institution);
        waitUntilPageFinishLoading();
        institutionTextBox().sendKeys(Keys.PAGE_DOWN);
        eventLocationInAddVisitPopup().sendKeys(Keys.PAGE_DOWN);
        addVisitButtonInVisitSchedulePopup().click();
        waitForUITransition();
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

    public void verifyFairsAreClickable(String collegeFairName, String date, String startTime, String endTime, String RSVPDate, String cost, String maxNumberofColleges, String numberofStudentsExpected) {
        getNavigationBar().goToRepVisits();
        calendar().click();
        waitUntilElementExists(calendarsTitle());
        pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        List<WebElement> moreLinks = driver.findElements(calendarMoreLinks());
        if (driver.findElements(By.xpath(fairInCalendarLocator(FairName))).size() == 0) {
            if (moreLinks.size() > 0) {
                for (WebElement moreLink : moreLinks) {
                    moreLink.click();
                    if (overlayHeader().getText().contains(day(date))) {
                        if (driver.findElements(By.xpath(fairInCalendarLocator(FairName))).size() == 0) {
                            driver.findElement(By.xpath(forwardDayButtonLocator)).click();
                        } else {
                            driver.findElement(By.xpath(fairInCalendarLocator(FairName))).click();
                            break;
                        }
                    }
                    calendarsTitle().click();
                }
            }
        } else {
            driver.findElement(By.xpath(fairInCalendarLocator(FairName))).click();
        }
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(fairFormLocator), 0));
        Assert.assertTrue("Tray was not displayed!", driver.findElement(By.cssSelector(fairFormLocator)).isDisplayed());
        Actions action = new Actions(driver);
        action.sendKeys(Keys.END).build().perform();
        textbox(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Tray was not displayed!", driver.findElement(By.cssSelector("button[class='ui primary right floated button']")).isDisplayed());
        Assert.assertTrue("'College Fair Details' Title is not displayed", driver.findElement(By.xpath("//h2/span[text()='College Fair Details']")).isDisplayed());
        Assert.assertTrue("'College Fair Name' Label is not displayed", text("College Fair Name").isDisplayed());
        Assert.assertTrue("'Date' Label is not displayed", driver.findElement(By.xpath("//label[text()='Date']")).isDisplayed());
        textbox(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_UP);
        Assert.assertTrue("'Start Time' Label is not displayed", driver.findElement(By.xpath("//label[text()='Start Time']")).isDisplayed());
        Assert.assertTrue("'End Time' Label is not displayed", driver.findElement(By.xpath("//label[text()='End Time']")).isDisplayed());
        Assert.assertTrue("'RSVP Deadline' Label is not displayed", driver.findElement(By.xpath("//label[text()='RSVP Deadline']")).isDisplayed());
        Assert.assertTrue("'Cost' Label is not displayed", driver.findElement(By.xpath("//label[text()='Cost']")).isDisplayed());
        Assert.assertTrue("'Max Number of Colleges' Label is not displayed", driver.findElement(By.xpath("//label[text()='RSVP Deadline']")).isDisplayed());
        Assert.assertTrue("'Number of Students Expected' Label is not displayed", driver.findElement(By.xpath("//label[text()='Max Number of Colleges']")).isDisplayed());
        Assert.assertTrue("'Instructions for College Representatives' Label is not displayed", driver.findElement(By.xpath("//label[text()='Number of Students Expected']")).isDisplayed());
        Assert.assertTrue("'Automatically Confirm Incoming Requests From Colleges?' Label is not displayed", driver.findElement(By.xpath("//label[text()='Instructions for College Representatives']")).isDisplayed());
        Assert.assertTrue("'Settings' Label is not displayed", driver.findElement(By.xpath("//h2/span[text()='Settings']")).isDisplayed());
        Assert.assertTrue("'Email Message to Colleges After Confirmation' Label is not displayed", driver.findElement(By.xpath("//label[text()='Email Message to Colleges After Confirmation']")).isDisplayed());
        Assert.assertTrue("'Cancel This College Fair' Button is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This College Fair']")).isDisplayed());
        Assert.assertTrue("'Publish/Unpublish' Button is not displayed", unpublishButton().isDisplayed());

        if (!FairName.equals("")) {
            String currentFairName = textbox(By.id("college-fair-name")).getAttribute("value");
            Assert.assertTrue("'College Fair Name' value was not as expected.", currentFairName.contains(FairName));
            driver.findElement(By.xpath("//input[@id='college-fair-name']")).sendKeys(Keys.PAGE_DOWN);
        }
        if (!startTime.equals("")) {
            String currentStartTime = textbox(By.id("college-fair-start-time")).getAttribute("value");
            Assert.assertTrue("'Start Time' value was not as expected.", currentStartTime.equals(startTime));
        }
        if (!endTime.equals("")) {
            String currentEndTime = textbox(By.id("college-fair-end-time")).getAttribute("value");
            Assert.assertTrue("'End Time' value was not as expected.", currentEndTime.equals(endTime));
        }
        if (!RSVPDate.equals("")) {
            String actualDate = driver.findElement(By.id("college-fair-rsvp-deadline")).getAttribute("value");
            String fairDate = selectdate(Integer.parseInt(RSVPDate));
            waitUntilPageFinishLoading();
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if (!cost.equals("")) {
            String currentCost = textbox(By.id("college-fair-cost")).getAttribute("value");
            waitUntilPageFinishLoading();
            Assert.assertTrue("'Cost' value was not as expected.", currentCost.equals(cost));
        }

        if (!numberofStudentsExpected.equals("")) {
            String currentNumberofStudentsExpected = textbox(By.id("college-fair-number-expected-students")).getAttribute("value");
            waitUntilPageFinishLoading();
            Assert.assertTrue("'Max Number of Colleges' value was not as expected.", currentNumberofStudentsExpected.equals(numberofStudentsExpected));
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
        if (!collegeFairName.equals("")) {
            if (collegeFairName.equalsIgnoreCase("PreviouslySetFair"))
                collegeFairName = FairName;
            Assert.assertTrue("College Fair Name is not Displayed", driver.findElement(By.xpath("//h1[text()='" + collegeFairName + "']")).isDisplayed());
            ;
        }
        if (!date.equals("")) {
            String actualDate = driver.findElement(By.xpath("//div[@class='thirteen wide column']//b/span")).getText();
            String fairDate = verifySpecificDate(Integer.parseInt(date));
            Assert.assertTrue("Date is not Displayed.", actualDate.equals(fairDate));
        }
        if (!instructionsforCollegeRepresentatives.equals("")) {
            String actualInstructionsforCollegeRepresentatives = driver.findElement(By.xpath("//div[@class='column']//p")).getText();
            Assert.assertTrue("Date is not Displayed.", actualInstructionsforCollegeRepresentatives.equals(instructionsforCollegeRepresentatives));
        }
    }

    public void accessListoffairAttendees(String buttonToClick, String name) {
        int nameID = getColumnIdByFieldName("//table[@id='he-account-dashboard']//thead", "Name");
        int rowID = getRowIdByColumnId("//table[@id='he-account-dashboard']//tbody", nameID, name);
        if (buttonToClick.equals("Cancel")) {
            try {
                rowID = rowID + 1;
                WebElement cancelbutton = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody//tr[" + rowID + "]//td//button/span[text()='CANCEL']"));
                cancelbutton.click();
                waitUntilPageFinishLoading();
            }catch (Exception e){
                rowID = rowID + 1;
                WebElement cancelbutton = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody//tr[" + rowID + "]//td//button/span[text()='CANCEL']"));
                cancelbutton.click();
                waitUntilPageFinishLoading();
            }
        }
    }

    public void accessConfirmCancelPopup(String cancellationMessage, String buttonToClick) {

        if (!cancellationMessage.equals("")) {
            Assert.assertTrue("Cancellation Message TextBox is not displayed", attendeeCancellationMessageTextBox().isDisplayed());
            attendeeCancellationMessageTextBox().clear();
            attendeeCancellationMessageTextBox().sendKeys(cancellationMessage);
            //verify 'Yes, cancel visit" Button
            //Assert.assertTrue("Button 'Yes, cancel attendee' is not displayed",yesCancelAttendeeLink().isDisplayed());
            Assert.assertTrue("Button Label \"Yes, Cancel Attendee\" is not correct.", yesCancelAttendeeText().isDisplayed());
        }

        if (!buttonToClick.equals("")) {
            if (buttonToClick.equals("No, go back")) {

                noGoBackButton().click();
                waitUntilPageFinishLoading();
            } else if (buttonToClick.equals("Yes, cancel visit")) {
                yesCancelAttendeeLink().click();
                waitUntilPageFinishLoading();
            } else {
                Assert.fail("The given option for the button to click is not a valid one");
            }
        }
    }

    public void verifyListofRegisteredAttendee(String name, String contact, String notes, String status, String action) {
        int nameID = getColumnIdByFieldName("//table[@id='he-account-dashboard']//thead", "Name");
        int rowID = getRowIdByColumnId("//table[@id='he-account-dashboard']//tbody", nameID, name);
        nameID = nameID + 1;
        rowID = rowID + 1;

        if (!name.equals("")) {
            String actualName = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr[" + rowID + "]/td[" + nameID + "]")).getText();
            Assert.assertTrue("College Attendee Name is not Displayed.", actualName.equals(name));
        }
        if (!contact.equals("")) {
            String[] contacts = contact.split(",");
            String actualContact = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr[" + rowID + "]/td[2]")).getText();
            int contactsCount = contacts.length;
            for (int i = 0; i < contactsCount; i++) {
                Assert.assertTrue("College Contacts " + contacts[i] + " is not Displayed", actualContact.contains(contacts[i]));
            }
        }
        if (!notes.equals("")) {
            String actualNotes = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr[" + rowID + "]/td[3]")).getText();
            Assert.assertTrue("Notes is not Displayed.", actualNotes.equals(notes));
        }
        if (!status.equals("")) {
            String actualStatus = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr[" + rowID + "]/td[4]")).getText();
            Assert.assertTrue("Status is not Displayed.  Expected: " + status + "  Actual: " + actualStatus, actualStatus.equals(status));
        }
        if (!action.equals("")) {
            Assert.assertTrue("Cancel Button is not Displayed.", driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr[" + rowID + "]/td[5]//button/span[text()='CANCEL']")).isDisplayed());
        }
    }

    public void removeTimeSlotsInRegularWeeklyHoursTab(String date, String time) {
        if (getDay(date).equalsIgnoreCase("Sat")) {
            date = Integer.toString(Integer.parseInt(date) + 2);
        } else {
            if (getDay(date).equalsIgnoreCase("Sun")) {
                date = Integer.toString(Integer.parseInt(date) + 1);
            }
        }
        time = StartTime.toUpperCase();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        navigateToException();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        int Date = Integer.parseInt(date);
        String Day = getSpecificDate(Date, "EEE").toUpperCase();
        removeSlotTimeByDayAndTime(Day, time);
    }

    public void removeTimeSlotsInExceptionsTab(String date, String time) {
        navigateToException();
        waitUntilPageFinishLoading();
        nextWeekException().click();
    }

    /**
     * deletes a slot time in the regular weekly hours
     *
     * @param day,  the day of the slot time to be deleted: MON, TUE, WED, THU, FRI
     * @param time, the time of the slot time to be deleted: 8:25PM
     */
    public void removeSlotTimeByDayAndTime(String day, String time) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        driver.findElement(By.partialLinkText("REGULAR WEEKLY HOURS")).click();
        String firstLetterDay = day.substring(0, 1).toUpperCase();
        String restLettersDay = day.substring(1).toLowerCase();
        day = firstLetterDay + restLettersDay;
        if (time.length() == 6) {
            String meridian = time.substring(4);
            String hour = time.substring(0, 4);
            time = hour + meridian.toLowerCase();
        } else {
            String meridian = time.substring(5);
            String hour = time.substring(0, 5);
            time = hour + meridian.toLowerCase();
        }
        Map<String, String> days = new HashMap<String, String>();
        days.put("Mon", "1");
        days.put("Tue", "2");
        days.put("Wed", "3");
        days.put("Thu", "4");
        days.put("Fri", "5");
        try {
            WebElement deleteButton = getDriver().findElement(By.xpath(String.format("//tbody/tr/td[%s]/div/button[text()='%s']/preceding-sibling::span/i", days.get(day), time)));
            deleteButton.click();
            getRemoveTimeSlotButton().click();
        } catch (Exception e) {
            throw new AssertionFailedError(String.format("The slot time with day: %s and time: $s could not be deleted", day, time));
        }
    }

    //Function Name  : getColumnIdByUsingColumnName() for Regular Weekly Hours
    public int getColumnIdFromTable(String tableHeaderLocator, String fieldName) {
        int columnId = -1, colCount = 0;
        String presentField = null;

        waitUntilElementExists(driver.findElement(By.xpath(tableHeaderLocator)));
        WebElement webEleTableHeader = driver.findElement(By.xpath(tableHeaderLocator));
        List<WebElement> webEleRows = webEleTableHeader.findElements(By.tagName("tr"));
        logger.info("webEleRows =" + webEleRows.size());
        List<WebElement> webEleColumns = webEleRows.get(0).findElements(By.tagName("th"));
        colCount = webEleColumns.size();
        logger.info("webEleColumns =" + webEleColumns.size());

        if (colCount > 0) {
            for (int colNum = 0; colNum < colCount; colNum++) {
                presentField = webEleColumns.get(colNum).getText().trim().replace("\"", "");

                if (presentField.equals(fieldName)) {
                    columnId = colNum;
                    break;
                }
            }
        }

        return columnId;
    }

    //Function Name  : getRowIdByColumnId()  for Regular Weekly Hours
    public int getRowIdByColumn(String tableBodyLocator, int columnId, String dataToFind) {
        int rowId = -1, rowCount = 0;

        WebElement webEleTableBody = driver.findElement(By.xpath(tableBodyLocator));
        List<WebElement> webEleRows = webEleTableBody.findElements(By.tagName("tr"));
        rowCount = webEleRows.size();

        if (rowCount > 0) {
            for (int rowNum = 0; rowNum <= rowCount; rowNum++) {
                List<WebElement> webEleColumns = webEleRows.get(rowNum).findElements(By.tagName("td"));
                webEleColumns.size();
                logger.info("Time Slot =" + webEleColumns.get(columnId).findElement(By.tagName("button")).getText());
                if (webEleColumns.get(columnId).findElement(By.tagName("button")).getText().equals(dataToFind)) {
                    rowId = rowNum;
                    break;
                }
            }
        }

        return rowId;
    }

    public void verifyAddvisitButtonInCalendarPage(String option) {
        if (option.equals("Disabled")) {
            waitUntilElementExists(nextButton());
            nextButton().click();
            calendarHS().click();
            waitUntilPageFinishLoading();
            WebElement buttonDisabled = driver.findElement(By.xpath("//button[@class='ui teal disabled button _2vMIFbyypA0b_pLiQmz0hV' and @disabled='']"));
            Assert.assertTrue("AddVisit button is enabled in the calendar page", buttonDisabled.isDisplayed());
        } else if (option.equals("Enabled")) {
            while (driver.findElements(By.xpath("//div[@class='active step' and @name='Complete!']")).size() == 0) {
                waitUntilElementExists(nextButton());
                nextButton().click();
                waitForUITransition();
                waitUntilPageFinishLoading();
            }
            Assert.assertTrue("Complete page is not displayed", visitAvailabilityText().isDisplayed());
            nextButton().click();
            waitUntilPageFinishLoading();
            waitForUITransition();
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Add visit button is disabled", addVisitHS().isEnabled());
            waitForUITransition();
        } else {
            logger.info("Invalid option");
        }
    }

    public void addVisitsManuallyFromCalendarTab(String date, String time, String attendee, String location) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(goToDateButtonLocator), 1));
        doubleClick(goToDateButton());
        goToDateButton().click();
        setSpecificDateforManuallyCreatingVisit(date);
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(timePill(StartTime)), 0));
        WebElement button = driver.findElement(By.xpath(timePill(StartTime)));
        moveToElement(button);
        driver.findElement(By.xpath(timePill(StartTime))).click();
        moveToElement(attendeeTextBox());
        attendeeTextBox().sendKeys(Keys.PAGE_DOWN);
        attendeeTextBox().sendKeys(attendee);
        selectAttendeeOrInstitution(attendee).click();
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("calendar-search-reps"), 0));
        String selectedAttendeeValue = driver.findElement(By.id("calendar-search-reps")).getAttribute("value");
        waitForUITransition();
        Assert.assertTrue("Representative is not present in the Representative text box", selectedAttendeeValue.equals(attendee));
        eventLocationNewVisit().clear();
        eventLocationNewVisit().sendKeys(location);
        waitForUITransition();
        eventLocationNewVisit().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(addVisitButtonInVisitSchedulePopupLocator), 0));
        moveToElement(addVisitButtonInVisitSchedulePopup());
        Assert.assertTrue("AddVisit button is not Enabled", addVisitButtonInVisitSchedulePopup().isEnabled());
        addVisitButtonInVisitSchedulePopup().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
    }

    public void verifyAppointmentsIncalendar(String date, String time, String institution) {
        String startTime = getVisitStartTimeforcalendar();
        getNavigationBar().goToRepVisits();
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
        waitForUITransition();
        String currentDay = day(date);
        List<WebElement> calendarMoreLinksList = driver.findElements(calendarMoreLinks());
        if (calendarMoreLinksList.size() > 0) {
            for (WebElement moreLink : calendarMoreLinksList) {
                moreLink.click();
                if (overlayHeader().getText().contains(currentDay)) {
                    Assert.assertTrue("university is not displayed", driver.findElement(By.xpath(universityCalendarElementLocator(startTime, institution))).isDisplayed());
                    Assert.assertTrue("time is not displayed", driver.findElement(By.xpath(timeCalendarElementLocator(institution, startTime))).isDisplayed());
                    waitUntilPageFinishLoading();
                }
                calendarsTitle().click();
            }
        } else {
            Assert.assertTrue("university is not displayed", driver.findElement(By.xpath(universityCalendarElementLocator(startTime, institution))).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath(timeCalendarElementLocator(institution, startTime))).isDisplayed());
            waitUntilPageFinishLoading();
        }
    }

    public void scheduleNewVisitusingCustomTime(String date, String startTime, String endTime, String attendee, String location) {
        availabilityAndSettings().click();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Want a custom time? Add it manually']"), 1));
        for (int i = 0; i < 5; i++) {
            if (driver.findElements(By.cssSelector(selectDateButtonLocator)).size() == 0) {
                addVisitManually().click();
            }
        }
        waitUntilElementExists(selectDateButton());
        doubleClick(selectDateButton());
        setSpecificDateforManuallyCreatingNewVisit(date);
        ManualStartTime = startTime(startTime);
        logger.info("Start Time = " + ManualStartTime);
        moveToElement(manualEndTime());
        manualStartTime().sendKeys(ManualStartTime);
        manualEndTime().sendKeys(endTime);
        addAttendee().sendKeys(attendee);
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/div[text()='" + attendee + "']")));
        attendee(attendee).click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("calendar-search-reps"), 1));
        String selectedAttendeeValue = driver.findElement(By.id("calendar-search-reps")).getAttribute("value");
        Assert.assertTrue("Representative is not present in the Representative text box", selectedAttendeeValue.contains(attendee));
        manualStartTime().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(eventLocationHsLocator()));
        moveToElement(eventLocationHS());
        eventLocationHS().clear();
        eventLocationHS().sendKeys(location);
        eventLocationHS().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']"), 1));
        moveToElement(addVisitButtonInVisitSchedulePopup());
        Assert.assertTrue("AddVisit button is not Enabled", addVisitButtonInVisitSchedulePopup().isEnabled());
        addVisitButtonInVisitSchedulePopup().click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void verifyConfirmationMessage(String message) {
        waitUntil(ExpectedConditions.visibilityOf(confirmationMessageCreatedVisit(message)), 10);
        Assert.assertTrue("Confirmation Message is not displayed", confirmationMessageCreatedVisit(message).isDisplayed());
    }

    public void verifyCalendarPageIsDisplayed() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        Assert.assertTrue("add visit button is not displayed", addVisitButton().isDisplayed());
        Assert.assertTrue("calendar page is not displayed", calendar().isDisplayed());
    }

    public void verifyCloseDrawerInVisitSchedulePopup() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntilElementExists(circularButton());
        Assert.assertTrue("close drawer is not displayed", circularButton().isDisplayed());
        Assert.assertTrue("schedule new visit is not displayed", textInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("select an available time slot is not displayed", verifyTextInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("Go to date is not displayed", gotoDateButtonInVisitSchedulePopup().isDisplayed());
    }

    public void verifyLinkInVisitSchedulePopup(String link) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='" + link + "']"), 1));
        Assert.assertTrue(link + "is not displayed", driver.findElement(By.xpath("//span[text()='" + link + "']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='" + link + "']")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyGobacktoListLinkInVisitSchedulePopup(String link) {
        Assert.assertTrue("Go back to list link is not displayed", driver.findElement(By.xpath("//span[text()='" + link + "']")).isDisplayed());
    }

    public void verifyStartTypingTextInVisitSchedulePopup(String text) {
        Assert.assertTrue("Find Representative text is not displayed", driver.findElement(By.xpath("//span[text()='Find Representative']")).isDisplayed());
        Assert.assertTrue("Start Typing text is not displayed in the placeholder", driver.findElement(By.xpath("//input[@placeholder='" + text + "']")).isDisplayed());
        driver.findElement(By.xpath("//input[@placeholder='" + text + "']")).sendKeys(Keys.PAGE_DOWN);
    }

    public void verifyTextboxInVisitSchedulePopup(String firstName, String lastName, String email, String phone, String position) {
        Assert.assertTrue("Rep-firstname textbox is not displayed", driver.findElement(By.id(firstName)).isDisplayed());
        Assert.assertTrue("Rep-lastname textbox is not displayed", driver.findElement(By.id(lastName)).isDisplayed());
        driver.findElement(By.id(lastName)).sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id(email), 1));
        Assert.assertTrue("Rep-email textbox is not displayed", driver.findElement(By.id(email)).isDisplayed());
        Assert.assertTrue("Rep-phone textbox is not displayed", driver.findElement(By.id(phone)).isDisplayed());
        Assert.assertTrue("Rep-position textbox is not displayed", driver.findElement(By.id(position)).isDisplayed());
        driver.findElement(By.id(position)).sendKeys(Keys.PAGE_DOWN);
    }

    public void verifyRequiredFieldInVisitSchedulePopup(String requiredField) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='field required field']/label/span[text()='" + requiredField + "']"), 1));
        Assert.assertTrue("Required field text box is not displayed", driver.findElement(By.xpath("//div[@class='field required field']/label/span[text()='" + requiredField + "']")).isDisplayed());
    }

    public void verifyTextInVisitSchedulepopup(String actualValue, String text) {
        Assert.assertTrue("Rep-Institiution textbox is not displayed", driver.findElement(By.id(text)).isDisplayed());
        String originalText = driver.findElement(By.xpath("//span[@class='_32cVj2iOoilFhz0dhZXXd']/span")).getText();
        Assert.assertTrue("If their school isn't in the list, you can simply type it in text is not displayed", originalText.equals(actualValue));
        addrepInstitution().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//input[@aria-label='Event Location']"), 1));
        Assert.assertTrue("event location is not displayed", eventLocationNewVisit().isDisplayed());
        eventLocationNewVisit().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/textarea[@id='internalNotes']"), 1));
        Assert.assertTrue("internal notes is not displayed", internalNotesTextBoxInVisitSchedulePopup().isDisplayed());
    }

    public void verifyAddvisitButtoninVisitSchedulepopup(String addVisitButton) {
        Assert.assertTrue("add visit button is not displayed", button(addVisitButton).isDisplayed());
        circularIconCloseButton().click();
    }

    private WebElement getAddAttendeeSearchBox() {
        return driver.findElement(By.cssSelector("input[placeholder='Start Typing ...']"));
    }

    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }

    private WebElement viewMoreUpcomingEventsLink() {
        return getDriver().findElement(By.xpath("//span[text()='View More Upcoming Events']"));
    }

    private WebElement viewMorePastEventsLink() {
        return getDriver().findElement(By.xpath("//span[text()='View More Past Events']"));
    }

    private WebElement fairElementDetails(String fairName) {
        return getDriver().findElement(By.xpath
                ("//div[@class='_1743W0qaWdOtlS0jkveD7o'][1]/table/tbody/tr/td[text()='" + fairName + "']/following-sibling::td[4]/a/span"));
    }

    private WebElement editFairButton() {
        return getDriver().findElement(By.cssSelector("#edit-college-fair"));
    }

    private WebElement cancelThisCollegeFair() {
        return getDriver().findElement(By.cssSelector("button.ui.negative.button"));
    }

    private String cancelMessageTextBoxLocator() {
        return "college-fair-cancellation-message";
    }

    private WebElement cancelFairButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui primary right floated button _4kmwcVf4F-UxKXuNptRFQ']"));
    }

    private WebElement closeButton() {
        return getDriver().findElement(By.xpath(closeButtonLocator));
    }

    private String closeButtonLocator = "//button[text()='Close']";

    private WebElement addFairButton() {
        return getDriver().findElement(By.cssSelector("#add-college"));
    }

    private WebElement fairNameTextBox() {
        return getDriver().findElement(By.cssSelector("#college-fair-name"));
    }

    private WebElement dateCalendarIcon() {
        return getDriver().findElement(By.cssSelector("div.ui.fluid.input i.calendar.large.link.icon"));
    }

    private WebElement startTimeTextBox() {
        return getDriver().findElement(By.cssSelector("#college-fair-start-time"));
    }

    private WebElement endTimeTextBox() {
        return getDriver().findElement(By.cssSelector("#college-fair-end-time"));
    }

    private WebElement rsvpCalendarIcon() {
        return getDriver().findElement(By.cssSelector("#college-fair-rsvp-deadline + i"));
    }

    private WebElement costTextBox() {
        return getDriver().findElement(By.cssSelector("#college-fair-cost"));
    }

    private WebElement maxNumOfColleges() {
        return getDriver().findElement(By.cssSelector("#college-fair-max-number-colleges"));
    }

    private WebElement numOfStudents() {
        return getDriver().findElement(By.cssSelector("#college-fair-number-expected-students"));
    }

    private WebElement autoApprovalYesRadButton() {
        return getDriver().findElement(By.cssSelector("#college-fair-automatic-request-confirmation-yes"));
    }

    private WebElement autoApprovalNoRadButton() {
        return getDriver().findElement(By.cssSelector("#college-fair-automatic-request-confirmation-no"));
    }

    private WebElement saveButton() {
        return getDriver().findElement(By.xpath("//span[text()='Save']"));
    }

    private WebElement cancelFairNoAutoApprovals() {
        return driver.findElement(By.cssSelector("button.ui.primary.right.floated.button span"));
    }

    private WebElement availabilityAndSettingsButton() {
        return getDriver().findElement(By.partialLinkText("Availability & Settings"));
    }

    private WebElement innerExceptionsButton() {
        return getDriver().findElement(By.cssSelector("ul.ui.pointing.secondary.fourth.menu li:nth-of-type(3) a"));
    }

    private WebElement chooseADateButton() {
        return getDriver().findElement(By.cssSelector("button.ui.small.button i"));
    }

    private WebElement newTimeSlotStartTime() {
        return getDriver().findElement(By.cssSelector("input[title=\"start time\"]"));
    }

    private WebElement newTimeSlotEndTime() {
        return getDriver().findElement(By.cssSelector("input[title=\"end time\"]"));
    }

    private WebElement newTimeSlotVisits() {
        return getDriver().findElement(By.cssSelector("input[title=\"numVisits\"]"));
    }

    private WebElement addTimeSlotButton() {
        return getDriver().findElement(By.xpath("//span[text()='ADD TIME SLOT']"));
    }

    private WebElement timeSlot(String date, String startTime) {
        return getDriver().findElement(By.xpath("//span[text()='" + date + "']/../../../../following-sibling::tbody/tr/td/div/button[text()='" + startTime + "']"));
    }

    private WebElement timeSlotDeleteIcon(String date, String startTime) {
        return getDriver().findElement(By.xpath("//span[text()='" + date + "']/../../../../following-sibling::tbody/tr/td/div/button[text()='" + startTime + "']/../span/i"));
    }

    private WebElement removeSlotConfirmButton() {
        return getDriver().findElement(By.cssSelector("div.ui.modal.transition.visible.active button.ui.primary.button"));
    }

    private WebElement removeOpenings() {
        return getDriver().findElement(By.cssSelector("i.teal.trash.outline.icon"));
    }

    private WebElement writeInConfirmationMessage() {
        return getDriver().findElement(By.cssSelector("textarea[name='emailInstructions']"));
    }

    private void writeInSpecialInstructionForRepViists(String message) {
        getDriver().findElement(By.cssSelector("textarea[name='webInstructions']")).clear();
        getDriver().findElement(By.cssSelector("textarea[name='webInstructions']")).sendKeys(message);
    }

    private WebElement getContactsBtn() {
        return driver.findElement(By.partialLinkText("Contacts"));
    }

    private WebElement getSearchBoxforContact() {
        return driver.findElement(By.name("contacts-search"));
    }

    private WebElement getSearchButton() {
        return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));
    }

    private WebElement fairCost() {
        return getDriver().findElement(By.id("college-fair-cost"));
    }

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
        actions.doubleClick(elementLocator).build().perform();

    }

    private WebElement getSearchAndScheduleBtn() {
        return driver.findElement(By.partialLinkText("Search and Schedule"));
    }

    public void clicklinkCollegeFair() {
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(collegeFairs());
        collegeFairs().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("add-college")));
    }

    public void verifyCollgeFairBlankDashBoard() {
        Assert.assertTrue("College Fairs Header is not present", getDriver().findElement(By.cssSelector("h1")).isDisplayed());
        Assert.assertTrue("Welcome to College Fairs Message is not present", text("Welcome to College Fairs").isDisplayed());
        Assert.assertTrue("College Fair Default Message is not present", text("Details about past and upcoming College Fairs you have created will appear here").isDisplayed());
        Assert.assertTrue("Add a College Fair Button is not present", getDriver().findElement(By.cssSelector(".ui.small.button.ui.primary.button")).isDisplayed());

    }

    public void SetSpecialInstructionsForHEUser(String Instructions) {
        getWebInstructions().clear();
        getWebInstructions().sendKeys(Instructions);
        button("Update Messaging").click();

    }

    private WebElement chooseDates() {
        return button(By.cssSelector("button[class='ui button _2TgAEzclbuAyQiI-jXUoxe']"));
    }

    private WebElement addBlockedTime() {
        return button(By.cssSelector("button[class='ui primary button _2r0LAIwbM94mTAqf-2YGUG']"));
    }

    public void VerifySpecialInstructionsForHE(String instructionsText) {
        Assert.assertTrue("Special Instructions for RepVisits Text is not similar", getDriver().findElement(By.id("webInstructions")).getText().contains(instructionsText));
    }

    public void navigateToRepVisitsSection(String pageName) {
        getNavigationBar().goToRepVisits();
        if (pageName.equalsIgnoreCase("visit feedback")) {
            notificationsAndTasks().click();
            getVisitsFeedbackBtn().click();
        } else {
            driver.findElement(By.partialLinkText(pageName)).click();
        }
        waitUntilPageFinishLoading();
    }

    public void verifyRepVisitsPageWhenNoVisitsScheduledForNext7Days() {

        Assert.assertTrue("'You don't have any visits or fairs for the next week' text is not displayed",
                driver.findElement(By.xpath("//div[@class='column _2oJjQM9p7RJ4cIsu8IJs-y _2yDDA7hJhhRjDz5LtnmL-e']")).getText().equals("You don't have any visits or fairs for the next week."));
        Assert.assertTrue("Calendar icon is not displayed", driver.findElement(By.xpath("(//div[@class='column _2oJjQM9p7RJ4cIsu8IJs-y'])[1]")).isDisplayed());
        Assert.assertTrue("'It looks like you don't have any upcoming visits or fairs in the next 7 days.' text is not displayed",
                driver.findElement(By.xpath("(//div[@class='column _2oJjQM9p7RJ4cIsu8IJs-y'])[2]")).getText().equals("It looks like you don't have any upcoming visits or fairs in the next 7 days.\n" +
                        "You can always check your calendar for more upcoming appointments."));
        calendar().click();

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

        for (int day = 0; day < 7; day++) {
            String date = getSpecificDate(day, "MMMMM dd yyyy");
            List<WebElement> events = getEventsScheduledForDate(date.split(" ")[1], date.split(" ")[0], date.split(" ")[2]).get("EVENTS");

            while (events.size() != 0) {
                WebElement event = events.get(0);

                String cssClass = event.findElement(By.xpath("./div[@class='rbc-event-content']/div")).getAttribute("class");

                if (cssClass.equals("_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa"))
                    eventType = "VISIT";

                if (cssClass.equals("_2_SLvlPA02MerU8g5DX1vz _2CrNWdVYs3sN4k1Rgr_RDv"))
                    eventType = "COLLEGE_FAIR";

                if (eventType.equals("VISIT")) {
                    WebDriverWait wait = new WebDriverWait(driver, 10);
                    event.click();

                    WebElement cancelThisVisitLink = wait.until(presenceOfElementLocated(By.xpath("//span[text()='Cancel This Visit']")));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelThisVisitLink);
                    wait.until(ExpectedConditions.visibilityOf(cancelThisVisitLink)).click();

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repVisit-cancelation-message"))).sendKeys("comment for cancellation");
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Yes, cancel visit']"))).click();

                } else if (eventType.equals("COLLEGE_FAIR")) {
                    WebDriverWait wait = new WebDriverWait(driver, 10);
                    event.click();

                    WebElement cancelThisCollegeFairButton = wait.until(presenceOfElementLocated(By.xpath("//span[text()='Cancel This College Fair']")));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelThisCollegeFairButton);
                    cancelThisCollegeFairButton.click();

                    button("Yes, Cancel this fair").click();
//                        driver.findElement(By.id("college-fair-cancellation-message")).sendKeys("by QA");
//                        driver.findElement(By.id("college-fair-cancellation-message")).sendKeys(Keys.PAGE_DOWN);
//                        button("Cancel fair and notify colleges").click();

//                        button("Close").click();
                }

                events = getEventsScheduledForDate(date.split(" ")[1], date.split(" ")[0], date.split(" ")[2]).get("EVENTS");
            }
        }
    }

    public HashMap<String, List<WebElement>> getEventsScheduledForDate(String day, String month, String year) {
        ArrayList<WebElement> eventsScheduledForDateElements = new ArrayList<WebElement>();
        ArrayList<WebElement> showMoreLinkElement = new ArrayList<WebElement>();
        HashMap<String, List<WebElement>> allElementsInDayCell = new HashMap<String, List<WebElement>>();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'rbc-calendar')]//*")));

        String currentMonthAndYear = driver.findElement(By.xpath("//div[@class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']")).getText();

        String currentMonth = currentMonthAndYear.split(" ")[0];
        String currentYear = currentMonthAndYear.split(" ")[1];

        if (month.equals(currentMonth) == false || year.equals(currentYear) == false) {
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
        } catch (Exception ex) {
        }

        showMoreLinkElement.add(showMoreLink);

        allElementsInDayCell.put("EVENTS", new ArrayList(eventsScheduledForDateElements));
        allElementsInDayCell.put("SHOW_MORE", new ArrayList(showMoreLinkElement));


        return allElementsInDayCell;
    }

    public void addnewTimeSlot(String day, String startTime, String endTime, String numVisits, String option) {
        getNavigationBar().goToRepVisits();
        WebElement element = availabilityAndSettings();
        waitUntilElementExists(element);
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        waitUntilPageFinishLoading();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        startOrEndDate().sendKeys(Keys.PAGE_DOWN);
        StartTime = startTime(startTime);
        deleteDuplicateSlot(StartTime);
        addTimeSlot().click();
        List<WebElement> slot = getDriver().findElements(By.cssSelector("button[class='ui small button IHDZQsICrqtWmvEpqi7Nd']"));
        if (slot.size() > 0) {
            availabilityButton().sendKeys(Keys.PAGE_DOWN);
        }
        availabilityEndtimeTextbox().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        waitUntilElementExists(selectDay());
        String visitDay = day(day);
        selectDayForSlotTime("div[class='ui button labeled dropdown icon QhYtAi_-mVgTlz73ieZ5W']", visitDay);
        logger.info("Start Time = " + StartTime);
        addStartTime().sendKeys(StartTime);
        addEndTime().sendKeys(endTime);
        logger.info("End Time = " + endTime);
        visitsNumber(numVisits);
        waitUntilElementExists(submit());
        addTimeSlotSubmit().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> displayingPopup = getDriver().findElements(By.xpath("//div/span[text()='Review Previously Deleted Time Slots']"));
        List<WebElement> duplicateTimeSlot = getDriver().findElements(By.xpath("//span[text()='Cannot create a duplicate time slot']"));
        if (displayingPopup.size() == 1 && option.contains("1")) {
            ignoreTimeSlotOption().click();
            addRegularHoursButton().click();
            waitUntilPageFinishLoading();
        } else if (duplicateTimeSlot.size() == 1) {
            addnewTimeSlot(day, startTime, endTime, numVisits, option);
        }
    }

    public boolean verifyTimeSlotInExceptions() {
        navigateToException();
        try {
            Assert.assertFalse("College Fair TextBox is not displayed", driver.findElement(By.xpath("//div[@class = 'igoATb7tmfPWBM8CX8CkN']/table/tbody/tr/td/div/button[text()='+StartTime+']")).isDisplayed());
        } catch (NoSuchElementException ex) {
            return true;
        }
        return false;
    }


    public void accessCreateCollegeFair(String collegeFairName, String date, String startTime, String endTime, String RSVPDate, String cost, String maxNumberofColleges, String numberofStudentsExpected, String buttonToClick) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement fairs = collegeFairs();
        waitUntilElementExists(fairs);
        collegeFairs().click();
        waitUntilPageFinishLoading();
        addCollegeButton().click();
        FairName = randomizeFairName(collegeFairName);

        if (!collegeFairName.equals("")) {
            Assert.assertTrue("College Fair TextBox is not displayed", collegeFairsNameTextBox().isDisplayed());
            collegeFairsNameTextBox().sendKeys(FairName);
        }
        if (!date.equals("")) {
            Assert.assertTrue("Date Textbox are not displayed", collegeFairsDateTextBox().isDisplayed());
            fairsDatePicker().click();
            setSpecificDateCollegeFairs(date);
        }
        if (!startTime.equals("")) {
            Assert.assertTrue("Start Time TextBox is not displayed", fairsStartTimeTextBox().isDisplayed());
            fairsStartTimeTextBox().sendKeys(startTime);
        }
        if (!endTime.equals("")) {
            Assert.assertTrue("End Time TextBox is not displayed", fairsEndTimeTextBox().isDisplayed());
            fairsEndTimeTextBox().sendKeys(endTime);
        }
        if (!RSVPDate.equals("")) {
            Assert.assertTrue("RSVP Deadline TextBox is not displayed", fairsRSVPDateTextBox().isDisplayed());
            fairsRSVPDatePicker().click();
            setSpecificDateCollegeFairs(RSVPDate);
        }
        if (!cost.equals("")) {
            Assert.assertTrue("Cost TextBox is not displayed", fairsCostTextBox().isDisplayed());
            fairsCostTextBox().sendKeys(cost);
        }
        if (!maxNumberofColleges.equals("")) {
            Assert.assertTrue("'Max Number of Colleges' TextBox is not displayed", fairsMaxCollegeTextBox().isDisplayed());
            fairsMaxCollegeTextBox().sendKeys(maxNumberofColleges);
        }
        if (!numberofStudentsExpected.equals("")) {
            Assert.assertTrue("'Number of Students Expected' TextBox is not displayed", fairsExpectedStudentsTextBox().isDisplayed());
            fairsExpectedStudentsTextBox().sendKeys(numberofStudentsExpected);
        }
        // Send a JavaScript click to this control because it will be off-screen.
        jsClick(fairsAutomaticRequestConfirmation());
        fairsExpectedStudentsTextBox().sendKeys(Keys.PAGE_DOWN);
        eMailMessageTextBox().sendKeys(Keys.PAGE_DOWN);

        if (!buttonToClick.equals("")) {
            if (buttonToClick.equals("Cancel This College Fair")) {
                Assert.assertTrue("'Cancel This College Fair' Button is not displayed", cancelFairsButton().isDisplayed());
                cancelFairsButton().click();
                waitUntilPageFinishLoading();
            } else if (buttonToClick.equals("Save")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Save']")));
                Assert.assertTrue("'Save' Button is not displayed", fairsSaveButton().isDisplayed());
                jsClick(fairsSaveButton());
                waitUntilPageFinishLoading();
            } else {
                Assert.fail("The option for the button to click =" + buttonToClick + " is not a valid one");
            }
        }
    }

    public void verifyCollegeFairOverview(String fairName, String date, String attendees, String RSVPBy, String time, String viewDetails) {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
            waitUntilPageFinishLoading();
        }
        //Verify UI
        Assert.assertTrue("'Settings' button is not displayed", fairsSettingsButton().isDisplayed());
        Assert.assertTrue("'Add a College Fair' button is not displayed", addCollegeFairButton().isDisplayed());
        Assert.assertTrue("'Upcoming Events' Heading ", upcomingEventsHeading().isDisplayed());
        Assert.assertTrue("'Past Events' Heading", pastEventsHeading().isDisplayed());

        //Verify the Headers of the table
        List<String> columns = new ArrayList<>();
        columns.add("Fair Date");
        columns.add("Fair Name");
        columns.add("Attendees");
        columns.add("RSVP By");
        columns.add("Time");

        for (String column : columns) {
            Assert.assertTrue("column Name " + column + " is not displaying as expected!", getDriver().findElement(By.xpath("//table[@class='ui unstackable table']//thead/tr//th/span[text()='" + column + "']")).isDisplayed());
        }
        fairName = FairName;

        //Verify the data present in the table
        int nameColumnID = getColumnIdByFieldName("//table[@class='ui unstackable table']//thead", "Fair Name");
        int rowID = getRowIdByColumnId("//table[@class='ui unstackable table']//tbody", nameColumnID, fairName);
        nameColumnID = nameColumnID + 1;
        rowID = rowID + 1;


        if (!fairName.equals("")) {
            String actualName = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr[" + rowID + "]//td[" + nameColumnID + "]")).getText();
            Assert.assertTrue("Name Column is not present.", actualName.equals(fairName));
        }

        if (!date.equals("")) {
            String actualDate = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr[" + rowID + "]//td[1]")).getText();
            String fairDate = verifySpecificDate(Integer.parseInt(date));
            Assert.assertTrue("Date Column is not present.", actualDate.contains(fairDate));

        }
        if (!attendees.equals("")) {
            String actualcollegesRegistered = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr[" + rowID + "]//td[3]")).getText();
            Assert.assertTrue("Colleges Registered Column is not present.", actualcollegesRegistered.equals(attendees));
        }
        if (!RSVPBy.equals("")) {
            String actualRSVPBy = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr[" + rowID + "]//td[4]")).getText();
            String RSVPfairDate = verifySpecificDate(Integer.parseInt(RSVPBy));
            Assert.assertTrue("RSVP By Column is not present.", actualRSVPBy.equals(RSVPfairDate));
        }
        if (!time.equals("")) {
            String actualTime = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr[" + rowID + "]//td[5]")).getText();
            Assert.assertTrue("RSVP By Column is not present.", actualTime.equals(time));
        }
        if (!viewDetails.equals("")) {
            WebElement actualviewDetails = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr[" + rowID + "]//td//a/span[text()='View Details']"));
            actualviewDetails.isDisplayed();
        }
    }

    public void verifyExportButtonInCalendar() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitForUITransition();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Export button is not enabled", button("Export").isEnabled());
    }

    public void exportAppointmentsInCalendarPage(String startDate, String endDate) {
        exportButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='Export Settings']")));
        Assert.assertTrue("Export Settings text is not displayed in the Export button", driver.findElement(By.xpath("//div/span[text()='Export Settings']")).isDisplayed());
        Assert.assertTrue("From Text is not displayed in the Export button", driver.findElement(By.xpath("//span[text()='From']")).isDisplayed());
        Assert.assertTrue("To text is not displayed in the Export button", driver.findElement(By.xpath("//span[text()='To']")).isDisplayed());
        String From = selectdateforExportAppointmentsIncalendar(startDate);
        setDateforCalendarPage(From, "From");
        String To = selectdateforExportAppointmentsIncalendar(endDate);
        setDateforCalendarPage(To, "To");
        Assert.assertTrue("Download button is not displayed", button("Download").isDisplayed());
        button("Download").click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void verifyDownloadedCsvFileInCalendar(String csvFile, DataTable headers) {
        List<String> expectedHeaderValues = headers.asList(String.class);
        String home = System.getProperty("user.home");
        String[] actualHeaderValues = CsvFileUtility.getCsvHeaders(String.format("%s/Downloads/%s", home, csvFile));
        for (int i = 0; i < expectedHeaderValues.size(); i++) {
            Assert.assertTrue(String.format("The Csv header is not correct, actual: %s, expected: %s ",
                    actualHeaderValues[i], expectedHeaderValues.get(i)),
                    actualHeaderValues[i].contains(expectedHeaderValues.get(i)));
        }
    }

    public void deleteDownloadedFileInCalendar(String filePath) {
        String home = System.getProperty("user.home");
        FileManager.deleteFile(String.format("%s/Downloads/%s", home, filePath));
    }

    public void verifySuccessMessage(String originalMessage) {
        String successMessage = getDriver().findElement(By.xpath("//div[@class='content']")).getText();
        Assert.assertTrue("Success Message is not equal", successMessage.equals(originalMessage));
    }

    public void setDateforCalendarPage(String date, String fromOrTo) {
        String[] parts = date.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        if (fromOrTo.equals("From")) {
            getDriver().findElement(By.cssSelector("button[class='ui button _1wqaQnL4wzTmKK7w_sXTwT']")).click();
        } else if (fromOrTo.equals("To")) {
            getDriver().findElement(By.cssSelector("button[class='ui button _2pj6YkRYwl9szEe_wh7dxF']")).click();
        } else {
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
        } else {
            cal = getDeltaDate(Integer.parseInt(addDays));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
        goToDateButton().click();
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void setSpecificDateForException(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        if (addDays.length() > 2) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(addDays, formatter);
            int days = date.getMonthValue();
            cal.add(Calendar.DATE, days);
        } else {
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

    public void setSpecificDateCollegeFairs(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        if (addDays.length() > 2) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(addDays, formatter);
            int days = date.getMonthValue();
            cal.add(Calendar.DATE, days);
        } else {
            cal = getDeltaDate(Integer.parseInt(addDays));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@id='college-fair-date']/following-sibling::i[@class='calendar alternate outline large link icon _33WZE2kSRNAgPqnmxrKs-o']")).click();
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void setSpecificDateforManuallyCreatingVisit(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        goToDateButton().click();
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void setSpecificDateforManuallyCreatingNewVisit(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        goToNewDateButton().click();
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public HashMap<String, Integer> getDateCellIndicesInGrid(WebElement dateCell) {
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

        if (format != null)
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
        Assert.assertTrue("'Connecting Naviance and RepVisits' Title is not displayed", connectingNavianceText().isDisplayed());
        Assert.assertTrue("Text under the Title 'Connecting Naviance and RepVisits' is not displayed", optOutTextInNaviance().isDisplayed());
        Assert.assertTrue("'How the RepVisits-Naviance Connection Works:' Title is not displayed", howItWorksText().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection Works:' is not displayed", appointmentScheduledText().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection Works:' is not displayed", reScheduledOrCancelledMessage().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection Works:' is not displayed", repVisitsInstructionText().isDisplayed());
        Assert.assertTrue("'How the RepVisits-Naviance Connection is Set Up:' Title is not displayed", setupTitle().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed", publishedOptionText().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed", visitSettingsText().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed", navianceImportText().isDisplayed());
        Assert.assertTrue("Text under the title 'How the RepVisits-Naviance Connection is Set Up:' is not displayed", optInOptOutInstruction().isDisplayed());
        Assert.assertTrue("Radio button with the label 'Yes, I would like to connect Naviance and RepVisits' is not displayed", optInYesRadioButton().isDisplayed());
        Assert.assertTrue("Radio button with the label 'No, I would like to use RepVisits without the Naviance integration' is not displayed", optInNoRadioButton().isDisplayed());
    }

    public String selectdate(String addDays) {
        String DATE_FORMAT_NOW = "EEEE, MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    /**
     * Verify no notification message in notification tab
     * @param message : no notification message
     *                ex : You currently have no notifications...
     */
    public void verifynoNotificationMessage(String message) {
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(notificationsAndTasks());
        notificationsAndTasks().click();
        removeNotificationRequestSubtab("QA Declined", "Yes, Decline");
        Assert.assertTrue("'You currently have no notifications' is not displayed", text(message).isDisplayed());
    }

    public void setSpecificStartAndEndDatesinRegularWeeklyHoursTab(String startDate, String endDate) {
        waitUntilPageFinishLoading();
        setDefaultDateforStartAndEndDate();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        availabilityAndSettings().click();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        endDate = getSpecificDate(endDate);
        setDate(endDate, "End");
        startDate = getSpecificDate(startDate);
        setDate(startDate, "Start");
    }

    public void verifyVisitsinException(String option, String time, String Date, String visits) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        navigateToException();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(Date);
        setDate(Currentdate, "other");
        String date = selectCurrentDate(Date);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody//tr/td/div//button[text()='" + StartTime + "']")));
        Assert.assertTrue("Appointments are not displayed", driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody//tr/td/div//button[text()='" + StartTime + "']")).isDisplayed());
        WebElement slot = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody//tr/td/div//button[text()='" + StartTime + "']"));
        doubleClick(slot);
        if (option.equals("Max visits met") || option.equals("Fully booked")) {
            List<WebElement> element = driver.findElements(By.xpath("//table//thead//div/span[text()='" + date + "']/parent::div/following-sibling::div/span[text()='" + option + "']/ancestor::thead/following-sibling::tbody/tr/td/div/span[text()='Appointment scheduled']/following-sibling::button[text()='" + StartTime + "']"));
            if (element.size() == 0) {
                Assert.assertTrue(option + " are not displayed", driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::th/div/span[text()='" + option + "']")).isDisplayed());
            } else {
                logger.info("More than one time slots are there");
            }
        } else {
            saveButton().click();
            Assert.assertTrue(option + " are not displayed", driver.findElement(By.xpath("//span[text()='" + date + "']/ancestor::th/ancestor::thead/following-sibling::tbody//tr//td//div//span[text()='" + option + "']")).isDisplayed());
        }
    }

    public void verifyTimeslot(String startDate, String endDate, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        navigateToException();
        waitUntilElementExists(availabilityAndSettings());
        availabilityAndSettings().click();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        endDate = getSpecificDate(endDate);
        setDate(endDate, "End");
        startDate = getSpecificDate(startDate);
        setDate(startDate, "Start");
        updateBtn().click();
        waitUntilPageFinishLoading();
        availabilityButton().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        WebElement slot = driver.findElement(By.xpath("//div/button[text()='" + StartTime + "']"));
        waitUntilElementExists(slot);
        Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//td[@class='three wide _2Bvad4lXuWWJM64BNVsAQ2']/div/button[text()='" + StartTime + "']")).isDisplayed());
        driver.findElement(By.xpath("//button[text()='" + StartTime + "']")).click();
        WebElement text = driver.findElement(By.xpath("//input[@type='number']"));
        waitUntilElementExists(text);
    }

    public void editSlot(String noOfVisits) {
        driver.findElement(By.xpath("//input[@type='number']")).clear();
        driver.findElement(By.xpath("//input[@type='number']")).sendKeys(noOfVisits);
//        button("Save").click();
        driver.findElement(By.cssSelector("button[class='ui primary button kirHVQYTJ7-jMrlwR-VTA']")).click();
        waitUntilElementExists(driver.findElement(By.cssSelector("div[class='ui small icon success message toast']")));
        Assert.assertTrue("confirmation message is not displayed", driver.findElement(By.cssSelector("div[class='ui small icon success message toast']")).isDisplayed());
    }

    public void verifyPillsInManuallyAddedAppointmentsPage(String date, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        button("add visit").click();
        waitForUITransition();
        dateButtonInAddvisitButtonPopup().click();
        setSpecificDate(date);
        waitForUITransition();
        try {
            Assert.assertTrue("Appointments are not diplayed", driver.findElement(By.xpath("//td/button[text()='" + StartTime + "']")).isDisplayed());
            driver.findElement(By.xpath("//td/button[text()='" + StartTime + "']")).click();
        } catch (Exception e) {
        }
    }

    public void verifyPillsNotdisplayedScheduleNewVisit(String date, String time) {
        time = StartTime;
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        button("add visit").click();
        waitForUITransition();
        dateButtonInAddvisitButtonPopup().click();
        setSpecificDate(date);
        waitForUITransition();
        try {
            getDriver().findElement(By.xpath("//td/button[text()='" + time + "']"));
            throw new AssertionFailedError("The Time slot " + time + " is displayed in the Schedule New Visit page");
        } catch (Exception e) {
        }
    }

    public void removeTimeslotforEntireDayInRegularWeeklyHours(String date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        int Date = Integer.parseInt(date);
        String Day = getSpecificDate(Date, "EEE").toUpperCase();
        int columnID = getColumnIdFromTable("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']/thead", Day);
        columnID = columnID + 1;
        List<WebElement> rowCount = driver.findElements(By.xpath("//table/tbody/tr/td[" + columnID + "]/div/span/i[@class='trash alternate outline icon _26AZia1UzBMUnJh9vMujjF']"));

        if (columnID > 0) {
            //Remove Time slot
            for (int rowID = rowCount.size(); rowID >= 1; rowID--) {
                WebElement removeIcon = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody//tr[" + rowID + "]//td[" + columnID + "]//i[@class='trash alternate outline icon _26AZia1UzBMUnJh9vMujjF']"));
                jsClick(removeIcon);
                waitUntilPageFinishLoading();
                driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
                waitUntilPageFinishLoading();
            }
        } else {
            logger.info("Time Slot is not displayed in the Regular weekly hours ");
        }
    }

    public void verifyActivity(String option, String user, String university, String date, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        notificationAndTasks().click();
        waitUntilPageFinishLoading();
        activityTab().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='" + user + "']")));
        Assert.assertTrue("user name is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']")).isDisplayed());
        Assert.assertTrue("notification is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        Assert.assertTrue(option + " is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        String Date = driver.findElement(By.xpath("//b[text()='" + university + "']/parent::div/following-sibling::div/span[contains(text(),'" + StartTime + "')]/parent::div/span")).getText();
        String actualDate[] = Date.split(" at");
        String originalDate = actualDate[0];
        String visitDate = selectdateForActivity(date);
        Assert.assertTrue("Date is not equal", originalDate.equals(visitDate));
    }

    public void verifyActivityforReschedule(String option, String user, String university, String date, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        notificationAndTasks().click();
        activityTab().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='" + user + "']")));
        Assert.assertTrue("user name is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']")).isDisplayed());
        Assert.assertTrue("notification is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        Assert.assertTrue(option + " is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        String Date = driver.findElement(By.xpath("//b[text()='" + university + "']/parent::div/following-sibling::div/span[contains(text(),'" + RescheduleStartTimeforNewVisit + "')]/parent::div/span")).getText();
        String actualDate[] = Date.split(" at");
        String originalDate = actualDate[0];
        String visitDate = selectdateForActivity(date);
        Assert.assertTrue("Date is not equal", originalDate.equals(visitDate));
    }

    public void verifyReScheduledNotification(String user, String institution, String day, String date, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        notificationAndTasks().click();
        activityTab().click();
        waitUntilPageFinishLoading();
        if (text("You currently have no notifications.").isDisplayed()) {
            logger.info("you have no notifications");
        } else {
            while (showMore().isDisplayed()) {
                showMore().click();
                waitUntilPageFinishLoading();
            }
        }
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='" + user + "']")));
        Assert.assertTrue("user name is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']")).isDisplayed());
        Assert.assertTrue("notification is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='rescheduled']")).isDisplayed());
        Assert.assertTrue("institution is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='rescheduled']/parent::div[text()='a Visit']/../../following-sibling::div/div/div/b[text()='" + institution + "']")).isDisplayed());
        Assert.assertTrue("date and time is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='rescheduled']/parent::div[text()='a Visit']/../../following-sibling::div/div/div/b[text()='" + institution + "']/../following-sibling::div/span[contains(text(),'" + day + ", " + date + " at " + time + "')]")).isDisplayed());
    }

    public void verifyCalendarPage(String university, String time, String date, String option) {
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']")).isDisplayed());
        Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).click();
        waitUntilPageFinishLoading();
    }

    public void removeAppointmentfromCalendar() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Cancel This Visit']")));
        Assert.assertTrue("Cancel This Visit is not displayed", driver.findElement(By.xpath("//button/span[text()='Cancel This Visit']")).isDisplayed());
        driver.findElement(By.xpath("//button/span[text()='Cancel This Visit']")).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("repVisit-cancelation-message")));
        driver.findElement(By.id("repVisit-cancelation-message")).click();
        driver.findElement(By.id("repVisit-cancelation-message")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("repVisit-cancelation-message")).sendKeys("by QA");
        button("Yes, Cancel Visit").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }

    public void verifyCancelOrReSchedule(String option, String user, String time, String institution, String date, String startTime) {
        startTime = getRescheduleVisitSchedulePopupStartTimeInCalendar();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + institution + "')]")));
        Assert.assertTrue("Institution name is not displayed", driver.findElement(By.xpath("//div[contains(text(),'" + institution + "')]")).isDisplayed());
        Assert.assertTrue("Username is not displayed", driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]")).isDisplayed());
        String actualDate = selectdate(date);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='" + actualDate + "']"), 1));
        Assert.assertTrue("Date is not displayed", driver.findElement(By.xpath("//span[text()='" + actualDate + "']")).isDisplayed());
        Assert.assertTrue("Time is not displayed", driver.findElement(By.xpath("//div/span[text()='" + startTime + "']")).isDisplayed());
        eventLocationTextboxInSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        internalNotesTextBoxInReschedulePopup().sendKeys(Keys.PAGE_DOWN);
        button(option).click();
        waitUntilPageFinishLoading();
    }

    public void verifyActivityForCanceltheVisit(String option, String user, String university, String date, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        notificationAndTasks().click();
        waitUntilPageFinishLoading();
        activityTab().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='" + user + "']")));
        Assert.assertTrue("user name is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']")).isDisplayed());
        Assert.assertTrue("notification is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        Assert.assertTrue(option + " is not displayed", driver.findElement(By.xpath("//div/span[text()='" + user + "']/../b[text()='" + option + "']")).isDisplayed());
        String Date = driver.findElement(By.xpath("//b[text()='" + university + "']/parent::div/following-sibling::div/span[contains(text(),'" + RescheduleStartTimeforNewVisit + "')]/parent::div/span")).getText();
        String actualDate[] = Date.split(" at");
        String originalDate = actualDate[0];
        String visitDate = selectdateForActivity(date);
        Assert.assertTrue("Date is not equal", originalDate.equals(visitDate));
    }

    public void verifyUpcommingEvents(String collegeFairName, String rsvpDeadLine) {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        rsvpDeadLine = getSpecificDateForFairsPage(rsvpDeadLine);
        boolean resultUpcomingVerification = findUpcommingEvents(FairName, rsvpDeadLine);
        Assert.assertTrue("Upcoming Events it's not working as expected ", resultUpcomingVerification);
    }

    public boolean findUpcommingEvents(String collegeFairName, String rsvpDeadLine) {
        String eventsData;
        String eventsRsvp;
        Boolean verifyElement = false;
        String rsvpConverted = dateConverter(rsvpDeadLine);
        try {
            verifyElement = getDriver().findElement(By.xpath("//span[text()='View More Upcoming Events']")).isDisplayed();
        } catch (NoSuchElementException e) {
            verifyElement = false;
        }

        if (verifyElement) {
            List<WebElement> viewMoreEvent = getDriver().findElements(By.xpath("//span[text()='View More Upcoming Events']"));
            while (viewMoreEvent.size() > 0) {
                viewMoreUpcomingEventsLink().click();
                waitUntilPageFinishLoading();
                viewMoreEvent = getDriver().findElements(By.xpath("//span[text()='View More Upcoming Events']"));
            }
        }
        List<WebElement> upcomingEvents = getDriver().findElements(By.cssSelector("div[class='_1743W0qaWdOtlS0jkveD7o']:nth-child(1)>table[class='ui unstackable table']:nth-child(2)>tbody>tr>td+td"));
        int listSize = upcomingEvents.size();
        int count = 0;
        for (WebElement events : upcomingEvents) {
            eventsData = events.getText();
            if (events.getText().contains(collegeFairName)) {
                for (WebElement eventRsvp : upcomingEvents.subList(count + 2, listSize)) {
                    eventsRsvp = eventRsvp.getText();
                    if (eventRsvp.getText().contains(rsvpDeadLine))
                        return true;

                }
            } else {
                count = count + 1;
            }
        }
        return false;
    }

    public String dateConverter(String rsvpDeadline) {

        return "";
    }

    public Boolean cancelNewEvent(String collegeFairName) {
        waitUntilPageFinishLoading();
        waitForUITransition();
        String eventsData;
        String eventsRsvp;
        Boolean flag = false;
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        boolean verifyElement = false;

        try {
            verifyElement = driver.findElement(By.xpath("//*[contains(text(), 'View More Upcoming Events']/..')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            verifyElement = false;
        }

        if (verifyElement) {
            while (viewMoreUpcomingEventsLink().isDisplayed()) {
                viewMoreUpcomingEventsLink().click();
                waitUntilPageFinishLoading();
                waitForUITransition();
            }
        }
        List<WebElement> upcommingEvents = getDriver().findElements(By.cssSelector("div[class='_1743W0qaWdOtlS0jkveD7o']:nth-child(1)>table[class='ui unstackable table']:nth-child(2)>tbody>tr>td"));
        int listSize = upcommingEvents.size();
        int count = 0;
        for (WebElement events : upcommingEvents) {
            if (events.getText().contains(FairName)) {
                for (WebElement eventRsvp : upcommingEvents.subList(count + 5, listSize)) {
                    waitUntilPageFinishLoading();

                    //Click on View Details link
                    viewDetailLink().click();
//                    eventRsvp.click(); changed because Span was added to the HTML code.
                    clickOnEditFair();
                    cancelCollegeFair(FairName);
                    clickOnClose();
                    flag = true;
                    break;
                }
            } else {
                count = count + 1;
            }

            if (flag) {
                break;
            }
        }
        return true;
    }

    public void clickOnEditFair() {
        driver.findElement(By.cssSelector("button[class='ui basic primary right floated button _2WIBPMrHDvfagooC6zkFpq']")).click();
    }

    public void verifyCanceledEvents(String collegeFairName) {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        Boolean resultFairEventsVerification = findPastEvents(FairName);
        Assert.assertTrue("Upcoming Events it's not working as expected ", resultFairEventsVerification);
    }

    public Boolean findPastEvents(String collegeFairName) {
        String eventsData;
        String eventsRsvp;
        boolean verifyElement = false;


        boolean verifyLink = false;

        try {
            waitForUITransition();
            verifyLink = getDriver().findElement(By.xpath("//span[text()='View More Past Events']")).isDisplayed();
        } catch (NoSuchElementException e) {
            verifyLink = false;
        }

        if (verifyLink) {
            List<WebElement> viewPastEvent = getDriver().findElements(By.xpath("//span[text()='View More Past Events']"));
            while (viewPastEvent.size() > 0) {
                viewMorePastEventsLink().click();
                waitForUITransition();
                viewPastEvent = getDriver().findElements(By.xpath("//span[text()='View More Past Events']"));
            }
        }
        List<WebElement> pastEvents = getDriver().findElements(By.cssSelector("table[class='ui unstackable table']:nth-child(2)>tbody>tr>td+td"));
        int listSize = pastEvents.size();
        int count = 0;
        for (WebElement events : pastEvents) {
            eventsData = events.getText();
            if (events.getText().contains(FairName)) {
                for (WebElement eventRsvp : pastEvents.subList(count + 2, listSize)) {
                    eventsRsvp = eventRsvp.getText();
                    if (eventRsvp.getText().contains("Canceled"))
                        return true;

                }
            } else {
                count = count + 1;
            }
        }
        return false;
    }

    public String selectdateForActivity(String addDays) {
        String DATE_FORMAT_NOW = "EEEE, MMM dd, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getcurrentBlockedDate(String addDays) {
        String DATE_FORMAT_NOW = "MM/dd/yy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String selectdateForRequestSubTab(String addDays) {
        String DATE_FORMAT_NOW = "EEEE, MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifyNotificationInRequestTab(String option, String user, String institution, String time, String date) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(notificationAndTaskButton()));
        notificationAndTasks().click();
        waitUntilPageFinishLoading();
        String startDate = getSpecificDateForNotification(date);
        if (option.equals("Decline")) {
            Assert.assertTrue("Decline button is displayed with visit details", verifyDeclineButton(user, institution, startDate).size() == 0);
        } else if (option.equals("Confirm")) {
            Assert.assertTrue("Confirm button is displayed with visit details", verifyConfirmButton(user, institution, startDate).size() == 0);
        } else {
            Assert.fail("Invalid option");
        }
    }

    private void setDefaultDateforStartAndEndDate() {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        String date = verifyStartDate();
        if (verifyDateIsEnabledOrDisabled(date)) {
            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()=" + date + " and @aria-disabled='false']")).click();
            date = verifyEndDate();
            if (verifyDateIsEnabledOrDisabled(date)) {
                driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()=" + date + " and @aria-disabled='false']")).click();
            }
        } else {
            date = verifyEndDate();
            if (verifyDateIsEnabledOrDisabled(date)) {
                driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()=" + date + " and @aria-disabled='false']")).click();
            }
            date = verifyStartDate();
            if (verifyDateIsEnabledOrDisabled(date)) {
                driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()=" + date + " and @aria-disabled='false']")).click();
            }
        }
        clickUpdateButtonInRepVisits();
    }

    private boolean verifyDateIsEnabledOrDisabled(String date) {
        boolean enabledDate = false;
        List<WebElement> enabledOrDisabled = driver.findElements(By.xpath("//div[text()=" + date + " and @aria-disabled='false']"));
        if (enabledOrDisabled.size() != 0) {
            enabledDate = true;
        } else if (enabledOrDisabled.size() == 0) {
            enabledDate = false;
        } else {
            logger.info("The Date selected is out of RANGE.");
        }
        return enabledDate;
    }

    private String verifyStartDate() {
        String startDate = getSpecificDate("0");
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(1)")).click();
        String[] parts = startDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        String date = parts[1];
        findMonth(calendarHeading, "Start");
        return date;
    }

    private String verifyEndDate() {
        String endDate = getSpecificDate("1");
        button(By.cssSelector("div[style='display: inline-block; position: relative;'] :nth-child(3)")).click();
        String[] parts = endDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        String date = parts[1];
        findMonth(calendarHeading, "End");
        return date;
    }

    public void findMonth(String month, String startOrEndDate) {
        waitUntilPageFinishLoading();
        boolean monthStatus = false;
        monthStatus = compareDate(month, startOrEndDate);
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
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }

    public Boolean compareDate(String month, String startOrEndDate) {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        if (startOrEndDate.contains("Start")) {
            dateCaption = driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).getText();
        } else if (startOrEndDate.contains("End")) {
            dateCaption = button(By.cssSelector("div[style='display: inline-block; position: relative;'] :nth-child(3)")).getText();
        } else if (startOrEndDate.contains("FirstDate")) {
            dateCaption = driver.findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button")).getText();
        } else if (startOrEndDate.contains("LastDate") || startOrEndDate.equals("DisabledDate")) {
            dateCaption = driver.findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/following-sibling::button")).getText();
        } else {
            dateCaption = driver.findElement(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).getText();
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

    public String selectCurrentDate(String addDays) {
        String DATE_FORMAT_NOW = "MM/dd/yy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    /*locators for Messaging Options Page*/
    private WebElement getWebInstructions() {
        return getDriver().findElement(By.id("webInstructions"));
    }

    /**
     * remove notification from request tab
     * @param message : declined message
     *
     * @param submit : submit button name
     */
    public void removeNotificationRequestSubtab(String message, String submit) {
        try{
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='ui mini button _3wYCijG-cEpNomL_5h1LcD']")));
            List<WebElement> Notificationsize = getDriver().findElements(By.cssSelector("button[class='ui mini button _3wYCijG-cEpNomL_5h1LcD']"));
            while (Notificationsize.size() > 0) {
                WebElement button = driver.findElement(By.xpath("//button[1]/span[text()='Decline']"));
                jsClick(button);
                waitUntilPageFinishLoading();
                jsClick(cancellationMessage());
                cancellationMessage().sendKeys(message);
                button(submit).click();
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='success-message-grid']>button>span")));
                WebElement bt = getDriver().findElement(By.cssSelector("div[id='success-message-grid']>button>span"));
                jsClick(bt);
                Notificationsize = getDriver().findElements(By.cssSelector("button[class='ui mini button _3wYCijG-cEpNomL_5h1LcD']"));
            }
        }catch (Exception e){}
    }

    public void cancelRgisteredCollegeFair(String fairName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-college-fair")));
        Assert.assertTrue("Edit button is not displayed", editButtonInCollegeFair().isDisplayed());
        editButtonInCollegeFair().click();
        waitUntilPageFinishLoading();
        String displayedFairName = driver.findElement(By.id("college-fair-name")).getAttribute("value");
        Assert.assertTrue("FairName is displayed", displayedFairName.equals(FairName));
        driver.findElement(By.id("college-fair-start-time")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("college-fair-max-number-colleges")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("college-fair-email-message-to-colleges")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This College Fair button is not displayed", button("Cancel This College Fair").isDisplayed());
        button("Cancel This College Fair").click();
        waitUntilPageFinishLoading();
        List<WebElement> textbox = driver.findElements(By.id("college-fair-cancellation-message"));
        if (textbox.size() > 0) {
            driver.findElement(By.id("college-fair-cancellation-message")).sendKeys("by QA");
            driver.findElement(By.id("college-fair-cancellation-message")).sendKeys(Keys.PAGE_DOWN);
            button("Cancel fair and notify colleges").click();
        } else {
            button("Yes, Cancel this fair").click();
        }
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Close']")));
        driver.findElement(By.xpath("//button[text()='Close']")).click();
        waitUntilPageFinishLoading();
    }

    /*We have to refactor the same method with a Create NEw and Edit a Fair */
    public void cancelRegisteredEditedCollegeFair(String fairName) {
        fairName = FairName;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(editCollegeFairButton()));
        Assert.assertTrue("Edit button is not displayed", editButtonInCollegeFair().isDisplayed());
        editButtonInCollegeFair().click();
        waitUntilPageFinishLoading();
        String displayedFairName = fairNameTextBox().getAttribute("value");
        Assert.assertTrue("FairName is displayed", displayedFairName.equals(fairName));
        fairsStartTimeInEditFairsPopup().sendKeys(Keys.PAGE_DOWN);
        maxNoOfCollegesInEditFairsPopup().sendKeys(Keys.PAGE_DOWN);
        collegeFairEmailInEditFairsPopup().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This College Fair button is not displayed", cancelCollegeFairButton().isDisplayed());
        cancelCollegeFairButton().click();
        waitUntilPageFinishLoading();
        if (fairCancellationTextBox().size() > 0) {
            cancelMessageTextboxInFairsPopup().sendKeys("by QA");
            cancelMessageTextboxInFairsPopup().sendKeys(Keys.PAGE_DOWN);
            notificationButtonForFairsCancel().click();
        } else {
            fairCancelButton().click();
        }
        waitUntilPageFinishLoading();
        try{
            waitUntil(ExpectedConditions.visibilityOfElementLocated(fairsClose()));
            closeButtonInFairsPopup().click();
            waitUntilPageFinishLoading();
        }catch (Exception e){
            getDriver().navigate().refresh();
            waitUntilPageFinishLoading();
        }
    }

    public String selectdateforExportAppointmentsIncalendar(String addDays) {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }


    public void clickLinkNotificationsAndTasks() {
        getNavigationBar().goToRepVisits();
        notificationAndTasks().click();
    }

    public void verifyTextAskingHSUserForFeedbackOnHEVisit() {

        if (text("No visits to submit feedback, yet").isDisplayed()) {
            logger.warn("'No visits to submit feedback, yet' message is displayed");
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, 10);

        List<WebElement> listOfEntriesInPendingTab = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='_3eL2jveDiva_TtJk49-Jdt']")));
        List<WebElement> listOfEntriesContainingText = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='_3eL2jveDiva_TtJk49-Jdt']//*[contains(text(), 'has asked for feedback on their recent visit.')]")));


        Assert.assertEquals("'Asked for feedback' message is not displayed in all entries in pending tab", listOfEntriesInPendingTab.size(), listOfEntriesContainingText.size());
    }

    public void clickLinkVisitFeedback() {
        driver.findElement(By.partialLinkText("Visit Feedback")).click();
    }

    public void createFairWithGeneratedDate(String daysFromNow, DataTable fairDetails) {
        Calendar calendarStartDate = getDeltaDate(Integer.parseInt(daysFromNow));
        generatedDate = getMonth(calendarStartDate);
        generatedDateDayOfWeek = getDayOfWeek(calendarStartDate) + " " + getDay(calendarStartDate);
        Calendar calendarRSVPDate = getDeltaDate(Integer.parseInt(daysFromNow) - 1);
        waitUntil(ExpectedConditions.visibilityOf(addFairButton()));
        addFairButton().click();
        waitForUITransition();
        dateCalendarIcon().click();
        pickDateInDatePicker(calendarStartDate);
        rsvpCalendarIcon().click();
        pickDateInDatePicker(calendarRSVPDate);
        fillFairForm(fairDetails);
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));
        waitUntil(ExpectedConditions.visibilityOf(saveButton()));
        saveButton().click();
    }

    public void fillFairForm(DataTable fairDetails) {
        List<List<String>> details = fairDetails.asLists(String.class);
        for (List<String> row : details) {
            switch (row.get(0)) {
                case "Name":
                    String fairName = randomizeFairName(row.get(1));
                    FairName = fairName;
                    fairNameTextBox().sendKeys(fairName);
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
                        jsClick(autoApprovalYesRadButton());
                    } else if (row.get(1).equals("No")) {
                        jsClick(autoApprovalNoRadButton());
                    }
            }
        }
    }

    /**
     * Schedules a new visit
     *
     * @param day
     * @param time
     * @param representativeFirstName
     * @param representativeLastName
     * @param representativeInstitution
     * @param location
     * @param maxNumberOfStudents
     * @param registrationWillClose
     */
    public void scheduleNewVisit(String day, String time, String representativeFirstName, String
            representativeLastName,
                                 String representativeInstitution, String location, String maxNumberOfStudents,
                                 String registrationWillClose) {
        getNavigationBar().goToRepVisits();
        calendar().click();
        waitUntilPageFinishLoading();
        getAddVisitButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class$='_3GJIUrSQadO6hk9FZvH28D']")));
        doubleClick(DateButton());
        waitForUITransition();
        setSpecificDate(day);
        waitForUITransition();
        int Date = Integer.parseInt(day);
        time = StartTime.toUpperCase();
        ;
        day = getSpecificDate(Date, "EEE").toUpperCase();

        int columnID = getColumnIdFromTableforManuallyAddVisit("//table[@class='ui unstackable basic table']/thead", day);
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table']//tbody", columnID, time);
        if (columnID >= 0 && rowID >= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            WebElement appointmentSlot = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table']//tbody//tr[" + rowID + "]//td[" + columnID + "]//button[text()='" + StartTime + "']"));
            jsClick(appointmentSlot);
            waitUntilPageFinishLoading();
        } else {
            Assert.fail("The Time Slot " + StartTime + "is not displayed in the Manually adding appointment page ");
        }
        DateButton().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Not in the list? Add them manually']"), 1));
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
        /* Actually it's not displaying in the UI
//        getMaxNumberOfStudentsTextBox().sendKeys(maxNumberOfStudents);
////           getRegistrationWillCloseTextBox().sendKeys(registrationWillClose.split(" ")[0]);
////        getRegistrationWillCloseDropDown().click();
////        getDriver().findElement(By.xpath(String.format(".//span[text()='%s']",
////                registrationWillClose.split(" ")[1]))).click();
         Actually it's not displaying in the UI*/
        action.sendKeys(Keys.TAB).build().perform();
        institutionTextBox().sendKeys(Keys.PAGE_DOWN);
        eventLocationInAddVisitPopup().sendKeys(Keys.PAGE_DOWN);
        addVisitButtonInVisitSchedulePopup().click();
        waitForUITransition();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Month']")));
    }

    /**
     * Verifies if the block this time slot button is displayed when clicking on a slot time in the exceptions tab
     *
     * @param day  of the time slot to be selected
     * @param time of the time slot to be selected
     */
    public void verifyBlockThisTimeSlotButtonIsDisplayed(String day, String time) {
        int Date = Integer.parseInt(day);
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(day);
        setDate(Currentdate, "other");
        waitForUITransition();
        day = getSpecificDate(Date, "EEE");
        selectTimeSlot(day, StartTime);
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
        time = StartTime;
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
        time = StartTime;
        int Date = Integer.parseInt(day);
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(day);
        setDate(Currentdate, "other");
        waitForUITransition();
        day = getSpecificDate(Date, "EEE");
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
        time = StartTime;
        int Date = Integer.parseInt(day);
        day = getSpecificDate(Date, "EEE");
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
        time = StartTime;
        int Date = Integer.parseInt(day);
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(day);
        setDate(Currentdate, "other");
        waitForUITransition();
        day = getSpecificDate(Date, "EEE");
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
        time = StartTime;
        int Date = Integer.parseInt(day);
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(day);
        setDate(Currentdate, "other");
        waitForUITransition();
        day = getSpecificDate(Date, "EEE");
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
        time = StartTime;
        int Date = Integer.parseInt(day);
        day = getSpecificDate(Date, "EEE");
        getNavigationBar().goToRepVisits();
        calendar().click();
        getAddVisitButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class$='_3GJIUrSQadO6hk9FZvH28D']")));
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
        int Date = Integer.parseInt(day);
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(day);
        setDate(Currentdate, "other");
        waitForUITransition();
        day = getSpecificDate(Date, "EEE");
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
        int Date = Integer.parseInt(day);
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='previous week']")));
        waitUntilElementExists(dateButton());
        String Currentdate = getSpecificDate(day);
        setDate(Currentdate, "other");
        waitForUITransition();
        day = getSpecificDate(Date, "EEE");
        try {
            (new WebDriverWait(getDriver(), 3)).until(presenceOfElementLocated(By.xpath(String.format(
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
        int Date = Integer.parseInt(day);
        day = getSpecificDate(Date, "EEE");
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
        getNavigationBar().goToRepVisits();
        calendar().click();
        waitUntil(ExpectedConditions.visibilityOf(getAddVisitButton()));
        waitForUITransition();
        while (getDriver().findElements(By.xpath(String.format(".//div[span/text()='%s' and span/text()='%s']", time, college))).size() < 1) {
            driver.findElement(By.cssSelector("button[title= 'Forwards']")).click();
        }
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
     * Refactored remove time slot without Global variable that introduce an issue
     *
     * @param date, the day of the slot time to be deleted: MON, TUE, WED, THU, FRI
     * @param time, the time of the slot time to be deleted: 8:25PM
     */
    public void removeTimeSlotsRefactoredForHS(String date, String time) {
        time = time.toUpperCase();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        int Date = Integer.parseInt(date);
        String Day = getSpecificDate(Date, "EEE").toUpperCase();
        int columnID = getColumnIdFromTable("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']/thead", Day);
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody", columnID, time);

        if (columnID >= 0 && rowID >= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            //Remove Time slot
            WebElement removeIcon = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table _3QKM3foA8ikG3FW3DiePM4']//tbody//tr[" + rowID + "]//td[" + columnID + "]//i[@class='trash outline icon _26AZia1UzBMUnJh9vMujjF']"));
            jsClick(removeIcon);
            waitUntilPageFinishLoading();
            driver.findElement(By.cssSelector("button[class='ui primary button']")).click();
            waitUntilPageFinishLoading();
        } else {
            Assert.fail("The Time Slot " + time + "is not displayed in the Regular weekly hours ");
        }
    }

    /**
     * Add New Time Slot in HS
     *
     * @param date      - Date to do the Visit or Appointment
     * @param startTime - Set Start Time of the new Visit or Appointment
     * @param endTime   - Set End Time of the new Visit or Appointment
     * @param numVisits - Set Number of Visits
     */
    public void addNewTimeSlotForHS(String date, String startTime, String endTime, String numVisits) {
        navigateToAvailabilityAndSettings();
        setStartandEndDates("0", "99");
        addnewTimeSlotRefactoredForHS(date, startTime, endTime, numVisits);
    }

    /**
     * Confirm Request
     *
     * @param user       - User to confirm request
     * @param highSchool - High School of the request
     * @param time       - Request time
     */
    public void confirmRequest(String user, String highSchool, String time) {
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(notificationAndTasks());
        navigateToNotificaionsAndTasksRequestPage();
        confirmButton(user, highSchool, time).click();
    }

    /**
     * Verify Visit in Naviance
     *
     * @param user       - To verify user data
     * @param highSchool - To verify High School data
     * @param time       - To verify time data
     */
    public void verifyVisitInNaviance(String user, String highSchool, String time) {
        //Verification in Naviance side
        time = getVisitStartTimeInReschedule();
        load(GetProperties.get("naviance.visits.url"));
        getDriver().navigate().refresh();
        load(GetProperties.get("naviance.visits.url"));
        waitForUITransition();
        Assert.assertTrue("Representative was not found in Naviance", getDriver().getPageSource().contains(user));
        Assert.assertTrue("High School was not found in Naviance", getDriver().getPageSource().contains(highSchool));
        Assert.assertTrue("Schedule time was not changed", getDriver().getPageSource().contains(time));
        getDriver().navigate().back();
    }

    /**
     * Verify Cancel in Naviance
     *
     * @param user       - To verify user data
     * @param highSchool - To verify High School data
     * @param time       - To verify time data
     */
    public void verifyCancelInNaviance(String user, String highSchool, String time) {
        //Verification Cancel in Naviance side
        load(GetProperties.get("naviance.visits.url"));
        driver.navigate().refresh();
        try {
            Assert.assertFalse("Representative was found in Naviance", !clickViewLink(user, getVisitReScheduledStartTimeInNaviance()).isDisplayed());
        } catch (Exception e) {
        }
        driver.navigate().back();
    }

    /**
     * Verify Reschedule for visit
     *
     * @param user       - To verify user data
     * @param highSchool - To verify High School data
     * @param time       - To verify time data
     */
    public void verifyRescheduleInNaviance(String user, String highSchool, String time) {
        //Verification Reschedule in Naviance side
        load(GetProperties.get("naviance.visits.url"));
        driver.navigate().refresh();
        time = getRescheduledVisitStartTime();
        Assert.assertTrue("Representative was not found in Naviance", driver.getPageSource().contains(user));
        Assert.assertTrue("High School was not found in Naviance", driver.getPageSource().contains(highSchool));
        Assert.assertTrue("Schedule time was not changed", driver.getPageSource().contains(time));
        driver.navigate().back();
    }

    /**
     * VReschedule Visits for HS
     *
     * @param university - University to reschedule
     * @param time       - Time to reschedule
     * @param date       - Date to reschedule
     */
    public void reschedulevisitForHS(String university, String time, String date) {
        getNavigationBar().goToRepVisits();
        String startTime = time;
        waitUntilElementExists(calendar());
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        //  pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        List<WebElement> calendarTime = driver.findElements(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']"));
        if (calendarTime.size() == 1) {
            Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']")).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
            driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).click();
            waitUntilPageFinishLoading();
        } else {
            startTime = getCalendarStartTime();
            Assert.assertTrue("university is not displayed", driver.findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + university + "']")).isDisplayed());
            Assert.assertTrue("time is not displayed", driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).isDisplayed());
            driver.findElement(By.xpath("//span[text()='" + university + "']/preceding-sibling::span[text()='" + startTime + "']")).click();
            waitUntilPageFinishLoading();
        }
    }

    public void selectRescheduleForHS(String time, String reason, String date) {
        dateButtonInVisitReschedule().click();
        setSpecificDate(date);
        waitForUITransition();
        driver.findElement(By.xpath("//button[@class='ui tiny button _3GJIUrSQadO6hk9FZvH28D']")).sendKeys(Keys.PAGE_DOWN);
        waitUntilPageFinishLoading();
        int Date = Integer.parseInt(date);
        time = RescheduleStartTimeforNewVisit.toUpperCase();
        String Day = getSpecificDate(Date, "EEE").toUpperCase();
        int columnID = getColumnIdFromTableforManuallyAddVisit("//table[@class='ui unstackable basic table']/thead", Day);
        int rowID = getRowIdByColumn("//table[@class='ui unstackable basic table']//tbody", columnID, time);
        if (columnID >= 0 && rowID >= 0) {
            columnID = columnID + 1;
            rowID = rowID + 1;
            time = time.toLowerCase();
            WebElement appointmentSlot = getDriver().findElement(By.xpath("//table[@class='ui unstackable basic table']//tbody//tr[" + rowID + "]//td[" + columnID + "]//button[text()='" + time + "']"));
            jsClick(appointmentSlot);
            waitUntilPageFinishLoading();
        } else {
            Assert.fail("The Time Slot " + time + "is not displayed in the Manually adding appointment page ");
        }
        driver.findElement(By.xpath("//td/button[text()='" + time + "']")).sendKeys(Keys.PAGE_DOWN);
        rescheduleMessageTextBox().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("rescheduleMessage textbox is not displayed", rescheduleMessageTextBox().isDisplayed());
        rescheduleMessageTextBox().sendKeys(reason);
        Assert.assertTrue("Reschedule Visit is not displayed", rescheduleVisitButton().isDisplayed());
        rescheduleVisitButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyReschedulePopupForHS(String user, String university, String time, String date) {
        waitForUITransition();
        String actualDate = driver.findElement(By.xpath("//a[text()='" + university + "']/parent::div/parent::div/following-sibling::div/div/div/span[1]")).getText();
        jsClick(rescheduleButtonInReScheduleVisitPage());
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[@class='_25XyePHsmpWU1qQ18ojKip']/span"), 1));
    }


    public void navigateToNotificaionsAndTasksRequestPage() {
        waitUntilPageFinishLoading();
        waitForUITransition();
        notificationAndTasks().click();
        requestLink().click();
    }

    public void navigateToNotificationsAndTasksVisitsFeedBack() {
        waitUntilPageFinishLoading();
        navigateToRepVisitsSection("visit feedback");
        notificationAndTasks().click();
        visitFeedback().click();
    }


    /**
     * Setup Naviance Sync Settings Page.
     *
     * @param option Option of the config in the Sync Settings Page
     */
    public void navianceSyncSettingsSetup(String option) {
        navigateToNavianceSettingsInAvailabilitySettingsPage();
        switch (option) {
            case "Automatically publish confirmed visits.":
                getDriver().findElement(By.cssSelector("input[name='autoPublish']")).click();
                saveChanges().click();
                break;
        }
    }

    /**
     * Setup Availability Settings Page.
     *
     * @param option Option of the config in the Availability Settings Page
     */
    public void availabilitySettingsSetup(String option) {
        navigateToAvailabilitySettings();
        switch (option) {
            case "All RepVisits Users":
                getDriver().findElement(By.cssSelector("input[name='availabilityVisible']")).click();
                saveChanges().click();
                break;
            case "No, I want to manually review all incoming requests.":
                WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
                getDriver().findElement(By.xpath("div/label[text()[contains(., '" + option + "')]]")).click();
                saveChangesAvailability().click();
                break;
        }
    }

    public void navigateToAvailabilitySettings() {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        availability().click();
        availabilitySettings().click();
        waitUntilPageFinishLoading();
    }


    /**
     * Adds atendees to a college fair that was just created.
     * Prerequisite:  User has the "Close or Add Attendees" drawer open after saving a new college fair.
     *
     * @param dataTable Data Table containing all the names for the attendees to be added.
     */
    public void addAttendees(DataTable dataTable) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p/span[text()='" + FairName + "']/parent::p")));
        getAddAttendeesButton().click();
        List<String> names = dataTable.asList(String.class);
        for (String name : names) {
            getAddAttendeeSearchBox().sendKeys(name);
            getDriver().findElement(By.xpath("//div[text()='" + name + "']")).click();
        }
        getDriver().findElement(By.xpath("//button/span[text()='Add Attendees']")).click();
    }

    /**
     * Click on "Disconnect RepVisits from Naviance" button.
     */
    public void disconnectFromNavianceButton() {
        try {
            disconnectButton().click();
        } catch (WebDriverException e) {
            optInYesRadioButton().click();
            nextButton().click();
            waitForUITransition();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupWizardNextButton()));
            nextButton().click();
            nextButton().click();
            nextButton().click();
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            FluentWait<WebDriver> wait = new WebDriverWait(driver, 5);
            wait.until(presenceOfElementLocated(By.className("_3ygB2WO7tlKf42qb0NrjA3")));
            takeMeToMyVisitsButton().click();
            waitUntilPageFinishLoading();
            waitUntilElementExists(todayText());
            availabilityAndSettings().click();
            navianceSettings().click();
            disconnectButton().click();
        }
    }

    /**
     * Verify Disconnect button from RV from Naviance.
     */
    public void verifyCancelDisconnectFromNavianceButton() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cancelButtonIndisconnectRVfromNaviancePopup()));
        Assert.assertTrue("Cancel from Disconnect RepVisits from Naviance is displayed", cancelIndisconnectRVfromNaviancePopup().isDisplayed());
        cancelIndisconnectRVfromNaviancePopup().click();
        Assert.assertTrue("The button Cancel closes the modal and maintains the connection between RepVisits and Naviance for the user", disconnectButton().isDisplayed());
    }

    /**
     * Verify Yes, Disconnect button from RV from Naviance.
     *
     * @param date      - Date to do the Visit or Appointment
     * @param startTime - Set Start Time of the new Visit or Appointment
     * @param endTime   - Set End Time of the new Visit or Appointment
     * @param numVisits - Set Number of Visits
     * @param attendee  - Attendee to use un the new Visit or Appointment
     * @param location  - The location in the new Visit or Appointment
     */
    public void verifyYesDisconnectFromNavianceButton(String date, String startTime, String endTime, String numVisits, String attendee, String college, String location) {
        waitUntilElementExists(yeslIndisconnectRVfromNaviancePopup());
        Assert.assertTrue("Yes from Disconnect RepVisits from Naviance is displayed", yeslIndisconnectRVfromNaviancePopup().isDisplayed());
        yeslIndisconnectRVfromNaviancePopup().click();
        /*The ability to add/edit/delete events directly in Naviance is re-enabled and is present in the UI for the user in Naviance*/
        load(GetProperties.get("naviance.visits.url"));
        waitUntilPageFinishLoading();
        getDriver().navigate().refresh();
        getDriver().navigate().refresh();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText("add new visit")));
        Assert.assertTrue("The ability to add new visit was not activated", getDriver().getPageSource().contains("add new visit"));
        Assert.assertTrue("The ability to view was not activated", getDriver().getPageSource().contains("view"));
        Assert.assertTrue("The ability to edit was not activated", getDriver().getPageSource().contains("edit"));
        Assert.assertTrue("The ability to cancel was not activated", getDriver().getPageSource().contains("cancel"));
        getDriver().navigate().back();
        /*The RepVisits>Availability & Settings>Naviance Settings page is restored to the "opt in/out" to connect page */
        /*Also MATCH-3462 verify the UI*/
        waitUntilElementExists(optInYesRadioButton());
        Assert.assertTrue("Opt In was not activated ", optInYesRadioButton().isDisplayed());
        Assert.assertTrue("Opt In was not activated ", optInNoRadioButton().isDisplayed());

        //cancelling created appointment
        verifyAndSelectAppointmentInCalendarPage(college, startTime, date, "Scheduled");
        removeAppointmentfromCalendar();

        /*Events can be added/edited/cancelled separately in both Naviance and RepVisits*/
        addNewTimeSlotAndAddVisit(date, startTime, endTime, numVisits, attendee, location);
        getNavigationBar().goToRepVisits();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        //Verification in RV side
        verifyAppointmentInCalendar(college, startTime, date, "Scheduled");

        //Verification in Naviance side
        load(GetProperties.get("naviance.visits.url"));
        getDriver().navigate().refresh();
        waitUntilElementExists(getDriver().findElement(By.linkText("add new visit")));
        try {
            Assert.assertFalse("Representative was found in Naviance", !clickViewLink(attendee, startTime).isDisplayed());
        } catch (Exception e) {
        }
        getDriver().navigate().back();

        navigateToNavianceSettingsInAvailabilitySettingsPage();

        /*After Disconnecting the publish sync, user can resync*/
        optInYesRadioButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
        waitUntilElementExists(nextButton());
        nextButton().click();
        takeMeToMyVisitsButton().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(todayText());
        selectOptionforManuallyorAutomatically("Automatically publish confirmed visits.");
        //Verification in Naviance side
        load(GetProperties.get("naviance.visits.url"));
        driver.navigate().refresh();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText("view")));
        Assert.assertTrue("Attendee details are not displayed", attendeeDetailsInNaviance(attendee, startTime).size() == 1);
        clickViewLink(attendee, startTime).click();
        Assert.assertTrue("Representative was not found in Naviance", getDriver().getPageSource().contains(attendee));
        Assert.assertTrue("Representative was not found in Naviance", getDriver().getPageSource().contains(getVisitStartTimeInNaviance()));
        driver.navigate().back();
        driver.navigate().back();
    }

    public void verifyTimeslotInException(String date, String time) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        exception().click();
        String startDate = getSpecificDate(date);
        setDate(startDate, "other");
        String formattedDate = selectCurrentDate(date);
        waitForUITransition();
        //verify Timeslot
        if (time.equals("PreviouslySetTime"))
            time = StartTime;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='" + formattedDate + "']/parent::div/parent::th/parent::tr/parent::thead/following-sibling::tbody/tr/td/div/button[text()='" + time + "']")));
        Assert.assertTrue("Timeslot is not displayed", driver.findElement(By.xpath("//div/span[text()='" + formattedDate + "']/parent::div/parent::th/parent::tr/parent::thead/following-sibling::tbody/tr/td/div/button[text()='" + time + "']")).isDisplayed());
    }

    public void verifyStaffNotifications(String primaryContactName, String alternativePrimaryContact) {
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        collegeFairsSettings().click();
        waitUntilPageFinishLoading();

        /* Primary contact is selected using a drop down verification */
        WebElement PrimaryContactDropDown = primaryContact();
        waitUntilElementExists(PrimaryContactDropDown);
        primaryContact().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(primaryContact(primaryContactName)));
        moveToElement(primaryContactName(primaryContactName));
        jsClick(primaryContactName(primaryContactName));
        Assert.assertTrue("Primary Contact was not found.", PrimaryContactDropDown.findElement(By.className("text")).getText().contains(primaryContactName));

        /* Dropdown is populated with a list of all community members that are tied to my school */

        /* Primary contact is shown in the list of users (check boxes below), but cannot be unchecked (as primary must always receive notifications) */

        Assert.assertFalse("Primary Contact can be checked.", checkbox(By.cssSelector("[name='Q29tbXVuaXR5UGVyc29uOjQ1MTg1MTAwLWVkZWUtYTQ4NS0xNmMzLTNhMjQzNmMyMjIxZg==']")).isEnabled());

        /* If primary contact is changed, previous primary user becomes checkable in the list and currently selected primary user is checked and not un-checkable.   */

        primaryContact().click();
        primaryContactName(alternativePrimaryContact).click();
        Assert.assertTrue("Primary Contact was not found.", PrimaryContactDropDown.findElement(By.className("text")).getText().contains(alternativePrimaryContact));
        Assert.assertFalse("New Primary Contact can be checked.", checkbox(By.cssSelector("[name='Q29tbXVuaXR5UGVyc29uOmZmMzg1MTAwLWVkZWUtYTQ4NS0xNmMzLTNhMjQzNmMyMjIxZg==']")).isEnabled());

        /* Heading: "Notifications for Visits"  */
        Assert.assertTrue("Notification Header was not found.", getDriver().findElement(By.cssSelector("h2[class='ui header']:nth-child(3)")).getText().contains("Notifications for Fairs"));

        /* Text: "Choose users who will receive mail notifications. Users are notified when visit requests are made, confirmed, denied, rescheduled, canceled, or manually added."  */

        waitUntilPageFinishLoading();
        String textNotificationVerification = getDriver().findElement(By.cssSelector("form[id='form-repvisits-notifications-and-fair-settings']>span")).getText();
        Assert.assertTrue("Notification Header was not found.", textNotificationVerification.
                contains("Choose users who will receive email notifications. Users are notified when visit requests are made, confirmed, denied, rescheduled, canceled or manually added."));

        /*  A list of all community members from the current high school is shown.
            The list is in two columns
            The list includes a check box next to each name
            Primary contact's check box (a) is checked, and (b) cannot be unchecked */

        Assert.assertTrue("Two columns are not displayed", getDriver().findElement(By.cssSelector("div[class='grouped fields _3wL_DuaLhBL9_OQEhGZg0p']")).isDisplayed());
        Assert.assertTrue("Two columns are not displayed", getDriver().findElement(By.cssSelector("div[class='grouped fields _3wL_DuaLhBL9_OQEhGZg0p']:nth-child(2)")).isDisplayed());

        Assert.assertFalse("Primary Contact can be checked.", checkbox(By.cssSelector("[name='Q29tbXVuaXR5UGVyc29uOmZmMzg1MTAwLWVkZWUtYTQ4NS0xNmMzLTNhMjQzNmMyMjIxZg==']")).isEnabled());

        Assert.assertTrue("Check boxes for Primary contacts are not displayed ", getDriver().findElement(By.cssSelector("div[class='ui checkbox']")).isDisplayed());

        /* When a user is checked, and the save changes button has been clicked, that user receives notifications when visit requests are made, confirmed, denied, rescheduled, canceled, or manually added. */

        primaryContact().click();
        primaryContactName(alternativePrimaryContact).findElement(By.xpath("//span[@class='text'][contains(text(), '" + alternativePrimaryContact + "')]")).click();
        checkbox(By.cssSelector("button[class='ui primary right floated button']")).click();

    }

    public void verifyCollegeFairNotificationWasReceived(String collegeFair, String attendee) {
        if (collegeFair.equals("PreviouslySetFair"))
            collegeFair = FairName;
        getNavigationBar().goToRepVisits();
        notificationAndTasks().click();
        waitUntilPageFinishLoading();
        activityTab().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='View Full Details']")));
        getDriver().findElements(By.xpath("//span[text()='View Full Details']")).get(0).click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='ui segments _3pzgJh2J1gbNBaq2S9asNJ']")));
        Assert.assertTrue("Fairs does not contains New Fair", getDriver().findElement(By.cssSelector("div[class='ui segments _3pzgJh2J1gbNBaq2S9asNJ']")).getText().contains(collegeFair));
        Assert.assertTrue("Fairs does not contains Attendee name", getDriver().findElement(By.cssSelector("div[class='ui segments _3pzgJh2J1gbNBaq2S9asNJ']")).getText().contains(attendee));
        closeFairScreen().click();
        waitUntilPageFinishLoading();
    }

    public void verifyNotificationsToNonMembersSection(String correctEmail, String incorrectEmail) {
        waitUntilElementExists(driver.findElement(By.cssSelector("div[id='app']")));
        getNavigationBar().goToRepVisits();
        collegeFairs().click();
        waitUntilPageFinishLoading();
        collegeFairsSettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[id='notification_fairs_additional_emails']")));
        Assert.assertTrue("Notification Area for Non Community Does not exist.", getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).isDisplayed());
        getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).clear();
        getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).sendKeys(incorrectEmail);
        getDriver().findElement(By.cssSelector("button[class='ui primary right floated button']")).click();
        Assert.assertTrue("Invalid email format error isn't being shown correctly", getDriver().findElement(By.xpath("//span[text() = 'Emails must be valid and separated by comma']")).isDisplayed());
        getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).clear();
        getDriver().findElement(By.cssSelector("textarea[id='notification_fairs_additional_emails']")).sendKeys(correctEmail);
        getCollegeFairsPrimaryContactPhoneNumberField().clear();
        getCollegeFairsPrimaryContactPhoneNumberField().sendKeys("555555555");
        clickSaveSettingsButtonInCollegeFairsTab();
        waitUntilPageFinishLoading();
        getDriver().navigate().refresh();
        waitUntilElementExists(button("SAVE SETTINGS"));
        clickSaveSettingsButtonInCollegeFairsTab();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='ui small icon success message toast']")));
        Assert.assertTrue("Saved was not successfully", getDriver().findElement(By.cssSelector("div[class='ui small icon success message toast']")).getText().contains("You've updated College Fair settings"));
    }

    public void verifyCalendarPageForaddVisit() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        Assert.assertTrue("add visit button is not displayed", addVisitButton().isDisplayed());
        Assert.assertTrue("calendar page is not displayed", calendar().isDisplayed());
        addVisitButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyVisitSchedulepopup() {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[contains(text(),'Go to date')]"), 1));
        waitUntilElementExists(gotoDateButtonInVisitSchedulePopup());
        Assert.assertTrue("schedule new visit is not displayed", textInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("select an available time slot is not displayed", verifyTextInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("Go to date is not displayed", gotoDateButtonInVisitSchedulePopup().isDisplayed());
        addAttendee().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(eventLocationHsLocator()));
        Assert.assertTrue("event location is not displayed", eventLocationTextboxInAddVisitSchedulePopup().isDisplayed());
        eventLocationTextboxInAddVisitSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/textarea[@name='internalNotes']")));
        Assert.assertTrue("internal notes is not displayed", internalNotesTextBoxInVisitSchedulePopup().isDisplayed());
        Assert.assertTrue("add visit button is not displayed", verifyAddVisitButtonInVisitSchedulePopup().isDisplayed());
        internalNotesTextBoxInVisitSchedulePopup().sendKeys(Keys.PAGE_UP);
        eventLocationTextboxInAddVisitSchedulePopup().sendKeys(Keys.PAGE_UP);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/textarea[@id='internalNotes']")));
        findRepTextbox().sendKeys(Keys.PAGE_UP);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Go to date')]")));
    }

    public void removeManuallyAddedBlockedDate(String startDate, String endDate) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        blockedDays().click();
        waitUntilPageFinishLoading();
        startDate = getSpecificDateFormat(startDate);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='" + startDate + "']/../following-sibling::td[@class='_1DmNQ0_pLQlqak2JJluwxn']/span")));
        WebElement BlockedDate = driver.findElement(By.xpath("//table[@class='ui basic table']//tbody/tr/td/span[text()='" + startDate + "']/../following-sibling::td[@class='_1DmNQ0_pLQlqak2JJluwxn']/span"));
        moveToElement(BlockedDate);
        jsClick(BlockedDate);
        waitUntilPageFinishLoading();
    }

    public void setSpecificBlockedDate(String reason, String blockdate) {
        String Date;
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        blockedDays().click();
        waitUntilPageFinishLoading();
        chooseDates().click();
        waitUntilPageFinishLoading();
        if (blockdate.length() > 3) {
            setDateDoubleClick(blockdate);
        }else {
            Date = getSpecificDate(blockdate);
            setDateDoubleClick(Date);
        }
        WebElement selectReason = driver.findElement(By.xpath("//div/div[@class='text']"));
        selectReason.click();
        waitUntilPageFinishLoading();
        WebElement element = driver.findElement(By.xpath("//span[text()='" + reason + "']"));
        moveToElement(element);
        jsClick(element);
        addBlockedTime().click();
    }

    public String getSpecificDateFormat(String addDays) {
        String DATE_FORMAT_NOW = "MMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void navigateToException() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        exceptionLink().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']"), 1));
    }

    public void verifyBlockedDayInException(String date) {
        Assert.assertTrue("Date button is not displayed", dateButtonInException().isDisplayed());
        dateButtonInException().click();
        setSpecificDateForException(date);
        waitForUITransition();
        String currentDate = getcurrentBlockedDate(date);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(blockedDayInException(currentDate)));
        Assert.assertTrue("Blocked day is not displayed",getDriver().findElement(blockedDayInException(currentDate)) .isDisplayed());
    }

    public void verifyPartiallyScheduledDayInExceptionsSubtab(String reason, String startDate) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        exception().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("button[title='previous week']"), 1));
        waitUntilPageFinishLoading();
        driver.findElement(By.cssSelector("button[class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']")).click();
        setSpecificDateForException(startDate);
        String date = selectCurrentDate(startDate);
        waitUntilPageFinishLoading();
        if (reason.equals("Max visits met") || reason.equals("Fully booked")) {
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::th/div/span[text()='" + reason + "']"), 1));
            Assert.assertTrue(reason + " are not displayed", driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::th/div/span[text()='" + reason + "']")).isDisplayed());
        } else {
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='" + date + "']/ancestor::th/ancestor::thead/following-sibling::tbody//tr//td//div//span[text()='" + reason + "']"), 1));
            Assert.assertTrue(reason + " are not displayed", driver.findElement(By.xpath("//span[text()='" + date + "']/ancestor::th/ancestor::thead/following-sibling::tbody//tr//td//div//span[text()='" + reason + "']")).isDisplayed());
        }
    }

    public void verifyTrashIconInExcepionTab() {
        Assert.assertTrue("Trash icon is not displayed", trashIconInException().isDisplayed());
    }

    /**
     * Add new time slot and add new visit
     *
     * @param date      - Date to do the Visit or Appointment
     * @param startTime - Set Start Time of the new Visit or Appointment
     * @param endTime   - Set End Time of the new Visit or Appointment
     * @param numVisits - Set Number of Visits
     * @param attendee  - Attendee to use un the new Visit or Appointment
     * @param location  - The location in the new Visit or Appointment
     */
    public void addNewTimeSlotAndAddVisit(String date, String startTime, String endTime, String numVisits, String attendee, String location) {
        setStartandEndDates("0", "99");
        addnewTimeSlotRefactoredForHS(date, startTime, endTime, numVisits);
        setStartandEndDates("0", "99");
        addNewVisit(date, startTime, attendee, location);
        waitForUITransition();
    }

    /**
     * Add new Visit from calendar without global variable that cause issue in the Start time value.
     *
     * @param date      - Date to do the Visit or Appointment
     * @param startTime - Set Start Time of the new Visit or Appointment
     * @param attendee  - Attendee to use un the new Visit or Appointment
     * @param location  - The location in the new Visit or Appointment
     */
    public void addNewVisit(String date, String startTime, String attendee, String location) {
        addVisitsRefactoredForHS(date, startTime, attendee, location);
    }

    /**
     * Add new Visit from calendar without global variable that cause issue in the Start time value.
     *
     * @param date      - Date to do the Visit or Appointment
     * @param startTime - Set Start Time of the new Visit or Appointment
     * @param attendee  - Attendee to use un the new Visit or Appointment
     * @param location  - The location in the new Visit or Appointment
     */
    public void addVisitsRefactoredForHS(String date, String startTime, String attendee, String location) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(addVisitButton());
        addVisitButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span/span[text()='Go to date']"), 1));
        doubleClick(goToDateButton());
        setSpecificDateforManuallyCreatingVisit(date);
        waitUntilElementExists(driver.findElement(By.xpath("//td/button[text()='" + StartTime + "']")));
        //waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//td/button[text()='"+startTime+"']"),1));
        WebElement button = driver.findElement(By.xpath("//td/button[text()='" + StartTime + "']"));
        moveToElement(button);
        driver.findElement(By.xpath("//td/button[text()='" + StartTime + "']")).click();
        moveToElement(attendeeTextBox());
        attendeeTextBox().sendKeys(Keys.PAGE_DOWN);
        attendeeTextBox().sendKeys(attendee);
        Assert.assertTrue("Attendee name is not displayed in the dropdown", driver.findElement(By.xpath("//div/div[text()='" + attendee + "']")).isDisplayed());
        selectAttendeeOrInstitution(attendee).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("calendar-search-reps")));
        eventLocationTextboxInSchedulePopup().clear();
        eventLocationTextboxInSchedulePopup().sendKeys(location);
        waitForUITransition();
        eventLocationTextboxInSchedulePopup().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']"), 1));
        moveToElement(addVisitButtonInVisitSchedulePopup());
        Assert.assertTrue("AddVisit button is not Enabled", addVisitButtonInVisitSchedulePopup().isEnabled());
        addVisitButtonInVisitSchedulePopup().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }

    /**
     * Add new time slot without using the Global variable that cause issue in the Start time value creation.
     *
     * @param day       - Date to do the Visit or Appointment
     * @param startTime - Set Start Time of the new Visit or Appointment
     * @param endTime   - Set End Time of the new Visit or Appointment
     * @param numVisits - Set Number of Visits
     */
    public void addnewTimeSlotRefactoredForHS(String day, String startTime, String endTime, String numVisits) {
        getNavigationBar().goToRepVisits();
        WebElement element = availabilityAndSettings();
        waitUntilElementExists(element);
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        availability().click();
        waitUntilPageFinishLoading();
        regularWeeklyHours().click();
        waitUntilPageFinishLoading();
        startOrEndDate().sendKeys(Keys.PAGE_DOWN);
        addTimeSlot().click();
        List<WebElement> slot = driver.findElements(By.cssSelector("button[class='ui small button IHDZQsICrqtWmvEpqi7Nd']"));
        if (slot.size() > 0) {
            availabilityButton().sendKeys(Keys.PAGE_DOWN);
        }
        availabilityEndtimeTextbox().sendKeys(Keys.PAGE_DOWN);
        waitForUITransition();
        waitUntilElementExists(selectDay());
        String visitDay = day(day);
        selectDayForSlotTime("div[class='ui button labeled dropdown icon QhYtAi_-mVgTlz73ieZ5W']", visitDay);
        StartTime = startTime(startTime);
        logger.info("Start Time = " + startTime);
        addStartTime().sendKeys(StartTime);
        addEndTime().sendKeys(endTime);
        logger.info("End Time = " + endTime);
        visitsNumber(numVisits);
        waitUntilElementExists(submit());
        addTimeSlotSubmit().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> displayingPopup = driver.findElements(By.xpath("//div/span[text()='Review Previously Deleted Time Slots']"));
        List<WebElement> duplicateTimeSlot = driver.findElements(By.xpath("//span[text()='Cannot create a duplicate time slot']"));
        if (displayingPopup.size() == 1) {
            driver.findElement(By.id("ignore-time-slots")).click();
            button("Add regular hours").click();
            waitUntilPageFinishLoading();
            waitForUITransition();
        } else if (duplicateTimeSlot.size() == 1) {
            addnewTimeSlot(day, startTime, endTime, numVisits, "1");
        }
    }

    public void verifyAvailabilityslotColorInException(String Date, String time) {
        String date = selectCurrentDate(Date);
        WebElement slot = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody//tr/td/div//button[text()='" + StartTime + "']"));
        jsClick(slot);
        waitForUITransition();
        Assert.assertTrue("Slot color is not changed to grey", driver.findElement(By.xpath("//div/span[text()='" + date + "']/ancestor::thead/following-sibling::tbody/tr/td/div/button[@class='ui small button _1fEvzCLMxf84BOPZhb45Zu _3BM-cRueGbAuIRynEvoohd']/parent::div/button[text()='" + StartTime + "']")).isDisplayed());

    }

    public void verifyHashLinesInBlockedDate(String Date, String color) {
        String date = selectCurrentDate(Date);
        String actualColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']")).getCssValue("background-color");
        Assert.assertTrue("HashLines are not displayed in the blocked date", actualColor.equals(color));
    }

    public void verifyHashLinesInMaxAppointmentsMetDate(String Date, String color) {
        String date = selectCurrentDate(Date);
        String actualColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']")).getCssValue("background-color");
        Assert.assertTrue("HashLines are not displayed in the blocked date", actualColor.equals(color));
    }

    public void verifyHashLinesInFullyBookedDate(String Date, String color) {
        String date = selectCurrentDate(Date);
        String actualColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']")).getCssValue("background-color");
        Assert.assertTrue("HashLines are not displayed in the blocked date", actualColor.equals(color));
    }

    public void verifyColorInPartiallyScheduledAvailability(String startTime, String Date, String color) {
        String date = selectCurrentDate(Date);
        String actualColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody//tr/td/div//button[text()='" + StartTime + "']")).getCssValue("background-color");
        Assert.assertTrue("HashLines are not displayed in the blocked date", actualColor.equals(color));
    }

    public void verifyMaximumCollegesInException(String noOfColleges, String Date, String time) {
        String date = selectCurrentDate(Date);
        Assert.assertTrue("Maximum colleges are not present in the Availability slot", driver.findElement(By.xpath("//span[text()='" + date + "']/ancestor::thead/following-sibling::tbody/tr/td/div/button[text()='" + StartTime + "']/preceding-sibling::span[contains(text(),'" + noOfColleges + " Colleges Max')]")).isDisplayed());
    }

    public void verifyUnscheduledAppointmentsInExceptionsSubtab(String time, String Date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("button[title='previous week']"), 1));
        Assert.assertTrue("Previous week button is not displayed", driver.findElement(By.cssSelector("button[title='previous week']")).isDisplayed());
        Assert.assertTrue("Next week button is not displayed", driver.findElement(By.cssSelector("button[title='next week']")).isDisplayed());
        driver.findElement(By.cssSelector("button[class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']")).click();
        setSpecificDateForException(Date);
        String currentDate = selectdateSpecificformat(Date);
        String originalDate = getDateInDateButton();
        String date = selectCurrentDate(Date);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody/tr/td//button[text()='" + StartTime + "']")));
        Assert.assertTrue("date is not equal", currentDate.equals(originalDate));
        Assert.assertTrue("Appointments are not displayed", driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody/tr/td//button[text()='" + StartTime + "']")).isDisplayed());
    }

    public void verifyBlockedDaysInExceptionsSubtab(String reason, String Date) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        availabilityAndSettings().click();
        exception().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("button[title='previous week']"), 1));
        driver.findElement(By.cssSelector("button[class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']")).click();
        setSpecificDateForException(Date);
        String date = selectCurrentDate(Date);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//table//th//div/span[text()='" + date + "']/parent::div/following-sibling::div/span[text()='" + reason + "']"), 1));
        WebElement calendar = driver.findElement(By.cssSelector("div[class='igoATb7tmfPWBM8CX8CkN']"));
        waitUntil(ExpectedConditions.visibilityOf(calendar));

        //verify Blocked Days
        Assert.assertTrue("Blocked Days are not displayed",
                driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/parent::div/following-sibling::div/span[text()='" + reason + "']")).isDisplayed());
    }

    public void verifyPillsColorInException(String startTime, String Date, String slotOriginalColor, String startTimeOriginalColor) {
        String date = selectCurrentDate(Date);
        String SlotActualColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody/tr/td//button[text()='" + StartTime + "']")).getCssValue("background-color");
        Assert.assertTrue("Color is not equal", slotOriginalColor.equals(SlotActualColor));
        String startTimeActualColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']/ancestor::table/tbody/tr/td//button[text()='" + StartTime + "']")).getCssValue("color");
        Assert.assertTrue("StartTime Color is not equal", startTimeOriginalColor.equals(startTimeActualColor));
    }

    public void verifyOutlineColorInException(String Date, String outlineColor) {
        String date = selectCurrentDate(Date);
        String actualOutlineColor = driver.findElement(By.xpath("//table//th//div/span[text()='" + date + "']")).getCssValue("border-color");
        Assert.assertTrue("Outline Color is not equal", outlineColor.equalsIgnoreCase(actualOutlineColor));
    }

    public String selectdateSpecificformat(String addDays) {
        String DATE_FORMAT_NOW = "EEE, MMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void removeCreatedBlockedDaysInBlockedDaysTab() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntilElementExists(availabilityAndSettings());
        availabilityAndSettings().click();
        waitUntilPageFinishLoading();
        blockedDays().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/div/span[text()='Choose Dates']"), 1));
        WebElement dateButton = driver.findElement(By.xpath("//button/div/span[text()='Choose Dates']"));
        moveToElement(dateButton);
        waitUntilPageFinishLoading();
        List<WebElement> removeButton = driver.findElements(By.xpath("//span[text()='Remove']"));
        int removeButtonIteration = removeButton.size();
        while (removeButtonIteration > 0) {
            WebElement remove = driver.findElement(By.xpath("//span[text()='Remove']"));
            jsClick(remove);
            waitUntilPageFinishLoading();
            removeButton = driver.findElements(By.xpath("//span[text()='Remove']"));
            removeButtonIteration = removeButton.size() - 1;
        }
        getDriver().findElement(By.cssSelector("button[class='ui primary button']")).click();

    }

    public int getColumnIdFromTableforManuallyAddVisit(String tableHeaderLocator, String fieldName) {
        int columnId = -1, colCount = 0;
        String presentField = null;

        WebElement webEleTableHeader = driver.findElement(By.xpath(tableHeaderLocator));
        List<WebElement> webEleRows = webEleTableHeader.findElements(By.tagName("tr"));
        logger.info("webEleRows =" + webEleRows.size());
        List<WebElement> webEleColumns = webEleRows.get(0).findElements(By.tagName("th"));
        colCount = webEleColumns.size();
        logger.info("webEleColumns =" + webEleColumns.size());

        if (colCount > 0) {
            for (int colNum = 0; colNum < colCount; colNum++) {
                presentField = webEleColumns.get(colNum).getText().trim().replace("\"", "");
                String day[] = presentField.split("\n");
                String selectDay = day[0];
                if (selectDay.equals(fieldName)) {
                    columnId = colNum;
                    break;
                }
            }
        }

        return columnId;
    }

    public void clickSaveSettingsButtonInCollegeFairsTab() {
        button("SAVE SETTINGS").click();
    }

    public void verifySettingsSavedBannerIsDislayedInCollegeFairsTab() {
        assertEquals("The 'Settings Saved' banner is not displayed", successBanner().getText(), "Great!You've updated College Fair settings.");
    }

    public void verifyButtonsInFairs(String decline, String confirm) {
        waitUntilPageFinishLoading();
        Assert.assertTrue("Decline button is not displayed", button(decline).isDisplayed());
        Assert.assertTrue("Confirm button is not displayed", button(confirm).isDisplayed());
    }

    public void selectOptionInFairs(String option, String fair) {
        waitUntilPageFinishLoading();
        Assert.assertTrue("Fair name is not displayed", driver.findElement(By.xpath("//div/h1[text()='" + FairName + "']")).isDisplayed());
        Assert.assertTrue(option + " button is not displayed", button(option).isDisplayed());
        button(option).click();
        waitUntilPageFinishLoading();
    }

    public void verifyDeclinePopupInFairs(String institution, String msg) {
        Assert.assertTrue("Institution is not displayed", driver.findElement(By.xpath("//strong[text()='" + institution + "']")).isDisplayed());
        Assert.assertTrue("Textbox is not displayed", declineMsgTextBox().isDisplayed());
        declineMsgTextBox().sendKeys(msg);
        Assert.assertTrue("Decline button is not displayed", declineButton().isDisplayed());
        declineButton().click();
        waitForUITransition();
        waitUntilElementExists(close());
        Assert.assertTrue("Close button is not displayed", close().isDisplayed());
        waitUntilElementExists(closeButton());
        closeButton();
        waitUntilPageFinishLoading();
    }

    public void accessAgendaView(String agenda) {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='" + agenda + "']"), 1));
        Assert.assertTrue("Agenda button is not displayed", agendaButton().isDisplayed());
        jsClick(agendaButton());
        waitUntilElementExists(getStartDateInAgenda());
        Assert.assertTrue("Agenda page is not displayed in the calendar", getStartDateInAgenda().isDisplayed());
    }

    public void verifyUserCannotAccessAgendaView() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        List<WebElement> calendar = driver.findElements(By.partialLinkText("Calendar"));
        Assert.assertTrue("Calendar is displayed", calendar.size() == 0);
        Assert.assertTrue("Negative message is not displayed", negativeMessageInRepvisits().isDisplayed());
    }

    public void verifyHEUsersNameLink() {
        getNavigationBar().goToRepVisits();
        //link("Notifications & Tasks").click();
        notificationsAndTasks().click();
        waitUntilPageFinishLoading();
        link("Visit Feedback").click();
        waitUntilPageFinishLoading();
        link("PENDING").click();
        waitUntilPageFinishLoading();
        if (text("No visits to submit feedback, yet").isDisplayed()) {
            logger.info("There is no data present to show under Visit Feedback-->Pending subtab, so verifying default text !!!");
            Assert.assertTrue("No visits to submit feedback, yet text is not displaying", text("No visits to submit feedback, yet").isDisplayed());
            Assert.assertTrue("Previous visits that are available to provide feedback on will... is not displaying", text("Previous visits that are available to provide feedback on will appear here. Providing feedback helps colleges improve the way they connect with students and high schools.").isDisplayed());
        } else {
            if (getUserNameHE().isEnabled()) {
                String userNameColorHE = getUserNameHE().getCssValue("color");
                Assert.assertTrue("HE user name is not showing teal in color.", userNameColorHE.contains("rgba(30, 120, 122, 1)"));
                getUserNameHE().click();
                waitUntilPageFinishLoading();
                String getURLHE = driver.getCurrentUrl();
                Assert.assertTrue("User link is not working.", getURLHE.contains("/community/profile/"));
            }
        }

        navigateToNotificationsAndTasksVisitsFeedBack();

        link("SUBMITTED").click();
        if (text("No visit feedback has been submitted.").isDisplayed()) {
            logger.info("There is no data present to show under Visit Feedback-->Submitted subtab, so verifying default text !!!");
            Assert.assertTrue("No visit feedback has been submitted. text is not displaying", text("No visit feedback has been submitted.").isDisplayed());
        } else {
            WebElement userNameHS = driver.findElement(By.xpath("//span[contains(text(), 'provided feedback about a visit.')]/../a"));
            if (userNameHS.isEnabled()) {
                String userNameColorHS = userNameHS.getCssValue("color");
                Assert.assertTrue("HE user name is not showing teal in color.", userNameColorHS.contains("rgba(30, 120, 122, 1)"));
                userNameHS.click();
                waitUntilPageFinishLoading();
                String getURLHS = driver.getCurrentUrl();
                Assert.assertTrue("User link is not working.", getURLHS.contains("/community/profile/"));
            }
        }
    }

    public void openFairDetailsWithGeneratedDate() {
        String startTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.time;
        String getTime[] = startTime.split(":");
        if (getTime[0].startsWith("0")) {
            startTime = getTime[0].replace("0", "") + ":" + getTime[1];
        }
        formattedDate = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.generatedDate + ","
                + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.generatedDateDayOfWeek + ","
                + startTime.replaceFirst(" ", "");
        openVisit(formattedDate);
    }

    public void openVisit(String date) {
        waitUntilPageFinishLoading();
        driver.get(driver.getCurrentUrl());
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(exportButtonLocator), 1));
        try {
            jsClick(eventInCell(date.split(",")[2]));
        } catch (Exception e) {
            List<WebElement> showMoreLinksList = driver.findElements(By.xpath(showMoreLinkLocator(date.split(",")[1].split(" ")[1])));
            if (showMoreLinksList.size() > 0) {
                for (WebElement showMoreLinkInRow : showMoreLinksList) {
                    try {
                        showMoreLinkInRow.sendKeys(Keys.RETURN);
                        eventInOverlay(date.split(",")[2].replaceFirst("0", "")).click();
                        break;
                    } catch (WebDriverException f) {
                    }
                }
            }
        }
    }

    public void cancelOpenVisit() {
        moveToElement(getCancelThisVisitButon());
        jsClick(getCancelThisVisitButon());
        waitUntilPageFinishLoading();
        selectCancel();
    }

    /*locators*/

    private WebElement getUserNameHE() {
        return driver.findElement(By.xpath("//span[contains(text(), 'has asked for feedback on their recent visit.')]/..//a"));
    }

    private WebElement primaryContact() {
        return getDriver().findElement(By.cssSelector("div[name='primaryContact'"));
    }

    private WebElement primaryContactName(String primaryContactName) {
        return getDriver().findElement(By.xpath("//span[@class='text'][contains(text(), '" + primaryContactName + "')]"));
    }

    private WebElement getAddAttendeesButton() {
        return getDriver().findElement(By.id("next-action-add"));
    }

    private WebElement currentDateInCalendar() {
        WebElement day = driver.findElement(By.xpath("//button[@title='Today']"));
        waitUntilElementExists(day);
        return day;
    }

    private WebElement calendarappointmentsInNewScheduleVisitPage() {
        WebElement calendar = driver.findElement(By.cssSelector("form[id='add-calendar-appointment']"));
        waitUntilElementExists(calendar);
        return calendar;
    }

    private WebElement previousWeekInNewScheduleVisitPage() {
        WebElement button = driver.findElement(By.cssSelector("button[aria-label='Previous week']"));
        return button;
    }

    private WebElement monthInReScheduleVisitPage() {

        WebElement month = driver.findElement(By.xpath("//button[@title='Month']"));
        return month;
    }

    private WebElement addTimeSlotSubmit() {
        WebElement submit = driver.findElement(By.cssSelector("button[class='ui primary button']"));
        return submit;
    }

    private WebElement addStartTime() {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='startTime']"));
        return inputStartTime;
    }

    private WebElement addEndTime() {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='endTime']"));
        return inputStartTime;
    }

    private WebElement declineButton(String user, String university, String time) {
        WebElement button = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + time + "')]/../../following-sibling::div/button/span[text()='Decline']"));
        return button;
    }

    private WebElement userName(String user) {
        WebElement userName = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]"));
        return userName;
    }

    private WebElement university(String University) {
        WebElement university = driver.findElement(By.xpath("//strong[text()='" + University + "']"));
        return university;
    }

    private WebElement confirmButton(String user, String university, String time) {
        WebElement button = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + time + "')]/../../following-sibling::div/button/span[text()='Confirm']"));
        return button;
    }

    private WebElement usernameInDeclinePopup(String user) {
        WebElement userName = driver.findElement(By.xpath("//strong[contains(text(),'" + user + "')]"));
        return userName;
    }

    private WebElement institutionInDeclinePopup(String user, String institution) {
        WebElement Institution = driver.findElement(By.xpath("//strong[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/strong[contains(text(),'" + institution + "')]"));
        return Institution;
    }

    private WebElement getDateInDeclinePopup(String institution, String time) {
        WebElement date = driver.findElement(By.xpath("//strong[text()='" + institution + "']/../following-sibling::div/span[contains(text(),'" + time + "')]/span"));
        return date;
    }

    private WebElement cancellationMessage() {
        WebElement text = driver.findElement(By.name("cancellationMessage"));
        return text;
    }

    private WebElement textBoxInDeclineConfirmation() {
        WebElement text = driver.findElement(By.xpath("//button[@class='ui black basic circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"));
        return text;
    }

    private WebElement declineNotificationMessage(String school) {
        WebElement msg = driver.findElement(By.xpath("//p/span[contains(text(),'" + school + "')]/parent::p[text()='has been declined.']"));
        return msg;
    }

    private WebElement addVisitButton() {
        calendar().click();
        WebElement button = driver.findElement(By.cssSelector("button[class='ui teal button _2vMIFbyypA0b_pLiQmz0hV']"));
        return button;
    }

    private WebElement goToDateButton() {
        WebElement button = driver.findElement(By.xpath(goToDateButtonLocator));
        return button;
    }

    private WebElement goToNewDateButton() {
        WebElement button = driver.findElement(By.xpath(goToNewDateButtonLocator));
        return button;
    }

    private String goToDateButtonLocator = "//button/span/span[text()='Go to date']";

    private String goToNewDateButtonLocator = "//button/span/span[text()='Select Date']";

    private WebElement attendeeTextBox() {
        WebElement id = driver.findElement(By.id("calendar-search-reps"));
        return id;
    }

    private WebElement selectAttendeeOrInstitution(String AttendeeOrInstitution) {
        WebElement attendee = driver.findElement(By.xpath("//div[text()='" + AttendeeOrInstitution + "']"));
        return attendee;

    }

    private WebElement eventLocationTextBox() {
        WebElement id = driver.findElement(By.xpath("//input[@aria-label='Event Location']"));
        return id;
    }

    private WebElement firstNameTextBox() {
        WebElement text = driver.findElement(By.id("add-rep-first-name"));
        return text;
    }

    private WebElement lastNameTextBox() {
        WebElement text = driver.findElement(By.id("add-rep-last-name"));
        return text;
    }

    private WebElement emailTextBox() {
        WebElement text = driver.findElement(By.id("add-rep-email"));
        return text;
    }

    private WebElement phoneNoTextBox() {
        WebElement text = driver.findElement(By.id("add-rep-phone"));
        return text;
    }

    private WebElement positionTextBox() {
        WebElement text = driver.findElement(By.id("add-rep-position"));
        return text;
    }

    private WebElement institutionTextBox() {
        WebElement text = driver.findElement(By.id("add-rep-institution"));
        return text;
    }

    private WebElement confirmationMessageCreatedVisit(String message) {
        String successMsg[] = message.split(",");
//        WebElement msg=driver.findElement(By.xpath("//span[text()='"+successMsg[0]+"']/following-sibling::span[text()='"+successMsg[1]+"']"));
        WebElement msg = driver.findElement(By.xpath("//div[text()='" + successMsg[0] + "']"));
        return msg;
    }

    private WebElement addcontactManually() {
        WebElement link = driver.findElement(By.xpath("//span[text()='Not in the list? Add them manually']"));
        return link;
    }

    public WebElement visitsNumber() {
        WebElement inputStartTime = driver.findElement(By.cssSelector("input[name='numVisits']"));
        return inputStartTime;
    }

    public String startTime(String time) {
        String startTime[] = time.split(":");
        String randomNo = randomNumberGenerator();
        logger.info("randomNo = " + randomNo);
        String finalTime;
        String meridiem = "";
        char getMeridiem[] = startTime[1].toCharArray();
        int i = getMeridiem.length - 1;
        while (i > getMeridiem.length - 3) {
            meridiem = meridiem + "" + getMeridiem[i];
            i--;
        }
        String meridiemValue = new StringBuffer(meridiem).reverse().toString();
        finalTime = startTime[0] + ":" + randomNo + meridiemValue;
        logger.info("Time = " + time);
        return finalTime;
    }

    public String randomizeFairName(String fair) {
        String randomNo = Integer.toString(new Random().nextInt(9999));
        logger.info("random suffix = " + randomNo + "\n");
        String FairName = fair + "" + randomNo;
        logger.info("New FairName = " + FairName + "\n");
        return FairName;
    }

    public String randomNumberGenerator() {
        Random random = new Random();
        int n = random.nextInt(49) + 10;
        String time = Integer.toString(n);
        logger.info("random = " + time);
        return time;
    }

    public void addSchoolUserManually() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(linkToAddSchoolUser()));
        jsClick(getDriver().findElement(linkToAddSchoolUser()));
        waitUntilPageFinishLoading();
    }

    public void addDataToAddAttendeeManually(DataTable AttendeeDetails) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(attendeeFirstName()));
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

    public void clickAddAttendeetovisit() {
        addSchoolAttendees().click();
    }

    public String getSpecificDateForCalendar(String addDays) {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getDisplayingDateInCalendarAgenda(String addDays) {
        String DATE_FORMAT_NOW = "MM/d/yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifyYourNotificationTab(String yourNotification, String user) {
        switch (user) {
            case "Naviance":
                waitUntilPageFinishLoading();
                getNavigationBar().goToRepVisits();
                waitUntilPageFinishLoading();
                userDropdown().click();
                List<WebElement> accountSettings = driver.findElements(By.xpath("//span[text()='Account Settings']"));
                Assert.assertTrue("Account Settings is displayed", accountSettings.size() == 0);
                userDropdown().click();
                break;
            case "non-Naviance":
                waitUntilPageFinishLoading();
                getNavigationBar().goToRepVisits();
                waitUntilPageFinishLoading();
                userDropdown().click();
                accountSettings().click();
                waitUntilPageFinishLoading();
                List<WebElement> yourNotificationTab = driver.findElements(By.xpath("//a/span[text()='" + yourNotification + "']"));
                Assert.assertTrue("Your Notification tab is displayed", yourNotificationTab.size() == 0);
                break;
            default:
                Assert.fail("Invalid user");
                break;
        }
    }

    //The below method is to check the functionality of Visit Feedback-->Pending & Submitted subtab like when there is data present in both subtabs & when both are empty accordingly.
    public void verifyVisitFeedbackForHSUser() {
        getNavigationBar().goToRepVisits();
        notificationsAndTasks().click();
        //link("Notifications & Tasks").click();
        waitUntilPageFinishLoading();
        visitFeedback().click();
        waitUntilPageFinishLoading();

        Assert.assertTrue("Pending subtab is not displaying.", link("PENDING").isDisplayed());
        Assert.assertTrue("Submitted subtab is not displaying.", link("SUBMITTED").isDisplayed());
        Assert.assertTrue("Visit Feedback text is not displaying.", getDriver().findElement(By.xpath("//div[@class='ui header']/span[text()='Visit Feedback']")).isDisplayed());
        Assert.assertTrue("The following institutions have asked for feedback on their visit. text is not displaying.", text("The following institutions have asked for feedback on their visit.").isDisplayed());
        Assert.assertTrue("Take the HS user to the 'Pending' sub tab by default is clicked not working", driver.findElement(By.xpath("//a[@class='menu-link active']/span[text()='Pending']")).isDisplayed());
    }

    //The below method will verify the left panel tag sequence for Naviance HS Users
    public void verifyVisitFeedbackTagSequenceForNavianceHS() {
        getNavigationBar().goToRepVisits();
        notificationsAndTasks().click();
        //link("Notifications & Tasks").click();
        waitUntilPageFinishLoading();
        visitFeedback().click();
        waitUntilPageFinishLoading();

        WebElement leftPanel = text(By.xpath("//ul[@class='ui vertical third menu']"));
        List<WebElement> leftPanelItems = leftPanel.findElements(By.tagName("a"));
        String expLeftPanelItems[] = {"Requests", "Activity", "Naviance Sync", "Visit Feedback"};
        int i = 0;
        for (WebElement element : leftPanelItems) {
            String elementText = element.getText();
            Assert.assertTrue("RepVisits --> Notification & Tasks, left panel items are not in proper sequence.", elementText.equalsIgnoreCase(expLeftPanelItems[i]));
            i++;
        }
    }

    //The below method will verify the left panel tag sequence for Non-Naviance HS Users
    public void verifyVisitFeedbackTagSequenceForNonHS() {
        getNavigationBar().goToRepVisits();
        notificationsAndTasks().click();
        //link("Notifications & Tasks").click();
        waitUntilPageFinishLoading();
        visitFeedback().click();
        waitUntilPageFinishLoading();

        WebElement leftPanel = text(By.xpath("//ul[@class='ui vertical third menu']"));
        List<WebElement> leftPanelItems = leftPanel.findElements(By.tagName("a"));
        String expLeftPanelItems[] = {"Requests", "Activity", "Visit Feedback"};
        int i = 0;
        for (WebElement element : leftPanelItems) {
            String elementText = element.getText();
            Assert.assertTrue("RepVisits --> Notification & Tasks, left panel items are not in proper sequence.", elementText.equalsIgnoreCase(expLeftPanelItems[i]));
            i++;
        }
    }

    // Validates the "Pending" tab for RepVisits Feedback.  Expects to already be on the feedback page.
    public void verifyPendingSubtab() {
        link("PENDING").click();
        waitUntilPageFinishLoading();
        if (text("No visits to submit feedback, yet").isDisplayed()) {
            logger.info("There is no data present to show under Visit Feedback-->Pending subtab, so verifying default text !!!");
            Assert.assertTrue("No visits to submit feedback, yet text is not displaying", text("No visits to submit feedback, yet").isDisplayed());
            Assert.assertTrue("Previous visits that are available to provide feedback on will... is not displaying", text("Previous visits that are available to provide feedback on will appear here. Providing feedback helps colleges improve the way they connect with students and high schools.").isDisplayed());
        } else {
            logger.info("Visit feedback data is present under Visit Feedback-->Pending subtab !!!");
            WebElement userInfoAndTimeBar = driver.findElement(By.xpath("//div[@class='row _35sP911yWhTVQLgBQ_uz0a']"));
            Assert.assertTrue("Date is not displaying in HE side.", userInfoAndTimeBar.findElement(By.xpath(".//div[@class='gmxHq4ccK-TdfOAyiXiky _2Ye8Ial27PcdAQzLSnk_Lu']")).isDisplayed());
            Assert.assertTrue("HE name is not displaying with text has asked for feedback on their recent visit.", getUserNameHE().isDisplayed());
            Assert.assertTrue("Institution information and time is not displaying.", driver.findElements(By.xpath("//div[@class='_2Ye8Ial27PcdAQzLSnk_Lu']")).size() > 1);
            Assert.assertTrue("How was the visit? text is not displaying", text("How was the visit?").isDisplayed());
            Assert.assertTrue("I don't want to submit feedback on this visit text is not displaying", text("I don't want to submit feedback on this visit").isDisplayed());
            checkStarRatingValidation();
        }
    }

    private void deleteDuplicateSlot(String startTime) {
        while (duplicateSlot(startTime).size() > 0) {
            jsClick(clickDuplicateSlot(startTime));
            removeButton().click();
            waitForUITransition();
        }
    }

    private void checkStarRatingValidation() {
        link("PENDING").click();
        waitUntilPageFinishLoading();
        if (text("How was the visit?").isDisplayed()) {
            if (driver.findElements(By.xpath("//div[@class='_3eL2jveDiva_TtJk49-Jdt']")).size() == 25) {
                Assert.assertTrue("Display up to 25 visits to rate at a time, and then display 'View More' pagination is not working.", button("Show More").isDisplayed());
            }

            String fullVisitsText = driver.findElement(By.xpath("//div[@class='availability']")).getText();
            Assert.assertTrue("Fair detail is displaying.", !fullVisitsText.contains("Fair"));

            WebElement visitOne = driver.findElement(By.xpath("(//div[@class='_3eL2jveDiva_TtJk49-Jdt'])[1]"));
            visitOne.findElement(By.xpath("//span[contains(text(),'want to submit feedback on this visit')]")).click();
            Assert.assertTrue("Institute avator is not displaying.", visitOne.findElement(By.tagName("img")).isDisplayed());
            Assert.assertTrue("Are you sure you do not want to submit feedback on this visit? text is not displaying.", text("Are you sure you do not want to submit feedback on this visit?").isDisplayed());
            Assert.assertTrue("CONFIRM button is not displaying.", button("CONFIRM").isDisplayed());
            Assert.assertTrue("Cancel button is not displaying.", button("Cancel").isDisplayed());

            button("Cancel").click();
            Assert.assertTrue("I don't want to submit feedback on this visit text is not displaying", visitOne.findElement(By.xpath("//span[contains(text(),'want to submit feedback on this visit')]")).isDisplayed());

            Assert.assertTrue("Empty One Star is not displaying.", visitOne.findElement(By.xpath(".//i[@aria-posinset='1'][@aria-checked='false']")).isDisplayed());
            Assert.assertTrue("Empty Two Star is not displaying.", visitOne.findElement(By.xpath(".//i[@aria-posinset='2'][@aria-checked='false']")).isDisplayed());
            Assert.assertTrue("Empty Third Star is not displaying.", visitOne.findElement(By.xpath(".//i[@aria-posinset='3'][@aria-checked='false']")).isDisplayed());
            Assert.assertTrue("Empty Fourth Star is not displaying.", visitOne.findElement(By.xpath(".//i[@aria-posinset='4'][@aria-checked='false']")).isDisplayed());
            Assert.assertTrue("Empty Fifth Star is not displaying.", visitOne.findElement(By.xpath(".//i[@aria-posinset='5'][@aria-checked='false']")).isDisplayed());

            visitOne.findElement(By.xpath(".//i[@aria-posinset='5']")).click();
            Assert.assertTrue("What was great about the visit? text is not displaying.", text("What was great about the visit?").isDisplayed());
            String fullVisitText = visitOne.getText();
            visitOne.findElement(By.xpath(".//i[@aria-posinset='4']")).click();

            Assert.assertTrue("What could be improved? * text is not displaying", text("What could be improved?").isDisplayed());
            Assert.assertTrue("How was the visit? text is not displaying", visitOne.findElement(By.xpath("//span[text()='How was the visit?']")).isDisplayed());
            Assert.assertTrue("Professionalism is not displaying", button("Professionalism").isDisplayed());
            Assert.assertTrue("Knowledge is not displaying", button("Knowledge").isDisplayed());
            Assert.assertTrue("Materials is not displaying", button("Materials").isDisplayed());
            Assert.assertTrue("Other is not displaying", button("Other").isDisplayed());
            Assert.assertTrue("Add a comment is not displaying", text("Add a comment").isDisplayed());
            Assert.assertTrue("Optional Comments place holder text is not displaying.", driver.findElement(By.xpath("//div[@class='field _38acLFlWVzR-FmmFmaZg_G']/textarea")).getAttribute("placeholder").equalsIgnoreCase("Optional Comments"));

            Assert.assertTrue("500 characters left text is not displaying.", text("500 characters left").isDisplayed());
            driver.findElement(By.xpath("//div[@class='field _38acLFlWVzR-FmmFmaZg_G']/textarea")).sendKeys("test");
            Assert.assertTrue("While writing comment characters are not decreasing.", text("496 characters left").isDisplayed());

            Assert.assertTrue("Submit feedback anonymously text is not displaying.", text("Submit feedback anonymously").isDisplayed());
            Assert.assertTrue("Submit feedback anonymously checkbox is checked.", !driver.findElement(By.id("is-anonymous-checkbox")).isSelected());
            Assert.assertTrue("Your name will be included with your feedback unless you choose to submit it anonymously. text is not displaying.", text("Your name will be included with your feedback unless you choose to submit it anonymously.").isDisplayed());
            Assert.assertTrue("CANCEL button is not displaying.", button("CANCEL").isDisplayed());
            Assert.assertTrue("SEND FEEDBACK button is not disabled", !button("SEND FEEDBACK").isEnabled());

            button("CANCEL").click();
            fullVisitText = visitOne.getText();
            Assert.assertTrue("What could be improved? text is still displaying.", !fullVisitText.contains("What could be improved?"));
            visitOne.findElement(By.xpath(".//i[@aria-posinset='4']")).click();
            fullVisitText = visitOne.getText();

            button("Professionalism").click();

            Assert.assertTrue("SEND FEEDBACK button is disabled", button("SEND FEEDBACK").isEnabled());
            button("SEND FEEDBACK").click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Feedback sent! text is not displaying", text("Feedback sent!").isDisplayed());

            fullVisitText = visitOne.getText();
            Assert.assertTrue("What could be improved? * text is displaying", !fullVisitText.contains("What could be improved?"));
            Assert.assertTrue("How was the visit? text is displaying", !fullVisitText.contains("How was the visit?"));
            Assert.assertTrue("Add a comment is displaying", !fullVisitText.contains("Add a comment"));
            driver.navigate().refresh();

            //visitOne = driver.findElement(By.xpath("(//div[@class='_3eL2jveDiva_TtJk49-Jdt'])[1]"));
            //fullVisitText = visitOne.getText();
            Assert.assertTrue("Feedback sent! text is still displaying", !text("Feedback sent!").isDisplayed());

        }
    }

    public void submitAnonymouslyFeedback() {
        link("PENDING").click();
        if (text("How was the visit?").isDisplayed()) {
            WebElement visitPending1 = driver.findElement(By.xpath("(//div[@class='_3eL2jveDiva_TtJk49-Jdt'])[1]"));
            visitPending1.findElement(By.xpath(".//i[@aria-posinset='2']")).click();
            button("Professionalism").click();
            text(By.id("is-anonymous-checkbox")).click();
            Assert.assertTrue("SEND FEEDBACK button is disabled", button("SEND FEEDBACK").isEnabled());
            button("SEND FEEDBACK").click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Feedback sent! text is not displaying", text("Feedback sent!").isDisplayed());
            verifyLockIconForAnonymouslyFeedback();
        }
    }

    private void verifyLockIconForAnonymouslyFeedback() {
        link("SUBMITTED").click();
        WebElement firstSubmittedVisit = text(By.xpath("(//div[@class='_2EkuXNSXnDSal55Gx0CRix'])[1]"));
        Assert.assertTrue("Lock icon is not displaying for Anonymously Feedback in Submitted tab.", firstSubmittedVisit.findElement(By.xpath("//i[@class='lock icon']")).isDisplayed());
    }

    public void verifySubmittedSubtab() {
        link("SUBMITTED").click();
        if (text("No visit feedback has been submitted.").isDisplayed()) {
            logger.info("There is no data present to show under Visit Feedback-->Submitted subtab, so verifying default text !!!");
            Assert.assertTrue("No visit feedback has been submitted. text is not displaying", text("No visit feedback has been submitted.").isDisplayed());
        } else {
            logger.info("Visit feedback data is present under Visit Feedback-->Submitted subtab !!!");
            boolean fiveStar = false, lessThenFiveStar = false;
            Assert.assertTrue("Your Visit Feedback text is not displaying", text("Your Visit Feedback").isDisplayed());
            WebElement HSUserInfoBar = driver.findElement(By.xpath("//div[@class='row _35sP911yWhTVQLgBQ_uz0a']"));
            Assert.assertTrue("Time duration information bar is not displaying.", HSUserInfoBar.findElement(By.xpath(".//div[@class='gmxHq4ccK-TdfOAyiXiky _2Ye8Ial27PcdAQzLSnk_Lu']")).isDisplayed());
            Assert.assertTrue("HS user name is not displaying.", getUserNameHS().isDisplayed());
            Assert.assertTrue("Institution information with time is not displaying.", driver.findElements(By.xpath("//div[@class='_2Ye8Ial27PcdAQzLSnk_Lu']")).size() > 1);

            if (driver.findElements(By.xpath("//div[@class='_2EkuXNSXnDSal55Gx0CRix']")).size() == 25) {
                Assert.assertTrue("Display up to 25 visits at a time, and then display 'View More' pagination is not working.", button("Show More").isDisplayed());
            }

            //if (text(By.xpath("//i[@aria-posinset='5'][@aria-checked='true']")).isDisplayed()){
            if (driver.findElements(By.xpath("//i[@aria-posinset='5'][@aria-checked='true']")).size() > 0) {
                WebElement fiveStarVisit = driver.findElement(By.xpath("//i[@aria-posinset='5'][@aria-checked='true']/../../.."));
                Assert.assertTrue("What Was Great text is not displaying.", fiveStarVisit.findElement(By.xpath(".//span[text()='What Was Great']")).isDisplayed());
                Assert.assertTrue("What Was Great text, options are not displaying.", fiveStarVisit.findElement(By.xpath("//span[text()='Professionalism']")).isDisplayed() || fiveStarVisit.findElement(By.xpath("//span[text()='Knowledge']")).isDisplayed() || fiveStarVisit.findElement(By.xpath("//span[text()='Materials']")).isDisplayed() || fiveStarVisit.findElement(By.xpath("//span[text()='Other']")).isDisplayed());
                fiveStar = true;
            }

            if (driver.findElements(By.xpath("//span[contains(text(), 'What Could Be Improved')]")).size() > 0) {
                WebElement NotFiveStarVisit = driver.findElement(By.xpath(".//span[contains(text(), 'What Could Be Improved')]/../.."));
                Assert.assertTrue("What Could Be Improved text is not displaying.", NotFiveStarVisit.findElement(By.xpath(".//span[text()='What Could Be Improved']")).isDisplayed());
                Assert.assertTrue("What Could Be Improved text, options are not displaying.", NotFiveStarVisit.findElement(By.xpath("//span[text()='Professionalism']")).isDisplayed() || NotFiveStarVisit.findElement(By.xpath("//span[text()='Knowledge']")).isDisplayed() || NotFiveStarVisit.findElement(By.xpath("//span[text()='Materials']")).isDisplayed() || NotFiveStarVisit.findElement(By.xpath("//span[text()='Other']")).isDisplayed());
                lessThenFiveStar = true;
            }
            WebElement visit = driver.findElement(By.xpath("//div[@class='_2EkuXNSXnDSal55Gx0CRix']"));
            Assert.assertTrue("Options(it can be all or any 1,2 or 3) ie Professionalism, Knowledge, Materials, Others is not displaying.", driver.findElement(By.xpath("//span[@class='_36RjaM33LbOxryL43lH6Ds']")).isDisplayed());
            Assert.assertTrue("Comment section is not displaying.", visit.findElement(By.xpath("//span[text()='Comments']")).isDisplayed());
            Assert.assertTrue("Submitted by option verification is not proper.", visit.findElement(By.xpath("//span[contains(text(), \"This feedback was submitted with the author's name and your high school information.\")]")).isDisplayed() || visit.findElement(By.xpath("//span[text()='This feedback was shared anonymously.']")).isDisplayed());
            Assert.assertTrue("HE institution avator is not displaying.", visit.findElement(By.tagName("img")).isDisplayed());
            if (fiveStar && lessThenFiveStar)
                logger.info("Visit with five star and less than five star are validated");
            else if (fiveStar)
                logger.info("Only Visit with five star is validated, since we don't have any visit less than five start in submitted subtab.");
            else if (lessThenFiveStar)
                logger.info("Only Visit with less than five star is validated, since we don't have any visit with five star in submitted subtab.");
        }
    }

    public void createVisit(String daysAheadFromNow, DataTable dataTable) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'Want a custom time? Add it manually']")));
        customTimeLink().click();
        Calendar calendarDate = getDeltaDate(Integer.parseInt(daysAheadFromNow));
        generatedDateDayOfWeek = getDayOfWeek(calendarDate) + " " + getDay(calendarDate);
        selectDateButton().click();
        pickDateInDatePicker(calendarDate);
        List<List<String>> details = dataTable.asLists(String.class);

        for (List<String> row : details) {
            switch (row.get(0)) {
                case "Start Time":
                    manualstartTime().sendKeys(row.get(1));
                    time = row.get(1).toUpperCase();
                    break;
                case "End Time":
                    manualEndTime().sendKeys(row.get(1));
                    break;
                case "Representative":
                    findRepresentativeTextBox().sendKeys(row.get(1));
                    waitUntilPageFinishLoading();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("div.ui.active.visible.category.focus.fluid.loading.search"), 0));
                    representativeOption(row.get(1)).click();
                    break;
            }
        }
        generatedDate = dateButtonText().getText().split(",")[1].trim().split(" ")[0];
        findRepresentativeTextBox().sendKeys(Keys.PAGE_DOWN);
        scheduleVisitAddVisitButton().click();
        waitForUITransition();
        if (errorMessage().size() == 1) {
            getDriver().navigate().refresh();
            waitUntilPageFinishLoading();
            navigateToRVCalendar();
            buttonAddVisit();
            createVisit(daysAheadFromNow, dataTable);
        }
    }

    private void verifyAppointmentInCalendar(String university, String time, String date, String option) {
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() == 1) {
            verifyAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    verifyAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        verifyAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                verifyAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public String getDay(String addDays) {
        String DATE_FORMAT_NOW = "EE";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDay = sdf.format(cal.getTime());
        return currentDay;
    }

    public void verifyAppointmentInCalendar(String startTime, String university) {
        Assert.assertTrue("Appointment is not displayed", calendarAppointment(startTime, university).isDisplayed());
    }
  /**
   *
   *  Submitting required values in Naviance settings page
   *
   *  Accept the data table values
   * @param dataTable - valid sections : option , NumVisits , Location , studentsCount , DeadlineValue , Notes , deadlineOption
   *                  Acceptable values : Ex: Automatically publish confirmed visits., 3 ,etc..
   */
   public void submitTheValuesInNavianceSettings(DataTable dataTable) {
       getNavigationBar().goToRepVisits();
       waitUntilPageFinishLoading();
       availabilityAndSettings().click();
       waitUntilPageFinishLoading();
       navianceSettings().click();
       waitUntilPageFinishLoading();
       waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Naviance Sync Settings']")));
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for(String key:data.keySet()){
            switch(key){
                case "option":
                    getDriver().findElement(By.xpath("//div[@class='grouped fields']/div/div/label[text()[contains(.,'"+data.get(key)+"')]]")).click();
                    break;
                case "NumVisits":
                    maxNoOfVisits().clear();
                    maxNoOfVisits().sendKeys(data.get(key));
                    break;
                case "Location":
                    locationTextBoxInNaviance().sendKeys(Keys.PAGE_DOWN);
                    locationTextBoxInNaviance().clear();
                    locationTextBoxInNaviance().sendKeys(data.get(key));
                    break;
                case "studentsCount":
                    maxNoOfStudents().clear();
                    maxNoOfStudents().sendKeys(data.get(key));
                    break;
                case "DeadlineValue":
                    visitDeadLine().clear();
                    visitDeadLine().sendKeys(data.get(key));
                    break;
                case "Notes":
                    studentNotes().clear();
                    studentNotes().sendKeys(data.get(key));
                    break;
                case "deadlineOption":
                    getDriver().findElement(By.id("rsvpKind")).click();
                    getDriver().findElement(By.xpath("//span[text()='"+data.get(key)+"']")).click();
                    break;
                default:
                    Assert.fail("Invalid option");
                    break;
            }
        }
        saveChangesNaviance().click();
        waitUntilPageFinishLoading();
    }
    /**
     *
     *  Verifying visit details in Naviance page after reschedule the visit
     *
     *  Accept the data table values
     * @param Date - Accepting date as string value and converting to Date value
     *
     * @param dataTable - Getting values using data table
     *                   Acceptable values are : college ,attendee etc..
     */
    public void verifyNavianceCollegeVisitPageAfterReschedule(String Date,DataTable dataTable){
        int getDate = Integer.parseInt(Date);
        String date=getSpecificDate(getDate,"EEEE MMMM d, yyyy");
        List<String> visitDetails=dataTable.asList(String.class);
        for(String detail:visitDetails) {
            Assert.assertTrue(detail+"is not displayed",driver.findElement(By.xpath("//td[contains(text(),'"+detail+"')]")).isDisplayed());
        }
        Assert.assertTrue("Date is not displayed",driver.findElement(By.xpath("//td[contains(text(),'"+date+"')]")).isDisplayed());
        Assert.assertTrue("Start Time is not displayed",driver.findElement(By.xpath("//td[contains(text(),'"+getVisitStartTimeforReschedule()+"')]")).isDisplayed());
    }
    /**
     *
     *  Verifying visit details in Naviance page before reschedule the visit
     *
     *  Accept the data table values
     * @param Date - Accepting date as string value and converting to Date value
     *
     * @param dataTable - Getting values using data table
     *                   Acceptable values are : college ,attendee etc..
     */
    public void verifyNavianceCollegeVisitPage(String Date,DataTable dataTable) {
        List<String> data = dataTable.asList(String.class);
        int getDate = Integer.parseInt(Date);
        String date=getSpecificDate(getDate,"EEEE MMMM d, yyyy");
        for(String visitDetails:data) {
            Assert.assertTrue(visitDetails+"is not displayed",driver.findElement(By.xpath("//td[contains(text(),'"+visitDetails+"')]")).isDisplayed());
        }
        Assert.assertTrue("Date is not displayed",driver.findElement(By.xpath("//td[contains(text(),'"+date+"')]")).isDisplayed());
        Assert.assertTrue("Start Time is not displayed",driver.findElement(By.xpath("//td[contains(text(),'"+getVisitStartTime()+"')]")).isDisplayed());
    }
/**
 * select view option for the respective appointment in naviance page after reschedule the appointment
 *
 * @param Date - Accepting date as string value and converting to Date value
 *
 * @param option - View
 *
 * @param Time - Ex : 10:34 AM
 */
    public void selectViewOptionInNavianceCollegeVisitPageAfterReschedule(String option,String Date,String Time){
        String startTime=getVisitStartTimeforReschedule();
        Actions action = new Actions(driver);
        action.moveToElement(collegeOptionInNaviancePage()).build().perform();
        visitsInNavianceColleges().click();
        int getDate = Integer.parseInt(Date);
        String date=getSpecificDate(getDate,"EEE MMMM d, yyyy");
        WebElement select=driver.findElement(By.xpath("//td[text()='"+date+"']/following-sibling::td[contains(text(),'"+startTime+"')]/following-sibling::td/a[text()='"+option+"']"));
        waitUntilElementExists(select);
        select.click();
        waitUntilPageFinishLoading();
    }
    /**
     * select view option for the respective appointment in naviance page before reschedule the appointment
     *
     * @param Date - Accepting date as string value and converting to Date value
     *
     * @param option - View
     *
     * @param Time - Ex : 10:34 AM
     */
    public void selectViewOptionInNavianceCollegeVisitPage(String option,String Date,String Time) {
        String startTime=getVisitStartTime();
        Actions action = new Actions(driver);
        action.moveToElement(collegeOptionInNaviancePage()).build().perform();
        visitsInNavianceColleges().click();
        int getDate = Integer.parseInt(Date);
        String date=getSpecificDate(getDate,"EEE MMMM d, yyyy");
        WebElement select=driver.findElement(By.xpath("//td[text()='"+date+"']/following-sibling::td[contains(text(),'"+startTime+"')]/following-sibling::td/a[text()='"+option+"']"));
        waitUntilElementExists(select);
        select.click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }

    /**
     * verify the appointment in calendar page
     *
     * @param university : Ex:Alpena Community College
     *
     * @param time : Ex :10:58AM
     *
     * @param date : Accepting date as string value and converting to Date value
     *
     * @param option : Ex : scheduled or rescheduled
     */
    public void verifyAppointmentCalendarPage(String university, String time, String date, String option){
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
        collegeFairTextBoxInCalendarPage().click();
        pendingCheckBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() == 1) {
            verifyAppointment(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    verifyAppointment(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        verifyAppointment(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                verifyAppointment(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void verifyAppointment(String startTime, String university) {
        Assert.assertTrue("Appointment is not displayed", calendarAppointment(startTime, university).isDisplayed());
    }

    public void hsCreateCollegeFair(String collegeFairName, String date, String startTime, String endTime, String RSVPDate, String cost, String maxNumberofColleges, String numberofStudentsExpected, String buttonToClick) {
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement fairs = collegeFairs();
        waitUntilElementExists(fairs);
        collegeFairs().click();
        waitUntilPageFinishLoading();
        addCollegeButton().click();
        FairName = randomizeFairName(collegeFairName);

        if (!collegeFairName.equals("")) {
            Assert.assertTrue("College Fair TextBox is not displayed", collegeFairsNameTextBox().isDisplayed());
            collegeFairsNameTextBox().sendKeys(FairName);
        }
        if (!date.equals("")) {
            Assert.assertTrue("Date Textbox are not displayed", collegeFairsDateTextBox().isDisplayed());
            fairsDatePicker().click();
            setDateForFair(date);
        }
        if (!startTime.equals("")) {
            Assert.assertTrue("Start Time TextBox is not displayed", fairsStartTimeTextBox().isDisplayed());
            fairsStartTimeTextBox().sendKeys(startTime);
        }
        if (!endTime.equals("")) {
            Assert.assertTrue("End Time TextBox is not displayed", fairsEndTimeTextBox().isDisplayed());
            fairsEndTimeTextBox().sendKeys(endTime);
        }
        if (!RSVPDate.equals("")) {
            Assert.assertTrue("RSVP Deadline TextBox is not displayed", fairsRSVPDateTextBox().isDisplayed());
            fairsRSVPDatePicker().click();
            setDateForFair(RSVPDate);
        }
        if (!cost.equals("")) {
            Assert.assertTrue("Cost TextBox is not displayed", fairsCostTextBox().isDisplayed());
            fairsCostTextBox().sendKeys(cost);
        }
        if (!maxNumberofColleges.equals("")) {
            Assert.assertTrue("'Max Number of Colleges' TextBox is not displayed", fairsMaxCollegeTextBox().isDisplayed());
            fairsMaxCollegeTextBox().sendKeys(maxNumberofColleges);
        }
        if (!numberofStudentsExpected.equals("")) {
            Assert.assertTrue("'Number of Students Expected' TextBox is not displayed", fairsExpectedStudentsTextBox().isDisplayed());
            fairsExpectedStudentsTextBox().sendKeys(numberofStudentsExpected);
        }
        // Send a JavaScript click to this control because it will be off-screen.
        jsClick(fairsAutomaticRequestConfirmation());
        fairsExpectedStudentsTextBox().sendKeys(Keys.PAGE_DOWN);
        eMailMessageTextBox().sendKeys(Keys.PAGE_DOWN);

        if (!buttonToClick.equals("")) {
            if (buttonToClick.equals("Cancel This College Fair")) {
                Assert.assertTrue("'Cancel This College Fair' Button is not displayed", cancelFairsButton().isDisplayed());
                cancelFairsButton().click();
                waitUntilPageFinishLoading();
            } else if (buttonToClick.equals("Save")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span[text()='Save']")));
                Assert.assertTrue("'Save' Button is not displayed", fairsSaveButton().isDisplayed());
                jsClick(fairsSaveButton());
                waitUntilPageFinishLoading();
            } else {
                Assert.fail("The option for the button to click =" + buttonToClick + " is not a valid one");
            }
        }
    }

    public void setDateForFair(String addDays) {
        int date = Integer.parseInt(addDays);
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, date);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void setStartDateAndEndDateInCalendarAgendaView(String numberOfDaysFromNowTillStartDate, String numberOfDaysFromNowTillEndDate) {
        int date = Integer.parseInt(numberOfDaysFromNowTillEndDate);
        agendaViewEndDatePicker().click();
        setSpecificDate(date);
        agendaViewStartDatePicker().click();
        setSpecificDate(date);
    }

    // Locators

    private WebElement getUserNameHS() {
        return driver.findElement(By.xpath("//span[contains(text(), 'provided feedback about a visit.')]/..//a"));
    }

    private WebElement addSchoolAttendees() {
        return getDriver().findElement(By.cssSelector("button.ui.primary.right.floated.button:not(.small):not(.basic)"));
    }

    private By linkToAddSchoolUser() {
        return By.cssSelector("a.KKyfdym6DkswwZqeWN7Ck");
    }

    private WebElement attendeeFirstNameTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-first-name"));
    }

    private WebElement attendeeLastNameTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-last-name"));
    }

    private WebElement attendeeEmailTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-email"));
    }

    private WebElement attendeePhoneTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-phone"));
    }

    private WebElement attendeePositionTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-position"));
    }

    private WebElement attendeeInstitutionTextBox() {
        return getDriver().findElement(By.cssSelector("input#add-rep-institution"));
    }

    private WebElement attendeeCollege() {
        return getDriver().findElement(By.xpath(".//*[@id='undefined-results']/div[1]/div/div[1]"));
    }

    private WebElement getRepVisitsBtn() {
        return driver.findElement(By.id("js-main-nav-rep-visits-menu-link"));
    }

    private WebElement userDropdown() {
        WebElement dropdown = driver.findElement(By.id("user-dropdown"));
        return dropdown;
    }

    private WebElement getInstitutionProfileBtn() {
        return driver.findElement(By.xpath("//span[text()='Institution Profile']"));
    }

    private WebElement getYourProfileBtn() {
        return driver.findElement(By.xpath("//span[text()='Your Profile']"));
    }

    private WebElement signOut() {
        WebElement signOut = driver.findElement(By.xpath("//span[text()='Sign Out']"));
        return signOut;
    }

    private WebElement loggedInText() {
        WebElement text = driver.findElement(By.xpath("//span[contains(text(),'Logged in as')]"));
        return text;
    }

    private WebElement frameInCommunity() {
        driver.switchTo().defaultContent();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("iframe[title=Community]"), 1));
        WebElement frame = driver.findElement(By.xpath("//iframe[@title='Community']"));
        return frame;
    }

    private WebElement userProfilePage() {
        WebElement profile = driver.findElement(By.xpath("//a[text()='Profile']"));
        return profile;
    }

    private WebElement institutionProfilePage() {
        WebElement institution = driver.findElement(By.xpath("//a[@class='active' and text()='Institution']"));
        return institution;
    }

    private WebElement accountSettings(String accountSettings) {
        WebElement label = driver.findElement(By.xpath("//span[text()='" + accountSettings + "']"));
        return label;
    }

    private WebElement currentPasswordInput() {
        WebElement currentPassword = driver.findElement(By.id("current-password-input"));
        return currentPassword;
    }

    private WebElement newPasswordInput() {
        WebElement newPassword = driver.findElement(By.id("new-password-input"));
        return newPassword;
    }

    private WebElement confirmPasswordInput() {
        WebElement confirmPassword = driver.findElement(By.id("confirm-password-input"));
        return confirmPassword;
    }

    private WebElement passwordErrorMessage() {
        WebElement msg = driver.findElement(By.xpath("//span[contains(text(),'The new password failed to satisfy security requirements')]"));
        return msg;
    }

    private WebElement firstNameTextbox() {
        WebElement text = driver.findElement(By.id("user-form-first-name"));
        return text;
    }

    private WebElement lastNameTextbox() {
        WebElement text = driver.findElement(By.id("user-form-last-name"));
        return text;
    }

    private WebElement eMailTextbox() {
        WebElement text = driver.findElement(By.id("user-form-email"));
        return text;
    }

    private WebElement availabilityAndSettings() {
        overview().click();
        waitUntilElementExists(driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Availability & Settings']")));
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Availability & Settings']"));
    }

    private WebElement collegeFairs() {
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='College Fairs']"));
    }

    private WebElement collegeFairsSettings() {
        return driver.findElement(By.xpath("//a[@class='ui small right floated button _1V3Ic-4VeK2sTRYUuz8RGZ']/span[text()='SETTINGS']"));
    }

    private WebElement blockedDays() {
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/h2/span[text()='Blocked Days']"));
    }

    private WebElement timeZone() {
        return driver.findElement(By.xpath("//a[@class='menu-link' or @class='menu-link active']/span[text()='Time Zone']"));
    }

    private WebElement messageOptions() {
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Messaging Options']"));
    }

    private WebElement availabilitySettings() {
        return driver.findElement(By.xpath("//h2/span[text()='Availability Settings']"));
    }

    private WebElement notificationAndPrimaryContact() {
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Primary Contact']"));
    }

    public void clickAddCollegeFairButton() {
        addCollegeFair().click();
    }

    public void noteForSchools(String note) {
        Assert.assertTrue("Note For Schools is not displayed", noteDeclaration().getText().contains(note));
    }

    public void closeAddEditFairScreen() {
        waitUntilElementExists(closeFairScreen());
        closeFairScreen().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(closeFairPopup()));
    }

    private WebElement rescheduleButtonInReScheduleVisitPage() {
        WebElement reschedule = driver.findElement(By.xpath("//button/span[text()='Reschedule']"));
        return reschedule;
    }

    private WebElement hsNotesInReScheduleVisitPage() {
        WebElement notes = driver.findElement(By.xpath("//input[@name='hsNotes']"));
        return notes;
    }

    private WebElement reScheduleTextboxInReScheduleVisitPage() {
        WebElement textBox = driver.findElement(By.xpath("//textarea[@id='rescheduleMessage']"));
        return textBox;
    }

    private WebElement noAvailabilityInNewScheduleVisitPage() {
        WebElement avialability = driver.findElement(By.xpath("//table[@class='ui unstackable basic table']//tbody//td/span[text()='No availability this week']"));
        return avialability;
    }

    private WebElement addvVisitManuallyInNewScheduleVisitPage() {
        WebElement addVisit = driver.findElement(By.xpath("//div/span[text()='Want a custom time? Add it manually']"));
        return addVisit;
    }

    private WebElement getVisitsFeedbackBtn() {
        return visitFeedback();
    }

    private WebElement saveChanges() {
        WebElement saveChanges = saveChangesNaviance();
        waitUntilElementExists(saveChanges);
        return saveChanges;
    }

    private WebElement selectDay() {
        WebElement selectDay = driver.findElement(By.xpath("//div[@class='ui button labeled dropdown icon QhYtAi_-mVgTlz73ieZ5W']"));
        waitUntilElementExists(selectDay);
        return selectDay;
    }

    private WebElement submit() {
        WebElement submit = driver.findElement(By.cssSelector("button[class='ui primary button']"));
        waitUntilElementExists(submit);
        return submit;
    }

    private WebElement confirm() {
        WebElement button = button("Confirm");
        waitUntilElementExists(button);
        return button;
    }

    private WebElement close() {
        WebElement button = getDriver().findElement(closeButtonLocator());
        return button;
    }

    private By closeButtonLocator(){
        return By.cssSelector("button[class='ui button']");
    }

    private WebElement goBack() {
        WebElement button = button("No, go back");
        waitUntilElementExists(button);
        return button;
    }

    private String day(String day) {
        String date = selectdate(day);
        String selectDay[] = date.split(",");
        String currentDay = selectDay[0];
        return currentDay;
    }

    private WebElement startOrEndDate() {
        WebElement date = driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']"));
        return date;
    }

    private WebElement addTimeSlot() {
        WebElement add = button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']"));
        return add;
    }

    private WebElement availabilityButton() {
        WebElement pills = driver.findElement(By.cssSelector("button[class='ui small button IHDZQsICrqtWmvEpqi7Nd']"));
        return pills;
    }


    /**
     * Gets the remove slot button
     *
     * @return webelement
     */
    private WebElement getRemoveTimeSlotButton() {
        return button("REMOVE");
    }

    private WebElement instructionsTextBox() {
        return getDriver().findElement(By.cssSelector("#college-fair-instructions"));
    }

    private WebElement getUnpublishButton() {
        return getDriver().findElement(By.xpath("//button[text()[contains(.,'Unpublish')]]"));
    }

    public void moveToElement(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    private WebElement declinePopupMessage() {
        WebElement message = driver.findElement(By.xpath("//span[text()='Are you sure you want to decline?']"));
        return message;
    }

    private WebElement availability() {
        WebElement availability = driver.findElement(By.xpath("//span[text()='Availability']"));
        return availability;
    }

    private WebElement regularWeeklyHours() {
        WebElement regularWeeklyHours = driver.findElement(By.xpath("//span[text()='Regular Weekly Hours']"));
        return regularWeeklyHours;
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
        return getDriver().findElement(By.cssSelector("button[class='ui teal button _2vMIFbyypA0b_pLiQmz0hV']"));
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
     *
     * @return webelement
     */
    private WebElement getRepresentativeLastName() {
        return getDriver().findElement(By.id("add-rep-last-name"));
    }

    /**
     * Gets the representative institution textbox
     *
     * @return webelement
     */
    private WebElement getRepresentativeInstitutionTextBox() {
        return getDriver().findElement(By.id("add-rep-institution"));
    }

    /**
     * Gets the event location textbox
     *
     * @return webelement
     */
    private WebElement getEventLocationTextBox() {
        return textbox(By.cssSelector("input[id='eventLocation']"));
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
        return getDriver().findElement(By.xpath("//span[text()='Cancel This Visit']"));

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

    private WebElement navianceSettings() {
        WebElement navianceSettings = driver.findElement(By.partialLinkText("Naviance Settings"));
        return navianceSettings;
    }

    private WebElement autopublishInNavianceSettings(String option) {
        WebElement publish = driver.findElement(By.xpath("//input[@name='autoPublish']/parent::label[text()='" + option + "']"));
        return publish;
    }

    private WebElement notifyStudents(String option) {
        WebElement notifyStudents = driver.findElement(By.xpath("//input[@name='notifyStudents']/parent::label[text()='" + option + "']"));
        return notifyStudents;
    }

    private WebElement displayDeadlines(String option) {
        WebElement displayDeadlines = driver.findElement(By.xpath("//input[@name='displayDeadline']/parent::label[text()='" + option + "']"));
        return displayDeadlines;
    }

    private WebElement saveSettings() {
        WebElement button = button("Save changes");
        return button;
    }

    private WebElement exception() {
        WebElement link = driver.findElement(By.xpath("//h2/span[text()='Exceptions']"));
        return link;
    }

    private WebElement dateButton() {
        WebElement date = button("Choose a Date");
        waitUntilElementExists(date);
        return date;
    }

    private WebElement calendar() {
        WebElement navbar = driver.findElement(By.xpath("//ul[@class='ui pointing secondary hidden-mobile hidden-tablet FutsUBH7pYEk_L4sKJpZq menu']/li/a/span[text()='Calendar']"));
        return navbar;
    }

    private WebElement calendarHS() {
        WebElement navbar = driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Calendar']"));
        return navbar;
    }

    private WebElement overview() {
        waitUntil(ExpectedConditions.visibilityOf(notificationsAndTasks()));
        notificationsAndTasks().click();
        WebElement navbar = driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Overview']"));
        return navbar;
    }

    private WebElement notificationsAndTasks() {
        waitUntilElementExists(driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Tasks']")));
        WebElement navbar = driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Tasks']"));
        return navbar;
    }

    private WebElement visitFeedback() {
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Visit Feedback']"));
    }


    private WebElement dateButtonInAddvisitButtonPopup() {
        WebElement button = driver.findElement(By.xpath("//button/span/span[text()='Go to date']"));
        return button;
    }

    private String getVisitStartTimeforcalendar() {
        String[] time = StartTime.split("am");
        String startTime = time[0] + "AM";
        return startTime;
    }

    private WebElement collegeFairTextBoxInCalendarPage() {
        WebElement check = driver.findElement(By.xpath("//div/label[text()='College Fair - Confirmed']"));
        return check;
    }

    private WebElement pendingCheckBoxInCalendarPage() {
        WebElement check = driver.findElement(By.xpath("//div/label[text()='Pending']"));
        return check;
    }

    private String month(String date) {
        String month = getSpecificDate(date);
        String selectMonth[] = month.split(" ");
        String currentMonth = selectMonth[0];
        return currentMonth;
    }

    private WebElement currentMonthInCalendarPage() {
        WebElement month = driver.findElement(By.xpath("//div[@class='three wide column']/div[@class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']"));
        return month;
    }

    private WebElement nextMonthButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui icon button _38R7SJgG4fJ86m-eLYYZJw']"));
    }

    private WebElement forwardDayButton() {
        WebElement button = driver.findElement(By.xpath(forwardDayButtonLocator));
        return button;
    }

    private String forwardDayButtonLocator = "//button[@title='Forwards']";

    private WebElement backwardsDayButton() {
        WebElement button = driver.findElement(By.xpath("//button[@title='Backwards']"));
        return button;
    }

    private WebElement eventLocationTextboxInSchedulePopup() {
        WebElement eventLocation = null;
        if (getDriver().findElements(By.xpath("//input[@name='locationWithinSchool']")).size() == 1) {
            eventLocation = getDriver().findElement(By.xpath("//input[@name='locationWithinSchool']"));
        } else if (getDriver().findElements(By.xpath("//input[@name='eventLocation']")).size() == 1) {
            eventLocation = getDriver().findElement(By.xpath("//input[@name='eventLocation']"));
        }
        return eventLocation;
    }

    private WebElement eventLocationNewVisit() {
        WebElement text = driver.findElement(By.xpath("//input[@name='locationWithinSchool']"));
        return text;
    }

    private WebElement addVisitManually() {
        WebElement link = driver.findElement(By.xpath("//span[text()='Want a custom time? Add it manually']"));
        return link;
    }

    private WebElement selectDateButton() {
        WebElement button = driver.findElement(By.cssSelector("button[class$='_3VnqII6ynYglzDU1flY9rw']"));
        return button;
    }

    private WebElement manualStartTime() {
        WebElement text = driver.findElement(By.id("manualStartTime"));
        return text;
    }

    /* private WebElement manualEndTime() {
         WebElement text=driver.findElement(By.id("manualEndTime"));
         return text;
     }*/
    private WebElement addAttendee() {
        WebElement text = driver.findElement(By.id("calendar-search-reps"));
        return text;
    }

    private WebElement attendee(String Attendee) {
        WebElement attendee = driver.findElement(By.xpath("//div[text()='" + Attendee + "']"));
        return attendee;
    }

    private WebElement eventLocation() {
        WebElement location = driver.findElement(By.name("eventLocation"));
        return location;
    }

    private WebElement eventLocationHS() {
        WebElement location = driver.findElement(eventLocationHsLocator());
        return location;
    }

    private By eventLocationHsLocator(){
        By locator = null;
        List<WebElement> eventLocation= getDriver().findElements(By.cssSelector("input[name='locationWithinSchool']"));
        if(eventLocation.size()==1){
            locator = By.cssSelector("input[name='locationWithinSchool']");
        }else {
            locator = By.cssSelector("input[name='eventLocation']");
        }
        return locator;
    }

    private WebElement addVisitButtonInVisitSchedulePopup() {
        WebElement button = driver.findElement(By.xpath(addVisitButtonInVisitSchedulePopupLocator));
        return button;
    }

    private String addVisitButtonInVisitSchedulePopupLocator = "//form[@id='add-calendar-appointment']//button[@class='ui teal right floated button']";

    private WebElement DateButton() {
        WebElement button = driver.findElement(By.cssSelector("button[class$='_3GJIUrSQadO6hk9FZvH28D']"));
        return button;
    }

    private WebElement internalNotesTextBoxInVisitSchedulePopup() {
        WebElement text = driver.findElement(By.xpath("//div/textarea[@name='internalNotes']"));
        return text;
    }

    private WebElement addrepInstitution() {
        WebElement id = driver.findElement(By.id("add-rep-institution"));
        return id;
    }

    private WebElement studentsNotes() {
        WebElement text = driver.findElement(By.xpath("//div/textarea[@id='internalNotes']"));
        return text;
    }

    private WebElement findRepTextbox() {
        WebElement text = driver.findElement(By.id("calendar-search-reps"));
        return text;
    }

    private WebElement exceptionLink() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/span[text()='Exceptions']")));
        WebElement link = driver.findElement(By.xpath("//h2/span[text()='Exceptions']"));
        return link;
    }

    private WebElement dateButtonInException() {
        WebElement button = driver.findElement(By.xpath("//button[@class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']"));
        return button;
    }

    private WebElement gotoDateButtonInVisitSchedulePopup() {
        WebElement button = driver.findElement(By.xpath("//span[contains(text(),'Go to date')]"));
        return button;
    }

    private WebElement textInVisitSchedulePopup() {
        WebElement text = driver.findElement(By.xpath("//span[contains(text(),'schedule new visit')]"));
        return text;
    }

    private WebElement verifyTextInVisitSchedulePopup() {
        WebElement text = driver.findElement(By.xpath("//span[contains(text(),'select an available time slot')]"));
        return text;
    }

    private WebElement eventLocationTextboxInAddVisitSchedulePopup() {
        WebElement locator = null;
        List<WebElement> eventLocation= getDriver().findElements(By.cssSelector("input[name='locationWithinSchool']"));
        if(eventLocation.size()==1){
            locator = getDriver().findElement(By.cssSelector("input[name='locationWithinSchool']"));
        }else {
            locator = getDriver().findElement(By.cssSelector("input[name='eventLocation']"));
        }
        return locator;
    }

    private WebElement verifyAddVisitButtonInVisitSchedulePopup() {
        WebElement button = driver.findElement(By.xpath("//form[@id='add-calendar-appointment']//button[@class='ui teal disabled right floated button']"));
        return button;
    }

    private WebElement availabilityEndtimeTextbox() {
        WebElement textbox = driver.findElement(By.id("availability-end-time"));
        return textbox;
    }

    public void viewFairDetails() {
        waitUntilPageFinishLoading();
        getDriver().findElement(By.xpath("//div[@class='_1743W0qaWdOtlS0jkveD7o'][1]/table/tbody/tr[1]/td/following-sibling::td[4]/a/span")).click();
    }

    public void editFair() {
        waitUntilPageFinishLoading();
        editCFButton().click();
    }

    public void editCFFair() {
        waitUntilPageFinishLoading();
        editCFButton().click();
    }

    private WebElement noteDeclaration() {
        return getDriver().findElement(By.cssSelector("p._2jKMD8r6D3Vkw7TQidWlZ_"));
    }

    private WebElement closeFairScreen() {
        return getDriver().findElement(By.cssSelector("button[class='ui circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"));
    }

    private WebElement editButton() {
        return getDriver().findElement(By.cssSelector("button#edit-college-fair.ui.basic.primary.right.floated.button._2WIBPMrHDvfagooC6zkFpq"));
    }

    private WebElement editCFButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui right floated button _2WIBPMrHDvfagooC6zkFpq']"));
    }

    private WebElement getSearchBox() {
        return getDriver().findElement(By.xpath("//input[@placeholder='Search for a school...']"));
    }

    private WebElement trashIconInException() {
        WebElement trash = driver.findElement(By.xpath("//span/i[@class='trash alternate outline icon _6S_VIt7XKOpy-Mn7y_CJu']"));
        return trash;
    }

    private String getDateInDateButton() {
        String days = driver.findElement(By.xpath("//button[@class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']/div/span")).getText();
        return days;
    }

    public void navigateToRVCalendar() {
        getNavigationBar().goToRepVisits();
        driver.findElement(By.partialLinkText("Calendar"));
    }

    public void buttonAddVisit() {
        waitForUITransition();
        button("add visit").click();

    }

    public void selectRepresentative(String representative) {
        addrepresentativefromlist().clear();
        addrepresentativefromlist().sendKeys(representative);
        waitForUITransition();
        representativeuser().click();
    }

    public void addVisitInternalNotes(String internalNotes) {
        internalNotes().sendKeys(internalNotes);

    }

    public void addVisitHSUser() {
        addVisitFloat().click();
    }

    public void datepicker() {
        datepickerbuton().click();
    }

    public void pickMonthDateforVisit(String monthAndYear, String visitDate) {
        int control = 0;

        while (!monthselector().getText().equals(monthAndYear)) {
            monthRightArrow().click();
        }
        monthday(visitDate.split(" ")[1]).click();

        List<WebElement> dayslist = getDriver().findElements(By.xpath("//th[@class='center aligned middle aligned _39IsE6GwUYRagGhvLF1PMz']/div/span"));
        for (int i = 0; i < 5; i++) {
            //String
            String controle = dayslist.get(i).getText();

            if (dayslist.get(i).getText().equals(visitDate)) {
                control = i + 1;
                break;
            }

        }
        getDriver().findElement(By.cssSelector("table.ui.unstackable.basic.table td:nth-of-type(" + control + ") button")).click();


    }


    //locator for Add Visit
    private WebElement rightArrowNextWeek() {
        return getDriver().findElement(By.xpath("//button[@aria-label='Next week']"));
    }

    private WebElement nextWeekException() {
        return getDriver().findElement(By.xpath("//button[@title='next week']"));
    }

    private WebElement selectFridayVisit() {
        return getDriver().findElement(By.cssSelector("table.ui.unstackable.basic.table td:nth-of-type(4) button"));
    }

    private WebElement addrepresentativefromlist() {
        return getDriver().findElement(By.cssSelector("input#calendar-search-reps"));
    }

    private WebElement representativeuser() {
        return getDriver().findElement(By.xpath("//div[@class='results transition visible']/div/div[2]"));
    }

    private WebElement internalNotes() {
        return getDriver().findElement(By.cssSelector("textarea#internalNotes"));
    }

    private WebElement addVisitHS() {
        return driver.findElement(By.xpath("//span[text()='add visit']"));
    }

    private WebElement addVisitFloat() {
        return driver.findElement(By.xpath("//button[@class='ui teal right floated button']/span[text()='add visit']"));
    }

    private WebElement datepickerbuton() {
        return getDriver().findElement(By.cssSelector("button.ui.tiny.button._3GJIUrSQadO6hk9FZvH28D"));
    }

    private WebElement monthselector() {
        return getDriver().findElement(By.cssSelector("div.DayPicker-Caption"));
    }

    private WebElement monthRightArrow() {
        return getDriver().findElement(By.cssSelector("span[aria-label='Next Month']"));

    }

    private WebElement monthday(String day) {
        return getDriver().findElement(By.xpath("//div[@class='DayPicker-Day' and text()='" + day + "']"));
    }

    //Calendar verification for created Visit

    public void goToMonthOnCalendar(String visitMonth) {
        getNavigationBar().goToRepVisits();
        driver.findElement(By.partialLinkText("Calendar")).click();

        while (!monthHeader().getText().equals(visitMonth)) {
            //rightArrowMonth().sendKeys(Keys.RETURN);
            rightArrowMonth().click();
        }
    }

    public void visitGetsAdded(String visitpresent, String dateOfVisit) {
        int position = 0;
        List<WebElement> visitDaysList = getDriver().findElements(By.xpath("//div[@class='rbc-date-cell']/a[text()='" + dateOfVisit + "']/../../div[@class='rbc-date-cell']"));
        for (int i = 0; i < 7; i++) {
            if (visitDaysList.get(i).getText().equals(dateOfVisit)) {

                position = i + 1;
                break;
            }
        }
        Assert.assertTrue("Time of the Visit is not displayed on Calendar", addedVisitTime(dateOfVisit, Integer.toString(position)).getText().equals(visitpresent.split(",")[0]));

        Assert.assertTrue("Name of the school is not displayed on Calendar", addedVisitSchoolName(dateOfVisit, Integer.toString(position)).getText().equals(visitpresent.split(",")[1]));
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
        }


    }

    public void clickCustomeTimelink() {
        waitForUITransition();
        linkForCustomeSlot().click();
    }

    public void visitStartandEndTime(String startTime, String endtime) {
        manualstartTime().sendKeys(startTime);
        manualEndTime().sendKeys(endtime);
    }

    public void clickAgenda() {
        getDriver().navigate().refresh();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='calendar-agenda-view-button']")));
        clicklinkAgenda().sendKeys(Keys.RETURN);
    }

    public void clickDayCalendar() {
        waitForUITransition();
        clicklinkDays().click();

    }

    public void clickAgendaDatePicker()

    {
        getAgendaDatePicker().click();
        waitUntilPageFinishLoading();
    }

    public void datePickeronAgendaView(String daysonAgenda) {
        pickDateInDatePicker(getDeltaDate(Integer.parseInt(daysonAgenda)));
    }

    public void verifyVisitInternalNotes(String visitInternalNotes) {
        Assert.assertTrue("Visit Internal Notes are not displayed", getDriver().findElement(By.cssSelector("input[value='" + visitInternalNotes + "']")).isDisplayed());
    }

    public void clickVisitName(String schoolName, String startTime, String endTime) {

        int count = 0;
        while (getDriver().findElements(By.cssSelector("div[title='" + startTime + " — " + endTime + ": " + schoolName + "']")).size() < 1) {
            if (count <= 5) {
                backwardsDayButton().click();
                count = count + 1;
            } else {

                while (getDriver().findElements(By.cssSelector("div[title='" + startTime + " — " + endTime + ": " + schoolName + "']")).size() < 1) {

                    forwardDayButton().click();
                }
            }

        }
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[title='" + startTime + " — " + endTime + ": " + schoolName + "']")));
        getDriver().findElement(By.cssSelector("div[title='" + startTime + " — " + endTime + ": " + schoolName + "']")).click();
        waitForUITransition();

    }

    public void visitCancelled(String cancellationNotes) {
        getCancelVisit().click();
        getCancelnotes().sendKeys(cancellationNotes);
        getYesCancelVisit().click();

    }

    public void verifyVisitDaysFromNow(String futureVisitday) {
        Calendar calendarStartDate = getDeltaDate(Integer.parseInt(futureVisitday));
        if (getDayOfWeek(calendarStartDate).equals("Saturday")) {
            calendarStartDate = getDeltaDate(Integer.parseInt(futureVisitday) - 1);
        } else if (getDayOfWeek(calendarStartDate).equals("Sunday")) {
            calendarStartDate = getDeltaDate(Integer.parseInt(futureVisitday) + 1);
        }

        generatedDateForExceptions = getMonthNumber(calendarStartDate) + "/" + getDay(calendarStartDate) + "/"
                + getYear(calendarStartDate).substring(2);

        chooseADateButton().click();
        pickDateInDatePicker(calendarStartDate);

    }

    /*public void addDataToAddAttendeeManually(DataTable AttendeeDetails){
        List<List<String>> AttendeeInformation = AttendeeDetails.asLists(String.class);
        for (List<String> fieldrow : AttendeeInformation) {
            switch (fieldrow.get(0)) {
                case "FirstName":
                    //attendeeFirstNameTextBox().clear();
                    attendeeFirstNameTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "LastName":
                    attendeeLastNameTextBox().sendKeys(fieldrow.get(1));
                    break;
                case "E-mail":
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
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("./*//*[@id='undefined-results']/div[1]/div/div[1]"), 1));
                    attendeeCollege().click();
                    break;
            }
        }

    }*/

    public void verifyRepDetails(String repDetails) {

        Assert.assertTrue("Email address is not correct", getRepDetails().getText().equals(repDetails));


    }

    //Locator for calendar screen
    private WebElement yesCancelAttendeeText() {
        return driver.findElement(By.xpath("//button/span[text()='Yes, cancel attendee']"));
    }

    private WebElement rightArrowMonth() {
        return getDriver().findElement(By.cssSelector("div._HSuPqZbac8aQcV7GQOr i.caret.right.icon"));

    }

    private WebElement monthHeader() {
        return getDriver().findElement(By.cssSelector("div[role='heading']"));
    }

    private List<WebElement> addedVisitweekday(String visitDate) {
        return getDriver().findElements(By.xpath("//div[@class='rbc-date-cell']/a[text()='" + visitDate + "']/../../div[@class='rbc-date-cell']"));
    }

    private WebElement addedVisitTime(String dayNumber, String position) {
        return getDriver().findElement(By.xpath("//div[@class='rbc-date-cell']/a[text()='" + dayNumber + "']/../../following-sibling::*/div[" + position + "]/div/div/div/span[@class='rbc-event-time']"));
    }

    private WebElement addedVisitSchoolName(String dayNumber, String position) {
        return getDriver().findElement(By.xpath("//div[@class='rbc-date-cell']/a[text()='" + dayNumber + "']/../../following-sibling::*/div[" + position + "]/div/div/div/span[not(@class='rbc-event-time')]"));
    }

    private WebElement linkForCustomeSlot() {
        return getDriver().findElement(By.cssSelector("div._3nrJ9RwrMDsDPZ9tAJC4q6 span"));

    }

    private WebElement manualstartTime() {
        return getDriver().findElement(By.cssSelector("input#manualStartTime"));

    }

    private WebElement manualEndTime() {
        return getDriver().findElement(By.cssSelector("input#manualEndTime"));

    }

    private WebElement clicklinkAgenda() {
        return getDriver().findElement(By.cssSelector("button[id='calendar-agenda-view-button']"));

    }

    private WebElement clicklinkDays() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='ui button GFr3D5C_jMOwFFfwEoOXq _1aOzhO0fbLlfLnEsVWQZCQ']")));
        return getDriver().findElement(By.cssSelector("button[class='ui button GFr3D5C_jMOwFFfwEoOXq _1aOzhO0fbLlfLnEsVWQZCQ']"));

    }

    private WebElement getVisistName(String collegeName) {
        //return getDriver().findElements(By.xpath("//span[contains(text(),'"+collegeName+"')]")).get(0);
        return getDriver().findElement(By.cssSelector("div[title='3:00 AM — 5:00 AM: Alma College']"));

    }

    private WebElement getAgendaDatePicker() {
        return getDriver().findElement(By.cssSelector("button.bne-HEiKl3BvzkB-LIC8M:nth-child(3)"));

    }

    private WebElement getCancelVisit() {
        return getDriver().findElement(By.cssSelector("button[class='ui negative button']"));

    }

    private WebElement getCancelnotes() {
        return getDriver().findElement(By.cssSelector("textarea#repVisit-cancelation-message"));

    }

    private WebElement getYesCancelVisit() {
        return getDriver().findElement(By.cssSelector("button.ui.negative.right.floated.button span"));
    }

    private WebElement getYesCancelCollegeFair() {
        return getDriver().findElement(By.xpath("//span[text() = 'Yes, Cancel this fair']"));
    }

    /*    private WebElement attendeeFirstNameTextBox() {
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
            return  getDriver().findElement(By.xpath("./*//*[@id='undefined-results']/div[1]/div/div[1]"));
    }*/
    private WebElement getRepDetails() {
        return getDriver().findElement(By.cssSelector("div._3DhFv-KjwgxmXKcCAgKD8c"));
    }

    private String getRescheduleVisitStartTimeInCalendar() {
        String[] time = RescheduleStartTimeforNewVisit.split("am");
        String startTime = time[0] + "AM";
        return startTime;
    }

    private String getVisitSchedulePopupStartTime() {
        String[] time = StartTime.split("am");
        String startTime = time[0] + " " + "AM";
        return startTime;
    }

    private String getRescheduleVisitSchedulePopupStartTimeInCalendar() {
        String[] time = RescheduleStartTimeforNewVisit.split("am");
        String startTime = time[0] + " " + "AM";
        return startTime;
    }

    private String getVisitSchedulePopupStartTimeInCalendar() {
        String[] time = StartTime.split("am");
        String startTime = time[0] + "" + "AM";
        return startTime;
    }

    private WebElement eventLocationInVisitSchedule() {
        WebElement location = driver.findElement(By.xpath("//input[@aria-label='Event Location']"));
        return location;
    }

    private WebElement dateButtonInVisitReschedule() {
        WebElement button = driver.findElement(By.xpath("//button/span/span[text()='Go to date']"));
        return button;
    }

    private WebElement rescheduleVisitButton() {
        WebElement button = button("Reschedule Visit");
        return button;
    }

    protected void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();", element);
    }

    private WebElement rescheduleMessageTextBox() {
        WebElement text = driver.findElement(By.xpath("//textarea[@id='rescheduleMessage']"));
        return text;
    }

    private WebElement notificationAndTasks() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Tasks']")));
        WebElement notification = driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Tasks']"));
        return notification;
    }

    private WebElement activityTab() {
        WebElement activity = driver.findElement(By.xpath("//span[text()='Activity']"));
        return activity;
    }

    private WebElement showMore() {
        WebElement activity = driver.findElement(By.partialLinkText("Show More"));
        return activity;
    }

    private void clickOnClose() {
        driver.findElement(By.cssSelector("button[class='ui basic primary button']")).click();
    }

    private WebElement internalNotesTextBoxInReschedulePopup() {
        WebElement text = driver.findElement(By.xpath("//input[@aria-label='Internal Notes']"));
        return text;
    }

    private WebElement cancellationMessageTextBox() {
        WebElement text = driver.findElement(By.xpath("//textarea[@id='repVisit-cancelation-message']"));
        return text;
    }

    private WebElement cancelVisitButton() {
        WebElement button = button("Yes, cancel visit");
        return button;
    }

    private WebElement selectViewDetails(String fairNametoClickViewDetails) {
        waitUntilElementExists(driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[text()='" + fairNametoClickViewDetails + "']/parent::tr/td/a[span='View Details']")));
        WebElement select = driver.findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[text()='" + fairNametoClickViewDetails + "']/parent::tr/td/a[span='View Details']"));
        return select;
    }

    private String getVisitStartTimeInCalendar() {
        String[] time = StartTime.split("am");
        String startTime = time[0] + "AM";
        return startTime;
    }

    private String getVisitStartTimeInReschedule() {
        String[] time = StartTime.split("am");
        String startTime = time[0] + " " + "AM";
        return startTime;
    }

    private String getRescheduledVisitStartTimeInCalendar() {
        String[] time = RescheduleStartTimeforNewVisit.split("am");
        String startTime = time[0] + "AM";
        return startTime;
    }

    private String getRescheduledVisitStartTime() {
        String[] time = RescheduleStartTimeforNewVisit.split("am");
        String startTime = time[0] + " " + "AM";
        return startTime;
    }

    private WebElement editButtonInCollegeFair() {
        return getDriver().findElement(By.id("edit-college-fair"));

    }

    public void savePrimaryContactForVisit() {
        button("Save changes").click();
    }

    public void primaryContactForVisit(String phone, String email) {
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        notificationAndPrimaryContact().click();
        primaryContactPhone().clear();
        primaryContactPhone().sendKeys(phone);
        primaryContactOutsideCommunity().clear();
        primaryContactOutsideCommunity().sendKeys(email);
    }

    /* Locator for Primary Contact*/
    private WebElement primaryContactPhone() {
        return getDriver().findElement(By.cssSelector("input#notification_contacts_primary_contact_phone"));
    }

    private WebElement primaryContactOutsideCommunity() {
        return getDriver().findElement(By.cssSelector("textarea#notification_contacts_additional_emails"));
    }

    public void editCollegeFairs() {
        waitUntilPageFinishLoading();
        editFairButton().click();
    }

    public void cancelCollegeFairClick() {
        cancelThisCollegeFair().click();

    }

    public void cancelMessageForColleges(String cancelFairMessage) {
        cancelFairMessage().sendKeys(cancelFairMessage);

    }

    public void cancelFairAndNotifyColleges() {
        cancelFairAndNotify().click();

    }

    public void fairAttendeeclick() {
        FairAttendee().click();
    }

    public String getCalendarVisitTime() {
        String time = "", option = "";
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        char value[] = visitTime.toCharArray();
        for (int i = 0; i <= 4; i++) {
            time = time + value[i];
        }
        for (int i = 5; i <= value.length - 1; i++) {
            option = option + value[i];
        }
        String startTime = time + option;
        return startTime;
    }

    /**
     * Verifies that a given text is displayed in Connecting Naviance and Repvisits page
     * @param text
     */
    public void verifyTextInConnectingNavianceAndRepvisitsPage(String text){
        waitUntil(ExpectedConditions.visibilityOf(connectingNavianceText()));
        softly().assertThat(text(text).isDisplayed());
    }

    /**
     * Verifies that a given text is displayed in Naviance Sync Settings page
     * @param text
     */
    public void verifyTextInNavianceSyncSettingsPage(String text){
        getNavigationBar().goToRepVisits();
        availabilityAndSettings().click();
        navianceSettings().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(navianceSettingsPageLocator()));
        softly().assertThat(text(text).isDisplayed());
    }

    /**
     * Manually adding attendee in college fair
     * @param institution
     */
    public void manuallyAddAttendee(String firstName,String institution){
        getAddAttendeesButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(addAttendeeManually()));
        getDriver().findElement(addAttendeeManually()).click();
        waitUntil(ExpectedConditions.visibilityOf(attendeeFirstNameTextBox()));
        attendeeFirstNameTextBox().sendKeys(firstName);
        attendeeInstitutionTextBox().sendKeys(institution);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(getInstitution(institution)));
        getDriver().findElement(getInstitution(institution)).click();
        getDriver().findElement(addAttendeeButtonInCollegeFair()).click();
    }
  
   public void verifyPendingVisitInCalendar(String university, String date, String option){
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
//Pending check box is not checked
        collegeFairTextBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() > 0) {
            verifyAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() > 0) {
                    verifyAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment > 0) {
                        verifyAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment > 0) {
                verifyAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void verifyScheduledVisitInCalendar(String university, String date, String option){
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
//Pending check box is checked
//Visits Confirmed check box is not checked
        pendingCheckBoxInCalendarPage().click();
        collegeFairTextBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() > 0) {
            verifyAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() > 0) {
                    verifyAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment > 0) {
                        verifyAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment > 0) {
                verifyAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void verifyDateIsDisabledInCollegeFairPage(String date){
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        WebElement fairs = collegeFairs();
        waitUntilElementExists(fairs);
        collegeFairs().click();
        waitUntilPageFinishLoading();
        addCollegeButton().click();
        fairsDatePicker().click();
        String[] parts;
        if(date.length()>2){
             parts = date.split(" ");
        }else {
            int Date = Integer.parseInt(date);
            String currentDate = getSpecificDate(Date,"MMMM d yyyy");
            parts = currentDate.split(" ");
        }
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        Assert.assertTrue("Selected date is enabled",getDriver().findElement(By.xpath("//div[@aria-disabled='true'][text()=" + parts[1] + "]")).isDisplayed());
        fairsDatePicker().click();
        getDriver().findElement(closeFairPopup()).click();
        waitUntilPageFinishLoading();
    }

    public void verifyDateIsDisabledInEditCollegeFairPage(String date){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(editCollegeFairButton()));
        Assert.assertTrue("Edit button is not displayed", editButtonInCollegeFair().isDisplayed());
        editButtonInCollegeFair().click();
        waitUntilPageFinishLoading();
        fairsDatePicker().click();
        String[] parts;
        if(date.length()>2){
            parts = date.split(" ");
        }else {
            int Date = Integer.parseInt(date);
            String currentDate = getSpecificDate(Date,"MMMM d yyyy");
            parts = currentDate.split(" ");
        }
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        Assert.assertTrue("Selected date is enabled",getDriver().findElement(By.xpath("//div[@aria-disabled='true'][text()=" + parts[1] + "]")).isDisplayed());
        fairsDatePicker().click();
        getDriver().findElement(closeFairPopup()).click();
        waitUntilPageFinishLoading();
    }

    /**
     * verifying undefined text is not displaying in college fair attendee page
     * @param firstNameWithUndefinedText
     */
    public void verifyUndefinedTextIsNotDisplayingInAttendeePage(String firstNameWithUndefinedText){
        Assert.assertTrue("Undefined text is displaying",attendeeFirstNameAndLastName(firstNameWithUndefinedText).size()==0);
    }

    /*public void closeSendEmailMessageBox(){
        button("Close").click();

    }*/
    //*locators for Edit/Cancel Fair

    private WebElement cancelFairMessage() {
        return getDriver().findElement(By.cssSelector("textarea#college-fair-cancellation-message"));

    }

    private WebElement cancelFairAndNotify() {
        return getDriver().findElement(By.cssSelector("button.ui.primary.right.floated.button._4kmwcVf4F-UxKXuNptRFQ"));

    }

    private WebElement FairAttendee() {
        return getDriver().findElement((By.xpath("//span[contains(text(),'ADD ATTENDEE')]")));

    }

    private WebElement fairSave() {
        return getDriver().findElement((By.xpath("//span[contains(text(),'Save')]")));
    }

    private WebElement closeFairCancelConfiration() {
        return getDriver().findElement((By.xpath("//button[contains(text(),'Close')]")));
    }

    private WebElement fairsNameInEditFairsPopup() {
        return getDriver().findElement(By.id("college-fair-name"));
    }

    private WebElement fairsStartTimeInEditFairsPopup() {
        return getDriver().findElement(By.id("college-fair-start-time"));
    }

    private WebElement fairsDateInEditFairsPopup() {
        return getDriver().findElement(By.id("college-fair-date"));
    }

    private WebElement maxNoOfCollegesInEditFairsPopup() {
        return getDriver().findElement(By.id("college-fair-max-number-colleges"));
    }

    private WebElement collegeFairEmailInEditFairsPopup() {
        return getDriver().findElement(By.id("college-fair-email-message-to-colleges"));
    }

    private WebElement closeButtonInFairsPopup() {
        return getDriver().findElement(By.xpath("//button[text()='Close']"));
    }

    private WebElement cancelMessageTextboxInFairsPopup() {
        return getDriver().findElement(By.id("college-fair-cancellation-message"));
    }

    private WebElement notificationButtonForFairsCancel() {
        return button("Cancel fair and notify colleges");
    }

    private WebElement fairCancelButton() {
        return button("Yes, Cancel this fair");
    }

    private WebElement cancelCollegeFairButton() {
        return button("Cancel This College Fair");
    }

    private WebElement eventLocationInAddVisitPopup() {
        WebElement element = null;
        if (getDriver().findElements(By.cssSelector("input[name='locationWithinSchool']")).size() == 1) {
            element = getDriver().findElement(By.cssSelector("input[name='locationWithinSchool']"));
        } else if (getDriver().findElements(By.id("eventLocation")).size() == 1) {
            element = getDriver().findElement(By.id("eventLocation"));
        }
        return element;
    }

    private WebElement editFairsStartTimeTextBox() {
        return getDriver().findElement(By.id("college-fair-start-time"));
    }

    private WebElement editFairsEndTimeTextBox() {
        return getDriver().findElement(By.id("college-fair-end-time"));
    }

    private WebElement editFairsMaxNoOfCollegesTextBox() {
        return getDriver().findElement(By.id("college-fair-max-number-colleges"));
    }

    private WebElement editFairsEmailMsgTextBox() {
        return getDriver().findElement(By.id("college-fair-email-message-to-colleges"));
    }

    private WebElement editFairsSubmitButton() {
        return getDriver().findElement(By.xpath("//button/span[text()='Save']"));
    }

    private WebElement successBanner() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='aIt5aCQ6cGJhCVYB9NA02']")));
        return getDriver().findElement(By.xpath("//div[@class='aIt5aCQ6cGJhCVYB9NA02']"));
    }

    private WebElement declineMsgTextBox() {
        WebElement textbox = driver.findElement(By.id("attendee-decline-message"));
        return textbox;
    }

    private WebElement declineButton() {
        WebElement button = button("Yes, decline visit");
        return button;
    }

    private WebElement agendaButton() {
        return driver.findElement(By.xpath("//button[@title='Agenda']"));
    }

    private WebElement getStartDateInAgenda() {
        return driver.findElement(By.xpath("//button[@class='ui tiny button bne-HEiKl3BvzkB-LIC8M'][1]/b/span"));
    }

    private WebElement negativeMessageInRepvisits() {
        return getDriver().findElement(By.xpath("//div[@class='ui negative message']"));
    }

    private WebElement accountSettings() {
        WebElement link = driver.findElement(By.xpath("//span[text()='Account Settings']"));
        return link;
    }

    private WebElement agendaViewStartDatePicker() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//i[contains(@class, 'grey calendar outline link')])[1]")));
        return getDriver().findElement(By.xpath("(//i[contains(@class, 'grey calendar outline link')])[1]"));
    }

    private WebElement agendaViewEndDatePicker() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//i[contains(@class, 'grey calendar outline link')])[1]")));
        return getDriver().findElement(By.xpath("(//i[contains(@class, 'grey calendar outline link')])[2]"));
    }

    private List<WebElement> listOfEventsDisplayedInAgendaView() {
        waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[@class='rbc-agenda-event-cell']")));
        return driver.findElements(By.xpath("//td[@class='rbc-agenda-event-cell']"));
    }

    private WebElement updateMessagingButton() {
        return driver.findElement(By.xpath("//button[@class='ui primary button']"));
    }

    private WebElement viewDetailLink() {
        return driver.findElement(By.cssSelector("div[class='_1743W0qaWdOtlS0jkveD7o']:nth-child(1)>table[class='ui unstackable table']:nth-child(2)>tbody>tr>td:nth-child(6)>a>span"));
    }

    private WebElement locationTextBoxInNaviance() {
        return getDriver().findElement(By.id("locationWithinSchool"));
    }

    private WebElement saveChangesNaviance() {
        return getDriver().findElement(By.xpath("//button/span[text()='Save changes']"));
    }

    private By saveChangesAvailabilityLocator() {
        return By.xpath("//button/span[text()='Save Changes']");
    }

    private WebElement saveChangesAvailability() {
        return getDriver().findElement(saveChangesAvailabilityLocator());
    }

    private WebElement navianceSync() {
        return getDriver().findElement(By.xpath("//a/span[text()='Naviance Sync']"));
    }

    private WebElement pushToNaviancelinkText() {
        return getDriver().findElement(By.xpath("//span[text()='Push all to Naviance?']"));
    }

    private String getCalendarStartTime() {
        String startTime = "";
        String[] time = StartTime.split("am");
        String hour[] = time[0].split(":");
        if (hour[0].equals("12"))
            startTime = "00" + ":" + hour[1];
        return startTime;
    }

    private WebElement getCalendarBtn() {
        return link("Calendar");
    }

    private WebElement disconnectButton() {
        return driver.findElement(By.xpath("//span[text()='Disconnect RepVisits from Naviance']"));
    }

    private WebElement cancelIndisconnectRVfromNaviancePopup() {
        return driver.findElement(By.cssSelector("button[class='ui teal basic button']"));
    }

    private WebElement yeslIndisconnectRVfromNaviancePopup() {
        return driver.findElement(By.cssSelector("button[class='ui primary button']"));
    }

    private WebElement optInYesRadioButton() {
        return driver.findElement(By.xpath("//label[text()='Yes, I would like to connect Naviance and RepVisits']"));
    }

    private WebElement optInNoRadioButton() {
        return driver.findElement(By.xpath("//label[text()='No, I would like to use RepVisits without the Naviance integration']"));
    }

    private WebElement nextButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui primary button']"));
    }

    private List<WebElement> navianceImport() {
        return getDriver().findElements(By.xpath("//span[text()='Naviance Import']"));
    }

    private List<WebElement> nextButtons() {
        return getDriver().findElements(By.cssSelector("button[class='ui primary button']"));
    }

    private WebElement backButton() {
        return driver.findElement(By.xpath("//span[text()='Back']"));
    }

    private WebElement takeMeToMyVisitsButton() {
        return driver.findElement(By.xpath("//button/span[text()='Take me to my visits']"));
    }

    private List<WebElement> takeMeToMyVisitsButtons() {
        return driver.findElements(By.xpath("//button/span[text()='Take me to my visits']"));
    }


    private WebElement takeMeButton() {
        return driver.findElement(By.xpath("//button[@class='ui button']"));
    }

    /**
     * Gets the Button to perform action over there.
     *
     * @param button name of the Button to click
     * @return webelement
     */
    private WebElement clickOnButton(String button) {
        return driver.findElement(By.xpath("//span[contains(text(),'" + button + "')]"));
    }

    private WebElement requestLink() {
        WebElement request = driver.findElement(By.xpath("//a[@class='menu-link active']/span[text()='Requests']"));
        return request;
    }

    private WebElement iamDoneButton() {
        return driver.findElement(By.cssSelector("button[class='ui button']"));
    }

    private WebElement customTimeLink() {
        return driver.findElement(By.xpath("//span[text() = 'Want a custom time? Add it manually']"));
    }

    private WebElement addMoreButton() {
        return driver.findElement(By.cssSelector("button[class='ui basic primary button']"));
    }

    private WebElement dateButtonText() {
        return driver.findElement(By.cssSelector("button.ui.small.fluid.button span span"));
    }

    private WebElement findRepresentativeTextBox() {
        return driver.findElement(By.cssSelector("input#calendar-search-reps"));
    }

    private WebElement representativeOption(String representativeName) {
        return driver.findElement(By.xpath("//div[@class = 'title' and contains(text(), '" + representativeName + "')]"));
    }

    private WebElement scheduleVisitAddVisitButton() {
        return driver.findElement(By.cssSelector("button[type='submit'].floated"));
    }

    public String showMoreLinkLocator(String dayNumber) {
        return "//div[@class='rbc-date-cell']/a[text()='" + dayNumber + "']/../../../../div[@class = 'rbc-row-content']/div/div/a[@class = 'rbc-show-more']";
    }

    private WebElement eventInOverlay(String time) {
        return driver.findElement(By.xpath("//div[@class = 'rbc-overlay']/div/div/div/span[@class = 'rbc-event-time' and text() = '" + time + "']"));
    }

    private WebElement eventInCell(String time) {
        return getDriver().findElement(By.xpath("//div[@class = 'rbc-row-content']/div/div/div/div/div/span[text() = '" + time + "']"));
    }

    private WebElement studentRegistrationDeadlineField() {
        return driver.findElement(By.cssSelector("input#rsvpNumber"));
    }

    private WebElement hoursDaysDropdown() {
        return driver.findElement(By.cssSelector("div#rsvpKind"));
    }

    private WebElement hoursDaysOption(String option) {
        return driver.findElement(By.xpath("//div[@role='option']/span[text() = '" + option + "']"));
    }

    private String exportButtonLocator = "button[title='Export']";

    private WebElement createTimeSlotOption() {
        return driver.findElement(By.id("recreate-time-slots"));
    }

    private WebElement ignoreTimeSlotOption() {
        return getDriver().findElement(By.cssSelector("div[class='ui radio checkbox _1vltWKJ_6mK9OYjf4UnP2l']"));
    }

    private WebElement addRegularHoursButton() {
        return button("Add regular hours");
    }

    private WebElement removeSingleTimeSlot() {
        return getDriver().findElement(By.cssSelector("i[class='trash alternate outline icon _6S_VIt7XKOpy-Mn7y_CJu']"));
    }

    private WebElement acceptButton() {
        return driver.findElement(By.cssSelector("button[class='ui primary button']"));
    }

    private WebElement visitsRadioButton() {
        return getDriver().findElement(By.xpath("//div[@class='field _2sdmYWzbOPHy7r9z3SIJp9']"));
    }

    private WebElement fairsRadioButton() {
        return driver.findElement(By.cssSelector("input[value='FAIRS']"));
    }

    private WebElement visitsAndFairsRadioButton() {
        return driver.findElement(By.cssSelector("input[value='VISITS_AND_FAIRS']"));
    }

    private WebElement circularButton() {
        return getDriver().findElement(By.xpath("//button[@class='ui circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"));
    }

    private WebElement selectVisitInTheCalendar() {
        return getDriver().findElement(By.cssSelector("div[class='_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa']"));
    }

    private WebElement circularIconCloseButton() {
        return getDriver().findElement(By.xpath("//button[@class='ui circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"));
    }

    private WebElement addCollegeFair() {
        return driver.findElement(By.cssSelector("button[id='add-college']"));
    }

    private WebElement yesAcceptAllIncommingRequestsRadioButton() {
        return getDriver().findElement(By.xpath("//label[text()='Yes, accept all incoming requests.']"));
    }

    private WebElement onlyMeRadioButton() {
        return getDriver().findElement(By.xpath("//label[text()='Only Me']"));
    }

    private WebElement allRepVisitsUsersRadioButton() {
        return getDriver().findElement(By.xpath("//label[text()='All RepVisits Users']"));
    }

    private By successMessage() {
        return By.cssSelector("div[class='ui small icon success message toast']");
    }

    private List<WebElement> duplicateSlot(String startTime) {
        return driver.findElements(By.xpath("//button[text()='" + startTime + "']"));
    }

    private WebElement clickDuplicateSlot(String startTime) {
        return driver.findElement(By.xpath("//button[text()='" + startTime + "']/preceding-sibling::span/i"));
    }

    private WebElement yesCancelAttendeeLink() {
        return getDriver().findElement(By.cssSelector("button[class='ui negative right floated button']"));
    }

    private WebElement noGoBackButton() {
        return getDriver().findElement(By.cssSelector("button[id='go-back']"));
    }

    private WebElement removeButton() {
        return getDriver().findElement(By.xpath("//button/span[text()='REMOVE']"));
    }

    private By fairsViewDetails(String fairNametoClickViewDetails) {
        return By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[text()='" + fairNametoClickViewDetails + "']/parent::tr/td/a[span='View Details']");
    }

    private WebElement attendeeCancellationMessageTextBox() {
        return getDriver().findElement(By.xpath("//textarea[@id='attendee-cancellation-message']"));
    }

    private WebElement addCollegeButton() {
        return button(By.id("add-college"));
    }

    private WebElement collegeFairsNameTextBox() {
        return textbox(By.id("college-fair-name"));
    }

    private WebElement collegeFairsDateTextBox() {
        return textbox(By.id("college-fair-date"));
    }

    private WebElement fairsDatePicker() {
        return getDriver().findElement(By.xpath("//input[@id='college-fair-date']/following-sibling::i[@class='calendar alternate outline large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
    }

    private WebElement fairsStartTimeTextBox() {
        return getDriver().findElement(By.cssSelector("form input[id='college-fair-start-time']"));
    }

    private WebElement fairsEndTimeTextBox() {
        return getDriver().findElement(By.cssSelector("form input[id='college-fair-end-time']"));
    }

    private WebElement fairsRSVPDatePicker() {
        return getDriver().findElement(By.xpath("//input[@id='college-fair-rsvp-deadline']/following-sibling::i[@class='calendar alternate outline large link icon _33WZE2kSRNAgPqnmxrKs-o']"));
    }

    private WebElement fairsRSVPDateTextBox() {
        return textbox(By.id("college-fair-rsvp-deadline"));
    }

    private WebElement fairsCostTextBox() {
        return textbox(By.id("college-fair-cost"));
    }

    private WebElement fairsMaxCollegeTextBox() {
        return textbox(By.id("college-fair-max-number-colleges"));
    }

    private WebElement fairsExpectedStudentsTextBox() {
        return textbox(By.id("college-fair-number-expected-students"));
    }

    private WebElement fairsAutomaticRequestConfirmation() {
        return getDriver().findElement(By.id("college-fair-automatic-request-confirmation-no"));
    }

    private WebElement fairsCloseButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui button']"));
    }

    private WebElement addAttendeeButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui primary button']"));
    }

    private WebElement fairsSuccessMessage() {
        return getDriver().findElement(By.xpath("//p/span[text()='" + FairName + "']/parent::p"));
    }

    private List<WebElement> moreUpComingEvents() {
        return getDriver().findElements(By.xpath("//span[text()='View More Upcoming Events']"));
    }

    private WebElement editCollegFair() {
        return getDriver().findElement(By.cssSelector("button[id='edit-college-fair']"));
    }

    private WebElement collegeFairAddAttendee() {
        return getDriver().findElement(By.cssSelector("button[id='add-attendee']"));
    }

    private WebElement messageCollegesButton() {
        return getDriver().findElement(By.cssSelector("button[id='message-colleges']"));
    }

    private WebElement visitRequestButton() {
        WebElement button = getDriver().findElement(By.xpath("//button[contains(text(),'Yes, Request this time')]"));
        return button;
    }

    private WebElement goToDate() {
        WebElement goToDate = getDriver().findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return goToDate;
    }

    private WebElement eMailMessageTextBox() {
        return getDriver().findElement(By.id("college-fair-email-message-to-colleges"));
    }

    private WebElement cancelFairsButton() {
        return getDriver().findElement(By.xpath("//button/span[text()='Cancel This College Fair']"));
    }

    private WebElement fairsSaveButton() {
        return getDriver().findElement(By.xpath("//button/span[text()='Save']"));
    }

    private By editCollegeFairButton() {
        return By.id("edit-college-fair");
    }

    private List<WebElement> fairCancellationTextBox() {
        return getDriver().findElements(By.id("college-fair-cancellation-message"));
    }

    private WebElement formEmailTextBox(){ return driver.findElement(By.id("user-form-email")); }
   
    private By fairsClose() {
        return By.xpath("//button[text()='Close']");
    }

    private WebElement unpublishButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui button']"));
    }

    private WebElement visitsAndFairsLink() {
        return getDriver().findElement(By.xpath("//label[text()='Visits and Fairs']"));
    }

    private WebElement fairsLink() {
        return getDriver().findElement(By.xpath("//label[text()='Fairs']"));
    }

    private List<WebElement> highSchoolInformation() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='High School Information']"));
    }

    private By setUpWizardText() {
        return By.xpath("//h1/span[text()='Tell us about your High School']");
    }

    private WebElement highSchoolTextInSetupWizard() {
        return text("Tell us about your high school.");
    }

    private WebElement timeZoneText() {
        return text("Please specify your high school's time zone.");
    }

    private WebElement searchSelectionDropDown() {
        return getDriver().findElement(By.xpath("//div[@class='ui search selection dropdown']//div[@class='text']"));
    }

    private By visitButtonInSetupWizard() {
        return By.cssSelector("input[value='VISITS']");
    }

    private List<WebElement> availabilityActiveInSetupWizard() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='Availability']"));
    }

    private WebElement regularWeeklyHoursText() {
        return text("Regular Weekly Hours");
    }

    private List<WebElement> collegeFairsActiveInSetupWizard() {
        return getDriver().findElements(By.xpath("//a[@class='menu-link active']/span[text()='College Fairs']"));
    }

    private WebElement collegeFairText() {
        return getDriver().findElement(By.xpath("//a[@class='menu-link qxSNjKWAyYiOIN9yZHE_d']/span[text()='College Fairs']"));
    }

    private WebElement setupWizardWelcomeText() {
        return text("Welcome to RepVisits");
    }

    private List<WebElement> calendarSyncActiveInSetupWizard() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='Calendar Sync']"));
    }

    private WebElement subscriptionText() {
        return text("iCal/Outlook Subscription");
    }

    private WebElement navianceSettingsPageText() {
        return text("Connecting Naviance and RepVisits");
    }

    private WebElement primaryContactText() {
        return text("Primary Contact for Visits");
    }

    private List<WebElement> notificationAndPrimaryContactActiveInSetupWizard() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='Notifications & Primary Contact']"));
    }

    private WebElement notificationPrimaryContactPhone() {
        return getDriver().findElement(By.id("notification_contacts_primary_contact_phone"));
    }

    private WebElement primaryContactName() {
        return getDriver().findElement(By.xpath("//div[@name='primaryContact']//div[@class='text']"));
    }

    private By selectPrimaryContact(String user) {
        return By.xpath("//div[@class='visible menu transition']/div/span[text()='" + user + "']");
    }

    private WebElement confirmationMessage() {
        return text("Confirmation Message");
    }

    private By setUpWizardWelcomePageText() {
        return By.xpath("//h1/span[text()='Welcome to RepVisits']");
    }

    private By setupWizardNextButton() {
        return By.cssSelector("button[class='ui primary button']");
    }

    private List<WebElement> activeWizard(String wizardName) {
        return driver.findElements(By.xpath("//div[@class='active step' and @name ='" + wizardName + "']"));
    }

    private WebElement visitStartTimeTextBox() {
        return getDriver().findElement(By.cssSelector("input[name='startTime']"));
    }

    private WebElement visitEndTimeTextBox() {
        return getDriver().findElement(By.cssSelector("input[name='endTime']"));
    }

    private WebElement numVisitsTextBox() {
        return getDriver().findElement(By.cssSelector("input[title='numVisits']"));
    }

    private WebElement selectDayDropDown() {
        return getParent(text("Monday - Friday"));
    }

    private WebElement selectDay(String day) {
        return getDriver().findElement(By.xpath("//span[text()='" + day + "']"));
    }

    private List<WebElement> setupWizardAvailability(String tabName) {
        return driver.findElements(By.xpath("//li[@class='_1gXbsnbxcvr12eMqyC1xjb']/span[text()='" + tabName + "']"));
    }

    private WebElement selectDayName(String dayName) {
        return getDriver().findElement(By.xpath("//div[@title='" + dayName + "']/input/following-sibling::label"));
    }

    private WebElement blockedDayText() {
        return text("Blocked Days");
    }

    private WebElement changeWeek(String changeWeek) {
        return getDriver().findElement(By.xpath("//button[@title='" + changeWeek + "']"));
    }

    private WebElement exceptionText() {
        return text("Exceptions");
    }

    private WebElement availabilitySettingsText() {
        return text("Availability Settings");
    }

    private WebElement visitConfirmation(String visitsConfirmations) {
        return getDriver().findElement(By.xpath("//div/label[text()[contains(., '" + visitsConfirmations + "')]]"));
    }

    private WebElement visitBox() {
        return getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
    }

    private WebElement cancellationBox() {
        return getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
    }

    private By messagingOptions() {
        return By.xpath("//div[@class='active step' and @name ='Messaging Options']");
    }

    private WebElement messagingOptionsActive() {
        return getDriver().findElement(By.xpath("//div[@class='active step' and @name ='Messaging Options']"));
    }

    private WebElement visitButton() {
        return getDriver().findElement(By.cssSelector("input[value='VISITS']"));
    }

    private WebElement fairsButton() {
        return getDriver().findElement(By.cssSelector("input[value='FAIRS']"));
    }

    private WebElement visitAndFairsButton() {
        return getDriver().findElement(By.xpath("//label[text() = 'Visits and Fairs']"));
    }

    private List<WebElement> completeWizardActiveStep() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='Complete!']"));
    }

    private List<WebElement> navianceSettingsActiveStep() {
        return getDriver().findElements(By.xpath("//div[@name='Naviance Settings' and @class='active step']"));
    }

    private WebElement visitAvailabilityText() {
        return text("Visit Availability");
    }

    private WebElement successMessageText() {
        return text("Your visit availability has been successfully set up!");
    }

    private WebElement repVisitAck() {
        return text("https://CounselorCommunity.com");
    }

    private WebElement todayText() {
        return text("Today");
    }

    private WebElement startedWithCollegeFairButton() {
        return button("Get started with college fairs");
    }

    private List<WebElement> messageOptionsActiveStep() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='Messaging Options']"));
    }

    private List<WebElement> setupWizardAvailabilitySettings() {
        return getDriver().findElements(By.xpath("//span[contains(text(),'Availability Settings')]"));
    }

    private WebElement confirmationMessageText() {
        return text("Confirmation Message");
    }

    private WebElement specialInstructionMessageText() {
        return text("Special Instruction for RepVisits");
    }

    private WebElement emailInstructionsText() {
        return getDriver().findElement(By.cssSelector("label[for='emailInstructions']"));
    }

    private WebElement webInstructionsText() {
        return getDriver().findElement(By.cssSelector("label[for='webInstructions']"));
    }

    private WebElement emailInstructionTextbox() {
        return getDriver().findElement(By.id("emailInstructions"));
    }

    private WebElement webInstructionsTextbox() {
        return getDriver().findElement(By.id("webInstructions"));
    }

    private WebElement availabilitysettings() {
        return getDriver().findElement(By.xpath("//span[contains(text(),'Availability Settings')]"));
    }

    private WebElement phoneNoText() {
        return text("Primary Contact Phone Number");
    }

    private WebElement welcomeWizardText() {
        return text("Tell us about your High School");
    }

    private List<WebElement> regularweeklyHours() {
        return getDriver().findElements(By.xpath("//a/span[text()='Regular Weekly Hours']"));
    }

    private By addvisitButton() {
        return By.xpath("//span[text()='add visit']");
    }

    private By calendarMonth() {
        return By.xpath("//button[normalize-space(@class)='ui pink button GFr3D5C_jMOwFFfwEoOXq _2I9ZFEPwCCOsGFn0AAk1Gl' and @title='Month']");
    }

    private List<WebElement> activeStepNavianceSettings() {
        return getDriver().findElements(By.xpath("//div[@class='active step' and @name='Naviance Settings']"));
    }

    private WebElement connectingNavianceText() {
        return getDriver().findElement(By.xpath("//span[text()='Connecting Naviance and RepVisits']"));
    }

    private WebElement optOutTextInNaviance() {
        return getDriver().findElement(By.xpath("//span[text()='By Connecting RepVisits and Naviance, all of your Naviance College Visits will be managed within RepVisits unless you choose to opt-out of the integration in the future.']"));
    }

    private WebElement howItWorksText() {
        return getDriver().findElement(By.xpath("//span[text()='How It Works']"));
    }

    private WebElement appointmentScheduledText() {
        return getDriver().findElement(By.xpath("//span[text()='Appointments scheduled via RepVisits can be published to Naviance College Visits (so students can see them and sign up).']"));
    }

    private WebElement reScheduledOrCancelledMessage() {
        return getDriver().findElement(By.xpath("//span[text()='If a visit is rescheduled or cancelled in RepVisits, it will automatically update in Naviance College Visits and Naviance Student.']"));
    }

    private WebElement repVisitsInstructionText() {
        return getDriver().findElement(By.xpath("//span[text()='You will be able to view your visits and student registrations directly in Naviance (staff view). However, you will need to use RepVisits to create, edit, or cancel visits.']"));
    }

    private WebElement setupTitle() {
        return getDriver().findElement(By.xpath("//span[text()='Set Up']"));
    }

    private WebElement publishedOptionText() {
        return getDriver().findElement(By.xpath("//span[text()='Publishing Options: You will be able to specify whether RepVisits should be published to Naviance College Visits automatically or manually on a visit-by-visit basis.']"));
    }

    private WebElement visitSettingsText() {
        return getDriver().findElement(By.xpath("//span[text()='Visit Settings: You will be able to create default settings for Naviance College Visits (location, notes, registration deadline, maximum number of students) within RepVisits. You will also be able to adjust these settings on a visit-by-visit basis.']"));
    }

    private WebElement navianceImportText() {
        return getDriver().findElement(By.xpath("//span[normalize-space(text())='Importing from Naviance: Your current College Visits in Naviance will be imported into RepVisits. We will automatically match these to any existing visits in RepVisits for the same colleges on the same day. You will have a chance to review and validate these matches before the Naviance and RepVisits entries are linked.']"));
    }

    private WebElement optInOptOutInstruction() {
        return getDriver().findElement(By.xpath("//span[text()='Opt-In/Opt-Out: Once you choose to connect RepVisits and Naviance, you will have the option to disconnect the sync by returning to Naviance Settings.']"));
    }

    private By navianceSyncSettings() {
        return By.xpath("//span[text()='Naviance Sync Settings']");
    }

    private WebElement visitAvailability(String option) {
        return getDriver().findElement(By.xpath("//label[text()='" + option + "']"));
    }

    private By notificationAndTaskButton() {
        return By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Tasks']");
    }

    private By cityAndStateInActivity(String city, String state, String institution) {
        return By.xpath("//div/span[contains(text(),'" + StartTime + "')]/parent::div/preceding-sibling::div[text()='" + city + "," + state + "']/preceding-sibling::div/b[text()='" + institution + "']");
    }

    private WebElement verifyCityAndStateInActivity(String city, String state, String institution) {
        return getDriver().findElement(By.xpath("//div/span[contains(text(),'" + StartTime + "')]/parent::div/preceding-sibling::div[text()='" + city + "," + state + "']/preceding-sibling::div/b[text()='" + institution + "']"));
    }

    private By cityAndStateInActivityforFairs(String city, String state, String institution) {
        return By.xpath("//div[text()='" + city + "," + state + "']/preceding-sibling::div/b[text()='" + institution + "']");
    }

    private WebElement verifyCityAndStateInActivityforFairs(String city, String state, String institution) {
        return getDriver().findElement(By.xpath("//div[text()='" + city + "," + state + "']/preceding-sibling::div/b[text()='" + institution + "']"));
    }

    private By declineButtonInRequestNotification(String user, String university) {
        return By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Decline']");
    }

    private WebElement verifyDeclineButtonInRequestNotification(String user, String university) {
        return getDriver().findElement(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Decline']"));
    }

    private WebElement verifyConfirmButtonInRequestNotification(String user, String university) {
        return getDriver().findElement(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Confirm']"));
    }

    private WebElement userNameInRequestTab(String user) {
        return getDriver().findElement(By.xpath("//div[contains(text(),'" + user + "')]"));
    }

    private WebElement universityNameInRequestTab(String university) {
        return getDriver().findElement(By.xpath("//strong[text()='" + university + "']"));
    }

    private WebElement verifyDeclineButtonInRequestNotificationforFairs(String user, String university, String fairsDate, String fairsStartTime) {
        return getDriver().findElement(By.xpath("//div[contains(text(),'" + user + "')]/ancestor::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span/span[contains(text(),'" + fairsDate + "')]/parent::span[contains(text(),'" + fairsStartTime + "')]/../../following-sibling::div/button/span[text()='Decline']"));
    }

    private WebElement verifyConfirmButtonInRequestNotificationforFairs(String user, String university, String fairsDate, String fairsStartTime) {
        return getDriver().findElement(By.xpath("//div[contains(text(),'" + user + "')]/ancestor::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span/span[contains(text(),'" + fairsDate + "')]/parent::span[contains(text(),'" + fairsStartTime + "')]/../../following-sibling::div/button/span[text()='Confirm']"));
    }

    private By confirmButtonInRequestTab(String user, String university, String fairsDate, String fairsStartTime) {
        return By.xpath("//div[contains(text(),'" + user + "')]/ancestor::div/following-sibling::div/div/div/strong[contains(text(),'" + university + "')]/parent::div/following-sibling::div/span/span[contains(text(),'" + fairsDate + "')]/parent::span[contains(text(),'" + fairsStartTime + "')]/../../following-sibling::div/button/span[text()='Confirm']");
    }

    private WebElement getDate(String university) {
        return getDriver().findElement(By.xpath("//a[text()='" + university + "']/parent::div/parent::div/following-sibling::div/div/div/span[1]"));
    }

    private By internalNotesInReschedule() {
        return By.cssSelector("input[aria-label='Internal Notes']");
    }

    private WebElement timeInReschedulePopup(String time) {
        return getDriver().findElement(By.xpath("//div[@class='eight wide column']/div/span[text()='" + time + "']"));
    }

    private By reschedulePopupText() {
        return By.cssSelector("span[class='_25XyePHsmpWU1qQ18ojKip']>span");
    }

    private WebElement rescheduleDateButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui tiny button _3GJIUrSQadO6hk9FZvH28D']"));
    }

    private List<WebElement> verifyDeclineButton(String user, String institution, String date) {
        return getDriver().findElements(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + institution + "')]/parent::div/following-sibling::div/span/span[text()='" + date + "']/parent::span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Decline']"));
    }

    private List<WebElement> verifyConfirmButton(String user, String institution, String date) {
        return getDriver().findElements(By.xpath("//div[contains(text(),'" + user + "')]/parent::div/parent::div/following-sibling::div/div/div/strong[contains(text(),'" + institution + "')]/parent::div/following-sibling::div/span/span[text()='" + date + "']/parent::span[contains(text(),'" + StartTime + "')]/../../following-sibling::div/button/span[text()='Confirm']"));
    }

    private By calendarDayButton() {
        return By.cssSelector("button[title='Day']");
    }

    private List<WebElement> calendarAppointments(String startTime, String institution) {
        return getDriver().findElements(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + institution + "']"));
    }

    private List<WebElement> getMonthRow() {
        return getDriver().findElements(By.cssSelector("div.rbc-month-row"));
    }

    private List<WebElement> showMore(int index, int rowCount) {
        return getDriver().findElements(By.cssSelector("div[class='rbc-month-row']:nth-of-type(" + index + ")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type(" + rowCount + ")>div>a.rbc-show-more"));
    }

    private List<WebElement> showMoreButton(int firstIndex, int secondIndex, int rowCount) {
        return getDriver().findElements(By.cssSelector("div[class='rbc-month-row']:nth-of-type(" + firstIndex + ")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type(" + rowCount + ")>div:nth-of-type(" + secondIndex + ")>a.rbc-show-more"));
    }

    private WebElement selectShowMoreButton(int firstIndex, int secondIndex, int rowCount) {
        return getDriver().findElement(By.cssSelector("div[class='rbc-month-row']:nth-of-type(" + firstIndex + ")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type(" + rowCount + ")>div:nth-of-type(" + secondIndex + ")>a.rbc-show-more"));
    }

    private WebElement calendarAppointment(String startTime, String institution) {
        return getDriver().findElement(By.xpath("//span[text()='" + startTime + "']/following-sibling::span[text()='" + institution + "']"));
    }

    private By fairsCancelButton() {
        return By.cssSelector("button.ui.primary.right.floated.button._4kmwcVf4F-UxKXuNptRFQ span");
    }

    private WebElement fairsSettingsButton() {
        return getDriver().findElement(By.xpath("//a/span[text()='SETTINGS']"));
    }

    private WebElement addCollegeFairButton() {
        return getDriver().findElement(By.cssSelector("button[id='add-college']"));
    }

    private WebElement upcomingEventsHeading() {
        return getDriver().findElement(By.xpath("//div//span[text()='Upcoming Events']"));
    }

    private WebElement pastEventsHeading() {
        return getDriver().findElement(By.xpath("//div//span[text()='Past Events']"));
    }

    private WebElement setVisitsConfirmation(String option) {
        return getDriver().findElement(By.xpath("//div/label[text()='" + option + "']"));
    }

    private By cancelButtonIndisconnectRVfromNaviancePopup() {
        return By.cssSelector("button[class='ui teal basic button']");
    }

    private WebElement verifyAttendeeInNaviance(String attendee) {
        return getDriver().findElement(By.xpath("//td[contains(text(),'" + attendee + "')]"));
    }

    private String getVisitStartTimeInNaviance() {
        String[] time = StartTime.split("am");
        String startTime = time[0] + " " + "AM";
        return startTime;
    }

    private String getVisitReScheduledStartTimeInNaviance() {
        String[] time = RescheduleStartTimeforNewVisit.split("am");
        String startTime = time[0] + " " + "AM";
        return startTime;
    }

    private WebElement clickViewLink(String attendee, String time) {
        time = getVisitStartTimeInNaviance();
        return getDriver().findElement(By.xpath("//td[contains(text(),'" + attendee + "')]/following-sibling::td[contains(text(),'" + time + "')]/following-sibling::td/a[text()='view']"));
    }

    private List<WebElement> attendeeDetailsInNaviance(String attendee, String time) {
        time = getVisitStartTimeInNaviance();
        return getDriver().findElements(By.xpath("//td[contains(text(),'" + attendee + "')]/following-sibling::td[contains(text(),'" + time + "')]/following-sibling::td/a[text()='view']"));
    }

    private By primaryContact(String primaryContactName) {
        return By.xpath("//span[@class='text'][contains(text(), '" + primaryContactName + "')]");
    }

    private List<WebElement> regularWeeklyHoursTab() {
        return getDriver().findElements(By.xpath("//a//span[text()='Regular Weekly Hours']"));
    }

    private List<WebElement> optinYesRadioButton() {
        return getDriver().findElements(By.xpath("//label[text()='Yes, I would like to connect Naviance and RepVisits']"));
    }

    private List<WebElement> completePage() {
        return getDriver().findElements(By.xpath("//label[text()='All RepVisits Users']"));
    }

    private List<WebElement> availabilityandSettings() {
        return getDriver().findElements(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Availability & Settings']"));
    }

    private List<WebElement> takeMetoMyVisitsButton() {
        return getDriver().findElements(By.xpath("//button/span[text()='Take me to my visits']"));
    }

    private WebElement viewDetailsLink() {
        return link("View Details");
    }
    private List<WebElement> fairsSubmitButton(){
        return getDriver().findElements(By.xpath("//button/span[text()='Save']"));
    }
    private By closeFairPopup () { return By.cssSelector("button[class='ui circular icon button _1zaSIpaNy8bj4C9yOAOsXw']"); }

    private List<WebElement> errorMessage() {
        return getDriver().findElements(By.xpath("//div/span[text()='There were some errors with your submission']"));
    }

    private WebElement exportButton() {
        return getDriver().findElement(By.cssSelector("button[title='Export']"));
    }

    private By calendarMoreLinks() {
        return By.xpath("//a[@class='rbc-show-more']");
    }

    private WebElement overlayHeader() {
        return driver.findElement(By.cssSelector("div.rbc-overlay-header"));
    }

    private WebElement calendarsTitle() {
        return driver.findElement(By.cssSelector("div.ui.large.header"));
    }

    private String universityCalendarElementLocator(String startTime, String institution) {
        return "//span[text()='" + startTime
                + "']/following-sibling::span[text()='" + institution + "']";
    }

    private String timeCalendarElementLocator(String institution, String startTime) {
        return "//span[text()='" + institution
                + "']/preceding-sibling::span[text()='" + startTime + "']";
    }

    private List<WebElement> viewDetails() {
        return getDriver().findElements(By.xpath("//h2/span[text()='Upcoming Events']/parent::h2/following-sibling::table//span[text()='View Details']"));
    }

    private String pillInCalendarLocator = "div.rbc-event-content div";

    private String notesFieldLocator = "input[name='hsNotes']";

    private String viewDetailsLocator = "//span[text() = 'View Details']";

    private String cancelThisCollegeFairButtonLocator = "//span[text() = 'Cancel This College Fair']";

    private String yesCancelThisFairButtonLocator = "//span[contains(text(), 'Yes, Cancel this fair')]";

    private String cancelFairAndNotifyCollegesButtonLocator = "//span[contains(text(), 'Cancel fair and notify colleges')]";

    private String timePill(String specificTime) {
        return "//td/button[text()='" + specificTime + "']";
    }

    private String selectDateButtonLocator = "button.ui.small.fluid.button._3VnqII6ynYglzDU1flY9rw";

    private WebElement verifyCityAndStateInRequest(String city, String state, String institution) {
        return getDriver().findElement(By.xpath("//div/span[contains(text(),'" + StartTime + "')]/parent::div/preceding-sibling::div[text()='" + city + "," + state + "']/preceding-sibling::div/strong[text()='" + institution + "']"));
    }

    private WebElement cityAndStateInRequestTabforFairs(String city, String state, String institution) {
        return getDriver().findElement(By.xpath("//div[text()='" + city + "," + state + "']/preceding-sibling::div/strong[text()='" + institution + "']"));
    }

    private List<WebElement> cityAndStateInRequestforFairs(String city, String state, String institution) {
        return getDriver().findElements(By.xpath("//div[text()='" + city + "," + state + "']/preceding-sibling::div/strong[text()='" + institution + "']"));
    }

    private List<WebElement> cityAndStateInRequestTab(String city, String state, String institution) {
        return getDriver().findElements(By.xpath("//div/span[contains(text(),'" + StartTime + "')]/parent::div/preceding-sibling::div[text()='" + city + "," + state + "']/preceding-sibling::div/strong[text()='" + institution + "']"));
    }

    private List<WebElement> cityAndStateInActivityTabforFairs(String city, String state, String institution) {
        return getDriver().findElements(By.xpath("//div[text()='" + city + "," + state + "']/preceding-sibling::div/b[text()='" + institution + "']"));
    }

    private List<WebElement> verifyCityAndStateInActivityTab(String city, String state, String institution) {
        return getDriver().findElements(By.xpath("//div/span[contains(text(),'" + StartTime + "')]/parent::div/preceding-sibling::div[text()='" + city + "," + state + "']/preceding-sibling::div/b[text()='" + institution + "']"));
    }

    private WebElement closeButtonInRequestTab() {
        return getDriver().findElement(By.xpath("//span[text()='Close']"));
    }

    private List<WebElement> showmore() {
        return getDriver().findElements(By.xpath("//span[text()='Show More']"));
    }

    private WebElement showMoreButton() {
        return getDriver().findElement(By.xpath("//span[text()='Show More']"));
    }

    private String fairInCalendarLocator(String fairName) {
        return "//div[@class = 'rbc-event-content']//span[text() = '" + fairName + "']";
    }

    private String fairFormLocator = "form[id='college-fair-form']";

    private By requestTabUserName(String user) {
        return By.xpath("//div[contains(text(),'" + user + "')]");
    }

    private By requestTabCloseButton() {
        return By.xpath("//span[text()='Close']");
    }

    private String failedToCancelMessageLocator = "//div[@class = 'ui red message']//span[text() = 'Failed to cancel the appointment.']";

    private String noGoBackButtonLocator = "//button//span[text() = 'No, go back']";

    private String cancelMessageFieldLocator = "textarea#repVisit-cancelation-message";

    private String visitFormLocator = "div.pusher.dimmed._2W8TlpOgDDdSBCwym-4ZmD._1Sl5cBdXlfz2B_mV3fC2cp";

    private String pillInOverlayLocator = "div.rbc-overlay div.rbc-event-content";

    private WebElement collegeFairConfirmedCheckbox() {
        return driver.findElement(By.xpath("//label[text() = 'College Fair - Confirmed']"));
    }

    private String errorsWithASubmissionMessageLocator = "//div[@class = 'ui red message']//span[text() = 'There were some errors with your submission']";

    private WebElement fairCloseButton() {
        return getDriver().findElement(By.xpath("//button[text()='Close']"));
    }

    private String appointmentLocator = ".rbc-event-content";

    private WebElement verifyAnnouncementTitle(String announcementTitle){return getDriver().findElement(By.xpath("//div/b[text()='"+announcementTitle+"']"));}

    private By announcementTitle(String announcementTitle){return By.xpath("//div/b[text()='"+announcementTitle+"']");}

    private WebElement readMoreButton(){return button("Read More");}

    private WebElement announcementDismissButton(){return getDriver().findElement(By.cssSelector("i[class='close icon _3AcltzPxtgX0rUCbxyMhN_']"));}

    private WebElement announcementContent(String content){return getDriver().findElement(By.xpath("//span[text()='"+content+"']"));}

    private By content(String content){return By.xpath("//span[text()='"+content+"']");}

    private By dismissButton(){return By.cssSelector("i[class='close icon _3AcltzPxtgX0rUCbxyMhN_']");}

    private List<WebElement> announcementsDismissButton(){return getDriver().findElements(By.cssSelector("i[class='close icon _3AcltzPxtgX0rUCbxyMhN_']"));}

    private List<WebElement> readMorebutton(){return getDriver().findElements(By.xpath("//button[text()='Read More']"));}

    /**
     * @return return visit start time
     */

    private String getVisitStartTime(){
        String[] time=StartTime.split("am");
        String startTime=time[0]+" "+"AM";
        return startTime;
    }

    /**
     *
     * @return : colleges text
     */

    private WebElement collegeOptionInNaviancePage() {
        return getDriver().findElement(By.xpath("//li/a[text()='Colleges']"));
    }

    /**
     *
     * @return : visit text
     */
    private WebElement visitsInNavianceColleges() {
        return getDriver().findElement(By.xpath("//li/a[text()='Visits']"));
    }

    /**
     *
     * @return : max no of visits
     */
    private WebElement maxNoOfVisits() {
        return getDriver().findElement(By.id("maxStudentVisits"));
    }

    /**
     *
     * @return : max no of students
     */
    private WebElement maxNoOfStudents() {
        return getDriver().findElement(By.id("maxNumStudents"));
    }

    /**
     *
     * @return rsvp number
     */
    private WebElement visitDeadLine() {
        return getDriver().findElement(By.id("rsvpNumber"));
    }

    /**
     *
     * @return student notes
     */
    private WebElement studentNotes() {
        return getDriver().findElement(By.id("studentNotes"));
    }

    /**
     * @return return Reschedule visit start time
     */
    private String getVisitStartTimeforReschedule(){
        String[] time=RescheduleStartTimeforNewVisit.split("am");
        String startTime=time[0]+" "+"AM";
        return startTime;
    }

   private By timeSlotStartTime() {
        return By.cssSelector("input[title=\"start time\"]");
    }
    private  WebElement UnpublishedTextEle(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'success-message-grid')]//p")));
        return driver.findElement(By.xpath("//div[contains(@id,'success-message-grid')]//p"));
    }

    private  WebElement unpublishOption(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'ui button')]")));
        return   driver.findElement(By.xpath("//button[contains(@class,'ui button')]"));
    }

    private By setupWizardCheckmarkButton(){
        return By.cssSelector("i[class='checkmark circular icon']");
    }

    /**
     * Gets the Naviance Settings page locator
     * @return
     */
    private By navianceSettingsPageLocator(){
        return By.id("form-naviance-settings");
    }

    /**
     * return locator for add attendee manually in college fair
     * @return
     */
    private By addAttendeeManually(){
        return By.cssSelector("a[class='KKyfdym6DkswwZqeWN7Ck']");
    }

    /**
     * return locator for institution in college fair
     * @param institution
     * @return
     */
    private By getInstitution(String institution){
        return By.xpath("//div[text()='"+institution+"']");
    }

    /**
     * return locator for add attendee button
     * @return
     */
    private By addAttendeeButtonInCollegeFair(){
        return By.cssSelector("button[class='ui primary right floated button']");
    }

    /**
     * return locator attendee first name and last name
     * @param attendeeFirstNameAndLastName
     * @return
     */
    private List<WebElement> attendeeFirstNameAndLastName(String attendeeFirstNameAndLastName){
        return getDriver().findElements(By.xpath("//div[text()='"+attendeeFirstNameAndLastName+"']"));
    }

    private By attendeeFirstName() {
        return By.cssSelector("input#add-rep-first-name");
    }

    private By blockedDayInException(String currentDate){return By.xpath("//div/span[text()='" + currentDate + "']/parent::div/following-sibling::div/span[text()='Blocked - Holiday']");}

}
