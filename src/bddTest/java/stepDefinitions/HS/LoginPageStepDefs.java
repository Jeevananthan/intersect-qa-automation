package stepDefinitions.HS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HS.loginPage.LoginPageImpl;
import pageObjects.HS.homePage.HomePageImpl;

public class LoginPageStepDefs implements En {

    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();
        HomePageImpl homePage = new HomePageImpl();

        Given("^HS I am logged in to Intersect HS through Naviance with user type \"([^\"]*)\"$", loginPage::loginThroughNaviance);

        Given ("^HS I am logged in to Intersect HS through Naviance with account \"([^\"]*)\"$",loginPage::loginNaviance);

        Given("^HS I am navigating to Intersect HS through Non naviance Url$", loginPage::openNonNavianceLoginPage);

        Then("^HS I click the new user link in the login page$",loginPage::clickNewUserBtn);

        And("^HS I search for \"([^\"]*)\" in \"([^\"]*)\" and verify the results$",loginPage::searchForHSInstitution);

        When("^HS I want to login to the HS app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        When("^HS I will block HS app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::blockAccount);

        Then("^HS I verify that the HS login page is displayed$", loginPage::verifyHSLoginPage);

        Given("^HS I verify the HS login screen$", loginPage::verifyLoginScreen);

        Given("^HS I verify that the following links are working as expected:$", loginPage::verifyLinks);

        Given("^HS I verify the following types of error messages in the HS login page:$", loginPage::verifyErrorMessages);

        Then("^HS I am locked out from logging in as user type \"([^\"]*)\"$", loginPage::userLockedOut);

        Then("^HS I am logged in to Intersect HS as user type \"([^\"]*)\"$", loginPage::defaultLogin);

        And("^HS I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        Then ("^HS I verify the address page of \"([^\"]*)\" which is a \"([^\"]*)\" school in \"([^\"]*)\"$",loginPage::verifyHSAddressPage);

        Given("^HS I navigate to Registration Intersect url$",loginPage::navigateToHSRegistrationPage);

        Then("^HS I verify the Institution page$",loginPage::verifyInstitutionPage);

        Then("^HS I search for \"([^\"]*)\" in High School Staff Member registration page$",loginPage::searchInstitution);

        Then("^HS I verify the link \"([^\"]*)\"$",loginPage::verifyLink);

        Then("^HS I verify the Intersect Logo present in the Login Page$",loginPage::verifyLogoInLoginPage);

        Then("^HS I verify the Intersect Logo present in the Home Page$",loginPage::verifyLogoInHomePage);

        Then("^SP I am logged in to Support for Intersect$", loginPage::defaultLoginForSupport);

        Then("^HS I verify the search results on the registration page contain \"([^\"]*)\"$", loginPage::verifySearchResultsOnRegistrationPage);

        Then("^HS I go to the Naviance Page with user type \"([^\"]*)\"$",loginPage::loginToNaviancePage);

        Then("^HS I select \"([^\"]*)\" from the registration page search results$", loginPage::selectInstitutionFromRegistration);
    }
}
