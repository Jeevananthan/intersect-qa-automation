package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.activeMatch.ActiveMatchNextGenPageImpl;

public class ActiveMatchNextGenStepDefs implements En{


        public ActiveMatchNextGenStepDefs() {

            ActiveMatchNextGenPageImpl activeMatchNextGenPage = new ActiveMatchNextGenPageImpl();

            Then("^SM I click on the \"([^\"]*)\" for the \"([^\"]*)\" card$", activeMatchNextGenPage::clickOnForTheCard);
        }
}
