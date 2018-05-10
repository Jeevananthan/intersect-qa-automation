package pageObjects.SM.searchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class SearchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SearchPageImpl() {
        logger = Logger.getLogger(SearchPageImpl.class);
    }

    /**
     * Accepts a DataTable that describes the location fit criteria to be selected, and selects them from the dialog
     * @param dataTable - Valid sections:  Search Type, State or Province, Quick Selection: US Regions & Others, Campus Surroundings
     *                    Values for each section should be comma separated and in the format the page displays.  e.x.:  Mid-Atlantic, Northeast
     */
    public void setLocationCriteria(DataTable dataTable) {
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        getDriver().findElement(By.xpath("//li[contains(text(),'Location')]")).click();
        for (Map<String,String> criteria : entities) {
            for (String key : criteria.keySet()) {
                switch (key) {
                    // TODO - Some of this is not working yet
                    case "Search Type":
                        if (criteria.get(key).contains("distance"))
                            radioButton("searchByDistance").select();
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
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]/../input"));
        if (!checkbox.isSelected()) {
            label.click();
            waitUntilPageFinishLoading();
        }
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    /**
     * Accepts a String with the name of the option in the Resources fit criteria list to deactivate.
     * @param option String with the name of the option to disable.  e.x.: Counseling Services, Day Care Services, etc.
     */
    public void unsetResourcesCriteria(String option) {
        getDriver().findElement(By.xpath("//li[contains(text(),'Resources')]")).click();
        WebElement label = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]"));
        WebElement checkbox = driver.findElement(By.xpath("//label[contains(text(), '"+option+"')]/../input"));
        if (checkbox.isSelected()) {
            label.click();
            waitUntilPageFinishLoading();
        }
        getDriver().findElement(By.xpath("//button[contains(text(),' Close')]")).click();
    }

    /**
     * Verifies that the "Must Have" box contains the passed item.
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void verifyMustHaveBoxContains(String item) {
        Assert.assertTrue("'Must Have' box should contain " + item + ", but it does not.",getMustHaveBox().getText().contains(item.toUpperCase()));
    }

    /**
     * Verifies that the "Must Have" box does not contain the passed item.
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void verifyMustHaveBoxDoesNotContain(String item) {
        Assert.assertTrue("'Must Have' box should not contain " + item + ", but it does.",!getMustHaveBox().getText().contains(item.toUpperCase()));
    }

    /**
     * Moves the passed item from the "Must Have" box to the "Nice to Have" box.
     * @param item String containing the value to look for in the "Must Have" box.
     */
    public void moveToNiceToHave(String item) {
        getParent(button(item)).findElement(By.xpath(".//button[3]")).click();
    }

    public void verifyStudentBodyUI() {
        institutionCharacteristicsMenuItem().click();
        Assert.assertTrue("'All students' radio button is not selected by default", allStudentsRadioButton().isSelected());
        undergraduateStudentsOnlyLabel().click();
        Assert.assertTrue("'Undergraduate students only' radio button is not selected", undergraduateStudentsOnlyRadioButton().isSelected());

        veryLargeStudentBodyLabel().click();
        Assert.assertTrue("'Very large (Over 20,000 students)' checkbox is not selected", veryLargeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertTrue("'Very large (Over 20,000 students)' fit criteria is not displayed in 'Must Have' box", mustHaveBox().getText().contains("VERY LARGE (OVER 20,000 STUDENTS)"));
        veryLargeStudentBodyLabel().click();
        Assert.assertFalse("'Very large (Over 20,000 students)' checkbox is selected", veryLargeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertFalse("'Very large (Over 20,000 students)' fit criteria is displayed in 'Must Have' box", mustHaveBox().getText().contains("VERY LARGE (OVER 20,000 STUDENTS)"));


        largeStudentBodyLabel().click();
        Assert.assertTrue("'Large (13,001 to 20,000 students)' checkbox is not selected", largeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertTrue("'Large (13,001 to 20,000 students)' fit criteria is not displayed in 'Must Have' box", mustHaveBox().getText().contains("LARGE (13,001 TO 20,000 STUDENTS)"));
        largeStudentBodyLabel().click();
        Assert.assertFalse("'Large (13,001 to 20,000 students)' checkbox is selected", largeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertFalse("'Large (13,001 to 20,000 students)' fit criteria is displayed in 'Must Have' box", mustHaveBox().getText().contains("LARGE (13,001 TO 20,000 STUDENTS)"));


        midSizeStudentBodyLabel().click();
        Assert.assertTrue("'Mid-Size (7,001 to 13,000 students)' checkbox is not selected", midSizeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertTrue("'Mid-Size (7,001 to 13,000 students)' fit criteria is not displayed in 'Must Have' box", mustHaveBox().getText().contains("MID-SIZE (7,001 TO 13,000 STUDENTS)"));
        midSizeStudentBodyLabel().click();
        Assert.assertFalse("'Mid-Size (7,001 to 13,000 students)' checkbox is selected", midSizeStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertFalse("'Mid-Size (7,001 to 13,000 students)' fit criteria is displayed in 'Must Have' box", mustHaveBox().getText().contains("MID-SIZE (7,001 TO 13,000 STUDENTS)"));


        mediumStudentBodyLabel().click();
        Assert.assertTrue("'Medium (4,001 to 7,000 students)' checkbox is not selected", mediumStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertTrue("'Medium (4,001 to 7,000 students)' fit criteria is not displayed in 'Must Have' box", mustHaveBox().getText().contains("MEDIUM (4,001 TO 7,000 STUDENTS)"));
        mediumStudentBodyLabel().click();
        Assert.assertFalse("'Medium (4,001 to 7,000 students)' checkbox is selected", mediumStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertFalse("'Medium (4,001 to 7,000 students)' fit criteria is displayed in 'Must Have' box", mustHaveBox().getText().contains("MEDIUM (4,001 TO 7,000 STUDENTS)"));


        smallStudentBodyLabel().click();
        Assert.assertTrue("'Small (2,001 to 4,000 students)' checkbox is not selected", smallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertTrue("'Small (2,001 to 4,000 students)' fit criteria is not displayed in 'Must Have' box", mustHaveBox().getText().contains("SMALL (2,001 TO 4,000 STUDENTS)"));
        smallStudentBodyLabel().click();
        Assert.assertFalse("'Small (2,001 to 4,000 students)' checkbox is selected", smallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertFalse("'Small (2,001 to 4,000 students)' fit criteria is displayed in 'Must Have' box", mustHaveBox().getText().contains("SMALL (2,001 TO 4,000 STUDENTS)"));


        verySmallStudentBodyLabel().click();
        Assert.assertTrue("'Very Small (2,000 or fewer students)' checkbox is not selected", verySmallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertTrue("'Very Small (2,000 or fewer students)' fit criteria is not displayed in 'Must Have' box", mustHaveBox().getText().contains("VERY SMALL (2,000 OR FEWER STUDENTS)"));
        verySmallStudentBodyLabel().click();
        Assert.assertFalse("'Very Small (2,000 or fewer students)' checkbox is selected", verySmallStudentBodyCheckbox().isSelected());
        waitForUITransition();
        Assert.assertFalse("'Very Small (2,000 or fewer students)' fit criteria is displayed in 'Must Have' box", mustHaveBox().getText().contains("VERY SMALL (2,000 OR FEWER STUDENTS)"));
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

    public void verifyGPACriteriaNotInMustHaveBox() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        gpaTextBox().clear();
        gpaTextBox().sendKeys("3");

        Assert.assertTrue("Must have box doesn't contain GPA score fit criteria", mustHaveBox().findElement(By.xpath("./p[@class='helper-text']")).isDisplayed()
                && !mustHaveBox().getText().contains("3") && !mustHaveBox().getText().toLowerCase().contains("gpa"));
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

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("0");
        waitForUITransition();
        Assert.assertTrue(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("ACT value must be a number between 1 and 36"));

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("18.1");
        waitForUITransition();
        Assert.assertTrue(actScoreTextBox().findElement(By.xpath(".//ancestor::div[contains(@class, 'sixteen column grid')]")).getText().contains("ACT value must be a number between 1 and 36"));

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

    public void verifyACTScoreCriteriaNotInMustHaveBox() {

        if(!admissionMenuItem().getAttribute("class").contains("active"))
        {
            admissionMenuItem().click();
            waitForUITransition();
        }

        actScoreTextBox().clear();
        actScoreTextBox().sendKeys("8");

        Assert.assertTrue("Must have box doesn't contain ACT score fit criteria", mustHaveBox().findElement(By.xpath("./p[@class='helper-text']")).isDisplayed()
                && !mustHaveBox().getText().contains("8") && !mustHaveBox().getText().toLowerCase().contains("act"));
    }

    public void verifyOnlineLearningOpportunitiesTooltipIcon() {

        chooseFitCriteriaTab("Academics");

        selectRadioButton("Certificate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Certificate' degree type",
                driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                 "//ancestor::div[@class='column']//i[@class='teal info circle icon']")).isDisplayed());

        selectRadioButton("Associate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Associate' degree type",
                driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                        "//ancestor::div[@class='column']//i[@class='teal info circle icon']")).isDisplayed());

        selectRadioButton("Bachelor");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Bachelor' degree type",
                driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                        "//ancestor::div[@class='column']//i[@class='teal info circle icon']")).isDisplayed());

        selectRadioButton("Master");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Master' degree type",
                driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                        "//ancestor::div[@class='column']//i[@class='teal info circle icon']")).isDisplayed());

        selectRadioButton("Doctorate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Doctorate' degree type",
                driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                        "//ancestor::div[@class='column']//i[@class='teal info circle icon']")).isDisplayed());

        selectRadioButton("Graduate Certificate");
        Assert.assertTrue("Tooltip icon is not displayed next to 'Include online learning' text for 'Graduate Certificate' degree type",
                driver.findElement(By.xpath("//label[text()='Include online learning opportunities']" +
                        "//ancestor::div[@class='column']//i[@class='teal info circle icon']")).isDisplayed());

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
        onlyRadioButton = driver.findElement(By.xpath("//label[contains(text(), '"+radioButton+"')]/../input"));
        Assert.assertTrue(radioButton+" radio button is not selected.", onlyRadioButton.isSelected());
    }


    // Locators Below

    private WebElement getFitCriteriaCloseButton() { return driver.findElement(By.xpath("//button[contains(text(), 'Close')]")); }
    private WebElement getMustHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[1]")); }
    private WebElement getNiceToHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[2]")); }
    private WebElement admissionMenuItem() {
        return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']//li[contains(text(), 'Admission')]"));
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
    private WebElement mustHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[1]"));
    }

}
