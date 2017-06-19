package stepDefinitions.HS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.GlobalSearch;

public class GlobalSearchStepDefs implements En {
    public GlobalSearchStepDefs() {
        GlobalSearch globalSearch = new GlobalSearch();

        Then("^HS I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);
        Then("^HS I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);
        Then("^HS I verify there are no search results returned$", globalSearch::verifyNoSearchResults);
        And("^HS I select \"([^\"]*)\" from the results$", globalSearch::selectResult);
        Then("^HS I type into the global search box to show results are returned below the search box in real-time \"([^\"]*)\"$", globalSearch::verifyRealTimeSearch);
        Then("^HS I verify search results were categorized by entity$", globalSearch::verifySearchCategorized);
        Then("^HS I verify that only five or less results are listed in real-time results displayed$", globalSearch::verifySearchCategoryTabDisplaysFiveResults);
        Then("^HS I verify real-time search results are clickable and actionable \"([^\"]*)\"$", globalSearch::verifySearchDropBoxResultsActionable);
        Then("^HS I verify real-time search results are displayed correctly \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchLayout);
    }
}
