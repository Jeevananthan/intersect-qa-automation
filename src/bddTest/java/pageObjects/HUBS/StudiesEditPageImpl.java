package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StudiesEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    StudiesPageImpl studiesPreview = new StudiesPageImpl();
    StudentLifeEditPageImpl studentLifeEditPage = new StudentLifeEditPageImpl();

    public StudiesEditPageImpl() {
        logger = Logger.getLogger(StudiesEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(DataTable stringsDataTable) {
        List<List<String>> fieldsAndValues = stringsDataTable.cells(0);
        for (List<String> fieldAndValueElement : fieldsAndValues) {
            switch (fieldAndValueElement.get(0)) {
                case "Student/Faculty Ratio" :
                    studentFacultyRatioButton().click();
                    studentFacultyRatioTextBox().clear();
                    studentFacultyRatioTextBox().sendKeys(fieldAndValueElement.get(1));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studiesPreview.studentFacultyRatioText().getText().equals(fieldAndValueElement.get(1)));
                    break;
                case "Student Retention (%)" :
                    studentRetentionButton().click();
                    studentRetentionTextBox().clear();
                    studentRetentionTextBox().sendKeys(fieldAndValueElement.get(1));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studiesPreview.studentRetentionText().getText().equals(fieldAndValueElement.get(1)));
                    break;
                case "Graduation Rate (%)" :
                    gradRateButton().click();
                    gradRateTextBox().clear();
                    gradRateTextBox().sendKeys(fieldAndValueElement.get(1));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studiesPreview.graduationRateText().getText().equals(fieldAndValueElement.get(1)));
                    break;
                case "Top Areas of Study" :
                    topAreasOfStudyButton().click();
                    editTopAreaOfStudy(fieldAndValueElement.get(1).split(";")[0], fieldAndValueElement.get(1).split(";")[1]);
                    WebElement topAreOfStudy = studiesPreview.topAreasOfStudyList().get(Integer.parseInt(fieldAndValueElement.get(1).split(";")[0]) - 1);
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            topAreOfStudy.getText().equals(fieldAndValueElement.get(1).split(";")[1]));
                    break;
                case "Study Options" :
                    String enableDisable;
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    jsClick(studyOptionsButton());
                    //studyOptionsButton().click(); added js click
                    if (fieldAndValueElement.get(1).split(";")[1].equals("enabled")) {
                        enableDisable = "enabled";
                    } else {
                        enableDisable = "disabled";
                    }
                    editStudyOption(fieldAndValueElement.get(1).split(";")[0], enableDisable);
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studiesPreview.studyOption(fieldAndValueElement.get(1).split(";")[0]).getAttribute("class").contains("study-option__icon--no"));
                    break;
            }
        }
    }

    private void editTopAreaOfStudy(String areaOfStudyNumber, String value) {
        textbox("Popular Area of Study #" + areaOfStudyNumber).clear();
        textbox("Popular Area of Study #" + areaOfStudyNumber).sendKeys(value);
    }

    private void editStudyOption(String checkBoxText, String status) {
        if (status.equals("enabled")) {
            if (studiesPreview.studyOption(checkBoxText).getAttribute("class").contains("--no")) {
                studyOptionCheckbox(checkBoxText).click();
            }
        } else if (status.equals("disabled")) {
            if (studiesPreview.studyOption(checkBoxText).getAttribute("class").contains("--yes")) {
                studyOptionCheckbox(checkBoxText).click();
            }
        }
    }

    public void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues, List<List<String>> topAreasOfStudyAndStudyOptionsDetails) {
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Student/Faculty Ratio" :
                    studentFacultyRatioButton().click();
                    studentFacultyRatioTextBox().clear();
                    studentFacultyRatioTextBox().sendKeys(generatedValues.get(key));
                    break;
                case "Student Retention (%)" :
                    studentRetentionButton().click();
                    studentRetentionTextBox().clear();
                    studentRetentionTextBox().sendKeys(generatedValues.get(key));
                    break;
                case "Graduation Rate (%)" :
                    gradRateButton().click();
                    gradRateTextBox().clear();
                    gradRateTextBox().sendKeys(generatedValues.get(key));
                    break;
                case "Top Areas of Study" :
                    topAreasOfStudyButton().click();
                    editTopAreaOfStudy(topAreasOfStudyAndStudyOptionsDetails.get(0).get(1), generatedValues.get(key));
                    break;
                case "Study Options" :
                    studyOptionsButton().click();
                    if (generatedValues.get(key).equals("enabled")) {
                        editStudyOption(topAreasOfStudyAndStudyOptionsDetails.get(1).get(1), "enabled");
                    } else if (generatedValues.get(key).equals("disabled")){
                        editStudyOption(topAreasOfStudyAndStudyOptionsDetails.get(1).get(1), "disabled");
                    }
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues, List<List<String>> details) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");
        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "Student/Faculty Ratio" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Student Retention (%)" :
                    if ((Integer.parseInt(fieldValues.get(key)) + 1) > 100) {
                        generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) - 1));
                    } else {
                        generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    }
                    break;
                case "Graduation Rate (%)" :
                    if ((Integer.parseInt(fieldValues.get(key)) + 1) > 100) {
                        generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) - 1));
                    } else {
                        generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    }

                    break;
                case "Top Areas of Study" :
                    if (fieldValues.get(key).contains("/")) {
                        generatedValues.put(key, fieldValues.get(key).split("/")[0] + "/" + dateFormat.format(date));
                    } else {
                        generatedValues.put(key, fieldValues.get(key) + "/" + dateFormat.format(date));
                    }
                    break;
                case "Study Options" :
                    if (studiesPreview.studyOption(details.get(1).get(1)).getAttribute("class").contains("--yes")) {
                        generatedValues.put(key, "disabled");
                    } else if (studiesPreview.studyOption(details.get(1).get(1)).getAttribute("class").contains("--no")){
                        generatedValues.put(key, "enabled");
                    }
            }
        }
        return generatedValues;
    }

    private void enterPublishReasonsText(String publishReason) {
        publishReasonsTextArea().sendKeys(publishReason);
    }

    private void clickSubmitChangesButton() {
        submitChangesButton().click();
    }

    private void clickContinueEditingLink() {
        continueEditingLink().click();
    }

    private void clickPublishButton() {
        publishButton().click();
    }

    public void editAllFieldsBasedOnGatheredValues(DataTable stringsDataTable, HashMap<String, String> originalValues) {
        List<List<String>> topAreasOfStudyAndStudyOptions = stringsDataTable.cells(0);
        HashMap<String, String> newValues = generateValues(originalValues, topAreasOfStudyAndStudyOptions);
        StudiesPageImpl.generatedValues = newValues;
        editFieldValuesWithGeneratedData(newValues, topAreasOfStudyAndStudyOptions);
        clickPublishButton();
        enterPublishReasonsText(topAreasOfStudyAndStudyOptions.get(2).get(1));
        clickSubmitChangesButton();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(studentLifeEditPage.loadingIconLeftMenuLocator), 1));
        logger.info("All changes were submitted");
    }

    //Locators
    private WebElement studentFacultyRatioButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Student/Faculty Ratio']"));
    }
    private WebElement studentRetentionButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Student Retention (%)']"));
    }
    private WebElement gradRateButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Graduation Rate (%)']"));
    }
    private WebElement topAreasOfStudyButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Top Areas of Study']"));
    }
    private WebElement studyOptionsButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Study Options']"));
    }
    private WebElement studentFacultyRatioTextBox() {
        return getDriver().findElement(By.xpath("//input[contains(@id, 'studentfacultyratio')]"));
    }
    private WebElement studentRetentionTextBox() {
        return getDriver().findElement(By.xpath("//input[contains(@id, 'retentionratefulltime')]"));
    }
    private WebElement gradRateTextBox() {
        return getDriver().findElement(By.xpath("//input[contains(@id, 'gradratebachelors6yrs')]"));
    }
    private WebElement publishReasonsTextArea() {
        return textbox(By.id("submit-overlay__rationale"));
    }
    private WebElement submitChangesButton() {
        return button("Submit Changes");
    }
    private WebElement continueEditingLink() {
        return link("Continue editing");
    }
    public WebElement publishButton() {
        return getDriver().findElement(By.xpath("//span[@class='intersect-btn intersect-btn--fuschia ng-binding']"));
    }
    private WebElement studyOptionCheckbox(String label) {
        return getDriver().findElement(By.xpath("//label[@class='hem-checkbox-label ng-binding'][text()=' " + label + " ']"));
    }
}
