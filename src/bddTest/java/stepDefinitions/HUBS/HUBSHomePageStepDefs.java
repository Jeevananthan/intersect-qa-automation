package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.*;

public class HUBSHomePageStepDefs implements En{

    public HUBSHomePageStepDefs() {

        HUBSHomePageImpl hubsHomePage = new HUBSHomePageImpl();

        Then("^HUBS all three tabs ie \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" should display$", hubsHomePage::verifyHubPageMainMenuTabs);

        And("^HUBS I click on \"([^\"]*)\" tab in main menu$", hubsHomePage::clickOnHubsMenuTab);

        And("^HUBS I verify the Publish your media changes pop up$", hubsHomePage::verifyPublishYourMediaChangesModel);

        And("^HUBS I check \"([^\"]*)\" tab functionality$", hubsHomePage::checkHEMMainMenuTabFunctionality);
    }
}
