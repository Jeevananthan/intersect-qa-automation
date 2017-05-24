package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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

    public StudiesEditPageImpl() {
        logger = Logger.getLogger(StudiesEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(List<String> fieldsAndValues) {
        boolean verificationStuFacRatio = false;
        boolean verificationStuRetention = false;
        boolean verificationGradRate = false;
        boolean verificationTopAreasOfStudy = false;
        boolean verificationStudyOptions = false;
        for (String fieldAndValueElement : fieldsAndValues) {
            switch (fieldAndValueElement.split(";")[0]) {
                case "Student/Faculty Ratio" :
                    studentFacultyRatioButton().click();
                    studentFacultyRatioTextBox().clear();
                    studentFacultyRatioTextBox().sendKeys(fieldAndValueElement.split(";")[1]);
                    verificationStuFacRatio = studiesPreview.studentFacultyRatioText().getText().equals(fieldAndValueElement.split(";")[1]);
                    if (!verificationStuFacRatio) {
                        System.out.println(fieldAndValueElement.split(";")[0] + ": " + verificationStuFacRatio);
                    }
                    break;
                case "Student Retention (%)" :
                    studentRetentionButton().click();
                    studentRetentionTextBox().clear();
                    studentRetentionTextBox().sendKeys(fieldAndValueElement.split(";")[1]);
                    verificationStuRetention = studiesPreview.studentRetentionText().getText().equals(fieldAndValueElement.split(";")[1]);
                    if (!verificationStuRetention) {
                        System.out.println(fieldAndValueElement.split(";")[0] + ": " + verificationStuRetention);
                    }
                    break;
                case "Graduation Rate (%)" :
                    gradRateButton().click();
                    gradRateTextBox().clear();
                    gradRateTextBox().sendKeys(fieldAndValueElement.split(";")[1]);
                    verificationGradRate = studiesPreview.graduationRateText().getText().equals(fieldAndValueElement.split(";")[1]);
                    if (!verificationGradRate) {
                        System.out.println(fieldAndValueElement.split(";")[0] + ": " + verificationGradRate);
                    }
                    break;
                case "Top Areas of Study" :
                    topAreasOfStudyButton().click();
                    editTopAreaOfStudy(fieldAndValueElement.split(";")[1], fieldAndValueElement.split(";")[2]);
                    WebElement topAreOfStudy = studiesPreview.topAreasOfStudyList().get(Integer.parseInt(fieldAndValueElement.split(";")[1]) - 1);
                    verificationTopAreasOfStudy = topAreOfStudy.getText().equals(fieldAndValueElement.split(";")[2]);
                    if (!verificationTopAreasOfStudy) {
                        System.out.println("Preview: " + topAreOfStudy.getText());
                        System.out.println(fieldAndValueElement.split(";")[0] + ": " + verificationTopAreasOfStudy);
                    }
                    break;
                case "Study Options" :
                    String enableDisable;
                    studyOptionsButton().click();
                    if (fieldAndValueElement.split(";")[2].equals("enable")) {
                        enableDisable = "enabled";
                    } else {
                        enableDisable = "disabled";
                    }
                    editStudyOption("Study Abroad Credit", enableDisable);
                    verificationStudyOptions = studiesPreview.studyOption(fieldAndValueElement.split(";")[1]).getAttribute("class").contains("study-option__icon--no");
                    if (!verificationStudyOptions) {
                        System.out.println(fieldAndValueElement.split(";")[0] + ": " + verificationStudyOptions);
                    }
                    break;
            }
        }

        assertTrue("It is not possible to edit one or more fields in real time", (verificationStuFacRatio && verificationStuRetention &&
                verificationGradRate && verificationTopAreasOfStudy && verificationStudyOptions));
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

    public void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues, List<String> topAreasOfStudyAndStudyOptionsDetails) {
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
                    editTopAreaOfStudy(topAreasOfStudyAndStudyOptionsDetails.get(0).split(";")[1], generatedValues.get(key));
                    break;
                case "Study Options" :
                    studyOptionsButton().click();
                    if (generatedValues.get(key).equals("enabled")) {
                        editStudyOption(topAreasOfStudyAndStudyOptionsDetails.get(1).split(";")[1], "enabled");
                    } else if (generatedValues.get(key).equals("disabled")){
                        editStudyOption(topAreasOfStudyAndStudyOptionsDetails.get(1).split(";")[1], "disabled");
                    }
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues, List<String> details) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");
        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "Student/Faculty Ratio" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Student Retention (%)" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Graduation Rate (%)" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Top Areas of Study" :
                    if (fieldValues.get(key).contains("/")) {
                        generatedValues.put(key, fieldValues.get(key).split("/")[0] + "/" + dateFormat.format(date));
                    } else {
                        generatedValues.put(key, fieldValues.get(key) + "/" + dateFormat.format(date));
                    }
                    break;
                case "Study Options" :
                    if (studiesPreview.studyOption(details.get(1).split(";")[1]).getAttribute("class").contains("--yes")) {
                        generatedValues.put(key, "disabled");
                    } else if (studiesPreview.studyOption(details.get(1).split(";")[1]).getAttribute("class").contains("--no")){
                        generatedValues.put(key, "enabled");
                    }
            }
        }
        return generatedValues;
    }

    public void enterPublishReasonsText(String publishReason) {
        publishReasonsTextArea().sendKeys(publishReason);
    }

    public void clickSubmitChangesButton() {
        submitChangesButton().click();
        new WebDriverWait(getDriver(), 40).until(ExpectedConditions.elementToBeClickable(By.linkText("Continue editing")));
    }

    public void clickContinueEditingLink() {
        continueEditingLink().click();
    }

    public void clickPublishButton() {
        publishButton().click();
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
    private WebElement publishButton() {
        return getDriver().findElement(By.xpath("//span[@class='intersect-btn intersect-btn--fuschia']"));
    }
    private WebElement studyOptionCheckbox(String label) {
        return getDriver().findElement(By.xpath("//label[@class='hem-checkbox-label ng-binding'][text()='" + label + "']"));
    }
}
