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

        And("^HS I search for \"([^\"]*)\" in \"([^\"]*)\"$",loginPage::searchForHSInstitution);

    }
}
