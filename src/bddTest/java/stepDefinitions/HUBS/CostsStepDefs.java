package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HUBS.*;

import java.util.HashMap;

public class CostsStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public CostsStepDefs() {
        CostsPageImpl costsPage = new CostsPageImpl();
        CostsEditPageImpl costsEditPage = new CostsEditPageImpl();

        Then("^HUBS All the elements of the costs tab should be displayed$", costsPage::verifyAllElementsDisplayed);

        And("^HUBS I take note of the values from the following fields in Costs:$", (DataTable stringsDataTable) -> {
            originalValues = costsPage.getValuesFromFields(stringsDataTable.asLists(String.class));
        });
        And("^HUBS I edit all the fields in Costs based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            costsEditPage.editAllFieldsBasedOnGatheredValues(stringsDataTable, originalValues);
        });
    }

}