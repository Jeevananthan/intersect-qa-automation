package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.activeMatchPage.ActiveMatchPageImpl;

public class ActiveMatchPageStepDefs implements En {

    public ActiveMatchPageStepDefs() {

        ActiveMatchPageImpl activeMatchPage = new ActiveMatchPageImpl();

        Then("^HE The Active Match page is displayed$", activeMatchPage::verifyTitleIsPresent);
    }
}
