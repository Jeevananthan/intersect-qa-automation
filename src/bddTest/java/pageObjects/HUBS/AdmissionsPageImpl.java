package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AdmissionsPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;
    private HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
    private FCMainPageImpl fcMain = new FCMainPageImpl();
    private FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
    private HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
    private HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

    public AdmissionsPageImpl() {
        logger = Logger.getLogger(AdmissionsPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        assertTrue("Acceptance Rate section is not displayed", acceptanceRateText().isDisplayed());
        assertTrue("Important Policies section is not displayed", importantPoliciesSection().isDisplayed());
        assertTrue("Application Fees section is not displayed", applicationFeesText().isDisplayed());
        assertTrue("Early Action Deadline section is not displayed", earlyActionDeadlineText().isDisplayed());
        assertTrue("Application Requirements section is not displayed", appRequirementsSection().isDisplayed());
        assertTrue("Recommended Courses section is not displayed", recommendedCoursesSection().isDisplayed());
        assertTrue("Application Factors section is not displayed", appFactorsSection().isDisplayed());
        assertTrue("Application Information section is not displayed", appInformationSection().isDisplayed());
    }

    public HashMap<String, String> getValuesFromFields(List<String> fieldList) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        for (String field : fieldList) {
            switch (field.split(";")[0]) {
                case "Acceptance Rate" :
                    fieldValues.put(field.split(";")[0], acceptanceRateText().getText());
                    break;
                case "Important Policies" :
                    //This detects the presence of "Wait List" in the
                    //"Important" policies column
                    String isPolicyPresent = "not present";
                    for (WebElement policy : importantPoliciesList()) {
                        if (policy.getText().equals(field.split(";")[1])) {
                            isPolicyPresent = "present";
                        }
                        break;
                    }
                    fieldValues.put(field.split(";")[0], isPolicyPresent);
                    break;
                case "Deadlines" :
                    //This tales the value of the month of the Early Action Deadline
                    fieldValues.put(field.split(";")[0], earlyActionDeadlineMonthText().getText());
                    break;
                case "Fees" :
                    //This takes the value of the Freshman Application Fee
                    feesTab().click();
                    fieldValues.put(field.split(";")[0], freshmanApplicationFee().getText().replace("$", ""));
                    break;
                case "Application Requirements" :
                    //This detects the presence of the "Campus Visit" item in the "Required" list
                    String isCampusVisitPresent = "not present";
                    for (WebElement appReqRequired: appReqList(field.split(";")[1])) {
                        if (appReqRequired.getText().equals(field.split(";")[2])) {
                            isCampusVisitPresent = "present";
                            break;
                        }
                    }
                    fieldValues.put(field.split(";")[0], isCampusVisitPresent);
                    break;
                case "Recommended Courses" :
                    //This takes the "Years Required" for "English"
                    String yearsRequired = getCourseYears(field.split(";")[1], field.split(";")[2]).getText();
                    fieldValues.put(field.split(";")[0], yearsRequired);
                    break;
                case "Application Factors" :
                    //This detects the presence of the "Class Rank" item in the "Important" application factors list
                    String isAppFactorPresent = "not present";
                    for (WebElement appFactor : appFactorsList(field.split(";")[2])) {
                        if (appFactor.getText().equals(field.split(";")[1])) {
                            isAppFactorPresent = "present";
                            break;
                        }
                    }
                    fieldValues.put(field.split(";")[0], isAppFactorPresent);
                    break;
            }
        }
        logger.info("\nValues from the page: \n");
        for (String key : fieldValues.keySet()) {
            logger.info(key + " : " + fieldValues.get(key) + "\n");
        }
        return fieldValues;
    }

    public void verifyChangesPublishedInHUBS(String username, String password, String college, DataTable stringsDataTable) {
        List<String> sections = stringsDataTable.asList(String.class);
        List<String> creds = new ArrayList<String>() {{
            add(username);
            add(password);
            add(college);
        }};
        hubsLogin.defaultLogIn(creds);
        fcMain.clickCollegesTab();
        collegesPage.searchAndOpenCollege(college);
        waitUntilPageFinishLoading();
        hubsMainMenu.clickAdmissionsTab();
        waitUntilPageFinishLoading();
        verifyGeneratedValues(sections, generatedValues);
    }

    private void verifyGeneratedValues(List<String> sections, HashMap<String, String> generatedValues) {
        String course = "";
        String requiredOrRecommended = "";
        String importance = "";

        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Acceptance Rate" :
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key
                            + "was not successfully generated").isEqualTo(acceptanceRateText().getText());
                    break;
                case "Important Policies" :
                    List<String> policiesStrings = new ArrayList<>();
                    String policy = "";
                    for (WebElement element : importantPoliciesList()) {
                        policiesStrings.add(element.getText());
                    }
                    for (String row : sections) {
                        if (row.split(";")[0].equals(key)) {
                            policy = row.split(";")[1];
                        }
                    }
                    softly().assertThat(policiesStrings).as("The value for " + key + " was not successfully generated").contains(policy);
                    break;
                case "Deadlines" :
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key + " was not successfully generated")
                            .isEqualTo(earlyActionDeadlineMonthText().getText());
                    break;
                case "Fees" :
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key + " was not successfully generated")
                            .isEqualTo(applicationFeesText().getText());
                    break;
                case "Application Requirements" :
                    List<String> requirementsStrings = new ArrayList<>();
                    String requirement = "";
                    for (WebElement element : importantPoliciesList()) {
                        requirementsStrings.add(element.getText());
                    }
                    for (String row : sections) {
                        if (row.split(";")[0].equals(key)) {
                            requirement = row.split(";")[2];
                        }
                    }
                    softly().assertThat(requirementsStrings).as("The value for " + key + " was not successfully generated").contains(requirement);
                    break;
                case "Recommended Courses" :
                    for (String row : sections) {
                        if (row.split(";")[0].equals(key)) {
                            course = row.split(";")[1];
                            requiredOrRecommended = row.split(";")[2];
                        }
                    }
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key + " was not successfully generated")
                            .isEqualTo(getCourseYears(course, requiredOrRecommended).getText());
                    break;
                case "Application Factors" :
                    List<String> appFactors = new ArrayList<>();
                    String isPresentOrNot = "";
                    for (String row : sections) {
                        if (row.split(";")[0].equals(key)) {
                            importance = row.split(";")[2];
                        }
                    }
                    for (WebElement appFactor : appFactorsList(importance)) {
                        appFactors.add(appFactor.getText());
                    }
                    if (appFactors.contains(generatedValues.get(key))) {
                        isPresentOrNot = "present";
                    } else {
                        isPresentOrNot = "not present";
                    }
                    softly().assertThat(generatedValues.get(key)).as("The value for " + key + " was not successfully generated")
                            .isEqualTo(isPresentOrNot);
                    break;
            }
        }
    }

    public WebElement getCourseYears(String courseName, String requiredOrRecommended) {
        String yearsPosition = "";
        if (requiredOrRecommended.equals("Years Required")) {
            yearsPosition = "1";
        } else if (requiredOrRecommended.equals("Years Recommended")) {
            yearsPosition = "2";
        }
        return getDriver().findElement(By.xpath("//td[text()='" + courseName + "']/following-sibling::td[" + yearsPosition + "]"));
    }

    //Locators

    public WebElement acceptanceRateText() {
        return getDriver().findElement(By.cssSelector("div.hub-data-pod--meter-value.hub-data-pod--meter-value--percent.ng-binding"));
    }
    public WebElement importantPoliciesSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.policies.length > 0\"]"));
    }
    public WebElement applicationFeesText() {
        return getDriver().findElement(By.cssSelector("div.hub-data-pod--money.hub-data-pod--admissions.ng-binding"));
    }
    public WebElement earlyActionDeadlineText() {
        return getDriver().findElement(By.cssSelector("h2.hub-data-pod__heading.ng-binding + div div.hub-deadline__day.ng-binding"));
    }
    public WebElement earlyActionDeadlineMonthText() { return getDriver().findElement(By.cssSelector("h2.hub-data-pod__heading.ng-binding + div div.hub-deadline__month.ng-binding")); }
    public WebElement appRequirementsSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.requirementLists.length > 0\"]"));
    }
    public WebElement recommendedCoursesSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"::vm.prerequisiteCourses\"]"));
    }
    public WebElement appFactorsSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.showAdmissionFactors\"]"));
    }
    public WebElement appInformationSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.informationTabs.isAnyVisible()\"]"));
    }
    private WebElement avgNetPriceText() { return getDriver().findElement(By.cssSelector("")); }
    public WebElement feesTab() { return getDriver().findElement(By.cssSelector("div.fc-tabs__labels span[ng-if=\"vm.informationTabs.isVisible('fees')\"]")); }
    public WebElement transferApplicationFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.transferApplicationFee\"]")); }
    public WebElement transferDepositFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.transferApplicationDeposit\"]")); }
    public WebElement freshmanApplicationFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.freshmanApplicationFee\"]")); }
    public WebElement freshmanDeposit() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.freshmanApplicationDeposit\"]")); }
    public WebElement internationalApplicationFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.internationalApplicationFee\"]")); }
    public WebElement internationalDeposit() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.internationalApplicationDeposit\"]")); }
    public WebElement appFactsTab(String group) { return getDriver().findElement(By.xpath("//li[contains(text(), '" + group + "')]")); }
    public List<WebElement> appReqList(String section) { return getDriver().findElements(By.xpath("//h4[text()='" + section + "']/following-sibling::ul/li")); }


    public WebElement getAppRequirement(String group, String requirement) {
        return getDriver().findElement(By.xpath("//legend[text()='" + group + "']/following-sibling::*//strong[contains(text(), '" + requirement + "')]"));
    }
    public List<WebElement> appFactorsList(String importance) { return getDriver().findElements(By.xpath("//h4[text()='" + importance + "']/following-sibling::ul/li")); }
    public WebElement deadLineMonth(String deadLineType) { return getDriver().findElement(By.xpath("//span[text()='" + deadLineType + " Deadline']/../div/div[1]")); }
    public List<WebElement> importantPoliciesList() { return getDriver().findElements(By.cssSelector("div.hub-data-pod--policy")); }
    public List<WebElement> requiredAppReqList() { return getDriver().findElements(By.xpath("//h4[text()='Required']/following-sibling::ul/li")); }
}
