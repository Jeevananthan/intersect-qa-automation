package pageObjects.HE.eventsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class EventsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public EventsPageImpl() {
        logger = Logger.getLogger(EventsPageImpl.class);
    }

    public void verifyEventTitleIsPresent() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Events page is not correctly displayed", eventsTitle().isDisplayed());
    }

    //locators
    private WebElement eventsTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
}
