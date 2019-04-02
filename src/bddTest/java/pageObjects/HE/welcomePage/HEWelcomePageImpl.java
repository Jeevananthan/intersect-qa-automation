package pageObjects.HE.welcomePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

public class HEWelcomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;


    public HEWelcomePageImpl()  {
        logger = Logger.getLogger(HEWelcomePageImpl.class);}


    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    public void saveChanges() {
        logger.info("Saving changes.");
        saveBtn().click();
        waitUntilPageFinishLoading();
    }

    public void verifyRequiredPageforNewUser(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for(String tab:list){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@name='mainmenu']")));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab){
                case "Counselor Community":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-counselor-community-menu-link")));
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
                case "Events":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-am-events-menu-link")));
                    eventsMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='welcome-text']")));
                    Assert.assertTrue("Counselor Community profile form is not displayed",ccProfileForm().isDisplayed());
                    iframeExit();
                    break;
                case "ActiveMatch":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-am-plus-menu-link")));
                    activeMatchMenuLink().click();
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

    public void verifyCommunityActivationForRepVisits(){
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe._2ROBZ2Dk5vz-sbMhTR-LJ")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(communityWelcomeFormLocator()));
        Assert.assertTrue("Community Profile Welcome Page is not displaying...", communityWelcomeForm().isDisplayed());
        driver.switchTo().defaultContent();
    }

    public void clearCommunityProfile(){
        load(GetProperties.get("he.community.clear"));
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
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

    public void verifyingTabNavigation(DataTable dataTable) {
        List<String> list = dataTable.asList(String.class);
        for (String tab : list) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@name='mainmenu']")));
            navigationDropDown().sendKeys(Keys.ENTER);
            switch (tab) {
                case "Counselor Community":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-counselor-community-menu-link")));
                    counselorCommunityMenuLink().click();
                    iframeEnter();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/a[text()='Home']")));
                    Assert.assertTrue("Counselor Community profile form is not displayed", homeTabInCommunity().isDisplayed());
                    iframeExit();
                    break;
                case "RepVisits":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-rep-visits-menu-link")));
                    repVisitsMenuLink().click();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(searchButton()));
                    Assert.assertTrue("Clicking on RepVisits is not redirecting to Search and Schedule tab", getDriver().findElement(searchButton()).isDisplayed());
                    break;
                case "Events":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-am-events-menu-link")));
                    eventsMenuLink().click();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='CREATE EVENT']")));
                    Assert.assertTrue("Event button is not displayed", eventButton().isDisplayed());
                    break;
                case "ActiveMatch":
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-am-plus-menu-link")));
                    activeMatchMenuLink().click();
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Connections']")));
                    Assert.assertTrue("Connection button is not displayed", connectionsText().isDisplayed());
                    break;
                default:
                    Assert.fail("Invalid option");
            }
        }
    }

    /**
     * Goes to the Welcome Counselor community page
     */
    public void goToWelcomeCounselorCommunityPage(){
        navigationDropDown().sendKeys(Keys.ENTER);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-home-menu-link")));
        counselorCommunityMenuLink().click();
        iframeEnter();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-submit")));
    }

    /**
     * Verifies that the welcome header contains the given text
     * @param text
     */
    public void verifyWelcomeCounselorCommunityPageHeader(String text){
        softly().assertThat(welcomePageHeader().getText().contains(text));
    }

    /**
     *  Verifies that the agreements label has the given text
     * @param text
     */
    public void verifyAgreementsLabel(String text){
        softly().assertThat(agreementsLabel().getText().contains(text));
    }

    /**
     * Verifies that the edit privacy info label has the given text
     * @param text
     */
    public void verifyEditPrivacyInfoLabel(String text){
        softly().assertThat(editPrivacyInfoLabel().getText().contains(text));
    }

    private WebElement communityWelcomeForm(){ return driver.findElement(communityWelcomeFormLocator()); }

    private By communityWelcomeFormLocator(){
        return By.id("user-profile-form");
    }

    private WebElement saveBtn() {return driver.findElement(By.id("edit-submit"));}
   private WebElement navigationDropDown(){
        return driver.findElement(By.xpath("//a[@name='mainmenu']"));
    }
    private WebElement counselorCommunityMenuLink(){
        return driver.findElement(By.xpath("//dt/a/span[text()='Counselor Community']"));
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
    private WebElement eventsMenuLink(){
        return driver.findElement(By.id("js-main-nav-am-events-menu-link"));
    }
    private WebElement activeMatchMenuLink(){
        return driver.findElement(By.id("js-main-nav-am-plus-menu-link"));
    }
    private WebElement eventButton(){
        return driver.findElement(By.xpath("//span[text()='CREATE EVENT']"));
    }
    private WebElement connectionsText(){
        return driver.findElement(By.xpath("//span[text()='Connections']"));
    }
    private WebElement getSearchAndScheduleHeading(){ return text("Search"); }

    /**
     * Gets the welcome page header
     * @return Webelement
     */
    private WebElement welcomePageHeader(){
        return getDriver().findElement(By.xpath("//div[@id='featured']//p"));
    }

    /**
     * Gets the agreements label
     * @return Webelement
     */
    private WebElement agreementsLabel(){
        return getDriver().findElement(By.id("edit-privacy-info"));
    }

    /**
     * Gets the edit privacy info label
     * @return
     */
    private WebElement editPrivacyInfoLabel(){
        return getDriver().findElement(By.id("edit-cp-privacy-info"));
    }

    private By searchButton(){return By.cssSelector("button[aria-label='search-btn']");}
}
