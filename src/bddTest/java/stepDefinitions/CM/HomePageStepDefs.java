package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();

        Then("^CM I verify that the upgrade widget is \"([^\"]*)\" for \"([^\"]*)\" users$", homePage::verifyUpgradeWidget);

        Then("^I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        And("^I successfully sign out from the HE app$", homePage::logoutHE);

        And("^I successfully sign out from the Support app$", homePage::logoutSupport);

        And("^I go to Counselor Community page$", homePage::accessCounselorCommunityPage);

        And("^I sign out from the HE app$", homePage::logoutHEDefault);

        And("^I sign out from the Support app$", homePage::logoutSupport);

        Then("^I check if Home posts are visible$", homePage::checkIfHomePostsAreVisible);

    }
}
