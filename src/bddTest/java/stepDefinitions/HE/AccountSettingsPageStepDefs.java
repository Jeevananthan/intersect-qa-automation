package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.accountSettingsPage.AccountSettingsPageImpl;

public class AccountSettingsPageStepDefs implements En {

    public AccountSettingsPageStepDefs() {

        AccountSettingsPageImpl accountSettingsPage = new AccountSettingsPageImpl();

        Then("^HE I enter the following data on the Account Settings page and click \"([^\"]*)\"$",accountSettingsPage::fillAndInteract);

        And("^HE I verify the Password Requirements are displayed$",accountSettingsPage::verifyPasswordRequirementsMessage);

        Then("^HE I navigate to the \"([^\"]*)\" page to access the \"([^\"]*)\" page$",accountSettingsPage::accessUsersPage);

        Then("^HE I select the \"([^\"]*)\" options from the actions dropdown for the HE primary account \"([^\"]*)\"$",accountSettingsPage::selectOption);
    }
}
