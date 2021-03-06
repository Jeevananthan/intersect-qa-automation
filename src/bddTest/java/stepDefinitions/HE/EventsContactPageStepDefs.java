package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.eventsPage.EventsContactPageImpl;


public class EventsContactPageStepDefs implements En {

    public EventsContactPageStepDefs() {

        EventsContactPageImpl eventsContactPage = new EventsContactPageImpl();

        And("^HE I click on Create Event button$",eventsContactPage:: clickCreateEvent);
        And("^HE I click on box Enter Primary Contact and enter text \"([^\"]*)\"$",eventsContactPage ::newContact);
        And("^HE I click on New Contact link$",eventsContactPage :: clickNewContact);
        And("^HE Enter Following Data to create Contact$",eventsContactPage :: addNewContact);
        And("^HE I select save button$",eventsContactPage:: saveContact);
        And("^HE I click link Return to list$",eventsContactPage:: returnToList);
        And("^HE I verify \"([^\"]*)\" present  in the contact list$",eventsContactPage:: verifyContact);
        And("^HE I verify required field warning messages$",eventsContactPage::requiredFieldMessage);
        And("^HE I click on Edit against contact$",eventsContactPage::editEventContact);
        And("^HE I click on Delete button$", eventsContactPage:: deleteEventContact);
        And("^HE I click on YES on Confirmation message$", eventsContactPage:: confirmDeleteForEventContact);
        And("^HE I verify warning messages$",eventsContactPage:: warningMessages);
        Then("^HE I verify the error message when deleting the Contact associated to a unpublished event$",eventsContactPage :: unpublishedEventContactMessage);
        And("^HE I select No for delete Confirmation$", eventsContactPage :: deleteNo);
        Then("^HE I verify the error message when deleting the Contact associated to a published event$",eventsContactPage :: publishedEventContactMessage);


    }
}
