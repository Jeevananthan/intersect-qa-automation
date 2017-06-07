package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();

        Then("^HE I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        And("^HE I successfully sign out$", homePage::logout);

        Then("^HE I access the Account Settings page$", homePage::accountSettings);

        Then("^HE I verify the Account Settings page for the following user$", homePage::verifyAccountSettings);

        Then("^HE I verify the Update Profile page for the following user$", homePage::verifyUpdateProfile);

        Then("^HE I verify I have access the Intersect Help page$", homePage::accessHelpPage);

        Then("^HE I verify the upgrade message on the Community widget$", homePage::verifyCommunityUpgradeMessage);

        Then("^HE I click on Learn More button on Upgrade to Preemium Widget$",homePage::accessFreemiumLearnMoreOption);

        Then("^HE I verify the benefits of the Counselor Community popup and the details of the following freemium user$", homePage::verifyFullBenefitsofCounselorCommunity);

        Then("^I click on Request Information button Counselor Community popup$",homePage::accessCounselorCommunity);

        Then("^I verify the Confirmation message for Request Information$",homePage::verifyRequestInformation);


    }
}
