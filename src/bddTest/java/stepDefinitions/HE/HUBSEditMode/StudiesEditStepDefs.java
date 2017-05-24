package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.HEMPreviewPageImpl;
import pageObjects.HE.HUBSEditMode.NavianceCollegeProfilePageImpl;
import pageObjects.HE.HUBSEditMode.StudiesEditPageImpl;
import pageObjects.HE.HUBSEditMode.StudiesPageImpl;

import java.util.List;

public class StudiesEditStepDefs implements En{

    public StudiesEditStepDefs() {
        StudiesEditPageImpl studiesEdit = new StudiesEditPageImpl();

        And("^HUBS I should be able to edit the following fields in real time:", (DataTable stringsDataTable) -> {
            List<String> fieldsList = stringsDataTable.asList(String.class);
            studiesEdit.verifyFieldsInRealTime(fieldsList);
        });
    }

}
