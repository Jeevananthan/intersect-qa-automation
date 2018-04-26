package pageObjects.SM.searchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.surveyPage.SurveyPageImpl;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SearchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SearchPageImpl() {
        logger = Logger.getLogger(SearchPageImpl.class);
    }
    public SurveyPageImpl survey = new SurveyPageImpl();


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


    public void verifyDarkBlueFooter()
    {
        Assert.assertTrue("College Search footer is not displayed", superMatchFooter().isDisplayed());

        Assert.assertTrue("College Search footer is not dark blue", superMatchFooter()
                .getCssValue("background-color").equals("rgba(28, 29, 57, 1)"));

        Assert.assertTrue("Search box in College Search footer is not displayed", superMatchFooter().findElement(By.xpath(".//input[@placeholder='Search...']"))
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
        Assert.assertEquals(25.0, round((firstBox().getSize().getWidth()/(double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
        Assert.assertEquals(37.5, round((secondBox().getSize().getWidth()/(double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
        Assert.assertEquals(37.5, round((thirdBox().getSize().getWidth()/(double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
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

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


    /**
     * Accepts a DataTable that describes the location fit criteria to be selected, and selects them from the dialog
     * @param dataTable - Valid sections:  Search Type, State or Province, Quick Selection: US Regions & Others, Campus Surroundings
     *                    Values for each section should be comma separated and in the format the page displays.  e.x.:  Mid-Atlantic, Northeast
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
                            checkbox(region).select();
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
     * @param option String with the name of the option to enable.  e.x.: Counseling Services, Day Care Services, etc.
     */
    public void setResourcesCriteria(String option) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Resources')]")).click();
        if (option.equals("Asperger's/Autism Support"))
            option="Autism Support";
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]/../input"));
        if (!checkbox.isSelected()) {
            label.click();
            waitUntilPageFinishLoading();
        }
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void selectRadioButtonInAcademicsFitCriteria(String option) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+ option.split("'")[0] + "')]"))).click();
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void selectMajorsFromSearchMajorsComboBoxForBachelorsDegreeType(DataTable items) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        WebElement chevronInSearchMajorsCombobox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class, 'supermatch-menuitem-popup')]//i[@class='teal chevron down icon'])[1]")));

        //open combobox
        chevronInSearchMajorsCombobox.click();

        List<List<String>> itemsToSelect = items.raw();
        int itemsToSelectSize = itemsToSelect.size();

        for(int i = 0; i < itemsToSelectSize; i++)
        {
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

        for(int i = 0; i < itemsToUnselectSize; i++)
        {
            String item = itemsToUnselect.get(i).get(0);
            getDriver().findElement(By.xpath("(//div[@role='combobox'])[1]//a[text()='" + item +"']/i[@class='delete icon']")).click();
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

        for(int i = 0; i < itemsToSelectSize; i++)
        {
            String item = itemsToSelect.get(i).get(0);
            getDriver().findElement(By.xpath("(//span[text()='" + item + "'])[2]")).click();
        }

        //close combobox
        chevronInSearchMinorsCombobox.click();
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    public void unselectMinorsFromSearchMinorsComboBoxForBachelorsDegreeType(DataTable items) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Academics')]")).click();

        List<List<String>> itemsToUnselect = items.raw();
        int itemsToUnselectSize = itemsToUnselect.size();

        for(int i = 0; i < itemsToUnselectSize; i++)
        {
            String item = itemsToUnselect.get(i).get(0);
            getDriver().findElement(By.xpath("(//div[@role='combobox'])[2]//a[text()='" + item +"']/i[@class='delete icon']")).click();
        }

          getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }


    /**
     * Accepts a String with the name of the option in the Resources fit criteria list to deactivate.
     * @param option String with the name of the option to disable.  e.x.: Counseling Services, Day Care Services, etc.
     */
    public void unsetResourcesCriteria(String option) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Resources')]")).click();
        if (option.equals("Asperger's/Autism Support"))
            option="Autism Support";
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]/../input"));
        if (checkbox.isSelected()) {
            label.click();
            waitUntilPageFinishLoading();
        }
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    /**
     * Accepts a String with the name of the option in the Resources fit criteria to verify, and whether it should
     * be checked or unchecked.
     * @param option String with the name of the option to verify.  e.x.: Counseling Services, Day Care Services, etc.
     * @param checkedOrUnchecked String containing "checked" or "unchecked"
     */
    public void verifyResourcesCriteria(String option, String checkedOrUnchecked) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Resources')]")).click();
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '" + option + "')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '" + option + "')]/../input"));
        if (checkedOrUnchecked.equalsIgnoreCase("checked")) {
            Assert.assertTrue("Expected '" + option + "' to be selected, but it was not.", checkbox.isSelected());
        } else if (checkedOrUnchecked.equalsIgnoreCase("unchecked")) {
            Assert.assertFalse("Expected '" + option + "' to be unselected, but it was not.",checkbox.isSelected());
        } else {
            Assert.fail("Expected value shuould be 'checked' or 'unchecked'. '" + checkedOrUnchecked + "' is not a valid value.");
        }
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    /**
     * Verifies that the "Must Have" box contains the passed item.
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void verifyMustHaveBoxContains(String item) {
        Assert.assertTrue("'Must Have' box should contain " + item + ", but it does not.",getMustHaveBox().getText().contains(item));
    }

    /**
     * Verifies that the "Must Have" box does not contain the passed item.
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void verifyMustHaveBoxDoesNotContain(String item) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Assert.assertTrue("'Must Have' box should not contain " + item + ", but it does.",!getMustHaveBox().findElement(By.xpath("./div/button[contains(text(),'"+ item +"')]")).isDisplayed());//.getText().contains(item.toUpperCase()));
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (org.openqa.selenium.NoSuchElementException nsee) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            logger.info("Could not find the 'Must Have' box, so the item we don't want to see there clearly isn't there.");
        }
    }

    /**
     * Verifies that the "Nice to Have" box contains the passed item.
     * @param item String containing the value to look for in the "Nice to Have" box.
     */
    public void verifyNiceToHaveBoxContains(String item) {
        Assert.assertTrue("'Nice to Have' box should contain " + item + ", but it does not.",getNiceToHaveBox().getText().contains(item));
    }

    /**
     * Verifies that the "Nice to Have" box does not contain the passed item.
     * @param item String containing the value to look for in the "Nice to Have" box.
     */
    public void verifyNiceToHaveBoxDoesNotContain(String item) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Assert.assertTrue("'Nice to Have' box should not contain " + item + ", but it does.",!getNiceToHaveBox().findElement(By.xpath("./div/button[contains(text(),'"+ item +"')]")).isDisplayed());//.getText().contains(item.toUpperCase()));
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (org.openqa.selenium.NoSuchElementException nsee) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            logger.info("Could not find the 'Nice to Have' box, so the item we don't want to see there clearly isn't there.");
        }
    }

    /**
     * Moves the passed item from the "Must Have" box to the "Nice to Have" box.
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void moveToNiceToHave(String item) {
        getParent(button(item)).findElement(By.xpath(".//button[3]/i[@class='arrow right icon']")).click();
    }

    /**
     * Moves the passed item from the "Nice to Have" box to the "Must Have" box.
     * @param item String containing the value to look for in the "Nice to Have" box.
     */
    public void moveToMustHave(String item) {
        getParent(button(item)).findElement(By.xpath(".//button[3]/i[@class='arrow left icon']")).click();
    }

    /**
     * Removes the passed item from the "Must Have" or "Nice to Have" box.
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
        waitForUITransition();
        verifyMustHaveBoxContains("Student Body Size [1]");
        veryLargeStudentBodyLabel().click();
        Assert.assertFalse("'Very large (Over 20,000 students)' checkbox is selected", veryLargeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        largeStudentBodyLabel().click();
        Assert.assertTrue("'Large (13,001 to 20,000 students)' checkbox is not selected", largeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxContains("Student Body Size [1]");
        largeStudentBodyLabel().click();
        Assert.assertFalse("'Large (13,001 to 20,000 students)' checkbox is selected", largeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        midSizeStudentBodyLabel().click();
        Assert.assertTrue("'Mid-Size (7,001 to 13,000 students)' checkbox is not selected", midSizeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxContains("Student Body Size [1]");
        midSizeStudentBodyLabel().click();
        Assert.assertFalse("'Mid-Size (7,001 to 13,000 students)' checkbox is selected", midSizeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        mediumStudentBodyLabel().click();
        Assert.assertTrue("'Medium (4,001 to 7,000 students)' checkbox is not selected", mediumStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxContains("Student Body Size [1]");
        mediumStudentBodyLabel().click();
        Assert.assertFalse("'Medium (4,001 to 7,000 students)' checkbox is selected", mediumStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        smallStudentBodyLabel().click();
        Assert.assertTrue("'Small (2,001 to 4,000 students)' checkbox is not selected", smallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxContains("Student Body Size [1]");
        smallStudentBodyLabel().click();
        Assert.assertFalse("'Small (2,001 to 4,000 students)' checkbox is selected", smallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");

        verySmallStudentBodyLabel().click();
        Assert.assertTrue("'Very Small (2,000 or fewer students)' checkbox is not selected", verySmallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxContains("Student Body Size [1]");
        verySmallStudentBodyLabel().click();
        Assert.assertFalse("'Very Small (2,000 or fewer students)' checkbox is selected", verySmallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        verifyMustHaveBoxDoesNotContain("Student Body Size [1]");
    }

    public void verifySystemResponseWhenGPAInputIsValid() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        gpaTextBox().clear();
        gpaTextBox().sendKeys("0.1");
        waitForUITransition();
        Assert.assertFalse(gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("GPA value must be a number between 0.1 and 4"));

        gpaTextBox().clear();
        gpaTextBox().sendKeys("2");
        waitForUITransition();
        Assert.assertFalse(gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("GPA value must be a number between 0.1 and 4"));

        gpaTextBox().clear();
        gpaTextBox().sendKeys("4");
        waitForUITransition();
        Assert.assertFalse(gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("GPA value must be a number between 0.1 and 4"));

    }

    public void verifySystemResponseWhenGPAInputIsInvalid() {


        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        gpaTextBox().clear();
        gpaTextBox().sendKeys("0");
        waitForUITransition();
        Assert.assertTrue(gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("GPA value must be a number between 0.1 and 4"));

        gpaTextBox().clear();
        gpaTextBox().sendKeys("4.1");
        waitForUITransition();
        Assert.assertTrue(gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("GPA value must be a number between 0.1 and 4"));

        gpaTextBox().clear();
        gpaTextBox().sendKeys("5");
        waitForUITransition();
        Assert.assertTrue(gpaTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("GPA value must be a number between 0.1 and 4"));

    }

    public void verifySystemResponseWhenSATScoreInputIsValid() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("400");
        waitForUITransition();
        Assert.assertFalse(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1000");
        waitForUITransition();
        Assert.assertFalse(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1600");
        waitForUITransition();
        Assert.assertFalse(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

    }

    public void verifySystemResponseWhenSATScoreInputIsInvalid() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("100");
        waitForUITransition();
        Assert.assertTrue(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("399");
        waitForUITransition();
        Assert.assertTrue(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1601");
        waitForUITransition();
        Assert.assertTrue(satScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("SAT value must be a number between 400 and 1600"));

    }

    public void verifyIfSATScoreDataIsStoredOnOurSide() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("827");
        resourcesMenuItem().click();

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }
        Assert.assertTrue("SAT score data is not persisting", satScoreTextBox().getAttribute("value").equals("827"));
        satScoreTextBox().clear();
        satScoreTextBox().sendKeys("1300");
        resourcesMenuItem().click();

    }

    public void verifyGPADataPersists() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        gpaTextBox().clear();
        gpaTextBox().sendKeys("3");

        getFitCriteriaCloseButton().click();

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        Assert.assertTrue("GPA data is not stored on our side", gpaTextBox().getAttribute("value").equals("3"));

    }

    public void verifySystemResponseWhenACTScoreIsValid() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("1");
        waitForUITransition();
        Assert.assertFalse(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]"))
                .getText().contains("ACT value must be a number between 1 and 36"));

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("18");
        waitForUITransition();
        Assert.assertFalse(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText()
                .contains("ACT value must be a number between 1 and 36"));

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("36");
        waitForUITransition();
        Assert.assertFalse(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText()
                .contains("ACT value must be a number between 1 and 36"));

    }

    public void verifySystemResponseWhenACTScoreIsInvalid() {


        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        // You can no longer enter a leading zero in ACT scores, or enter decimals at all.
        /*actScoreTextBox().clear();
        actScoreTextBox().sendKeys("0");
        waitForUITransition();
        Assert.assertTrue(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("ACT value must be a number between 1 and 36"));

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("18.1");
        waitForUITransition();
        Assert.assertTrue(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("ACT value must be a number between 1 and 36"));
*/
        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("37");
        waitForUITransition();
        Assert.assertTrue(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("ACT value must be a number between 1 and 36"));

    }

    public void verifyACTScoreDataPersists() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("6");

        getFitCriteriaCloseButton().click();

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        Assert.assertTrue("ACT score data is not stored on our side", actScoreTextBox().getAttribute("value").equals("6"));

    }

    public void verifySurvey(String buttonLabel) {
        waitForUITransition();
        button(buttonLabel).click();
        String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        waitForUITransition();
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
        checkbox(By.xpath("(//li[contains(.,'" + filterCriteria + "')])")).click();

    }

    /**
     *
     * @param validationMessage
     */
    public void checkValidationMessageIsVisible(String validationMessage) {
        Assert.assertTrue("Validation message '" + validationMessage + "' did not appear",
                driver.findElement(By.className("supermatch-error-text")).getText().equals(validationMessage));
    }

    public void verifyMeets100ofNeedCheckbox(String checkBox){
        String path = "//label[contains(text(), '"+checkBox+"')]";
        Assert.assertTrue("Meets 100% of Need fit criteria is not displaying.", driver.findElement(By.xpath(path)).getText().equals("Meets 100% of Need"));
        Assert.assertTrue("Tooltip for Meets 100% of Need fit criteria is not displaying.", driver.findElement(By.xpath(path+"/../../i[@aria-hidden='true']")).isDisplayed());
    }

    /**
     * select any selected checkbox only when fit criteria menu is open.
     */
    public void selectCheckBox(String checkBox, String fitCriteriaName){
        if (!(driver.findElements(By.xpath("//h1[text()='"+fitCriteriaName+"']")).size()>0))
            openFitCriteria(fitCriteriaName);
        WebElement checkboxLocator = driver.findElement(By.xpath("//label[contains(text(), '"+checkBox+"')]"));
        WebElement onlyCheckbox = driver.findElement(By.xpath("//label[contains(text(), '"+checkBox+"')]/../input"));
        Assert.assertTrue(checkBox+" checkbox by default is not selected.", !checkboxLocator.isSelected());
        if (!checkboxLocator.isSelected()) {
            checkboxLocator.click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue(checkBox+" checkbox is not selected.", onlyCheckbox.isSelected());
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }
    /**
     * unselect any selected checkbox only when fit criteria menu is open.
     */
    public void unselectCheckbox(String checkBox, String fitCriteriaName) {
        if (!(driver.findElements(By.xpath("//h1[text()='"+fitCriteriaName+"']")).size()>0))
            openFitCriteria(fitCriteriaName);
        WebElement checkboxLocator = driver.findElement(By.xpath("//label[contains(text(), '"+checkBox+"')]"));
        WebElement onlyCheckbox = driver.findElement(By.xpath("//label[contains(text(), '"+checkBox+"')]/../input"));
        Assert.assertTrue(checkBox+" checkbox is not selected.", onlyCheckbox.isSelected());
        if (onlyCheckbox.isSelected()) {
            checkboxLocator.click();
            waitUntilPageFinishLoading();
        }
        Assert.assertTrue(checkBox+" checkbox is selected.", !onlyCheckbox.isSelected());
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    private void openFitCriteria(String fitCriteria){
        driver.findElement(By.xpath("//li[contains(text(), '"+fitCriteria+"')]")).click();
    }

    public void selectMeest100ofNeedCheckbox(String checkboxName){
        selectCheckBox(checkboxName, "Cost");
    }

    public void selectStudentSuccessFitCriteriaCheckbox(String checkboxName){
        selectCheckBox(checkboxName, "Institution Characteristics");
    }

    public void unselectStudentSuccessFitCriteriaCheckbox(String checkboxName){
        unselectCheckbox(checkboxName, "Institution Characteristics");
    }

    public void verifyStudentSuccessFitCriteriaCheckbox(String checkboxName) {
        openFitCriteria("Institution Characteristics");
        String path = "//label[contains(text(),'" + checkboxName + "')]";
        Assert.assertTrue("Student Success text is not displaying.", driver.findElement(By.xpath("//span[@class='supermatch-menu-institution-characteristics-heading'][contains(text(), 'Student Success')]")).isDisplayed());
        Assert.assertTrue(checkboxName + " label is not displaying.", driver.findElement(By.xpath(path)).isDisplayed());
        Assert.assertTrue(checkboxName + " checkbox tooltip is not showing.", driver.findElement(By.xpath(path + "/../../i")).isDisplayed());
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    // Locators Below

    private WebElement getFitCriteriaCloseButton() { return driver.findElement(By.xpath("//button[contains(text(), 'Close')]")); }
    private WebElement getMustHaveBox() { return driver.findElement(By.xpath("(//div[@class='column box box-selection'])[1]")); }
    private WebElement getNiceToHaveBox() { return driver.findElement(By.xpath("(//div[@class='column box box-selection'])[2]")); }
    private WebElement admissionMenuItem() {
        return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']//li[contains(text(), 'Admission')]"));
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
    private WebElement selectCriteriaToStart2() {
        return driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//button[text()='Select Criteria To Start']"));
    }
    private WebElement superMatchFooter() {
        return driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]"));
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

}
