package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.activeMatchPage.ActiveMatchPageImpl;

public class ActiveMatchPageStepDefs implements En {

    public ActiveMatchPageStepDefs() {

        ActiveMatchPageImpl activeMatchPage = new ActiveMatchPageImpl();

        Then("^HE The Active Match page is displayed$", activeMatchPage::verifyTitleIsPresent);

        Then("^HE I navigate to the ActiveMatch Tab$", activeMatchPage::navigateToActiveMatch);

        Then("^HE I verify the following details are present under the new header of \"([^\"]*)\" in the ActiveMatch export connections dropdown Menu$",activeMatchPage::verifyActiveMatchDropdownMenu);

        When("^HE I export the ActiveMatchConnections for the current year$",activeMatchPage::downloadActiveMatchConnectionsForCurrentYear);

        Then("^HE I verify the downloaded ActiveMatch Cvs file \"([^\"]*)\" contains the following headers",activeMatchPage::verifyDownloadedActiveMatchConnectionsHeaders);

        Then("^HE I delete the downloaded ActiveMatch Cvs file \"([^\"]*)\"",activeMatchPage::deleteDownloadedActiveMatchConnectionsFile);

    }
}
