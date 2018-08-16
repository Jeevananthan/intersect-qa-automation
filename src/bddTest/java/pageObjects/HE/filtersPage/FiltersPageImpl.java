package pageObjects.HE.filtersPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class FiltersPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public FiltersPageImpl() {
        logger = Logger.getLogger(FiltersPageImpl.class);
    }

    public void createFilter(DataTable newFilterData) {
        List<List<String>> filterData = newFilterData.asLists(String.class);
        for (List<String> filterDataElement : filterData) {
            switch (filterDataElement.get(0)) {
                case "Gender" : genderCheckBox(filterDataElement.get(1)).click();
                    break;
                case "Location" :
                    locationMilesDropdown().click();
                    getDropdownOption(filterDataElement.get(1).split(";")[0]).click();
                    locationPostalCodeField().sendKeys(filterDataElement.get(1).split(";")[1]);
                    break;
                case "Race and Ethnicity" :
                    raceAndEthnicityField().click();
                    getDropdownOption(filterDataElement.get(1)).click();
                    filterNameLabel().click();
                    break;
                case "Grade Level" :
                    gradeLevel().click();
                    waitForUITransition();
                    getDropdownOption(filterDataElement.get(1)).click();
                    filterNameLabel().click();
                    break;
                case "GPA" :
                    gpaField().click();
                    getDropdownOption(filterDataElement.get(1)).click();
                    filterNameLabel().click();
                    break;
                case "Filter Name" :
                    filterNameLabel().click();
                    filterNameField().sendKeys(filterDataElement.get(1));
                    break;
            }
        }
        saveFilterButton().click();
        waitForUITransition();
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
        threePointsMenu(filterName).click();
        threePointsMenuElement("Delete").click();
        deleteConfirmationButton().click();
        waitForUITransition();
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
        Assert.assertTrue("The displayed number does not match the number of events the filter " + filterName + " is assigned to",
                numberOfAssignedEvents(filterName).getText().equals(numberOfAssignedEvents));
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
    private WebElement getDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@class = 'menu transition visible']/div/span[text() = '" + optionName + "']")); }
    private WebElement filterNameLabel() { return driver.findElement(By.xpath("//label/span[text()='Filter Name']")); }
    private WebElement threePointsMenu(String filterName) { return driver.findElement(By.xpath("//strong[text() = '" + filterName + "']/../../div[contains(@class, 'ui right pointing dropdown button')]")); }
    private WebElement threePointsMenuElement(String optionName) { return driver.findElement(By.xpath("//div[@class='menu transition visible']/*[contains(@class, 'item')]/span[text()='" + optionName + "']")); }
    private WebElement deleteConfirmationButton() { return driver.findElement(By.cssSelector("button.ui.teal.button:not(.basic)")); }
    private WebElement nameField() { return driver.findElement(By.cssSelector("input[id=\"name\"]")); }
    private WebElement submitButton() { return driver.findElement(By.cssSelector("button[class=\"ui primary button\"]")); }
    private String deleteFilterDialogTitle = "Are you sure you want to delete this filter?";
    private WebElement deleteFilterDialogHeader() { return driver.findElement(By.cssSelector("div.ui.header span")); }
    private WebElement numberOfAssignedEvents(String filterName) { return driver.findElement(By.xpath("//strong[text()='" + filterName + "']/../../div/span/span[@role='button']")); }
    private WebElement locationErrorMessage() { return driver.findElement(By.cssSelector("input#postalCode + div span")); }
    private WebElement filterNameErrorMessage() { return driver.findElement(By.cssSelector("input#name + div span")); }
}
