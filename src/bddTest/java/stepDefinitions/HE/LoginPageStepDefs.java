package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.loginPage.LoginPageImpl;

public class LoginPageStepDefs implements En {
    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        Given("^HE I want to login to the HE app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        Given("^HE I am logged in to Intersect HE as user type \"([^\"]*)\"$",loginPage::defaultLogin);

        Given("^HE I want to create a New User via the Request Primary User page$",loginPage::createNewUser);

        Given("^HE I am prevented from logging in as user type \"([^\"]*)\"$",loginPage::failedLogin);

        Given("^HE I am locked out from logging in as user type \"([^\"]*)\"$",loginPage::userLockedOut);

        Given("^HE I begin the reset password process for user type \"([^\"]*)\"$",loginPage::beginResetPassword);

        Given("^HE I receive the below reset password email and reset the password for user type \"([^\"]*)\"$",loginPage::processResetPassword);

        Given("^HE I verify the HE login screen$",loginPage::verifyLoginScreen);

        And ("^HE I click the link \"([^\"]*)\"$",loginPage::clickLinkInRegisterationPage);

        Then ("^HE I verify all field type in request user page$",loginPage::validateFieldsInRequestUserForm);

        Given("^HE I navigate to Registration Intersect url$",loginPage::navigateToRegistrationPage);

        And("^HE I search for \"([^\"]*)\" in \"([^\"]*)\" registration page$",loginPage::searchForHEInstitution);

        Then("^HE I verify all field in request user page$",loginPage::verifyFields);

        Then("^HE I verify captcha in request user page$",loginPage::verifyCaptcha);

        Then("^HE I validate all fields in request user page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",loginPage::validateFields);

        Given("^HE I navigate to the Intersect Registration app$",loginPage::navigateToRegistrationPage);

        Then ("^HE I select \"([^\"]*)\" and verify that the appropriate text is displayed$",loginPage::goToAppropriateRegistrationpage);


    }
}
