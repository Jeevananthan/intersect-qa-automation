package pageObjects.SP.configPages;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

public class ConfigPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public ConfigPageImpl() { logger = Logger.getLogger(ConfigPageImpl.class);    }

    public void goToManageBlockedAccounts(String urlType){
            String url = GetProperties.get("sp."+ urlType + ".url");
            driver.get(url);
    }

    public void activateBlockedUser(String inactivatedUserName){
        driver.switchTo().frame(0);
        WebElement inactiveUserCheckbox = driver.findElement(By.xpath("//div[text()='"+inactivatedUserName+"']/../../../../../td/div/input"));
        inactiveUserCheckbox.click();
        WebElement unblockSelectedAccountsButton = driver.findElement(By.id("edit-submit"));
        unblockSelectedAccountsButton.click();
        List<WebElement> users = driver.findElements(By.xpath("//div[text()='"+inactivatedUserName+"']"));
        Assert.assertTrue(inactivatedUserName + " is not activated successfully.", users.size()==0);
    }

    //locator

}
