package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.StudiesEditPageImpl;

public class StudiesEditStepDefs implements En{

    public StudiesEditStepDefs() {
        StudiesEditPageImpl studiesEdit = new StudiesEditPageImpl();

        And("^HUBS I should be able to edit the following fields in real time:", studiesEdit::verifyFieldsInRealTime);
    }

}
