package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.COMMON.HelpImpl;

public class HelpStepdefs implements En {

    public HelpStepdefs() {

        HelpImpl help = new HelpImpl();

        Then("^HE I verify that the help content is secure and matches the correct URL for \"([^\"]*)\"$", help::verifyHelpExistAndSecure);
    }
}
