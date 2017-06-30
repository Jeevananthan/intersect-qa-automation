package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HS.loginPage.LoginPageImpl;

public class LoginPageStepDefs implements En {

    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        Given("^HS I am logged in to Intersect HS through Naviance with account \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$", loginPage::loginThroughNaviance);

        When("^HS I want to login to the HS app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        Then("^HS I verify that the HS login page is displayed$", loginPage::verifyHSPage);

        Then ("^HS I verify the address page of the \"([^\"]*)\" which is \"([^\"]*)\" school$",loginPage::verifyHSAddressPage);

    }
}
