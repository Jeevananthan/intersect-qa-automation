package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HS.loginPage.LoginPageImpl;

public class LoginPageStepDefs implements En {

    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        Given("^HS I am logged in to Intersect HS through Naviance with account \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$", loginPage::loginThroughNaviance);

        When("^HS I want to login to the HS app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        Then("^HS I verify that the HS login page is displayed$", loginPage::verifyHSPage);

        Then ("^HS I verify the address page of the \"([^\"]*)\" which is \"([^\"]*)\" school using \"([^\"]*)\"$",loginPage::verifyHSAddressPage);

        Given("^HS I navigate to Registration Intersect url$",loginPage::navaigateToRegistration);

        Then("^HS I verify the Institution page$",loginPage::verifyInstitutionPage);

        Then("^HS I search for \"([^\"]*)\" in High School Staff Member registeration page$",loginPage::searchInstitution);

        Then("^HS I verify the link \"([^\"]*)\"$",loginPage::verifyLink);

    }
}
