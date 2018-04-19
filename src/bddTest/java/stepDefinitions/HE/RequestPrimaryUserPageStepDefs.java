package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.requestPrimaryUserPage.RequestPrimaryUserPageImpl;

public class RequestPrimaryUserPageStepDefs implements En {

    public RequestPrimaryUserPageStepDefs() {

        RequestPrimaryUserPageImpl requestPrimaryUserPage = new RequestPrimaryUserPageImpl();

        Given("^HE I use the New User Search to find \"([^\"]*)\" and request a new freemium user$", requestPrimaryUserPage::requestNewFreemiumUser);

        Given("^HE I use the New User Search to find \"([^\"]*)\" and request a new institution$", requestPrimaryUserPage::requestNewInstitution);

        Then("^HE I verify the fields and error messaging on the Request User page using the following info$", requestPrimaryUserPage::fillFormAndVerifyMessaging);

        Then("^HE I submit the Request User form using the following info$", requestPrimaryUserPage::fillFormAndSubmit);

    }
}
