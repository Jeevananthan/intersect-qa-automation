package pageObjects.HE.activeMatchPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class ActiveMatchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public ActiveMatchPageImpl() {
        logger = Logger.getLogger(ActiveMatchPageImpl.class);
    }

    public void verifyTitleIsPresent() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Active Match page is not correctly displayed", activeMatchTitle().isDisplayed());
    }

    //locators
    private WebElement activeMatchTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
}
