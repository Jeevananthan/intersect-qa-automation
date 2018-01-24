package pageObjects.SM.loginPage;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

public class LoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

    /**
     * Logs in to SuperMatch through Family Connection, using the default credentials:
     * Username:  Password:  SchoolID:
     */
    public void defaultLoginThroughFamilyConnection() {
        //Right now Family Connection is not integrated with SuperMatch, so we'll just go to the public URL
        navigateToSuperMatch();
        //In the future, we'll need to actually go through family connection
/*        navigateToFamilyConnection("school ID here");
        getDriver().findElement(By.name("username")).sendKeys("user ID here");
        getDriver().findElement(By.name("password")).sendKeys("password here");
        button("Log In").click();
        link("colleges").click();
        link("supermatch").click();
        enterSuperMatchiFrame();   */
    }

    /**
     * Logs in to SuperMatch through Family Connection
     * @param username - username for Family Connection
     * @param password - password for Family Connection
     * @param hsid - SchoolID that is needed for the hsid paramater in the request URL
     */
    public void loginThroughFamilyConnection(String username, String password, String hsid) {
        navigateToFamilyConnection(hsid);
        getDriver().findElement(By.name("username")).sendKeys(username);
        getDriver().findElement(By.name("password")).sendKeys(password);
        button("Log In").click();
        link("colleges").click();
        link("supermatch").click();
        enterSuperMatchiFrame();
    }

    /**
     * Logs out of SuperMatch from inside Family Connection
     */
    public void logoutFromFamilyConnection() {
        //Right now Family Connection is not integrated with SuperMatch, so we'll just delete our cookies
        /*getDriver().switchTo().defaultContent();
        link("sign out").click();*/
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Naviagates to the public-facing SuperMatch URL that has no login screen
     */
    private void navigateToSuperMatch() {
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

    /**
     * Moves the focus of the WebDriver into the SuperMatch iFrame
     */
    private void enterSuperMatchiFrame() {
        // TODO - Don't know what it's called yet.
    }

}
