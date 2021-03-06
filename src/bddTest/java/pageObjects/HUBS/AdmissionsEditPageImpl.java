package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AdmissionsEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    AdmissionsPageImpl admissionsPreview = new AdmissionsPageImpl();
    PublishPageImpl publish = new PublishPageImpl();
    OverviewEditPageImpl overviewEditPage = new OverviewEditPageImpl();

    public AdmissionsEditPageImpl() {
        logger = Logger.getLogger(AdmissionsEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(DataTable stringsDataTable) {
        String value="";
        List<List<String>> fieldsAndValues = stringsDataTable.asLists(String.class);
        for (List<String> fieldAndValueElement : fieldsAndValues) {
            switch (fieldAndValueElement.get(0)) {
                case "Acceptance Rate":
                    //acceptanceRateButton().click(); //added jsClick
                    jsClick(acceptanceRateButton());
                    //Applications accepted
                    getAcceptanceTextBox("Applications Accepted").clear();
                    getAcceptanceTextBox("Applications Accepted").sendKeys(fieldAndValueElement.get(1));

                    //Applications received
                    getAcceptanceTextBox("Applications Received").clear();
                    getAcceptanceTextBox("Applications Received").sendKeys(fieldAndValueElement.get(2));

                    int appAccepted = Integer.parseInt(fieldAndValueElement.get(1));
                    int appReceived = Integer.parseInt(fieldAndValueElement.get(2));
                    double calculatedAcceptanceRate = (double) appAccepted / (double) appReceived;
                    Double roundedAcceptanceRate = (Math.round(calculatedAcceptanceRate * 100.0) / 100.0) * 100;
                    int calculatedValue = roundedAcceptanceRate.intValue();
                    logger.info(calculatedValue);
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time. UI value: " + admissionsPreview.acceptanceRateText().getText() +
                                    " . Calculated value: " + roundedAcceptanceRate,
                            admissionsPreview.acceptanceRateText().getText().equals(Integer.toString(calculatedValue)));
                    break;
                case "Important Policies":
                    boolean isPolicyAdded = false;
                    //importantPoliciesButton().click();//added jsClick
                    jsClick(importantPoliciesButton());
                    Select importantPolicyDropdown = new Select(getImportantPolicyDropDown(fieldAndValueElement.get(1)));
                    importantPolicyDropdown.selectByVisibleText(fieldAndValueElement.get(2));
                    for (WebElement policy : admissionsPreview.importantPoliciesList()) {
                        if (policy.getText().toLowerCase().contains(fieldAndValueElement.get(2).replace("policy", "").toLowerCase().trim())) {
                            isPolicyAdded = true;
                        }
                    }
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time", isPolicyAdded);
                    break;
                case "Deadlines":
                    String newMonth = "";
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    //deadlinesButton().click();//added jsClick
                    jsClick(deadlinesButton());
                    //deadlineType(fieldAndValueElement.get(1)).click();//added click with below modification
                    value=" "+fieldAndValueElement.get(1)+" ";
                    deadlineType(value).click();
                    if (fieldAndValueElement.get(2).split(";")[0].equals("month")) {
                        Select deadlineMonthDropDown = new Select(deadlineMonth());
                        newMonth = pickNextMonth(admissionsPreview.deadLineMonth(fieldAndValueElement.get(1)).getText());
                        deadlineMonthDropDown.selectByVisibleText(newMonth);
                        assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time. UI value: " +
                                        admissionsPreview.deadLineMonth(fieldAndValueElement.get(1)).getText() + ". Data: " + newMonth.substring(0, 3),
                                admissionsPreview.deadLineMonth(fieldAndValueElement.get(1)).getText().equals(newMonth.substring(0, 3)));
                    } else if (fieldAndValueElement.get(2).split(";")[0].equals("day")) {
                        Select deadlineDayDropDown = new Select(deadlineDay());
                        deadlineDayDropDown.selectByVisibleText(fieldAndValueElement.get(2).split(";")[1]);
                    }
                    break;
                case "Fees":
                    jsClick(feesButton());
                    feesType(fieldAndValueElement.get(1)).clear();
                    feesType(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    admissionsPreview.feesTab().click();
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time. UI: " +
                                    getFeesInPreview(fieldAndValueElement.get(1)).getText() + ", Data: " + fieldAndValueElement.get(2),
                            getFeesInPreview(fieldAndValueElement.get(1)).getText().replace("$", "").equals(fieldAndValueElement.get(2)));
                    break;
                case "Application Requirements":
                    boolean isAppReqPresent = false;
                    //  applicationRequirementsButton().click(); added jsClick
                    jsClick(applicationRequirementsButton());
                    value=" "+fieldAndValueElement.get(2).split(";")[0]+" ";//passing this value in the next line of code
                    admissionsPreview.getAppRequirement(fieldAndValueElement.get(1), value).click();
                    //Select appReqDropDown = new Select(getAppRequirementDropDown(fieldAndValueElement.get(1), fieldAndValueElement.get(2).split(";")[0]));//added other line of code in the next line
                    Select appReqDropDown = new Select(getAppRequirementDropDown(fieldAndValueElement.get(1), value));
                    appReqDropDown.selectByVisibleText(fieldAndValueElement.get(2).split(";")[1]);
                    //admissionsPreview.appFactsTab(fieldAndValueElement.get(1)).click(); added js click
                    jsClick(admissionsPreview.appFactsTab(fieldAndValueElement.get(1)));
                    for (WebElement appReq : admissionsPreview.appReqList(fieldAndValueElement.get(2).split(";")[1])) {
                        if (appReq.getText().equals(fieldAndValueElement.get(2).split(";")[0])) {
                            isAppReqPresent = true;
                            break;
                        }
                    }
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time",
                            isAppReqPresent);
                    break;
                case "Recommended Courses":
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    //recommendedCoursesButton().click();//added js click
                    jsClick(recommendedCoursesButton());
                    //recommendedCourse(fieldAndValueElement.get(1)).click(); //click using the next line of code
                    value=" "+fieldAndValueElement.get(1)+" ";//added space before and after of a string due to new app changes
                    recommendedCourse(value).click();
                    courseYears(fieldAndValueElement.get(2).split(";")[0]).clear();
                    courseYears(fieldAndValueElement.get(2).split(";")[0]).sendKeys(fieldAndValueElement.get(2).split(";")[1]);

                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time. UI: " +
                                    admissionsPreview.getCourseYears(fieldAndValueElement.get(1), fieldAndValueElement.get(2).
                                            split(";")[0]).getText() + ", Data: " + fieldAndValueElement.get(2).split(";")[1],
                            admissionsPreview.getCourseYears(fieldAndValueElement.get(1), fieldAndValueElement.get(2).
                                    split(";")[0]).getText().equals(fieldAndValueElement.get(2).split(";")[1]));
                    break;
                case "Application Factors":
                    boolean isAppFactorPresent = false;
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    //applicationFactorsButton().click(); //jsclick
                    jsClick(applicationFactorsButton());
                    //appFactorType(fieldAndValueElement.get(1)).click();//added below click
                    value=" "+fieldAndValueElement.get(1)+" ";
                    appFactorType(value).click();
                    Select importanceDropdown = new Select(appFactorsImportanceDropdownElement());
                    importanceDropdown.selectByVisibleText(fieldAndValueElement.get(2));
                    for (WebElement appFactor : admissionsPreview.appFactorsList(fieldAndValueElement.get(2))) {
                        if (appFactor.getText().equals(fieldAndValueElement.get(1))) {
                            isAppFactorPresent = true;
                            break;
                        }
                    }
                    assertTrue(fieldAndValueElement.get(0) + " cannot be edited in real time",
                            isAppFactorPresent);
                    break;
            }
        }
    }

    private void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues, List<List<String>> fieldsDetails) {
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Acceptance Rate":
                    acceptanceRateButton().click();
                    getAcceptanceTextBox("Applications Received").clear();
                    getAcceptanceTextBox("Applications Received").sendKeys("10000");
                    String newPercentage = Integer.toString(Integer.parseInt(generatedValues.get(key))) + "00";
                    getAcceptanceTextBox("Applications Accepted").clear();
                    getAcceptanceTextBox("Applications Accepted").sendKeys(newPercentage);
                    break;
                case "Important Policies":
                    importantPoliciesButton().click();
                    Select waitingListDropdown = new Select(getImportantPolicyDropDown("Waiting List"));
                    if (generatedValues.get(key).equals("present")) {
                        waitingListDropdown.selectByVisibleText("yes");
                    } else if (generatedValues.get(key).equals("not present")) {
                        waitingListDropdown.selectByVisibleText("no");
                    }
                    break;
                case "Deadlines":
                    deadlinesButton().click();
                    String deadlineName = "";
                    for (List<String> row : fieldsDetails) {
                        if (row.get(0).equals(key)) {
                            deadlineName = row.get(1);
                        }
                    }
                    //deadlineType(deadlineName).click(); added below click
                    String value=" "+deadlineName+" ";
                    jsClick(deadlineType(value));
                    Select deadlineMonthDropdown = new Select(deadlineMonth());
                    deadlineMonthDropdown.selectByVisibleText(generatedValues.get(key));
                    break;
                case "Fees":
                    feesButton().click();
                    feesType("Application Fee").clear();
                    feesType("Application Fee").sendKeys(generatedValues.get(key));
                    break;
                case "Application Requirements":
                    applicationRequirementsButton().click();
                    appReqElement("Freshman", " Campus Visit ").click();//added space in  Campus visit
                    Select appReqDropdown = new Select(getAppRequirementDropDown("Freshman", " Campus Visit "));
                    if (generatedValues.get(key).equals("present")) {
                        appReqDropdown.selectByVisibleText("Recommended");
                    } else if (generatedValues.get(key).equals("not present")) {
                        appReqDropdown.selectByVisibleText("Required");
                    }
                    break;
                case "Recommended Courses":
                    // recommendedCoursesButton().click(); added jsclick
                    //recommendedCourse("English").click(); added jsclick
                    jsClick(recommendedCoursesButton());
                    jsClick(recommendedCourse(" English "));
                    String newRecommCoursesYearsReq = Integer.toString(Integer.parseInt(generatedValues.get(key)));
                    courseYears("Years Required").clear();
                    courseYears("Years Required").sendKeys(newRecommCoursesYearsReq);
                    break;
                case "Application Factors":
                    String appFactor = "";
                    for (List<String> row : fieldsDetails) {
                        String control = row.get(0);
                        if (row.get(0).equals("Application Factors")) {
                            appFactor = row.get(1);
                        }
                    }
                    //applicationFactorsButton().click(); added below js click
                    //appFactorType(appFactor).click(); added below js click
                    jsClick(applicationFactorsButton());
                    value=" "+appFactor+" ";
                    appFactorType(value).click();
                    Select importanceDropdown = new Select(appFactorsImportanceDropdownElement());
                    if (generatedValues.get(key).equals("present")) {
                        importanceDropdown.selectByVisibleText("Important");
                    } else if (generatedValues.get(key).equals("not present")) {
                        importanceDropdown.selectByVisibleText("Not Considered");
                    }
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues, List<List<String>> details) {
        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "Acceptance Rate":
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key).replace(",","")) + 1));
                    break;
                case "Important Policies":
                    String result = "";
                    if (fieldValues.get(key).equals("present")) {
                        result = "not present";
                    } else if (fieldValues.get(key).equals("not present")) {
                        result = "present";
                    }
                    generatedValues.put(key, result);
                    break;
                case "Deadlines":
                    String resultingMonth = pickNextMonth(fieldValues.get(key).substring(0, 3));
                    generatedValues.put(key, resultingMonth);
                    break;
                case "Fees":
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Application Requirements":
                    String isAppReqPresent = "";
                    if (fieldValues.get(key).equals("present")) {
                        isAppReqPresent = "not present";
                    } else if (fieldValues.get(key).equals("not present")) {
                        isAppReqPresent = "present";
                    }
                    generatedValues.put(key, isAppReqPresent);
                    break;
                case "Recommended Courses":
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Application Factors":
                    String isAppFactorPresent = "";
                    if (fieldValues.get(key).equals("present")) {
                        isAppFactorPresent = "not present";
                    } else if (fieldValues.get(key).equals("not present")) {
                        isAppFactorPresent = "present";
                    }
                    generatedValues.put(key, isAppFactorPresent);
            }
        }
        logger.info("\nGenerated values: \n");
        for (String key : generatedValues.keySet()) {
            logger.info(key + " : " + generatedValues.get(key) + "\n");
        }
        return generatedValues;
    }

    public void editAllFieldsBasedOnGatheredValues(DataTable stringsDataTable, HashMap<String, String> originalValues) {
        List<List<String>> fieldsDetails = stringsDataTable.asLists(String.class);
        HashMap<String, String> newValues = generateValues(originalValues, fieldsDetails);
        AdmissionsPageImpl.generatedValues = newValues;
        editFieldValuesWithGeneratedData(newValues, fieldsDetails);
        publish.clickPublishButton();
        publish.enterPublishReasonsText(fieldsDetails.get(0).get(1));
        publish.clickSubmitChangesButton();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(overviewEditPage.loadingIconLeftMenuLocator), 1));
        logger.info("All changes were submitted");
    }

    public String pickNextMonth(String monthFirstThreeLetters) {
        String result = "";
        switch (monthFirstThreeLetters) {
            case "Jan":
                result = "February";
                break;
            case "Feb":
                result = "March";
                break;
            case "Mar":
                result = "April";
                break;
            case "Apr":
                result = "May";
                break;
            case "May":
                result = "June";
                break;
            case "Jun":
                result = "July";
                break;
            case "Jul":
                result = "August";
                break;
            case "Aug":
                result = "September";
                break;
            case "Sep":
                result = "October";
                break;
            case "Oct":
                result = "November";
                break;
            case "Nov":
                result = "December";
                break;
            case "Dec":
                result = "January";
                break;
            default:
                result = "No value was assigned. Check the input string format";
        }
        return result;
    }

    private WebElement getFeesInPreview(String type) {
        WebElement result = null;
        switch (type) {
            case "Freshman Application Fee":
                result = admissionsPreview.freshmanApplicationFee();
                break;
            case "Freshman Deposit Fee":
                result = admissionsPreview.freshmanDeposit();
                break;
            case "Transfer Application Fee":
                result = admissionsPreview.transferApplicationFee();
                break;
            case "Transfer Deposit Fee":
                result = admissionsPreview.transferDepositFee();
                break;
            case "International Application Fee":
                result = admissionsPreview.internationalApplicationFee();
                break;
            case "International Deposit Fee":
                result = admissionsPreview.internationalDeposit();
                break;
        }
        return result;
    }

    public void verifyErrorMessageWithInvalidData(DataTable stringsDataTable) {
        List<List<String>> fieldsDetails = stringsDataTable.cells(0);
        for (List<String> fieldElement : fieldsDetails) {
            switch (fieldElement.get(0)) {
                case "Recommended Courses":
                    recommendedCoursesButton().click();
                    for (int i = 0; i < 10; i++) {
                        try {
                            recommendedCourse(fieldElement.get(1)).click();
                            break;
                        } catch (WebDriverException e) {
                            publishButton().sendKeys(Keys.PAGE_DOWN);
                        }
                    }
                    courseYears(fieldElement.get(2)).clear();
                    courseYears(fieldElement.get(2)).sendKeys(fieldElement.get(3));
                    assertTrue("Error message is not displayed", errorMsg().isDisplayed());
                    break;
                case "Fees":
                    feesButton().click();
                    feesType(fieldElement.get(1)).clear();
                    feesType(fieldElement.get(1)).sendKeys(fieldElement.get(2));
                    assertTrue("Error message is not displayed", errorMsg().isDisplayed());
            }
        }
    }

    //Locators
    private WebElement acceptanceRateButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Acceptance Rate']"));
    }

    private WebElement getAcceptanceTextBox(String label) {
        return getDriver().findElement(By.xpath("//label[text()='" + label + "']/following-sibling::input"));
    }

    private WebElement importantPoliciesButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Important Policies']"));
    }

    private WebElement getImportantPolicyDropDown(String label) {
        return getDriver().findElement(By.xpath("//label[text()='" + label + "']/following-sibling::select"));
    }

    private WebElement deadlinesButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Deadlines']"));
    }

    private WebElement deadlineType(String label) {
        return getDriver().findElement(By.xpath("//strong[text()='" + label + "']"));
    }

    private WebElement deadlineMonth() {
        return getDriver().findElement(By.cssSelector("select[id *= '-field_he_month_of_the_deadline-month'"));
    }

    private WebElement deadlineDay() {
        return getDriver().findElement(By.cssSelector("select[id *= '-field_he_day_of_the_deadline-day'"));
    }

    private WebElement feesButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Fees']"));
    }

    private WebElement feesType(String label) {
        return getDriver().findElement(By.xpath("//label[text()='" + label + "']/../input"));
    }

    private WebElement applicationRequirementsButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Application Requirements']"));
    }

    private WebElement appReqElement(String group, String label) {
        return getDriver().findElement(By.xpath("//legend[text()='" + group + "']/following-sibling::entity-collection/div/div/div/div/strong[text()='" + label + "']"));
    }

    private WebElement getAppRequirementDropDown(String group, String label) {
        return getDriver().findElement(By.xpath("//legend[text()='" + group + "']/following-sibling::entity-collection/div/div/div/div/strong[text()='" + label + "']/../../div/select-field/div/select"));
    }

    private WebElement recommendedCoursesButton() {
        return getDriver().findElement(By.xpath("//div[@class='field-group__heading']//h3[text()='Recommended Courses']"));
    }

    private WebElement applicationFactorsButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Application Factors']"));
    }

    private WebElement appFactorType(String type) {
        return getDriver().findElement(By.xpath("//strong[text()='" + type + "']"));
    }
    private WebElement appFactorsImportanceDropdownElement() { return getDriver().findElement(By.xpath("//select[contains(@id, 'field_he_importance')]")); }
    private WebElement recommendedCourse(String label) {
        return getDriver().findElement(By.xpath("//strong[contains(text(), '"+ label +"')]")); }

    private WebElement courseYears(String requiredOrRecommended) { return getDriver().findElement(By.xpath("//label[text()='" + requiredOrRecommended + "']/following-sibling::input")); }
    private WebElement errorMsg() { return getDriver().findElement(By.cssSelector("ng-form.ng-valid-maxlength.ng-dirty.ng-valid-parse.ng-invalid.ng-invalid-pattern span")); }
    private WebElement publishButton() { return driver.findElement(By.cssSelector("span.intersect-btn.intersect-btn--fuschia.ng-binding")); }
}