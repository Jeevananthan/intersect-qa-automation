package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.diversity.DiversityPageImpl;

public class DiversityStepDefs implements En {

    public DiversityStepDefs() {

        DiversityPageImpl diversityPage = new DiversityPageImpl();

        Then("^I verify that the default text in \"([^\"]*)\" is \"([^\"]*)\"$", diversityPage::verifyDefaultTextInElement);

        Then("^SM I verify that the \"([^\"]*)\" radio button is \"([^\"]*)\"$", diversityPage::verifyRadioButtonIsSelected);

    }
}
