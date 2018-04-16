package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
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

    public HashMap<String, String> getValuesFromFields(List<List<String>> fieldList) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        for (List<String> field : fieldList) {
            switch (field.get(0)) {
                case "Acceptance Rate" :
                    fieldValues.put(field.get(0), acceptanceRateText().getText());
                    break;
                case "Important Policies" :
                    //This detects the presence of "Wait List" in the
                    //"Important" policies column
                    String isPolicyPresent = "not present";
                    for (WebElement policy : importantPoliciesList()) {
                        if (policy.getText().equals("Wait List")) {
                            isPolicyPresent = "present";
                        }
                        break;
                    }
                    fieldValues.put(field.get(0), isPolicyPresent);
                    break;
                case "Deadlines" :
                    //This tales the value of the month of the Early Action Deadline
                    fieldValues.put(field.get(0), earlyActionDeadlineMonthText().getText());
                    break;
                case "Fees" :
                    //This takes the value of the Freshman Application Fee
                    feesTab().click();
                    fieldValues.put(field.get(0), freshmanApplicationFee().getText().replace("$", ""));
                    break;
                case "Application Requirements" :
                    //This detects the presence of the "Campus Visit" item in the "Required" list
                    String isCampusVisitPresent = "not present";
                    for (WebElement appReqRequired: requiredAppReqList()) {
                        if (appReqRequired.getText().equals("Campus Visit")) {
                            isCampusVisitPresent = "present";
                            break;
                        }
                    }
                    fieldValues.put(field.get(0), isCampusVisitPresent);
                    break;
                case "Recommended Courses" :
                    //This takes the "Years Required" for "English"
                    String yearsRequired = getCourseYears("English", "Years Required").getText();
                    fieldValues.put(field.get(0), yearsRequired);
                    break;
                case "Application Factors" :
                    //This detects the presence of the "Class Rank" item in the "Important" application factors list
                    String isAppFactorPresent = "not present";
                    for (WebElement appFactor : appFactorsList("Important")) {
                        if (appFactor.getText().equals("Class Rank")) {
                            isAppFactorPresent = "present";
                            break;
                        }
                    }
                    fieldValues.put(field.get(0), isAppFactorPresent);
                    break;
            }
        }
        logger.info("\nValues from the page: \n");
        for (String key : fieldValues.keySet()) {
            logger.info(key + " : " + fieldValues.get(key) + "\n");
        }
        return fieldValues;
    }

//    private void verifyGeneratedValues(List<List<String>> sections, HashMap<String, String> generatedValues) {
//
//        for (int i = 0; i < 20; i++) {
//            if (!generatedValues.get("Average Net Prices").equals(avgNetPriceText().getText().replace(",", ""))) {
//                String whatever = avgNetPriceText().getText();
//                logger.info(generatedValues.get("Average Net Prices"));
//                logger.info(whatever.replace(",", ""));
//                Select avgNetPricesDropdown = new Select(avgNetPriceDropDown());
//                avgNetPricesDropdown.selectByVisibleText(getDropDownOption(sections.get(0).get(1)));
//                getDriver().get(getDriver().getCurrentUrl());
//            }
//        }
//
//        for (String key : generatedValues.keySet()) {
//            switch (key) {
//                case "Average Net Prices" :
//                    Select avgNetPricesDropdown = new Select(avgNetPriceDropDown());
//                    avgNetPricesDropdown.selectByVisibleText(getDropDownOption(sections.get(0).get(1)));
//                    System.out.println("cosa: " + generatedValues.get(key));
//                    System.out.println("cosa: " + avgNetPriceText().getText());
//                    assertTrue("The value for " + key + " was not successfully generated",
//                            generatedValues.get(key).equals(avgNetPriceText().getText().replace(",", "")));
//                    break;
//                case "% Receiving Aid" :
//                    Select receivingAidDropdown = new Select(receivingAidDropDown());
//                    receivingAidDropdown.selectByVisibleText(sections.get(1).get(1));
//                    assertTrue("The value for " + key + " was not successfully generated",
//                            generatedValues.get(key).equals(percentReceivingAidText().getText()));
//                    break;
//                case "Average Amount of Aid" :
//                    avgAmountOfAidButton(sections.get(2).get(1)).sendKeys(Keys.RETURN);
//                    assertTrue("The value for " + key + " was not successfully generated",
//                            generatedValues.get(key).equals(avgGrantAmountText().getText().replace(",", "")));
//                    break;
//            }
//        }
//    }

    public void verifyChangesPublishedInHUBS(String username, String password, String college, DataTable stringsDataTable) {
        while (!generatedValues.get("Acceptance Rate").equals(acceptanceRateText().getText())) {
            getDriver().get(getDriver().getCurrentUrl());
        }

        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "Acceptance Rate" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(acceptanceRateText().getText()));
                    break;
                case "Important Policies" :
                    List<String> importantPoliciesStrings = new ArrayList<>();
                    for (WebElement importantPolicy : importantPoliciesList()) {
                        importantPoliciesStrings.add(importantPolicy.getText());
                    }
                    if (generatedValues.get(key).equals("present")) {
                        assertTrue("The policy 'Wait List' was not successfully added to the important policies",
                                importantPoliciesStrings.contains("Wait List"));
                    } else if (generatedValues.get(key).equals("not present")) {
                        assertFalse("The policy 'Wait List' is present in important policies, when it shouldn't",
                                importantPoliciesStrings.contains("Wait List"));
                    }
                    break;
                case "Deadlines" :
                    assertTrue("The 'Early Action Deadline' was not modified successfully",
                            generatedValues.get(key).equals(earlyActionDeadlineMonthText().getText()));
                    break;
                case "Fees" :
                    assertTrue("Freshman Application Fee was not updated successfully",
                            generatedValues.get(key).equals(freshmanApplicationFee().getText()));
                    break;
                case "Application Requirements" :
                    List<String> appReqStrings = new ArrayList<>();
                    for (WebElement appReq : requiredAppReqList()) {
                        appReqStrings.add(appReq.getText());
                    }
                    if (generatedValues.get(key).equals("present")) {
                        assertTrue("'Campus Visit' was not added to the 'Required' list",
                                appReqStrings.contains("Campus Visit"));
                    } else if (generatedValues.get(key).equals("not present")) {
                        assertFalse("'Campus Visit' was added to the 'Important' list, when it shouldn't",
                                appReqStrings.contains("Campus Visit"));
                    }
                    break;
                case "Recommended Courses" :
                    assertTrue("'Years Required' field for 'English' was not sucessfully updated",
                            getCourseYears("English", "Years Required").getText().equals(generatedValues.get(key)));
                    break;
                case "Application Factors" :
                    List<String> appFactorsStrings = new ArrayList<>();
                    for (WebElement appFactor : appFactorsList("Important")) {
                        appFactorsStrings.add(appFactor.getText());
                    }
                    if (generatedValues.get(key).equals("present")) {
                        assertTrue("'Class Rank' was not added to te 'Important' list",
                                appFactorsStrings.contains("Class Rank"));
                    } else if (generatedValues.get(key).equals("not present")) {
                        assertFalse("'Class Rank' isn in the 'Important' list, when it shouldn't",
                                appFactorsStrings.contains("Class Rank"));
                    }
                    break;
            }
        }
    }

    public String getDropDownOption(String avgNetPriceRange) {
        String option = "";
        switch (avgNetPriceRange) {
            case "$0 - $30,000" : option = "$0 - $30K";
                break;
            case "$30,001 - $48,000" : option = "$30 - $48K";
                break;
            case "$48,001 - $75,000" : option = "$48 - $75K";
                break;
            case "$75,001 - $110,000" : option = "$75 - $110K";
                break;
            case "$110,001+" : option = " > $110K";
                break;
        }
        return option;
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
    public WebElement feesTab() { return getDriver().findElement(By.cssSelector("div.fc-tabs__labels span[ng-if=\"vm.informationTabs.isVisible('fees')\"]")); }
    public WebElement transferApplicationFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.transferApplicationFee\"]")); }
    public WebElement transferDepositFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.transferApplicationDeposit\"]")); }
    public WebElement freshmanApplicationFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.freshmanApplicationFee\"]")); }
    public WebElement freshmanDeposit() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.freshmanApplicationDeposit\"]")); }
    public WebElement internationalApplicationFee() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.internationalApplicationFee\"]")); }
    public WebElement internationalDeposit() { return getDriver().findElement(By.cssSelector("dd[ng-if=\"vm.fees.internationalApplicationDeposit\"]")); }
    public WebElement appFactsTab(String group) { return getDriver().findElement(By.xpath("//li[text()='" + group + "']")); }
    public List<WebElement> appReqList(String section) { return getDriver().findElements(By.xpath("//h4[text()='" + section + "']/following-sibling::ul/li")); }


    public WebElement getAppRequirement(String group, String requirement) {
        return getDriver().findElement(By.xpath("//legend[text()='" + group + "']/following-sibling::entity-collection/div/div/div/div/strong[text()='" + requirement + "']"));
    }
    public List<WebElement> appFactorsList(String importance) { return getDriver().findElements(By.xpath("//h4[text()='" + importance + "']/following-sibling::ul/li")); }
    public WebElement appInfoEarlyDeadLineMonth() { return getDriver().findElement(By.xpath("//span[text()='Early Action Deadline']/../div/div[1]")); }
    public List<WebElement> importantPoliciesList() { return getDriver().findElements(By.cssSelector("div.hub-data-pod--policy")); }
    public List<WebElement> requiredAppReqList() { return getDriver().findElements(By.xpath("//h4[text()='Required']/following-sibling::ul/li")); }
}
