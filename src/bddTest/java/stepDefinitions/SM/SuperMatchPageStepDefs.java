package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class SuperMatchPageStepDefs implements En {

    public SuperMatchPageStepDefs() {

        FCSuperMatchPageImpl fcSuperMatch = new FCSuperMatchPageImpl();

        Then("^SM I verify that the link in the new SuperMatch banner takes me to the new SuperMatch$", fcSuperMatch::verifySuperMatchBannerLink);
    }
}
