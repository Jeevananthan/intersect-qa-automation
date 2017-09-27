package stepDefinitions.CM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.CM.groupsPage.GroupsPageImpl;

/**
 * Created by bojan on 6/5/17.
 */
public class GroupsPageStepDefs implements En {

    public GroupsPageStepDefs() {

        GroupsPageImpl groupsPage = new GroupsPageImpl();

        Then("^I navigate to Groups page$", groupsPage::goToGroupsPage);
        And("^I click on Add Group button$", groupsPage::clickAddGroupButton);
        Then("^I populate fields for new Group with name \"([^\"]*)\"$", groupsPage::populateGroupFields);
        And("^I select institution type \"([^\"]*)\"$", groupsPage::selectInstitutionType);
        And("^I click on Create button$", groupsPage::clickCreateButton);
        Then("^I check if group with name \"([^\"]*)\" is created$", groupsPage::assertGroupCreated);
        And("^I check if non Community Administrator can create new group$", groupsPage::assertNonAdministratorCannotCreateGroup);
        And("^I search for \"([^\"]*)\" group$", groupsPage::searchForGroup);
        And("^I am sure that user is a member of the group$", groupsPage::makeSureUserIsMemberOfTheGroup);
        And("^I am sure that user is not a member of the group$", groupsPage::makeSureUserIsNotMemberOfTheGroup);
        Then("^I check if user is unable to create a post$", groupsPage::makeSureUserCannotCreateGroupPost);
        And("^I am sure that user is a member of the private group$", groupsPage::makeSureUserIsMemberOfThePrivateGroup);
        And("^I disable comments on group posts$", groupsPage::disableCommentsOnGroupPosts);
        Then("^I check if comments are allowed to the post$", groupsPage::checkIfPostCommentsAreAllowed);
        And("^I check if group with name \"([^\"]*)\" exists$", groupsPage::checkIfGroupAlreadyExists);
        And("^I check if my groups are displayed$", groupsPage::checkMyGroupsPage);
        And("^I can go to Hobsons default group page$", groupsPage::checkIfHobsonsGropLink);
        Then("^I request to join the group$", groupsPage::sendRequestToJoinTheGroup);
        And("^I approve request to join the group$", groupsPage::approveRequestToJoinTheGroup);
        And("^I check if user is a member of the group$", groupsPage::checkIfUserIsMemberOfTheGroup);
        And("^I deny request to join the group$", groupsPage::denyRequestToJoinTheGroup);
        Then("^I check if user is not a member of the group$", groupsPage::checkIfUserIsNotMemberOfTheGroup);
        And("^I navigate to Manage Group Members page$", groupsPage::goToManageGroupMembersPage);
        And("^I remove the user from the group$", groupsPage::removeUserFromTheGroup);
        Then("^I check if the user is removed$", groupsPage::checkIfUserIsRemoved);
        Then("^I see 'Leave' action on the page$", groupsPage::findLeaveGroupBtn);
        Then("^I open my groups tab$", groupsPage::clickGroupsTab);
        And("^I leave the \"([^\"]*)\" group$", groupsPage::leaveGroup);
        And("^I check if \"([^\"]*)\" group\" is no longer visible in my groups list$", groupsPage::checkIfTheGroupIsNotInTheList);
        And("^I check if I see posts from the group$", groupsPage::checkIfThereArePostsFromPublicGroup);
        And("^I check if I do not see posts from the group$", groupsPage::checkIfThereAreNoPostsFromPublicGroup);
        And("^I set group visibility to \"([^\"]*)\"$", groupsPage::setGroupVisibility);
        Then("^I open \"([^\"]*)\" group page$", groupsPage::openGroupPageFromGroupsList);
        And("^I click on Edit group button$", groupsPage::editGroup);
        And("^I Update changes$", groupsPage::updateChanges);
        Then("^I check if the group visibility is \"([^\"]*)\"$", groupsPage::checkGroupVisibility);
        And("^I delete group \"([^\"]*)\"$", groupsPage::deleteCreatedGroup);
        Then("^I change name of the group with name \"([^\"]*)\"$", groupsPage::editGroupName);
        Then("^I check if the group name is changed to \"([^\"]*)\"$", groupsPage::checkGroupName);
        Then("^I check if I see \"([^\"]*)\" button$", groupsPage::checkIfBtnIsDisplayedByCssSelector);
        Then("^I check if the massage is displayed \"([^\"]*)\"$", groupsPage::checkMessageDisplayed);
        Then("^I check if I cannot see post with text \"([^\"]*)\"$", groupsPage::checkPostNotDisplayedWithText);
        Then("^I check if I can see post with text \"([^\"]*)\"$", groupsPage::checkPostDisplayedWithText);
        And("^I click on 'Join Group' button$", groupsPage::clickJoinGroupButton);
        And("^I check if I can see \"([^\"]*)\" on the page$", groupsPage::checkIfTextIsVisible);
        Then("^I enter \"([^\"]*)\" in search box$", groupsPage::enterTextInSearchBox);
        And("^I click on Search icon$", groupsPage::clickSearchIcon);
        Then("^I click on Groups tab under search$", groupsPage::goToGroupsTabUnderSearch);
        And("^I check if I am presented with a 'Join' action next to any Public group that returns in my search results$",groupsPage::checkPresentedJoinButtonNextToPublicGroup);
    }
}
