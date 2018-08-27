package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.EditMenuPageImpl;

public class EditMenuStepDefs implements En{

    public EditMenuStepDefs() {
        EditMenuPageImpl editMenu = new EditMenuPageImpl();

        And("^HUBS I open \"([^\"]*)\" in the edit menu$", editMenu::clickEditMenuButton);

        Then("^HUBS I verify the Terms of Service page$", editMenu::verifyTermsOfService);

        Then("^HUBS I verify the error message \"([^\"]*)\" in the GPA textbox, with the following data:$", editMenu::verifyGPAValidations);

        And("^HUBS I set the GPA values as follows:$", editMenu::setGPAValues);

        Then("^HUBS I verify that the error message \"([^\"]*)\" is displayed for \"([^\"]*)\"$", editMenu::verifyErrorMessageForTextBox);

        Then("^HUBS I verify that the following error messages:$", editMenu::verifyErrorMessages);
    }

}
