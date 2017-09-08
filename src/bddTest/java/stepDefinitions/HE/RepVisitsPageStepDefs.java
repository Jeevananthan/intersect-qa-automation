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

        And("^HE I verify empty contacts page in Contacts$", repVisits::verifyEmptyContactPage);

        And("^HE I verify the contacts page in Contacts$", repVisits::verifyFullContactPage);

        And("^HE I verify contacts details  in Contacts$", repVisits::verifyContactDetails);

        And("^HE I search for \"([^\"]*)\" in Contacts$", repVisits::searchforContact);

        And("^HE I search for partial data of \"([^\"]*)\" in Contacts$", repVisits::partialsearchforContact);

        And("^HE I search for invalid data of \"([^\"]*)\" in Contacts$", repVisits::verifyinvalidcontact);

        And("^HE I search for partial data in Contacts using \"([^\"]*)\",\"([^\"]*)\"$",repVisits::searchforPartialdata);

        And("^HE I verify the contacts page is full or empty$",repVisits::verifyFullorEmpty);

        And("^HE I verify the contacts list is sorted or not$",repVisits::sortingContacts);

        And("^HE I validating the pagination of 25 contacts in Contacts Page$",repVisits::validatingthePaginationof25Contacts);
    }

}
