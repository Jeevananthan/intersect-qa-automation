package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.COMMON.GlobalSearch;

public class GlobalSearchStepDefs implements En {
    public GlobalSearchStepDefs() {
        GlobalSearch globalSearch = new GlobalSearch();

        //Placeholder for testing
        Then("^HE I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);

        //Placeholder for testing
        Then("^HE I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);
    }
}
