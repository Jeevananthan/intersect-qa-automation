package pageObjects.HS.loginPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;
import java.util.Set;

public class LoginPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(pageObjects.HE.loginPage.LoginPageImpl.class);
    }

    public void loginThroughNaviance(String account, String username, String password) {
        driver.manage().deleteAllCookies();
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
        waitUntilElementExists(driver.findElement(By.id("js-main-nav-home-menu-link")));
    }

    public void openNonNavianceLoginPage(){
        driver.manage().deleteAllCookies();
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();

    }

    public void searchForHSInstitution(String institutionName,String institutionType){

        if(institutionType.equalsIgnoreCase("high school")){
            button("High School Staff Member").click();
        }
        else{
            button("Higher Education Staff Member").click();
        }

        driver.findElement(By.cssSelector("input[class='prompt']")).sendKeys(institutionName);
        button("Search").click();
        while(button("More Institutions").isDisplayed()){
            button("More Institutions").click();
        }

        if(!institutionName.equalsIgnoreCase("Request new institution")){
            if(driver.findElement(By.xpath("//table[@id='institution-list']")).isDisplayed() &&  link(institutionName).isDisplayed()){
                logger.info("Results are displayed after the search");
                link(institutionName).click();
            }
            else{
                logger.info("Results are not displayed after the search");
            }
        }
        else{
            link(institutionName).click();
        }

        Assert.assertTrue("Institution Page is not loaded",text(institutionName).isDisplayed());
        link("Back to search").click();

    }

    public void clickNewUserBtn(){
        getNewUserBtn().click();
        waitUntilPageFinishLoading();
    }

    private WebElement getNewUserBtn(){

        return link("New User?");
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

    public void verifyLoginScreen() {
        openHSLoginPage();
        Assert.assertTrue("Intersect logo is not present!",driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("\"New user?\" link was not present, but should be!",link("New User?").isDisplayed());
        Assert.assertTrue("Email textbox is not present", getDriver().findElement(By.id("username")).isDisplayed());
        Assert.assertTrue("Password textbox is not present", getDriver().findElement(By.id("password")).isDisplayed());
        Assert.assertTrue("Login button is not present", button("Login").isDisplayed());
    }

    public void verifyLinks(DataTable linksAndDetails) {
        openHSLoginPage();
        List<List<String>> links = linksAndDetails.asLists(String.class);
        for (List<String> link : links) {
            getDriver().findElement(By.xpath("//span[text()='" + link.get(0) + "']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("The link " + link.get(0) + " redirects to a wrong URL: " + link.get(1), getDriver().getCurrentUrl().equals(link.get(1)));
            load(GetProperties.get("hs.app.url"));
        }
    }

    public void verifyErrorMessages(DataTable errorsList) {
        openHSLoginPage();
        List<String> errorTypes = errorsList.asList(String.class);

        for (String errorType : errorTypes) {
            switch (errorType) {
                case "Improper email address" :
                    textbox(By.name("username")).sendKeys("qwerty");
                    //Pending of implementation
                    break;
                case "Email or password not provided" :
                    textbox(By.name("username")).sendKeys("");
                    textbox(By.name("password")).sendKeys("");
                    button("Login").click();
                    Assert.assertTrue("The error message was not displayed, even when the email is empty", emptyEmailErrorMessage().isDisplayed());
                    Assert.assertTrue("The error message was not displayed, even when the password is empty", emptyPasswordErrorMessage().isDisplayed());
                    break;
                case "User not found" :
                    textbox(By.name("username")).sendKeys("testmail@hobsonstest.com");
                    textbox(By.name("password")).sendKeys("123");
                    button("Login").click();
                    waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.active.loader")));
                    waitUntil(ExpectedConditions.elementToBeClickable(userNotFoundErrorMessage()));
                    Assert.assertTrue("The 'User not found' error was not displayed", userNotFoundErrorMessage().isDisplayed());
                    break;
            }
        }
    }

    public void userLockedOut(String userType) {
        attemptLogin(userType,"This user has been locked due to excessive invalid password attempts.");
    }

    public void attemptLogin(String userType, String errorMessage) {
        openHSLoginPage();
        String username = GetProperties.get("hs."+ userType + ".username");
        String password = GetProperties.get("hs."+ userType + ".password");
        waitUntilPageFinishLoading();
        logger.info("Attempting to log into the HE app");
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        logger.info("Sending credentials - " + username + ":" + password);
        button("Login").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Login error message is not displayed as expected!\nExpected: "+errorMessage+"\n",text(errorMessage).isDisplayed());
    }

    //Log in as an HS administrator
    public void defaultLogin(String usertype) {
        openHSLoginPage();
        String username = GetProperties.get("he."+ usertype + ".username");
        String password = GetProperties.get("he."+ usertype + ".password");
        logger.info("Logging into the HE app");
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        logger.info("Sending credentials - " + username + ":" + password);
        button("Login").click();
        waitUntilElementExists(link(By.id("user-dropdown")));
        waitUntilPageFinishLoading();
    }

    //Locators

    private WebElement emptyEmailErrorMessage() {
        return getDriver().findElement(By.cssSelector("label[for=\"username\"] + div + div"));
    }
    private WebElement emptyPasswordErrorMessage() {
        return getDriver().findElement(By.cssSelector("label[for=\"password\"] + div + div"));
    }
    private WebElement userNotFoundErrorMessage() {
        return getDriver().findElement(By.cssSelector("div.ui.negative.message span"));
    }
    private WebElement spinner() {
        return getDriver().findElement(By.cssSelector("div.ui.active.loader"));
    }

    public void verifyHSPage() {
        Assert.assertTrue("Username field is not displayed", textbox(By.name("username")).isDisplayed());
        Assert.assertTrue("Password field is not displayed", textbox(By.name("password")).isDisplayed());
        Assert.assertTrue("Intersect logo is not displayed", driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("Login button is not displayed", button("Login").isDisplayed());
    }
}
