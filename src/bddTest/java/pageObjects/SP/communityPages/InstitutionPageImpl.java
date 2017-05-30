package pageObjects.SP.communityPages;

import org.junit.Assert;
import org.openqa.selenium.By;
import selenium.SeleniumBase;

import org.apache.log4j.Logger;

public class InstitutionPageImpl extends SeleniumBase {
    private Logger logger;

    public InstitutionPageImpl() { logger = Logger.getLogger(InstitutionPageImpl.class);    }

    public void goToHubsPage(String collegeName){
        getDriver().switchTo().frame(driver.findElement(By.tagName("iframe")));
        link("Additional info").click();
        link("VIEW NAVIANCE COLLEGE PROFILE").click();
        getDriver().switchTo().defaultContent();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        Assert.assertTrue("College Name is not displaying in Hubs View", text(collegeName).isDisplayed());
        getDriver().switchTo().defaultContent();
    }
}
