package stepDefinitions.COMMON;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.COMMON.HEHSCommonImpl;

public class HEHSCommonStepDefs implements En {

    public HEHSCommonStepDefs() {

        HEHSCommonImpl HEHSCommonImpl = new HEHSCommonImpl();

        Then("I verify the following sub-tabs are displaying and \"([^\"]*)\" is \"([^\"]*)\" in the notification tab for \"([^\"]*)\" user$",HEHSCommonImpl::verifySubtabsInNotificationsPage);
        When("^HE I navigate to the \"([^\"]*)\" url$",HEHSCommonImpl::navigateToURL);
        Then("^I verify that the column headers in the \"([^\"]*)\" table are the following:$", HEHSCommonImpl::verifyColumnHeaders);

    }
}
