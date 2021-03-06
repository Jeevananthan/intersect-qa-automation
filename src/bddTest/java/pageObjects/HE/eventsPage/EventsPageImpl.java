package pageObjects.HE.eventsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.homePage.HomePageImpl;
import pageObjects.HUBS.FamilyConnection.FCColleges.FCCollegeEventsPage;
import pageObjects.HUBS.NavianceCollegeProfilePageImpl;
import utilities.GetProperties;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EventsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static HashMap<String, String> editedData = new HashMap<>();
    public static String staticEventName;
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
        saveDraftButton().click();
        //saveDraftButton().sendKeys(Keys.RETURN);
    }

    public void verifyEventIsPresent(String eventName) {
        eventName = eventName;
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(getEventsInternalTab("Unpublished")));
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
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
        verifyEventIsPresent(eventName);
    }

    public void deleteEvent(String eventName) {
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
        waitUntilPageFinishLoading();
        getEventsInternalTab("Unpublished").click();
        waitUntilPageFinishLoading();
        menuButtonForEvent(eventName).click();
        waitUntilPageFinishLoading();
        getOptionFromMenuButtonForEvents("Delete").click();
        deleteYesButton().click();
    }

    public void verifyEventIsInCancelledList(String eventName) {
        getEventsInternalTab("Cancelled").click();
        verifyEventIsPresent(eventName);
    }

    /**
     * Randomizes a passed event name by adding a random number between 1 and 99,999 to the end
     * @param eventName - Base of the name to be used.  Random number will be appended to this
     * @return String - Randomized event name
     */
    public String randomizeEventName(String eventName) {
        String randomNo = Integer.toString(new Random().nextInt(99999));
        logger.info("random suffix = " + randomNo + "\n");
        String newEventName = eventName + "" + randomNo;
        logger.info("New Event Name = " + newEventName + "\n");
        return newEventName;
    }

    public void fillCreateEventForm(List<List<String>> data) {
        for (List<String> row : data) {
            switch (row.get(0)) {
                case "Event Name":
                    if (row.get(1).equalsIgnoreCase("RandomEventName")) {
                        staticEventName = randomizeEventName("RandomEventName");
                        eventNameField().clear();
                        eventNameField().sendKeys(staticEventName);
                    } else {
                        eventNameField().clear();
                        eventNameField().sendKeys(row.get(1));
                    }
                    break;
                case "Event Start":
                    eventStartCalendarButton().click();
                    String formattedDate = row.get(1).split(";")[0];
                    setSpecificDate(formattedDate,"MMMM dd yyyy");
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
                    formattedDate = row.get(1).split(";")[0];
                    setSpecificDate(formattedDate,"MMMM dd yyyy");
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

    public void editEventToPublish(String clickEventName){
        if (clickEventName.equalsIgnoreCase("RandomEventName"))
            clickEventName = staticEventName;
        editEventClick(clickEventName).click();

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
        // The data on the form doesn't load with the page, so we need to wait for that to populate first.
        waitUntil(ExpectedConditions.attributeContains(eventNameField(),"value",eventName));
        for (String key : editedData.keySet()) {
            switch (key) {
                case "Event Name" :
                    softly().assertThat(eventNameField().getText()).as("Event Name").isEqualTo(editedData.get(key));
                    break;
                case "Event Start" :
                    softly().assertThat(eventStartTimeField().getText()).as("Event Start").isEqualTo(editedData.get(key));
                    break;
                case "Timezone" :
                    softly().assertThat(timeZoneDropdown().getText()).as("Timezone").isEqualTo(editedData.get(key));
                    break;
                case "Description" :
                    softly().assertThat(descriptionField().getText()).as("Description").isEqualTo(editedData.get(key));
                    break;
                case "Max Attendees" :
                    softly().assertThat(maxAttendeesField().getText()).as("Max Attendees").isEqualTo(editedData.get(key));
                    break;
                case "RSVP Deadline" :
                    softly().assertThat(rsvpCalendarButton().getText()).as("RSVP Deadline").isEqualTo(editedData.get(key));
                    break;
                case "EVENT LOCATION" :
                    softly().assertThat(locationField().getAttribute("value")).as("Event Location").isEqualTo(editedData.get(key));
                    break;
                case "EVENT PRIMARY CONTACT" :
                    softly().assertThat(primaryContactField().getAttribute("value")).as("Event Primary Contact").isEqualTo(editedData.get(key));
                    break;
                case "EVENT AUDIENCE" :
                    softly().assertThat(audienceField().getAttribute("value")).as("Event Audience").isEqualTo(editedData.get(key));
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
        waitUntilPageFinishLoading();
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
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
        waitUntilPageFinishLoading();
        driver.get(driver.getCurrentUrl());
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(getEventsInternalTab("Unpublished")));
        Assert.assertTrue("The deleted event is still present in the list",
                driver.findElements(By.xpath(eventsListLocator(eventName))).size() == 0);
    }

    public void unpublishEvent(String eventName) {
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(getEventsInternalTab("Published")));
        if (driver.findElements(By.cssSelector("input#name")).size() == 1) {
            eventsTabFromEditEventScreen().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='CREATE EVENT']"), 1));
        }
        homePage.openEventList();
        try {
            getEventsInternalTab("Published").click();
        } catch(WebDriverException e) {
            getNavigationBar().goToHome();
            getEventsInternalTab("Published").click();
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
        staticEventName = eventNameField().getAttribute("value");
        publishNowButton().sendKeys(Keys.RETURN);
    }

    public void cancelCreatedEvent() {
        menuButtonForEvent(staticEventName).click();
        getMenuButton("Cancel").click();
        cancelYesButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(eventsListLocator(staticEventName)), 0));
    }

    public void clickCreateEvent() {
        createEventButton().click();
    }

    public void verifyCreatedEventIsInCancelledList() {
        verifyEventIsInCancelledList(staticEventName);
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
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(loadingIconLocator), 1));
    }

    public void EditAndPublishEventWithGenDate (String minutesFromNow, DataTable eventDetailsData) {
        List<List<String>> eventDetails = eventDetailsData.asLists(String.class);
        editfillEventStartDateTimeFields(minutesFromNow);
        fillCreateEventForm(eventDetails);
        publishNowButton().sendKeys(Keys.RETURN);
    }

    public void fillEventStartDateTimeFields(String minutesFromNow) {
        waitUntilElementExists(createEventButton());
        generatedTime = getDeltaTime(Integer.parseInt(minutesFromNow));
        Calendar date = Calendar.getInstance();
        createEventButton().click();
        waitUntil(ExpectedConditions.visibilityOf(eventStartCalendarButton()));
        eventStartCalendarButton().click();
        try {
            pickDateInDatePicker(date);
        } catch (NoSuchElementException e) {
            eventStartCalendarButton().click();
            pickDateInDatePicker(date);
        }
        eventStartTimeField().sendKeys(getTime(generatedTime).replace(" ", ""));
    }

    public void editfillEventStartDateTimeFields(String minutesFromNow) {
        waitUntilPageFinishLoading();
        generatedTime = getDeltaTime(Integer.parseInt(minutesFromNow));
        Calendar date = Calendar.getInstance();
        waitUntil(ExpectedConditions.visibilityOf(eventStartCalendarButton()));
        eventStartCalendarButton().click();
        try {
            pickDateInDatePicker(date);
        } catch (NoSuchElementException e) {
            eventStartCalendarButton().click();
            pickDateInDatePicker(date);
        }
        eventStartTimeField().sendKeys(getTime(generatedTime).replace(" ", ""));
    }
    public void verifyEventInExpiredList() {
        getEventsTab("Expired").click();

    }

    public void unpublishEventOfGeneratedName() {
        waitUntil(ExpectedConditions.visibilityOf(menuButtonForEvent(staticEventName)));
        menuButtonForEvent(staticEventName).click();
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
        List<WebElement> listOfEventNames;
        List<String> listOfEventNamesStrings = new ArrayList<>();

        if (driver.findElements(By.cssSelector(FCCollegeEventsPage.nextArrowsList)).size() > 0) {
            while (!listOfEventNamesStrings.contains(EventsPageImpl.staticEventName)) {
                waitForUITransition();
                waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(collegeNameHeader), 0));
                listOfEventNames = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventNamesList));
                for (WebElement eventNameElement : listOfEventNames) {
                    listOfEventNamesStrings.add(eventNameElement.getText());
                }
                if (listOfEventNamesStrings.contains(EventsPageImpl.staticEventName)) {
                    Assert.assertTrue("The cancellation message is not dispalyed. UI text: " +
                                    cancelledEventMessage(EventsPageImpl.staticEventName).getText(),
                            cancelledEventMessage(EventsPageImpl.staticEventName).getText().contains(cancellationMessage));
                    break;
                }
                driver.findElements(By.cssSelector(FCCollegeEventsPage.nextArrowsList)).get(0).click();
            }
        } else {
            listOfEventNames = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventNamesList));
            for (WebElement eventNameElement : listOfEventNames) {
                listOfEventNamesStrings.add(eventNameElement.getText());
            }
            if (listOfEventNamesStrings.contains(EventsPageImpl.staticEventName)) {
                Assert.assertTrue("The cancellation message is not dispalyed. UI text: " +
                                cancelledEventMessage(EventsPageImpl.staticEventName).getText(),
                        cancelledEventMessage(EventsPageImpl.staticEventName).getText().contains(cancellationMessage));
            }
        }
        waitForUITransition();
    }

    public void verifyPastDateErrorMessage() {
        List<WebElement> errorMessagesList = driver.findElements(By.cssSelector(pastDateErrorMessagesListLocator));
        Assert.assertTrue("The past date error message is not displayed. Message: " + errorMessagesList.get(0).getText(), errorMessagesList.get(0).getText().equals(pastDateErrorMessageString) &&
                errorMessagesList.get(1).getText().equals(pastRSVPErrorMessageString));
    }

    public void openTab(String tabName) {
        try {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(eventsTab(tabName)));
            waitUntil(ExpectedConditions.visibilityOfElementLocated(eventsHeader()));
            getEventsTab(tabName).click();
        } catch(WebDriverException e) {
            mainEventsTitle().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(eventsTab(tabName)));
            waitUntil(ExpectedConditions.visibilityOfElementLocated(eventsHeader()));
            getEventsTab(tabName).click();
        }

         //added below wait instead of waitForUITransition
         waitUntilPageFinishLoading();
        //Commenting the below line to increase the performance
        //waitForUITransition();
    }

    public void verifyFilterIsPresentInList(String eventName) {
        waitUntilPageFinishLoading();
        List<String> filtersNamesStrings = new ArrayList<>();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(filter(eventName)));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(filtersList)));
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
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
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
        waitUntil(ExpectedConditions.elementToBeClickable(getEventsInternalTab("ATTENDEES")));
        softly().assertThat(noAttendeesMessage().getText()).as("No attendees message").isEqualTo(noAttendeesMessageString);
    }


    public void verifyAttendeesFromEditMenu(String eventName) {
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
        menuButtonForEvent(eventName).click();
        getOptionFromMenuButtonForEvents("Attendees").click();
        verifyNoAttendeesMessage();
    }

    public void openEventsTab(String tabName) {
        waitUntilPageFinishLoading();
        getEventsInternalTab(tabName).click();
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
        if (eventName.equalsIgnoreCase("RandomEventName"))
            eventName = staticEventName;
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Event status is incorrect. UI: " + eventStatus(eventName).getText(), eventStatus(eventName).getText().equals(status));
    }

    public void verifyEventWithGenNameStatus(String status) {
        verifyEventStatus(status, staticEventName);
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
        menuButtonForEvent(staticEventName).click();
        getMenuButton("Attendees").click();
    }

    public void openTabInEditEvent(String tabName) {
        getEventsInternalTab(tabName).click();
        waitUntilPageFinishLoading();
        driver.get(driver.getCurrentUrl());
        waitUntilPageFinishLoading();
    }

    public void verifyAttendeesErrorMessage(DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        attendeeStatusBarStudent(staticEventName).click();
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
        staticEventName = eventNameField().getAttribute("value");
        publishNowButton().sendKeys(Keys.RETURN);
    }

    /*public void statusDraft(){
        Assert.assertTrue("Status of the Event is set to Draft under Unpublished tab",unpublishedStatus().getText().status(statusDraft));
    }*/

    public void deleteAllUnpublishedEventsOfName(String eventName) {
        List<WebElement> eventsToDeleteList = driver.findElements(By.xpath(unpublishedEventsEllipsisLocator(eventName)));
        for (WebElement eventEllipsis : eventsToDeleteList) {
            eventEllipsis.click();
            getMenuButton("Delete").click();
            deleteYesButton().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(progressBarLocator), 0));
        }
    }

    /**
     * select date by given value
     * @param addDays : number of days to add (ex 10 )
     * @param format : date format
     */
    public void setSpecificDate(String addDays,String format) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        if (format != null)
            DATE_FORMAT_NOW = format;
        Calendar cal = Calendar.getInstance();
        if (addDays.length() > 2) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_NOW, Locale.ENGLISH);
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

    /**
     * select the month by given value
     * @param month Ex : july
     */
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
     *
     * @param month : compare month by given value
     * @return
     */
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

    /**
     * click the day in the month
     * @param date : date to click
     */
    public void clickOnDay(String date) {

        try {

            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[@aria-disabled='false'][text()=" + date + "]")).click();

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    //locators
//    private WebElement statusDraft(){return  driver.findElement(By.cssSelector(""))};
    private WebElement eventsTitle() { return driver.findElement(By.xpath("//span[text()='Events']")); }
    private WebElement eventNameField() { return driver.findElement(By.cssSelector("input#name")); }
    private WebElement eventStartCalendarButton() { return driver.findElement(By.cssSelector("div#content.ui div.row:nth-of-type(3) button.ui.basic.button")); }
    private WebElement eventStartTimeField() { return driver.findElement(By.cssSelector("input#startTime")); }
    private WebElement timeZoneDropdown() { return driver.findElement(By.cssSelector("div[aria-live='polite']")); }
    private WebElement timeZoneText() { return driver.findElement(By.cssSelector("input.search + div")); }
    private WebElement descriptionField() { return driver.findElement(By.cssSelector("textarea#eventDescription")); }
    private WebElement maxAttendeesField() { return driver.findElement(By.cssSelector("input#availableSeats")); }
    private WebElement rsvpCalendarButton() { return driver.findElement(By.cssSelector("div.ui.stackable.middle.aligned.grid div.row:nth-of-type(7) button")); }
    private WebElement rsvpTimeField() { return driver.findElement(By.cssSelector("input#registrationDeadlineTime")); }
    public WebElement locationField() { return driver.findElement(By.cssSelector("input[name='locations-dropdown']")); }
    private WebElement primaryContactField() { return driver.findElement(By.cssSelector("input[name='contacts-search']")); }
    public WebElement audienceField() { return driver.findElement(By.cssSelector("input[name='filters-dropdown']")); }
    private WebElement saveDraftButton() { return driver.findElement(By.cssSelector("button[title='Save Draft']")); }
    private WebElement publishNowButton() { return driver.findElement(By.cssSelector("button[title='Publish Now']")); }
    private WebElement createEventButton() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='CREATE EVENT']")));
        return driver.findElement(By.xpath("//span[text()='CREATE EVENT']")); }
    public WebElement menuButtonForEvent(String eventName) {
        return driver.findElement(By.xpath("//h3[text() = '"+eventName+"']/ancestor::div[@class='seven wide column _3l-lrs4zHMTh-dgZAy1GoL']/following-sibling::div[@class='three wide column _9SozV5IWiYp704W5xAqOC']//i"));
    }
    private WebElement getOptionFromMenuButtonForEvents(String optionName) {
        return getDriver().findElement(By.xpath("//span[text()='" + optionName + "']"));
    }
    private WebElement getMenuButton(String optionName) {
        return driver.findElement(By.xpath("//span[text()  = '" + optionName + "']"));
    }
    private WebElement updateButton() { return driver.findElement(By.cssSelector("button[title='Update']")); }
    private WebElement cancelYesButton() { return driver.findElement(By.cssSelector("button[data-status='CANCELED']")); }
    public WebElement getEventsTab(String tabName) {
        return driver.findElement(By.xpath("//span[contains(text(), '" + tabName + "')]/.."));
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
        return driver.findElement(By.xpath("//span[text()='Please select a location for the event.']"));
    }
    private WebElement primaryContactError() {
        return driver.findElement(By.xpath("//span[text()='Please select a contact for the event.']"));
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
    private WebElement getTab(String tabName) { return driver.findElement(By.xpath("//li[contains(@class, 'link item')]/a/h2[text()='" + tabName + "']")); }
    private String filtersList = "div[class*=dimmable] strong";
    private WebElement newFilterLink() { return driver.findElement(By.cssSelector("table[class *= \"ui unstackable very basic left aligned table\"] a")); }
    private WebElement filterInEventAudienceList(String filterName) { return driver.findElement(By.xpath("//table[contains(@class, 'ui unstackable very basic left aligned table')]/tbody/tr/td/div[text()='" + filterName + "']")); }
    private WebElement filtersListContainer() { return driver.findElement(By.cssSelector("div.ui.secondary + div")); }
    private List<WebElement> filtersInEventsAudienceList(String filterName) { return driver.findElements(By.xpath("//table[contains(@class, 'ui unstackable very basic left aligned table')]/tbody/tr/td/div[text()='" + filterName + "']")); }
    private WebElement attendeeStatusBarStudent(String eventName) {
        //return driver.findElement(By.xpath("//a[text() = '" + eventName + "']/../../../div[contains(@class, 'four wide column')]/a"));
        return getDriver().findElement(By.xpath("//h3[text()='"+eventName+"']/../../../following::div/a"));
    }
    private WebElement noAttendeesMessage() { return driver.findElement(By.xpath("//div[@class[contains(.,'_22IjfAfN4Zs4CnM4Q_AlWZ')]]")); }
    private String noAttendeesMessageString = "There are no attendees currently registered for this event.";
    private WebElement notAuthorizedErrorMessage() { return driver.findElement(By.cssSelector("#content > div > h1")); }
    private String expectedNotAuthorizedErrorText = "You are not authorized to view the content on this page";
    private String eventsListLocator(String eventName) { return "//div[@class='ui stackable middle aligned grid _3nZvz_klAMpfW_NYgtWf9P']/div[@class='row _3yNTg6-hDkFblyeahQOu7_']/div/div/a[text()='" + eventName + "']"; }
    private WebElement eventStatus(String eventName) { return driver.findElement(By.xpath("//a/h3[text()='"+eventName+"']/../../../../div[@class[contains(.,'two')]]/div")); }
    private WebElement eventAudienceTextBox() { return driver.findElement(By.cssSelector("input[name = 'filters-dropdown']")); }
    private WebElement mainEventsTitle() { return driver.findElement(By.cssSelector("a div div.hidden-mobile")); }
    private WebElement eventLinkByPosition(int position) { return driver.findElement(By.cssSelector("div[class *= 'ui stackable middle aligned grid'] div[class *= 'row']:nth-of-type(" + position + ") a:not(.ui)")); }
    private WebElement attendeesErrorMessage() { return driver.findElement(By.cssSelector("table[class *= 'ui very basic table'] div.ui.header span")); }
    private WebElement attendeeDataFirstName(String firstName){return  driver.findElement(By.xpath("//Div[text()='" + firstName + "']"));}
    private WebElement attendeeDataLastName(String lastName){return  driver.findElement(By.xpath("//Div[text()='" + lastName + "']"));}
    private WebElement attendeeDataEmail(String Email){return driver.findElement(By.xpath("//Div[text()='" + Email + "']"));}
    private WebElement attendeeDataStatus(String Status){return driver.findElement(By.xpath("//Div[text()='" + Status + "']"));}
    private WebElement getEventsInternalTab(String tabName) { return  driver.findElement(By.xpath("//h2[contains(text(), '" + tabName + "')]")); }
    private WebElement editEventClick (String nameOfEvent){return driver.findElement(By.xpath("//h3[text()='" +nameOfEvent+ "']"));}
    private String unpublishedEventsEllipsisLocator(String eventName) { return "//h3[text() = '" + eventName + "']/../../../..//i"; }
    private String progressBarLocator = "div[role='progressbar']";
    private String collegeNameHeader = "div.events-list__column-head.events-list__column-head--name";
    private String loadingIconLocator = "div.ui.active.loader";
    private By eventsTab(String tabName) {
        return By.xpath("//span[contains(text(), '" + tabName + "')]/..");
    }
    private By eventsHeader() {
        return By.xpath("//main//div//a");
    }
    private By filter(String filterName) { return By.xpath("//strong[text()='"+filterName+"']/../..//i[@class[contains(.,'ellipsis')]]"); }
}
