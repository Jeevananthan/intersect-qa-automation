package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.editEventPage.EditEventPageImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;

public class EventsPageStepDefs implements En {

    public EventsPageStepDefs() {

        EventsPageImpl eventsPage = new EventsPageImpl();
        EditEventPageImpl editEventPage = new EditEventPageImpl();

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

        When("^HE I delete the event of name \"([^\"]*)\"$", eventsPage::deleteEvent);

        And("^HE I verify required fields error messages$", eventsPage::verifyAllErrorMessages);

        And("^HE I verify required fields error messages for events$", eventsPage::verifyAllErrorMessagesForEvents);

        Then("^HE The deleted event of name \"([^\"]*)\" should not be displayed in the unpublished events list$", eventsPage::verifyEventNotPresentInList);

        When("^HE I unpublish the event of name \"([^\"]*)\"$", eventsPage::unpublishEvent);

        And("^HE I create and save a new event with a unique name and the following details:$", eventsPage::createAndSaveEventWithUniqueName);

        When("^HE I cancel the created event$", eventsPage::cancelCreatedEvent);

        Then("^HE The cancelled event should be displayed in the canceled events list$", eventsPage::verifyCreatedEventIsInCancelledList);

        And("^HE I open the Create Event screen$", eventsPage::clickCreateEvent);

        And("^HE I create and save a new event \"([^\"]*)\" minutes ahead from now with the following details:$", eventsPage::createAndSaveEventWithGenDate);

        And("^HE I create and publish a new event \"([^\"]*)\" minutes ahead from now with the following details:$", eventsPage::createAndPublishEventWithGenDate);

        And("^HE I verify that the event of name \"([^\"]*)\" is in the expired list$", eventsPage::verifyEventInExpiredList);

        And("^HE I attempt to unpublish the event of generated name$", eventsPage::unpublishEventOfGeneratedName);

        Then("^HE I verify the message that warns that an event with attendee cannot be unpublished$", eventsPage::verifyNoUnpublishWithAttendeesMessage);

        Then("^I verify the cancelation message for the generated event$", eventsPage::verifyCancellationMessageOfGenEvent);

        Then("^HE I verify that a warning message about the past date is displayed$", eventsPage::verifyPastDateErrorMessage);

        And("^HE I open the \"([^\"]*)\" tab in the Events section$", eventsPage::openTab);

        Then("^HE A filter of name \"([^\"]*)\" is displayed in the filters list$", eventsPage::verifyFilterIsPresentInList);

        And("^HE I open the Create Filter dialog from the Event Audience field$", eventsPage::openCreateFilterFromEventAudience);

        Then("^HE A filter of name \"([^\"]*)\" is displayed in the Event Audience list$", eventsPage::verifyFilterInEventAudienceList);

        Then("^HE I should be able to see a list of all the AM Events filters$", eventsPage::verifyFiltersList);

        And("^HE I open the event of name \"([^\"]*)\"$", eventsPage::openEvent);

        Then("^HE The filter of name \"([^\"]*)\" should not be present in the Event Audience list$", eventsPage::verifyFilterNotPresentInAudienceList);

        And("^HE I create and publish a new event with the following details:$", eventsPage::createAndPublishEvent);

        And("^HE I verify that the Attendees tab in the event of name \"([^\"]*)\" is opened by clicking the attendee status bar/students area$", eventsPage::verifyAttendeesFromStatusBar);

        Then("^HE I verify that the Attendees tab in the event of name \"([^\"]*)\" is opened by clicking the Attendees option in the edit menu$", eventsPage::verifyAttendeesFromEditMenu);

        And("^HE I open the \"([^\"]*)\" tab in Events$", eventsPage::openEventsTab);

        Then("^HE I verify that that the user does not have access to the connections page by URL$", eventsPage::verifyNoAccessToConnections);

        Then("^HE I verify status \"([^\"]*)\" for the event of name \"([^\"]*)\"$", eventsPage::verifyEventStatus);

       // Then("^HE I verify status \"([^\"]*)\" for the event of generated name$", eventsPage::verifyEventWithGenNameStatus);

        Then("^HE I verify that the filter of name \"([^\"]*)\" is displayed by default in the Event Audience field$", eventsPage::verifyDefaultFilter);

        Then("^HE I verify that the events' names are clickable and they open the Edit Event screen$", eventsPage::verifyEventsNamesClickable);

        And("^HE I open the event of generated name$", eventsPage::openEventOfGeneratedName);

        And("^HE I open the \"([^\"]*)\" tab in the Edit Event screen$", eventsPage::openTabInEditEvent);

        Then("^HE I verify in \"([^\"]*)\" tries that the Export Attendees button exports a document of name \"([^\"]*)\" with the following headers:$", editEventPage::verifyExportAction);

        Then("^HE I verify that the following error message is displayed when the Attendees section for the generated event is open:$", eventsPage::verifyAttendeesErrorMessage);

        And("^HE I create and save a new event \"([^\"]*)\" minutes ahead from now with a unique name and the following details:$", eventsPage::createAndSaveEventWithGenDateAndName);
        //Then("^HE I verify status \"([^\"]*)\" under Unpublished tab$",eventsPage:: statusDraft);
        And("^HE I verify Attendee Data Details on Edit Events attendee screen$",eventsPage::VerifyAttendeeData);
        And("^HE click on Event Name \"([^\"]*)\" to edit$", eventsPage :: editEventToPublish);
        And("^HE I edit and publish a new event \"([^\"]*)\" minutes ahead from now with the following details:$", eventsPage::EditAndPublishEventWithGenDate );
         }
}
