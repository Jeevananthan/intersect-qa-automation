package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class SaveSearchPageStepDefs implements En {

    public SaveSearchPageStepDefs(){

        FCSuperMatchPageImpl fcSuperMatch = new FCSuperMatchPageImpl();

        Then("^SM I create a save search by selecting \"([^\"]*)\" from Resources tab$", fcSuperMatch::createOneSaveSearch);

        And("^SM I check if any save search is present if not then create a save search for \"([^\"]*)\"$", fcSuperMatch::checkSaveSearch);


    }
}