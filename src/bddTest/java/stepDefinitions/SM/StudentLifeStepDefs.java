package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;
import pageObjects.SM.studentLife.StudentLifeImpl;

public class StudentLifeStepDefs implements En {

    public StudentLifeStepDefs() {

        SearchPageImpl searchPageObj = new SearchPageImpl();
        StudentLifeImpl studentLifeObj = new StudentLifeImpl();

        Then("^SM I select the \"([^\"]*)\" fit criteria$", searchPageObj::selectFitCriteria);

        And("^SM I verify Greek Life option \"([^\"]*)\"$", studentLifeObj::verifyGreekLife);

        And("^SM I pick \"([^\"]*)\" from the \"([^\"]*)\" dropdown in Student Life fit criteria$", studentLifeObj::pickOptionFromDropdownInStudentLife);

        And("^I click the dropdown \"([^\"]*)\"$", studentLifeObj::clickDropdown);

        Then("^I verify that the options list \"([^\"]*)\" matches the list in \"([^\"]*)\"$", studentLifeObj::verifyListMatchesList);

        Then("^I select the option \"([^\"]*)\" from the list \"([^\"]*)\"$", studentLifeObj::selectOptionFromList);

        Then("^SM I verify that the option \"([^\"]*)\" was added to the dropdown field$", studentLifeObj::verifyAddedOption);

        And("^SM I remove the option \"([^\"]*)\" from the dropdown field$", studentLifeObj::removeOptionFromDropdownField);

        Then("^SM I verify that (\\d+) items are displayed in the dropdown field$", studentLifeObj::verifyNumberOfAddedOptions);

    }
}
