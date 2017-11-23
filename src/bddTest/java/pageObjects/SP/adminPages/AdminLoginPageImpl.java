package pageObjects.SP.adminPages;

import Selenium.WebElement.ButtonImpl;
import Selenium.WebElement.TextboxImpl;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

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
        button("Next").click();
        waitUntilPageFinishLoading();
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
            passwordTextbox().sendKeys(password);
        }
        button("Sign in").click();
        waitUntilPageFinishLoading();
        List<WebElement> resetLink = driver.findElements(By.xpath("//a[@id='idA_IL_ForgotPassword0']"));
        if (resetLink.size()==1){
            logger.info("The given username or password is wrong");
        }else if(button(By.id("idBtn_Back")).isDisplayed()){
            button("No").click();
            waitUntilPageFinishLoading();
        }
    }

    public void verifyExpectedErrorMessage(String expectedErrorMsg) {
        String actualErrorMsg = getDriver().findElement(By.id("passwordError")).getText();
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

    public void loginAsACommunityUser() {
        login(GetProperties.get("sp.communityUser.username"), GetProperties.get("sp.communityUser.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    public void loginAsACommunityManagerUser() {
        login(GetProperties.get("sp.communityManager.username"), GetProperties.get("sp.communityManager.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    public void loginAsNoAccessUser() {
        login(GetProperties.get("sp.norole.username"), GetProperties.get("sp.norole.password"));
        adminPage.verifyUserIsLoggedIn();
    }

    //Page Web Elements
    private TextboxImpl usernameTextbox() {
        return textbox(By.id("i0116"));
    }

    private TextboxImpl passwordTextbox() {
        return textbox(By.id("i0118"));
    }

    private ButtonImpl loginButton() {
        return button(By.id("cred_sign_in_button"));
    }
}
