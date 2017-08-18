package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.RepVisitsPageImpl;

public class RepVisitsPageStepDefs implements En {

    RepVisitsPageImpl repVisits = new RepVisitsPageImpl();

    public RepVisitsPageStepDefs() {

        Then("^HE I verify the following tabs exist on the RepVisits page$", repVisits::checkRepVisitsSubTabs);

        Then("^HE I verify the Search and Schedule tab of the RepVisits page$", repVisits::verifySearchAndSchedulePage);

        And("^HE I search for \"([^\"]*)\" in RepVisits$", repVisits::searchforHighSchool);

        Then("^HE I select \"([^\"]*)\" in \"([^\"]*)\" from the RepVisits intermediate search results$", repVisits::selectHighSchoolFromIntermediateSearchResults);

        Then("^HE I view the map plugin on RepVisits Search & Schedule subtab$", repVisits::viewMapPlugin);

        And("^HE I select \"([^\"]*)\" from the RepVisists map plugin$", repVisits::selectSchoolFromMap);

        And("^HE I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("HE I verify the high school information popup contains the following data", repVisits::checkHighSchoolPopUp);

        Then ("^HE I verify the upsell messaging on the Travel Plan page in RepVisits$",repVisits::verifyUpgradeMessageInTravelPlanInRepVisits);

        Then("^HE I verify the Check RepVisits Availability button$", repVisits::verifyCheckRepVisitsAvailabilityButton);

        Then("^HE I select Visits to schedule the appointment for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::visitsSchedule);

        Then("^HE I verify the schedule pop_up for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifySchedulePopup);

        Then("HE verify the Pills got disappear for \"([^\"]*)\"$",repVisits::verifyPills);

        Then("^HE I search for school in RepVisits using \"([^\"]*)\"$",repVisits::searchSchoolinRepVisits);
    }
}
