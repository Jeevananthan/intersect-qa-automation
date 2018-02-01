package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.loginPage.LoginPageImpl;

public class LoginPageStepDefs implements En {
    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        //Given("^I want to login to the Community through the HE app using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::loginHE);

        //Given("^I am logged in to Purple Community through the HE App$",loginPage::defaultLoginHE);

        //Given("^I am logged in to Purple Community through the Support App$",loginPage::defaultLoginSupport);
        //Given("^I am logged in to Purple Community through the HS App$", loginPage::defaultLoginHS);

    }
}
