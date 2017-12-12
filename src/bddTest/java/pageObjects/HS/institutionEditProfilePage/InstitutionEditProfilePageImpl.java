package pageObjects.HS.institutionEditProfilePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstitutionEditProfilePageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public InstitutionEditProfilePageImpl() {
        logger = Logger.getLogger(InstitutionEditProfilePageImpl.class);
    }

    public void editInstitutionProfile() {
        if (link("edit").isDisplayed()){
            link("edit").click();
            waitUntilPageFinishLoading();
        }
        else
            Assert.fail("There is no edit institution profile button present");
    }

    public void fillAndInteract(String action, DataTable dataTable){
        waitUntilPageFinishLoading();
        logger.info("Filling in institution profile information.");
        System.out.println();
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for (String key : data.keySet()) {
            switch (key) {
                case "Country":
                    driver.findElement(By.name("country")).click();
                    driver.findElement(By.cssSelector("input[class='search']")).click();
                    driver.findElement(By.cssSelector("input[class='search']")).sendKeys(data.get(key));
                    break;
                case "Charter School":
                    WebElement drpCharterSchool = driver.findElement(By.id("charterSchool"));
                    drpCharterSchool.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpCharterSchool.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + data.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "Coeducational":
                    WebElement drpCoeducational = driver.findElement(By.id("coeducational"));
                    drpCoeducational.click();
                    waitUntilPageFinishLoading();
                    drpCoeducational.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + data.get(key)+"')]")).click();
                    waitUntilPageFinishLoading();
                    break;
                case "Highest Grade":
                    WebElement drpHighestGrade = driver.findElement(By.id("highestGrade"));
                    drpHighestGrade.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpHighestGrade.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//div [@id='highestGrade']/div/div/span[contains(text(),'" + data.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "Lowest Grade":
                    WebElement drpLowestGrade = driver.findElement(By.id("lowestGrade"));
                    drpLowestGrade.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpLowestGrade.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//div [@id='lowestGrade']/div/div/span[contains(text(),'" + data.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "School Level":
                    WebElement drpSchoolLevel = driver.findElement(By.id("schoolLevel"));
                    drpSchoolLevel.click();
                    waitUntilPageFinishLoading();
                    drpSchoolLevel.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + data.get(key)+"')]")).click();
                    waitUntilPageFinishLoading();
                    break;
                case "School Type Options":
                    WebElement drpSchoolType = driver.findElement(By.id("schoolPublicType"));
                    drpSchoolType.click();
                    waitUntilPageFinishLoading();
                    drpSchoolType.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + data.get(key)+"')]")).click();
                    waitUntilPageFinishLoading();
                    break;
                case "Title I Eligible":
                    WebElement drpEligible = driver.findElement(By.id("titleEligible"));
                    drpEligible.click();
                    waitUntilPageFinishLoading();
                    drpEligible.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + data.get(key)+"')]")).click();
                    waitUntilPageFinishLoading();
                    break;
                case "Title I Status":
                    WebElement drpStatus = driver.findElement(By.id("titleStatus"));
                    drpStatus.click();
                    waitUntilPageFinishLoading();
                    //TODO - This doesn't work for all values of this dropdown, for unknown reasons.  "Unknown", in particular, does not work.
                    jsClick(drpStatus.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + data.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                default:
                    textbox(key).sendKeys(data.get(key));
                    break;
            }
        }
        switch (action) {
            case "Cancel":
                cancelButton().click();
                break;
            case "Save":
                saveChanges().click();
                break;
        }
        waitUntilPageFinishLoading();
    }

    public void navigateToInstitutionProfile() {
        waitUntilPageFinishLoading();
        communityFrame();
        link("institution").click();
    }

    public void noInstitutionProfileEditButton(){
        Assert.assertFalse("Institution profile has and edit button option.", link("edit").isDisplayed());
    }

    public void verifyDataSaved(DataTable dataTable) {
        waitUntilPageFinishLoading();
        logger.info("Verifing updated data saved in institution profile information.");
        System.out.println();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            switch (key) {
                case "Country":
                    WebElement verifyCountry = driver.findElement(By.id("country"));
                    Assert.assertEquals("Country data did not save on update", data.get(key), verifyCountry.findElement(By.className("text")).getText());
                    break;
                case "Charter School":
                    waitUntilPageFinishLoading();
                    WebElement verifyCharterSchool = driver.findElement(By.id("charterSchool"));
                    Assert.assertEquals("Charter School data did not save on update", data.get(key), verifyCharterSchool.findElement(By.className("text")).getText());
                    break;
                case "Coeducational":
                    WebElement verifyCoeducational = driver.findElement(By.id("coeducational"));
                    Assert.assertEquals("Coeducational data did not save on update", data.get(key), verifyCoeducational.findElement(By.className("text")).getText());
                    break;
                case "Highest Grade":
                    WebElement verifyHighestGrade = driver.findElement(By.id("highestGrade"));
                    Assert.assertEquals("Highest Grade data did not save on update", data.get(key), verifyHighestGrade.findElement(By.className("text")).getText());
                    break;
                case "Lowest Grade":
                    WebElement verifyLowestGrade = driver.findElement(By.id("lowestGrade"));
                    Assert.assertEquals("Lowest Grade data did not save on update", data.get(key), verifyLowestGrade.findElement(By.className("text")).getText());
                    break;
                case "School Level":
                    WebElement verifySchoolLevel = driver.findElement(By.id("schoolLevel"));
                    Assert.assertEquals("School Level data did not save on update", data.get(key), verifySchoolLevel.findElement(By.className("text")).getText());
                    break;
                case "School Type Options":
                    WebElement verifySchoolType = driver.findElement(By.id("schoolPublicType"));
                    Assert.assertEquals("School Type data did not save on update", data.get(key), verifySchoolType.findElement(By.className("text")).getText());
                    break;
                case "Title I Eligible":
                    WebElement verifyTitleEligible = driver.findElement(By.id("titleEligible"));
                    Assert.assertEquals("Title I Eligible data did not save on update", data.get(key), verifyTitleEligible.findElement(By.className("text")).getText());
                    break;
                case "Title I Status":
                    WebElement verifyTitleStatus = driver.findElement(By.id("titleStatus"));
                    Assert.assertEquals("Title I Status data did not save on update", data.get(key), verifyTitleStatus.findElement(By.className("text")).getText());
                    break;
                default:
                    Assert.assertEquals(key + " data did not save on update", data.get(key), textbox(key).getAttribute("value"));
                    break;
            }
        }
    }

    public void verifyDropdownListCompleteAndSorted(String dropDownField, DataTable dataTable){
        waitUntilPageFinishLoading();
        logger.info("Verifying that the " + dropDownField + " dropdown list is a complete list and sorted.");
        List<String> list = dataTable.asList(String.class);
        List<String> dropdownList = getAllDropdownDivOptions(dropDownField);
        Assert.assertEquals("Dropdown options did not match the expected list.",list, dropdownList);
    }

    public void verifyHeaderExist(String header){
        logger.info("Verifying that the " + header + " header is visible.");
        Assert.assertTrue("Institution header " + header + " was not found.", getParent(driver.findElement(By.xpath("//span[contains(text(),'" + header + "')]"))).getTagName().contains("h"));
    }

    public void verifyInstitutionProfileFieldsDoNotExist(DataTable dataTable){
        waitUntilPageFinishLoading();
        logger.info("Verifying institution profile fields do not exist.");
        List<String> list = dataTable.asList(String.class);
        for (String institutionFields : list) {
            Assert.assertFalse("Institution profile " + institutionFields + " field was not found.", textbox(""+ institutionFields).isDisplayed());
        }
    }

    public void verifyInstitutionProfileFieldsExist(DataTable dataTable){
        waitUntilPageFinishLoading();
        logger.info("Verifying institution profile fields exist.");
        List<String> list = dataTable.asList(String.class);
        for (String institutionFields : list) {
            Assert.assertTrue("Institution profile " + institutionFields + " field was not found.", textbox(""+ institutionFields).isDisplayed());
        }
    }

    private List<String> getAllDropdownOptions(By by){
        List<String> allOptions = new ArrayList<>();
        WebElement drop_down = driver.findElement(by);
        List<WebElement> options = drop_down.findElements(By.xpath(".//div[@role='option']/span"));
        for (WebElement option:options) {
            allOptions.add(option.getAttribute("innerText"));
        }
        return allOptions;
    }

    private List<String> getAllDropdownDivOptions(String grade){
        List<String> allOptions = new ArrayList<>();
        List<WebElement> lowestGrades = driver.findElements(By.cssSelector("div[name='" + grade + "']>div[class='menu transition']>div"));
        for(WebElement opt : lowestGrades) {
            String value = getLabelText(opt);
            if(!value.isEmpty())
                allOptions.add(value);
        }

        return allOptions;
    }


    public void verifyPlaceholdersByID(String action, DataTable dataTable){
        waitUntilPageFinishLoading();
        logger.info("\nVerifying field placeholders.");
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for (String key : data.keySet()) {
            Assert.assertEquals("Institution profile " + key + "field placeholder is incorrect or missing.", data.get(key), driver.findElement(By.id(key)).getAttribute("placeholder"));
        }

        switch (action) {
            case "Cancel":
                cancelButton().click();
                break;
            case "Save":
                saveChanges().click();
                break;
        }
        waitUntilPageFinishLoading();
    }
    private void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();",element);
    }
    private String getLabelText(WebElement opt) {
        return opt.getAttribute("innerText");
    }
    private WebElement saveChanges() {
        return button("SAVE");
    }
    private WebElement cancelButton() {
        return button("CANCEL");
    }
}