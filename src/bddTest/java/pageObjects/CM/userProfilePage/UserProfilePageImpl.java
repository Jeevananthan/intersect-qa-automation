package pageObjects.CM.userProfilePage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageObjects.CM.commonPages.PageObjectFacadeImpl;

/**
 * Created by bojan on 6/1/17.
 */
public class UserProfilePageImpl extends PageObjectFacadeImpl {

    private Logger logger;


    public UserProfilePageImpl()  {
        logger = Logger.getLogger(UserProfilePageImpl.class);}

    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    public void goToUserProfilePage() {
        logger.info("Going to user profile page.");
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        iframeEnter();
        link(By.cssSelector("a[href='/profile']")).click();
        waitUntilPageFinishLoading();
        link(By.cssSelector("a[href='/profile']")).click();
    }

    public void clickProfileTab() {
        logger.info("Going to profile tab.");
        link(By.cssSelector("a[href='/profile']")).click();
    }


    public void createNewFeedPost() {
        logger.info("Creating new user post.");
        newPostTextbox().sendKeys("Autogenerated test post.");
        driver.findElement(By.id("edit-save")).click();
    }

    public void createNewUserPost(String postText) {
        logger.info("Creating new user post.");
        newPostTextbox().sendKeys(postText);
        driver.findElement(By.id("edit-save")).click();
    }

    public void createNewRichContentPostWithHyperlink(String postText, String hyperlink) {
        logger.info("Creating new user post with hyperlink.");
        newPostTextbox().sendKeys(postText);
        addHhyperlinkBtn().click();
        addHyperlinkTextBox().sendKeys(hyperlink);
        driver.findElement(By.id("edit-save")).click();
    }

    public void checkIfUserPostIsCreated(String postText) {
        logger.info("Checking if user post is created");
        Assert.assertTrue("The post is not created!", checkItemVisible(postText));
        deleteCreatedPost();
        waitUntilPageFinishLoading();
    }

    public void checkIfUserPostIsCreatedNoDelete(String postText) {
        logger.info("Checking if user post is created");
        Assert.assertTrue("The post is not created!", checkItemVisible(postText));
        waitUntilPageFinishLoading();
    }

    public void checkIfUserPostIsDeleted(String postText) {
        logger.info("Checking if user post is deleted");
        waitUntilPageFinishLoading();
        Assert.assertFalse("The post is not deleted!", checkItemVisible(postText));
        waitUntilPageFinishLoading();
    }

    public void checkIfRichContentPostWithHyperlinkIsCreated(String postText, String hyperlink) {
        logger.info("Checking if user post is created");
        Assert.assertTrue("The post is not created!", checkItemVisible(postText));
        Assert.assertTrue("The hyperlink is not saved!", checkHyperlinkDisplayed(hyperlink));
        deleteCreatedPost();
        waitUntilPageFinishLoading();
    }

    public void checkIfRichContentPostWithVideoIsCreated(String postText) {
        logger.info("Checking if user post is created");
        Assert.assertTrue("The post is not created!", checkItemVisible(postText));
        Assert.assertTrue("The video is not saved!", checkVideoDisplayed());
        deleteCreatedPost();
        waitUntilPageFinishLoading();
    }

    private boolean checkHyperlinkDisplayed(String hyperlink) {
        try {
            driver.findElement(By.cssSelector("a[href='"+hyperlink+"']"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    private boolean checkVideoDisplayed() {
        try {
            driver.findElement(By.xpath("//iframe[contains(@src, 'QIr8Fr4MiqM')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void deleteCreatedPost() {
        logger.info("Deleting created user post.");
        editCreatedPostBtn().click();
        driver.findElement(By.id("edit-delete")).click();
        waitUntilElementExists(driver.findElement(By.id("edit-delete--3")));
        driver.findElement(By.id("edit-delete--3")).click();
    }

    public void checkIfProfilePostsAreVisible(){
        logger.info("Checking if there are posts on the Profile page");

        try {
            profilePicOnPostsFeed();
        } catch (NoSuchElementException e) {
            createNewFeedPost();
        }

        Assert.assertTrue(profilePicOnPostsFeed().isDisplayed());
    }

    public String findPostId() {
        WebElement locatePost = driver.findElement(By.xpath("//div[@class='content']/article[1]/div[contains(@id, 'anchor-')]"));
        String getAnchorId = locatePost.getAttribute("id");
        String postId = getAnchorId.substring(getAnchorId.lastIndexOf("-") + 1);
        logger.info(">>>>>>POST ID IS: " + postId);
        return postId;
    }

    public void likeOrDislikeMyOwnPost() {
        likeMyOwnPostBtn().click();
    }

    public void makeSureMyOwnPostIsLiked() {
        logger.info("Making sure that my own post is liked.");
        try {
            driver.findElement(By.xpath("//div[@id='like-node-" + findPostId() + "']/a[@title='Unlike this']"));
        } catch (NoSuchElementException ex)  {
            likeOrDislikeMyOwnPost();
            waitUntilPageFinishLoading();
        }
    }

    public void makeSureMyOwnPostIsNotLiked() {
        logger.info("Making sure that my own post is liked.");
        try {
            driver.findElement(By.xpath("//div[@id='like-node-" + findPostId() + "']/a[@title='like this']"));
        } catch (NoSuchElementException ex)  {
            likeOrDislikeMyOwnPost();
            waitUntilPageFinishLoading();
        }
    }

    public void assertMyOwnPostLiked() {
        logger.info("Asserting that my own post is liked.");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='like-node-" + findPostId() + "']/a[@title='Unlike this']")).isDisplayed());
    }

    public void assertMyOwnPostNotLiked() {
        logger.info("Asserting that my own post is not liked.");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='like-node-" + findPostId() + "']/a[@title='like this']")).isDisplayed());
    }

    public void clickOnEditProfileButton() {
        editProfileBtn().click();
    }

    public void selectState01(String stateName) {
        logger.info("Selecting state." + stateName);
        Select element = new Select(state01DropdownMenu());
        //element.deselectAll();
        element.selectByVisibleText(stateName);
        waitUntilPageFinishLoading();
    }

    public void selectState02(String stateName) {
        logger.info("Selecting state." + stateName);
        Select element = new Select(state02DropdownMenu());
        element.selectByVisibleText(stateName);
        waitUntilPageFinishLoading();
    }

    public void checkIfStateSaved(String stateName) {
        Assert.assertTrue("The state is not being saved.", isStateVisible(stateName));
        removeSelectedState();
    }

    private boolean isStateVisible(String stateName) {
        logger.info("Making sure that state is saved.");
        try {
            driver.findElement(By.xpath("//div[contains(text(), '" + stateName + "')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    private void removeSelectedState() {
        clickOnEditProfileButton();
        logger.info("Deselecting state.");
        Select element = new Select(state01DropdownMenu());
        //element.deselectAll();
        element.selectByVisibleText("Choose a state");
        waitUntilPageFinishLoading();
        saveBtn().click();
    }

    public void editProfileInfo(String personalEmail, String officePhone, String mobilePhone, String title, String territory, String bio, String headline, String almaMeter) {
        logger.info("Populating user fields.");
        personalEmailField().clear();
        personalEmailField().sendKeys(personalEmail);
        officePhoneField().clear();
        officePhoneField().sendKeys(officePhone);
        mobilePhoneField().clear();
        mobilePhoneField().sendKeys(mobilePhone);
        titleField().clear();
        titleField().sendKeys(title);
        selectState01(territory);
        bioField().clear();
        bioField().sendKeys(bio);
        headlineField().clear();
        headlineField().sendKeys(headline);
        almaMeterField().clear();
        almaMeterField().sendKeys(almaMeter);
    }

    public void checkProfileInfoSaved(String personalEmail, String officePhone, String mobilePhone, String title, String territory, String bio, String headline, String almaMeter) {
        logger.info("Making sure that new user info is saved.");
        Assert.assertTrue("Personal Email field not saved.", checkItemVisible(personalEmail));
        Assert.assertTrue("Office Phone field not saved.", checkItemVisible(officePhone));
        Assert.assertTrue("Mobile Phone field not saved.", checkItemVisible(mobilePhone));
        Assert.assertTrue("Job Title field not saved.", checkItemVisible(title));
        Assert.assertTrue("State/Territory field not saved.", checkItemVisible(territory));
        Assert.assertTrue("Bio field not saved.", checkItemVisible(bio));
        Assert.assertTrue("Headline filed not saved.", checkItemVisible(headline));
        Assert.assertTrue("Alma Meter field not saved.", checkItemVisible(almaMeter));
    }

    private boolean checkItemVisible(String item) {
        try {
            driver.findElement(By.xpath("//*[contains(text(), '" + item + "')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void checkRequiredFields() {
        officePhoneField().clear();
        titleField().clear();
        saveBtn().click();

        Assert.assertTrue("The message is not displayed that Job Title is required", checkItemVisible("Job Title field is required."));
        Assert.assertTrue("The message is not displayed that Office Phone is required", checkItemVisible("Office Phone field is required."));

    }

    private void selectCountiesForState01(String county) {
        logger.info("Adding new county: " + county);
        addNewCountyForState01Field().clear();
        addNewCountyForState01Field().sendKeys(county);
        waitUntilPageFinishLoading();
        addNewCountyForState01Field().sendKeys(Keys.ENTER);
    }

    private void selectCountiesForState02(String county) {
        logger.info("Adding new county: " + county);
        addNewCountyForState02Field().clear();
        addNewCountyForState02Field().sendKeys(county);
        waitUntilPageFinishLoading();
        addNewCountyForState02Field().sendKeys(Keys.ENTER);
    }


    public void selectFirstStateWithCounties(String firststate, String county1, String county2) {
        selectState01(firststate);
        selectCountiesForState01(county1);
        selectCountiesForState01(county2);
//        addNewStateBtn().click();

    }

    public void selectSecondStateWithCounties(String secondstate, String county1, String county2) {
        selectState02(secondstate);
        selectCountiesForState02(county1);
        selectCountiesForState02(county2);
//        addNewStateBtn().click();
    }


    public void checkFirstStateWithCounties(String firststate, String county1, String county2) {
        logger.info("Checking if first state is saved including counties");
        driver.findElement(By.xpath("//*[contains(text(), '" + firststate + "')]/../a[@class='show-counties']")).click();
        Assert.assertTrue("The county " + county1 + " is not saved!", checkItemVisible(county1));
        Assert.assertTrue("The county " + county2 + " is not saved!", checkItemVisible(county2));
    }

    public void checkSecondStateWithCounties(String secondstate, String county1, String county2) {
        logger.info("Checking if second state is saved including counties");
        driver.findElement(By.xpath("//*[contains(text(), '" + secondstate + "')]/../a[@class='show-counties']")).click();
        Assert.assertTrue("The county " + county1 + " is not saved!", checkItemVisible(county1));
        Assert.assertTrue("The county " + county2 + " is not saved!", checkItemVisible(county2));
        clearSelectedStates();
    }

    public void clearSelectedStates() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("scroll(0, -250);");
        waitUntilPageFinishLoading();

        clickOnEditProfileButton();
        logger.info("Deselecting states.");
        Select element01 = new Select(state01DropdownMenu());
        element01.selectByVisibleText("Choose a state");

        Select element02 = new Select(state02DropdownMenu());
        element02.selectByVisibleText("Choose a state");

        waitUntilPageFinishLoading();
        saveBtn().click();
    }

    public void likePost(String posttext) {
        logger.info("Liking post: " + posttext);
        iframeEnter();
        driver.findElement(By.xpath("//p[contains(text(), '" + posttext + "')]/../../../../div[@class='like-wrapper like-node']/a[@title='like this']")).click();
        waitUntilPageFinishLoading();
    }

    public void unlikePost(String posttext) {
        logger.info("Unliking post: " + posttext);
        iframeEnter();
        driver.findElement(By.xpath("//p[contains(text(), '" + posttext + "')]/../../../../div[@class='like-wrapper like-node']/a[@title='Unlike this']")).click();
        waitUntilPageFinishLoading();
    }

    public void openNotifications() {
        iframeExit();
        logger.info("Opening notifications list.");
        waitUntilPageFinishLoading();
        notificationsGlove().click();
    }

    private boolean checkNewNotification() {
        logger.info("Checking if new notification is raised.");
        try {
            driver.findElement(By.xpath("//i[@class='red exclamation icon']/../*[contains(text(), 'MatchSupportUIQA4')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    private boolean checkNewNotificationExclamationIcon() {
        logger.info("Checking if new notification is raised.");
        waitUntilPageFinishLoading();
        try {
            driver.findElement(By.xpath("//i[@class='red exclamation icon']"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void checkNewNotificationRaised() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("There are no new notifications!", checkNewNotificationExclamationIcon());

    }

    public void checkNewGroupApprovementNotificationRaised() {
        Assert.assertTrue("There are no new notifications!", checkNewNotificationExclamationIcon());

    }

    public void clearAllNotifications() {
        logger.info("Clearing all the notifications");
        iframeExit();
        notificationsGlove().click();
        waitUntilPageFinishLoading();
        notificationsGlove().click();
    }

    public void checkNewNotificationNotRaised() {
        Assert.assertFalse("The new notification is raised!", checkNewNotification());
        iframeEnter();
        deleteCreatedPost();
    }

    public void checkIfMyProfilePageIsOpened() {
        logger.info("Checking if my profile page is opened.");
        Assert.assertTrue("The user profile page is not opened.", checkItemVisible("PurpleHE Automation"));
    }

    public void setPrivacyForFields() {
        logger.info("Setting privacy for work email to public, for personal email to private and for office phone to connections only");
        privacyWorkEmail("public").click();
        privacyPersonalEmail("private").click();
        privacyOfficePhone("connections").click();
    }

    public void checkPublicFieldsVisibility() {
        iframeEnter();
        logger.info("Checking if only public fields are visible.");
        Assert.assertTrue("Public field work email is not visible.", checkItemVisible("purpleheautomation@gmail.com"));
        Assert.assertFalse("Connections Only privacy field office phone is visible.", checkItemVisible("+12161234567"));
        Assert.assertFalse("Private privacy field personal email is visible.", checkItemVisible("testemail@personal.com"));
    }

    public void checkConnectionsOnlyFieldsVisibility() {
        iframeEnter();
        logger.info("Checking if fields with connection only privacy are visible.");
        Assert.assertTrue("Public field work email is not visible.", checkItemVisible("purpleheautomation@gmail.com"));
        Assert.assertTrue("Connections Only privacy field office phone is not visible.", checkItemVisible("+12161234567"));
        Assert.assertFalse("Private privacy field personal email is visible.", checkItemVisible("testemail@personal.com"));
    }

    public void checkVisibleToOnlyMeFieldsVisibility() {
        iframeEnter();
        logger.info("Checking if fields with visible to only me privacy are visible.");
        Assert.assertFalse("Private privacy field personal email is visible.", checkItemVisible("testemail@personal.com"));
    }

    public void writeCommentOnProfilePost(String commentText, String postText) {
        logger.info("Writing comment to the post.");
        driver.findElement(By.xpath("//p[contains(text(), '"+postText+"')]/../../../../div[@class='post-comments-link']")).click();
        driver.findElement(By.id("edit-comment-body")).sendKeys(commentText);
        driver.findElement(By.cssSelector("input[class='form-submit ajax-processed']")).click();
        waitUntilPageFinishLoading();
    }

    public void checkIfCommentIsPosted(String commentText) {
        logger.info("Checking if comment is posted.");
        Assert.assertTrue("The comment is not visible on the page!", checkItemVisible(commentText));
        deleteCreatedPost();
        waitUntilPageFinishLoading();
    }

    public void editCreatedPost(String editPostText) {
        logger.info("Editing created post.");
        editCreatedPostBtn().click();
        editPostTextbox().clear();
        editPostTextbox().sendKeys(editPostText);
        updateBtn().click();
        waitUntilPageFinishLoading();
    }


    private WebElement newPostTextbox() {
        return driver.findElement(By.id("edit-body"));
    }
    private WebElement editPostTextbox() {
        return driver.findElement(By.id("edit-body--2"));
    }
    private WebElement updateBtn() {
        return driver.findElement(By.id("edit-save--2"));
    }
    private WebElement profilePicOnPostsFeed() {return driver.findElement(By.xpath("//img[contains(@src, 'https://qa.community.hobsons.com/sites/default/files/styles/post_thumbnail/public/')]"));}
    private WebElement likeMyOwnPostBtn() {return  driver.findElement(By.xpath("//div[@id='like-node-" + findPostId() + "']/a[1]"));}
    private WebElement editProfileBtn() {return driver.findElement(By.xpath("//div[@id='block-cp-ur-tools-cp-ur-profile-view']/div/a"));}
    private WebElement state01DropdownMenu() {return driver.findElement(By.name("cp_states[0][state]"));}
    private WebElement state02DropdownMenu() {return driver.findElement(By.name("cp_states[1][state]"));}
    private WebElement saveBtn() {return driver.findElement(By.id("edit-submit"));}
    private WebElement personalEmailField() {return driver.findElement(By.id("edit-field-contact-personal-und-0-value"));}
    private WebElement officePhoneField() {return driver.findElement(By.id("edit-field-office-phone-und-0-value"));}
    private WebElement mobilePhoneField() {return driver.findElement(By.id("edit-field-mobile-phone-und-0-value"));}
    private WebElement titleField() {return driver.findElement(By.id("edit-field-job-position-und-0-value"));}
    private WebElement bioField() {return driver.findElement(By.id("edit-field-bio-und-0-value"));}
    private WebElement headlineField() {return driver.findElement(By.id("edit-field-tagline-und-0-value"));}
    private WebElement almaMeterField() {return driver.findElement(By.id("edit-field-alma-mater-und-0-value"));}
    private WebElement addNewStateBtn() {return driver.findElement(By.id("edit-add-item"));}
    private WebElement addNewCountyForState01Field() {return driver.findElement(By.xpath("//div[@id='edit_cp_states_0_counties_chosen']/ul/li/input"));}
    private WebElement addNewCountyForState02Field() {return driver.findElement(By.xpath("//div[@id='edit_cp_states_1_counties_chosen']/ul/li/input"));}
    private WebElement notificationsGlove() {return link(By.id("notifications"));}
    private WebElement privacyWorkEmail(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-contact-work-privacy']/option[@value='"+privacy+"']"));}
    private WebElement privacyPersonalEmail(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-contact-personal-privacy']/option[@value='"+privacy+"']"));}
    private WebElement privacyOfficePhone(String privacy) {return driver.findElement(By.xpath("//select[@id='edit-privacy-options-field-office-phone-privacy']/option[@value='"+privacy+"']"));}
    private WebElement addHhyperlinkBtn() {return driver.findElement(By.cssSelector("a[class='fieldset-button first-button']"));}
    private WebElement addHyperlinkTextBox() {return driver.findElement(By.id("edit-url"));}
    private WebElement editCreatedPostBtn() {return driver.findElement(By.linkText("edit"));}

}