package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.accountSettingsPage.YourNotificationsPageImpl;

public class YourNotificationsPageStepDef implements En {
    public YourNotificationsPageStepDef(){
        YourNotificationsPageImpl yourNotificationsPage = new YourNotificationsPageImpl();
        Given("^HE I go to Your Notifications$",yourNotificationsPage::goToYourNotifications);
        And("^HE I verify that \"([^\"]*)\" title is displayed$",yourNotificationsPage :: verifyYourNotificationsTitle);
        And("^HE I verify that \"([^\"]*)\" sub title is displayed$",yourNotificationsPage :: verifyRepVisitsTitle);
        And("^HE I verify that \"([^\"]*)\" description is displayed$",yourNotificationsPage :: verifyYourNotificationsDescription);
        And("^HE I verify that Alert me when high schools become available in RepVisits checkbox is displayed$",yourNotificationsPage::verifyAlertMeWhenHighSchoolsBecomeAvailableChecbox);
        And("^HE I save your notifications$",yourNotificationsPage::saveYourNotifications);
        And("^HE I verify the toast that says \"([^\"]*)\" is displayed$",yourNotificationsPage :: verifySaveConfigurationsToast);
    }
}
