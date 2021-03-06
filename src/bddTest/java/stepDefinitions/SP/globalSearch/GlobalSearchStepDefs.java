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
        Then("^SP I select \"([^\"]*)\" from the global search results$", globalSearch::selectResult);
        Then("^SP I search for \"([^\"]*)\" in \"([^\"]*)\"$", globalSearch::search);
        Then("^SP I go to the advanced search page for \"([^\"]*)\"$", globalSearch::goToAdvancedSearch);
        Then("^SP I verify there are no search results returned$", globalSearch::verifyNoSearchResults);
        When("^SP I search for \"([^\"]*)\" as an Institution in the global search box$", globalSearch::searchForInstitutions);
        Then("^SP I verify real-time search results were categorized by entity$", globalSearch::verifyRealTimeSearchCategorized);
        Then("^SP I verify that only five or less results are listed in real-time results displayed$", globalSearch::verifyRealTimeSearchCategoriesDisplayFiveOrLessResults);
        Then("^SP I verify real-time search results are clickable and actionable \"([^\"]*)\"$", globalSearch::verifySearchDropBoxResultsActionable);
        Then("^SP I verify real-time search layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchLayout);
        Then("^SP I type into the global search box and hit return/enter key to display advanced search results \"([^\"]*)\"$", globalSearch::verifyAdvanceSearchByEnterKey);
        Then("^SP I verify the search results page defaults me to the users tab after performing a global search$", globalSearch::VerifyUserSearchDefaultPage);
        Then("^SP I type into the global search box and click search icon to display advanced search results \"([^\"]*)\"$", globalSearch::verifyAdvanceSearchByIcon);
        Then("^SP I verify advanced search results were categorized by entity$", globalSearch::verifyAdvancedSearchResultsCategorized);
        Then("^SP I verify that only five or less results are listed for advanced search results displayed by category$", globalSearch::verifyAdvancedSearchCategoryTabsDisplayFiveOrLessResults);
        Then("^SP I verify advanced search tab layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchResultsLayout);
        Then("^SP I verify I can perform an advanced search utilizing any combination of fields for \"([^\"]*)\"$", globalSearch::verifyAdvancedSearchFieldsReturnResults);
        Then("^SP Global real-time search returns search results for HE Accounts \"([^\"]*)\"$", globalSearch::verifyHEAccountSearchResults);
        And("^SP I set HE Account Subscriptions \"([^\"]*)\"$", globalSearch::setHEAccountSubscriptions);
        Then("^SP I verify the real-time results return for global search are a partial and full match \"([^\"]*)\"$", globalSearch::verifyRealTimeSearchMatch);
        Then("^SP I am able to see \"([^\"]*)\" institution in the results for HS school$", globalSearch :: verifyHSSchoolResults);
        Then("^SP I verify High School client details with following data$",globalSearch::verifyHighSchoolDetails);
        Then("^SP I verify the real-time results return for global search are full match \"([^\"]*)\"$", globalSearch::verifyRealTimeSearch);
        Then("^SP I verify search tab layouts are displayed correctly \"([^\"]*)\"$", globalSearch::verifySearchResultsLayout);
    }
}
