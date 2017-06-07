package pageObjects.CM.homePage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public HomePageImpl() {
        logger = Logger.getLogger(pageObjects.HE.homePage.HomePageImpl.class);
    }

    public void verifyUpgradeWidget(String visibility, String userType){
        navBar.goToCommunity();
        waitUntilPageFinishLoading();
        communityFrame();
        switch (visibility){
            case "visible":
                Assert.assertTrue("New Widget Learn More is not displaying for "+userType+" User", getLearnMoreLink().isDisplayed());
                getLearnMoreLink().click();
                waitUntilPageFinishLoading();
                driver.switchTo().defaultContent();
                Assert.assertTrue("The Learn More Widget overlay window is not displaying", getRequestInformationButton().isDisplayed());
                driver.findElement(By.xpath("//i[@class='close icon']")).click();
                break;
            case "not visible":
                Assert.assertFalse("New Widget Learn More is displaying for "+userType+" User", getLearnMoreLink().isDisplayed());
                driver.switchTo().defaultContent();
                break;
            default:
                logger.info("Wrong Visibility entered in Feature file.");
        }
    }

    private WebElement getLearnMoreLink(){ return link("Learn More"); }
    private WebElement getRequestInformationButton(){ return driver.findElement(By.cssSelector("[class='ui pink button']")); }
}
