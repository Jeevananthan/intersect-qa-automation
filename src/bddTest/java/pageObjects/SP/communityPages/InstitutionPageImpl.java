package pageObjects.SP.communityPages;

import org.junit.Assert;
import org.openqa.selenium.By;
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
//        communityFrame();
        link("Additional info").click();
        link("VIEW NAVIANCE COLLEGE PROFILE").click();
        waitUntilPageFinishLoading();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(collageNameLabel()));
        Assert.assertTrue("College Name is not displaying in Hubs View", collageNameLabel().getText().trim().equals(collegeName));
        getDriver().switchTo().defaultContent();
    }

    //locator
    private WebElement collageNameLabel() {
        return getDriver().findElement(By.cssSelector("h1.masthead__name"));
    }
}
