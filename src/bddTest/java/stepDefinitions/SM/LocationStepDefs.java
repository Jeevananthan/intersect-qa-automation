package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.diversity.DiversityPageImpl;
import pageObjects.SM.location.LocationPageImpl;

public class LocationStepDefs implements En {

    public LocationStepDefs() {

        LocationPageImpl locationPage = new LocationPageImpl();

        Then("^SM I verify that the following options were added to the dropdown field:$", locationPage::verifyOptionsInDropdownField);

    }
}
