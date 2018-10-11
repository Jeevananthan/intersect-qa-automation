package pageObjects.HE.advanceawarenessPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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



    //locators

    private WebElement configureSub(String clickConfigure){
        return driver.findElement(By.xpath("//div[text()='"+clickConfigure +"']/../div/a"));
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

}