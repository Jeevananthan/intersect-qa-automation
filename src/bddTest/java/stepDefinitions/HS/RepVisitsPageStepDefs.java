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
        And("^HS I verify the UI of the Messaging Options Page$", repVisits::VerifyMessagingOptionsUI);
        And("^HS I set the Special Instructions Text as \"([^\"]*)\";$", repVisits::SetSpecialInstructionsForHEUser);
        And("^HS I verify the Special Instructions are \"([^\"]*)\"$", repVisits::VerifySpecialInstructionsForHE);

        Then("^HS I set a date using \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::setStartAndEndDates);

        Then("^HS I verify the Time Slot time were added with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::verifyTimeSlotAdded);

        Then("^HS I add new time slot with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::addNewTimeSlot);

        Then("^HS I verify the Regular Weekly Hours section of the Availability subtab of the Availability & Settings tab in RepVisits$", repVisits::verifyContentsOfRegularWeeklyHours);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date was set in the calendar$", repVisits::verifyStartAndEndDates);

        Then("^HS I verify the time zone in Repvisits Availability & Settings is \"([^\"]*)\"$",repVisits::verifyTimeZonePage);

        And("^HS I set the RepVisits Availability & Settings time zone to \"([^\"]*)\"$", repVisits::setTimeZone);

        And("^HS I click on Availability on the Availability & Settings tab in RepVisits$",repVisits::clickLinkAvailability);
        And("^HS I Navigate to College Fairs tab of the Repvisits Page$",repVisits::clicklinkCollegeFair);
        And("^HS I verify the College Fair Blank DashBoard Message$",repVisits::verifyCollgeFairBlankDashBoard);

        And("^HS I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("^HS I set the RepVisits Visits Confirmations option to \"([^\"]*)\"$", repVisits::setVisitsConfirmations);

        Then("^HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesSchedulingNewVisits);

        Then("^HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesCancellingorRescheduling);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date with \"([^\"]*)\" was present in the Holidays tab in the Availability & Settings page in RepVisits$",repVisits::verifyBlockedHolidaysAdded);

        Then("^HS I set the Blocked date as \"([^\"]*)\" and select the reason as \"([^\"]*)\" in the Holiday tab$",repVisits::setBlockedDate);

        Then("^HS I click the Remove option for the \"([^\"]*)\" and \"([^\"]*)\" in the Holiday tab$",repVisits::RemoveBlockedDate);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date with \"([^\"]*)\" was not present in the Holidays tab in the Availability & Settings page in RepVisits",repVisits::verifyBlockedHolidaysRemoved);

        And ("^HS I verify the repvisit setup wizard displayed for high school information$",repVisits::verifyRepvisitsSetupWizardTimeZoneMilestones);

        Then ("^HS I check the time zone is selected as \"([^\"]*)\" and change it to \"([^\"]*)\"$",repVisits::verifyTimeZoneInRepVisits);

        Then ("^HS I am verifying the welcome milestone in setup wizard$",repVisits::verifyWelcomeWizard);

        And ("^HS I click the Get Started button in the welcome milestone page$",repVisits::clickGetStartedBtn);

        And ("^HS I navigate to college fairs,visits through availability option$",repVisits::navigateToFairsAndVisistsAndVerifyEachScreen);

        Then ("^HS I set the Visit Availability of RepVisits Availability Settings to \"([^\"]*)\"$",repVisits::accessVisitAvailability);

        Then ("^HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with \"([^\"]*)\"$",repVisits::verifyVisitAvailability);

        Then("^HE I set and verify that \"([^\"]*)\" is blocked on the Blocked Days page$", repVisits::verifyManualBlockedHolidays);

        Then("HE I search for \"([^\"]*)\" in RepVisits page using \"([^\"]*)\" and verify that \"([^\"]*)\" is blocked$", repVisits::searchforSchool);
    }
}
