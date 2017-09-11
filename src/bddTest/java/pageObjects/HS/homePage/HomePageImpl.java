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
        button(By.id("user-dropdown-signout")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign out", button("Login").isDisplayed());
    }

    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }
}
