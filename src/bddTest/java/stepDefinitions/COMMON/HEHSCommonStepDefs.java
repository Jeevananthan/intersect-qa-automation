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
        Then("^HE I click menu tab \"([^\"]*)\"$",HEHSCommonImpl::clickMenuTab );
        Then("^HE I click menu link \"([^\"]*)\"$",HEHSCommonImpl::clickMenuLink);
        Then("^HE I check \"([^\"]*)\" checkbox for the first row on the Threshold Page$", HEHSCommonImpl::checkCheckboxFirsRow);
        Then("^HE I uncheck \"([^\"]*)\" checkbox for the first row on the Threshold Page$", HEHSCommonImpl::uncheckCheckboxFirsRow);
        Then("^HE I set default filter value \"([^\"]*)\" for \"([^\"]*)\" on the Threshold Page$", HEHSCommonImpl::setDefaultValue);
        Then("^HE I clear default filter value for \"([^\"]*)\" on the Threshold Page$", HEHSCommonImpl::clearDefaultFIlterValue);
        Then("^I check there is no \"([^\"]*)\" text on the page$", HEHSCommonImpl::checkThereIsNoText);
        Then("^HE I verify \"([^\"]*)\" value for the first row is \"([^\"]*)\" on the Threshold Page$",HEHSCommonImpl::verifyFilterValue);
        Then("^HE I set filter value \"([^\"]*)\" for \"([^\"]*)\" on the Threshold Page$",HEHSCommonImpl::setValue );
        Then("^HE I clear filter value for \"([^\"]*)\" on the Threshold Page$", HEHSCommonImpl::clearFilterValue);
        Then("^HE I pick the \"([^\"]*)\" from the menu items$", HEHSCommonImpl::pickFromTHeMenuItems);

    }
}
