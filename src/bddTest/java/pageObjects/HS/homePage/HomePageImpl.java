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
        //link(By.cssSelector("a[id='js-main-nav-home-menu-link']>span")).click();
        navigationBar.goToCommunityInHS();
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
        new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe((additionalInfoCurrentURL)));
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
  
      public void clearCommunityProfile(){
        load(GetProperties.get("hs.community.clear"));
        waitUntilPageFinishLoading();
    }

    public void verifyCommunityActivationForRepVisits(){
        navigationBar.goToRepVisits();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe._2ROBZ2Dk5vz-sbMhTR-LJ")));
        waitForUITransition();
        Assert.assertTrue("Community Profile Welcome Page is not displaying...", communityWelcomeForm().isDisplayed());
        driver.switchTo().defaultContent();
    }

    public void verifyRequiredPageforNewUser(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for(String tab:list){
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@name='mainmenu']"),1));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab){
                case "Counselor Community":
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("js-main-nav-home-menu-link"),1));
                    counselorCommunityMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='welcome-text']"),1));
                    Assert.assertTrue("Counselor Community profile form is not displayed",ccProfileForm().isDisplayed());
                    iframeExit();
                    break;
                case "RepVisits":
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("js-main-nav-rep-visits-menu-link"),1));
                    repVisitsMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='welcome-text']"),1));
                    Assert.assertTrue("Counselor Community profile form is not displayed",ccProfileForm().isDisplayed());
                    iframeExit();
                    break;
                default:
                    Assert.fail("Invalid option");
            }
        }
    }

    public void verifyRequiredFieldsInCCProfileForm(DataTable dataTable){
        navigationDropDown().sendKeys(Keys.ENTER);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("js-main-nav-home-menu-link"),1));
        counselorCommunityMenuLink().click();
        iframeEnter();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("edit-submit"),1));
        saveButtonInCommunity().click();
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        for(String fields:list){
            Assert.assertTrue(fields+" is not displayed",text(fields).isDisplayed());
        }
        iframeExit();
    }

    public void verifyingTabNavigation(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for(String tab:list){
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@name='mainmenu']"),1));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab) {
                case "Counselor Community":
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("js-main-nav-home-menu-link"),1));
                    counselorCommunityMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//li/a[text()='Home']"),1));
                    Assert.assertTrue("Counselor Community profile form is not displayed",homeTabInCommunity().isDisplayed());
                    iframeExit();
                    break;
                case "RepVisits":
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("js-main-nav-rep-visits-menu-link"),1));
                    repVisitsMenuLink().click();
                    waitUntilElementExists(getCalendarPage());
                    Assert.assertTrue("Search and schedule tab is not displayed",getCalendarPage().isDisplayed());
                    break;
                default:
                    Assert.fail("Invalid option");
            }}
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void fillCommunityWelcomeMandatoryFields(String OfficePhone, String JobTitle, String euCitizen){
        driver.switchTo().frame(0);
        getofficePhone().sendKeys(OfficePhone);
        getJobTitle().sendKeys(JobTitle);
        driver.findElement(By.xpath(String.format(
                "//label[@for='edit-field-eu-citizen-und']/following-sibling::div/div/label[text()='%s ']",
                euCitizen))).click();
        getTermsAndConditionCheckBox().click();
        driver.executeScript("arguments[0].click()",getCreationAndMaintenanceConsentCheckBox());
        saveButton().click();
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
        navigationBar.goToRepVisits();
    }

    private WebElement collageNameLabel() {
        return getDriver().findElement(By.cssSelector("h1.masthead__name"));
    }
    private WebElement moduleButton(String moduleName) { return driver.findElement(By.xpath("//div[text() = '" + moduleName + "']/../div/a")); }
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
      private WebElement communityWelcomeForm(){ return driver.findElement(By.id("user-profile-form")); }
    private WebElement navigationDropDown(){
        return driver.findElement(By.xpath("//a[@name='mainmenu']"));
    }
    private WebElement counselorCommunityMenuLink(){
        return driver.findElement(By.id("js-main-nav-home-menu-link"));
    }
    private WebElement repVisitsMenuLink(){
        return driver.findElement(By.id("js-main-nav-rep-visits-menu-link"));
    }
    private WebElement saveButtonInCommunity(){
        return driver.findElement(By.id("edit-submit"));
    }
    private WebElement homeTabInCommunity(){
        return driver.findElement(By.xpath("//li/a[text()='Home']"));
    }
    private WebElement getCalendarPage(){
        return driver.findElement(By.xpath("//a[@class='menu-link qxSNjKWAyYiOIN9yZHE_d']/span[text()='Calendar']"));
    }
    private WebElement ccProfileForm(){
        return driver.findElement(By.xpath("//div/h1[text()='Welcome to the Counselor Community!']"));
    }
    private WebElement getofficePhone() { return driver.findElement(By.id("edit-field-office-phone-und-0-value"));}
    private WebElement getJobTitle(){ return driver.findElement(By.id("edit-field-job-position-und-0-value"));}
    private WebElement getTermsAndConditionCheckBox(){ return driver.findElement(By.xpath("//label[@for='edit-terms-and-conditions']"));}
    private WebElement getCreationAndMaintenanceConsentCheckBox(){
        return driver.findElement(By.id("edit-field-account-consent-und"));
    }
    private WebElement saveButton(){
        return button("Save");
    }
}
