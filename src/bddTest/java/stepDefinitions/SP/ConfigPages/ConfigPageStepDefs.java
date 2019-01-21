package stepDefinitions.SP.ConfigPages;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SP.communityPages.InstitutionPageImpl;
import pageObjects.SP.configPages.ConfigPageImpl;

public class ConfigPageStepDefs implements En {
    ConfigPageImpl configPageObj = new ConfigPageImpl();

    public ConfigPageStepDefs() {

        And("^SP I go to \"([^\"]*)\" page$", configPageObj::goToManageBlockedAccounts);
        And("^SP I unblock or activate the account for \"([^\"]*)\"$", configPageObj::activateBlockedUser);
    }

}