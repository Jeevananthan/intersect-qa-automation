package pageObjects.HE.advanceawarenessPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.io.File;
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

    }

    public void selectDiversityOptions(DataTable diversityOptions){
        List<String> ethnicities = diversityOptions.asList(String.class);
        for (String  eachOption : ethnicities) {
            switch (eachOption) {
                case "Asian":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "Hispanic/Latino of any race":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "Multiracial":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "American Indian or Alaskan Native":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "Black or African American":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "Filipino":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "Native Hawaiian or Other Pacific Islander":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;
                case "All Other Racial/Ethnic Minority Groups":
                    diversityEthnicityCheckBox(eachOption).click();
                    break;


    }}}

    public void verifyDiversityOptionschecked(DataTable listOfDiversityCheckBoxes){
        List<String> ethnicity = listOfDiversityCheckBoxes.asList(String.class);
        for (String  eachOption : ethnicity) {
            switch (eachOption) {
                case "Asian":
                    Assert.assertTrue("Asian Ethnicity is unChecked", checkBoxDiversityChecked(eachOption).isSelected());
                    break;
                case "Hispanic/Latino of any race":
                    Assert.assertTrue("Hispanic Ethnicity is unChecked", checkBoxDiversityChecked(eachOption).isSelected());
                    break;

    }}}

    public void VerifyDiversityOptionUnChecked(DataTable ListofUncheckedDiversityBoxes){
        List<String> ethnicity = ListofUncheckedDiversityBoxes.asList(String.class);
        for (String  eachOption : ethnicity) {
            switch (eachOption) {
                case "Multiracial":
                    Assert.assertFalse("Multiracial Ethnicity is Checked", checkBoxDiversityChecked(eachOption).isSelected());
                    break;


    }}}



    //locators

    private WebElement configureSub(String clickConfigure){
        return driver.findElement(By.xpath("//div[text()='"+clickConfigure +"']/../div/a"));
    }
    private WebElement selectMenuOption(String clickMenuOption){
        return driver.findElement(By.xpath("//ul[@class='ui vertical third menu']/li/a[@class='menu-link']/span[text()='" + clickMenuOption +"']"));
    }
    private WebElement diversityEthnicityCheckBox(String enthicity){
        return driver.findElement(By.xpath("//label[text()='" + enthicity + "']"));

    }
    private WebElement checkBoxDiversityChecked(String enthicity) {
        return driver.findElement(By.xpath("//label[text()='" + enthicity + "']/../input"));
    }

}