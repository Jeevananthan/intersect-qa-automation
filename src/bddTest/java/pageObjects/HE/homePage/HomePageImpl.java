package pageObjects.HE.homePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;
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
        driver.switchTo().defaultContent();
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
        navBar.goToCommunity();

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
        communityFrame();

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

    public void accessFreemiumLearnMoreOption() {
        WebElement CounselorCommunity = driver.findElement(By.id("upgrade-message"));
        waitUntilElementExists(CounselorCommunity);
        Assert.assertTrue("Learn More button is not displayed",driver.findElement(By.cssSelector("button[class='ui small inverted button _2RAreSxYNRmsb8gR5pSnkq']")).isDisplayed());
        button(By.cssSelector("button[class='ui small inverted button _2RAreSxYNRmsb8gR5pSnkq']")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyFullBenefitsofCounselorCommunity(DataTable dataTable) {
        WebElement counselorCommunity = driver.findElement(By.id("upgrade-form"));
        waitUntilElementExists(counselorCommunity);
        //verify the Title of the pop-up is displayed
        Assert.assertTrue("'Experience the full benefits of the Counselor Community' Pop-up title is not displayed",text("Experience the full benefits of the Counselor Community").isDisplayed());
        //verify the Image and followed by the label is displayed
        Assert.assertTrue("Image for the 'Connect with high school counselors'",driver.findElement(By.cssSelector("img[alt='Connect with high school counselors']")).isDisplayed());
        Assert.assertTrue("Text 'Connect with high school counselors' is not displayed",text("Connect with and message high school counselors").isDisplayed());
        Assert.assertTrue("Image for the 'Create unlimited staff accounts'",driver.findElement(By.cssSelector("img[alt='Unlimited staff accounts']")).isDisplayed());
        Assert.assertTrue("Text 'Create unlimited staff accounts' is not displayed",text("Create unlimited staff accounts").isDisplayed());
        Assert.assertTrue("Image for the 'See who is following your institution'",driver.findElement(By.cssSelector("img[alt='See who is following']")).isDisplayed());
        Assert.assertTrue("Text 'See who is following your institution' is not displayed",text("See who is following your institution").isDisplayed());
        Assert.assertTrue("Image for the 'Utilize advanced search capabilities'",driver.findElement(By.cssSelector("img[alt='Search capabilities']")).isDisplayed());
        Assert.assertTrue("Text 'Utilize advanced search capabilities' is not displayed",text("Utilize advanced search capabilities").isDisplayed());
        Assert.assertTrue("Image for the 'Join groups and collaborate within the Community'",driver.findElement(By.cssSelector("img[alt='Join groups and collaborate']")).isDisplayed());
        Assert.assertTrue("Text 'Join groups and collaborate within the Community' is not displayed",text("Join groups and collaborate within the Community").isDisplayed());
        //verify the contact information of the user
        Assert.assertTrue("Header Text 'Verify Your Contact Information' is not displayed",text("Verify Your Contact Information").isDisplayed());
        Assert.assertTrue("Label 'Last Name' is not displayed",text("Last Name").isDisplayed());
        Assert.assertTrue("Label 'Work Email Address' is not displayed",text("Work Email Address").isDisplayed());
        Assert.assertTrue("Label 'Phone' is not displayed",text("Phone").isDisplayed());
        Assert.assertTrue("Label 'School / Institution Name' is not displayed",text("School / Institution Name").isDisplayed());
        Assert.assertTrue("Label 'Message' is not displayed",driver.findElement(By.id("field18")).findElement(By.xpath("//span[contains(text(),'Message')]")).isDisplayed());
        Assert.assertTrue("Receive Hobsons Communications Checkbox",driver.findElement(By.id("field20")).isEnabled());
        Assert.assertTrue("Receive Hobsons Communication Text",text("Receive Hobsons Communications").isDisplayed());
        Assert.assertTrue("Request Information",button("Request Information").isDisplayed());

        List<Map<String,String>> entities = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> CounselorCommunity : entities ) {
            for (String key : CounselorCommunity.keySet()) {
                switch (key) {
                    case "First Name":
                        String actualFirstName = driver.findElement(By.id("field13")).getAttribute("value");
                        Assert.assertTrue("First Name was not as expected.", actualFirstName.contains(CounselorCommunity.get(key)));
                        break;
                    case "Last Name":
                        String actualLastName = driver.findElement(By.id("field14")).getAttribute("value");
                        Assert.assertTrue("Last Name was not as expected.", actualLastName.equals(CounselorCommunity.get(key)));
                        break;
                    case "Work Email Address":
                        String actualEmailAddress = driver.findElement(By.id("field12")).getAttribute("value");
                        Assert.assertTrue("Work Email Address was not as expected.", actualEmailAddress.equals(CounselorCommunity.get(key)));
                        break;
                    case "Phone":
                        String actualPhone = driver.findElement(By.id("field15")).getAttribute("value");
                        Assert.assertTrue("Phone was not as expected.", actualPhone.equals(CounselorCommunity.get(key)));
                        break;
                    case "School / Institution Name":
                        String actualSchoolInstitutionName = driver.findElement(By.id("field16")).getAttribute("value");
                        Assert.assertTrue("School / Institution Name was not as expected.", actualSchoolInstitutionName.equals(CounselorCommunity.get(key)));
                        break;
                    case "Message":
                        String actualMessage = driver.findElement(By.id("field18")).getText();
                        Assert.assertTrue("Messages was not as expected.", actualMessage.equals(CounselorCommunity.get(key)));
                        break;
                    }
                }
            }
        }

    public void accessCounselorCommunity() {
        button("Request Information").click();
    }

    public void verifyRequestInformation(){
        Assert.assertTrue("Thanks message",driver.findElement(By.xpath(".//*[@id='upgrade-form']//div/form/div//div/b/span[text()='Thanks!']")).isDisplayed());
        Assert.assertTrue("We will contact you soon message ", driver.findElement(By.xpath("//*[@id='upgrade-form']//div/form/div//div/p/span")).isDisplayed());


    }

    public void verifyCommunityActivationForRepVisits(){
        getRepVisitsBtn().click();
        waitUntilPageFinishLoading();
        driver.switchTo().frame(0);
        Assert.assertTrue("Community Profile Welcome Page is not displaying...", communityWelcomeForm().isDisplayed());
        driver.switchTo().defaultContent();
    }

    public void verifyWidgetIsVisible(String widgetName){

        Assert.assertTrue(widgetName+"Widget is not visible",text(widgetName).isDisplayed());
    }

    public void verifyWidgetIsNotVisible(String widgetName){

        Assert.assertFalse(widgetName+"Widget is not visible",text(widgetName).isDisplayed());
    }

    public void fillCommunityWelcomeMandatoryFields(String OfficePhone, String JobTitle){
        driver.switchTo().frame(0);
        getofficePhone().sendKeys(OfficePhone);
        getJobTitle().sendKeys(JobTitle);
        getTermsAndConditionCheckBox().click();
        button("Save").click();
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
    }

    public void clickRepVisits(){
        int count = 1;
        while (!getSearchAndScheduleHeading().isDisplayed() && count<4){
            getRepVisitsBtn().click();
            count++;
        }
        Assert.assertTrue("Clicking on RepVisits is not redirecting to Search and Schedule tab", getSearchAndScheduleHeading().isDisplayed());
    }

    //Below method is for clearing the community account to get the Community Welcome Paga.
    public void clearCommunityProfile(){
        load(GetProperties.get("he.community.clear"));
        waitUntilPageFinishLoading();
    }

    //locators
    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }
    private WebElement communityWelcomeForm(){ return driver.findElement(By.id("user-profile-form")); }
    private WebElement getRepVisitsBtn() { return link(By.id("js-main-nav-rep-visits-menu-link")); }
    private WebElement getofficePhone() { return driver.findElement(By.id("edit-field-office-phone-und-0-value"));}
    private WebElement getJobTitle(){ return driver.findElement(By.id("edit-field-job-position-und-0-value"));}
    private WebElement getTermsAndConditionCheckBox(){ return driver.findElement(By.xpath("//label[@for='edit-terms-and-conditions']"));}
    private WebElement getSearchAndScheduleHeading(){ return text("Search and Schedule"); }
}
