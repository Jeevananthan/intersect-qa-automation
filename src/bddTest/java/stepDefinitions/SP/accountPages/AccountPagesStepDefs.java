package stepDefinitions.SP.accountPages;

import cucumber.api.java8.En;
import pageObjects.SP.accountPages.AccountPageImpl;

public class AccountPagesStepDefs implements En {

    public AccountPagesStepDefs() {

        AccountPageImpl accountPage = new AccountPageImpl();

        Then("^SP I am able to view the individual account page$", () -> {
            accountPage.verifyImOnAnInstitutionPage();
        });
    }
}
