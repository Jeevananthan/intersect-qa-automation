package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;

public class SearchPageStepDefs implements En {

    public SearchPageStepDefs() {

        SearchPageImpl searchPage = new SearchPageImpl();

        Then("^I select the following data from the Location Fit Criteria$",searchPage::setLocationCriteria);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Resources fit criteria$",searchPage::setResourcesCriteria);

        Then("^SM I unselect the \"([^\"]*)\" checkbox from the Resources fit criteria$",searchPage::unsetResourcesCriteria);

        And("^SM I verify that the Must Have box contains \"([^\"]*)\"$", searchPage::verifyMustHaveBoxContains);

        And("^SM I verify that the Must Have box does not contain \"([^\"]*)\"$", searchPage::verifyMustHaveBoxDoesNotContain);

        And("^SM I move \"([^\"]*)\" from the Must Have box to the Nice to Have box$", searchPage::moveToNiceToHave);

        And("^SM I verify the Student Body UI in Resources Dropdown$", searchPage::verifyStudentBodyUI);

        And("^SM I verify the system response when the ACT score entered by the user is valid$", searchPage::verifySystemResponseWhenACTScoreIsValid);

        And("^SM I verify the system response when the ACT score entered by the user is invalid$", searchPage::verifySystemResponseWhenACTScoreIsInvalid);

        And("^SM I verify that ACT score data is stored on our side$", searchPage::verifyIfACTScoreDataIsStoredOnOurSide);

        And("^SM I verify that ACT score doesn't become fit criteria in Must Have box$", searchPage::verifyACTScoreCriteriaNotInMustHaveBox);

    }
}
