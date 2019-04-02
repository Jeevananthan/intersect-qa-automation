package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java8.En;
import pageObjects.SM.collegesImApplyingTo.CollegesImApplyingToPageImpl;
import pageObjects.SM.collegesImThinkingAbout.CollegesImThinkingAboutPageImpl;

public class CollegesIamApplyingToStepDefs implements En {


    public CollegesIamApplyingToStepDefs() {
        CollegesImApplyingToPageImpl collegesIamApplyingToStepDefs = new CollegesImApplyingToPageImpl();

        And("^SM I add \"([^\"]*)\" to I'm applying list$", collegesIamApplyingToStepDefs::addCollegeToImApplyingTo);

        And("^SM I click \"([^\"]*)\" from the More menu for \"([^\"]*)\"$", collegesIamApplyingToStepDefs::clickMoreMenuElement);

        Then("^SM I verify that \"([^\"]*)\" is displayed in the More menu for \"([^\"]*)\"$", collegesIamApplyingToStepDefs::verifyItemInMoreMenu);

        And("^SM I remove \"([^\"]*)\" if it is in the Colleges I'm applying to list$", collegesIamApplyingToStepDefs::removeCollegeFromList);

    }
}
