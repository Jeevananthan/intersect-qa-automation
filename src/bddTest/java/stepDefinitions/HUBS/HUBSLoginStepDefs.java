package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.InternationalPageImpl;
import pageObjects.HUBS.OverviewPageImpl;
import pageObjects.HUBS.StudentLifePageImpl;
import pageObjects.HUBS.StudiesPageImpl;

public class HUBSLoginStepDefs implements En{

    public HUBSLoginStepDefs() {

        StudiesPageImpl studies = new StudiesPageImpl();
        StudentLifePageImpl studentLife = new StudentLifePageImpl();
        OverviewPageImpl overview = new OverviewPageImpl();
        InternationalPageImpl international = new InternationalPageImpl();

        Then("^HUBS I should be able to verify the changes published in HUBS, with the following credentials:$", studies::verifyChangesPublishedInHUBS);

        Then("^Then HUBS I should be able to verify the changes for Student Life published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", studentLife::verifyChangesPublishedInHUBS);

        Then("^Then HUBS I should be able to verify the changes for Overview published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", overview::verifyChangesPublishedInHUBS);

        Then("^Then HUBS I should be able to verify the changes for International published in HUBS, with username \"([^\"]*)\", password \"([^\"]*)\" and college \"([^\"]*)\", in the following sections$", international::verifyChangesPublishedInHUBS);
    }
}
