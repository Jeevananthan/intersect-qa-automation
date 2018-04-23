package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HUBS.HEMPreviewPageImpl;
import pageObjects.HUBS.StudiesEditPageImpl;
import pageObjects.HUBS.StudiesPageImpl;

import java.util.HashMap;

public class StudiesStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public StudiesStepDefs() {
        HEMPreviewPageImpl hemPreviewPage = new HEMPreviewPageImpl();
        StudiesPageImpl studiesPage = new StudiesPageImpl();
        StudiesEditPageImpl studiesEditPage = new StudiesEditPageImpl();

        And("^HUBS I take note of the values from the following fields:$", (DataTable stringsDataTable) -> {
            originalValues = studiesPage.getValuesFromFields(stringsDataTable.asList(String.class));
        });

        And("^HUBS I edit all the fields based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            studiesEditPage.editAllFieldsBasedOnGatheredValues(stringsDataTable, originalValues);
        });
    }

}