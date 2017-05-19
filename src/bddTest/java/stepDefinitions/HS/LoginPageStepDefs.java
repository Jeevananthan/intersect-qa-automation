package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HS.loginPage.LoginPageImpl;
import pageObjects.HS.homePage.HomePageImpl;

public class LoginPageStepDefs implements En {

    public LoginPageStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();
        HomePageImpl homePage = new HomePageImpl();

        Given("^HS I am logged in to Intersect HS through Naviance with account \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$", loginPage::loginThroughNaviance);

        And("^HS I successfully sign out$", homePage::logout );

    }
}
