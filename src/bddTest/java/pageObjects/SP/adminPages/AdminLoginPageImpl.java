package pageObjects.SP.adminPages;

import Selenium.WebElement.ButtonImpl;
import Selenium.WebElement.TextboxImpl;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.SP.commonPages.PageObjectFacadeImpl;
import utilities.GetProperties;

public class AdminLoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private HomePageImpl adminPage;

    public AdminLoginPageImpl() {
        logger = Logger.getLogger(AdminLoginPageImpl.class);
        adminPage = new HomePageImpl();
    }

    public void login(String username, String password) {
        driver.manage().deleteAllCookies();
        openAdminPage();
        // Make sure our previous session ended.
        if (link(By.id("user-dropdown")).isDisplayed()) {
            link(By.id("user-dropdown")).click();
            button(By.id("user-dropdown-signout")).click();
            driver.manage().deleteAllCookies();
            openAdminPage();
        }
        WebElement userAnotherAccount = button(By.className("use_another_account"));
        if (userAnotherAccount.isDisplayed()) {
            userAnotherAccount.click();
        }
        logger.info("Login in to the admin page");
        usernameTextbox().sendKeys(username);
        logger.info("Using " + username + " as username");
        passwordTextbox().click();
        logger.info("Using " + password + " as password");
        handleAccountTypeDialog(password);
        logger.info("Clicked the login button");
        waitUntilPageFinishLoading();
    }

    // Sometimes Azure accounts get confused about whether they are personal or
    // work accounts.  This will handle that dialog and continue.
    private void handleAccountTypeDialog(String password) {
        /*try {
            wait(5000);
        } catch (Exception e){}*/
        waitUntilPageFinishLoading();
        if (link(By.id("aad_account_tile_link")).isDisplayed()){
            link(By.id("aad_account_tile_link")).click();
            textbox(By.id("cred_password_inputtext")).sendKeys(password);
        } else {
            textbox(By.id("cred_password_inputtext")).sendKeys(password);
        }
        button(By.id("cred_sign_in_button")).click();
    }

    public void verifyExpectedErrorMessage(String expectedErrorMsg) {
        String actualErrorMsg = getDriver().findElement(By.id("recover_container")).getText().replace("\n", " ");
        Assert.assertEquals("Error message did not match", expectedErrorMsg, actualErrorMsg);
    }

    private void openAdminPage() {
        load(GetProperties.get("sp.app.url"));
    }

    public void loginAsAViewOnlyUser() {
        login(GetProperties.get("sp.viewOnly.username"), GetProperties.get("sp.viewOnly.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    public void loginAsASalesOpsUser() {
        login(GetProperties.get("sp.salesOps.username"), GetProperties.get("sp.salesOps.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    public void loginAsAnAdminUser() {
        login(GetProperties.get("sp.admin.username"), GetProperties.get("sp.admin.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    public void loginAsASupportUser() {
        login(GetProperties.get("sp.support.username"), GetProperties.get("sp.support.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    //Page Web Elements
    private TextboxImpl usernameTextbox() {
        return textbox(By.id("cred_userid_inputtext"));
    }

    private TextboxImpl passwordTextbox() {
        return textbox(By.id("cred_password_inputtext"));
    }

    private ButtonImpl loginButton() {
        return button(By.id("cred_sign_in_button"));
    }
}
