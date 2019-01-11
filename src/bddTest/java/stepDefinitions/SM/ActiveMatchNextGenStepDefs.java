package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.activeMatch.ActiveMatchNextGenPageImpl;

public class ActiveMatchNextGenStepDefs implements En{


        public ActiveMatchNextGenStepDefs() {

            ActiveMatchNextGenPageImpl activeMatchNextGenPage = new ActiveMatchNextGenPageImpl();

            Then("^SM I click on the \"([^\"]*)\" for the \"([^\"]*)\" card$", activeMatchNextGenPage::clickOnForTheCard);
            Then("^SM I check ActiveMatch NextGen matches are displayed$", activeMatchNextGenPage::checkActiveMatchNextGenAreDisplayed);
            Then("^SM I check Legacy AM matches are displayed$",activeMatchNextGenPage::checkLegacyAMAreDisplayed);
            Then("^SM I check Legacy AM match for \"([^\"]*)\" displayes:$",activeMatchNextGenPage::checkLegacyCardDisplayes);
        }
}
