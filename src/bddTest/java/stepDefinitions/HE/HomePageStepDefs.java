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

        Then("^I verify that the \"([^\"]*)\" widget is displayed$",homePage::verifyWidgetIsVisible);

        Then("^I verify that the \"([^\"]*)\" widget is not displayed$",homePage::verifyWidgetIsNotVisible);

        When("^HE I verify that I am redirected to the Community activate profile page when accessing RepVisits$",
                homePage::verifyCommunityActivationForRepVisits);

        Then("^HE I verify the left navigation bar and section breadcrumbs are as follows$", navBar::verifyLeftNavAndBreadcrumbs);

        Then("^HE I click on Learn More button on Upgrade message on the Community Widget$",homePage::accessFreemiumLearnMoreOption);

        Then("^HE I verify the benefits of the Counselor Community popup and the details of the following freemium user$", homePage::verifyFullBenefitsofCounselorCommunity);

        Then("^HE I click on Request Information button Counselor Community popup$",homePage::accessCounselorCommunity);

        Then("^HE I verify the Confirmation message for Request Information$",homePage::verifyRequestInformation);

        And("^HE I verify the \"([^\"]*)\" nav link is displaying for this user$",navBar::verifySubMenuIsVisible);

        And("^HE I verify the \"([^\"]*)\" nav link is not displaying for this user$",navBar::verifySubMenuIsNotVisible);

        And("^HE I activate my community profile by providing OfficePhone as \"([^\"]*)\" and JobTitle as \"([^\"]*)\"$",
            homePage::fillCommunityWelcomeMandatoryFields);

        And("^HE I go to the Counselor Community$", navBar::goToCommunity);

        And("^HE I verify clicking on RepVisits will redirect to Search and Schedule tab of RepVisits$",
                homePage::clickRepVisits);

        And("^HE I clear the account to get the community welcome page again$",homePage::clearCommunityProfile);

    }
}
