package stepDefinitions.SP.communityPages;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SP.communityPages.InstitutionPageImpl;

public class InstitutionPageStepDefs implements En {
    InstitutionPageImpl collProfile = new InstitutionPageImpl();

    public InstitutionPageStepDefs() {

        Then("^SP I verify Hubs view mode for \"([^\"]*)\"$", collProfile::goToHubsPage);
        Then("^SP I verify the user can successfully access Counselor Community from within the Support App$",collProfile::accessCounselorCommunity);
        Then("^SP I verify the user can access the following sub tabs in the Counselor Community$",collProfile::accessFieldsInCounselorCommunity);
        Then("^SP I verify the user tied to the Hobsons institution$",collProfile::verifyUser);
    }

}