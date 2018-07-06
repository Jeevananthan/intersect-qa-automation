package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;

public class SearchPageStepDefs implements En {

    public SearchPageStepDefs() {

        SearchPageImpl searchPage = new SearchPageImpl();

        Then("^I select the following data from the Location Fit Criteria$",searchPage::setLocationCriteria);

        Then("^I select the following data from the Diversity Fit Criteria$",searchPage::setDiversityCriteria);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Resources fit criteria$",searchPage::setResourcesCriteria);

        Then("^SM I verify that the \"([^\"]*)\" checkbox from the Resources fit criteria is \"([^\"]*)\"$",searchPage::verifyResourcesCriteria);

        Then("^SM I unselect the \"([^\"]*)\" checkbox from the Resources fit criteria$",searchPage::unsetResourcesCriteria);

        And("^SM I verify that the Must Have box contains \"([^\"]*)\"$", searchPage::verifyMustHaveBoxContains);

        And("^SM I verify that the Must Have box does not contain \"([^\"]*)\"$", searchPage::verifyMustHaveBoxDoesNotContain);

        And("^SM I verify that the Nice to Have box contains \"([^\"]*)\"$", searchPage::verifyNiceToHaveBoxContains);

        And("^SM I verify that Nice to Have box does not contain \"([^\"]*)\"$", searchPage::verifyNiceToHaveBoxDoesNotContain);

        And("^SM I move \"([^\"]*)\" from the Must Have box to the Nice to Have box$", searchPage::moveToNiceToHave);

        And("^SM I move \"([^\"]*)\" from the Nice to Have box to the Must Have box$", searchPage::moveToMustHave);

        And("^SM I remove the \"([^\"]*)\" fit criteria from the Must Have box or Nice to Have box$", searchPage::removeFitCriteria);

        And("^SM I verify the Student Body UI in Resources Dropdown$", searchPage::verifyStudentBodyUI);

        And("^SM I verify the system response when the SAT score entered by the user is valid$", searchPage::verifySystemResponseWhenSATScoreInputIsValid);

        And("^SM I verify the system response when the SAT score entered by the user is invalid$", searchPage::verifySystemResponseWhenSATScoreInputIsInvalid);

        And("^SM I verify that SAT score persists when changing fit criteria$", searchPage::verifyIfSATScoreDataIsStoredOnOurSide);

        And("^SM I verify that entered GPA data persists$", searchPage::verifyGPADataPersists);

        Then("^SM I verify the system response when the GPA entered by the user is valid$",searchPage::verifySystemResponseWhenGPAInputIsValid);

        Then("^SM I verify the system response when the GPA entered by the user is invalid$",searchPage::verifySystemResponseWhenGPAInputIsInvalid);

        And("^SM I verify the system response when the ACT score entered by the user is valid$", searchPage::verifySystemResponseWhenACTScoreIsValid);

        And("^SM I verify the system response when the ACT score entered by the user is invalid$", searchPage::verifySystemResponseWhenACTScoreIsInvalid);

        And("^SM I verify that entered ACT score data persists$", searchPage::verifyACTScoreDataPersists);

        And("^SM I verify if dark blue header is present$", searchPage::verifyDarkBlueHeaderIsPresent);

        And("^SM I verify if Your Fit Criteria text is present$", searchPage::verifyYourFitCriteriaTextIsPresent);

        And("^SM I verify the Choose Fit Criteria bar$", searchPage::verifyChooseFitCriteriaBar);

        And("^SM I verify Select Criteria to Start button and instructional text$", searchPage::verifySelectCriteriaButtonAndInstructionalText);

        And("^SM I verify Must Have and Nice to Have boxes$", searchPage::verifyMustHaveAndNiceToHaveBoxes);

        And("^SM I verify the empty results table$", searchPage::verifyEmptyResultsTable);

        And("^SM I verify the dark blue footer$", searchPage::verifyDarkBlueFooter);

        Then("^SM I select the \"([^\"]*)\" radio button from the Academics fit criteria",searchPage::selectRadioButtonInAcademicsFitCriteria);

        Then("^SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type$", searchPage::selectMajorsFromSearchMajorsComboBoxForBachelorsDegreeType);

        Then("^SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type$", searchPage::selectMinorsFromSearchMinorsComboBoxForBachelorsDegreeType);

        Then("^SM I unselect the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type$", searchPage::unselectMajorsFromSearchMajorsComboBoxForBachelorsDegreeType);

        Then("^SM I unselect the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type$", searchPage::unselectMinorsFromSearchMinorsComboBoxForBachelorsDegreeType);

        Then ("SM I click \"([^\"]*)\" filter criteria tab", searchPage::chooseFitCriteriaTab);

        Then("^SM I see validation message \"([^\"]*)\"$", (searchPage::checkValidationMessageIsVisible));

        Then("^SM I verify \"([^\"]*)\" checkbox in Cost fit criteria$", searchPage::verifyMeets100ofNeedCheckbox);

        Then("^SM I unselect the \"([^\"]*)\" checkbox from the \"([^\"]*)\" fit criteria$", searchPage::unselectCheckbox);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Cost fit criteria$", searchPage::selectMeest100ofNeedCheckbox);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Institution Characteristics fit criteria$", searchPage::selectStudentSuccessFitCriteriaCheckbox);

        And("^SM I verify \"([^\"]*)\" checkbox from the Institution Characteristics fit criteria$", searchPage::verifyStudentSuccessFitCriteriaCheckbox);

        Then("^SM I unselect the \"([^\"]*)\" checkbox from the Institution Characteristics fit criteria$", searchPage::unselectStudentSuccessFitCriteriaCheckbox);

        Then("^SM I verify the widths of the three boxes$", searchPage::verifyWidthsOfThreeBoxes);

        And("^I select the following data from the Admission Fit Criteria$", searchPage::setAdmissionCriteria);

        Then("^SM I verify each fit category in the Choose Fit Criteria header bar is clickable and match the color$", searchPage::verifyEachFitCriteria);

        Then("^SM I verify clicking outside of the box will also close the box$", searchPage::checkOutsideClick);

        And("^SM I check both Select Criteria To Start buttons take the user to the Location dropdown$", searchPage::checkSelectCriteriaToStartButtonsRedirectsLocation);

        Then("^SM I \"([^\"]*)\" the \"([^\"]*)\" checkbox from the Diversity$",searchPage::selectOrUnselectDiversityCheckbox);

        Then("^SM I select the \"([^\"]*)\" checkbox from \"([^\"]*)\" fit criteria$", searchPage::selectCheckBox);

        And("^SM I verify \"([^\"]*)\" checkbox from the \"([^\"]*)\" fit criteria$", searchPage::verifyAdmissionFitCriteriaCheckbox);

        Then("^SM I click on Institution Characteristics fit criteria$", searchPage::getInstitutionCharacteristicsFC);

        Then("^SM I check the selection and deselection and Must Have box functionality for Average Class Size drop down list$",
                searchPage::verifyAverageClassSizeList);
        Then("^SM I check when Average Class Size filter is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box$",
                searchPage::verifyMAndNSyncWithAverageClassSizeFilter);
        And("^SM I verify the default column headers displayed in the results table$", searchPage::verifyDefaultColumnHeadersInResultsTable);

        And("^SM I verify if the option selected or defaulted in column header can be changed to \"([^\"]*)\"$", searchPage::verifyIfOptionDefaultedInColumnHeaderCanBeChanged);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Diversity fit criteria$", searchPage::selectHighInternationalPopulationCheckbox);

        Then("^SM I verify \"([^\"]*)\" checkbox in Diversity fit criteria$", searchPage::verifyHighInternationalPopulationCheckbox);

        Then("^SM I verify the Average Class Size text under Institution Characteristics in the results list is correct$", searchPage::verifyAverageClassSizeTextInResults);

        Then("^SM I verify that the Save Search popup is closed when I use the Cancel action$", searchPage::verifySaveSearchIsClosedWhenCancelIsClicked);

        Then("^SM I verify that the Save Search popup is closed when I click outside the popup$", searchPage::verifySaveSearchIsClosedWithOutterClick);

        Then("^SM I verify that the text inside the Save Search popup is correct$", searchPage::verifyTextInsideSaveSearchBox);

        Then("^SM I verify the error message for more than \"([^\"]*)\" characters$", searchPage::verifyErrorMessageforXCharacters);

        And("^SM I save the search with the name \"([^\"]*)\"$", searchPage::saveSearchWithName);

        And("^SM I verify the error message for less than \"([^\"]*)\" characters$", searchPage::verifyErrorMessageforXCharacters);

        Then("^SM I verify the confirmation message$", searchPage::verifyConfirmationMessage);

        Then("^SM I check Diversity column in result colleges for \"([^\"]*)\"$", searchPage::checkDiversityColumnInResult);

        Then("^SM I verify that the column headers in the results table are the following:$", searchPage::verifyColumnHeaders);

        Then("^SM I should see at the bottom the search by college name text box with default text \"([^\"]*)\"$", searchPage::verifySearchByCollegeNameTextBox);

        Then("^SM I search by college name using \"([^\"]*)\"$", searchPage::searchCollegeByName);

        Then("^SM I see a message at the top of the results box that says \"([^\"]*)\"$", searchPage::verifyTextInSearchByCollegeNameResults);

        And("^SM I verify \"([^\"]*)\" results were displayed when searching by college name$", searchPage::verifyAmountOfResultsWhenSearchingByCollegeName);

        And("^SM I see a message in the search by college name text box that says \"([^\"]*)\"$", searchPage::verifySearchByCollegeNameNoResultFoundMessage);

        And("^SM I verify that tooltip icon is added to the include online learning opportunities fit criteria$", searchPage::verifyOnlineLearningOpportunitiesTooltipIcon);

    }
}
