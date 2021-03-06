package pageObjects.HE.accountSettingsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AccountSettingsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static String generatedFirstName;
    public static String oldValueFirstName;

    public AccountSettingsPageImpl() {
        logger = Logger.getLogger(AccountSettingsPageImpl.class);
    }

    public void fillAndInteract(String action, DataTable dataTable){
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        logger.info("Filling out Account Settings form");
        for (String key : data.keySet()) {
            textbox(key).sendKeys(data.get(key));
        }
        switch (action) {
            case "Home":
                getNavigationBar().goToHome();
                waitUntilPageFinishLoading();
                break;
            case "Save":
                waitUntil(ExpectedConditions.visibilityOf(saveChanges()));
                saveChanges().click();
                waitUntilPageFinishLoading();
                break;
        }
    }

    public void verifyPasswordRequirementsMessage() {
        boolean messagingDisplayed;
        messagingDisplayed = text("Passwords must contain at least 8 characters and must").isDisplayed();
        messagingDisplayed = messagingDisplayed && text("contain a lowercase letter").isDisplayed();
        messagingDisplayed = messagingDisplayed && text("contain an uppercase letter").isDisplayed();
        messagingDisplayed = messagingDisplayed && text("contain a number").isDisplayed();
        messagingDisplayed = messagingDisplayed && text("contain a special character (^ $ * . [ ] ( ) ? - \" ! @ # % & / , > < ' : ; | _ ~ `)").isDisplayed();
        Assert.assertTrue("Password requirements are not correctly displayed on Change Password screen", messagingDisplayed);
    }

    public void accessUsersPage(String option,String value)
    {
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(userDropDown()));
        userDropdown().click();
        Assert.assertTrue("Account Settings option is not displayed",selectOptionfromDropdownList(option).isDisplayed());
        selectOptionfromDropdownList(option).click();
        waitUntil(ExpectedConditions.visibilityOf(selectOptionInAccountSettings(value)));
        Assert.assertTrue(String.format("%s option is not displayed",value),selectOptionInAccountSettings(value).isDisplayed());
        selectOptionInAccountSettings(value).click();
        waitForUITransition();
        // Temporary fix because an error is displayed due to amount of data to be processed
        for(int i=0; i<20;i++){
            try{
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.
                        xpath("//h1[text()='Something unexpected happened. Please, try again.']")),10);
                //driver.navigate().refresh();
                getNavigationBar().goToHome();
                waitUntilPageFinishLoading();
                userDropdown().click();
                Assert.assertTrue("Account Settings option is not displayed",selectOptionfromDropdownList(option).isDisplayed());
                selectOptionfromDropdownList(option).click();
                waitUntilPageFinishLoading();
                Assert.assertTrue(String.format("%s option is not displayed",value),selectOptionInAccountSettings(value).isDisplayed());
                selectOptionInAccountSettings(value).click();
                waitForUITransition();
            } catch (Exception e){
                break;
            }
        }
        waitUntilPageFinishLoading();
    }

    public  void selectOption(String option,String eMail)
    {
        driver.findElement(By.xpath("//div[text()='"+eMail+"']/parent::td/following-sibling::td/div/div[text()='Actions']")).click();
        jsClick(driver.findElement(By.xpath("//div/span[text()='"+option+"']")));
    }

    public void addStringToCurrentFirstName() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");
        oldValueFirstName = firstNameField().getAttribute("value");
        firstNameField().clear();
        generatedFirstName = oldValueFirstName + dateFormat.format(date);
        firstNameField().sendKeys(generatedFirstName);
        Assert.assertTrue("The value was not correctly set", firstNameField().getAttribute("value").equals(generatedFirstName));
    }

    public void clickSaveChanges() {
        saveChanges().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']"),1));
    }

    public void setFirstNameToOriginalValue(String firstName) {
        firstNameField().clear();
        firstNameField().sendKeys(firstName);
    }

    /**
     * Verifies if a given account settings tab is not displayed
     */
    public void verifyAccountSettingsTabIsNotDisplayed(String tab){
        getNavigationBar().selectUserOption("Account Settings");
        Assert.assertFalse(String.format("The tab: %s is displayed",tab),
                getDriver().findElements(accountSettingsTabLocator(tab)).size()>0);
    }

    //Locators
    private WebElement saveChanges() { return driver.findElement(By.xpath("//span[text()='SAVE']")); }
    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }
    private WebElement selectOptionfromDropdownList(String Option)
    {
        WebElement option=driver.findElement(By.xpath("//span[text()='"+Option+"']"));
        return option;
    }
    private WebElement selectOptionInAccountSettings(String value)
    {
    WebElement option=driver.findElement(By.xpath("//span[text()='"+value+"']"));
    return option;
    }

    private WebElement firstNameField() { return driver.findElement(By.cssSelector("input#user-form-first-name")); }

    /**
     * Gets the account settings tabs
     * @param tab
     * @return
     */
    private By accountSettingsTabLocator(String tab){
        return By.xpath(String.format("//span[text()='%s']",tab));
    }

    private By userDropDown(){
        return By.id("user-dropdown");
    }
}
