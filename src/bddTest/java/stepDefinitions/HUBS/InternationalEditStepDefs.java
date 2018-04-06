package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.InternationalEditPageImpl;

import java.util.HashMap;

public class InternationalEditStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public InternationalEditStepDefs() {
        InternationalEditPageImpl internationalEdit = new InternationalEditPageImpl();

        Then("^HUBS I should be able to edit the following fields for International in real time:$", internationalEdit::verifyFieldsInRealTime);
    }

}
