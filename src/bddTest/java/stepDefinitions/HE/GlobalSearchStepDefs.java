package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.COMMON.GlobalSearch;

public class GlobalSearchStepDefs implements En {
    public GlobalSearchStepDefs() {
        GlobalSearch globalSearch = new GlobalSearch();

        Then("^HE I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);
        Then("^HE I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);
        And("^HE I select \"([^\"]*)\" from the results$", globalSearch::selectResult);
        Then("^HE I type into the global search box to show results are returned below the search box in real-time \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchResultsReturned);
        Then("^HE I verify real-time search results were categorized by entity$", globalSearch::verifyRealTimeSearchCategorized);
        Then("^HE I verify that only five or less results are listed in real-time results displayed$", globalSearch::verifyRealTimeSearchCategoriesDisplayFiveOrLessResults);
        Then("^HE I verify real-time search results are clickable and actionable \"([^\"]*)\"$", globalSearch::verifySearchDropBoxResultsActionable);
        Then("^HE I verify real-time search layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchLayout);
        Then("^HE I verify there is no global search options available$", globalSearch::verifyGlobalSearchNotDisplayed);
        Then("^HE I type into the global search box and hit return/enter key to display advanced search results \"([^\"]*)\"$", globalSearch::verifyAdvanceSearchByEnterKey);
        Then("^HE I verify the search results page defaults me to the users tab after performing a global search$", globalSearch::VerifyUserSearchDefaultPage);
        Then("^HE I type into the global search box and click search icon to display advanced search results \"([^\"]*)\"$", globalSearch::verifyAdvanceSearchByIcon);
        Then("^HE I verify advanced search results were categorized by entity$", globalSearch::verifyAdvancedSearchResultsCategorized);
        Then("^HE I verify that only five or less results are listed for advanced search results displayed by category$", globalSearch::verifyAdvancedSearchCategoryTabsDisplayFiveOrLessResults);
        Then("^HE I verify advanced search tab layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchResultsLayout);
        Then("^HE I verify advanced search returns the HS user's general description field below the title and institution fields \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchUserResultsDisplaysGeneralDescription);
        Then("^HE I verify I can perform an advanced search utilizing any combination of fields for \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchFieldsReturnResults);
        Then("^HE I verify real-time search results do not return any results for HS groups \"([^\"]*)\"$", globalSearch::verifyNoRealTimeSearchResultsReturned);
        Then("^HE I verify advanced search results do not return any results for HS groups \"([^\"]*)\"$", globalSearch::verifyNoAdvancedSearchResultsReturned);
        Then("^HE I verify the real-time results return for global search are a partial and full match \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchMatch);

    }
}
