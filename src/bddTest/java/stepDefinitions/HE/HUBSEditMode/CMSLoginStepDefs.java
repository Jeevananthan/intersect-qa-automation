package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.*;

public class CMSLoginStepDefs implements En{

    public CMSLoginStepDefs() {
        CMSNodeMenuPageImpl nodeMenu = new CMSNodeMenuPageImpl();

        And("^HUBS I approve the changes in CMS for Studies with the following details:$", nodeMenu::approveChangesInCMS);
    }

}
