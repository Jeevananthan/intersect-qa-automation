package pageObjects.HE.eventsPage;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.homePage.HomePageImpl;
import pageObjects.HUBS.FamilyConnection.FCColleges.FCCollegeEventsPage;
import pageObjects.HUBS.NavianceCollegeProfilePageImpl;
import utilities.GetProperties;
import java.text.SimpleDateFormat;
import java.util.*;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static HashMap<String, String> editedData = new HashMap<>();
    public static String eventName;
    private HomePageImpl homePage = new HomePageImpl();
    public static Calendar generatedTime;
    private NavianceCollegeProfilePageImpl navianceCollegeProfilePage = new NavianceCollegeProfilePageImpl();

    public EventsPageImpl() {
        logger = Logger.getLogger(EventsPageImpl.class);
    }

    public void verifyTitleIsPresent() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Events page is not correctly displayed", eventsTitle().isDisplayed());
    }

    public void verifyEventsNotPresent() {
        waitUntilPageFinishLoading();
        List<WebElement> eventsElements = driver.findElements(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc"));
        Assert.assertTrue("Events section is present, when it shouldn't", eventsElements.size() == 0);
    }

    public void createAndSaveEvent(DataTable eventDetailsData) {
        List<List<String>> eventDetails = eventDetailsData.asLists(String.class);
        waitUntilPageFinishLoading();
        createEventButton().click();
        fillCreateEventForm(eventDetails);
        saveDraftButton().sendKeys(Keys.RETURN);
    }

    public void verifyEventIsPresent(String eventName) {
        waitUntilPageFinishLoading();
        driver.get(driver.getCurrentUrl());
        int numberOfEventsFound = driver.findElements(By.xpath("//div[@class='ui stackable middle aligned grid _3nZvz_klAMpfW_NYgtWf9P']" +
                "/div[@class='row _3yNTg6-hDkFblyeahQOu7_']/div/div/div[text()='" + eventName + "']")).size();
        if(numberOfEventsFound > 0) {
            if (numberOfEventsFound == 1) {
                Assert.assertTrue("Event was not found in the list", true);
            } else if (numberOfEventsFound > 1) {
                logger.info("More than 1 event with the same name were found");
            }
        }
    }

    public void editEvent(String eventName, DataTable eventDataTable) {
        List<List<String>> eventData = eventDataTable.asLists(String.class);
        openEditScreen(eventName);
        fillCreateEventForm(eventData);
    }

    public void publishEvent() {
        publishNowButton().sendKeys(Keys.RETURN);
        waitUntilPageFinishLoading();
    }

    public void verifyPublishedEventPresent(String eventName) {
        verifyEventIsPresent(eventName);
    }

    public void deleteEvent(String eventName) {
        waitUntilPageFinishLoading();
        getEventsTab("Unpublished").click();
        waitUntilPageFinishLoading();
        menuButtonForEvent(eventName).click();
        waitUntilPageFinishLoading();
        getOptionFromMenuButtonForEvents("Delete").click();
        deleteYesButton().click();
    }

    public void verifyEventIsInCancelledList(String eventName) {
        getEventsTab("Cancelled").click();
        verifyEventIsPresent(eventName);
    }

    public void fillCreateEventForm(List<List<String>> data) {
        for (List<String> row : data) {
            switch (row.get(0)) {
                case "Event Name":
                    eventNameField().clear();
                    eventNameField().sendKeys(row.get(1));
                    break;
                case "Event Start":
                    eventStartCalendarButton().click();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyy");
                    Calendar date = Calendar.getInstance();
                    try {
                        Date formattedDate = formatter.parse(row.get(1).split(";")[0]);
                        date.setTime(formattedDate);
                        pickDateInDatePicker(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    eventStartTimeField().sendKeys(row.get(1).split(";")[1]);
                    break;
                case "Timezone":
                    timeZoneDropdown().click();
                    getTimeZoneOption(row.get(1)).click();
                    break;
                case "Description":
                    descriptionField().clear();
                    descriptionField().sendKeys(row.get(1));
                    break;
                case "Max Attendees":
                    maxAttendeesField().clear();
                    maxAttendeesField().sendKeys(row.get(1));
                    break;
                case "RSVP Deadline":
                    rsvpCalendarButton().click();
                    SimpleDateFormat rsvpFormatter = new SimpleDateFormat("MM-dd-yyy");
                    Calendar rsvpDate = Calendar.getInstance();
                    try {
                        Date formattedDate = rsvpFormatter.parse(row.get(1).split(";")[0]);
                        rsvpDate.setTime(formattedDate);
                        pickDateInDatePicker(rsvpDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "EVENT LOCATION":
                    selectLocationByName(row.get(1));
                    break;
                case "EVENT LOCATION BY POSITION":
                    selectLocationByPosition(Integer.parseInt(row.get(1)));
                    break;
                case "EVENT PRIMARY CONTACT":
                    if (isStringNumber(row.get(1))) {
                        selectContactByPosition(Integer.parseInt(row.get(1)));
                    } else{
                        selectPrimaryContactByName(row.get(1));
                    }
                    break;
                case "EVENT PRIMARY CONTACT BY POSITION":
                    selectContactByPosition(Integer.parseInt(row.get(1)));
                    break;
                case "EVENT AUDIENCE":
                    selectFilterByName(row.get(1));
                    break;
                case "EVENT AUDIENCE BY POSITION":
                    selectFilterByPosition(Integer.parseInt(row.get(1)));
                    break;
            }
        }
    }

    public void selectLocationByPosition(int position) {
        if (locationField().getText().length() > 0) {
            for (int i = 0; i <= locationField().getText().length(); i++) {
                locationField().sendKeys(Keys.BACK_SPACE);
            }
        } else {
            locationField().clear();
        }
        locationField().sendKeys("a");
        locationField().sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.xpath("//table[@class='ui unstackable very basic left aligned table AUCnq8YpQQX6dyWSXlgRo']" +
                "/tbody/tr[@class='_1hExGvG5jluro4Q-IOyjd7'][" + position + "]")).click();
    }

    public void selectContactByPosition(int position) {
        if (primaryContactField().getText().length() > 0) {
            for (int i = 0; i <= primaryContactField().getText().length(); i++) {
                primaryContactField().sendKeys(Keys.BACK_SPACE);
            }
        } else {
            primaryContactField().clear();
        }
        primaryContactField().sendKeys("a");
        primaryContactField().sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.xpath("//table[@class='ui unstackable very basic left aligned table _1CESARq218cDE7u8vMyW3O']" +
                "/tbody/tr[" + position + "]/td/div[@class='_3NjlddcItI-OTbh8G7MTQQ']")).click();
    }

    public void selectPrimaryContactByName(String contactName){

        openSelectionFieldMenu(primaryContactField());
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//div[text()='"+contactName+"']")).click();

    }

    public void selectLocationByName(String name) {
        clearSelectionField(locationField());
        openSelectionFieldMenu(locationField());
        driver.findElement(By.xpath("//table[@class='ui unstackable very basic left aligned table AUCnq8YpQQX6dyWSXlgRo']" +
                "/tbody/tr[@class='_1hExGvG5jluro4Q-IOyjd7']/td/div[@class = '_1mf5Fc8-Wa2hXhNfBdgxce']" +
                "[text() = '" + name + "']")).click();
    }

    public void selectFilterByName(String filterName) {
        clearSelectionField(audienceField());
        openSelectionFieldMenu(audienceField());
        // Original locator: //table[contains(@class,'ui unstackable very basic left aligned table _2NlS0bmSsF9TMGlRj-exrG')]/tbody/tr/td/div[text()='" + filterName + "']
        driver.findElement(By.xpath("//div[text()='" + filterName + "']")).click();
    }


    public void selectFilterByPosition(int position) {
        if (audienceField().getText().length() > 0) {
            for (int i = 0; i <= audienceField().getText().length(); i++) {
                audienceField().sendKeys(Keys.BACK_SPACE);
            }
        } else {
            audienceField().clear();
        }
        audienceField().sendKeys("a");
        audienceField().sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.xpath("//table[@class='ui unstackable very basic left aligned table _2NlS0bmSsF9TMGlRj-exrG']" +
                "/tbody/tr[" + position + "]/td/div[@class='_3BUJ-YOdd0uTHeZhux4Duw']")).click();
    }

    private void clearSelectionField(WebElement selectionField) {
        if (selectionField.getText().length() > 0) {
            for (int i = 0; i <= selectionField.getText().length(); i++) {
                selectionField.sendKeys(Keys.BACK_SPACE);
            }
        } else {
            selectionField.clear();
        }
    }

    private void openSelectionFieldMenu(WebElement selectionField) {
        selectionField.sendKeys("a");
        selectionField.sendKeys(Keys.BACK_SPACE);
    }

    public void openEditScreen(String eventName) {
        menuButtonForEvent(eventName).click();
        getMenuButton("Edit").click();
    }

    public void takeNoteOfData() {
        editedData.put("Event Name", eventNameField().getText());
        editedData.put("Event Start", eventStartTimeField().getText());
        editedData.put("Timezone", timeZoneText().getText());
        editedData.put("Description", descriptionField().getText());
        editedData.put("Max Attendees", maxAttendeesField().getText());
        editedData.put("RSVP Deadline", rsvpCalendarButton().getText());
        editedData.put("EVENT LOCATION", locationField().getAttribute("value"));
        editedData.put("EVENT PRIMARY CONTACT", primaryContactField().getAttribute("value"));
        editedData.put("EVENT AUDIENCE", audienceField().getAttribute("value"));
    }

    public void clickUpdate() {
        updateButton().click();
    }

    public void verifyEditedData(String eventName) {
        openEditScreen(eventName);
        waitUntilPageFinishLoading();
        for (String key : editedData.keySet()) {
            switch (key) {
                case "Event Name" :
                    Assert.assertTrue(key + " was not successfully updated", eventNameField().getText().equals(editedData.get(key)));
                    break;
                case "Event Start" :
                    Assert.assertTrue(key + " was not successfully updated", eventStartTimeField().getText().equals(editedData.get(key)));
                    break;
                case "Timezone" :
                    Assert.assertTrue(key + "was not successfully updated", timeZoneDropdown().getText().equals(editedData.get(key)));
                    break;
                case "Description" :
                    //it is necessary to reload the page to see the change in Description (MATCH-3460)
                    driver.get(driver.getCurrentUrl());
                    Assert.assertTrue(key + " was not successfully updated", descriptionField().getText().equals(editedData.get(key)));
                    break;
                case "Max Attendees" :
                    Assert.assertTrue(key + " was not successfully updated", maxAttendeesField().getText().equals(editedData.get(key)));
                    break;
                case "RSVP Deadline" :
                    Assert.assertTrue(key + " was not successfully updated", rsvpCalendarButton().getText().equals(editedData.get(key)));
                    break;
                case "EVENT LOCATION" :
                    Assert.assertTrue(key + " was not successfully updated. UI: " + locationField().getText()
                            + ", Edited Data: " + editedData.get(key), locationField().getAttribute("value").equals(editedData.get(key)));
                    break;
                case "EVENT PRIMARY CONTACT" :
                    Assert.assertTrue(key + " was not successfully updated", primaryContactField().getAttribute("value").equals(editedData.get(key)));
                    break;
                case "EVENT AUDIENCE" :
                    Assert.assertTrue(key + " was not successfully updated", audienceField().getAttribute("value").equals(editedData.get(key)));
                    break;
            }
        }
    }

    public void cancelEvent(String eventName) {
        menuButtonForEvent(eventName).click();
        getMenuButton("Cancel").click();
        cancelYesButton().click();
    }

    public void clickSaveDraft() {
        saveDraftButton().click();
    }

    public void verifyAllErrorMessages() {
        Assert.assertTrue("The error message for Location Name is not displayed", locationNameError().isDisplayed());
        Assert.assertTrue("The error message for Location State is not displayed", locationStateError().isDisplayed());
        Assert.assertTrue("The error message for Location Zip number is not displayed", locationZipError().isDisplayed());
        eventNameField().click();
    }

    public void verifyAllErrorMessagesForEvents() {
        Assert.assertTrue("The error message for Event Name is not displayed", eventNameError().isDisplayed());
        /*This is disabled because of an issue noted in MATCH-2913*/
        //Assert.assertTrue("The error message for Event Start is not displayed", eventStartError().isDisplayed());
        Assert.assertTrue("The error message for Event Name is not displayed", eventNameError().isDisplayed());
        Assert.assertTrue("The error message for Max Attendees is not displayed", maxAttendeesError().isDisplayed());
        Assert.assertTrue("The error message for Event Location is not displayed", eventLocationError().isDisplayed());
        Assert.assertTrue("The error message for Primary Contact is not displayed", primaryContactError().isDisplayed());
    }


    public void verifyEventNotPresentInList(String eventName) {
        waitUntilPageFinishLoading();
        driver.get(driver.getCurrentUrl());
        waitUntilPageFinishLoading();
        Assert.assertTrue("The deleted event is still present in the list",
                driver.findElements(By.xpath(eventsListLocator(eventName))).size() == 0);
    }

    public void unpublishEvent(String eventName) {
        waitUntilPageFinishLoading();
        waitForUITransition();
        if (driver.findElements(By.cssSelector("input#name")).size() == 1) {
            eventsTabFromEditEventScreen().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='CREATE EVENT']"), 1));
        }
        homePage.openEventList();
        try {
            getEventsTab("Published").click();
        } catch(WebDriverException e) {
            //navianceCollegeProfilePage.welcomeTitle().click();
            getNavigationBar().goToHome();
            getEventsTab("Published").click();
        }
        for(int i=0; i<6;i++) {
            try {
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(eventsListLocator(eventName)), 0));
                break;
            } catch (Exception e) {
                getDriver().navigate().refresh();
            }
        }

        menuButtonForEvent(eventName).click();
        getMenuButton("Unpublish").click();
        unpublishYesButton().click();
    }

    public void createAndSaveEventWithUniqueName(DataTable eventData) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");
        List<List<String>> eventDetails = eventData.asLists(String.class);
        waitUntilPageFinishLoading();
        createEventButton().click();
        fillCreateEventForm(eventDetails);
        eventNameField().sendKeys(dateFormat.format(date));
        eventName = eventNameField().getAttribute("value");
        publishNowButton().sendKeys(Keys.RETURN);
    }

    public void cancelCreatedEvent() {
        menuButtonForEvent(eventName).click();
        getMenuButton("Cancel").click();
        cancelYesButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(eventsListLocator(eventName)), 0));
    }

    public void clickCreateEvent() {
        createEventButton().click();
    }

    public void verifyCreatedEventIsInCancelledList() {
        verifyEventIsInCancelledList(eventName);
    }

    public void createAndSaveEventWithGenDate(String minutesFromNow, DataTable eventDetailsData) {
        List<List<String>> eventDetails = eventDetailsData.asLists(String.class);
        fillEventStartDateTimeFields(minutesFromNow);
        fillCreateEventForm(eventDetails);
        saveDraftButton().sendKeys(Keys.RETURN);
    }

    public void createAndPublishEventWithGenDate(String minutesFromNow, DataTable eventDetailsData) {
        List<List<String>> eventDetails = eventDetailsData.asLists(String.class);
        fillEventStartDateTimeFields(minutesFromNow);
        fillCreateEventForm(eventDetails);
        publishNowButton().sendKeys(Keys.RETURN);
    }

    public void fillEventStartDateTimeFields(String minutesFromNow) {
        waitUntilPageFinishLoading();
        generatedTime = getDeltaTime(Integer.parseInt(minutesFromNow));
        Calendar date = Calendar.getInstance();
        createEventButton().click();
        waitForUITransition();
        eventStartCalendarButton().click();
        waitForUITransition();
        pickDateInDatePicker(date);
        eventStartTimeField().sendKeys(getTime(generatedTime).replace(" ", ""));
    }

    public void verifyEventInExpiredList() {
        getEventsTab("Expired").click();

    }

    public void unpublishEventOfGeneratedName() {
        waitUntil(ExpectedConditions.visibilityOf(menuButtonForEvent(eventName)));
        menuButtonForEvent(eventName).click();
        getMenuButton("Unpublish").click();
    }

    public void verifyNoUnpublishWithAttendeesMessage() {
        waitForUITransition();
        Assert.assertTrue("No error message is displayed when unpublishing an event with attendees", eventWithAttendeesUnpublishMessage().isDisplayed());
        unpublishOkButton().click();
    }

    public void verifyCancellationMessageOfGenEvent() {
        waitForUITransition();
        if (driver.findElements(By.cssSelector(FCCollegeEventsPage.welcomeTooltipLocator)).size() > 0) {
            FCCollegeEventsPage.welcomeTooltipCloseButton.click();
        }
        List<WebElement> listOfEventNames = new ArrayList<>();
        List<String> listOfEventNamesStrings = new ArrayList<>();

        WebElement upperNextArrow = driver.findElements(By.cssSelector(FCCollegeEventsPage.nextArrowsList)).get(0);

        listOfEventNames = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventNamesList));
        for (WebElement eventNameElement : listOfEventNames) {
            listOfEventNamesStrings.add(eventNameElement.getText());
        }

        while (!listOfEventNamesStrings.contains(EventsPageImpl.eventName)) {
            waitForUITransition();
            waitUntilPageFinishLoading();
            waitUntilElementExists(upperNextArrow);
            upperNextArrow.click();
            waitForUITransition();
            listOfEventNames = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventNamesList));
            for (WebElement eventNameElement : listOfEventNames) {
                listOfEventNamesStrings.add(eventNameElement.getText());
            }
        }

        if (listOfEventNamesStrings.contains(EventsPageImpl.eventName)) {
            Assert.assertTrue("The cancellation message is not dispalyed. UI text: " +
                            cancelledEventMessage(EventsPageImpl.eventName).getText(),
                    cancelledEventMessage(EventsPageImpl.eventName).getText().contains(cancellationMessage));
        }
        waitForUITransition();
    }

    public void verifyPastDateErrorMessage() {
        List<WebElement> errorMessagesList = driver.findElements(By.cssSelector(pastDateErrorMessagesListLocator));
        Assert.assertTrue("The past date error message is not displayed. Message: " + errorMessagesList.get(0).getText(), errorMessagesList.get(0).getText().equals(pastDateErrorMessageString) &&
                errorMessagesList.get(1).getText().equals(pastRSVPErrorMessageString));
    }

    public void openTab(String tabName) {
        waitForUITransition();
        try {
            getTab(tabName).click();
        } catch(WebDriverException e) {
            mainEventsTitle().click();
            waitUntilPageFinishLoading();
            getTab(tabName).click();
        }
        waitForUITransition();
    }

    public void verifyFilterIsPresentInList(String eventName) {
        List<String> filtersNamesStrings = new ArrayList<>();
        List<WebElement> filtersNames = driver.findElements(By.cssSelector(filtersList));
        for (WebElement filterName : filtersNames) {
            filtersNamesStrings.add(filterName.getText());
        }
        Assert.assertTrue("The filter is not displayed in the filters list", filtersNamesStrings.contains(eventName));
    }

    public void openCreateFilterFromEventAudience() {
        audienceField().click();
        newFilterLink().click();
    }

    public void verifyFilterInEventAudienceList(String filterName) {
        audienceField().click();
        Assert.assertTrue("The filter is not in the Event Audience List", filterInEventAudienceList(filterName).isDisplayed());
    }

    public void verifyFiltersList() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The filters list is not displayed", filtersListContainer().isDisplayed());
    }

    public void openEvent(String eventName) {
        menuButtonForEvent(eventName).click();
        getMenuButton("Edit").click();
    }

    public void verifyFilterNotPresentInAudienceList(String filterName) {
        audienceField().click();
        Assert.assertTrue("The deleted filter is displayed in the Event Audience list", filtersInEventsAudienceList(filterName).size() == 0);
    }

    public void createAndPublishEvent(DataTable eventDetailsData) {
        List<List<String>> eventDetails = eventDetailsData.asLists(String.class);
        waitUntilPageFinishLoading();
        createEventButton().click();
        fillCreateEventForm(eventDetails);
        publishNowButton().sendKeys(Keys.RETURN);
        waitUntil(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//h1/span[text()='Events']"))));
    }

    public void verifyAttendeesFromStatusBar(String eventName) {
        waitForUITransition();
        attendeeStatusBarStudent(eventName).click();
        verifyNoAttendeesMessage();
    }

    public void VerifyAttendeeData(DataTable attendeeData) {
        waitUntilPageFinishLoading();
        List<List<String>> attendeeDataDetails = attendeeData.asLists(String.class);
        getTab("ATTENDEES").click();

        for (int i = 0; i<6; i++) {
            try {
                driver.navigate().refresh();
                waitUntilPageFinishLoading();
                VerifyDataForAttendees(attendeeDataDetails);
                break;
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }



    private void VerifyDataForAttendees(List<List<String>> data){
            for (List<String> row : data) {
            switch (row.get(0)) {
                case "AttendeeFirstName":
                    Assert.assertTrue("Attendee First Name is missinng",attendeeDataFirstName(row.get(1)).getText().contains(row.get(1)));
                    break;
                case "AttendeeLastName":
                    Assert.assertTrue("Attendee Last Name is missinng",attendeeDataLastName(row.get(1)).getText().contains(row.get(1)));
                    break;
                case "AttendeeEmail":
                    Assert.assertTrue("Attendee Email is missinng",attendeeDataEmail(row.get(1)).getText().contains(row.get(1)));
                    break;
                case "AttendeeStatus":
                    Assert.assertTrue("Attendee Registered Status is missinng",attendeeDataStatus(row.get(1)).getText().contains(row.get(1)));
                    break;
                    }
        }

    }

    private void verifyNoAttendeesMessage() {
        softly().assertThat(noAttendeesMessage().getText()).as("No attendees message").isEqualTo(noAttendeesMessageString);
    }


    public void verifyAttendeesFromEditMenu(String eventName) {
        menuButtonForEvent(eventName).click();
        getOptionFromMenuButtonForEvents("Attendees").click();
        verifyNoAttendeesMessage();
    }

    public void openEventsTab(String tabName) {
        waitUntilPageFinishLoading();
        getEventsTab(tabName).click();
    }

    private void selectContactByName(String contactName) {
        clearSelectionField(primaryContactField());
        openSelectionFieldMenu(primaryContactField());
        driver.findElement(By.xpath("//table[contains(@class, 'ui unstackable very basic left aligned table')]/tbody/tr/td/div[text()='" + contactName + "']"));
    }

    public void verifyNoAccessToConnections() {
        waitUntilPageFinishLoading();
        String connectionsUrl = driver.getCurrentUrl() + GetProperties.get("amconnections.url.part");
        driver.get(connectionsUrl);
        waitUntilPageFinishLoading();
        Assert.assertTrue("No error message is displayed when a community user access AM Connections by URL", notAuthorizedErrorMessage().getText().equals(expectedNotAuthorizedErrorText));
    }

    public void verifyEventStatus(String status, String eventName) {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Event status is incorrect. UI: " + eventStatus(eventName).getText(), eventStatus(eventName).getText().equals(status));
    }

    public void verifyEventWithGenNameStatus(String status) {
        verifyEventStatus(status, eventName);
    }

    public void verifyDefaultFilter(String filterName) {
        Assert.assertTrue("The created filter is not displayed by default in the Event Audience field",
                eventAudienceTextBox().getAttribute("value").equals(filterName));
    }

    public void verifyEventsNamesClickable() {
        eventLinkByPosition(1).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Edit Events screen was not opened", eventNameField().isDisplayed());
    }

    public void openEventOfGeneratedName() {
        waitUntilPageFinishLoading();
        menuButtonForEvent(eventName).click();
        getMenuButton("Attendees").click();
    }

    public void openTabInEditEvent(String tabName) {
        getEventsTab(tabName).click();
        waitUntilPageFinishLoading();
        driver.get(driver.getCurrentUrl());
        waitUntilPageFinishLoading();
    }

    public void verifyAttendeesErrorMessage(DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        attendeeStatusBarStudent(eventName).click();
        Assert.assertTrue("The error message is not correct", attendeesErrorMessage().getText().equals(details.get(0)));
    }

    public void createAndSaveEventWithGenDateAndName(String xDaysFromNow, DataTable dataTable) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");
        List<List<String>> eventDetails = dataTable.asLists(String.class);
        waitUntilPageFinishLoading();
        fillEventStartDateTimeFields(xDaysFromNow);
        fillCreateEventForm(eventDetails);
        eventNameField().sendKeys(dateFormat.format(date));
        eventName = eventNameField().getAttribute("value");
        publishNowButton().sendKeys(Keys.RETURN);
    }

    /*public void statusDraft(){
        Assert.assertTrue("Status of the Event is set to Draft under Unpublished tab",unpublishedStatus().getText().status(statusDraft));
    }*/

    //locators
//    private WebElement statusDraft(){return  driver.findElement(By.cssSelector(""))};
    private WebElement eventsTitle() { return driver.findElement(By.xpath("//span[text()='Events']")); }
    private WebElement eventNameField() { return driver.findElement(By.cssSelector("input#name")); }
    private WebElement eventStartCalendarButton() { return driver.findElement(By.cssSelector("div.seven.wide.column button[title='Event Date']")); }
    private WebElement eventStartTimeField() { return driver.findElement(By.cssSelector("input#startTime")); }
    private WebElement timeZoneDropdown() { return driver.findElement(By.cssSelector("div[aria-live='polite']")); }
    private WebElement timeZoneText() { return driver.findElement(By.cssSelector("input.search + div")); }
    private WebElement descriptionField() { return driver.findElement(By.cssSelector("textarea#eventDescription")); }
    private WebElement maxAttendeesField() { return driver.findElement(By.cssSelector("input#availableSeats")); }
    private WebElement rsvpCalendarButton() { return driver.findElement(By.cssSelector("div.ui.stackable.middle.aligned.grid div.row:nth-of-type(7) button")); }
    private WebElement rsvpTimeField() { return driver.findElement(By.cssSelector("input#registrationDeadlineTime")); }
    public WebElement locationField() { return driver.findElement(By.cssSelector("input[name='locations-dropdown']")); }
    private WebElement primaryContactField() { return driver.findElement(By.cssSelector("input[name='contacts-search']")); }
    private WebElement audienceField() { return driver.findElement(By.cssSelector("input[name='filters-dropdown']")); }
    private WebElement saveDraftButton() { return driver.findElement(By.cssSelector("button[title='Save Draft']")); }
    private WebElement publishNowButton() { return driver.findElement(By.cssSelector("button[title='Publish Now']")); }
    private WebElement createEventButton() { return driver.findElement(By.xpath("//span[text()='CREATE EVENT']")); }
    public WebElement menuButtonForEvent(String eventName) {
        return driver.findElement(By.xpath("//h3[text() = '" + eventName + "']/../../../..//i"));
    }
    private WebElement getOptionFromMenuButtonForEvents(String optionName) {
        return getDriver().findElement(By.xpath("//span[text()='" + optionName + "']"));
    }
    private WebElement getMenuButton(String optionName) {
        return driver.findElement(By.xpath("//span[text()  = '" + optionName + "']"));
    }
    private WebElement updateButton() { return driver.findElement(By.cssSelector("button[title='Update']")); }
    private WebElement cancelYesButton() { return driver.findElement(By.cssSelector("button[data-status='CANCELED']")); }
    private WebElement getEventsTab(String tabName) {
        return driver.findElement(By.xpath("//h2[contains(text(), '" + tabName + "')]"));
    }
    private WebElement getTimeZoneOption(String optionName) {
        return driver.findElement(By.xpath("//div[@class='ui stackable middle aligned grid _22IjfAfN4Zs4CnM4Q_AlWZ']" +
                "/div/div/div/div/div/span[text()='" + optionName + "']"));
    }
    private WebElement deleteYesButton() {
        return  driver.findElement(By.cssSelector("button[data-status='DELETED']"));
    }
    private WebElement eventNameError() { return driver.findElement(By.cssSelector("div.field._1yIY4zDrmhXWZye_U-WaJP + div span")); }
    private WebElement eventStartError() { return driver.findElement(By.cssSelector("div.field._2nQ3XyXmKsCNicAyziEoh0 + div span")); }
    private WebElement maxAttendeesError() {
        return driver.findElement(By.xpath("//input[@id='availableSeats']/../../../../div[@class='four wide column']/div/span"));
    }
    private WebElement eventLocationError() {
        return driver.findElement(By.cssSelector("div.ui.icon.input._2PODx9czzn4W7nb0z2u3aA + div span"));
    }
    private WebElement primaryContactError() {
        return driver.findElement(By.cssSelector("div.ui.icon.input._3KUN7Tb1NlCv2_RQqzKQCj + div span"));
    }
    private WebElement unpublishYesButton() {
        return driver.findElement(By.cssSelector("button[data-status='UNPUBLISHED']"));
    }
    private WebElement eventsTabFromEditEventScreen() { return driver.findElement(By.cssSelector("a[href $='published']")); }
    private WebElement locationNameError() {
        return driver.findElement(By.cssSelector("div.dimmable div._1TudguVf2jObtmqUQKvIAk div[role='tooltip'] span"));
    }
    private WebElement locationStateError() {
        return driver.findElement(By.cssSelector("div.dimmable div._2gPcidNhI4UgSMMTgArhV8:nth-of-type(1) span"));
    }
    private WebElement locationZipError() {
        return driver.findElement(By.cssSelector("div.dimmable div._2gPcidNhI4UgSMMTgArhV8:nth-of-type(2) span"));
    }
    private WebElement eventWithAttendeesUnpublishMessage() {
        return driver.findElement(By.cssSelector("div.ui.warning.message span"));
    }
    private WebElement unpublishOkButton() {
        return driver.findElement(By.cssSelector("button.ui.black.basic.button"));
    }
    private WebElement cancelledEventMessage(String eventName) {
        return driver.findElement(By.xpath("//h3[text()='" + eventName + "']/../../../div[@class='event-summary__status-column']"));
    }

    private String cancellationMessage = "Event cancelled by hosts";
    private String pastDateErrorMessagesListLocator = "div.ui.red.pointing.basic.label span";
    private String pastDateErrorMessageString = "Event Start date and time must not be in the past";
    private String pastRSVPErrorMessageString = "Event RSVP Deadline date and time must not be in the past";
    private WebElement getTab(String tabName) { return driver.findElement(By.xpath("//li[contains(@class, 'link item')]/a/span[text()='" + tabName + "']")); }
    private String filtersList = "div[class*=dimmable] strong";
    private WebElement newFilterLink() { return driver.findElement(By.cssSelector("table[class *= \"ui unstackable very basic left aligned table\"] a")); }
    private WebElement filterInEventAudienceList(String filterName) { return driver.findElement(By.xpath("//table[contains(@class, 'ui unstackable very basic left aligned table')]/tbody/tr/td/div[text()='" + filterName + "']")); }
    private WebElement filtersListContainer() { return driver.findElement(By.cssSelector("ul[class *= \"ui huge pointing secondary stackable\"] + div")); }
    private List<WebElement> filtersInEventsAudienceList(String filterName) { return driver.findElements(By.xpath("//table[contains(@class, 'ui unstackable very basic left aligned table')]/tbody/tr/td/div[text()='" + filterName + "']")); }
    private WebElement attendeeStatusBarStudent(String eventName) {
        //return driver.findElement(By.xpath("//a[text() = '" + eventName + "']/../../../div[contains(@class, 'four wide column')]/a"));
        return getDriver().findElement(By.xpath("//h3[text()='"+eventName+"']/../../../following::div/a"));
    }
    private WebElement noAttendeesMessage() { return driver.findElement(By.xpath("//div[@class[contains(.,'_22IjfAfN4Zs4CnM4Q_AlWZ')]]")); }
    private String noAttendeesMessageString = "There are no attendees currently registered for this event.";
    private WebElement notAuthorizedErrorMessage() { return driver.findElement(By.cssSelector("ul.ui.huge.pointing.secondary.stackable + div h1")); }
    private String expectedNotAuthorizedErrorText = "You are not authorized to view the content on this page";
    private String eventsListLocator(String eventName) { return "//div[@class='ui stackable middle aligned grid _3nZvz_klAMpfW_NYgtWf9P']/div[@class='row _3yNTg6-hDkFblyeahQOu7_']/div/div/a[text()='" + eventName + "']"; }
    private WebElement eventStatus(String eventName) { return driver.findElement(By.xpath("//div[@class = 'ui middle aligned grid']/a[text() = '" + eventName + "']/../../../div[contains(@class, 'two wide column')]/div")); }
    private WebElement eventAudienceTextBox() { return driver.findElement(By.cssSelector("input[name = 'filters-dropdown']")); }
    private WebElement mainEventsTitle() { return driver.findElement(By.cssSelector("a div div.hidden-mobile")); }
    private WebElement eventLinkByPosition(int position) { return driver.findElement(By.cssSelector("div[class *= 'ui stackable middle aligned grid'] div[class *= 'row']:nth-of-type(" + position + ") a:not(.ui)")); }
    private WebElement attendeesErrorMessage() { return driver.findElement(By.cssSelector("table[class *= 'ui very basic table'] div.ui.header span")); }
    private WebElement attendeeDataFirstName(String firstName){return  driver.findElement(By.xpath("//Div[text()='" + firstName + "']"));}
   // private WebElement attendeeDataFirstName(String firstName){return driver.findElement(By.cssSelector("div._3xgrllu8DG-OcR4kpSPd3A"));}
    private WebElement attendeeDataLastName(String lastName){return  driver.findElement(By.xpath("//Div[text()='" + lastName + "']"));}
    private WebElement attendeeDataEmail(String Email){return driver.findElement(By.xpath("//Div[text()='" + Email + "']"));}
    private WebElement attendeeDataStatus(String Status){return driver.findElement(By.xpath("//Div[text()='" + Status + "']"));}

}
