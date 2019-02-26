package pageObjects.SM.loginPage;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

    /**
     * Checks the System property SuperMatchEnv and then logs in to the appropriate system
     * If SuperMatchEnv = Standalone, use that, otherwise, use Naviance Student.
     */
    public void defaultLoginThroughFamilyConnection() {
        // Check if we're testing the embedded version of SM, or the standalone
        try {
            if (System.getProperty("SuperMatchEnv").equals("Standalone")) {
                navigateToSuperMatch();
            } else {
                loginThroughFamilyConnection(GetProperties.get("fc.default.username"), GetProperties.get("fc.default.password"), GetProperties.get("fc.default.hsid"));
                getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
            }
        } catch (NullPointerException npe) {
            loginThroughFamilyConnection(GetProperties.get("fc.default.username"), GetProperties.get("fc.default.password"), GetProperties.get("fc.default.hsid"));
            getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
        }
    }

    /**
     * Logs in to SuperMatch through Family Connection
     * @param username - username for Family Connection
     * @param password - password for Family Connection
     * @param hsid - SchoolID that is needed for the hsid paramater in the request URL
     */
    public void loginThroughFamilyConnection(String username, String password, String hsid) {
        // Just deleting cookies isn't enough to end your session in FC, so close the browser too.
        try {
            getDriver().manage().deleteAllCookies();
            getDriver().close();
        } catch (Exception e) {}
        navigateToFamilyConnection(hsid);
        // Sometimes the FC UI takes a long time to load, give it some extra room.
        getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        getDriver().findElement(By.name("username")).sendKeys(username);
        getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
        getDriver().findElement(By.name("password")).sendKeys(password);
        button("Login").click();
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOf(link("/colleges"))).click();
        //Search Tools
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOf(button("Find Your Fit"))).click();
        //SuperMatch™ College Search Next
        new WebDriverWait(getDriver(),20).until(ExpectedConditions.visibilityOf(link("SuperMatch®"))).click();
        new WebDriverWait(getDriver(),100).until(ExpectedConditions.visibilityOfElementLocated(By.className("supermatch-page")));
    }

    /**
     * Logs in to SuperMatch through Naviance Student as the specified user type.
     * @param user - User type to login as - from fc.[usertype].username, password, etc. in Env config.
     */
    public void loginThroughFamilyConnectionByType(String user) {
        loginThroughFamilyConnection(GetProperties.get("fc."+user+".username"), GetProperties.get("fc."+user+".password"), GetProperties.get("fc."+user+".hsid"));
        getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")), TimeUnit.SECONDS);
    }

    /**
     * Naviagates to the public-facing SuperMatch URL that has no login screen
     */
    public void navigateToSuperMatch() {
        load(GetProperties.get("sm.app.url"));
    }

    /**
     * Clears the onboarding popups if they are present.
     */
    public void clearOnboardingPopups() {
        try {
            getDriver().manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
            if (getDriver().findElement(By.cssSelector("div[class~=supermatch-onboarding-popup]")).isDisplayed()) {
                getDriver().findElement(By.className("logoWrapper")).click();
                //new WebDriverWait(getDriver(),3).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class~=supermatch-onboarding-popup]")));
            }
            getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")),TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("Exception when clearing Onboarding Popups: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
            getDriver().manage().timeouts().implicitlyWait(Long.parseLong(GetProperties.get("implicitWaitTime")),TimeUnit.SECONDS);
        }
    }

    public void logOutOfSuperMatchAndCloseBrowser() {
        getDriver().manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
        getDriver().findElement(By.xpath("//button[text()='LOG OUT']")).click();
        getDriver().quit();
   }

    /**
     * Navigates to the Family Connection login screen
     * @param hsid - SchoolID that is needed for the hsid paramater in the request URL
     */
    public void navigateToFamilyConnection(String hsid) {
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
