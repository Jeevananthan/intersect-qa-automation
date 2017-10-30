package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.eventsPage.EventsPageImpl;

public class EventsPageStepDefs implements En {

    public EventsPageStepDefs() {

        EventsPageImpl eventsPage = new EventsPageImpl();

        Then("^HE The Events page is displayed$", eventsPage::verifyTitleIsPresent);

        And("^HE I create and save a new event with the following details:$", eventsPage::createAndSaveEvent);

        Then("^HE I should see the event of name \"([^\"]*)\" present in the events list as an unpublished event$", eventsPage::verifyEventIsPresent);

        When("^HE I edit the event of name \"([^\"]*)\" with the following details:$", eventsPage::editEvent);

        Then("^HE The event of name \"([^\"]*)\" should have the following data:$", eventsPage::verifyEventData);

        When("^HE I publish the event with name \"([^\"]*)\"$", eventsPage::publishEvent);

        Then("^HE I should see the event of name \"([^\"]*)\" present in the events list as a published event$", eventsPage::verifyPublishedEventPresent);

        When("^HE I delete the event of name \"([^\"]*)\"$", eventsPage::deleteEvent);

        Then("^HE The deleted event should not be displayed in the events list$", eventsPage::verifyEventIsNotPresent);
    }
}
