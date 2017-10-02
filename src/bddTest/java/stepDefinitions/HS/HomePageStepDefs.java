package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.HE.loginPage.LoginPageImpl;
import pageObjects.HS.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        LoginPageImpl loginPage = new LoginPageImpl();
        NavBarImpl navBar = new NavBarImpl();

        And("^HS I successfully sign out$", homePage::logout );

        And("^HS I go to the Counselor Community$", homePage::goToCounselorCommunity);

        Given("^HS I navigate to Registration Intersect url$",loginPage::navigateToRegistrationPage);
        And("^HS I search for \"([^\"]*)\" in \"([^\"]*)\" registeration page$",loginPage::searchForHEInstitution);
        And ("^HS I click the link \"([^\"]*)\"$",loginPage::clickLinkInRegisterationPage);
        Then ("^HS I verify all field type in request user page$",loginPage::validateFieldsInRequestUserForm);
        And("^HS I enter the following data in request user page$",loginPage::enterDataInRequestUserForm);

        And ("^HS I verify the page Title is showing as \"([^\"]*)\" and \"([^\"]*)\"$",homePage::verifyTitleHS);

        And ("^HS I go to the Rep Visits$",navBar::goToRepVisits);

        Then("^HS I verify the left navigation bar and section breadcrumbs are as follows$",navBar::verifyLeftNavAndBreadcrumbs);
        Given("^HS I navigate to Registration Intersect url$",loginPage::navigateToRegistrationPage);

        And("^HS I search for \"([^\"]*)\" in \"([^\"]*)\" registeration page$",loginPage::searchForHEInstitution);

    }
}
