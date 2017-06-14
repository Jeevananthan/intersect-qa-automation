package stepDefinitions.HS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HS.institutionProfilePage.InstitutionEditProfilePageImpl;

public class InstitutionEditProfilePageStepDefs implements En {

    InstitutionEditProfilePageImpl profile = new InstitutionEditProfilePageImpl();

    public InstitutionEditProfilePageStepDefs() {
        Then("^HS I access the PROFILE page$", profile::navigateToInstitutionProfile);
        And( "^HS I access the EDIT PROFILE page by clicking edit button$", profile::editInstitutionProfile);
        Then("^HS I make sure the CONTACT INFORMATION FIELDS exist$", profile::verifyInstitutionProfileFieldsExist);
        And("^HS I enter the following CONTACT data on the Institution Profile page and click \"([^\"]*)\"$", profile::fillAndInteract );
        Then("^HS I make sure the ENROLLMENT INFORMATION FIELDS exist$", profile::verifyInstitutionProfileFieldsExist);
        And("^HS I enter the following ENROLLMENT data on the Institution Profile page and click \"([^\"]*)\"$", profile::fillAndInteract );
        Then("^HS I make sure the ACADEMIC DATA FIELDS exist$", profile::verifyInstitutionProfileFieldsExist);
        And("^HS I enter the following ACADEMIC data on the Institution Profile page and click \"([^\"]*)\"$", profile::fillAndInteract);
        And("^HS I verify that I do not have access to the institution profile edit button$", profile::noInstitutionProfileEditButton);
        Then("^HS I make sure the LOWEST and HIGHEST GRADE LEVEL FIELDS exist$", profile::verifyInstitutionProfileFieldsExist);
        Then("^HS I make sure the LOWEST GRADE LEVEL dropdown is complete and sorted correctly and \"([^\"]*)\"$", profile::verifyDropdownListCompleteAndSorted);
        Then("^HS I make sure the HIGHEST GRADE LEVEL dropdown is complete and sorted correctly and \"([^\"]*)\"$", profile::verifyDropdownListCompleteAndSorted);
        Then("^HS I make sure that the country field exist$", profile::verifyInstitutionProfileFieldsExist);
        And("^HS I enter the following Country data on the Institution Profile page and click \"([^\"]*)\"$", profile::fillAndInteract);
        Then("^HS I verify the header exist above Title I data entry boxes \"([^\"]*)\"$", profile::verifyHeaderExist);
        And("^HS I verify updated data entered has successfully been saved$", profile::verifyDataSaved);
        Then("^HS I re-enter original data and click \"([^\"]*)\"$", profile::fillAndInteract);
        Then("^HS I make sure that no total fields exist$", profile::verifyInstitutionProfileFieldsDoNotExist);
        Then("^HS I make sure the Title I Eligibility dropdown only displays appropriate options \"([^\"]*)\"$", profile::verifyDropdownListCompleteAndSorted);
        Then("^HS I make sure the Title I Status dropdown only displays appropriate options \"([^\"]*)\"$", profile::verifyDropdownListCompleteAndSorted);
        Then("^HS I make sure the Charter School dropdown only displays appropriate options \"([^\"]*)\"$", profile::verifyDropdownListCompleteAndSorted);
        Then("^HS I make sure the Coeducational dropdown only displays appropriate options \"([^\"]*)\"$", profile::verifyDropdownListCompleteAndSorted);
    }
}
