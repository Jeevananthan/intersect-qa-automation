package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.EditMenuPageImpl;

public class EditMenuStepDefs implements En{

    public EditMenuStepDefs() {
        EditMenuPageImpl editMenu = new EditMenuPageImpl();

        And("^HUBS I open \"([^\"]*)\" in the edit menu$", editMenu::clickEditMenuButton);

        Then("^HUBS I verify the Terms of Service page$", editMenu::verifyTermsOfService);
    }

}
