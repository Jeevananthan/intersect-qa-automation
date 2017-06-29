package pageObjects.HS.loginPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.Set;

public class LoginPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(pageObjects.HE.loginPage.LoginPageImpl.class);
    }

    public void loginThroughNaviance(String account, String username, String password) {
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
        openNavianceLoginPage();
        textbox(By.name("hsid")).sendKeys(account);
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        button("Sign In").click();
        waitUntilPageFinishLoading();
        link(By.cssSelector("[title='Counselor Community']")).click();
        Set<String> windows = driver.getWindowHandles();
        for (String thisWindow : windows) {
            if (!thisWindow.equals(navianceWindow)){
                intersectWindow = thisWindow;
            }
        }
        driver.close();
        driver.switchTo().window(intersectWindow);
        waitUntilPageFinishLoading();
    }

    private void openNavianceLoginPage() {
        load(GetProperties.get("naviance.app.url"));
        waitUntilPageFinishLoading();

    }

    private void openHSLoginPage() {
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();
    }

    public void login(String username, String password) {
        openHSLoginPage();
        logger.info("Login into the HS app");
        textbox(By.name("username")).sendKeys(username);
        logger.info("Using " + username + " as username");
        textbox(By.name("password")).sendKeys(password);
        logger.info("Using " + password + " as password");
        button("Login").click();
        logger.info("Clicked the login button");
        waitUntilPageFinishLoading();
    }

    public void verifyHSPage() {
        Assert.assertTrue("Username field is not displayed", textbox(By.name("username")).isDisplayed());
        Assert.assertTrue("Password field is not displayed", textbox(By.name("password")).isDisplayed());
        Assert.assertTrue("Intersect logo is not displayed", driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("Login button is not displayed", button("Login").isDisplayed());
    }
}
