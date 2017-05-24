package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.HEMPreviewPageImpl;
import pageObjects.HE.HUBSEditMode.StudiesEditPageImpl;
import pageObjects.HE.HUBSEditMode.StudiesPageImpl;

import java.util.HashMap;
import java.util.List;

public class StudiesStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();
    private HashMap<String, String> newValues = new HashMap<>();

    public StudiesStepDefs() {
        HEMPreviewPageImpl hemPreviewPage = new HEMPreviewPageImpl();
        StudiesPageImpl studiesPage = new StudiesPageImpl();
        StudiesEditPageImpl studiesEditPage = new StudiesEditPageImpl();


        And("^HUBS I open the \"([^\"]*)\" tab in the preview$", hemPreviewPage::clickMenuButton);

        Then("^HUBS All the elements of the studies tab should be displayed$", studiesPage::verifyAllElementsDisplayed);

        And("^HUBS I take note of the values from the following fields:$", (DataTable stringsDataTable) -> {
            List<String> fieldsList = stringsDataTable.asList(String.class);
            originalValues = studiesPage.getValuesFromFields(fieldsList);
        });

        And("^HUBS I edit all the fields based on the gathered values, with the following details:$", (DataTable stringsDataTable) -> {
            List<String> topAreasOfStudyAndStudyOptions = stringsDataTable.asList(String.class);
            newValues = studiesEditPage.generateValues(originalValues, topAreasOfStudyAndStudyOptions);
            StudiesPageImpl.generatedValues = newValues;
            studiesEditPage.editFieldValuesWithGeneratedData(newValues, topAreasOfStudyAndStudyOptions);
            studiesEditPage.clickPublishButton();
            studiesEditPage.enterPublishReasonsText("test");
            studiesEditPage.clickSubmitChangesButton();
            studiesEditPage.clickContinueEditingLink();
        });
    }

}
