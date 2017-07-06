package pageObjects.CM.homePage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    public void iframeEnter()  {
        //Thread.sleep(3000); //Implicitly wait does not work on Mac (should be fixed soon)
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    public void logoutHEDefault() {
        getDriver().switchTo().defaultContent();
        userDropdown().click();
        button(By.id("user-dropdown-signout")).click();
        waitUntilPageFinishLoading();
    }

    public void logoutSupport() {
        getDriver().switchTo().defaultContent();
        userDropdown().click();
        button(By.id("user-dropdown-signout")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign out",driver.findElement(By.id("login_panel")).isDisplayed());

//        driver.manage().deleteAllCookies();
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("localStorage.clear();sessionStorage.clear();");
    }

    public void verifyUserIsLoggedIn() {
        //Check if user element is present
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign in successfully",link("Profile").isDisplayed());
        logger.info("Logged in to Community successfully");
    }

    public void logoutHE() {
        getDriver().switchTo().defaultContent();
        userDropdown().click();
        button(By.id("user-dropdown-signout")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign out",button("LOGIN").isDisplayed());
    }

    public void accessCounselorCommunityPage() {
        logger.info("Going to Counselor Community page.");
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
    }

    public void checkIfHomePostsAreVisible(){
        iframeEnter();
        logger.info("Checking if there are posts on the Home page");
        Assert.assertTrue(profilePicOnPostsFeed().isDisplayed());
    }

    private WebElement getLearnMoreLink(){ return link("Learn More"); }
    private WebElement getRequestInformationButton(){ return driver.findElement(By.cssSelector("[class='ui pink button']")); }
    private WebElement userDropdown() {return button(By.id("user-dropdown"));}
    private WebElement profilePicOnPostsFeed() {return driver.findElement(By.xpath("//img[contains(@src, 'https://qa.community.hobsons.com/sites/default/files/styles/post_thumbnail/public/')]"));}

}
