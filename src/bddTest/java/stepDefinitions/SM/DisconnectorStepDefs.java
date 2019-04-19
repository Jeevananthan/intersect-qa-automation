package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.connector.ConnectorPageImpl;
import pageObjects.SM.connector.DisconnectorPageImpl;

public class DisconnectorStepDefs implements En {
    public DisconnectorStepDefs() {
        DisconnectorPageImpl disconnectorPage = new DisconnectorPageImpl();

        Then("^SM I click the button \"([^\"]*)\" in the disconnect dialog$", disconnectorPage::clickDialogButton);
    }}
