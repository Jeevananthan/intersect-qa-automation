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
        And("^HE I setup a SFTP connection with the following data$",sftpDataTransferPage::setupFtpConnection);
        And("^HE I verify that clicking on GO BACK button it cancels the configuration deletion$", sftpDataTransferPage::verifyGoBackButtonBehaviorWhenDeletingConnection);
        And("^HE I verify that the success toast that says \"([^\"]*)\" is displayed$", sftpDataTransferPage::verifySuccessToastText);
        And("^HE I enable the server fingerprint verification$", sftpDataTransferPage::enableServerFingerPrintVerification);
        And("^HE I verify that the new fingerprint detected alert box has the text \"([^\"]*)\"$", sftpDataTransferPage::verifyTextInNewFingerprintDetectedAlertBox);
        And("^HE I select the Yes, Fingerprint is Correct link$", sftpDataTransferPage::clickOnYesFingerPrintIsCorrect);
        And("^HE I verify that the server fingerprint verification is enabled$", sftpDataTransferPage::verifyFingerPrintToVerifyServerIsEnabled);
        And("^HE I disable the server fingerprint verification$", sftpDataTransferPage::disableServerFingerPrintVerification);
        And("^HE I select the Disable Server Fingerprint Verification link$", sftpDataTransferPage::clickOnDisableFingerPrintVerification);
        And("^HE I verify that the server fingerprint verification is disabled$", sftpDataTransferPage::verifyFingerPrintToVerifyServerIsNotEnabled);
        And("^HE I verify that the new fingerprint detected alert box is not displayed$", sftpDataTransferPage::verifyNewFingerPrintDetectedAlertBoxIsNotDisplayed);
        And("^HE I go to the Set Edit Connection page$",sftpDataTransferPage::goToEditConnectionPage);
        And("^HE I verify the last updated label has the text: \"([^\"]*)\"$", sftpDataTransferPage::verifyConnectionLastUpdateText);
        And("^HE I go to the Main Sftp Connection page$",sftpDataTransferPage::goToSftpConnectionMainPageThroughTitleLink);
        And("^HE fill the sftp connection form with the following data$",sftpDataTransferPage::fillSftpConnectionData);
    }
}
