package stepDefinitions.SP.adminPages;

import cucumber.api.java8.En;
import pageObjects.SP.adminPages.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();

        Then("^SP I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        Then("^SP I see the following security message \"([^\"]*)\"$", homePage::verifySecurityMessage);

        And("^SP I successfully sign out$", homePage::logout);

        When("^SP I click on an institution name$", homePage::selectTheFistInstitutionOnTheList);

        Then("^SP I go to the institution dashboard and make sure \"([^\"]*)\" is on the dashboard$", homePage::verifyInstitutionExist);

        Then("^SP I go to the institution dashboard and make sure \"([^\"]*)\" is not on the dashboard$", homePage::verifyInstitutionDoesNotExist);

        Then("^SP I select \"([^\"]*)\" from the institution dashboard$", homePage::goToInstitution);

        Then("^SP I go to the users list for \"([^\"]*)\" from the institution dashboard$", homePage::goToUsersList);
    }
}
