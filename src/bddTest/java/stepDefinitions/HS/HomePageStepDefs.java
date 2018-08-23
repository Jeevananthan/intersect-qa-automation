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

        Then("^HS I verify the left navigation bar and section breadcrumbs are as follows$",navigationBar::verifyLeftNavAndBreadcrumbs);

        Then ("^HS I verify the Request New User page$",loginPage::validateFieldsInRequestUserForm);

        Then("^HS I verify the URL \"([^\"]*)\" of \"([^\"]*)\" page before clicking \"([^\"]*)\" link using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in the college profile page$",homePage::verifyAdditionalInfoURLBeforeClickingBackToIntersectLink);

        Then("^HS I verify the URL \"([^\"]*)\" after clicking \"([^\"]*)\" link using \"([^\"]*)\",\"([^\"]*)\" in the college profile page$",homePage::verifyAdditionalInfoURLAfterClickingBackToIntersectLink);

        Then("^HS I verify the navigation globe is displayed for this user$",navigationBar::verifyNotificationIconInHomePage);

        And("^HS I click the navigation globe for viewing the recent notifications$",navigationBar::clickNotificationsDropdown);

        Then("^HS I verify that the text in the button for \"([^\"]*)\" is \"([^\"]*)\"$", homePage::verifyTextInButtonFromModule);

        Then("^HS I verify that \"([^\"]*)\" is opened from the \"([^\"]*)\" module$", homePage::verifyScreenIsOpenFromModule);

        And("^HS I clear the account to get the community welcome page again$",homePage::clearCommunityProfile);

        When("^HS I verify that I am redirected to the Community activate profile page when accessing RepVisits$",homePage::verifyCommunityActivationForRepVisits);

        Then("^HS I verify the new user required to complete the Counselor Community profile form before they can access the following fields$",homePage::verifyRequiredPageforNewUser);

        Then("^HS I verify the following fields are required fields in the Counselor Community profile form$",homePage::verifyRequiredFieldsInCCProfileForm);

        Then("^HS I verify the user can access the following fields$",homePage::verifyingTabNavigation);

        And("^HS I activate my community profile by providing OfficePhone as \"([^\"]*)\" JobTitle as \"([^\"]*)\" and EU citizen as \"([^\"]*)\"$", homePage::fillCommunityWelcomeMandatoryFields);
    }
}
