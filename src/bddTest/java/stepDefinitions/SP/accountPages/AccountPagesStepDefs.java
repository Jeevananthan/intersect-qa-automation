package stepDefinitions.SP.accountPages;

import cucumber.api.java8.En;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.SP.accountPages.AccountPageImpl;

public class AccountPagesStepDefs implements En {

    public AccountPagesStepDefs() {

        AccountPageImpl accountPage = new AccountPageImpl();
        NavBarImpl navBar = new NavBarImpl();

        Then("^SP I am able to view the individual account page$", accountPage::verifyImOnAnInstitutionPage);

        And("^SP I verify that I can create a new primary user$",accountPage::verifyCreatePrimaryUser);

        Then("^SP I should see Additional Contact Details on Institutional Account Page$", accountPage::verifyInstitutuionalInformation);

        Then("^SP I do not have access to View Log History$", () -> {
            accountPage.verifyAccessToLogHistory("No");
        });

        Then("^SP I do have access to View Log History$", () -> {
            accountPage.verifyAccessToLogHistory("Yes");
        });

        Then("^SP I verify subscription start date restrictions$", accountPage::verifyStartDateFeasibility);

        Then("^SP I verify subscription end date restrictions$", accountPage::verifyEndDateFeasibility);

        Then("^SP I verify the left navigation bar and section breadcrumbs are as follows$",navBar::verifyLeftNavAndBreadcrumbs);

        And("^SP I Click the Save Changes button",accountPage::clicksaveChangesButton);

        Then ("^SP I verify the \"([^\"]*)\" should be \"([^\"]*)\" for \"([^\"]*)\"$",accountPage::verifyModuleDetails);

        Then("^SP I set the \"([^\"]*)\" module to \"([^\"]*)\" with the start date \"([^\"]*)\" and end date \"([^\"]*)\" in the institution page$",accountPage::setModuleStatusAsActiveOrInActiveWithDate);

        Then("^SP I verify the status \"([^\"]*)\" with the start date \"([^\"]*)\" and end date \"([^\"]*)\" for the module \"([^\"]*)\"$",accountPage::verifyModuleDetails);

        And("^SP I set the \"([^\"]*)\" module to \"([^\"]*)\" in the institution page$",accountPage::setModuleStatusAsActiveOrInActive);

        Then("^SP I add the user account \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and set the user to be a new primary user \"([^\"]*)\"$", accountPage::addUserAccount);

        Then("^SP I verify the year \"([^\"]*)\" is present in the calendar of the institution page for \"([^\"]*)\" using \"([^\"]*)\",\"([^\"]*)\"$",accountPage::verifyYearInInstitutionPage);

        Then("^SP I verify the color \"([^\"]*)\" is present in the selected date \"([^\"]*)\",\"([^\"]*)\" in calendar of the institution page for \"([^\"]*)\"$",accountPage::verifySelectedDateColorInInstitutionPage);

    }
}
