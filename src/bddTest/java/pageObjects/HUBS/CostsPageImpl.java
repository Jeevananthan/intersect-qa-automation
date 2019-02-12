package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CostsPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;
    private HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
    private FCMainPageImpl fcMain = new FCMainPageImpl();
    private FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
    private HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
    private HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

    public CostsPageImpl() {
        logger = Logger.getLogger(CostsPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        assertTrue("Average Net Prices section is not displayed", avgNetPriceText().isDisplayed());
        assertTrue("% Receiving Aid section is not displayed", percentReceivingAidText().isDisplayed());
        assertTrue("Average Amount of Aid section is not displayed", avgGrantAmountText().isDisplayed());
    }

    public HashMap<String, String> getValuesFromFields(List<List<String>> fieldList) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        for (List<String> field : fieldList) {
            switch (field.get(0)) {
                case "Average Net Prices" :
                    Select avgNetPricesDropdown = new Select(avgNetPriceDropDown());
                    avgNetPricesDropdown.selectByVisibleText(getDropDownOption(field.get(1)));
                    fieldValues.put(field.get(0), avgNetPriceText().getText());
                    break;
                case "% Receiving Aid" :
                    Select receivingAidDropdown = new Select(receivingAidDropDown());
                    receivingAidDropdown.selectByVisibleText(field.get(1));
                    fieldValues.put(field.get(0), percentReceivingAidText().getText());
                    break;
                case "Average Amount of Aid" :
                    avgAmountOfAidButton(field.get(1)).sendKeys(Keys.RETURN);
                    fieldValues.put(field.get(0), avgGrantAmountText().getText());
                    break;
            }
        }
        logger.info("\nValues from the page: \n");
        for (String key : fieldValues.keySet()) {
            logger.info(key + " : " + fieldValues.get(key) + "\n");
        }
        return fieldValues;
    }

    private void verifyGeneratedValues(List<List<String>> sections, HashMap<String, String> generatedValues) {

        for (int i = 0; i < 20; i++) {
            if (!generatedValues.get("Average Net Prices").equals(avgNetPriceText().getText().replace(",", ""))) {
                String whatever = avgNetPriceText().getText();
                logger.info(generatedValues.get("Average Net Prices"));
                logger.info(whatever.replace(",", ""));
                Select avgNetPricesDropdown = new Select(avgNetPriceDropDown());
                avgNetPricesDropdown.selectByVisibleText(getDropDownOption(sections.get(0).get(1)));
                getDriver().get(getDriver().getCurrentUrl());
            }
        }

        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Average Net Prices" :
                    Select avgNetPricesDropdown = new Select(avgNetPriceDropDown());
                    avgNetPricesDropdown.selectByVisibleText(getDropDownOption(sections.get(0).get(1)));
                    System.out.println("cosa: " + generatedValues.get(key));
                    System.out.println("cosa: " + avgNetPriceText().getText());
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(avgNetPriceText().getText().replace(",", "")));
                    break;
                case "% Receiving Aid" :
                    Select receivingAidDropdown = new Select(receivingAidDropDown());
                    receivingAidDropdown.selectByVisibleText(sections.get(1).get(1));
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(percentReceivingAidText().getText()));
                    break;
                case "Average Amount of Aid" :
                    avgAmountOfAidButton(sections.get(2).get(1)).sendKeys(Keys.RETURN);
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(avgGrantAmountText().getText().replace(",", "")));
                    break;
            }
        }
    }

    public void verifyChangesPublishedInHUBS(String username, String password, String college, DataTable stringsDataTable) {
        List<List<String>> sections = stringsDataTable.asLists(String.class);
        List<String> creds = new ArrayList<String>() {{
            add(username);
            add(password);
            add(college);
        }};
        int numberOfTries = 0;
        hubsLogin.defaultLogIn(creds);
        fcMain.clickCollegesTab();
        collegesPage.searchAndOpenCollege(college);
        waitUntilPageFinishLoading();
        hubsMainMenu.clickCostsTab();
        waitUntilPageFinishLoading();
        for (List<String> row : sections) {
            switch (row.get(0)) {
                case "Number of tries" : numberOfTries = Integer.parseInt(row.get(1));
            }
        }
        for (int i = 0; i < numberOfTries; i++) {
            waitForUITransition();
            Select avgNetPricesDropdown = new Select(avgNetPriceDropDown());
            avgNetPricesDropdown.selectByVisibleText(getDropDownOption(sections.get(0).get(1)));
            if (!generatedValues.get("Average Net Prices").equals(avgNetPriceText().getText().replace(",", ""))) {
                header.clickLogOut();
                hubsLogin.defaultLogIn(creds);
                fcMain.clickCollegesTab();
                collegesPage.searchAndOpenCollege(creds.get(2));
                hubsMainMenu.clickCostsTab();
            }
        }
        verifyGeneratedValues(sections, generatedValues);
    }

    public String getDropDownOption(String avgNetPriceRange) {
        String option = "";
        switch (avgNetPriceRange) {
            case "$0 - $30,000" : option = "$0 - $30K";
                break;
            case "$30,001 - $48,000" : option = "$30 - $48K";
                break;
            case "$48,001 - $75,000" : option = "$48 - $75K";
                break;
            case "$75,001 - $110,000" : option = "$75 - $110K";
                break;
            case "$110,001+" : option = " > $110K";
                break;
        }
        return option;
    }

    //Locators

    public WebElement avgNetPriceText() {
        return getDriver().findElement(By.cssSelector("div.hub-data-pod--money.hub-data-pod--overview.ng-binding"));
    }
    public WebElement percentReceivingAidText() {
        return getDriver().findElement(By.cssSelector("div.hub-data-pod--percent.hub-data-pod--overview.ng-binding"));
    }
    public WebElement avgGrantAmountText() {
        return getDriver().findElement(By.cssSelector("div[ng-show=\"vm.showAvgGrantAmt\"] div.hub-data-pod--money.costs-avg-section__text.ng-binding"));
    }
    public WebElement avgNetPriceDropDown() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.averageNetPrices.length > 0\"] select"));
    }
    public WebElement receivingAidDropDown() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.typeOfAid\"] select"));
    }
    public WebElement avgAmountOfAidButton(String label) {
        String locatorPart = "";

        switch (label) {
            case "Grant" :locatorPart=" Average Grant Amount "; //locatorPart = "Average Grant Amount";  //old locator
                break;
            case "Pell Grant" : locatorPart=" Average Pell Grant Amount "; //locatorPart = "Average Pell Grant Amount";  //old locator
                break;
            case "Federal Student Loan" : locatorPart=" Average Federal Student Loan Amount "; //locatorPart = "Average Federal Student Loan Amount"; //old locator

                break;
        }
        return getDriver().findElement(By.xpath("//li[text()='" + locatorPart + "']"));
    }
}
