package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.collegesImThinkingAbout.CollegesImThinkingAboutPageImpl;

public class CollegesImThinkingAboutStepDefs implements En {

    public CollegesImThinkingAboutStepDefs() {

        CollegesImThinkingAboutPageImpl collegesImThinkingAboutPage = new CollegesImThinkingAboutPageImpl();

        And("^SM I remove \"([^\"]*)\" from the I'm thinking about list if it is added in the list$", collegesImThinkingAboutPage::removeCollegeFromImThinkingAboutList);

    }
}
