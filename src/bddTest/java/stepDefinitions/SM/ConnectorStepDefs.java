package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.collegesLookingForStudentsLikeYou.CollegesLookingForStudentsLikeYouPageImpl;
import pageObjects.SM.connector.ConnectorPageImpl;

/**
 * Created by mbhangu on 9/28/2018.
 */
public class ConnectorStepDefs implements En {
    public ConnectorStepDefs() {
        ConnectorPageImpl connectorPage = new ConnectorPageImpl();

        Then("^SM I verify that all the checkboxes are \"([^\"]*)\" when \"([^\"]*)\" is \"([^\"]*)\"$", connectorPage::verifyCheckboxesRelatedTo);

        Then("^SM I verify that \"([^\"]*)\" is unselected when any data checkbox is unselected, for example \"([^\"]*)\"$", connectorPage::verifyMainCheckboxRelatedTo);

        Then("^SM I verify that the following connector fields are editable:$", connectorPage::verifyConnectorFieldsEditable);

        Then("^SM I verify that the following connector fields are not editable:$", connectorPage::verifyConnectorFieldsNotEditable);

        And("^HE I click the button \"([^\"]*)\" in the connector dialog$", connectorPage::clickButtonInConnectorDialog);
    }}
