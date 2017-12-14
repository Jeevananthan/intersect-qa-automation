package pageObjects.HE.eventsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventsLocationPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static HashMap<String, String> editedLocationData = new HashMap<>();
    private EventsPageImpl eventsPage = new EventsPageImpl();

    public EventsLocationPageImpl() {
        logger = Logger.getLogger(EventsLocationPageImpl.class);
    }

    public void createAndSaveNewLocation(DataTable locationDataTable) {
        eventsPage.locationField().click();
        newLocationLink().click();
        editAllFields(locationDataTable);
        doneButton().click();
    }

    public void verifyCreatedLocation(String locationName) {
        waitUntil(ExpectedConditions.attributeContains(By.cssSelector("input[name='locations-dropdown']"), "value", locationName));
        Assert.assertTrue("The created Location is not displayed in the Location field", eventsPage.locationField().
                getAttribute("value").equals(locationName));
        eventsPage.locationField().click();
        Assert.assertTrue("The created location is not displayed in the location list", singleResultInLocationListNameText().getText().equals(locationName));
        Assert.assertTrue("The Edit button is not displayed for the new location in the location list",
                singleResultInLocationListEditLink().isDisplayed());
    }

    public void editLocation(DataTable newLocationData) {
        openEditFormForSelectedLocation();
        editAllFields(newLocationData);
    }

    public void takeNoteOfDataInCurrentLocation() {
        editedLocationData.put("Location Name", locationNameField().getAttribute("value"));
        editedLocationData.put("Street", streetField().getAttribute("value"));
        editedLocationData.put("City", cityField().getAttribute("value"));
        editedLocationData.put("State", stateField().getAttribute("value"));
        editedLocationData.put("Postal Code", postalCodeField().getAttribute("value"));
    }

    public void saveLocation() {
        doneButton().click();
    }

    public void verifyLocationUpdated() {
        openEditFormForSelectedLocation();
        for (String key : editedLocationData.keySet()) {
            switch (key) {
                case "Location Name" :
                    Assert.assertTrue(key + "was not successfully edited", editedLocationData.get(key).
                            equals(locationNameField().getAttribute("value")));
                    break;
                case "Street" :
                    Assert.assertTrue(key + "was not successfully edited", editedLocationData.get(key).
                            equals(streetField().getAttribute("value")));
                    break;
                case "City" :
                    Assert.assertTrue(key + "was not successfully edited", editedLocationData.get(key).
                            equals(cityField().getAttribute("value")));
                    break;
                case "State" :
                    Assert.assertTrue(key + "was not successfully edited", editedLocationData.get(key).
                            equals(stateField().getAttribute("value")));
                    break;
                case "Postal Code" :
                    Assert.assertTrue(key + "was not successfully edited", editedLocationData.get(key).
                            equals(postalCodeField().getAttribute("value")));
                    break;
            }
        }
    }

    private void openEditFormForSelectedLocation() {
        waitUntilPageFinishLoading();
        eventsPage.locationField().click();
        singleResultInLocationListEditLink().click();
    }

    public void deleteOpenLocation() {
        deleteLocationLink().click();
        deleteLocationYesButton().click();
    }

    public void verifyLocationIsNotPresentInList(String locationName) {
        List<String> locationListStrings = new ArrayList<>();
        Assert.assertFalse("The location is in the location list, when it shouldn't", locationListStrings.contains(locationName));
    }

    public void openCreateLocation() {
        eventsPage.locationField().click();
        newLocationLink().click();
    }

    public void clickDone() {
        doneButton().click();
    }

    public void editOpenLocation(DataTable locationDataTable) {
        editAllFields(locationDataTable);
    }

    public void editAllFields(DataTable data) {
        List<List<String>> locationData = data.asLists(String.class);
        for (List<String> row : locationData) {
            switch (row.get(0)) {
                case "Location Name" :
                    locationNameField().clear();
                    locationNameField().sendKeys(row.get(1));
                    break;
                case "Street" :
                    streetField().clear();
                    streetField().sendKeys(row.get(1));
                    break;
                case "City" :
                    cityField().clear();
                    cityField().sendKeys(row.get(1));
                    break;
                case "State" :
                    stateField().clear();
                    stateField().sendKeys(row.get(1));
                    break;
                case "Postal Code" :
                    postalCodeField().clear();
                    postalCodeField().sendKeys(row.get(1));
                    break;
            }
        }
    }

    //locators
    private WebElement newLocationLink() {
        return driver.findElement(By.xpath("//span[text() = '+ New Location']"));
    }
    private WebElement locationNameField() {
        return  driver.findElement(By.cssSelector("input[name='location-name']"));
    }
    private WebElement streetField() { return driver.findElement(By.cssSelector("input[name='location-street']")); }
    private WebElement cityField() { return driver.findElement(By.cssSelector("input[name='location-city']")); }
    private WebElement stateField() { return driver.findElement(By.cssSelector("input[name='location-state']")); }
    private WebElement postalCodeField() { return driver.findElement(By.cssSelector("input[name='location-postalcode']")); }
    private WebElement doneButton() { return driver.findElement(By.cssSelector("button.ui.primary.button._2hWsKmd3ZpllcWLNxffNeu")); }
    private WebElement singleResultInLocationListNameText() { return driver.findElement(By.cssSelector("tr._1hExGvG5jluro4Q-IOyjd7 div._1mf5Fc8-Wa2hXhNfBdgxce")); }
    private WebElement singleResultInLocationListEditLink() { return driver.findElement(By.cssSelector("tr._1hExGvG5jluro4Q-IOyjd7 td._1OZ1oMK3GglRUsHKbjbU2- span")); }
    private WebElement deleteLocationLink() { return driver.findElement(By.cssSelector("a._2m8lgYbkXvYZuzvkh1m_Q1 span")); }
    private WebElement deleteLocationYesButton() { return driver.findElement(By.cssSelector("div.ui.warning.message + div button.ui.primary.button")); }
    private List<WebElement> locationList() {
        return driver.findElements(By.cssSelector("div._1mf5Fc8-Wa2hXhNfBdgxce"));
    }
}
