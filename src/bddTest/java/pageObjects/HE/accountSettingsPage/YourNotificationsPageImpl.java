package pageObjects.HE.accountSettingsPage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class YourNotificationsPageImpl extends PageObjectFacadeImpl {
    private AccountSettingsPageImpl accountSettings = new AccountSettingsPageImpl();

    /**
     * Navigates to your Account Settings > Your Notifications
     */
    public void goToYourNotifications(){
        accountSettings.accessUsersPage("Account Settings", "Your Notifications");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(yourNotificationsTitleLocator()));
    }

    /**
     * Verifies the given text is displayed in your notifications title
     * @param text
     */
    public void verifyYourNotificationsTitle(String text){
        Assert.assertEquals(String.format("Your notifications title is not correct, actual %s, expected: %s"
                ,yourNotificationsTitle().getText(), text), yourNotificationsTitle().getText(), text);
    }

    /**
     * Verifies the given text is displayed in your notifications description
     * @param text
     */
    public void verifyYourNotificationsDescription(String text){
        Assert.assertEquals(String.format("Your notifications description is not correct, actual %s, expected: %s"
                ,yourNotificationsDescription().getText(), text), yourNotificationsDescription().getText(), text);
    }

    /**
     * Verifies the given text is displayed in the rep visits title
     * @param text
     */
    public void verifyRepVisitsTitle(String text){
        Assert.assertEquals(String.format("RepVisits title is not correct, actual %s, expected: %s"
                ,repvisitsTitle().getText(), text), repvisitsTitle().getText(), text);
    }

    /**
     * Verifies that the alert me when high schools become available text box is displayed
     */
    public void verifyAlertMeWhenHighSchoolsBecomeAvailableChecbox(){
        Assert.assertTrue("The alert me when high school become available check box is not displayed",
                alertMeWhenShoolsBecomeAvailableCheckbox().isDisplayed());
    }

    /**
     * Saves your notifications
     */
    public void saveYourNotifications(){
        saveButton().click();
    }

    /**
     * Verifies the text of the toast when your notifications are saved
     * @param text
     */
    public void verifySaveConfigurationsToast(String text){
        Assert.assertEquals(String.format("The saved toast is not correct, actual: %s, expected: %s",
                yourNotificationsWereUpdatedToast().getText(), text), yourNotificationsWereUpdatedToast().getText(), text);
    }

    /**
     * Gets your notifications title locator
     * @return Webelement
     */
    private By yourNotificationsTitleLocator(){
        return By.cssSelector("h2[id='right-side-heading']");
    }

    /**
     * Gets your notifications title
     * @return WebElement
     */
    private WebElement yourNotificationsTitle(){
        return getDriver().findElement(yourNotificationsTitleLocator());
    }

    /***
     * Gets the rep visists title
     * @return Webelement
     */
    private WebElement repvisitsTitle(){
        return getDriver().findElement(By.cssSelector("h3[class='ui header _1nKv4w-z95pmVWGWegwq-o']"));
    }

    /**
     * Gets the your notifications description label
     * @return Webelement
     */
    private WebElement yourNotificationsDescription(){
        return getDriver().findElement(By.xpath("//div[@class='hNXetcyRcBasIWMAW-Eab']/p/span"));
    }

    /**
     * Gets the alert when school becomes available checkbox
     * @return Webelement
     */
    private WebElement alertMeWhenShoolsBecomeAvailableCheckbox(){
        return getDriver().findElement(By.cssSelector("div[class='ui checkbox']"));
    }

    /**
     * Gets the toast when the notifications are updated
     * @return Webelement
     */
    private WebElement yourNotificationsWereUpdatedToast(){
        return getDriver().findElement(By.xpath("//div[@class='content']/span/span"));
    }

    /**
     * Gets the SAVE button
     * @return
     */
    private WebElement saveButton(){
        return getDriver().findElement(By.cssSelector("button[class='ui primary button']"));
    }

}
