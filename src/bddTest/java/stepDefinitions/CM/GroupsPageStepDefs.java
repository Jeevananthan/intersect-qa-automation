package stepDefinitions.CM;

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
        And("^I am sure that user is a member of the private group$",groupsPage::makeSureUserIsMemberOfThePrivateGroup);
        And("^I disable comments on group posts$", groupsPage::disableCommentsOnGroupPosts);
        Then("^I check if comments are allowed to the post$", groupsPage::checkIfPostCommentsAreAllowed);
        And("^I check if group with name \"([^\"]*)\" exists$", groupsPage::checkIfGroupAlreadyExists);

    }
}
