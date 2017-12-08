package pageObjects.SP.communityPages;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import selenium.SeleniumBase;

import org.apache.log4j.Logger;

public class InstitutionPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public InstitutionPageImpl() { logger = Logger.getLogger(InstitutionPageImpl.class);    }

    public void goToHubsPage(String collegeName){
        waitUntilPageFinishLoading();
        communityFrame();
        link("Additional info").click();
        waitUntilPageFinishLoading();
        link("VIEW NAVIANCE COLLEGE PROFILE").click();
        waitUntilPageFinishLoading();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        waitUntilPageFinishLoading();
        try{
            waitUntil(ExpectedConditions.textToBePresentInElement(collageNameLabel(),collegeName));
        }catch(Exception e){
            throw new AssertionFailedError("College Name is not displaying in Hubs View");
        }
        getDriver().switchTo().defaultContent();
    }

    //locator
    private WebElement collageNameLabel() {
        return getDriver().findElement(By.cssSelector("h1.masthead__name"));
    }
}
