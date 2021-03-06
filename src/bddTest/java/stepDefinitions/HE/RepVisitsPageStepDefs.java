package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.EmailNotifications;
import pageObjects.HE.repVisitsPage.RepVisitsPageImpl;

public class RepVisitsPageStepDefs implements En {

    RepVisitsPageImpl repVisits = new RepVisitsPageImpl();

    public RepVisitsPageStepDefs() {

        Then("^HE I verify the following tabs exist on the RepVisits page$", repVisits::checkRepVisitsSubTabs);

        Then("^HE I verify the Search and Schedule tab of the RepVisits page$", repVisits::verifySearchAndSchedulePage);

        Then("^HE I search for High Schools with the following location data in RepVisits$", repVisits::verifySearchResultOfSearchAndSchedule);

        And("^HE I search for \"([^\"]*)\" in RepVisits$", repVisits::searchforHighSchool);

        Then("^HE I request an appointment with \"([^\"]*)\" for Visits with \"([^\"]*)\" and \"([^\"]*)\"$", repVisits::selectVisitForHE);

        Then("^HE I select \"([^\"]*)\" in \"([^\"]*)\" from the RepVisits intermediate search results$", repVisits::selectHighSchoolFromIntermediateSearchResults);

        Then("^HE I view the map plugin on RepVisits Search & Schedule subtab$", repVisits::viewMapPlugin);

        And("^HE I select \"([^\"]*)\" from the RepVisists map plugin$", repVisits::selectSchoolFromMap);

        And("^HE I verify the Coming Soon message on the RepVisits Overview page$", repVisits::verifyOverviewPage);

        Then("HE I verify the high school information popup contains the following data", repVisits::checkHighSchoolPopUp);

        Then ("^HE I verify the upgrade messaging on the Travel Plan page in RepVisits$",repVisits::verifyUpgradeMessageInTravelPlanInRepVisits);

        Then ("^HE I verify the see details link in RepVisits$",repVisits::verifySeeDetailsLinkInRepVisits);

        Then("^HE I verify the Check RepVisits Availability button$", repVisits::verifyCheckRepVisitsAvailabilityButton);

        Then("^HS I go to the repvisits page$",repVisits::navigatetoRepVisits);

        Then("^HS I select \"([^\"]*)\" to show view availability$",repVisits::selectAllRepVisitsUser);

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

        And("^SP I search for \"([^\"]*)\" in Support", repVisits::searchSchoolForSupport);

        Then("^HE I select Visits to schedule the appointment for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::visitsSchedule);

        Then("^HE I click Request button in visit schedule popup$",repVisits::clickRequestButton);

        Then("^HE I register for the \"([^\"]*)\" college fair at \"([^\"]*)\"$",repVisits::visitFairsToRegister);

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

        Then("^HE I verify the calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" for Fairs$",repVisits::verifyCalendarPageforFairs);

        Then("^HE I cancel the Fair appointment from the calendar$",repVisits::cancelFairAppointmentfromCalendar);

        Then("HE verify the Pills got disappear for \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyPills);

        Then("^HE I search for school in RepVisits using \"([^\"]*)\"$",repVisits::searchSchoolinRepVisits);

        Then("^HE I verify the unpaid users are blocked from exporting in Calendar page$",repVisits::verifyExportButtonInCalendar);

        Then("^HE I verify the Export button is Enabled in Calendar page$",repVisits::verifyExportButtonisEnabledInCalendar);

        Then("^HE I export the appointments for the following details \"([^\"]*)\",\"([^\"]*)\"$",repVisits::exportAppointmentsInCalendarPage);

        Then("^HE I verify the downloaded Appointments csv file \"([^\"]*)\" contains following details$",repVisits::verifyDownloadedCsvFileInCalendar);

        Then("^HE I delete the downloaded Appointments Cvs file \"([^\"]*)\"$",repVisits::deleteDownloadedFileInCalendar);
      
        Then("^HE I navigate to the Institution Notification page$",repVisits::navigateToInstitutionNotificationPage);

        Then("^HE I verify the Institution Notification page$",repVisits::verifyInstitutionNotificationPage);

        Then("^HE I validate the Email in the Institution Notification page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::validateEmailInInstitutionNotificationPage);

        Then("^HE I validate the Checkbox in the Institution Notification page using \"([^\"]*)\"$",repVisits::validateCheckboxInInstitutionNotificationPage);

        Then("^HE I verify the Non-admins do not have the tab in navigation$",repVisits::verifyNotificationTabinNonAdmin);

        Then("^HE I verify the Non-admins cannot reach the page directly by URL$",repVisits::verifyNavigationInNonAdminByURl);

        Then("^HE I verify the School details in Travel plan \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifySchoolDetailsInTravelPlan);

        Then("^HE I verify the instructional text in Travel Plan and verify the link to navigate to the Recommendations page$",repVisits::verifyLinkInTravelPlanPage);

        Then("^HE I verify the states of the school are present in the ABC order$",repVisits::verifysortingStatesInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" Text is present in the Travel plan for \"([^\"]*)\"$",repVisits::verifyUpcommingAppointmentTextInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" Text is present in the Travel plan page for \"([^\"]*)\"$",repVisits::verifyScheduledTextInTravelPlan);

        Then("^HE I verify the Visit details are displayed in the Travel plan for \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyVisitDetailsInTravelPlan);

        Then("^HE I verify the Fair details are displayed in the Travel plan for \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyFairDetailsInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" label is displayed for \"([^\"]*)\"$",repVisits::verifyLabelForTravelPlanHighSchool);

        Then("^HE I verify the text \"([^\"]*)\" is present in the Travel plan page for \"([^\"]*)\"$",repVisits::verifyPreviousAppointmentsTextInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" Button is present in the Travel plan page for \"([^\"]*)\"$",repVisits::verifyViewAvailabilityButtonInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" button for \"([^\"]*)\", navigate to the search and schedule page or not$",repVisits::selectViewAvailabilityButtonInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" button is present in the Travel Plan for \"([^\"]*)\"$",repVisits::verifyRemoveButtonInTravelPlan);

        Then("^HE I verify upcoming fair message is displayed in the Travel plan page for \"([^\"]*)\"$",repVisits::verifyUpcomingFairMessageInTravelPlan);

        Then("^HE I verify the \"([^\"]*)\" text is displaying in the Travel Plan for \"([^\"]*)\"$",repVisits::verifyToDoTextInTravelPlan);

        And("^HE I verify the default toggle \"([^\"]*)\" is \"([^\"]*)\" in search and schedule Tab$",repVisits::verifyDefaultToggleinSearchAndSchedule);

        And("^HE I verify the Availability slot \"([^\"]*)\" is displaying in the visit toggle \"([^\"]*)\",\"([^\"]*)\" in search and schedule Tab$",repVisits::verifyAvailabilitySlotInSearchAndSchedule);

        And("^HE I verify the Availability slot \"([^\"]*)\" is not displaying in the visit toggle \"([^\"]*)\",\"([^\"]*)\" in search and schedule Tab$",repVisits::verifyAvailabilitySlotIsNotDisplayingInSearchAndSchedule);

        Then("^HE I search the \"([^\"]*)\" by \"([^\"]*)\"$",repVisits::searchSchoolbyLocation);

        Then("^HE I switch to the Support App$",repVisits::switchToSupportApp);

        Then("^HE I post a \"([^\"]*)\" Message in the homepage$",repVisits::postMessageInHomePage);

        Then("HE I verify the title \"([^\"]*)\" in RepVisits branding header",repVisits::verifyRepVisitsBrandingHeader);

        And("^HE I verify that rate or rating text is not present on Visit Feedback Overview page$", repVisits::verifyRateTextIsPresentInVFOverviewPage);

        And("^HE I verify text displayed while viewing individual staff member feedback$", repVisits::verifyTextDisplayedOnViewingStaffFeedback);

        And("^HE I verify the Repvisits Overview Upgrade Subscription page$", repVisits::verifyRepvisitsOverviewUpgradeSubscriptionPage);

        Then("^HE I verify that the previously created fair appears for \"([^\"]*)\"$",repVisits::verifyCollegeFairVisible);

        Then("^HE I verify that the previously created fair does not appear for \"([^\"]*)\"$",repVisits::verifyCollegeFairNotVisible);
      
        Then("^HE I select Visits to verify the appointment is not present for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifyBlockedAvailability);

        When("^HE I open the Calendar tab in RepVisits$", repVisits::openCalendar);

        And("^HE I open the new fair details in the generated date$", repVisits::openFairDetailsWithGeneratedDate);

        Then("^HE I verify that the following details are present in the fair details in the generated date:$", repVisits::verifyFairDetailsWithGenDate);

        And("^HE I verify that the Email Notification Message says: \"([^\"]*)\"$",EmailNotifications::verifyEmailBody);
      
       Then("^HE I verify the pills is displayed in the search and schedule page using \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifyPillsIsPresent);

        Then("^HE I verify the pills is not displayed in the search and schedule page using \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifyPillsIsNotPresent);

        Then("^SP I click in \"([^\"]*)\" link$",repVisits::clickOnSeeAllUsersLink);

        And("^SP I \"([^\"]*)\" to \"([^\"]*)\"$",repVisits::reInviteSendEmail);

        Then ("HE I verify default page as show visits tab and toggle between tabs",repVisits::verifyDefaultTabAndToggleTab);

        Then("HE I verify the high school information contains the following data", repVisits::checkHighSchoolDetails);

        Then("HE I verify the Intersect Presence Subscription module is active for \"([^\"]*)\"$",repVisits::verifyActiveSubscription);

        Then("^HE I verify the high school information popup contains the following details$",repVisits::verifyHSpopup);

        Then("^HE I verify No Appointments Available and blocked text for \"([^\"]*)\"$",repVisits::verifyHSBlockedText);

        Then("^HE I select high school's Counselor Community institution profile link for \"([^\"]*)\"$",repVisits::selectHSLink);

        Then("HE I verify the Intersect Presence Subscription module is Inactive for \"([^\"]*)\"$",repVisits::verifyInActiveSubscription);

        Then("^HE I verify the dropdown named \"([^\"]*)\" in search and schedule page$",repVisits::verifyDropdownInSearchAndSchedulePage);

        Then("^HE I verify the following fields are displayed after click Search by drop-down$",repVisits::verifyDropdownFieldsInSearchAndSchedule);

        Then("^HE I verify the Background color \"([^\"]*)\" for the following fields to represent that fields are Freemium$",repVisits::verifyBackgroundColorforFreemiumorPremium);

        Then("^HE I verify the Background color \"([^\"]*)\" for the following fields to represent that fields are Premium$",repVisits::verifyBackgroundColorforFreemiumorPremium);

        And("^HE I verify the \"([^\"]*)\" text present with lock icon in the search by drop-down$",repVisits::verifyPremiumSearchInSearchByDropdown);

        Then("^HE I verify \"([^\"]*)\" is a default option in the Search by drop-down for the following fields$",repVisits::verifyDefaultOptionInSearchByDropdown);

        Then("^HE I verify the \"([^\"]*)\" pop-up page, when selecting the premium options from the search by dropdown$",repVisits::verifyUpgradeNotificationPage);

        Then("^HE I verify the fields are displaying box after selecting the following fields in the dropdown$",repVisits::verifySearchByOptionAfterSelectFields);

        Then("^HE I verify the text \"([^\"]*)\" present in the text box$",repVisits::verifyTextInSearchAndScheduleTextBox);

        Then("^HE I select the following fields will not submit the search on the page search by \"([^\"]*)\",\"([^\"]*)\"$",repVisits::selectFieldswillnotSubmitSearch);
      
        Then("^HE I verify the following fields after click Search by drop-down$",repVisits::verifyDropdownFieldsInSearchAndSchedule);

        And("^HE I search a school by \"([^\"]*)\" using \"([^\"]*)\"$",repVisits::searchBySchool);

        And("^HE I verify the search results have \"([^\"]*)\" in the \"([^\"]*)\" field$",repVisits::verifyFilteredSearchResults);

        And("^HE I verify \"([^\"]*)\" is displayed in the search results$",repVisits::verifyLabelInSearchResult);
      
        And("^HE I verify \"([^\"]*)\" is displayed in the search tooltip$",repVisits::verifySearchToolTip);

        Then("^HE I click the link \"([^\"]*)\" in search and schedule page$",repVisits::selectLinkInsearchAndSchedule);

        Then("^HE I verify the Header \"([^\"]*)\" is displayed in search results Page$",repVisits::verifyTextInSearchResultPage);

        Then("^HE I verify the Header name is changed to \"([^\"]*)\" in search result Page$",repVisits::verifyTextInSearchResultPage);

        Then("^HE I verify the school is displayed in schedule page after click the school link using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifySchoolInSchedulePage);

        Then("^HE I verify the button \"([^\"]*)\" is displaying for more than 25 results$",repVisits::verifyMoreResultButtonInSearchAndSchedulePage);

        Then("^HE I verify \"([^\"]*)\" results is not displayed in search and schedule page after move out from International Schools results view$",repVisits::verifySearchAndSchedulePageAfterMovedOut);

        Then("^HE I verify the International Schools list view does not load for freemium users in search and schedule page$",repVisits::verifyInternationalSchoolsListIsNotDisplayedforFreemium);

        Then("^HE I get the URL of the current page$",repVisits::getCurrentPageURL);
      
        And("^HE I verify the city and state \"([^\"]*)\" are present in the underneath of School Name \"([^\"]*)\" in the Request Notification Tab$",repVisits::verifyCityAndStateInRequestNotificationsubTab);

        And("^HE I verify the city and state \"([^\"]*)\" are present in the underneath of School Name \"([^\"]*)\" in the Activity Tab$",repVisits::verifyCityAndStateInActivitysubTab);

        And("^HE I verify the city and state \"([^\"]*)\" are present in the underneath of School Name \"([^\"]*)\" in the Request Notification Tab for Fairs$",repVisits::verifyCityAndStateInRequestNotificationsubTabforFairs);

        And("^HE I verify the city and state \"([^\"]*)\" are present in the underneath of School Name \"([^\"]*)\" in the Activity Tab for Fairs$",repVisits::verifyCityAndStateInActivitysubTabforFairs);
      
        Then("^HE I verify the results count by \"([^\"]*)\" using \"([^\"]*)\" in search results page$",repVisits::verifyResultsCountInSchedulePage);

        And("^HE I verify the data for the following fair$",repVisits::verifyCollegeFairOnList);
      
        Then("^HE I verify \"([^\"]*)\" stub menu is present in Account settings page for Premium$",repVisits::verifyYourNotificationTabforPremium);

        Then("^HE I verify \"([^\"]*)\" stub menu is not present in Account settings page for Freemium$",repVisits::verifyYourNotificationTabforfreemium);

        Then("^HE I verify the following details are present in Your Notifications subtab$",repVisits::verifyYourNotificationTab);

        Then("^HE I verify the success message \"([^\"]*)\" after click Save button$",repVisits::verifySuccessMessageInYourNotification);

        Then("^HE I verify the saved changes after navigate away from Your Notifications subtab$",repVisits::verifySavedChangesInYourNotification);
      
        Then("^HE I set the date using \"([^\"]*)\" and \"([^\"]*)\" in calendar \"([^\"]*)\" view$",repVisits::setDateInCalendarAgenda);

        And("^HE I verify the \"([^\"]*)\" button \"([^\"]*)\" for schools with \"([^\"]*)\"$",repVisits::verifyViewButtonForTravelPlanSchools);

        Then("^HE I verify the user can access \"([^\"]*)\" view$",repVisits::accessAgendaView);

        Then("^HE I verify the message \"([^\"]*)\" is displaying in the \"([^\"]*)\" page$",repVisits::verifyAgendaPageForFreemium);

        And("^HE I verify the \"([^\"]*)\" appointments for schools in travel plan$",repVisits::verifySchoolAppointmentsInTravelPlan);

        Then("^HE I verify \"([^\"]*)\" button is displaying in the \"([^\"]*)\" page$",repVisits::verifyUpgradeButtonInAgendaPage);

        Then("^HE I verify the upgrade model page after clicking the UPGRADE button in Agenda view$",repVisits::verifyUpgradeModelPage);

        Then("^HE I set the value Alert me when high schools become available in RepVisits to selected$",repVisits::selectAlertBoxInYourNotification);
      
        Then("^HE I verify the disabled date \"([^\"]*)\" is not clickable in calendar Agenda view$",repVisits::verifyDisabledDateIsNotClickableInEndDate);

        Then("^HE I verify that Share Calendars Link is displayed in Calendar page$",repVisits::verifyShareCalendarsLinkIsDisplayed);

        Then("^HE I verify that Share your calendar modal is opened when clicking the Share Calendars Link$",repVisits::verifyShareYourCalendarModalIsDisplayed);
      
        Then("^HE I verify the college fair is \"([^\"]*)\" in the calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyCollegeFairInHECalendar);

        Then("^HE I verify default the HE user to see the REQUESTS subtab when they arrive on the Notifications page$",repVisits::verifyRequestsSubTabIsEnabled);

        Then("^HE I verify the Sorting notification entries in the REQUESTS subtab by newest to oldest$",repVisits::verifySortingNotificationEntries);

        Then("^HE I remove the appointment from the calendar for fairs$",repVisits::removeFairsAppointmentFromCalendar);

        Then("^HE I verify the visit details are present in the your schedule section using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyVisitDetailsInYourSchedule);

        Then("^HE I verify the visit details are present in the calendar using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyVisitDetailsInCalendar);

        Then("^HE I verify and select an appointment in calendar page using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyAndSelectAppointmentInCalendarPage);

        Then("^HE I verify the announcement title \"([^\"]*)\" in the announcement details$",repVisits::verifyAnnouncementTitleIsDisplaying);

        Then("^HE I verify the 'Read More' button is displaying for the content with more than 140 character limit$",repVisits::verifyReadMoreButtonIsDisplaying);

        Then("^HE I verify the announcement details after the success toast message using \"([^\"]*)\"$",repVisits::verifyAnnouncementDetailsAfterSuccessMessage);

        Then("^HE I verify the dismiss button is displaying in the announcement details$",repVisits::verifyDismissButtonInAnnouncementDetails);

        Then("^HE I click the dismiss button$",repVisits::clickDismissButton);

        Then("^HE I verify the announcement is not displaying after clicking dismiss button$",repVisits::verifyAnnouncementIsNotDisplaying);

        Then("^HE I verify the content begins where title would begin in notification bar \"([^\"]*)\"$",repVisits::verifyProductAnnouncementContent);

        Then("^HE I verify the Recommendation tab of the RepVisits page$", repVisits::verifyRecommendatiosPage);

        Then("^HE I search \"([^\"]*)\" in Recommendation tab of the RepVisits page",repVisits::searchforRecommendationsPage);

        Then("^HE I verify the Details of Recommendation result for \"([^\"]*)\"$",repVisits::verifyDetailsofRecommendation);

        Then("^HE I click the View Availability for \"([^\"]*)\" and verify the close button$",repVisits::verifyViewAvailabilityCloseButton);

        Then("^HE I verify \"([^\"]*)\" for \"([^\"]*)\"$",repVisits::verifyAvailability);

        Then("^HE I verify the 'Read More' button is not displaying for the content with less than 140 character limit$",repVisits::verifyReadMoreButtonIsNotDisplaying);

        Then("^HE I verify the \"([^\"]*)\" button$",repVisits::verifyAddToTravelPlanButtonInHSPage);

        Then ("^HE I verify the school \"([^\"]*)\" and delete if it is added to travel plan$",repVisits::verifyTravelPlanAndDelete);
      
        Then("^HE I verify the premium feature header is displaying in search and schedule page using \"([^\"]*)\"$",repVisits::verifyPremiumFeatureHeaderIsDisplayingInSearchAndSchedule);

        Then("^HE I verify the lock icon is displaying in search and schedule page using \"([^\"]*)\"$",repVisits::verifyLockIconIsDisplayingInSearchAndSchedule);

        Then("^HE I verify learn more hyper link is displaying in search and schedule page using \"([^\"]*)\"$",repVisits::verifyLearnMoreHyperLinkIsDisplayingInSearchAndSchedulePage);

        Then("^HE I verify the premium feature header is not displaying in search and schedule page$",repVisits::verifyPremiumFeatureHeaderIsNotDisplayingInSearchAndSchedule);

        Then("^HE I verify the lock icon is not displaying in search and schedule page$",repVisits::verifyLockIconIsNotDisplayingInSearchAndSchedule);

        Then("^HE I verify learn more hyper link is not displaying in search and schedule page$",repVisits::verifyLearnMoreHyperLinkIsNotDisplayingInSearchAndSchedulePage);

        Then("^HE I navigate to \"([^\"]*)\" page$",repVisits::navigateToRepVisitsSection);

        Then("^HE I verify \"([^\"]*)\" text is displaying in search and schedule tab$",repVisits::verifyYourScheduleTextInSearchAndSchedule);

        Then("^HE I verify the month and dates displayed in the title bar of the your schedule content always matches what is selected for the search bar \"([^\"]*)\"$",repVisits::verifyCurrentVisitDateInSearchAndSchedule);

        Then("^HE I verify the view details hyperlink for each high school visit already scheduled by that user opens a popup \"([^\"]*)\"$",repVisits::verifyHyperLinkInYourSchedulePage);

        Then("^HE I verify the school details are present in the your schedule popup \"([^\"]*)\"$",repVisits::verifySchoolDetailsInYourSchedulePopup);

        Then("^HE I verify the link navigate to the Counselor Community institution profile page \"([^\"]*)\"$",repVisits::verifyLinkNavigationInYourSchedulePopup);

        Then("^HE I verify the popup has an 'X' icon that would close the popup if clicked$",repVisits::verifyCloseIconInYourSchedulePopup);
      
        Then("^HE I verify the availability pill is displaying in community availability side bar \"([^\"]*)\"$",repVisits::verifyAvailabilitySlotInCommunitySideBar);

        Then("^HE I verify the availability pill is clickable in community availability side bar$",repVisits::verifyAvailabilitySlotIsClickable);

        Then("^HE I verify the pill is clickable in search and schedule page$",repVisits::verifyAvailabilitySlotIsClickable);

        Then("^HE I verify the appointment date is displaying in the schedule popup \"([^\"]*)\"$",repVisits::verifyAppointmentDateIsDisplayingInSchedulePopup);

        Then("^HE I verify the start and end time is displaying in the schedule popup \"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyStartAndEndTimeInSearchAndSchedulePopup);

        Then("^HE I verify the time zone is displaying in the schedule popup$",repVisits::verifyTimeZoneInSearchAndSchedulePopup);

        Then("^HE I verify the High school name is displaying in the schedule popup \"([^\"]*)\"$",repVisits::verifyHSNameInVisitSchedulePopup);

        Then("^HE I verify the \"([^\"]*)\" button is displaying in the schedule popup$",repVisits::verifySubmitButtonInVisitSchedulePopup);

        Then("^HE I verify that i can close the schedule popup by clicking \"([^\"]*)\" button if i do not want to submit request$",repVisits::closeSearchAndSchedulePopup);

        Then("^HE I verify \"([^\"]*)\" button to submit request to the high school$",repVisits::selectSubmitButtonInSearchAndSchedulePopup);

        Then("^HE I verify the success message \"([^\"]*)\" is displaying in search and schedule page$",repVisits::verifySuccessMessageInSearchAndSchedulePage);

        Then("^HE I logged in to Intersect HE as another HE user type \"([^\"]*)\"$",repVisits::loggingAnotherAccount);

        Then("^HE I switch to another HE User Tab$",repVisits::switchToAnotherHEuserTab);

        Then("^HE I verify the negative message \"([^\"]*)\" is displaying in search and schedule page$",repVisits::verifyNegativeMessageIsDisplaying);

        Then("^HE I go to the calendar page and verify the visit appointment is displaying with gray color \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyPendingVisitInCalendar);

        Then("^HE I go to the calendar page and verify the visit appointment is displaying with blue color \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyScheduledVisitInCalendar);

        Then("^HE I verify that i can close the schedule popup in community availability side bar by clicking \"([^\"]*)\" button if i do not want to submit request$",repVisits::clickCancelButtonInCommunityAvailabilityPopup);

        Then("^HE I close community availability side bar$",repVisits::closeCommunityAvailability);

        Then("^HE I navigate to \"([^\"]*)\"$",repVisits::navigateToRepVisitsSection);

        Then("^HE I verify the schedule pop_up for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$",repVisits::verifySchedulePopup);
    }
}