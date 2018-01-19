package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class HUBSMainMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HUBSMainMenuPageImpl() {
        logger = Logger.getLogger(HUBSMainMenuPageImpl.class);
    }

    public void clickStudiesTab() {
        waitUntil(ExpectedConditions.elementToBeClickable(studiesTab()));
        studiesTab().click();
    }
    public void clickCostsTab() {
        waitUntil(ExpectedConditions.elementToBeClickable(costsTab()));
        costsTab().click();
    }

    //Locators

    private WebElement studiesTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Studies')]"));
    }
    private WebElement costsTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Costs')]"));
    }
}
