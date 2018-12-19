package stepDefinitions.SP.accountPages;

import cucumber.api.java8.En;
import pageObjects.SP.accountPages.UserListPageImpl;

public class UserListPageStepDefs implements En  {

    public UserListPageStepDefs() {

        UserListPageImpl userListPage = new UserListPageImpl();

        And("^SP I \"([^\"]*)\" the user account for \"([^\"]*)\"$",userListPage::setUserStatus);

        Then("^SP I verify that the user account for \"([^\"]*)\" is \"([^\"]*)\"$",userListPage::verifyUserStatus);

        And("^SP I set the user \"([^\"]*)\" to be the new primary user$",userListPage::setPrimaryUser);

        Then("^SP I verify that the user account for \"([^\"]*)\" is the primary user$",userListPage::verifyUserIsPrimary);

        Then("^SP I verify that the user account for \"([^\"]*)\" is not the primary user$",userListPage::verifyUserIsNotPrimary);

        Then("^SP I verify that I can create a new primary user using create new user button$",userListPage::verifyCreateUserButton);

        Then("^SP I verify that I can edit the Primary User Details$", userListPage::verifyEditPrimaryUserDetails);

        And("^SP I Login as the user \"([^\"]*)\"$", userListPage::loginAs);

        And("^SP I verify the \"([^\"]*)\" message in the homepage$",userListPage::verifyLoginMessageInHomPage);

        Then("^SP I receive the Intersect Invitation email with the following data:$", userListPage::verifyInviteEmail);

        And("^SP I check the impersonated window$",userListPage::checkImpersonatedWindow);

        And("^SP I check the \"([^\"]*)\" option will be hidden for impersonation$",userListPage::checkImpersonationAccountSettings);

        And("^SP I use \"([^\"]*)\" feature for login as \"([^\"]*)\"$",userListPage::loginAsWithNonNavianceUser);

    }

}
