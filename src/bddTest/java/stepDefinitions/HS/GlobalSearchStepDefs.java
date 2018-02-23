package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.COMMON.GlobalSearch;

public class GlobalSearchStepDefs implements En {
    public GlobalSearchStepDefs() {
        GlobalSearch globalSearch = new GlobalSearch();

        Then("^HS I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);
        Then("^HS I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);
        And("^HS I select \"([^\"]*)\" from the results$", globalSearch::selectResult);
        Then("^HS I type into the global search box to show results are returned below the search box in real-time \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchResultsReturned);
        Then("^HS I verify real-time search results were categorized by entity$", globalSearch::verifyRealTimeSearchCategorized);
        Then("^HS I verify that only five or less results are listed in real-time results displayed$", globalSearch::verifyRealTimeSearchCategoriesDisplayFiveOrLessResults);
        Then("^HS I verify real-time search results are clickable and actionable \"([^\"]*)\"$", globalSearch::verifySearchDropBoxResultsActionable);
        Then("^HS I verify real-time search layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchLayout);
        Then("^HS I type into the global search box and hit return/enter key to display advanced search results \"([^\"]*)\"$", globalSearch::verifyAdvanceSearchByEnterKey);
        Then("^HS I verify the search results page defaults me to the users tab after performing a global search$", globalSearch::VerifyUserSearchDefaultPage);
        Then("^HS I type into the global search box and click search icon to display advanced search results \"([^\"]*)\"$", globalSearch::verifyAdvanceSearchByIcon);
        Then("^HS I verify advanced search results were categorized by entity$", globalSearch::verifyAdvancedSearchResultsCategorized);
        Then("^HS I verify that only five or less results are listed for advanced search results displayed by category$", globalSearch::verifyAdvancedSearchCategoryTabsDisplayFiveOrLessResults);
        Then("^HS I verify advanced search tab layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchResultsLayout);
        Then("^HS I verify advanced search returns the HS user's general description field below the title and institution fields \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchUserResultsDisplaysGeneralDescription);
        Then("^HS I verify I can perform an advanced search utilizing any combination of fields for \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchFieldsReturnResults);
        Then("^HS I verify real-time search results do not return any results for HE groups \"([^\"]*)\"$", globalSearch::verifyNoRealTimeSearchResultsReturned);
        Then("^HS I verify advanced search results do not return any results for HE groups \"([^\"]*)\"$", globalSearch::verifyNoAdvancedSearchResultsReturned);
    }
}
