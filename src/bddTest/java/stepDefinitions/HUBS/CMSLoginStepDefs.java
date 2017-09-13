package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.CMSNodeMenuPageImpl;

public class CMSLoginStepDefs implements En{

    public CMSLoginStepDefs() {
        CMSNodeMenuPageImpl nodeMenu = new CMSNodeMenuPageImpl();

        And("^HUBS I approve the changes in CMS for Studies with the following details:$", nodeMenu::approveChangesInCMS);
    }

}
