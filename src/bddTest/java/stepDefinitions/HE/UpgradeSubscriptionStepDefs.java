package stepDefinitions.HE;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.UpgradeSubscriptionImpl;
import pageObjects.HE.loginPage.LoginPageImpl;

public class UpgradeSubscriptionStepDefs implements En {
    public UpgradeSubscriptionStepDefs() {
        UpgradeSubscriptionImpl UpgradeSubscriptionPage = new UpgradeSubscriptionImpl();


            Given("^HE I Verify Upgrade Subscription Ribbon and Button for \"([^\"]*)\"$", UpgradeSubscriptionPage::premiumLock);
            And("^HE I select check for field Receive Hobsons Communications$",UpgradeSubscriptionPage:: recieveCommunications);
            And("^HE I click on Upgrade button subscription \"([^\"]*)\"$",UpgradeSubscriptionPage :: clickUpgradeSubscription);
            And("^HE I Request Information to Upgrade Subscription$",UpgradeSubscriptionPage :: requestInformationToUpgrade);
            And("^HE I enter message on request information page \"([^\"]*)\"$",UpgradeSubscriptionPage :: enterAdditionalMessage);
            And("^HE I verify message is not more than \"([^\"]*)\" characters$",UpgradeSubscriptionPage :: verifyMessageCount);
            And("^HE I Edit The Contact Details with the following data$",UpgradeSubscriptionPage :: editContactDetails);
            And("^HE I verify Upgrade Message for \"([^\"]*)\"$", UpgradeSubscriptionPage :: clickUpgradeFilter);
            And("^HE I Verify Upgrade Filter Ribbon and Button for \"([^\"]*)\"$",UpgradeSubscriptionPage :: filterPremiumLock);
            And("^HE I click on Upgrade button filter \"([^\"]*)\"$", UpgradeSubscriptionPage :: filterUpgradeClick);
            Then("^HE I navigate to the community page$",UpgradeSubscriptionPage::navigateToCommunityPage);
            Then("^HE I click upgrade button in community page$",UpgradeSubscriptionPage::clickUpgradeButtonInCommunity);
            Then("^HE I verify the following fields are required in the upgrade form$",UpgradeSubscriptionPage::verifyFieldsInUpgradeForm);
            Then("^HE I verify the check box is changed to \"([^\"]*)\" in the upgrade form$",UpgradeSubscriptionPage::verifyCheckBoxInUpgradeForm);
            Then("^HE I verify the note \"([^\"]*)\" is displaying in the upgrade form$",UpgradeSubscriptionPage::verifyNoteInUpgradeForm);
            Then("^HE I verify the privacy policy link navigate to the URL \"([^\"]*)\" in the upgrade form$",UpgradeSubscriptionPage::verifyPrivacyPolicy);
            Then("^HE I verify the updated text \"([^\"]*)\" is displaying in the upgrade form$",UpgradeSubscriptionPage::verifyUpdatedTextInUpgradeForm);
            Then("^HE I navigate to the Fields \"([^\"]*)\" in repvisits page$",UpgradeSubscriptionPage::navigateToFieldsInRepVisits);
            Then("^HE I click upgrade button in repVisits$",UpgradeSubscriptionPage::clickUpgradeButtonInRepvisits);
            Then("^HE I close upgrade form$",UpgradeSubscriptionPage::closeUpgradeForm);
    }}