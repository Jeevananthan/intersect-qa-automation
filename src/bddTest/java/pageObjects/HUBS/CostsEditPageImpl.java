package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CostsEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    CostsPageImpl costsPreview = new CostsPageImpl();
    PublishPageImpl publish = new PublishPageImpl();
    OverviewEditPageImpl overviewEditPage = new OverviewEditPageImpl();

    public CostsEditPageImpl() {
        logger = Logger.getLogger(CostsEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(DataTable stringsDataTable) {
        List<List<String>> fieldsAndValues = stringsDataTable.asLists(String.class);
        for (List<String> fieldAndValueElement : fieldsAndValues) {
            switch (fieldAndValueElement.get(0)) {
                case "Average Net Prices" :
                    avgNetPricesButton().click();
                    avgNetPriceTextBox(fieldAndValueElement.get(1)).clear();
                    avgNetPriceTextBox(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    Select avgNetPriceDropDown = new Select(costsPreview.avgNetPriceDropDown());
                    avgNetPriceDropDown.selectByVisibleText(costsPreview.getDropDownOption(fieldAndValueElement.get(1)));
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time",
                            costsPreview.avgNetPriceText().getText().replace(",", "").equals(fieldAndValueElement.get(2)));
                    break;
                case "% Receiving Aid" :
                    percentReceivingAidButton().click();
                    percentRceivingAidTextBox(fieldAndValueElement.get(1)).clear();
                    percentRceivingAidTextBox(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    Select receivingAidDropDown = new Select(costsPreview.receivingAidDropDown());
                    receivingAidDropDown.selectByVisibleText(fieldAndValueElement.get(1));
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time",
                            costsPreview.percentReceivingAidText().getText().equals(fieldAndValueElement.get(2)));
                    break;
                case "Average Amount of Aid" :
                    avgAmountOfAidButton().click();
                    avgAmountOfAidTextBox(fieldAndValueElement.get(1)).clear();
                    avgAmountOfAidTextBox(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time",
                            costsPreview.avgGrantAmountText().getText().replace(",", "").equals(fieldAndValueElement.get(2)));
                    break;
            }
        }
    }

    public void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues, List<List<String>> fieldsDetails) {
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Average Net Prices" :
                    avgNetPricesButton().click();
                    avgNetPriceTextBox(fieldsDetails.get(0).get(1)).clear();
                    avgNetPriceTextBox(fieldsDetails.get(0).get(1)).sendKeys(generatedValues.get(key));
                    if (!avgNetPriceTextBox(fieldsDetails.get(0).get(1)).getText().equals(generatedValues.get(key))) {
                        avgNetPriceTextBox(fieldsDetails.get(0).get(1)).sendKeys(generatedValues.get(key));
                    }
                    break;
                case "% Receiving Aid" :
                    percentReceivingAidButton().click();
                    percentRceivingAidTextBox(fieldsDetails.get(1).get(1)).clear();
                    percentRceivingAidTextBox(fieldsDetails.get(1).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Average Amount of Aid" :
                    avgAmountOfAidButton().click();
                    avgAmountOfAidTextBox(fieldsDetails.get(2).get(1)).clear();
                    avgAmountOfAidTextBox(fieldsDetails.get(2).get(1)).sendKeys(generatedValues.get(key));
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues, List<List<String>> details) {
        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "Average Net Prices" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key).replace(",","")) + 1));
                    break;
                case "% Receiving Aid" :
                    if (Integer.parseInt(fieldValues.get(key)) >= 100) {
                        generatedValues.put(key, String.valueOf(1));
                    } else {
                        generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    }
                    break;
                case "Average Amount of Aid" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key).replace(",","")) + 1));
                    break;
            }
        }
        logger.info("\nGenerated values: \n");
        for (String key : generatedValues.keySet()) {
            logger.info(key + " : " + generatedValues.get(key) + "\n");
        }
        return generatedValues;
    }

    public void editAllFieldsBasedOnGatheredValues(DataTable stringsDataTable, HashMap<String, String> originalValues) {
        List<List<String>> fieldsDetails = stringsDataTable.cells(0);
        HashMap<String, String> newValues = generateValues(originalValues, fieldsDetails);
        CostsPageImpl.generatedValues = newValues;
        editFieldValuesWithGeneratedData(newValues, fieldsDetails);
        publish.clickPublishButton();
        publish.enterPublishReasonsText(fieldsDetails.get(3).get(1));
        publish.clickSubmitChangesButton();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(overviewEditPage.loadingIconLeftMenuLocator), 1));
        logger.info("All changes were submitted");
    }

    public void verifyValidDataInFields(DataTable stringsDataTable) {
        List<List<String>> fieldsDetails = stringsDataTable.cells(0);
        for (List<String> fieldElement : fieldsDetails) {
            switch (fieldElement.get(0)) {
                case "Average Net Prices" :
                    avgNetPricesButton().click();
                    avgNetPriceTextBox(fieldElement.get(1)).clear();
                    avgNetPriceTextBox(fieldElement.get(1)).sendKeys(fieldElement.get(2));
                    Select dropDown  = new Select(costsPreview.avgNetPriceDropDown());
                    dropDown.selectByVisibleText(costsPreview.getDropDownOption(fieldElement.get(1)));
                    assertTrue(fieldElement.get(0) + " is receiving invalid data", costsPreview.avgNetPriceText()
                            .getText().replace(",", "").equals(fieldElement.get(3)));
            }
        }

    }

    public void verifyErrorMessageWithInvalidData(DataTable stringsDataTable) {
        List<List<String>> fieldsDetails = stringsDataTable.cells(0);
        for (List<String> fieldElement : fieldsDetails) {
            switch (fieldElement.get(0)) {
                case "Average Net Prices" :
                    avgNetPricesButton().click();
                    avgNetPriceTextBox(fieldElement.get(1)).clear();
                    avgNetPriceTextBox(fieldElement.get(1)).sendKeys(fieldElement.get(2));
                    Select dropDown  = new Select(costsPreview.avgNetPriceDropDown());
                    dropDown.selectByVisibleText(costsPreview.getDropDownOption(fieldElement.get(1)));
                    assertTrue("Error message is not displayed", errorMsg().isDisplayed());
            }
        }
    }

    //Locators
    private WebElement avgNetPricesButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Average Net Prices']"));
    }
    private WebElement percentReceivingAidButton() {
        return getDriver().findElement(By.xpath("//h3[text()='% Receiving Aid']"));
    }
    private WebElement avgAmountOfAidButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Average Amount of Aid']"));
    }
    private WebElement avgNetPriceTextBox(String fieldLabel) {
        return getDriver().findElement(By.cssSelector("text-field[name=\"" + fieldLabel + "\"] input"));
    }
    private WebElement percentRceivingAidTextBox(String label) {
        return getDriver().findElement(By.cssSelector("text-field[name=\"" + label + "\"] input"));
    }
    private WebElement avgAmountOfAidTextBox(String label) {
        return getDriver().findElement(By.cssSelector("text-field[name=\"" + label + "\"] input"));
    }
    private WebElement errorMsg() {
        return getDriver().findElement(By.cssSelector("ng-form.ng-valid-maxlength.ng-dirty.ng-valid-parse.ng-invalid.ng-invalid-pattern span"));
    }
}
