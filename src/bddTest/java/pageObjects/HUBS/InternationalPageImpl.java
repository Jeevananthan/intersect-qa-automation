package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class InternationalPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;
    private HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
    private FCMainPageImpl fcMain = new FCMainPageImpl();
    private FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
    private HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
    private HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

    public InternationalPageImpl() {
        logger = Logger.getLogger(InternationalPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        waitForUITransition();
        assertTrue("Text message for international is not displayed", internationalMessage().isDisplayed());
        assertTrue("Application Deadline section is not displayed", internationalApplicationDeadlineSection().isDisplayed());
        assertTrue("Fees section is not displayed", feesSection().isDisplayed());
        assertTrue("Test Requirements section is not displayed", testReqSection().isDisplayed());
        assertTrue("Applications section is not displayed", intApplications().isDisplayed());
        assertTrue("Test Scores section is not displayed", testScoresSection().isDisplayed());
        assertTrue("Qualifications/Accepted English Tests section is not displayed", qualificationsSection().isDisplayed());
    }

    public HashMap<String, String> getValuesFromFields(List<List<String>> fieldList) {
        waitUntilPageFinishLoading();
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        for (List<String> field : fieldList) {
            switch (field.get(0)) {
                case "Application Deadline" :
                    if (field.get(1).equals("Day")) {
                        fieldValues.put(field.get(0), intAppDeadlineText().getText().split("-")[1]);
                    } else if (field.get(1).equals("Month")) {
                        fieldValues.put(field.get(0), intAppDeadlineText().getText().split("-")[0]);
                    }
                    break;
                case "Fees" :
                    fieldValues.put(field.get(0), getInternationalFee(field.get(1)).getText());
                    break;
                case "Test Requirements" :
                    for (WebElement intTestReqElement : getIntTestReqs()) {
                        if (intTestReqElement.findElement(By.cssSelector("span:nth-of-type(1)")).getText().equals(field.get(1))) {
                            fieldValues.put(field.get(0), intTestReqElement.findElement(By.cssSelector("span:nth-of-type(2)")).getText());
                        }
                    }
                    break;
                case "Applications" :
                    fieldValues.put(field.get(0), getIntApplication(field.get(1).toUpperCase()).getText());
                    break;
                case "Test Scores" :
                    fieldValues.put(field.get(0), getIntTestScoresTableValue(field.get(1).split(";")[0], field.get(1).split(";")[1]).getText());
                    break;
                case "Qualifications" :
                    fieldValues.put(field.get(0), getIntQualification(field.get(1).replace("to", "To")).getText());
                    break;
                case "Accepted English Tests" :
                    String result = "";
                    List<String> englishTestsStrings = new ArrayList<>();
                    for (WebElement englishTestElement : getAcceptedLanguagesTests()) {
                        englishTestsStrings.add(englishTestElement.getText());
                    }
                    if (englishTestsStrings.contains(field.get(1))) {
                        result = "yes";
                    } else if (!englishTestsStrings.contains(field.get(1))) {
                        result = "no";
                    }
                    fieldValues.put(field.get(0), result);
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
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Application Deadline" :
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key + " was not successfully generated")
                            .isEqualTo(intAppDeadlineText().getText().split("-")[1]);
                    break;
                case "Fees" :
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key + " was not successfully generated")
                            .isEqualTo(getInternationalFee(sections.get(1).get(1)).getText());
                    break;
                case "Test Requirements" :
                    for (WebElement testReqElement : getIntTestReqs()) {
                        if (testReqElement.findElement(By.cssSelector("span:nth-of-type(1)")).getText().equals(sections.get(2).get(0))) {
                            softly().assertThat(testReqElement.findElement(By.cssSelector("span:nth-of-type(1)")).getText())
                                    .as("The value for " + key + " was not successfully generated")
                                    .isEqualTo(generatedValues.get(key));
                        }
                    }
                    break;
                case "Applications" :
                    softly().assertThat(getIntApplication(sections.get(3).get(1).toUpperCase()).getText())
                            .as("The value for " + key + " was not successfully generated")
                            .isEqualTo(generatedValues.get(key));
                    break;
                case "Test Scores" :
                    softly().assertThat(getIntTestScoresTableValue(sections.get(4).get(1).split(";")[0], sections.get(4).get(1).split(";")[1]).getText())
                            .as("The value for " + key + " was not successfully generated. UI: " + getIntTestScoresTableValue(sections.get(4).get(1).split(";")[0], sections.get(4).get(1).split(";")[1]).getText() + ". Data: " + generatedValues.get(key))
                            .isEqualTo(generatedValues.get(key));
                    break;
                case "Qualifications" :
                    softly().assertThat(getIntQualification(sections.get(5).get(1).replace("to", "To")).getText())
                            .as("The value for " + key + " was not successfully generated")
                            .isEqualTo(generatedValues.get(key).toUpperCase());
                    break;
                case "Accepted English Tests" :
                    List<String> englishTestsStrings = new ArrayList<>();
                    for (WebElement languageTestElement : getAcceptedLanguagesTests()) {
                        englishTestsStrings.add(languageTestElement.getText());
                    }
                    if (generatedValues.get(key).equals("yes")) {
                        softly().assertThat(englishTestsStrings).as("The value for " + key + " was not successfully generated")
                                .contains(sections.get(6).get(1));
                    } else if (generatedValues.get(key).equals("no")) {
                        softly().assertThat(englishTestsStrings).as("The value for " + key + " was not successfully generated")
                                .doesNotContain(sections.get(6).get(1));
                    }
                    break;
            }
        }
    }

    public void verifyChangesPublishedInHUBS(String username, String password, String college, DataTable stringsDataTable) {
        driver.close();
        load(GetProperties.get("hubs.app.url"));
        List<List<String>> sections = stringsDataTable.asLists(String.class);
        List<String> creds = new ArrayList<String>() {{
            add(username);
            add(password);
        }};
        hubsLogin.internationalLogIn(creds);
        fcMain.clickCollegesTab();
        collegesPage.searchAndOpenCollege(college);
        hubsMainMenu.clickInternationalTab();
        waitUntilPageFinishLoading();
        for (int i = 0; i < 10; i++) {
            if (!generatedValues.get("Application Deadline").equals(intAppDeadlineText().getText().split("-")[1])) {
                header.clickLogOut();
                hubsLogin.internationalLogIn(creds);
                fcMain.clickCollegesTab();
                collegesPage.searchAndOpenCollege(college);
                hubsMainMenu.clickInternationalTab();
            }
        }
        verifyGeneratedValues(sections, generatedValues);
    }

    //Locators

    public WebElement internationalApplicationDeadlineSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.deadlines.length > 0\"]"));
    }
    public WebElement feesSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.admissions.fees.internationalApplicationFee || vm.admissions.fees.internationalApplicationDeposit\"]"));
    }
    public WebElement testReqSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.hasTestRequirements\"]"));
    }
    public WebElement intApplications() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.hasApplications\"]"));
    }
    public WebElement testScoresSection() {
        return getDriver().findElement(By.cssSelector("div.international__scores.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-8.fc-grid__col--md-7"));
    }
    public WebElement qualificationsSection() {
        return getDriver().findElement(By.cssSelector("div[ng-show=\"vm.hasQualifications\"]"));
    }
    public WebElement intAppDeadlineText() {
        return getDriver().findElement(By.cssSelector("div.hub-data-pod--international.hub-data-pod--number.hub-data-pod--international--deadline.ng-binding"));
    }
    public WebElement getInternationalFee(String typeOfFee) {
        return getDriver().findElement(By.xpath("//span[text()='" + typeOfFee + "']/../span[1]"));
    }
    public List<WebElement> getIntTestReqs() {
        return getDriver().findElements(By.cssSelector("div.hub-data-pod--international--reqs div.hub-data-pod--international--row.ng-scope"));
    }
    public WebElement getIntTestScoresTableValue(String testScoreType, String level) {
        String scoreType = "";
        String finalLevel = "";
        if (testScoreType.equals("Michigan (MELAB)")) {
            scoreType = "MELAB (Michigan)";
        } else {
            scoreType = testScoreType;
        }
        switch (level) {
            case "Low" : finalLevel = "1";
            break;
            case "Average" : finalLevel = "2";
            break;
            case "High" : finalLevel = "3";
            break;
        }
        return getDriver().findElement(By.xpath("//div[contains(@class, 'international__scores fc-grid__col fc-grid__col--xs-12 " +
                "fc-grid__col--sm-8 fc-grid__col--md-7')]/div/div[text()='" + scoreType + "']/following-sibling::div[" + finalLevel + "]"));
    }
    public WebElement getIntApplication(String type) {
        return getDriver().findElement(By.xpath("//span[text()='" + type + "']/../span[1]"));
    }
    public WebElement getIntQualification(String type) {
        return getDriver().findElement(By.xpath("//div[text()='" + type + "']/../div[2]"));
    }
    public List<WebElement> getAcceptedLanguagesTests() {
        return getDriver().findElements(By.cssSelector("div.international__qualifications--last.fc-grid__row." +
                "fc-grid__row--xs-center.fc-grid__row--md-start div.ng-binding.ng-scope"));
    }
    public WebElement internationalMessage() {
        return getDriver().findElement(By.cssSelector("div.community-message__text.community-message__text--double"));
    }
}
