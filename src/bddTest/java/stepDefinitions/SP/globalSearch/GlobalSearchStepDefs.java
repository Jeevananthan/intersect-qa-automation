package stepDefinitions.SP.globalSearch;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.GlobalSearch;

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

        Then("^SP I verify there are no search results returned$", globalSearch::verifyNoSearchResults);

        Then("^SP I type into the global search box to show results are returned below the search box in real-time \"([^\"]*)\"$", globalSearch::verifyRealTimeSearch);

        Then("^SP I verify search results were categorized by entity$", globalSearch::verifySearchCategorized);

        Then("^SP I verify that only five or less results are listed in real-time results displayed$", globalSearch::verifySearchCategoryTabDisplaysFiveResults);

        Then("^SP I verify real-time search results are clickable and actionable \"([^\"]*)\"$", globalSearch::verifySearchDropBoxResultsActionable);

        Then("^SP I verify real-time search results are displayed correctly \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchLayout);
    }
}
