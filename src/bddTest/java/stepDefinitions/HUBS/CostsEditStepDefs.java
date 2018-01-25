package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.CostsEditPageImpl;

public class CostsEditStepDefs implements En{

    public CostsEditStepDefs() {
        CostsEditPageImpl costsEdit = new CostsEditPageImpl();

        Then("^HUBS I should be able to edit the following fields for Costs in real time:$", costsEdit::verifyFieldsInRealTime);
    }

}
