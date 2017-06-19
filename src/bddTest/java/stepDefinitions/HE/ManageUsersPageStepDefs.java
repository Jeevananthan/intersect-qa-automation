package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.manageUsersPage.ManageUsersPageImpl;

public class ManageUsersPageStepDefs implements En {

    public ManageUsersPageStepDefs() {

        ManageUsersPageImpl manageUsersPage = new ManageUsersPageImpl();

        Then("^HE I inactivate the user account for \"([^\"]*)\"$",manageUsersPage::inactivateUser);

        Then("^HE I activate the user account for \"([^\"]*)\"$",manageUsersPage::activateUser);

        Then("^HE I unlock the user account for \"([^\"]*)\"$",manageUsersPage::unlockUser);

        Then("^HE I edit the user account for \"([^\"]*)\" with the following info$",manageUsersPage::editUser);

        Then("^HE I verify the user roles available in my institution$",manageUsersPage::verifyUserRoles);

        Then("^HE I verify the Manage Users screen contains the following user$",manageUsersPage::verifyUserData);

        Then("^HE I receive the \"Matching Account has been Updated\" email below$",manageUsersPage::verifyEmailChangedNotification);

        Then("^HE I can see the last login date for user type \"([^\"]*)\"$", manageUsersPage::verifyLastLoginData);

        And("^HE I can create a user in HE app$",manageUsersPage::createHigherEducationUser);

    }

}
