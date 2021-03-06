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

        Then("^HS I add new time slot with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::addnewTimeSlot);

        Then("^HS I verify the Regular Weekly Hours section of the Availability subtab of the Availability & Settings tab in RepVisits$", repVisits::verifyContentsOfRegularWeeklyHours);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date was set in the calendar$", repVisits::verifyStartAndEndDates);

        Then("^HS I verify the time zone in Repvisits Availability & Settings is \"([^\"]*)\"$", repVisits::verifyTimeZonePage);

        And("^HS I set the RepVisits Availability & Settings time zone to \"([^\"]*)\"$", repVisits::setTimeZone);

        And("^HS I click on Availability on the Availability & Settings tab in RepVisits$",repVisits::clickLinkAvailability);

        And("^HS I Navigate to College Fairs tab of the Repvisits Page$", repVisits::clicklinkCollegeFair);

        And("^HS I verify the College Fair Blank DashBoard Message$", repVisits::verifyCollgeFairBlankDashBoard);

        And("^HS I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("^HS I set the RepVisits Visits Confirmations option to \"([^\"]*)\"$", repVisits::setVisitsConfirmations);

        Then("^HS I set a date using \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::setStartAndEndDates);

        Then("^HS I set my RepVisits availability to the current school year$", repVisits::setAvailabilityToFullSchoolYear);

        Then("^HS I set the date using \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::setStartandEndDates);

        Then("^HS I verify that availability dates are from \"([^\"]*)\" to \"([^\"]*)\" for visits the days \"([^\"]*)\",\"([^\"]*)\" in the calendar$", repVisits::verifyAvaliabilityDates);

        Then("HS I remove the Time Slot created with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::removeTimeSlotAdded);

        Then("^HS I verify the Time Slot time were removed with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::verifyTimeSlotRemoved);

        Then("^HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesSchedulingNewVisits);

        Then("^HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to \"([^\"]*)\"$", repVisits::setPreventCollegesCancellingorRescheduling);

        And("^HS I cancel the fair of name \"([^\"]*)\" with the reason \"([^\"]*)\"$", repVisits::cancelFair);

        Then("^HS I create a new college fair with the following details:$", repVisits::createFair);

        And("^HS I verify the Calendar Sync Milestone in the setup wizard of repvisits$", repVisits::verifyCalendarSyncMilestoneInSetupWizard);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date with \"([^\"]*)\" was present in the Holidays tab in the Availability & Settings page in RepVisits$", repVisits::verifyBlockedHolidaysAdded);

        Then("^HS I set the Blocked date as \"([^\"]*)\" and select the reason as \"([^\"]*)\" in the Holiday tab$", repVisits::setBlockedDate);

        Then("^HS I verify in exceptions the appointments color and status for \"([^\"]*)\" with color \"([^\"]*)\"$", repVisits::verifyPillsColorDays);

        Then("^HS I click the Remove option for the \"([^\"]*)\" and \"([^\"]*)\" in the Holiday tab$", repVisits::RemoveBlockedDate);

        Then("^HS I verify the \"([^\"]*)\" and \"([^\"]*)\" date with \"([^\"]*)\" was not present in the Holidays tab in the Availability & Settings page in RepVisits", repVisits::verifyBlockedHolidaysRemoved);

        And("^HS I verify the repvisit setup wizard displayed for high school information$", repVisits::verifyRepvisitsSetupWizardTimeZoneMilestones);

        Then("^HS I check the time zone is selected as \"([^\"]*)\" and change it to \"([^\"]*)\"$", repVisits::verifyTimeZoneInRepVisits);

        Then("^HS I am verifying the welcome milestone in setup wizard$", repVisits::verifyWelcomeWizard);

        And("^HS I click the Get Started button in the welcome milestone page$", repVisits::clickGetStartedBtn);

        And("^HS I navigate to college fairs,visits through availability option$", repVisits::navigateToFairsAndVisistsAndVerifyEachScreen);

        Then("^HS I set the Visit Availability of RepVisits Availability Settings to \"([^\"]*)\"$", repVisits::accessVisitAvailability);

        Then("^HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with \"([^\"]*)\"$", repVisits::verifyVisitAvailability);

        When("^HS I open the Exceptions page$", repVisits::openExceptions);

        And("^HS I select the date \"([^\"]*)\"$", repVisits::selectDateInExceptions);

        And("^HS I add a new time slot with the following data:$", repVisits::addTimeSlot);

        Then("^HS I verify that the time slot was added in date \"([^\"]*)\", with the start time \"([^\"]*)\"$", repVisits::verifyTimeSlot);

        And("^HS I delete the time slot in date \"([^\"]*)\", with start time \"([^\"]*)\"$", repVisits::deleteTimeSlot);

        And("^HS I verify that the time slot was removed from date \"([^\"]*)\", with the start time \"([^\"]*)\"$", repVisits::verifyAbsenceOfTimeSlot);

        Then("^HS I clear the day$", repVisits::clearDay);

        Then("^HE I set and verify that \"([^\"]*)\" is blocked on the Blocked Days page$", repVisits::verifyManualBlockedHolidays);

        Then("HE I search for school \"([^\"]*)\" in RepVisits page using \"([^\"]*)\" and verify that \"([^\"]*)\" is blocked$", repVisits::searchforSchool);

        Then("HE I search in \"([^\"]*)\" in RepVisits page using \"([^\"]*)\" and verify that \"([^\"]*)\" is blocked actually$", repVisits::searchforSchool);

        And("^HS I verify the calendar view in RepVisits$", repVisits::verifyCalendarViewOnRepVisits);

        And("^HS I change the primary contact from \"([^\"]*)\" to \"([^\"]*)\" and verify that the save option is working", repVisits::verifyNotificationAndPrimaryContactInSetupWizard);
        And("^HS I am Navigating to Calendar Home Screen$", repVisits::navigateToRVCalendar);
        And("^HS I click on button Add Visit$", repVisits::buttonAddVisit);
        //And("^HS I click to select date of visit \"([^\"]*)\"$",repVisits:: selectVisitDate);
        And("^HS I select representative from drop down \"([^\"]*)\"$", repVisits::selectRepresentative);
        And("^HS I Enter Internal Notes \"([^\"]*)\"$", repVisits::addVisitInternalNotes);
        And("^HS I click on Add Visit button$", repVisits::addVisitHSUser);
        And("^HS I select Month and Date of Visit \"([^\"]*)\" and day \"([^\"]*)\"$", repVisits::pickMonthDateforVisit);
        And("^Hs I open the date picker$", repVisits::datepicker);
        And("^HS I click on Calendar Dashboard Arrow  to navigate to \"([^\"]*)\"$", repVisits::goToMonthOnCalendar);
        And("^^HS I Verify \"([^\"]*)\" on \"([^\"]*)\" is present on Calendar$", repVisits::visitGetsAdded);


        And("^HS I navigate to the \"([^\"]*)\" Page$", repVisits::navaigateToAccountSettings);

        And("^HS I verify the left-sub menu \"([^\"]*)\" is present in the Account Settings page$", repVisits::verifyAccountsettings);

        And("^HS I verify the non-password fields \"([^\"]*)\" are pre-populated with current data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyPasswordFields);

        And("^HS I validate the password field \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::validatePassword);

        And("^HS I reset the password for \"([^\"]*)\",\"([^\"]*)\"$", repVisits::resetPassword);

        And("^HS I verify the success message \"([^\"]*)\" in Account settings page$", repVisits::verifySuccessMessageinAccountSettingsPage);

        Then("^HS I go to welcome wizard of the repvisits$", repVisits::goToWelcomeWizard);

        And("^HS I navigate to \"([^\"]*)\" wizard in repvisits$", repVisits::navigateToRepvisitWizard);

        Then("^HS I add the time slot in \"([^\"]*)\" with start time as \"([^\"]*)\" and end time as \"([^\"]*)\" and \"([^\"]*)\" visits with \"([^\"]*)\"$", repVisits::addTimeSlotInRegularWeeklyHours);

        And("^HS I navigate to sub tab \"([^\"]*)\" in availability wizard$", repVisits::navigateToSubTabsInAvailabilityWizard);

        Then("^HS I select \"([^\"]*)\" in blocked days tab and verify saving option works successfully$", repVisits::selectBlockDaysAndSave);

        Then("^HS I change to \"([^\"]*)\" in exception and verify saving option works successfully$", repVisits::changeWeekAvailability);

        Then("^HS I set the RepVisits Visits Confirmations option to \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::availabilityandSettingsPage);

        And("^HS I verify the update button appears and I click update button$", repVisits::clickUpdateButtonInRepVisits);

        And("^HS I verify the StartDate is set to \"([^\"]*)\" and EndDate is set to \"([^\"]*)\"$", repVisits::verifyStartDateAndEndDateInAvailabilitySetting);

        Then("^HS I select option for welcome in setup wizard \"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessWelcomeSetupWizard);

        Then("^HS I select the \"([^\"]*)\" option on the welcome page in the RepVisits setup wizard$", repVisits::accessWelcomeSetupWizard);

        Then("^HS I select option for High School Information in welcome setup wizard \"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessHighschoolInformationSetupWizard);

        Then("^HS I verify the Fair overview page$", repVisits::verifyFairOverview);

        Then("^HS I select the \"([^\"]*)\" option for Visit Availability on the 'One Last Step' page$", repVisits::accessOneLastStepSetupWizard);

        Then("^HS I verify the 'You're All Set' page is correct when Visit Availability is set to \"([^\"]*)\"$", repVisits::verifyYouAreAllSetPage);

        Then("^HS verify pills are not available for the past dates in schedule new visit page$", repVisits::verifyPillsNotAvailableinNewScheduleVisitPage);

        Then("^HS verify the past dates are disabled in the select custom date section$", repVisits::verifyPastDatesDisabledInNewScheduleVisitPage);

        Then("^HS verify pills are not available for the past dates in Re-schedule visit page$", repVisits::verifyPillsNotAvailableinReScheduleVisitPage);

        Then("^HS verify the past dates are disabled in the select custom date section for Re-schedule visit page$", repVisits::verifyPastDatesDisabledInNewScheduleVisitPage);

        Then("^HS I set the RepVisits Confirmation message to \"([^\"]*)\"$", repVisits::addDefaultMessage);

        Then("^HS I verify the RepVisits Confirmation message is set to \"([^\"]*)\"$", repVisits::verifyMessageUpdated);

        And("^HS I verify the messaging updated confirmation toast message$", repVisits::verifyMessageConfirmation);

        Then("^HS I navigate to the \"([^\"]*)\" page in RepVisits$", repVisits::navigateToRepVisitsSection);

        Then("^HS I cancel all events for the next 7 days$", repVisits::cancelAllEventsForNext7Days);

        Then("^HS I verify the RepVisits Overview page when no events are scheduled for the next 7 days$", repVisits::verifyRepVisitsPageWhenNoVisitsScheduledForNext7Days);

        Then("^HS I add the new time slot with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\" with \"([^\"]*)\"$", repVisits::addnewTimeSlot);

        Then("^HS I verify time slot was not created in Exception$", repVisits::verifyTimeSlotInExceptions);



        //And("^HS I set the following data to On the College Fair page \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$", repVisits::accessCreateCollegeFair);

        And("^HS I select a date \"([^\"]*)\" days ahead from now$", repVisits::selectGeneratedDateInExceptions);
        And("^HS I select a date \"([^\"]*)\" days ahead from now from the standard date picker$", repVisits::datePickeronAgendaView);


        Then("^HS I verify that the time slot was added in a generated date, with the start time \"([^\"]*)\"$", repVisits::verifyTimeSlotWithGeneratedDate);

        And("^HS I delete the time slot in a generated date, with start time \"([^\"]*)\"$", repVisits::deleteTimeSlotWithGeneratedDate);

        And("^HS I verify that the time slot was removed from the generated date, with the start time \"([^\"]*)\"$", repVisits::verifyAbsenceOfTimeSlotWithGeneratedDate);

        Then("^HS I verify the Messaging Options Page in the repvists setup wizard \"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyRepvisitsSetupWizardMessagingOptions);

        Then("^HS I enter the messages in the Message Option page for Repvists Setup wizard \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessRepvisitsSetupWizardMessagingOptions);

        Then("^HS I verify the Primary Contact for Visits page and then click the \"([^\"]*)\" button$", repVisits::verifyPrimaryContactVisitsPage);

        Then("^HS I verify the Availability Settings page and then click the \"([^\"]*)\" button$", repVisits::verifyAvailabilitySettingsPage);

        Then("^HS I set the data to create the College Fair \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessCreateCollegeFair);

        Then("^HS I verify the Success Message for the College Fair \"([^\"]*)\"$", repVisits::verifySuccessMessageforCreateFair);

        Then("^HS I Click on the \"([^\"]*)\" button in the success page of the college fair$", repVisits::accessSuccessMessageforFair);

        And("^HS I verify the fairs are clickable \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyFairsAreClickable);

        And("^HS I cancel college fair created \"([^\"]*)\"$", repVisits::cancelCollegeFair);

        And("^HS I clean the college fairs created$", repVisits::cleanCollegeFair);

        And("^HS I clean the visits created$", repVisits::cleanVisits);

        Then("^HS I Click on the \"([^\"]*)\" button in the College Fair Details Page$",repVisits::accessCollegeFairDetailsPage);

        And("^HS I set the following data to On the College Fair page \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$", repVisits::accessCreateCollegeFair);

        Then("^HS I verify the data are present on the Edit College Fair page \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyEditCollegeFair);

        Then("^HS I set the data to the Edit a college Fair \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessEditCollegeFair);

        Then("^HS I verify the Success Message for the Edit College Fair \"([^\"]*)\"$", repVisits::verifySuccessMessageforEditFair);

        Then("^HS I Click the View Details button for the College Fair Event for \"([^\"]*)\"$", repVisits::accessCollegeFairOverviewPage);

        And("^HS I verify the Primary Contact Phone Number is required in the Visits and Fairs setup wizard$", repVisits::primaryContactDetailsforVisitsAndFairs);

        And("^HS I go to the Notifications & Primary Contact Tab in HS Setup Wizard page$", repVisits::navigateToVisitsAndFairsWizard);

        And("^HS I go to the Availability & Settings$", repVisits::navigateToAvailabilityAndSettings);

        And("^HS I go to the College Fair Settings page$", repVisits::navigateToCollegeFairSettings);

        And("^HS I verify the Primary Contact Phone Number is required in College Fair Settings$", repVisits::primaryContactDetailsforFairs);

        And("^HS I verify the Primary Contact Phone Number is required in Availability & Settings$", repVisits::primaryContactDetailsinAvailabilityandSettings);

        And("^HS I search for \"([^\"]*)\" in Contacts$", repVisits::searchforContact);

        And("^HS I search for partial data of \"([^\"]*)\" in Contacts$", repVisits::partialsearchforContact);

        And("^HS I search for invalid data of \"([^\"]*)\" in Contacts$", repVisits::verifyinvalidcontact);

        And("^HS I verify empty contacts page in Contacts$", repVisits::verifyEmptyContactPage);

        And("^HS I verify full contacts page in Contacts$", repVisits::verifyFullContactPage);

        And("^HS I verify contacts details  in Contacts$", repVisits::verifyContactDetails);

        And("^HS I verify the contacts page is full or empty$", repVisits::verifyFullorEmpty);

        And("^HS I verify the contacts list is sorted or not$", repVisits::sortingContacts);

        And("^HS I validating the pagination of 25 contacts in Contacts Page$", repVisits::validatingthePaginationof25Contacts);

        Then("^HS I create a Job Fair$", repVisits::createCollegeFair);

        Then("^HS I make sure the \"([^\"]*)\" button works properly for college fair attendee requests for \"([^\"]*)\"$", repVisits::confirmDeclineCollegeAttendanceRequest);

        Then("^HS date$", repVisits::enterCollegeFairData);

        Then("^HS I create a College Fair with the following data$", repVisits::createCollegeFair);

        Then("^HS I create a dynamic College Fair with the following data$", repVisits::createDynamicCollegeFair);

        Then("^HS I edit a dynamic College Fair with the following data$", repVisits::editCollegeFair);

        Then("^HS I verify edit a dynamic College Fair with the following data$", repVisits::verifyDataCollegeFair);

        //And("^HS I verify the items in the user dropdown for a Non-Naviance user$",repVisits::verifyUserDropdownforNonNaviance);
        And("^HS I verify the items in the user dropdown for a Non-Naviance user$", repVisits::verifyUserDropdownforNonNaviance);

        And("^HS I verify the items are navigate to the respective page in the user dropdown for a Non-Naviance user$", repVisits::verifyNavigationUserDropdownforNonNaviance);

        And("^HS I verify the user is \"([^\"]*)\" or not$", repVisits::verifyUserAdminorNot);

        And("^HS I verify the items are present in the help center dropdown for a Non-Naviance user$", repVisits::verifyHelpCentreforNonNaviance);

        And("^HS I verify the items in the user dropdown for a Naviance user$", repVisits::verifyUserDropdownforNaviance);

        And("^HS I verify the items are navigate to the respective page in the user dropdown for a Naviance user$", repVisits::verifyNavigationinUserDropdownforNaviance);

        Then("^HS I Add the following Attendee \"([^\"]*)\" from the results in the Add Attendee pop-up page$", repVisits::accessAddAttendeePopUp);

        Then("^HS I Click on the \"([^\"]*)\" button in the success page of the Add Attendees page$", repVisits::accessSuccessMessageforAddAttendees);

        Then("^HS I verify the Fair Details Page \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyCollegeFairDetailsPage);

        Then("^HS I set the following data in the confirm cancel pop-up \"([^\"]*)\",\"([^\"]*)\"$", repVisits::accessConfirmCancelPopup);

        Then("^HS I Click the \"([^\"]*)\" button for the attendee named \"([^\"]*)\"$", repVisits::accessListoffairAttendees);

        Then("^HS I verify the list of registered college fair attendees for the \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyListofRegisteredAttendee);

        Then("HS I remove the Time Slot created with \"([^\"]*)\",\"([^\"]*)\" in Regular Weekly Hours Tab$", repVisits::removeTimeSlotsInRegularWeeklyHoursTab);

        Then("HS I remove the Time Slot created with \"([^\"]*)\",\"([^\"]*)\" in Exceptions Tab$", repVisits::removeTimeSlotsInExceptionsTab);

        Then("^HS I add the email \"([^\"]*)\" in the primary contact in Notifications & Primary Contact page$", repVisits::addEmailInNotificationandPrimaryContactPage);

        Then("^HS I navigate to the Naviance Settings page through the setup Wizard$", repVisits::navigateToNavianceSettingsPage);

        Then("^HS I verify the toast \"([^\"]*)\" displayed when setup Wizard is incomplete$", repVisits::verifyToastInSetupWizardIncomplete);

        Then("^HS I select \"([^\"]*)\" to connect RepVisits with Naviance in the Wizard", repVisits::connectRVWithNaviance);

        Then("^HS I click on link Add School User Manually$",repVisits::addSchoolUserManually);
        Then("^HS I Enter Following Data to Add a School User Manually$",repVisits::addDataToAddAttendeeManually);
        Then("^HS I click on button Add attendees$",repVisits::clickAddAttendeetovisit);
        Then("^HS I click the Message Colleges button$",repVisits::clickMessageCollegesButton);
        Then("^HS I Enter Message as \"([^\"]*)\"$",repVisits:: massEmailMessageForAttendees);
        Then("^HS I click on Send Message$",repVisits::sendMessage);
        Then("^HS I verify confirmation message$",repVisits::verifySentEmailConfirmationMessage);
        Then("^HS I Click on close button$",repVisits::closeSendEmailMessageBox);

        Then("^HS I verify the UI of the Naviance Settings Page in setup wizard$", repVisits::verifyUIofNavianceSettingsPageinSetupWizard);

//        And("^HS I validating the pagination of 25 contacts in Contacts Page", repVisits::validatingthePaginationof25Contacts);

        And("^HS I Click button Add a College Fair to Add a fair$", repVisits::clickAddCollegeFairButton);

        And("^HS I verify Note on Add Edit Fair screen \"([^\"]*)\"$", repVisits::noteForSchools);

        And("^HS I click on close icon on Add Edit College Fair pop-up$", repVisits::closeAddEditFairScreen);

        And("^HS I click View Details against fair$", repVisits::viewFairDetails);

        And("^HS I click on Edit button to edit fair$", repVisits::editFair);

        Then("^HS I verify the Notification \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in the Request Notification Tab$", repVisits::verifyRequestNotificationTab);

        Then("^HS I select \"([^\"]*)\" option for the Notification using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::selectoption);

        And("^HS I verify the Decline Pop-up in the Notification Tab \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", repVisits::verifyDeclinePopup);

        Then("HS I select the \"([^\"]*)\" button by entering the message \"([^\"]*)\" for \"([^\"]*)\"$", repVisits::declineConfirmation);

        Then("^HS I select \"([^\"]*)\" option for the Notification using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for Fairs$", repVisits::selectoptionforFairs);

        Then("^HS I verify the Notification \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in the Request Notification Tab for Fairs$", repVisits::verifyRequestNotificationTabforFairs);

        Then("^HS I verify the Decline Pop-up in the Notification Tab \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for Fairs$", repVisits::verifyDeclinePopupforFairs);

        Then("^HS I verify the Export button is Enabled in Calendar page$", repVisits::verifyExportButtonInCalendar);

        Then("^HS I export the appointments for the following details \"([^\"]*)\",\"([^\"]*)\"$", repVisits::exportAppointmentsInCalendarPage);

        Then("^HS I verify the downloaded Appointments csv file \"([^\"]*)\" contains following details$", repVisits::verifyDownloadedCsvFileInCalendar);

        Then("^HS I delete the downloaded Appointments Cvs file \"([^\"]*)\"$", repVisits::deleteDownloadedFileInCalendar);

        Then("^HS I verify the Message \"([^\"]*)\" is displayed in the Request Notification Tab$",repVisits::verifynoNotificationMessage);

        Then("^HS I Click the \"([^\"]*)\" button for the attendee Name \"([^\"]*)\"$",repVisits::accessListoffairAttendees);

        Then("^HS I Click on the View Details button for the College Fair \"([^\"]*)\"$",repVisits::accessViewDetailsPageforFair);

        Then("^HS I select Edit button to cancel the college Fair \"([^\"]*)\"$",repVisits::cancelRegisteredEditedCollegeFair);

        And ("^HS I navigate to the college visits page$",repVisits::navigateToVisitPage);

        Then("^HS I verify the default calendar page present after the Wizard completion$", repVisits::verifydefaultRepVisitPage);

        And("^HS I verify the success Message \"([^\"]*)\" in Availability Settings page", repVisits::verifySuccessMessage);

        And("^HS I Navigate to Notifications & Tasks tab of the Repvisits Page$", repVisits::clickLinkNotificationsAndTasks);
        And("^HS I select custom time manually$", repVisits::clickCustomeTimelink);
        And("^HS I select Visit StartTime \"([^\"]*)\" and End Time \"([^\"]*)\"$", repVisits::visitStartandEndTime);
        And("^HS I verify Visits on Calendar created \"([^\"]*)\" from now$", repVisits::verifyVisitDaysFromNow);
        And("^HS I click on Agenda on Calendar$", repVisits::clickAgenda);
        And("^HS I click on Day on Calendar$", repVisits::clickDayCalendar);

        And("^HS I click the Visit Feedback sub tab$", repVisits::clickLinkVisitFeedback);

        And("^HS I should be able to see the text - #HE User# has asked for feedback on their recent visit.- in every entry present in Visit Feedback Pending tab$", repVisits::verifyTextAskingHSUserForFeedbackOnHEVisit);

        And("^HS I remove the time slot with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::removeSlotTimeByDayAndTime);

        Then("^HS I unpublish the College Fair$", repVisits::unpublishCollegeFair);

        Then("^HS I create a new college fair \"([^\"]*)\" days ahead of today and the following details:$", repVisits::createFairWithGeneratedDate);

        Then("^HS I verify the data for the fair present on the College Fair Overview page \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCollegeFairOverview);

        Then("^HS I navigate to the calendar page to verify AddVisit Button is \"([^\"]*)\"$",repVisits::verifyAddvisitButtonInCalendarPage);

        Then("^HS I add the appointment based on pre-determined time slots using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::addVisitsManuallyFromCalendarTab);

        Then("^HS I manually add the contact to an appointment using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::addContactManually);

        Then("^HS verify the created Appointment is present in the calendar \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyAppointmentsIncalendar);

        Then("^HS I schedule a new visit for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::scheduleNewVisitusingCustomTime);

        Then("^HS I verify the confirmation message \"([^\"]*)\" for the created visit$",repVisits::verifyConfirmationMessage);

        Then("^HS I verify the calendar page is displayed$",repVisits::verifyCalendarPageIsDisplayed);

        Then("^HS I verify the close drawer is displaying in the visit Schedule popup$",repVisits::verifyCloseDrawerInVisitSchedulePopup);

        Then("^HS I verify the link \"([^\"]*)\" is displaying in the visit Schedule popup$",repVisits::verifyLinkInVisitSchedulePopup);

        Then("^HS I verify the link \"([^\"]*)\" is displayed in the visit Schedule popup$",repVisits::verifyGobacktoListLinkInVisitSchedulePopup);

        Then("^HS I verify the text \"([^\"]*)\" is present in the Attendee text box$",repVisits::verifyStartTypingTextInVisitSchedulePopup);

        Then("^HS I verify the text box is displaying in the visit Schedule popup for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyTextboxInVisitSchedulePopup);

        Then("^HS I verify the \"([^\"]*)\" only required field in the visit Schedule popup$",repVisits::verifyRequiredFieldInVisitSchedulePopup);

        Then("^HS I verify the \"([^\"]*)\" text is present under \"([^\"]*)\" in the visit Schedule popup$",repVisits::verifyTextInVisitSchedulepopup);

        Then("^HS I verify the button \"([^\"]*)\" is displaying in the visit Schedule popup$",repVisits::verifyAddvisitButtoninVisitSchedulepopup);

        And("^HS I schedule a new visit with day \"([^\"]*)\" time \"([^\"]*)\" representative name \"([^\"]*)\" representative last name \"([^\"]*)\" representative institution \"([^\"]*)\" location \"([^\"]*)\" NumberOfStudents \"([^\"]*)\" registrationWillClose \"([^\"]*)\"$", repVisits::scheduleNewVisit);

        Then("^HS I verify that Block this time slot button is displayed for time slot with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::verifyBlockThisTimeSlotButtonIsDisplayed);

        Then("^HS I verify that Block this time slot ToolTip is displayed for time slot with day \"([^\"]*)\" and time \"([^\"]*)\"",repVisits::verifyBlockThisTimeSlotToolTipIsDisplayed);

        And("^HS I block the time slot with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::blockTimeSlot);

        Then("^HS I verify that Unblock this time slot button is displayed for time slot with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::verifyUnblockThisTimeSlotButtonIsDisplayed);

        Then("^HS I verify that Blocked label is displayed in the slot time with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::verifyBlockedLabelIsDisplayedInTimeSlot);

        Then("^HS I verify that a new visit with day \"([^\"]*)\" and time \"([^\"]*)\" cannot be set$",repVisits::verifyNewVisitCannotBeSet);

        And("^HS I unblock the time slot with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::unblockTimeSlot);

        Then("^HS I verify that the blocked label is not displayed for the time slot with day \"([^\"]*)\" and time \"([^\"]*)\"$",repVisits::verifyBlockedLabelIsNotDisplayedInTimeSlot);

        Then("^HS I verify that the number of visits for the time slot with day \"([^\"]*)\" and time \"([^\"]*)\" is \"([^\"]*)\"$",repVisits::verifyNumberOfVisits);

        And("^HS I cancel a visit with time \"([^\"]*)\" college \"([^\"]*)\" and note \"([^\"]*)\"$",repVisits::cancelVisit);

        Then("HS I remove the Time Slot recently created with \"([^\"]*)\",\"([^\"]*)\" in Regular Weekly Hours Tab$", repVisits::removeTimeSlotsRefactoredForHS);

        Then("^HS I add new Time Slot as precondition with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::addNewTimeSlotForHS);

        Then("^HS I confirm Request with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::confirmRequest);

        Then("^HS I verify the visit in Naviance with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyVisitInNaviance);

        Then("^HS I verify the cancel in Naviance with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCancelInNaviance);

        Then("^HS I verify the reschedule in Naviance with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyRescheduleInNaviance);

        Then("^HS I reschedule a visit for HS with the following details \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::selectRescheduleForHS);

        Then("^HS I verify reschedule pop-up for HS with the following data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyReschedulePopupForHS);

        Then("^HS I reschedule the visit in HS for the following data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::reschedulevisitForHS);

        Then("^HS I setup Naviance Sync Settings page with \"([^\"]*)\" option$",repVisits::navianceSyncSettingsSetup);

        Then("^HS I setup Availability Settings page with \"([^\"]*)\" option$",repVisits::availabilitySettingsSetup);

        Then("^HS I add the following attendees to the College Fair$", repVisits::addAttendees);

        Then("^HS I click on Disconnect RepVisits from Naviance button$",repVisits::disconnectFromNavianceButton);

        Then("^HS I verify the Cancel on the disconnect confirmation popup$",repVisits::verifyCancelDisconnectFromNavianceButton);

        Then("^HS I verify the Yes on the disconnect confirmation popup with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifyYesDisconnectFromNavianceButton);

        Then("^HS I verify configuration and staff notifications for \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::verifyStaffNotifications);

        Then("^HS I verify that the user receives an activity notification with \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::verifyCollegeFairNotificationWasReceived);

        Then("^HS I verify non community members to be notified with \"([^\"]*)\" and \"([^\"]*)\" email$",repVisits::verifyNotificationsToNonMembersSection);

        And("^HS I go to the Naviance settings$",repVisits::naviagateToAvailbilityandSettings);

        And("^HS I verify the success message after save the changes$",repVisits::verifyNavianceSuccessMessage);

        And("^HS I verify the success Message \"([^\"]*)\" in Fair Settings page",repVisits::verifySuccessMessage);

        Then("^HS I verify there is a timeslot on \"([^\"]*)\" at \"([^\"]*)\" in the Exceptions tab$", repVisits::verifyTimeslotInException);

        Then("^HS I set a date using \"([^\"]*)\" and \"([^\"]*)\" in Regular Weekly Hours Tab$",repVisits::setSpecificStartAndEndDatesinRegularWeeklyHoursTab);

        Then("^HS I go to the Exception tab to verify the visits using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyVisitsinException);

        Then("^HS I select the time slot in Regular Weekly Hours to verify the pills is highlighted using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyTimeslot);

        Then("^HS I edit the slots in Regular Weekly Hours to \"([^\"]*)\"$",repVisits::editSlot);

        Then("^HS I verify the pills \"([^\"]*)\",\"([^\"]*)\" is displayed in the schedule new visit popup$",repVisits::verifyPillsInManuallyAddedAppointmentsPage);

        Then("^HS I verify the pills \"([^\"]*)\",\"([^\"]*)\" is not displayed in the schedule new visit popup$",repVisits::verifyPillsNotdisplayedScheduleNewVisit);

        Then("^HS I clear the time slot for the particular day \"([^\"]*)\" in Regular Weekly Hours Tab$",repVisits::removeTimeslotforEntireDayInRegularWeeklyHours);

        Then("^HS I verify the Partially scheduled Appointments With Message \"([^\"]*)\" in Exception subtab using \"([^\"]*)\"$",repVisits::verifyPartiallyScheduledDayInExceptionsSubtab);

        Then("^HS I verify the Trash icon is present in the Exception Tab to remove the slot$",repVisits::verifyTrashIconInExcepionTab);

        Then("^HS I verify the Availability slot color after select the slot \"([^\"]*)\",\"([^\"]*)\" in the Exception Tab$",repVisits::verifyAvailabilityslotColorInException);

        Then("^HS I verify the diagonal HashLines present in the Blocked date \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyHashLinesInBlockedDate);

        Then("^HS I verify the \"([^\"]*)\" Maximum colleges are present in the Availability slot for the following details \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyMaximumCollegesInException);

        Then("^HS I verify the Unscheduled Appointments using \"([^\"]*)\" in Exception subtab using \"([^\"]*)\"$",repVisits::verifyUnscheduledAppointmentsInExceptionsSubtab);

        Then("^HS I set the Blocked date and select the reason as \"([^\"]*)\" in the Holiday tab using \"([^\"]*)\"$",repVisits::setBlockedDate);

        Then("^HS I verify the Blocked days with reason \"([^\"]*)\" in Exception subtab using \"([^\"]*)\"$",repVisits::verifyBlockedDaysInExceptionsSubtab);

        Then("^HS I click the Remove option for the \"([^\"]*)\" and \"([^\"]*)\" in blocked days$",repVisits::removeManuallyAddedBlockedDate);

        Then("^HS I verify the color of the Appointments using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in Exception Tab$",repVisits::verifyPillsColorInException);

        Then("^HS I verify the color of selected date picker outline using \"([^\"]*)\",\"([^\"]*)\" in Exception Tab$",repVisits::verifyOutlineColorInException);

        Then("^HS I verify the diagonal HashLines present in the Max Appointments Met date \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyHashLinesInMaxAppointmentsMetDate);

        Then("^HS I verify the diagonal HashLines present in the Fully booked date \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyHashLinesInFullyBookedDate);

        Then("^HS I verify the light blue background color present in the Partially Scheduled availability using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in Exception Tab$",repVisits::verifyColorInPartiallyScheduledAvailability);

        Then("^HS I Remove the created blocked days$",repVisits::removeCreatedBlockedDaysInBlockedDaysTab);

        And("^Hs I open the date picker on Agenda View$", repVisits::clickAgendaDatePicker);

        And("^HS I click on Visit with \"([^\"]*)\" from \"([^\"]*)\" to \"([^\"]*)\" on Day Calendar$", repVisits::clickVisitName);

        And("^HS I verify Internal Notes on Visit Details screen \"([^\"]*)\"$", repVisits::verifyVisitInternalNotes);

        And("^HS I Cancel visit to create again add Notes to Cancel \"([^\"]*)\"$", repVisits::visitCancelled);

        And("^HS I verify Representative details on Visit Details screen \"([^\"]*)\"$", repVisits::verifyRepDetails);

        Then("^HS I go the Exception tab in RepVisits$",repVisits::navigateToException);

        Then("^HS I verify the blocked day in Exception tab using \"([^\"]*)\"$",repVisits::verifyBlockedDayInException);

        Then("^HS I verify the calendar page$",repVisits::verifyCalendarPageForaddVisit);

        Then("^HS I verify the visit schedule popup$",repVisits::verifyVisitSchedulepopup);

        Then("^HS I set Blocked date as \"([^\"]*)\" and select the reason as \"([^\"]*)\" in the Holiday tab$",repVisits::setSpecificBlockedDate);

        Then("^HS I verify the city and state \"([^\"]*)\" are present in the underneath of Institiution Name \"([^\"]*)\" in the Request Notification Tab$",repVisits::verifyCityAndStateInRequestNotificationsubTab);

        Then("^HS I verify the city and state \"([^\"]*)\" are present in the underneath of Institiution Name \"([^\"]*)\" in the Activity Tab$",repVisits::verifyCityAndStateInActivitysubTab);

        Then("^HS I verify the city and state \"([^\"]*)\" are present in the underneath of Institiution Name \"([^\"]*)\" in the Request Notification Tab for Fairs$",repVisits::verifyCityAndStateInRequestNotificationsubTabforFairs);

        Then("^HS I verify the city and state \"([^\"]*)\" are present in the underneath of Institiution Name \"([^\"]*)\" in the Activity Tab for Fairs$",repVisits::verifyCityAndStateInActivitysubTabforFairs);

//        Then("^HS I Click on the View Details button for the College Fair Event \"([^\"]*)\"$",repVisits::accessViewDetailsPageforFair);

        Then("^HS I verify I can make it through the RepVisits wizard as a non-Naviance HS$", repVisits::verifyRepvisitsSetupWizardNonNaviance);

        Then("^HS I set the value for Reschedule the visit$",repVisits::rescheduleVisitStartTime);

        Then("^HS I verify the message \"([^\"]*)\" is displayed in the ACTIVITY subtab$",repVisits::verifyNotificationMessage);

        Then("^HS I verify the Paginate the ACTIVITY subtab via 25 entries with a \"([^\"]*)\" action to display the next 25 entries$",repVisits::verify25Entries);

        Then("^HS I reschedule the visit for the following data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::reschedulevisit);

        Then("^HS I verify reschedule pop-up for the following data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyReschedulepopup);

        Then("^HS I reschedule a visit for the following details \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::selectReschedule);

        Then("^HS I select Activity in RepVisits to verify \"([^\"]*)\" notification for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyActivity);

        Then("^HS I select Activity in RepVisits to verify \"([^\"]*)\" notification for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" after Rescheduled the visit$",repVisits::verifyActivityforReschedule);

        Then("^HS I select Activity tab in RepVisits to verify rescheduled notification for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyReScheduledNotification);

        Then("^HS I verify the calendar page in RepVisits using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCalendarPage);

        Then("^HS I verify \"([^\"]*)\" notification for \"([^\"]*)\" using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCancelOrReSchedule);

        Then("^HS I verify the Dashboard Upcoming events for \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::verifyUpcommingEvents);

        Then("^HS I cancel new event created for \"([^\"]*)\"$", repVisits::cancelNewEvent);

        Then("^HS I verify the Canceled events for \"([^\"]*)\"$", repVisits::verifyCanceledEvents);

        Then("^HS I select cancel the Visit$",repVisits::selectCancel);

        Then("^HS I verify the calendar page in RepVisits using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for cancel the Visit$",repVisits::verifyCalendarPageForCancelVisit);

        Then("^HS I select Activity in RepVisits to verify \"([^\"]*)\" notification for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for Fairs$",repVisits::verifyActivityforFairs);

        Then("^HS I verify the edit fair popup \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyEditFairPopup);

        Then("^HS I reschedule the fair using \"([^\"]*)\"$",repVisits::rescheduleFairs);

        Then("^HS I verify the \"([^\"]*)\" in reschedule page$",repVisits::verifyTextInReschedulePopup);

        Then("^HS I verify the university \"([^\"]*)\" in reschedule page$",repVisits::verifyUniversityInReschedulePopup);

        Then("^HS I verify the date \"([^\"]*)\" in reschedule page$",repVisits::verifyDateInReschedulePopup);

        Then("^HS I verify the time \"([^\"]*)\" in reschedule page$",repVisits::verifyTimeInReschedulePopup);

        Then("^HS I verify the calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for cancel the Visit$",repVisits::verifyCalendarPageForCanceltheVisit);

        Then("^HS I verify the \"([^\"]*)\" notification for \"([^\"]*)\" using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCancelVisitPopup);

        Then("^HS I select Activity in RepVisits to verify \"([^\"]*)\" notification for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" after cancelled the visit$",repVisits::verifyActivityForCanceltheVisit);

        And("^HS I Save the Primary Contacts for visits for my school$",repVisits::savePrimaryContactForVisit);

        And("^^HS I Set the Primary Contact for Visits for my  school with phone\"([^\"]*)\" and Email \"([^\"]*)\"$",repVisits:: primaryContactForVisit );

        And("^HS I click on Edit button to navigate to Edit College Fair$",repVisits:: editCollegeFairs);

        And("^HS I Click on button Cancel This College Fair$",repVisits::cancelCollegeFairClick);

        And("^^HS I Enter Fair Cancellation Message for colleges \"([^\"]*)\"$",repVisits::cancelMessageForColleges);

        And("^HS I click on button Cancel Fair and Notify Colleges$",repVisits::cancelFairAndNotifyColleges);

        And("^HS I click on button Add attendees for fair$",repVisits::fairAttendeeclick);

        Then("^HS I cancel the \"([^\"]*)\" College Fair$", repVisits::cancelCollegeFair);

        Then("^HS I verify the Notification is not displayed after \"([^\"]*)\" the visit in the Request Notification Tab for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyNotificationInRequestTab);

        Then("^HS I set the date using \"([^\"]*)\" and \"([^\"]*)\" in calendar \"([^\"]*)\" view$",repVisits::setDateInCalendarAgenda);

        Then("^HS I verify the disabled date \"([^\"]*)\" is not clickable in calendar Agenda view$",repVisits::verifyDisabledDateIsNotClickableInEndDate);

        And("^HS I click on the Save Settings button in College Fairs tab$",repVisits::clickSaveSettingsButtonInCollegeFairsTab);

        And("^HS I verify that a banner appears letting me know that College Fair settings were saved$",repVisits::verifySettingsSavedBannerIsDislayedInCollegeFairsTab);

        Then("^HS I Click the \"([^\"]*)\" button for the attendee \"([^\"]*)\"$",repVisits::accessListoffairAttendees);

        Then("^HS I verify the \"([^\"]*)\",\"([^\"]*)\" buttons are present in the Fairs tab$",repVisits::verifyButtonsInFairs);

        Then("^HS I select \"([^\"]*)\" option for \"([^\"]*)\"$",repVisits::selectOptionInFairs);

        Then("^HS I verify the DECLINE pop-up for \"([^\"]*)\",\"([^\"]*)\" in Fairs$",repVisits::verifyDeclinePopupInFairs);

        Then("^HS I verify the user can access \"([^\"]*)\" view$",repVisits::accessAgendaView);

        Then("^HS I verify the user cannot access Agenda view$",repVisits::verifyUserCannotAccessAgendaView);

        Then("^HS I verify \"([^\"]*)\" stub menu is not present in Account settings page for \"([^\"]*)\"$",repVisits::verifyYourNotificationTab);

        Then("^HS I verify the following details are displaying in Naviance Settings page$",repVisits::verifyNavianceSettingsPage);

        Then("^HS I navigate to naviance settings page$",repVisits::navigateToNavianceSettingsInAvailabilitySettingsPage);

        And("^HS I navigate to \"([^\"]*)\" wizard in repvisits page$", repVisits::navigateToNavianceSync);

        Then("^HS I complete the set up wizard page by selecting \"([^\"]*)\" option on the 'One Last Step' page$",repVisits::goToCalendarInWizardLastStepPage);

        Then("^HS I complete the set up wizard that not yet completed by selecting \"([^\"]*)\" option on the 'One Last Step' page$",repVisits::completeSetupWizard);

        Then("^HS I complete the set up wizard broken$",repVisits::completeSetupWizardBroken);

        Then("^HS I complete the set up wizard from any location$",repVisits::completeSetupWizardFromAnylocation);

        And("^HS I select Start date \"([^\"]*)\" and End date \"([^\"]*)\" in Agenda view$", repVisits::setStartDateAndEndDateInAgendaView);

        And("^HS I verify that \"([^\"]*)\" visits are displayed in Agenda view$", repVisits::verifyNumberOfVisitsDisplayedInAgendaView);

        And("^HS I verify that it should not be possible to select an End date \"([^\"]*)\" which is less than the Start date \"([^\"]*)\" in Agenda view$", repVisits::verifyUserCannotSelectEndDateWhichIsLessThanStartDateInAgendaView);

        Then("^HS I go to the Naviance Settings to select the option \"([^\"]*)\"$",repVisits::selectOptionforManuallyorAutomatically);

        Then("^HS I verify the Notification is \"([^\"]*)\" in the Naviance Sync Tab for the following details \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyNavianceSyncTab);

        Then("^HS I verify the Rescheduled Notification is \"([^\"]*)\" in the Naviance Sync Tab for an appointment that has not been pushed to Naviance using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyRescheduledNotificationInNavianceSyncTab);

        Then("^HS I select calendar in RepVisits$",repVisits::selectCalendar);

        Then("^HS I verify the calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCalendarPage);

        Then("^HS I remove the appointment from the calendar$",repVisits::removeAppointmentfromCalendar);

        Then("^HS I verify HE user's name be an active hyperlink to the HS user's Community profile in visit feedback subtab$", repVisits::verifyHEUsersNameLink);

        Then("^HS I verify visit feedback tab and its subtabs$",repVisits::verifyVisitFeedbackForHSUser);

        Then("^HS I verify the visit feedback tab showing after Naviance Sync tab$", repVisits::verifyVisitFeedbackTagSequenceForNavianceHS);

        Then("^HS I verify the visit feedback tab showing after Activity tab$", repVisits::verifyVisitFeedbackTagSequenceForNonHS);

        And("^HS I verify the Pending subtab under Visit Feedback$", repVisits::verifyPendingSubtab);

        And("^HS I verify the lock icon for Anonymously feedback$", repVisits::submitAnonymouslyFeedback);

        And("^HS I verify submitted subtab under Visit Feedback$", repVisits::verifySubmittedSubtab);

        Then("^HS I verify the Attendee details \"([^\"]*)\" in Edit fairs page$",repVisits::verifyAttendeeDetailsInEditFairs);

        Then("^HS I cancel registered college fair \"([^\"]*)\"$",repVisits::cancelRegisteredCollegeFair);

        And("^HS I create a visit \"([^\"]*)\" days ahead from now with the following details$", repVisits::createVisit);

        And("^HS I open the visit with generated time in the Calendar$", repVisits::openFairDetailsWithGeneratedDate);

        Then("^HS I verify the input validations for Student Registration Deadline with the data:$", repVisits::verifyInputValidationsForStuRegDeadline);

        And("^HS I cancel the open visit$", repVisits::cancelOpenVisit);

        Then("^HS I Click on the View Details button for the College Fair Event \"([^\"]*)\"$",repVisits::viewDetailsPageforEditedFair);

        Then("^HS I reschedule the visit for the following data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in calendar$",repVisits::rescheduleVisitInCalendar);

        Then("^HS I verify and select an appointment in calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyAndSelectAppointmentInCalendarPage);

        Then("^HS I complete the setupWizard$",repVisits::completeTheSetupWizard);

        Then("^HS I create a new visit to verify the details in naviance with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::createVisitForNaviance);

        And("^HS I cancel the college fairs created$", repVisits::cancelCollegeFairs);

        And("^HS I clean the visits for particular Month \"([^\"]*)\"$", repVisits::cleanVisitsForParticularMonth);

        Then("^HS I verify the announcement title \"([^\"]*)\" in the announcement details$",repVisits::verifyAnnouncementTitleIsDisplaying);

        Then("^HS I verify the 'Read More' button is displaying for the content with more than 140 character limit$",repVisits::verifyReadMoreButtonIsDisplaying);

        Then("^HS I verify the announcement details after the success toast message using \"([^\"]*)\"$",repVisits::verifyAnnouncementDetailsAfterSuccessMessage);

        Then("^HS I verify the dismiss button is displaying in the announcement details$",repVisits::verifyDismissButtonInAnnouncementDetails);

        Then("^HS I click the dismiss button$",repVisits::clickDismissButton);

        Then("^HS I verify the announcement is not displaying after clicking dismiss button$",repVisits::verifyAnnouncementIsNotDisplaying);

        Then("^HS I verify the content begins where title would begin in notification bar \"([^\"]*)\"$",repVisits::verifyProductAnnouncementContent);

        Then("^HS I verify the 'Read More' button is not displaying for the content with less than 140 character limit$",repVisits::verifyReadMoreButtonIsNotDisplaying);

        Then("^HS I verify the time slot is added with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" in Regular Weekly Hours Tab$",repVisits::verifyTimeSlotIsDisplayedInRegularWeeklyHoursTab);

        Then("^HS I go to the Naviance Settings and submit the following details$",repVisits::submitTheValuesInNavianceSettings);

        Then("^HS I verify the naviance college visit page using the following details \"([^\"]*)\"$",repVisits::verifyNavianceCollegeVisitPage);

        Then("^HS I verify the naviance college visit page using the following details after Reschedule the Visits \"([^\"]*)\"$",repVisits::verifyNavianceCollegeVisitPageAfterReschedule);

        Then("^HS I select \"([^\"]*)\" In Naviance college visit Page After Reschedule the visit using \"([^\"]*)\",\"([^\"]*)\"$",repVisits::selectViewOptionInNavianceCollegeVisitPageAfterReschedule);

        Then("^HS I select \"([^\"]*)\" In Naviance college visit Page using \"([^\"]*)\",\"([^\"]*)\"$",repVisits::selectViewOptionInNavianceCollegeVisitPage);

        Then("^HS I verify the Appointment in calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyAppointmentCalendarPage);

        Then("^HS I verify the Default the HS user to see the Request subtab when they arrive on the Notifications page$",repVisits::verifySubTabInNotifications);

        Then("^HS I complete the setup wizard$",repVisits::loadSetupWizardPage);
        And("^HS  I Store the college Fair Name created$", repVisits::storeCollegeFairName);
        And("^HS I click on Unpublish option on repvisits sidebar$", repVisits::chooseUnpublish);
        Then("^HS I verify Fair unpublished message on the repvisits sidebar$", repVisits::assertUnpublish);

        And("^HS I verify that the connecting naviance and repvisits page contains the text: \"([^\"]*)\"$", repVisits::verifyTextInConnectingNavianceAndRepvisitsPage);
        And("^HS I verify that the naviance sync settings page contains the text: \"([^\"]*)\"$", repVisits::verifyTextInNavianceSyncSettingsPage);

        Then("^HS I manually add the attendee for created college fair without last name \"([^\"]*)\",\"([^\"]*)\"$",repVisits::manuallyAddAttendee);

        Then("^HS I verify \"([^\"]*)\" text is not displaying in the attendee page$",repVisits::verifyUndefinedTextIsNotDisplayingInAttendeePage);
      
        Then("^HS I go to the calendar page and verify the visit appointment is displaying with gray color \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyPendingVisitInCalendar);

        Then("^HS I go to the calendar page and verify the visit appointment is displaying with blue color \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyScheduledVisitInCalendar);

        Then("^HS I verify the date \"([^\"]*)\" is not enabled in college fair page$",repVisits::verifyDateIsDisabledInCollegeFairPage);

        Then("^HS I verify the date \"([^\"]*)\" is not enabled in edit college fair page$",repVisits::verifyDateIsDisabledInEditCollegeFairPage);

        Then("^HS I create a College Fair using the following data \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$",repVisits::hsCreateCollegeFair);

        Then("^HS I select Start date \"([^\"]*)\" and End date \"([^\"]*)\" in calendar Agenda view$",repVisits::setStartDateAndEndDateInCalendarAgendaView);

    }
}