package pageObjects.HS.homePage;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;
import java.util.Calendar;
import java.util.Set;

public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public HomePageImpl() {
        logger = Logger.getLogger(pageObjects.HE.homePage.HomePageImpl.class);
    }

    public void verifyUserIsLoggedIn() {
        //Check if user element is present
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign in successfully", link(By.id("user-dropdown")).isDisplayed());
        logger.info("Logged in successfully");
    }

    public void logout() {
        driver.switchTo().defaultContent();
        waitUntil(ExpectedConditions.elementToBeClickable(userDropdown()));
        userDropdown().click();
        button(By.cssSelector("i.sign.out.icon + span.text")).click();
        waitUntilPageFinishLoading();
        driver.manage().deleteAllCookies();
        Assert.assertTrue("User did not sign out", getDriver().getCurrentUrl().contains("login"));
    }

    public void goToCounselorCommunity(){
        getNavigationBar().goToCommunityInHS();
    }

    public void verifyTitleHS(String generalCategoryName,String pageName){

        //this function is used to verify the page title in HS app
        Assert.assertTrue("General Category Name is not displayed in the title name ",driver.findElement(By.xpath("//div[text()='"+generalCategoryName+"']")).isDisplayed());
        Assert.assertTrue("Page Name is not displayed in the title name ",driver.findElement(By.xpath("//div[text()='"+pageName+"']")).isDisplayed());
    }

    public void verifyAdditionalInfoURLBeforeClickingBackToIntersectLink(String additionalInfoURL,String additionalInfo,String backToIntersect,String SCID,String college,String info){
        waitUntilPageFinishLoading();
        communityFrame();
        WebElement additionalLink = link(additionalInfo);
        waitUntil(ExpectedConditions.visibilityOf(additionalLink));
        additionalLink.click();
        waitForUITransition();
        waitUntilPageFinishLoading();
        Assert.assertTrue("College is not displayed",driver.findElement(By.xpath("//div/h2[text()='"+college+"']")).isDisplayed());
        String currentURL = additionalInfoURL+SCID+info;
        String additionalInfoCurrentURL = driver.getCurrentUrl();
        Assert.assertTrue("Additional info URL is not displayed",additionalInfoCurrentURL.equals(currentURL));
        WebElement viewNavianceCollegeProfile = link("VIEW NAVIANCE COLLEGE PROFILE");
        waitUntil(ExpectedConditions.visibilityOf(viewNavianceCollegeProfile));
        viewNavianceCollegeProfile.click();
        waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        waitForUITransition();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Back to Intersect link is not displayed",link(backToIntersect).isDisplayed());
    }

    public void verifyAdditionalInfoURLAfterClickingBackToIntersectLink(String additionalInfoURL,String backToIntersect,String institutionID,String info){
        String currentURL = additionalInfoURL+institutionID+info;
        link(backToIntersect).click();
        String additionalInfoCurrentURL = driver.getCurrentUrl();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Additional info URL is not displayed",additionalInfoCurrentURL.equals(currentURL));
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
    }

    private WebElement userDropdown() {
        waitUntilPageFinishLoading();
        waitForUITransition();
        return driver.findElement(By.cssSelector("div[id='user-dropdown']"));
    }

    public void verifyTextInButtonFromModule(String moduleName, String buttonText) {
        Assert.assertTrue("The text in the button is incorrect. UI: " + moduleButton(moduleName).getText(), moduleButton(moduleName).getText().equals(buttonText));
    }

        public void verifyScreenIsOpenFromModule(String expectedUrl, String moduleName) {
            waitUntilElementExists(moduleButton(moduleName));
            moduleButton(moduleName).click();
            waitUntilPageFinishLoading();
            String expectedURL = GetProperties.get("hs.app.url") + expectedUrl;
            String actualURL = driver.getCurrentUrl();
            Assert.assertEquals(actualURL, expectedURL);
        }

    public void verifyYearInLoginPage(){
        String currentYear = getCurrentYear();
        driver.manage().deleteAllCookies();
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }

    public void verifyYearInNavianceLoginPage(){
        String currentYear = getCurrentYear();
        driver.manage().deleteAllCookies();
        load(GetProperties.get("naviance.app.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//ul/li[text()='Copyright © "+currentYear+", Hobsons Inc.']")).isDisplayed());
    }


    public void verifyYearInRegistrationPage(){
        String currentYear = getCurrentYear();
        load(GetProperties.get("hs.registration.url"));
        Assert.assertTrue("Registration page is not displayed",text("New User? Find Your Institution").isDisplayed());
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }

    public void verifyYearInHomePage(){
        String currentYear = getCurrentYear();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }

    public void verifyHelpCentre() {
        Assert.assertTrue("notifications icon is not displayed",notificationIconInHelpCentre().isDisplayed());
        Assert.assertTrue("helpNav-dropdown icon is not displayed",helpNavDropdown().isDisplayed());
        helpNavDropdown().click();
        Assert.assertTrue("Help Center is not displayed",helpCentre().isDisplayed());
        Assert.assertTrue("Contact Support is not displayed",contactSupport().isDisplayed());
        helpCentre().click();
        waitUntilPageFinishLoading();
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
        Set<String> windows = driver.getWindowHandles();
        for (String thisWindow : windows) {
            if (!thisWindow.equals(navianceWindow)){
                intersectWindow = thisWindow;
            }
        }
        driver.switchTo().window(intersectWindow);
        waitUntilPageFinishLoading();
        String currentYear = getCurrentYear();
        Assert.assertTrue("hobsons logo is not displayed",logo().isDisplayed());
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//span[text()='Copyright © "+currentYear+" ']/parent::span/following-sibling::span[text()='Hobsons']")).isDisplayed());
        driver.close();
        driver.switchTo().window(navianceWindow);
        waitUntilPageFinishLoading();
    }

    public void verifyYearInNaviancePage(String account, String username, String password){
        String currentYear = getCurrentYear();
        textbox(By.name("hsid")).sendKeys(account);
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        button("Sign In").click();

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

        waitUntilPageFinishLoading();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//td[contains(text(),'Copyright © "+currentYear+", Hobsons Inc.')]")).isDisplayed());
    }
    public void verifyYearInRepVisitsPage(){
        String currentYear = getCurrentYear();
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
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
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id("app")));
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }
    public String getCurrentYear(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String currentYear=Integer.toString(year);
        return currentYear;
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    private WebElement collageNameLabel() {
        return getDriver().findElement(By.cssSelector("h1.masthead__name"));
    }
    private WebElement moduleButton(String moduleName) { return driver.findElement(By.xpath("//div/h2[text() = '" + moduleName + "']/../div/a")); }
    private WebElement notificationIconInHelpCentre() {
        WebElement notificationIcon=driver.findElement(By.id("notificationsNav"));
        return notificationIcon;
    }
    private WebElement helpCentre()    {
        WebElement helpCentre=driver.findElement(By.xpath("//span[text()='Help Center']"));
        return  helpCentre;
    }
    private WebElement helpNavDropdown() {
        WebElement help=driver.findElement(By.id("helpNav-dropdown"));
        return  help;
    }
    private WebElement contactSupport()  {
        WebElement contactSupport= text("Contact Support");
        return  contactSupport;
    }
    private  WebElement logo() {
        WebElement Logo=driver.findElement(By.xpath("//div/a[@class='logo']"));
        return Logo;
    }
}
