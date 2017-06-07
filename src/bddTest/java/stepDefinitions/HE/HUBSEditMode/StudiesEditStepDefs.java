package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.StudiesEditPageImpl;

public class StudiesEditStepDefs implements En{

    public StudiesEditStepDefs() {
        StudiesEditPageImpl studiesEdit = new StudiesEditPageImpl();

        And("^HUBS I should be able to edit the following fields in real time:", studiesEdit::verifyFieldsInRealTime);
    }

}
