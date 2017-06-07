package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.*;

public class HUBSHeaderStepDefs implements En{

    public HUBSHeaderStepDefs() {

        HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

        And("^HUBS I successfully sign out$", header::clickLogOut);
    }

}
