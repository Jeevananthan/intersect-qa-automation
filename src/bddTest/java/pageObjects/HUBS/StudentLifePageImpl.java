package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StudentLifePageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    public static HashMap<String, String> generatedValues;
    private HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
    private FCMainPageImpl fcMain = new FCMainPageImpl();
    private FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
    private HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
    private HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

    public StudentLifePageImpl() {
        logger = Logger.getLogger(StudentLifePageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
        assertTrue("School Size is not displayed", schoolSizeText().isDisplayed());
        assertTrue("Nearest City is not displayed", nearestCityText().isDisplayed());
        assertTrue("Ethnicity section is not displayed", ethnicityChart().isDisplayed());
        assertTrue("Gender Data section is not dislpayed", genderDataChart().isDisplayed());
        assertTrue("Age Data section is not displayed", ageDataChart().isDisplayed());
        assertTrue("Housing Data section is not displayed", housingInfo().isDisplayed());
        greekLifeTab().click();
        assertTrue("Greek Life section is not displayed", greekLifeSection().isDisplayed());
        servicesTab().click();
        assertTrue("Services section is not displayed", servicesSection().isDisplayed());
        computingResourcesTab().click();
        assertTrue("Computing Resources section is not displayed", computingResources().isDisplayed());
        organizationsTab().click();
        assertTrue("Organizations section is not displayed", organizationsSection().isDisplayed());
        athleticsTab().click();
        assertTrue("Athletics section is not displayed", athleticsSection().isDisplayed());
    }

    public HashMap<String, String> getValuesFromFields(List<List<String>> fieldList) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        waitForUITransition();
        for (List<String> field : fieldList) {
            switch (field.get(0)) {
                case "School Size" :
                    fieldValues.put(field.get(0), totalStudentsValue().getText());
                    break;
                case "Nearest City" :
                    fieldValues.put(field.get(0), nearestCityText().getText());
                    break;
                case "Ethnicity" :
                    if (Integer.parseInt(chartsPercent(field.get(1)).getText().replace("%", "")) > 90) {
                        fieldValues.put(field.get(0), Integer.toString(1));
                    } else {
                        fieldValues.put(field.get(0), chartsPercent(field.get(1)).getText().replace("%", ""));
                    }
                    break;
                case "Gender Data" :
                    fieldValues.put(field.get(0), chartsPercent(field.get(1)).getText());
                    break;
                case "Age Data" :
                    if (Integer.parseInt(chartsPercent(field.get(1)).getText().replace("%", "")) > 90) {
                        fieldValues.put(field.get(0), Integer.toString(1));
                    } else {
                        fieldValues.put(field.get(0), chartsPercent(field.get(1)).getText().replace("%", ""));
                    }
                    break;
                case "Housing Data" :
                    fieldValues.put(field.get(0), getHousingDataSectionValue(field.get(1)).getText());
                    break;
                case "Greek Life" :
                    greekLifeTab().click();
                    fieldValues.put(field.get(0), getGreekLifeSectionValue(field.get(1)).getText());
                    break;
                case "Services" :
                    String result = "";
                    servicesTab().click();
                    List<String> servicesStringsList = new ArrayList<>();
                    for (WebElement serviceElement : basicServicesList()) {
                        servicesStringsList.add(serviceElement.getText());
                    }

                    if (servicesStringsList.contains(field.get(1))) {
                        result = "yes";
                    } else {
                        result = "no";
                    }
                    fieldValues.put(field.get(0), result);
                    break;
                case "Computing Resources" :
                    computingResourcesTab().click();
                    //fieldValues.put(field.get(0), getComputerResourcesValue(field.get(1).split(";")[0],
                    //                            field.get(1).split(";")[1]).getText()); added modified step below
                    String value=" "+field.get(1).split(";")[0]+" ";
                    fieldValues.put(field.get(0), getComputerResourcesValue(value,
                            field.get(1).split(";")[1]).getText());
                    break;
                case "Organizations" :
                    String isOrgPresent = "";
                    jsClick(organizationsTab());
                    //organizationsTab().click(); added js click
                    List<String> orgListStrings = new ArrayList<>();
                    for (WebElement orgElement : driver.findElements(By.cssSelector(organizationsList))) {
                        orgListStrings.add(orgElement.getText());
                    }
                    if (orgListStrings.contains(field.get(1))) {
                        isOrgPresent = "yes";
                    } else {
                        isOrgPresent = "no";
                    }
                    fieldValues.put(field.get(0), isOrgPresent);
                    break;
                case "Athletics" :
                    //athleticsTab().click(); added js click
                    jsClick(athleticsTab());
                    athleticsInnerSection(field.get(1).split(";")[0]).click();
                    athleticsTableValue(field.get(1).split(";")[1], field.get(1).split(";")[2]);
                    fieldValues.put(field.get(0), athleticsTableValue(field.get(1).split(";")[1], field.get(1).split(";")[2]).getText());
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
                case "School Size" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(totalStudentsValue().getText().split(" ")[0].replace(",", "")));
                    break;
                case "Nearest City" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(nearestCityText().getText()));
                    break;
                case "Ethnicity" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(chartsPercent(getSectionFromDataList(sections, key)).getText().replace("%", "")));
                    break;
                case "Gender Data" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(chartsPercent(getSectionFromDataList(sections, key)).getText().replace("%", "")));
                    break;
                case "Age Data" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(chartsPercent(getSectionFromDataList(sections, key)).getText().replace("%", "")));
                    break;
                case "Housing Data" :
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(getHousingDataSectionValue(getSectionFromDataList(sections, key)).getText().replace(",", "")));
                    break;
                case "Greek Life" :
                    greekLifeTab().sendKeys(Keys.RETURN);
                    assertTrue("The value for " + key + " was not successfully generated",
                            generatedValues.get(key).equals(getGreekLifeSectionValue(getSectionFromDataList(sections, key)).getText()));
                    break;
                case "Services" :
                    servicesTab().sendKeys(Keys.RETURN);
                    List<String> servicesStringList = new ArrayList<>();
                    for (WebElement serviceElement : basicServicesList()) {
                        servicesStringList.add(serviceElement.getText());
                    }
                    for (String controlText : servicesStringList) {
                        System.out.println(controlText);
                    }
                    String control = getSectionFromDataList(sections, key);
                    System.out.println(control);
                    if (generatedValues.get(key).equals("yes")) {
                        assertTrue("The value for " + key + " was not successfully generated",
                                servicesStringList.contains(getSectionFromDataList(sections, key)));
                    } else {
                        assertFalse("The value for " + key + " was not successfully generated",
                                servicesStringList.contains(getSectionFromDataList(sections, key)));
                    }

                    break;
                case "Computing Resources" :
                    computingResourcesTab().sendKeys(Keys.RETURN);
                    assertTrue("The value for " + key + " was not successfully generated",
                            getComputerResourcesValue(getSectionFromDataList(sections, key).split(";")[0], getSectionFromDataList(sections, key).
                                    split(";")[1]).getText().equals(generatedValues.get(key)));
                    break;
                case "Organizations" :
                    organizationsTab().sendKeys(Keys.RETURN);
                    List<String> orgStringList = new ArrayList<>();
                    for (WebElement orgElement : driver.findElements(By.cssSelector(organizationsList))) {
                        orgStringList.add(orgElement.getText());
                    }
                    if (generatedValues.get(key).equals("yes")) {
                        assertTrue("The value for " + key + " was not successfully generated",
                                orgStringList.contains(getSectionFromDataList(sections, key)));
                    } else {
                        assertFalse("The value for " + key + " was not successfully generated",
                                orgStringList.contains(getSectionFromDataList(sections, key)));
                    }
                    break;
                case "Athletics" :
                    athleticsTab().sendKeys(Keys.RETURN);
                    athleticsInnerSection(getSectionFromDataList(sections, key).split(";")[0]).click();
                    assertTrue("The value for " + key + " was not successfully generated",
                            athleticsTableValue(getSectionFromDataList(sections, key).split(";")[1], getSectionFromDataList(sections, key).split(";")[2]).getText().equals(generatedValues.get(key)));
                    break;
            }
        }
    }

    private String getSectionFromDataList(List<List<String>> sections, String index) {
        String result = "";
        for (List<String> section : sections) {
            if (section.get(0).equals(index)) {
                result = section.get(1);
            }
        }
        return result;
    }

    public void verifyChangesPublishedInHUBS(String username, String password, String college, DataTable stringsDataTable) {
        List<List<String>> sections = stringsDataTable.asLists(String.class);
        List<String> creds = new ArrayList<String>() {{
            add(username);
            add(password);
        }};
        hubsLogin.defaultLogIn(creds);
        fcMain.clickCollegesTab();
        collegesPage.searchAndOpenCollege(college);
        hubsMainMenu.clickStudentLifeTab();
        for (int i = 0; i < 10; i++) {
            if (!generatedValues.get("School Size").equals(totalStudentsValue().getText().split(" ")[0].replace(",", ""))) {
                header.clickLogOut();
                hubsLogin.defaultLogIn(creds);
                fcMain.clickCollegesTab();
                collegesPage.searchAndOpenCollege(college);
                hubsMainMenu.clickStudentLifeTab();
            }
        }
        verifyGeneratedValues(sections, generatedValues);
    }
    public WebElement getHousingDataSectionValue(String section) {
        WebElement result = null;
        switch (section) {
            case "Campus Housing Capacity" : result = getOrgAndServInnerSectionText("Capacity");
                break;
            case "% Live on Campus" : result = getOrgAndServInnerSectionText("Percent living on campus:");
                break;
            case "Freshman must live on campus" : result = getOrgAndServInnerSectionText(section + ":");
                break;
            case "Sophomores must live on campus" : result = getOrgAndServInnerSectionText(section + ":");
                break;
            case "Juniors must live on campus" : result = getOrgAndServInnerSectionText(section + ":");
                break;
            case "Seniors must live on campus" : result = getOrgAndServInnerSectionText(section + ":");
                break;
        }
        return result;
    }

    public WebElement getGreekLifeSectionValue(String section) {
        WebElement result = null;
        switch (section) {
            case "# of Fraternaties" : result = getOrgAndServInnerSectionText("Number of Fraternities");
                break;
            case "# of Sororities" : result = getOrgAndServInnerSectionText("Number of Sororities");
                break;
        }
        return result;
    }

    //Locators

    public WebElement schoolSizeText() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.getSchoolSize()\"] div.student-life-tab-topbar-text"));
    }
    public WebElement totalStudentsValue() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.getSchoolSize()\"] strong"));
    }
    public WebElement nearestCityText() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.profile.nearestMajorCity\"] div.student-life-tab-topbar-text"));
    }
    public WebElement ethnicityChart() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.ethnicityData.length > 0\"]"));
    }
    public WebElement genderDataChart() {
        return getDriver().findElement(By.cssSelector("div:not(.student-body-legend__bottom)[ng-if=\"vm.genderData.length > 0\"]"));
    }
    public WebElement ageDataChart() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.ageData.length > 0\"]"));
    }
    public WebElement housingInfo() {
        return getDriver().findElement(By.cssSelector("div.student-life-housing-information__data.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-7.fc-grid__col--md-6"));
    }
    public WebElement greekLifeSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.studentLifeTabs.getActive() == 'Greek Life'\"]"));
    }
    public WebElement servicesSection() {
        return getDriver().findElement(By.cssSelector("div.fc-grid__row.fc-grid__row--xs-start.services"));
    }
    public WebElement computingResources() {
        return getDriver().findElement(By.cssSelector("table.fc-table"));
    }
    public WebElement organizationsSection() {
        return getDriver().findElement(By.cssSelector("div.fc-grid__row.fc-grid__row--xs-start.organizations"));
    }
    public WebElement athleticsSection() {
        return getDriver().findElement(By.cssSelector("div[ng-if=\"vm.studentLifeTabs.getActive() == 'Athletics'\"]"));
    }
    public WebElement greekLifeTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Greek Life')]"));
    }
    public WebElement servicesTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Services')]"));
    }
    public WebElement computingResourcesTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Computing Resources')]"));
    }
    public WebElement organizationsTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Organizations')]"));
    }
    public WebElement athleticsTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Athletics')]"));
    }
    public WebElement chartsPercent(String section) {
        String sectionTextLocator = "";
        switch (section) {
            case "% African American" : sectionTextLocator = "Black / African American";
                break;
            case "% Hispanic" : sectionTextLocator = "Hispanic / Latino";
                break;
            case "% Two Or More Races" : sectionTextLocator = "Two or more races";
                break;
            case "% Caucasian" : sectionTextLocator = "White / Caucasian";
                break;
            case "% Asian" : sectionTextLocator = "Asian";
                break;
            case "Male" : sectionTextLocator = "Male";
                break;
            case "Female" : sectionTextLocator = "Female";
                break;
            case "% Students 24 Years Old" : sectionTextLocator = "Over 24";
                break;
        }
        return getDriver().findElement(By.xpath("//div[@class='student-body-legend__key-title ng-binding'][text()='" + sectionTextLocator + "']/following-sibling::div"));
    }
    public WebElement getOrgAndServInnerSectionText(String section) { return getDriver().findElement(By.xpath("//dt[text()='" + section + ":']/following-sibling::dd")); }
    public List<WebElement> basicServicesList() { return getDriver().findElements(By.cssSelector("div.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-6.services__item.ng-binding.ng-scope")); }
    public WebElement getComputerResourcesValue(String location, String typeOfComputer) {
        int columnNumber = 0;
        if (typeOfComputer.equals("pc")) {
            columnNumber = 1;
        } else if (typeOfComputer.equals("mac")) {
            columnNumber = 2;
        }
        return getDriver().findElement(By.xpath("//td[text()='" + location + "']/following-sibling::td[" + columnNumber + "]"));
    }
    public String organizationsList = "div.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-6.organizations__item.ng-binding.ng-scope";
    public WebElement athleticsInnerSection(String tabLabel) { return getDriver().findElement(By.xpath("//span[contains(text(), '" + tabLabel + "')]")); }
    public WebElement athleticsTableValue(String sportName, String section) {
        String sectionNumber = "";
        switch (section) {
            case "Division" : sectionNumber = "1";
                break;
            case "Conference" : sectionNumber = "2";
                break;
            case "Association" : sectionNumber = "3";
                break;
        }
        return getDriver().findElement(By.xpath("//td[contains(text(), '" + sportName + "')]/following-sibling::td[" + sectionNumber + "]"));
    }
}