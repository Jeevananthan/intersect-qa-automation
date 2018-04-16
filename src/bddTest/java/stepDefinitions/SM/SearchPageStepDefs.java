package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;

public class SearchPageStepDefs implements En {

    public SearchPageStepDefs() {

        SearchPageImpl searchPage = new SearchPageImpl();

        Then("^I select the following data from the Location Fit Criteria$",searchPage::setLocationCriteria);

        And("^SM I verify the Student Body UI in Resources Dropdown$", searchPage::verifyStudentBodyUI);

        And("^SM I verify the system response when the SAT score entered by the user is valid$", searchPage::verifySystemResponseWhenSATScoreInputIsValid);

        And("^SM I verify the system response when the SAT score entered by the user is invalid$", searchPage::verifySystemResponseWhenSATScoreInputIsInvalid);

        And("^SM I verify that SAT score persists when changing fit criteria$", searchPage::verifyIfSATScoreDataIsStoredOnOurSide);

        And("^SM I verify that SAT score doesn't become fit criteria in Must Have box$", searchPage::verifySATScoreCriteriaNotInMustHaveBox);

    }
}
