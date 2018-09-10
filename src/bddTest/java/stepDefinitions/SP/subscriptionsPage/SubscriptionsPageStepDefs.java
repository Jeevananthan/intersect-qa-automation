package stepDefinitions.SP.subscriptionsPage;

import cucumber.api.java8.En;
import pageObjects.SP.subscriptionsPage.SubscriptionsPageImpl;

public class SubscriptionsPageStepDefs implements En {

    public SubscriptionsPageStepDefs() {

        SubscriptionsPageImpl subscriptionsPage = new SubscriptionsPageImpl();

        Then("^SP I verify the functionality of the Back button$", subscriptionsPage::verifyBackButtonFunctionality);

        And("^SP I fill the new subscription with the following data:$", subscriptionsPage::fillNewSubscriptionForm);

        And("^SP I save the new subscription$", subscriptionsPage::clickFinish);

        Then("^SP I verify that a new subscription was added to the subscription table with the following data:$", subscriptionsPage::verifyNewSubscription);

        And("^SP I click the Next button$", subscriptionsPage::clickNextButton);

        And("^SP I select the radio button \"([^\"]*)\" in Add new Subscription modal$", subscriptionsPage::selectRadioButton);

        And("^SP I click the button \"([^\"]*)\"$", subscriptionsPage::clickButton);

        And("^SP I delete the subscriptions with the following data:$", subscriptionsPage::deleteSubscription);
    }
}