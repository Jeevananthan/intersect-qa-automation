package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

public class HUBSMainMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HUBSMainMenuPageImpl() {
        logger = Logger.getLogger(HUBSMainMenuPageImpl.class);
    }

    public void clickStudiesTab() {
        studiesTab().click();
    }

    //Locators

    private WebElement studiesTab() {
        return getDriver().findElement(By.cssSelector("div.tabs.hubs-top-tabs-bar span:nth-of-type(2)"));
    }
}
