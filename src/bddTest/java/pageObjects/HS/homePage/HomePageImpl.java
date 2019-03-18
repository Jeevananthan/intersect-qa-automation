package pageObjects.HS.homePage;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.welcomePage.HEWelcomePageImpl;
import utilities.GetProperties;

import java.text.SimpleDateFormat;
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
        Assert.assertTrue("User did not log in successfully", link(By.id("user-dropdown")).isDisplayed());
        logger.info("Logged in successfully");
    }

    public void logout() {
        driver.switchTo().defaultContent();
        waitUntil(ExpectedConditions.elementToBeClickable(userDropdown()));
        userDropdown().click();
        button(By.cssSelector("i.sign.out.icon + span.text")).click();
        waitUntilPageFinishLoading();
        driver.manage().deleteAllCookies();
        waitUntil(ExpectedConditions.elementToBeClickable(loginButton()));
        waitUntilPageFinishLoading();
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
        String expectedURL = additionalInfoURL+institutionID+info;
        link(backToIntersect).click();
        waitUntilPageFinishLoading();
        String currentURL = driver.getCurrentUrl();
        softly().assertThat(currentURL).as("URL").isEqualTo(expectedURL);
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
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © "+currentYear+", Hobsons Inc.']")).isDisplayed());
        openNavianceLoginPage();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()=' Copyright © "+currentYear+"']/span[contains(text(),'Hobsons Inc')]")).isDisplayed());
    }

    public void verifyHeaderInProductAnnouncementsReadMoreDrawer(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(productAnnouncementsReadMore()));
        productAnnouncementsReadMoreButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(productAnnouncementsHeader()));
        Assert.assertTrue("Product announcements 'header' is not displayed",productAnnouncementsReadMoreHeader().isDisplayed());
    }

    public void verifyCloseButtonInProductAnnouncementsReadMoreDrawer(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(productAnnouncementsReadMoreClose()));
        Assert.assertTrue("Product announcements 'Close' button is not displayed",productAnnouncementsReadMoreCloseButton().isDisplayed());
    }

    public void verifyTitleInProductAnnouncementsReadMoreDrawer(String productAnnouncementsTitle){
        Assert.assertTrue("Product announcements 'Title' is not displayed",productAnnouncementsReadMoreTitle(productAnnouncementsTitle).isDisplayed());
    }

    public void verifyDateFormatInProductAnnouncementsReadMoreDrawer(String productAnnouncementsTitle,String dateFormat){
        String date = selectDateForProductAnnouncements("0",dateFormat);
        Assert.assertTrue("Product announcements 'Date' is not displayed",productAnnouncementsReadMoreDate(productAnnouncementsTitle,date).isDisplayed());
    }

    public void verifyContentInProductAnnouncementsReadMoreDrawer(String content){
        String actualContent = getProductAnnouncementsContent().getText();
        Assert.assertTrue("Product announcements 'content' is not equal",actualContent.equals(content));
    }

    public void clickCloseButtonInProductAnnouncementsReadMoreDrawer(){
        productAnnouncementsReadMoreCloseButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(productAnnouncementsReadMore()));
    }

    public String selectDateForProductAnnouncements(String addDays,String format) {
        String DATE_FORMAT_NOW = format;
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    /**
     *
     * Logout from the naviance page
     */
    public void logoutFromNaviance(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//i[@class='icon-cog']"))).build().perform();
        link("Logout").click();
        waitUntilPageFinishLoading();
        driver.manage().deleteAllCookies();
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
//        String currentYear = getCurrentYear();
        Assert.assertTrue("hobsons logo is not displayed",logo().isDisplayed());
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//span[text()='Copyright © 2018 ']/parent::span/following-sibling::span[text()='Hobsons']")).isDisplayed());
        driver.close();
        driver.switchTo().window(navianceWindow);
        waitUntilPageFinishLoading();
    }

    public void verifyYearInRepVisitsPage(){
        String currentYear = getCurrentYear();
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

    /**
     * This method is for clearing the HS community profile for any user.
     */
    public void clearHSCommunityProfile(){
        load(GetProperties.get("hs.community.clear"));
        waitUntilPageFinishLoading();
    }

    /**
     * This method is checking the Community Welcome page, which we used to get whenever community profile is not filled
     */
    public void verifyHSCommunityActivationForRepVisits(){
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe._2ROBZ2Dk5vz-sbMhTR-LJ")));
        getDriver().navigate().refresh();
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(communityWelcomePage()));
        Assert.assertTrue("Community Profile Welcome Page is not displaying...", getDriver().findElement(communityWelcomePage()).isDisplayed());
        driver.switchTo().defaultContent();
    }

    /**
     * This method will check the Community welcome page existence whenever any user try to access Counselor Community
     * and RepVisits before completing the Community profile.
     * @param dataTable
     */
    public void verifyRequiredPageforNewUser(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for(String tab:list){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@name='mainmenu']")));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab){
                case "Counselor Community":
                    iframeEnter();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='welcome-text']")));
                    Assert.assertTrue("Counselor Community profile form is not displayed",ccProfileForm().isDisplayed());
                    iframeExit();
                    break;
                case "RepVisits":
                    repVisitsMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='welcome-text']")));
                    Assert.assertTrue("Counselor Community profile form is not displayed",ccProfileForm().isDisplayed());
                    iframeExit();
                    break;
                default:
                    Assert.fail("Invalid option");
            }
        }
    }

    /**
     * This method will check the all mandatory fields which we need to fill for community profile activation.
     * @param dataTable
     */
    public void verifyRequiredFieldsInCCProfileFormForHS(DataTable dataTable){
        navigationDropDown().sendKeys(Keys.ENTER);
        iframeEnter();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-submit")));
        saveButtonInCommunity().click();
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        for(String fields:list){
            Assert.assertTrue(fields+" is not displayed",text(fields).isDisplayed());
        }
        iframeExit();
    }

    /**
     * This method will fill all the mandatory fields which require to activate community profile.
     * @param OfficePhone
     * @param JobTitle
     * @param euCitizen - valid values: Yes, No
     */
    public void fillCommunityWelcomeMandatoryFieldsForHS(String OfficePhone, String JobTitle, String euCitizen){
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
        getNavigationBar().goToRepVisits();
    }

    public void verifyRepVisitsLandingPageForHS(){
        getNavigationBar().goToRepVisits();
        waitUntilElementExists(getSearchAndScheduleHeading());
        Assert.assertTrue("Clicking on RepVisits is not redirecting to Search and Schedule tab", getSearchAndScheduleHeading().isDisplayed());
    }


    //Locators
    private WebElement getSearchAndScheduleHeading(){ return text("Search"); }
    private WebElement saveButton(){
        return button("Save");
    }
    private WebElement getCreationAndMaintenanceConsentCheckBox(){
        return driver.findElement(By.id("edit-field-account-consent-und"));
    }
    private WebElement getTermsAndConditionCheckBox(){ return driver.findElement(By.xpath("//label[@for='edit-terms-and-conditions']"));}

    private WebElement getJobTitle(){ return driver.findElement(By.id("edit-field-job-position-und-0-value"));}

    private WebElement getofficePhone() { return driver.findElement(By.id("edit-field-office-phone-und-0-value"));}

    private WebElement saveButtonInCommunity(){
        return driver.findElement(By.id("edit-submit"));
    }

    private WebElement navigationDropDown(){
        return driver.findElement(By.xpath("//a[@name='mainmenu']"));
    }
    private WebElement counselorCommunityMenuLink(){
        return driver.findElement(By.xpath("//dt/a/span[text()='Counselor Community']"));
    }
    private WebElement ccProfileForm(){
        return driver.findElement(By.xpath("//div/h1[text()='Welcome to the Counselor Community!']"));
    }
    private WebElement repVisitsMenuLink(){
        return driver.findElement(By.id("js-main-nav-rep-visits-menu-link"));
    }
    private By communityWelcomeForm(){ return By.id("user-profile-form"); }
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
    private WebElement productAnnouncementsReadMoreButton(){
        return getDriver().findElement(By.id("read-more-announcement-button"));
    }
    private WebElement productAnnouncementsReadMoreHeader() {
        return getDriver().findElement(By.xpath("//div[text()='Product Announcement']"));
    }
    private WebElement productAnnouncementsReadMoreCloseButton(){
        return getDriver().findElement(By.xpath("//div[text()='Product Announcement']/parent::div/preceding-sibling::button/i"));
    }
    private WebElement productAnnouncementsReadMoreTitle(String title){
        return getDriver().findElement(By.xpath("//div[text()='"+title+"']"));
    }
    private WebElement productAnnouncementsReadMoreDate(String title,String date){
        return getDriver().findElement(By.xpath("//div[text()='"+title+"']/span[text()='"+date+"']"));
    }
    private WebElement getProductAnnouncementsContent(){
        return getDriver().findElement(By.cssSelector("div[class='_15nVgLN5TXRcsMm5l81wvA']"));
    }
    private By productAnnouncementsHeader(){return By.xpath("//div[text()='Product Announcement']");}
    private By productAnnouncementsReadMore(){return By.id("read-more-announcement-button");}
    private By productAnnouncementsReadMoreClose(){ return By.xpath("//div[text()='Product Announcement']/parent::div/preceding-sibling::button/i"); }

    /**
     * @return  : return login button
     */
    private By loginButton(){
        return By.xpath("//button/span[text()='Login']");
    }

    private By communityWelcomePage(){
        return By.cssSelector("div[class='welcome-title']");
    }
}
