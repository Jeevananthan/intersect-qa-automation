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

    /**
     * This method will login into URL, which displays all blocked users to support.
     * Author : Arun
     * @param urlType
     */
    public void goToManageBlockedAccounts(String urlType){
            String url = GetProperties.get("sp."+ urlType + ".url");
            driver.get(url);
    }

    /**
     * The below method will activate users who inactivated their own community profile.
     * Parameter accepted : User Name which you want to activate.
     * Author : Arun
     * @param inactivatedUserName
     */
    public void activateBlockedUser(String inactivatedUserName){
        driver.switchTo().frame(0);
        if(inactivateUsers(inactivatedUserName).size()==1) {
            inactiveUserCheckbox(inactivatedUserName).click();
            unblockSelectedAccountsButton().click();
        }
        Assert.assertTrue(inactivatedUserName + " is not activated successfully.", inactivateUsers(inactivatedUserName).size()==0);
    }

    //locator
    private WebElement inactiveUserCheckbox(String inactivatedUserName){return driver.findElement(By.xpath("//div[text()='"+inactivatedUserName+"']/../../../../../td/div/input"));}
    private WebElement unblockSelectedAccountsButton(){return driver.findElement(By.id("edit-submit"));}
    private List<WebElement> inactivateUsers(String inactivatedUserName){ return driver.findElements(By.xpath("//div[text()='"+inactivatedUserName+"']"));}
}
