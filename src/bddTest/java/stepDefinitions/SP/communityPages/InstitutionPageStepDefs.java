package stepDefinitions.SP.communityPages;

import cucumber.api.java8.En;
import pageObjects.SP.communityPages.InstitutionPageImpl;

public class InstitutionPageStepDefs implements En {
    InstitutionPageImpl collProfile = new InstitutionPageImpl();

    public InstitutionPageStepDefs() {

        Then("^SP I verify Hubs view mode for \"([^\"]*)\"$", collProfile::goToHubsPage);

    }

}