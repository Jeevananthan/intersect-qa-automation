package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class FCSuperMatchPageStepDefs implements En {

    public FCSuperMatchPageStepDefs() {

        FCSuperMatchPageImpl fcSuperMatch = new FCSuperMatchPageImpl();

        Then("^SM I verify that a banner with a message about the new SuperMatch is displayed$", fcSuperMatch::verifySuperMatchBanner);

        Then("^SM I check the delete icon in save search$", fcSuperMatch::checkDeleteIconInSaveSearch);

        Then("^SM I verify delete confirmation popup message$", fcSuperMatch::verifySaveSearchDeleteConfirmationPopup);

        And("^SM I delete the save search and verify it$",fcSuperMatch::deleteSaveSearch);

        When("^SM I add \"([^\"]*)\" to the Colleges I'm thinking about list if it is not already there$", fcSuperMatch::addCollegeToImThinkingAboutList);

        And("^SM I go to Colleges Looking for Students Like You list$", fcSuperMatch::goToCollegesLookingForStudentsLikeYou);

    }
}
