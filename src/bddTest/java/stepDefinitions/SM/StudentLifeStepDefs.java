package stepDefinitions.SM;

import cucumber.api.PendingException;
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

        Then("^SM I verify that the default text in the \"([^\"]*)\" dropdown is \"([^\"]*)\" in Student Life fit criteria$", studentLifeObj::verifyDefaultTextInDropdown);

        And("^I click the dropdown \"([^\"]*)\"$", studentLifeObj::clickDropdown);

        Then("^I verify that the options list \"([^\"]*)\" matches the list in \"([^\"]*)\"$", studentLifeObj::verifyListMatchesList);

        Then("^I select the option \"([^\"]*)\" from the list \"([^\"]*)\"$", studentLifeObj::selectOptionFromList);

        Then("^SM I verify that the option \"([^\"]*)\" was added to the Organizations and Clubs dropdown$", studentLifeObj::verifyAddedOptionInOrgsAndClubs);

        Then("^SM I verify that \"([^\"]*)\" items are displayed in the Organizations and Clubs dropdown$", studentLifeObj::verifyNumberOfAddedOptionsInOrgsAndClubs);

        And("^SM I remove the option \"([^\"]*)\" from the Organizations and Clubs text field$", studentLifeObj::removeOptionFromOrgAndClubs);

    }
}
