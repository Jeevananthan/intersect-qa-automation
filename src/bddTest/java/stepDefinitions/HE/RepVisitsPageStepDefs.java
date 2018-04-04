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

        Then("^HE I verify that the registered fair is displayed in the calendar for the date \"([^\"]*)\"$", repVisits::verifyFairInCalendar);

        Then("^HE I verify that the registered fair is displayed for \"([^\"]*)\" in the Search and Schedule quickview in the date \"([^\"]*)\"$", repVisits::verifyFairInQuickView);

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

        And("^HE I verify the default toggle \"([^\"]*)\" is \"([^\"]*)\" in search and schedule Tab$",repVisits::verifyDefaultToggleinSearchAndSchedule);

        And("^HE I verify the Availability slot \"([^\"]*)\" is displaying in the visit toggle \"([^\"]*)\",\"([^\"]*)\" in search and schedule Tab$",repVisits::verifyAvailabilitySlotInSearchAndSchedule);

        And("^HE I verify the Availability slot \"([^\"]*)\" is not displaying in the visit toggle \"([^\"]*)\",\"([^\"]*)\" in search and schedule Tab$",repVisits::verifyAvailabilitySlotIsNotDisplayingInSearchAndSchedule);

        Then("^HE I search the \"([^\"]*)\" by \"([^\"]*)\"$",repVisits::searchSchoolbyLocation);

    }
}
