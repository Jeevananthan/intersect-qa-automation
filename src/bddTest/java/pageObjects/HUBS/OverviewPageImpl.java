package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class OverviewPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;
    private HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
    private FCMainPageImpl fcMain = new FCMainPageImpl();
    private FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
    private HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
    private HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

    public OverviewPageImpl() {
        logger = Logger.getLogger(OverviewPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("div.summary__statement.ng-binding"), 1));
        assertTrue("Opening Statement is not displayed", openingStatement().isDisplayed());
        assertTrue("Website is not displayed", websiteText().isDisplayed());
        assertTrue("School Type is not displayed", getQuickFact("School Type").isDisplayed());
        assertTrue("Undergraduate Enrollment is not displayed", getQuickFact("Undergraduate Enrollment").isDisplayed());
        assertTrue("Student / Faculty Ratio is not displayed", getQuickFact("Student-to-Faculty Ratio").isDisplayed());
        assertTrue("Campus Surroundings is not displayed", getQuickFact("Campus Surroundings").isDisplayed());
        assertTrue("Test Scores/GPA section is not displayed", testScoresSection().isDisplayed());
        assertTrue("Contact Information section is not displayed", contactInfoSection().isDisplayed());
        assertTrue("Counselor comments is displayed for HE users, which should not happen", counselorComments().size() == 0);
        assertTrue("The products section is displayed for HE users, which should not happen", productsSection().size() == 0);
        assertTrue("The visits section is displayed for HE users, which should not happen", visitsSection().size() == 0);
    }

    public HashMap<String, String> getValuesFromFields(List<List<String>> fieldList) {
        waitUntilPageFinishLoading();
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        for (List<String> field : fieldList) {
            switch (field.get(0)) {
                case "Opening Statement" :
                    fieldValues.put(field.get(0), openingStatement().getText());
                    break;
                case "Website" :
                    fieldValues.put(field.get(0), websiteText().getText());
                    break;
                case "School Type" :
                    fieldValues.put(field.get(0), getSchoolTypeForEdit(getQuickFact(field.get(0)).getText()));
                    break;
                case "Undergraduate Enrollment" :
                    fieldValues.put(field.get(0), getQuickFact(field.get(0)).getText().replace(",", ""));
                    break;
                case "Student / Faculty Ratio" :
                    fieldValues.put(field.get(0), getQuickFact(field.get(0)).getText());
                    break;
                case "Campus Surroundings" :
                    fieldValues.put(field.get(0), getQuickFact(field.get(0)).getText());
                    break;
                case "Test Scores" :
                    getTestScoresTableButton("SAT").click();
                    fieldValues.put(field.get(0), getTestScoresTableValue("SAT 2400 Reading", "Low").getText());
                    break;
                case "Average GPA" :
                    fieldValues.put(field.get(0), avgGPAText().getText());
                    break;
                case "Contact Information" :
                    fieldValues.put(field.get(0), contactInfoSection().getText());
                    break;
            }
        }
        logger.info("Generated values: \n");
        for (String key : fieldValues.keySet()) {
            logger.info(key + " : " + fieldValues.get(key));
        }
        return fieldValues;
    }

    private void verifyGeneratedValues(List<List<String>> sections, HashMap<String, String> generatedValues) {
        waitUntil(ExpectedConditions.textToBe(By.cssSelector("span[ng-if=\"vm.profile.displayUrl\"] + a"), getQuickFact("Website").getText()));
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Opening Statement" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(openingStatement().getText()));
                    break;
                case "Website" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(getQuickFact("Website").getText()));
                    break;
                case "School Type" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(getSchoolTypeForEdit(getQuickFact(key).getText())));
                    break;
                case "Undergraduate Enrollment" :
                    //This is not being updated. A ticket was created for this: MATCH-2832
//                    assertTrue("The value for " + key + " was not successfully generated",
//                            generatedValues.get(key).equals(getQuickFact(key).getText()));
                    break;
                case "Student / Faculty Ratio" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(getQuickFact(key).getText().split(" ")[0]));
                    break;
                case "Campus Surroundings" :
                    System.out.println("Campus Surroundings: " + getQuickFact(key).getText());
                    System.out.println("Campus Surroundings corrected: " + getEditValue(getQuickFact(key).getText()));
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(getEditValue(getQuickFact(key).getText())));
                    break;
                case "Test Scores" :
                    nationalRangesTab().sendKeys(Keys.RETURN);
                    for (int i = 0; i < 10; i++) {
                        try {
                            getTestScoresTableButton("SAT").click();
                            break;
                        } catch (WebDriverException e) {
                            getDriver().findElement(By.cssSelector("div.hub-links-bar__links a:nth-of-type(1)")).sendKeys(Keys.ARROW_DOWN);
                        }
                    }
                    assertTrue("The value for " + key + " was not successfully generated. UI value: " + getTestScoresTableValue("SAT 2400 Reading", "Low").getText(),
                            generatedValues.get(key).equals(getTestScoresTableValue("SAT 2400 Reading", "Low").getText()));
                    break;
                case "Average GPA" :
                    nationalRangesTab().sendKeys(Keys.RETURN);
                    for (int i = 0; i < 10; i++) {
                        try {
                            getTestScoresTableButton("GPA").click();
                            break;
                        } catch (WebDriverException e) {
                            getDriver().findElement(By.cssSelector("div.hub-links-bar__links a:nth-of-type(1)")).sendKeys(Keys.ARROW_DOWN);
                        }
                    }
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(gpaCumulativeConverted().getText()));
                    break;
                case "Contact Information" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            contactInfoSection().getText().contains(generatedValues.get(key)));
                    break;
            }
        }
    }

    public void verifyChangesPublishedInHUBS(String username, String password, String college, DataTable stringsDataTable) {
        List<List<String>> sections = stringsDataTable.asLists(String.class);
        List<String> creds = new ArrayList<String>() {{
            add(username);
            add(password);
        }};
        hubsLogin.defaultLogIn(creds);
        fcMain.clickCollegesTab();
        collegesPage.searchAndOpenCollege(college);
        hubsMainMenu.clickOverviewTab();
        waitUntilPageFinishLoading();
        for (int i = 0; i < 10; i++) {
            if (!generatedValues.get("Website").equals(getQuickFact("Website").getText())) {
                header.clickLogOut();
                hubsLogin.defaultLogIn(creds);
                fcMain.clickCollegesTab();
                collegesPage.searchAndOpenCollege(college);
                hubsMainMenu.clickOverviewTab();
            }
        }
        verifyGeneratedValues(sections, generatedValues);
    }

    //Locators

    public WebElement openingStatement() {
        return getDriver().findElement(By.cssSelector("div.summary__statement.ng-binding"));
    }
    public WebElement websiteText() {
        return getDriver().findElement(By.cssSelector("span[ng-if=\"vm.profile.displayUrl\"] + a"));
    }
    public WebElement getQuickFact(String sectionName) {
        if (sectionName.equals("Student / Faculty Ratio")) {
            return getDriver().findElement(By.xpath("//strong[text()='Student-to-Faculty Ratio']/../following-sibling::div"));
        } else {
            return getDriver().findElement(By.xpath("//strong[text()='" + sectionName + "']/../following-sibling::div"));
        }
    }
    public WebElement testScoresSection() {
        return getDriver().findElement(By.cssSelector("div.scores-table"));
    }
    public WebElement contactInfoSection() {
        return getDriver().findElement(By.cssSelector("div.contacts__mail.ng-binding"));
    }
    public List<WebElement> counselorComments() { return getDriver().findElements(By.cssSelector("h2.college-comments__header")); }
    public List<WebElement> productsSection() { return getDriver().findElements(By.xpath("//div[contains(@class, 'product-links')]/descendant::*")); }
    public List<WebElement> visitsSection() { return getDriver().findElements(By.cssSelector("h2.college-visits__header")); }
    public WebElement getTestScoresTableButton(String identifier) {
        String label = "";
        switch (identifier) {
            case "ACT" : label = "ACT Combined";
            break;
            case "SAT" : label = "SAT 2400 Combined";
            break;
            case "GPA" : label = "GPA Cumulative";
            break;
        }
        return getDriver().findElement(By.xpath("//span[text()='" + label + "']"));
    }
    public WebElement avgGPAText() {
        return getDriver().findElement(By.cssSelector("div.scores-table__cell.scores-table__name.ng-scope.scores-table--first.scores-table--gpa.scores-table__name--text--collapsed +div +div"));
    }
    public WebElement gpaCumulativeConverted() {
        return getDriver().findElement(By.xpath("//span[text()='GPA Cumulative Converted']/../../div[3]"));
    }
    public WebElement applicationMailingAddressText() {
        return getDriver().findElement(By.cssSelector("div.contacts__mail.ng-binding"));
    }
    public WebElement getTestScoresTableValue(String section, String level) {
        String position = "";
        switch (level) {
            case "Low" : position = "2";
            break;
            case "Average" : position = "3";
            break;
            case "High" : position = "4";
            break;
        }
        return getDriver().findElement(By.xpath("//span[text()='" + section + "']/../../div[" + position + "]"));
    }
    public String getSchoolTypeForEdit(String previewLabel) {
        String result = "";
        switch (previewLabel) {
            case "Private / 4 Year" : result = "Four Year School";
            break;
            case "Private / 2 Year" : result = "Two Year School";
            break;
            case "Private / <2 Year" : result = "Less than 2 Year School";
            break;
            case "Public / Corporate" : result = "Corporation";
            break;
        }
        return result;
    }
    public WebElement nationalRangesTab() {
        return getDriver().findElement(By.cssSelector("span.fc-tabs__label.fc-tabs__label--active"));
    }
    public String getEditValue(String hubsValue) {
        String result = "";
        switch (hubsValue) {
            case "Large City" : result = "Large City, greater than 250,000 pop";
            break;
            case "Midsize City" : result = "Midsize City, greater than 100,000 pop";
            break;
            case "Small City" : result = "Small City, less than 100,000 pop";
            break;
            case "Suburb near large city" : result = "Suburb, near city w/ greater than 250,000 pop";
            break;
            case "Suburb near midsize city" : result = "Suburb, near city w/ greater than 100,000 pop";
            break;
            case "Suburb near small city" : result = "Suburb, near city w/ less than 100,000 pop";
            break;
            case "Town" : result = "Town, within 10 miles of urban area";
            break;
            case "Rural" : result = "Rural, within 5 miles of urban area";
            break;
        }
        return result;
    }
}
