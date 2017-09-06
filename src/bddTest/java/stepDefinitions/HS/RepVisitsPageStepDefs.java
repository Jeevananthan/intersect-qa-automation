package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HS.repVisitsPage.RepVisitsPageImpl;

public class RepVisitsPageStepDefs implements En {

    RepVisitsPageImpl repVisits = new RepVisitsPageImpl();

    public RepVisitsPageStepDefs() {

        Then("^HS I verify the following tabs exist on the RepVisits page$", repVisits::checkRepVisitsSubTabs);

        Then("^HS I verify the Availability & Settings tab of the RepVisits page$", repVisits::verifyAvailabilityAndSettingsPage);

        Then("^HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data$", repVisits::verifyAvailabilitySettings);

        Then("^HS I set the Accept option of RepVisits Visit Scheduling to \"([^\"]*)\" \"([^\"]*)\" visits per day$", repVisits::setAcceptinAvailabilitySettings);

        Then("^HS I set the Accept option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setAcceptInVisitSchedulingToFullyBooked);

        Then("^HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page$", repVisits::verifyContentsOfNavianceSettings);

        Then("^HS I verify the Regular Weekly Hours section of the Availability subtab of the Availability & Settings tab in RepVisits$", repVisits::verifyContentsOfRegularWeeklyHours);

        Then("^HS I verify the time zone in Repvisits Availability & Settings is \"([^\"]*)\"$",repVisits::verifyTimeZonePage);

        And("^HS I set the RepVisits Availability & Settings time zone to \"([^\"]*)\"$", repVisits::setTimeZone);

        And("^HS I click on Availability on the Availability & Settings tab in RepVisits$",repVisits::clickLinkAvailability);

        And("^HS I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("^HS I set the RepVisits Visits Confirmations option to \"([^\"]*)\"$", repVisits::setVisitsConfirmations);

        Then("^HE I verify the blocked \"([^\"]*)\" page$", repVisits::verifyManualBlockedHolidays);

        Then("HE I search for \"([^\"]*)\" in RepVisits page using \"([^\"]*)\" with \"([^\"]*)\"$", repVisits::searchforSchool);
    }
}
