package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.HUBSHeaderPageImpl;

public class HUBSHeaderStepDefs implements En{

    public HUBSHeaderStepDefs() {

        HUBSHeaderPageImpl header = new HUBSHeaderPageImpl();

        And("^HUBS I successfully sign out$", header::clickLogOut);

        And("^HUBS I add the college to the I'm thinking about list, if it is not already added$", header::addColToImThinkingAboutListIfNotAlreadyThere);

        And("^HUBS I verify that the AM Next Gen component is loaded when the user clicks the Connect button$", header::verifyAMNextGenIsLoadedWhenClickConnect);

        And("^HUBS I verify that the Legacy AM component is loaded when the user clicks the Connect button$", header::verifyLegacyAMNIsLoadedWhenClickConnect);
    }

}
