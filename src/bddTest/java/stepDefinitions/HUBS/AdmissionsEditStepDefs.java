package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.AdmissionsEditPageImpl;

public class AdmissionsEditStepDefs implements En{

    public AdmissionsEditStepDefs() {
        AdmissionsEditPageImpl admissionsEdit = new AdmissionsEditPageImpl();

        Then("^HUBS I should be able to edit the following fields for Admissions in real time:$", admissionsEdit::verifyFieldsInRealTime);

        Then("^HUBS I verify an error message when non valid data is used in Admissions:$", admissionsEdit::verifyErrorMessageWithInvalidData);
    }

}
