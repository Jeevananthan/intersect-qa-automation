package stepDefinitions.HE;

import cucumber.api.java.cs.A;
import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.RepVisitsPageImpl;

public class RepVisitsPageStepDefs implements En {

    RepVisitsPageImpl repVisits = new RepVisitsPageImpl();

    public RepVisitsPageStepDefs() {

        Then("^HE I verify the following tabs exist on the RepVisits page$", repVisits::checkRepVisitsSubTabs);

        Then("^HE I verify the Search and Schedule tab of the RepVisits page$", repVisits::verifySearchAndSchedulePage);

        Then("^HE I search for High Schools with the following location data in RepVisits$", repVisits::verifySearchResultOfSearchAndSchedule);

        And("^HE I search for \"([^\"]*)\" in RepVisits$", repVisits::searchforHighSchool);

        Then("^HE I select \"([^\"]*)\" in \"([^\"]*)\" from the RepVisits intermediate search results$", repVisits::selectHighSchoolFromIntermediateSearchResults);

        Then("^HE I view the map plugin on RepVisits Search & Schedule subtab$", repVisits::viewMapPlugin);

        And("^HE I select \"([^\"]*)\" from the RepVisists map plugin$", repVisits::selectSchoolFromMap);

        And("^HE I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("HE I verify the high school information popup contains the following data", repVisits::checkHighSchoolPopUp);

        Then ("^HE I verify the upgrade messaging on the Travel Plan page in RepVisits$",repVisits::verifyUpgradeMessageInTravelPlanInRepVisits);

        Then("^HE I verify the Check RepVisits Availability button$", repVisits::verifyCheckRepVisitsAvailabilityButton);

        And("^HE I select \"([^\"]*)\" from the RepVisits search result$", repVisits::selectHighSchoolFromResults);

        Then("^HE I should be able to open the registration popup for the fair \"([^\"]*)\" in Search and Schedule$", repVisits::clickRegistrationButton);

        Then("^HE I verify that the fair request confirmation popup contains all the required fields, including high school name \"([^\"]*)\"$", repVisits::verifyConfirmationPopup);

        And("^HE I close the fair request popup$", repVisits::closeFairRequestPopup);

        And("^HE I open the fairs tab$", repVisits::clickFairsTab);

        And("^HE I open the Fairs tab in Check RepVisits Availability sidebar$", repVisits::openFairsInChckRepVisitsAv);

        Then("^HE I should be able to open the registration popup for the fair \"([^\"]*)\" in Check RepVisits Availability sidebar$", repVisits::clickRegistrationButton);

        Then("^HE I register to the \"([^\"]*)\" fair from Search and Schedule screen$", repVisits::registerFair);

        Then("^HE I verify that the message for registered fairs with auto approval is displayed$", repVisits::verifySuccessMessageWithAutoApprovals);

        Then("^HE I verify that the registered fair \"([^\"]*)\" is displayed in the calendar for the date \"([^\"]*)\" and time \"([^\"]*)\"$", repVisits::verifyFairInCalendar);

        Then("^HE I verify that the registered fair is displayed for \"([^\"]*)\" in the Search and Schedule quickview in the date \"([^\"]*)\" and time \"([^\"]*)\"$", repVisits::verifyFairInQuickView);

        Then("^HE I verify that the message for registered fairs without auto approval is displayed$", repVisits::verifySuccessMessageWithoutAutoApprovals);

        When("^HE I open the institution of ID \"([^\"]*)\"$", repVisits::openInstitutionByURLPartID);

        And("^HE I click the upgrade button$",repVisits::clickUpgradeButton);

        Then("^HE I verify the Upgrade popup and the details displayed in the popup$",repVisits::verifyUpgradePopupAndInformations);

        Then("^HE I verify the upgrade messaging on the Recommendations page in RepVisits$",repVisits::verifyUpgradeMessageInRecommendationspage);

        Then("^HE I verify the upgrade messaging on the Contacts page in RepVisits$",repVisits::verifyUpgradeMessageInContactspage);

        And("^HE I verify Repvisits Special Instructions for School are \"([^\"]*)\"$", repVisits::verifyHSSpecialInstructions);

        And("^HE I verify the calendar view in repvisits$",repVisits::verifyCalendarViewOnRepVisits);

        Then("^HE I navigate to the \"([^\"]*)\" page in RepVisits$", repVisits::navigateToRepVisitsSection);

        And("^HE I verify the non-administrator messaging on the Visits Feedback page$", repVisits::verifyVisitsFeedbackNonAdminMessaging);

        And("^HE I verify the freemium messaging on the Visits Feedback page$", repVisits::verifyVisitsFeedbackFreemiumMessaging);

        And("^HE I navigate to the \"([^\"]*)\" Page$",repVisits::navaigateToAccountSettings);

        And("^HE I verify the left-sub menu \"([^\"]*)\" are present in the Account Settings page$",repVisits::verifyAccountsettings);

        And("^HE I verify the non-password fields \"([^\"]*)\" are pre-populated with current data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyPasswordFields);

        And("^HE I validate the password field \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::validatePassword);

        And("^HE I verify the left sub menu are present in the Account Settings page$",repVisits::verifyDetailsInaccountSettings);

        And("^HE I reset the password for \"([^\"]*)\",\"([^\"]*)\"$",repVisits::resetPassword);

        And("^HE I verify the left sub menu \"([^\"]*)\" is present in the Account Settings page for Non-Admin$",repVisits::verifyDetailsInaccountSettingsforNonAdmin);

        And("^HE I verify the success message \"([^\"]*)\" in Account settings page$",repVisits::verifySuccessMessageinAccountSettingsPage);

        Then("^HE I verify the formatting of the Visit Feedback page$", repVisits::verifyVisitFeedbackPage);

        Then("^HE I verify the RepVisits Overview page and Search and Schedule hyperlink when no events are scheduled for the next 7 days", repVisits::verifyDefaultMessageOverviewPage);

        Then("^HE I search for \"([^\"]*)\" in RepVisits page",repVisits::searchSchool);

        Then("^HE I select Visits to schedule the appointment for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::visitsSchedule);

        Then("^HE I verify the schedule pop_up for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifySchedulePopup);

        Then("^HE I verify the pills is not displayed in the search and schedule page using \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifyPills);

        Then("^HE I verify the pills is displayed in the search and schedule page using \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifyPillsIsPresent);

        Then("^HE I select Fairs for \"([^\"]*)\" and schoolName \"([^\"]*)\"$",repVisits::visitFairsToRegister);

        Then("^HE I verify the Notifications & Tasks using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyNotification);

        Then("^HE I verify the Notifications & Tasks using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for fairs$",repVisits::verifyNotificationforFairs);

        Then("^HE I verify the Paginate the REQUESTS subtab via 25 entries with a \"([^\"]*)\" action to display the next 25 entries$",repVisits::verify25Entries);

        Then("^HE I click the View full details option in the Request subTab using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::selectViewDetails);

        Then("^HE I click the View full details option in the Request subTab using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for fairs$",repVisits::selectViewDetailsforFairs);

        Then("^HE I verify the message \"([^\"]*)\" is displayed in the Request subTab$",repVisits::verifynoNotificationMessage);

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

        //Then("^HE I request HS jobfair appointment to be scheduled \"([^\"]*)\" for \"([^\"]*)\"$", repVisits::checkHighSchoolJobFairAvailability);
        Then("^HE I request an appointment with \"([^\"]*)\" for College Fair \"([^\"]*)\"$", repVisits::selectFairForHE);
        //selectFairForHE

        And("^HE I verify the items in the user dropdown for a HE user$",repVisits::verifyUserDropdownforHE);

        And("^HE I verify the items are navigate to the respective page in the user dropdown for a HE user$",repVisits::verifyNavigationUserDropdownforHE);

        And("^HE I verify the user is \"([^\"]*)\" or not$",repVisits::verifyUserAdminorNot);

        And("^HE I verify the items are present in the help center dropdown for a HE user$",repVisits::verifyHelpCentreforHE);

        And("^HE I verify the Search heading over the search bar in Search and Schedule Tab$",repVisits::verifytSearchHeadingInSearchAndScheduleTab);

        And("^HE I verify the Schedule heading over the availability block$",repVisits::verifyScheduleHeadingOverAvailabilityBlockInSearchAndSchedule);

        Then("^HE I verify the calender icon is present next to date$",repVisits::verifyCalendarIconNextToDateInSearchAndSchedule);

        Then("^HE I verify the date and calendar icon present over the availability table$",repVisits::verifyDateAndCalenderIconOverAvailabilityTable);

        Then("^HE I verify the next and previous buttons at the top, far right of the availability table$",repVisits::verifyNextPrevButtonInSearchAndSchedule);

        Then("^HE I verify the view type button to the left of the next/previous buttons$",repVisits::verifyViewTypeButtonInsearchAndSchedule);

        Then("^HE I verify the color of the active view type button$",repVisits::verifyColorofViewTypeButton);

        Then("^HE I verify the Map in SearchAndSchedule Page$",repVisits::verifyMapInSearchAndScheduleTab);

        Then("^HE I verify \"([^\"]*)\" Text in Fairs Tab in Search and Schedule Tab$",repVisits::verifyTextInFairsTabInSearchAndScheduleTab);

        Then("^HE I verify the Your Schedule Text in Search and Schedule Page$",repVisits::verifyYourScheduleTextInSearchAndScheduleTab);

        And("^HE I verify the Search heading over the search bar after search the school in Search and Schedule Tab$",repVisits::verifytSearchHeadingInSearchAndScheduleTabAfterSchoolSearch);

        Then("^HE I select calendar in RepVisits$",repVisits::selectCalendar);

        Then("^HE I verify the calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCalendarPage);

        Then("^HE I verify the popup for \"([^\"]*)\" using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCalendarPopup);

        Then("^HE I verify the popup for \"([^\"]*)\" using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for freemium$",repVisits::verifyCalendarPopupforfreemium$);

        Then("^HE I select Visits to schedule the appointment for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\" in freemium$",repVisits::visitsScheduleInFreemium);

        Then("^HE I remove the appointment from the calendar$",repVisits::removeAppointmentfromCalendar);

        And("HE I add \"([^\"]*)\" high school with location \"([^\"]*)\" to the Travel Plan",repVisits::addHighSchoolToRepVisitsTravelPlan);

        Then("HE I verify the \"([^\"]*)\" label is displayed for \"([^\"]*)\" high school", repVisits::verifyLabelForTravelPlanHighSchool);

        Then("HE I verify the trash icon for \"([^\"]*)\" high school", repVisits::verifyTrashIconForTravelPlanHighSchool);

        And("HE I remove \"([^\"]*)\" high school from the travel plan", repVisits::removeHighSchoolFromTravelPlan);

        And("HE I cancel removing \"([^\"]*)\" high school from the travel plan", repVisits::cancelRemoveHighSchoolFromTravelPlan);

        Then("HE I verify \"([^\"]*)\" is not displayed in the Travel Plan list",repVisits::verifyHighSchoolIsNotInTravelPlan);

        Then("HE I verify \"([^\"]*)\" is displayed in the Travel Plan list",repVisits::verifyHighSchoolInTravelPlan);

        Then("HE I verify travel plan is locked for non premium users",repVisits::verifyTravelPlanIsLocked);

    }
}
