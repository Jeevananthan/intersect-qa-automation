package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HE.loginPage.LoginPageImpl;
import pageObjects.HS.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();
        LoginPageImpl loginPage = new LoginPageImpl();

        And("^HS I successfully sign out$", homePage::logout );

        And("^HS I go to the Counselor Community$", homePage::goToCounselorCommunity);

        Given("^HS I navigate to Registration Intersect url$",loginPage::navigateToRegistrationPage);

        And("^HS I search for \"([^\"]*)\" in \"([^\"]*)\" registeration page$",loginPage::searchForHEInstitution);

        Then ("^HS I verify Request for New User page$",loginPage::validateFieldsInRequestUserForm);


    }
}
