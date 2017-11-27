package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.eventsPage.EventsPageImpl;

public class EventsPageStepDefs implements En {

    public EventsPageStepDefs() {

        EventsPageImpl eventsPage = new EventsPageImpl();

        Then("^HE The Events page is displayed$", eventsPage::verifyTitleIsPresent);

        And("^HE I create and save a new event with the following details:$", eventsPage::createAndSaveEvent);

        Then("^HE I should see the event of name \"([^\"]*)\" present in the unpublished events list as Draft event$", eventsPage::verifyEventIsPresent);

        When("^HE I edit the event of name \"([^\"]*)\" with the following details:$", eventsPage::editEvent);

        When("^HE I publish the current event$", eventsPage::publishEvent);

        Then("^HE I should see the event of name \"([^\"]*)\" present in the events list as a published event$", eventsPage::verifyPublishedEventPresent);

        When("^HE I cancel the event of name \"([^\"]*)\"$", eventsPage::cancelEvent);

        Then("^HE The canceled event of name \"([^\"]*)\" should be displayed in the canceled events list$", eventsPage::verifyEventIsInCancelledList);

        And("^HE I take note of the data in the Event$", eventsPage::takeNoteOfData);

        And("^HE I update the event$", eventsPage::clickUpdate);

        And("^HE I save the draft$", eventsPage::clickSaveDraft);

        Then("^HE The event of name \"([^\"]*)\" should be updated$", eventsPage::verifyEditedData);
    }
}
