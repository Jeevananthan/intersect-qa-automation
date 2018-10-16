package pageObjects.CM.groupsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.CM.homePage.HomePageImpl;
import pageObjects.CM.loginPage.LoginPageImpl;
import pageObjects.CM.userProfilePage.UserProfilePageImpl;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Objects;

/**
 * Created by bojan on 6/5/17.
 */
public class GroupsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;


    public GroupsPageImpl() {
        logger = Logger.getLogger(GroupsPageImpl.class);
    }

    LoginPageImpl loginPage = new LoginPageImpl();
    HomePageImpl homePage = new HomePageImpl();
    UserProfilePageImpl userProfilePage = new UserProfilePageImpl();


    // Deprecated - use communityFrame from PageObjectFacadeImpl instead.
    public void iframeEnter() {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
        waitForUITransition();
        logger.info("Entered Community iFrame.");
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }


    public void goToGroupsPage() {
        logger.info("Going to groups page.");
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        waitUntilPageFinishLoading();
        communityFrame();
        link("Groups").click();
    }

    public void clickGroupsTab() {
        logger.info("Going to groups tab.");
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

    public void editGroupName(String grouName) {
        logger.info("Populating new group form.");
        groupNameField().clear();
        groupNameField().sendKeys(grouName);

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


    public void deleteCreatedGroup(String grouptodelete) {
        WebElement group = driver.findElement(By.partialLinkText(grouptodelete));
        String grouplink = group.getAttribute("href");
        String groupid = grouplink.substring(grouplink.lastIndexOf("/") + 1);
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
            setImplicitWaitTimeout(1);
            addPostComment().isDisplayed();
            logger.info("User can add new comment to the post.");
            resetImplicitWaitTimeout();
            return true;

        } catch (NoSuchElementException e) {
            logger.info("User cannot add new comment to the post.");
            resetImplicitWaitTimeout();
            return false;
        }
    }

    public void searchForGroup(String groupname) {
        waitUntilPageFinishLoading();
        iframeExit();
        textbox(By.id("global-search-box-input")).clear();
        textbox(By.id("global-search-box-input")).sendKeys(groupname);
        logger.info("Searching for the group.");
        waitUntilElementExists(link(By.id("global-search-box-item-0")));
        getDriver().findElement(By.id("global-search-box-item-0")).findElement(By.className("title")).click();
        communityFrame();
        waitUntilPageFinishLoading();
    }

    public void enterTextInSearchBox(String text) {
        logger.info("Entering search criteria in search box.");
        waitUntilPageFinishLoading();
        iframeExit();
        textbox(By.id("global-search-box-input")).clear();
        textbox(By.id("global-search-box-input")).sendKeys(text);
    }

    public void makeSureUserIsMemberOfTheGroup() {
        try {
            setImplicitWaitTimeout(1);
            joinGroupButton();
            logger.info("User is going to join the group.");
            joinGroupButton().click();
            waitUntilPageFinishLoading();
            resetImplicitWaitTimeout();

        } catch (NoSuchElementException e) {
            logger.info("User already joined the group.");
            resetImplicitWaitTimeout();
        }
    }

    public void makeSureUserIsMemberOfThePrivateGroup() {
        try {
            if (driver.findElement(By.xpath("//*[@id=\"follow-10671\"]/a")).isDisplayed()) {
                logger.info("User is going to request to join private group.");
                requestToJoinGroupButton().click();
            }
            homePage.logoutHEDefault();
            approveRequestToJoinTheGroup();
            waitUntilPageFinishLoading();
            loginPage.defaultLoginHE();
        } catch (NoSuchElementException e){
            logger.info("User already joined the group.");
            }
        try {
            if (driver.findElement(By.xpath("//*[@id=\"unfollow-10671\"]/a")).isDisplayed()) {
                logger.info("Canceling Pending request.");
                cancelPendingRequest().click();
            }
            logger.info("Requesting to join private group.");
            requestToJoinGroupButton().click();
            homePage.logoutHEDefault();
            approveRequestToJoinTheGroup();
            waitUntilPageFinishLoading();
            loginPage.defaultLoginHE();
        } catch (NoSuchElementException e){
            logger.info("User already joined the group.");
        }
    }

    public void makeSureUserIsMemberOfThePublicGroup() {
        try {
            joinGroupButton();
            logger.info("User is going to join the group.");
            joinGroupButton().click();

        } catch (NoSuchElementException e) {
            logger.info("User already joined the group.");
        }
    }

    public void sendRequestToJoinTheGroup() {
        try {
            setImplicitWaitTimeout(1);
            logger.info("Clicking on button to join the group.");
            requestToJoinGroupButton().click();
            waitUntilPageFinishLoading();
            resetImplicitWaitTimeout();
        } catch (NoSuchElementException e) {
            logger.info("User has already applied to join the group.");
            resetImplicitWaitTimeout();
        }
    }

    public void goToManageGroupMembersPage() {
        logger.info("Going to the Manage Group Members Page.");
        // Implementing Brian's fix from @MATCH-654
        getNavigationBar().goToCommunity();
        communityFrame();
        link("Groups").click();
        waitUntilPageFinishLoading();
        link(By.linkText("**Test Automation** HE Community PRIVATE Group")).click();
        driver.findElement(By.className("manage-group-members-link")).click();
    }

    public void goToSpecificManageGroupMembersPage(String group) {
        logger.info("Going to the Manage Group Members Page for group " + group);
        getNavigationBar().goToCommunity();
        communityFrame();
        link("Groups").click();
        waitUntilPageFinishLoading();
        link(By.linkText(group)).click();
        driver.findElement(By.className("manage-group-members-link")).click();
    }

    public void approveRequestToJoinTheGroup() {
        logger.info("Approving request to join the group.");
        loginPage.defaultLoginSupport();
        goToManageGroupMembersPage();
        driver.findElement(By.className("accept-link")).click();
        //homePage.logoutSupport();
        // LoginLogout.defaultLoginHE();
      //  searchForGroup("**Test Automation** HE Community PRIVATE Group");
    }

    public void denyRequestToJoinTheGroup() {
        logger.info("Denying request to join the group.");
        loginPage.defaultLoginSupport();
        goToManageGroupMembersPage();
        driver.findElement(By.className("decline-link")).click();
        //homePage.logoutSupport();
    }

    public void makeSureUserIsNotMemberOfTheGroup() {
        try {
            setImplicitWaitTimeout(1);
            leaveGroupButton();
            logger.info("User is going to leave the group.");
            leaveGroupButton().click();
            waitUntilPageFinishLoading();
            resetImplicitWaitTimeout();

        } catch (NoSuchElementException e) {
            resetImplicitWaitTimeout();
            logger.info("User is not a member of the group.");
        }
    }

    public void checkIfUserIsMemberOfTheGroup() {
        logger.info("Checking if user is a member of the group.");
        setImplicitWaitTimeout(1);
        try {
            leaveGroupButton();
            waitUntilPageFinishLoading();
            resetImplicitWaitTimeout();
        } catch (NoSuchElementException e) {
            getDriver().findElement(By.cssSelector("a[class^='active-trail']"));
            logger.info("User is not a member of the group.");
            resetImplicitWaitTimeout();
        }
    }

    public void checkIfUserIsNotMemberOfTheGroup() {
        logger.info("Checking if user is not a member of the group.");
        try {
            requestToJoinGroupButton();
            waitUntilPageFinishLoading();

        } catch (NoSuchElementException e) {
            logger.info("User is a member of the group.");
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

    private boolean checkItemVisible(String item) {
        try {
            driver.findElement(By.xpath("//*[contains(text(), \"" + item + "\")]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    private boolean checkItemVisibleByCssSelector(String tag, String cssselector, String selectorvalue) {
        try {
            driver.findElement(By.cssSelector(""+tag+"["+cssselector+"='"+selectorvalue+"']"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void checkMyGroupsPage() {
        logger.info("Checking if 'Your Groups' headline exists.");
        Assert.assertTrue("'Your Groups' headline is not displayed.", checkItemVisible("Your Groups"));

        logger.info("Checking if default group is listed.");
        Assert.assertTrue("The default Hobsons group is not visible in the list", checkItemVisible("Hobsons News & Events"));
    }

    public void checkIfHobsonsGropLink() {
        logger.info("Checking if default Hobsons group can be visited from the My Groups page.");
        hobsonsNewsGroupLink().click();
        Assert.assertTrue("The group page is not opened.", checkItemVisible("Hobsons News & Events"));
    }

    public void removeUserFromTheGroup() {
        logger.info("Removing user from the group.");
        driver.findElement(By.xpath("//div[contains(text(), 'PurpleHE Automation')]/../../../../../td[@class='remove-link']")).click();
    }

    public void checkIfUserIsRemoved() {
        logger.info("Checking if user is removed from the group.");
        setImplicitWaitTimeout(1);
        boolean removed = checkItemVisible("PurpleHE Automation");
        resetImplicitWaitTimeout();
        Assert.assertFalse("The user is not removed from the group.", removed);
    }

    public void findLeaveGroupBtn() {
        logger.info("Checking if leave group button exists");
        Assert.assertTrue("Leave Group button cannot be found on the page.", leaveGroupButton().isDisplayed());
    }

    public void leaveGroup(String groupname) {
        logger.info("Leaving the group.");
        link(By.linkText(groupname)).click();
        leaveGroupButton().click();
    }

    public void checkIfTheGroupIsNotInTheList(String groupname) {
        logger.info("Checking if the group is no longer visible in my groups list.");
        waitUntilPageFinishLoading();
        clickGroupsTab();
        waitUntilPageFinishLoading();
        Assert.assertFalse("The group is still visible.", checkItemVisible(groupname));
    }

    public void checkIfThereArePostsFromPublicGroup() {
        logger.info("Checking if posts from PUBLIC group are visible.");
        Assert.assertTrue("There are no posts from PUBLIC group.", checkItemVisible("**Test Automation** HE Community PUBLIC Group post."));
        iframeExit();
    }

    public void checkIfThereAreNoPostsFromPublicGroup() {
        logger.info("Checking if posts from PUBLIC group are not visible.");
        Assert.assertFalse("There are no posts from PUBLIC group.", checkItemVisible("**Test Automation** HE Community PUBLIC Group post."));
    }

    public void setGroupVisibility(String visibility) {
        logger.info("Setting group visibility.");
        if (Objects.equals(visibility, "Public")) {
            visibilityPublicRadio().click();
        }
        else if (Objects.equals(visibility, "Private")) {
            visibilityPrivateRadio().click();
        }
    }

    public void openGroupPageFromGroupsList(String groupname) {
        logger.info("Opening group page of group: " + groupname);
        driver.findElement(By.xpath("//a[contains(text(), '"+groupname+"')]")).click();
        waitUntilPageFinishLoading();
    }

    public void editGroup() {
        logger.info("Clicking on edit group button.");
        editGroupBtn().click();
        waitUntilPageFinishLoading();
    }

    public void updateChanges() {
        logger.info("Updating changes.");
        updateBtn().click();
    }

    public void checkGroupVisibility(String visibilityType) {
        logger.info("Checking if the group visibility is: " + visibilityType);
        Assert.assertTrue("The group visibility is not correct!", checkItemVisible(visibilityType));
        clickGroupsTab();
    }

    public void checkGroupName(String groupName) {
        logger.info("Checking if the group name is" + groupName);
        Assert.assertTrue("The group name is not correct!", checkItemVisible(groupName));
        clickGroupsTab();
    }

    public void checkIfBtnIsDisplayedByCssSelector(String button) {
        logger.info("Checking if "+button+" button is displayed.");
        Assert.assertTrue(""+button+" button not visible!", checkItemVisibleByCssSelector("a", "title", ""+button+""));
    }

    public void checkMessageDisplayed(String message) {
        logger.info("Checking if message is displayed.");
        Assert.assertTrue("The message is not displayed!", checkItemVisible(message));
    }

    public void checkPostNotDisplayedWithText(String posttext) {
        logger.info("Checking if the post is not displayed.");
        Assert.assertFalse("The post is displayed!", checkItemVisible(posttext));
    }

    public void checkPostDisplayedWithText(String posttext) {
        logger.info("Checking if the post is displayed.");
        Assert.assertTrue("The post is not displayed!", checkItemVisible(posttext));
    }

    public void clickJoinGroupButton() {
        logger.info("Joining group.");
        joinGroupButton().click();
        waitUntilPageFinishLoading();
    }

    public void checkIfTextIsVisible(String item) {
        logger.info("Searching for "+item+".");
        Assert.assertTrue("The "+item+" cannot be found on the page!", checkItemVisible(item));
    }

    public void clickSearchIcon(){
        logger.info("Clicking on search icon.");
        searchIcon().click();
        waitUntilPageFinishLoading();
    }

    public void goToGroupsTabUnderSearch() {
        logger.info("Clicking on groups tab under search.");
        groupsTabUnderSearch().click();
    }

    public void checkPresentedJoinButtonNextToPublicGroup() {
        logger.info("Check if I am presented with a 'Join' action next to any Public group that returns in my search results");
        Assert.assertTrue("'Join' button cannot be found on the page!", checkItemVisibleByCssSelector("i", "class", "user plus icon"));
    }

    public void checkAggregateFeedOnGroupPage() {
        logger.info("Checking if aggregate feed is visible on group page.");
        Assert.assertTrue("", checkItemVisibleByCssSelector("div","class","post-submitted"));
    }

    public void disableCommentsOnGroupPosts() {
        disableCommentsCheckbox().click();
    }

    private WebElement addGroupButton() {
        return driver.findElement(By.cssSelector("[href='/groups/add']"));
    }

    /*
    private WebElement groupNameField() {
        return driver.findElement(By.xpath("//*[@id=\"edit-group\"]"));
    }
    */

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

    private WebElement cancelPendingRequest() {return driver.findElement(By.cssSelector("a[href].use-ajax.unfollow.pending.ajax-processed.reply-processed"));}

    private WebElement newPostField() {return driver.findElement(By.id("edit-body"));}

    private WebElement disableCommentsCheckbox() {return driver.findElement(By.cssSelector("label[for='edit-post-comment']"));}

    private WebElement addPostComment() {return driver.findElement(By.className("post-comments-link"));}
    private WebElement hobsonsNewsGroupLink() {return driver.findElement(By.xpath("//a[contains(text(), 'Hobsons News & Events')]"));}
    private WebElement visibilityPublicRadio() {return driver.findElement(By.cssSelector("label[for='edit-group-og-access-0']"));}
    private WebElement visibilityPrivateRadio() {return driver.findElement(By.cssSelector("label[for='edit-group-og-access-1']"));}
    private WebElement editGroupBtn() {return driver.findElement(By.cssSelector("div[class='edit-group-link']"));}
    private WebElement updateBtn() {return driver.findElement(By.id("edit-update"));}
    private WebElement searchIcon() {return driver.findElement(By.cssSelector("i[class='search link icon']"));}
    private WebElement groupsTabUnderSearch() {return driver.findElement(By.id("searchResultsTabgroups"));}
}
