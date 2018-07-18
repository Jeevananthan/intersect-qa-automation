package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class SuperMatchPageStepDefs implements En {

    public SuperMatchPageStepDefs() {

        FCSuperMatchPageImpl fcSuperMatch = new FCSuperMatchPageImpl();

        Then("^SM I verify that the link in the new SuperMatch banner takes me to the new SuperMatch$", fcSuperMatch::verifySuperMatchBannerLink);

        Then("^SM I verify that the tooltips are displayed in the tab \"([^\"]*)\"$", fcSuperMatch::verifyTooltipsInFitCriteria);

        Then("^SM I verify that the tooltips are displayed in the section \"([^\"]*)\"$", fcSuperMatch::verifyTooltipsInSection);

        Then("^SM I verify that the appropriate legend is displayed in the Why Drawer in position \"([^\"]*)\", according to the following data:$", fcSuperMatch::verifyLegendInWhyDrawer);

        And("^SM I skip the onboarding modals$", fcSuperMatch::skipModals);

        Then("^SM I verify that the onboarding modal is displayed for the following sections$", fcSuperMatch::verifyOnboardingModals);

        And("^SM I enable the onboarding modals if they are disabled$", fcSuperMatch::enableOnboardingModalsIfDisabled);

        And("^SM I open the Save Search popup$", fcSuperMatch::clickSaveSearchButton);

        Then("^SM I verify that the Save Search button is disabled$", fcSuperMatch::verifySaveSearchButtonDisabled);

        Then("^SM I verify the saved search of name \"([^\"]*)\" is displayed in the Saved Searches dropdown$", fcSuperMatch::verifySavedSearchInDropdown);

        And("^SM I select \"([^\"]*)\" in the Saved Searches dropdown$", fcSuperMatch::selectOptionFromDropdown);

        Then("^SM I verify that \"([^\"]*)\" is displayed as selected option in the Saved Searches dropdown$", fcSuperMatch::verifySelectedOption);

        And("^SM I select the \"([^\"]*)\" option from the \"([^\"]*)\" dropdown in Cost$", fcSuperMatch::selectOptionInDropdown);

        Then("^SM I create fifteen different save search from Resources tab$", fcSuperMatch::createFifteenSaveSearch);

        And("^SM I validate the error message \"([^\"]*)\"$", fcSuperMatch::verifySaveSearchMessage);

        When("^SM I open the \"([^\"]*)\" tab$", fcSuperMatch::openTab);

        Then("^SM I verify that the appropriate wording is used for dropdowns of the following options:$", fcSuperMatch::verifyDropdownsWordingInCost);


    }
}
