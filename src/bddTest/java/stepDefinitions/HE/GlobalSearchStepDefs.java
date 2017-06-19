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
        Then("^HE I type into the global search box to show results are returned below the search box in real-time \"([^\"]*)\"$", globalSearch::verifyRealTimeSearch);
        Then("^HE I verify search results were categorized by entity$", globalSearch::verifySearchCategorized);
        Then("^HE I verify that only five or less results are listed in real-time results displayed$", globalSearch::verifySearchCategoryTabDisplaysFiveResults);
        Then("^HE I verify real-time search results are clickable and actionable \"([^\"]*)\"$", globalSearch::verifySearchDropBoxResultsActionable);
        Then("^HE I verify real-time search results are displayed correctly \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchLayout);
    }
}
