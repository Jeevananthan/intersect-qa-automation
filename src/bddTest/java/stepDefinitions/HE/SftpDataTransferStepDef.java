package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.accountSettingsPage.SftpDataTransferPageImpl;

public class SftpDataTransferStepDef implements En {
    public SftpDataTransferStepDef(){
        SftpDataTransferPageImpl sftpDataTransferPage = new SftpDataTransferPageImpl();
        Given("^HE I delete the SFTP Data Transfer connection", sftpDataTransferPage::deleteSftpDataTransferConnection);
        And("HE I verify the text \"([^\"]*)\" is displayed",sftpDataTransferPage::verifySftpDataTransferText);
        And("HE I verify the SET UP CONNECTION button is displayed",sftpDataTransferPage::verifySetupConnectionButtonIsDisplayed);
    }
}
