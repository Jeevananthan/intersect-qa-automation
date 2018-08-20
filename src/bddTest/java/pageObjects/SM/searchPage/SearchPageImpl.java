package pageObjects.SM.searchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HS.repVisitsPage.RepVisitsPageImpl;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;
import pageObjects.SM.surveyPage.SurveyPageImpl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.support.Color;
import utilities.HUBSEditMode.Navigation;

public class SearchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSaveSearchPopupContent%sSaveSearchPopupContent.properties", fs, fs, fs, fs, fs);

    WebDriverWait wait = new WebDriverWait(driver, 10);
    public SearchPageImpl() {
        logger = Logger.getLogger(SearchPageImpl.class);
    }

    public SurveyPageImpl survey = new SurveyPageImpl();
    private RepVisitsPageImpl repVisitsPageUtility = new RepVisitsPageImpl();
    private Navigation navigation = new Navigation();

    /**
     * The below line of code for just a declaration for the object which we can use in scroll down purpose
     */
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public void verifyDarkBlueHeaderIsPresent() {
        Assert.assertTrue("The dark blue header is not displayed correctly",
                superMatchCustomHeader()
                        .getCssValue("background-color").equals("rgba(28, 29, 57, 1)")
                        && superMatchCustomHeader().findElement(By.xpath("./h1"))
                        .getText().equals("SuperMatch College Search"));
    }

    public void verifyYourFitCriteriaTextIsPresent() {
        Assert.assertTrue("'Your Fit Criteria' text is not present", driver.findElement(By.xpath("//h2[@class='heading']")).getText().equals("Your Fit Criteria"));
    }

    public void verifyChooseFitCriteriaBar() {
        List<WebElement> liElements = chooseFitCriteriaBar().findElements(By.xpath(".//li"));
        Assert.assertTrue("'Choose Fit Criteria' text is not present", liElements.get(0).getText().contains("Choose Fit Criteria"));
        Assert.assertTrue("'Location' menu item is not present", liElements.get(1).getText().contains("Location"));
        Assert.assertTrue("'Academics' menu item is not present", liElements.get(2).getText().contains("Academics"));
        Assert.assertTrue("'Admission' menu item is not present", liElements.get(3).getText().contains("Admission"));
        Assert.assertTrue("'Diversity' menu item is not present", liElements.get(4).getText().contains("Diversity"));
        Assert.assertTrue("'Institution Characteristics' menu item is not present", liElements.get(5).getText().contains("Institution Characteristics"));
        Assert.assertTrue("'Cost' menu item is not present", liElements.get(6).getText().contains("Cost"));
        Assert.assertTrue("'Student Life' menu item is not present", liElements.get(7).getText().contains("Student Life"));
        Assert.assertTrue("'Athletics' menu item is not present", liElements.get(8).getText().contains("Athletics"));
        Assert.assertTrue("'Resources' menu item is not present", liElements.get(9).getText().contains("Resources"));
    }

    public void verifySelectCriteriaButtonAndInstructionalText() {
        if (getAllPillsCloseIcon().size()>=0){
            clearAllPillsFromMustHaveAndNiceToHaveBox();
        }
        Assert.assertTrue("'Select Criteria to Start' button is not displayed", selectCriteriaButton1().isDisplayed());
        Assert.assertTrue("Instructional text is not displayed", selectCriteriaInstructionalText().getText()
                .equals("To refine your results, use the arrows to move your criteria into the \"Must Have\" and \"Nice to Have\" boxes."));

    }

    public void verifyMustHaveAndNiceToHaveBoxes() {

        Assert.assertTrue("Title for Must Have box is not displayed",
                getMustHaveBox().findElement(By.xpath("./p[@class='title']"))
                        .getText().equals("Must Have"));

        Assert.assertTrue("Helper Text for Must Have box is not displayed",
                getMustHaveBox().findElement(By.xpath("./p[@class='helper-text']"))
                        .getText().equals("A Must Have is anything you absolutely need to be happy and successful."));

        Assert.assertTrue("Title for Nice to Have box is not displayed",
                getNiceToHaveBox().findElement(By.xpath("./p[@class='title']"))
                        .getText().equals("Nice to Have"));

        Assert.assertTrue("Helper Text for Nice to Have box is not displayed",
                getNiceToHaveBox().findElement(By.xpath("./p[@class='helper-text']"))
                        .getText().equals("A Nice to Have is anything that's important to you, but isn't an absolute must have."));

    }

    public void verifyEmptyResultsTable() {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'supermatch-results')]//h3")))
                .getText().equals("No Results Yet"));

        Assert.assertTrue("The 1st header column is not gray", superMatchEmptyTable().
                findElement(By.xpath("./thead/tr/th[1]")).getCssValue("background-color").equals("rgba(230, 230, 241, 1)"));

        Assert.assertTrue("The 2nd header column is not gray", superMatchEmptyTable().
                findElement(By.xpath("./thead/tr/th[2]")).getCssValue("background-color").equals("rgba(230, 230, 241, 1)"));

        Assert.assertTrue("The 3rd header column is not gray", superMatchEmptyTable().
                findElement(By.xpath("./thead/tr/th[3]")).getCssValue("background-color").equals("rgba(230, 230, 241, 1)"));

        Assert.assertTrue("The 4th header column is not gray", superMatchEmptyTable().
                findElement(By.xpath("./thead/tr/th[4]")).getCssValue("background-color").equals("rgba(230, 230, 241, 1)"));

        Assert.assertTrue("The 5th header column is not gray", superMatchEmptyTable().
                findElement(By.xpath("./thead/tr/th[5]")).getCssValue("background-color").equals("rgba(230, 230, 241, 1)"));

        Assert.assertTrue("The 6th header column is not gray", superMatchEmptyTable().
                findElement(By.xpath("./thead/tr/th[6]")).getCssValue("background-color").equals("rgba(230, 230, 241, 1)"));

        //Verify the table headers
        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./thead/tr/th[2]")).getText()
                .equals("Fit Score"));


        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./thead/tr/th[3]")).getText()
                .equals("Academic Match"));

        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./thead/tr/th[4]")).getText()
                .equals("Pick what to show"));

        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./thead/tr/th[5]")).getText()
                .equals("Pick what to show"));

        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./thead/tr/th[6]")).getText()
                .equals("Pick what to show"));

        //Verify if the empty rows are present
        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./tbody/tr[1]")).isDisplayed());

        Assert.assertTrue(superMatchEmptyTable().findElement(By.xpath("./tbody/tr[2]")).isDisplayed());

        //Verify if 'Select Criteria to Start' button is displayed in table
        Assert.assertTrue(selectCriteriaToStart2().isDisplayed());

    }

    public void verifyZipCodeValidationMessage(DataTable dataTable) {
        String locationSearchType, selectMiles, zipCode, verifyErrorMessageIsDisplayed, verifyErrorMessageIsNotDisplayed
                , verifyPillIsDisplayedInMustHaveBox, verifyPillIsNotDisplayedInMustHaveBox;

        FCSuperMatchPageImpl.skipModals();

        openFitCriteria("Location");

        List<List<String>> rows = dataTable.asLists(String.class);

        for(int index = 1; index < rows.size(); index++)
        {
            locationSearchType = rows.get(index).get(0);
            if(locationSearchType != null && locationSearchType.trim().length() != 0)
                selectRadioButton(locationSearchType);

            selectMiles = rows.get(index).get(1);
            if(selectMiles != null && selectMiles.trim().length() != 0)
                selectOptionFromSelectMilesListBox(selectMiles);

            zipCode = rows.get(index).get(2);
            if(zipCode != null && zipCode.trim().length() != 0) {
                zipCodeTextBox().clear();
                zipCodeTextBox().sendKeys(zipCode);
            }

            verifyErrorMessageIsDisplayed = rows.get(index).get(3);
            if(verifyErrorMessageIsDisplayed != null && verifyErrorMessageIsDisplayed.trim().length() != 0) {
                Assert.assertFalse(zipcodeErrorMessageElement().getText().contains(verifyErrorMessageIsDisplayed));

                waitForUITransition();
                waitForUITransition();

                Assert.assertTrue(zipcodeErrorMessageElement().getText().contains(verifyErrorMessageIsDisplayed));
            }

            verifyPillIsDisplayedInMustHaveBox = rows.get(index).get(4);
            if(verifyPillIsDisplayedInMustHaveBox != null && verifyPillIsDisplayedInMustHaveBox.trim().length() != 0) {
                waitForUITransition();
                verifyMustHaveBoxContains(verifyPillIsDisplayedInMustHaveBox);
            }

            verifyPillIsNotDisplayedInMustHaveBox = rows.get(index).get(5);
            if(verifyPillIsNotDisplayedInMustHaveBox != null && verifyPillIsNotDisplayedInMustHaveBox.trim().length() != 0) {
                verifyMustHaveBoxDoesNotContain(verifyPillIsNotDisplayedInMustHaveBox);
            }

        }
    }

    public void selectOptionFromSelectMilesListBox(String optionText) {
        WebElement listBoxPlaceholder = driver.findElement(By.xpath("//div[@id='supermatch-location-miles-dropdown']/div[@role='alert']"));
        listBoxPlaceholder.click();
        WebElement optionToSelect = driver.findElement(By.xpath("//div[@id='supermatch-location-miles-dropdown']//span[text()='"+ optionText + "']"));
        optionToSelect.click();
    }

    public void verifyDarkBlueFooter() {
        Assert.assertTrue("College Search footer is not displayed", superMatchFooter().isDisplayed());

        Assert.assertTrue("College Search footer is not dark blue", superMatchFooter()
                .getCssValue("background-color").equals("rgba(28, 29, 57, 1)"));

        // placeholder changed in MATCH-3471 from Search... to Search by College Name
        Assert.assertTrue("Search box in College Search footer is not displayed", superMatchFooter().findElement(By.xpath(".//input[@placeholder='Search by College Name']"))
                .isDisplayed());

        Assert.assertTrue("'PINNED' menu is not displayed", superMatchFooter().findElement(By.xpath("//span[text()='Pinned']"))
                .isDisplayed());

        Assert.assertTrue("'THINKING ABOUT' menu is not displayed", superMatchFooter().findElement(By.xpath("//span[text()='Thinking About']"))
                .isDisplayed());

        Assert.assertTrue("'APPLYING TO' menu is not displayed", superMatchFooter().findElement(By.xpath("//span[text()='Applying To']"))
                .isDisplayed());

        Assert.assertTrue("'MORE' menu is not displayed", superMatchFooter().findElement(By.xpath("//span[text()='More']"))
                .isDisplayed());
    }

    public void verifyWidthsOfThreeBoxes() {
        Assert.assertEquals(25.0, round((firstBox().getSize().getWidth() / (double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
        Assert.assertEquals(37.5, round((secondBox().getSize().getWidth() / (double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
        Assert.assertEquals(37.5, round((thirdBox().getSize().getWidth() / (double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
    }

    private WebElement threeBoxContainer() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ui equal width grid box-container']")));
    }

    private WebElement firstBox() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ui equal width grid box-container']/div[contains(@class, 'column')])[1]")));
    }

    private WebElement secondBox() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ui equal width grid box-container']/div[contains(@class, 'column')])[2]")));
    }

    private WebElement thirdBox() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ui equal width grid box-container']/div[contains(@class, 'column')])[3]")));
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


    /**
     * Accepts a DataTable that describes the location fit criteria to be selected, and selects them from the dialog
     *
     * @param dataTable - Valid sections:  Search Type, State or Province, Quick Selection: US Regions & Others, Campus Surroundings
     *                  Values for each section should be comma separated and in the format the page displays.  e.x.:  Mid-Atlantic, Northeast
     */
    public void setLocationCriteria(DataTable dataTable) {
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        chooseFitCriteriaTab("Location");
        for (Map<String, String> criteria : entities) {
            for (String key : criteria.keySet()) {
                switch (key) {
                    // TODO - Some of this is not working yet
                    case "Search Type":
                        if (criteria.get(key).contains("distance"))
                            searchByDistance().click();
                        else
                            radioButton("searchByStateOrRegion").select();
                        break;
                    case "State or Province":
                        String[] states = criteria.get(key).split(",");
                        for (String state : states) {
                            // TODO - Actually access this component - it's poorly labeled in the app
                        }
                        break;
                    case "Quick Selection: US Regions & Others":
                        String[] regions = criteria.get(key).split(",");
                        for (String region : regions) {
                            checkbox(region).click();
                        }
                        break;
                    case "Campus Surroundings":
                        String[] surroundings = criteria.get(key).split(",");
                        for (String surrounding : surroundings) {
                            checkbox(surrounding).select();
                        }
                        break;
                    case "Select Miles":
                        selectMilesDropdown().click();
                        driver.findElement(By.xpath("//span[text()='" + criteria.get(key) + "']")).click();
                        break;
                    case "Zip Code":
                        zipCodeInput().sendKeys(criteria.get(key));
                        break;

                }
            }
        }
    }

    /**
     * Accepts a DataTable that describes the diversity
     *
     * @param dataTable - Valid sections:  Diversity, Percentage, Select race or ethnicity etc.
     */
    public void setDiversityCriteria(DataTable dataTable) {
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        chooseFitCriteriaTab("Diversity");
        for (Map<String, String> criteria : entities) {
            for (String key : criteria.keySet()) {
                switch (key) {
                    case "Diversity":
                        if (criteria.get(key).contains("Overall"))
                            overallDiversity().click();
                        else
                            specificDiversity().click();
                        break;
                    case "Percentage":
                        diversityPercentDropdown().click();
                        diversityPercentDropdown().findElement(By.xpath("//*[text()='" + criteria.get(key) + "']")).click();
                        break;
                    case "Select race or ethnicity":
                        diversityRaceDropdown().click();
                        diversityRaceDropdown().findElement(By.xpath("//*[text()='" + criteria.get(key) + "']")).click();
                        break;
                    case "% MALE VS. FEMALE":
                        maleFemalePercentDropdown().click();
                        maleFemalePercentDropdown().findElement(By.id("male-female-percent-selection-option-" + criteria.get(key) + "")).click();
                        break;
                    case "Gender":
                        maleFemaleGenderDropdown().click();
                        maleFemaleGenderDropdown().findElement(By.xpath("//*[text()='" + criteria.get(key) + "']")).click();
                        break;
                }
            }
        }
    }

    /**
     * Accepts a String with the name of the option in the Resources fit criteria list to activate.
     *
     * @param option String with the name of the option to enable.  e.x.: Counseling Services, Day Care Services, etc.
     */
    public void setResourcesCriteria(String option) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(text(),'Resources')]"))).click();
        if (option.equals("Asperger's/Autism Support"))
            option = "Autism Support";
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '" + option + "')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '" + option + "')]/../input"));
        if (!checkbox.isSelected()) {
            label.click();
            waitUntilPageFinishLoading();
        }
        closeButtonForFitCriteria().click();
    }

    public void selectRadioButtonInAcademicsFitCriteria(String option) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'" + option.split("'")[0] + "')]"))).click();
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    /**
     * select any radio button only when fit criteria menu is open.
     */
    public void selectRadioButton(String radioButton){
        WebElement radioButtonLocator = driver.findElement(By.xpath("//label[contains(text(), '"+radioButton+"')]"));
        WebElement onlyRadioButton = driver.findElement(By.xpath("//label[contains(text(), '"+radioButton+"')]/../input"));
        Assert.assertTrue(radioButton+" radioButton by default is not selected.", !radioButtonLocator.isSelected());
        if (!radioButtonLocator.isSelected()) {
            radioButtonLocator.click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue(radioButton+" radio button is not selected.", onlyRadioButton.isSelected());
    }


    public void selectMajorsFromSearchMajorsComboBoxForBachelorsDegreeType(DataTable items) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        WebElement chevronInSearchMajorsCombobox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class, 'supermatch-menuitem-popup')]//i[@class='teal chevron down icon'])[1]")));

        //open combobox
        chevronInSearchMajorsCombobox.click();

        List<List<String>> itemsToSelect = items.raw();
        int itemsToSelectSize = itemsToSelect.size();

        for (int i = 0; i < itemsToSelectSize; i++) {
            String item = itemsToSelect.get(i).get(0);
            getDriver().findElement(By.xpath("(//span[text()='" + item + "'])[1]")).click();

        }

        //close combobox
        chevronInSearchMajorsCombobox.click();
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void unselectMajorsFromSearchMajorsComboBoxForBachelorsDegreeType(DataTable items) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();

        List<List<String>> itemsToUnselect = items.raw();
        int itemsToUnselectSize = itemsToUnselect.size();

        for (int i = 0; i < itemsToUnselectSize; i++) {
            String item = itemsToUnselect.get(i).get(0);
            getDriver().findElement(By.xpath("(//div[@role='combobox'])[1]//a[text()='" + item + "']/i[@class='delete icon']")).click();
        }

        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void selectMinorsFromSearchMinorsComboBoxForBachelorsDegreeType(DataTable items) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        WebElement chevronInSearchMinorsCombobox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[contains(@class, 'supermatch-menuitem-popup')]//i[@class='teal chevron down icon'])[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chevronInSearchMinorsCombobox);

        //open combobox
        chevronInSearchMinorsCombobox.click();

        List<List<String>> itemsToSelect = items.raw();
        int itemsToSelectSize = itemsToSelect.size();

        for (int i = 0; i < itemsToSelectSize; i++) {
            String item = itemsToSelect.get(i).get(0);
            getDriver().findElement(By.xpath("(//span[text()='" + item + "'])")).click();
        }

        //close combobox
        chevronInSearchMinorsCombobox.click();
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void unselectMinorsFromSearchMinorsComboBoxForBachelorsDegreeType(DataTable items) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();

        List<List<String>> itemsToUnselect = items.raw();
        int itemsToUnselectSize = itemsToUnselect.size();

        for (int i = 0; i < itemsToUnselectSize; i++) {
            String item = itemsToUnselect.get(i).get(0);
            getDriver().findElement(By.xpath("(//div[@role='combobox'])[2]//a[text()='" + item + "']/i[@class='delete icon']")).click();
        }

        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }


    /**
     * Accepts a String with the name of the option in the Resources fit criteria list to deactivate.
     *
     * @param option String with the name of the option to disable.  e.x.: Counseling Services, Day Care Services, etc.
     */
    public void unsetResourcesCriteria(String option) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Resources')]")).click();
        if (option.equals("Asperger's/Autism Support"))
            option = "Autism Support";
        waitUntilElementExists(getDriver().findElement(By.xpath("//label[contains(text(), '" + option + "')]")));
        WebElement label = getDriver().findElement(By.xpath("//label[contains(text(), '" + option + "')]"));
        WebElement checkbox = getDriver().findElement(By.xpath("//label[contains(text(), '" + option + "')]/../input"));
        if (checkbox.isSelected()) {
            label.click();
            waitUntilPageFinishLoading();
        }
        closeButtonForFitCriteria().click();
    }

    /**
     * Accepts a String with the name of the option in the Resources fit criteria to verify, and whether it should
     * be checked or unchecked.
     *
     * @param option             String with the name of the option to verify.  e.x.: Counseling Services, Day Care Services, etc.
     * @param checkedOrUnchecked String containing "checked" or "unchecked"
     */
    public void verifyResourcesCriteria(String option, String checkedOrUnchecked) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Resources')]")).click();
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '" + option + "')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '" + option + "')]/../input"));
        if (checkedOrUnchecked.equalsIgnoreCase("checked")) {
            Assert.assertTrue("Expected '" + option + "' to be selected, but it was not.", checkbox.isSelected());
        } else if (checkedOrUnchecked.equalsIgnoreCase("unchecked")) {
            Assert.assertFalse("Expected '" + option + "' to be unselected, but it was not.", checkbox.isSelected());
        } else {
            Assert.fail("Expected value shuould be 'checked' or 'unchecked'. '" + checkedOrUnchecked + "' is not a valid value.");
        }
        closeButtonForFitCriteria().click();
    }

    /**
     * Verifies that the "Must Have" box contains the passed item.
     *
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void verifyMustHaveBoxContains(String item) {
        Assert.assertTrue("'Must Have' box should contain " + item + ", but it does not.", getMustHaveBox().getText().contains(item));
    }

    /**
     * Verifies that the "Must Have" box does not contain the passed item.
     *
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void verifyMustHaveBoxDoesNotContain(String item) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Assert.assertTrue("'Must Have' box should not contain " + item + ", but it does.", !getMustHaveBox().findElement(By.xpath("./div/button[contains(text(),'" + item + "')]")).isDisplayed());//.getText().contains(item.toUpperCase()));
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (org.openqa.selenium.NoSuchElementException nsee) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            logger.info("Could not find the 'Must Have' box, so the item we don't want to see there clearly isn't there.");
        }
    }

    /**
     * Verifies that the "Nice to Have" box contains the passed item.
     *
     * @param item String containing the value to look for in the "Nice to Have" box.
     */
    public void verifyNiceToHaveBoxContains(String item) {
        Assert.assertTrue("'Nice to Have' box should contain " + item + ", but it does not.", getNiceToHaveBox().getText().contains(item));
    }

    /**
     * Verifies that the "Nice to Have" box does not contain the passed item.
     *
     * @param item String containing the value to look for in the "Nice to Have" box.
     */
    public void verifyNiceToHaveBoxDoesNotContain(String item) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Assert.assertTrue("'Nice to Have' box should not contain " + item + ", but it does.", !getNiceToHaveBox().findElement(By.xpath("./div/button[contains(text(),'" + item + "')]")).isDisplayed());//.getText().contains(item.toUpperCase()));
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (org.openqa.selenium.NoSuchElementException nsee) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            logger.info("Could not find the 'Nice to Have' box, so the item we don't want to see there clearly isn't there.");
        }
    }

    /**
     * Moves the passed item from the "Must Have" box to the "Nice to Have" box.
     *
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void moveToNiceToHave(String item) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getParent(button(item)).findElement(By.xpath(".//button[3]/i[@class='arrow right icon']")));
        // Intermittent problems with clicking this in Embedded version, so sending the click directly with JS.
        jsClick(getParent(button(item)).findElement(By.xpath(".//button[3]/i[@class='arrow right icon']")));
    }

    /**
     * Moves the passed item from the "Nice to Have" box to the "Must Have" box.
     *
     * @param item String containing the value to look for in the "Nice to Have" box.
     */
    public void moveToMustHave(String item) {
        getParent(button(item)).findElement(By.xpath(".//button[3]/i[@class='arrow left icon']")).click();
    }

    /**
     * Removes the passed item from the "Must Have" or "Nice to Have" box.
     *
     * @param item String containing the value to look for in the "Must HAae" or "Nice to Have" boxes.
     */
    public void removeFitCriteria(String item) {
        getParent(button(item)).findElement(By.xpath(".//button[1]")).click();
    }

    public void verifyStudentBodyUI() {
        institutionCharacteristicsMenuItem().click();
        Assert.assertTrue("'All students' radio button is not selected by default", allStudentsRadioButton().isSelected());
        undergraduateStudentsOnlyLabel().click();
        Assert.assertTrue("'Undergraduate students only' radio button is not selected", undergraduateStudentsOnlyRadioButton().isSelected());

        veryLargeStudentBodyLabel().click();
        Assert.assertTrue("'Very large (Over 20,000 students)' checkbox is not selected", veryLargeStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxContains("Student Body Size [1]");
        veryLargeStudentBodyLabel().click();
        Assert.assertFalse("'Very large (Over 20,000 students)' checkbox is selected", veryLargeStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        largeStudentBodyLabel().click();
        Assert.assertTrue("'Large (13,001 to 20,000 students)' checkbox is not selected", largeStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxContains("Student Body Size [1]");
        largeStudentBodyLabel().click();
        Assert.assertFalse("'Large (13,001 to 20,000 students)' checkbox is selected", largeStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        midSizeStudentBodyLabel().click();
        Assert.assertTrue("'Mid-Size (7,001 to 13,000 students)' checkbox is not selected", midSizeStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxContains("Student Body Size [1]");
        midSizeStudentBodyLabel().click();
        Assert.assertFalse("'Mid-Size (7,001 to 13,000 students)' checkbox is selected", midSizeStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        mediumStudentBodyLabel().click();
        Assert.assertTrue("'Medium (4,001 to 7,000 students)' checkbox is not selected", mediumStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxContains("Student Body Size [1]");
        mediumStudentBodyLabel().click();
        Assert.assertFalse("'Medium (4,001 to 7,000 students)' checkbox is selected", mediumStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        smallStudentBodyLabel().click();
        Assert.assertTrue("'Small (2,001 to 4,000 students)' checkbox is not selected", smallStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxContains("Student Body Size [1]");
        smallStudentBodyLabel().click();
        Assert.assertFalse("'Small (2,001 to 4,000 students)' checkbox is selected", smallStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        verySmallStudentBodyLabel().click();
        Assert.assertTrue("'Very Small (2,000 or fewer students)' checkbox is not selected", verySmallStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxContains("Student Body Size [1]");
        verySmallStudentBodyLabel().click();
        Assert.assertFalse("'Very Small (2,000 or fewer students)' checkbox is selected", verySmallStudentBodyCheckbox().isSelected());
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");
    }

    public void verifySystemResponseWhenGPAInputIsValid(DataTable dataTable) {

        List<String> scores = dataTable.asList(String.class);


        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }

        for (String score : scores) {
            gpaTextBox().clear();
            gpaTextBox().sendKeys(score);
            Assert.assertFalse(GPAValidationMessageElement().getText().contains("GPA value must be a number between 0.1 and 4"));
        }
    }

    public void verifySystemResponseWhenGPAInputIsInvalid(DataTable dataTable) {

        List<String> scores = dataTable.asList(String.class);

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }

        for (String score : scores) {
            gpaTextBox().clear();
            gpaTextBox().sendKeys(score);
            waitUntil(ExpectedConditions.textToBe(By.cssSelector(".supermatch-error-text"),"GPA value must be a number between 0.1 and 4"));
        }
    }

    public void verifySystemResponseWhenSATScoreInputIsValid() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
           selectFitCriteria("Admission");
        }

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("400");
        Assert.assertFalse(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1000");
        Assert.assertFalse(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        Assert.assertFalse(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

    }

    public void verifySystemResponseWhenSATScoreInputIsInvalid() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
           selectFitCriteria("Admission");
        }

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("100");
        waitUntil(ExpectedConditions.textToBe(By.cssSelector(".supermatch-error-text"),"SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("399");
        waitUntil(ExpectedConditions.textToBe(By.cssSelector(".supermatch-error-text"),"SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1601");
        waitUntil(ExpectedConditions.textToBe(By.cssSelector(".supermatch-error-text"), "SAT value must be a number between 400 and 1600"));

    }

    public void verifyIfSATScoreDataIsStoredOnOurSide() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
          selectFitCriteria("Admission");
        }

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("827");
        resourcesMenuItem().click();

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }
        Assert.assertTrue("SAT score data is not persisting", satScoreTextBox().getAttribute("value").equals("827"));
        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1300");
        resourcesMenuItem().click();

    }

    public void verifyGPADataPersists(DataTable dataTable) {

        List<List<String>> data = dataTable.raw();
        String dataToPersist = data.get(0).get(0);

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }


        gpaTextBox().clear();
        gpaTextBox().sendKeys(dataToPersist);

        getFitCriteriaCloseButton().click();

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }

        Assert.assertTrue("GPA data is not stored on our side", gpaTextBox().getAttribute("value").equals(dataToPersist));

    }

    public void verifySystemResponseWhenACTScoreIsValid(DataTable dataTable) {

        List<String> scores = dataTable.asList(String.class);

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }

        for (String score : scores) {
            actScoreTextBox().clear();
            actScoreTextBox().sendKeys(score);
            Assert.assertFalse(ACTValidationMessageElement().getText().contains("ACT value must be a number between 1 and 36"));
        }
    }

    public void verifySystemResponseWhenACTScoreIsInvalid(DataTable dataTable) {

        List<String> scores = dataTable.asList(String.class);

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }

        for (String score : scores) {
            actScoreTextBox().clear();
            actScoreTextBox().sendKeys(score);
            waitUntil(ExpectedConditions.textToBe(By.cssSelector(".supermatch-error-text"),"ACT value must be a number between 1 and 36"));
        }

    }

    public void verifyACTScoreDataPersists(DataTable dataTable) {

        List<String> scores = dataTable.asList(String.class);

        if (!admissionMenuItem().getAttribute("class").contains("active")) {
            admissionMenuItem().click();
        }

        for (String score : scores) {

            actScoreTextBox().clear();
            actScoreTextBox().sendKeys(score);

            getFitCriteriaCloseButton().click();

            if (!admissionMenuItem().getAttribute("class").contains("active")) {
                admissionMenuItem().click();
            }

            Assert.assertTrue("ACT score data is not stored on our side", actScoreTextBox().getAttribute("value").equals(score));
        }
    }

    public void selectOrUnselectDiversityCheckbox(String selectOrUnselect, String option) {
        switch (selectOrUnselect.toUpperCase()) {
            case "SELECT":
                selectCheckBox(option, "Diversity");
                break;
            case "UNSELECT":
                unselectCheckbox(option, "Diversity");
                break;
        }

    }

    public void verifySurvey(String buttonLabel) {
        button(buttonLabel).click();
        String winHandleBefore = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        Assert.assertTrue("The survey is not displayed", survey.surveySubtitle().isDisplayed());
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
        }
        driver.switchTo().window(winHandleBefore);
    }

    /**
     * Activates particular filter criteria tab
     *
     * @param filterCriteria containing the value of filter tab, example:Locale, Admission, etc.
     */
    public void chooseFitCriteriaTab(String filterCriteria) {
        checkbox(By.xpath("(//li[contains(.,'" + filterCriteria.split(":")[0] + "')])")).click();

    }

    /**
     * @param validationMessage
     */
    public void checkValidationMessageIsVisible(String validationMessage) {
        Assert.assertTrue("Validation message '" + validationMessage + "' did not appear",
                driver.findElement(By.className("supermatch-error-text")).getText().equals(validationMessage));
    }

    public void verifyMeets100ofNeedCheckbox(String checkBox) {
        chooseFitCriteriaTab("Cost");
        String path = "//label[contains(text(), '" + checkBox + "')]";
        Assert.assertTrue("Meets 100% of Need fit criteria is not displaying.", driver.findElement(By.xpath(path)).getText().equals("Meets 100% of Need"));
        Assert.assertTrue("Tooltip for Meets 100% of Need fit criteria is not displaying.", driver.findElement(By.xpath(path + "/../../button[@aria-label='undefined help']")).isDisplayed());
        getFitCriteriaCloseButton().click();
    }

    /**
     * select any selected checkbox only when fit criteria menu is open.
     */
    public void selectCheckBox(String checkBox, String fitCriteriaName) {
        if (!(driver.findElements(By.xpath("//h1[text()='" + fitCriteriaName + "']")).size() > 0))
            openFitCriteria(fitCriteriaName);
        WebElement checkboxLocator = driver.findElement(By.xpath("//label[contains(text(), \"" + checkBox + "\")]"));
        WebElement onlyCheckbox = driver.findElement(By.xpath("//label[contains(text(), \"" + checkBox + "\")]/../input"));
//        Assert.assertTrue(checkBox + " checkbox by default is not selected.", !checkboxLocator.isSelected());
        if (!onlyCheckbox.isSelected()) {
            checkboxLocator.click();
        }
        Assert.assertTrue(checkBox + " checkbox is not selected.", onlyCheckbox.isSelected());
        getFitCriteriaCloseButton().click();
    }

    /**
     * select any selected checkbox/radiobutton and do not close the window
     */
    public void selectCheckBoxNotClosingTab(String checkBox, String fitCriteriaName) {
        if (!(driver.findElements(By.xpath("//h1[text()='" + fitCriteriaName + "']")).size() > 0))
            openFitCriteria(fitCriteriaName);
        WebElement checkboxLocator = driver.findElement(By.xpath("//label[contains(text(), '" + checkBox + "')]"));
        WebElement onlyCheckbox = driver.findElement(By.xpath("//label[contains(text(), '" + checkBox + "')]/../input"));
        if (!onlyCheckbox.isSelected()) {
            checkboxLocator.click();
        }
        Assert.assertTrue(checkBox + " checkbox is not selected.", onlyCheckbox.isSelected());
    }

    /**
     * unselect any selected checkbox only when fit criteria menu is open.
     */
    public void unselectCheckbox(String checkBox, String fitCriteriaName) {
        if (!(driver.findElements(By.xpath("//h1[text()='" + fitCriteriaName + "']")).size() > 0))
            openFitCriteria(fitCriteriaName);
        WebElement checkboxLocator = driver.findElement(By.xpath("//label[contains(text(), '" + checkBox + "')]"));
        WebElement onlyCheckbox = driver.findElement(By.xpath("//label[contains(text(), '" + checkBox + "')]/../input"));
        Assert.assertTrue(checkBox + " checkbox is not selected.", onlyCheckbox.isSelected());
        if (onlyCheckbox.isSelected()) {
            checkboxLocator.click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue(checkBox + " checkbox is selected.", !onlyCheckbox.isSelected());
        getFitCriteriaCloseButton().click();
    }


    public void verifyCheckboxState(String checkBox, String expectedState, String fitCriteriaName) {
        if (!(driver.findElements(By.xpath("//h1[text()='" + fitCriteriaName + "']")).size() > 0))
            openFitCriteria(fitCriteriaName);
        WebElement checkboxLocator = driver.findElement(By.xpath("//label[contains(text(), '" + checkBox + "')]"));
        WebElement onlyCheckbox = driver.findElement(By.xpath("//label[contains(text(), '" + checkBox + "')]/../input"));
        switch(expectedState.toUpperCase())
        {
            case "UNSELECTED":Assert.assertTrue(checkBox + " checkbox is selected.", !onlyCheckbox.isSelected());
                              break;
            case "SELECTED": Assert.assertTrue(checkBox + " checkbox is not selected.", onlyCheckbox.isSelected());
                             break;
        }
        getFitCriteriaCloseButton().click();
    }


    private void openFitCriteria(String fitCriteria) {
        driver.findElement(By.xpath("//li[contains(text(), '" + fitCriteria + "')]")).sendKeys(Keys.RETURN);
    }

    public void selectMeest100ofNeedCheckbox(String checkboxName) {
        selectCheckBox(checkboxName, "Cost");
    }

    public void selectStudentSuccessFitCriteriaCheckbox(String checkboxName) {
        selectCheckBox(checkboxName, "Institution Characteristics");
    }

    public void unselectStudentSuccessFitCriteriaCheckbox(String checkboxName) {
        unselectCheckbox(checkboxName, "Institution Characteristics");
    }

    public void verifyStudentSuccessFitCriteriaCheckbox(String checkboxName) {
        openFitCriteria("Institution Characteristics");
        String path = "//label[contains(text(),'" + checkboxName + "')]";
        Assert.assertTrue("Student Success text is not displaying.", driver.findElement(By.xpath("//span[@class='supermatch-menu-institution-characteristics-heading'][contains(text(), 'Student Success')]")).isDisplayed());
        Assert.assertTrue(checkboxName + " label is not displaying.", driver.findElement(By.xpath(path)).isDisplayed());
        Assert.assertTrue(checkboxName + " checkbox tooltip is not showing.", driver.findElement(By.xpath(path + "/../../button/i")).isDisplayed());
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void setAdmissionCriteria(DataTable dataTable) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        waitUntilElementExists(ChooseFitCriteriaText());
        List<List<String>> entities = dataTable.asLists(String.class);
        chooseFitCriteriaTab("Admission");
        for (List<String> criteria : entities) {
            switch (criteria.get(0)) {
                case "GPA (4.0 scale)":
                    gpaTextBox().clear();
                    gpaTextBox().sendKeys(criteria.get(1));
                    break;
                case "SAT Composite":
                    satScoreTextBox().sendKeys(Keys.CONTROL + "a");
                    satScoreTextBox().sendKeys(Keys.DELETE);
                    satScoreTextBox().sendKeys(criteria.get(1));
                    break;
                case "ACT Composite":
                    actScoreTextBox().clear();
                    actScoreTextBox().sendKeys(criteria.get(1));
                    break;
                case "Acceptance Rate":
                    getAcceptanceRateCheckbox(criteria.get(1)).click();
                    break;
            }
        }
        getFitCriteriaCloseButton().click();
        waitUntilPageFinishLoading();
    }

    /**
     * The below method is to check all the fit criteria is clickable and as per the fit criteria menu option is showing
     */
    public void verifyEachFitCriteria() {
        int counter = getFitCriteriaBar().findElements(By.xpath(".//li")).size();
        for (int i = 1; i < counter; i++) {
            List<WebElement> listFitCriterias = getFitCriteriaBar().findElements(By.xpath(".//li"));
            Assert.assertTrue("Font color is not correct.", Color.fromString(listFitCriterias.get(i).getCssValue("color")).asHex().equals("#00838c"));
            listFitCriterias.get(i).click();
            Assert.assertTrue("Fit criteria menu is not displaying.", closeFitCriteria().isDisplayed());
            Assert.assertTrue("Close action is not available to close the box", closeFitCriteria().isDisplayed());
            closeFitCriteria().click();
            Assert.assertTrue("Close action is not available to close the box", getDriver().findElements(By.xpath("//i[@class='close icon']")).size() == 0);
        }
    }

    /**
     * The below method is to check while clicking outside the fit criteria, menu box is closing.
     */
    public void checkOutsideClick() {
        int counter = getFitCriteriaBar().findElements(By.xpath(".//li")).size();
        for (int i = 1; i < counter; i++) {
            List<WebElement> listFitCriterias = getFitCriteriaBar().findElements(By.xpath(".//li"));
            listFitCriterias.get(i).click();
            Assert.assertTrue("Close action is not available to close the box", closeFitCriteria().isDisplayed());
            ChooseFitCriteriaText().click();
            Assert.assertTrue("Close action is not available to close the box", getDriver().findElements(By.xpath("//i[@class='close icon']")).size() == 0);
        }
    }

    public void clearAllPillsFromMustHaveAndNiceToHaveBox(){
        List<WebElement> allPills = getAllPillsCloseIcon();
        for (WebElement singlePill :
                allPills) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", singlePill);
            wait.until(ExpectedConditions.elementToBeClickable(singlePill)).click();
        }
    }

    /**The below method is to check after clicking on Select Criteria To Start Buttons is opening Location fit criteria */

    public void checkSelectCriteriaToStartButtonsRedirectsLocation() {
        if (getAllPillsCloseIcon().size() > 0) {
            clearAllPillsFromMustHaveAndNiceToHaveBox();
        }
        Assert.assertTrue("First Select Criteria To Start button is not displaying.", firstSelectCriteriaToStartButton().isDisplayed());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstSelectCriteriaToStartButton());
        firstSelectCriteriaToStartButton().click();
        Assert.assertTrue("After clicking on Select Criteria to Start button Location fit criteria is not opening.", locationFitCriteria().isDisplayed());
        ChooseFitCriteriaText().click();
        Assert.assertTrue("Second Select Criteria To Start button is not displaying.", secondSelectCriteriaToStartButton().isDisplayed());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", secondSelectCriteriaToStartButton());
        secondSelectCriteriaToStartButton().click();
        Assert.assertTrue("After clicking on Select Criteria to Start button Location fit criteria is not opening.", locationFitCriteria().isDisplayed());
    }

    public void verifyAdmissionFitCriteriaCheckbox(String checkboxName, String fitCriteriaName) {
        String path = "//label[contains(text(),'" + checkboxName + "')]";
        if (!(driver.findElements(By.xpath("//h1[text()='" + fitCriteriaName + "']")).size() > 0))
            openFitCriteria(fitCriteriaName);
        Assert.assertTrue(checkboxName + " label is not displaying.", driver.findElement(By.xpath(path)).isDisplayed());
        Assert.assertTrue(checkboxName + " checkbox tooltip is not showing.", driver.findElement(By.xpath(path + "/../../button/i")).isDisplayed());
        openFitCriteria("Admission");
    }

    public void getInstitutionCharacteristicsFC() {
        institutionCharacteristicsMenuItem().click();
        Assert.assertTrue("Institution Characteristics fit criteria is not clicked.", getDriver().findElement(By.xpath("//h1[text()='Institution Characteristics']")).isDisplayed());
    }

    public void verifyAverageClassSizeList() {
        int j = 0;
        String tempString;
        String path = "//div[@id='class-size-selection-option-";
        Assert.assertTrue("AVERAGE CLASS SIZE text is not displaying", getAverageClassSizeText().isDisplayed());
        getAverageClassSizeListIcon().click();
        String expectedOptions[] =  {"close","10", "20", "30", "40", "50", "100"};
        ArrayList<String> actualOptions = new ArrayList<>();
        actualOptions.add(driver.findElement(By.xpath("//div[@id='classsize-dropdown-option-close']/span")).getText());
        for (int i=1;i<5;i++){
            actualOptions.add(driver.findElement(By.xpath(path+expectedOptions[i]+"']/span")).getText());
        }
        Iterator<String> ite = actualOptions.iterator();
        while (ite.hasNext()){
                tempString = ite.next();
                if(tempString.equals("Select")){
                    driver.findElement(By.xpath("//div[@id='classsize-dropdown-option-close']/span")).click();
                }
                else {
                    driver.findElement(By.xpath(path + expectedOptions[j] + "']/span")).click();
                }
            switch (tempString){
                case "Select":
                    logger.info("For Select option don't do anything...");
                    Assert.assertTrue("AVERAGE CLASS SIZE option Select is not selected.", getSelectedAverageClassSizeOption().getText().equals("Select"));
                    getAverageClassSizeListIcon().click();
                    break;
                case "10":
                    logger.info("AVERAGE CLASS SIZE option 10 is selected");
                    Assert.assertTrue("AVERAGE CLASS SIZE option 10 is not selected.", getSelectedAverageClassSizeOption().getText().equals("10"));
                    Assert.assertTrue("AVERAGE CLASS SIZE option 10 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 10"));
                    getAverageClassSizeListIcon().click();
                    break;
                case "20":
                    logger.info("AVERAGE CLASS SIZE option 20 is selected");
                    Assert.assertTrue("AVERAGE CLASS SIZE option 20 is not selected.", getSelectedAverageClassSizeOption().getText().equals("20"));
                    Assert.assertTrue("AVERAGE CLASS SIZE option 20 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 20"));
                    getAverageClassSizeListIcon().click();
                    break;
                case "30":
                    logger.info("AVERAGE CLASS SIZE option 30 is selected");
                    waitForUITransition();
                    Assert.assertTrue("AVERAGE CLASS SIZE option 30 is not selected.", getSelectedAverageClassSizeOption().getText().equals("30"));
                    Assert.assertTrue("AVERAGE CLASS SIZE option 30 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 30"));
                    getAverageClassSizeListIcon().click();
                    break;
                case "40":
                    logger.info("AVERAGE CLASS SIZE option 40 is selected");
                    Assert.assertTrue("AVERAGE CLASS SIZE option 40 is not selected.", getSelectedAverageClassSizeOption().getText().equals("40"));
                    Assert.assertTrue("AVERAGE CLASS SIZE option 40 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 40"));
                    getAverageClassSizeListIcon().click();
                    break;
                case "50":
                    logger.info("AVERAGE CLASS SIZE option 40 is selected");
                    Assert.assertTrue("AVERAGE CLASS SIZE option 50 is not selected.", getSelectedAverageClassSizeOption().getText().equals("50"));
                    Assert.assertTrue("AVERAGE CLASS SIZE option 50 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 50"));
                    getAverageClassSizeListIcon().click();
                    break;
                case "100":
                    logger.info("AVERAGE CLASS SIZE option 40 is selected");
                    Assert.assertTrue("AVERAGE CLASS SIZE option 100 is not selected.", getSelectedAverageClassSizeOption().getText().equals("100"));
                    Assert.assertTrue("AVERAGE CLASS SIZE option 100 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 100"));
                    getAverageClassSizeListIcon().click();
                    break;
            }
            j++;
        }
    }

    public void verifyMAndNSyncWithAverageClassSizeFilter() {
        text("Institution Characteristics").click();
        getMustHaveBox().findElement(By.xpath(".//div/button[3]")).click();
        getNiceToHaveBox().findElement(By.xpath(".//div/button[2]")).click();
        getAverageClassSizeListIcon().click();
        getDriver().findElement(By.xpath("//div[@id='classsize-dropdown-option-close']/span")).click();
        Assert.assertTrue("AVERAGE CLASS SIZE option 40 is displaying in Nice to Have box.", !getNiceToHaveBox().getText().contains("Class size < 40"));
        Assert.assertTrue("AVERAGE CLASS SIZE option 40 is displaying in Must Have box.", !getMustHaveBox().getText().contains("Class size < 40"));
        getAverageClassSizeListIcon().click();
        getDriver().findElement(By.xpath("//div[@id='class-size-selection-option-10']/span")).click();
        Assert.assertTrue("AVERAGE CLASS SIZE option 10 is not added to Must Have box.", getMustHaveBox().getText().contains("Class size < 10"));
        Assert.assertTrue("AVERAGE CLASS SIZE option 10 is displaying in Nice to Have box.", !getNiceToHaveBox().getText().contains("Class size < 10"));
        getFitCriteriaCloseButton().click();
    }

    public void verifyAverageClassSizeTextInResults() {
        text("Institution Characteristics").click();
        getAverageClassSizeListIcon().click();
        getDriver().findElement(By.id("class-size-selection-option-30")).click();
        getFitCriteriaCloseButton().click();
        getDriver().findElement(By.className("csr-heading-dropdown-text")).click();
        WebElement resultsColumHeader = getParent(getDriver().findElement(By.className("csr-heading-dropdown-text")));
        // This tends to go off screen when running on the grid, so just force click it.  We're not testing the functionality, just the text after this is set.
        jsClick(resultsColumHeader.findElement(By.xpath(".//span[text()='Institution Characteristics']")));
        Assert.assertTrue("Could not find \"Average Class Size\" label under Institution Characteristics!", getDriver().findElement(By.xpath("//span[@class='institution-char-label'][text()='Average Class Size']")).isDisplayed());
    }

    public void verifyDefaultColumnHeadersInResultsTable(DataTable data) {
        List<String> expectedHeaders = data.asList(String.class);

        WebElement headerTable = getDriver().findElement(By.cssSelector("table[class~=csr-header-table]"));
        List<WebElement> headerTitles = headerTable.findElements(By.xpath(".//span[@class='csr-heading-dropdown-text']"));

        List<String> actualHeaders = new LinkedList<>();
        for (WebElement we : headerTitles) {
            actualHeaders.add(we.getText());
        }
        for (String header : expectedHeaders) {
            Assert.assertTrue(header + " was not found in the default results list headers!", actualHeaders.contains(header));
        }
    }

    public void verifyIfOptionDefaultedInColumnHeaderCanBeChanged(String optionToSelect) {

        WebElement downChevron = superMatchNonEmptyTable().findElement(By.xpath("./thead/tr/th[4]//i"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", downChevron);
        downChevron.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", superMatchNonEmptyTable().findElement(By.xpath(".//span[text()='" + optionToSelect + "']")));
        superMatchNonEmptyTable().findElement(By.xpath(".//span[text()='" + optionToSelect + "']")).click();
        Assert.assertTrue("'" + optionToSelect + "' header is not displayed as the column header", superMatchNonEmptyTable().findElement(By.xpath("./thead/tr/th[4]//span[@class='csr-heading-dropdown-text']")).getAttribute("innerHTML")
                .equals(optionToSelect));

    }

    public void selectDiversityCheckbox(String checkboxName) {
        selectCheckBox(checkboxName, "Diversity");
    }

    public void selectFitCriteria(String fitCriteria) {
        driver.findElement(By.xpath("//li[contains(text(), '" + fitCriteria + "')]")).click();
    }

    public void verifyHighInternationalPopulationCheckbox(String checkBox) {
        openFitCriteria("Diversity");
        String path = "//label[contains(text(), '" + checkBox + "')]";
        Assert.assertTrue("International Students Label is not displaying.", driver.findElement(By.xpath("//span[contains(text(),'International Students')]")).isDisplayed());
        Assert.assertTrue(checkBox + " is by default is selected.", !driver.findElement(By.xpath(path + "/../input")).isSelected());
        Assert.assertTrue(checkBox + " is not displaying.", driver.findElement(By.xpath(path)).getText().equals("High International Population"));
        getFitCriteriaCloseButton().click();
    }

    public void verifyCollegeProfile(String collegeName, DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        for (String element : details) {
            switch (element) {
                case "Search results":
                    while (driver.findElements(By.xpath(getResultsCollegeNameLink(collegeName))).size() < 1) {
                        waitUntilPageFinishLoading();
                        backToTopButton().sendKeys(Keys.END);
                        waitUntilPageFinishLoading();
                        try {
                            showMoreButton().click();
                        } catch (WebDriverException e) {
                            whyDrawerButton(collegeName).sendKeys(Keys.END);
                            showMoreButton().click();
                            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
                        }
                        waitUntilPageFinishLoading();
                    }
                    whyDrawerButton(collegeName).sendKeys(Keys.SPACE);

                    searchResultsCollegeNameLink(collegeName).sendKeys(Keys.RETURN);
                    verifyProfilePage(collegeName);
                    break;
                case "Why? drawer":
                    whyDrawerButton(collegeName).click();
                    waitUntil(ExpectedConditions.elementToBeClickable(whyDrawerCollegeProfileLink()));
                    whyDrawerCollegeProfileLink().click();
                    verifyProfilePage(collegeName);
                    whyDrawerCloseButton().click();
                    break;
                case "Academic Match section":
                    whyDrawerButton(collegeName).click();
                    waitUntil(ExpectedConditions.elementToBeClickable(whyDrawerAcademicMatchLink()));
                    whyDrawerAcademicMatchLink().click();
                    verifyProfilePage(collegeName);
                    break;
            }
        }
    }

    public void verifyProfilePage(String collegeName) {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        Assert.assertTrue("The Profile Page is not displayed. UI: " + profilePageCollegeName().getText() + ". Data: " + collegeName, profilePageCollegeName().getText().trim().equals(collegeName));

        navigation.closeNewTabAndSwitchToOriginal(originalHandle);
    }

    public void pinCollege(String collegeName) {
        goToCollegeInSearchResults(collegeName);
        boolean elementNotFound = true;
        WebElement firstPinLinkCoincidence = driver.findElements(By.xpath(pinLinkLocator(collegeName))).get(0);
        if (firstPinLinkCoincidence.getText().trim().contains("PINNED")) {
            while (elementNotFound) {
                try {
                    firstPinLinkCoincidence.click();
                    waitUntilPageFinishLoading();
                    elementNotFound = false;
                } catch (WebDriverException e) {
                    whyDrawerButton(collegeName).sendKeys(Keys.ARROW_UP);
                    elementNotFound = true;
                }
            }

            elementNotFound = true;
            whyDrawerButton(collegeName).sendKeys(Keys.HOME);
            goToCollegeInSearchResults(collegeName);

            while (elementNotFound) {
                firstPinLinkCoincidence = driver.findElements(By.xpath(pinLinkLocator(collegeName))).get(0);
                try {
                    firstPinLinkCoincidence.click();
                    waitUntilPageFinishLoading();
                    elementNotFound = false;
                } catch (WebDriverException e) {
                    whyDrawerButton(collegeName).sendKeys(Keys.ARROW_DOWN);
                    elementNotFound = true;
                }
            }
        } else {
            while (elementNotFound) {
                try {
                    firstPinLinkCoincidence.click();
                    if (driver.findElements(By.xpath(pinLinkLocator(collegeName))).get(0).getText().equals("PINNED")) {
                        elementNotFound = false;
                    }
                } catch (WebDriverException e) {
                    whyDrawerButton(collegeName).sendKeys(Keys.ARROW_UP);
                    elementNotFound = true;
                }
            }
            Assert.assertTrue("The college was not pinned. UI: " + driver.findElements(By.xpath(pinLinkLocator(collegeName))).get(0).getText(), driver.findElements(By.xpath(pinLinkLocator(collegeName))).get(0).getText().equals("PINNED"));
        }
    }

    public void openPinnedCompareSchools() {
        pinnedFooterOption().click();
        comparePinnedCollegesLink().click();
    }

    public void verifySaveSearchIsClosedWhenCancelIsClicked() {
        saveSearchPopupCancelLink().click();
        Assert.assertTrue("The Save Search popup was not closed when Cancel was clicked",
                driver.findElements(By.xpath("saveSearchPopupCancelLinkLocator")).size() < 1);
    }

    public void verifySaveSearchIsClosedWithOutterClick() {
        chooseFitCriteriaBar().click();
        Assert.assertTrue("The Save Search popup was not closed when Cancel was clicked",
                driver.findElements(By.xpath("saveSearchPopupCancelLinkLocator")).size() < 1);
    }

    public void verifyTextInsideSaveSearchBox() {
        Assert.assertTrue("The text in the Save Search popup header is not correct",
                saveSearchPopupHeader().getText().equals(getStringFromPropFile(propertiesFilePath, "save.search.header")));
        Assert.assertTrue("The text in the section below the header in the Save Search popup is not correct",
                saveSearchPopupGiveANameLine().getText().equals(getStringFromPropFile(propertiesFilePath, "save.search.give.name")));
        Assert.assertTrue("The text in the Search Box in the Saved Search popup is not correct",
                saveSearchPopupSearchBox().getAttribute("placeholder").equals(getStringFromPropFile(propertiesFilePath, "save.search.box")));
        Assert.assertTrue("The text in the first line about special characters is not correct",
                saveSearchPopupSpecialCharLine1().getText().contains(getStringFromPropFile(propertiesFilePath, "save.search.not.allowed.characters.1")));
        Assert.assertTrue("The text in the second line about special characters is not correct",
                saveSearchPopupSpecialCharLine2().getText().equals(getStringFromPropFile(propertiesFilePath, "save.search.not.allowed.characters.2")));
    }

    public void verifyErrorMessageforXCharacters(String numberOfCharacters) {
        if(Integer.parseInt(numberOfCharacters) == 50) {
            saveSearchPopupSearchBox().clear();
            saveSearchPopupSearchBox().sendKeys(getStringFromPropFile(propertiesFilePath, "save.search.50.characters"));
            Assert.assertTrue("The error message text is not correct", saveSearchPopupErrorMessage().getText().
                    equals(getStringFromPropFile(propertiesFilePath, "save.search.error.message")));
        } else if(Integer.parseInt(numberOfCharacters) == 3) {
            saveSearchPopupSearchBox().clear();
            saveSearchPopupSearchBox().sendKeys("aa");
            // Save Search button is no longer clickable when less than 3 characters are entered.
            //saveSearchLink().click();
            Assert.assertTrue("The error message text is not correct", saveSearchPopupErrorMessage().getText().
                    equals(getStringFromPropFile(propertiesFilePath, "save.search.error.message.3.char")));
        }

    }

    public void saveSearchWithName(String searchName) {
        saveSearchPopupSearchBox().clear();
        saveSearchPopupSearchBox().sendKeys(searchName);
        saveSearchLink().click();
    }

    public void verifyConfirmationMessage() {
        Assert.assertTrue("The confirmation message is not displayed when a Search is saved",
                confirmationMessage().getText().contains(getStringFromPropFile(propertiesFilePath, "save.search.confirmation.message")));
    }

    /**
     * The below method is to scroll down the webpage till the specific webelement, which method is collecting in method parameter
     */
    public void scrollDown(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,350)", "");
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    /**
     * The below method is to scroll down the webpage till the end
     */
    public void scrollDownAtTheEnd(){
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void checkDiversityColumnInResult(String genderConcentration){

        scrollDown(getResultTable());
        driver.findElement(By.cssSelector(".csr-heading-dropdown-text")).click();
        scrollDown(driver.findElement(By.xpath("//span[contains(text(), 'Diversity')]")));
        getResultTable().findElement(By.xpath("//span[contains(text(), 'Diversity')]")).click();

                    List<WebElement> columnData = getResultTable().findElements(By.xpath(".//tbody//td[4]"));
                    List<String>actualData = new ArrayList<>();
                    for(WebElement data: columnData){
                        actualData.add(data.getText());
                    }
                    if(actualData.contains(genderConcentration)&&actualData.contains("% Male/Female")
                            &&actualData.contains("Out of State")&&actualData.contains("International")
                            &&actualData.contains("Minorities")){
                        logger.info("Diversity Results Column contains the following status : "+genderConcentration+", " +
                                "% Male/Female, Out of State, International and Minorities");

                }
            }

    private void goToCollegeInSearchResults(String collegeName) {
        while(driver.findElements(By.xpath(getResultsCollegeNameLink(collegeName))).size() < 1) {
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(showMoreSpinnerLocator), 0));
            waitUntil(ExpectedConditions.elementToBeClickable(backToTopButton()));
            backToTopButton().sendKeys(Keys.END);
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(showMoreSpinnerLocator), 0));
            showMoreButton().sendKeys(Keys.RETURN);
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(showMoreSpinnerLocator), 0));
        }
        repVisitsPageUtility.scrollDown(driver.findElements(By.xpath(pinLinkLocator(collegeName))).get(0));
    }

    public void verifySingleValueInCostColumn(String collegeName, String singleValue) {
        goToCollegeInSearchResults(collegeName);
        Assert.assertTrue("The value of " + singleValue + " is not displayed in the Cost column",
                singleCostValue(collegeName).getText().equals(singleValue));
    }

    public void verifyFootnoteNoGPANoScores(DataTable dataTable) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        List<String> textMessage = dataTable.asList(String.class);
        List<WebElement> footnotes = driver.findElements(By.cssSelector(noGPANoScoresFootnoteLocator));
        Assert.assertTrue("The text in the footnote for no GPA and no scores is incorrect.",
                footnotes.get(0).getText().equals(textMessage.get(0)));
    }

    public void verifyFootnoteGPANoScores(String collegeName, DataTable dataTable) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        List<String> textMessage = dataTable.asList(String.class);
        Assert.assertTrue("The text in the footnote for known GPA but unknown scores is incorrect.",
                collegeFootnote(collegeName).getText().equals(textMessage.get(0)));
    }


    public void verifyBackToTopButtonFunctionality() {
        backToTopButton().sendKeys(Keys.END);
        backToTopButton().sendKeys(Keys.RETURN);
        waitUntilPageFinishLoading();
        JavascriptExecutor executor = driver;
        Long value = (Long) executor.executeScript("return window.pageYOffset;");
        Assert.assertTrue("The Back to top button did not send the screen to the top",
                value == 0);
    }

    public void verifyColumnHeaders(DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        for(String element : details) {
            switch (element) {
                case "Fit Score" :
                    Assert.assertTrue("The header name of the Fit Score column is not correct",
                            fitScoreColumnHeader().getText().trim().contains(element));
                    break;
                case "Academic Match" :
                    Assert.assertTrue("The header name of the Academic Match column is not correct",
                            academicMatchColumnHeader().getText().trim().contains(element.split(" ")[0]) &
                                    academicMatchColumnHeader().getText().trim().contains(element.split(" ")[1]));
                    break;
                case "Admission Info" :
                    Assert.assertTrue("The header name of the Admission Info column is not correct",
                            admissionInfoColumnHeader().getText().trim().equals(element));
                    break;
                case "Cost" :
                    Assert.assertTrue("The header name of the Cost column is not correct",
                            costColumnHeader().getText().trim().equals(element));
                    break;
                case "Pick what to show" :
                    Assert.assertTrue("The header of the Pick what to show column is not correct",
                            pickWhatToShowColumnHeader().getText().trim().equals(element));
                    break;
            }
        }
    }

    /**
     * Verifies the search by college name text box
     * @param message
     */
    public void verifySearchByCollegeNameTextBox(String message){
        Assert.assertTrue("The Search by college name text box is not displayed",
                getSearchByCollegeNameTextBox().isDisplayed());
        Assert.assertEquals(String.format("The default text of the search by college name text box is not correct, " +
                        "expected: %s, actual: %s",message,getSearchByCollegeNameTextBox().getAttribute("placeholder"))
                ,message.trim(),getSearchByCollegeNameTextBox().getAttribute("placeholder").trim());
        Assert.assertTrue("The magnifying icon is not displayed in the search by college name text box",
                getSearchIcon().isDisplayed());
    }

    /**
     * Search the given college by name
     * @param name
     */
    public void searchCollegeByName(String name){
        getSearchByCollegeNameTextBox().clear();
        getSearchByCollegeNameTextBox().sendKeys(name);
        waitForUITransition();
    }

    /**
     * Verifies the given text is displayed in the search by college name results box
     * @param text
     */
    public void verifyTextInSearchByCollegeNameResults(String text){
        String actualText = getSearchByCollegeResultBoxMessage().getAttribute("innerText")
                .replace("\"","");
        Assert.assertEquals(String.format(
                "The text in the search by college name text box results is not correct, expected: %s, actual: %s"
                ,text,actualText),text,actualText);
    }

    /**
     * Verifies that is displayed the given amount of results when searching by college name
     * @param amount
     */
    public void verifyAmountOfResultsWhenSearchingByCollegeName(String amount){
        int resultAmount = getSearchByCollegeResultList().findElements(
                By.cssSelector("div[class='item supermatch-search-college-by-name-result']")).size();
        String actualAmount = Integer.toString(resultAmount);
        Assert.assertEquals(String.format("The amount of displayed results is not correct, expected: %s, actual: %s",
                amount,actualAmount),amount,actualAmount);
    }

    /**
     * Verifies the given text is displayed in the seach by text box when no results were found
     * @param message
     */
    public void  verifySearchByCollegeNameNoResultFoundMessage(String message){
        String actualMessage= getSearchByCollegeNameNoResultFoundMessage().getAttribute("innerText")
                .replace("\"","");
        Assert.assertEquals(String.format(
                "The text in the search by college name text box results is not correct, expected: %s, actual: %s"
                ,message,actualMessage),message,actualMessage);
    }

    public void verifyOnlineLearningOpportunitiesTooltipIcon() {

        try {
            setImplicitWaitTimeout(1);
            if(firstOnboardingPopup().isDisplayed())
                superMatchCollegeSearchHeader().click();
            resetImplicitWaitTimeout();
        } catch (Exception e) {
            resetImplicitWaitTimeout();
        }

        chooseFitCriteriaTab("Academics");

        selectRadioButton("Certificate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Certificate' degree type",
                includeOnlineLearningOpportunitiesTooltipIcon().isDisplayed());

        selectRadioButton("Associate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Associate' degree type",
                includeOnlineLearningOpportunitiesTooltipIcon().isDisplayed());

        selectRadioButton("Bachelor");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Bachelor' degree type",
                includeOnlineLearningOpportunitiesTooltipIcon().isDisplayed());

        selectRadioButton("Master");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Master' degree type",
                includeOnlineLearningOpportunitiesTooltipIcon().isDisplayed());

        selectRadioButton("Doctorate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Doctorate' degree type",
                includeOnlineLearningOpportunitiesTooltipIcon().isDisplayed());

        selectRadioButton("Graduate Certificate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Graduate Certificate' degree type",
                includeOnlineLearningOpportunitiesTooltipIcon().isDisplayed());

    }

    public void verifyCLEARPINNEDLISTOptionIsClickable()
    {
        //open the PINNED dropdown
        pinnedDropdown().click();

        Assert.assertTrue("'CLEAR PINNED LIST' option is not enabled/clickable", clearPinnedListOption().isEnabled());

        //close the PINNED dropdown
        pinnedDropdown().click();
    }

    public void verifyCLEARPINNEDLISTConfirmationModal()
    {
        //open the PINNED dropdown
        pinnedDropdown().click();

        clearPinnedListOption().click();

        Assert.assertTrue("'CLEAR PINNED LIST' modal is not displayed", clearPinnedListModal().isDisplayed());
        Assert.assertTrue("'Are you sure you want to clear your pinned list?' text is not displayed in the modal",
                clearPinnedListModal().getText().contains("Are you sure you want to clear your pinned list?"));
        Assert.assertTrue("'Clearing your pinned colleges is permanent and cannot be undone.' text is not displayed in the modal",
                clearPinnedListModal().getText().contains("Clearing your pinned colleges is permanent and cannot be undone."));
        Assert.assertTrue("'YES, CLEAR MY LIST' button is not displayed in the modal", yesClearMyListButton().isDisplayed());
        Assert.assertTrue("'NO, CANCEL' button is not displayed in the modal", noDontClearMyListButton().isDisplayed());

        noDontClearMyListButton().click();
    }

    public void verifyPinnedCollegesNotClearedWhenNOCANCELbuttonIsClicked()
    {
        int numberOfPinnedColleges = Integer.parseInt(pinCount().getText());

        //open the PINNED dropdown
        pinnedDropdown().click();

        clearPinnedListOption().click();

        noDontClearMyListButton().click();

        Assert.assertTrue("Colleges were cleared when 'No, Cancel' button is clicked", numberOfPinnedColleges == Integer.parseInt(pinCount().getText()));

    }

    public void verifyCLEARPINNEDLISTIsDisabled()
    {
        //open the PINNED dropdown
        pinnedDropdown().click();

        Assert.assertFalse("'CLEAR PINNED LIST' option is enabled/clickable", clearPinnedListOption().isEnabled());

        //close the PINNED dropdown
        pinnedDropdown().click();
    }

    public void pinCollegeIfNotPinnedAlready(String collegeName) {
        goToCollegeInSearchResults(collegeName);
        if(driver.findElement(By.xpath(pinLinkLocator(collegeName))).getText().contains("PIN TO COMPARE")) {
            pinCollege(collegeName);
            waitUntilPageFinishLoading();
        }
    }

    public void startSearchOver() {
        if(driver.findElements(By.cssSelector(startOverButtonLocator)).size() > 0) {
            startOverButton().click();
            yesStartOverLink().click();
        }
    }

    public void reloadPage() {
        //We need the fixed waiter because the spinner might keep loading forever (MATCH-4830), so we need to establish
        //the desired fit criteria and reload after some fixed time.
        waitForUITransition();
        driver.get(driver.getCurrentUrl());
    }

    public void verifyTextDisplayedInMaleVsFemaleFitCriteria() {
        chooseFitCriteriaTab("Diversity");

        Assert.assertTrue("'% Male Vs. Female' section header is not displayed",
                maleVsFemaleSectionHeader().isDisplayed());
        Assert.assertTrue("'At least' text is not displayed",
                maleVsFemaleSectionWrapper().getText().contains("At least"));
        Assert.assertTrue("'are' text is not displayed",
                maleVsFemaleSectionWrapper().getText().contains("are"));

        closeFitCriteria().click();
    }

    public void verifyPlaceholdersInSelectPercentAndSelectGenderDropdown(DataTable dataTable)
    {
        chooseFitCriteriaTab("Diversity");

        List<List<String>> data = dataTable.raw();
        String selectPercentPlaceholder = data.get(0).get(0);
        String selectGenderPlaceholder = data.get(1).get(0);
        Assert.assertTrue("The default text displayed for male vs. female percent dropdown is not correct",
                maleVsFemalePercentDropdownDefaultOption().getText().equals(selectPercentPlaceholder));
        Assert.assertTrue("The default text displayed for male vs. female gender dropdown is not correct",
                maleVsFemaleGenderDropdownDefaultOption().getText().equals(selectGenderPlaceholder));

        closeFitCriteria().click();
    }

    public void verifyOptionsInSelectPercentDropdown(DataTable dataTable)
    {
        int optionIndex = 0;
        String actualOption;

        chooseFitCriteriaTab("Diversity");

        List<String> expectedOptions = dataTable.asList(String.class);
        maleVsFemalePercentDropdownChevron().click();

        List<String> maleFemalePercentOptionsActual = maleVsFemalePercentDropdownOptions().stream().map(item -> item.getText())
                .collect(Collectors.toList());

        for (String expectedOption : expectedOptions) {
            actualOption = maleFemalePercentOptionsActual.get(optionIndex);
            Assert.assertTrue("Expected option: " + expectedOption + " but found " + actualOption + " in Male Vs. Female 'Select %' dropdown", expectedOption.equals(actualOption));
            optionIndex++;
        }

        closeFitCriteria().click();

    }

    public void verifyOptionsInSelectGenderDropdown(DataTable dataTable)
    {
        int optionIndex = 0;
        String actualOption;

        chooseFitCriteriaTab("Diversity");

        List<String> expectedOptions = dataTable.asList(String.class);
        maleVsFemaleGenderDropdownChevron().click();

        List<String> maleFemaleGenderOptionsActual = maleVsFemaleGenderDropdownOptions().stream().map(item -> item.getText())
                .collect(Collectors.toList());

        for (String expectedOption : expectedOptions) {
            actualOption = maleFemaleGenderOptionsActual.get(optionIndex);
            Assert.assertTrue("Expected option: " + expectedOption + " but found " + actualOption + " in Male Vs. Female 'Select gender' dropdown", expectedOption.equals(actualOption));
            optionIndex++;
        }

        closeFitCriteria().click();

    }

    public void iPinColleges(String numberOfCollegesToPin)
    {
        int numOfCollegesToPin = Integer.parseInt(numberOfCollegesToPin);
        WebElement pinToCompareElement;

        while(numOfCollegesToPin != 0)
        {
            try
            {
                pinToCompareElement = pinToCompareElement();
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -arguments[1].offsetHeight);", pinToCompareElement, resultsTableHeader());
                pinToCompareElement.click();
                waitForUITransition();
                numOfCollegesToPin--;
            }
            catch(Exception ex)
            {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -arguments[1].offsetHeight);", showMoreButton(), resultsTableHeader());
                showMoreButton().click();
            }
        }
    }


    public void verifyPinnedCollegesClearedWhenYesClearButtonIsClicked()
    {
        boolean isPinnedListCleared = true;
        //open the PINNED dropdown
        waitUntilPageFinishLoading();
        pinnedDropdown().click();

        if(clearPinnedListOption().getAttribute("aria-disabled").equals("false")) {
            clearPinnedListOption().click();
            yesClearMyListButton().click();
        } else {
            //close the PINNED dropdown
            pinnedDropdown().click();
        }

        try {
            waitUntil(ExpectedConditions.textToBePresentInElement(pinCount(), "0"));
        } catch (Exception ex)
        {
            isPinnedListCleared = false;
        }

        Assert.assertTrue("The pinned list is not cleared", isPinnedListCleared);

    }

    public void verifyErrorMessageDisplayedOnPinning26thCollege() {

        Assert.assertTrue("'Only allowed to pin 25 schools' error message is not displayed", maxTwentyFivePinnedSchoolsAllowedErrorMessage().isDisplayed());

    }

    public void setCostCriteria(DataTable dataTable)
    {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        chooseFitCriteriaTab("Cost");
        for (String key : map.keySet()) {
            switch (key) {
                case "Radio":
                    selectRadioButton(map.get(key));
                    break;
                case "Maximum Cost":
                    selectOptionInMaximumCostDropdown(map.get(key));
                    break;
                case "Home State":
                    selectOptionInHomeStateDropdown(map.get(key));
                    break;
                case "Family Income":
                    selectOptionInFamilyIncomeDropdown(map.get(key));
                    break;
            }
        }

        closeButtonForFitCriteria().click();
    }

    public void verifyDataInCostCriteria(DataTable dataTable)
    {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        chooseFitCriteriaTab("Cost");
        for (String key : map.keySet()) {
            switch (key) {
                case "Family Income":
                    Assert.assertTrue("The option selected in Family Income dropdown is not correct",
                            familyIncomeDropdown().findElement(By.xpath("./div[contains(@class,'text')]")).getText().equals(map.get(key)));
                    break;
            }
        }

        closeButtonForFitCriteria().click();
    }

    public void selectOptionInMaximumCostDropdown(String option)
    {
        maximumCostDropdown().findElement(By.xpath("./i")).click();
        maximumCostDropdown().findElement(By.xpath(".//span[text()='" + option + "']")).click();
    }

    public void selectOptionInHomeStateDropdown(String option)
    {
        homeStateDropdown().findElement(By.xpath(".//div[contains(@class, 'text')]")).click();
        homeStateDropdown().findElement(By.xpath(".//span[text()='" + option + "']")).click();
    }

    public void selectOptionInFamilyIncomeDropdown(String option)
    {
        familyIncomeDropdown().findElement(By.xpath("./div[contains(@class,'text')]")).click();
        familyIncomeDropdown().findElement(By.xpath(".//span[text()='" + option + "']")).click();
    }


    public void pressButton(String text){
        waitUntil(ExpectedConditions.elementToBeClickable(button(text)));
        button(text).click();
    }

    public void pickFromDropdown(String choice, String dropdown){
        try {
            driver.findElement(By.className(dropdown)).click();
        }
        catch (Exception e){
            try {
                driver.findElement(By.id(dropdown)).click();
            }
            catch (Exception exp){
                driver.findElement(By.cssSelector(dropdown)).click();
            }
        }
        driver.findElement(By.xpath("//*[text()='"+choice+"']")).click();
    }

    public void pressWhyForCollegeWithScore(Integer score) {
        scrollDown(driver.findElement(By.xpath("//*[@class='supermatch-number'][text()='" + score + "']/../../../../../following-sibling::tr")));
        driver.findElement(By.xpath("//*[@class='supermatch-number'][text()='" + score + "']/../../../button")).click();
    }

    public void pressWhyButtonForCollege(String collegeName) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        WebElement whyButtonForCollege = driver.findElement(By.xpath("//*[text()='" + collegeName
                + "']/../../../..//button[@class='ui teal basic button supermatch-why-btn']"));
        WebElement nextCollege = driver.findElement(By.xpath("//*[text()='" + collegeName
                + "']/../../../../following-sibling::tr"));
        scrollDown(nextCollege);
        whyButtonForCollege.click();
    }

    public void verifyCheckboxIsDisplayed(String checkboxText) {

        waitUntil(ExpectedConditions.visibilityOf(getCheckBoxElementByText(checkboxText)));

    }

    public void verifyCheckboxIsNotChecked(String checkboxText) {

       Assert.assertFalse("CheckBox" + checkboxText + " is checked", getCheckBoxLabelByText(checkboxText).isSelected());

    }

    public void verifyCheckboxCanBeCheckedAndUnchecked(String checkboxText) {

        boolean check = getCheckBoxLabelByText(checkboxText).isSelected();
        if (check == true) {
            getCheckBoxElementByText(checkboxText).click();
            verifyCheckboxIsNotChecked(checkboxText);
        } else {
            getCheckBoxElementByText(checkboxText).click();
            Assert.assertTrue("CheckBox" + checkboxText + " is not checked", getCheckBoxLabelByText(checkboxText).isSelected());

        }

        check = getCheckBoxLabelByText(checkboxText).isSelected();
        if (check == false) {
            getCheckBoxElementByText(checkboxText).click();
            Assert.assertTrue("CheckBox" + checkboxText + " is not checked", getCheckBoxLabelByText(checkboxText).isSelected());
        } else {
            getCheckBoxElementByText(checkboxText).click();
            verifyCheckboxIsNotChecked(checkboxText);

        }

    }

    public void scrollToMiddleOfMainPage() {
        waitUntil(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr")));
        ((JavascriptExecutor)driver).executeScript("var scrollHeight = document.body.scrollHeight; " +
                "window.scrollTo(0, scrollHeight/2); ");
    }

    public void verifyScrollBarIsPositionedAtTheTopOfPinnedSchoolsComparePage() {
        waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//tr")));
        Long scrollPosition = (Long) ((JavascriptExecutor)driver).executeScript("return window.pageYOffset; ");
        Assert.assertEquals("Scroll bar is not positioned at the top of the page", new Long(0), scrollPosition);
    }

    /**
     * Picks a date in the calendars of 'DatePicker' type.
     *
     * @param date - Calendar object with the desired date
     */
    public void pickDateInDatePickerSM(Calendar date) {

        String dateString = getDay(date);
        if (Character.valueOf(dateString.charAt(0)).equals('0')) {
            dateString = dateString.substring(1);
        }
            while (!datePickerMonthYearText().getText().equals(getMonth(date))) {
                datePickerNextMonthButton().click();

        }
        waitForUITransition();
        driver.findElement(By.xpath("//div[@class='DayPicker-Day' or @class='DayPicker-Day DayPicker-Day--today'" +
                "or @class='DayPicker-Day DayPicker-Day--selected' or @class = 'DayPicker-Day DayPicker-Day--selected " +
                "DayPicker-Day--today'][text()='" + dateString + "']")).click();
    }

    public void clickClearCalendarIcon() {

        clearCalendarIconButton().click();
    }

    public void clearGPASATACTScores() {

        gpaTextBox().clear();
        satScoreTextBox().clear();
        actScoreTextBox().clear();
    }



    // Locators Below

    private WebElement datePickerMonthYearText() { return driver.findElement(By.cssSelector("div.DayPicker-Caption")); }
    private WebElement datePickerNextMonthButton() { return driver.findElement(By.cssSelector("span.DayPicker-NavButton.DayPicker-NavButton--next")); }
    private WebElement clearCalendarIconButton() { return driver.findElement(By.className("supermatch-application-deadline-clear-icon")); }


    private WebElement getFitCriteriaCloseButton() { return driver.findElement(By.xpath("//button[contains(text(), 'Close')]")); }
    private WebElement getMustHaveBox() { return driver.findElement(By.xpath("(//div[@class='column box box-selection'])[1]")); }
    private WebElement getNiceToHaveBox() { return driver.findElement(By.xpath("(//div[@class='column box box-selection'])[2]")); }
    private WebElement admissionMenuItem() {
        return driver.findElement(By.xpath("//div[contains(@class,'supermatch-searchfilter-menu-container')]//li[contains(text(), 'Admission')]"));
    }
    private WebElement academicsMenuItem() {
        return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']//li[contains(text(), 'Academics')]"));
    }
    private WebElement institutionCharacteristicsMenuItem() {
        return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']//li[contains(text(), 'Institution Characteristics')]"));
    }
    private WebElement gpaTextBox() {
        return driver.findElement(By.xpath("//input[@name='gpa']"));
    }
    private WebElement actScoreTextBox() {
        return driver.findElement(By.xpath("//input[@name='actScore']"));
    }
    private WebElement satScoreTextBox() {
        return driver.findElement(By.xpath("//input[@name='satScore']"));
    }
    private WebElement allStudentsRadioButton() {
        return driver.findElement(By.xpath("//label[contains(text(), 'All students')]//preceding-sibling::input"));
    }
    private WebElement undergraduateStudentsOnlyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Undergraduate students only')]"));
    }
    private WebElement undergraduateStudentsOnlyRadioButton() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Undergraduate students only')]//preceding-sibling::input"));
    }
    private WebElement veryLargeStudentBodyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Very large (Over 20,000 students)')]"));
    }
    private WebElement veryLargeStudentBodyCheckbox() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Very large (Over 20,000 students)')]//preceding-sibling::input"));
    }
    private WebElement largeStudentBodyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Large (13,001 to 20,000 students)')]"));
    }
    private WebElement largeStudentBodyCheckbox() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Large (13,001 to 20,000 students)')]//preceding-sibling::input"));
    }
    private WebElement midSizeStudentBodyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Mid-Size (7,001 to 13,000 students)')]"));
    }
    private WebElement midSizeStudentBodyCheckbox() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Mid-Size (7,001 to 13,000 students)')]//preceding-sibling::input"));
    }
    private WebElement mediumStudentBodyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Medium (4,001 to 7,000 students)')]"));
    }
    private WebElement mediumStudentBodyCheckbox() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Medium (4,001 to 7,000 students)')]//preceding-sibling::input"));
    }
    private WebElement smallStudentBodyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Small (2,001 to 4,000 students)')]"));
    }
    private WebElement smallStudentBodyCheckbox() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Small (2,001 to 4,000 students)')]//preceding-sibling::input"));
    }
    private WebElement verySmallStudentBodyLabel() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Very Small (2,000 or fewer students)')]"));
    }
    private WebElement verySmallStudentBodyCheckbox() {
        return driver.findElement(By.xpath("//label[contains(text(), 'Very Small (2,000 or fewer students)')]//preceding-sibling::input"));
    }
    private WebElement resourcesMenuItem() {
        return driver.findElement(By.xpath("//li[contains(text(), 'Resources')]"));
    }
    private WebElement superMatchCustomHeader() {
        return driver.findElement(By.xpath("//div[contains(@class, 'supermatch-custom-header')]"));
    }
    private WebElement chooseFitCriteriaBar() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='supermatch-searchfilter-menu-container']")));
        return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']"));
    }
    private WebElement selectCriteriaButton1() {
        return driver.findElement(By.xpath("(//button[text()='Select Criteria To Start'])[2]"));
    }
    private WebElement selectCriteriaInstructionalText() {
        return driver.findElement(By.xpath("//div[@class='computer only four wide column']//p"));
    }
    private WebElement superMatchEmptyTable() {
        return driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table"));
    }
    private WebElement superMatchNonEmptyTable() {
        return driver.findElement(By.xpath("(//div[contains(@class, 'supermatch-results')]//table[contains(@class, 'csr-results-table')])[1]"));
    }
    private WebElement selectCriteriaToStart2() {
        return driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//button[text()='Select Criteria To Start']"));
    }
    private WebElement superMatchFooter() {
        return driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]"));
    }
    private WebElement closeButtonForFitCriteria(){
        return getDriver().findElement(By.xpath("//button[contains(text(),' Close')]"));
    }
    private WebElement getAverageClassSizeText(){ return driver.findElement(By.xpath("//span[text()='AVERAGE CLASS SIZE']")); }
    private WebElement getAverageClassSizeListIcon(){ return driver.findElement(By.id("classsize-dropdown")); }
    private WebElement getSelectedAverageClassSizeOption(){ return driver.findElement(By.xpath("//div[@id='classsize-dropdown']/div[1]")); }
    private WebElement includeOnlineLearningOpportunitiesTooltipIcon()
    {
        return driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                "//ancestor::div[@class='column']//i[@class='teal info circle icon']"));
    }

    private WebElement selectMilesDropdown() {
        return driver.findElement(By.id("supermatch-location-miles-dropdown"));
    }

    private WebElement zipCodeInput() {
        return driver.findElement(By.xpath("//input[@placeholder = 'Zip Code']"));
    }

    private WebElement searchByDistance(){
        return driver.findElement(By.xpath("//input[@value='searchByDistance']/../label"));
    }

    private WebElement overallDiversity(){
        return driver.findElement(By.xpath("//input[@value='overallDiversity']/../label"));
    }

    private WebElement specificDiversity(){
        return driver.findElement(By.xpath("//input[@value='specificDiversity']/../label"));
    }

    private WebElement diversityPercentDropdown(){
        return driver.findElement(By.id("supermatch-diversity-percent-dropdown"));
    }

    private WebElement diversityRaceDropdown(){
        return driver.findElement(By.id("supermatch-diversity-race-dropdown"));
    }

    private WebElement maleFemalePercentDropdown(){
        return driver.findElement(By.id("male-female-percent-dropdown"));
    }

    private WebElement maleFemaleGenderDropdown(){
        return driver.findElement(By.id("male-female-gender-dropdown"));
    }

    private WebElement costFitCriteria(){
        return driver.findElement(By.xpath("//li[contains(text(), 'Cost')]"));
    }
    private WebElement zipCodeTextBox() {
        return driver.findElement(By.xpath("//input[@placeholder='Zip Code']"));
    }
    private WebElement firstOnBoardingPopup() {
        return getDriver().findElement(By.xpath("//*[contains(@class, 'supermatch-onboarding-popup')]"));
    }
    private WebElement superMatchCollegeSearchHeader() {
        return getDriver().findElement(By.xpath("//h1[text()='SuperMatch College Search']"));
    }
    private WebElement zipcodeErrorMessageElement() {
        return driver.findElement(By.xpath("//div[@class='div-distance']"));
    }

    private WebElement getAcceptanceRateCheckbox(String checkboxLabel) {
        return driver.findElement(By.xpath("//label[text()='" + checkboxLabel + "']"));
    }

    private WebElement getFitCriteriaBar() {
        //return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container offscreen-right']/ul"));
        //return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']/ul"));
        //return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']/ul"));
        return driver.findElement(By.xpath("//span[contains(text(),'Choose Fit Criteria')]/../../.."));
    }

    private WebElement firstSelectCriteriaToStartButton() {
        return driver.findElement(By.xpath("(//button[contains(text(),'Select Criteria To Start')])[2]"));
    }

    private WebElement secondSelectCriteriaToStartButton(){
        return driver.findElement(By.xpath("(//button[contains(text(),'Select Criteria To Start')])[3]"));
    }

    private WebElement closeFitCriteria(){ return driver.findElement(By.xpath("//i[@class='close icon']")); }

    private WebElement locationFitCriteria(){ return getDriver().findElement(By.xpath("//h1[text()='Location']")); }

    private WebElement ChooseFitCriteriaText(){ return getDriver().findElement(By.xpath("//span[text()='Choose Fit Criteria']")); }

    public WebElement firstWhyButton() { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table']/tbody/tr[1]/td/div/button")); }

    private String getResultsCollegeNameLink(String collegeName) { return "//a[text()='" + collegeName + "']"; }

    private WebElement searchResultsCollegeNameLink(String collegeName) { return driver.findElement(By.xpath(getResultsCollegeNameLink(collegeName))); }

    public WebElement profilePageCollegeName() { return driver.findElement(By.cssSelector("h1.masthead__name.ng-binding")); }

    private WebElement whyDrawerButton(String collegeName) { return driver.findElement(By.xpath("//a[text()='" + collegeName + "']/../../../following-sibling::td/div/button")); }

    private WebElement whyDrawerCollegeProfileLink() { return driver.findElement(By.cssSelector("div.twelve.wide.column a.result-row-decription-label")); }

    private WebElement whyDrawerCloseButton() { return  driver.findElement(By.cssSelector("button.ui.teal.basic.button.asLink-btn")); }

    private WebElement whyDrawerAcademicMatchLink() { return driver.findElement(By.cssSelector("div.column em a.result-row-decription-label")); }

    private WebElement backToTopButton() { return driver.findElement(By.cssSelector("button[aria-roledescription=\"Back to top\"]")); }

    private WebElement pinnedFooterOption() { return driver.findElement(By.cssSelector("div#pinCount + span")); }

    private WebElement comparePinnedCollegesLink() { return driver.findElement(By.cssSelector("div#supermatch-pinned-compare-colleges-link span")); }

    private WebElement saveSearchPopupCancelLink() { return driver.findElement(By.xpath(saveSearchPopupCancelLinkLocator)); }

    private String saveSearchPopupCancelLinkLocator = "//button[@class='ui teal basic button' and text()='Cancel']";

    private WebElement saveSearchPopupHeader() { return driver.findElement(By.cssSelector("div.header")); }

    private WebElement saveSearchPopupGiveANameLine() { return driver.findElement(By.cssSelector("div.field label")); }

    private WebElement saveSearchPopupSpecialCharLine1() { return driver.findElement(By.cssSelector("form.ui.form p")); }

    private WebElement saveSearchPopupSpecialCharLine2() { return driver.findElement(By.cssSelector("form.ui.form p span")); }

    private WebElement saveSearchPopupSearchBox() { return driver.findElement(By.cssSelector("div.field label + div input")); }

    private WebElement saveSearchPopupErrorMessage() { return driver.findElement(By.cssSelector("div.field + p")); }

    private WebElement saveSearchLink() { return driver.findElement(By.xpath("//button[@class='ui teal basic button supermatch-save-search-modal-save' and text()='Save Search']")); }

    private WebElement confirmationMessage() { return driver.findElement(By.cssSelector("span.supermatch-toast-title + span")); }

    private WebElement getResultTable(){ return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table']")); }

    private WebElement admissionInfoResultTableIcon(){ return driver.findElement(By.xpath("//span[contains(text(), 'Admission Info')]/../i")); }

    private WebElement firstOnboardingPopup() {
        return getDriver().findElement(By.xpath("//*[contains(@class, 'supermatch-onboarding-popup')]"));
    }

    private WebElement fitScoreColumnHeader() { return driver.findElement(By.cssSelector("table.ui.unstackable.table.csr-results-table.csr-header-table tr th:nth-of-type(2)")); }

    private WebElement academicMatchColumnHeader() { return driver.findElement(By.cssSelector("table.ui.unstackable.table.csr-results-table.csr-header-table tr th:nth-of-type(3)")); }

    private WebElement admissionInfoColumnHeader() { return driver.findElement(By.cssSelector("table.ui.unstackable.table.csr-results-table.csr-header-table tr th:nth-of-type(4) div span")); }

    private WebElement costColumnHeader() { return driver.findElement(By.cssSelector("table.ui.unstackable.table.csr-results-table.csr-header-table tr th:nth-of-type(5) div span")); }

    private WebElement pickWhatToShowColumnHeader() { return driver.findElement(By.cssSelector("table.ui.unstackable.table.csr-results-table.csr-header-table tr th:nth-of-type(6) div span.csr-heading-dropdown-text")); }

    private WebElement ACTValidationMessageElement() {
        return actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]"));
    }

    private WebElement showMoreButton() { return driver.findElement(By.cssSelector("button[aria-roledescription='Load more Results']")); }

    private String pinLinkLocator(String collegeName) { return "//a[text()='" + collegeName + "']/../../div/a[@class = 'supermatch-college-action-pin-to-compare']"; }

    private WebElement singleCostValue(String collegeName) { return driver.findElement(By.xpath("//a[text() = '" + collegeName + "']/../../../../td[@class = 'sm-hidden-xl-down csr-data-points']/div/p/span[@class = 'cost-text']")); }

    private String noGPANoScoresFootnoteLocator = "div.academic-match-instructions";

    private String spinnerLocator = "div.ui.active.loader";

    private WebElement collegeFootnote(String collegeName) { return driver.findElement(By.xpath("//a[text() = '" + collegeName + "']/../../../../td[@class = 'sm-hidden-m-down csr-data-points']/div/div/span")); }

    private String showMoreSpinnerLocator = "button.ui.teal.basic.loading.disabled.button.supermatch-pagination-btn";

    private WebElement navianceLogo() { return driver.findElement(By.cssSelector("img[alt=\"Naviance\"]")); }

    /**
     * Returns the search by college name textbox
     * @return
     */
    private WebElement getSearchByCollegeNameTextBox(){
        return driver.findElement(By.id("supermatch-search-box-input"));
    }

    /**
     *Gets the search icon
     * @return
     */
    private WebElement getSearchIcon(){
        return driver.findElement(By.xpath(
                "//div[@class='ui left icon input supermatch-search-box-input sm-hidden-s-down']/i[@class='search icon']"));
    }

    /**
     * Gets the result box for the search by college text box
     * @return
     */
    private WebElement getSearchByCollegeResultBoxMessage(){
        return driver.findElement(By.xpath("//div[@class='item search-college-by-name-term']/a"));
    }

    /**
     * Gets the college results list
     * @return
     */
    private WebElement getSearchByCollegeResultList(){
        return driver.findElement(By.id("supermatch-search-college-by-name-results"));
    }

    /**
     * Gets the no result found message for the search by college name text box
     * @return
     */
    private WebElement getSearchByCollegeNameNoResultFoundMessage(){
        return driver.findElement(
                By.xpath("//div[@id='supermatch-search-college-by-name-results']/div[@role='listitem']"));
    }

    private String startOverButtonLocator = "button.ui.teal.basic.button.supermatch-start-over-button:not(.disabled)";

    private WebElement maleVsFemaleSectionHeader() {
        return getDriver().findElement(By.xpath("//div[@class='ui tiny header']/span[text()='% Male Vs. Female']"));
    }

    private WebElement maleVsFemaleSectionWrapper() {
        return getDriver().findElement(By.xpath("(//div[@class='supermatch-religious-affiliation-wrapper'])[2]"));
    }

    private WebElement maleVsFemalePercentDropdownDefaultOption() {
        return getDriver().findElement(By.xpath("//div[@id='male-female-percent-dropdown']/div[@class='default text']"));
    }

    private WebElement maleVsFemaleGenderDropdownDefaultOption() {
        return getDriver().findElement(By.xpath("//div[@id='male-female-gender-dropdown']/div[@class='default text']"));
    }

    private WebElement maleVsFemalePercentDropdownChevron() {
        return getDriver().findElement(By.xpath("//div[@id='male-female-percent-dropdown']/i"));
    }

    private List<WebElement> maleVsFemalePercentDropdownOptions() {
        return getDriver().findElements(By.xpath("//div[@id='male-female-percent-dropdown']//span"));
    }

    private WebElement maleVsFemaleGenderDropdownChevron() {
        return getDriver().findElement(By.xpath("//div[@id='male-female-gender-dropdown']/i"));
    }

    private List<WebElement> maleVsFemaleGenderDropdownOptions() {
        return getDriver().findElements(By.xpath("//div[@id='male-female-gender-dropdown']//span"));
    }

    private WebElement startOverButton() { return driver.findElement(By.cssSelector("button.ui.teal.basic.button.supermatch-start-over-button")); }

    private WebElement yesStartOverLink() { return driver.findElement(By.cssSelector("div.actions button:not([default=''])")); }

    private WebElement noCancelLink() { return driver.findElement(By.cssSelector("div.actions button[default='']")); }

    private WebElement pinnedDropdown() {
        return getDriver().findElement(By.xpath("//div[contains(@class, 'supermatch-pinned-dropdown')]"));
    }

    private WebElement pinToCompareElement() {
        return getDriver().findElement(By.xpath("(//span[text()='PIN TO COMPARE'])"));
    }

    private WebElement resultsTableHeader() {
        return getDriver().findElement(By.xpath("//tr[@class='search-results-header-row']"));
    }

    private WebElement clearPinnedListOption() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Clear Pinned List')]//ancestor::div[1]"));
    }

    private WebElement clearPinnedListModal() {
        return getDriver().findElement(By.xpath("//div[contains(@class, 'visible active supermatch-modal')]"));
    }

    private WebElement yesClearMyListButton() {
        return clearPinnedListModal().findElement(By.xpath(".//button[text()='YES, CLEAR MY LIST']"));
    }

    private WebElement noDontClearMyListButton() {
        return clearPinnedListModal().findElement(By.xpath(".//button[text()='NO, CANCEL']"));
    }

    private WebElement pinCount() {
        return getDriver().findElement(By.id("pinCount"));
    }

    private WebElement maxTwentyFivePinnedSchoolsAllowedErrorMessage() {
        return getDriver().findElement(By.xpath("//span[text()='You have reached your maximum of 25 pinned schools. Remove a pinned school to add a new one.']"));
    }

    private WebElement GPAValidationMessageElement() {
        return gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]"));
    }

    public List<WebElement> getAllPillsCloseIcon() {
        return driver.findElements(By.xpath("//i[@class='x icon'][@aria-hidden='true']"));
    }
    private WebElement maximumCostDropdown()
    {
        return getDriver().findElement(By.xpath("//div[@id='cost-maximum']"));
    }

    private WebElement homeStateDropdown()
    {
        return getDriver().findElement((By.xpath("//div[contains(@class, 'supermatch-menu-cost-homestate')]")));
    }

    private WebElement familyIncomeDropdown()
    {
        return getDriver().findElement((By.xpath("//div[@id='cost-family-income-dropdown']")));
    }

    private WebElement getCheckBoxLabelByText(String checkboxText) {
        return driver.findElement(By.xpath("//label[text()='" + checkboxText + "']/../input"));
    }

    private WebElement getCheckBoxElementByText(String checkboxText) {
        return driver.findElement(By.xpath("//label[text()='" + checkboxText + "']/.."));
    }

}
