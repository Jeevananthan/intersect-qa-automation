package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.RepVisitsPageImpl;

public class RepVisitsPageStepDefs implements En {

    RepVisitsPageImpl repVisits = new RepVisitsPageImpl();

    public RepVisitsPageStepDefs() {

        Then("^HE I verify the following tabs exist on the RepVisits page$", repVisits::checkRepVisitsSubTabs);

        Then("^I verify the Search and Schedule tab of the RepVisits page$", repVisits::verifySearchAndSchedulePage);

    }
}
