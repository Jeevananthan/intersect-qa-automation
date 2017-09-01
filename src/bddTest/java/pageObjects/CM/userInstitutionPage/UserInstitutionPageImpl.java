package pageObjects.CM.userInstitutionPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pageObjects.CM.commonPages.PageObjectFacadeImpl;


/**
 * Created by bojan on 6/1/17.
 */
public class UserInstitutionPageImpl extends PageObjectFacadeImpl {


    private Logger logger;


    public UserInstitutionPageImpl()  {
        logger = Logger.getLogger(UserInstitutionPageImpl.class);}

    public void iframeEnter()  {
        //Thread.sleep(3000); //Implicitly wait does not work on Mac (should be fixed soon)
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }


    public void goToUserInstitutionPage() {
        logger.info("Going to user profile page.");
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        iframeEnter();
        link(By.cssSelector("a[href='/institution']")).click();
    }

    public void clickInstitutionTab() {
        logger.info("Going to institution tab.");
        link(By.cssSelector("a[href='/institution']")).click();
    }

    public void createNewInstitutionPost(String postText) {
        logger.info("Creating new institution post.");
        newPostTextbox().sendKeys(postText);
        driver.findElement(By.cssSelector("label[for='edit-post-as-institution']")).click();
        driver.findElement(By.id("edit-save")).click();
    }

    public void checkIfInstitutionPostIsCreated(String postText) {
        logger.info("Checking if user post is created");
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), '"+postText+"')]")).isDisplayed());
        deleteCreatedPost();
    }

    private void deleteCreatedPost() {
        logger.info("Deleting created institution post.");
        driver.findElement(By.linkText("edit")).click();
        driver.findElement(By.id("edit-delete")).click();
        waitUntilElementExists(driver.findElement(By.id("edit-delete--3")));
        driver.findElement(By.id("edit-delete--3")).click();
    }

    public void goToHobsonsInstitutionPage() {
        waitUntilPageFinishLoading();
        driver.navigate().to("https://qa-he.intersect.hobsons.com/counselor-community/institution/1");
        iframeEnter();
    }

    public void goToInstitutionPageById(String instiId) {
        logger.info("Going to institution page with id "+instiId+"");
        iframeExit();
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        waitUntilPageFinishLoading();
        driver.navigate().to("https://qa-he.intersect.hobsons.com/counselor-community/institution/"+instiId+"");
        iframeEnter();
    }

    public void checkIfInstituionBannerExists() {
        logger.info("Checking if institution banner exists");
        Assert.assertTrue(institutionBanner().isDisplayed());
    }

    public void checkIfInstitutionPostsAreVisible(){
        logger.info("Checking if there are posts on the Institution page");
        Assert.assertTrue(profilePicOnPostsFeed().isDisplayed());
    }

    public void checkIfHobsonsInstituionIsDisplayed(){
        logger.info("Checking if I'm following Hobsons institution.");
        Assert.assertTrue(hobsonsInstitution().isDisplayed());
    }

    public void goToInstitutionStaffMembersList() {
        logger.info("Going to the list of Institution Staff Members.");
        link(By.cssSelector("a[href='/institution/1/staff']")).click();
    }

    public void checkIfStaffMembersExistsAndCanConenct() {
        goToInstitutionStaffMembersList();
        logger.info("Checking if there are staff members on the Institution page");
        Assert.assertTrue(profilePicStaffMember().isDisplayed());

        logger.info("Checking if connection request can be sent to staff members");
        Assert.assertTrue(connectToUserBtn().isDisplayed());
    }

    public void makeSureICannotUnfollowHobsons() {
        Assert.assertFalse(isUnfollowBtnDisplayed());
    }

    private boolean isUnfollowBtnDisplayed() {
        logger.info("Checking if user can unfollow the Hobsons institution.");
        try {
            unfollowBtn().isDisplayed();
            logger.info("User can unfollow the Hobsons institution..");
            return true;

        } catch (NoSuchElementException e) {
            logger.info("User cannot unfollow the Hobsons institution..");
            return false;
        }
    }

    private boolean checkItemVisible(String item) {
        try {
            driver.findElement(By.xpath("//*[contains(text(), '" + item + "')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void checkMyInstitutionPageOpened() {
        logger.info("Checking if my institution page is opened.");
        Assert.assertTrue("My institution page is not opened.", checkItemVisible("The University of Alabama"));
    }

    public void checkIfEditInstitutionBtnDisplayed() {
        logger.info("Checking if Edit institution button exists so user can edit info about the institution.");
        Assert.assertTrue("The edit button for the institution is not visible.", editInstitutionBtn().isDisplayed());
    }

    public void checkIfCorrectErrorMessageDisplayed() {
        logger.info("Checking if correct error message is displayed when user try to open institution page with not recognized ID.");
        Assert.assertTrue("The error message is not displayed correctly!.", checkItemVisible("The requested page \"/institution/345678945678\" could not be found."));
    }

    private boolean checkItemVisibleByCssSelector(String tag, String cssselector, String selectorvalue) {
        try {
            driver.findElement(By.cssSelector(""+tag+"["+cssselector+"='"+selectorvalue+"']"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void notFollowingInstitution(String institutionid) {
        logger.info("Making sure I am not following the institution.");
        driver.navigate().to("https://qa-he.intersect.hobsons.com/counselor-community/institution/"+institutionid+"");
        waitUntilPageFinishLoading();
        iframeEnter();
        try {
            unfollowBtn();
            unfollowBtn().click();
            waitUntilPageFinishLoading();

        } catch (NoSuchElementException ex)  {
            logger.info("User already not following the institution.");
        }
    }

    public void checkFollowInstitutionBtnVisible() {
        logger.info("Checking if Follow institution button is visible.");
        Assert.assertTrue("Follow institution button not visible!", checkItemVisibleByCssSelector("a", "title", "Follow Institution"));
    }


    public void clickFollowInstitution() {
        logger.info("Clicking on Follow institution button.");
        followBtn().click();
        waitUntilPageFinishLoading();
    }

    public void checkInstitutionListed(String instId) {
        logger.info("Checking if institution with id "+instId+" is in the list");
        Assert.assertTrue("The institution is not visible in the list!", checkItemVisibleByCssSelector("a", "href", "/institution/"+instId+""));
    }


    private WebElement newPostTextbox() {
        return driver.findElement(By.id("edit-body"));
    }

    private WebElement institutionBanner() {return driver.findElement(By.xpath("//*[@id='block-cp-og-tools-cp-og-institution-banner']/div/div/div[2]/div/div/img[contains(@src, 'hobsons_0.png')]"));}

    private WebElement profilePicOnPostsFeed() {return driver.findElement(By.xpath("//img[contains(@src, 'https://qa.community.hobsons.com/sites/default/files/styles/post_thumbnail/public/')]"));}

    private WebElement profilePicStaffMember() {return driver.findElement(By.xpath("//img[contains(@src, 'https://qa.community.hobsons.com/sites/default/files/styles/staff_members__60x60_/')]"));}

    private WebElement connectToUserBtn() {return driver.findElement(By.cssSelector("a[title='Send Connect Request']"));}
    private WebElement hobsonsInstitution() {return driver.findElement(By.cssSelector("a[href='/institution/1']"));}
    private WebElement unfollowBtn() {return driver.findElement(By.cssSelector("a[title='Unfollow Institution']"));}
    private WebElement followBtn() {return driver.findElement(By.cssSelector("a[title='Follow Institution']"));}
    private WebElement editInstitutionBtn() {return driver.findElement(By.cssSelector("a[class='edit-institution-link']"));}

}
