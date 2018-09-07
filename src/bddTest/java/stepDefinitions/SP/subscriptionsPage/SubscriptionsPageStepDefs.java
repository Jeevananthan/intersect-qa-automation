package stepDefinitions.SP.subscriptionsPage;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SP.subscriptionsPage.SubscriptionsPageImpl;

public class SubscriptionsPageStepDefs implements En {

    public SubscriptionsPageStepDefs() {

        SubscriptionsPageImpl subscriptionsPage = new SubscriptionsPageImpl();

        And("^SP I add a new subscription of type \"([^\"]*)\"$", subscriptionsPage::addNewSubscription);

        Then("^SP I verify the functionality of the Back button$", subscriptionsPage::verifyBackButtonFunctionality);

        And("^SP I fill the new subscription with the following data:$", subscriptionsPage::fillNewSubscriptionForm);

        And("^SP I save the new subscription$", subscriptionsPage::clickFinish);

        Then("^SP I verify that a new subscription was added with the following data:$", subscriptionsPage::verifyNewSubscription);

        Then("^SP I verify that the value in the Radius From Zips field is \"([^\"]*)\"$", subscriptionsPage::verifyValueRadiusFromZips);
    }
}