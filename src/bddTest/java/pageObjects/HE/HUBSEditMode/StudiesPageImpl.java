package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StudiesPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;

    public StudiesPageImpl() {
        logger = Logger.getLogger(StudiesPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        boolean result = false;
        if (studentFacultyRatioText().isDisplayed()
            && studentRetentionText().isDisplayed()
            && graduationRateText().isDisplayed()
            && degreesOfferedSection().isDisplayed()
            && topAreasOfStudySection().isDisplayed()
            && majorsOfferedSection().isDisplayed()
            && studyOptionsSection().isDisplayed()) {
            result = true;
        }
        assertTrue("One or more elements are not displayed in Studies tab", result);
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
        return fieldValues;
    }

    public void verifyGeneratedValues(String studentOptionLabel, HashMap<String, String> generatedValues) {
        while (!generatedValues.get("Student/Faculty Ratio").equals(studentFacultyRatioText().getText())) {
            getDriver().get(getDriver().getCurrentUrl());
        }
        boolean stuFacRatio = false;
        boolean stuRetention = false;
        boolean gradRate = false;
        boolean topAreasStudy = false;
        boolean studyOptions = false;

        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Student/Faculty Ratio" :
                    if (generatedValues.get(key).equals(studentFacultyRatioText().getText())) stuFacRatio = true;
                    break;
                case "Student Retention (%)" :
                    if (generatedValues.get(key).equals(studentRetentionText().getText())) stuRetention = true;
                    break;
                case "Graduation Rate (%)" :
                    if (generatedValues.get(key).equals(graduationRateText().getText())) gradRate = true;
                    break;
                case "Top Areas of Study" :
                    if (generatedValues.get(key).equals(topAreasOfStudyList().get(0).getText())) topAreasStudy = true;
                    break;
                case "Study Options" :
                    if (studyOption(studentOptionLabel).getAttribute("class").contains("--yes")) {
                        if (generatedValues.get(key).equals("enabled")) studyOptions =  true;
                    } else {
                        if (generatedValues.get(key).equals("disabled")) studyOptions = true;
                    }
            }
        }
        System.out.println("Student/Faculty Ratio: "  + stuFacRatio);
        System.out.println("Student Retention (%): " + stuRetention);
        System.out.println("Graduation Rate (%): " + gradRate);
        System.out.println("Top Areas of Study: " + topAreasStudy);
        System.out.println("Study Options: " + studyOptions);
        assertTrue(stuFacRatio && stuRetention && gradRate && topAreasStudy && studyOptions);
    }

    //Locators

    public WebElement studentFacultyRatioText() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.studentFacultyRatio']/div[contains(@class, 'ng-binding')]"));
    }
    public WebElement studentRetentionText() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.studentRetention']/div[contains(@class, 'ng-binding')]"));
    }
    public WebElement graduationRateText() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.gradRate']/div[contains(@class, 'ng-binding')]"));
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
