package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.accountSettingsPage.SftpDataTransferPageImpl;

public class SftpDataTransferStepDef implements En {
    public SftpDataTransferStepDef(){
        SftpDataTransferPageImpl sftpDataTransferPage = new SftpDataTransferPageImpl();
        Given("^HE I delete the SFTP Data Transfer connection", sftpDataTransferPage::deleteSftpDataTransferConnection);
    }
}
