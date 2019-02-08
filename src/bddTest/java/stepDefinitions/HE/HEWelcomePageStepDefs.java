package stepDefinitions.HE;

import cucumber.api.java8.En;
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

        And("^HE I verify the Welcome page has a header that says: \"([^\"]*)\"$", heWelcomePage::verifyWelcomeCounselorCommunityPageHeader);

        And("^HE I go to the Welcome Counselor Community page$", heWelcomePage::goToWelcomeCounselorCommunityPage);

        And("^HE I verify the agreements label that says: \"([^\"]*)\"$", heWelcomePage::verifyAgreementsLabel);

        And("^HE I verify the edit privacy info label that says: \"([^\"]*)\"$", heWelcomePage::verifyEditPrivacyInfoLabel);

    }
}
