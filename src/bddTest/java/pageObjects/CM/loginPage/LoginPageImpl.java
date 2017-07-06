package pageObjects.CM.loginPage;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.CM.commonPages.PageObjectFacadeImpl;
import utilities.GetProperties;

public class LoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

    private void openLoginPageHE() {
        load(GetProperties.get("he.app.url"));
    }

    private void openLoginPageSupport() {
        load(GetProperties.get("sp.app.url"));
    }

    public void loginHE(String username, String password) {
        openLoginPageHE();
        //link("Go To Authorized Page").click();
        logger.info("Login into the HE app");
        usernameTextbox().sendKeys(username);
        logger.info("Using " + username + " as username");
        passwordTextbox().sendKeys(password);
        logger.info("Using " + password + " as password");
        loginButton().click();
        logger.info("Clicked the login button");
        waitUntilPageFinishLoading();
        logger.info("Accessing Community from HE App");
        link("Community").click();
        waitUntilPageFinishLoading();
        getDriver().switchTo().frame(driver.findElement(By.tagName("iframe")));
    }

    public void defaultLoginHE() {
        openLoginPageHE();
        String username = GetProperties.get("he.administrator.username");
        String password = GetProperties.get("he.administrator.password");
        //link("Go To Authorized Page").click();
        waitUntilPageFinishLoading();
        logger.info("Logging into the HE app");
        usernameTextbox().sendKeys(username);
        passwordTextbox().sendKeys(password);
        logger.info("Sending credentials - " + username + ":" + password);
        loginButton().click();
        waitUntilPageFinishLoading();
        logger.info("Accessing Community from HE App");
//        link(By.id("js-main-nav-counselor-community-menu-link")).click();
//        waitUntilPageFinishLoading();
//        getDriver().switchTo().frame(driver.findElement(By.tagName("iframe")));
    }

    public void defaultLoginSupport() {
        openLoginPageSupport();
        String username = GetProperties.get("sp.admin.username");
        String password = GetProperties.get("sp.admin.password");
        waitUntilPageFinishLoading();
        logger.info("Logging into the Support app");
        textbox("Email or phone").sendKeys(username);
        passwordTextbox().click();
        handleAccountTypeDialog();
        logger.info("Sending credentials - " + username + ":" + password);
        //button("Sign in").click();
//        logger.info("Accessing Community from Support App");
//        link(By.id("js-main-nav-community-menu-link")).click();
//        waitUntilPageFinishLoading();
//        getDriver().switchTo().frame(driver.findElement(By.tagName("iframe")));
    }

    // Sometimes Azure accounts get confused about whether they are personal or
    // work accounts.  This will handle that dialog and continue.
    private void handleAccountTypeDialog() {
       /* try {
            wait(5000);
        } catch (Exception e){}*/
        waitUntilPageFinishLoading();
        String password = GetProperties.get("sp.admin.password");
        if (link(By.id("aad_account_tile_link")).isDisplayed()){
            link(By.id("aad_account_tile_link")).click();
            textbox("Password").sendKeys(password);
        } else {
            textbox("Password").sendKeys(password);
        }
        button("Sign in").click();
    }
    private WebElement usernameTextbox() {
        return textbox("E-mail Address");
    }

    private WebElement passwordTextbox() {
        return textbox("Password");
    }

    private WebElement loginButton() {
        return button("Login");
    }

}
