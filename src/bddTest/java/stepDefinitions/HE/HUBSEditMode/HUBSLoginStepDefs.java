package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.*;

public class HUBSLoginStepDefs implements En{

    public HUBSLoginStepDefs() {

        StudiesPageImpl studies = new StudiesPageImpl();

        Then("^HUBS I should be able to verify the changes published in HUBS, with the following credentials:$", studies::verifyChangesPublishedInHUBS);
    }



}
