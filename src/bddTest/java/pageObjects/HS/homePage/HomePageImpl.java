package pageObjects.HS.homePage;

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

    public void verifyUserIsLoggedIn() {
        //Check if user element is present
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign in successfully", link(By.id("user-dropdown")).isDisplayed());
        logger.info("Logged in successfully");
    }

    public void logout() {
        driver.switchTo().defaultContent();
        userDropdown().click();
        button(By.cssSelector("i.sign.out.icon + span.text")).click();
        waitUntilPageFinishLoading();
        driver.manage().deleteAllCookies();
        Assert.assertTrue("User did not sign out", getDriver().getCurrentUrl().contains("login"));
    }

    public void goToCounselorCommunity(){
        //link(By.cssSelector("a[id='js-main-nav-home-menu-link']>span")).click();
        navBar.goToCommunity();
    }

    public void verifyTitleHS(String generalCategoryName,String pageName){

        //this function is used to verify the page title in HS app
        Assert.assertTrue("General Category Name is not displayed in the title name ",driver.findElement(By.xpath("//div[text()='"+generalCategoryName+"']")).isDisplayed());
        Assert.assertTrue("Page Name is not displayed in the title name ",driver.findElement(By.xpath("//div[text()='"+pageName+"']")).isDisplayed());
    }

    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }

}
