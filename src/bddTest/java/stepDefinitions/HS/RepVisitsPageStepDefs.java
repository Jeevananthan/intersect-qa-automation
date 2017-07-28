package stepDefinitions.HS;

import cucumber.api.PendingException;
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

        Then("^HS I set a date using \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::setStartAndEndDates);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date was set in the calendar$", repVisits::verifyStartAndEndDates);

        Then("^HS I verify the time zone in Repvisits Availability & Settings is \"([^\"]*)\"$",repVisits::verifyTimeZonePage);

        And("^HS I set the RepVisits Availability & Settings time zone to \"([^\"]*)\"$", repVisits::setTimeZone);

        And("^HS I click on Availability on the Availability & Settings tab in RepVisits$",repVisits::clickLinkAvailability);

        And("^HS I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("^HS I set the RepVisits Visits Confirmations option to \"([^\"]*)\"$", repVisits::setVisitsConfirmations);

        Then("^HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesSchedulingNewVisits);

        Then("^HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesCancellingorRescheduling);

        And ("^HS I verify the repvisit setup wizard displayed for high school information$",repVisits::verifyRepvisitsSetupWizardTimeZoneMilestones);

        Then ("^HS I check the time zone is selected as \"([^\"]*)\" and change it to \"([^\"]*)\"$",repVisits::verifyTimeZoneInRepVisits);

        Then ("^HS I am verifying the welcome milestone in setup wizard$",repVisits::verifyWelcomeWizard);

        And ("^HS I click the Get Started button in the welcome milestone page$",repVisits::clickGetStartedBtn);

        And ("^HS I navigate to college fairs,visits through availability option$",repVisits::navigateToFairsAndVisistsAndVerifyEachScreen);

        Then ("^HS I set the Visit Availability of RepVisits Availability Settings to \"([^\"]*)\"$",repVisits::accessVisitAvailability);

        Then ("^HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with \"([^\"]*)\"$",repVisits::verifyVisitAvailability);

        When("^HS I open the Exceptions page$", repVisits::openExceptions);

        And("^HS I select the date \"([^\"]*)\"$", repVisits::selectDateInExceptions);

        And("^HS I add a new time slot with the following data:$", repVisits::addTimeSlot);

        Then("^HS I verify that the time slot was added in date \"([^\"]*)\", with the start time \"([^\"]*)\"$", repVisits::verifyTimeSlot);

        And("^HS I delete the time slot in date \"([^\"]*)\", with start time \"([^\"]*)\"$", repVisits::deleteTimeSlot);

        And("^HS I verify that the time slot was removed from date \"([^\"]*)\", with the start time \"([^\"]*)\"$", repVisits::verifyAbsenceOfTimeSlot);

        Then("^HS I clear the day$", repVisits::clearDay);
    }
}
