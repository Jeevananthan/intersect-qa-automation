package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.AdmissionsEditPageImpl;
import pageObjects.HUBS.MediaTabEditPageImpl;

public class EditMediaTabStepDefs implements En{

    public EditMediaTabStepDefs() {
        MediaTabEditPageImpl mediaTabEdit = new MediaTabEditPageImpl();

        When("^HUBS I click on \"([^\"]*)\" tab$", mediaTabEdit::clickOnMediaTab);
        Then("^HUBS I click on \"([^\"]*)\" button$", mediaTabEdit::clickOnPublishMyMediaChangesButton);
        Then("^HUBS I verify the Publishing Modal Confirmation with the \"([^\"]*)\" element$",mediaTabEdit::verifyPublishingModalConfirmation);
        Then("^HUBS I verify functionality of Publishing Modal Confirmation$",mediaTabEdit::verifyFunctionalityForPublishingModalConfirmation);


    }

}
