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
    }}