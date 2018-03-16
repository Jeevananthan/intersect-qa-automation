package stepDefinitions.SM;

import cucumber.api.PendingException;
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

        And("^SM I verify the system response when the GPA entered by the user is valid$", searchPage::verifySystemResponseWhenGPAInputIsValid);

        And("^SM I verify the system response when the GPA entered by the user is invalid$", searchPage::verifySystemResponseWhenGPAInputIsInvalid);

        And("^SM I verify that entered GPA data persists$", searchPage::verifyGPADataPersists);

        And("^SM I verify that GPA doesn't become a fit criteria in the Must Have box$", searchPage::verifyGPACriteriaNotInMustHaveBox);

        And("^SM I verify the system response when the ACT score entered by the user is valid$", searchPage::verifySystemResponseWhenACTScoreIsValid);

        And("^SM I verify the system response when the ACT score entered by the user is invalid$", searchPage::verifySystemResponseWhenACTScoreIsInvalid);

        And("^SM I verify that entered ACT score data persists$", searchPage::verifyACTScoreDataPersists);

        And("^SM I verify that ACT score doesn't become a fit criteria in the Must Have box$", searchPage::verifyACTScoreCriteriaNotInMustHaveBox);

        And("^SM I verify if dark blue header is present$", searchPage::verifyDarkBlueHeaderIsPresent);

        And("^SM I verify if Your Fit Criteria text is present$", searchPage::verifyYourFitCriteriaTextIsPresent);

        And("^SM I verify the Choose Fit Criteria bar$", searchPage::verifyChooseFitCriteriaBar);

        And("^SM I verify Select Criteria to Start button and instructional text$", searchPage::verifySelectCriteriaButtonAndInstructionalText);

        And("^SM I verify Must Have and Nice to Have boxes$", searchPage::verifyMustHaveAndNiceToHaveBoxes);

        And("^SM I verify the empty results table$", searchPage::verifyEmptyResultsTable);

        And("^SM I verify the dark blue footer$", searchPage::verifyDarkBlueFooter);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Institution Characteristics fit criteria$", searchPage::selectStudentSuccessFitCriteriaCheckbox);

        And("^SM I verify \"([^\"]*)\" checkbox from the Institution Characteristics fit criteria$", searchPage::verifyStudentSuccessFitCriteriaCheckbox);

        Then("^SM I unselect the \"([^\"]*)\" checkbox from the Institution Characteristics fit criteria$", searchPage::unselectStudentSuccessFitCriteriaCheckbox);

    }
}
