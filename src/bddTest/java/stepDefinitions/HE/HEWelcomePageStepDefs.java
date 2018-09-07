package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.COMMON.NavigationBarImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;
import pageObjects.HE.homePage.HomePageImpl;
import pageObjects.HE.welcomePage.HEWelcomePageImpl;

public class HEWelcomePageStepDefs implements En {

    public HEWelcomePageStepDefs() {

        HEWelcomePageImpl heWelcomePage = new HEWelcomePageImpl();

        Then("^HE I verify the new user required to complete the Counselor Community profile form before they can access the following fields$",heWelcomePage::verifyRequiredPageforNewUser);

        Then("^HE I verify the following fields are required fields in the Counselor Community profile form$",heWelcomePage::verifyRequiredFieldsInCCProfileForm);

        Then("^HE I verify the user can access the following fields$",heWelcomePage::verifyingTabNavigation);

        When("^HE I verify that I am redirected to the Community activate profile page when accessing RepVisits$",
                heWelcomePage::verifyCommunityActivationForRepVisits);

        And("^HE I clear the account to get the community welcome page again$",heWelcomePage::clearCommunityProfile);

        And("^HE I activate my community profile by providing OfficePhone as \"([^\"]*)\" JobTitle as \"([^\"]*)\" and EU citizen as \"([^\"]*)\"$",
                heWelcomePage::fillCommunityWelcomeMandatoryFields);

    }
}
