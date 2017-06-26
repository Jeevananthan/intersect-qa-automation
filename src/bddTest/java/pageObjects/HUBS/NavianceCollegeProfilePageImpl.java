package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class NavianceCollegeProfilePageImpl extends PageObjectFacadeImpl{

    Logger logger = null;

    public NavianceCollegeProfilePageImpl() {
        logger = Logger.getLogger(NavianceCollegeProfilePageImpl.class);
    }

    public void openHUBSEditorMode() {
        navBar.goToCollegeProfile();
        getStartedButton().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
        waitUntilPageFinishLoading();
        logger.info("HUBS Editor Mode opened");
    }

    //Locators
    private WebElement getStartedButton() {
        return button("Get Started");
    }

}
