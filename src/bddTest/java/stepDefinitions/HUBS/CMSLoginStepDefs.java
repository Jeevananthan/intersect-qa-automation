package stepDefinitions.HUBS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HUBS.CMSNodeMenuPageImpl;

public class CMSLoginStepDefs implements En{

    public CMSLoginStepDefs() {
        CMSNodeMenuPageImpl nodeMenu = new CMSNodeMenuPageImpl();

        And("^HUBS I approve the changes in CMS with the user email \"([^\"]*)\" and the following details:$", nodeMenu::approveChangesInCMS);

    }
}
