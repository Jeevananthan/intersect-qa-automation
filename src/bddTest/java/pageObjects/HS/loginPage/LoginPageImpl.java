package pageObjects.HS.loginPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.sql.Driver;
import java.util.List;
import java.nio.file.Watchable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class LoginPageImpl extends PageObjectFacadeImpl {
    private Logger logger;
    private void openLoginPageSupport() {
        load(GetProperties.get("sp.app.url"));
    }

    public LoginPageImpl() {
        logger = Logger.getLogger(pageObjects.HE.loginPage.LoginPageImpl.class);
    }

    public void loginNaviance(String usertype) {
        openNavianceLoginPage();
        String hsid = GetProperties.get("hs."+ usertype + ".hsid");
        String username = GetProperties.get("hs."+ usertype + ".username");
        String password = GetProperties.get("hs."+ usertype + ".password");
        logger.info("Logging into the Naviance app -" + driver.getCurrentUrl());
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
        textbox(By.name("hsid")).sendKeys(hsid);
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        logger.info("Sending credentials - "+ hsid +":"+ username + ":" + password);
        button("Log In").click();

        waitUntilElementExists(link(By.xpath("//li/a[@title='Counselor Community']")));
        // Necessary to handle the announcements overlay.
        try {
            link(By.xpath("//li/a[@title='Counselor Community']")).click();
        } catch (Exception e) {
            if (getDriver().findElement(By.id("announcement-overlay")).isDisplayed()) {
                //We have to jsclick the close button... because the close button is behind the overlay...
                jsClick(getDriver().findElement(By.name("continue-button")));
                link(By.xpath("//li/a[@title='Counselor Community']")).click();
            } else {
                throw e;
            }
        }


        if (username.contains("molly"))
        {
            waitUntilElementExists(driver.findElement(By.xpath("//a[@class='ns-top-nav__secondary-link js-community-link']")));
            waitUntilPageFinishLoading();
            driver.findElement(By.xpath("//a[@class='ns-top-nav__secondary-link js-community-link']")).click();
            waitUntilPageFinishLoading();
        } else {

            waitUntilElementExists(driver.findElement(By.xpath("//a[@title='Counselor Community']")));
            waitUntilPageFinishLoading();
            driver.findElement(By.xpath("//a[@title='Counselor Community']")).click();
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
    }

    public void loginThroughNaviance(String usertype) {
        openNavianceLoginPage();
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
        String hsid = GetProperties.get("hs."+ usertype + ".hsid");
        textbox(By.name("hsid")).sendKeys(hsid);
        String username = GetProperties.get("hs."+ usertype + ".username");
        textbox(By.name("username")).sendKeys(username);
        String password = GetProperties.get("hs."+ usertype + ".password");
        textbox(By.name("password")).sendKeys(password);
        button("Log In").click();
        waitForUITransition();
        waitUntilElementExists(link(By.xpath("//li/a[@title='Counselor Community']")));
        // Necessary to handle the announcements overlay.
        try {
            link(By.xpath("//li/a[@title='Counselor Community']")).click();
        } catch (Exception e) {
            if (getDriver().findElement(By.id("announcement-overlay")).isDisplayed()) {
                //We have to jsclick the close button... because the close button is behind the overlay...
                jsClick(getDriver().findElement(By.name("continue-button")));
                link(By.xpath("//li/a[@title='Counselor Community']")).click();
            } else {
                throw e;
            }
        }

        Set<String> windows = driver.getWindowHandles();
        if(windows.size()>1){
            for (String thisWindow : windows) {
                if (!thisWindow.equals(navianceWindow)){
                    intersectWindow = thisWindow;
                }
            }
            waitForUITransition();
            driver.close();
            driver.switchTo().window(intersectWindow);
        }
        //That set is just to put a limit in the wait until element exists, not is a hardcoded time.
        //Read more information here: https://stackoverflow.com/questions/6992993/selenium-c-sharp-webdriver-wait-until-element-is-present
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id("user-dropdown")));

    }

    public void openNonNavianceLoginPage(){
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();

    }

    public void searchForHSInstitution(String institutionName,String institutionType){
        if(institutionType.equalsIgnoreCase("High school"))
        highSchoolStaffMember().click();
        inputTextBox().click();
        inputTextBox().clear();
        inputTextBox().sendKeys(institutionName);
        searchButton().click();
        waitUntilPageFinishLoading();
        while (showMore().size()==1){
            showMoreButton().click();
            waitUntilPageFinishLoading();
        }
        List<WebElement> schoolList = driver.findElements(By.xpath("//td/a[contains(text(),'"+institutionName+"')]"));
        if(schoolList.size()==0) {
            inputTextBox().click();
            inputTextBox().clear();
            inputTextBox().sendKeys(institutionName);
            List<WebElement> school = driver.findElements(By.xpath("//div[contains(text(),'" + institutionName + "')]"));
            if (school.size() == 1)
                driver.findElement(By.xpath("//div[contains(text(),'" + institutionName + "')]")).click();
        }else {
            driver.findElement(By.xpath("//td/a[contains(text(),'"+institutionName+"')]")).click();
        }
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h1[text()='"+institutionName+"']")));
        Assert.assertTrue("Institution is displayed",driver.findElement(By.xpath("//div/h1[text()='"+institutionName+"']")).isDisplayed());
    }

    public void clickNewUserBtn(){
        getNewUserBtn().click();
        waitUntilPageFinishLoading();
    }

    private WebElement getNewUserBtn(){

        return link("New User?");
    }

    private void openNavianceLoginPage() {

        try {
            //getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            load(GetProperties.get("naviance.app.url"));
            try {
                setImplicitWaitTimeout(2);
                getDriver().findElement(By.xpath("//a[@href='/legacy']")).click();
                resetImplicitWaitTimeout();
            } catch (Exception e2) {
                resetImplicitWaitTimeout();
                logger.info("New Naviance login page was not shown, using legacy flow.");
            }
            waitUntilPageFinishLoading();
        } catch (Exception e) {
            try{getDriver().close();} catch (Exception e2) { logger.info("Tried to call .close() on an already killed session."); }
            load("http://www.google.com");
            System.out.println("Page: " + GetProperties.get("naviance.app.url") + " did not load within 40 seconds!");
        }
        waitUntilPageFinishLoading();
    }

    public void navigateToHSRegistrationPage(){
        load(GetProperties.get("hs.registration.url"));
        Assert.assertTrue("Registration page is not displayed",text("New User? Find Your Institution").isDisplayed());
    }

    public void verifyHSAddressPage(String schoolName,String navianceOrNonnaviance,String address){

        Assert.assertTrue("High School name is not displayed", text(schoolName).isDisplayed());
        Assert.assertTrue("High School address is not displayed", driver.findElement(By.xpath("//span[text()='"+address+"']")).isDisplayed());

        if(navianceOrNonnaviance.equalsIgnoreCase("naviance")){
            Assert.assertTrue("'access counselor community' text is not displayed for naviance HS", driver.findElement(By.xpath("//span[contains(text(),'Please access the Counselor Community via')]")).isDisplayed());
            Assert.assertTrue("'naviance' link is not displayed for naviance HS", link("Naviance").isDisplayed());
            Assert.assertTrue("'log in ' button is displayed for naviance HS", !button("Log In").isDisplayed());
            Assert.assertTrue("",driver.findElement(By.xpath("//span[text()='Back to search']")).isDisplayed());
            try{
                if(!driver.findElement(By.xpath("//span[contains(text(),'Primary User')]")).isDisplayed())
                {
                    logger.info("Primary user details is not displayed");
                }else{logger.info("Primary user details is displayed");}
            }catch(Exception e){}
        }
        else{
            Assert.assertTrue("'log in ' button is not displayed for non-naviance HS", button("Log In").isDisplayed());
            Assert.assertTrue("'Already have an account? ' text is not displayed for non-naviance HS", text("Already have an account?").isDisplayed());
            Assert.assertTrue("'please complete this form' link is not displayed for non-naviance HS", link("please complete this form.").isDisplayed());
            Assert.assertTrue("Back to search is not displayed",link("Back to search").isDisplayed());
            try{
                if(!driver.findElement(By.xpath("//span[contains(text(),'Primary User')]")).isDisplayed())
                {
                    logger.info("Primary user details is not displayed");
                }else{logger.info("Primary user details is displayed");}
            }catch(Exception e){}

        }

    }

    public void navaigateToRegistration()
    {
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();
        link("New User?").click();
    }

    public void verifyInstitutionPage()
    {
        waitUntilElementExists(highSchoolButton());
        Assert.assertTrue("High School Staff Member is not displayed",button("High School Staff Member").isDisplayed());
        Assert.assertTrue("Higher Education Staff Member is not displayed",button("Higher Education Staff Member").isDisplayed());
        Assert.assertTrue("Log In button is not displayed",button("Log In").isDisplayed());
        Assert.assertTrue("text is not displayed",text("New User? Find Your Institution").isDisplayed());
        Assert.assertTrue("textbox is not displayed",driver.findElement(By.xpath("//input[@placeholder='Search Institutions...']")).isDisplayed());

    }
    public void searchInstitution(String school)
    {
        waitUntilElementExists(highSchoolButton());
        driver.findElement(By.xpath("//input[@placeholder='Search Institutions...']")).clear();
        driver.findElement(By.xpath("//input[@placeholder='Search Institutions...']")).sendKeys(school);
        button("Search").click();
        //Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//a[text()='"+school+"']")).isDisplayed());
        //driver.findElement(By.xpath("//a[text()='"+school+"']")).click();
        waitUntilPageFinishLoading();
    }

    public void loginToNaviancePage(String usertype){
        driver.manage().deleteAllCookies();
        openNavianceLoginPage();
        String hsid = GetProperties.get("hs."+ usertype + ".hsid");
        textbox(By.name("hsid")).sendKeys(hsid);
        String username = GetProperties.get("hs."+ usertype + ".username");
        textbox(By.name("username")).sendKeys(username);
        String password = GetProperties.get("hs."+ usertype + ".password");
        textbox(By.name("password")).sendKeys(password);
        button("Log In").click();
        waitUntilElementExists(link(By.cssSelector("[title='Counselor Community']")));
        waitUntilPageFinishLoading();
    }

    public void verifySearchResultsOnRegistrationPage(String school)
    {
        List<WebElement> links = registrationPageResultsTable().findElements(By.tagName("a"));
        for (WebElement link : links) {
            softly().assertThat(link.getText()).as("School Name").contains(school);
        }
    }

    public void verifyLink(String navianceORnonNavianceLink)
    {
       Assert.assertTrue("Link is not displayed",link(navianceORnonNavianceLink).isDisplayed());
        link(navianceORnonNavianceLink).click();
        waitUntilPageFinishLoading();
        if (navianceORnonNavianceLink.equalsIgnoreCase("please complete this form.")) {
            Assert.assertTrue("New user request form was not displayed!", text("Request User Account").isDisplayed());
        } else if (navianceORnonNavianceLink.equalsIgnoreCase("Naviance")) {
            Assert.assertTrue("Error:  Naviance login page was not displayed!", textbox(By.name("hsid")).isDisplayed());
        }
    }

    private void openHSLoginPage() {
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();
    }

    public void verifyLogoInLoginPage()
    {
        openHSLoginPage();
        String intersectLogo="https://static.intersect.hobsons.com/images/counselor-community-by-hobsons-rgb-gray-teal.jpg";
        String actualIntersectLogo=driver.findElement(By.cssSelector("div[class='centered row']>div>img[alt='Intersect Logo']")).getAttribute("src");
        if(intersectLogo.equals(actualIntersectLogo))
        {
            logger.info("Logo is present in the Login Page");
        }else
        {
            logger.info("Logo is not displayed in the Login Page");
        }
}

    public void verifyLogoInHomePage()
    {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        String intersectLogo="https://static.intersect.hobsons.com/images/counselor-community-by-hobsons-rgb-white.png";
        String actualIntersectLogo=driver.findElement(By.cssSelector("dt[class='header _2_tAB8btcE4Sc5e1O_XUwn']>img[alt='Intersect Logo']")).getAttribute("src");
        if(intersectLogo.equals(actualIntersectLogo))
        {
            logger.info("Logo is present in the Home Page");
        }else
        {
            logger.info("Logo is not displayed in the Home Page");
        }
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
        //That set is just to put a limit in the wait until element exists, not is a hardcoded time.
        //Read more information here: https://stackoverflow.com/questions/6992993/selenium-c-sharp-webdriver-wait-until-element-is-present
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id("user-dropdown")));
        waitUntilPageFinishLoading();
    }

    public void blockAccount(String username, String password) {
        openHSLoginPage();
        logger.info("Login into the HS app");
        textbox(By.name("username")).sendKeys(username);
        logger.info("Using " + username + " as username");
        textbox(By.name("password")).sendKeys(password);
        logger.info("Using " + password + " as password");
        button("Login").click();
        waitForUITransition();
        button("Login").click();
        waitForUITransition();
        button("Login").click();
        waitForUITransition();
        button("Login").click();
        waitForUITransition();
        button("Login").click();
        logger.info("Clicked the login button");
//        waitUntilElementExists(link(By.id("user-dropdown")));
        waitUntilPageFinishLoading();
    }

    public void verifyLoginScreen() {
        openHSLoginPage();
        verifyHSLoginPage();
}

    public void verifyLinks(DataTable linksAndDetails) {
        openHSLoginPage();
        waitUntil(ExpectedConditions.elementToBeClickable(button("Login")));
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

    //Log in as an HS user
    public void defaultLogin(String usertype) {
        openHSLoginPage();
        String username = GetProperties.get("hs."+ usertype + ".username");
        String password = GetProperties.get("hs."+ usertype + ".password");
        logger.info("Logging into the HS app");
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        logger.info("Sending credentials - " + username + ":" + password);
        button("Login").click();
        waitUntilElementExists(link(By.id("user-dropdown")));
        waitUntilPageFinishLoading();
    }

    public void defaultLoginForSupport() {
        openLoginPageSupport();
        String username = GetProperties.get("sp.admin.username");
        String password = GetProperties.get("sp.admin.password");
        waitUntilPageFinishLoading();
        logger.info("Logging into the Support app");
        emailUserNameTextboxForSupport().sendKeys(username);
        nextButtonToSupport().sendKeys(Keys.ENTER);
        passwordTextboxForSupport().sendKeys(password);
        signInForSupport().sendKeys(Keys.ENTER);
        yesButtonForSupport().sendKeys(Keys.ENTER);
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

    public void verifyHSLoginPage() {
        Assert.assertTrue("Intersect logo is not present!",driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/counselor-community-by-hobsons-rgb-gray-teal.jpg\"]")).isDisplayed());
        Assert.assertTrue("\"New user?\" link was not present, but should be!",link("New User?").isDisplayed());
        Assert.assertTrue("Email textbox is not present", getDriver().findElement(By.id("username")).isDisplayed());
        Assert.assertTrue("Password textbox is not present", getDriver().findElement(By.id("password")).isDisplayed());
        Assert.assertTrue("Login button is not present", button("Login").isDisplayed());
    }

    private WebElement highSchoolButton()
    {
        WebElement highSchool=button("High School Staff Member");
        waitUntilElementExists(highSchool);
        return highSchool;
    }

    private WebElement emailUserNameTextboxForSupport() {
        return textbox(By.id("i0116"));
    }

    private WebElement passwordTextboxForSupport() {
        return textbox(By.id("i0118"));
    }

    private WebElement signInForSupport() {
        return driver.findElement(By.cssSelector("input[class='btn btn-block btn-primary']"));
    }

    private WebElement yesButtonForSupport() {
        return driver.findElement(By.cssSelector("input[class='btn btn-block btn-primary']"));
    }

    private WebElement nextButtonToSupport() {
        return driver.findElement(By.cssSelector("input[class='btn btn-block btn-primary']"));
    }

    private WebElement inputTextBox(){
        return driver.findElement(By.cssSelector("input[class='prompt']"));
    }

    private WebElement highSchoolStaffMember(){
        return driver.findElement(By.xpath("//button/span[text()='High School Staff Member']"));
    }

    private WebElement searchButton(){
        return driver.findElement(By.xpath("//button/span[text()='Search']"));
    }

    private List<WebElement> showMore(){
        return driver.findElements(By.xpath("//button/span[text()='More Institutions']"));
    }

    private WebElement showMoreButton(){
        return driver.findElement(By.xpath("//button/span[text()='More Institutions']"));
    }

    private WebElement registrationPageResultsTable(){
        return getDriver().findElement(By.id("institution-list"));
    }
}
