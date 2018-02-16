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
    public void verifyMAndNBoxSync(String checkBoxName, String elementPath){
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

    public void verifyDeafAndHardHearingCheckbox() {
        String path = "//label[contains(text(), 'Services for the Deaf and Hard of Hearing')]";
        driver.findElement(By.xpath(path)).click();
        Assert.assertTrue("Services for the Deaf and Hard of Hearing checkbox is not selected.", driver.findElement(By.xpath(path+"/..//input")).isSelected());
        waitForUITransition();
        Assert.assertTrue("Services for the Deaf and Hard of Hearing is not added to Must Have box.", getMustHaveBox().getText().contains("SERVICES FOR THE DEAF AND HARD OF HEARING"));

        driver.findElement(By.xpath(path)).click();
        Assert.assertTrue("Un-selecting, the Services for the Deaf and Hard of Hearing checkbox is not working.", !driver.findElement(By.xpath(path+"/..//input")).isSelected());
        waitForUITransition();
        Assert.assertTrue("After un-selection, Must Have box is still showing Services for the Deaf and Hard of Hearing button", !getMustHaveBox().getText().contains("SERVICES FOR THE DEAF AND HARD OF HEARING"));
    }

    public void verifyMHAndNHSyncWithServiceforDeafandHardofHearingFilter() {

        verifyMAndNBoxSync("Services for the Deaf and Hard of Hearing", "//label[contains(text(), 'Services for the Deaf and Hard of Hearing')]");
    }

        //Locators

    private WebElement getMustHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[1]")); }

    private WebElement getNiceToHaveBox() { return driver.findElement(By.xpath("(//div[@class='box box-selection'])[2]")); }



}
