package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.StudentLifeEditPageImpl;
import pageObjects.HUBS.StudentLifePageImpl;

import java.util.HashMap;

public class StudentLifeStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public StudentLifeStepDefs() {
        StudentLifePageImpl studentLifePage = new StudentLifePageImpl();
        StudentLifeEditPageImpl studentLifeEditPage = new StudentLifeEditPageImpl();

        Then("^HUBS All the elements of the student life tab should be displayed$", studentLifePage::verifyAllElementsDisplayed);

        And("^HUBS I take note of the values from the following fields in Student Life:$", (DataTable stringsDataTable) -> {
            originalValues = studentLifePage.getValuesFromFields(stringsDataTable.asLists(String.class));
        });

        And("^HUBS I edit all the fields in Student Life based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            studentLifeEditPage.editAllFieldsBasedOnGatheredValues(stringsDataTable, originalValues);
        });

    }

}