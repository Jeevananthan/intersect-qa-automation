package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.loginPage.LoginPageImpl;

public class LoginPageStepDefs implements En {
    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        //Given("^I want to login to the Community through the HE app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", LoginLogout::loginHE);

        //Given("^I am logged in to Purple Community through the HE App$",LoginLogout::defaultLoginHE);

        //Given("^I am logged in to Purple Community through the Support App$",LoginLogout::defaultLoginSupport);
        //Given("^I am logged in to Purple Community through the HS App$", LoginLogout::defaultLoginHS);

    }
}
