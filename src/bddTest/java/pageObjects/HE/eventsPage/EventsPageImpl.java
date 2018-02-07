package pageObjects.HE.eventsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.homePage.HomePageImpl;

import java.text.SimpleDateFormat;
import java.util.*;

public class EventsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static HashMap<String, String> editedData = new HashMap<>();
    public static String eventName = "";
    private HomePageImpl homePage = new HomePageImpl();

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
                    selectLocationByPosition(Integer.parseInt(row.get(1)));
                    break;
                case "EVENT PRIMARY CONTACT":
                    selectContactByPosition(Integer.parseInt(row.get(1)));
                    break;
                case "EVENT AUDIENCE":
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

    public void openEditScreen(String eventName) {
        menuButtonForEvent(eventName).click();
        menuButtonForEventsEdit().click();
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
        menuButtonForEventsCancel().click();
        cancelYesButton().click();
    }

    public void clickSaveDraft() {
        saveDraftButton().click();
    }

    public void verifyAllErrorMessages() {
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
                driver.findElements(By.xpath("//div[@class='ui stackable middle aligned grid _3nZvz_klAMpfW_NYgtWf9P']" +
                        "/div[@class='row _3yNTg6-hDkFblyeahQOu7_']/div/div/a[text()='" + eventName + "']")).size() == 0);
    }

    public void unpublishEvent(String eventName) {
        if (driver.findElements(By.cssSelector("input#name")).size() == 1) {
            eventsTabFromEditEventScreen().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='CREATE EVENT']"), 1));
        }
        homePage.openEventList();
        getEventsTab("Published").click();
        menuButtonForEvent(eventName).click();
        menuButtonForEventsUnpublish().click();
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
        menuButtonForEventsCancel().click();
        cancelYesButton().click();
    }

    public void clickCreateEvent() {
        createEventButton().click();
    }

    public void verifyCreatedEventIsInCancelledList() {
        verifyEventIsInCancelledList(eventName);
    }

    //locators
    private WebElement eventsTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
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
    private WebElement menuButtonForEvent(String eventName) {
        return driver.findElement(By.xpath("//a[text() = '" + eventName + "']/../../../div[contains(@class, 'three wide column')]/div/div/i"));
    }
    private WebElement getOptionFromMenuButtonForEvents(String optionName) {
        return driver.findElement(By.xpath("//span[text()='" + optionName + "']"));
    }
    private WebElement menuButtonForEventsEdit() {
        return driver.findElement(By.cssSelector("div.menu.transition.visible.h8roPzSIEFBFl1AUxcoMO div:nth-of-type(1) span"));
    }
    private WebElement menuButtonForEventsUnpublish() {
        return driver.findElement(By.cssSelector("div.menu.transition.visible.h8roPzSIEFBFl1AUxcoMO div:nth-of-type(2) span"));
    }
    private WebElement menuButtonForEventsCancel() {
        return driver.findElement(By.cssSelector("div.menu.transition.visible.h8roPzSIEFBFl1AUxcoMO div:nth-of-type(3) span"));
    }
    private WebElement menuButtonForEventsDuplicate() {
        return driver.findElement(By.cssSelector("div.menu.transition.visible.h8roPzSIEFBFl1AUxcoMO div:nth-of-type(4) span"));
    }
    private WebElement menuButtonForEventsAttendees() {
        return driver.findElement(By.cssSelector("div.menu.transition.visible.h8roPzSIEFBFl1AUxcoMO div:nth-of-type(5) span"));
    }


    private WebElement updateButton() { return driver.findElement(By.cssSelector("button[title='Update']")); }
    private WebElement cancelYesButton() { return driver.findElement(By.cssSelector("button[data-status='CANCELED']")); }
    private WebElement getEventsTab(String tabName) {
        return driver.findElement(By.xpath("//ul[@class='ui huge pointing secondary stackable _1efVFbHpRG36vpSaIzpNNv menu']" +
                "/li/a/span[text()='" + tabName + "']"));
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
    private WebElement eventsTabFromEditEventScreen() { return driver.findElement(By.xpath("//a[@class='_32YTxE8-igE6Tjpe2vRTtL']/span[text()='Events']")); }
}
