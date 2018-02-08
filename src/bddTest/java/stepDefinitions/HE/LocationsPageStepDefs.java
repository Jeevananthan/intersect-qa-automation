package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.eventsPage.EventsLocationPageImpl;

public class LocationsPageStepDefs implements En {

    public LocationsPageStepDefs() {

        EventsLocationPageImpl locationsPage = new EventsLocationPageImpl();

        And("^HE I create and save a new location with the following details:$", locationsPage::createAndSaveNewLocation);

        Then("^HE I should see the location of name \"([^\"]*)\" present in the locations list$", locationsPage::verifyCreatedLocation);

        When("^HE I edit the location currently selected in the event with the following details:$", locationsPage::editLocation);

        And("^HE I take note of the data in the Location$", locationsPage::takeNoteOfDataInCurrentLocation);

        And("^HE I save the location$", locationsPage::saveLocation);

        Then("^HE The location currently selected should be updated$", locationsPage::verifyLocationUpdated);

        When("^HE I delete the open location$", locationsPage::deleteOpenLocation);

        Then("^HE The deleted location of name \"([^\"]*)\" should not be present in the locations list$", locationsPage::verifyLocationIsNotPresentInList);

        And("^HE I open the Create Location dialog$", locationsPage::openCreateLocation);

        And("^HE I save the current location$", locationsPage::clickDone);

        And("^HE I edit the new location with the following details:$", locationsPage::editOpenLocation);

        And("^HE I verify the error message when deleting the location of name \"([^\"]*)\" associated to a \"([^\"]*)\" event$", locationsPage::verifyErrorMsgWhenDeletingAssociatedLocation);

        And("^HE I delete the location of name \"([^\"]*)\"$", locationsPage::deleteLocation);
    }
}
