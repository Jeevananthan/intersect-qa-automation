package pageObjects.CM.groupsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.CM.commonPages.PageObjectFacadeImpl;
import pageObjects.CM.homePage.HomePageImpl;
import pageObjects.CM.loginPage.LoginPageImpl;
import pageObjects.CM.userProfilePage.UserProfilePageImpl;

/**
 * Created by bojan on 6/5/17.
 */
public class GroupsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;



    public GroupsPageImpl()  {
        logger = Logger.getLogger(GroupsPageImpl.class);}

    LoginPageImpl loginPage = new LoginPageImpl();
    HomePageImpl homePage = new HomePageImpl();
    UserProfilePageImpl userProfilePage = new UserProfilePageImpl();



    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }


    public void goToGroupsPage() {
        logger.info("Going to groups page.");
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        waitUntilPageFinishLoading();
        iframeEnter();
        link(By.cssSelector("a[href='/groups']")).click();
    }

    public void clickAddGroupButton() {
        addGroupButton().click();
    }

    public void populateGroupFields(String grouName) {
        logger.info("Populating new group form.");
        groupNameField().sendKeys(grouName);
        groupDescriptionField().sendKeys("This group is generated by Test Automation script!");

    }

    public void selectInstitutionType(String instType) {
        logger.info("Selecting institution type.");
        Select element = new Select(institutionTypeDropdown());
        //element.deselectAll();
        element.selectByVisibleText(instType);
    }

    public void clickCreateButton() {
        createButton().click();
        waitUntilPageFinishLoading();
    }

    public void assertGroupCreated(String groupname) {
        driver.findElement(By.xpath("//*[contains(text(), '" + groupname + "')]")).isDisplayed();
        deleteCreatedGroup(groupname);
        iframeExit();
    }


    private void deleteCreatedGroup(String grouptodelete) {
        WebElement group = driver.findElement(By.partialLinkText(grouptodelete));
        String grouplink = group.getAttribute("href");
        String groupid = grouplink.substring(grouplink.lastIndexOf("/")+1);
        logger.info(">>>>>>DELETING GROUP WITH ID: " + groupid);
        driver.navigate().to("https://qa-support.intersect.hobsons.com/counselor-community/cp-test/remove-user-group/" + groupid);
        logger.info("The group is deleted.");
        //driver.findElement(By.id("teesst")).click();
        waitUntilPageFinishLoading();
    }

    public void assertNonAdministratorCannotCreateGroup() {
        Assert.assertFalse(checkIftNonAdministratorCanCreateGroup());
    }

    public void checkIfPostCommentsAreAllowed() {
        Assert.assertFalse(arePostCommentsAllowed());
        userProfilePage.deleteCreatedPost();
    }

    public void checkIfGroupAlreadyExists(String groupname) {
        logger.info("Checking if grouo already exists.");
        try {
            driver.findElement(By.partialLinkText(groupname));
            logger.info("The group exists, deleting the group.");
            deleteCreatedGroup(groupname);
            goToGroupsPage();

        } catch (NoSuchElementException e) {
            logger.info("The group does not exist, test will continue.");
        }
    }

    private boolean checkIftNonAdministratorCanCreateGroup() {
        logger.info("Checking if user can create a new group.");
        try {
            addGroupButton().isDisplayed();
            logger.info("User can create a group.");
            return true;

        } catch (NoSuchElementException e) {
            logger.info("User cannot create a group.");
            return false;
        }
    }

    private boolean arePostCommentsAllowed() {
        logger.info("Checking if user can add new comment to the post.");
        try {
            addPostComment().isDisplayed();
            logger.info("User can add new comment to the post.");
            return true;

        } catch (NoSuchElementException e) {
            logger.info("User cannot add new comment to the post.");
            return false;
        }
    }

    public void searchForGroup(String groupname){
        waitUntilPageFinishLoading();
        textbox(By.id("global-search-box-input")).sendKeys(groupname);
        logger.info("Searching for the group.");
        waitUntilElementExists(link(By.id("global-search-box-item-0")));
        link(By.id("global-search-box-item-0")).click();
        iframeEnter();
        waitUntilPageFinishLoading();

    }

    public void makeSureUserIsMemberOfTheGroup() {
        try {
            joinGroupButton();
            logger.info("User is going to join the group.");
            joinGroupButton().click();
            waitUntilPageFinishLoading();

        } catch (NoSuchElementException e) {
            logger.info("User already joined the group.");
        }
    }

    public void makeSureUserIsMemberOfThePrivateGroup() {
        try {
            requestToJoinGroupButton();
            logger.info("User is going to join the group.");
            requestToJoinGroupButton().click();
            homePage.logoutHEDefault();

            waitUntilPageFinishLoading();
            approveRequestToJoinTheGroup();

        } catch (NoSuchElementException e) {
            logger.info("User already joined the group.");
        }
    }

    private void approveRequestToJoinTheGroup() {
        logger.info("Approving request to join the group.");
        loginPage.defaultLoginSupport();
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        link(By.cssSelector("a[href='/groups']")).click();
        link(By.linkText("**Test Automation** HE Community PRIVATE Group")).click();
        driver.findElement(By.className("manage-group-members-link")).click();
        driver.findElement(By.className("accept-link")).click();
        homePage.logoutSupport();
        loginPage.defaultLoginHE();
        searchForGroup("**Test Automation** HE Community PRIVATE Group");
    }

    public void makeSureUserIsNotMemberOfTheGroup() {
        try {
            leaveGroupButton();
            logger.info("User is going to leave the group.");
            leaveGroupButton().click();
            waitUntilPageFinishLoading();

        } catch (NoSuchElementException e) {
            logger.info("User is not a member of the group.");
        }
    }

    private boolean checkIfUserCanCreateGroupPost() {
        logger.info("Checking if user is unable to create a group post.");
        try {
            newPostField();
            return true;

        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void makeSureUserCannotCreateGroupPost() {
        Assert.assertFalse(checkIfUserCanCreateGroupPost());
        logger.info("User cannot create a group post.");
    }

    public void disableCommentsOnGroupPosts() {
        disableCommentsCheckbox().click();
    }

    private WebElement addGroupButton() {
        return driver.findElement(By.cssSelector("[href='/groups/add']"));
    }

    private WebElement groupNameField() {
        return driver.findElement(By.id("edit-group-title"));
    }

    private WebElement groupDescriptionField() {
        return driver.findElement(By.id("edit-group-body"));
    }

    private WebElement institutionTypeDropdown() {
        return driver.findElement(By.id("edit-group-cp-institution-type"));
    }

    private WebElement createButton() {
        return driver.findElement(By.id("edit-create"));
    }

    private WebElement joinGroupButton() {return driver.findElement(By.cssSelector("a[title='Join Group']"));}

    private WebElement leaveGroupButton() {return driver.findElement(By.cssSelector("a[title='Leave Group']"));}

    private WebElement requestToJoinGroupButton() {return driver.findElement(By.cssSelector("a[title='Request to join']"));}

    private WebElement newPostField() {return driver.findElement(By.id("edit-body"));}

    private WebElement disableCommentsCheckbox() {return driver.findElement(By.cssSelector("label[for='edit-post-comment']"));}

    private WebElement addPostComment() {return driver.findElement(By.className("post-comments-link"));}

}
