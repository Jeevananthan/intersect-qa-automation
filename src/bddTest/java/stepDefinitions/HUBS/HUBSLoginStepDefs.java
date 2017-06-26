package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.StudiesPageImpl;

public class HUBSLoginStepDefs implements En{

    public HUBSLoginStepDefs() {

        StudiesPageImpl studies = new StudiesPageImpl();

        Then("^HUBS I should be able to verify the changes published in HUBS, with the following credentials:$", studies::verifyChangesPublishedInHUBS);
    }



}
