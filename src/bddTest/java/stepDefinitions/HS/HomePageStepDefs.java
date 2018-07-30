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

        And ("^HS I go to the Rep Visits$",navBar::goToRepVisits);

        Then("^HS I verify the left navigation bar and section breadcrumbs are as follows$",navBar::verifyLeftNavAndBreadcrumbs);

        Then ("^HS I verify the Request New User page$",loginPage::validateFieldsInRequestUserForm);

        Then("^HS I verify the URL \"([^\"]*)\" of \"([^\"]*)\" page before clicking \"([^\"]*)\" link using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in the college profile page$",homePage::verifyAdditionalInfoURLBeforeClickingBackToIntersectLink);

        Then("^HS I verify the URL \"([^\"]*)\" after clicking \"([^\"]*)\" link using \"([^\"]*)\",\"([^\"]*)\" in the college profile page$",homePage::verifyAdditionalInfoURLAfterClickingBackToIntersectLink);

        Then("^HS I verify the navigation globe is displayed for this user$",navBar::verifyNotificationIconInHomePage);

        And("^HS I click the navigation globe for viewing the recent notifications$",navBar::clickNavigationGlobeIcon);

    }
}
