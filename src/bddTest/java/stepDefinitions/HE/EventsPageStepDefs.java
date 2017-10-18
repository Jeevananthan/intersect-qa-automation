package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;
import pageObjects.HE.homePage.HomePageImpl;

public class EventsPageStepDefs implements En {

    public EventsPageStepDefs() {

        EventsPageImpl eventsPage = new EventsPageImpl();

        Then("^HE The Events page is displayed$", eventsPage::verifyTitleIsPresent);
    }
}
