package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.eventsPage.EventsPageImpl;

public class EventsPageStepDefs implements En {

    public EventsPageStepDefs() {

        EventsPageImpl eventsPage = new EventsPageImpl();

        Then("^HE The Events page is displayed$", eventsPage::verifyEventTitleIsPresent);
    }
}
