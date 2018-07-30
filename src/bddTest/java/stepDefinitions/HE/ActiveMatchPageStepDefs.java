package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.activeMatchPage.ActiveMatchPageImpl;

public class ActiveMatchPageStepDefs implements En {

    public ActiveMatchPageStepDefs() {

        ActiveMatchPageImpl activeMatchPage = new ActiveMatchPageImpl();

        Then("^HE I verify the ActiveMatch page$", activeMatchPage::verfyActiveMatchPage);

        Then("^HE I verify the Headers are present in the Active Match Connections$",activeMatchPage::verifyActiveMatchConnectionsHeaders);

        Then("^HE I verify the following details are present under the new header of \"([^\"]*)\" in the ActiveMatch export connections dropdown Menu$",activeMatchPage::verifyActiveMatchDropdownMenu);

        Then("^HE I verify the following headers are present in the ActiveMatch export connections dropdown Menu in the following order$",activeMatchPage::verifyDropdownMenuHeader);

        Then("^HE I verify the Default drop-down Menu selection to remain \"([^\"]*)\" after all connections are modified$",activeMatchPage::verifyDefaultdropdownMenuSelection);

        Then("^HE I verify the following details are present under the header of \"([^\"]*)\" in the ActiveMatch export connections dropdown Menu$",activeMatchPage::verifyActiveMatchConnectionsDropdownMenu);

        Then("^HE I navigate to the ActiveMatch Tab$", activeMatchPage::navigateToActiveMatch);

        When("^HE I export the ActiveMatchConnections for the current year$",activeMatchPage::downloadActiveMatchConnectionsForCurrentYear);

        Then("^HE I verify the downloaded ActiveMatch Cvs file \"([^\"]*)\" contains the following headers",activeMatchPage::verifyDownloadedActiveMatchConnectionsHeaders);

        Then("^HE I delete the downloaded ActiveMatch Cvs file \"([^\"]*)\"",activeMatchPage::deleteDownloadedActiveMatchConnectionsFile);
    }
}
