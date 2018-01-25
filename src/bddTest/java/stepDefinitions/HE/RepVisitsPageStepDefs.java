package stepDefinitions.HE;

import cucumber.api.PendingException;
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

        Then("^HE I verify the formatting of the Visit Feedback page$", repVisits::verifyVisitFeedbackPage);

        Then("^HE I verify the RepVisits Overview page and Search and Schedule hyperlink when no events are scheduled for the next 7 days", repVisits::verifyDefaultMessageOverviewPage);
    }
}
