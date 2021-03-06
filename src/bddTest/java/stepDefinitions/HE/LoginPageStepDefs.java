package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.loginPage.LoginPageImpl;

public class LoginPageStepDefs implements En {
    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        Given("^HE I want to login to the HE app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        Given("^HE I am logged in to Intersect HE as user type \"([^\"]*)\"$",loginPage::defaultLogin);

        Given("^HEM I am logged in to Intersect HEM as user type \"([^\"]*)\"$",loginPage::defaultHEMLogin);

        Given("^HE I want to request for a New User account via the Request User Account page$",loginPage::createNewUser);

        Given("^HE I am prevented from logging in as user type \"([^\"]*)\"$",loginPage::failedLogin);

        Given("^HE I am locked out from logging in as user type \"([^\"]*)\"$",loginPage::userLockedOut);

        Given("^HE I begin the reset password process for user type \"([^\"]*)\"$",loginPage::beginResetPassword);

        Given("^HE I receive the below reset password email and reset the password for user type \"([^\"]*)\"$",loginPage::processResetPassword);

        Given("^HE I verify the HE login screen$",loginPage::navigateToLoginScreenAndVerify);

        And ("^HE I click the link \"([^\"]*)\"$",loginPage::clickLinkInRegisterationPage);

        Then ("^HE I verify all field type in request user page$",loginPage::validateFieldsInRequestUserForm);

        Given("^HE I navigate to the Intersect Registration app$",loginPage::navigateToRegistrationPage);

        Then ("^HE I select \"([^\"]*)\" and verify that the appropriate text is displayed$",loginPage::goToAppropriateRegistrationpage);

        Then("^HE I verify the Email Notification Message for \"([^\"]*)\" using \"([^\"]*)\",\"([^\"]*)\"$",loginPage::verifyEmailNotification);

        Given("^HE I navigate to Registration Intersect url$",loginPage::navigateToRegistrationPage);

        And("^HE I search for \"([^\"]*)\" in \"([^\"]*)\" register page$",loginPage::searchForHEInstitutionWithInvalidData);

        Then("^HE I verify captcha in request user page$",loginPage::verifyCaptcha);

        And("^HE I click on update button on Component Naviance College Profile$",loginPage:: updateNavianceCollegeProfile);

        And("^HE I verify user is redirected to Counselor Community Welcome page$", loginPage:: counselorCommunityWelcomePge);

        Given("^HE I am logged in to Intersect HE as user type \"([^\"]*)\" and url \"([^\"]*)\"$",loginPage::loginWithUrl);
    }
}
