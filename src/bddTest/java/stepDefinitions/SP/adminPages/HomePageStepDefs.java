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

        Then("^SP I go to the institution dashboard and make sure \"([^\"]*)\" is on the dashboard$", homePage::verifyInstitutionExist);

        Then("^SP I go to the institution dashboard and make sure \"([^\"]*)\" is not on the dashboard$", homePage::verifyInstitutionDoesNotExist);

        Then("^SP I select \"([^\"]*)\" from the institution dashboard$", homePage::goToInstitution);

        Then("^SP I go to the users list for \"([^\"]*)\" from the institution dashboard$", homePage::goToUsersList);

        And("^SP I navigate to create user page$",homePage::navigateToCreateUser);

        Then("^SP I go to the Create user for \"([^\"]*)\" from the institution dashboard$", homePage::goToCreateUser);

        Then("^SP I fill the create user form \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",userList::fillFormInCreateUser);

        Then("^SP I go to the Log History for \"([^\"]*)\" from the institution dashboard$", homePage::goToLogHistory);

        Then("^SP I do not have access to \"([^\"]*)\" sub menu in left navigation$", navBar::verifySubMenuIsNotVisible);

        Then("^SP I do have access to \"([^\"]*)\" sub menu in left navigation$", navBar::verifySubMenuIsVisible);

        Then("^SP verify the error message in create user page$",userList::verifyErrorMessageinCreateUser);

    }
}
