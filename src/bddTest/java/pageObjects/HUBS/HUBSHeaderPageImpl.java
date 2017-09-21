package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class HUBSHeaderPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HUBSHeaderPageImpl() {
        logger = Logger.getLogger(HUBSHeaderPageImpl.class);
    }

    public void clickLogOut() {
        waitUntil(ExpectedConditions.elementToBeClickable(logOutButton()));
        logOutButton().sendKeys();
        waitUntilPageFinishLoading();
        logger.info("User signed out HUBS");
    }

    //Locators

    private WebElement logOutButton() {
        return getDriver().findElement(By.cssSelector("span.fc-user-nav__student a"));
    }
}
