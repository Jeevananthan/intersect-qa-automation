package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.InternationalEditPageImpl;
import pageObjects.HUBS.InternationalPageImpl;

import java.util.HashMap;

public class InternationalStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public InternationalStepDefs() {
        InternationalPageImpl intPage = new InternationalPageImpl();
        InternationalEditPageImpl intEditPage = new InternationalEditPageImpl();

        Then("^HUBS All the elements of the international tab should be displayed$", intPage::verifyAllElementsDisplayed);

        And("^HUBS I take note of the values from the following fields in International:$", (DataTable stringsDataTable) -> {
            originalValues = intPage.getValuesFromFields(stringsDataTable.asLists(String.class));
        });

        And("^HUBS I edit all the fields in International based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            intEditPage.editAllFieldsBasedOnGatheredValues(stringsDataTable, originalValues);
        });
    }

}