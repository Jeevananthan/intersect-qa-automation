package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.HUBSHeaderPageImpl;

public class HUBSHeaderStepDefs implements En{

    public HUBSHeaderStepDefs() {

        HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

        And("^HUBS I successfully sign out$", header::clickLogOut);
    }

}
