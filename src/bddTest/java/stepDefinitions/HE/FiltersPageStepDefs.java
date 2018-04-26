package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.filtersPage.FiltersPageImpl;

public class FiltersPageStepDefs implements En {

    public FiltersPageStepDefs() {

        FiltersPageImpl filtersPage = new FiltersPageImpl();

        When("^HE I create a new filter based on the following details:$", filtersPage::createFilter);

        And("^HE I open the page for filter creation$", filtersPage::clickCreateFilter);

        And("^HE I save the filter leaving all the fields blank$", filtersPage::clickSaveFilter);

        Then("^HE I verify the error messages for the required fields$", filtersPage::verifyReqDataErrorMessages);
    }
}
