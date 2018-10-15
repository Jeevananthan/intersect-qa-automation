package stepDefinitions.SM;

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

        Then("^SM I select the \"([^\"]*)\" checkbox from the \"([^\"]*)\" fit criteria$", searchPage::selectCheckBox);

        And("^SM I verify the \"([^\"]*)\" checkbox from the \"([^\"]*)\" fit criteria$", searchPage::verifyAdmissionFitCriteriaCheckbox);

        Then("^SM I select the \"([^\"]*)\" from the \"([^\"]*)\" fit criteria not closing the tab$", searchPage::selectCheckBoxNotClosingTab);

        Then("^SM I click on Institution Characteristics fit criteria$", searchPage::getInstitutionCharacteristicsFC);

        Then("^SM I check the selection and deselection and Must Have box functionality for Average Class Size drop down list$",
                searchPage::verifyAverageClassSizeList);
        Then("^SM I check when Average Class Size filter is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box$",
                searchPage::verifyMAndNSyncWithAverageClassSizeFilter);
        And("^SM I verify the default column headers displayed in the results table$", searchPage::verifyDefaultColumnHeadersInResultsTable);

        And("^SM I verify if the option selected or defaulted in column header can be changed to \"([^\"]*)\"$", searchPage::verifyIfOptionDefaultedInColumnHeaderCanBeChanged);

        Then("^SM I select the \"([^\"]*)\" checkbox from the Diversity fit criteria$", searchPage::selectDiversityCheckbox);

        Then("^SM I verify \"([^\"]*)\" checkbox in Diversity fit criteria$", searchPage::verifyHighInternationalPopulationCheckbox);

        Then("^SM I verify the Average Class Size text under Institution Characteristics in the results list is correct$", searchPage::verifyAverageClassSizeTextInResults);

        Then("^SM I verify the College Profile page for \"([^\"]*)\" in the following sections:$", searchPage::verifyCollegeProfile);

        And("^SM I open the Pinned Schools Compare screen$", searchPage::openPinnedCompareSchools);

        Then("^SM I verify that the Save Search popup is closed when I use the Cancel action$", searchPage::verifySaveSearchIsClosedWhenCancelIsClicked);

        Then("^SM I verify that the Save Search popup is closed when I click outside the popup$", searchPage::verifySaveSearchIsClosedWithOutterClick);

        Then("^SM I verify that the text inside the Save Search popup is correct$", searchPage::verifyTextInsideSaveSearchBox);

        Then("^SM I verify the error message for more than \"([^\"]*)\" characters$", searchPage::verifyErrorMessageforXCharacters);

        And("^SM I save the search with the name \"([^\"]*)\"$", searchPage::saveSearchWithName);

        And("^SM I verify the error message for less than \"([^\"]*)\" characters$", searchPage::verifyErrorMessageforXCharacters);

        Then("^SM I verify the confirmation message$", searchPage::verifyConfirmationMessage);

        Then("^SM I check Diversity column in result colleges for \"([^\"]*)\"$", searchPage::checkDiversityColumnInResult);

        Then("^SM I verify that \"([^\"]*)\" displays \"([^\"]*)\" in the Cost column$", searchPage::verifySingleValueInCostColumn);

        Then("^SM I verify that screen jumps to the top of the page after clicking the Back to top button$", searchPage::verifyBackToTopButtonFunctionality);

        Then("^SM I verify the footnote for no GPA and no other scores, with the text:$", searchPage::verifyFootnoteNoGPANoScores);

        Then("^SM I verify the footnote for known GPA but unknown test scores for \"([^\"]*)\", with the text:$", searchPage::verifyFootnoteGPANoScores);

        And("^SM I pin \"([^\"]*)\"$", searchPage::pinCollege);

        And("^SM I pin \"([^\"]*)\" if it is not pinned already$", searchPage::pinCollegeIfNotPinnedAlready);

        Then("^SM I verify that the column headers in the results table are the following:$", searchPage::verifyColumnHeaders);

        Then("^SM I should see at the bottom the search by college name text box with default text \"([^\"]*)\"$", searchPage::verifySearchByCollegeNameTextBox);

        Then("^SM I search by college name using \"([^\"]*)\"$", searchPage::searchCollegeByName);

        Then("^SM I see a message at the top of the results box that says \"([^\"]*)\"$", searchPage::verifyTextInSearchByCollegeNameResults);

        And("^SM I verify \"([^\"]*)\" results were displayed when searching by college name$", searchPage::verifyAmountOfResultsWhenSearchingByCollegeName);

        And("^SM I see a message in the search by college name text box that says \"([^\"]*)\"$", searchPage::verifySearchByCollegeNameNoResultFoundMessage);

        And("^SM I verify that tooltip icon is added to the include online learning opportunities fit criteria$", searchPage::verifyOnlineLearningOpportunitiesTooltipIcon);

        And("^SM I reload the page$", searchPage::reloadPage);

        And("^SM I verify the text displayed in the % Male vs. Female Fit Criteria$", searchPage::verifyTextDisplayedInMaleVsFemaleFitCriteria);

        And("^SM I verify the placeholders displayed in the Select % dropdown and Select gender dropdown$", searchPage::verifyPlaceholdersInSelectPercentAndSelectGenderDropdown);

        And("^SM I verify the options displayed in the Select % dropdown$", searchPage::verifyOptionsInSelectPercentDropdown);

        And("^SM I verify the options displayed in the Select Gender dropdown$", searchPage::verifyOptionsInSelectGenderDropdown);

        And("^SM I start the search over$", searchPage::startSearchOver);

        And("^SM I pin \"([^\"]*)\" colleges$", searchPage::iPinColleges);

        And("^SM I clear pinned schools list$", searchPage::verifyPinnedCollegesClearedWhenYesClearButtonIsClicked);

        And("^SM I verify the error message displayed on pinning the 26th college$", searchPage::verifyErrorMessageDisplayedOnPinning26thCollege);

        And("^SM I select the following data in the Cost Fit Criteria$", searchPage::setCostCriteria);

        And("^SM I verify the following data in the Cost Fit Criteria$", searchPage::verifyDataInCostCriteria);

        Then("^SM I select the \"([^\"]*)\" checkbox from \"([^\"]*)\" fit criteria$", searchPage::selectCheckBox);

        And("SM I verify that \"([^\"]*)\" checkbox is \"([^\"]*)\" in \"([^\"]*)\" fit criteria", searchPage::verifyCheckboxState);

        Then("^SM I clear all pills from Must have  and Nice to have boxes$", searchPage::clearAllPillsFromMustHaveAndNiceToHaveBox);

        And("^SM I verify that checkBox with text \"([^\"]*)\" is displayed$", searchPage::verifyCheckboxIsDisplayed);

        And("^SM I verify that checkBox with text \"([^\"]*)\" is not checked$", searchPage::verifyCheckboxIsNotChecked);

        And("^SM I verify that checkBox with text \"([^\"]*)\" can be checked|unchecked$", searchPage::verifyCheckboxCanBeCheckedAndUnchecked);

        And("^SM I press button \"([^\"]*)\"$", searchPage::pressButton);

        And("^SM I pick \"([^\"]*)\" from the dropdown \"([^\"]*)\"$", searchPage::pickFromDropdown);

        And("^SM I press Why button for the first college in results with score (\\d+)%$", searchPage::pressWhyForCollegeWithScore);

        And("^SM I press Why button for \"([^\"]*)\" college$", searchPage::pressWhyButtonForCollege);

        Then("^SM I scroll to the middle of the main page$", searchPage::scrollToMiddleOfMainPage);

        Then("^SM I verify scrollbar is positioned at the top of the Pinned Schools Compare page$", searchPage::verifyScrollBarIsPositionedAtTheTopOfPinnedSchoolsComparePage);

        And("^SM I verify that CLEAR PINNED LIST option is clickable$", searchPage::verifyCLEARPINNEDLISTOptionIsClickable);

        And("^SM I verify the CLEAR PINNED LIST confirmation modal$", searchPage::verifyCLEARPINNEDLISTConfirmationModal);

        And("^SM I verify that the pinned colleges are not cleared when the NO CANCEL button is clicked in the modal$", searchPage::verifyPinnedCollegesNotClearedWhenNOCANCELbuttonIsClicked);

        And("^SM I verify that the pinned colleges are cleared when the the YES, CLEAR MY LIST button is clicked in the modal$",searchPage::verifyPinnedCollegesClearedWhenYesClearButtonIsClicked);

        And("^SM I verify that the CLEAR PINNED LIST option is disabled$", searchPage::verifyCLEARPINNEDLISTIsDisabled);

        Then("^SM I verify if the validation message displayed for Zip Code field is user friendly$", searchPage::verifyZipCodeValidationMessage);

        Then("^SM I pick the date \"([^\"]*)\" from the date picker$", searchPage::pickDateInDatePickerSM);

        Then ("^SM I click clear calendar icon$", searchPage::clickClearCalendarIcon);

        And("^SM I clean GPA/SAT/ACT scores$", searchPage::clearGPASATACTScores);

        Then("^SM I send text \"([^\"]*)\" to the Zip Code field$", searchPage::sendTextToZipCOdeField);

        Then("^SM I double click on PIN TO COMPARE link and check if the second click bounces off$", searchPage::verifySecondClickBouncesOffForPinToCompare);

        Then("^SM I verify that \"([^\"]*)\" message is displayed in Save Search popup$", searchPage::verifyMessageInSaveSearchPopup);

        Then("^SM I verify the radio buttons displayed in the Cost fit criteria$", searchPage::verifyRadioButtonsDisplayedInCostFitCriteria);

        Then("^SM I verify that the below options are displayed in Maximum Cost dropdown$", searchPage::verifyOptionsDisplayedInMaximumCostDropdown);

        Then("^SM I verify the Home State dropdown in Cost fit criteria$", searchPage::verifyHomeStateDropdownInCostCriteria);

        Then("^SM I verify that the below options are displayed in Family Income dropdown$", searchPage::verifyOptionsDisplayedInFamilyIncomeDropdown);

        Then("^I check there are (\\d+) icons \"([^\"]*)\" are displayed$", searchPage::checkNumberOfElementsDisplayed);

        Then("^SM I verify if the GPA and test scores revert to those stored in naviance student profile when Start Over action is performed$", searchPage::onStartOverVerifyIfGPAAndTestScoresRevertToValuesStoredInNavianceStudentProfile);

        Then("^SM I verify the pinned college count is \"([^\"]*)\" in footer$", searchPage::verifyPinnedCollegeCountInFooter);

        Then("^SM I verify if the GPA and test scores are not reverted to those stored in naviance student profile when page is refreshed$", searchPage::onPageRefreshVerifyIfGPAAndTestScoresDoNotRevertToValuesStoredInNavianceStudentProfile);

        Then("^SM I verify that COMPARE PINNED COLLEGES is not clickable$", searchPage::verifyComparePinnedCollegesOptionIsNotClickable);

        Then("^SM I verify the header text in Compare Pinned Colleges page$", searchPage::verifyHeaderTextinComparePinnedCollegesPage);

        Then("^SM I click on the Back button in Compare Pinned Colleges page$", searchPage::clickOnBackButtonInComparePinnedCollegesPage);

        Then("^I check number of records in the \"([^\"]*)\" table$", searchPage::getCurrentNumberOfTableRows);

        Then("^I check that table \"([^\"]*)\"  has one more row$", searchPage::checkTableHasOneMoreRow);
      
        Then("^SM I verify that the text from \"([^\"]*)\" is displayed in Your Fit Criteria screen$", searchPage::verifyTextIsPresentInFitCriteria);

        Then("^SM I verify that the button Select Criteria To Start is not displayed in the Your Fit Criteria screen$", searchPage::verifySelectCriteriaButtonNotPresent);

        Then("^SM I navigate to page via URL path \"([^\"]*)\"$", searchPage::navigateToPageViaURLPath);

        Then("^SM I verify that the pagination text displayed in Compare Pinned Colleges page is \"([^\"]*)\"$", searchPage::verifyPaginationTextInComparePinnedCollegesPage);

        Then("^SM I verify that the left pagination button is \"([^\"]*)\" and the right pagination button is \"([^\"]*)\" in Compare Pinned Colleges page$", searchPage::verifyPaginationButtonsAreEnabledOrDisabledInCpmparePinnedCollegesPage);

        And("^SM I verify that \"([^\"]*)\" is displayed in the \"([^\"]*)\" box in the Why Drawer$", searchPage::verifyTextInBoxInWhyDrawer);

        Then("^SM I verify that a PINNED dropdown is present in the footer$", searchPage::verifyPINNEDDropdownISPresentInFooter);

        Then("^SM I verify the following options are displayed in the PINNED dropdown$", searchPage::verifyFollowingOptionsDisplayedInPinnedDropdown);

        Then("^SM I verify that a pink circle is displayed next to the pinned dropdown$", searchPage::verifyPinkCircleIsDisplayedNextToThePinnedDropdown);

        Then("^SM I verify that the college \"([^\"]*)\" is displayed in position \"([^\"]*)\" in the results table$", searchPage::verifyCollegePosition);

        Then("^SM I verify that the college in position \"([^\"]*)\" contains \"([^\"]*)\" in its class$", searchPage::verifyClassContentCollegePosition);

        And("^SM I select the \"([^\"]*)\" radio button in Diversity Fit Criteria$", searchPage::selectRadioButtonInDiversityFitCriteria);

        And("^SM I verify the options displayed in the Specific Representation percent listbox", searchPage::verifyOptionsInSpecificRepresentationPercentListBox);

        And("^SM I verify the options displayed in the Specific Representation race and ethnicity listbox",searchPage::verifyOptionsInRaceAndEthnicityListBox);

        And("^SM I select the option \"([^\"]*)\" in the Specific Representation percent listbox", searchPage::selectOptionInSpecificRepresentationPercentListBox);

        And("^SM I select the option \"([^\"]*)\" in the Specific Representation race and ethnicity listbox$", searchPage::selectOptionInRaceAndEthnicityListBox);

        Then("^SM I verify the text displayed in the % Out of State Students Fit Criteria$", searchPage::verifyTextDisplayedInPercentageOutOfStateStudentsFitCriteria);

        Then("^SM I verify the options displayed in Out of State students Select % dropdown$", searchPage::verifyOptionsInOutOfStateStudentsSelectPercentDropdown);

        And("^I select the radio button \"([^\"]*)\"$", searchPage::selectRadioButton);

        Then("^SM I verify that the match score for the college in position (\\d+) is \"([^\"]*)\" (\\d+)$", searchPage::verifyMatchScoreByPosition);

        And("^SM I verify that radio button with text \"([^\"]*)\" is selected$", searchPage::verifyRadioButtonIsSelected);

        Then("^SM I verify the options displayed in On-Campus Housing Select % dropdown$", searchPage::verifyOptionsInOnCampusHousingSelectPercentDropdown);

        Then("^SM I close the fit criteria selection window$", searchPage::closeFitCriteriaWindow);

        Then("^SM I \"([^\"]*)\" the \"([^\"]*)\" checkbox from \"([^\"]*)\" Fit Criteria$", searchPage::selectOrUnselectCheckboxInFitCriteria);
    }
}
