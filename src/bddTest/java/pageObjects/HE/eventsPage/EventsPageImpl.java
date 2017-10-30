package pageObjects.HE.eventsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class EventsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

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
        fillCreateEventForm(eventDetails);
        saveButton().sendKeys(Keys.RETURN);
    }

    public void verifyEventIsPresent(String eventName) {
        /*Steps to verify*/
    }

    public void editEvent(List<List<String>> eventData, String eventName) {
        /*Steps to open event*/
        fillCreateEventForm(eventData);
        saveButton().click();
    }

    public void verifyEventData(List<List<String>> eventData) {
        /*Verify new data in event*/
    }

    public void publishEvent(String eventName) {
        /*Steps to open event from list*/
        publishButton().sendKeys(Keys.RETURN);
    }

    public void verifyPublishedEventPresent(String eventName) {
        /*Steps to verify in list*/
    }

    public void deleteEvent(String eventName) {
        /*Steps to open event from list*/
        /*Steps to delete event*/
    }

    public void verifyEventIsNotPresent(String eventName) {
        /*Steps to verify element is not present*/
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
                    /*Código para seleccionar fecha*/
                    eventStartTimeField().sendKeys(row.get(1).split(";")[1]);
                    break;
                case "Event End":
                    eventEndCalendarButton().click();
                    /*Code for selecting date*/
                    eventEndTimeField().sendKeys(row.get(1).split(";")[1]);
                    break;
                case "Timezone":
                    Select timeZone = new Select(timeZoneDropdown());
                    timeZone.selectByVisibleText(row.get(1));
                    break;
                case "Description":
                    descriptionField().clear();
                    descriptionField().sendKeys(row.get(1));
                    break;
                case "Max Atendees":
                    maxAtendeesField().clear();
                    maxAtendeesField().sendKeys(row.get(1));
                    break;
                case "RSVP Deadline":
                    rsvpCalendarButton().click();
                    /*Code for picking date*/
                    rsvpTimeField().sendKeys(row.get(1).split(";")[1]);
                    break;
                case "EVENT LOCATION":
                    Select location = new Select(locationDropdown());
                    location.selectByVisibleText(row.get(1));
                    break;
                case "EVENT PRIMARY CONTACT":
                    Select primaryContact = new Select(primaryContactDropdown());
                    primaryContact.selectByVisibleText(row.get(1));
                    break;
                case "EVENT AUDIENCE":
                    Select audience = new Select(audienceDropdown());
                    audience.selectByVisibleText(row.get(1));
                    break;
            }
        }
    }

    //locators
    private WebElement eventsTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
    private WebElement eventNameField() { return driver.findElement(By.cssSelector("PONER LOCALIZADOR!")); }
    private WebElement eventStartCalendarButton() { return driver.findElement(By.cssSelector("LOCALIZADOR")); }
    private WebElement eventEndCalendarButton() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement eventStartTimeField() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement eventEndTimeField() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement timeZoneDropdown() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement descriptionField() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement maxAtendeesField() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement rsvpCalendarButton() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement rsvpTimeField() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement locationDropdown() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement primaryContactDropdown() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement audienceDropdown() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement saveButton() { return driver.findElement(By.cssSelector("LOCATOR")); }
    private WebElement publishButton() { return driver.findElement(By.cssSelector("LOCATOR")); }
}
