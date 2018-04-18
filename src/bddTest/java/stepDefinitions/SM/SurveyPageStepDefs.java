package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.surveyPage.SurveyPageImpl;

public class SurveyPageStepDefs implements En {

    public SurveyPageStepDefs() {

        SurveyPageImpl surveyPage = new SurveyPageImpl();

        Then("^SM I verify that a survey is opened after clicking the Provide Feedback button$", surveyPage::verifySurvey);

        Then("^SM I verify that the survey URL is \"([^\"]*)\"$", surveyPage::verifySurveyURL);

        And("^SM I close the survey$", surveyPage::closeSurvey);

        Then("^SM I open the survey using the Provide Feedback button$", surveyPage::openSurvey);
    }
}
