package pageObjects.SM.loginPage;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.concurrent.TimeUnit;

public class LoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

    /**
     * Checks the System property SuperMatchEnv and then logs in to the appropriate system
     * If SuperMatchEnv = FamilyConnection, use that, otherwise, use standalone.
     */
    public void defaultLoginThroughFamilyConnection() {
        // See if we're currently logged in to Family Connection and log out if we are
        try{
            /*getDriver().manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
            getDriver().findElement(By.xpath("//button[text()='LOG OUT']")).click();
            new WebDriverWait(getDriver(),60).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']")));
            // For some reason, seeing the login button doesn't *actually* mean we're logged out, so wait a few seconds...
            waitForUITransition();
            getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);*/
        } catch (Exception e){
            logger.info("Caught Exception when trying to log out of Family Connection: " + e.getMessage() + e);
            getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
        }

        // Check if we're testing the embedded version of SM, or the standalone
        try {
            if (System.getProperty("SuperMatchEnv").equals("FamilyConnection")) {
                getDriver().manage().deleteAllCookies();
                getDriver().close();
                loginThroughFamilyConnection(GetProperties.get("fc.default.username"), GetProperties.get("fc.default.password"), GetProperties.get("fc.default.hsid"));
                getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
            } else {
                navigateToSuperMatch();
            }
        } catch (NullPointerException npe) {
            navigateToSuperMatch();
        }
    }

    /**
     * Logs in to SuperMatch through Family Connection
     * @param username - username for Family Connection
     * @param password - password for Family Connection
     * @param hsid - SchoolID that is needed for the hsid paramater in the request URL
     */
    public void loginThroughFamilyConnection(String username, String password, String hsid) {
        navigateToFamilyConnection(hsid);
        // Sometimes the FC UI takes a long time to load, give it some extra room.
        getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        getDriver().findElement(By.name("username")).sendKeys(username);
        getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
        getDriver().findElement(By.name("password")).sendKeys(password);
        button("Login").click();
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOf(link("/colleges"))).click();
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOf(button("Search Tools"))).click();
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOf(link("SuperMatch™ College Search Next"))).click();
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOfElementLocated(By.className("supermatch-page")));
    }

    /**
     * Naviagates to the public-facing SuperMatch URL that has no login screen
     */
    public void navigateToSuperMatch() {
        load(GetProperties.get("sm.app.url"));
    }

    /**
     * Navigates to the Family Connection login screen
     * @param hsid - SchoolID that is needed for the hsid paramater in the request URL
     */
    private void navigateToFamilyConnection(String hsid) {
        String url = GetProperties.get("fc.app.url") + hsid;
        load(url);
    }

    /*private void navigateToFamilyConnectionStaging(String hsid) {
        String url = GetProperties.get("fc.staging.url") + hsid;
        load(url);
    }*/

    //Locators
    private WebElement getMenuBar(){
        return driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']"));
    }

}
