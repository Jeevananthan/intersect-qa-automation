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
        And("^I go to Institutions that I'm following page$", userConnections::goToInstituionFollowingPage);
        Then("^I export my connections$", userConnections::exportConnections);
        And("^I check if connections CSV file \"([^\"]*)\" is exported successfully to location \"([^\"]*)\"$", userConnections::assertConnectionsCSVDownloaded);


    }

}
