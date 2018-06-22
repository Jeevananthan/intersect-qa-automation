package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.CostsEditPageImpl;

public class CostsEditStepDefs implements En{

    public CostsEditStepDefs() {
        CostsEditPageImpl costsEdit = new CostsEditPageImpl();

        Then("^HUBS I should be able to edit the following fields for Costs in real time:$", costsEdit::verifyFieldsInRealTime);

        Then("^HUBS I verify that the following fields for Costs accept only valid values:$", costsEdit::verifyValidDataInFields);

        Then("^HUBS I verify an error message when non valid data is used in Costs:$", costsEdit::verifyErrorMessageWithInvalidData);
    }

}
