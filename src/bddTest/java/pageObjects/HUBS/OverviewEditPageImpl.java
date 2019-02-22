package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class OverviewEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    OverviewPageImpl overviewPreview = new OverviewPageImpl();
    PublishPageImpl publish = new PublishPageImpl();
    StudiesEditPageImpl studiesEditPage = new StudiesEditPageImpl();

    public OverviewEditPageImpl() {
        logger = Logger.getLogger(StudentLifeEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(DataTable stringsDataTable) {
        List<List<String>> fieldsAndValues = stringsDataTable.cells(0);
        for (List<String> fieldAndValueElement : fieldsAndValues) {
            switch (fieldAndValueElement.get(0)) {
                case "Opening Statement" :
                    openingStatementButton().click();
                    openingStatementTextArea().clear();
                    openingStatementTextArea().sendKeys(fieldAndValueElement.get(1));

                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.openingStatement().getText().equals(fieldAndValueElement.get(1)));
                    break;
                case "Website" :
                    websiteButton().click();
                    websiteTextArea().clear();
                    websiteTextArea().sendKeys(fieldAndValueElement.get(1));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.websiteText().getText().equals(fieldAndValueElement.get(1)));
                    break;
                case "School Type" :
                    schoolTypeButton().click();
                    Select dropDown = new Select(getSchoolTypeDropDown(fieldAndValueElement.get(1).split(";")[0]));
                    dropDown.selectByVisibleText(fieldAndValueElement.get(1).split(";")[1]);
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.getQuickFact(fieldAndValueElement.get(0)).getText().equals(fieldAndValueElement.get(1).split(";")[2]));
                    break;
                case "Undergraduate Enrollment" :
                    undergradEnrollButton().click();
                    innerEditSection("Undergraduate Women").clear();
                    innerEditSection("Undergraduate Women").sendKeys(fieldAndValueElement.get(1).split(";")[0]);
                    innerEditSection("Undergraduate Men").clear();
                    innerEditSection("Undergraduate Men").sendKeys(fieldAndValueElement.get(1).split(";")[1]);
                    int totalUndergradEnroll = Integer.parseInt(fieldAndValueElement.get(1).split(";")[0]) +
                            Integer.parseInt(fieldAndValueElement.get(1).split(";")[1]);

                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time: ",
                            overviewPreview.getQuickFact(fieldAndValueElement.get(0)).getText().equals(Integer.toString(totalUndergradEnroll)));
                    break;
                case "Student / Faculty Ratio" :
                    stuFacRatioButton().click();
                    stuFacRatioTextArea().clear();
                    stuFacRatioTextArea().sendKeys(fieldAndValueElement.get(1));

                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.getQuickFact(fieldAndValueElement.get(0)).getText().contains(fieldAndValueElement.get(1)));
                    break;
                case "Campus Surroundings" :
                    campusSurroundingsButton().click();
                    Select campusSurrDropDown = new Select(getCampusSurroundingsDropDown());
                    campusSurrDropDown.selectByVisibleText(fieldAndValueElement.get(1).split(";")[1]);

                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.getQuickFact(fieldAndValueElement.get(0)).getText().
                                    equals(fieldAndValueElement.get(1).split(";")[1].split(",")[0]));
                    break;
                case "Test Scores" :
                    testScoresButton().click();
                    studiesEditPage.publishButton().sendKeys(Keys.PAGE_DOWN);
                    studiesEditPage.publishButton().sendKeys(Keys.PAGE_DOWN);
                    testScoresInnerSection(fieldAndValueElement.get(1).split(";")[0]).click();
                    waitUntilElementExists(innerEditSection(fieldAndValueElement.get(1).split(";")[1]));
                    innerEditSection(fieldAndValueElement.get(1).split(";")[1]).clear();
                    waitUntilElementExists(innerEditSection(fieldAndValueElement.get(1).split(";")[1]));
                    innerEditSection(fieldAndValueElement.get(1).split(";")[1]).sendKeys(fieldAndValueElement.get(1).split(";")[2]);
                    overviewPreview.getTestScoresTableButton(fieldAndValueElement.get(1).split(";")[0].split(" ")[0]).click();
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.getTestScoresTableValue("SAT 2400 Reading", fieldAndValueElement.get(1).split(";")[1])
                                    .getText().equals(fieldAndValueElement.get(1).split(";")[2]));
                    break;
                case "Average GPA" :
                    avgGPAButton().click();
                    studiesEditPage.publishButton().sendKeys(Keys.PAGE_DOWN);
                    studiesEditPage.publishButton().sendKeys(Keys.PAGE_DOWN);
                    avgGPATextArea().clear();
                    avgGPATextArea().sendKeys(fieldAndValueElement.get(1));

                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.avgGPAText().getText().equals(fieldAndValueElement.get(1)));
                    break;
                case "Contact Information" :
                    contactInfoButton().click();
                    studiesEditPage.publishButton().sendKeys(Keys.PAGE_DOWN);
                    studiesEditPage.publishButton().sendKeys(Keys.PAGE_DOWN);
                    getContactInfoInput(fieldAndValueElement.get(1).split(";")[0], fieldAndValueElement.get(1).split(";")[1]).clear();
                    getContactInfoInput(fieldAndValueElement.get(1).split(";")[0], fieldAndValueElement.get(1).split(";")[1])
                            .sendKeys(fieldAndValueElement.get(1).split(";")[2]);
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            overviewPreview.applicationMailingAddressText().getText().contains(fieldAndValueElement.get(1).split(";")[2]));

                    break;
            }
        }
    }

    public void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues) {
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Opening Statement" :
                    openingStatementButton().click();
                    openingStatementTextArea().clear();
                    openingStatementTextArea().sendKeys(generatedValues.get(key));
                    break;
                case "Website" :
                    websiteButton().click();
                    websiteTextArea().clear();
                    websiteTextArea().sendKeys(generatedValues.get(key));
                    break;
                case "School Type" :
                    schoolTypeButton().click();
                    Select dropDown = new Select(getSchoolTypeDropDown("Level"));
                    dropDown.selectByVisibleText(generatedValues.get(key));
                    break;
                case "Undergraduate Enrollment" :
                    //undergradEnrollButton().click(); added js click
                    jsClick(undergradEnrollButton());
                    innerEditSection("Undergraduate Women").clear();
                    innerEditSection("Undergraduate Women").sendKeys(generatedValues.get(key));
                    innerEditSection("Undergraduate Men").clear();
                    innerEditSection("Undergraduate Men").sendKeys("0");
                    break;
                case "Student / Faculty Ratio" :
                    stuFacRatioButton().click();
                    stuFacRatioTextArea().clear();
                    stuFacRatioTextArea().sendKeys(generatedValues.get(key));
                    break;
                case "Campus Surroundings" :
                    campusSurroundingsButton().click();//added js click
                    jsClick(campusSurroundingsButton());
                    Select campusSurrDropDown = new Select(getCampusSurroundingsDropDown());
                    campusSurrDropDown.selectByVisibleText(generatedValues.get(key));
                    break;
                case "Test Scores" :
                    testScoresButton().click();
                    testScoresInnerSection("SAT Critical Reading").click();
                    innerEditSection("Low").clear();
                    innerEditSection("Low").sendKeys(generatedValues.get(key));
                    break;
                case "Average GPA" :
                    avgGPAButton().click();
                    avgGPATextArea().clear();
                    avgGPATextArea().sendKeys(generatedValues.get(key));
                    break;
                case "Contact Information" :
                    contactInfoButton().click();
                    getContactInfoInput("Application Mailing Address", "ZIP").clear();
                    getContactInfoInput("Application Mailing Address", "ZIP").sendKeys(generatedValues.get(key));
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("mm:ss");
        SimpleDateFormat simpleDate = new SimpleDateFormat("ss");

        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "Opening Statement" :
                    if (fieldValues.get(key).contains("/")) {
                        generatedValues.put(key, fieldValues.get(key).split("/")[0] + "/" + dateFormat.format(date));
                    } else {
                        generatedValues.put(key, fieldValues.get(key) + "/" + dateFormat.format(date));
                    }
                    break;
                case "Website" :
                    if (fieldValues.get(key).contains(",")) {
                        generatedValues.put(key, fieldValues.get(key).split(",")[0] + "," + dateFormat.format(date));
                    } else {
                        generatedValues.put(key, fieldValues.get(key) + "," + dateFormat.format(date));
                    }
                    break;
                case "School Type" :
                    generatedValues.put(key, getNextSchoolTypeDropDown(fieldValues.get(key)));
                    break;
                case "Undergraduate Enrollment" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key).replace(",", "")) + 1));
                    break;
                case "Student / Faculty Ratio" :
                    int finalValue = Integer.parseInt(fieldValues.get(key).split(" ")[0]);
                    generatedValues.put(key, String.valueOf(finalValue + 1));
                    break;
                case "Campus Surroundings" :
                    generatedValues.put(key, getNextCampusSurroundings(fieldValues.get(key)));
                    break;
                case "Test Scores" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Average GPA" :
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    generatedValues.put(key, String.valueOf(numberFormat.format(Double.parseDouble(fieldValues.get(key)) + 0.1)));
                    break;
                case "Contact Information" :
                    generatedValues.put(key, simpleDate.format(date));
                    break;
            }
        }
        logger.info("Generated values: \n");
        for (String key : generatedValues.keySet()) {
            logger.info(key + " : " + generatedValues.get(key));
        }
        return generatedValues;
    }

    public void editAllFieldsBasedOnGatheredValues(String publishReason, HashMap<String, String> originalValues) {
        HashMap<String, String> newValues = generateValues(originalValues);
        OverviewPageImpl.generatedValues = newValues;
        editFieldValuesWithGeneratedData(newValues);
        publish.clickPublishButton();
        publish.enterPublishReasonsText(publishReason);
        publish.clickSubmitChangesButton();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(loadingIconLeftMenuLocator), 1));
        logger.info("All changes were submitted");
    }

    public void verifyOverlayMessage(String message) {
        boolean getOverlayMessagePartI = getDriver().findElement(By.xpath("//div[text()='Use the ']")).isDisplayed();
        boolean getOverlayMessagePartII = getDriver().findElement(By.xpath("//i[text()='Photos and Videos']")).isDisplayed();
        boolean getOverlayMessagePartIII = getDriver().findElement(By.xpath("//div[text()=' option under the ']")).isDisplayed();
        boolean getOverlayMessagePartIV = getDriver().findElement(By.xpath("//i[text()='Media']")).isDisplayed();
        boolean closeButton = getDriver().findElement(By.cssSelector("div[class='webtour-overlay-content-container__close']")).isDisplayed();


        assertTrue("Overlay Message it's not correct",
                getOverlayMessagePartI);
        assertTrue("Overlay Message it's not correct",
                getOverlayMessagePartI);

        assertTrue("Overlay Message it's not correct",
                getOverlayMessagePartII);

        assertTrue("Overlay Message it's not correct",
                getOverlayMessagePartIII);

        assertTrue("Overlay Message it's not correct",
                getOverlayMessagePartIV);

        assertTrue("Close button does not exists",
                getOverlayMessagePartIV);
    }

    //Locators
    private WebElement openingStatementButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Opening Statement']"));
    }
    private WebElement openingStatementTextArea() {
        return getDriver().findElement(By.cssSelector("textarea[name=\"inputField\"]"));
    }
    private WebElement websiteButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Website']"));
    }
    private WebElement schoolTypeButton() {
        return getDriver().findElement(By.xpath("//h3[text()='School Type']"));
    }
    private WebElement undergradEnrollButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Undergraduate Enrollment']"));
    }
    private WebElement stuFacRatioButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Student / Faculty Ratio']"));
    }
    private WebElement campusSurroundingsButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Campus Surroundings']"));
    }
    private WebElement testScoresButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Test Scores']"));
    }
    private WebElement avgGPAButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Average GPA']"));
    }
    private WebElement contactInfoButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Contact Information']"));
    }
    private WebElement websiteTextArea() {
        return getDriver().findElement(By.cssSelector("input[id*='-field_url']"));
    }
    private WebElement getSchoolTypeDropDown(String label) {
        return getDriver().findElement(By.xpath("//label[text()='" + label + "']/../select"));
    }
    private WebElement innerEditSection(String section) {
        return getDriver().findElement(By.xpath("//label[text()='" + section + "']/following-sibling::input"));
    }
    private WebElement stuFacRatioTextArea() {
        return getDriver().findElement(By.cssSelector("input[id*='-field_he_studentfacultyratio']"));
    }
    private WebElement testScoresInnerSection(String label) {
        return getDriver().findElement(By.xpath("//strong[contains(text(), '" + label + "')]"));
    }
    private WebElement avgGPATextArea() {
        return getDriver().findElement(By.cssSelector("input[id*='-field_he_gpaaverage']"));
    }
    private WebElement getContactInfoInput(String section, String inputLabel) {
        WebElement result = null;
        switch (section) {
            case "Application Mailing Address" :
                if (inputLabel.equals("Country")) {
                    result = getDriver().findElement(By.xpath("select[id*='-field_country_ref'].fc-select.fc-select--full-bleed.hem-select-field.ng-pristine.ng-valid.ng-empty.ng-touched"));
                } else if (inputLabel.equals("State")) {
                    result = getDriver().findElement(By.xpath("//legend[text()='Application Mailing Address']/../address-field/typeahead-field[1]/div/div/input"));
                } else {
                    result = getDriver().findElement(By.xpath("//legend[text()='Application Mailing Address']/../address-field/text-field[@name='" + inputLabel + "']/div/ng-form/input"));
                }
                break;
        }
        return result;
    }
    private WebElement getCampusSurroundingsDropDown() {
        return getDriver().findElement(By.cssSelector("select[id*='-field_he_city_size']"));
    }
    private String getNextSchoolTypeDropDown(String dropDownValue) {
        String result = "";
        switch (dropDownValue) {
            case "Four Year School" : result = "Two Year School";
                break;
            case "Two Year School" : result = "Less than 2 Year School";
                break;
            case  "Less than 2 Year School" : result = "Corporation";
                break;
            case "Corporation" : result = "Four Year School";
        }
        return result;
    }
    private String getNextCampusSurroundings(String currentCampusSurroundings) {
        String result = "";
        switch (currentCampusSurroundings) {
            case "Large City" : result = "Midsize City, greater than 100,000 pop";
                break;
            case "Midsize City" : result = "Small City, less than 100,000 pop";
                break;
            case "Small City" : result = "Suburb, near city w/ greater than 250,000 pop";
                break;
            case "Suburb near large city" : result = "Suburb, near city w/ greater than 100,000 pop";
                break;
            case "Suburb near midsize city" : result = "Suburb, near city w/ less than 100,000 pop";
                break;
            case "Suburb near small city" : result = "Town, within 10 miles of urban area";
                break;
            case "Town" : result = "Rural, within 5 miles of urban area";
                break;
            case "Rural" : result = "Large City, greater than 250,000 pop";
                break;
        }
        return result;
    }
    public String loadingIconLeftMenuLocator = "div.fc-loader.fc-loader-three-bounce.fc-loader--color-primary";
}
