package stepDefinitions.SP.subscriptionsPage;

import cucumber.api.PendingException;
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

        And("^SP I delete the subscriptions with the following data:$", subscriptionsPage::deleteSubscription);

        Then("^SP I verify that a new subscription was added with the following data:$", subscriptionsPage::verifyNewSubscription);

        Then("^SP I verify that the value in the Radius From Zips field is \"([^\"]*)\"$", subscriptionsPage::verifyValueRadiusFromZips);

        And("^SP I delete all the subscriptions for school$", subscriptionsPage :: deleteMultipleSubscriptions);

        And("^SP I press on the name of the Subscription \"([^\"]*)\"$", subscriptionsPage::openSubscription);

        And("^SP I verify that checkBox with text 'Majors' can be checked$", subscriptionsPage::verifyMajorsCheckboxCanBeChecked);

        And("^SP I verify that checkBox with text 'Majors' can be unchecked$", subscriptionsPage::verifyMajorsCheckboxCanBeUnchecked);

        And("^SP I verify that checkBox with text 'Connection' can be checked$", subscriptionsPage::verifyConnectionCheckboxCanBeChecked);

        And("^SP I verify that checkBox with text 'Connection' can be unchecked$", subscriptionsPage::verifyConnectionCheckboxCanBeUnchecked);

        Then("^SP I close subscriptions popup$",subscriptionsPage::closeSubscriptionsPopup);

        Then("^SP I click 'ADD NEW SUBSCRIPTION' button$",subscriptionsPage::clickAddNewSubscriptionButton);

    }
}