package pageObjects.HE.homePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.fail;

public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public HomePageImpl() {
        logger = Logger.getLogger(HomePageImpl.class);
    }

    public void verifyUserIsLoggedIn() {
        //Check if user element is present
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign in successfully",link(By.id("user-dropdown")).isDisplayed());
        logger.info("Logged in successfully");
    }

    public void logout() {
        userDropdown().click();
        button(By.id("user-dropdown-signout")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign out",button("LOGIN").isDisplayed());
    }

    public void accountSettings() {
        // This is needed to "reset" the Account Settings page if we just made changes to the account.
        link(By.id("js-main-nav-home-menu-link")).click();
        userDropdown().click();
        button(By.id("user-dropdown-change-profile")).click();
        Assert.assertTrue("User was not taken to Account Settings screen",button("SAVE").isDisplayed());
    }

    public void updateProfile() {
        // This line should not be needed.  Current flow is broken.
        link(By.id("js-main-nav-community-menu-link")).click();

        userDropdown().click();
        button(By.id("user-dropdown-update-profile")).click();
        ensureWeAreOnUpdateProfilePage();
    }

    public void verifyAccountSettings(DataTable data) {
        accountSettings();
        Map<String,String> entity = data.asMap(String.class,String.class);
        Assert.assertTrue(link("update your profile?").isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("[value=\"" + entity.get("E-mail Address") + "\"]")).isDisplayed());
        Assert.assertTrue(textbox("Current Password").isDisplayed());
        Assert.assertTrue(textbox("New Password").isDisplayed());
        Assert.assertTrue(textbox("Confirm Password").isDisplayed());

        // Verify that Update your profile? link works as expected
        link("update your profile?").click();
        waitUntilPageFinishLoading();
        ensureWeAreOnUpdateProfilePage();
        driver.switchTo().defaultContent();
    }

    private void ensureWeAreOnUpdateProfilePage() {
        // Go into Community Frame
        getCommunityFrame();

        // This line should not be needed.  Current flow is broken.
        link("EDIT PROFILE").click();

        Assert.assertTrue("User was not taken to Update Profile screen",link("Back to Profile").isDisplayed());
    }

    public void verifyUpdateProfile(DataTable data) {
        updateProfile();
        Map<String,String> entity = data.asMap(String.class,String.class);
        Assert.assertTrue(driver.findElement(By.cssSelector("[value=\"" + entity.get("E-mail Address") + "\"]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("[value=\"" + entity.get("First Name") + "\"]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("[value=\"" + entity.get("Last Name") + "\"]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("[value=\"" + entity.get("Your institution") + "\"]")).isDisplayed());
        Assert.assertTrue(textbox("Personal Email").isDisplayed());
        Assert.assertTrue(textbox("Office Phone").isDisplayed());
        Assert.assertTrue(textbox("Mobile Phone").isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("edit-cp-states-field-general-description-und-0-value")).isDisplayed());

        // Scroll to the end of the form
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.id("edit-field-bio-und-0-value"))).perform();

        Assert.assertTrue(driver.findElement(By.id("edit-cp-states-0-state")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("edit-field-job-position")).isDisplayed());
        Assert.assertTrue(textbox("Headline").isDisplayed());
        Assert.assertTrue(textbox("Alma Mater").isDisplayed());
        // Drop back to parent Frame
        getDriver().switchTo().defaultContent();
    }

    public void verifyCommunityUpgradeMessage() {
        navBar.goToHome();
       try {
           Assert.assertTrue(driver.findElement(By.id("upgrade-message")).isDisplayed());
           Assert.assertTrue("Expected message for the new widget was not found!"
                   ,text( "You currently have limited access to the Counselor Community.").isDisplayed());
           Assert.assertTrue("Expected message for the new widget was not found!"
                   ,text("Upgrade today to search, connect, message, and collaborate within the Counselor Community.").isDisplayed());

        } catch (Exception e) {
            logger.info("Exception while verifying new Widget: " + e.getMessage());
            e.printStackTrace();
            fail("The new Widget was not found.");
        }
    }

    public void accessHelpPage() {
        link(By.id("js-main-nav-help-menu-link")).click();
        String heWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.contains(heWindow)) {
                driver.switchTo().window(window);
                Assert.assertTrue("Did not end up on Helpsite URL!", driver.getCurrentUrl().contains("helpsite.hobsons.com/Intersect"));
                // Close the tab
                driver.close();
                driver.switchTo().window(heWindow);
            }
        }
    }

    //locators
    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }

    private void getCommunityFrame() { getDriver().switchTo().frame(driver.findElement(By.tagName("iframe"))); }
}
