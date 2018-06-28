package stepDefinitions.CM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.CM.welcomePage.WelcomePageImpl;

/**
 * Created by bojan on 5/30/17.
 */
public class WelcomePageStepDefs implements En {



    public WelcomePageStepDefs() {

        WelcomePageImpl welcomePage = new WelcomePageImpl();

        Given("^I am sure that HE user will be logged in for the first time and Welcome page will be opened$", welcomePage::makeSureHEWelcomeScreenIsOpened);
        Given("^I am sure that HS user will be logged in for the first time and HS Welcome page will be opened$", welcomePage::makeSureHSWelcomeScreenIsOpened);
        Then("^I upload Profile and Banner pictures$", welcomePage::profileAndBannerPicturesUpload);
        And("^I populate all the fields on Welcome page$", welcomePage::popupateWelcomeUserForm);
        And("^I accept Terms and conditions$", welcomePage::acceptTermsAndConditions);
        Then("^I Save changes$", welcomePage::saveChanges);
        And("^I check if changes are saved$", welcomePage::assertUserProfileFieldsPopulated);
        Then("^I check if fields name, institution and email address are uneditable$", welcomePage::assertFieldsUneditable);
        Then("^I check if my profile fields are set to 'public' by default$", welcomePage::checkFieldsPublic);
        Then("^I set work email and office phone privacy to 'Connections Only'$", welcomePage::setPrivacyToConnectionsOnly);
        And("^I set personal email and mobile phone privacy to 'Visible to Only Me'$", welcomePage::setPrivacyToVisibleToOnlyMe);
        And("^I check if privacy settings are saved properly$", welcomePage::checkPrivacySettingsSaved);
        And("^I consent to create and maintain my Intersect account$",welcomePage::consentCreateAndMaintainIntersectAccount);
        And("^I set the EU citizen to \"([^\"]*)\"$",welcomePage::setEuCitizen);


    }
}
