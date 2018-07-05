package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.COMMON.HelpImpl;

public class HelpStepdefs implements En {

    public HelpStepdefs() {

        HelpImpl help = new HelpImpl();

        Then("^HS I verify that the help content is secure and matches the correct URL for \"([^\"]*)\"$", help::verifyHelpExistAndSecure);

        Then("HS I verify that the help content is not available for \"([^\"]*)\"$", help::verifyHelpExistAndSecure);

        And("^HS I navigate to each page and verify the unique URL is present in the \"([^\"]*)\" page in Help Center$",help::verifyURLinHSCounselorCommunityGuidelines);
    }
}
