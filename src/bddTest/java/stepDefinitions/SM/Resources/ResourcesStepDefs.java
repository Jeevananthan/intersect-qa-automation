package stepDefinitions.SM.Resources;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.loginPage.LoginPageImpl;
import pageObjects.SM.resourcesPage.ResourcesImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class ResourcesStepDefs implements En {

    public ResourcesStepDefs() {
        LoginPageImpl loginPage = new LoginPageImpl();
        ResourcesImpl resourcesObj = new ResourcesImpl();

        Then("^SM I click on \"([^\"]*)\" fit criteria$", loginPage::clickDesiredFitCriteria);

        Then("^SM I check the selection and deselection and Must Have box functionality for \"([^\"]*)\" checkbox$",
                resourcesObj::verifyResourcesCheckbox);
        Then("^SM I check when \"([^\"]*)\" is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box$",
                resourcesObj::verifyMAndNBoxSync);

    }
}
