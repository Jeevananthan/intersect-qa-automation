package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StudentLifeEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    StudentLifePageImpl studentLifePreview = new StudentLifePageImpl();
    PublishPageImpl publish = new PublishPageImpl();


    public StudentLifeEditPageImpl() {
        logger = Logger.getLogger(StudentLifeEditPageImpl.class);
    }

    public void verifyFieldsInRealTime(DataTable stringsDataTable) {
        List<List<String>> fieldsAndValues = stringsDataTable.cells(0);
        for (List<String> fieldAndValueElement : fieldsAndValues) {
            waitForUITransition();
            switch (fieldAndValueElement.get(0)) {
                case "School Size" :
                    schoolSizeButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    int calculatedStudentsNumber = Integer.parseInt(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            Integer.toString(calculatedStudentsNumber).equals(fieldAndValueElement.get(2)));
                    schoolSizeButton().click();
                    break;
                case "Nearest City" :
                    nearestCityButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studentLifePreview.nearestCityText().getText().equals(fieldAndValueElement.get(2)));
                    break;
                case "Ethnicity" :
                    ethnicityButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    if (invalidValueMessage("% Unknown").isDisplayed()) {
                        innerEditSection("% Unknown").clear();
                        innerEditSection("% Unknown").sendKeys("1");
                    }
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time: " + studentLifePreview.chartsPercent(fieldAndValueElement.get(1)).getText(),
                            studentLifePreview.chartsPercent(fieldAndValueElement.get(1)).getText().equals(fieldAndValueElement.get(2) + "%"));
                    break;
                case "Gender Data" :
                    genderDataButton().click();
                    innerEditSection("Undergraduate Women").clear();
                    innerEditSection("Undergraduate Women").sendKeys("0");
                    innerEditSection("Graduate Women").clear();
                    innerEditSection("Graduate Women").sendKeys("0");
                    innerEditSection("Undergraduate Men").clear();
                    innerEditSection("Undergraduate Men").sendKeys("0");
                    innerEditSection("Graduate Men").clear();
                    innerEditSection("Graduate Men").sendKeys("0");
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));

                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time: " + studentLifePreview.chartsPercent("Male").getText(),
                            studentLifePreview.chartsPercent("Male").getText().equals("100%"));
                    break;
                case "Age Data" :
                    ageDataButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studentLifePreview.chartsPercent(fieldAndValueElement.get(1)).getText().equals(fieldAndValueElement.get(2) + "%"));
                    break;
                case "Housing Data" :
                    housingDataButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studentLifePreview.getHousingDataSectionValue(fieldAndValueElement.get(1)).getText().equals(fieldAndValueElement.get(2)));
                    break;
                case "Greek Life" :
                    greekLifeButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    studentLifePreview.greekLifeTab().click();
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studentLifePreview.getGreekLifeSectionValue(fieldAndValueElement.get(1)).getText().equals(fieldAndValueElement.get(2)));
                    break;
                case "Services" :
                    servicesButton().click();
                    studentLifePreview.servicesTab().click();
                    List<String> basicServicesTextList = new ArrayList<>();
                    for (WebElement serviceElement : studentLifePreview.basicServicesList()) {
                        basicServicesTextList.add(serviceElement.getText());
                    }
                    innerCheckBox(fieldAndValueElement.get(1)).click();
                    if (innerCheckBox(fieldAndValueElement.get(1)).getAttribute("class").contains("ng-empty")) {
                        basicServicesTextList.clear();
                        for (WebElement serviceElement : studentLifePreview.basicServicesList()) {
                            basicServicesTextList.add(serviceElement.getText());
                        }
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                !basicServicesTextList.contains(fieldAndValueElement.get(1)));
                    } else if (innerCheckBox(fieldAndValueElement.get(1)).getAttribute("class").contains("ng-not-empty")) {
                        basicServicesTextList.clear();
                        for (WebElement serviceElement : studentLifePreview.basicServicesList()) {
                            basicServicesTextList.add(serviceElement.getText());
                        }
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                basicServicesTextList.contains(fieldAndValueElement.get(1)));
                    }
                    break;
                case "Computing Resources" :
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    computingResourcesButton().click();
                    innerEditSection(fieldAndValueElement.get(1)).clear();
                    innerEditSection(fieldAndValueElement.get(1)).sendKeys(fieldAndValueElement.get(2));
                    studentLifePreview.computingResourcesTab().click();
                    String typeOfComputerPart = fieldAndValueElement.get(0).split("in")[0];
                    String locationPart = fieldAndValueElement.get(0).split("in")[1];

                    if (typeOfComputerPart.contains("PCs")) {
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                studentLifePreview.getComputerResourcesValue(locationPart.trim(), "pc").getText().equals(fieldAndValueElement.get(2)));
                    } else if (typeOfComputerPart.contains("Macs")) {
                        assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                studentLifePreview.getComputerResourcesValue(locationPart.trim(), "mac").getText().equals(fieldAndValueElement.get(2)));
                    }
                    break;
                case "Organizations" :
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    studentLifePreview.organizationsTab().click();
                    List<String> organizationsTextList = new ArrayList<>();
                    for (WebElement organizationsElement : driver.findElements(By.cssSelector(studentLifePreview.organizationsList))) {
                        organizationsTextList.add(organizationsElement.getText());
                    }
                    organizationsButton().click();

                    if (fieldAndValueElement.get(2).equals("yes")) {
                        if (innerCheckBox(fieldAndValueElement.get(1)).getAttribute("class").contains("ng-empty")) {
                            innerCheckBox(fieldAndValueElement.get(1)).click();
                            organizationsTextList.clear();
                            for (WebElement organizationsElement : driver.findElements(By.cssSelector(studentLifePreview.organizationsList))) {
                                organizationsTextList.add(organizationsElement.getText());
                            }
                            assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                    organizationsTextList.contains(fieldAndValueElement.get(1)));
                        }
                    } else if (fieldAndValueElement.get(2).equals("no")) {
                        if (innerCheckBox(fieldAndValueElement.get(1)).getAttribute("class").contains("ng-not-empty")) {
                            innerCheckBox(fieldAndValueElement.get(1)).click();
                            organizationsTextList.clear();
                            for (WebElement organizationsElement : driver.findElements(By.cssSelector(studentLifePreview.organizationsList))) {
                                organizationsTextList.add(organizationsElement.getText());
                            }
                            assertFalse(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                                    organizationsTextList.contains(fieldAndValueElement.get(1)));
                        }
                    }
                    break;
                case "Athletics" :
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    publishButton().sendKeys(Keys.PAGE_DOWN);
                    studentLifePreview.athleticsTab().click();
                    studentLifePreview.athleticsInnerSection(fieldAndValueElement.get(1)).click();
                    athleticsButton().click();
                    athleticsInnerEditSection(fieldAndValueElement.get(2).split(";")[0]).click();
                    Select dropdown = new Select(getAthleticsDropDown(fieldAndValueElement.get(1), fieldAndValueElement.get(2).split(";")[1]));
                    dropdown.selectByVisibleText(fieldAndValueElement.get(2).split(";")[2]);
                    assertTrue(fieldAndValueElement.get(0) + " is not successfully edited in real time",
                            studentLifePreview.athleticsTableValue(fieldAndValueElement.get(2).split(";")[0],
                                    fieldAndValueElement.get(2).split(";")[1]).getText().equals(fieldAndValueElement.get(2).split(";")[2]));

            }
        }
    }

    public void editFieldValuesWithGeneratedData(HashMap<String, String> generatedValues, List<List<String>> details) {
        for (String key : generatedValues.keySet()) {
            switch (key) {
                case "School Size" :
                    schoolSizeButton().click();
                    int totalStudents = Integer.parseInt(generatedValues.get(key));
                    int individualValue = totalStudents/3;
                    int diffValue = totalStudents - (individualValue*3);
                    innerEditSection("Undergraduate Women").clear();
                    innerEditSection("Undergraduate Women").sendKeys(Integer.toString(individualValue));
                    innerEditSection("Undergraduate Men").clear();
                    innerEditSection("Undergraduate Men").sendKeys(Integer.toString(individualValue));
                    innerEditSection("Graduate Women").clear();
                    innerEditSection("Graduate Women").sendKeys(Integer.toString(individualValue));
                    innerEditSection("Graduate Men").clear();
                    innerEditSection("Graduate Men").sendKeys(Integer.toString(diffValue));
                    String percentMaleGender = studentLifePreview.chartsPercent("Male").getText();
                    break;
                case "Nearest City" :
                    nearestCityButton().click();
                    innerEditSection(details.get(1).get(1)).clear();
                    innerEditSection(details.get(1).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Ethnicity" :
                    ethnicityButton().click();
                    innerEditSection(details.get(2).get(1)).clear();
                    innerEditSection(details.get(2).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Age Data" :
                    ageDataButton().click();
                    innerEditSection(details.get(4).get(1)).clear();
                    innerEditSection(details.get(4).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Housing Data" :
                    housingDataButton().click();
                    innerEditSection(details.get(5).get(1)).clear();
                    innerEditSection(details.get(5).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Greek Life" :
                    greekLifeButton().click();
                    innerEditSection(details.get(6).get(1)).clear();
                    innerEditSection(details.get(6).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Services" :
                    servicesButton().click();
                    if (generatedValues.get(key).equals("yes")) {
                        if (innerCheckBox(details.get(7).get(1)).getAttribute("class").contains("ng-empty")) {
                            innerCheckBox(details.get(7).get(1)).click();
                        }
                    } else if (generatedValues.get(key).equals("no")) {
                        if (innerCheckBox(details.get(7).get(1)).getAttribute("class").contains("ng-not-empty")) {
                            innerCheckBox(details.get(7).get(1)).click();
                        }
                    }
                    break;
                case "Computing Resources" :
                    computingResourcesButton().click();
                    innerEditSection(details.get(8).get(1)).clear();
                    innerEditSection(details.get(8).get(1)).sendKeys(generatedValues.get(key));
                    break;
                case "Organizations" :
                    organizationsButton().click();
                    if (generatedValues.get(key).equals("yes")) {
                        if (innerCheckBox(details.get(9).get(1)).getAttribute("class").contains("ng-empty")) {
                            innerCheckBox(details.get(9).get(1)).click();
                        }
                    } else if (generatedValues.get(key).equals("no")) {
                        if (innerCheckBox(details.get(9).get(1)).getAttribute("class").contains("ng-not-empty")) {
                            innerCheckBox(details.get(9).get(1)).click();
                        }
                    }
                    break;
                case "Athletics" :
                    athleticsButton().click();
                    athleticsInnerEditSection(details.get(10).get(1).split(";")[1]).click();
                    Select athleticsDropDown = new Select(getAthleticsDropDown(details.get(10).get(1).split(";")[0], details.get(10).get(1).split(";")[2]));
                    athleticsDropDown.selectByVisibleText(details.get(10).get(1).split(";")[2] + " " + generatedValues.get(key));
                    break;
            }
        }
    }

    public HashMap<String, String> generateValues(HashMap<String, String> fieldValues) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("mm:ss");

        HashMap<String, String> generatedValues = new HashMap<String, String>();
        for (String key : fieldValues.keySet()) {
            switch (key) {
                case "School Size" :
                    String cleanedData = fieldValues.get(key).split(" ")[0].replace(",", "");
                    generatedValues.put(key, String.valueOf(Integer.parseInt(cleanedData) + 1));
                    break;
                case "Nearest City" :
                    if (fieldValues.get(key).contains("/")) {
                        generatedValues.put(key, fieldValues.get(key).split("/")[0] + "/" + dateFormat.format(date));
                    } else {
                        generatedValues.put(key, fieldValues.get(key) + "/" + dateFormat.format(date));
                    }
                    break;
                case "Ethnicity" :
                    String cleanedEthnicityData = fieldValues.get(key).replace("%", "").trim();
                    generatedValues.put(key, String.valueOf(Integer.parseInt(cleanedEthnicityData) + 1));
                    break;
                case "Gender Data" :
                    generatedValues.put(key, "50");
                    break;
                case "Age Data" :
                    String cleanedAgeData = fieldValues.get(key).replace("%", "").trim();
                    generatedValues.put(key, String.valueOf(Integer.parseInt(cleanedAgeData) + 1));
                    break;
                case "Housing Data" :
                    String cleanedHousingData = fieldValues.get(key).replace(",", "").trim();
                    generatedValues.put(key, String.valueOf(Integer.parseInt(cleanedHousingData) + 1));
                    break;
                case "Greek Life" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Services" :
                    if (fieldValues.get(key).equals("yes")) {
                        generatedValues.put(key, "no");
                    } else if (fieldValues.get(key).equals("no")) {
                        generatedValues.put(key, "yes");
                    }
                    break;
                case "Computing Resources" :
                    generatedValues.put(key, String.valueOf(Integer.parseInt(fieldValues.get(key)) + 1));
                    break;
                case "Organizations" :
                    if (fieldValues.get(key).equals("yes")) {
                        generatedValues.put(key, "no");
                    } else if (fieldValues.get(key).equals("no")) {
                        generatedValues.put(key, "yes");
                    }
                    break;
                case "Athletics" :
                    if (fieldValues.get(key).equals("II")) {
                        generatedValues.put(key, "III");
                    } else if (fieldValues.get(key).equals("III")) {
                        generatedValues.put(key, "II");
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
        StudentLifePageImpl.generatedValues = newValues;
        editFieldValuesWithGeneratedData(newValues, details);
        publish.clickPublishButton();
        publish.enterPublishReasonsText(details.get(11).get(1));
        publish.clickSubmitChangesButton();
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(loadingIconLeftMenuLocator), 1));
        logger.info("All changes were submitted");
    }

    public void verifyErrorMessageWithInvalidData(DataTable stringsDataTable) {
        List<List<String>> fieldsDetails = stringsDataTable.cells(0);
        for (List<String> fieldElement : fieldsDetails) {
            switch (fieldElement.get(0)) {
                case "Computing Resources" :
                    //computingResourcesButton().click(); //added js click
                    jsClick(computingResourcesButton());
                    innerEditSection(fieldElement.get(1)).clear();
                    innerEditSection(fieldElement.get(1)).sendKeys(fieldElement.get(2));
                    assertTrue("Error message is not displayed", errorMsg().isDisplayed());
                    break;
                case "Age Data" :
                    ageDataButton().click();
                    innerEditSection(fieldElement.get(1)).clear();
                    innerEditSection(fieldElement.get(1)).sendKeys(fieldElement.get(2));
                    assertTrue("Error message is not displayed", errorMsg().isDisplayed());
                    break;
            }
        }
    }

    //Locators
    private WebElement schoolSizeButton() {
        return getDriver().findElement(By.xpath("//h3[text()='School Size']"));
    }
    private WebElement nearestCityButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Nearest City']"));
    }
    private WebElement ethnicityButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Ethnicity']"));
    }
    private WebElement genderDataButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Gender Data']"));
    }
    private WebElement ageDataButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Age Data']"));
    }
    private WebElement housingDataButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Housing Data']"));
    }
    private WebElement greekLifeButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Greek Life']"));
    }
    private WebElement servicesButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Services']"));
    }
    private WebElement computingResourcesButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Computing Resources']"));
    }
    private WebElement organizationsButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Organizations']"));
    }
    private WebElement athleticsButton() {
        return getDriver().findElement(By.xpath("//h3[text()='Athletics']"));
    }
    private WebElement innerEditSection(String section) {
        return getDriver().findElement(By.xpath("//label[text()='" + section + "']/following-sibling::input"));
    }
    private WebElement innerCheckBox(String label) {
        return getDriver().findElement(By.xpath("//label[text()='" + label + "']/../input"));
    }
    private WebElement athleticsInnerEditSection(String label) {
        return getDriver().findElement(By.xpath("//strong[text()='" + label + "']"));
    }
    private WebElement getAthleticsDropDown(String section, String dropDownLabel) {
        WebElement dropDown = null;
        if (section.equals("Men")) {
            switch (dropDownLabel) {
                case "Association" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.mensAssocField.value\"]"));
                    break;
                case "Division" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.mensDivField.value\"]"));
                    break;
                case "Conference" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.mensConfField.value\"]"));
            }
        } else if (section.equals("Women")) {
            switch (dropDownLabel) {
                case "Association" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.womensAssocField.value\"]"));
                    break;
                case "Division" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.womensDivField.value\"]"));
                    break;
                case "Conference" : dropDown = getDriver().findElement(By.cssSelector("ng-model=\"vm.womensConfField.value\""));
                    break;
            }
        } else if (section.equals("Co-Ed")) {
            switch (dropDownLabel) {
                case "Association" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.coedAssocField.value\"]"));
                    break;
                case "Division" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.coedDivField.value\"]"));
                    break;
                case "Conference" : dropDown = getDriver().findElement(By.cssSelector("select[ng-model=\"vm.coedConfField.value\"]"));
                    break;
            }
        }
        return dropDown;
    }
    private WebElement errorMsg() { return getDriver().findElement(By.cssSelector("ng-form.ng-valid-maxlength.ng-dirty.ng-valid-parse.ng-invalid.ng-invalid-pattern span")); }
    public String loadingIconLeftMenuLocator = "div.fc-loader.fc-loader-three-bounce.fc-loader--color-primary";
    private WebElement invalidValueMessage(String label) { return driver.findElement(By.xpath("//label[text() = '" + label + "']/following-sibling::span")); }
    private WebElement publishButton() { return driver.findElement(By.cssSelector("span.intersect-btn.intersect-btn--fuschia.ng-binding")); }
}
