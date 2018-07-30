package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.COMMON.NavigationBarImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;
import pageObjects.HE.homePage.HomePageImpl;
import cucumber.api.java.cs.A;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        NavBarImpl navBar = new NavBarImpl();
        NavigationBarImpl navigationBar = new NavigationBarImpl();
        EventsPageImpl eventsPage = new EventsPageImpl();

        Then("^HE I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        And("^HE I successfully sign out$", navigationBar::logout);

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

        And("^HE I verify the \"([^\"]*)\" nav link is displaying for this user$",navigationBar::verifySubMenuIsVisible);

        And("^HE I verify the \"([^\"]*)\" nav link is not displaying for this user$",navigationBar::verifySubMenuIsNotVisible);

        And("^HE I activate my community profile by providing OfficePhone as \"([^\"]*)\" JobTitle as \"([^\"]*)\" and EU citizen as \"([^\"]*)\"$",
                homePage::fillCommunityWelcomeMandatoryFields);

        And("^HE I go to the Counselor Community$", navigationBar::goToCommunity);

        And("^HE I verify clicking on RepVisits will redirect to Search and Schedule tab of RepVisits$",
                homePage::verifyRepVisitsLandingPage);

        And("^HE I clear the account to get the community welcome page again$",homePage::clearCommunityProfile);

        And("^HE I open the Events list$", homePage::openEventList);

        Then("^HE Events section is not displayed for Community users$", eventsPage::verifyEventsNotPresent);

        And("^HE I open the Active Match section$", navigationBar::goToActiveMatch);

        And("^HE I open the Events section$", navigationBar::goToEvents);

        Then("^HE I verify the navigation globe is displayed for this user$",navBar::verifyNotificationIconInHomePage);

        And("^HE I click the navigation globe for viewing the recent notifications$",navBar::clickNavigationGlobeIcon);
    }
}
