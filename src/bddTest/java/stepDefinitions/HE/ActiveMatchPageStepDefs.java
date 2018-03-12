package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.activeMatchPage.ActiveMatchPageImpl;

public class ActiveMatchPageStepDefs implements En {

    public ActiveMatchPageStepDefs() {

        ActiveMatchPageImpl activeMatchPage = new ActiveMatchPageImpl();

        Then("^HE The Active Match page is displayed$", activeMatchPage::verifyTitleIsPresent);

        Then("^HE I navigate to the ActiveMatch Tab$", activeMatchPage::navigateToActiveMatch);

        Then("^HE I verify the ActiveMatch page$", activeMatchPage::verfyActiveMatchPage);

        Then("^HE I verify the following details are present under the new header of \"([^\"]*)\" in the ActiveMatch export connections dropdown Menu$",activeMatchPage::verifyActiveMatchDropdownMenu);

        Then("^HE I verify the following headers are present in the ActiveMatch export connections dropdown Menu in the following order$",activeMatchPage::verifyDropdownMenuHeader);

        Then("^HE I verify the Header after selecting \"([^\"]*)\" for the following selection in the ActiveMatch export connections$",activeMatchPage::verifySelectedHeader);

        Then("^HE I verify the Header \"([^\"]*)\"$",activeMatchPage::verifyHeader);

        Then("^HE I verify the Default drop-down Menu selection to remain \"([^\"]*)\" after all connections are modified$",activeMatchPage::verifyDefaultdropdownMenuSelection);
    }
}
