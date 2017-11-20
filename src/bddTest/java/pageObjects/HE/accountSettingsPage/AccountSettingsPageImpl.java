package pageObjects.HE.accountSettingsPage;

import cucumber.api.DataTable;
import cucumber.api.java.cs.A;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
            case "Cancel":
                cancelButton().click();
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

    public void accessUsersPage()
    {
        Assert.assertTrue("Users is not displayed",driver.findElement(By.id("js-main-nav-manage-users-menu-link")).isDisplayed());
        driver.findElement(By.id("js-main-nav-manage-users-menu-link")).click();
    }

    public  void selectOption(String option,String eMail)
    {
        Assert.assertTrue("Actions dropdown is displayed",driver.findElement(By.xpath("//div[text()='"+eMail+"']/parent::td/following-sibling::td/div/div[text()='Actions']")).isDisplayed());
        driver.findElement(By.xpath("//div[text()='"+eMail+"']/parent::td/following-sibling::td/div/div[text()='Actions']")).click();
        driver.findElement(By.xpath("//div/span[text()='"+option+"']")).click();
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


}
