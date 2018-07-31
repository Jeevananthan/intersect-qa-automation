package pageObjects.SP.communityPages;

import junit.framework.AssertionFailedError;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import org.apache.log4j.Logger;


public class InstitutionPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public InstitutionPageImpl() { logger = Logger.getLogger(InstitutionPageImpl.class);    }

    public void goToHubsPage(String collegeName){
        waitUntilPageFinishLoading();
        communityFrame();
        WebElement additionalLink = driver.findElement(By.xpath(("//li/a[text()='Additional info']")));
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//li/a[text()='Additional info']"),1));
        additionalLink.click();
        waitUntilPageFinishLoading();
        WebElement viewNavianceCollegeProfile = link("VIEW NAVIANCE COLLEGE PROFILE");
        waitUntil(ExpectedConditions.visibilityOf(viewNavianceCollegeProfile));
        viewNavianceCollegeProfile.click();
        waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        waitForUITransition();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        waitForUITransition();
        try{
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("h1.masthead__name"), 1));
            waitUntil(ExpectedConditions.textToBePresentInElement(collageNameLabel(),collegeName));
        }catch(Exception e){
            logger.info("Caught Exception: " + e.getMessage());
            getDriver().switchTo().defaultContent();
            throw new AssertionFailedError("College Name is not displaying in Hubs View");
        }
        getDriver().switchTo().defaultContent();
    }
    //locator
        private WebElement collageNameLabel() {
            return getDriver().findElement(By.cssSelector("h1.masthead__name"));
        }
}
