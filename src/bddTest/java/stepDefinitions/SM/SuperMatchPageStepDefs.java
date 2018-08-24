package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class SuperMatchPageStepDefs implements En {

    public SuperMatchPageStepDefs() {

        FCSuperMatchPageImpl fcSuperMatch = new FCSuperMatchPageImpl();

        Then("^SM I verify that the link in the new SuperMatch banner takes me to the new SuperMatch$", fcSuperMatch::verifySuperMatchBannerLink);

        Then("^SM I verify that the tooltips are displayed in the tab \"([^\"]*)\"$", fcSuperMatch::verifyTooltipsInFitCriteria);

        Then("^SM I verify that the tooltips are displayed in the section \"([^\"]*)\"$", fcSuperMatch::verifyTooltipsInSection);

        Then("^SM I verify that the appropriate legend is displayed in the Why Drawer in position \"([^\"]*)\", according to the following data:$", fcSuperMatch::verifyLegendInWhyDrawer);

        And("^SM I skip the onboarding modals$", fcSuperMatch::skipModalPopups);

        Then("^SM I verify that the onboarding modal is displayed for the following sections$", fcSuperMatch::verifyOnboardingModals);

        And("^SM I enable the onboarding modals if they are disabled$", fcSuperMatch::enableOnboardingModalsIfDisabled);

        And("^SM I open the Save Search popup$", fcSuperMatch::clickSaveSearchButton);

        Then("^SM I verify that the Save Search button is disabled$", fcSuperMatch::verifySaveSearchButtonDisabled);

        Then("^SM I verify the saved search of name \"([^\"]*)\" is displayed in the Saved Searches dropdown$", fcSuperMatch::verifySavedSearchInDropdown);

        And("^SM I select \"([^\"]*)\" in the Saved Searches dropdown$", fcSuperMatch::selectOptionFromDropdown);

        Then("^SM I verify that \"([^\"]*)\" is displayed as selected option in the Saved Searches dropdown$", fcSuperMatch::verifySelectedOption);

        Then("^SM I create till fifteen different save search from Resources tab$", fcSuperMatch::createTotalFifteenSaveSearch);

        Then("^SM I verify that the Start Over button is disabled$", fcSuperMatch::verifyStartOverButtonDisabled);

        Then("^SM I verify the content of the popup that is opened by the Start Over button$", fcSuperMatch::verifyStartOverPopupContent);

        Then("^SM I verify that the search results remain after clicking the No, Cancel button$", fcSuperMatch::verifyResultsAfterCancelButton);

        And("^SM I open the Start Over popup$", fcSuperMatch::clickStartOverButton);

        Then("^SM I verify that the fit criteria is removed after clicking the Yes, Start Over button$", fcSuperMatch::verifyFitCriteriaRemovedAfterStartOverButton);

        And("^SM I select the \"([^\"]*)\" option from the \"([^\"]*)\" dropdown in Cost$", fcSuperMatch::selectOptionInDropdown);

        Then("^SM I create fifteen different save search from Resources tab$", fcSuperMatch::createTotalFifteenSaveSearch);

        And("^SM I validate the error message \"([^\"]*)\"$", fcSuperMatch::verifySaveSearchMessage);

        When("^SM I open the \"([^\"]*)\" tab$", fcSuperMatch::openTab);

        Then("^SM I verify that the appropriate wording is used for dropdowns of the following options:$", fcSuperMatch::verifyDropdownsWordingInCost);

        Then("^SM I create a save search \"([^\"]*)\" by selecting \"([^\"]*)\" from Resources tab$", fcSuperMatch::createOneSaveSearch);

        Then("^SM I check the delete icon in save search \"([^\"]*)\"$", fcSuperMatch::checkDeleteIconInSaveSearch);

        Then("^SM After clicking \"([^\"]*)\" delete icon I check the confirmation popup message$", fcSuperMatch::verifySaveSearchDeleteConfirmationPopup);

        And("^SM I check clicking outside will close the \"([^\"]*)\" popup message$",fcSuperMatch::verifyClickOutsideClosePopup);

        And("^SM I delete the save search \"([^\"]*)\" and verify it$",fcSuperMatch::deleteSaveSearch);

        And("^SM I delete the saved search named \"([^\"]*)\"$",fcSuperMatch::deleteSavedSearchByName);

        And("^SM I cancel the Save Search popup$",fcSuperMatch::cancelSaveSearchPopup);

        Then("^SM I verify that no tooltip icon is displayed for GPA in the results table$", fcSuperMatch::verifyNoGPATooltipIcon);

        Then("^I verify that there is not text \"([^\"]*)\" on the page$", fcSuperMatch::verifyThereIsNoTextOnThePage);

    }
}
