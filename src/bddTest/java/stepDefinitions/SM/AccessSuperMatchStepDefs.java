package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.loginPage.LoginPageImpl;


public class AccessSuperMatchStepDefs implements En {

    public AccessSuperMatchStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        Given("^SM I am logged in to SuperMatch through Family Connection$", loginPage::defaultLoginThroughFamilyConnection);

        Given("^SM I am logged in to SuperMatch through Family Connection as user \"([^\"]*)\" with password \"([^\"]*)\" from school \"([^\"]*)\"$",loginPage::loginThroughFamilyConnection);

        Given("^SM I am logged in to SuperMatch through Family Connection as user type \"([^\"]*)\"$",loginPage::loginThroughFamilyConnectionByType);

        Given("^SM I am logged in to SuperMatch through the SuperMatch link$", loginPage::navigateToSuperMatch);

        Then("^I clear the onboarding popups if present$", loginPage::clearOnboardingPopups);
    }
}
