package stepDefinitions.SP.adminPages;

import Selenium.SeleniumLibrary;
import cucumber.api.java8.En;
import pageObjects.SP.adminPages.AdminLoginPageImpl;

public class AdminLoginPageStepDefs extends SeleniumLibrary implements En {

    public AdminLoginPageStepDefs() {
        AdminLoginPageImpl loginPage = new AdminLoginPageImpl();

        Given("^SP I want to login to the admin page using \"([^\"]*)\" as username and \"([^\"]*)\" as password$", loginPage::login);

        Then("^SP I am unable to login and I see the following error message \"([^\"]*)\"$", loginPage::verifyExpectedErrorMessage);

        Given("^SP I am logged in to the Admin page as a View Only user$", loginPage::loginAsAViewOnlyUser);

        Given("^SP I am logged in to the Admin page as a Sales Ops user$", loginPage::loginAsASalesOpsUser);

        Given("^SP I am logged in to the Admin page as an Admin user$", loginPage::loginAsAnAdminUser);

        Given("^SP I am logged in to the Admin page as a Support user$", loginPage::loginAsASupportUser);

        Given("^SP I am logged in to the Admin page as a Community user$", loginPage::loginAsACommunityUser);

        Given("^SP I am logged in to the Admin page as a Community Manager user$", loginPage::loginAsACommunityManagerUser);

        Given("^SP I am logged in to the Admin page as a user with no Intersect access$", loginPage::loginAsNoAccessUser);
    }
}
