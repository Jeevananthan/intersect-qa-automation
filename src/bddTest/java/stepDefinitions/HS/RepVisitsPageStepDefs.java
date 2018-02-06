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

        And("^HS I verify the UI of the Messaging Options Page$", repVisits::VerifyMessagingOptionsUI);

        And("^HS I set the Special Instructions Text as \"([^\"]*)\";$", repVisits::SetSpecialInstructionsForHEUser);

        And("^HS I verify the Special Instructions are \"([^\"]*)\"$", repVisits::VerifySpecialInstructionsForHE);

        Then("^HS I set the visit availability dates to \"([^\"]*)\" through \"([^\"]*)\"$", repVisits::setStartAndEndDates);

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

        Then("^HS I set a date using \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::setStartAndEndDates);

        Then("HS I remove the Time Slot created with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::removeTimeSlotAdded);

        Then("^HS I verify the Time Slot time were removed with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::verifyTimeSlotRemoved);

        Then("^HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesSchedulingNewVisits);

        Then("^HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesCancellingorRescheduling);

        And("^HS I cancel the fair of name \"([^\"]*)\" with the reason \"([^\"]*)\"$", repVisits::cancelFair);

        Then("^HS I create a new college fair with the following details:$", repVisits::createFair);

        And ("^HS I verify the Calendar Sync Milestone in the setup wizard of repvisits$",repVisits::verifyCalendarSyncMilestoneInSetupWizard);

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

        When("^HS I open the Exceptions page$", repVisits::openExceptions);

        And("^HS I select the date \"([^\"]*)\"$", repVisits::selectDateInExceptions);

        And("^HS I add a new time slot with the following data:$", repVisits::addTimeSlot);

        Then("^HS I verify that the time slot was added in date \"([^\"]*)\", with the start time \"([^\"]*)\"$", repVisits::verifyTimeSlot);

        And("^HS I delete the time slot in date \"([^\"]*)\", with start time \"([^\"]*)\"$", repVisits::deleteTimeSlot);

        And("^HS I verify that the time slot was removed from date \"([^\"]*)\", with the start time \"([^\"]*)\"$", repVisits::verifyAbsenceOfTimeSlot);

        Then("^HS I clear the day$", repVisits::clearDay);

        Then("^HE I set and verify that \"([^\"]*)\" is blocked on the Blocked Days page$", repVisits::verifyManualBlockedHolidays);

        Then("HE I search for \"([^\"]*)\" in RepVisits page using \"([^\"]*)\" and verify that \"([^\"]*)\" is blocked$", repVisits::searchforSchool);

        And("^HS I verify the calendar view in RepVisits$",repVisits::verifyCalendarViewOnRepVisits);

        And("^HS I change the primary contact from \"([^\"]*)\" to \"([^\"]*)\" and verify that the save option is working",repVisits::verifyNotificationAndPrimaryContactInSetupWizard);

        Then("^HS I go to welcome wizard of the repvisits$",repVisits::goToWelcomeWizard);

        And("^HS I navigate to \"([^\"]*)\" wizard in repvisits$",repVisits::navigateToRepvisitWizard);

        Then("^HS I add the time slot in \"([^\"]*)\" with start time as \"([^\"]*)\" and end time as \"([^\"]*)\" and \"([^\"]*)\" vistis$",repVisits::addTimeSlotInRegularWeeklyHours);

        And ("^HS I navigate to sub tab \"([^\"]*)\" in availability wizard$",repVisits::navigateToSubTabsInAvailabilityWizard);

        Then ("^HS I select \"([^\"]*)\" in blocked days tab and verify saving option works successfully$",repVisits::selectBlockDaysAndSave);

        Then ("^HS I change to \"([^\"]*)\" in exception and verify saving option works successfully$",repVisits::changeWeekAvailability);

        Then("^HS I set the RepVisits Visits Confirmations option to \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::availabilityandSettingsPage);

        And ("^HS I verify the update button appears and I click update button$",repVisits::clickUpdateButtonInRepVisits);

        And ("^HS I verify the StartDate is set to \"([^\"]*)\" and EndDate is set to \"([^\"]*)\"$",repVisits::verifyStartDateAndEndDateInAvailabilitySetting);

        Then("^HS I select option for welcome in setup wizard \"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessWelcomeSetupWizard);

        Then("^HS I select the \"([^\"]*)\" option on the welcome page in the RepVisits setup wizard$", repVisits::accessWelcomeSetupWizard);

        Then("^HS I select option for High School Information in welcome setup wizard \"([^\"]*)\",\"([^\"]*)\"$",repVisits::accessHighschoolInformationSetupWizard);

        Then("^HS I verify the Fair overview page$",repVisits::verifyFairOverview);

        Then("^HS I select the \"([^\"]*)\" option for Visit Availability on the 'One Last Step' page$",repVisits::accessOneLastStepSetupWizard);

        Then("^HS I verify the 'You're All Set' page is correct when Visit Availability is set to \"([^\"]*)\"$",repVisits::verifyYouAreAllSetPage);

        Then("^HS verify pills are not available for the past dates in schedule new visit page$",repVisits::verifyPillsNotAvailableinNewScheduleVisitPage);

        Then("^HS verify the past dates are disabled in the select custom date section$",repVisits::verifyPastDatesDisabledInNewScheduleVisitPage);

        Then("^HS verify pills are not available for the past dates in Re-schedule visit page$",repVisits::verifyPillsNotAvailableinReScheduleVisitPage);

        Then("^HS verify the past dates are disabled in the select custom date section for Re-schedule visit page$",repVisits::verifyPastDatesDisabledInNewScheduleVisitPage);

        Then("^HS I set the RepVisits Confirmation message to \"([^\"]*)\"$", repVisits::addDefaultMessage);

        Then("^HS I verify the RepVisits Confirmation message is set to \"([^\"]*)\"$", repVisits::verifyMessageUpdated);

        And("^HS I verify the messaging updated confirmation toast message$", repVisits::verifyMessageConfirmation);

        Then("^HS I navigate to the \"([^\"]*)\" page in RepVisits$", repVisits::navigateToRepVisitsSection);

        Then("^HS I cancel all events for the next 7 days$", repVisits::cancelAllEventsForNext7Days);

        Then("^HS I verify the RepVisits Overview page when no events are scheduled for the next 7 days$", repVisits::verifyRepVisitsPageWhenNoVisitsScheduledForNext7Days);

        And("^HS I select a date \"([^\"]*)\" days ahead from now$", repVisits::selectGeneratedDateInExceptions);

        Then("^HS I verify that the time slot was added in a generated date, with the start time \"([^\"]*)\"$", repVisits::verifyTimeSlotWithGeneratedDate);

        And("^HS I delete the time slot in a generated date, with start time \"([^\"]*)\"$", repVisits::deleteTimeSlotWithGeneratedDate);

        And("^HS I verify that the time slot was removed from the generated date, with the start time \"([^\"]*)\"$", repVisits::verifyAbsenceOfTimeSlotWithGeneratedDate);

        Then("^HS I verify the Messaging Options Page in the repvists setup wizard \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyRepvisitsSetupWizardMessagingOptions);

        Then("^HS I enter the messages in the Message Option page for Repvists Setup wizard \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::accessRepvisitsSetupWizardMessagingOptions);

        Then("^HS I verify the Primary Contact for Visits page and then click the \"([^\"]*)\" button$",repVisits::verifyPrimaryContactVisitsPage);

        Then("^HS I verify the Availability Settings page and then click the \"([^\"]*)\" button$",repVisits::verifyAvailabilitySettingsPage);
        And("^HS I Click button Add a College Fair to Add a fair$",repVisits::clickAddCollegeFairButton);
        And("^HS I verify Note on Add Edit Fair screen \"([^\"]*)\"$",repVisits:: noteForSchools);
        And("^HS I click on close icon on Add Edit College Fair pop-up$",repVisits:: closeAddEditFairScreen);
        And("^HS I click View Details against fair \"([^\"]*)\"$",repVisits::viewFairDetails);
        And("^HS I click on Edit button to edit fair$",repVisits:: editFair);

    }
}
