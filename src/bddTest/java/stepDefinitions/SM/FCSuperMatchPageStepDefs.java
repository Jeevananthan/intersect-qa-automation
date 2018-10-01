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

    }
}
