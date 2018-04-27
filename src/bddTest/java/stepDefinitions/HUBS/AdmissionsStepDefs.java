package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HUBS.AdmissionsEditPageImpl;
import pageObjects.HUBS.AdmissionsPageImpl;

import java.util.HashMap;

public class AdmissionsStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public AdmissionsStepDefs() {
        AdmissionsPageImpl admissionsPage = new AdmissionsPageImpl();
        AdmissionsEditPageImpl admissionsEditPage = new AdmissionsEditPageImpl();

        Then("^HUBS All the elements of the admissions tab should be displayed$", admissionsPage::verifyAllElementsDisplayed);

        And("^HUBS I take note of the values from the following fields in Admissions:$", (DataTable stringsDataTable) -> {
            originalValues = admissionsPage.getValuesFromFields(stringsDataTable.asList(String.class));
        });

        And("^HUBS I edit all the fields in Admissions based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            admissionsEditPage.editAllFieldsBasedOnGatheredValues(stringsDataTable, originalValues);
        });
    }

}