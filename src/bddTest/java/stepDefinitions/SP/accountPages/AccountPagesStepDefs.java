package stepDefinitions.SP.accountPages;

import cucumber.api.java8.En;
import pageObjects.SP.accountPages.AccountPageImpl;

public class AccountPagesStepDefs implements En {

    public AccountPagesStepDefs() {

        AccountPageImpl accountPage = new AccountPageImpl();

        Then("^SP I am able to view the individual account page$", accountPage::verifyImOnAnInstitutionPage);

        And("^SP I verify that I can create a new primary user$",accountPage::verifyCreatePrimaryUser);

        Then("^SP I should see Additional Contact Details on Institutional Account Page$", accountPage::verifyInstitutuionalInformation);

        Then("^SP I do not have access to View Log History$", () -> {
            accountPage.verifyAccessToLogHistory("No");
        });

        Then("^SP I do have access to View Log History$", () -> {
            accountPage.verifyAccessToLogHistory("Yes");
        });
    }
}
