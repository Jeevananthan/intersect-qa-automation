package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.accountSettingsPage.SftpDataTransferPageImpl;

public class SftpDataTransferStepDef implements En {
    public SftpDataTransferStepDef(){
        SftpDataTransferPageImpl sftpDataTransferPage = new SftpDataTransferPageImpl();
        Given("^HE I delete the SFTP Data Transfer connection", sftpDataTransferPage::deleteSftpDataTransferConnection);
        And("HE I verify the text \"([^\"]*)\" is displayed",sftpDataTransferPage::verifySftpDataTransferText);
        And("HE I verify the SET UP CONNECTION button is displayed",sftpDataTransferPage::verifySetupConnectionButtonIsDisplayed);
        And("^HE I go to the Set Up Connection page$", sftpDataTransferPage::goToSetupConnectionPage);
        And("^HE I verify the Host text box has the value: \"([^\"]*)\"$", sftpDataTransferPage::verifyHostTextBoxValue);
        And("^HE I verify the Port text box has the value: \"([^\"]*)\"$", sftpDataTransferPage::verifyPortTextBoxValue);
        And("^HE I verify the Path text box has the value: \"([^\"]*)\"$", sftpDataTransferPage::verifyPathTextBoxValue);
        And("^HE I verify the User Name text box has the value: \"([^\"]*)\"$", sftpDataTransferPage::verifyUserNameTextBoxValue);
        And("^HE I verify the Password text box has the value: \"([^\"]*)\"$", sftpDataTransferPage::verifyPasswordTextBoxValue);
        And("^HE I select the value \"([^\"]*)\" in authentication method radio buttons$",sftpDataTransferPage::selectAuthenticationMethodRadioButton);
        And("^HE I verify GENERATE KEY button is displayed$",sftpDataTransferPage::verifyGenerateKeyButtonIsDisplayed);
        And("^HE I verify that the following check boxes are displayed in transfer frequency section$",sftpDataTransferPage::verifyTransferFrequencyCheckBoxes);
        And("^HE I verify the following ip addresses are displayed in IP WHITELISTING text box$",sftpDataTransferPage::verifyIpWhiteListingValues);
        And("^HE I verify that Check fingerprint to verify server check box is displayed$",sftpDataTransferPage::verifyCheckFingerprintToVerifyServerCheckBoxIsDisplayed);
        And("^HE I verify that the TEST AND SAVE button is displayed$",sftpDataTransferPage::verifyTestAndSaveButtonIsDisplayed);
        And("^HE I verify the title of the page is \"([^\"]*)\"$",sftpDataTransferPage::verifySftpDataTransferTitleLink);
        And("^HE I verify that when clicking on the title link I am redirected to the main page$",sftpDataTransferPage::verifySftDataTransferTitleLinkBehavior);
        And("^HE I verify the text of the generate ssh key button is \"([^\"]*)\"$",sftpDataTransferPage::verifyGenerateKeyButtonText);
        And("^HE I verify the text of the re generate ssh key button is \"([^\"]*)\"$",sftpDataTransferPage::verifyReGenerateKeyButtonText);
        And("^I generate a new SSH Key$",sftpDataTransferPage::generateSSHKey);
        And("^I verify a new SSH key was generated$",sftpDataTransferPage::verifySshKeyWasGenerated);
        And("^I verify the generated ssh key message that says \"([^\"]*)\"$",sftpDataTransferPage::verifyGeneratedSshKeyMessage);
    }
}
