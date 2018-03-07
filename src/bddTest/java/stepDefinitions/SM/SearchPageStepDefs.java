package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;

public class SearchPageStepDefs implements En {

    public SearchPageStepDefs() {

        SearchPageImpl searchPage = new SearchPageImpl();

        Then("^I select the following data from the Location Fit Criteria$",searchPage::setLocationCriteria);

        And("^SM I verify the Student Body UI in Resources Dropdown$", searchPage::verifyStudentBodyUI);

        And("^SM I verify the system response when the GPA entered by the user is valid$", searchPage::verifySystemResponseWhenGPAInputIsValid);

        And("^SM I verify the system response when the GPA entered by the user is invalid$", searchPage::verifySystemResponseWhenGPAInputIsInvalid);

        And("^SM I verify that entered GPA data persists$", searchPage::verifyGPADataPersists);

        And("^SM I verify that GPA doesn't become a fit criteria in the Must Have box$", searchPage::verifyGPACriteriaNotInMustHaveBox);

    }
}
