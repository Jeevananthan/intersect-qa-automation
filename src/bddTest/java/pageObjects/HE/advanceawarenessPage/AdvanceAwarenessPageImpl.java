package pageObjects.HE.advanceawarenessPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvanceAwarenessPageImpl extends PageObjectFacadeImpl {

    private Logger logger;


    public AdvanceAwarenessPageImpl() {
        logger = Logger.getLogger(AdvanceAwarenessPageImpl.class);
    }

    public void configureSubscription(String configuresubscription) {
        configureSub(configuresubscription).click();
    }
    public void selectAdvanceAwarenessMenuOption(String menuOption){
        waitUntilPageFinishLoading();
        selectMenuOption(menuOption).click();
        waitUntilPageFinishLoading();

    }

    public void selectDiversityOptions(DataTable diversityOptions){
        List<String> ethnicities = diversityOptions.asList(String.class);
        for (String  eachOption : ethnicities) {
            if(!checkBoxDiversityChecked(eachOption).isSelected())
                diversityEthnicityCheckBox(eachOption).click();
    }}

    public void unselectDiversityOptions(DataTable diversityOptions){
        List<String> ethnicities = diversityOptions.asList(String.class);
        for (String  eachOption : ethnicities) {
            if(checkBoxDiversityChecked(eachOption).isSelected())
            diversityEthnicityCheckBox(eachOption).click();
            }}


    public void verifyDiversityOptionschecked(DataTable listOfDiversityCheckBoxes){
        List<String> ethnicity = listOfDiversityCheckBoxes.asList(String.class);
        for (String  eachOption : ethnicity) {
           Assert.assertTrue( checkBoxDiversityChecked(eachOption).isSelected());
    }}

    public void VerifyDiversityOptionUnChecked(DataTable ListofUncheckedDiversityBoxes){
        List<String> ethnicity = ListofUncheckedDiversityBoxes.asList(String.class);
        for (String  eachOption : ethnicity) {
            Assert.assertFalse(checkBoxDiversityChecked(eachOption).isSelected());

    }}
    public void verifyAudienceData (DataTable dataTable){
        String audience="";
        String competitors="";

        List<List<String>> details = dataTable.asLists(String.class);
        for (List<String> row : details){
            switch (row.get(0)) {
                case "Audience" :
                    audience = row.get(1);
                    break;
                case "Competitors" :
                    competitors = row.get(1);
                    break;
            }
        }
        Assert.assertTrue("The Audience are not displayed in HE App ", getDriver().findElement(By.xpath(audianceName(audience))).isDisplayed());

    }

    public void checkCompetitorsAreInAlphabeticalOrder(){
       List<String> actualCompetitorsOrder = new ArrayList<>();
        for (WebElement competitor: getCompetitors()) {
            actualCompetitorsOrder.add(competitor.getText());
        }
       List<String> expectedCompetitorsOrder = actualCompetitorsOrder;
        java.util.Collections.sort(expectedCompetitorsOrder);
        Assert.assertTrue(Arrays.equals(actualCompetitorsOrder.toArray(),expectedCompetitorsOrder.toArray()));
    }

    public void checkEchCompetitorContainsOption(String option){
        for (WebElement competitor: getCompetitors()) {
          Assert.assertTrue(competitor.findElement(getOption(option)).isDisplayed());
        }
    }

    public void checkEachMajorContainsOption(String option){
        for (WebElement competitor: getCompetitors()) {
            Assert.assertTrue(competitor.findElement(getOption(option)).isDisplayed());
        }
    }

    public void selectCheckbox(String option){
       waitForUITransition();
        getCompetitors().get(0).findElement(getOption(option)).click();
    }

    public void setCompetitorMessage(String message){
        getCompetitors().get(0).findElement(textFieldForCOmpatitorMessage()).sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),message);
    }

    public void clickOnSaveButton(){
        waitUntil(ExpectedConditions.visibilityOf(saveButton()));
        saveButton().click();
    }

    //locators

    private WebElement configureSub(String clickConfigure){
        return driver.findElement(By.xpath("//div/h2[text()='"+clickConfigure +"']/../div/a"));
    }
    private WebElement selectMenuOption(String clickMenuOption){
        return driver.findElement(By.xpath("//ul[@class='ui vertical third menu']/li/a[@class='menu-link']/span[text()='" + clickMenuOption +"']"));
    }
    private WebElement diversityEthnicityCheckBox(String locator){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(getDiversityEthnicity(locator))));
        return driver.findElement(By.xpath(getDiversityEthnicity(locator)));

    }
    private WebElement checkBoxDiversityChecked(String enthicity) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(getDiversityEthnicity(enthicity))));
        return driver.findElement(By.xpath(getCheckboxDiversity(enthicity)));
    }

    String getDiversityEthnicity(String enthicity){
        return  "//label[text()='" + enthicity + "']";
    }

    String getCheckboxDiversity(String enthicity){
        return "//label[text()='" + enthicity + "']/../input" ;
    }

    private String  audianceName(String subName){
        return "//table [@class='ui table connections-table']/tbody/tr/td/span/div[text()='" + subName + "']";
    }

    private  List <WebElement> getCompetitors(){
        return getDriver().findElements(By.className("_2NX7sEemExJO2064BSLXHB"));
    }

    private By getOption(String option){
        return By.xpath("..//label[text()='" + option + "']");
    }

    private By textFieldForCOmpatitorMessage(){
       return By.xpath("..//textarea");
    }

    private WebElement saveButton(){
        return getDriver().findElement(By.xpath("//button[@class='ui primary button'] | //button[@class='ui teal primary button']"));
    }
}