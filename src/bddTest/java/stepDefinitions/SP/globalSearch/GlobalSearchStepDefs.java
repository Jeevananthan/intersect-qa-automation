package stepDefinitions.SP.globalSearch;

import cucumber.api.java8.En;
import pageObjects.SP.commonPages.GlobalSearch;

public class GlobalSearchStepDefs implements En {
    public GlobalSearchStepDefs() {

        GlobalSearch globalSearch = new GlobalSearch();

        And("^SP I search for \"([^\"]*)\" as a HE Institution in the global search box$", globalSearch::searchForHEInstitutions);

        Then("^SP I am presented with all institutional accounts that contain \"([^\"]*)\" in their names below the global search box$", globalSearch::verifyInstitutionalResults);

        When("^SP I search for \"([^\"]*)\"$", globalSearch::searchForAll);

        Then("^SP I am able to see \"([^\"]*)\" institution in the results$", globalSearch::verifyInstitutionalResults);

        And("^SP I select the following institution \"([^\"]*)\" from the results$", globalSearch::selectResult);

        Then("^SP I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);

        Then("^SP I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);
    }
}
