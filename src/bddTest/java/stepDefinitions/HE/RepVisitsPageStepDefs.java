package stepDefinitions.HE;

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

        Then ("^HE I verify the upgrade messaging on the Travel Plan page in RepVisits$",repVisits::verifyUpgradeMessageInTravelPlanInRepVisits);

        Then ("^HE I verify the upgrade messaging on the Contacts page in RepVisits$",repVisits::verifyUpgradeMessageInContactsInRepVisits);

        Then ("^HE I verify the upgrade messaging on the Recommendations page in RepVisits$",repVisits::verifyUpgradeMessageInRecommendationsInRepVisits);

        Then("^HE I verify the Check RepVisits Availability button$", repVisits::verifyCheckRepVisitsAvailabilityButton);

        And("^HE I verify Repvisits Special Instructions for School are \"([^\"]*)\"$", repVisits::verifyHSSpecialInstructions);

        And("^HE I verify the calendar view in repvisits$",repVisits::verifyCalendarViewOnRepVisits);

        Then("^HE I navigate to the \"([^\"]*)\" page in RepVisits$", repVisits::navigateToRepVisitsSection);

        And("^HE I verify the non-administrator messaging on the Visits Feedback page$", repVisits::verifyVisitsFeedbackNonAdminMessaging);

        And("^HE I verify the freemium messaging on the Visits Feedback page$", repVisits::verifyVisitsFeedbackFreemiumMessaging);

        And("^HE I navigate to the \"([^\"]*)\" Page$",repVisits::navaigateToAccountSettings);

        And("^HE I verify the left-sub menu are present in the Account Settings page$",repVisits::verifyAccountsettings);

        And("^HE I verify the non-password fields are pre-populated with current data \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::verifyPasswordFields);

        And("^HE I validate the password field \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",repVisits::validatePassword);

        And("^HE I verify the left sub menu are present in the Account Settings page$",repVisits::verifyDetailsInaccountSettings);

        And("^HE I reset the password for \"([^\"]*)\",\"([^\"]*)\"$",repVisits::resetPassword);

        And("^HE I verify the left sub menu are present in the Account Settings page for Non-Admin$",repVisits::verifyDetailsInaccountSettingsforNonAdmin);

    }
}
