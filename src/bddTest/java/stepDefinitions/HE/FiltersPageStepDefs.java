package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.filtersPage.FiltersPageImpl;

public class FiltersPageStepDefs implements En {

    public FiltersPageStepDefs() {

        FiltersPageImpl filtersPage = new FiltersPageImpl();

        When("^HE I create a new filter based on the following details:$", filtersPage::createFilter);

        And("^HE I open the page for filter creation$", filtersPage::clickCreateFilter);

        And("^HE I save the filter leaving all the fields blank$", filtersPage::clickSaveFilter);

        Then("^HE I verify the error messages for the required fields$", filtersPage::verifyReqDataErrorMessages);

        And("^HE I open the Create Filter screen$", filtersPage::clickCreateFilter);

        And("^HE I edit the Event Filter's name \"([^\"]*)\" to \"([^\"]*)\"$", filtersPage::renameFilter);

        And("^HE I delete the filter of name \"([^\"]*)\"$", filtersPage::deleteFilter);

        And("^HE I verify that the filter of name \"([^\"]*)\" is assigned to \"([^\"]*)\" events$", filtersPage::verifyNumberOfAssignedEvents);
    }
}
