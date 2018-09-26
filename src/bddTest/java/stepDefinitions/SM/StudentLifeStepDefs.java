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

    }
}
