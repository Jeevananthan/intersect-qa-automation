package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.diversity.DiversityPageImpl;

public class DiversityStepDefs implements En {

    public DiversityStepDefs() {

        DiversityPageImpl diversityPage = new DiversityPageImpl();

        Then("^I verify that the default text in \"([^\"]*)\" is \"([^\"]*)\"$", diversityPage::verifyDefaultTextInElement);

        And("^I scroll the dialog down, anchored in the element \"([^\"]*)\"$", diversityPage::scrollToBottom);

    }
}
