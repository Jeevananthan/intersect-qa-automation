package pageObjects.SM.resourcesPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class ResourcesImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public ResourcesImpl() {
        logger = Logger.getLogger(ResourcesImpl.class);
    }

    //The below method is common to verify the below AC
    //When a filter is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box.
    public void verifyMAndNBoxSync(String checkBoxName){
        String elementPath = "//label[contains(text(), '"+checkBoxName+"')]";
        driver.findElement(By.xpath(elementPath)).click();
        Assert.assertTrue(checkBoxName+" is not selected", driver.findElement(By.xpath(elementPath+"/..//input")).isSelected());
        waitForUITransition();
        Assert.assertTrue(checkBoxName+" is not added to Must Have box.", getMustHaveBox().getText().contains(checkBoxName.toUpperCase()));
        getMustHaveBox().findElement(By.xpath(".//div/button[3]")).click();
        waitForUITransition();
        getNiceToHaveBox().findElement(By.xpath(".//div/button[2]")).click();
        Assert.assertTrue(checkBoxName+" is not displaying in Nice to Have box.", getNiceToHaveBox().getText().contains(checkBoxName.toUpperCase()));
        Assert.assertTrue(checkBoxName+" is not displaying in Must Have box.", !getMustHaveBox().getText().contains(checkBoxName.toUpperCase()));
        driver.findElement(By.xpath(elementPath)).click();
        Assert.assertTrue(checkBoxName+" is displaying in Nice to Have box.", !getNiceToHaveBox().getText().contains(checkBoxName.toUpperCase()));
        Assert.assertTrue(checkBoxName+" is displaying in Must Have box.", !getMustHaveBox().getText().contains(checkBoxName.toUpperCase()));

        driver.findElement(By.xpath(elementPath)).click();
        Assert.assertTrue(checkBoxName+" checkbox is not selected.", driver.findElement(By.xpath(elementPath+"/..//input")).isSelected());
        waitForUITransition();
        Assert.assertTrue(checkBoxName+" is not added to Must Have box.", getMustHaveBox().getText().contains(checkBoxName.toUpperCase()));
    }

    //The below method is for checking the selection and deselection and Must Have box functionality for Resources fit criteria checkboxes
    public void verifyResourcesCheckbox(String checkboxName) {
        String path = "//label[contains(text(), '"+checkboxName+"')]";
        driver.findElement(By.xpath(path)).click();
        Assert.assertTrue(checkboxName+" checkbox is not selected.", driver.findElement(By.xpath(path+"/..//input")).isSelected());
        waitForUITransition();
        Assert.assertTrue(checkboxName+" checkbox is not added to Must Have box.", getMustHaveBox().getText().contains(checkboxName.toUpperCase()));

        driver.findElement(By.xpath(path)).click();
        Assert.assertTrue("Un-selecting, the "+checkboxName+" checkbox is not working.", !driver.findElement(By.xpath(path+"/..//input")).isSelected());
        waitForUITransition();
        Assert.assertTrue("After un-selection, Must Have box is still showing "+checkboxName, !getMustHaveBox().getText().contains(checkboxName.toUpperCase()));
    }

    //Locators

    private WebElement getMustHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[1]")); }

    private WebElement getNiceToHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[2]")); }



}
