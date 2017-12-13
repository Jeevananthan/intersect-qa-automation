package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class FCMainPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public FCMainPageImpl() {
        logger = Logger.getLogger(FCMainPageImpl.class);
    }

    public void clickCollegesTab() {
        collegesTab().click();
    }

    //Locators

    private WebElement collegesTab() {
        return getDriver().findElement(By.xpath("//ul[@id='nav']/li/a[text()='colleges']"));
    }
}
