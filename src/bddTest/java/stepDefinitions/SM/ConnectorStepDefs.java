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

        Then("^SM I verify that at least one of the following fields is required for submitting the form: Email, Phone, Address$", connectorPage::verifyRequiredFieldsEmailPhoneStreet);

        Then("^SM I verify that, when \"([^\"]*)\" is used as value for Phone, an error message is displayed$", connectorPage::verifyPhoneErrorMessage);

        Then("^SM I verify that the birthday format is: Month\\(abbreviated\\) DD, YYYY$", connectorPage::verifyBirthdayFormat);

        Then("^SM I verify that it is possible to submit the form with \"([^\"]*)\" as Birthday value$", connectorPage::verifyItsPossibleToSubmit);

        Then("^SM I verify that the connector dialog is displayed in the page of URL \"([^\"]*)\"$", connectorPage::verifyConnectorDialogInURL);

        Then("^SM I verify that all the connection checkboxes are \"([^\"]*)\" by default$", connectorPage::verifyConnectionCheckboxes);

        Then("^SM I verify that it is possible to select the value \"([^\"]*)\" in the Majors dropdown$", connectorPage::verifyValueSelectionMajors);

        Then("^SM I verify that the Successfully Submitted! screen is displayed$", connectorPage::verifySubmittedScreen);

        Then("^SM I verify that the Next button is disabled when all competitors are unchecked$", connectorPage::verifyNextButtonDisabled);

        Then("^SM I verify that the following text is present in the connector dialog:$", connectorPage::verifyTextInConnectorDialog);

        Then("^SM I \"([^\"]*)\" the checkbox for \"([^\"]*)\" in the connector dialog$", connectorPage::checkUncheckCollegeInConnector);

        Then("^SM I verify that no connector dialog is displayed$", connectorPage::verifyNoConnectorIsDisplayed);

        Then("^SM I verify that the URL of the current page contains \"([^\"]*)\"$", connectorPage::verifyStringInURL);

        And("^SM I close the connector with the close icon$", connectorPage::closeConnectorWithCloseIcon);

    }}
