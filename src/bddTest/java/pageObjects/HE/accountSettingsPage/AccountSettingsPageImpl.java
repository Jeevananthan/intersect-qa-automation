package pageObjects.HE.accountSettingsPage;

import cucumber.api.DataTable;
import cucumber.api.java.cs.A;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Map;

public class AccountSettingsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

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
                navBar.goToHome();
                waitUntilPageFinishLoading();
                break;
            case "Save":
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
        userDropdown().click();
        Assert.assertTrue("Account Settings option is not displayed",selectOptionfromDropdownList(option).isDisplayed());
        selectOptionfromDropdownList(option).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue(String.format("%s option is not displayed",value),selectOptionInAccountSettings(value).isDisplayed());
        selectOptionInAccountSettings(value).click();
        waitForUITransition();
        // Temporary fix because an error is displayed due to amount of data to be processed
        for(int i=0; i<5;i++){
            try{
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.
                        xpath("//h1[text()='Something unexpected happened. Please, try again.']")),10);
                driver.navigate().refresh();
                waitUntilPageFinishLoading();
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

    private WebElement currentPasswordBox() {
        return textbox("Current Password");
    }

    private WebElement newPasswordBox() {
        return textbox("New Password");
    }

    private WebElement confirmPasswordBox() {
        return textbox("Confirm Password");
    }

    private WebElement saveChanges() { return button("SAVE"); }

    private WebElement cancelButton() { return button("CANCEL"); }

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

}
