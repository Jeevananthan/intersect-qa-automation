package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class EditMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    List<String> tabs = new ArrayList<>();

    public EditMenuPageImpl() {
        logger = Logger.getLogger(EditMenuPageImpl.class);
    }

    public void clickEditMenuButton(String label) {
        waitForUITransition();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='editor ng-scope']"), 1));
        switch (label) {
            case "Studies" : studiesButton().click();
                break;
            case "Student Life" : studentLifeButton().click();
                break;
            case "Overview" : overviewButton().click();
                break;
            case "International" : internationalButton().click();
                break;
            case "Costs" : costsButton().click();
                break;
            case "Admissions" : admissionsButton().click();
                break;
            case "GPA" : gpaButton().click();
                break;
        }
        logger.info(label + " button was clicked");
    }

    public void verifyTermsOfService(DataTable data) {
        List<String> dataStrings = data.asList(String.class);
        getDriver().switchTo().defaultContent();
        for (String dataElement : dataStrings) {
            link(dataElement).click();
            tabs = new ArrayList<String>(getDriver().getWindowHandles());
            getDriver().switchTo().window(tabs.get(1));
            assertTrue("The 'Terms of Service' page is not correctly displayed", termsOfUsePageLocator().isDisplayed());
            getDriver().switchTo().window(tabs.get(1));
            getDriver().close();
            getDriver().switchTo().window(tabs.get(0));
        }
    }

    public void verifyGPAValidations(String errorMessage, DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        for(List<String> row : details) {
            if(errorMessage.equals("Please use a GPA between 0.1 and 4.0")) {
                switch (row.get(0)) {
                    case "25th Percentile (visible only in SuperMatch)":
                        gpa25PercentileTextBox().clear();
                        gpa25PercentileTextBox().sendKeys(row.get(1));
                        Assert.assertTrue("The error message is incorrect. UI: " + gpa25PercentileError().getText(), gpa25PercentileError().getText().equals(errorMessage));
                        break;
                    case "Average":
                        averageTextBox().clear();
                        averageTextBox().sendKeys(row.get(1));
                        Assert.assertTrue("The error message is incorrect", averageError().getText().equals(errorMessage));
                        break;
                    case "75th Percentile (visible only in SuperMatch)":
                        gpa75PercentileTextBox().clear();
                        gpa75PercentileTextBox().sendKeys(row.get(1));
                        Assert.assertTrue("The error message is incorrect", gpa75PercentileError().getText().equals(errorMessage));
                        break;
                }
            }
        }
    }

    public void setGPAValues(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        for(List<String> row : details) {
            switch (row.get(0)) {
                case "25th Percentile (visible only in SuperMatch)" :
                    gpa25PercentileTextBox().clear();
                    gpa25PercentileTextBox().sendKeys(row.get(1));
                    break;
                case "Average" :
                    averageTextBox().clear();
                    averageTextBox().sendKeys(row.get(1));
                    break;
                case "75th Percentile (visible only in SuperMatch)" :
                    gpa75PercentileTextBox().clear();
                    gpa75PercentileTextBox().sendKeys(row.get(1));
                    break;
            }
        }
    }

    public void verifyErrorMessageForTextBox(String errorMessage, String textBoxLabel) {
        switch (textBoxLabel) {
            case "25th Percentile (visible only in SuperMatch)" :
                Assert.assertTrue("The error message is not correct", gpa25PercentileError().getText().equals(errorMessage));
                break;
        }
    }

    public void verifyErrorMessages(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        for(List<String> row : details) {
            switch (row.get(0)) {
                case "25th Percentile (visible only in SuperMatch)" :
                    Assert.assertTrue("The error message is incorrect", gpa25PercentileError().getText().equals(row.get(1)));
                    break;
                case "Average" :
                    Assert.assertTrue("The error message is incorrect", averageError().getText().equals(row.get(1)));
                    break;
                case "75th Percentile (visible only in SuperMatch)" :
                    Assert.assertTrue("The error message is incorrect", gpa75PercentileError().getText().equals(row.get(1)));
                    break;
            }
        }
    }


    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//li[text()='Studies']"));
    }
    private WebElement studentLifeButton() { return getDriver().findElement(By.xpath("//li[text()='Student Life']")); }
    private WebElement overviewButton() { return getDriver().findElement(By.xpath("//li[text()='Overview']")); }
    private WebElement internationalButton() { return getDriver().findElement(By.xpath("//li[text()='International']")); }
    private WebElement costsButton() { return getDriver().findElement(By.xpath("//li[text()='Costs']")); }
    private WebElement admissionsButton() { return getDriver().findElement(By.xpath("//li[text()='Admissions']")); }
    private WebElement termsOfUsePageLocator() { return getDriver().findElement(By.cssSelector("p:nth-of-type(1) span")); }
    private WebElement gpa25PercentileTextBox() { return driver.findElement(By.cssSelector("input[id $= '-field_he_gpa_25th_percentile']")); }
    private WebElement gpa25PercentileError() { return driver.findElement(By.cssSelector("input[id $= '-field_he_gpa_25th_percentile'] + span")); }
    private WebElement averageTextBox() { return  driver.findElement(By.cssSelector("input[id $= '-field_he_gpaaverage']")); }
    private WebElement averageError() { return driver.findElement(By.cssSelector("input[id $= '-field_he_gpaaverage'] + span")); }
    private WebElement gpa75PercentileTextBox() { return driver.findElement(By.cssSelector("input[id $= '-field_he_gpa_75th_percentile']")); }
    private WebElement gpa75PercentileError() { return driver.findElement(By.cssSelector("input[id $= '-field_he_gpa_75th_percentile'] + span")); }
    private WebElement gpaButton() { return driver.findElement(By.cssSelector("field-group[title='GPA'] h3")); }
}
