package pageObjects.HE;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Length;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpgradeSubscriptionImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sRequestInformationMessageContent%sRequestInformationMessage.properties",fs ,fs ,fs ,fs ,fs);
    public UpgradeSubscriptionImpl() {
        logger = Logger.getLogger(UpgradeSubscriptionImpl.class);
    }

    public void premiumLock(String subscriptionForLock){

        Assert.assertTrue("Advance Awareness logo is not available",premiumLockIcon(subscriptionForLock).isDisplayed());
        Assert.assertTrue("Advance Awareness Upgrade button is not available",upgradeSubscriptionbutton(subscriptionForLock).isDisplayed());



    }

    public void clickUpgradeSubscription(String subscriptionName){

        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//h2[text()='"+subscriptionName+"']/../div/button"),1));
        upgradeSubscriptionbutton(subscriptionName).click();

    }

    public void recieveCommunications(){
        communicationCheck().click();

    }
    public void enterAdditionalMessage(String messageEntryTitle){
        subscriptionMessage().clear();
        subscriptionMessage().sendKeys(getStringFromPropFile(propertiesFilePath,messageEntryTitle));

    }

    public void requestInformationToUpgrade(){
        requestInformation().click();
        Assert.assertTrue("Request Information Confirmation Message in not displayed",requestConfirmationMessage().isDisplayed());

    }

    public int messageCharCount(String message)  {

         int count = message.length();
        return count;

    }
    public void verifyMessageCount(String characterCount){
        int x = messageCharCount(subscriptionMessage().getText());
        Assert.assertTrue("Additional Message Count is not less than 500 characters",x<= Integer.parseInt(characterCount));

    }

    public void editContactDetails(DataTable ContactDataTable){
        List<List<String>> contactDetails = ContactDataTable.asLists(String.class);
        enterContactDetails(contactDetails);
    }


    public void enterContactDetails(List<List<String>> data){
        for (List<String> row : data){
            switch (row.get(0)){
                case "First Name":
                    contactFirstNameField().clear();
                    contactFirstNameField().sendKeys(row.get(1));
                    break;
                case "Last Name":
                    contactLastNameFiled().clear();
                    contactLastNameFiled().sendKeys(row.get(1));
                    break;
                case "Email":
                    ContactEmailField().clear();
                    ContactEmailField().sendKeys(row.get(1));
                    break;
                case "Phone":
                    contactPhoneField().clear();
                    contactPhoneField().sendKeys(row.get(1));
                    break;
                case "Message":
                    contactMessageField().clear();
                    contactMessageField().sendKeys(row.get(1));
                    break;
            }
        }
    }

    public void clickUpgradeFilter(String filter ){
        upgradeFilterButton(filter).click();

    }

    public void filterPremiumLock(String filterForLock){
        Assert.assertTrue("Advance Awareness logo is not available",premiumLockIconForFilter(filterForLock).isDisplayed());
        Assert.assertTrue("Advance Awareness Upgrade button is not available",upgradeFilterButton(filterForLock).isDisplayed());


    }

    public void filterUpgradeClick(String upgradeFilters){
        upgradeFilterButton(upgradeFilters).click();

    }

    private WebElement contactFirstNameField(){
        return driver.findElement(By.cssSelector("input#field13"));
    }

    private WebElement contactLastNameFiled(){
        return driver.findElement(By.cssSelector("input#field14"));
    }
    private WebElement ContactEmailField(){
        return driver.findElement(By.cssSelector("input#field12"));

    }
    private WebElement contactPhoneField(){
        return driver.findElement(By.cssSelector("input#field15"));
    }
    private WebElement contactMessageField(){
        return driver.findElement(By.cssSelector("textarea#field18"));
    }

    private WebElement subscriptionMessage(){
        return driver.findElement(By.cssSelector("textarea#field18"));
    }
    private WebElement communicationCheck(){
        return driver.findElement(By.cssSelector("input#field20.ui.checkbox"));
    }
    private WebElement premiumLockIcon(String LockIconForSubscription){
        return driver.findElement(By.xpath("//h2[text()='"+LockIconForSubscription+"']/../../img"));
    }

    private WebElement upgradeSubscriptionbutton(String upgradeSubscription){
        return driver.findElement(By.xpath("//h2[text()='"+upgradeSubscription+"']/../div/button"));
    }
    private WebElement Upgradebu(String upgradeSubscription){
        return driver.findElement(By.xpath("//div[text()='"+upgradeSubscription+"']/../div/button"));
    }
    private WebElement requestInformation(){
        return driver.findElement(By.xpath("//span[text()='Request Information']"));
    }
    private WebElement requestConfirmationMessage(){
        return driver.findElement((By.xpath("//span[contains(text(), 'be reaching out to you soon.')]")));
    }
    private WebElement premiumLockIconForFilter(String filterName){
        return driver.findElement(By.xpath("//span[text()='" + filterName + "']/../../..//img"));

    }
    private WebElement upgradeFilterButton(String filterUpgrade){
        return driver.findElement(By.cssSelector("button#locked-upgrade-button.ui.pink.button._1xXwDP5X-gB37o7xxX7f5k"));

    }

}
