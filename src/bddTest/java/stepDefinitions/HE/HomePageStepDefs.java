package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;
import pageObjects.HE.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        EventsPageImpl eventsPage = new EventsPageImpl();

        Then("^HE I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        And("^HE I successfully sign out$",  homePage::logout);

        Then("^HE I access the Account Settings page$", homePage::accountSettings);

        Then("^HE I verify the Account Settings page for the following user$", homePage::verifyAccountSettings);

        Then("^HE I verify the Update Profile page for the following user$", homePage::verifyUpdateProfile);

        Then("^HE I verify I have access the Intersect Help page$", homePage::accessHelpPage);

        Then("^HE I verify the upgrade message on the Community widget$", homePage::verifyCommunityUpgradeMessage);

        Then("^I verify that the \"([^\"]*)\" widget is displayed$",homePage::verifyWidgetIsVisible);

        Then("^I verify that the \"([^\"]*)\" widget is not displayed$",homePage::verifyWidgetIsNotVisible);

        Then("^HE I verify the left navigation bar and section breadcrumbs are as follows$", homePage::verifyLeftNavAndBreadcrumbs);

        Then("^HE I click on Learn More button on Upgrade message on the Community Widget$",homePage::accessFreemiumLearnMoreOption);

        Then("^HE I verify the benefits of the Counselor Community popup and the details of the following freemium user$", homePage::verifyFullBenefitsofCounselorCommunity);

        Then("^HE I click on Request Information button Counselor Community popup$",homePage::accessCounselorCommunity);

        Then("^HE I verify the Confirmation message for Request Information$",homePage::verifyRequestInformation);

        And("^HE I verify the \"([^\"]*)\" nav link is displaying for this user$",homePage::verifySubMenuIsVisible);

        And("^HE I verify the \"([^\"]*)\" nav link is not displaying for this user$",homePage::verifySubMenuIsNotVisible);

        And("^HE I go to the Counselor Community$", homePage::goToCommunity);

        And("^HE I verify clicking on RepVisits will redirect to Search and Schedule tab of RepVisits$",
                homePage::verifyRepVisitsLandingPage);

        And("^HE I open the Events list$", homePage::openEventList);

        Then("^HE Events section is not displayed for Community users$", eventsPage::verifyEventsNotPresent);

        And("^HE I open the Active Match section$", homePage::goToActiveMatch);

        And("^HE I open the Events section$", homePage::goToEvents);

        Then("^HE I verify the navigation globe is displayed for this user$",homePage::verifyNotificationIconInHomePage);

        And("^HE I click the navigation globe for viewing the recent notifications$",homePage::clickNotificationsDropdown);

        Then("^HE I verify that the text in the button for \"([^\"]*)\" is \"([^\"]*)\"$", homePage::verifyTextInButtonFromModule);

        Then("^HE I verify that \"([^\"]*)\" is opened from the \"([^\"]*)\" module$", homePage::verifyScreenIsOpenFromModule);

        Then("^HE I verify the header 'Product Announcement' is displaying in the product announcements 'Read More' drawer$",homePage::verifyHeaderInProductAnnouncementsReadMoreDrawer);

        Then("^HE I verify the close button is displaying in the product announcements 'Read More' drawer$",homePage::verifyCloseButtonInProductAnnouncementsReadMoreDrawer);

        Then("^HE I verify the title \"([^\"]*)\" is displaying in the product announcements 'Read More' drawer$",homePage::verifyTitleInProductAnnouncementsReadMoreDrawer);

        Then("^HE I verify the date is displaying next to the title \"([^\"]*)\" with \"([^\"]*)\" format in the product announcements 'Read More' drawer$",homePage::verifyDateFormatInProductAnnouncementsReadMoreDrawer);

        Then("^HE I verify the content \"([^\"]*)\" is displaying in the product announcements 'Read More' drawer$",homePage::verifyContentInProductAnnouncementsReadMoreDrawer);

        Then("^HE I click close button in the product announcements 'Read More' drawer$",homePage::clickCloseButtonInProductAnnouncementsReadMoreDrawer);

    }
}
