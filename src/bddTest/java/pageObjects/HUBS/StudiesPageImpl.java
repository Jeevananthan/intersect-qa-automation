package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StudiesPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;
    private HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
    private FCMainPageImpl fcMain = new FCMainPageImpl();
    private FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
    private HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
    private HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

    public StudiesPageImpl() {
        logger = Logger.getLogger(StudiesPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        waitUntilElementExists(degreesOfferedSection());
        assertTrue("Student Faculty Ratio is not displayed", studentFacultyRatioText().isDisplayed());
        assertTrue("Student Retention is not displayed", studentRetentionText().isDisplayed());
        assertTrue("Graduation Rate is not displayed", graduationRateText().isDisplayed());
        assertTrue("Degrees Offered section is not dislpayed", degreesOfferedSection().isDisplayed());
        assertTrue("Top Areas of Study section is not displayed", topAreasOfStudySection().isDisplayed());
        assertTrue("Majors Offered section is not displayed", majorsOfferedSection().isDisplayed());
        assertTrue("Study Options section is not displayed", studyOptionsSection().isDisplayed());
    }

    public HashMap<String, String> getValuesFromFields(List<String> fieldList) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        for (String field : fieldList) {
            if (field.contains("Study Options")) {
                if (studyOption(field.split(";")[1]).getAttribute("class").contains("--yes")) {
                    fieldValues.put(field.split(";")[0], "enabled");
                } else {
                    fieldValues.put(field.split(";")[0], "disabled");
                }
            }
            switch (field) {
                case "Student/Faculty Ratio" :
                    fieldValues.put(field, studentFacultyRatioText().getText());
                    break;
                case "Student Retention (%)" :
                    fieldValues.put(field, studentRetentionText().getText());
                    break;
                case "Graduation Rate (%)" :
                    fieldValues.put(field, graduationRateText().getText());
                    break;
                //Get value from the first element of the Top Areas of Study list
                case "Top Areas of Study" :
                    fieldValues.put(field, topAreasOfStudyList().get(0).getText());
                    break;
            }
        }
        logger.info("\nValues in page: \n");
        for (String key : fieldValues.keySet()) {
            logger.info(key + " : " + fieldValues.get(key) + "\n");
        }
        return fieldValues;
    }

    private void verifyGeneratedValues(String studentOptionLabel, HashMap<String, String> generatedValues) {

        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Student/Faculty Ratio" :
                    assertTrue("The value for " + key + " was not successfully edited. Expected: " +
                                    generatedValues.get(key) + ", Actual: " + studentFacultyRatioText().getText(),
                            generatedValues.get(key).equals(studentFacultyRatioText().getText()));
                    break;
                case "Student Retention (%)" :
                    assertTrue("The value for " + key + " was not successfully edited. Expected: " +
                                    generatedValues.get(key) + ", Actual: " + studentRetentionText().getText(),
                            generatedValues.get(key).equals(studentRetentionText().getText()));
                    break;
                case "Graduation Rate (%)" :
                    assertTrue("The value for " + key + " was not successfully edited. Expected:" +
                                    generatedValues.get(key) + ", Actual: " + graduationRateText().getText(),
                            generatedValues.get(key).equals(graduationRateText().getText()));
                    break;
                case "Top Areas of Study" :
                    assertTrue("The value for " + key + " was not successfully edited. Expected: " +
                                    generatedValues.get(key) + ", Actual: " + topAreasOfStudyList().get(0).getText(),
                            generatedValues.get(key).equals(topAreasOfStudyList().get(0).getText()));
                    break;
                case "Study Options" :
                    if (studyOption(studentOptionLabel).getAttribute("class").contains("--yes")) {
                        assertTrue("The value for " + key + " was not successfully edited. Expected: " +
                                        generatedValues.get(key),
                                generatedValues.get(key).equals("enabled"));
                    } else {
                        assertTrue("The value for " + key + " was not successfully edited. Expected: " +
                                        generatedValues.get(key),
                                generatedValues.get(key).equals("disabled"));
                    }
            }
        }
    }

    /**
     * Verifies that the updated made in HEM are displayed in HUBS
     *
     * @param stringsDataTable - Data Table containing data about the fields that should be updated in HUBS.
     */
    public void verifyChangesPublishedInHUBS(DataTable stringsDataTable) {
        driver.close();
        load(GetProperties.get("hubs.app.url"));
        driver.manage().deleteAllCookies();
        List<String> creds = stringsDataTable.asList(String.class);
        hubsLogin.defaultLogIn(creds);
        fcMain.clickCollegesTab();
        collegesPage.searchAndOpenCollege(creds.get(2));
        hubsMainMenu.clickStudiesTab();
        verifyValuesAreUpdated(creds, 10);
        verifyGeneratedValues(creds.get(3), generatedValues);
    }

    /**
     * Verifies if the Student/Faculty Ratio field is updated. It tries a given number of times to log out, log in
     * and verify if the field was updated. If the field was updated, the loop is broken. This field was selected because
     * it usually is the last field to be updated.
     *
     * @param credentials - Credentials for Family Connection/Naviance Student
     * @param numberOfTries - Number of tries to verify the upda
     */
    private void verifyValuesAreUpdated(List<String> credentials, int numberOfTries) {
        for (int i = 0; i < numberOfTries; i++) {
            if (!generatedValues.get("Student/Faculty Ratio").equals(studentFacultyRatioText().getText().replace(",", ""))) {
                header.clickLogOut();
                hubsLogin.defaultLogIn(credentials);
                fcMain.clickCollegesTab();
                collegesPage.searchAndOpenCollege(credentials.get(2));
                hubsMainMenu.clickStudiesTab();
            }
        }
    }

    //Locators

    public WebElement studentFacultyRatioText() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.studentFacultyRatio']/div[contains(@class, 'ng-binding')]"));
    }
    public WebElement studentRetentionText() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.studentRetention']/div[contains(@class, 'ng-binding')]"));
    }
    public WebElement graduationRateText() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.gradRate']/div[contains(@class, 'hub-data-pod--studies ng-binding')]"));
    }
    public List<WebElement> topAreasOfStudyList() {
        return getDriver().findElements(By.xpath("//h5[contains(@class, 'studies-popular__area-of-study')]"));
    }
    public WebElement studyOption(String label) {
        return getDriver().findElement(By.xpath("//div[text()='" + label + "']/preceding-sibling::div"));
    }
    private WebElement degreesOfferedSection() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.profile.friendlyDegrees.length > 0']"));
    }
    private WebElement topAreasOfStudySection() {
        return getDriver().findElement(By.xpath("//div[@class='hubs-section__content fc-grid__row studies-popular']"));
    }
    private WebElement majorsOfferedSection() {
        return getDriver().findElement(By.xpath("//div[@class='studies-programs__content']"));
    }
    private WebElement studyOptionsSection() {
        return getDriver().findElement(By.xpath("//div[@class='study-options fc-grid__row fc-grid__row--xs-center ng-scope']"));
    }
}
