package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.GlobalSearch;

public class GlobalSearchStepDefs implements En {
    public GlobalSearchStepDefs() {
        GlobalSearch globalSearch = new GlobalSearch();

        Then("^HE I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);

        Then("^HE I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);

        Then("^HE I verify there are no search results returned$", globalSearch::verifyNoSearchResults);

        And("^HE I select \"([^\"]*)\" from the results$", globalSearch::selectResult);

    }
}
