package pageObjects.HE.filtersPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;
import stepDefinitions.World;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FiltersPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private World world;

    public FiltersPageImpl() {
        this.world = new World();
        logger = Logger.getLogger(FiltersPageImpl.class);
    }

    private EventsPageImpl eventsPage = new EventsPageImpl();

    public  void summaryFilter(DataTable summaryFilterData){
        List<List<String>> filterData = summaryFilterData.asLists(String.class);
        for (List<String> filterDataElement : filterData) {
            switch (filterDataElement.get(0)) {
                case "Gender" : genderCheckBox(filterDataElement.get(1)).click();
                    break;
                case "Location" :
                    locationMilesDropdown().click();
                    getDropdownOption(filterDataElement.get(1).split(";")[0]).click();
                    locationPostalCodeField().sendKeys(filterDataElement.get(1).split(";")[1]);
                    break;
            }}}

    public void recommendedCount(){
        waitUntilPageFinishLoading();
        Assert.assertTrue(" Filter Summary count is Zero",Integer.parseInt(countNotZero().getText())>0);

    }

    public void createFilter(DataTable newFilterData) {
        List<List<String>> filterData = newFilterData.asLists(String.class);
        fillFilterForm(filterData);
        saveFilterButton().click();
        waitUntilPageFinishLoading();
        if(driver.findElements(By.cssSelector(filterNameUniqueErrorMessageLocator)).size() > 0) {
            softly().assertThat(false).as("A filter with the same name already exists.");
            if (driver.findElements(By.cssSelector(createFilterCancelButtonLocator)).size() > 0) {
                driver.findElement(By.cssSelector(createFilterCancelButtonLocator)).click();
            } else {
                eventsPage.getEventsTab("Filters");
            }
        }
    }

    public void clickCreateFilter() {
        createFilterButton().click();
    }

    public void clickSaveFilter() {
        saveFilterButton().click();
    }

    public void verifyReqDataErrorMessages(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        for( List<String> row : details) {
            switch (row.get(0)) {
                case "Location" :
                    Assert.assertTrue("The error message for Location is not correct",
                            locationErrorMessage().getText().equals(row.get(1)));
                    break;
                case "Filter Name" :
                    Assert.assertTrue("The error message for Filter Name is not correct",
                            filterNameErrorMessage().getText().equals(row.get(1)));
                    break;
            }
        }
    }

    public void deleteFilter(String filterName) {
        int filter;
       try{
           waitUntil(ExpectedConditions.visibilityOfElementLocated(filter(filterName)));
           filter = filterSize(filterName).size();
           if (filter > 0) {
               threePointsMenu(filterName).click();
               threePointsMenuElement("Delete").click();
               deleteConfirmationButton().click();
               waitUntil(ExpectedConditions.visibilityOfElementLocated(eventsHeader()));
           }
       }catch (Exception e) {
           filter = filterSize(filterName).size();
           if (filter > 0) {
               threePointsMenu(filterName).click();
               threePointsMenuElement("Delete").click();
               deleteConfirmationButton().click();
               waitUntil(ExpectedConditions.visibilityOfElementLocated(eventsHeader()));
           }
       }
    }

    public void renameFilter(String originalName, String newName) {
        waitUntil(ExpectedConditions.visibilityOf(threePointsMenu(originalName)));
        threePointsMenu(originalName).click();
        threePointsMenuElement("Rename").click();
        nameField().clear();
        nameField().sendKeys(newName);
        submitButton().click();
    }

    public void verifyNumberOfAssignedEvents(String filterName, String numberOfAssignedEvents) {
        waitUntilElementExists(getDriver().findElement(By.xpath("//h1/span[text()='Filters']")));
        try {
            numberOfAssignedEvents(filterName).isDisplayed();
        } catch (NoSuchElementException f) {
            driver.get(driver.getCurrentUrl());
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue("The displayed number does not match the number of events the filter " + filterName + " is assigned to",
                numberOfAssignedEvents(filterName).getText().equals(numberOfAssignedEvents));
    }
    public void verifyAssignedEventName(String eventName, String filterName){
        driver.navigate().refresh();
        numberOfAssignedEvents(filterName).click();
        Assert.assertTrue("The assigned event name do not match", eventsAssignedToFilter().getText().contains(eventName));
    }

    public void verifyFiltersOrder(String baseName, String filterOption) {
        sortByDropdown().click();
        sortByDropdownOption(filterOption).click();
        List<WebElement> recommendedToElements = getRecommendedCountFromFiltersWithBaseName(baseName);
        for (int i = 0; i < recommendedToElements.size(); i++) {
            if((i + 1) < recommendedToElements.size()) {
                Assert.assertTrue("The filters are not displayed in the correct order",
                        Integer.parseInt(recommendedToElements.get(i).getText()) >= Integer.parseInt(recommendedToElements.get(i + 1).getText()));
            }
        }
    }

    public List<WebElement> getRecommendedCountFromFiltersWithBaseName(String baseName) {
        return driver.findElements(By.xpath(recommendedCountListLocator(baseName)));
    }

    public void createFilterWithGenName(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");
        fillFilterForm(details);
        filterNameField().sendKeys(dateFormat.format(date));
        world.string = filterNameField().getAttribute("value");
        saveFilterButton().click();
    }

    public void fillFilterForm(List<List<String>> formData) {
        for (List<String> filterDataElement : formData) {
            switch (filterDataElement.get(0)) {
                case "Gender" : genderCheckBox(filterDataElement.get(1)).click();
                    break;
                case "Location" :
                    locationMilesDropdown().click();
                    getCreateFilterDropdownOption(filterDataElement.get(1).split(";")[0]).click();
                    locationPostalCodeField().sendKeys(filterDataElement.get(1).split(";")[1]);
                    break;
                case "Race and Ethnicity" :
                    raceAndEthnicityField().click();
                    getCreateFilterDropdownOption(filterDataElement.get(1)).click();
                    filterNameLabel().click();
                    break;
                case "Grade Level" :
                    gradeLevel().click();
                    waitForUITransition();
                    getCreateFilterDropdownOption(filterDataElement.get(1)).click();
                    filterNameLabel().click();
                    break;
                case "GPA" :
                    waitUntilPageFinishLoading();
                    gpaField().click();
                    getCreateFilterDropdownOption(filterDataElement.get(1)).click();
                    filterNameLabel().click();
                    break;
                case "Filter Name" :
                    filterNameLabel().click();
                    filterNameField().sendKeys(filterDataElement.get(1));
                    break;
            }
        }
    }

    public void verifyFilterOfGenNameIsDefaultInEventAudience() {
        Assert.assertTrue("The filter " + world.string + " is not displayed in the Event Audience field.",
                eventsPage.audienceField().getAttribute("value").equals(world.string));
    }

    public void deleteFilterOfGenName() {
        threePointsMenu(world.string).click();
        threePointsMenuElement("Delete").click();
        deleteConfirmationButton().click();
        waitForUITransition();
    }

    public void deleteFilterIfPresent(String filterName) {
        if (driver.findElements(By.xpath(filterNameLocator(filterName))).size() > 0) {
            deleteFilter(filterName);
        }
    }

    //locators
    private WebElement genderCheckBox(String option) { return driver.findElement(By.cssSelector("input[value=\"" + option.toUpperCase() + "\"]")); }
    private WebElement locationMilesDropdown() { return driver.findElement(By.cssSelector("div[name=\"distanceValue\"]")); }
    private WebElement locationPostalCodeField() { return driver.findElement(By.cssSelector("input#postalCode")); }
    private WebElement raceAndEthnicityField() { return driver.findElement(By.cssSelector("div[name=\"ethnicities\"]")); }
    private WebElement gradeLevel() { return driver.findElement(By.cssSelector("div[name=\"gradeLevels\"]")); }
    private WebElement gpaField() { return driver.findElement(By.cssSelector("div[name=\"letterGrades\"]")); }
    private WebElement filterNameField() { return driver.findElement(By.cssSelector("input[aria-label=\"Filter Name\"]")); }
    private WebElement saveFilterButton() { return driver.findElement(By.cssSelector("button[type=\"submit\"] span")); }
    private WebElement createFilterButton() { return driver.findElement(By.cssSelector("a[role=\"button\"] span")); }
    private WebElement getDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@role='option']/span[text()='"+optionName+"']")); }
    private WebElement filterNameLabel() { return driver.findElement(By.xpath("//label/span[text()='Filter Name']")); }
    private WebElement threePointsMenu(String filterName) { return driver.findElement(By.xpath("//strong[text()='"+filterName+"']/../..//i[@class[contains(.,'ellipsis')]]")); }
    private WebElement threePointsMenuElement(String optionName) { return driver.findElement(By.xpath("//div[@class='menu transition visible']/*[contains(@class, 'item')]/span[text()='" + optionName + "']")); }
    private WebElement deleteConfirmationButton() { return driver.findElement(By.xpath("//button/span[text()='Delete']")); }
    private WebElement nameField() { return driver.findElement(By.cssSelector("input[id=\"name\"]")); }
    private WebElement submitButton() { return driver.findElement(By.cssSelector("button[class=\"ui primary button\"]")); }
    private String deleteFilterDialogTitle = "Are you sure you want to delete this filter?";
    private WebElement deleteFilterDialogHeader() { return driver.findElement(By.cssSelector("div.ui.header span")); }
    private WebElement numberOfAssignedEvents(String filterName) { return driver.findElement(By.xpath("//strong[text()='" + filterName + "']/../../div/span/span[@role='button']")); }
    private WebElement locationErrorMessage() { return driver.findElement(By.cssSelector("input#postalCode + div span")); }
    private WebElement filterNameErrorMessage() { return driver.findElement(By.cssSelector("input#name + div span")); }
  //  private WebElement eventsAssignedToFilter(String eventName) { return driver.findElement(By.xpath("//strong[text()='" + eventName + "']/../../div/span/span[@role='listitem']")); }
    private WebElement eventsAssignedToFilter(){return driver.findElement(By.cssSelector(".ui.relaxed.list.NSL7sBgr5GF9KrxVqCXR8 a"));}
    private WebElement countNotZero(){return  driver.findElement(By.cssSelector("div._1v0QZJyY25uE_4FnAGv9hk"));}
    private WebElement sortByDropdown() { return driver.findElement(By.cssSelector("div[class *= 'ui button floating labeled dropdown icon'] div.text")); }
    private WebElement sortByDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@class = 'item']//span[text() = '" + optionName + "']")); }
    private String recommendedCountListLocator(String eventBaseName) { return "//div[contains(@class, 'dimmable')]/div/strong[contains(text(), '" + eventBaseName + "')]/../../div[5]/span[2]"; }
    private WebElement getCreateFilterDropdownOption(String option) { return driver.findElement(By.xpath("//span[@class = 'text' and text() = '" + option + "']")); }
    private String filterNameUniqueErrorMessageLocator = "div.ui.error.message span";
    private String createFilterCancelButtonLocator = "form.ui.small.form button.ui.basic.right.floated.button span";
    private String filterNameLocator(String filterName) { return "//strong[text() = '" + filterName + "']";}
    private List<WebElement> filterSize(String filterName) { return driver.findElements(By.xpath("//strong[text()='"+filterName+"']/../..//i[@class[contains(.,'ellipsis')]]")); }
    private By filter(String filterName) { return By.xpath("//strong[text()='"+filterName+"']/../..//i[@class[contains(.,'ellipsis')]]"); }
    public By eventsHeader() { return By.xpath("//main//div//a"); }
}
