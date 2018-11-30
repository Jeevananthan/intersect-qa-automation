package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.*;

public class HUBSLoginStepDefs implements En{

    public HUBSLoginStepDefs() {

        CostsPageImpl costs = new CostsPageImpl();
        StudiesPageImpl studies = new StudiesPageImpl();
        StudentLifePageImpl studentLife = new StudentLifePageImpl();
        OverviewPageImpl overview = new OverviewPageImpl();
        InternationalPageImpl international = new InternationalPageImpl();
        AdmissionsPageImpl admissions = new AdmissionsPageImpl();
        HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();

        Then("^HUBS I should be able to verify the changes published in HUBS, with the following credentials:$", studies::verifyChangesPublishedInHUBS);

        Then("^HUBS I should be able to verify the changes for costs published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", costs::verifyChangesPublishedInHUBS);

        Then("^HUBS I should be able to verify the changes for Student Life published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", studentLife::verifyChangesPublishedInHUBS);

        Then("^HUBS I should be able to verify the changes for Overview published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", overview::verifyChangesPublishedInHUBS);

        Then("^HUBS I should be able to verify the changes for International published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", international::verifyChangesPublishedInHUBS);

        Then("^HUBS I should be able to verify the changes for admissions published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", admissions::verifyChangesPublishedInHUBS);

        Given("^HUBS I am logged to Naviance Student as user \"([^\"]*)\", with password \"([^\"]*)\" from school \"([^\"]*)\"$", hubsLogin::loginNavianceStudent);

        When("^HUBS I search for the college \"([^\"]*)\"$", hubsLogin::searchCollege);

        And("^HUBS I open the HUBS page for \"([^\"]*)\"$", hubsLogin::openHUBSPage);

        And("^HUBS I enable AM Next Gen using the URL part \"([^\"]*)\"$", hubsLogin::enableAMNextGenByURL);
    }
}
