package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HS.loginPage.LoginPageImpl;
import pageObjects.HS.homePage.HomePageImpl;

public class LoginPageStepDefs implements En {

    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();
        HomePageImpl homePage = new HomePageImpl();

        Given("^HS I am logged in to Intersect HS through Naviance with account \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$", loginPage::loginThroughNaviance);
        Given("^HS Iam navigating to Intersect HS through Non naviance Url$", loginPage::openNonNavianceLoginPage);
        Then("^HS I click the new user link in the login page$",loginPage::clickNewUserBtn);

        And("^HS I search for \"([^\"]*)\" in \"([^\"]*)\" and verify the results$",loginPage::searchForHSInstitution);

        When("^HS I want to login to the HS app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        Then("^HS I verify that the HS login page is displayed$", loginPage::verifyHSLoginPage);

        Given("^HS I verify the HS login screen$", loginPage::verifyLoginScreen);

        Given("^HS I verify that the following links are working as expected:$", loginPage::verifyLinks);

        Given("^HS I verify the following types of error messages in the HS login page:$", loginPage::verifyErrorMessages);

        Then("^HS I am locked out from logging in as user type \"([^\"]*)\"$", loginPage::userLockedOut);

        Then("^HS I am logged in to Intersect HS as user type \"([^\"]*)\"$", loginPage::defaultLogin);

        And("^HS I am able to successfully login$", homePage::verifyUserIsLoggedIn);

    }
}
