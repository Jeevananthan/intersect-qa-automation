package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.searchPage.SearchPageImpl;
import pageObjects.SM.studentLife.StudentLifeImpl;
import pageObjects.SM.surveyPage.SurveyPageImpl;

public class StudentLifeStepDefs implements En {

    public StudentLifeStepDefs() {

        SearchPageImpl searchPageObj = new SearchPageImpl();
        StudentLifeImpl studentLifeObj = new StudentLifeImpl();

        Then("^SM I select the \"([^\"]*)\" fit criteria$", searchPageObj::selectFitCriteria);

        And("^SM I verify Greek Life option \"([^\"]*)\"$", studentLifeObj::verifyGreekLife);

    }
}
