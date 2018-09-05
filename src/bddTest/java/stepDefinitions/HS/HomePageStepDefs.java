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
//        NavBarImpl navBar = new NavBarImpl();
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

        Then("^HS I verify the current year is displayed at the bottom of the window in the Naviance page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",homePage::verifyYearInNaviancePage);

        Then("^HS I verify the current year is displayed at the bottom of the window in the RepVisits Page$",homePage::verifyYearInRepVisitsPage);

        Then("^HS I verify the current year is displayed at the bottom of the window in the login page for Naviance$",homePage::verifyYearInNavianceLoginPage);

    }
}
