package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.accountSettingsPage.AccountSettingsPageImpl;

public class AccountSettingsPageStepDefs implements En {

    public AccountSettingsPageStepDefs() {

        AccountSettingsPageImpl accountSettingsPage = new AccountSettingsPageImpl();

        Then("^HE I enter the following data on the Account Settings page and click \"([^\"]*)\"$",accountSettingsPage::fillAndInteract);

        And("^HE I verify the Password Requirements are displayed$",accountSettingsPage::verifyPasswordRequirementsMessage);
    }
}
