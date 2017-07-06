package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.welcomePage.WelcomePageImpl;

/**
 * Created by bojan on 5/30/17.
 */
public class WelcomePageStepDefs implements En {



    public WelcomePageStepDefs() {

        WelcomePageImpl welcomePage = new WelcomePageImpl();

        Given("^I am sure that HE user will be logged in for the first time and Welcome page will be opened$", welcomePage::makeSureWelcomeScreenIsOpened);
        Then("^I upload Profile and Banner pictures$", welcomePage::profileAndBannerPicturesUpload);
        And("^I populate all the fields on Welcome page$", welcomePage::popupateWelcomeUserForm);
        And("^I accept Terms and conditions$", welcomePage::acceptTermsAndConditions);
        Then("^I Save changes$", welcomePage::saveChanges);
        And("^I assert if changes are saved$", welcomePage::assertUserProfileFieldsPopulated);
        Then("^I check if fields name, institution and email address are uneditable$", welcomePage::assertFieldsUneditable);


    }
}
