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
/*
        SearchPageImpl searchPage = new SearchPageImpl();

        Then("^I select the following data from the Location Fit Criteria$",searchPage::setLocationCriteria);

        And("^SM I verify the Student Body UI in Resources Dropdown$", searchPage::verifyStudentBodyUI);
        */
        Then("^SM I click on \"([^\"]*)\" fit criteria$", loginPage::clickDesiredFitCriteria);

        Then("^SM I check the selection and deselection and Must Have box functionality for Services for the Deaf and Hard of Hearing checkbox$",
                resourcesObj::verifyDeafAndHardHearingCheckbox);

        Then("^SM I check when Services for the Deaf and Hard of Hearing is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box$",
                resourcesObj::verifyMHAndNHSyncWithServiceforDeafandHardofHearingFilter);


    }
}
