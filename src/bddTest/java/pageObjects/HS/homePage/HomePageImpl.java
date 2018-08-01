package pageObjects.HS.homePage;

import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

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
        waitUntilPageFinishLoading();
        userDropdown().click();
        button(By.cssSelector("i.sign.out.icon + span.text")).click();
        waitUntilPageFinishLoading();
        driver.manage().deleteAllCookies();
        Assert.assertTrue("User did not sign out", getDriver().getCurrentUrl().contains("login"));
    }

    public void goToCounselorCommunity(){
        //link(By.cssSelector("a[id='js-main-nav-home-menu-link']>span")).click();
        navigationBar.goToCommunity();
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
        Assert.assertTrue("College is not displayed",driver.findElement(By.xpath("//div/h2[text()='"+college+"" +" test'" +"]")).isDisplayed());
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
        return button(By.id("user-dropdown"));
    }

    private WebElement collageNameLabel() {
        return getDriver().findElement(By.cssSelector("h1.masthead__name"));
    }
}
