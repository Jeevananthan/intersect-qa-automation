package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class NavianceCollegeProfilePageImpl extends PageObjectFacadeImpl{

    Logger logger = null;

    public NavianceCollegeProfilePageImpl() {
        logger = Logger.getLogger(NavianceCollegeProfilePageImpl.class);
    }

    public void openHUBSEditorMode() {
        getNavigationBar().goToCollegeProfile();
        getStartedButton().click();
        waitUntilPageFinishLoading();
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        waitUntilPageFinishLoading();
    }

    //Locators
    private WebElement getStartedButton() {
        return button("Get Started");
    }

}
