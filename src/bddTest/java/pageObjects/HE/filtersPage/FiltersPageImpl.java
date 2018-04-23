package pageObjects.HE.filtersPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class FiltersPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public FiltersPageImpl() {
        logger = Logger.getLogger(FiltersPageImpl.class);
    }

    public void createFilter(DataTable newFilterData) {
        createFilterButton().click();
        List<List<String>> filterData = newFilterData.asLists(String.class);
        for (List<String> filterDataElement : filterData) {
            switch (filterDataElement.get(0)) {
                case "Gender" : genderCheckBox(filterDataElement.get(1)).click();
                break;
                case "Location" :
//                    Select milesDropdown = new Select(locationMilesDropdown());
//                milesDropdown.selectByVisibleText(filterDataElement.get(1).split(";")[0]);
//                locationPostalCodeField().clear();
//                locationPostalCodeField().sendKeys(filterDataElement.get(1).split(";")[1]);
                locationMilesDropdown().click();

                break;
                case "Race and Ethnicity" : raceAndEthnicityField().clear();
                raceAndEthnicityField().sendKeys(filterDataElement.get(1));
                break;
                case "Grade Level" : gradeLevel().clear();
                gradeLevel().sendKeys(filterDataElement.get(1));
                break;
                case "GPA" : gpaField().clear();
                gpaField().sendKeys(filterDataElement.get(1));
                break;
                case "Filter Name" : filterNameField().clear();
                filterNameField().sendKeys(filterDataElement.get(1));
                break;
            }
        }
        saveFilterButton().click();
    }

    public void clickCreateFilter() {
        createFilterButton().click();
    }

    public void clickSaveFilter() {
        saveFilterButton().click();
    }

    public void verifyReqDataErrorMessages() {
        /*code to verify error messages for missing data in the fields*/
    }

    //locators
    private WebElement genderCheckBox(String option) { return driver.findElement(By.cssSelector("input[value=\"" + option.toUpperCase() + "\"]")); }
    private WebElement locationMilesDropdown() { return driver.findElement(By.cssSelector("div[name=\"distanceValue\"]")); }
    private WebElement locationPostalCodeField() { return driver.findElement(By.cssSelector("input#postalCode")); }
    private WebElement raceAndEthnicityField() { return driver.findElement(By.cssSelector("div[name=\"ethnicities\"]")); }
    private WebElement gradeLevel() { return driver.findElement(By.cssSelector("div[name=\"gradeLevels\"]")); }
    private WebElement gpaField() { return driver.findElement(By.cssSelector("div[name=\"letterGrades\"]")); }
    private WebElement filterNameField() { return driver.findElement(By.cssSelector("input[id=\"name\"]")); }
    private WebElement saveFilterButton() { return driver.findElement(By.cssSelector("button[type=\"submit\"] span")); }
    private WebElement createFilterButton() { return driver.findElement(By.cssSelector("a[role=\"button\"] span")); }
}
