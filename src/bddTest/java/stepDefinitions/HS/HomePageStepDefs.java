package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.COMMON.NavigationBarImpl;
import pageObjects.HE.loginPage.LoginPageImpl;
import pageObjects.HS.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        LoginPageImpl loginPage = new LoginPageImpl();
        NavigationBarImpl navigationBar = new NavigationBarImpl();

        And("^HS I successfully sign out$", homePage::logout );

        And("^HS I go to the Counselor Community$", homePage::goToCounselorCommunity);

        And("^HS I search for \"([^\"]*)\" in the registration page$",loginPage::searchForHEInstitution);
        And ("^HS I click the link \"([^\"]*)\"$",loginPage::clickLinkInRegisterationPage);
        Then ("^HS I verify all field type in request user page$",loginPage::validateRequestUserForm);
        And("^HS I enter the following data in request user page$",loginPage::enterDataInRequestUserForm);

        And ("^HS I verify the page Title is showing as \"([^\"]*)\" and \"([^\"]*)\"$",homePage::verifyTitleHS);

        And ("^HS I go to the Rep Visits$",navigationBar::goToRepVisits);

        Then("^HS I verify the left navigation bar and section breadcrumbs are as follows$",navigationBar::verifyLeftNavAndBreadcrumbsForHS);

        Then ("^HS I verify the Request New User page$",loginPage::validateFieldsInRequestUserForm);

        Then("^HS I verify the URL \"([^\"]*)\" of \"([^\"]*)\" page before clicking \"([^\"]*)\" link using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in the college profile page$",homePage::verifyAdditionalInfoURLBeforeClickingBackToIntersectLink);

        Then("^HS I verify the URL \"([^\"]*)\" after clicking \"([^\"]*)\" link using \"([^\"]*)\",\"([^\"]*)\" in the college profile page$",homePage::verifyAdditionalInfoURLAfterClickingBackToIntersectLink);

        Then("^HS I verify the navigation globe is displayed for this user$",navigationBar::verifyNotificationIconInHomePage);

        And("^HS I click the navigation globe for viewing the recent notifications$",navigationBar::clickNotificationsDropdown);

        Then("^HS I verify that the text in the button for \"([^\"]*)\" is \"([^\"]*)\"$", homePage::verifyTextInButtonFromModule);

        Then("^HS I verify that \"([^\"]*)\" is opened from the \"([^\"]*)\" module$", homePage::verifyScreenIsOpenFromModule);

        Then("^HS I verify the current year is displayed at the bottom of the window in the login page$",homePage::verifyYearInLoginPage);

        Then("^HS I verify the current year is displayed at the bottom of the window in the Registration page$",homePage::verifyYearInRegistrationPage);

        Then("^HS I verify the current year is displayed at the bottom of the window in the Home Page$",homePage::verifyYearInHomePage);

        And("^HS I verify the items are present in the help center dropdown$",homePage::verifyHelpCentre);

        Then("^HS I verify the current year is displayed at the bottom of the window in the RepVisits Page$",homePage::verifyYearInRepVisitsPage);

        Then("^HS I verify the current year is displayed at the bottom of the window in the login page for Naviance$",homePage::verifyYearInNavianceLoginPage);

        Then("^HS I verify the header 'Product Announcement' is displaying in the product announcements 'Read More' drawer$",homePage::verifyHeaderInProductAnnouncementsReadMoreDrawer);

        Then("^HS I verify the close button is displaying in the product announcements 'Read More' drawer$",homePage::verifyCloseButtonInProductAnnouncementsReadMoreDrawer);

        Then("^HS I verify the title \"([^\"]*)\" is displaying in the product announcements 'Read More' drawer$",homePage::verifyTitleInProductAnnouncementsReadMoreDrawer);

        Then("^HS I verify the date is displaying next to the title \"([^\"]*)\" with \"([^\"]*)\" format in the product announcements 'Read More' drawer$",homePage::verifyDateFormatInProductAnnouncementsReadMoreDrawer);

        Then("^HS I verify the content \"([^\"]*)\" is displaying in the product announcements 'Read More' drawer$",homePage::verifyContentInProductAnnouncementsReadMoreDrawer);

        Then("^HS I click close button in the product announcements 'Read More' drawer$",homePage::clickCloseButtonInProductAnnouncementsReadMoreDrawer);

        Then("^HS I successfully sign out from the Naviance$",homePage::logoutFromNaviance);

        And("^HS I clear the account to get the community welcome page again$",homePage::clearHSCommunityProfile);

        When("^HS I verify that I am redirected to the Community activate profile page when accessing RepVisits$", homePage::verifyHSCommunityActivationForRepVisits);

        Then("^HS I verify the new user required to complete the Counselor Community profile form before they can access the following fields$",homePage::verifyRequiredPageforNewUser);

        Then("^HS I verify the following fields are required fields in the Counselor Community profile form$",homePage::verifyRequiredFieldsInCCProfileFormForHS);

        And("^HS I activate my community profile by providing OfficePhone as \"([^\"]*)\" JobTitle as \"([^\"]*)\" and EU citizen as \"([^\"]*)\"$", homePage::fillCommunityWelcomeMandatoryFieldsForHS);

        And("^HS I verify clicking on RepVisits will redirect to Search and Schedule tab of RepVisits$", homePage::verifyRepVisitsLandingPageForHS);
    }
}
