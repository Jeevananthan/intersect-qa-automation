package pageObjects.HE.accountSettingsPage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class SftpDataTransferPageImpl extends PageObjectFacadeImpl {
    private AccountSettingsPageImpl accountSettings = new AccountSettingsPageImpl();

    /**
     * Verifies that the label that says: SFTP Data Transfer is displayed
     */
    public void verifySftpDataTransferText(String expectedText){
        Assert.assertTrue(String.format("The text: %s is not displayed",expectedText),text(expectedText).isDisplayed());
    }

    /**
     * Verifies that the SE UP CONNECTION BUTTON is displayed
     */
    public void verifySetupConnectionButtonIsDisplayed(){
        Assert.assertTrue("Yhe SET UP CONNECTION button is not displayed",
                setupConnectionButton().isDisplayed());
    }

    /**
     *
     */
    public void deleteSftpDataTransferConnection(){
        accountSettings.accessUsersPage("Account Settings", "SFTP Data Transfer");
        if(getDriver().findElements(editLinkLocator()).size()>0){
            editLink().click();
            deleteThisConfigurationLink().click();
            waitUntil(ExpectedConditions.visibilityOf(deleteSftpConfigurationButton()));
            deleteSftpConfigurationButton().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupConnectionButtonLocator()));
        }
    }

    /**
     * Gets the button: SET UP CONNECTION
     * @return WebElement
     */
    private WebElement setupConnectionButton(){
        return getDriver().findElement(setupConnectionButtonLocator());
    }

    /**
     * Gets the edit link
     * @return
     */
    private WebElement editLink(){
        return getDriver().findElement(editLinkLocator());
    }

    /**
     * Returns the delete this configuration link
     * @return
     */
    private WebElement deleteThisConfigurationLink(){
        return getDriver().findElement(By.cssSelector("a[class='_2MEy4XgNL-4iHq3oKYYQxQ']"));
    }

    /**
     * Gets the DELETE sftp configuration button
     * @return
     */
    private WebElement deleteSftpConfigurationButton(){
        return getDriver().findElement(By.cssSelector("button[class='ui teal button']"));
    }

    /**
     * Gets the setup configuration locator
     * @return By
     */
    private By setupConnectionButtonLocator(){
        return By.cssSelector("a[class='ui basic primary button _3cZsSOtK8zE89dYeekrtKo']");
    }

    /**
     * Gets the edit link locator
     * @return By
     */
    private By editLinkLocator(){
        return By.cssSelector("a[class='WNpDJtkdD8rjQ1bCvaie1'");
    }
}
