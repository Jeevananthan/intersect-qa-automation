package pageObjects.CM.homePage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import static org.junit.Assert.fail;


public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public HomePageImpl() {
        logger = Logger.getLogger(pageObjects.HE.homePage.HomePageImpl.class);
    }

    public void verifyUpgradeWidget(String visibility, String userType){
        //Wait page load function deleted.
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
                setImplicitWaitTimeout(1);
                Assert.assertFalse("New Widget Learn More is displaying for "+userType+" User", getLearnMoreLink().isDisplayed());
                resetImplicitWaitTimeout();
                driver.switchTo().defaultContent();
                break;
            default:
                fail("Wrong Visibility entered in Feature file.");
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
        iframeExit();
        userDropdown().click();
        signOutBtn().click();
        waitUntilPageFinishLoading();
//        driver.navigate().refresh();
//        driver.manage().deleteAllCookies();
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("localStorage.clear();sessionStorage.clear();");
//        driver.navigate().refresh();
    }

    public void logoutHSDefault() {
        iframeExit();
        userDropdown().click();
        signOutBtnHS().click();
        waitUntilPageFinishLoading();
//        driver.navigate().refresh();
//        driver.manage().deleteAllCookies();
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("localStorage.clear();sessionStorage.clear();");
//        driver.navigate().refresh();

    }

    public void logoutSupport() {
        getDriver().switchTo().defaultContent();
        userDropdown().click();
        button(By.id("user-dropdown-signout")).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("User did not sign out",driver.findElement(By.id("login_panel")).isDisplayed());

        driver.manage().deleteAllCookies();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("localStorage.clear();sessionStorage.clear();");
    }

    public void verifyUserIsLoggedIn() {
        waitUntilPageFinishLoading();
        getNavigationBar().goToCommunity();
        communityFrame();
        //Check if user Profile element is present
        Assert.assertTrue("User did not sign in successfully",link("Profile").isDisplayed());
        logger.info("Logged in to Community successfully");
    }

    public void verifyUserIsLoggedInSuportUser() {
        waitUntilPageFinishLoading();
        //Check if user Profile element is present
        Assert.assertTrue("User did not sign in successfully",welcomeSupportTitle().getText().contains("Welcome to the Support Application"));
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
        counselorCommunity().click();
        communityFrame();
    }

    public void accessHSCounselorCommunityPage() {
        logger.info("Going to HS Counselor Community page.");
        link(By.id("js-main-nav-home-menu-link")).click();
        communityFrame();
    }

    public void goToHomePage() {
        logger.info("Going to home page.");
        iframeExit();
        getNavigationBar().goToCommunity();
        communityFrame();
//        link(By.cssSelector("a[href='/']")).click();
    }

    public void goToHSHomePage() {
        logger.info("Going to HS home page.");
        iframeExit();
        link(By.id("js-main-nav-home-menu-link")).click();
        communityFrame();
//        link(By.cssSelector("a[href='/']")).click();
    }

    public void clickOnHighEducationTab() {
        logger.info("Going to High Education tab.");
        waitUntilPageFinishLoading();
        button(By.xpath("//*[@class='ui button _3y0_nbKvcnKh4vXrUyRAUa']")).click();
    }

    public void clickOnHomeTab() {
        logger.info("Going to home tab.");
        waitUntilPageFinishLoading();
        link(By.xpath("//*[@id='main-menu']/li[1]/a")).click();
    }

    public void checkIfHomePostsAreVisible(){
        communityFrame();
        logger.info("Checking if there are posts on the Home page");
        Assert.assertTrue(profilePicOnPostsFeed().isDisplayed());
        iframeExit();
    }

    public void checkIfHobsonsPostIsVisible() {
        logger.info("Checking if there Hobsons post is visible.");
        Assert.assertTrue(likeHobsonsPostBtn().isDisplayed());
    }

    public void makeSureHobsonsPostNotLiked() {
        logger.info("Making sure that Hobsons post is not liked.");
        try {
            driver.findElement(By.xpath("//div[@id='like-node-841']/a[@title='like this']"));
        } catch (NoSuchElementException ex)  {
            likeOrDislikeHobsonsPost();
            waitUntilPageFinishLoading();
        }
    }

    public void makeSureHobsonsPostIsLiked() {
        logger.info("Making sure that Hobsons post is liked.");
        try {
            driver.findElement(By.xpath("//div[@id='like-node-841']/a[@title='Unlike this']"));
        } catch (NoSuchElementException ex)  {
            likeOrDislikeHobsonsPost();
            waitUntilPageFinishLoading();

        }
    }

    public void likeOrDislikeHobsonsPost() {
        likeHobsonsPostBtn().click();
    }

    public void assertHobsonsPostLiked() {
        logger.info("Asserting that Hobsons post is liked.");
        Assert.assertTrue(driver.findElement(unlikePost()).isDisplayed());
    }

    public void assertHobsonsPostNotLiked() {
        logger.info("Asserting that Hobsons post is not liked.");
        Assert.assertTrue(driver.findElement(likePost()).isDisplayed());
    }

    public void writeCommentOnHobsonsPost(String commentText) {
        logger.info("Writing comment to the post.");
        driver.findElement(lastComment()).click();
        driver.findElement(commentBody()).sendKeys(commentText);
        driver.findElement(postComment()).click();
        waitUntilPageFinishLoading();
    }

    private boolean checkItemVisible(String item) {
        try {
            driver.findElement(By.xpath("//*[contains(text(), '" + item + "')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void checkIfHobsonsPostCommentIsPosted(String commentText) {
        logger.info("Checking if comment is posted.");
        Assert.assertTrue("The comment is not visible on the page!", checkItemVisible(commentText));
        deleteCreatedComment(commentText);
        waitUntilPageFinishLoading();
    }

    public void deleteCreatedComment(String commentText) {
        logger.info("Deleting created user comment.");
//        driver.findElement(By.linkText("edit")).click();
        driver.findElement(By.xpath("//p[contains(text(), '"+commentText+"')]/../../../../../ul/li[@class='comment-edit last']")).click();
        waitUntilPageFinishLoading();
        driver.findElement(By.id("edit-delete")).click();
        waitUntilPageFinishLoading();
//        waitUntilElementExists(driver.findElement(By.id("edit-delete--3")));
//        driver.findElement(By.id("edit-delete--3")).click();
    }

    public void verifyHeaderTagsInPage(String headerText, String tabName, String hTagType) {

        iframeExit();
        communityFrame();

        String[] tabs = tabName.split(">");
        for(int i = 0; i < tabs.length; i++)
        {
            driver.findElement(By.xpath("//a[text()=\"" + tabs[i].trim() + "\"]")).click();
        }

        Assert.assertTrue(driver.findElement(By.xpath("//" + hTagType + "[contains(text(),'" + headerText + "')]")).isDisplayed());
    }

    public void navigateToCounselorCommunityPage() {
        participateButton().click();
    }

    public void verifyInstructionalTextInPostBox() {
        iframeExit();
        communityFrame();

        Assert.assertTrue("Post box instructional text is not displayed correctly", postBoxInstructionalMessage().getText().contains("This post will be visible to your connections on the Counselor" +
                " Community homepage and to anyone that visits your profile. " +
                "Looking to post in a group instead? Go to Your Groups"));

        Assert.assertTrue("'Your Groups' link is not displayed in the post boc instructional text", yourGroupsLink().isDisplayed());
    }

    private WebElement getLearnMoreLink(){ return button ("Learn More"); }
    private WebElement getRequestInformationButton(){ return driver.findElement(By.cssSelector("[class='ui pink button']")); }
    private WebElement userDropdown() {return driver.findElement(By.id("user-dropdown"));}
    private WebElement signOutBtn() {return driver.findElement(By.id("user-dropdown-signout"));}
    private WebElement signOutBtnHS() {return driver.findElement(By.cssSelector("i[class='sign out icon']"));}
    private WebElement profilePicOnPostsFeed() {return driver.findElement(By.xpath("//img[contains(@src, 'https://qa.community.hobsons.com/sites/default/files/styles/post_thumbnail/public/')]"));}
    private WebElement likeHobsonsPostBtn() {return  driver.findElement(By.xpath("(//a[@class='use-ajax like-btn ajax-processed reply-processed'])[1]"));}
    private WebElement participateButton() {return driver.findElement(By.xpath("//a[text()='Participate']"));}
    private WebElement postBoxInstructionalMessage() {return driver.findElement(By.xpath("//div[@id='edit-post-instructions']"));}
    private WebElement yourGroupsLink() {return driver.findElement(By.xpath("//a[text()='Your Groups']"));}
    private WebElement welcomeSupportTitle() {return driver.findElement(By.xpath("//*[@id='app']/div/div/main/div/p"));}
    private WebElement counselorCommunity() {return driver.findElement(By.id("js-main-nav-counselor-community-menu-link"));}
    private By lastComment() {return By.xpath("(//*[contains(@id,'-comments-link')])[1]"); }
    private By commentBody() {return By.id("edit-comment-body"); }
    private By postComment() {return By.xpath("//*[@id='edit-save--2']");}
    private By unlikePost() {return By.xpath("//a[@title='Unlike this']");}
    private By likePost() {return By.xpath("//a[@title='like this']");}


}
