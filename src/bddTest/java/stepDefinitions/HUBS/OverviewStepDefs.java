package stepDefinitions.HUBS;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HUBS.*;

import java.util.HashMap;

public class OverviewStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public OverviewStepDefs() {
        OverviewPageImpl overviewPage = new OverviewPageImpl();
        OverviewEditPageImpl overviewEditPage = new OverviewEditPageImpl();

        Then("^HUBS All the elements of the overview tab should be displayed$", overviewPage::verifyAllElementsDisplayed);

        And("^HUBS I take note of the values from the following fields in Overview:$", (DataTable stringsDataTable) -> {
            originalValues = overviewPage.getValuesFromFields(stringsDataTable.asLists(String.class));
        });

        And("^HUBS I edit all the fields in Overview based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            overviewEditPage.editAllFieldsBasedOnGatheredValues(stringsDataTable, originalValues);
        });
    }

}