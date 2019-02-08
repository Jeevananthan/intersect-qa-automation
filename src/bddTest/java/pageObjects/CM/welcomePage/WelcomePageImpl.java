package pageObjects.CM.welcomePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

/**
 * Created by bojan on 5/30/17.
 */
public class WelcomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;


    public WelcomePageImpl()  {
        logger = Logger.getLogger(WelcomePageImpl.class);}


    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    private void destroyHEUser()  {
        logger.info("Destroying user.");
        driver.navigate().to("https://qa-he.intersect.hobsons.com/counselor-community/cp-test/destroy-user");
        waitForUITransition();
    }

    private void destroyHSUser()  {
        logger.info("Destroying user.");
        driver.navigate().to("https://qa-hs.intersect.hobsons.com/community/cp-test/destroy-user");
    }


    public void makeSureHEWelcomeScreenIsOpened() {
        logger.info("Making sure that Welcome screen will be opened when user logs in for the first time.");

        homeLink().click();
        counselorLink().click();
        communityFrame();

        if (driver.findElements(By.xpath("//*[contains(text(), 'Welcome to the Counselor Community!')]")).size() != 0) {

            iframeExit();
        }
        else {
            destroyHEUser();
            iframeExit();
        }

    }

    public void makeSureHSWelcomeScreenIsOpened() {
        logger.info("Making sure that Welcome screen will be opened when user logs in for the first time.");
        link(By.id("js-main-nav-home-menu-link")).click();
        communityFrame();

        if (driver.findElements(By.xpath("//*[contains(text(), 'Welcome to the Counselor Community!')]")).size() != 0) {

            iframeExit();
        }
        else {
            destroyHSUser();
            iframeExit();
        }

    }

    public void profileAndBannerPicturesUpload() {
        communityFrame();
        logger.info("Uploading profile picture.");
        textbox("Profile picture").sendKeys("/Users/bojan/Documents/IntelliJ-Projects/hcp_intersect_qa_automation/hcp_intersect_qa_automation/src/bddTest/resources/HobsonsTestPictures/lion-cartoon-roaring.jpg");
        driver.findElement(By.id("edit-field-profile-picture-und-0-upload-button")).click();
        waitUntilElementExists(driver.findElement(By.xpath("//img[contains(@src, 'lion-cartoon-roaring')]")));

        logger.info("Uploading profile banner.");
        textbox("Profile Banner").sendKeys("/Users/bojan/Documents/IntelliJ-Projects/hcp_intersect_qa_automation/hcp_intersect_qa_automation/src/bddTest/resources/HobsonsTestPictures/seleniumbanner.jpg");
        driver.findElement(By.id("edit-field-profile-banner-und-0-upload-button")).click();
        waitUntilElementExists(driver.findElement(By.xpath("//img[contains(@src, 'seleniumbanner')]")));
    }

    public void popupateWelcomeUserForm() {
        waitUntil(ExpectedConditions.visibilityOf(getWelcomeToCounselorCommunityLabel()));
        logger.info("Populating welcome user form.");
        emailTextbox().sendKeys("testemail@personal.com");
        driver.findElement(By.id("edit-field-office-phone-und-0-value")).sendKeys("+12161234567"); //could not properly locate mandatory fields by using texbox method
        mobilePhoneTextbox().sendKeys("+12167654321");
        generalDesTextbox().sendKeys("Lorem ipsum dolor sit amet, integre verterem interpretaris ea mea");
        driver.findElement(By.id("edit-field-job-position-und-0-value")).sendKeys("QAManager"); //could not properly locate mandatory fields by using texbox method
        headlineTextbox().sendKeys("Ad usu solet salutatus");
        bioTextbox().sendKeys("No est viderer eloquentiam, duo an veniam oblique percipit.");
    }

    public void saveChanges() {
        logger.info("Saving changes.");
        saveBtn().click();
        waitUntilPageFinishLoading();
    }

    public void assertFieldsUneditable() {
        communityFrame();
        logger.info("Checking if fields are uneditable (firstname, lastname, institution and work email).");
        Assert.assertEquals(driver.findElement(By.id("edit-field-first-name-und-0-value")).getAttribute("disabled"), "true");
        Assert.assertEquals(driver.findElement(By.id("edit-field-last-name-und-0-value")).getAttribute("disabled"), "true");
        Assert.assertEquals(driver.findElement(By.id("edit-field-user-institution-und-0-target-id")).getAttribute("disabled"), "true");
        Assert.assertEquals(driver.findElement(By.id("edit-field-contact-work-und-0-value")).getAttribute("disabled"), "true");
    }

    public void assertUserProfileFieldsPopulated() {
        waitUntilPageFinishLoading();
        logger.info("Checking if user fields are populated successfully.");
        //Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src, 'lion-cartoon-roaring')]")).isDisplayed());
        //Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src, 'seleniumbanner')]")).isDisplayed());

        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'testemail@personal.com')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), '+12161234567')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), '+12167654321')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Lorem ipsum dolor sit amet, integre verterem interpretaris ea mea')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'QAManager')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Ad usu solet salutatus')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'No est viderer eloquentiam, duo an veniam oblique percipit.')]")).isDisplayed());

    }

    public WebElement acceptTermsAndConditions() {
        logger.info("Accepting Terms and Conditions.");
        WebElement hiddenWebElement = driver.findElement(By.id("edit-terms-and-conditions"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",hiddenWebElement);
        return hiddenWebElement;
    }

    public void checkFieldsPublic() {
        logger.info("Checking if fields are public.");
        Assert.assertEquals("Work Email is not public by default.", "true", privacyWorkEmail("public").getAttribute("selected"));
        Assert.assertEquals("Personal Email is not public by default.", "true", privacyPersonalEmail("public").getAttribute("selected"));
        Assert.assertEquals("Office Phone is not public by default.", "true", privacyOfficePhone("public").getAttribute("selected"));
        Assert.assertEquals("Mobile Phone is not public by default.", "true", privacyMobilePhone("public").getAttribute("selected"));
        Assert.assertEquals("Alma Meter is not public by default.", "true", privacyAlmaMeter("public").getAttribute("selected"));
    }

    public void setPrivacyToConnectionsOnly() {
        logger.info("Setting privacy to connections only.");
        privacyWorkEmail("connections").click();
        privacyOfficePhone("connections").click();
    }

    public void setPrivacyToVisibleToOnlyMe() {
        logger.info("Setting privacy to Visible to Only Me.");
        privacyPersonalEmail("private").click();
        privacyMobilePhone("private").click();
    }

    public void checkPrivacySettingsSaved() {
        logger.info("Checking if privacy settings are saved properly.");
        Assert.assertEquals("Work Email privacy is not set properly.", "true", privacyWorkEmail("connections").getAttribute("selected"));
        Assert.assertEquals("Office Phone privacy is not set properly.", "true", privacyOfficePhone("connections").getAttribute("selected"));
        Assert.assertEquals("Personal Email privacy is not set properly.", "true", privacyPersonalEmail("private").getAttribute("selected"));
        Assert.assertEquals("Mobile Phone privacy is not set properly.", "true", privacyMobilePhone("private").getAttribute("selected"));
        Assert.assertEquals("Alma Meter privacy is not set properly.", "true", privacyAlmaMeter("public").getAttribute("selected"));
    }

    /**
     * Clicks on consent creation and maintenance of Intersect Account
     */
    public void consentCreateAndMaintainIntersectAccount(){
        WebElement hiddenWebElement = driver.findElement(By.id("edit-field-account-consent-und"));
        driver.executeScript("arguments[0].click()",hiddenWebElement);
    }

    /***
     * Sets the eu citizen radio button
     * @param euCitizen, to be set, it can be Yes or No
     */
    public void setEuCitizen(String euCitizen){
        driver.findElement(By.xpath(String.format(
                "//label[@for='edit-field-eu-citizen-und']/following-sibling::div/div/label[text()='%s ']",
                euCitizen))).click();
    }

    public void verifyCommunityActivationForRepVisits(){
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe._2ROBZ2Dk5vz-sbMhTR-LJ")));
        Assert.assertTrue("Community Profile Welcome Page is not displaying...", communityWelcomeForm().isDisplayed());
        driver.switchTo().defaultContent();
    }

    public void verifyRequiredPageforNewUser(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for(String tab:list){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@name='mainmenu']")));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab){
                case "Counselor Community":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-home-menu-link")));
                    counselorCommunityMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='welcome-text']")));
                    Assert.assertTrue("Counselor Community profile form is not displayed",ccProfileForm().isDisplayed());
                    iframeExit();
                    break;
                case "RepVisits":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-rep-visits-menu-link")));
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

    public void verifyRequiredFieldsInCCProfileForm(DataTable dataTable){
        saveButtonInCommunity().click();
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        for(String fields:list){
            Assert.assertTrue(fields+" is not displayed",text(fields).isDisplayed());
        }
        iframeExit();
    }

    public void goToWelcomeCounselorCommunityPage(){
        navigationDropDown().sendKeys(Keys.ENTER);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-home-menu-link")));
        counselorCommunityMenuLink().click();
        iframeEnter();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-submit")));
    }

    public void verifyingTabNavigation(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for(String tab:list){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@name='mainmenu']")));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab) {
                case "Counselor Community":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-home-menu-link")));
                    counselorCommunityMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/a[text()='Home']")));
                    Assert.assertTrue("Counselor Community profile form is not displayed",homeTabInCommunity().isDisplayed());
                    iframeExit();
                    break;
                case "RepVisits":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-rep-visits-menu-link")));
                    repVisitsMenuLink().click();
                    waitUntilElementExists(calendarPage());
                    Assert.assertTrue("Search and schedule tab is not displayed",calendarPage().isDisplayed());
                    break;
                default:
                    Assert.fail("Invalid option");
            }}
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
        getNavigationBar().goToRepVisits();
    }

    public void clearCommunityProfile(){
        load(GetProperties.get("hs.community.clear"));
        waitUntilPageFinishLoading();
    }


    private WebElement communityWelcomeForm(){ return driver.findElement(By.id("user-profile-form")); }
    private WebElement emailTextbox() {
        return textbox("Personal Email");
    }
    private WebElement mobilePhoneTextbox() {
        return textbox("Mobile Phone");
    }
    private WebElement generalDesTextbox() {
        return textbox("General description");
    }
    private WebElement headlineTextbox() {
        return textbox("Headline");
    }
    private WebElement bioTextbox() {
        return textbox("Bio");
    }
    private WebElement saveBtn() {return driver.findElement(By.id("edit-submit"));}
    private WebElement privacyWorkEmail(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-contact-work-privacy']/option[@value='"+privacy+"']"));}
    private WebElement privacyPersonalEmail(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-contact-personal-privacy']/option[@value='"+privacy+"']"));}
    private WebElement privacyOfficePhone(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-office-phone-privacy']/option[@value='"+privacy+"']"));}
    private WebElement privacyMobilePhone(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-mobile-phone-privacy']/option[@value='"+privacy+"']"));}
    private WebElement privacyAlmaMeter(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-alma-mater-privacy']/option[@value='"+privacy+"']"));}
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
    private WebElement calendarPage(){
        return driver.findElement(By.xpath("//div[text()='Calendars']"));
    }
    private WebElement ccProfileForm(){
        return driver.findElement(By.xpath("//div/h1[text()='Welcome to the Counselor Community!']"));
    }
    /**
     * Gets the welcome to counselor community label
     * @return WebElement
     */
    private WebElement getWelcomeToCounselorCommunityLabel(){
        return text("Welcome to the Counselor Community!");
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
    private WebElement eventsMenuLink(){
        return driver.findElement(By.id("js-main-nav-am-events-menu-link"));
    }
    private WebElement activeMatchMenuLink(){
        return driver.findElement(By.id("js-main-nav-am-plus-menu-link"));
    }
    private WebElement homeLink(){ return driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div/header/nav/a[2]/div/div[1]")); }
    private WebElement counselorLink() { return driver.findElement(By.id("js-main-nav-counselor-community-menu-link")); }
}
