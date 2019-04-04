package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.userConnectionsPage.userConnectionsPageImpl;

/**
 * Created by bojan on 6/2/17.
 */
public class userConnectionsStepDefs implements En {

    public userConnectionsStepDefs() {

        userConnectionsPageImpl userConnections = new userConnectionsPageImpl();

        Then("^I search for \"([^\"]*)\" and open profile page of this user$", userConnections::searchForUser);
        And("^I check if I can connect to the user$", userConnections::checkIfConnectionButtonExists);
        And("^I click on connect button$", userConnections::connectToUser);
        And("^I check if confirmation message is displayed$", userConnections::checkIfConfirmationMsgDisplayed);
        Then("^I check if confirmation message includes message box and Send Invite button$", userConnections::checkIfConfirmationMsgIncludesMsgBox);
        And("^I write \"([^\"]*)\" to the box$", userConnections::enterConnectionRequestMsg);
        And("^I go to connections page$", userConnections::goToUserConnectionsPage);
        And("^I go to connections page as HS user$", userConnections::goToUserConnectionsHSPage);
        And("^I go to Institutions that I'm following page$", userConnections::goToInstituionFollowingPage);
        Then("^I export my connections$", userConnections::exportConnections);
        And("^I check if connections CSV file \"([^\"]*)\" is exported successfully to location \"([^\"]*)\"$", userConnections::assertConnectionsCSVDownloaded);
        Then("^I create new connections category using name \"([^\"]*)\"$", userConnections::addNewConnectionsCategory);
        And("^I check if category with name \"([^\"]*)\" is created$", userConnections::checkIfNewConnectionsCategoryIsAdded);
        When("^I am sure that user has at least one connection$", userConnections::userHasAtLeastOneConnection);
        And("^I check if my connections page is opened", userConnections::checkIfMyConnectionsPageOpened);
        And("^I check if I can navigate to an individual user$", userConnections::navigateToIndividualUser);
        And("^I am connected to HS user$", userConnections::connectToHSUser);
        And("^I am not connected to \"([^\"]*)\" user$", userConnections::disconnectFromUser);
        And("^I am not connected to \"([^\"]*)\" HE user$", userConnections::disconnectFromUserHE);
        And("^I check if I am not following HS user's institution$", userConnections::checkIfNotFollowingLebanonHighSchool);
        And("^I check if I am following HS user's institution$", userConnections::checkIfFollowingLebanonHighSchool);
        Then("^I open connections tab$", userConnections::clickConnectionsTab);
        And("^I open a list of user's connections$",userConnections::goToUserCOnenctionsList);
        And("^I check if Mutual connections are displayed$", userConnections::checkMutualConnectionsDisplayed);
        And("^I send the connection invitation$", userConnections::sendConnectionInvitation);
        Then("^As a HS user I accept the invitation$", userConnections::acceptConnectionRequestByHSUser);
        And("^I check if user is connected to \"([^\"]*)\" user$", userConnections::checkIfuserIsConencted);
        And("^I check if HS user is connected to \"([^\"]*)\" user$", userConnections::checkIfHSUserIsConencted);
        Then("^As a HS user I ignore the invitation$", userConnections::ignoreConnectionRequestByHSUser);
        And("^I check if user is not connected to \"([^\"]*)\" user$", userConnections::checkIfHeIsNotConnected);
        Then("^I see 'Invited' status$", userConnections::statusInvitedVisible);
        And("^I see \"([^\"]*)\" status button$", userConnections::statusInvitedVisible);
        Then("^As a HE user I accept the invitation$", userConnections::acceptConnectionRequestByHEUser);
        Then("^As a HE user I ignore the invitation$", userConnections::ignoreConnectionRequestByHEUser);
        And("^I click on Message link$", userConnections::clickMessageLink);
        Then("^I check if new message form elements are present$", userConnections::checkNewMsgForm);
        And("^I click disconnect button$", userConnections::clickDisconnectBtn);
        And("^I disconnect from the \"([^\"]*)\" user$", userConnections::disconnectFromUserFromManageConnectionsPage);
        Then("^I fill in Subject \"([^\"]*)\" and message body \"([^\"]*)\"$", userConnections::fillNewMessageForm);
        And("^I click on Send button$", userConnections::sendMessageToUser);
        And("^I am connected to MatchSupportQA3 user$", userConnections::userHasAtLeastOneConnection);
        And("^I click on Message button$", userConnections::clickOnMessageButton);
        Then("^I add \"([^\"]*)\" user as selected connection$", userConnections::addUserToSandMessageTo);
        And("^I check if there is a notification about message action$", userConnections::checkMessageActionNotification);
        And("^I check if user's connections are displayed$", userConnections::checkConnectionsDisplayed);
        And("^I check when viewing a connection's connections if I am able to request to connect with them$", userConnections::checkIfICanConnectToUsersConnections);
        And("^I add \"([^\"]*)\" to the \"([^\"]*)\" category$", userConnections::addUserToCustomCategory);
        Then("^I go to the \"([^\"]*)\" category$", userConnections::goToCategory);
        And("^I check if \"([^\"]*)\" can be found in the category$", userConnections::checkIfUserIsInCategory);
        Then("^I remove all connections from \"([^\"]*)\" category$", userConnections::removeAllConnectionsFromCategory);
        Then("^I delete \"([^\"]*)\" category$", userConnections::deleteCreatedConenctionsCategory);
        And("^And I remove \"([^\"]*)\" from the \"([^\"]*)\" category$", userConnections::removeUserFromCustomCategory);
        And("^I check if \"([^\"]*)\" cannot be found in the category$", userConnections::checkIfUserIsNotInCategory);
        Then("^I edit category name from \"([^\"]*)\" to \"([^\"]*)\"$", userConnections::changeConenctionCategoryName);
        And("^I check if subtabs/categories called \"([^\"]*)\" and \"([^\"]*)\" are displayed$", userConnections::checkIfCategoriesAreDisplayed);
        Then("^I check if I see a red indicator which indicates pending connections$", userConnections::checkRedIndicatorVisible);
        And("^I click on Pending requests link$", userConnections::clickOnPendingRequestsLink);
        And("^I click Approve button for \"([^\"]*)\" user$", userConnections::clickApproveBtn);
        And("^I go to HS connections page$", userConnections::goToHSUserConnectionsPage);


    }

}
