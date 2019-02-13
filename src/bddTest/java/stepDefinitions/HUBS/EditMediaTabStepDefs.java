package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.AdmissionsEditPageImpl;
import pageObjects.HUBS.MediaTabEditPageImpl;

public class EditMediaTabStepDefs implements En{

    public EditMediaTabStepDefs() {
        MediaTabEditPageImpl mediaTabEdit = new MediaTabEditPageImpl();

        When("^HUBS I click on \"([^\"]*)\" tab$", mediaTabEdit::clickOnHEMTab);
        When("^HUBS I expanded the \"([^\"]*)\" link$", mediaTabEdit::clickOnLink);
        When("^HUBS verify contents \"([^\"]*)\" into \"([^\"]*)\" section", mediaTabEdit::verifyContents);
        Then("^HUBS I click on \"([^\"]*)\" button$", mediaTabEdit::clickOnPublishMyMediaChangesButton);
        Then("^HUBS I verify the Publishing Modal Confirmation with the \"([^\"]*)\" element$",mediaTabEdit::verifyPublishingModalConfirmation);
        Then("^HUBS I verify functionality of Publishing Modal Confirmation$",mediaTabEdit::verifyFunctionalityForPublishingModalConfirmation);
        And("^HUBS I click on EDIT button$", mediaTabEdit::clickOnEditButton);
        Then("^HUBS I verify contents \"([^\"]*)\" and \"([^\"]*)\" in Media tab$",mediaTabEdit::verifyContentsMediaTab);
        Then("^HUBS I verify Arrows and Slots in the Photos and Videos panel$",mediaTabEdit::verifyArrowsAndSlotsInPhotosAndVideos);
        Then("^HUBS I verify contents \"([^\"]*)\" in Intro tab$",mediaTabEdit::verifyContentsIntroTab);
        Then("^HUBS I verify \"([^\"]*)\" Lock$",mediaTabEdit::verifyPremiumFeatureLock);
        Then("^HUBS I verify the Upgrade Modal$",mediaTabEdit::verifyUpgradeModal);
    }

}
