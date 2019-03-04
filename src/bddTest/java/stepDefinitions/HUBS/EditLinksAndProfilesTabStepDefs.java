package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.LinksAndProfilesTabPageImpl;
import pageObjects.HUBS.MediaTabEditPageImpl;

public class EditLinksAndProfilesTabStepDefs implements En{

    public EditLinksAndProfilesTabStepDefs() {
        LinksAndProfilesTabPageImpl linksAndProfilesObj = new LinksAndProfilesTabPageImpl();

        And("^HUBS I click on Links link$", linksAndProfilesObj::clickLinks);
        And("^HUBS I update Reguest Information field$", linksAndProfilesObj::updateRequestInformationField);

    }

}
