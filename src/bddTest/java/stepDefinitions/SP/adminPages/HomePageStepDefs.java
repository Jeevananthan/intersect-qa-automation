package stepDefinitions.SP.adminPages;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.SP.accountPages.UserListPageImpl;
import pageObjects.SP.adminPages.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        UserListPageImpl userList = new UserListPageImpl();
        NavBarImpl navBar = new NavBarImpl();

        Then("^SP I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        Then("^SP I see the following security message \"([^\"]*)\"$", homePage::verifySecurityMessage);

        And("^SP I successfully sign out$", homePage::logout);

        When("^SP I click on an institution name$", homePage::selectTheFistInstitutionOnTheList);

        Then("^SP I select \"([^\"]*)\" from the institution dashboard$", homePage::goToInstitution);

        Then("^SP I go to the users list for \"([^\"]*)\" from the institution dashboard$", homePage::goToUsersList);

        And("^SP I navigate to create user page$",homePage::navigateToCreateUser);

        Then("^SP I go to the Create user for \"([^\"]*)\" from the institution dashboard$", homePage::goToCreateUser);

        Then("^SP I fill the create user form \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",userList::fillFormInCreateUser);

        Then("^SP I go to the Log History for \"([^\"]*)\" from the institution dashboard$", homePage::goToLogHistory);

        Then("^SP I do not have access to \"([^\"]*)\" sub menu in left navigation$", navBar::verifySubMenuIsNotVisible);

        Then("^SP I do have access to \"([^\"]*)\" sub menu in left navigation$", navBar::verifySubMenuIsVisible);

        Then("^SP I go to the users list for \"([^\"]*)\" with NCES_ID \"([^\"]*)\" from the institution dashboard using the search$", homePage::goToUsersListUsingSearch);

        Then("^SP verify the error message in create user page$",userList::verifyErrorMessageinCreateUser);

        Then("^SP verify there is no empty field error message in the create user page$",userList::verifyNoErrorMessageinCreateUser);

        Then("^SP I verify the visit details are present in the Log History page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",userList::verifyVisitDetailsInLogHistory);

        Then("^SP I verify the Fairs details are present in the Log History page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",userList::verifyFairDetailsInLogHistory);

        Then("^SP I verify the Logged in details are present in the Log History page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",userList::verifyLoggedInDetailsInLogHistory);

        Then("^SP I verify the created post details are present in the Log History page using \"([^\"]*)\",\"([^\"]*)\"$",userList::verifyCreatedPostDetailsInLogHistory);

        Then("^SP I go to \"([^\"]*)\"$", homePage::goToInstitution);

        Then("^SP I add post in the Homepage \"([^\"]*)\"$",navBar::addPost);

        Then("^SP I verify the user update details are present in the Log History page using \"([^\"]*)\",\"([^\"]*)\"$", userList::verifyUserUpdateInLogHistory);

        Then("^HE I verify the current year is displayed at the bottom of the window in the login page$",homePage::verifyYearInLoginPage);

        Then("^HE I verify the current year is displayed at the bottom of the window in the Registration page$",homePage::verifyYearInRegistrationPage);

        Then("^HE I verify the current year is displayed at the bottom of the window in the Home Page$",homePage::verifyYearInHomePage);

        And("^HE I verify the items are present in the help center dropdown$",homePage::verifyHelpCentre);

        Then("^SP I go to the users list for \"([^\"]*)\" user, institution \"([^\"]*)\" from the institution dashboard$",homePage::goToAccountUsersList);

        When("^SP I navigate to the GraphiQL page$", homePage::navigateToGraphiQL);
    }
}
