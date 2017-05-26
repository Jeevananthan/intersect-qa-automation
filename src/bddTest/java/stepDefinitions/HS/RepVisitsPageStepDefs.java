package stepDefinitions.HS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HS.repVisitsPage.RepVisitsPageImpl;

public class RepVisitsPageStepDefs implements En {

    RepVisitsPageImpl repVisits = new RepVisitsPageImpl();



    public RepVisitsPageStepDefs() {

        Then("^HS I verify the following tabs exist on the RepVisits page$", repVisits::checkRepVisitsSubTabs);

        Then("^HS I verify the Availability & Settings tab of the RepVisits page$", repVisits::verifyAvailabilityAndSettingsPage);
        Then("^HS I Verify the Time Zone in Repvisits Availability & Settings is \"([^\"]*)\"$",repVisits::verifyTimeZonePage);
        And("^HS I Set the RepVisits Availability & Settings Time ZOne to \"([^\"]*)\"$", repVisits::setTimeZone);
        And("^HS I Click on Availability on Availability & Settings tab in RepVisits$",repVisits::clickLinkAvailability);


    }

    }

