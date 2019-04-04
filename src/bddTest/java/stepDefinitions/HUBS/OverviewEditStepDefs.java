package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.OverviewEditPageImpl;
import pageObjects.HUBS.StudentLifeEditPageImpl;

import java.util.HashMap;

public class OverviewEditStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public OverviewEditStepDefs() {
        OverviewEditPageImpl overviewEdit = new OverviewEditPageImpl();

        Then("^HUBS I should be able to edit the following fields for Overview in real time:$", overviewEdit::verifyFieldsInRealTime);
        Then("^HUBS I should be able to see overlay message \"([^\"]*)\"$", overviewEdit::verifyOverlayMessage);
    }
}
