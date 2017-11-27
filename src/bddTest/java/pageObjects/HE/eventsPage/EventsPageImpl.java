package pageObjects.HE.eventsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static HashMap<String, String> editedData = new HashMap<>();

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

//    public void verifyEventData(List<List<String>> eventData) {
//        for (List<String> row : eventData) {
//            switch (row.get(0)) {
//                case "Event Name":
//                    Assert.assertTrue(row.get(0) + " was not updated successfully", eventNameField().getText().equals(row.get(1)));
//                    break;
//                case "Event Start":
//                    Assert.assertTrue(row.get(0) + " was not updated successfully", eventStartTimeField().getText().equals(row.get(1).split(";")[1]));
//                    break;
//                case "Timezone":
//                    Select timeZoneDropdown = new Select(timeZoneDropdown());
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", timeZoneDropdown.getFirstSelectedOption().getText().equals(row.get(1)));
//                    break;
//                case "Description":
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", descriptionField().getText().equals(row.get(1)));
//                    break;
//                case "Max Atendees":
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", maxAttendeesField().getText().equals(row.get(1)));
//                    break;
//                case "RSVP Deadline":
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", rsvpTimeField().getText().equals(row.get(1)));
//                    break;
//                case "EVENT LOCATION":
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", locationField().getAttribute("value").equals(row.get(1)));
//                    break;
//                case "EVENT PRIMARY CONTACT":
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", primaryContactField().getAttribute("value").equals(row.get(1)));
//                    break;
//                case "EVENT AUDIENCE":
//                    Assert.assertTrue(row.get(0) + "was not updated successfully", audienceField().getAttribute("value").equals(row.get(1)));
//                    break;
//            }
//        }
//    }

    public void publishEvent() {
        publishNowButton().sendKeys(Keys.RETURN);
    }

    public void verifyPublishedEventPresent(String eventName) {
        verifyEventIsPresent(eventName);
    }

    public void deleteEvent(String eventName) {
        getOptionFromMenuButtonForEvents("Delete");
    }

    public void verifyEventIsInCancelledList(String eventName) {
        getEventsTab("CANCELED").click();
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
                    timeZoneDropdown().clear();
//                    getTimeZoneOption(row.get(1)).click();
                    timeZoneDropdown().sendKeys(row.get(1));
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
//        primaryContactField().clear();
//        primaryContactField().sendKeys("a");
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
        getOptionFromMenuButtonForEvents("Edit").click();
    }

    public void takeNoteOfData() {
        editedData.put("Event Name", eventNameField().getText());
        editedData.put("Event Start", eventStartTimeField().getText());
        editedData.put("Timezone", timeZoneText().getText());
        editedData.put("Description", descriptionField().getText());
        editedData.put("Max Attendees", maxAttendeesField().getText());
        editedData.put("RSVP Deadline", rsvpTimeField().getText());
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
                //There is an issue that prevents Timezone from being updated. A comment about this was added to MATCH-2913
//                case "Timezone" :
//                    Assert.assertTrue(key + "was not successfully updated", timeZoneDropdown().getText().equals(editedData.get(key)));
//                    break;
                case "Description" :
                    Assert.assertTrue(key + " was not successfully updated", descriptionField().getText().equals(editedData.get(key)));
                    break;
                case "Max Attendees" :
                    Assert.assertTrue(key + " was not successfully updated", maxAttendeesField().getText().equals(editedData.get(key)));
                    break;
                case "RSVP Deadline" :
                    Assert.assertTrue(key + " was not successfully updated", rsvpTimeField().getText().equals(editedData.get(key)));
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
        getOptionFromMenuButtonForEvents("Cancel").click();
        cancelYesButton().click();
    }

    public void clickSaveDraft() {
        saveDraftButton().click();
    }

    //locators
    private WebElement eventsTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
    private WebElement eventNameField() { return driver.findElement(By.cssSelector("input#name")); }
    private WebElement eventStartCalendarButton() { return driver.findElement(By.cssSelector("div.seven.wide.column button[title='Event Date']")); }
    private WebElement eventStartTimeField() { return driver.findElement(By.cssSelector("input#startTime")); }
    private WebElement timeZoneDropdown() { return driver.findElement(By.cssSelector("input.search")); }
    private WebElement timeZoneText() { return driver.findElement(By.cssSelector("input.search + div")); }
    private WebElement descriptionField() { return driver.findElement(By.cssSelector("textarea#eventDescription")); }
    private WebElement maxAttendeesField() { return driver.findElement(By.cssSelector("input#availableSeats")); }
    private WebElement rsvpCalendarButton() { return driver.findElement(By.cssSelector("div.three.wide.column button[title='Event Date']")); }
    private WebElement rsvpTimeField() { return driver.findElement(By.cssSelector("div.three.wide.column button[title='Event Date']")); }
    private WebElement locationField() { return driver.findElement(By.cssSelector("input[name='locations-dropdown']")); }
    private WebElement primaryContactField() { return driver.findElement(By.cssSelector("input[name='contacts-search']")); }
    private WebElement audienceField() { return driver.findElement(By.cssSelector("input[name='filters-dropdown']")); }
    private WebElement saveDraftButton() { return driver.findElement(By.cssSelector("button.ui.button.zPxT0vhJlMhEbCWzz52_S")); }
    private WebElement publishNowButton() { return driver.findElement(By.cssSelector("button.ui.button.bI0v4ge6zgbar6xs9MqwX")); }
    private WebElement createEventButton() { return driver.findElement(By.xpath("//span[text()='CREATE EVENT']")); }
    private WebElement menuButtonForEvent(String eventName) {
        return driver.findElement(By.xpath("//div[@class='ui stackable middle aligned grid _3nZvz_klAMpfW_NYgtWf9P']" +
                "/div[@class='row _3yNTg6-hDkFblyeahQOu7_']/div/div/div[text()='" + eventName + "']/../../../div/div/div/i"));
    }
    private WebElement getOptionFromMenuButtonForEvents(String optionName) {
        return driver.findElement(By.xpath("//span[text()='" + optionName + "']"));
    }
    private WebElement updateButton() { return driver.findElement(By.cssSelector("button[title='Update']")); }
    private WebElement cancelYesButton() { return driver.findElement(By.cssSelector("button[data-status='CANCELED']")); }
    private WebElement getEventsTab(String tabName) {
        return driver.findElement(By.xpath("//ul[@class='ui huge pointing secondary stackable _1efVFbHpRG36vpSaIzpNNv menu']" +
                "/li/a/span[text()='" + tabName + "']"));
    }
    private WebElement getTimeZoneOption(String optionName) {
        return driver.findElement(By.xpath("//div[@role='option']/span[text()='" + optionName + "']"));
    }
}
