package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.loginPage.LoginPageImpl;


public class AccessSuperMatchStepDefs implements En {

    public AccessSuperMatchStepDefs() {

        LoginPageImpl loginPage = new LoginPageImpl();

        Given("^SM I am logged in to SuperMatch through Family Connection$", loginPage::defaultLoginThroughFamilyConnection);

        Then("^SM I sign out of SuperMatch through Family Connection$", loginPage::logoutFromFamilyConnection);

        Given("^SM I am logged in to SuperMatch through Family Connection as user \"([^\"]*)\" with password \"([^\"]*)\" from school \"([^\"]*)\"$",loginPage::loginThroughFamilyConnection);

        Given("^SM I am logged in to SuperMatch through Family Connection in Staging with the following details$", loginPage::defaultLoginThroughFamilyConnectionStaging);

        Given("^SM I am logged in to SuperMatch through the SuperMatch link$", loginPage::navigateToSuperMatchDirectly);
    }
}
