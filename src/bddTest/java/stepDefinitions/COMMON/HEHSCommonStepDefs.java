package stepDefinitions.COMMON;
import cucumber.api.java8.En;
import pageObjects.COMMON.HEHSCommonImpl;

public class HEHSCommonStepDefs implements En {

    public HEHSCommonStepDefs() {

        HEHSCommonImpl HEHSCommonImpl = new HEHSCommonImpl();

        Then("I verify the following sub-tabs are displaying and \"([^\"]*)\" is \"([^\"]*)\" in the notification tab for \"([^\"]*)\" user$",HEHSCommonImpl::verifySubtabsInNotificationsPage);

         }
}
