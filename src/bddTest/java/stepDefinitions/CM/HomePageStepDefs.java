package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();

        Then("^CM I verify that the upgrade widget is \"([^\"]*)\" for \"([^\"]*)\" users$", homePage::verifyUpgradeWidget);

    }
}
