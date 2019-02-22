package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class InternationalEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    InternationalPageImpl internationalPreview = new InternationalPageImpl();
    PublishPageImpl publish = new PublishPageImpl();

    public InternationalEditPageImpl() {
        logger = Logger.getLogger(InternationalEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(DataTable stringsDataTable) {
        List<List<String>> fieldsAndValues = stringsDataTable.cells(0);
        for (List<String> fieldAndValueElement : fieldsAndValues) {
            switch (fieldAndValueElement.get(0)) {
                case "Application Deadline" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    Select dropDown = new Select(getAppDeadlineDropDown(fieldAndValueElement.get(1)));
                    dropDown.selectByVisibleText(fieldAndValueElement.get(2));
                    if (fieldAndValueElement.get(1).equals("Day")) {
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                internationalPreview.intAppDeadlineText().getText().split("-")[1].equals(fieldAndValueElement.get(2)));
                    } else if (fieldAndValueElement.get(1).equals("Month")) {
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                internationalPreview.intAppDeadlineText().getText().split("-")[0].equals(fieldAndValueElement.get(2)));
                    }


                    break;
                case "Fees" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            internationalPreview.getInternationalFee(fieldAndValueElement.get(1)).getText().equals(fieldAndValueElement.get(2)));
                    break;
                case "Test Requirements" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    getTestRequirementInnerSection(fieldAndValueElement.get(1)).click();
                    Select testReqDropDown = new Select(getTestReqDropDown(fieldAndValueElement.get(1)));
                    testReqDropDown.selectByVisibleText(fieldAndValueElement.get(2));

                    Map<String, String> testReqData = new HashMap<String, String>();
                    for (WebElement testReqElement : internationalPreview.getIntTestReqs()) {
                        testReqData.put(testReqElement.findElement(By.cssSelector("span:nth-of-type(1)")).getText(),
                                testReqElement.findElement(By.cssSelector("span:nth-of-type(2)")).getText());
//                        System.out.println("UI: " + testReqData.get(testReqElement.findElement(By.cssSelector("span:nth-of-type(1)")).getText()));
//                        System.out.println("Data: " + fieldAndValueElement.get(2));
                    }
                    System.out.println("Data: " + testReqData.get(fieldAndValueElement.get(1)));
                    System.out.println("UI: " + fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            testReqData.get(fieldAndValueElement.get(1)).equals(fieldAndValueElement.get(2).toUpperCase()));
                    break;
                case "Applications" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time: ",
                            internationalPreview.getIntApplication(fieldAndValueElement.get(1).toUpperCase()).getText().equals(fieldAndValueElement.get(2)));
                    break;
                case "Test Scores" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    getTestScoresInnerSection(fieldAndValueElement.get(1)).click();
                    innerEditSection(fieldAndValueElement.get(2).split(";")[0]).clear();
                    innerEditSection(fieldAndValueElement.get(2).split(";")[0]).sendKeys(fieldAndValueElement.get(2).split(";")[1]);
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            internationalPreview.getIntTestScoresTableValue(fieldAndValueElement.get(1),
                                    fieldAndValueElement.get(2).split(";")[0]).getText().equals(fieldAndValueElement.get(2).split(";")[1]));
                    break;
                case "Qualifications" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    getQualificationsInnerSection(fieldAndValueElement.get(1)).click();
                    Select qualificationsDropDown = new Select(getQualificationsDropDown(fieldAndValueElement.get(1)));
                    qualificationsDropDown.selectByVisibleText(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            internationalPreview.getIntQualification(fieldAndValueElement.get(1).replace("to", "To")).getText().equals(fieldAndValueElement.get(2).toUpperCase()));
                    break;
                case "Accepted English Tests" :
                    getEditButton(fieldAndValueElement.get(0)).click();
                    List<String> acceptedLanguageStringList = new ArrayList<>();


                    if (innerCheckBox(fieldAndValueElement.get(1)).getAttribute("class").contains("ng-not-empty")) {
                        innerCheckBox(fieldAndValueElement.get(1)).click();
                        for (WebElement acceptedLanguageElement : internationalPreview.getAcceptedLanguagesTests()) {
                            acceptedLanguageStringList.add(acceptedLanguageElement.getText());
                        }
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                !acceptedLanguageStringList.contains(fieldAndValueElement.get(1)));
                    } else if (innerCheckBox(fieldAndValueElement.get(1)).getAttribute("class").contains("ng-empty")) {
                        innerCheckBox(fieldAndValueElement.get(1)).click();
                        for (WebElement acceptedLanguageElement : internationalPreview.getAcceptedLanguagesTests()) {
                            acceptedLanguageStringList.add(acceptedLanguageElement.getText());
                        }
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                acceptedLanguageStringList.contains(fieldAndValueElement.get(1)));
                    }
                    break;
            }
        }
    }

    public void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues, List<List<String>> details) {
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Application Deadline" :
                    getEditButton(key).click();
                    Select dropDown = new Select(getAppDeadlineDropDown(details.get(0).get(1)));
                    if(generatedValues.get(key).equals("32")) {
                        dropDown.selectByVisibleText("1");
                    } else {
                        dropDown.selectByVisibleText(generatedValues.get(key));
                    }
                    break;
                case "Fees" :
                    getEditButton(key).click();
                    innerEditSection(details.get(1).get(1)).clear();
                    innerEditSection(details.get(1).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Test Requirements" :
                    getEditButton(key).click();
                    getTestRequirementInnerSection(details.get(2).get(1)).click();
                    Select testReqDropDown = new Select(getTestReqDropDown(details.get(2).get(1)));
                    testReqDropDown.selectByVisibleText(generatedValues.get(key));
                    break;
                case "Applications" :
                    getEditButton(key).click();
                    innerEditSection(details.get(3).get(1)).clear();
                    innerEditSection(details.get(3).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Test Scores" :
                    getEditButton(key).click();
                    getTestScoresInnerSection(details.get(4).get(1).split(";")[0]).click();
                    innerEditSection(details.get(4).get(1).split(";")[1]).clear();
                    innerEditSection(details.get(4).get(1).split(";")[1]).sendKeys(generatedValues.get(key));
                    break;
                case "Qualifications" :
                    getEditButton(key).click();
                    getQualificationsInnerSection(details.get(5).get(1)).click();
                    Select qualificationsDropDown = new Select(getQualificationsDropDown(details.get(5).get(1)));
                    qualificationsDropDown.selectByVisibleText(generatedValues.get(key));
                    break;
                case "Accepted English Tests" :
                    getEditButton(key).click();
                    if (generatedValues.get(key).equals("no")) {
                        if (innerCheckBox(details.get(6).get(1)).getAttribute("class").contains("ng-not-empty")) {
                            innerCheckBox(details.get(6).get(1)).click();
                        }
                    } else if (generatedValues.get(key).equals("yes")) {
                        if (innerCheckBox(details.get(6).get(1)).getAttribute("class").contains("ng-empty")) {
                            innerCheckBox(details.get(6).get(1)).click();
                        }
                    }
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues) {

        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "Application Deadline" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Fees" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Test Requirements" :
                    generatedValues.put(key, getFollowingDropDownValue(fieldValues.get(key)));
                    break;
                case "Applications" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Test Scores" :
                    if (fieldValues.get(key).equals("--")) {
                        generatedValues.put(key, String.valueOf(1));
                    } else {
                        generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    }
                    break;
                case "Qualifications" :
                    generatedValues.put(key, getFollowingDropDownValue(fieldValues.get(key)));
                    break;
                case "Accepted English Tests" :
                    if (fieldValues.get(key).equals("yes")) {
                        generatedValues.put(key, "no");
                    } else if (fieldValues.get(key).equals("no")) {
                        generatedValues.put(key, "yes");
                    }
                    break;
            }
        }
        logger.info("Generated values: \n");
        for (String key : generatedValues.keySet()) {
            logger.info(key + " : " + generatedValues.get(key));
        }
        return generatedValues;
    }

    public void editAllFieldsBasedOnGatheredValues(DataTable stringsDataTable, HashMap<String, String> originalValues) {
        List<List<String>> details = stringsDataTable.asLists(String.class);
        HashMap<String, String> newValues = generateValues(originalValues);
        InternationalPageImpl.generatedValues = newValues;
        editFieldValuesWithGeneratedData(newValues, details);
        publish.clickPublishButton();
        publish.enterPublishReasonsText(details.get(7).get(1));
        publish.clickSubmitChangesButton();
        logger.info("All changes were submitted");
    }

    public void verifyErrorMessageWithInvalidData(DataTable stringsDataTable) {
        List<List<String>> fieldsDetails = stringsDataTable.cells(0);
        for (List<String> fieldElement : fieldsDetails) {
            switch (fieldElement.get(0)) {
                case "Test Scores" :
                    getEditButton(fieldElement.get(0)).click();
                    getTestScoresInnerSection(fieldElement.get(1)).click();
                    innerEditSection(fieldElement.get(2)).clear();
                    innerEditSection(fieldElement.get(2)).sendKeys(fieldElement.get(3));
                    assertTrue("Error message is not displayed", errorMsg().isDisplayed());
                    break;
            }
        }
    }

    //Locators
    private WebElement getEditButton(String label) {
        return getDriver().findElement(By.xpath("//h3[text()='"+label +"']"));
    }
    private WebElement innerEditSection(String section) {
        return getDriver().findElement(By.xpath("//label[text()='" + section + "']/following-sibling::input"));
    }
    private WebElement getAppDeadlineDropDown(String label) {
        WebElement dropDown = null;
        if (label.equals("Month")) {
            dropDown = getDriver().findElement(By.cssSelector("select#international-deadline-month"));
        } else if (label.equals("Day")) {
            dropDown = getDriver().findElement(By.cssSelector("select#international-deadline-day"));
        }
        return dropDown;
    }
    private WebElement getTestRequirementInnerSection(String label) {
        String position = "";
        switch (label) {
            case "SAT" : position = "1";
            break;
            case "ACT" : position = "2";
            break;
            case "TOEFL" : position = "3";
            break;
            case "IELTS" : position = "4";
            break;
        }
        return getDriver().findElement(By.xpath("//div[@class='entity-collection']/div/div[" + position + "]/div"));
    }
    private WebElement getTestReqDropDown(String section) {
        String position = "";
        switch (section) {
            case "SAT" : position = "1";
                break;
            case "ACT" : position = "2";
                break;
            case "TOEFL" : position = "3";
                break;
            case "IELTS" : position = "4";
                break;
        }
        return getDriver().findElement(By.xpath("//div[@class='entity-collection']/div/div[" + position + "]/div[2]/select-field/div/select"));
    }
    private WebElement getTestScoresInnerSection(String section) {
        return getDriver().findElement(By.xpath("//strong[contains(text(), '" + section + "')]"));
    }
    private WebElement getQualificationsDropDown(String section) {
        return getDriver().findElement(By.xpath("//strong[contains(text(), '" + section + "')]/../../div[2]/select-field/div/select"));
    }
    private WebElement getQualificationsInnerSection(String label) {
        return getDriver().findElement(By.xpath("//strong[contains(text(), '" + label + "')]"));
    }
    private WebElement innerCheckBox(String label) {
        return getDriver().findElement(By.xpath("//label[contains(text(), '" + label + "')]/../input"));
    }
    private String getFollowingDropDownValue(String option) {
        String result = "";
        switch (option) {
            case "OPTIONAL" : result = "Required";
            break;
            case "REQUIRED" : result = "Recommended";
            break;
            case "RECOMMENDED" : result = "Optional";
            break;
        }
        return result;
    }
    private WebElement errorMsg() { return getDriver().findElement(By.cssSelector("ng-form.ng-valid-maxlength.ng-dirty.ng-valid-parse.ng-invalid.ng-invalid-pattern span")); }
}
