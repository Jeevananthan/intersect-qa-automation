package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HUBS.CostsPageImpl;
import pageObjects.HUBS.StudiesPageImpl;

public class HUBSLoginStepDefs implements En{

    public HUBSLoginStepDefs() {

        CostsPageImpl costs = new CostsPageImpl();
        StudiesPageImpl studies = new StudiesPageImpl();

        Then("^HUBS I should be able to verify the changes published in HUBS, with the following credentials:$", studies::verifyChangesPublishedInHUBS);

        Then("^HUBS I should be able to verify the changes for costs published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", costs::verifyChangesPublishedInHUBS);
    }



}
