package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.HE.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        NavBarImpl navBar = new NavBarImpl();

        Then("^HE I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        And("^HE I successfully sign out$", homePage::logout);

        Then("^HE I access the Account Settings page$", homePage::accountSettings);

        Then("^HE I verify the Account Settings page for the following user$", homePage::verifyAccountSettings);

        Then("^HE I verify the Update Profile page for the following user$", homePage::verifyUpdateProfile);

        Then("^HE I verify I have access the Intersect Help page$", homePage::accessHelpPage);

        Then("^HE I verify the upgrade message on the Community widget$", homePage::verifyCommunityUpgradeMessage);

        And("^HE I verify the \"([^\"]*)\" menu is displaying for this user$",navBar::verifySubMenuIsVisible);

        And("^HE I verify the \"([^\"]*)\" menu is not displaying for this user$",navBar::verifySubMenuIsNotVisible);

        Then("^HE I am logging out from HE interset app$",homePage::logout);


    }
}
